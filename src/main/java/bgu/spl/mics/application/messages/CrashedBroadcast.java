package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class CrashedBroadcast implements Broadcast {
    private String sender;
    private String crashReason;
    public CrashedBroadcast(String sender, String crashReason){
        this.sender = sender;
        this.crashReason = crashReason;
    }
    public String getSender(){
        return this.sender;
    }
    public String getCrashReason(){
        return this.crashReason;
    }
}
