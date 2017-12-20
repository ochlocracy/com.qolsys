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

public class KeyfobDisarmingTest extends Setup {
    String page_name = "Keyfob Disarming testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private int delay = 15;
    private String disarm = "08 01";

    public KeyfobDisarmingTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Keyfob_Disarming_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelInfo_ServiceCalls service = PageFactory.initElements(driver, PanelInfo_ServiceCalls.class);
        logger.info("Adding sensors...");
        service.set_AUTO_STAY(00);
        sensors.add_primary_call(3, 4, 6619386, 102);
        Thread.sleep(2000);
        logger.info("Verify that Keyfob Disarming works when enabled");
        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        logger.info("Verify that Keyfob Disarming does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyArmaway();
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        sensors.delete_from_primary(3);
        settings.Home_button.click();
        service.set_AUTO_STAY(01);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
