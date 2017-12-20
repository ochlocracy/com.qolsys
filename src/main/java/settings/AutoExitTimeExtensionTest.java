package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class AutoExitTimeExtensionTest extends Setup {
    String page_name = "Auto Exit Time Extension testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();

    public AutoExitTimeExtensionTest() throws Exception {
        SensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Exit_Time_Extension_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        logger.info("Verify that Auto Exit Time Extension works when enabled");
        logger.info("Adding sensors...");
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        ARM_AWAY(3);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            logger.info("Failed: System is ARMED AWAY");
        } catch (Exception e) {
            logger.info("Pass: System is NOT ARMED AWAY");
        } finally {
        }
        Thread.sleep(60000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Verify that Auto Exit Time Extension does not works when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_AWAY(3);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(10000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
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
