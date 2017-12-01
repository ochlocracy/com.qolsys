package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class CameraSettingsPageTest extends Setup {

    String page_name = "Camera settings testing";
    Logger logger = Logger.getLogger(page_name);

    public CameraSettingsPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Siren_Alarms_page() throws Exception {
        CameraSettingsPage camera = PageFactory.initElements(driver, CameraSettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        element_verification(camera.Secure_Delete_Images_summery, "Secure Delete Images summery");
        camera.Secure_Delete_Images.click();
        Thread.sleep(1000);
        element_verification(camera.Secure_Delete_Images_summery_disabled, "Secure Delete Images summery when disabled");
        camera.Secure_Delete_Images.click();
        Thread.sleep(1000);
        element_verification(camera.Disarm_Photos_summery, "Disarm Photos summery");
        camera.Disarm_Photos.click();
        Thread.sleep(1000);
        element_verification(camera.Disarm_Photos_summery_disabled, "Disarm Photos summery when disabled");
        camera.Disarm_Photos.click();
        Thread.sleep(1000);
        element_verification(camera.Alarm_Photos_summery, "Alarm Photos summery");
        camera.Alarm_Photos.click();
        Thread.sleep(1000);
        element_verification(camera.Alarm_Photos_summery_disabled, "Alarm Photos summery when disable");
        camera.Alarm_Photos.click();
        Thread.sleep(1000);
        element_verification(camera.Alarm_Videos_summery_enabled, "Alarm Videos summery Enabled");
        camera.Alarm_Videos.click();
        Thread.sleep(1000);
        element_verification(camera.Alarm_Videos_summery, "Alarm Videos summery");
        camera.Alarm_Videos.click();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        element_verification(camera.Setting_Photos_summery, "Setting Photos summery");
        camera.Settings_Photos.click();
        Thread.sleep(1000);
        element_verification(camera.Setting_Photos_summery_enabled, "Setting Photos summery when enabled");
        camera.Settings_Photos.click();
        Thread.sleep(1000);
        element_verification(camera.Allow_Master_Code_to_access_Camera_Settings_summery, "Allow Master Code to access Camera settings summery");
        camera.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
        element_verification(camera.Allow_Master_Code_to_access_Camera_Settings_summery_enabled, "Allow Master Code to access Camera settings summery when enabled");
        camera.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}