package pl.com.damdor.notepad.storage;

import java.util.List;

import pl.com.damdor.notepad.data.Note;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public interface NoteRepository {

    public interface NoteLoadListener {
        void onNotesLoaded(List<Note> notes);
    }

    public interface NoteUpdateListener {
        void onNoteUpdated(boolean isNewNote, Note note);
    }

    public interface NoteDeleteListener {
        void onNoteDeleted(Note deletedNote);
    }

    void load(NoteLoadListener listener);
    void update(Note note, NoteUpdateListener listener);
    void delete(int noteId, NoteDeleteListener listener);
}
