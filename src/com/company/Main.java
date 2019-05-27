package com.company;

import java.util.*;
import java.util.concurrent.Semaphore;

import static com.company.Main.Coordination.*;
import static com.company.Main.Coordination.WaitForBobForDinner;
import static com.company.Main.Coordination.waitingForDinnerToBeReady;

public class Main {

    public static final int numMinions = 10;
    public static final int numGnomes = 7;
    private static Gnome[] gnomes;
    private static Minion[] minions;
    public static Alice alice;
    public static Object BobSleeping = new Object();
    public static Bob bob;


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
            super("\t\tbob", new LineByDoor<Bob>(1,bobCanComeIn,false), "accounting firm", new GroupByGroup(true,1,()-> {}), "do whatever he wants");
        }

        @Override
        public void run() {
            //System.out.println("bob starting");
            try {
                System.out.println("bob is sleeping");
                sleep(9999999);
            } catch (InterruptedException e) {
                System.out.println("Bob has woken up");
            }
            super.run();
            System.out.println("bob after super.run");
        }

        @Override
        protected void PartingWordsToAlice() {
            //none
        }

        @Override
         public void BeforeBed() {
            GoReadABook(this);
        }

        public void ComeHome() {
            lineByDoor.WaitInLineAtDoor(this);
        }
    }

    static class Coordination{
        public static volatile int GnomesLeft;
        public static volatile int MinionsLeft;
        public static Semaphore MinionsHaveGone= new Semaphore(0);
        public static final Object waitingForBob = new Object();
        public static final Object waitingForDinnerToBeReady = new Object();
        public static boolean isDinnerReady=false;
        public static Semaphore DinnerTable=new Semaphore(5);
        public static final Queue<NamedThread> Couch = new ArrayDeque<>(2);

        public static void WaitForBobForDinner(Thread T) {
            synchronized (waitingForBob){
                if(T!= Main.bob){
                    try {
                        waitingForBob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    waitingForBob.notifyAll();
                }}}


            public static void WaitForDinnerToBeReady(Thread T) {
                synchronized (waitingForDinnerToBeReady){
                        try {
                            if(!isDinnerReady)
                            waitingForDinnerToBeReady.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                public static void GoReadABook(NamedThread T){
                    synchronized (Couch) {
                        if (Couch.isEmpty()) {
                            System.out.println("Lights are turning on for " + T.name + " to read");
                        }
                        Couch.add(T);
                        System.out.println(T.name+ " is reading on the couch");
                    }
                    try {
                        Thread.sleep((long) (Math.random()*500));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (Couch) {
                        Couch.remove(T);
                        System.out.println(T.name+ " is done reading");
                        if (Couch.isEmpty()) {
                            System.out.println("Lights are turning off as " + T.name + " leaves");
                        }

                    }
                }

        public static void GetSomeDinner(NamedThread T) {
            try {
                DinnerTable.acquire();
                System.out.println(T.name+" sitting down and eating dinner");
                Thread.sleep((long) (Math.random()*500));
                System.out.println(T.name+" done eating");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DinnerTable.release();
        }

        public static void GoToBed(NamedThread T) {
            System.out.println(T.name+" going to bed");
            try {
                Thread.sleep((long) (Math.random()+40));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(T.name+" falling asleep");
        }
    }


    static class Alice extends NamedThread {
        private static Gnome[] gnomes;
        private static Minion[] minions;
        private Bob bob;
        private final Semaphore doorToWaitBy;
        private List<Critter> critterList=new ArrayList<>();


        public Alice(Gnome[] gnomes, Minion[] minions, Bob bob) {
            name="alice";
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
            wakeBobWhenMinionsAreGone();
            waitByDoor();
            WaitForBobForDinner(this);
            prepareDinner();
            GetSomeDinner(this);
            GoReadABook(this);
        }

        private void prepareDinner() {
            System.out.println("alice preparing dinner");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (waitingForDinnerToBeReady){
                isDinnerReady=true;
                waitingForDinnerToBeReady.notifyAll();
            }
        }

        private void wakeBobWhenMinionsAreGone() {

            try {
                MinionsHaveGone.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("alice is waking bob up");
            bob.interrupt();
//            try {
                //sleep(10);//cheap workaround
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            makeLunchKissAndNotify(bob);

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
                makeLunchKissAndNotify(critter);
            }
        }

        private void makeLunchKissAndNotify(Critter critter) {
            while(!critter.readyForAliceInTheMorning);
            synchronized (critter){
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                System.out.println("alice making lunch for "+ critter.name);
                System.out.println("alice giving "+ critter.name+" a kiss");
                critter.notify();
            }
        }

    }
    }



