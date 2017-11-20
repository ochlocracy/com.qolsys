//package Sensors;
//
//import org.testng.annotations.Test;
//
//public class SensorObjectWithBuilder {
//
//    int Zone;
//    String SensorType;
//    String DLID;
//    int DLID_dec;
//    int SensorGroup;
//    int SupervisoryTime;
//    int Protocol_Int;
//    String Protocol_String;
//
//    public SensorObjectWithBuilder(SensorObjectWithBuilderBuilder builder){
//        this.Zone = builder.input_zone;
//
//
//        DLID = input_DLID;
//        DLID_dec = Integer.parseInt(DLID, 16);
//        SensorType = input_SensorType;
//        SensorGroup = input_SensorGroup;
//        SupervisoryTime = input_SupervisoryTime;
//        Protocol_Int = input_Protocol;
//
//        if(Protocol_Int == 0){
//            Protocol_String = "GE";
//        }
//    }
//
//    public SensorObjectWithBuilder(String SensorType) {this.SensorType = SensorType;}
//
//    public String getSensorType (){
//        return SensorType;
//    }
//
//    public void setZone(int setZone) {
//        Zone = setZone;
//    }
//    public int getZone () {
//        return Zone;
//    }
//
//    public void setDLID(String setDLID) {
//        DLID = setDLID;
//    }
//    public String getDLID (){
//        return DLID;
//    }
//
//    public void setSensorGroup(int setSensorGroup) {
//        SensorGroup = setSensorGroup;
//    }
//    public int getSensorGroup(){
//        return SensorGroup;
//    }
//
//    public void setSupervisoryTime(int setSupervisoryTime) {
//        SupervisoryTime = setSupervisoryTime;
//    }
//
//    public int getSupervisoryTime(){
//        return SupervisoryTime;
//    }
//
//    public void setProtocol_Int(int setProtocol) {Protocol_Int = setProtocol;}
//
//
//    public void printSensorDetails() {
//        System.out.println("Zone: " + Zone);
//        System.out.println("DLID: " + DLID);
//        System.out.println("Sensor Type: " + SensorType);
//        System.out.println("Sensor Group: " + SensorGroup);
//        System.out.println("Sensor Supervisory Time: " + SupervisoryTime);
//        System.out.println("Sensor Protocol: " + Protocol_Int);
//    }
//
//    String AllSensorTypes[] = {"door_window", "motion", "smoke_detector", "co_detector", "glassbreak", "keyfob",
//            "keypad", "auxiliary_pendant", "tilt", "heat", "freeze", "doorbell", "shock"};
//
//    // gives the index of an object in array
//    private static int indexOf(String c, String[] arr) {
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] == c) {
//                return i;}
//        }
//        return -1;
//    }
//
//    public class SensorObjectWithBuilderBuilder {
//        private int input_zone;
//        private String input_dlid;
//        private String input_sensorType;
//        private int input_sensorGroup;
//        private int input_supervisoryTime;
//        private int input_protocol;
//        private String sensorType;
//
//        public sensors.SensorObjectWithBuilderBuilder setInput_Zone(int input_zone) {
//            this.input_zone = input_zone;
//            return this;
//        }
//
//        public sensors.SensorObjectWithBuilderBuilder setInput_DLID(String input_dlid) {
//            this.input_dlid = input_dlid;
//            return this;
//        }
//
//        public sensors.SensorObjectWithBuilderBuilder setInput_SensorType(String input_sensorType) {
//            this.input_sensorType = input_sensorType;
//            return this;
//        }
//
//        public sensors.SensorObjectWithBuilderBuilder setInput_SensorGroup(int input_sensorGroup) {
//            this.input_sensorGroup = input_sensorGroup;
//            return this;
//        }
//
//        public sensors.SensorObjectWithBuilderBuilder setInput_SupervisoryTime(int input_supervisoryTime) {
//            this.input_supervisoryTime = input_supervisoryTime;
//            return this;
//        }
//
//        public sensors.SensorObjectWithBuilderBuilder setInput_Protocol(int input_protocol) {
//            this.input_protocol = input_protocol;
//            return this;
//        }
//
//        public sensors.SensorObjectWithBuilderBuilder setSensorType(String sensorType) {
//            this.sensorType = sensorType;
//            return this;
//        }
//
//        public SensorObjectWithBuilder createSensorObjectWithBuilder() {
//            return new SensorObjectWithBuilder(this);
//        }
//    }
//
//
//}
//
//    SensorObject sensor = new SensprObject.SensorObjectBuilderBuilder()
//     .set
//     .build();
//
//
//
//
//
//
//
