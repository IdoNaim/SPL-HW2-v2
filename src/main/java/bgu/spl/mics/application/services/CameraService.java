package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DetectedObjectsEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.messages.CrashedBroadcast;
import bgu.spl.mics.application.objects.STATUS;

import java.util.List;

/**
 * CameraService is responsible for processing data from the camera and
 * sending DetectObjectsEvents to LiDAR workers.
 * 
 * This service interacts with the Camera object to detect objects and updates
 * the system's StatisticalFolder upon sending its observations.
 */
public class CameraService extends MicroService {
    private Camera camera;
    /**
     * Constructor for CameraService.
     *
     * @param camera The Camera object that this service will use to detect objects.
     */
    public CameraService(Camera camera) {
        super(camera.getCamera_key());
        this.camera = camera;
    }
    /**
     * Initializes the CameraService.
     * Registers the service to handle TickBroadcasts and sets up callbacks for sending
     * DetectObjectsEvents.
     */
    @Override
    protected void initialize() {
        // TODO Implement this
        subscribeBroadcast(TickBroadcast.class,(TickBroadcast c) ->{
            DetectedObjectsEvent e = camera.handleTick(c.getCurrTime());
            if(e != null){
                if(!e.getDetectedObjects().isEmpty()) {
                    Future<Boolean> f = sendEvent(e);
                }
            }
            else{
                if(camera.getStatus() == STATUS.ERROR) {
                    sendBroadcast(new CrashedBroadcast(getName(), "Camera Disconnected"));
                    terminate();
                }
                else{
                    sendBroadcast(new TerminatedBroadcast(getName()));
                    terminate();
                }
/*
            List<DetectedObjectsEvent> eventList = camera.handleTick(c.getCurrTime());
            for(DetectedObjectsEvent event : eventList) {
                Future<Boolean> future = sendEvent(event);
                if(!future.get()){
                    // TODO: Crash
                }
                */

            }
        });
        subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast c)->{
            if(c.getSender().equals("Time")) {
                camera.Down();
                sendBroadcast(new TerminatedBroadcast(getName()));
                terminate();
            }
        });
        subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast c)->{
            camera.Down();
            sendBroadcast(new TerminatedBroadcast(getName()));
            terminate();
        });
        camera.Up();
    }
}
