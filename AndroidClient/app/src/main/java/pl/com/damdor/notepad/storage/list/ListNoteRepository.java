package pl.com.damdor.notepad.storage.list;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.List;

import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class ListNoteRepository implements NoteRepository {

    private final List<Note> mNotes;

    public ListNoteRepository(List<Note> notes){
        mNotes = cloneNoteList(notes);
        sortNoteList();
    }

    @Override
    public void load(final NoteLoadListener listener) {
        if(listener != null) {
            listener.onNotesLoaded(cloneNoteList(mNotes));
        }
    }

    @Override
    public void update(Note note, NoteUpdateListener listener) {
        deleteIfExist(note.getId());
        Note clone = note.clone();
        if(clone.getId() == Note.UNINITIALIZED_ID){
            clone.setId(generateId());
        }
        mNotes.add(clone);
        sortNoteList();

        if(listener != null) {
            listener.onNoteUpdated(clone.getId());
        }
    }

    @Override
    public void delete(int noteId, NoteDeleteListener listener) {
        deleteIfExist(noteId);
        if(listener != null){
            listener.onNoteDeleted();
        }
    }

    private List<Note> cloneNoteList(List<Note> noteList){
        return Stream.of(noteList).map(Note::clone).collect(Collectors.toList());
    }

    private void deleteIfExist(long id){
        int index = 0;
        while (index < mNotes.size()){
            if(mNotes.get(index).getId() == id){
                break;
            }
            ++index;
        }

        if(index < mNotes.size()){
            mNotes.remove(index);
        }

    }

    private void sortNoteList(){
        Collections.sort(mNotes, (o1, o2) -> Long.compare(o1.getId(), o2.getId()));
    }

    private long generateId(){
        return mNotes.size() > 0 ? mNotes.get(mNotes.size()-1).getId()+1 : 1;
    }

}
