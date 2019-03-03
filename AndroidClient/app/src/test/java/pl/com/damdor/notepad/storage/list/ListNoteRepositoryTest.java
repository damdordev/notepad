package pl.com.damdor.notepad.storage.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.NoteRepositoryTest;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class ListNoteRepositoryTest extends NoteRepositoryTest {

    @Test
    public void testIfRepositoryUseListCopy() throws InterruptedException {
        List<Note> notes = new ArrayList<>();
        ListNoteRepository repository = new ListNoteRepository(notes);
        Note note = new Note();
        note.setId(5);
        notes.add(note);

        List<Note> loadedNotes = load(repository);

        assertEquals(0, loadedNotes.size());
    }

    @Test
    public void testIfRepositoryReturnsNotesClones() throws InterruptedException {
        Note note = new Note();
        note.setId(5);
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        NoteRepository repository = new ListNoteRepository(notes);

        List<Note> loadedNotes = load(repository);
        Note firstNote = loadedNotes.get(0);
        makeChangeInNote(firstNote);
        loadedNotes = load(repository);

        assertNotEquals(firstNote, loadedNotes.get(0));
    }

    @Override
    protected NoteRepository createEmptyRepository() {
        return new ListNoteRepository(new ArrayList<>());
    }

}