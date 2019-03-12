package pl.com.damdor.notepad.view.adapters;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import androidx.test.platform.app.InstrumentationRegistry;
import pl.com.damdor.notepad.R;
import pl.com.damdor.notepad.data.Note;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-12.
 */
public class NoteListAdapterTest {

    private static final List<Note> TEST_NOTES = Arrays.asList(
            Note.create(1, "title1", "content1"),
            Note.create(2, "title2", "content2"),
            Note.create(3, "title3", "content3")
    );

    private NoteListAdapter mAdapter;

    @Before
    public void setup(){
        mAdapter = new NoteListAdapter(InstrumentationRegistry.getInstrumentation().getTargetContext(), TEST_NOTES);
    }

    @Test
    public void testView(){
        @SuppressWarnings("ConstantConditions")
        View view = mAdapter.getView(0, null, null);

        TextView titleText = view.findViewById(R.id.item_note_title);
        assertEquals(TEST_NOTES.get(0).getTitle(), titleText.getText());
    }

}