package pl.com.damdor.notepad.storage.sql;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import androidx.room.Room;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.BaseNoteRepository;
import pl.com.damdor.notepad.storage.NoteRepository;

/**
 * Created by Damian Doroba on 2019-02-26.
 */
public class SqlNoteRepository extends BaseNoteRepository {

    private final NoteDatabase mDatabase;

    public SqlNoteRepository(NoteDatabase database){
        mDatabase = database;
    }

    @SuppressWarnings("unused")
    public SqlNoteRepository(Context context){
        mDatabase = Room
                .databaseBuilder(context.getApplicationContext(),
                                 NoteDatabase.class,
                                 "notes-database")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public void load(NoteLoadListener listener) {
        if(listener != null) {
             listener.onNotesLoaded(Stream.of(mDatabase.noteDao().getAll())
                     .map(this::convert)
                     .collect(Collectors.toList()));
        }
    }

    @Override
    public void update(Note note, NoteUpdateListener listener) {
        long id = mDatabase.noteDao().insert(convert(note));
        if(listener != null) {
            listener.onNoteUpdated(id);
        }
        notifyChanged();
    }

    @Override
    public void delete(long noteId, NoteDeleteListener listener) {
        mDatabase.noteDao().delete(noteId);
        if(listener != null){
            listener.onNoteDeleted();
        }
        notifyChanged();
    }

    private Note convert(NoteData data){
        Note note = new Note();

        note.setId(data.id);
        note.setTitle(data.title);
        note.setContent(data.content);

        return note;
    }

    private NoteData convert(Note note){
        NoteData data = new NoteData();

        data.id = note.getId();
        data.title = note.getTitle();
        data.content = note.getContent();

        return data;
    }

}
