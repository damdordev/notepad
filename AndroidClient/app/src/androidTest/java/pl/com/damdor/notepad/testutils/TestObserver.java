package pl.com.damdor.notepad.testutils;

import androidx.lifecycle.Observer;

/**
 * Created by Damian Doroba on 2019-03-13.
 */
@SuppressWarnings("WeakerAccess")
public class TestObserver<T> extends AsynchronousTestListener<T> implements Observer<T> {

    @Override
    public void onChanged(T value) {
        setupResult(value);
    }

}
