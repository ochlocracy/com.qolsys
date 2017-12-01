package settings;

import panel.*;
import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Auto_Stay_Test extends Setup {

    String page_name = "Auto Stay testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private int delay = 15;

    public Auto_Stay_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }
    @Test
    public void Verify_Auto_Stay_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Adding sensors...");
        sensors.add_primary_call(1, 10, 6619296, 1);
        Thread.sleep(2000);
        logger.info("Verify that Auto Stay works when enabled");
        Thread.sleep(3000);
        logger.info("Arm Away the system");
        ARM_AWAY(delay);
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        logger.info("Verify that Auto Stay does not works when disabled");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipe_vertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Arm Away the system");
        ARM_AWAY(delay);
        verify_armaway();
        home.ArwAway_State.click();
        enter_default_user_code();
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipe_vertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(1);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
