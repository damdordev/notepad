package pl.com.damdor.notepad.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import pl.com.damdor.notepad.R;

import android.os.Bundle;

public class NoteEditActivity extends AppCompatActivity {

    public static final String INTENT_KEY_NOTE_ID = "noteId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
    }
}
