package settings;

import panel.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Alarm_Photos_Test extends Setup {

    String page_name = "Alarm Photos testing";
    Logger logger = Logger.getLogger(page_name);

    public Alarm_Photos_Test() throws Exception {}

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Verify_Alarm_Photos_works() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        logger.info("Verifying Alarm photo is taken when setting in enabled...");
        delete_all_camera_photos();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        if (camera.Photo_lable.isDisplayed()){
            logger.info("Pass: Alarm photo is displayed");
        }else { take_screenshot();
            logger.info("Failed: Alarm photo is NOT displayed");}
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enter_default_user_code();
        Thread.sleep(1000);
        logger.info("Verifying Alarm photo is NOT taken when setting in disabled...");
        navigate_to_Advanced_Settings_page();
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
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                take_screenshot();
                logger.info("Failed: Alarm photo is displayed");
        } catch (Exception e) {
            logger.info("Pass: Alarm photo is NOT displayed");
        } finally {
        }
        Thread.sleep(1000);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }
    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}