package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Sound_Page_Test extends Setup {

    String page_name = "Sound page";
    Logger logger = Logger.getLogger(page_name);

    public Sound_Page_Test() throws Exception {}

    public void swipe_up() throws InterruptedException {
        int starty = 260;
        int endy = 620;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Sound_page() throws Exception {
        Sound_Page sound = PageFactory.initElements(driver, Sound_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigate_to_Advanced_Settings_page();
        adv.SOUND.click();

        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        if (sound.Volume_summery.isDisplayed()) {
            logger.info("Pass: Correct Volume summery");
        }
        if (sound.Edit_Chimes_summery.isDisplayed()) {
            logger.info("Pass: Correct Edit Chimes summery");
        }
        if (sound.Voices_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Voices summery when ENABLED");
        }
        sound.Voices.click();
        if (sound.Voices_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Voices summery when DISABLED");
        }
        sound.Voices.click();
        if (sound.Sensors_voice_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct sensors voice summery when ENABLED");
        }
        sound.Sensors_Voice.click();
        if (sound.Sensors_voice_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct sensors voice summery when DISABLED");
        }
        sound.Sensors_Voice.click();
        if (sound.Panel_voice_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct panel voice summery when ENABLED");
        }
        sound.Panel_Voice.click();
        if (sound.Panel_voice_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct panel voice summery when DISABLED");
        }
        sound.Panel_Voice.click();
        swipe_vertical();
        Thread.sleep(1000);
        if (sound.Activity_Monitoring_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Monitoring summery when ENABLED");
        }
        sound.Activity_Monitoring.click();
        if (sound.Activity_Monitoring_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Monitoring summery when DISABLED");
        }
        sound.Activity_Monitoring.click();
        if (sound.ZWave_devices_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Device Voice Prompts summery when ENABLED");
        }
        sound.ZWave_Device_Voice_Prompts.click();
        if (sound.ZWave_devices_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Device Voice Prompts summery when DISABLED");
        }
        sound.ZWave_Device_Voice_Prompts.click();
        if (sound.ZWave_remote_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Remote Voice Prompts summery when ENABLED");
        }
        sound.ZWave_Remote_Voice_Prompts.click();
        if (sound.ZWave_remote_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Remote Voice Prompts summery when DISABLED");
        }
        sound.ZWave_Remote_Voice_Prompts.click();
        swipe_vertical();
        Thread.sleep(1000);
        if (sound.All_chimes_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct All Chimes summery when ENABLED");
        }
//        sound.All_Chimes.click();
//        if (sound.All_chimes_summery_disabled.isDisplayed()) {
//            logger.info("Pass: Correct All Chimes summery when DISABLED");
//        }
        sound.All_Chimes.click();
        if (sound.Sensor_chime_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Sensor Chime summery when ENABLED");
        }
        sound.Sensor_Chimes.click();
        if (sound.Sensor_chime_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Sensor Chime summery when DISABLED");
        }
        sound.Sensor_Chimes.click();
        if (sound.Panel_chime_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct panel Chime summery when ENABLED");
        }
        sound.Panel_Chimes.click();
        if (sound.Panel_chime_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct panel Chime summery when DISABLED");
        }
        sound.Panel_Chimes.click();
        if (sound.Activity_Sensor_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Sensor summery when ENABLED");
        }
        sound.Activity_Sensor.click();
        if (sound.Activity_Sensor_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Sensor summery when DISABLED");
        }
        sound.Activity_Sensor.click();
        swipe_vertical();
        Thread.sleep(1000);
        if (sound.Trouble_beeps_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Trouble Beeps summery when DISABLED");
        }
        sound.Trouble_Beeps.click();
        if (sound.Trouble_beeps_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Trouble Beeps summery when ENABLED");
        }
        if (sound.Sensor_Low_Battery_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Low Battery summery when ENABLED");
        }
        sound.Sensor_Low_Battery.click();
        if (sound.Sensor_Low_Battery_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Low Battery summery when DISABLED");
        }
        sound.Sensor_Low_Battery.click();
        if (sound.Sensor_Tamper_Beeps_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Tamper Beeps summery when ENABLED");
        }
        sound.Sensor_Tamper_Beeps.click();
        if (sound.Sensor_Tamper_Beeps_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Tamper Beeps summery when DISABLED");
        }
        sound.Sensor_Tamper_Beeps.click();
        swipe_vertical();
        Thread.sleep(1000);
        if (sound.Panel_Tamper_Beeps_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct panel Tamper Beeps summery when ENABLED");
        }
        sound.Panel_Tamper_Beeps.click();
        if (sound.Panel_Tamper_Beeps_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct panel Tamper Beeps summery when DISABLED");
        }
        sound.Panel_Tamper_Beeps.click();
        if (sound.Edit_Trouble_Beep_Chimes_summery.isDisplayed()) {
            logger.info("Pass: Correct Edit Trouble Beep Chimes summery");
        }
        if (sound.Trouble_Beeps_Timeout_summery.isDisplayed()) {
            logger.info("Pass: Correct Edit Trouble Beep Chimes summery");
        }
        if (sound.Fire_Safety_Device_Trouble_Beeps_summery_disabled.isDisplayed()) {
            logger.info("Pass: Correct Fire Safety Device Trouble Beeps summery when ENABLED");
        }
        sound.Fire_Safety_Device_Trouble_Beeps.click();
        if (sound.Fire_Safety_Device_Trouble_Beeps_summery_enabled.isDisplayed()) {
            logger.info("Pass: Correct Fire Safety Device Trouble Beeps summery when DISABLED");
        }
        sound.Fire_Safety_Device_Trouble_Beeps.click();
        swipe_vertical();
        Thread.sleep(1000);
        if (sound.Touch_Sounds.isDisplayed()) {
            logger.info("Pass: Touch Sounds is present");
        }
        swipe_up();
        sound.Trouble_Beeps.click();
    }
    
    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}