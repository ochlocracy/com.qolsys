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

public class AutoBypassTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Auto_Bypass");

    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();

    public AutoBypassTest() throws Exception {
        SensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");

    }

    @Test
    public void Verify_Auto_Bypass_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        serv.set_ARM_STAY_NO_DELAY_enable();
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        rep.create_report("Auto_Bypass_01");
        rep.log.log(LogStatus.INFO, ("*Auto_Bypass_01* Enable Auto Bypass -> Expected result = Sensors can be opened and closed without giving bypass pop-up message"));
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        verifyArmstay();
        rep.log.log(LogStatus.PASS, ("Pass: Bypass pop-up message is not present"));
        Thread.sleep(1000);
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(3000);
        settings.Home_button.click();
        Thread.sleep(3000);
        rep.create_report("Auto_Bypass_02");
        rep.log.log(LogStatus.INFO, ("*Auto_Bypass_02* Disable Auto Bypass -> Expected result = Sensors are opened and closed and give a bypass pop-up message"));
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        home.DISARM.click();
        Thread.sleep(2000);
        home.ARM_STAY.click();
        Thread.sleep(2000);
        elementVerification(home.Bypass_message, "Bypass pop-up message");
        rep.log.log(LogStatus.PASS, ("Pass: Bypass pop-up message is present"));
        Thread.sleep(2000);
        home.Bypass_OK.click();
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        sensors.delete_from_primary(3);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}