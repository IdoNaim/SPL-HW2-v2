package bgu.spl.mics.application.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * LiDarDataBase is a singleton class responsible for managing LiDAR data.
 * It provides access to cloud point data and other relevant information for tracked objects.
 */
public class LiDarDataBase {

    ArrayList<StampedCloudPoints> cloudPoints;
    ///idea
    //AtomicReference<ArrayList<StampedCloudPoints>> list;

    private static class LiDarDataBaseHolder {
        private static LiDarDataBase instance = new LiDarDataBase(new ArrayList<StampedCloudPoints>());
    }

    private LiDarDataBase(ArrayList<StampedCloudPoints> cloudPoints){
        this.cloudPoints = cloudPoints;
    }

    public void setCloudPoints(ArrayList<StampedCloudPoints> cloudPoints) {
        this.cloudPoints = cloudPoints;
    }

    public synchronized TrackedObject getTrackedObject(String id, int time) {
        TrackedObject result = null;
        for(StampedCloudPoints cloudPoint : cloudPoints){
            if(cloudPoint.id.equals(id) && cloudPoint.time == time){
                    cloudPoints.remove(cloudPoint);
                    ArrayList<CloudPoint> cloudPointsList = new ArrayList<>();
                    for (List<Double> point : cloudPoint.getCloudPoints()) {
                        cloudPointsList.add(new CloudPoint(point.get(0), point.get(1)));
                    }
                    result = new TrackedObject(id, time, "", cloudPointsList);
            }
        }
        return result;
    }
    ///idea
//    public void remove(StampedCloudPoints point){
//        list.updateAndGet(
//                list2->{ArrayList<StampedCloudPoints> newList = new ArrayList<>(list2);
//                        newList.remove(point);
//                        return newList;
//                }
//        );
//    }
    public synchronized boolean isEmpty(){
        return cloudPoints.isEmpty();
    }

    /**
     * Returns the singleton instance of LiDarDataBase.
     *
     *
     * @return The singleton instance of LiDarDataBase.
     */
    public synchronized static LiDarDataBase getInstance() {
        return LiDarDataBaseHolder.instance;
    }
}
