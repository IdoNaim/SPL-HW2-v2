import static org.junit.jupiter.api.Assertions.*;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;
import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.messages.DetectedObjectsEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrackedObjectsEvent;
import bgu.spl.mics.application.objects.Camera;
import bgu.spl.mics.application.objects.FusionSlam;
import bgu.spl.mics.application.objects.LiDarWorkerTracker;
import bgu.spl.mics.application.objects.StampedDetectedObjects;
import bgu.spl.mics.application.services.CameraService;
import bgu.spl.mics.application.services.FusionSlamService;
import bgu.spl.mics.application.services.LiDarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestMessageBusImpl {
    MessageBusImpl messageBus;
    CameraService cameraService;
    LiDarService liDarService;
    FusionSlamService fusionSlamService;
//    @BeforeEach
//    void setUp(){
//        messageBus.clear();
//        cameraService = new CameraService( new Camera());
//        liDarService = new LiDarService(new LiDarWorkerTracker());
//        fusionSlamService = new FusionSlamService(FusionSlam.getInstance());
//        cameraService.register();
//        liDarService.register();
//        fusionSlamService.register();
//        messageBus.subscribeEvent(DetectedObjectsEvent.class, liDarService);
//        messageBus.subscribeBroadcast(TickBroadcast.class, cameraService);
//        messageBus.subscribeBroadcast(TickBroadcast.class, fusionSlamService);
//        messageBus.subscribeEvent(TrackedObjectsEvent.class, fusionSlamService);
////        messageBus.subscribeEvent(DetectedObjectsEvent.class, liDarService);
////        messageBus.subscribeBroadcast(TickBroadcast.class, cameraService);
////        messageBus.subscribeBroadcast(TickBroadcast.class, fusionSlamService);
////        messageBus.subscribeEvent(TrackedObjectsEvent.class, fusionSlamService);
//    }
    @BeforeEach
    void setUp(){
        messageBus = MessageBusImpl.getInstance();
        messageBus.clear();
        messageBus = MessageBusImpl.getInstance();
        cameraService = new CameraService(new Camera());
        liDarService = new LiDarService(new LiDarWorkerTracker());
        fusionSlamService = new FusionSlamService(FusionSlam.getInstance());
        cameraService.register();
        liDarService.register();
        fusionSlamService.register();
        messageBus.subscribeEvent(DetectedObjectsEvent.class, liDarService);
        messageBus.subscribeBroadcast(TickBroadcast.class, cameraService);
        messageBus.subscribeBroadcast(TickBroadcast.class, fusionSlamService);
        messageBus.subscribeEvent(TrackedObjectsEvent.class, fusionSlamService);
    }
    @Test
    void subscribingTest(){
        assertTrue(messageBus.getEventsSubscribers().get(DetectedObjectsEvent.class).contains(liDarService));
        assertTrue(messageBus.getEventsSubscribers().get(TrackedObjectsEvent.class).contains(fusionSlamService));
        assertTrue(messageBus.getBroadcastsSubscribers().get(TickBroadcast.class).contains(cameraService));
        assertTrue(messageBus.getBroadcastsSubscribers().get(TickBroadcast.class).contains(fusionSlamService));
    }
    @Test
    void sendBroacastTest(){
        Broadcast b = new TickBroadcast("test", 1);
        messageBus.sendBroadcast(b);
        assertTrue(messageBus.getServices().get(cameraService).contains(b));
        assertTrue(messageBus.getServices().get(fusionSlamService).contains(b));
    }
    @Test
    void sendEventTest(){
        Event<Boolean> e1 = new DetectedObjectsEvent("test", new StampedDetectedObjects(1),1);
        Event<Boolean> e2 = new TrackedObjectsEvent("test", new ArrayList<>());
        messageBus.sendEvent(e1);
        messageBus.sendEvent(e2);
        assertTrue(messageBus.getServices().get(liDarService).contains(e1));
        assertTrue(messageBus.getServices().get(fusionSlamService).contains(e2));
    }
}
