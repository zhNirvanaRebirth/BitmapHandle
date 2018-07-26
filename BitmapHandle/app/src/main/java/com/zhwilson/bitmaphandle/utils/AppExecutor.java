package com.zhwilson.bitmaphandle.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static final int THREAD_COUNT = 4;

    private final Executor networkIO;
    private final Executor diskIO;
    private final Executor mainIO;

    private AppExecutor(Executor mainIO, Executor diskIO, Executor networkIO){
        this.mainIO = mainIO;
        this.diskIO = diskIO;
        this.networkIO = networkIO;
    }

    public AppExecutor() {
        this(new MainIOThreadExecutor(), new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT));
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainIO() {
        return  mainIO;
    }

    private static class MainIOThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
