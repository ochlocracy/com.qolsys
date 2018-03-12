package updateProcess;


import panel.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class SettingsUpdate extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public SettingsUpdate() throws Exception {
        ConfigProps.init();
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
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT(); //4 hours
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
        servcall.set_SIREN_ANNUNCIATION(false);
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




}
