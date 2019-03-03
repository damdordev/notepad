package pl.com.damdor.notepad.storage.sql;

import android.content.Context;

import org.junit.After;
import org.junit.Before;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.storage.NoteRepositoryTest;

/**
 * Created by Damian Doroba on 2019-03-03.
 */
public class SqlNoteRepositoryTest extends NoteRepositoryTest {

    private NoteDatabase mDatabase;

    @Override
    protected NoteRepository createEmptyRepository() {
        return new SqlNoteRepository(mDatabase);
    }

    @Before
    public void initDatabase(){
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        mDatabase = Room
                .inMemoryDatabaseBuilder(context, NoteDatabase.class)
                .build();
    }

    @After
    public void freeDatabase(){
        mDatabase.close();
    }
}