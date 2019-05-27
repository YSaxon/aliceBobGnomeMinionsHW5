package com.company;

import static com.company.Main.Coordination.GetSomeDinner;

public abstract class Critter extends NamedThread {

    public final GroupByGroup groupByGroup;
    public final String StuffTheyDoAfterComingHome;
    public boolean readyForAliceInTheMorning;
    protected LineByDoor<Critter> lineByDoor;
    private String workplace;

    public Critter(String name, LineByDoor lineByDoor, String workplace, GroupByGroup groupByGroup, String stuffTheyDoAfterComingHome) {
        this.name = name;
        this.lineByDoor = lineByDoor;
        this.workplace = workplace;
        this.groupByGroup = groupByGroup;
        StuffTheyDoAfterComingHome = stuffTheyDoAfterComingHome;
    }

    @Override
    public void run() {
        super.run();
        try {
            synchronized (this) {
                readyForAliceInTheMorning = true;
                wait();//wait for alice to make them lunch
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PartingWordsToAlice();
        LeaveForWork();
        Work();
        System.out.println(name + " done working");
        ComeHome();
        Main.Coordination.WaitForBobForDinner(this);
        Main.Coordination.WaitForDinnerToBeReady(this);
        GetSomeDinner(this);
        BeforeBed();
        Main.Coordination.GoToBed(this);
    }

    public void BeforeBed() {
    }


    protected abstract void PartingWordsToAlice();

    public void LeaveForWork() {
        groupByGroup.GoSingleFileWhenLegal(
                () -> System.out.println(name + " is leaving")
        );
        //System.out.println(name+"is leaving through the door and going to work");


    }


    protected void Work() {
        try {
            System.out.println(name + " is working at the " + workplace);
            sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ComeHome() {
        lineByDoor.WaitInLineAtDoor(this);
        System.out.println(name + " is going to go " + StuffTheyDoAfterComingHome);
    }

}
