package pl.com.damdor.notepad.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import pl.com.damdor.notepad.NotepadApplication;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.viewmodel.NoteEditViewModel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NoteEditActivity extends AppCompatActivity {

    public static final String INTENT_KEY_NOTE_ID = "noteId";

    private TextView mTitle;
    private TextView mContent;

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
    }

    private void onNoteChanged(Note note) {
        mTitle.setText(note.getTitle());
        mContent.setText(note.getContent());
    }
}