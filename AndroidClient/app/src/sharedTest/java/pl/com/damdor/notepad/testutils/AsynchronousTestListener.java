package pl.com.damdor.notepad.testutils;

/**
 * Created by Damian Doroba on 2019-02-24.
 */
public class AsynchronousTestListener<T> {

    private T mResult;
    private boolean mHasResult;

    protected void setupResult(T result){
        mResult = result;
        mHasResult = true;
        synchronized (this){
            notifyAll();
        }
    }

    public void waitForValue() throws InterruptedException {
        synchronized (this){
            wait(100);
        }
    }

    public boolean hasResult(){
        return mHasResult;
    }

    public T getResult() {
        return mResult;
    }

    public void reset(){
        mResult = null;
        mHasResult = false;
    }
}
