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

public class KeyfobAlarmDisarmTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Keyfob_Emerg_Alarm");
    Sensors sensors = new Sensors();
    private String disarm = "08 01";

    public KeyfobAlarmDisarmTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Keyfob_Alarm_Disarm_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);

        rep.create_report("Keyfob_Alarm_01");
        rep.log.log(LogStatus.INFO, ("*Keyfob_Alarm_01* Keyfob emergency Alarm Disarm setting disabled -> Expected result = Keyfob will not disarm the alarm"));
        Thread.sleep(2000);
        sensors.add_primary_call(3, 4, 6619386, 102);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(8000);
        if (emergency.Police_Emergency_Alarmed.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Police Emergency Alarm is displayed"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Police Emergency Alarm is NOT displayed"));
        }
        enterDefaultUserCode();
        rep.create_report("Keyfob_Alarm_02");
        rep.log.log(LogStatus.INFO, ("*Keyfob_Alarm_02* Keyfob emergency Alarm Disarm setting enabled -> Expected result = Keyfob will disarm the alarm"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        if (home.Disarmed_text.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Keyfob disarm works"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Keyfob disarm did not worked"));
        }
        verifyDisarm();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
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
