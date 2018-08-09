package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.CameraSettingsPage;
import panel.InstallationPage;
import panel.SettingsPage;
import utils.Setup;

import java.io.IOException;

public class AllowMasterCodeToAccessCameraSettingsTest extends Setup {

    String page_name = "Allow Master Code to access Camera settings testing";
    Logger logger = Logger.getLogger(page_name);

    public AllowMasterCodeToAccessCameraSettingsTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Camera_Settings_page() throws Exception {
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        logger.info("Navigate to the setting page to enable the access to the Camera settings page using Master Code");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        adv.INSTALLATION.click();
        Thread.sleep(1000);
        if (inst.CAMERA_SETTINGS.isDisplayed()) {
            logger.info("Pass: Camera settings icon is present");
        } else {
            takeScreenshot();
            logger.info("Failed: Camera settings icon is NOT present");
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Verify Camera settings icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.CAMERA_SETTINGS.isDisplayed())
                takeScreenshot();
            logger.info("Failed: Camera settings icon is present");
        } catch (Exception e) {
            logger.info("Pass: Camera settings icon is NOT present");
        } finally {
        }
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
