package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup;

import java.io.IOException;

public class ArmStayNoDelayTest extends Setup {

    String page_name = "Arm Stay No Delay testing";
    Logger logger = Logger.getLogger(page_name);

    public ArmStayNoDelayTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Verify_Arm_Stay_No_Delay_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        logger.info("Verify that Arm Stay - No Delay works when enabled");
        ARM_STAY();
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(2000);
        logger.info("Verify that Arm Stay - No Delay does not work when disabled");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipe_vertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        ARM_STAY();
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                take_screenshot();
            logger.info("Failed: System is ARMED STAY");
        } catch (Exception e) {
            logger.info("Pass: System is NOT ARMED STAY");
        } finally {
        }
        Thread.sleep(15000);
        verify_armstay();
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(2000);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipe_vertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}