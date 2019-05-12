package pl.com.damdor.notepad.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.testutils.BaseLifecycleTest;
import pl.com.damdor.notepad.testutils.TestData;
import pl.com.damdor.notepad.testutils.TestObserver;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
public class NoteListViewModelTest extends BaseLifecycleTest {

    private NoteRepository mRepository;

    @Before
    public void setup(){
        mRepository = new ListNoteRepository(TestData.TEST_NOTES);
    }

    @Test
    public void testGetAllNotes() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<List<Note>> observer = new TestObserver<>();

        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue();

        assertEquals(TestData.TEST_NOTES, observer.getResult());
    }

    @Test
    public void testCreateNote() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<Long> observer = new TestObserver<>();

        registerObserver(viewModel.editNoteEvent(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        viewModel.createNote();
        observer.waitForValue();

        assertTrue(observer.hasResult());
        assertEquals(Note.UNINITIALIZED_ID, (long) observer.getResult());
    }

    @Test
    public void testDeleteNote() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<List<Note>> observer = new TestObserver<>();
        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue();
        observer.reset();

        viewModel.deleteNote(TestData.TEST_NOTES.get(0));
        observer.waitForValue();

        assertEquals(TestData.TEST_NOTES.size()-1, observer.getResult().size());
    }

    @Test
    public void testIfDeleteShowsCancelDeletePanel() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<List<Note>> observer = new TestObserver<>();
        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue();
        observer.reset();

        TestObserver<Void> showPanelObserver = new TestObserver<>();
        registerObserver(viewModel.showCancelDeletePanel(), showPanelObserver);

        viewModel.deleteNote(TestData.TEST_NOTES.get(0));
        showPanelObserver.waitForValue();

        assertTrue(showPanelObserver.hasResult());
    }

    @Test
    public void testCancelDelete() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<List<Note>> observer = new TestObserver<>();
        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue();
        observer.reset();

        viewModel.deleteNote(TestData.TEST_NOTES.get(0));
        observer.waitForValue();

        observer.reset();
        viewModel.cancelDelete();
        observer.waitForValue();

        assertEquals(TestData.TEST_NOTES.size(), observer.getResult().size());
    }

    @Test
    public void testIfCancelDeleteHidesPanel() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<List<Note>> observer = new TestObserver<>();
        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue();
        observer.reset();

        viewModel.deleteNote(TestData.TEST_NOTES.get(0));
        observer.waitForValue();

        TestObserver<Void> hidePanelObserver = new TestObserver<>();
        registerObserver(viewModel.hideCancelDeletePanel(), hidePanelObserver);

        viewModel.cancelDelete();
        hidePanelObserver.waitForValue();

        assertTrue(hidePanelObserver.hasResult());
    }

}