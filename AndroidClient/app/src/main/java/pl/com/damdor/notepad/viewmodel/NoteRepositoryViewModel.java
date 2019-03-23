package pl.com.damdor.notepad.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import pl.com.damdor.notepad.storage.NoteRepository;
import pl.com.damdor.notepad.utils.SingleLiveEvent;

/**
 * Created by Damian Doroba on 2019-03-13.
 */
public abstract class NoteRepositoryViewModel extends ViewModel {

    public static class Factory implements ViewModelProvider.Factory {

        private NoteRepository mRepository;

        public Factory(NoteRepository repository){
            mRepository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return null;
        }

        protected NoteRepository getRepository() { return mRepository; }
    }

    private NoteRepository mRepository;
    private int mOperationCounter = 0;
    private final SingleLiveEvent<Void> mOperationStarted = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mOperationFinished = new SingleLiveEvent<>();

    public NoteRepositoryViewModel(NoteRepository repository){
        mRepository = repository;
    }

    public NoteRepository getRepository(){
        return mRepository;
    }
    public LiveData<Void> operationStarted() { return mOperationStarted; }
    public LiveData<Void> operationFinished() { return mOperationFinished; }

    protected synchronized void startOperation(){
        ++mOperationCounter;
        if(mOperationCounter == 1){
            mOperationStarted.call();
        }
    }

    protected synchronized void finishOperation(){
        --mOperationCounter;
        if(mOperationCounter == 0){
            mOperationFinished.call();
        }
    }

}
