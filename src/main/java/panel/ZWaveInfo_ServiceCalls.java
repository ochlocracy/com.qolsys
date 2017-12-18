package panel;

import org.apache.log4j.Logger;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class ZWaveInfo_ServiceCalls extends Setup {

    public Logger logger = Logger.getLogger(this.getClass().getName());
    public Runtime rt = Runtime.getRuntime();

    public ZWaveInfo_ServiceCalls() throws Exception {
        ConfigProps.init();

    }


///////////////////////PANEL_INFO_ZWAVE_ON_OFF, // on/off // Enable/disable Z-Wave radio///////////////////////////

public void getZWaveStatus() throws IOException, InterruptedException {
    String get_st = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 0 i32 71 i32 0 i32 0";
    rt.exec(get_st);
    String value = (execCmd(get_st)).toString();
    System.out.println(value);
    if (value.contains("00000001")) {
        System.out.println("Zwave is Enabled");
    } else if (value.contains("00000000")) {
        System.out.println("Zwave is Disabled, please Enable the setting");

    }}

    public void setZWaveEnabled() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 71 i32 1 i32 0 i32 0";
        rt.exec(command);         }

    public void setZWaveDisabled() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 71 i32 0 i32 0 i32 0";
        rt.exec(command);            }

    public void set_Remote_Node_add_Start() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1560 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
        rt.exec(command);            }
    public void set_Remote_Node_add_Abort() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
        rt.exec(command);            }

      /* NodeID = 0 ---> it will start Device clear Activity
                                   valied nodeID --> delete that nodeID in background */
    public void set_Remote_Node_Delete_Start(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1562 i32 0 i32 0 i32 0 i32 " + nodeID +" i32 0 i32 0 i32 0";
        rt.exec(command);}



    public void set_Remote_Node_Delete_Abort() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1563 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
        rt.exec(command);    }

    public void get_Zwave_network_ID_Home_ID() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 50 i32 0 i32 0";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value + "*** \n" +
                "Example: Parcel(00000000 00000004 a14bd9e3 '..........K.')\n" +
                "Home ID is --- e3d94ba1 and 4 is length of the byte array\n");}

    public void set_Rediscovery_Start() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1557 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
        rt.exec(command);    }
    public void set_Rediscovery_abort() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1558 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
        rt.exec(command);    }

    public void get_Zwave_Rediscovery_Result() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 68";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value); }

    public void get_valied_nodeID() throws IOException, InterruptedException {
    String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 21 i32 nodeID i32 0";
    rt.exec(command);
    String value = (execCmd(command)).toString();
    System.out.println(value);
    if (value.contains("00000001")) {
        System.out.println("The nodeId is registered");
    } else if (value.contains("00000000")) {
        System.out.println("The nodeId is not registered");
    } }

    public void get_Counter_CommandStatus() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 51 i32 0 i32 0";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value + "*** \n" +"Example: Parcel(\n" +
                " 0x00000000: 00000000 00000018 00000000 79000000 '...............y'\n" +
                " 0x00000010: 50000000 00000000 00000000 00000000 '...P............')\n" +
                "Will return 24 byte Array \n" +
                "0 to 3 bytes ---- Cmds no auto route\n" +
                "4 to 7 bytes --- cmds auto route\n" +
                "8 to 11 bytes ---- cmds no ack\n" +
                "12 to 15 bytes ----- cmds network failed\n" +
                "16 to 19 bytes ----- cmds network not idel\n" +
                "20 to 23 bytes --- cmds network no route ");
      }

      /* Option: 09(1001) : resets ACKED_COMMANDS_NO_AUTO_ROUTE counter and FAILED_COMMANDS_NWK_FAILED counter
                 06 (0110): resets ACKED_COMMANDS_AUTO_ROUTE counter and FAILED_COMMANDS_NO_ACK counter
                 If option is 63 will resets the all counters*/
    public void set_Counter_Reset(int option) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1564 i32 0 i32 0 i32 0 i32 " + option + " i32 0 i32 0 i32 0";
        rt.exec(command);}

    public void get_Serial_API_Service_Calls_HomeID() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 63 i32 0 i32 32 i32 0 i32 0 i32 0 i32 token i32 0";
        rt.exec(command);}

    public void get_Serial_API_Service_Calls_neighbor_info() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 63 i32 nodeID i32 128 i32 0 i32 0 i32 0 i32 token i32 0";
        rt.exec(command);}

    public void get_Cur_Thermostat_mode(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 41 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_Battery_Level_Value(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 45 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_Cur_Fan_mode(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 42 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_Door_Lock_mode_Value(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 39 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_Node_Count() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 0 i32 0 i32 0";
        rt.exec(command);}

    public void get_Panel_Node_ID() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 1 i32 0 i32 0";
        rt.exec(command);}

    public void get_Generic_Device_type(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 3 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_BASIC_DEVICE_TYPE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 2 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_Specific_DEVICE_TYPE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 4 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_NUM_COMMAND_CLASS(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 5 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_NUM_COMMAND_CLASS_INSTANCE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 6 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_COMMAND_CLASS_FOR_INSTANCE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 7 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_NUM_SECURE_COMMAND_CLASS(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 8 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_MANUFACTURER_ID(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 10 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_SECURE_COMMAND_CLASS(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 9 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_PRODUCT_TYPE_ID(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 12 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_LIBRARY_TYPE_VERSION(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 13 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_PROTOCOL_VERSION(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 14 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_PROTOCOL_SUB_VERSION(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 15 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_APPLICATION_VERSION(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 16 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_APPLICATION_SUB_VERSION(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 17 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_MULTI_CHANNEL_DETAILS(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 37 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_SETPOINT_VALUE(int nodeID, int index) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 40 i32 " + nodeID + " i32 " + index;
        rt.exec(command);}

    public void get_MULTILEVEL_SENSOR_VALUE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 44 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_MODE_BITMASK(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 18 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_FAN_MODE_BITMASK(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 19 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_SET_POINT_MODE_BITMASK(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 20 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_MULTILEVEL_SENSOR_BITMASK(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 55 i32 0 i32 2 i32 43 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_IS_NODE_VALID(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 21 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_SECURITY_INCLUSION_FAILED(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 22 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_IS_NODE_LISTENING_NODE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 23 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_IS_NODE_THERMOSTAT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 24 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_MODE_SUPPORT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 25 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_FAN_MODE_SUPPORT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 26 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_THERMOSTAT_SET_POINT_MODE_SUPPORT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 27 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_IS_NODE_FAILED(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 28 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_IS_NODE_ZWAVE_LOCK(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 29 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_IS_NODE_BINARY_SWITCH(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 30 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_IS_NODE_MULTILEVEL_SWITCH(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 31 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_IS_NODE_MULTILEVEL_SENSOR(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 32 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_ZWAVE_QUERY_IS_NODE_BASIC_REPORTING(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 33 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_MANUFACTURE_INFO_PRESENT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 34 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_VERSION_INFO_PRESENT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 35 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_QUERY_NODE_PROVIDES_BATTERY_LEVEL(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 36 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_NODE_PROVIDES_DOOR_LOCK_REPORT(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 2 i32 38 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_BASIC_REPORT_VALUE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 46 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_BINARY_SWITCH_LEVEL(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 47 i32 " + nodeID + " i32 0";
        rt.exec(command);}

    public void get_MULTILEVEL_SWITCH_VALUE(int nodeID) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 2 i32 48 i32 " + nodeID + " i32 0";
        rt.exec(command);}
////////////////////////////////////////////Node rediscovery start/stop by adc events:////////////////////////////////////

    public void set_EVENT_ADC_ZWAVE_NODE_DISCOVERY_STARTED() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1557 i32 6 i32 0 i32 0 i32 1 i32 0 i32 0 i32 0";
        rt.exec(command);}

    public void set_EVENT_ADC_ZWAVE_NODE_DISCOVERY_ENDED() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1558 i32 6 i32 0 i32 0 i32 1 i32 0 i32 0 i32 0";
        rt.exec(command);}

////////////////////////////////////////////Device Limits:////////////////////////////////////////////////////////

public void get_PANEL_INFO_DEVICE_LIMIT_SMART_SOCKET() throws IOException, InterruptedException {
    String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 85 i32 0 i32 0";
    rt.exec(command);}

    public void set_PANEL_INFO_DEVICE_LIMIT_SMART_SOCKET(int limit) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 85 i32 0 i32 " + limit + " i32 0";
        rt.exec(command);}

    public void get_PANEL_INFO_DEVICE_LIMIT_THERMOSTAT() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 86 i32 0 i32 0";
        rt.exec(command);}

    public void set_PANEL_INFO_DEVICE_LIMIT_THERMOSTAT(int limit) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 86 i32 0 i32 " + limit + " i32 0";
        rt.exec(command);}

    public void get_PANEL_INFO_DEVICE_LIMIT_DOORLOCK() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 87 i32 0 i32 0";
        rt.exec(command);}

    public void set_PANEL_INFO_DEVICE_LIMIT_DOORLOCK(int limit) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 87 i32 0 i32 " + limit + " i32 0";
        rt.exec(command);}

    public void get_PANEL_INFO_DEVICE_LIMIT_DIMMER() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 88 i32 0 i32 0";
        rt.exec(command);}

    public void set_PANEL_INFO_DEVICE_LIMIT_DIMMER(int limit) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 88 i32 0 i32 " + limit + " i32 0";
        rt.exec(command);}

    public void get_TEMPERATURE_SCALE() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 28 i32 0 i32 0";
        rt.exec(command);}

    public void set_TEMPERATURE_SCALE() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 28 i32 1 i32 0 i32 0";
        rt.exec(command);}

    public void get_ZWAVE_ON_OFF() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 0 i32 71 i32 0 i32 0";
        rt.exec(command);}

    public void set_ZWAVE_ON_OFF() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 71 i32 1 i32 0 i32 0";
        rt.exec(command);}

    public void get_DEVICE_LIMIT_SMART_SOCKET() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 85 i32 0 i32 0";
        rt.exec(command);}

    public void set_DEVICE_LIMIT_SMART_SOCKET(int LIMIT) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 85 i32 10 " + LIMIT + " i32 0 i32 0";
        rt.exec(command);}

    public void get_DEVICE_LIMIT_THERMOSTAT() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 86 i32 0 i32 0";
        rt.exec(command);}

    public void set_DEVICE_LIMIT_THERMOSTAT(int LIMIT) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 86 i32 5 " + LIMIT + " i32 0 i32 0";
        rt.exec(command);}

    public void get_DEVICE_LIMIT_DOORLOCK() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 87 i32 0 i32 0";
        rt.exec(command);}

    public void set_DEVICE_LIMIT_DOORLOCK(int LIMIT) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 87 i32 4 " + LIMIT + " i32 0 i32 0";
        rt.exec(command);}

    public void get_DEVICE_LIMIT_DIMMER() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 88 i32 0 i32 0";
        rt.exec(command);}

    public void set_DEVICE_LIMIT_DIMMER(int LIMIT) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 88 i32 4 " + LIMIT + " i32 0 i32 0";
        rt.exec(command);}

    public void get_DEVICE_LIMIT_OTHERDEVICES () throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 36 i32 0 i32 0 i32 125 i32 0 i32 0";
        rt.exec(command);}

    public void set_DEVICE_LIMIT_OTHERDEVICES( int LIMIT) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 39 i32 0 i32 0 i32 125 i32 " + LIMIT + " i32 0 i32 0";
        rt.exec(command);}

    public void get_ZWAVE_REMOTE_VOICE_PROMPTS () throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 0 i32 117 i32 0 i32 0";
        rt.exec(command);}

    public void set_ZWAVE_REMOTE_VOICE_PROMPTS(boolean Value) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 117  i32 0 " + Value + " i32 0 i32 0";
        rt.exec(command);}

    public void get_ZWAVE_DEVICE_VOICE_PROMPTS () throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 0 i32 116  i32 0 i32 0";
        rt.exec(command);}




}

