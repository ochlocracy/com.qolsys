package settings;

import panel.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Secure_Delete_Images_Test extends Setup {

    String page_name = "Secure Delete Images testing";
    Logger logger = Logger.getLogger(page_name);

    public Secure_Delete_Images_Test() throws Exception {}

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

    @Test
    public void Verify_Secure_Delete_Images_works() throws Exception {
        Home_Page home  = PageFactory.initElements(driver, Home_Page.class);
        Panel_Camera_Page camera = PageFactory.initElements(driver, Panel_Camera_Page.class);
        Camera_Settings_Page set_cam = PageFactory.initElements(driver, Camera_Settings_Page.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
        logger.info("Verifying deleting panel images requires valid code...");
        delete_all_camera_photos();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enter_default_user_code();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()){
            logger.info("Delete pop-up");}
        camera.Camera_delete_yes.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()){
            logger.info("Pass: Password is required to delete the image");
        }else { take_screenshot();
            logger.info("Failed: Password is NOT required to delete the image");
        }
        enter_default_user_code();
        Thread.sleep(1000);
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        logger.info("Verifying deleting panel images does not require valid code...");
        ARM_STAY();
        home.DISARM.click();
        enter_default_user_code();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()){
            logger.info("Delete pop-up");}
        camera.Camera_delete_yes.click();
        try {
            if (home.Enter_Code_to_Access_the_Area.isDisplayed())
                take_screenshot();
                logger.info("Failed: Password is required to delete the image");
        } catch (Exception e) {
            logger.info("Pass: Password is NOT required to delete the image");
        } finally {
        }
        swipeFromLefttoRight();
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
    }
    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
