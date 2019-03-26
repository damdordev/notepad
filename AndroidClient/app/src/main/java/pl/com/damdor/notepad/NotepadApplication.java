package pl.com.damdor.notepad;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.storage.sql.SqlNoteRepository;

/**
 * Created by Damian Doroba on 2019-03-07.
 */
public class NotepadApplication extends Application {

    private static NoteRepository mNoteRepository;

    public static NoteRepository getRepository(){
        return mNoteRepository;
    }

    @SuppressWarnings("unused")
    public NotepadApplication() {
        mNoteRepository = new ListNoteRepository(createStubNoteList());
    }

    private List<Note> createStubNoteList(){
        List<Note> notes = new ArrayList<>();
        notes.add(Note.create(1, "title1", "content1"));
        notes.add(Note.create(2, "title2", "content2"));
        notes.add(Note.create(3, "title3", "content3"));

        return notes;
    }
}
