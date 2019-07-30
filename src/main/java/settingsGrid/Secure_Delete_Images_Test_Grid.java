package settingsGrid;

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

public class Secure_Delete_Images_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Secure Delete Images testing";
    Logger logger = Logger.getLogger(page_name);
    public Runtime rt = Runtime.getRuntime();

    public void SCGrid_Set_Disarm_Photo(String UDID_, int state) throws IOException, InterruptedException {
        rt.exec("adb -s " + UDID_ + " shell service call qservice 40 i32 0 i32 0 i32 102 i32 " + state + " i32 0 i32 0");
        System.out.println(ConfigProps.adbPath + " -s " + UDID_ +  " shell service call qservice 40 i32 0 i32 0 i32 19 i32 " + state + " i32 0 i32 0" + " Auto Bypass Enabled");
    }

    public Secure_Delete_Images_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Secure_Delete_Images_works(String UDID_) throws Exception {
        HomePage home  = PageFactory.initElements(s.getDriver(), HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(s.getDriver(), PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(s.getDriver(), CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);

        //SCGrid_Set_Disarm_Photo(UDID_, 1 );

        logger.info("Verifying deleting panel images requires valid code...");
        s.delete_all_camera_photos();
        Thread.sleep(1000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        try {
            if (camera.Summary_Code_Required.isDisplayed())
                logger.info("code is required, continue with the test.");
        } catch (Exception e) {
            set_cam.Secure_Delete_Images.click();
        }
        home.Home_button.click();
        s.ARM_STAY();
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.swipeFromRighttoLeft();
        Thread.sleep(1000);
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()){
            logger.info("Delete pop-up");}
        camera.Camera_delete_yes.click();
//        try {
//            home.Home_button.click();
//        }catch ( Exception e){
//        }
        if (home.Three.isDisplayed()){
            logger.info(UDID_ +" Pass: Password is required to delete the image");
        }else { s.take_screenshot();
            logger.info(UDID_ +" Failed: Password is NOT required to delete the image");
        }
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.swipeFromLefttoRight();
        Thread.sleep(1000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        logger.info("Verifying deleting panel images does not require valid code...");
        s.ARM_STAY();
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.swipeFromRighttoLeft();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()){
            logger.info("Delete pop-up");}
        camera.Camera_delete_yes.click();
        try {
            if (home.Three.isDisplayed())
                s.take_screenshot();
            logger.info(UDID_ +" Failed: Password is required to delete the image");
        } catch (Exception e) {
            logger.info(UDID_ +" Pass: Password is NOT required to delete the image");
        } finally {
        }
        s.swipeFromLefttoRight();
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        set_cam.Secure_Delete_Images.click();
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