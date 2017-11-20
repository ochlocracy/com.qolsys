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

public class Keyfob_Alarm_Disarm_Test extends Setup {

    String page_name = "Keyfob Alarm Disarm";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String disarm = "08 01";

    public Keyfob_Alarm_Disarm_Test() throws Exception {}

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }
    @Test
    public void Verify_Keyfob_Alarm_Disarm_works() throws Exception {
        Security_Arming_Page arming = PageFactory.initElements(driver, Security_Arming_Page.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emergency = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("Adding sensors...");
        sensors.add_primary_call(1, 4, 6619386, 102);
        Thread.sleep(2000);
        logger.info("Verify that Keyfod Alarm Disarm does not work when disabled");
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 AF", disarm);
        Thread.sleep(2000);
        if (emergency.Emergency_sent_text.isDisplayed()){
            logger.info("Pass: Police Emergency is displayed");
        } else { take_screenshot();
            logger.info("Failed: Police Emergency is NOT displayed");}
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Verify that Keyfod Alarm Disarm  works when enabled");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(2000);
        swipe_vertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 AF", disarm);
        Thread.sleep(2000);
        verify_disarm();
        Thread.sleep(2000);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(2000);
        swipe_vertical();
        arming.Keyfob_Alarm_Disarm.click();
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
