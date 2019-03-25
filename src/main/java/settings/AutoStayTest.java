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

public class AutoStayTest extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    ExtentReport rep = new ExtentReport("Settings_Auto_Stay");
    Sensors sensors = new Sensors();
    private int delay = 15;

    public AutoStayTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Auto_Stay_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(1000);
        servcall.set_NORMAL_ENTRY_DELAY(11);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(10);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(13);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(12);
        Thread.sleep(1000);
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        rep.create_report("Auto_Stay_01");
        rep.log.log(LogStatus.INFO, ("*Auto_Stay_01* Enable Auto Stay -> Expected result = System goes into Arm Stay when Arm Away is clicked"));
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(13000);
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: System is in ARMED Stay"));
        } finally {
        }
        home.DISARM.click(); //need to lower the sia limit for the exit delay
        enterDefaultUserCode();
        rep.create_report("Auto_Stay_02");
        rep.log.log(LogStatus.INFO, ("*Auto_Stay_02* Disable Auto Stay -> Expected result = System goes into Arm Away when Arm Away is clicked"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        ARM_AWAY(delay);
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.PASS, ("Pass: System is ARMED AWAY"));
        } catch (Exception e) {
            rep.log.log(LogStatus.FAIL, ("Failed: System is in ARMED Stay"));
        } finally {
        }
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
        service.stop();
    }
}
