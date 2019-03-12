package pl.com.damdor.notepad.viewmodel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
public class NoteListViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {
        private final NoteRepository mNoteRepository;

        public Factory(NoteRepository noteRepository){
            mNoteRepository = noteRepository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new NoteListViewModel(mNoteRepository);
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final NoteRepository mRepository;
    private final MutableLiveData<List<Note>> mNote = new MutableLiveData<>();

    private NoteListViewModel(NoteRepository repository){
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
