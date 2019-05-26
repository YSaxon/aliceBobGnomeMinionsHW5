package com.company;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class LineByDoor<CritterType extends Critter> {
    public static final Semaphore GlobalDoor = new Semaphore(0);
    public Queue<CritterType> OrderOfWaitingAtDoor;


    private volatile int numToWaitFor;
    private final Semaphore weCanGo;
    private final boolean knockAndWaitForAlice;
    public final Semaphore weHaveGone = new Semaphore(0);
//    private Semaphore oneAtATimeThroughTheDoor = new Semaphore(1,true);


    public LineByDoor(int numToWaitFor, Semaphore othersToWaitFor, boolean knockAndWaitForAlice) {
        this.numToWaitFor = numToWaitFor;
        this.weCanGo = Optional.ofNullable(othersToWaitFor).orElse(new Semaphore(1));
        this.knockAndWaitForAlice = knockAndWaitForAlice;
        OrderOfWaitingAtDoor=new LinkedList<>();
    }

    public void WaitInLineAtDoor(CritterType ThisCritter) {
        synchronized (this){
        OrderOfWaitingAtDoor.add(ThisCritter);
            if (OrderOfWaitingAtDoor.size() != numToWaitFor) {//if not last to come
            try {
                sout(ThisCritter, " is waiting by the door");
                    do {//wait until last one comes
                        wait();
                    } while ((OrderOfWaitingAtDoor.peek() != ThisCritter));//and wait again on wake if you aren't next in line

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                goThroughTheDoor(ThisCritter);
                OrderOfWaitingAtDoor.remove(ThisCritter);
                notifyAll();


            } else {
                //todo knock on lineByDoor
                sout(ThisCritter, " is last of its group to come");
                //if(weCanGo.availablePermits()<=0)
                sout(ThisCritter, " is waiting by the door");
                try {
                    weCanGo.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(knockAndWaitForAlice){
                sout(ThisCritter, " is knocking on the door");
                synchronized (GlobalDoor){
                GlobalDoor.notify();}
                try {
                    GlobalDoor.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}



                notifyAll();
                try {
                        while((OrderOfWaitingAtDoor.peek()!=ThisCritter)){
                            wait();}
                } catch (InterruptedException e) {
                       // e.printStackTrace();
                    }
                goThroughTheDoor(ThisCritter);
                OrderOfWaitingAtDoor.remove(ThisCritter);
                sout(ThisCritter, "is last one of its group through the door. Any groups waiting on them can now go");
                weHaveGone.release();
            }


        }
    }

    private void goThroughTheDoor(CritterType T) {
        synchronized (GlobalDoor){
        sout(T, " is going though the door");
        try {
            Thread.sleep(20);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        sout(T, " has made it though the door");}
    }

    private void sout(CritterType T, String s) {
        System.out.println(T.name + s);
    }
}
