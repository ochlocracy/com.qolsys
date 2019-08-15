package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.CameraSettingsPage;
import panel.InstallationPage;
import panel.SettingsPage;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class AllowMasterCodeToAccessCameraSettingsTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Camera_Settings");

    public AllowMasterCodeToAccessCameraSettingsTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Camera_Settings_page() throws Exception {
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        rep.create_report("MasterCodeAccessCamera_01");
        rep.log.log(LogStatus.INFO, ("*MasterCodeAccessCamera_01* Enable access to Camera Settings when using Master Code -> Expected result = Camera Settings icon is present"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        if (set_cam.Allow_Master_Code_to_access_Camera_Settings_Is_Disabled.isDisplayed()){
            set_cam.Allow_Master_Code_to_access_Camera_Settings.click(); //added this if statement without checking if the if passes.
        }
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(1000);
        if (inst.CAMERA_SETTINGS.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Camera settings icon is present"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Camera settings icon is NOT present"));
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        rep.create_report("MasterCodeAccessCamera_02");
        rep.log.log(LogStatus.INFO, ("*MasterCodeAccessCamera_02* Disable access to Camera Settings when using Master Code -> Expected result = Camera Settings icon is gone"));
        Thread.sleep(3000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.CAMERA_SETTINGS.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Camera settings icon is present"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Camera settings icon is NOT present"));
        } finally {
        }
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
