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
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Siren_Alarms_page() throws Exception {
        CameraSettingsPage camera = PageFactory.initElements(driver, CameraSettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        elementVerification(camera.Secure_Delete_Images_Is_Enabled, "Secure Delete Images summery");
        camera.Secure_Delete_Images.click();
        Thread.sleep(1000);
        elementVerification(camera.Secure_Delete_Images_Is_Disabled, "Secure Delete Images summery when disabled");
        camera.Secure_Delete_Images.click();
        Thread.sleep(1000);
        elementVerification(camera.Disarm_Photos_Is_Enabled, "Disarm Photos summery");
        camera.Disarm_Photos.click();
        Thread.sleep(1000);
        elementVerification(camera.Disarm_Photos_Is_Disabled, "Disarm Photos summery when disabled");
        camera.Disarm_Photos.click();
        Thread.sleep(1000);
        elementVerification(camera.Alarm_Photos_Is_Enabled, "Alarm Photos summery");
        camera.Alarm_Photos.click();
        Thread.sleep(1000);
        elementVerification(camera.Alarm_Photos_Is_Disabled, "Alarm Photos summery when disable");
        camera.Alarm_Photos.click();
        Thread.sleep(1000);
        elementVerification(camera.Alarm_Videos_Summary_Is_Enabled, "Alarm Videos summery Enabled");
        camera.Alarm_Videos.click();
        Thread.sleep(1000);
        elementVerification(camera.Alarm_Videos_Summary_Is_Disabled, "Alarm Videos summery");
        camera.Alarm_Videos.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(camera.Settings_Photos_Is_Disabled, "Setting Photos summery");
        camera.Settings_Photos.click();
        Thread.sleep(1000);
        elementVerification(camera.Settings_Photos_Is_Enabled, "Setting Photos summery when enabled");
        camera.Settings_Photos.click();
        Thread.sleep(1000);
        elementVerification(camera.Allow_Master_Code_to_access_Camera_Settings_Is_Disabled, "Allow Master Code to access Camera settings summery");
        camera.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
        elementVerification(camera.Allow_Master_Code_to_access_Camera_Settings_Is_Enabled, "Allow Master Code to access Camera settings summery when enabled");
        camera.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}