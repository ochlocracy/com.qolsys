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

public class DisarmPhotosTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Disarm_Photos");

    public DisarmPhotosTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Disarm_Photos_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        rep.create_report("Disarm_Photo_01");
        rep.log.log(LogStatus.INFO, ("*Disarm_Photo_01* Disarm Photos already enabled -> Expected result = a photo is taken"));
        Thread.sleep(2000);
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Disarm_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Disarm photo is displayed"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Disarm photo is NOT displayed"));
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        rep.create_report("Disarm_Photo_02");
        rep.log.log(LogStatus.INFO, ("*Disarm_Photo_02* Disable Disarm Photos  -> Expected result = a photo is not taken upon disarm"));
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Disarm_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Disarm photo is displayed"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Disarm photo is NOT displayed"));
        } finally {
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}