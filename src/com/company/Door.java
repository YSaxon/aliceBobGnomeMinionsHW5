package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Door<Type extends Critter> {
    public Queue<Type> OrderOfWaitingAtDoor;


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

    public void WaitInLineAtDoor(Type T) {
        synchronized (this){
        OrderOfWaitingAtDoor.add(T);
            if (OrderOfWaitingAtDoor.size() != numToWaitFor) {
            //all other than the last one
            try {
                sout(T, " is waiting by the door");
                wait();
            } catch (InterruptedException e) {
                goThroughTheDoor(T);
    //            oneAtATimeThroughTheDoor.release();
                //OrderOfWaitingAtDoor.remove(T);//remove0
    //            if(OrderOfWaitingAtDoor.size()==1){
    //                notify();
    //            }
                OrderOfWaitingAtDoor.remove().interrupt();
            }} else {
                //todo knock on door

                sout(T, " is last to come");
                sout(T, " is waiting by the door");
                try {
                    weCanGo.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sout(T, " is knocking on door");
               // for (int i = OrderOfWaitingAtDoor.size() - 2; i >= 0; i--) {
               // for (int i = 0; i < OrderOfWaitingAtDoor.size(); i++) {

               // }
                   // sout(T, ": " + OrderOfWaitingAtDoor.get(i).name + " will be next");
            OrderOfWaitingAtDoor.remove().interrupt();
                    try {
                        wait();
                    } catch (InterruptedException e) {
                       // e.printStackTrace();
                    }
                    //   }


                //last guy
    //            try {
    //                wait();
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
                goThroughTheDoor(T);
                sout(T, " is going to release the lock");
                weHaveGone.release();


            }


        }
    }

    private void goThroughTheDoor(Type T) {
        sout(T, " is going though the door");
        try {
            Thread.sleep(20);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        sout(T, " has made it though the door");
    }

    private void sout(Type T, String s) {
        System.out.println(T.name + s);
    }
}
