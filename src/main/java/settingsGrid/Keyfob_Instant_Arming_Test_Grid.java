package settingsGrid;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.Setup1;

import java.io.IOException;

public class Keyfob_Instant_Arming_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Auto Exit Time Extension testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String armstay = "04 01";
    private String armaway = "04 04";

    public Keyfob_Instant_Arming_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters ({"UDID_"})
    @Test
    public void Verify_Keyfob_Instant_Arming_works(String UDID_) throws Exception {
        Security_Arming_Page arming = PageFactory.initElements(s.getDriver(), Security_Arming_Page.class);
        Settings_Page settings = PageFactory.initElements(s.getDriver(), Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(s.getDriver(), Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(s.getDriver(), Installation_Page.class);
        Home_Page home = PageFactory.initElements(s.getDriver(), Home_Page.class);
        Thread.sleep(3000);
        logger.info("Verify that Keyfob Instant Arming works when enabled");
        logger.info("Adding sensors...");
        s.add_primary_call(1, 4, 6619386, 102, UDID_);
        logger.info("Arm Stay the system");
        Thread.sleep(3000);
        s.primary_call(UDID_,"65 00 AF", armstay);
        Thread.sleep(5000);
        s.verify_armstay(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        s.primary_call(UDID_,"65 00 AF", armaway);
        Thread.sleep(4000);
        s.verify_armaway(UDID_);
        home.ArwAway_State.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Verify that Keyfob Instant Arming does not work when disabled");
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        s.swipe_vertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        logger.info("Arm Stay the system");
        s.primary_call(UDID_,"65 00 AF", armstay);
        Thread.sleep(4000);
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                s.take_screenshot();
            logger.info(UDID_ +" Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: System is NOT ARMED STAY");
        } finally {
        }
        Thread.sleep(10000);
        s.verify_armstay(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        s.primary_call(UDID_,"65 00 AF", armaway);
        Thread.sleep(4000);
        try {
            if (home.ArwAway_State.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ +" Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: System is NOT ARMED AWAY");
        } finally {
        }
        Thread.sleep(10000);
        s.verify_armaway(UDID_);
        Thread.sleep(2000);
        home.ArwAway_State.click();
        s.enter_default_user_code();
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        s.swipe_vertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        s.delete_from_primary(UDID_,1);
        Thread.sleep(2000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}