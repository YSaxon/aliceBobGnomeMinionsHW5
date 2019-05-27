package com.company;

public class Gnome extends Critter implements Runnable {

    public static GroupByGroup groupByGroup = new GroupByGroup(false,Main.numGnomes, Main.Coordination.MinionsHaveGone::release);

    //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
    //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
    //    public Semaphore lineByDoor=new Semaphore(-1,true);
    //private LineByDoor<Gnome> lineByDoor;

    public Gnome(String s, LineByDoor<Gnome> gnomeLineByDoor) {
        super(s, gnomeLineByDoor, "mines", groupByGroup, "go play outside");
    }

    @Override
    protected void PartingWordsToAlice() {
        System.out.println(name + " says to alice \"Have a good day\"");
    }

}
