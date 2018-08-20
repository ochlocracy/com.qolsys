package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class AlarmPhotosTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Alarm_Photos");

    public AlarmPhotosTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Alarm_Photos_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        rep.create_report("AlarmPhotos_01");
        rep.log.log(LogStatus.INFO, ("*AlarmPhotos_01* Arm and Disarm Panel -> Expected result = Alarm photo is taken"));
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        if (camera.Photo_lable.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm, Alarm photo is displayed"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Fail: System is not in Alarm, Alarm photo is not displayed"));
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        rep.create_report("AlarmPhotos_02");
        rep.log.log(LogStatus.INFO, ("*AlarmPhotos_02* Disable Alarm Photo setting -> No photo is displayed"));
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Alarm photo is displayed"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Alarm photo is NOT displayed"));
        } finally {
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}