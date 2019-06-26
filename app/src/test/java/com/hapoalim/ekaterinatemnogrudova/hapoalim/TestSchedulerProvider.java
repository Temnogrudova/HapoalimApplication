package com.hapoalim.ekaterinatemnogrudova.hapoalim;

import com.hapoalim.ekaterinatemnogrudova.hapoalim.utils.IScheduler;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

public class TestSchedulerProvider implements IScheduler {
    private static TestScheduler testScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }


    public Scheduler computation() {
        return testScheduler;
    }

    public Scheduler io() {
        return testScheduler;
    }

    public Scheduler ui() {
        return testScheduler;

    }
}