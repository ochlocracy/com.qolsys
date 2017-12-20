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

public class KeyfobInstantArmingTest extends Setup {
    String page_name = "Auto Exit Time Extension testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String armstay = "04 01";
    private String armaway = "04 04";

    public KeyfobInstantArmingTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Keyfob_Instant_Arming_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        logger.info("Verify that Keyfob Instant Arming works when enabled");
        logger.info("Adding sensors...");
        sensors.add_primary_call(3, 4, 6619386, 102);
        logger.info("Arm Stay the system");
        Thread.sleep(3000);
        sensors.primaryCall("65 00 AF", armstay);
        Thread.sleep(5000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        sensors.primaryCall("65 00 AF", armaway);
        Thread.sleep(4000);
        verifyArmaway();
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Verify that Keyfob Instant Arming does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        logger.info("Arm Stay the system");
        sensors.primaryCall("65 00 AF", armstay);
        Thread.sleep(4000);
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                takeScreenshot();
            logger.info("Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info("Pass: System is NOT ARMED STAY");
        } finally {
        }
        Thread.sleep(10000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        sensors.primaryCall("65 00 AF", armaway);
        Thread.sleep(4000);
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            logger.info("Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info("Pass: System is NOT ARMED AWAY");
        } finally {
        }
        Thread.sleep(10000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
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
