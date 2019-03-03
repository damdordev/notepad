package pl.com.damdor.notepad.storage.sql;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * Created by Damian Doroba on 2019-02-26.
 */
@Dao
public interface NoteDAO {

    @Query("SELECT * from NoteData")
    List<NoteData> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NoteData note);

    @Query("DELETE from NoteData where id = :id")
    void delete(int id);
}
