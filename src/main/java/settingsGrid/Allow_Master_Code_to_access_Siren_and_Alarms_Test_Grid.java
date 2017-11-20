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

public class Allow_Master_Code_to_access_Siren_and_Alarms_Test_Grid {

    Setup1 s = new Setup1();
    String page_name = "Allow Master Code to access Siren and Alarms testing";
    Logger logger = Logger.getLogger(page_name);

    public Allow_Master_Code_to_access_Siren_and_Alarms_Test_Grid() throws Exception {
    }

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Master_Code_gets_access_to_Siren_and_Alarms_page(String UDID_) throws Exception {
        Siren_Alarms_Page siren = PageFactory.initElements(s.getDriver(), Siren_Alarms_Page.class);
        Settings_Page settings = PageFactory.initElements(s.getDriver(), Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(s.getDriver(), Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(s.getDriver(), Installation_Page.class);
        Thread.sleep(3000);
        logger.info("Navigate to the setting page to enable the access to the Siren and Alarms page using Master Code");
        s.navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        logger.info("Navigate to the Advanced setting page to check Siren and Alarms icon");
        s.navigate_to_Settings_page();
        Thread.sleep(2000);
        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
        s.enter_default_user_code();
        Thread.sleep(3000);
        if (inst.SIREN_AND_ALARMS.isDisplayed()){
            logger.info(UDID_ + " Pass: Siren and Alarms icon is present");
        }else {  s.take_screenshot();
            logger.info(UDID_ + " Failed: Siren and Alarms icon is NOT present");}
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Verify Siren and Alarms icon disappears after disabling the setting");
        s.navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Navigate to the Advanced setting page to check Siren and Alarms icon");
        s.navigate_to_Settings_page();
        Thread.sleep(2000);
        settings.ADVANCED_SETTINGS.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ + " Failed: Siren and Alarms icon is present");
        } catch(Exception e){
            logger.info(UDID_ + " Pass: Siren and Alarms icon is NOT present");
        } finally{}
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}