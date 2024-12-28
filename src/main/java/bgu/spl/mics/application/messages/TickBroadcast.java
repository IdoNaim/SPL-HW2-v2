package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    private int currTime;

    public TickBroadcast(int time){
        this.currTime = time;
    }
}
