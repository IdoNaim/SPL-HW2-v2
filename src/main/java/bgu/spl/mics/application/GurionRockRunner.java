package bgu.spl.mics.application;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * The main entry point for the GurionRock Pro Max Ultra Over 9000 simulation.
 * <p>
 * This class initializes the system and starts the simulation by setting up
 * services, objects, and configurations.
 * </p>
 */
public class GurionRockRunner {

    /**
     * The main method of the simulation.
     * This method sets up the necessary components, parses configuration files,
     * initializes services, and starts the simulation.
     *
     * @param args Command-line arguments. The first argument is expected to be the path to the configuration file.
     */

    public static void main(String[] args) {
        System.out.println("Hello World!");
        // TODO: Parse configuration file.
        // TODO: Initialize system components and services.
        // TODO: Start the simulation.

        Gson gson = new Gson();
        JsonObject config;

        try (FileReader configReader = new FileReader(args[0])) {
            config = gson.fromJson(configReader, JsonObject.class);
            List<Camera> cameraList = GurionRockRunner.CamerasConfiguration(config,gson);
            List<LiDarWorkerTracker> lidarList = GurionRockRunner.LidarConfiguration(config,gson);
            ArrayList<Pose> poseList = GurionRockRunner.PoseConfiguration(config,gson);
            int TickTime = config.get("TickTime").getAsInt();
            int Duration = config.get("Duration").getAsInt();
            GPSIMU gps = new GPSIMU(0, poseList);
            ArrayList<MicroService> services = new ArrayList<>();
            ArrayList<CameraService> cameraServices = new ArrayList<>();
            for(Camera camera: cameraList){
                CameraService cameraService = new CameraService(camera);
                services.add(cameraService);
            }
            ArrayList<LiDarService> liDarServices = new ArrayList<>();
            for(LiDarWorkerTracker lidar : lidarList){
                LiDarService liDarService = new LiDarService(lidar);
                services.add(liDarService);
            }
            PoseService poseService = new PoseService(gps);
            FusionSlamService fusionSlamService = new FusionSlamService(FusionSlam.getInstance());
            services.add(poseService);
            FusionSlam.getInstance().setSensors(services.size());
            services.add(fusionSlamService);
            TimeService timeService = new TimeService(TickTime,Duration);
            for(MicroService service: services){
                Thread thread = new Thread(service);
                thread.start();
            }
            Thread timeThread = new Thread(timeService);
            timeThread.start();
            //TODO:create output file

        }
        catch (Exception e) {

        }



    }

    private static List<Camera> CamerasConfiguration(JsonObject config, Gson gson){
        List<Camera> cameraList = new ArrayList<>();
        String cameraDataPath = config.getAsJsonObject("Cameras").get("camera_datas_path").getAsString();
        try (FileReader cameraReader = new FileReader(cameraDataPath)){
            JsonArray camerasArray = config.getAsJsonObject("Cameras").getAsJsonArray("CamerasConfigurations");
            Type cameraListType = new TypeToken<List<Camera>>() {}.getType();
            cameraList = gson.fromJson(camerasArray, cameraListType);

            JsonObject cameraData = gson.fromJson(cameraReader, JsonObject.class);
            Type StampedDetectedObjectsListType = new TypeToken<ArrayList<StampedDetectedObjects>>() {}.getType();
            for(Camera camera : cameraList) {
                JsonArray temp = cameraData.getAsJsonObject(camera.getCamera_key()).getAsJsonArray();
                camera.setDetectedObjectsList(gson.fromJson(temp, StampedDetectedObjectsListType));
            }
        }
        catch (Exception e){

        }

        return cameraList;
    }

    private static List<LiDarWorkerTracker> LidarConfiguration(JsonObject config, Gson gson) {
        List<LiDarWorkerTracker> lidarList = new ArrayList<>();
        String lidarDataPath = config.getAsJsonObject("LidarWorkers").get("lidars_data_path").getAsString();

        try (FileReader lidarReader = new FileReader(lidarDataPath)) {
            JsonArray lidarsArray = config.getAsJsonObject("LidarWorkers").getAsJsonArray("LidarConfigurations");
            Type lidarListType = new TypeToken<List<LiDarWorkerTracker>>() {}.getType();
            lidarList = gson.fromJson(lidarsArray, lidarListType);

            Type StampedCloudPointsListType = new TypeToken<ArrayList<StampedCloudPoints>>() {}.getType();

            JsonArray temp = gson.fromJson(lidarReader,JsonArray.class);
            ArrayList<StampedCloudPoints> cloudPoints = gson.fromJson(temp, StampedCloudPointsListType);
            LiDarDataBase.getInstance().setCloudPoints(cloudPoints);
        }
        catch (Exception e) {

        }

        return lidarList;
    }

    private static ArrayList<Pose> PoseConfiguration(JsonObject config, Gson gson) {
        ArrayList<Pose> poseList = new ArrayList<>();
        String poseDataPath = config.get("poseJsonFile").getAsString();

        try (FileReader poseReader = new FileReader(poseDataPath)) {
            JsonArray poseArray = gson.fromJson(poseReader,JsonArray.class);
            Type poseListType = new TypeToken<List<Pose>>() {}.getType();
            poseList = gson.fromJson(poseArray, poseListType);
        }
        catch (Exception e) {

        }

        return poseList;
    }
}
