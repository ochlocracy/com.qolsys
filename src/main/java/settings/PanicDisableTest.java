package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class PanicDisableTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Panic_Disable");

    public PanicDisableTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Panic_Disable_works() throws Exception {
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);

        rep.create_report("Panic_Disable_01");
        rep.log.log(LogStatus.INFO, ("*Panic_Disable_01* Fire, Police, Auxiliary disabled -> Expected result = Element will not be there"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Police_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Police_icon.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Police Emergency is displayed"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Police Emergency is NOT displayed"));
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Fire_icon.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Fire Emergency is displayed"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Fire Emergency is NOT displayed"));
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Police_Panic.click();
        Thread.sleep(1000);
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Auxiliary_icon.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Auxiliary Emergency is displayed"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Auxiliary Emergency is NOT displayed"));
        } finally {
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Fire_Panic.click();
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
