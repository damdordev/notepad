package pl.com.damdor.notepad.testutils;

import java.util.Arrays;
import java.util.List;

import pl.com.damdor.notepad.data.Note;

/**
 * Created by Damian Doroba on 2019-03-12.
 */
public class TestData {
    public static final List<Note> TEST_NOTES = Arrays.asList(
            Note.create(1, "title1", "content1"),
            Note.create(2, "title2", "content2"),
            Note.create(3, "title3", "content3")
    );

    public static final long NO_EXISTING_NOTE_ID = 100;
}
