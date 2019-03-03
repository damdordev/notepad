package pl.com.damdor.notepad.storage.sql;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Damian Doroba on 2019-02-26.
 */
@Entity
class NoteData   {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name="title")
    public String title;

    @ColumnInfo(name="content")
    public String content;
}
