package pl.com.damdor.notepad.storage;

import com.annimon.stream.Stream;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Damian Doroba on 2019-03-26.
 */
public abstract class BaseNoteRepository implements NoteRepository {

    private Set<ChangeListener> mListeners = new HashSet<>();

    @Override
    public void register(ChangeListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregister(ChangeListener listener) {
        mListeners.remove(listener);
    }

    protected void notifyChanged(){
        Stream.of(mListeners).forEach(ChangeListener::onChanged);
    }

}
