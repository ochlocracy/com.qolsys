package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ArmingPhotos {
    Setup s = new Setup();
    String page_name = "Arming + Disarm/Alarm photos";
    Logger logger = Logger.getLogger(page_name);

    public ArmingPhotos() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        s.setup_driver(s.get_UDID(), "http://127.0.1.1", "4723");
        s.setup_logger(page_name);
    }

    @Test
    public void Test1() throws Exception {
        s.delete_all_camera_photos();
        TimeUnit.SECONDS.sleep(5);
        for (int i = 22; i > 0; i--) {
            s.ARM_STAY();
            TimeUnit.SECONDS.sleep(10);
            s.DISARM();
            TimeUnit.SECONDS.sleep(5);
            System.out.println(i);
        }
    }

    @Test
    public void Test2() throws Exception {
        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
        EmergencyPage emg = PageFactory.initElements(s.driver, EmergencyPage.class);
        s.delete_all_camera_photos();
        TimeUnit.SECONDS.sleep(5);
        for (int i = 22; i > 0; i--) {
            home.Emergency_Button.click();
            TimeUnit.SECONDS.sleep(2);
            emg.Fire_icon.click();
            TimeUnit.SECONDS.sleep(5);
            emg.Cancel_Emergency.click();
            s.enter_default_user_code();
            TimeUnit.SECONDS.sleep(5);

            System.out.println(i);
        }
    }

    @Test
    public void Test3() throws Exception {
        for (int i = 20; i > 0; i--) {
            s.swipeFromRighttoLeft();
            TimeUnit.SECONDS.sleep(2);
        }
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        s.driver.quit();
    }
}
