package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Door<CritterType extends Critter> {
    public Queue<CritterType> OrderOfWaitingAtDoor;


    private volatile int numToWaitFor;
    private final Semaphore weCanGo;
    public final Semaphore weHaveGone = new Semaphore(0);
//    private Semaphore oneAtATimeThroughTheDoor = new Semaphore(1,true);

    public Door(int numToWaitFor) {
        this.numToWaitFor = numToWaitFor;
        weCanGo =new Semaphore(1);
        OrderOfWaitingAtDoor=new LinkedList<>();
    }

    public Door(int numToWaitFor, Semaphore othersToWaitFor) {
        this.numToWaitFor = numToWaitFor;
        this.weCanGo = othersToWaitFor;
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
                //todo knock on door
                sout(ThisCritter, " is last to come");
                sout(ThisCritter, " is waiting by the door");
                try {
                    weCanGo.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sout(ThisCritter, " is knocking on door");
                notifyAll();
                try {
                        while((OrderOfWaitingAtDoor.peek()!=ThisCritter)){
                            wait();}
                } catch (InterruptedException e) {
                       // e.printStackTrace();
                    }
                goThroughTheDoor(ThisCritter);
                OrderOfWaitingAtDoor.remove(ThisCritter);
                sout(ThisCritter, " is going to release the lock");
                weHaveGone.release();
            }


        }
    }

    private void goThroughTheDoor(CritterType T) {
        sout(T, " is going though the door");
        try {
            Thread.sleep(20);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        sout(T, " has made it though the door");
    }

    private void sout(CritterType T, String s) {
        System.out.println(T.name + s);
    }
}
