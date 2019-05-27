package com.company;

public class GroupByGroup {

    boolean groupCanGo;
    volatile int HowManyHaveGoneThrough;
    final int GroupSize;
    Runnable WhatToDoWhenGroupHasGoneThrough;

    public GroupByGroup(boolean groupCanGoFromStart, int groupSize, Runnable whatToDoWhenGroupHasGoneThrough) {
        this.groupCanGo = groupCanGoFromStart;
        HowManyHaveGoneThrough = 0;
        GroupSize = groupSize;
        WhatToDoWhenGroupHasGoneThrough = whatToDoWhenGroupHasGoneThrough;
    }

    public void AllowGroupToGo() {
        synchronized (this) {
            groupCanGo=true;
            notifyAll();
        }
    }

    public void GoSingleFileWhenLegal(Runnable andDoStuff){
        synchronized (this){
            if (!groupCanGo) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
            andDoStuff.run();
            HowManyHaveGoneThrough++;
            if(HowManyHaveGoneThrough==GroupSize){
                WhatToDoWhenGroupHasGoneThrough.run();//maybe in a seperate thread?
            }
        }
    }

}
