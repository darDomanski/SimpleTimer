package com.darski.SimpleTimer.service;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timer extends Thread {

    private int seconds;
    private boolean isRunning;
    private boolean isPaused;
    private StringProperty timerProperty;

    public Timer() {
        timerProperty = new SimpleStringProperty(this, "timerProperty", toString());
        isRunning = true;
        isPaused = false;
    }

    public StringProperty getTimerProperty() {
        return timerProperty;
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (this) {
                if (!isRunning) {
                    interrupt();
                    break;
                }
                try {
                    if (isPaused) {
                        wait();
                    }
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                seconds++;
                Platform.runLater(() -> timerProperty.setValue(toString()));
            }
        }
    }

    public void startTimer() {
        synchronized (this) {
            if (isPaused) {
                isPaused = false;
            }
            if (!this.isAlive()) {
                start();
            }
            notifyAll();
        }
    }

    public void pause() {
        isPaused = true;
    }

    public void stopTimer() {
        isRunning = false;
    }

    @Override
    public String toString() {
        int hours = seconds / 3600;
        int minutes = seconds / 60;
        int sec = seconds % 60;
        return hours + "h:" + minutes + "m:" + sec + "s";
    }
}
