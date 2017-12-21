package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup;

import java.io.IOException;

public class SecureDeleteImagesTest extends Setup {
    String page_name = "Secure Delete Images testing";
    Logger logger = Logger.getLogger(page_name);

    public SecureDeleteImagesTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Secure_Delete_Images_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        logger.info("Verifying deleting panel images requires valid code...");
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            logger.info("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
            logger.info("Pass: Password is required to delete the image");
        } else {
            takeScreenshot();
            logger.info("Failed: Password is NOT required to delete the image");
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
        Thread.sleep(1000);
        logger.info("Verifying deleting panel images does not require valid code...");
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
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
            logger.info("Failed: Password is required to delete the image");
        } catch (Exception e) {
            logger.info("Pass: Password is NOT required to delete the image");
        } finally {
        }
        swipeFromLefttoRight();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
