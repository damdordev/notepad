package pl.com.damdor.notepad.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.transition.Scene;
import androidx.transition.TransitionManager;
import pl.com.damdor.notepad.NotepadApplication;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.view.adapters.NoteListAdapter;
import pl.com.damdor.notepad.viewmodel.NoteListViewModel;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private NoteListViewModel mViewModel;

    private ViewGroup mLayout;
    private ListView mListView;
    private FloatingActionButton mAddNoteButton;
    private ViewGroup mCancelDeletePanel;
    private Button mCancelDeleteButton;
    private ViewPropertyAnimator mCurrentAnimator;
    private boolean mWasAnimationFired = false;
    private Handler mHidePanelHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        mLayout = findViewById(R.id.activity_note_list_layout);
        mListView = findViewById(R.id.activity_note_list_list);
        mAddNoteButton = findViewById(R.id.activity_note_list_add_note_button);
        mCancelDeletePanel = findViewById(R.id.panel_cancel);
        mCancelDeleteButton = findViewById(R.id.button_cancel_delete);

        mViewModel = ViewModelProviders
                .of(this, new NoteListViewModel.Factory(NotepadApplication.getRepository()))
                .get(NoteListViewModel.class);

        mViewModel.notes().observe(this, this::onNoteListChanged);
        mViewModel.editNoteEvent().observe(this, this::editNote);
        mViewModel.showCancelDeletePanel().observe(this, v -> showCancelDeletePanel());
        mViewModel.hideCancelDeletePanel().observe(this, v -> hideCancelDeletePanel());

        mAddNoteButton.setOnClickListener(v -> mViewModel.createNote());
        mCancelDeleteButton.setOnClickListener(v -> mViewModel.cancelDelete());

        mCancelDeletePanel.setTranslationY(mCancelDeletePanel.getHeight());
    }

    private void onNoteListChanged(List<Note> notes) {
        mListView.setAdapter(createAdapter(notes));
    }

    private NoteListAdapter createAdapter(List<Note> notes){
        NoteListAdapter adapter = new NoteListAdapter(this, notes);
        adapter.setOnDeleteNoteListener(mViewModel::deleteNote);
        adapter.setOnEditNoteListener(this::onNoteClicked);
        return adapter;
    }

    private void onNoteClicked(Note note) {
        editNote(note.getId());
    }

    private void editNote(Long id) {
        Intent intent = new Intent(this, NoteEditActivity.class);
        intent.putExtra(NoteEditActivity.INTENT_KEY_NOTE_ID, id);
        startActivity(intent);
    }

    private void showCancelDeletePanel(){
        cancelCurrentAnimation();
        mCancelDeletePanel.setVisibility(View.VISIBLE);
        if(!mWasAnimationFired){
            mCancelDeletePanel.setTranslationY(mCancelDeletePanel.getHeight());
            mWasAnimationFired = true;
        }

        mCurrentAnimator = mCancelDeletePanel
                .animate()
                .translationY(0.0f);
        mCurrentAnimator.start();

        mHidePanelHandler = new Handler();
        mHidePanelHandler.postDelayed(() -> hideCancelDeletePanel(), 6000);
    }

    private void hideCancelDeletePanel(){
        cancelCurrentAnimation();

        mCurrentAnimator = mCancelDeletePanel
                .animate()
                .translationY(mCancelDeletePanel.getHeight())
                .withEndAction(() -> mCancelDeletePanel.setVisibility(View.INVISIBLE));
        mCurrentAnimator.start();
    }

    private void cancelCurrentAnimation(){
        if(mCurrentAnimator != null){
            mCurrentAnimator.cancel();
            mCurrentAnimator = null;
        }

        if(mHidePanelHandler != null){
            mHidePanelHandler.removeCallbacksAndMessages(null);
            mHidePanelHandler = null;
        }
    }

}
