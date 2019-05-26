package com.company;

public abstract class Critter extends Thread{


    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore lineByDoor=new Semaphore(-1,true);
    protected LineByDoor<Critter> lineByDoor;
    public String name;
    private String workplace;

    public Critter(String name, LineByDoor lineByDoor, String workplace) {
        this.name=name;
        this.lineByDoor = lineByDoor;
        this.workplace = workplace;
    }

    @Override
    public void run() {
        super.run();
        try {
            synchronized (this){
//            sleep(999999);
            wait();}
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        PartingWordsToAlice();
        LeaveForWork();

    }


    public void ReceiveLunchAndKiss(){
//        System.out.println(name + " got a lunch and a kiss");
//        PartingWordsToAlice();
//        LeaveForWork();
    }

    protected abstract void PartingWordsToAlice();

    public void LeaveForWork() {
        synchronized (LineByDoor.GlobalDoor){
        System.out.println(name+"is leaving through the door and going to work");}
        Work();
        System.out.println(name+" done working");
        ComeHome();
    }

    protected void Work(){
        try {
            System.out.println(name+" is working at the "+workplace);
            this.sleep((long) (Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ComeHome() {
        lineByDoor.WaitInLineAtDoor(this);
    }

}
