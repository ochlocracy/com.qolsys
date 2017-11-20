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

public class Secure_Delete_Images_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Secure Delete Images testing";
    Logger logger = Logger.getLogger(page_name);

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
        Home_Page home  = PageFactory.initElements(s.getDriver(), Home_Page.class);
        Panel_Camera_Page camera = PageFactory.initElements(s.getDriver(), Panel_Camera_Page.class);
        Camera_Settings_Page set_cam = PageFactory.initElements(s.getDriver(), Camera_Settings_Page.class);
        Settings_Page settings = PageFactory.initElements(s.getDriver(), Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(s.getDriver(), Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(s.getDriver(), Installation_Page.class);
        logger.info("Verifying deleting panel images requires valid code...");
        s.delete_all_camera_photos();
        Thread.sleep(1000);
        s.ARM_STAY();
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.swipeFromLefttoRight();
        Thread.sleep(1000);
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()){
            logger.info("Delete pop-up");}
        camera.Camera_delete_yes.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()){
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
        s.swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()){
            logger.info("Delete pop-up");}
        camera.Camera_delete_yes.click();
        try {
            if (home.Enter_Code_to_Access_the_Area.isDisplayed())
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