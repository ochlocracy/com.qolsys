package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class KeyfobInstantArmingTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Keyfob_Instant_Arm");
    Sensors sensors = new Sensors();
    private String armstay = "04 01";
    private String armaway = "04 04";

    public KeyfobInstantArmingTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Keyfob_Instant_Arming_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        rep.create_report("Keyfob_Inst_Arm_01");
        rep.log.log(LogStatus.INFO, ("*Keyfob_Inst_Arm_01* Keyfob Instant Arming enabled -> Expected result = Keyfob Arm system with no delay"));
        Thread.sleep(2000);
        logger.info("Verify that Keyfob Instant Arming works when enabled");
        logger.info("Adding sensors...");
        sensors.add_primary_call(3, 4, 6619386, 102);
        logger.info("Arm Stay the system");
        Thread.sleep(3000);
        sensors.primaryCall("65 00 AF", armstay);
        Thread.sleep(5000);
        verifyArmstay();
        if (home.ARM_STAY_text.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Keyfob Instant Arm Stay works"));
        }
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        sensors.primaryCall("65 00 AF", armaway);
        Thread.sleep(4000);
        verifyArmaway();
        if (home.ARM_AWAY_text.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Keyfob Instant Arm Away works"));
        }
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        rep.create_report("Keyfob_Inst_Arm_02");
        rep.log.log(LogStatus.INFO, ("*Keyfob_Inst_Arm_02* Keyfob Instant Arming disabled -> Expected result = Keyfob should Arm system with delay"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", armstay);
        Thread.sleep(1000);
        try {
            if (home.ARM_STAY_text.isDisplayed())
                rep.log.log(LogStatus.FAIL, ("Failed: System is ARMED STAY without delay"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: System is NOT ARMED STAY yet"));
        }
        Thread.sleep(10000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("Arm Away the system");
        sensors.primaryCall("65 00 AF", armaway);
        Thread.sleep(1000);
        try {
            if (home.ARM_AWAY_text.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY without delay"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: System is NOT ARMED AWAY yet"));
        }
        Thread.sleep(10000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
