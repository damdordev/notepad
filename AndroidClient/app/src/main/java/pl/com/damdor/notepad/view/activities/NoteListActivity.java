package pl.com.damdor.notepad.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import pl.com.damdor.notepad.NotepadApplication;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.view.adapters.NoteListAdapter;
import pl.com.damdor.notepad.viewmodel.NoteListViewModel;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        mListView = findViewById(R.id.activity_note_list_list);

        ViewModelProviders
                .of(this, new NoteListViewModel.Factory(NotepadApplication.getRepository()))
                .get(NoteListViewModel.class)
                .notes()
                .observe(this, this::onNoteListChanged);
    }

    private void onNoteListChanged(List<Note> notes) {
        mListView.setAdapter(new NoteListAdapter(this, notes));
    }
}
