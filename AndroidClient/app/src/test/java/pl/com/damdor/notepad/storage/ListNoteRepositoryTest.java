package pl.com.damdor.notepad.storage;

import org.javatuples.Pair;
import org.javatuples.Tuple;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.testutils.AsynchronousTestListener;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class ListNoteRepositoryTest {

    private class TestNoteLoadListener extends AsynchronousTestListener<List<Note>> implements NoteRepository.NoteLoadListener {

        @Override
        public void onNotesLoaded(List<Note> notes) {
            setupResult(notes);
        }
    }

    private class TestNoteUpdateListener extends AsynchronousTestListener<Pair<Boolean, Note>> implements NoteRepository.NoteUpdateListener {

        @Override
        public void onNoteUpdated(boolean isNewNote, Note note) {
            setupResult(new Pair<>(isNewNote, note));
        }
    }

    private class TestNoteDeleteListener extends AsynchronousTestListener<Note> implements NoteRepository.NoteDeleteListener {
        @Override
        public void onNoteDeleted(Note deletedNote) {
            setupResult(deletedNote);
        }
    }


    @Test
    public void testEmptyNoteList() throws InterruptedException {
        List<Note> loadedNotes = load(createEmptyRepository());

        assertNotNull(loadedNotes);
        assertEquals(0, loadedNotes.size());
    }

    @Test
    public void testIfRepositoryUseListCopy() throws InterruptedException {
        List<Note> notes = new ArrayList<>();
        ListNoteRepository repository = new ListNoteRepository(notes);
        notes.add(createNote());

        List<Note> loadedNotes = load(repository);

        assertEquals(0, loadedNotes.size());
    }

    @Test
    public void testIfRepositoryUseNotesClones() throws InterruptedException {
        Note note = createNote();
        List<Note> notes = createListWithNote(note);
        ListNoteRepository repository = new ListNoteRepository(notes);

        makeChangeInNote(note);
        List<Note> loadedNotes = load(repository);

        assertNotEquals(note, loadedNotes.get(0));
    }

    @Test
    public void testIfRepositoryReturnsNotesClones() throws InterruptedException {
        NoteRepository repository = createRepositoryWithNote();

        List<Note> loadedNotes = load(repository);
        Note firstNote = loadedNotes.get(0);
        makeChangeInNote(firstNote);
        loadedNotes = load(repository);

        assertNotEquals(firstNote, loadedNotes.get(0));
    }

    @Test
    public void testAddNewNote() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        makeChangeInNote(note);

        Pair<Boolean, Note> updateStatus = update(repository, note);

        assertNotNull(updateStatus);
        assertTrue(updateStatus.getValue0());
        assertEquals(note, updateStatus.getValue1());
    }

    @Test
    public void testIfNewNoteIsAddedToNoteList() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = new Note();
        makeChangeInNote(note);

        update(repository, note);
        List<Note> loadedNotes = load(repository);

        assertEquals(1, loadedNotes.size());
        assertEquals(note, loadedNotes.get(0));
    }

    @Test
    public void testIfNoteUpdateClone() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = new Note();
        makeChangeInNote(note);

        update(repository, note);
        makeChangeInNote(note);
        List<Note> loadedNotes = load(repository);

        assertNotEquals(note, loadedNotes.get(0));
    }

    @Test
    public void testUpdateExistingNote() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        makeChangeInNote(note);

        update(repository, note);
        makeChangeInNote(note);
        Pair<Boolean, Note> updateStatus = update(repository, note);

        assertNotNull(updateStatus);
        assertFalse(updateStatus.getValue0());
        assertEquals(note, updateStatus.getValue1());
    }

    @Test
    public void testIfNoteUpdateIsAddedToNoteList() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = createNote();
        makeChangeInNote(note);

        update(repository, note);
        makeChangeInNote(note);
        update(repository, note);
        List<Note> loadedNotes = load(repository);

        assertEquals(1, loadedNotes.size());
        assertEquals(note, loadedNotes.get(0));
    }

    @Test
    public void testDeleteExistingNote() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = createNote();
        note.setId(5);

        update(repository, note);
        Note deletedNote = delete(repository, 5);

        assertNotNull(deletedNote);
        assertEquals(note, deletedNote);
    }

    @Test
    public void testDeleteNotExistingNote() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note deletedNote = delete(repository, 5);

        assertNull(deletedNote);
    }

    private NoteRepository createEmptyRepository(){
        return new ListNoteRepository(new ArrayList<>());
    }

    private NoteRepository createRepositoryWithNote(){
        return createRepositoryWithNote(createNote());
    }

    private NoteRepository createRepositoryWithNote(Note note){
        return new ListNoteRepository(createListWithNote(note));
    }

    private List<Note> createListWithNote(Note note){
        ArrayList<Note> list = new ArrayList<>();
        list.add(note);
        return list;
    }

    private Note createNote(){
        Note note = new Note();
        note.setId(17);
        return note;
    }

    private void makeChangeInNote(Note note){
        note.setTitle(note.getTitle() + "_change");
    }

    private List<Note> load(NoteRepository repository) throws InterruptedException {
        TestNoteLoadListener listener = new TestNoteLoadListener();
        repository.load(listener);
        synchronized (listener){
            listener.wait(100);
        }
        return listener.getResult();
    }

    private Pair<Boolean, Note> update(NoteRepository repository, Note note) throws InterruptedException {
        TestNoteUpdateListener listener = new TestNoteUpdateListener();

        repository.update(note, listener);
        synchronized (listener){
            listener.wait(100);
        }

        return listener.getResult();
    }

    private Note delete(NoteRepository repository, int noteId) throws InterruptedException {
        TestNoteDeleteListener listener = new TestNoteDeleteListener();

        repository.delete(noteId, listener);
        synchronized (listener){
            listener.wait(100);
        }

        return listener.getResult();
    }


}