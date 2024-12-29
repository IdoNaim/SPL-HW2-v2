package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;

/**
 * TimeService acts as the global timer for the system, broadcasting TickBroadcast messages
 * at regular intervals and controlling the simulation's duration.
 */
public class TimeService extends MicroService {
    private int speed;
    private int duration;
    private int ticks;
    /**
     * Constructor for TimeService.
     *
     * @param TickTime  The duration of each tick in milliseconds.
     * @param Duration  The total number of ticks before the service terminates.
     */
    public TimeService(int TickTime, int Duration) {
        super("Time");
        this.speed = TickTime;
        this.duration = Duration;
        this.ticks=0;
        // TODO Implement this
    }

    /**
     * Initializes the TimeService.
     * Starts broadcasting TickBroadcast messages and terminates after the specified duration.
     */
    @Override
    protected void initialize() {
        // TODO Implement this
        try {
            while (getTicks() <= getDuration()) {
                TickBroadcast b = new TickBroadcast(getName(), getTicks());
                sendBroadcast(b);
                incTick();
                Thread.sleep(getSpeed());
            }
            terminate();
            Thread.currentThread().interrupt();
        }catch (InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }


    }
    private void incTick(){
        this.ticks++;
    }
    private int getTicks(){
        return ticks;
    }
    private int getDuration(){
        return duration;
    }
    private int getSpeed(){
        return speed;
    }
}
