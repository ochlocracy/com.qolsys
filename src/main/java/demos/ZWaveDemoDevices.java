package demos;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;
import zwave.DoorLockPage;
import zwave.LightsPage;

/**
 * Created by qolsysauto on 1/15/18.
 */
public class ZWaveDemoDevices extends Setup{

        public ZWaveDemoDevices() throws Exception{}

    String page_name = "Z-Wave Demo with Devices";
    Logger logger = Logger.getLogger(page_name);

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

        //Lock should be Unlocked and light should be OFF to start
        @Test(priority = 0)
        //@Test(invocationCount = 10)
        public void locksLightsDemo() throws Exception{
            DoorLockPage door = PageFactory.initElements(driver, DoorLockPage.class);
            LightsPage lite = PageFactory.initElements(driver , LightsPage.class);
            for(int i=0; i<=5; i++) {
                swipeFromRighttoLeft();
                Thread.sleep(1000);
                lite.Select_All.click();
                Thread.sleep(1000);
                lite.On_Button.click();
                Thread.sleep(1000);
                swipeFromRighttoLeft();
                Thread.sleep(2000);
                door.Lock_ALL.click();
                Thread.sleep(3000);
                swipeFromRighttoLeft();
                swipeFromRighttoLeft();
                swipeFromRighttoLeft();
                swipeFromRighttoLeft();
                Thread.sleep(1000);
                lite.Select_All.click();
                Thread.sleep(1000);
                lite.Off_Button.click();
                Thread.sleep(1000);
                swipeFromRighttoLeft();
                Thread.sleep(2000);
                door.Unlock_ALL.click();
                Thread.sleep(3000);
                swipeFromRighttoLeft();
                swipeFromRighttoLeft();
                swipeFromRighttoLeft();
            }
//
        }


}
