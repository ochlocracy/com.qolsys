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
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class AutoExitTimeExtensionTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Auto_Exit_Time_Extension");

    Sensors sensors = new Sensors();

    public AutoExitTimeExtensionTest() throws Exception {
        SensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Exit_Time_Extension_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        rep.create_report("Auto_Exit_Time_Extension_01");
        rep.log.log(LogStatus.INFO, ("*Auto_Exit_Time_Extension_01* Enable Auto Exit Time Extension -> Expected result = System extends timer 'til armed when sensor is opened"));
        Thread.sleep(2000);
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        ARM_AWAY(3);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: System is NOT ARMED AWAY"));
        } finally {
        }
        Thread.sleep(60000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        rep.create_report("Auto_Exit_Time_Extension_02");
        rep.log.log(LogStatus.INFO, ("*Auto_Exit_Time_Extension_02* Enable Auto Exit Time Extension -> Expected result = System does not extend timer 'til armed when sensor is opened"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_AWAY(3);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(10000);
        verifyArmaway();
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.PASS, ("Pass: System is ARMED AWAY"));
        } catch (Exception e) {
            rep.log.log(LogStatus.FAIL, ("Failed: System is NOT ARMED AWAY"));
        } finally {
        }
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
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
