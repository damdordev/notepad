package pl.com.damdor.notepad.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import pl.com.damdor.notepad.NotepadApplication;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.viewmodel.NoteEditViewModel;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NoteEditActivity extends AppCompatActivity {

    public static final String INTENT_KEY_NOTE_ID = "noteId";

    private TextView mTitle;
    private TextView mContent;

    private Note mNote;
    private NoteEditViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        mTitle = findViewById(R.id.activity_note_edit_title);
        mContent = findViewById(R.id.activity_note_edit_content);

        mViewModel = ViewModelProviders
                .of(this, new NoteEditViewModel.Factory(NotepadApplication.getRepository(),
                                                                getIntent().getLongExtra(INTENT_KEY_NOTE_ID, 0)))
                .get(NoteEditViewModel.class);

        mViewModel.note().observe(this, this::onNoteChanged);
        mViewModel.closeActivity().observe(this, v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_note_edit_save:
                mViewModel.save(getModifiedNote());
        }
        return true;
    }

    private void onNoteChanged(Note note) {
        mNote = note;
        mTitle.setText(note.getTitle());
        mContent.setText(note.getContent());
    }

    private Note getModifiedNote(){
        Note note = mNote.clone();
        note.setTitle(mTitle.getText().toString());
        note.setContent(mContent.getText().toString());

        return note;
    }
}
