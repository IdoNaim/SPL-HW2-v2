package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {
    private String sender;
    private int currTime;

    public TickBroadcast(String sender, int time){
        this.sender = sender;
        this.currTime = time;
    }
}
