package bgu.spl.mics.application.objects;

import java.util.ArrayList;

/**
 * Holds statistical information about the system's operation.
 * This class aggregates metrics such as the runtime of the system,
 * the number of objects detected and tracked, and the number of landmarks identified.
 */
public class StatisticalFolder {

    int systemRuntime;
    int numDetectedObjects;
    int numTrackedObjects;
    int numLandmarks;

    private static class StatisticalFolderHolder {
        private static StatisticalFolder instance = new StatisticalFolder();
    }

    private StatisticalFolder(){
        systemRuntime = 0;
        numDetectedObjects = 0;
        numTrackedObjects = 0;
        numLandmarks = 0;
    }

    public static StatisticalFolder getInstance(){
        return StatisticalFolder.StatisticalFolderHolder.instance;
    }


    public int getSystemRuntime() {
        return systemRuntime;
    }

    public int getNumDetectedObjects() {
        return numDetectedObjects;
    }

    public int getNumTrackedObjects() {
        return numTrackedObjects;
    }

    public int getNumLandmarks() {
        return numLandmarks;
    }

    public void setSystemRuntime(int systemRuntime) {
        this.systemRuntime = systemRuntime;
    }

    public void setNumDetectedObjects(int numDetectedObjects) {
        this.numDetectedObjects = numDetectedObjects;
    }

    public void setNumTrackedObjects(int numTrackedObjects) {
        this.numTrackedObjects = numTrackedObjects;
    }

    public void setNumLandmarks(int numLandmarks) {
        this.numLandmarks = numLandmarks;
    }



    // TODO: Its not written, but I think this one is a Singleton too.
    // TODO: Its a folder which holds info about the run



}
