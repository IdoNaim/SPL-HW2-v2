package bgu.spl.mics.application.objects;

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

    // TODO: Its not written, but I think this one is a Singleton too.
    // TODO: Its a folder which holds info about the run



}
