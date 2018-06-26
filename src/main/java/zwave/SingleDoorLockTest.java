package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.DevicesPage;
import panel.HomePage;
import panel.InstallationPage;
import utils.Setup;

import java.io.IOException;

import static utils.ConfigProps.primary;

/* One door lock, default name, status unlocked */

public class SingleDoorLockTest extends Setup {

    String page_name = "Door Lock Testing";
    Logger logger = Logger.getLogger(page_name);

    public SingleDoorLockTest() throws Exception {
    }


    public void statusVerification(WebElement element, String text) {
        if (element.getText().equals(text)) {
            System.out.println("Pass: status successfully changed to " + text);
        } else {
            System.out.println("Failed: status is not " + text);
        }
    }

    public void smart_click(WebElement element, WebElement element2, String status, String message) {
        if (element.getText().equals(status)) {
            element2.click();
            System.out.println("Door lock is successfully " + message);
        } else {
            System.out.println("Status is not as expected");
        }
    }


// Add method to change transmitter or real devices


    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(primary , "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test(priority = 0)
    public void Check_all_elements_on_DoorLock_page() throws Exception {
        DoorLockPage lockPage = PageFactory.initElements(driver, DoorLockPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        swipeFromRighttoLeft();
        swipeToDoorLockPage(lockPage);
        Thread.sleep(2000);
        elementVerification(lockPage.Key_icon, "Key icon");
        elementVerification(lockPage.DoorLock_Name, "Door Lock name");
        elementVerification(lockPage.DoorLock_Status, "Door Lock status");
        elementVerification(lockPage.Refresh_Status, "Refresh status");
        elementVerification(lockPage.Door_battery, "Door lock battery level");
        elementVerification(lockPage.unlockAll, "Unlock All Doors");
        elementVerification(lockPage.Lock_ALL, "Lock All Doors");
        home.Home_button.click();
    }

    @Test(priority = 1)
    public void Door_Lock_events() throws Exception {
        DoorLockPage locksPage = PageFactory.initElements(driver, DoorLockPage.class);
        swipeToDoorLockPage(locksPage);
        for(int i=0; i<=5; i++) {
        smart_click(locksPage.DoorLock_Status, locksPage.Lock_ALL, "UNLOCKED", "LockedTxt");
        Thread.sleep(7000);
        statusVerification(locksPage.DoorLock_Status, "LOCKED");
        Thread.sleep(5000);
        smart_click(locksPage.DoorLock_Status, locksPage.unlockAll, "LOCKED", "Unlocked");
        Thread.sleep(7000);
        statusVerification(locksPage.DoorLock_Status, "UNLOCKED");
        Thread.sleep(5000);
        }
        System.out.println("Ending Timer");
//        timer.end();
    }

//    public void smart_click(WebElement element, WebElement element2, String status, String message) {
//        if (element.getText().equals(status)) {
//            element2.click();
//            System.out.println("Door lock is successfully " + message);
//        } else {
//
//            System.out.println("Status is not as expected");
//        }
//    }



//****************************************************************************************************

    //add a method to select real device or simulator test


    /*Before Test Run
        *Set Default Settings
        *Manually Pair 3 Locks
        *Try pairing the 4th lock. This  should fail
        *Change max to 6 look locks
        *Pair max number of locks
    */

    public void realDevicePreSetup() {
        System.out.println("Running Real Device Test");
    }
    @Test
    public void dParingDeviceTest(){
        //Pair 2 door lock locally( name it stock "Front Door" and "Back Door")
        //pair 1 door lock from ADC( name custom name "Door Lock with node ID")
        //pair 1 door lock locally and expect max number failure
        //change max number setting with setters service call
        //pair other 3 lock with custom name with node id
        //verify all name are correct on the panel
        //verify all names are correct on ADC Dealer

    }
    @Test
    public void dNameChangeTest(){
        //Change stock name Front Door to custom name "Door Lock and Node ID" locally
        //change 3rd door lock with custom named to stock name "Side Door" locally
        // verify change on panel
        // verify change on ADC Dealer site
        // verify change on user site
        // change side door lock name to "srg door" on user site
        // verify side door lock changed to srg door on the panel
    }
    @Test
    public void dLockActionTest() throws Exception{
        DoorLockPage lockPage = PageFactory.initElements(driver,DoorLockPage.class);
        swipeToDoorLockPage(lockPage);
        System.out.println("At Door Lock Page");

    }
    public void Z_Wave_Door_Locks_Disarm_Mode() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

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
        // pair door lock with front door stock name
        //pair another door lock with a custom name. DoorLock1
        logger.info("*************************ZD_D_002*******************************");
        logger.info("Disarm mode: Verify that a new Door Lock appears on the ADC websites");

        logger.info("*************************ZD_D_011*******************************");
        logger.info("Disarm mode: Verify that a Single Door Lock can be Unlocked / LockedTxt from a panel");

//        logger.info("*************************ZD_D_013*******************************");
//        logger.info("Disarm mode: Verify that a Panel UI changes when the Door Lock is manually Unlocked / LockedTxt");

        logger.info("*************************ZD_D_012*******************************");
        logger.info("Disarm mode: Verify that a Single Door Lock can be Unlocked / Locked from a panel at 5 meters");

        logger.info("************************ZD_D_014********************************");
        logger.info("Disarm mode: Verify that multiple door locks can be Locked / Unlocked from a panel");

        logger.info("*************************ZD_D_015*******************************");
        logger.info("Disarm mode: Verify that multiple door locks can be LockedTxt / Unlocked from a panel at 5 meters");

        logger.info("*************************ZD_D_016*******************************");
        logger.info("Disarm mode: Verify that the Door Locks can be LockedTxt / Unlocked from ADC User site");

        logger.info("*************************ZD_D_026*******************************");
        logger.info("Disarm mode: Verify that the battery status is displayed");

//        logger.info("*************************ZD_D_027*******************************");
//        logger.info("Disarm mode: Verify that the battery is low alert message will appear");

//        logger.info("*************************ZD_D_017*******************************");
//        logger.info("Disarm mode: Verify both panel and ADC recognize Door Lock Jam Alarm");

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

    public void Z_Wave_Door_Locks_Arm_Stay_Mode() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

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

    public void Z_Wave_Door_Locks_Arm_Away_Mode() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

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
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}

