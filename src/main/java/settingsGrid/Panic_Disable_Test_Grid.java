package settingsGrid;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup1;

import java.io.IOException;

public class Panic_Disable_Test_Grid {

    Setup1 s = new Setup1();
    String page_name = "Panic Disable testing";
    Logger logger = Logger.getLogger(page_name);

    public Panic_Disable_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Keyfob_Alarm_Disarm_works(String UDID_) throws Exception {
        Siren_Alarms_Page siren = PageFactory.initElements(s.getDriver(), Siren_Alarms_Page.class);
        Settings_Page settings = PageFactory.initElements(s.getDriver(), Settings_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
        EmergencyPage emergency = PageFactory.initElements(s.getDriver(), EmergencyPage.class);
        Thread.sleep(1000);
        logger.info("Verify panic disappears from the Emergency page when disabled");
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        siren.Police_Panic.click();
        Thread.sleep(2000);
        settings.Emergency_button.click();
        try {
            if (emergency.Police_icon.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ +" Failed: Police Emergency is displayed");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: Police Emergency is NOT displayed");
        } finally {
        }
        s.swipeFromLefttoRight();
        Thread.sleep(2000);
        s.navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Fire_icon.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ +" Failed: Fire Emergency is displayed");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: Fire Emergency is NOT displayed");
        } finally {
        }
        s.swipeFromLefttoRight();
        Thread.sleep(2000);
        s.navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        siren.Auxiliary_Panic.click();
        Thread.sleep(2000);
        settings.Emergency_button.click();
        try {
            if (emergency.Auxiliary_icon.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ +" Failed: Auxiliary Emergency is displayed");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: Auxiliary Emergency is NOT displayed");
        } finally {
        }
        s.swipeFromLefttoRight();
        Thread.sleep(2000);
        s.navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        siren.Police_Panic.click();
        Thread.sleep(2000);
        siren.Fire_Panic.click();
        Thread.sleep(2000);
        siren.Auxiliary_Panic.click();
        Thread.sleep(2000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}