package pl.com.damdor.notepad.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import pl.com.damdor.notepad.NotepadApplication;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.view.adapters.NoteListAdapter;
import pl.com.damdor.notepad.viewmodel.NoteListViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private NoteListViewModel mViewModel;

    private ListView mListView;
    private FloatingActionButton mAddNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        mListView = findViewById(R.id.activity_note_list_list);
        mAddNoteButton = findViewById(R.id.activity_note_list_add_note_button);

        mViewModel = ViewModelProviders
                .of(this, new NoteListViewModel.Factory(NotepadApplication.getRepository()))
                .get(NoteListViewModel.class);

        mViewModel.notes().observe(this, this::onNoteListChanged);
        mViewModel.editNoteEvent().observe(this, this::editNote);

        mAddNoteButton.setOnClickListener(v -> mViewModel.createNote());
    }

    private void onNoteListChanged(List<Note> notes) {
        mListView.setAdapter(createAdapter(notes));
    }

    private NoteListAdapter createAdapter(List<Note> notes){
        NoteListAdapter adapter = new NoteListAdapter(this, notes);
        adapter.setOnDeleteNoteListener(this::deleteNote);
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

    private void deleteNote(Note note){
        mViewModel.deleteNote(note.getId());
    }
}
