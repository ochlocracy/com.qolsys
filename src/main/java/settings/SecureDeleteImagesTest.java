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

public class SecureDeleteImagesTest extends Setup {

    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ExtentReport rep = new ExtentReport("SettingsReport");

    public SecureDeleteImagesTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Secure_Delete_Images_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        servcall.set_ARM_STAY_NO_DELAY_enable();

        rep.create_report("Secure_Del_Img_01");
        rep.log.log(LogStatus.INFO, ("*Secure_Del_Img_01* Delete a Photo -> Expected result = A code required to Delete the image"));
        Thread.sleep(2000);
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        Thread.sleep(1000);
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            logger.info("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Password is required to delete the image"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Password is NOT required to delete the image"));
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        rep.create_report("Secure_Del_Img_02");
        rep.log.log(LogStatus.INFO, ("*Secure_Del_Img_02* Disable code required to Delete a Photo -> Expected result = Image deleted without code"));
        Thread.sleep(2000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            logger.info("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        try {
            if (home.Enter_Code_to_Access_the_Area.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Password is required to delete the image"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Password is NOT required to delete the image"));
        }
        swipeFromLefttoRight();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
