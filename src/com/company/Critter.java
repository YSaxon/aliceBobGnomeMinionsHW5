package com.company;

public abstract class Critter extends Thread{


    @Override
    public void run() {
        super.run();
        try {
            sleep(999999);
        } catch (InterruptedException e) {
            ReceiveLunchAndKiss();
            LeaveForWork();
        }

    }

    public String name;
    public void ReceiveLunchAndKiss(){
        System.out.println(name + " getting a lunch and a kiss");
    }

    public void LeaveForWork() {
        System.out.println(name+" going to work");
        try {
            this.sleep((long) (Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name+" done working");
        ComeHome();
    }

    protected abstract void ComeHome();

}
