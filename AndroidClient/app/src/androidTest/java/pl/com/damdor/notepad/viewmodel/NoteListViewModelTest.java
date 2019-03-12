package pl.com.damdor.notepad.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.Lifecycle;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.testutils.BaseViewModelTest;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
public class NoteListViewModelTest extends BaseViewModelTest {

    private static final List<Note> TEST_NOTES = Arrays.asList(
            Note.create(1, "title1", "content1"),
            Note.create(2, "title2", "content2"),
            Note.create(3, "title3", "content3")
    );

    private NoteRepository mRepository;

    @Before
    public void setup(){
        mRepository = new ListNoteRepository(TEST_NOTES);
    }

    @Test
    public void testGetAllNotes() throws InterruptedException {
        NoteListViewModel viewModel = new NoteListViewModel.Factory(mRepository).create(NoteListViewModel.class);
        TestObserver<List<Note>> observer = new TestObserver<>();

        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue();

        assertEquals(TEST_NOTES, observer.getResult());
    }
}