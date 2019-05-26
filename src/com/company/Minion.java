package com.company;

public class Minion extends Critter {

    public Minion(String s, LineByDoor<Minion> lineByDoor) {
        super(s, lineByDoor, "deli");
    }

    @Override
    protected void PartingWordsToAlice() {

            System.out.println(name + " says to alice \"Thank you alice\"");

    }
}
