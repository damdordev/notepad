package pl.com.damdor.notepad.viewmodel;

import org.junit.Before;
import org.junit.Test;

import androidx.lifecycle.Lifecycle;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.testutils.BaseViewModelTest;
import pl.com.damdor.notepad.testutils.TestData;
import pl.com.damdor.notepad.testutils.TestObserver;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-12.
 */
public class NoteEditViewModelTest extends BaseViewModelTest {

    private NoteRepository mNoteRepository;
    private NoteEditViewModel mViewModel;
    private TestObserver mObserver;

    @Before
    public void setup() {
        mNoteRepository = new ListNoteRepository(TestData.TEST_NOTES);
    }

    @Test
    public void testGetExistingNote() throws InterruptedException {
        initViewModelAndObserver(TestData.TEST_NOTES.get(0).getId());
        assertEquals(TestData.TEST_NOTES.get(0), mObserver.getResult());
    }

    @Test
    public void testGetNotExistingNote() throws InterruptedException {
        initViewModelAndObserver(TestData.NO_EXISTING_NOTE_ID);
        assertEquals(new Note(), mObserver.getResult());
    }

    private void initViewModelAndObserver(long id) throws InterruptedException {
        mViewModel = new NoteEditViewModel(mNoteRepository,id);
        mObserver = new TestObserver<>();

        registerObserver(mViewModel.note(), mObserver);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        mObserver.waitForValue();
    }

}