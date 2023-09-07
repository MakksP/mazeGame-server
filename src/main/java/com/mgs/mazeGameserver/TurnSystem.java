package com.mgs.mazeGameserver;

import java.util.concurrent.locks.ReentrantLock;

public class TurnSystem implements Runnable{
    public static final int SINGLE_TURN_TIME_MS = 120;
    public static final ReentrantLock turnLock = new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            turnLock.lock();
            try {
                Thread.sleep(SINGLE_TURN_TIME_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                turnLock.unlock();
            }
        }
    }
}
