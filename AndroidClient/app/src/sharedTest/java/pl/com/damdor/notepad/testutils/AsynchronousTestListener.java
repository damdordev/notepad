package pl.com.damdor.notepad.testutils;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class AsynchronousTestListener<T> {

    private T mResult;

    protected void setupResult(T result){
        mResult = result;
        synchronized (this){
            notifyAll();
        }
    }

    public T getResult() {
        return mResult;
    }
}