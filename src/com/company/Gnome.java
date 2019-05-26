package com.company;

public class Gnome extends Critter implements Runnable {


    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore waitingAreaByDoor=new Semaphore(-1,true);
    //private WaitingAreaByDoor<Gnome> waitingAreaByDoor;

    public Gnome(String s, WaitingAreaByDoor<Gnome> gnomeWaitingAreaByDoor) {
        super(s, gnomeWaitingAreaByDoor);
    }

}
