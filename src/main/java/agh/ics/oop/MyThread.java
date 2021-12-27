package agh.ics.oop;

import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread extends Thread{
    synchronized protected void suspendMe(AtomicBoolean isRunning){
        try {
            while(!isRunning.get()){
                wait();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void resumeMe(){
        notifyAll();
    }
}
