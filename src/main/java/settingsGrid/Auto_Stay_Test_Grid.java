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

public class Auto_Stay_Test_Grid {

    Setup1 s = new Setup1();
    String page_name = "Auto Stay testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private int delay = 15;

    public Auto_Stay_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters ({"UDID_"})
    @Test
    public void Verify_Auto_Stay_works(String UDID_) throws Exception {
        Security_Arming_Page arming = PageFactory.initElements(s.getDriver(), Security_Arming_Page.class);
        Settings_Page settings = PageFactory.initElements(s.getDriver(), Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(s.getDriver(), Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(s.getDriver(), Installation_Page.class);
        Home_Page home = PageFactory.initElements(s.getDriver(), Home_Page.class);
        logger.info("Adding sensors...");
        s.add_primary_call(1, 10, 6619296, 1, UDID_);
        Thread.sleep(2000);
        logger.info("Verify that Auto Stay works when enabled");
        Thread.sleep(3000);
        logger.info("Arm Away the system");
        s.ARM_AWAY(delay);
        s.verify_armstay(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();
        logger.info("Verify that Auto Stay does not work when disabled");
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        s.swipe_vertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Arm Away the system");
        s.ARM_AWAY(delay);
        s.verify_armaway(UDID_);
        home.ArwAway_State.click();
        s.enter_default_user_code();
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        s.swipe_vertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        s.delete_from_primary(UDID_,1);
        Thread.sleep(3000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}