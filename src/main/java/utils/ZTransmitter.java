package utils;

import java.util.HashMap;

/**
 * Created by qolsys on 12/5/17.
 */
public class ZTransmitter extends ConfigPropsBase {


    public static String transmitterInfo;
    public static String includeTransmitter;
    public static String excludeTransmitter;
    public static String addLight;
    public static String clearLight;
    public static String addThermostat;
    public static String clearThermostat;
    public static String doorLockInclusion;
    public static String doorLockExclude;
    public static String addGDC;
    public static String clearGDC;
    public static String ztransmitterExecute;

    private static ZTransmitter instance;

    HashMap<String, String> deviceType = new HashMap<String, String>();
    HashMap<String, String> deviceAction= new HashMap<String, String>();

    public String getztransmitterExecute(){
        return ztransmitterExecute;
    }


    public void setztransmitterExecute(String ztransmitterExecute){
        this.ztransmitterExecute = ztransmitterExecute ;
    }


    public ZTransmitter() throws Exception {
        super("transmitter.properties");

        includeTransmitter = getString("transmitterInclusion");
        excludeTransmitter = getString("transmitterExclude");
        addLight = getString("ligthInclusion");
        clearLight = getString("ligthExclude");
        addThermostat = getString("thermostatInclude");
        clearThermostat = getString("thermostatExclude");
        doorLockInclusion = getString("doorLockInclusion");
        doorLockExclude = getString("doorLockExclude");
        addGDC = getString("gdcInclude");
        clearGDC = getString("gdcExclude");
        transmitterInfo = getString("transmitterInfo");
    }

    public static void init() throws Exception {
        if (instance == null) {
            instance = new ZTransmitter();
        }
    }




    //service call for all device types
    public void deviceType() throws Exception{
        deviceType.put("Light", "service call zwavetransmitservice 5 i32 ");
        deviceType.put("Thermostat","service call zwavetransmitservice  6 i32 ");
        deviceType.put("Dimmer","service call zwavetransmitservice 5 i32 ");
        deviceType.put("DoorLock","service call zwavetransmitservice 7 i32 ");
        deviceType.put("GDC","service call zwavetransmitservice 8 i32 ");
        deviceType.put("SmartSocket","service call zwavetransmitservice 5 i32 ");

    }
    public void nodeID(){

    }
    //Add model profile for devices
//    public void modelProfile(){
//
//    }


    // Service call to perform a device action
    public void lightsAction() throws Exception{
        //Lights
        deviceAction.put("lightON", "i32 0 i32 1 i32 2 i32 1");
        deviceAction.put("lightOff", "i32 0 i32 1 i32 2 i32 0");
        deviceAction.put("MeteringGetInfo", "i32 0 i32 2 i32 1 i32");
        deviceAction.put("MeterignSetInfo", " i32 0 i32 2 i32 2 i32");//(value: 1000 = 1kwh)
    }

    public void doorLockAction() throws Exception{
        deviceAction.put("DoorLockOpen","i32 0 i32 1 i32 2 i32 0");
        deviceAction.put("DoorLockClose","i32 0 i32 1 i32 2 i32 1");
        deviceAction.put("DoorLockGet","i32 0 i32 1 i32 1");
    }
    public void thermostatAction() throws Exception{
        deviceAction.put("GetCurrentTemp","i32 0 i32 1 i32 1");
        deviceAction.put("SetCurrentTemp","i32 0 i32 0");// Values:000-1000. Ex: 70 degrees is 700
        deviceAction.put("GetTermostatMode","i32 0 i32 2 i32 1");
        deviceAction.put("SetThermostatModeOff","i32 0 i32 2 i32 2 i32 0");
        deviceAction.put("SetThermostatModeHeat","i32 0 i32 2 i32 2 i32 1");
        deviceAction.put("SetThermostatModeCool","i32 0 i32 2 i32 2 i32 2");
        deviceAction.put("SetThermostatModeAuto","i32 0 i32 2 i32 2 i32 3");
        deviceAction.put("GetSetPointHeat","i32");
        deviceAction.put("GetSetPointCool","");
        deviceAction.put("SetSetPointHeating","");
        deviceAction.put("SetSetPointCooling","");
        deviceAction.put("GetBatteryLevel","");
        deviceAction.put("SetBatteryLevel","");//Values: 0-100 in HEX
    }
    public void dimmerAction() throws Exception{
        deviceAction.put("DimmingLevelSet","i32 0 i32 3 i32 2 i32");//Value 0-255
        deviceAction.put("DimmingLevelGet","i32 0 i32 3 i32 1");
    }
    public void gdcActions() throws Exception{
        deviceAction.put("GarageDoorOpen","i32 0 i32 1 i32 2 i32 1");
        deviceAction.put("GarageDoorClose", "i32 0 i32 1 i32 2 i32 0 ");
        deviceAction.put("GarageDoorGet", "i32 0 i32 1 i32 1");
    }


}


