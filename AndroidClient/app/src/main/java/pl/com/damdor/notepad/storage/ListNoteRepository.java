package pl.com.damdor.notepad.storage;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import pl.com.damdor.notepad.data.Note;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class ListNoteRepository implements NoteRepository {

    private List<Note> mNotes;

    public ListNoteRepository(List<Note> notes){
        mNotes = cloneNoteList(notes);
    }

    @Override
    public void load(final NoteLoadListener listener) {
        if(listener != null) {
            listener.onNotesLoaded(cloneNoteList(mNotes));
        }
    }

    @Override
    public void update(Note note, NoteUpdateListener listener) {
        Note deletedNote = deleteIfExist(note.getId());
        Note clone = note.clone();
        mNotes.add(clone);

        if(listener != null) {
            listener.onNoteUpdated(deletedNote == null, clone);
        };
    }

    @Override
    public void delete(int noteId, NoteDeleteListener listener) {
        Note deletedNote = deleteIfExist(noteId);

        if(listener != null){
            listener.onNoteDeleted(deletedNote);
        }
    }

    private List<Note> cloneNoteList(List<Note> noteList){
        return Stream.of(noteList).map(note -> note.clone()).collect(Collectors.toList());
    }

    private Note deleteIfExist(int id){
        int index = 0;
        while (index < mNotes.size()){
            if(mNotes.get(index).getId() == id){
                break;
            }
            ++index;
        }

        Note deletedNote = null;

        if(index < mNotes.size()){
            deletedNote = mNotes.get(index);
            mNotes.remove(index);
        }

        return deletedNote;
    }

}
