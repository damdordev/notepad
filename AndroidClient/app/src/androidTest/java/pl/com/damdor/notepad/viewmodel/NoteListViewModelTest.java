package pl.com.damdor.notepad.viewmodel;

import android.app.Instrumentation;
import android.content.Context;

import junit.framework.TestListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.list.ListNoteRepository;
import pl.com.damdor.notepad.testutils.BaseViewModelTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
@RunWith(AndroidJUnit4.class)
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
        NoteListViewModel viewModel = new NoteListViewModel(mRepository);
        TestObserver<List<Note>> observer = new TestObserver<>();

        registerObserver(viewModel.notes(), observer);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
        observer.waitForValue(100);

        assertEquals(TEST_NOTES, observer.getResult());
    }
}