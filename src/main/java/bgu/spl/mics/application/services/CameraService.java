package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DetectObjectsEvent;
import bgu.spl.mics.application.messages.TerminatedBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.messages.CrashedBroadcast;

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
        super("Camera");
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
            DetectObjectsEvent e = camera.handleTick(c.getCurrTime());
            if(e != null) {
                Future<Boolean> f = sendEvent(e);
            }
            else{
                sendBroadcast(new CrashedBroadcast(getName(),"Camera Disconnected"));
            }
        });
        subscribeBroadcast(TerminatedBroadcast.class, (TerminatedBroadcast c)->{
            camera.Down();
            terminate();
        });
        subscribeBroadcast(CrashedBroadcast.class, (CrashedBroadcast c)->{
            camera.Down();
            terminate();
        });
        camera.Up();
    }
}
