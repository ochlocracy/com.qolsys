package zwave;

import panel.Advanced_Settings_Page;
import panel.Devices_Page;
import panel.Home_Page;
import panel.Installation_Page;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/* One door lock, default name, status unlocked */

public class DoorLockTest extends Setup {

    String page_name = "Door Lock Testing";
    Logger logger = Logger.getLogger(page_name);

    public DoorLockTest() throws Exception {
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
        DoorLockPage door = PageFactory.initElements(driver, DoorLockPage.class);
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
        DoorLockPage door = PageFactory.initElements(driver, DoorLockPage.class);

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


    public void Z_Wave_Door_Locks_Disarm_Mode(String UDID_) throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page instal = PageFactory.initElements(driver, Installation_Page.class);
        Devices_Page dev = PageFactory.initElements(driver, Devices_Page.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);

        logger.info("*************************ZD_D_001*******************************");
        logger.info("Disarm mode: Verify that a Door Lock can be paired with a Panel");

        logger.info("*************************ZD_D_029*******************************");
        logger.info("Disarm mode: Verify that a Door Lock can be added from the ADC website");

        logger.info("*************************ZD_D_004*******************************");
        logger.info("Disarm mode: Verify that multiple Door Locks can be paired with a Panel");

        logger.info("*************************ZD_D_003*******************************");
        logger.info("Disarm mode: Verify that the set number of Door Locks allowed restricts the number of Door Locks you can pair");

        logger.info("*************************ZD_D_005*******************************");
        logger.info("Disarm mode: Verify that the set number of Door Locks allowed won't go under the number of Door Locks paired");

        logger.info("*************************ZD_D_002*******************************");
        logger.info("Disarm mode: Verify that a new Door Lock appears on the ADC websites");

        logger.info("*************************ZD_D_011*******************************");
        logger.info("Disarm mode: Verify that a Single Door Lock can be Unlocked / Locked from a panel");

        logger.info("*************************ZD_D_013*******************************");
        logger.info("Disarm mode: Verify that a Panel UI changes when the Door Lock is manually Unlocked / Locked");

        logger.info("*************************ZD_D_012*******************************");
        logger.info("Disarm mode: Verify that a Single Door Lock can be Unlocked / Locked from a panel at 5 meters");

        logger.info("************************ZD_D_014********************************");
        logger.info("Disarm mode: Verify that multiple door locks can be Locked / Unlocked from a panel");

        logger.info("*************************ZD_D_015*******************************");
        logger.info("Disarm mode: Verify that multiple door locks can be Locked / Unlocked from a panel at 5 meters");

        logger.info("*************************ZD_D_016*******************************");
        logger.info("Disarm mode: Verify that the Door Locks can be Locked / Unlocked from ADC User site");

        logger.info("*************************ZD_D_026*******************************");
        logger.info("Disarm mode: Verify that the battery status is displayed");

        logger.info("*************************ZD_D_027*******************************");
        logger.info("Disarm mode: Verify that the battery is low alert message will appear");

        logger.info("*************************ZD_D_017*******************************");
        logger.info("Disarm mode: Verify both panel and ADC recognize Door Lock Jam Alarm");

        logger.info("*************************ZD_D_006*******************************");
        logger.info("Disarm mode: Verify that changing device name from preset to a custom name changes the UI name");

        logger.info("*************************ZD_D_007*******************************");
        logger.info("Disarm mode: Verify that you can edit a device name (Panel) and it matches ADC");

        logger.info("*************************ZD_D_008*******************************");
        logger.info("Disarm mode: Verify that you can edit a device name (ADC) and it matches the Panel");

        logger.info("*************************ZD_D_009*******************************");
        logger.info("Disarm mode: Verify that the Panel recognizes an unreachable device");

        logger.info("*************************ZD_D_018*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_019*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_020*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_021*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_022*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_023*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_024*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_025*******************************");
        logger.info("Disarm mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_D_010*******************************");
        logger.info("Disarm mode: Verify that the history events are displayed on the Z Wave Device Status page");

        logger.info("*************************ZD_D_028*******************************");
        logger.info("Disarm mode: Verify that a Door Lock can be deleted from a Panel");

        logger.info("*************************ZD_D_030*******************************");
        logger.info("Disarm mode: Verify that a Door Lock can be deleted from the ADC website");
    }
    public void Z_Wave_Door_Locks_Arm_Stay_Mode(String UDID_) throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page instal = PageFactory.initElements(driver, Installation_Page.class);
        Devices_Page dev = PageFactory.initElements(driver, Devices_Page.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);

        logger.info("*************************ZD_AS_001*******************************");
        logger.info("Arm Stay mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AS_002*******************************");
        logger.info("Arm Stay mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AS_003*******************************");
        logger.info("Arm Stay mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AS_004*******************************");
        logger.info("Arm Stay mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AS_005*******************************");
        logger.info("Arm Stay mode: Verify that  a Door Lock can be deleted from the ADC website");

    }
    public void Z_Wave_Door_Locks_Arm_Away_Mode(String UDID_) throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page instal = PageFactory.initElements(driver, Installation_Page.class);
        Devices_Page dev = PageFactory.initElements(driver, Devices_Page.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);

        logger.info("*************************ZD_AW_001*******************************");
        logger.info("Arm Away mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AW_002*******************************");
        logger.info("Arm Away mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AW_003*******************************");
        logger.info("Arm Away mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AW_004*******************************");
        logger.info("Arm Away mode: Create rule engine for Door Locks");

        logger.info("*************************ZD_AW_005*******************************");
        logger.info("Arm Away mode: Verify that  a Door Lock can be deleted from the ADC website");
    }
        @AfterClass
        public void tearDown () throws IOException, InterruptedException {
            log.endTestCase(page_name);
            driver.quit();
        }
    }

