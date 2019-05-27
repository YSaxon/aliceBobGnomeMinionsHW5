package com.company;

public abstract class Critter extends Thread{


    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore lineByDoor=new Semaphore(-1,true);
    protected LineByDoor<Critter> lineByDoor;
    public String name;
    public final GroupByGroup groupByGroup;
    private String workplace;
    public boolean readyForAliceInTheMorning;

    public Critter(String name, LineByDoor lineByDoor, String workplace, GroupByGroup groupByGroup) {
        this.name=name;
        this.lineByDoor = lineByDoor;
        this.workplace = workplace;
        this.groupByGroup = groupByGroup;
    }

    @Override
    public void run() {
        super.run();
        try {
            synchronized (this){
                readyForAliceInTheMorning=true;
                wait();//wait for alice to make them lunch
            }
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
        groupByGroup.GoSingleFileWhenLegal(
                ()-> System.out.println(name+" is leaving")
        );
        //System.out.println(name+"is leaving through the door and going to work");
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
