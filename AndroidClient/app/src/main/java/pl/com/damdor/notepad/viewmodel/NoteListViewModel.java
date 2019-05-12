package pl.com.damdor.notepad.viewmodel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import pl.com.damdor.notepad.data.Note;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.utils.SingleLiveEvent;

/**
 * Created by Damian Doroba on 2019-03-05.
 */
public class NoteListViewModel extends NoteRepositoryViewModel {

    public static class Factory extends NoteRepositoryViewModel.Factory {
        public Factory(NoteRepository repository){
            super(repository);
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new NoteListViewModel(getRepository());
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final MutableLiveData<List<Note>> mNote = new MutableLiveData<>();
    private final SingleLiveEvent<Long> mEditNote = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mShowCancelDeletePanel = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mHideCancelDeletePanel = new SingleLiveEvent<>();

    private Note mLastDeletedNote;

    private NoteListViewModel(NoteRepository repository){
        super(repository);
        load();
    }

    @Override
    protected void load() {
        getRepository().load(this::onAllNotesLoaded);
    }

    private void onAllNotesLoaded(List<Note> notes) {
        mNote.postValue(notes);
    }

    public LiveData<List<Note>> notes(){
        return mNote;
    }

    public LiveData<Long> editNoteEvent(){
        return mEditNote;
    }

    public LiveData<Void> showCancelDeletePanel() { return mShowCancelDeletePanel; }

    public LiveData<Void> hideCancelDeletePanel() { return mHideCancelDeletePanel; }

    public void createNote(){
        mEditNote.call(Note.UNINITIALIZED_ID);
    }

    public void deleteNote(Note note) {
        mLastDeletedNote = note;
        startOperation();
        getRepository().delete(note.getId(), () -> finishOperation());
        mShowCancelDeletePanel.call();
    }

    public void cancelDelete(){
        if(mLastDeletedNote == null){
            return;
        }
        startOperation();
        getRepository().update(mLastDeletedNote, n -> finishOperation());
        mLastDeletedNote = null;
        mHideCancelDeletePanel.call();
    }
}
