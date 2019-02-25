package pl.com.damdor.notepad.data;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class NoteTest {

    @Test
    public void testId(){
        Note note = new Note();

        note.setId(5);
        assertEquals(5, note.getId());
    }

    @Test
    public void testTitle() {
        Note note = new Note();

        note.setTitle("test title");
        assertEquals("test title", note.getTitle());
    }

    @Test
    public void testContent() {
        Note note = new Note();

        note.setContent("test content");
        assertEquals("test content", note.getContent());
    }

    @Test
    public void testCreateTime() {
        Note note = new Note();

        note.setCreateTime(createCalendar(2000, 5, 12, 13, 0, 0, 0));
        assertEquals(createCalendar(2000, 5, 12, 13, 0, 0, 0),
                     note.getCreateTime());
    }

    @Test
    public void setUpdateTime() {
        Note note = new Note();

        note.setLastUpdateTime(createCalendar(2000, 5, 12, 13, 0, 0, 0));
        assertEquals(createCalendar(2000, 5, 12, 13, 0, 0, 0),
                     note.getLastUpdateTime());
    }

    private Calendar createCalendar(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        return calendar;
    }

    @Test
    public void testNewNotesEquals() {
        assertEquals(new Note(), new Note());
    }

    @Test
    public void testNotEquals() {
        Note n1 = new Note();
        n1.setId(5);

        Note n2 = new Note();
        n2.setId(7);

        assertNotEquals(n1, n2);
    }

    @Test
    public void testEqualsNotes() {
        Note n1 = new Note();
        n1.setId(5);
        n1.setTitle("title");

        Note n2 = new Note();
        n2.setId(5);
        n2.setTitle("title");

        assertEquals(n1, n2);
    }

    @Test
    public void testNewNote() {
        Note note = new Note();

        assertEquals("", note.getTitle());
        assertEquals("", note.getContent());
    }

    @Test
    public void testEmptyNoteClone() {
        Note note = new Note();

        assertEquals(note, note.clone());
    }

    @Test
    public void testNoEmptyNoteClone() {
        Note note = new Note();
        note.setId(5);
        note.setTitle("some title");
        note.setContent("some content");

        assertEquals(note, note.clone());
    }

    @Test
    public void testIfCloneCreatesNewInstance(){
        // given
        Note note = new Note();
        Note clone = note.clone();

        // when
        note.setId(5);

        // then
        assertNotEquals(note, clone);
    }
}