package pl.com.damdor.notepad.storage;

import java.util.List;

import pl.com.damdor.notepad.data.Note;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public interface NoteRepository {

    interface ChangeListener {
        void onChanged();
    }

    interface NoteLoadListener {
        void onNotesLoaded(List<Note> notes);
    }

    interface NoteUpdateListener {
        void onNoteUpdated(long id);
    }

    interface NoteDeleteListener {
        void onNoteDeleted();
    }

    void load(NoteLoadListener listener);
    void update(Note note, NoteUpdateListener listener);
    void delete(int noteId, NoteDeleteListener listener);

    void register(ChangeListener listener);
    void unregister(ChangeListener listener);
}
