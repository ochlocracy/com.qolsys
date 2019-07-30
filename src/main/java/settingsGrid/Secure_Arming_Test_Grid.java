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

public class Secure_Arming_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Secure Arming testing";
    Logger logger = Logger.getLogger(page_name);

    public Secure_Arming_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Secure_Arming_works(String UDID_) throws Exception {
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(s.getDriver(), SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
        HomePage home = PageFactory.initElements(s.getDriver(), HomePage.class);
        Thread.sleep(2000);
        logger.info("Verify no code is required to Arm the system when setting is disabled");
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        s.verify_armstay(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        arming.Secure_Arming.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        logger.info("Verify code is required to Arm the system when setting is enabled");
        logger.info("Arm Stay the system");
        s.ARM_STAY();
        if(home.Enter_Code_to_Access_the_Area.isDisplayed()){
            logger.info(UDID_ +" Pass: code is requires to Arm the system");
        }
        s.enter_default_user_code();
        Thread.sleep(2000);
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        home.DISARM.click();
        home.ARM_AWAY.click();
        if(home.Enter_Code_to_Access_the_Area.isDisplayed()){
            logger.info(UDID_ +" Pass: code is requires to Arm the system");
        }
        s.enter_default_user_code();
        Thread.sleep(15000);
        home.ArwAway_State.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        arming.Secure_Arming.click();
        Thread.sleep(2000);
        s.swipe_vertical();
        Thread.sleep(2000);
        arming.Auto_Stay.click();
        Thread.sleep(2000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}