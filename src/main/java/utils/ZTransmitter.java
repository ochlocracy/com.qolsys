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


    HashMap<String, String> lightAction= new HashMap<String, String>();
    HashMap<String, String> doorLockAction= new HashMap<String, String>();
    HashMap<String, String> thermostatAction= new HashMap<String, String>();
    HashMap<String, String> dimmerAction= new HashMap<String, String>();
    HashMap<String, String> gdcActions= new HashMap<String, String>();

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
        addLight = getString("lightInclusion");
        clearLight = getString("lightExclude");
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
        HashMap<String, String> deviceType = new HashMap<String, String>();
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
        lightAction.put("lightON", "i32 0 i32 1 i32 2 i32 1");
        lightAction.put("lightOff", "i32 0 i32 1 i32 2 i32 0");
        lightAction.put("MeteringGetInfo", "i32 0 i32 2 i32 1 i32");
        lightAction.put("MeterignSetInfo", " i32 0 i32 2 i32 2 i32");//(value: 1000 = 1kwh)
    }

    public void doorLockAction() throws Exception{
        doorLockAction.put("DoorLockOpen","i32 0 i32 1 i32 2 i32 0");
        doorLockAction.put("DoorLockClose","i32 0 i32 1 i32 2 i32 1");
        doorLockAction.put("DoorLockGet","i32 0 i32 1 i32 1");
    }
    public void thermostatAction() throws Exception{
        thermostatAction.put("GetCurrentTemp","i32 0 i32 1 i32 1");
        thermostatAction.put("SetCurrentTemp","i32 0 i32 0");// Values:000-1000. Ex: 70 degrees is 700
        thermostatAction.put("GetTermostatMode","i32 0 i32 2 i32 1");
        thermostatAction.put("SetThermostatModeOff","i32 0 i32 2 i32 2 i32 0");
        thermostatAction.put("SetThermostatModeHeat","i32 0 i32 2 i32 2 i32 1");
        thermostatAction.put("SetThermostatModeCool","i32 0 i32 2 i32 2 i32 2");
        thermostatAction.put("SetThermostatModeAuto","i32 0 i32 2 i32 2 i32 3");
        thermostatAction.put("GetSetPointHeat","i32");
        thermostatAction.put("GetSetPointCool","");
        thermostatAction.put("SetSetPointHeating","");
        thermostatAction.put("SetSetPointCooling","");
        thermostatAction.put("GetBatteryLevel","");
        thermostatAction.put("SetBatteryLevel","");//Values: 0-100 in HEX
    }
    public void dimmerAction() throws Exception{
        dimmerAction.put("DimmingLevelSet","i32 0 i32 3 i32 2 i32");//Value 0-255
        dimmerAction.put("DimmingLevelGet","i32 0 i32 3 i32 1");
    }
    public void gdcActions() throws Exception{
        gdcActions.put("GarageDoorOpen","i32 0 i32 1 i32 2 i32 1");
        gdcActions.put("GarageDoorClose", "i32 0 i32 1 i32 2 i32 0 ");
        gdcActions.put("GarageDoorGet", "i32 0 i32 1 i32 1");
    }


}


