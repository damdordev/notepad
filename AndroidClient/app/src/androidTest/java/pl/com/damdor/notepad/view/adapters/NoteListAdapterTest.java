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
import pl.com.damdor.notepad.testutils.TestData;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-12.
 */
public class NoteListAdapterTest {

    private NoteListAdapter mAdapter;

    @Before
    public void setup(){
        mAdapter = new NoteListAdapter(InstrumentationRegistry.getInstrumentation().getTargetContext(), TestData.TEST_NOTES);
    }

    @Test
    public void testView(){
        @SuppressWarnings("ConstantConditions")
        View view = mAdapter.getView(0, null, null);

        TextView titleText = view.findViewById(R.id.item_note_title);
        assertEquals(TestData.TEST_NOTES.get(0).getTitle(), titleText.getText());
    }

}