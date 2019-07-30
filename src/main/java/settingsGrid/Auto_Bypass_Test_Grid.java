package settingsGrid;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.ConfigProps;
import utils.Setup1;

import java.io.IOException;

public class Auto_Bypass_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Auto Bypass testing";
    Logger logger = Logger.getLogger(page_name);
    private String open = "06 00";
    private String close = "04 00";
    public Runtime rt = Runtime.getRuntime();

    public Auto_Bypass_Test_Grid() throws Exception {}
    public void SCGrid_Set_Auto_Bypass(String UDID_, int state) throws IOException, InterruptedException {
        rt.exec("adb -s " + UDID_ +  " shell service call qservice 40 i32 0 i32 0 i32 19 i32 " + state + " i32 0 i32 0");
        System.out.println(ConfigProps.adbPath + " -s " + UDID_ +  " shell service call qservice 40 i32 0 i32 0 i32 19 i32 " + state + " i32 0 i32 0" + " Auto Bypass Enabled");
    }
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }

    @Parameters ({"UDID_"})
    @Test
    public void Verify_Auto_Bypass_works(String UDID_) throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(s.getDriver(), SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
        HomePage home = PageFactory.initElements(s.getDriver(), HomePage.class);
        Thread.sleep(2000);
        //check for auto bypass enabled (this "State" change to on is not actually sending the "on" service call to each panel. Needs rework.
        SCGrid_Set_Auto_Bypass(UDID_, 1 );
        logger.info("Adding sensors...");
        s.add_primary_call(1,10,6619296,1, UDID_);
        Thread.sleep(2000);
        logger.info("Verify that Auto Bypass works when enabled");
        s.primary_call(UDID_,"65 00 0A",open);
        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(3000);
        logger.info("Opening/closing bypassed sensor");
        s.primary_call(UDID_,"65 00 0A",close);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",open);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",close);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",open);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",close);
        Thread.sleep(2000);
        s.verify_armstay(UDID_); //fix verify.
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(3000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(3000);
        settings.Home_button.click();
        Thread.sleep(3000);
        logger.info("Verify that Auto Bypass does not work when disabled");
        s.primary_call(UDID_,"65 00 0A",open);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        s.element_verification(UDID_, home.Bypass_message,"Bypass pop-up message");
        Thread.sleep(2000);
        //home.Bypass_OK.click(); failing
        logger.info("Opening/closing bypassed sensor");
        s.primary_call(UDID_,"65 00 0A",close);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",open);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",close);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",open);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 00 0A",close);
        Thread.sleep(1000);
        s.verify_armstay(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        s.delete_from_primary(UDID_,1);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}