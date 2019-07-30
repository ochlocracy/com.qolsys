package settingsGrid;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.ConfigProps;
import utils.Setup1;

import java.io.IOException;

public class Disarm_Photos_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Disarm Photos testing";
    Logger logger = Logger.getLogger(page_name);
    public Runtime rt = Runtime.getRuntime();

    public void SCGrid_Secure_Photo_Enabled(String UDID_) throws IOException, InterruptedException {
        rt.exec("adb -s " + UDID_ + " shell service call qservice 40 i32 0 i32 0 i32 34 i32 1 i32 0 i32 0");
        System.out.println(ConfigProps.adbPath + " -s " + UDID_ +   " shell service call qservice 40 i32 0 i32 0 i32 34 i32 1 i32 0 i32 0  secure delete photos enabled");
    }
    public Disarm_Photos_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Disarm_Photos_works(String UDID_) throws Exception {
        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(s.driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(s.driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(s.driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.driver, InstallationPage.class);

        SCGrid_Secure_Photo_Enabled(UDID_);

        System.out.println("Verifying Disarm photo is taken when setting in enabled...");
        logger.info("Verifying Disarm photo is taken when setting in enabled");

        s.delete_all_camera_photos(); //must have secure delete on
        Thread.sleep(1000);
        s.ARM_STAY();
        home.DISARM.click();
        s.enterDefaultUserCode();
        s.swipeFromLefttoRight();
        Thread.sleep(2000);
        s.swipeFromLefttoRight();
        camera.Disarm_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            System.out.println("Pass: Disarm photo is displayed");
            logger.info("Pass: Disarm photo is displayed");
        } else {
            System.out.println("Failed: Disarm photo is NOT displayed");
            logger.info("Failed: Disarm photo is NOT displayed");
        }
        s.delete_all_camera_photos();
        Thread.sleep(1000);
        System.out.println("Verifying Disarm photo is NOT taken when setting in disabled...");
        logger.info("Disarm photo is NOT taken when setting in disabled");
        s.navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        s.ARM_STAY();
        home.DISARM.click();
        s.enterDefaultUserCode();
        s.swipeFromLefttoRight();
        s.swipeFromLefttoRight();
        camera.Disarm_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                System.out.println("Failed: Disarm photo is displayed");
            logger.info("Failed: Disarm photo is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Disarm photo is NOT displayed");
            logger.info("Pass: Disarm photo is NOT displayed");
        }
        Thread.sleep(1000);
        s.navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Disarm_Photos.click();
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