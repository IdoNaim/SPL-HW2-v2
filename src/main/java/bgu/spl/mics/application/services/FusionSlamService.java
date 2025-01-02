package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.TrackedObject;

/**
 * FusionSlamService integrates data from multiple sensors to build and update
 * the robot's global map.
 * 
 * This service receives TrackedObjectsEvents from LiDAR workers and PoseEvents from the PoseService,
 * transforming and updating the map with new landmarks.
 */
public class FusionSlamService extends MicroService {
    private FusionSlam fusionSlam;
    /**
     * Constructor for FusionSlamService.
     *
     * @param fusionSlam The FusionSLAM object responsible for managing the global map.
     */
    public FusionSlamService(FusionSlam fusionSlam) {
        super("FusionSlam");
        this.fusionSlam = fusionSlam;
        // TODO Implement this
    }

    /**
     * Initializes the FusionSlamService.
     * Registers the service to handle TrackedObjectsEvents, PoseEvents, and TickBroadcasts,
     * and sets up callbacks for updating the global map.
     */
    @Override
    protected void initialize() {
        // TODO Implement this
        subscribeBroadcast(TickBroadcast.class,(TickBroadcast c) ->{
            fusionSlam.handleTick(c.getCurrTime());
        });
        subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast c)->{
            if(c.getSender().equals("Time")) {
                sendBroadcast(new TerminatedBroadcast(getName()));
                terminate();
            }
            else{
                if(fusionSlam.handleTerminated(c)){
                    sendBroadcast(new TerminatedBroadcast(getName()));
                    terminate();
                }
            }
        });
        subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast c)->{
            sendBroadcast(new TerminatedBroadcast(getName()));
            terminate();
        });
        subscribeEvent(TrackedObjectsEvent.class, (TrackedObjectsEvent e)->{
            fusionSlam.handleTrackedObjects(e);
        });
        subscribeEvent(PoseEvent.class, (PoseEvent e)->{
            fusionSlam.handlePoseEvent(e);
        });
    }
}
