package com.company;

public class Gnome extends Critter implements Runnable {


    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore door=new Semaphore(-1,true);
    private Door<Gnome> door;

    public Gnome(String s, Door gnomeDoor) {
        this.door = gnomeDoor;
        name=s;
    }

    @Override
    public void ReceiveLunchAndKiss() {

    }

    public void ComeHome() {
        door.WaitInLineAtDoor(this);
    }
}
