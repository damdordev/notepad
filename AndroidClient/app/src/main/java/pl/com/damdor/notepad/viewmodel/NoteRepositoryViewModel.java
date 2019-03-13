package pl.com.damdor.notepad.viewmodel;

import androidx.lifecycle.ViewModelProvider;
import pl.com.damdor.notepad.storage.NoteRepository;

/**
 * Created by Damian Doroba on 2019-03-13.
 */
public abstract class NoteRepositoryViewModel implements ViewModelProvider.Factory {

    private NoteRepository mRepository;

    public NoteRepositoryViewModel(NoteRepository repository){
        mRepository = repository;
    }

    public NoteRepository getRepository(){
        return mRepository;
    }

}
