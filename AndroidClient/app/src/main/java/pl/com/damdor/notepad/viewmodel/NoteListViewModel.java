package pl.com.damdor.notepad.viewmodel;

import com.annimon.stream.Stream;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
public class NoteListViewModel {

    private NoteRepository mRepository;
    private MutableLiveData<List<Note>> mNote = new MutableLiveData<>();

    public NoteListViewModel(NoteRepository repository){
        mRepository = repository;
        mRepository.load(this::onAllNotesLoaded);
    }

    private void onAllNotesLoaded(List<Note> notes) {
        mNote.postValue(notes);
    }

    public LiveData<List<Note>> notes(){
        return mNote;
    }

}
