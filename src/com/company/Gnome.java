package com.company;

public class Gnome extends Critter implements Runnable {


    private Door<Gnome> gnomeDoor;

    public Gnome(String s, Door gnomeDoor) {
        this.gnomeDoor = gnomeDoor;
        name=s;
    }

    @Override
    public void ReceiveLunchAndKiss() {

    }
}
