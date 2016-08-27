package com.marco.mvp.model.source;

public abstract class Task implements Runnable {

    private boolean cancelled;


    public void cancel() {
        cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

}
