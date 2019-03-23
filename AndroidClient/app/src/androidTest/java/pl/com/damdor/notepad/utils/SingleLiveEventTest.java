package pl.com.damdor.notepad.utils;

import org.junit.Before;
import org.junit.Test;

import androidx.lifecycle.Lifecycle;
import pl.com.damdor.notepad.testutils.BaseLifecycleTest;
import pl.com.damdor.notepad.testutils.TestObserver;

import static org.junit.Assert.*;

/**
 * Created by Damian Doroba on 2019-03-22.
 */
public class SingleLiveEventTest extends BaseLifecycleTest {

    private SingleLiveEvent<Integer> mEvent;
    private TestObserver<Integer> mObserver;

    @Before
    public void setp(){
        mEvent = new SingleLiveEvent<>();
        mObserver = new TestObserver<>();
        registerObserver(mEvent, mObserver);
        setLifecycleOwnerState(Lifecycle.State.RESUMED);
    }

    @Test
    public void testIfEventDoesntNotifyWhenNotFired() throws InterruptedException {
        mObserver.waitForValue();

        assertFalse(mObserver.hasResult());
    }

    @Test
    public void testIfEventIsFired() throws InterruptedException {
        mEvent.call(null);
        mObserver.waitForValue();

        assertTrue(mObserver.hasResult());
    }

    @Test
    public void testIfInvocationParameterIsCorrectlyPassedToEvent() throws InterruptedException {
        mEvent.call(5);
        mObserver.waitForValue();

        assertTrue(mObserver.getResult().equals(5));
    }

    @Test
    public void testIfParametrlessInvocationWorks() throws InterruptedException {
        SingleLiveEvent<Void> event = new SingleLiveEvent<>();
        TestObserver<Void> observer = new TestObserver<>();
        registerObserver(event, observer);
        event.call();
        observer.waitForValue();

        assertTrue(observer.hasResult());
    }

    @Test
    public void testIfEventIsInvokedOnlyOnce() throws InterruptedException {
        mEvent.call(5);
        TestObserver<Integer> observer2 = new TestObserver<>();
        registerObserver(mEvent, observer2);

        assertFalse(observer2.hasResult());
    }

    @Test
    public void testIfTwoCallsGivesTwoInvocations() throws InterruptedException {
        mEvent.call(5);
        mEvent.call(7);
        mObserver.waitForValue();

        assertTrue(mObserver.getResult().equals(7));
    }

}