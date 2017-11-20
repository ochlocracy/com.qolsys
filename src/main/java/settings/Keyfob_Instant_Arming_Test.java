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

public class Keyfob_Instant_Arming_Test extends Setup {

    String page_name = "Auto Exit Time Extension testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String armstay = "04 01";
    private String armaway = "04 04";

    public Keyfob_Instant_Arming_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Verify_Keyfob_Instant_Arming_works() throws Exception {
        Security_Arming_Page arming = PageFactory.initElements(driver, Security_Arming_Page.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Thread.sleep(3000);
        logger.info("Verify that Keyfob Instant Arming works when enabled");
        logger.info("Adding sensors...");
        sensors.add_primary_call(1, 4, 6619386, 102);
        logger.info("Arm Stay the system");
        Thread.sleep(3000);
        sensors.primary_call("65 00 AF", armstay);
        Thread.sleep(5000);
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        sensors.primary_call("65 00 AF", armaway);
        Thread.sleep(4000);
        verify_armaway();
        home.ArwAway_State.click();
        enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Verify that Keyfob Instant Arming does not work when disabled");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(2000);
        swipe_vertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        logger.info("Arm Stay the system");
        sensors.primary_call("65 00 AF", armstay);
        Thread.sleep(4000);
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                take_screenshot();
                logger.info("Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info("Pass: System is NOT ARMED STAY");
        } finally {
        }
        Thread.sleep(10000);
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        sensors.primary_call("65 00 AF", armaway);
        Thread.sleep(4000);
        try {
            if (home.ArwAway_State.isDisplayed())
                take_screenshot();
                logger.info("Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info("Pass: System is NOT ARMED AWAY");
        } finally {
        }
        Thread.sleep(10000);
        verify_armaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enter_default_user_code();
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(2000);
        swipe_vertical();
        arming.Keyfob_Instant_Arming.click();
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
