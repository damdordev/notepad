package pl.com.damdor.notepad.storage;

import org.javatuples.Pair;
import org.junit.Test;

import java.util.List;

import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.testutils.AsynchronousTestListener;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-03.
 */
public abstract class NoteRepositoryTest {

    protected class TestNoteLoadListener extends AsynchronousTestListener<List<Note>> implements NoteRepository.NoteLoadListener {

        @Override
        public void onNotesLoaded(List<Note> notes) {
            setupResult(notes);
        }
    }

    protected class TestNoteUpdateListener extends AsynchronousTestListener<Pair<Boolean, Long>> implements NoteRepository.NoteUpdateListener {

        @SuppressWarnings("WeakerAccess")
        public TestNoteUpdateListener(){
            setupResult(new Pair<>(false, Note.UNINITIALIZED_ID));
        }

        @Override
        public void onNoteUpdated(long id) {
            setupResult(new Pair<>(true, id));
        }
    }

    protected class TestNoteDeleteListener extends AsynchronousTestListener<Boolean> implements NoteRepository.NoteDeleteListener {
        @Override
        public void onNoteDeleted() {
            setupResult(true);
        }
    }

    protected abstract NoteRepository createEmptyRepository();

    protected void makeChangeInNote(Note note){
        note.setTitle(note.getTitle() + "_change");
    }

    protected List<Note> load(NoteRepository repository) throws InterruptedException {
        TestNoteLoadListener listener = new TestNoteLoadListener();
        repository.load(listener);
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (listener){
            listener.wait(100);
        }
        return listener.getResult();
    }

    @SuppressWarnings("WeakerAccess")
    protected Pair<Boolean, Long> update(NoteRepository repository, Note note) throws InterruptedException {
        TestNoteUpdateListener listener = new TestNoteUpdateListener();

        repository.update(note, listener);
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (listener){
            listener.wait(100);
        }

        return listener.getResult();
    }

    @SuppressWarnings("WeakerAccess")
    protected boolean delete(NoteRepository repository, int noteId) throws InterruptedException {
        TestNoteDeleteListener listener = new TestNoteDeleteListener();

        repository.delete(noteId, listener);
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (listener){
            listener.wait(100);
        }

        return listener.getResult();
    }

    @Test
    public void testEmptyNoteList() throws InterruptedException {
        List<Note> loadedNotes = load(createEmptyRepository());

        assertNotNull(loadedNotes);
        assertEquals(0, loadedNotes.size());
    }

    @Test
    public void testIfRepositoryUsesNotesClones() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = new Note();
        note.setId(5);

        update(repository, note);
        makeChangeInNote(note);
        Note loadedNote = load(repository).get(0);

        assertNotEquals(note, loadedNote);
    }

    @Test
    public void testIfAddingNewNoteNotifyListener() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        makeChangeInNote(note);

        Pair<Boolean, Long> updateStatus = update(repository, note);

        assertTrue(updateStatus.getValue0());
    }

    @Test
    public void testIfAddingNewGeneratesNewId() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();

        Pair<Boolean, Long> updateStatus = update(repository, note);

        assertNotEquals((Long) Note.UNINITIALIZED_ID, updateStatus.getValue1());
    }

    @Test
    public void testIfNewNoteIsAddedToNoteList() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        makeChangeInNote(note);

        Pair<Boolean, Long> updateStatus = update(repository, note);
        note.setId(updateStatus.getValue1());

        List<Note> loadedNotes = load(repository);

        assertEquals(1, loadedNotes.size());
        assertEquals(note, loadedNotes.get(0));
    }

    @Test
    public void testIfNoteUpdateValues() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        Pair<Boolean, Long> updateStatus = update(repository, note);
        note.setId(updateStatus.getValue1());
        note = load(repository).get(0);
        makeChangeInNote(note);
        update(repository, note);

        Note loadedNote = load(repository).get(0);

        assertEquals(loadedNote, note);
    }

    @Test
    public void noteIfNoteUpdateRemainsId() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        note.setId(5);

        update(repository, note);
        Note loadedNote = load(repository).get(0);

        assertEquals(loadedNote, note);
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
    public void testIfNoteIsUpdated() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();

        Note note = new Note();
        note.setId(update(repository, note).getValue1());

        makeChangeInNote(note);
        update(repository, note);

        Note loadedNote = load(repository).get(0);

        assertEquals(note, loadedNote);
    }

    @Test
    public void testDeleteExistingNote() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = new Note();
        note.setId(5);

        update(repository, note);
        boolean notified = delete(repository, 5);

        assertTrue(notified);

        List<Note> notes = load(repository);
        assertEquals(0, notes.size());
    }

    @Test
    public void testDeleteNotExistingNote() throws InterruptedException {
        NoteRepository repository = createEmptyRepository();
        Note note = new Note();
        note.setId(5);

        update(repository, note);
        boolean notified = delete(repository, 6);

        assertTrue(notified);

        List<Note> notes = load(repository);
        assertEquals(1, notes.size());
    }

}