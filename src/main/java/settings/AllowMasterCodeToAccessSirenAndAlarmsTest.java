package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.InstallationPage;
import panel.SettingsPage;
import panel.SirenAlarmsPage;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class AllowMasterCodeToAccessSirenAndAlarmsTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Siren_and_Alarms");

    public AllowMasterCodeToAccessSirenAndAlarmsTest() throws Exception {
        ConfigProps.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Siren_and_Alarms_page() throws Exception {
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        rep.create_report("MasterCodeAccessSirens_and_Alarms_01");
        rep.log.log(LogStatus.INFO, ("*MasterCodeAccessSirens_and_Alarms_01* Enable access to Sirens and Alarms page using Master Code -> Expected result = Sirens and Alarms icon is present"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        if (inst.SIREN_AND_ALARMS.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Siren and Alarms icon is present"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Siren and Alarms icon is NOT present"));
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        rep.create_report("MasterCodeAccessSirens_and_Alarms_02");
        rep.log.log(LogStatus.INFO, ("*MasterCodeAccessSirens_and_Alarms_02* Disable access to Sirens and Alarms page using Master Code -> Expected result = Sirens and Alarms icon is gone"));
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Siren and Alarms icon is present"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Siren and Alarms icon is NOT present"));
        } finally {
        }
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
