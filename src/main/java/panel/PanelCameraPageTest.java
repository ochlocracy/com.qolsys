package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PanelCameraPageTest extends Setup {

    String page_name = "panel Camera page testing";
    Logger logger = Logger.getLogger(page_name);

    public PanelCameraPageTest() throws Exception {}

    @Override ()
    public void swipeVerticalUp() throws InterruptedException {
        int starty = 705;
        int endy = 270;
        int startx = 110;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
        setupLogger(page_name);}

    @Test
    public void Check_all_elements_on_Photo_Frame_page() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        InstallationPage installation = PageFactory.initElements(driver, InstallationPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        CameraSettingsPage cam_set = PageFactory.initElements(driver, CameraSettingsPage.class);
        deleteAllCameraPhotos();
        TimeUnit.SECONDS.sleep(2);
        swipeFromRighttoLeft();
        swipeFromRighttoLeft();
        logger.info("Verifying elements on the page...");
        elementVerification(camera.Panel_camera_page_title, "panel Camera title");
        elementVerification(camera.Disarm_photos, "Disarm tab");
        elementVerification(camera.Settings_photos, "settings tab");
        elementVerification(camera.Alarms_photo, "Alarms tab");
        elementVerification(camera.All_photos, "All tab");
        home.Emergency_Button.click();
        TimeUnit.SECONDS.sleep(2);
        emergency.Police_icon.click();
        TimeUnit.SECONDS.sleep(3);
        enterDefaultUserCode();
        swipeFromRighttoLeft();
        swipeFromRighttoLeft();
        TimeUnit.SECONDS.sleep(2);
        camera.Disarm_photos.click();
        elementVerification(camera.DISARMED_BY_ADMIN, "DISARMED BY ADMIN");
        camera.Alarms_photo.click();
        elementVerification(camera.POLICE_EMERGENCY_PANEL, "POLICE EMERGENCY (PANEL)");
        TimeUnit.SECONDS.sleep(2);
        logger.info("Enabling Alarm Videos...");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        installation.CAMERA_SETTINGS.click();
        TimeUnit.SECONDS.sleep(1);
        cam_set.Alarm_Videos.click();
        home.Home_button.click();
        TimeUnit.SECONDS.sleep(1);
        home.Emergency_Button.click();
        TimeUnit.SECONDS.sleep(2);
        emergency.Police_icon.click();
        TimeUnit.SECONDS.sleep(3);
        enterDefaultUserCode();
        swipeFromRighttoLeft();
        swipeFromRighttoLeft();
        TimeUnit.SECONDS.sleep(2);
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        installation.CAMERA_SETTINGS.click();
        TimeUnit.SECONDS.sleep(2);
        cam_set.Alarm_Videos.click();
        home.Home_button.click();
    }
    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
