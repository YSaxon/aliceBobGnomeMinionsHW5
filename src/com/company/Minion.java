package com.company;

public class Minion extends Critter {

    public static GroupByGroup groupByGroup = new GroupByGroup(true, Main.numMinions, Gnome.groupByGroup::AllowGroupToGo);

    public Minion(String s, LineByDoor<Minion> lineByDoor) {
        super(s, lineByDoor, "deli", groupByGroup, "go play games");
    }

    @Override
    protected void PartingWordsToAlice() {

        System.out.println(name + " says to alice \"Thank you alice\"");

    }


}
