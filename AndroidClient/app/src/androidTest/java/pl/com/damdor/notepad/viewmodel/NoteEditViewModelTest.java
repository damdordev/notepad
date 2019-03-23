package pl.com.damdor.notepad.viewmodel;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import androidx.lifecycle.Lifecycle;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.testutils.BaseLifecycleTest;
import pl.com.damdor.notepad.testutils.TestData;
import pl.com.damdor.notepad.testutils.TestObserver;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Damian Doroba on 2019-03-12.
 */
public class NoteEditViewModelTest extends BaseLifecycleTest {

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

    @Test
    public void testIfSavingNotifyOfActivityClose() throws InterruptedException {
        initViewModelAndObserver(TestData.TEST_NOTES.get(0).getId());
        TestObserver<Void> observer = new TestObserver<>();
        registerObserver(mViewModel.closeActivity(), observer);

        Note note = createModifiedClone(TestData.TEST_NOTES.get(0));
        mViewModel.save(note);

        observer.waitForValue();
        assertTrue(observer.hasResult());
    }

    @Test
    public void testIfSavingNotifyOfOperationStart() throws InterruptedException {
        initViewModelAndObserver(TestData.TEST_NOTES.get(0).getId());
        TestObserver<Void> observer = new TestObserver<>();
        registerObserver(mViewModel.operationStarted(), observer);

        Note note = createModifiedClone(TestData.TEST_NOTES.get(0));
        mViewModel.save(note);

        observer.waitForValue();
        assertTrue(observer.hasResult());
    }

    @Test
    public void testIfSavingNotifyOfOperationFinished() throws InterruptedException {
        initViewModelAndObserver(TestData.TEST_NOTES.get(0).getId());
        TestObserver<Void> observer = new TestObserver<>();
        registerObserver(mViewModel.operationFinished(), observer);

        Note note = createModifiedClone(TestData.TEST_NOTES.get(0));
        mViewModel.save(note);

        observer.waitForValue();
        assertTrue(observer.hasResult());
    }

    @Test
    public void testIfSavingCallsRepositoryMethod() throws InterruptedException {
        mNoteRepository = mock(NoteRepository.class);
        mViewModel = new NoteEditViewModel(mNoteRepository, TestData.TEST_NOTES.get(0).getId());

        mViewModel.save(TestData.TEST_NOTES.get(0));
        verify(mNoteRepository).update(eq(TestData.TEST_NOTES.get(0)), any());
    }

    private void initViewModelAndObserver(long id) throws InterruptedException {
        mViewModel = new NoteEditViewModel(mNoteRepository,id);
        mObserver = new TestObserver<>();

        registerObserver(mViewModel.note(), mObserver);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        mObserver.waitForValue();
    }

    private Note createModifiedClone(Note note){
        note = note.clone();
        note.setTitle("other title");
        note.setContent("other content");
        return note;
    }

}