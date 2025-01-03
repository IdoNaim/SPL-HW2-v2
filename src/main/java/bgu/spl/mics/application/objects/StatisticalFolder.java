package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Holds statistical information about the system's operation.
 * This class aggregates metrics such as the runtime of the system,
 * the number of objects detected and tracked, and the number of landmarks identified.
 */
public class StatisticalFolder {

    AtomicInteger systemRuntime;
    AtomicInteger numDetectedObjects;
    AtomicInteger numTrackedObjects;
    AtomicInteger numLandmarks;

    private static class StatisticalFolderHolder {
        private static StatisticalFolder instance = new StatisticalFolder();
    }

    private StatisticalFolder(){
        systemRuntime = new AtomicInteger(0);
        numDetectedObjects = new AtomicInteger(0);
        numTrackedObjects = new AtomicInteger(0);
        numLandmarks = new AtomicInteger(0);
    }

    public static StatisticalFolder getInstance(){
        return StatisticalFolder.StatisticalFolderHolder.instance;
    }


    public int getSystemRuntime() {
        return this.systemRuntime.get();
    }

    public int getNumDetectedObjects() {
        return numDetectedObjects.get();
    }

    public int getNumTrackedObjects() {
        return numTrackedObjects.get();
    }

    public int getNumLandmarks() {
        return numLandmarks.get();
    }

    public void setSystemRuntime(int systemRuntime) {
        int oldVal;
        int newVal;
        do{
            oldVal = this.systemRuntime.get();
            newVal = systemRuntime;
        }while ((!this.systemRuntime.compareAndSet(oldVal,newVal)));
    }

    public void setNumDetectedObjects(int numDetectedObjects) {
        int oldVal;
        int newVal;
        do{
            oldVal = this.numDetectedObjects.get();
            newVal = numDetectedObjects;
        }while ((!this.numDetectedObjects.compareAndSet(oldVal,newVal)));
    }

    public void setNumTrackedObjects(int numTrackedObjects) {
        int oldVal;
        int newVal;
        do{
            oldVal = this.numTrackedObjects.get();
            newVal = numTrackedObjects;
        }while ((!this.numTrackedObjects.compareAndSet(oldVal,newVal)));
    }

    public void setNumLandmarks(int numLandmarks) {
        int oldVal;
        int newVal;
        do{
            oldVal = this.numLandmarks.get();
            newVal = numLandmarks;
        }while ((!this.numLandmarks.compareAndSet(oldVal,newVal)));
    }



    // TODO: Its not written, but I think this one is a Singleton too.
    // TODO: Its a folder which holds info about the run



}
