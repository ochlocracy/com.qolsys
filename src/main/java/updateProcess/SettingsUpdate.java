package updateProcess;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.Setup;

import java.io.File;
import java.io.IOException;

public class SettingsUpdate extends Setup {

    String ON = "00000001";
    String OFF = "00000000";

    int one_sec = 1000;

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public SettingsUpdate() throws Exception {
        ConfigProps.init();
    }

    public void verifySetting(String setting, String call, String expected) throws IOException {
        String result = execCmd(ConfigProps.adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            log.log(LogStatus.PASS, "[Pass] " + setting + " has value: " + expected);
        else
            log.log(LogStatus.FAIL, "[Fail] " + setting + " has value: " + result + ". Expected:" + expected);
    }

    public void setDefaultSettings() throws IOException, InterruptedException {
        int ON = 1;
        int OFF = 0;
        int one_sec = 1000;
        //Dealer Settings
        servcall.set_POWER_MANAGEMENT_ON_OFF_enable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_disable();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT(4); //4 hours
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_TIMEOUTY_24h();
        Thread.sleep(one_sec);
        servcall.set_Cell_Signal_Timeout(30);
        Thread.sleep(one_sec);
        servcall.set_SIA_LIMITS_enable();
        Thread.sleep(one_sec);
        servcall.set_RF_JAM_DETECT_disable();
        Thread.sleep(one_sec);
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(1);
        Thread.sleep(one_sec);

        //need Glass Break detector and Panel motion!!!
        //new setting Wi-Fi Warning Msg

        //System Logs
        servcall.set_AUTO_UPLOAD_LOGS(0);
        Thread.sleep(one_sec);

        //Siren and Alarms
        servcall.set_SIREN_ANNUNCIATION(0);
        Thread.sleep(one_sec);
        servcall.set_FIRE_VERIFICATION(0);
        Thread.sleep(one_sec);
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(1);
        Thread.sleep(one_sec);
        servcall.set_DIALER_DELAY(30);
        Thread.sleep(one_sec);
        servcall.set_SIREN_TIMEOUT(240);
        Thread.sleep(one_sec);
        servcall.set_WATER_FREEZE_ALARM_disable();
        Thread.sleep(one_sec);
        servcall.set_POLICE_PANIC(1);
        Thread.sleep(one_sec);
        servcall.set_FIRE_PANIC(1);
        Thread.sleep(one_sec);
        servcall.set_AUXILIARY_PANIC(1);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(OFF);
        Thread.sleep(one_sec);

        //Security and Arming
        servcall.set_DURESS_AUTHENTICATION_disable();
        Thread.sleep(one_sec);
        servcall.set_SECURE_ARMING_disable();
        Thread.sleep(one_sec);
        servcall.set_NO_ARMING_ON_LOW_BATTERY_disable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(one_sec);
        servcall.set_AUTO_STAY(1);
        Thread.sleep(one_sec);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_EXIT_TIME_EXTENSION(1);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_ALARM_DISARM(0);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_DISARMING(1);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(OFF);
        Thread.sleep(one_sec);

        //Camera Settings
        servcall.set_SECURE_DELETE_IMAGES(1);
        Thread.sleep(one_sec);
        servcall.set_DISARM_PHOTO(1);
        Thread.sleep(one_sec);
        servcall.set_ALARM_PHOTOS(1);
        Thread.sleep(one_sec);
        //need Alarm_Videos
        servcall.set_SETTINGS_PHOTOS(0);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(OFF);
        Thread.sleep(one_sec);

        //Bluetooth
        servcall.set_BLUETOOTH(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM_TIMEOUT(10);
        Thread.sleep(one_sec);

        //sets media volume to 1
        //       servcall.set_SPEAKER_VOLUME(OFF);
        //       Thread.sleep(one_sec);
        servcall.set_ALL_VOICE_PROMPTS(ON);
        Thread.sleep(one_sec);
        servcall.set_SENSOR_VOICE_PROMPTS(1);
        Thread.sleep(one_sec);
        servcall.set_PANEL_VOICE_PROMPTS(1);
        Thread.sleep(one_sec);
        servcall.set_SAFETY_SENSORS_VOICE_PROMPTS(1);
        Thread.sleep(one_sec);
        servcall.set_ALL_CHIMES(ON);
        Thread.sleep(one_sec);
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_ZWAVE_REMOTE_VOICE_PROMPTS(true);
        Thread.sleep(one_sec);

        servcall.set_TOUCH_SOUND(1);
        Thread.sleep(one_sec);

        //Zwave
        servcall.set_ZWAVE_ON_OFF(1);
        Thread.sleep(one_sec);
        servcall.set_DEVICE_LIMIT_SMART_SOCKET(0);
        Thread.sleep(one_sec);
        servcall.set_DEVICE_LIMIT_THERMOSTAT(3);
        Thread.sleep(one_sec);
        servcall.set_DEVICE_LIMIT_DIMMER(5);
        Thread.sleep(one_sec);
        servcall.set_DEVICE_LIMIT_DOORLOCK(3);
        Thread.sleep(one_sec);
        servcall.set_DEVICE_LIMIT_OTHERDEVICES(3);
        Thread.sleep(one_sec);

        servcall.set_PANEL_AUTO_UPDATE(false);
        Thread.sleep(one_sec);
    }

    public void setAllOn() throws IOException, InterruptedException {
        int ON = 1;
        int one_sec = 1000;

        servcall.set_POWER_MANAGEMENT_ON_OFF_enable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_enable();
        Thread.sleep(one_sec);
        servcall.set_SIA_LIMITS_enable();
        Thread.sleep(one_sec);
        servcall.set_RF_JAM_DETECT_enable();
        Thread.sleep(one_sec);
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(1);
        Thread.sleep(one_sec);

        //need Glass Break detector and Panel motion!!!
        //new setting Wi-Fi Warning Msg

        //System Logs
        servcall.set_AUTO_UPLOAD_LOGS(1);
        Thread.sleep(one_sec);

        //Siren and Alarms
        servcall.set_SIREN_ANNUNCIATION(1);
        Thread.sleep(one_sec);
        servcall.set_FIRE_VERIFICATION(1);
        Thread.sleep(one_sec);
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(1);
        Thread.sleep(one_sec);
        servcall.set_WATER_FREEZE_ALARM_enable();
        Thread.sleep(one_sec);
        servcall.set_POLICE_PANIC(1);
        Thread.sleep(one_sec);
        servcall.set_FIRE_PANIC(1);
        Thread.sleep(one_sec);
        servcall.set_AUXILIARY_PANIC(1);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(1);
        Thread.sleep(one_sec);

        //Security and Arming
 //       servcall.set_DURESS_AUTHENTICATION_enable();
 //       Thread.sleep(one_sec);
        servcall.set_SECURE_ARMING_enable();
        Thread.sleep(one_sec);
        servcall.set_NO_ARMING_ON_LOW_BATTERY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(one_sec);
        servcall.set_AUTO_STAY(1);
        Thread.sleep(one_sec);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_EXIT_TIME_EXTENSION(1);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_ALARM_DISARM(1);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_DISARMING(1);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(1);
        Thread.sleep(one_sec);

        //Camera Settings
        servcall.set_SECURE_DELETE_IMAGES(1);
        Thread.sleep(one_sec);
        servcall.set_DISARM_PHOTO(1);
        Thread.sleep(one_sec);
        servcall.set_ALARM_PHOTOS(1);
        Thread.sleep(one_sec);
        //need Alarm_Videos
        servcall.set_SETTINGS_PHOTOS(1);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(1);
        Thread.sleep(one_sec);

        //Bluetooth
        servcall.set_BLUETOOTH(1);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM(1);
        Thread.sleep(one_sec);

        //sets media volume to 1
        //       servcall.set_SPEAKER_VOLUME(OFF);
        //       Thread.sleep(one_sec);
        servcall.set_ALL_VOICE_PROMPTS(ON);
        Thread.sleep(one_sec);
        servcall.set_SENSOR_VOICE_PROMPTS(1);
        Thread.sleep(one_sec);
        servcall.set_PANEL_VOICE_PROMPTS(1);
        Thread.sleep(one_sec);
        servcall.set_SAFETY_SENSORS_VOICE_PROMPTS(1);
        Thread.sleep(one_sec);
        servcall.set_ALL_CHIMES(ON);
        Thread.sleep(one_sec);
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(1);
        Thread.sleep(one_sec);
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(1);
        Thread.sleep(one_sec);
        servcall.set_ZWAVE_REMOTE_VOICE_PROMPTS(true);
        Thread.sleep(one_sec);
        servcall.set_ZWAVE_REMOTE_PROMPTS(1);

        servcall.set_TOUCH_SOUND(1);
        Thread.sleep(one_sec);

        servcall.set_PANEL_AUTO_UPDATE(true);
        Thread.sleep(one_sec);
    }

    public void setAllOff() throws IOException, InterruptedException {
        int OFF = 0;
        int one_sec = 1000;
        //Dealer Settings
        servcall.set_POWER_MANAGEMENT_ON_OFF_disable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_disable();
        Thread.sleep(one_sec);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(one_sec);
        servcall.set_RF_JAM_DETECT_disable();
        Thread.sleep(one_sec);
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(0);
        Thread.sleep(one_sec);

        //need Glass Break detector and Panel motion!!!
        //new setting Wi-Fi Warning Msg

        //System Logs
        servcall.set_AUTO_UPLOAD_LOGS(0);
        Thread.sleep(one_sec);

        //Siren and Alarms
        servcall.set_SIREN_ANNUNCIATION(0);
        Thread.sleep(one_sec);
        servcall.set_FIRE_VERIFICATION(0);
        Thread.sleep(one_sec);
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(0);
        Thread.sleep(one_sec);
        servcall.set_WATER_FREEZE_ALARM_disable();
        Thread.sleep(one_sec);
        servcall.set_POLICE_PANIC(0);
        Thread.sleep(one_sec);
        servcall.set_FIRE_PANIC(0);
        Thread.sleep(one_sec);
        servcall.set_AUXILIARY_PANIC(0);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(OFF);
        Thread.sleep(one_sec);

        //Security and Arming
//        servcall.set_DURESS_AUTHENTICATION_disable();
//        Thread.sleep(one_sec);
        servcall.set_SECURE_ARMING_disable();
        Thread.sleep(one_sec);
        servcall.set_NO_ARMING_ON_LOW_BATTERY_disable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_BYPASS(0);
        Thread.sleep(one_sec);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(one_sec);
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_EXIT_TIME_EXTENSION(0);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_NO_DELAY_disable();
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_ALARM_DISARM(0);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_DISARMING(0);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(OFF);
        Thread.sleep(one_sec);

        //Camera Settings
        servcall.set_SECURE_DELETE_IMAGES(0);
        Thread.sleep(one_sec);
        servcall.set_DISARM_PHOTO(0);
        Thread.sleep(one_sec);
        servcall.set_ALARM_PHOTOS(0);
        Thread.sleep(one_sec);
        //need Alarm_Videos
        servcall.set_SETTINGS_PHOTOS(0);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(OFF);
        Thread.sleep(one_sec);

        //Bluetooth
        servcall.set_BLUETOOTH(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM(OFF);
        Thread.sleep(one_sec);

        //sets media volume to 1
        //       servcall.set_SPEAKER_VOLUME(OFF);
        //       Thread.sleep(one_sec);
        servcall.set_ALL_VOICE_PROMPTS(0);
        Thread.sleep(one_sec);
        servcall.set_SENSOR_VOICE_PROMPTS(0);
        Thread.sleep(one_sec);
        servcall.set_PANEL_VOICE_PROMPTS(0);
        Thread.sleep(one_sec);
        servcall.set_SAFETY_SENSORS_VOICE_PROMPTS(0);
        Thread.sleep(one_sec);
        servcall.set_ALL_CHIMES(0);
        Thread.sleep(one_sec);
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_ZWAVE_REMOTE_VOICE_PROMPTS(false);
        Thread.sleep(one_sec);

        servcall.set_TOUCH_SOUND(0);
        Thread.sleep(one_sec);

        //Zwave
        servcall.set_ZWAVE_ON_OFF(0);
        Thread.sleep(one_sec);

        servcall.set_PANEL_AUTO_UPDATE(false);
        Thread.sleep(one_sec);
    }

    public void verifyDefault() throws IOException, InterruptedException {
        verifySetting("Power Management", "37 i32 0 i32 0 i32 73 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("SIA Power Restoration", "37 i32 0 i32 0 i32 74 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Loss of Supervisory Signals for Emergency sensors", "36 i32 0 i32 0 i32 118 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verifySetting("Loss of Supervisory Signals for Non-emergency sensors", "36 i32 0 i32 0 i32 31 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verifySetting("Cell Signal Timeout", "36 i32 0 i32 0 i32 101 i32 0 i32 0", "00000019");
        Thread.sleep(one_sec);
        verifySetting("SIA Limits", "37 i32 0 i32 0 i32 37 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("RF Jam Detect", "37 i32 0 i32 0 i32 25 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Open/Close Reports for Auto Learn", "37 i32 0 i32 0 i32 127 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Upload Logs", "37 i32 0 i32 0 i32 90 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Siren Annunciation", "37 i32 0 i32 0 i32 120 i32 0 i32 0 ", OFF);
        Thread.sleep(one_sec);
        verifySetting("Fire Verification", "37 i32 0 i32 0 i32 100 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Severe Weather Siren Warning", "37 i32 0 i32 0 i32 103 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Water Freeze Alarm", "37 i32 0 i32 0 i32 122  i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Police Panic", "37 i32 0 i32 0 i32 131 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Fire Panic", "37 i32 0 i32 0 i32 132 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auxillary Panic", "37 i32 0 i32 0 i32 133 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Siren and Alarms settings", "37 i32 0 i32 0 i32 105 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Duress Authentication", "37 i32 0 i32 0 i32 61 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Secure Arming", "37 i32 0 i32 0 i32 35 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("No Arming On Low Battery", "37 i32 0 i32 0 i32 36 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Auto Bypass", "37 i32 0 i32 0 i32 19 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Stay", "37 i32 0 i32 0 i32 20 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Arm Stay No Delay", "37 i32 0 i32 0 i32 21 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Exit Time Extension", "37 i32 0 i32 0 i32 84 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Instant Arming", "37 i32 0 i32 0 i32 22 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Alarm Disarm", "37 i32 0 i32 0 i32 129 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Disarming", "37 i32 0 i32 0 i32 134 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Security and Arming settings", "37 i32 0 i32 0 i32 106 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Secure Delete Images", "37 i32 0 i32 0 i32 104 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Disarm Photo", "37 i32 0 i32 0 i32 102 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Alarm Photos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Settings Photos", "37 i32 0 i32 0 i32 143 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Camera settings", "37 i32 0 i32 0 i32 107 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth", "37 i32 0 i32 0 i32 142 i32 0 i32 0", ON); //default after update, ON after master reset
        Thread.sleep(one_sec);
        verifySetting("Bluetooth Disarm", "37 i32 0 i32 0 i32 138 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Voice Prompts", "37 i32 0 i32 0 i32 42 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Sensors Prompts", " 37 i32 0 i32 0 i32 43 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Panel", "37 i32 0 i32 0 i32 44 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Activity Monitoring", "37 i32 0 i32 0 i32 45 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("ZWave voice prompts", "37 i32 0 i32 0 i32 116  i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("ZWave remote prompts", "37 i32 0 i32 0 i32 117 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Chimes", "37 i32 0 i32 0 i32 46 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Sensor Chimes", "37 i32 0 i32 0 i32 47 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Panel Chimes", "37 i32 0 i32 0 i32 48 i32 0 i32 0 ", ON);
        Thread.sleep(one_sec);
        verifySetting("Activity Sensor chimes", "37 i32 0 i32 0 i32 49 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Trouble beeps", "37 i32 0 i32 0 i32 111 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Touch Sounds", "37 i32 0 i32 0 i32 140 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("ZWave Radio", "37 i32 0 i32 0 i32 71 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Update", "37 i32 0 i32 0 i32 90 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
    }

    public void verifyAll(String state) throws InterruptedException, IOException {
        verifySetting("Power Management", "37 i32 0 i32 0 i32 73 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("SIA Power Restoration", "37 i32 0 i32 0 i32 74 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("SIA Limits", "37 i32 0 i32 0 i32 37 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("RF Jam Detect", "37 i32 0 i32 0 i32 25 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Open/Close Reports for Auto Learn", "37 i32 0 i32 0 i32 127 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Auto Upload Logs", "37 i32 0 i32 0 i32 90 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Siren Annunciation", "37 i32 0 i32 0 i32 120 i32 0 i32 0 ", state);
        Thread.sleep(one_sec);
        verifySetting("Fire Verification", "37 i32 0 i32 0 i32 100 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Severe Weather Siren Warning", "37 i32 0 i32 0 i32 103 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Water Freeze Alarm", "37 i32 0 i32 0 i32 122  i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Police Panic", "37 i32 0 i32 0 i32 131 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Fire Panic", "37 i32 0 i32 0 i32 132 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Auxillary Panic", "37 i32 0 i32 0 i32 133 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Siren and Alarms settings", "37 i32 0 i32 0 i32 105 i32 0 i32 0", state);
        Thread.sleep(one_sec);
 //       verifySetting("Duress Authentication", "37 i32 0 i32 0 i32 61 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Secure Arming", "37 i32 0 i32 0 i32 35 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("No Arming On Low Battery", "37 i32 0 i32 0 i32 36 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Auto Bypass", "37 i32 0 i32 0 i32 19 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Auto Stay", "37 i32 0 i32 0 i32 20 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Arm Stay No Delay", "37 i32 0 i32 0 i32 21 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Auto Exit Time Extension", "37 i32 0 i32 0 i32 84 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Instant Arming", "37 i32 0 i32 0 i32 22 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Alarm Disarm", "37 i32 0 i32 0 i32 129 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Disarming", "37 i32 0 i32 0 i32 134 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Security and Arming settings", "37 i32 0 i32 0 i32 106 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Secure Delete Images", "37 i32 0 i32 0 i32 104 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Disarm Photo", "37 i32 0 i32 0 i32 102 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Alarm Photos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Settings Photos", "37 i32 0 i32 0 i32 143 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Camera settings", "37 i32 0 i32 0 i32 107 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth", "37 i32 0 i32 0 i32 142 i32 0 i32 0", state); //default after update, ON after master reset
        Thread.sleep(one_sec);
        verifySetting("Bluetooth Disarm", "37 i32 0 i32 0 i32 138 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("All Voice Prompts", "37 i32 0 i32 0 i32 42 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("All Sensors Prompts", " 37 i32 0 i32 0 i32 43 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Panel", "37 i32 0 i32 0 i32 44 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Activity Monitoring", "37 i32 0 i32 0 i32 45 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("ZWave voice prompts", "37 i32 0 i32 0 i32 116  i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("ZWave remote prompts", "37 i32 0 i32 0 i32 117 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("All Chimes", "37 i32 0 i32 0 i32 46 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Sensor Chimes", "37 i32 0 i32 0 i32 47 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Panel Chimes", "37 i32 0 i32 0 i32 48 i32 0 i32 0 ", state);
        Thread.sleep(one_sec);
        verifySetting("Activity Sensor chimes", "37 i32 0 i32 0 i32 49 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Trouble beeps", "37 i32 0 i32 0 i32 111 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Touch Sounds", "37 i32 0 i32 0 i32 140 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("ZWave Radio", "37 i32 0 i32 0 i32 71 i32 0 i32 0", state);
        Thread.sleep(one_sec);
        verifySetting("Auto Update", "37 i32 0 i32 0 i32 90 i32 0 i32 0", state);
        Thread.sleep(one_sec);

    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        deleteReport();
    }

    @Test
    public void defaultSettings() throws IOException, InterruptedException {
        setDefaultSettings();
    }

    @Test
    public void Scenario1() throws IOException, InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html");
        report.loadConfig(new File(file));
        log = report.startTest("UpdateProcess.Settings");

        setAllOn();
        Thread.sleep(10000);
        verifyAll(ON);

    }

    @Test
    public void Scenario2() throws IOException, InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html");
        report.loadConfig(new File(file));
        log = report.startTest("UpdateProcess.Settings");

        setAllOff();
        Thread.sleep(10000);
        verifyAll(OFF);

    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
    }

    @AfterClass
    public void driver_quit() throws IOException, InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}
