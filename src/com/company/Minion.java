package com.company;

public class Minion extends Critter {
    public Minion(String s, Door<Minion> door) {
        name=s;
        this.door = door;
    }


    @Override
    public void ReceiveLunchAndKiss() {


    }

}
