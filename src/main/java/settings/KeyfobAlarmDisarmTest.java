package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.Setup;

import java.io.IOException;

public class KeyfobAlarmDisarmTest extends Setup {
    String page_name = "Keyfob Alarm Disarm";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String disarm = "08 01";

    public KeyfobAlarmDisarmTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Keyfob_Alarm_Disarm_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("Adding sensors...");
        sensors.add_primary_call(3, 4, 6619386, 102);
        Thread.sleep(2000);
        logger.info("Verify that Keyfod Alarm Disarm does not work when disabled");
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        if (emergency.Emergency_sent_text.isDisplayed()) {
            logger.info("Pass: Police Emergency is displayed");
        } else {
            takeScreenshot();
            logger.info("Failed: Police Emergency is NOT displayed");
        }
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Verify that Keyfod Alarm Disarm  works when enabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
