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

public class Keyfob_Alarm_Disarm_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Keyfob Alarm Disarm";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String disarm = "08 01";

    public Keyfob_Alarm_Disarm_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters ({"UDID_"})
    @Test
    public void Verify_Keyfob_Alarm_Disarm_works(String UDID_) throws Exception {
            SecurityArmingPage arming = PageFactory.initElements(s.getDriver(), SecurityArmingPage.class);
            ContactUs contact = PageFactory.initElements(s.getDriver(), ContactUs.class);
            SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
            AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
            InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
            HomePage home = PageFactory.initElements(s.getDriver(), HomePage.class);
            EmergencyPage emergency = PageFactory.initElements(s.getDriver(), EmergencyPage.class);
            logger.info("Adding sensors...");
            s.add_primary_call(1, 4, 6619386, 102, UDID_);
            Thread.sleep(2000);
            logger.info("Verify that Keyfod Alarm Disarm does not work when disabled");
            home.Emergency_Button.click();
            Thread.sleep(2000);
            emergency.Police_icon.click();
            Thread.sleep(4000);
            s.primary_call(UDID_,"65 00 AF", disarm);
            Thread.sleep(4000);
            if (emergency.Emergency_sent_text.isDisplayed()){
                logger.info(UDID_ +" Pass: Police Emergency is displayed");
            } else { s.take_screenshot();
                logger.info(UDID_ +" Failed: Police Emergency is NOT displayed");}
            s.enter_default_user_code();
            Thread.sleep(2000);
            logger.info("Verify that Keyfod Alarm Disarm  works when enabled");
            s.navigate_to_Advanced_Settings_page();
            adv.INSTALLATION.click();
            Thread.sleep(1000);
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            s.swipe_vertical();
            Thread.sleep(2000);
            s.swipe_vertical();
            arming.Keyfob_Alarm_Disarm.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            Thread.sleep(2000);
            home.Emergency_Button.click();
            emergency.Police_icon.click();
            Thread.sleep(2000);
            s.primary_call(UDID_,"65 00 AF", disarm);
            Thread.sleep(2000);
            s.verify_disarm(UDID_);
            Thread.sleep(2000);
            s.navigate_to_Advanced_Settings_page();
            adv.INSTALLATION.click();
            Thread.sleep(1000);
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            s.swipe_vertical();
            Thread.sleep(2000);
            s.swipe_vertical();
            arming.Keyfob_Alarm_Disarm.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            s.delete_from_primary(UDID_,1);
            Thread.sleep(2000);
            contact.acknowledge_all_alerts();
        }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
    }