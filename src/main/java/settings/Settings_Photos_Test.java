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

public class Settings_Photos_Test extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Photos");

    public Settings_Photos_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Settings_Photos_works() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        rep.create_report("Settings_Photos_01");
        rep.log.log(LogStatus.INFO, ("*Settings_Photos_01* Take photo when entering settings page disabled -> Expected result = No photo is taken"));
        Thread.sleep(2000);
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        settings.Home_button.click();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Settings_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Disarm photo is displayed"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Disarm photo is NOT displayed"));
        }
        Thread.sleep(2000);
        rep.create_report("Settings_Photos_02");
        rep.log.log(LogStatus.INFO, ("*Settings_Photos_02* Take photo when entering settings page enabled -> Expected result = A photo is taken"));
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        settings.Home_button.click();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Settings_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: settings photo is displayed"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: settings photo is NOT displayed"));
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        swipeVertical();
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }

}
