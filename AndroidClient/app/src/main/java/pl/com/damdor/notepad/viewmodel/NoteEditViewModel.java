package pl.com.damdor.notepad.viewmodel;

import com.annimon.stream.Stream;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;

/**
 * Created by Damian Doroba on 2019-03-12.
 */
public class NoteEditViewModel extends ViewModel {

    public static class Factory extends NoteRepositoryViewModel {

        private long mId;

        public Factory(NoteRepository repository, long id){
            super(repository);
            mId = id;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new NoteEditViewModel(getRepository(), mId);
        }

    }

    private NoteRepository mRepository;
    private long mId;
    private MutableLiveData<Note> mNote = new MutableLiveData<>();

    public NoteEditViewModel(NoteRepository repository, long id){
        mRepository = repository;
        mId = id;
        repository.load(this::onAllNotesLoaded);
    }

    public LiveData<Note> note(){
        return mNote;
    }

    private void onAllNotesLoaded(List<Note> notes){
        Note note = Stream.of(notes).filter(n -> n.getId() == mId).findFirst().orElse(null);
        if(note == null){
            note = new Note();
        }
        mNote.postValue(note);
    }

}
