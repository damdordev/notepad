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

}