package zwave;

import panel.Home_Page;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/* One door lock, default name, status unlocked */

public class Door_Lock_Test extends Setup {

    String page_name = "Door Lock Testing";
    Logger logger = Logger.getLogger(page_name);

    public Door_Lock_Test() throws Exception {
    }

    public void status_verification(WebElement element, String text) {
        if (element.getText().equals(text)) {
            System.out.println("Pass: status successfully changed to " + text);
        } else {
            System.out.println("Failed: status is not " + text);
        }
    }

    public void smart_click(WebElement element, WebElement element2, String status, String message ){
        if (element.getText().equals(status)) {
           element2.click();
            System.out.println("Door lock is successfully " + message);
        } else {
            System.out.println("Status is not as expected");
        }
    }



    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test(priority = 0)
    public void Check_all_elements_on_DoorLock_page() throws Exception {
        Door_Lock_Page door = PageFactory.initElements(driver, Door_Lock_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);

        swipeFromRighttoLeft();
        Thread.sleep(2000);
        element_verification(door.Key_icon, "Key icon");
        element_verification(door.DoorLock_Name, "Door Lock name");
        element_verification(door.DoorLock_Status, "Door Lock status");
        element_verification(door.Refresh_Status, "Refresh status");
        element_verification(door.Door_battery, "Door lock battery level");
        element_verification(door.Unlock_ALL, "Unlock All Doors");
        element_verification(door.Lock_ALL, "Lock All Doors");
        home.Home_button.click();
    }

    @Test(priority = 1)
    public void Door_Lock_events() throws Exception {
        Door_Lock_Page door = PageFactory.initElements(driver, Door_Lock_Page.class);

        swipeFromRighttoLeft();
        smart_click(door.DoorLock_Status,  door.Lock_ALL, "UNLOCKED", "Locked");
        Thread.sleep(7000);
        status_verification(door.DoorLock_Status, "LOCKED");
        Thread.sleep(5000);
        smart_click(door.DoorLock_Status,  door.Unlock_ALL, "LOCKED", "Unlocked");
        Thread.sleep(7000);
        status_verification(door.DoorLock_Status, "UNLOCKED");
        Thread.sleep(5000);
    }


        @AfterClass
        public void tearDown () throws IOException, InterruptedException {
            log.endTestCase(page_name);
            driver.quit();
        }
    }

