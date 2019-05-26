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
        LineByDoor<Minion> minionLineByDoor = new LineByDoor<Minion>(numMinions,null,true);
        LineByDoor<Gnome> gnomeLineByDoor = new LineByDoor<Gnome>(numGnomes, minionLineByDoor.weHaveGone,true);
        minions = new Minion[numMinions];
        for (int i = 0; i < minions.length; i++) {
            minions[i]=new Minion("minion"+i, minionLineByDoor);
        }
        gnomes = new Gnome[numGnomes];
        for (int i = 0; i < gnomes.length; i++) {
            gnomes[i]=new Gnome("\tgnome"+i, gnomeLineByDoor);
        }
        for (Gnome gnome : gnomes) {
            gnome.start();
        }
        for (Minion minion : minions) {
            minion.start();
        }
        bob = new Bob(BobSleeping, gnomeLineByDoor.weHaveGone);
        bob.start();
        alice = new Alice(gnomes,minions,bob);
        alice.start();
    }

    static class Bob extends Critter {

        public Bob(Object bobSleeping, Semaphore bobCanComeIn) {
            super("\t\tbob", new LineByDoor<Bob>(1,bobCanComeIn,false), "accounting firm");
        }

        @Override
        public void run() {
            System.out.println("bob starting");
            super.run();
            try {
                Thread.sleep(99999);
            } catch (InterruptedException e) {
                System.out.println("Bob has woken up");
            }
            LeaveForWork();
        }

        @Override
        protected void PartingWordsToAlice() {
            //none
        }


        public void ComeHome() {
            lineByDoor.WaitInLineAtDoor(this);
        }
    }
    static class Alice extends Thread {
        private static Gnome[] gnomes;
        private static Minion[] minions;
        private Bob bob;
        private final Semaphore doorToWaitBy;
        private List<Critter> critterList=new ArrayList<>();

        public Alice(Gnome[] gnomes, Minion[] minions, Bob bob) {
            Alice.gnomes =gnomes;
            Alice.minions =minions;
            this.bob = bob;
            this.doorToWaitBy = LineByDoor.GlobalDoor;
            critterList.addAll(Arrays.asList(minions));
            critterList.addAll(Arrays.asList(gnomes));
        }

//        public void knockOnDoor(Semaphore door) {
//            System.out.println("door was knocked on and Alice is opening it");
//            door.release();
//        }


        @Override
        public void run() {
            super.run();
            makeLunchAndKissEachOne();
            //wakeBob();
            waitByDoor();
            //bob.interrupt();
        }

        private void waitByDoor() {
            for (int i = 0; i < 2; i++) {
            synchronized (doorToWaitBy){
                try {
                    System.out.println("alice is waiting by door for a knock");
                    doorToWaitBy.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("alice heard the knock and is opening the door");
                doorToWaitBy.release();
            }}
        }

        private void makeLunchAndKissEachOne() {
            for (Critter critter : critterList) {
                synchronized (critter){
                    System.out.println("alice making lunch for "+critter.name);
                    System.out.println("alice giving "+critter.name+" a kiss");
                    critter.notify();
                    //critter.ReceiveLunchAndKiss();
                }
            }
        }
    }
    }



