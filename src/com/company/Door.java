package com.company;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Door<Type extends Critter> {
    public ArrayList<Type> OrderOfWaitingAtDoor;


    private volatile int numToWaitFor;
    private Semaphore weCanGo;
    public Semaphore weHaveGone = new Semaphore(-1);
//    private Semaphore oneAtATimeThroughTheDoor = new Semaphore(1,true);

    public Door(int numToWaitFor) {
        this.numToWaitFor = numToWaitFor;
        weCanGo =new Semaphore(1);
        OrderOfWaitingAtDoor=new ArrayList<>();
    }

    public Door(int numToWaitFor, Semaphore othersToWaitFor) {
        this.numToWaitFor = numToWaitFor;
        this.weCanGo = othersToWaitFor;
        OrderOfWaitingAtDoor=new ArrayList<>();
    }

    public void WaitInLineAtDoor(Type T) {
        synchronized (weCanGo){
        OrderOfWaitingAtDoor.add(T);
        if(OrderOfWaitingAtDoor.size()==numToWaitFor){
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
        OrderOfWaitingAtDoor.get(0).interrupt();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
        else{
        //all other than the last one
        try {
            sout(T, " is waiting by the door");
            wait();
        } catch (InterruptedException e) {
            goThroughTheDoor(T);
//            oneAtATimeThroughTheDoor.release();
            OrderOfWaitingAtDoor.remove(T);//remove0
//            if(OrderOfWaitingAtDoor.size()==1){
//                notify();
//            }
            OrderOfWaitingAtDoor.get(0).interrupt();
        }}


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
