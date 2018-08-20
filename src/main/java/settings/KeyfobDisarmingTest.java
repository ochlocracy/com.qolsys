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

public class KeyfobDisarmingTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Keyfob_Arm_Disarm");
    Sensors sensors = new Sensors();
    private int delay = 15;
    private String disarm = "08 01";

    public KeyfobDisarmingTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Keyfob_Disarming_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelInfo_ServiceCalls service = PageFactory.initElements(driver, PanelInfo_ServiceCalls.class);

        rep.create_report("Keyfob_Arm_Dis_01");
        rep.log.log(LogStatus.INFO, ("*Keyfob_Arm_Dis_01* Keyfob Disarm setting enabled -> Expected result = Keyfob will disarm system"));
        Thread.sleep(2000);
        service.set_AUTO_STAY(00);
        sensors.add_primary_call(3, 4, 6619386, 102);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        if (home.Disarmed_text.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Keyfob disarm works"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Keyfob disarm did not worked"));
        }
        Thread.sleep(2000);
        rep.create_report("Keyfob_Arm_Dis_02");
        rep.log.log(LogStatus.INFO, ("*Keyfob_Arm_Dis_02* Keyfob Disarm setting disabled -> Expected result = Keyfob will not disarm system"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        if (home.ArwAway_State.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Keyfob did not disarm"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Keyfob disarmed the system"));
        }
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        sensors.delete_from_primary(3);
        settings.Home_button.click();
        service.set_AUTO_STAY(01);
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
