package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.STATUS;

/**
 * LiDarService is responsible for processing data from the LiDAR sensor and
 * sending TrackedObjectsEvents to the FusionSLAM service.
 * 
 * This service interacts with the LiDarWorkerTracker object to retrieve and process
 * cloud point data and updates the system's StatisticalFolder upon sending its
 * observations.
 */
public class LiDarService extends MicroService {
    private LiDarWorkerTracker liDarWorkerTracker;
    /**
     * Constructor for LiDarService.
     *
     * @param LiDarWorkerTracker A LiDAR Tracker worker object that this service will use to process data.
     */
    public LiDarService(LiDarWorkerTracker LiDarWorkerTracker) {
        super("Lidar");
        this.liDarWorkerTracker = LiDarWorkerTracker;
        // TODO Implement this
    }

    /**
     * Initializes the LiDarService.
     * Registers the service to handle DetectObjectsEvents and TickBroadcasts,
     * and sets up the necessary callbacks for processing data.
     */
    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class,(TickBroadcast c) ->{
            TrackedObjectsEvent t = liDarWorkerTracker.handleTick(c.getCurrTime());
            if(t != null){
                if(!t.getTrackedObjects().isEmpty()){
                    sendEvent(t);
                }
            }
            else{
                if(liDarWorkerTracker.getStatus() == STATUS.ERROR){
                    sendBroadcast(new CrashedBroadcast(getName(), "LiDar disconnected"));
                    terminate();
                }
                else{
                    sendBroadcast(new TerminatedBroadcast(getName()));
                    terminate();
                }
            }
        });
        subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast c)->{
            if(c.getSender().equals("Time")) {
                liDarWorkerTracker.Down();
                sendBroadcast(new TerminatedBroadcast(getName()));
                terminate();
            }
        });
        subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast c)->{
            liDarWorkerTracker.Down();
            sendBroadcast(new TerminatedBroadcast(getName()));
            terminate();
        });
        subscribeEvent(DetectedObjectsEvent.class, (DetectedObjectsEvent e)->{
            TrackedObjectsEvent t = liDarWorkerTracker.handleDetectedObjects(e);
            if(t != null){
                if(!t.getTrackedObjects().isEmpty()){
                    Future<Boolean> f = sendEvent(t);
                    complete(e, true);
                }
            }
            else{
                if(liDarWorkerTracker.getStatus() == STATUS.ERROR){
                    complete(e, false);
                    sendBroadcast(new CrashedBroadcast(getName(), "LiDar disconnected"));
                    terminate();
                }
            }
        });
        liDarWorkerTracker.Up();
    }
}
