package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class SoundPageTest extends Setup {

    String page_name = "Sound page";
    Logger logger = Logger.getLogger(page_name);

    public SoundPageTest() throws Exception {
    }

    public void swipeUp() throws InterruptedException {
        int starty = 260;
        int endy = 620;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Sound_page() throws Exception {
        SoundPage sound = PageFactory.initElements(driver, SoundPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigateToAdvancedSettingsPage();
        adv.SOUND.click();

        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        if (sound.Volume_Summary.isDisplayed()) {
            logger.info("Pass: Correct Volume Summary");
        }
        if (sound.Edit_Chimes_Summary.isDisplayed()) {
            logger.info("Pass: Correct Edit Chimes Summary");
        }
        if (sound.Voices_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Voices Summary when Enabled");
        }
        sound.Voices.click();
        if (sound.Voices_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Voices Summary when Disabled");
        }
        sound.Voices.click();
        if (sound.Sensors_voice_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct sensors voice Summary when Enabled");
        }
        sound.Sensors_Voice.click();
        if (sound.Sensors_voice_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct sensors voice Summary when Disabled");
        }
        sound.Sensors_Voice.click();
        if (sound.Panel_voice_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct panel voice Summary when Enabled");
        }
        sound.Panel_Voice.click();
        if (sound.Panel_voice_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct panel voice Summary when Disabled");
        }
        sound.Panel_Voice.click();
        swipeVertical();
        Thread.sleep(1000);
        if (sound.Activity_Monitoring_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Monitoring Summary when Enabled");
        }
        sound.Activity_Monitoring.click();
        if (sound.Activity_Monitoring_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Monitoring Summary when Disabled");
        }
        sound.Activity_Monitoring.click();
        if (sound.ZWave_devices_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Device Voice Prompts Summary when Enabled");
        }
        sound.ZWave_Device_Voice_Prompts.click();
        if (sound.ZWave_devices_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Device Voice Prompts Summary when Disabled");
        }
        sound.ZWave_Device_Voice_Prompts.click();
        if (sound.ZWave_remote_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Remote Voice Prompts Summary when Enabled");
        }
        sound.ZWave_Remote_Voice_Prompts.click();
        if (sound.ZWave_remote_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Z-Wave Remote Voice Prompts Summary when Disabled");
        }
        sound.ZWave_Remote_Voice_Prompts.click();
        swipeVertical();
        Thread.sleep(1000);
//        if (sound.All_chimes_Summary_Enabled.isDisplayed()) {
//            logger.info("Pass: Correct All Chimes Summary when Enabled");
//        }
 //       sound.All_Chimes.click();
        if (sound.All_chimes_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct All Chimes Summary when Disabled");
        }
        sound.All_Chimes.click();
        if (sound.Sensor_chime_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Sensor Chime Summary when Enabled");
        }
        sound.Sensor_Chimes.click();
        if (sound.Sensor_chime_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Sensor Chime Summary when Disabled");
        }
        sound.Sensor_Chimes.click();
        if (sound.Panel_chime_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct panel Chime Summary when Enabled");
        }
        sound.Panel_Chimes.click();
        if (sound.Panel_chime_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct panel Chime Summary when Disabled");
        }
        sound.Panel_Chimes.click();
        if (sound.Activity_Sensor_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Sensor Summary when Enabled");
        }
        sound.Activity_Sensor.click();
        if (sound.Activity_Sensor_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Activity Sensor Summary when Disabled");
        }
        sound.Activity_Sensor.click();
        swipeVertical();
        Thread.sleep(1000);
        if (sound.Trouble_beeps_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Trouble Beeps Summary when Disabled");
        }
        sound.Trouble_Beeps.click();
        if (sound.Trouble_beeps_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Trouble Beeps Summary when Enabled");
        }
        if (sound.Sensor_Low_Battery_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Low Battery Summary when Enabled");
        }
        sound.Sensor_Low_Battery.click();
        if (sound.Sensor_Low_Battery_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Low Battery Summary when Disabled");
        }
        sound.Sensor_Low_Battery.click();
        if (sound.Sensor_Tamper_Beeps_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Tamper Beeps Summary when Enabled");
        }
        sound.Sensor_Tamper_Beeps.click();
        if (sound.Sensor_Tamper_Beeps_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct sensors Tamper Beeps Summary when Disabled");
        }
        sound.Sensor_Tamper_Beeps.click();
        swipeVertical();
        Thread.sleep(1000);
        if (sound.Panel_Tamper_Beeps_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct panel Tamper Beeps Summary when Enabled");
        }
        sound.Panel_Tamper_Beeps.click();
        if (sound.Panel_Tamper_Beeps_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct panel Tamper Beeps Summary when Disabled");
        }
        sound.Panel_Tamper_Beeps.click();
        if (sound.Edit_Trouble_Beep_Chimes_Summary.isDisplayed()) {
            logger.info("Pass: Correct Edit Trouble Beep Chimes Summary");
        }
        if (sound.Trouble_Beeps_Timeout_Summary.isDisplayed()) {
            logger.info("Pass: Correct Edit Trouble Beep Chimes Summary");
        }
        if (sound.Fire_Safety_Device_Trouble_Beeps_Summary_Disabled.isDisplayed()) {
            logger.info("Pass: Correct Fire Safety Device Trouble Beeps Summary when Enabled");
        }
        sound.Fire_Safety_Device_Trouble_Beeps.click();
        if (sound.Fire_Safety_Device_Trouble_Beeps_Summary_Enabled.isDisplayed()) {
            logger.info("Pass: Correct Fire Safety Device Trouble Beeps Summary when Disabled");
        }
        sound.Fire_Safety_Device_Trouble_Beeps.click();
        swipeVertical();
        Thread.sleep(1000);
        if (sound.Touch_Sounds.isDisplayed()) {
            logger.info("Pass: Touch Sounds is present");
        }
        swipeUp();
        sound.Trouble_Beeps.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}