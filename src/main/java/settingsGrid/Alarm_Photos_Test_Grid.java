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
import java.util.concurrent.TimeUnit;

public class Alarm_Photos_Test_Grid {

    Setup1 s =new Setup1();
    String page_name = "Alarm Photos testing";
    Logger logger = Logger.getLogger(page_name);

    public Alarm_Photos_Test_Grid() throws Exception {}

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Alarm_Photos_works(String UDID_) throws Exception {
        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(s.getDriver(), EmergencyPage.class);
        PanelCameraPage camera = PageFactory.initElements(s.getDriver(), PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(s.getDriver(), CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
        logger.info("Verifying Alarm photo is taken when setting in enabled...");
        s.delete_all_camera_photos();
        Thread.sleep(1000);
        logger.info("Generating an Alarm...");
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        s.enter_default_user_code();
        s.swipeFromLefttoRight();
        logger.info("Verifying Alarm photo is taken...");
        camera.Alarms_photo.click();
        Thread.sleep(1000);
        s.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (camera.Photo_lable.isDisplayed()) {
            logger.info(UDID_ + " Pass: Alarm photo is displayed");
        } else {
            s.take_screenshot();
            logger.info(UDID_ + " Failed: Alarm photo is NOT displayed");
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        Thread.sleep(2000);
        s.enter_default_user_code();
        Thread.sleep(1000);
        logger.info("Verifying Alarm photo is NOT taken when setting in disabled...");
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        logger.info("Generating an Alarm...");
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        s.enter_default_user_code();
        s.swipeFromLefttoRight();
        logger.info("Verifying Alarm photo is not taken...");
        camera.Alarms_photo.click();
        s.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        try {
            if (camera.Photo_lable.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ + " Failed: Alarm photo is displayed");
        } catch (Exception e) {
            logger.info(UDID_ + " Pass: Alarm photo is NOT displayed");
        } finally {
        }
        Thread.sleep(1000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(3000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}