package com.company;

import java.util.concurrent.Semaphore;

public class Gnome extends Critter implements Runnable {

    public static GroupByGroup groupByGroup = new GroupByGroup(false, Main.numGnomes, Main.Coordination.MinionsHaveGone::release);

    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore lineByDoor=new Semaphore(-1,true);
    //private LineByDoor<Gnome> lineByDoor;

    public Gnome(String s, LineByDoor<Gnome> gnomeLineByDoor) {
        super(s, gnomeLineByDoor, "mines", groupByGroup, "go play outside");
    }


    public static Semaphore Bathroom = new Semaphore(1);
    @Override
    public void BeforeBed() {
        try {
            Bathroom.acquire();
            System.out.println(name+" using the bathroom");
            sleep((long) (Math.random()*90));
            System.out.println(name+" done with the bathroom");
            Bathroom.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void PartingWordsToAlice() {
        System.out.println(name + " says to alice \"Have a good day\"");
    }

}
