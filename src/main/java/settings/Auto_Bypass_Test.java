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

public class Auto_Bypass_Test extends Setup {

    String page_name = "Auto Bypass testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();
    private String open = "06 00";
    private String close = "04 00";

    public Auto_Bypass_Test() throws Exception {}

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }
    @Test
    public void Verify_Auto_Bypass_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Adding sensors...");
        serv.set_ARM_STAY_NO_DELAY_enable();
        sensors.add_primary_call(1,10,6619296,1);
        Thread.sleep(2000);
        logger.info("Verify that Auto Bypass works when enabled");
        sensors.primary_call("65 00 0A",open);
        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(3000);
        sensors.primary_call("65 00 0A",close);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",open);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",close);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",open);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",close);
        Thread.sleep(1000);
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(3000);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(3000);
        settings.Home_button.click();
        Thread.sleep(3000);
        logger.info("Verify that Auto Bypass does not work when disabled");
        sensors.primary_call("65 00 0A",open);
        home.DISARM.click();
        Thread.sleep(2000);
        home.ARM_STAY.click();
        Thread.sleep(2000);
        element_verification(home.Bypass_message,"Bypass pop-up message");
        Thread.sleep(2000);
        home.Bypass_OK.click();
        sensors.primary_call("65 00 0A",close);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",open);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",close);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",open);
        Thread.sleep(1000);
        sensors.primary_call("65 00 0A",close);
        Thread.sleep(1000);
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(1000);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        sensors.delete_from_primary(1);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}