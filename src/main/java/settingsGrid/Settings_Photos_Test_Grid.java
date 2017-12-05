package settingsGrid;

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
        PanelCameraPage camera = PageFactory.initElements(s.getDriver(), PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(s.getDriver(), CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
        logger.info("Verifying settings photo is NOT taken when setting in disabled...");
        s.delete_all_camera_photos();
        Thread.sleep(1000);
        s.navigate_to_Settings_page();
        settings.ADVANCED_SETTINGS.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        settings.Home_button.click();
        s.swipeFromLefttoRight();
        camera.Settings_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ +" Failed: Disarm photo is displayed");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: Disarm photo is NOT displayed");
        } finally {
        }
        logger.info("Verifying settings photo is taken when setting in enabled...");
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        s.navigate_to_Settings_page();
        settings.ADVANCED_SETTINGS.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        settings.Home_button.click();
        s.swipeFromLefttoRight();
        camera.Settings_photos.click();
        if (camera.Photo_lable.isDisplayed()){
            logger.info(UDID_ +" Pass: settings photo is displayed");
        }else { s.take_screenshot();
            logger.info(UDID_ +" Failed: settings photo is NOT displayed");}
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        s.enter_default_user_code();
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}