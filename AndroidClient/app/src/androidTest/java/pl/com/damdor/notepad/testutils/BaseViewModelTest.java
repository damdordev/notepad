package pl.com.damdor.notepad.testutils;

import android.app.Instrumentation;

import org.junit.Before;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Created by Damian Doroba on 2019-03-06.
 */
public class BaseViewModelTest implements LifecycleOwner {

    private LifecycleRegistry mLifecycleRegistry;
    private final Instrumentation mInstrumentation = InstrumentationRegistry.getInstrumentation();

    @Before
    public void setupBaseViewModelTest(){
        mLifecycleRegistry = new LifecycleRegistry(this);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @SuppressWarnings("SameParameterValue")
    protected void setLifecycleOwnerState(Lifecycle.State state) {
        mInstrumentation.runOnMainSync(() -> mLifecycleRegistry.markState(state));
    }

    protected void registerObserver(LiveData liveData, Observer observer){
        //noinspection unchecked
        mInstrumentation.runOnMainSync(() -> liveData.observe(this, observer));
    }

}
