package settingsGrid;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup1;

import java.io.IOException;

public class Settings_Photos_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "settings Photos testing";
    Logger logger = Logger.getLogger(page_name);

    public Settings_Photos_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Settings_Photos_works(String UDID_) throws Exception {
        PanelCameraPage camera = PageFactory.initElements(s.driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(s.driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(s.driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.driver, InstallationPage.class);

        System.out.println("Verifying settings photo is NOT taken when setting in disabled...");
        logger.info("Verifying settings photo is NOT taken when setting in disabled");
        s.delete_all_camera_photos();
        Thread.sleep(1000);
        s.navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        s.enterDefaultUserCode();
        Thread.sleep(1000);
        settings.Home_button.click();
        s.swipeFromLefttoRight();
        s.swipeFromLefttoRight();
        camera.Settings_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                System.out.println("Failed: Disarm photo is displayed");
            logger.info("Failed: Disarm photo is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Disarm photo is NOT displayed");
            logger.info("Pass: Disarm photo is NOT displayed");
        } finally {
        }
        System.out.println("Verifying settings photo is taken when setting in enabled...");
        logger.info("Verifying settings photo is taken when setting in enabled");
        s.navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        s.navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        s.enterDefaultUserCode();
        Thread.sleep(1000);
        settings.Home_button.click();
        s.swipeFromLefttoRight();
        s.swipeFromLefttoRight();
        camera.Settings_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            System.out.println("Pass: settings photo is displayed");
            logger.info("Pass: settings photo is displayed");
        } else {
            System.out.println("Failed: settings photo is NOT displayed");
            logger.info("Pass settings photo is NOT displayed");
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        s.enterDefaultUserCode();
        s.navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        s.swipe_vertical();
        Thread.sleep(1000);
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}