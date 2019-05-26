package com.company;

public abstract class Critter extends Thread{


    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore door=new Semaphore(-1,true);
        public static Door door;

    @Override
    public void run() {
        super.run();
        try {
            sleep(999999);
        } catch (InterruptedException e) {
            ReceiveLunchAndKiss();
            LeaveForWork();
        }

    }

    public String name;
    public void ReceiveLunchAndKiss(){
        System.out.println(name + " getting a lunch and a kiss");
    }

    public void LeaveForWork() {
        System.out.println(name+" going to work");
        try {
            this.sleep((long) (Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name+" done working");
        ComeHome();
    }

    public void ComeHome() {
        door.WaitInLineAtDoor(this);
    }
}