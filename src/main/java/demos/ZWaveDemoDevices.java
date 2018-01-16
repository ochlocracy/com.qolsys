package demos;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import utils.Setup;
import zwave.DoorLockPage;
import zwave.LightsPage;

/**
 * Created by qolsysauto on 1/15/18.
 */
public class ZWaveDemoDevices extends Setup{

        public ZWaveDemoDevices() throws Exception{}

        //Lock should be Unlocked and light should be OFF to start
        @Test(priority = 0)
        public void locksLightsDemo() throws Exception{
            DoorLockPage door = PageFactory.initElements(driver, DoorLockPage.class);
            LightsPage lite = PageFactory.initElements(driver , LightsPage.class);
            swipeFromRighttoLeft();
            Thread.sleep(2000);
            door.Lock_ALL.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            lite.Select_All.click();
            Thread.sleep(1000);
            lite.On_Button.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            Thread.sleep(2000);
            door.Unlock_ALL.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            lite.Select_All.click();
            Thread.sleep(1000);
            lite.Off_Button.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            Thread.sleep(2000);
            door.Lock_ALL.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            lite.Select_All.click();
            Thread.sleep(1000);
            lite.On_Button.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            Thread.sleep(2000);
            door.Unlock_ALL.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            lite.Select_All.click();
            Thread.sleep(1000);
            lite.Off_Button.click();
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
        }


}
