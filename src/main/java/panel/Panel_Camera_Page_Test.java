package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Panel_Camera_Page_Test extends Setup {

    String page_name = "panel Camera page testing";
    Logger logger = Logger.getLogger(page_name);

    public Panel_Camera_Page_Test() throws Exception {}

    @Override ()
    public void swipe_vertical_up() throws InterruptedException {
        int starty = 705;
        int endy = 270;
        int startx = 110;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

    @Test
    public void Check_all_elements_on_Photo_Frame_page() throws Exception {
        Panel_Camera_Page camera = PageFactory.initElements(driver, Panel_Camera_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emergency = PageFactory.initElements(driver, Emergency_Page.class);
        Installation_Page installation = PageFactory.initElements(driver, Installation_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Camera_Settings_Page cam_set = PageFactory.initElements(driver, Camera_Settings_Page.class);
        delete_all_camera_photos();
        TimeUnit.SECONDS.sleep(2);
        swipeFromRighttoLeft();
        logger.info("Verifying elements on the page...");
        element_verification(camera.Panel_camera_page_title, "panel Camera title");
        element_verification(camera.Disarm_photos, "Disarm tab");
        element_verification(camera.Settings_photos, "settings tab");
        element_verification(camera.Alarms_photo, "Alarms tab");
        element_verification(camera.All_photos, "All tab");
        home.Emergency_Button.click();
        TimeUnit.SECONDS.sleep(2);
        emergency.Police_icon.click();
        TimeUnit.SECONDS.sleep(3);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        swipeFromRighttoLeft();
        TimeUnit.SECONDS.sleep(2);
        camera.Disarm_photos.click();
        element_verification(camera.DISARMED_BY_ADMIN, "DISARMED BY ADMIN");
        camera.Alarms_photo.click();
        element_verification(camera.POLICE_EMERGENCY_PANEL, "POLICE EMERGENCY (PANEL)");
        TimeUnit.SECONDS.sleep(2);
        logger.info("Enabling Alarm Videos...");
        navigate_to_Advanced_Settings_page();
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
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        swipeFromRighttoLeft();
        TimeUnit.SECONDS.sleep(2);
        swipe_vertical_up();
        TimeUnit.SECONDS.sleep(2);
        element_verification(camera.Alarm_Videos_title, "Alarm Videos title");
        element_verification(camera.POLICE_EMERGENCY_PANEL, "POLICE EMERGENCY (PANEL)");
        TimeUnit.SECONDS.sleep(2);
        camera.Alarm_Video_img.click();
        TimeUnit.SECONDS.sleep(7);
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enter_default_user_code();
        navigate_to_Advanced_Settings_page();
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
