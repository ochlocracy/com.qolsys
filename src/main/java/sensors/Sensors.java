package sensors;

import utils.ConfigProps;
import utils.Setup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Sensors {

    public Runtime rt = Runtime.getRuntime();

    static int number_of_sensors = 0;

    public static List<Integer> door_window_zones;
    public static List<Integer> motion_zones;
    public static List<Integer> keyfob_zones;
    public static List<Integer> smoke_detector_zones;
    public static List<Integer> co_detector_zones;
    public static List<Integer> water_flood_zones;
    public static List<Object> door_window_DLIDs;
    public static List<Integer> glassbreak_zones;
    public static List<Integer> tilt_zones;
    public static List<Integer> shock_IQ_zones;
    public static List<Integer> shock_other_zones;
    public static List<Integer> heat_zones;
    public static List<Integer> freeze_zones;
    public static List<Integer> keypad_zones;
    public static List<Integer> pendant_zones;
    public static List<Integer> doorbell_zones;
    public static List<Integer>  occupancy_zones;

    public  static List<SensorObject> SensorObject_ArrayList;

    HashMap<String, Integer> primary_sensor_int_map = new HashMap<String, Integer>();
    HashMap<String, Integer> transmitter_sensor_int_map = new HashMap<String, Integer>();

    public Sensors() throws Exception {
        inicialize_primary_sensor_int_map();
        initialize_transmitter_sensor_int_map();
        ConfigProps.init();
    }

    public void read_sensors_from_csv() throws IOException {
        SensorObject_ArrayList = new ArrayList();
        door_window_zones = new ArrayList();
        motion_zones = new ArrayList();
        keyfob_zones = new ArrayList();
        smoke_detector_zones = new ArrayList();
        co_detector_zones = new ArrayList();
        water_flood_zones = new ArrayList();
        glassbreak_zones = new ArrayList();
        tilt_zones = new ArrayList();
        shock_IQ_zones = new ArrayList();
        shock_other_zones = new ArrayList();
        heat_zones  = new ArrayList();
        freeze_zones = new ArrayList();
        keypad_zones = new ArrayList();
        pendant_zones  = new ArrayList();
        doorbell_zones = new ArrayList();
        occupancy_zones = new ArrayList();
        door_window_DLIDs = new ArrayList();

        SensorObject sensor = new SensorObject();
        SensorObject_ArrayList.add(sensor);
        sensor = new SensorObject();
        SensorObject_ArrayList.add(sensor);
        String heat = "/home/qolsys/Desktop/heat.csv";
        String main = "scr/test.csv";
        BufferedReader reader = new BufferedReader(new FileReader(main));
        String line = null;
        Scanner scanner = null;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            int zone = 0;
            String dlid = "";
            String sensor_type = "";
            int sensor_group = 0;
            int protocol = 0;
            //scanner.next(); // this should skip the first line for headers
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0) zone = Integer.parseInt(data);
                else if (index == 1) dlid = data;
                else if (index == 2) {
                    sensor_type = data;
                    //     System.out.println(sensor_type);
                    if (sensor_type.equals("door_window")) {
                        //     System.out.println("Add to an array " + zone);
                        door_window_zones.add(zone);
                    }
                    else if(sensor_type.equals("motion")) {
                        //      System.out.println("Add to an array " + zone);
                        motion_zones.add(zone);
                    }
                    else if (sensor_type.equals("keyfob")) {
                        //      System.out.println("Add to an array " + zone);
                        keyfob_zones.add(zone);
                    }
                    else if (sensor_type.equals("keypad")) {
                        //      System.out.println("Add to an array " + zone);
                        keypad_zones.add(zone);
                    }
                    else if (sensor_type.equals("auxiliary_pendant")) {
                        //      System.out.println("Add to an array " + zone);
                        pendant_zones.add(zone);
                    }
                    else if (sensor_type.equals("smoke_detector")) {
                        //      System.out.println("Add to an array " + zone);
                        smoke_detector_zones.add(zone);
                    }
                    else if (sensor_type.equals("co_detector")) {
                        //     System.out.println("Add to an array " + zone);
                        co_detector_zones.add(zone);
                    }
                    else if (sensor_type.equals("water_flood")) {
                        //     System.out.println("Add to an array " + zone);
                        water_flood_zones.add(zone);
                    }
                    else if (sensor_type.equals("glassbreak")) {
                        //     System.out.println("Add to an array " + zone);
                        glassbreak_zones.add(zone);
                    }
                    else if (sensor_type.equals("tilt")) {
                        //     System.out.println("Add to an array " + zone);
                        tilt_zones.add(zone);
                    }
                    else if (sensor_type.equals("shock_IQ")) {
                        //     System.out.println("Add to an array " + zone);
                        shock_IQ_zones.add(zone);
                    }
                    else if (sensor_type.equals("shock_other")) {
                        //     System.out.println("Add to an array " + zone);
                        shock_other_zones.add(zone);
                    }
                    else if (sensor_type.equals("heat")) {
                        //     System.out.println("Add to an array " + zone);
                        heat_zones.add(zone);
                    }
                    else if (sensor_type.equals("freeze")) {
                        //     System.out.println("Add to an array " + zone);
                        freeze_zones.add(zone);
                    }
                    else if (sensor_type.equals("doorbell")) {
                        //     System.out.println("Add to an array " + zone);
                        doorbell_zones.add(zone);
                    }
                    else if (sensor_type.equals("occupancy")) {
                        //     System.out.println("Add to an array " + zone);
                        occupancy_zones.add(zone);
                    }
                }
                // add the to DW list
                else if (index == 3) sensor_group = Integer.parseInt(data);
                else if (index == 4) protocol = Integer.parseInt(data);
                //  else
                //      System.out.println("invalid data::" + data);
                index++;
            }
            index = 0;
            sensor = new SensorObject(zone, dlid, sensor_type, sensor_group, 0, protocol);
            SensorObject_ArrayList.add(sensor);
            number_of_sensors++;
        }
        reader.close();
        //  System.out.println(SensorObject_ArrayList);
    }

    public static void debug_print_sensors() {
        for (int w = 0; w < number_of_sensors; w++) {
            SensorObject sensor = SensorObject_ArrayList.get(w);
            sensor.printSensorDetails();}
    }

    public void inicialize_primary_sensor_int_map() {
        primary_sensor_int_map.put("door_window", 1);
        primary_sensor_int_map.put("motion", 2);
        primary_sensor_int_map.put("smoke_detector", 5);
        primary_sensor_int_map.put("co_detector", 6);
        primary_sensor_int_map.put("water_multi", 15);
        primary_sensor_int_map.put("tilt", 16);
        primary_sensor_int_map.put("freeze", 17);
        primary_sensor_int_map.put("glassbreak", 19);
        primary_sensor_int_map.put("auxiliary_pendant", 21);
        primary_sensor_int_map.put("water_flood", 22);
        primary_sensor_int_map.put("keyfob", 102);
        primary_sensor_int_map.put("keypad", 104);
        primary_sensor_int_map.put("shock_IQ", 107);
        primary_sensor_int_map.put("shock_other", 113);
        primary_sensor_int_map.put("doorbell", 109);
        primary_sensor_int_map.put("occupancy", 114);
        primary_sensor_int_map.put("heat", 111);
    }

    public void initialize_transmitter_sensor_int_map() {
        transmitter_sensor_int_map.put("door_window", 0);
        transmitter_sensor_int_map.put("motion", 1);
        transmitter_sensor_int_map.put("smoke_detector", 6);
        transmitter_sensor_int_map.put("co_detector", 7);
        transmitter_sensor_int_map.put("water_multi", 12);
        transmitter_sensor_int_map.put("tilt", 15);
        transmitter_sensor_int_map.put("freeze", 14);
        transmitter_sensor_int_map.put("glassbreak", 2);
        transmitter_sensor_int_map.put("auxiliary_pendant", 5);
        transmitter_sensor_int_map.put("water_flood", 13);
        transmitter_sensor_int_map.put("keyfob", 3);
        transmitter_sensor_int_map.put("keypad", 4);
        transmitter_sensor_int_map.put("shock_IQ", 22);
        transmitter_sensor_int_map.put("shock_other", 23);
        transmitter_sensor_int_map.put("doorbell", 17);
        transmitter_sensor_int_map.put("occupancy", 24);
        transmitter_sensor_int_map.put("heat", 10);
    }

    public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }

    public void add_transmitter_call(String DLID, int sensor_type, int supervisory_time) throws IOException {
        String add_transmitter = "shell service call srftransmitservice 2 s16 " + DLID + " i32 0 i32 " + sensor_type + " i32 0 i32 " + supervisory_time;
        rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + add_transmitter);
    }

    public void primaryCall(String DLID, String State) throws IOException {
        String primary_send =" shell rfinjector 02 "+DLID+" "+State;
        rt.exec(ConfigProps.adbPath + primary_send);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
        System.out.println(primary_send); }

    // add all sensors from CSV file
    public void addAllSensors() throws IOException, InterruptedException {
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(i);

            int zone = i;
            int newGroup = temp_sensor.getSensorGroup();
            String newDLID = temp_sensor.getDLID();
            int newDLID_dec = Integer.parseInt(newDLID, 16);
            String sensor_type = temp_sensor.getSensorType();
            int supervisory_time = -1;
//            System.out.println(temp_sensor.getDLID());
//            inicialize_primary_sensor_int_map();
//            initialize_transmitter_sensor_int_map();
            String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + newGroup + " i32 " + newDLID_dec + " i32 " + primary_sensor_int_map.get(sensor_type);
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + " " + add_primary);
            System.out.println(add_primary);
            TimeUnit.SECONDS.sleep(1);
            String add_transmitter = " shell service call srftransmitservice 2 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 " + supervisory_time;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + add_transmitter);
            System.out.println(add_transmitter);
            //   TimeUnit.SECONDS.sleep(1);
        }
    }
    public void addAllSensors1(String UDID_) throws IOException, InterruptedException {
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(i);

            int zone = i;
            int newGroup = temp_sensor.getSensorGroup();
            String newDLID = temp_sensor.getDLID();
            int newDLID_dec = Integer.parseInt(newDLID, 16);
            String sensor_type = temp_sensor.getSensorType();
            int supervisory_time = -1;
            //       System.out.println(temp_sensor.getDLID());
//            inicialize_primary_sensor_int_map();
//            initialize_transmitter_sensor_int_map();
            String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + newGroup + " i32 " + newDLID_dec + " i32 " + primary_sensor_int_map.get(sensor_type);
            rt.exec(ConfigProps.adbPath + " -s " + UDID_ + add_primary);
            System.out.println(add_primary);
            TimeUnit.SECONDS.sleep(1);
            String add_transmitter = " shell service call srftransmitservice 2 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 " + supervisory_time;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + add_transmitter);
            System.out.println(add_transmitter);
            //   TimeUnit.SECONDS.sleep(1);
        }
    }
    public void getDoor_WindowSensorsDLID() {
        for (int i = 0; i < door_window_zones.size(); i++) {
            SensorObject sensor = SensorObject_ArrayList.get(i);
            String new_DLIDs = sensor.getDLID();
            System.out.println(new_DLIDs);
        }
    }
    // send packet to OPEN(x=1) or CLOSE(x=0) sensors
    public void sendPacket_DW_All(int x) throws IOException, InterruptedException {
        for (int i = 0; i < door_window_zones.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(door_window_zones.get(i));
            String newDLID = temp_sensor.getDLID();
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = " shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 0 i32 " + x;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + send_packet);
            System.out.println(send_packet);
        }
    }

    // send packet to DISARM(x=0), ARM_AWAY(x=1) or ARM_STAY(x=2)
    public void sendPacket_KeyFob_All(int z) throws IOException, InterruptedException {
        for (int i = 0; i < keyfob_zones.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(keyfob_zones.get(i));
            String newDLID = temp_sensor.getDLID();
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = " shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 0 i32 " + z;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + send_packet);
            System.out.println(send_packet);
        }
    }
    public void sendPacket_allSensors_selectedGroup(List<Integer> input_sens_zones, int input_group_number, int z) throws IOException, InterruptedException {
        List<Integer> foundSensorsInGroup;
        foundSensorsInGroup = new ArrayList();
        for (int i = 0; i < input_sens_zones.size(); i++) {
            SensorObject sensor = null;
            sensor = SensorObject_ArrayList.get(input_sens_zones.get(i));
            int found_group = sensor.getSensorGroup();
            if (found_group == input_group_number) {
                foundSensorsInGroup.add(input_sens_zones.get(i));
                //       System.out.println(foundSensorsInGroup);
            }
        }
        for (int l = 0; l < foundSensorsInGroup.size(); l++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(foundSensorsInGroup.get(l));
            String newDLID = temp_sensor.getDLID();
            //       System.out.println(newDLID);
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = " shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 0 i32 " + z;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + send_packet);
       //     System.out.println(send_packet);
            TimeUnit.SECONDS.sleep(2);
        }
    }
    public void sendTamper_allSensors_selectedGroup(List<Integer> input_sens_zones, int input_group_number) throws IOException, InterruptedException {
        List<Integer> foundSensorsInGroup;
        foundSensorsInGroup = new ArrayList();
        for (int i = 0; i < input_sens_zones.size(); i++) {
            SensorObject sensor = null;
            sensor = SensorObject_ArrayList.get(input_sens_zones.get(i));
            int found_group = sensor.getSensorGroup();
            if (found_group == input_group_number) {
                foundSensorsInGroup.add(input_sens_zones.get(i));
                //       System.out.println(foundSensorsInGroup);
            }
        }
        for (int l = 0; l < foundSensorsInGroup.size(); l++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(foundSensorsInGroup.get(l));
            String newDLID = temp_sensor.getDLID();
            //       System.out.println(newDLID);
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = " shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 1 i32 0";
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + send_packet);
      //      System.out.println(send_packet);
            TimeUnit.SECONDS.sleep(2);
        }
    }
    public void sendPacket_activateAllShockIQ_selectedGroup(int input_group_number, int z) throws IOException, InterruptedException {
        List<Integer> foundSensorsInGroup;
        foundSensorsInGroup = new ArrayList();
        for (int i = 0; i < shock_IQ_zones.size(); i++) {
            SensorObject sensor = null;
            sensor = SensorObject_ArrayList.get(shock_IQ_zones.get(i));
            int found_group = sensor.getSensorGroup();
            if (found_group == input_group_number) {
                foundSensorsInGroup.add(shock_IQ_zones.get(i));
                //       System.out.println(foundSensorsInGroup);
            }
        }
        for (int l = 0; l < foundSensorsInGroup.size(); l++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(foundSensorsInGroup.get(l));
            String newDLID = temp_sensor.getDLID();
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = "shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 0 i32 "+z;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + send_packet);
            System.out.println(send_packet);
            TimeUnit.SECONDS.sleep(2);}
    }

    public void deleteAllSensors() throws IOException, InterruptedException {
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            int zone = i;
            String deleteFromPrimary = "  shell service call qservice 51 i32 " + zone;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + deleteFromPrimary);
            //System.out.println(adbPath + " -s " + primary + deleteFromPrimary);
        }
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(i);
            String DLID = temp_sensor.getDLID();
            String deleteFromTransmitter = " shell service call srftransmitservice 4 s16 " + DLID;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + deleteFromTransmitter);
           // System.out.println(deleteFromTransmitter);
            TimeUnit.SECONDS.sleep(1);
        }
    }
    public void deleteAllSensorsTransmitter() throws IOException, InterruptedException {
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            int zone = i;
            String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + deleteFromPrimary);
            System.out.println(ConfigProps.adbPath + " -s " + ConfigProps.primary + deleteFromPrimary);
        }
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(i);
            String DLID = temp_sensor.getDLID();
            String deleteFromTransmitter = "shell service call srftransmitservice 4 s16 " + DLID;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + deleteFromTransmitter);
            System.out.println(deleteFromTransmitter);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void deleteAllSensors1(String UDID_) throws IOException, InterruptedException {
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            int zone = i;
            String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
            rt.exec(ConfigProps.adbPath + " -s " + UDID_ + deleteFromPrimary);
            System.out.println(deleteFromPrimary);
        }
        for (int i = 2; i < SensorObject_ArrayList.size(); i++) {
            SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(i);
            String DLID = temp_sensor.getDLID();
            String deleteFromTransmitter = "shell service call srftransmitservice 4 s16 " + DLID;
            rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.transmitter + deleteFromTransmitter);
     //       System.out.println(deleteFromTransmitter);
            TimeUnit.SECONDS.sleep(1);
        }
    }
    public void delete_from_primary(int zone) throws IOException, InterruptedException {
            String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
            rt.exec(ConfigProps.adbPath + deleteFromPrimary);
            System.out.println(deleteFromPrimary);}
}