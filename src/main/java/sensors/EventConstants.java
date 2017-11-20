package sensors;

public class EventConstants {

    public static final String EVENT_TO_ADC_STAY = "EVENT_ARM_STAY, userID = 0, keyfob = FALSE";
    public static final String EVENT_TO_ADC_AWAY = "EVENT_ARM_AWAY, userID = 0, keyfob = FALSE";
    public static final String EVENT_TO_ADC_STAY_materpin = "EVENT_ARM_STAY, userID = 1, keyfob = FALSE";
    public static final String EVENT_TO_ADC_AWAY_masterpin = "EVENT_ARM_AWAY, userID = 1, keyfob = FALSE";
    public static final String EVENT_TO_ADC_DISARM = "EVENT_DISARM, userID = 1, keyfob = FALSE.";

    public static final String Duress_Authentication = "EVENT_SETTINGS_UPDATED: payload0 = 0x003D0000, payload1 = 0x00000001";
    public static final String Secure_Arming = "EVENT_SETTINGS_UPDATED: payload0 = 0x00230000, payload1 = 0x00000001";
    public static final String Autobypass = "EVENT_SETTINGS_UPDATED: payload0 = 0x00130000, payload1 = 0x00000001";
    public static final String Autostay = "EVENT_SETTINGS_UPDATED: payload0 = 0x00140000, payload1 = 0x00000001";

    public static final String ArmStayNodelay = "EVENT_SETTINGS_UPDATED: payload0 = 0x00150000, payload1 = 0x00000001";
    public static final String KeyfobInstantArming = "EVENT_SETTINGS_UPDATED: payload0 = 0x00160000, payload1 = 0x00000001";

    public static final String EventProgrammingmodeStarted = "EVENT_PROGRAMMING_MODE_STARTED, ImageSensorID = 60, payload1 = 0x00000000";
    public static final String EventProgrammingmodeEnded =  "EVENT_PROGRAMMING_MODE_ENDED, ImageSensorID = 0, payload1 = 0x00000000";
    public static final String rfpump_3 = "EVENT_ZONE_TAMPERED: payload0 = 0x00000001, payload1 = 0x00000001";
    public static final String rfpump3_Restored = "EVENT_ZONE_TAMPERED: payload0 = 0x00000001, payload1 = 0x00000000";
    public static final String rfpump_2 = "EVENT_ZONE_CLOSED, zone = 1";
    public static final String rfpump_1 = "EVENT_ZONE_OPENED, zone = 1";
    public static final String rfpump_21 = "EVENT_ZONE_OPENED, zone = 2";
    public static final String rfpump_22 = "EVENT_ZONE_CLOSED, zone = 2";
    public static final String rfpump_23 = "EVENT_ZONE_TAMPERED: payload0 = 0x00000002, payload1 = 0x00000001";
    public static final String rfpump_23_Restored = "EVENT_ZONE_TAMPERED: payload0 = 0x00000002, payload1 = 0x00000000";

    public static final String rfpump_24 = "EVENT_ZONE_OPENED, zone = 3";
    public static final String rfpump_25 = "EVENT_ZONE_CLOSED, zone = 3";
    public static final String rfpump_26 = "EVENT_ZONE_TAMPERED: payload0 = 0x00000003, payload1 = 0x00000001";
    public static final String rfpump_26_Restored = "EVENT_ZONE_TAMPERED: payload0 = 0x00000003, payload1 = 0x00000000";

}
