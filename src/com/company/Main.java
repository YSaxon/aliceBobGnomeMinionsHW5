package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    public static final int numMinions = 10;
    public static final int numGnomes = 7;
    private static Gnome[] gnomes;
    private static Minion[] minions;
    private static Alice alice;
    public static Object BobSleeping = new Object();
    private static Bob bob;


    public static void main(String[] args) {
        Door minionDoor = new Door<Minion>(numMinions);
        Door gnomeDoor = new Door<Gnome>(numGnomes,minionDoor.weHaveGone);
        minions = new Minion[numMinions];
        for (int i = 0; i < minions.length; i++) {
            minions[i]=new Minion("minion"+i,minionDoor);
        }
        gnomes = new Gnome[numGnomes];
        for (int i = 0; i < gnomes.length; i++) {
            gnomes[i]=new Gnome("\tgnome"+i,gnomeDoor);
        }
        for (Gnome gnome : gnomes) {
            gnome.start();
        }
        for (Minion minion : minions) {
            minion.start();
        }
        bob = new Bob(BobSleeping,gnomeDoor.weHaveGone);
        alice = new Alice(gnomes,minions,bob);
        alice.start();
    }

    private static void CreateMunchkins() {

        }
    static class Bob extends Critter {

        //    public static ReentrantLock WaitToKnockOnDoor = new ReentrantLock(true);
        //    public static Queue<Minion> OrderOfWaitingAtDoor = new LinkedList<>();
        //    public Semaphore door=new Semaphore(-1,true);
        public static Door<Bob> door;

        public Bob(Object bobSleeping, Semaphore bobCanComeIn) {
            name="\t\tbob";
            door=new Door<Bob>(1,bobCanComeIn);
        }

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(-1);
            } catch (InterruptedException e) {
                System.out.println("Bob has woken up");
            }
            LeaveForWork();
        }

        @Override
        public void ReceiveLunchAndKiss() {

        }

        public void ComeHome() {
            door.WaitInLineAtDoor(this);
        }
    }
    static class Alice extends Thread {
        private static Gnome[] gnomes;
        private static Minion[] minions;
        private Bob bob;
        private List<Critter> critterList=new ArrayList<>();

        public Alice(Gnome[] gnomes, Minion[] minions, Bob bob) {
            Alice.gnomes =gnomes;
            Alice.minions =minions;
            this.bob = bob;
            critterList.addAll(Arrays.asList(minions));
            critterList.addAll(Arrays.asList(gnomes));
        }

        public static void knockOnDoor(Semaphore door) {
            System.out.println("door was knocked on and Alice is opening it");
            door.release();
        }

        @Override
        public void run() {
            super.run();
            makeLunchAndKissEachOne();
            bob.interrupt();
            bob.ReceiveLunchAndKiss();
            bob.LeaveForWork();
        }

        private void makeLunchAndKissEachOne() {
            for (Critter critter : critterList) {
                //critter.ReceiveLunchAndKiss();
                //critter.LeaveForWork();
                critter.interrupt();
            }
        }
    }
    }



