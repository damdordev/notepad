package pl.com.damdor.notepad.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import androidx.lifecycle.Lifecycle;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.testutils.BaseViewModelTest;
import pl.com.damdor.notepad.testutils.TestData;
import pl.com.damdor.notepad.testutils.TestObserver;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
public class NoteListViewModelTest extends BaseViewModelTest {

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

}