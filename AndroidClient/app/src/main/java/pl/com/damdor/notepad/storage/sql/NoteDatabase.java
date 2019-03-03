package pl.com.damdor.notepad.storage.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Damian Doroba on 2019-02-26.
 */
@Database(entities = {NoteData.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDAO noteDao();
}
