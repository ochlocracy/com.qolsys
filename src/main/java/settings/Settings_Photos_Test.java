package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup;

import java.io.IOException;

public class Settings_Photos_Test extends Setup {
    String page_name = "Settings Photos testing";
    Logger logger = Logger.getLogger(page_name);

    public Settings_Photos_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Settings_Photos_works() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        logger.info("Verifying settings photo is NOT taken when setting in disabled...");
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
            logger.info("Failed: Disarm photo is displayed");
        } catch (Exception e) {
            logger.info("Pass: Disarm photo is NOT displayed");
        } finally {
        }
        logger.info("Verifying settings photo is taken when setting in enabled...");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
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
            logger.info("Pass: settings photo is displayed");
        } else {
            takeScreenshot();
            logger.info("Failed: settings photo is NOT displayed");
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }

}
