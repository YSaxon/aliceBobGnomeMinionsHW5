package com.company;

public class Minion extends Critter {
    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore door=new Semaphore(-1,true);
        private Door<Minion> door;

    public Minion(String s, Door<Minion> door) {
        name=s;
        this.door = door;
    }


    @Override
    public void ReceiveLunchAndKiss() {


    }

    public void ComeHome() {
        door.WaitInLineAtDoor(this);
    }
}
