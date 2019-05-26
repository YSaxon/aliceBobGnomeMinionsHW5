package com.company;

public abstract class Critter extends Thread{


    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore waitingAreaByDoor=new Semaphore(-1,true);
    protected WaitingAreaByDoor<Critter> waitingAreaByDoor;
    public String name;

    public Critter(String name, WaitingAreaByDoor waitingAreaByDoor) {
        this.name=name;
        this.waitingAreaByDoor = waitingAreaByDoor;
    }

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
        waitingAreaByDoor.WaitInLineAtDoor(this);
    }

}
