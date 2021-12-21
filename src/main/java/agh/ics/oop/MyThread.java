package agh.ics.oop;

public class MyThread extends Thread{
    synchronized protected void suspendMe(){
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    synchronized public void resumeMe(){
        notifyAll();
    }
}
