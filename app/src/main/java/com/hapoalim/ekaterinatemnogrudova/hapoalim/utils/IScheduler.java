package com.hapoalim.ekaterinatemnogrudova.hapoalim.utils;

import io.reactivex.Scheduler;

public interface IScheduler {

    Scheduler io();
    Scheduler ui();
    Scheduler computation();

}