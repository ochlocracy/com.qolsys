package updateProcess;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;

import java.io.IOException;

public class PreUpdateSettings extends Setup {
    String page_name = "Pre-updateProcess panel settings";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public PreUpdateSettings() throws Exception {}

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void invertSettings() throws IOException, InterruptedException {
        int ON = 1;
        int OFF = 0;
        int one_sec = 1000;

        logger.info("SIA Limits: Disabled");
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(one_sec);
        //sets media volume to 1
        logger.info("Media Volume: 1/7");
        servcall.set_SPEAKER_VOLUME(ON);
        Thread.sleep(one_sec);
        logger.info("Voice Prompts: Disabled");
        servcall.set_ALL_VOICE_PROMPTS(OFF);
        Thread.sleep(one_sec);
        logger.info("All Chimes: Enabled");
        servcall.set_ALL_CHIMES(ON);
        Thread.sleep(one_sec);
        logger.info("Trouble Beeps: Enabled");
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(ON);
        Thread.sleep(one_sec);
        logger.info("Fire Safety Device Trouble Beeps: Enabled");
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(ON);
        Thread.sleep(one_sec);
        //logger.info("Touch Sound: Disabled");
        //servcall.set_TOUCH_SOUND(false);
        //Thread.sleep(one_sec);
        logger.info("Wifi: Enabled");
        servcall.set_WiFi(ON);
        Thread.sleep(one_sec);
        logger.info("Secure Delete Images: Disabled");
        servcall.set_SECURE_DELETE_IMAGES(OFF);
        Thread.sleep(one_sec);
        logger.info("Disarm Photos: Disabled");
        servcall.set_DISARM_PHOTO(OFF);
        Thread.sleep(one_sec);
        logger.info("Alarm Videos: Enabled");
        servcall.set_ALARM_VIDEOS(OFF);
        Thread.sleep(one_sec);
        logger.info("Alarm Photos: Disabled");
        servcall.set_ALARM_PHOTOS(OFF);
        Thread.sleep(one_sec);
        logger.info("Settings Photos: Enabled");
        servcall.set_SETTINGS_PHOTOS(ON);
        Thread.sleep(one_sec);
        logger.info("Duress Authentication: Enabled");
        servcall.set_DURESS_AUTHENTICATION_enable();
        Thread.sleep(one_sec);
        logger.info("Secure Arming: Enabled");
        servcall.set_SECURE_ARMING_enable();
        Thread.sleep(one_sec);
        logger.info("No Arming On Low Battery: Enabled");
        servcall.set_NO_ARMING_ON_LOW_BATTERY_enable();
        Thread.sleep(one_sec);
        logger.info("Auto Bypass: Disabled");
        servcall.set_AUTO_BYPASS(OFF);
        Thread.sleep(one_sec);
        logger.info("Auto Stay: Disabled");
        servcall.set_AUTO_STAY(OFF);
        Thread.sleep(one_sec);
        logger.info("Arm Stay No Delay: Disabled");
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(one_sec);
        logger.info("Auto Exit Time Extension: Disabled");
        servcall.set_AUTO_EXIT_TIME_EXTENSION(OFF);
        Thread.sleep(one_sec);
        logger.info("Keyfob Alarm Disarm: Enabled");
        servcall.set_KEYFOB_ALARM_DISARM(ON);
        Thread.sleep(one_sec);
        logger.info("Keyfob Disarming: Disabled");
        servcall.set_KEYFOB_DISARMING(OFF);
        Thread.sleep(one_sec);
        logger.info("Normal Entry Delay: 33 seconds");
        servcall.set_NORMAL_ENTRY_DELAY(33);
        Thread.sleep(one_sec);
        logger.info("Normal Exit Delay: 57 seconds");
        servcall.set_NORMAL_EXIT_DELAY(57);
        Thread.sleep(one_sec);
        logger.info("Long Entry Delay: 113 seconds");
        servcall.set_LONG_ENTRY_DELAY(113);
        Thread.sleep(one_sec);
        logger.info("Long Exit Delay: 117 seconds");
        servcall.set_LONG_EXIT_DELAY(117);
        Thread.sleep(one_sec);
        logger.info("Siren Disable: Disabled");
        servcall.set_SIREN_DISABLE(OFF);
        Thread.sleep(one_sec);
        logger.info("Fire Verification: Enabled");
        servcall.set_FIRE_VERIFICATION(ON);
        Thread.sleep(one_sec);
        logger.info("Severe Weather Siren Warning: Disabled");
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(OFF);
        Thread.sleep(one_sec);
        logger.info("Dialer Delay: 23 seconds");
        servcall.set_DIALER_DELAY(23);
        Thread.sleep(one_sec);
        logger.info("Siren Timeout: 7 minutes");
        servcall.set_SIREN_TIMEOUT(420);
        Thread.sleep(one_sec);
        logger.info("Water Freeze Alarm: Enabled");
        servcall.set_WATER_FREEZE_ALARM_enable();
        Thread.sleep(one_sec);
        logger.info("Police Panic: Disabled");
        servcall.set_POLICE_PANIC(OFF);
        Thread.sleep(one_sec);
        logger.info("Fire Panic: Disabled");
        servcall.set_FIRE_PANIC(OFF);
        Thread.sleep(one_sec);
        logger.info("Auxiliary Panic: Disabled");
        servcall.set_AUXILIARY_PANIC(OFF);
        Thread.sleep(one_sec);
        logger.info("Auto Upload Logs: Enabled");
        servcall.set_AUTO_UPLOAD_LOGS(ON);
        Thread.sleep(one_sec);
        //The following setting causes Refuse Arming When Battery Low to be inaccessible
        logger.info("Power Management: Disabled");
        servcall.set_POWER_MANAGEMENT_ON_OFF_disable();
        Thread.sleep(one_sec);
        logger.info("SIA Power Restoration: Enabled");
        servcall.set_SIA_POWER_RESTORATION_enable();
        Thread.sleep(one_sec);
        logger.info("Loss of Supervisory Signals for Non-emergency sensors: 12 hours");
        servcall.set_LOSS_OF_SUPERVISORY_TIMEOUTY_12h();
        Thread.sleep(one_sec);
        logger.info("Loss of Supervisory Signals for Emergency sensors: 12 hours");
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT();
        Thread.sleep(one_sec);
        logger.info("Cell Signal Timeout: 25 seconds");
        servcall.set_Cell_Signal_Timeout(25);
        Thread.sleep(one_sec);
        logger.info("RF Jam Detection: Enabled");
        servcall.set_RF_JAM_DETECT_enable();
        Thread.sleep(one_sec);
        logger.info("Open/Close Reports for Auto Learn: Disabled");
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(OFF);
        Thread.sleep(one_sec);
        logger.info("Bluetooth: Enabled");
        servcall.set_BLUETOOTH(ON);
        Thread.sleep(one_sec);
        logger.info("Bluetooth Disarm: Enabled");
        servcall.set_BLUETOOTH_DISARM(ON);
        Thread.sleep(one_sec);
        logger.info("Bluetooth Disarm Timeout: 30 seconds");
        servcall.set_BLUETOOTH_DISARM_TIMEOUT(30);
        Thread.sleep(one_sec);
        logger.info("Allow Master Code to Access Camera settings: Enabled");
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(ON);
        Thread.sleep(one_sec);
        logger.info("Allow Master Code to Access Security and Arming settings: Enabled");
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(ON);
        Thread.sleep(one_sec);
        logger.info("Allow Master Code to Access Siren and Alarms settings: Enabled");
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(ON);
        Thread.sleep(one_sec);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }
}
