package sensors;

public class SensorObject  {

    int Zone;
    String SensorType;
    String DLID;
    int DLID_dec;
    int SensorGroup;
    int SupervisoryTime;
    int Protocol_Int;
    String Protocol_String;

    public SensorObject(){}

    public SensorObject(int input_Zone, String input_DLID, String input_SensorType, int input_SensorGroup, int input_SupervisoryTime, int input_Protocol){
        Zone = input_Zone;
        DLID = input_DLID;
        DLID_dec = Integer.parseInt(DLID, 16);
        SensorType = input_SensorType;
        SensorGroup = input_SensorGroup;
        SupervisoryTime = input_SupervisoryTime;
        Protocol_Int = input_Protocol;

        if(Protocol_Int == 0){
            Protocol_String = "GE";
        }
    }

    public SensorObject(String SensorType) {this.SensorType = SensorType;}

    public String getSensorType (){
        return SensorType;
    }

    public void setZone(int setZone) {
        Zone = setZone;
    }
    public int getZone () {
        return Zone;
    }

    public void setDLID(String setDLID) {
        DLID = setDLID;
    }
    public String getDLID (){
        return DLID;
    }

    public void setSensorGroup(int setSensorGroup) {
        SensorGroup = setSensorGroup;
    }
    public int getSensorGroup(){
        return SensorGroup;
    }

    public void setSupervisoryTime(int setSupervisoryTime) {
        SupervisoryTime = setSupervisoryTime;
    }

    public int getSupervisoryTime(){
        return SupervisoryTime;
    }

    public void setProtocol_Int(int setProtocol) {Protocol_Int = setProtocol;}


    public void printSensorDetails() {
        System.out.println("Zone: " + Zone);
        System.out.println("DLID: " + DLID);
        System.out.println("Sensor Type: " + SensorType);
        System.out.println("Sensor Group: " + SensorGroup);
        System.out.println("Sensor Supervisory Time: " + SupervisoryTime);
        System.out.println("Sensor Protocol: " + Protocol_Int);
    }

    String AllSensorTypes[] = {"door_window", "motion", "smoke_detector", "co_detector", "glassbreak", "keyfob",
            "keypad", "auxiliary_pendant", "tilt", "heat", "freeze", "doorbell", "shock"};

    // gives the index of an object in array
    private static int indexOf(String c, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == c) {
                return i;}
        }
        return -1;
    }
}












