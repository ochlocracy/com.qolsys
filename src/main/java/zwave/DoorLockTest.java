package zwave;

import adc.ADC;
import adc.AdcDealerPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import panel.*;
import utils.ConfigProps;
import utils.Setup;
import utils.ZTransmitter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static utils.ConfigProps.primary;
import static utils.ConfigProps.transmitter;

/* One door lock, default name, status unlocked */

public class DoorLockTest extends Setup {

    String pageName = "Door Lock Testing";
    Logger logger = Logger.getLogger(pageName);
    ADC adc = new ADC();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;


    public DoorLockTest() throws Exception {
        ZTransmitter.init();
        ConfigProps.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/QTMS_PowerG_Disarm.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest(test_area_name);
    }


    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/QTMS_PowerG_Disarm.html", false);
        log = report.startTest(test_case_name);
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
//        adc.webDriverSetUp();
        setupLogger(pageName);
    }
    @BeforeClass
    public void webDriver() {
        adc.webDriverSetUp();
    }

//    @Test(priority = 0)
//    public void CheckAllElementsOnDoorLockPage() throws Exception {
//        DoorLockPage lockPage = PageFactory.initElements(driver, DoorLockPage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        swipeToDoorLockPage(lockPage);
//        Thread.sleep(2000);
//        elementVerification(lockPage.keyIcon, "Key icon");
//        elementVerification(lockPage.doorLockName, "Door Lock name");
//        elementVerification(lockPage.doorLockStatus, "Door Lock status");
//        elementVerification(lockPage.refreshStatus, "Refresh status");
//        elementVerification(lockPage.Door_battery, "Door lock battery level");
//        elementVerification(lockPage.unlockAll, "Unlock All Doors");
//        elementVerification(lockPage.Lock_ALL, "Lock All Doors");
//    }
//
//
//    @Test(priority = 1)
//    public void Door_Lock_events() throws Exception {
//        DoorLockPage locksPage = PageFactory.initElements(driver, DoorLockPage.class);
//        swipeToDoorLockPage(locksPage);
//        for(int i=0; i<=5; i++) {
//        smart_click(locksPage.doorLockStatus, locksPage.Lock_ALL, "UNLOCKED", "LockedTxt");
//        Thread.sleep(7000);
//        statusVerification(locksPage.doorLockStatus, "LOCKED");
//        Thread.sleep(5000);
//        smart_click(locksPage.doorLockStatus, locksPage.unlockAll, "LOCKED", "Unlocked");
//        Thread.sleep(7000);
//        statusVerification(locksPage.doorLockStatus, "UNLOCKED");
//        Thread.sleep(5000);
//        }
//        System.out.println("Ending Timer");
////        timer.end();
//    }

//    @Test
//    public void adcTest() throws Exception {
//        AdcDealerPage dealerPage = PageFactory.initElements(driver, AdcDealerPage.class);
//        adc.newADCSessionEmPowerPage("5390018");
//        adc.adcGetZWaveEquipmentList();
//
//        Thread.sleep(5000);
//    }


//    public void smart_click(WebElement element, WebElement element2, String status, String message) {
//        if (element.getText().equals(status)) {
//            element2.click();
//            System.out.println("Door lock is successfully " + message);
//        } else {
//
//            System.out.println("Status is not as expected");
//        }
//    }
//    @Test
//    public void testTest() throws Exception{
//
//
//        adc.newADCSessionEmPowerPage("5390018");
//    }
//    @Test
//    public void Test () throws Exception{
//        adc.remoteAddMode("5390018");
//        TimeUnit.SECONDS.sleep(50);
//        adc.exitRemoteAddMode();
//    }







//************************Test Suite****************************************************************************



    @Test(priority = 1)
    public void preDoorLockTestSetup() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        //remove all zwave devices
        removeAllDevices();
        //change all zwave settings to default
        logger.info("reseting Zwave setting to factory");
        zwaveSettingReset();
        //Add 3 door window sensor and call it Front Door, back door, bathroom window
        addDwSensor(3);
        home.Home_button.click();
    }


    @Test(priority = 2)
    public void pairingTransmitter() throws Exception {
        localIncludeBridge();
    }

    @Test(priority = 3)
    public void disarmLocalDeviceParingTest() throws Exception {
        AdcDealerPage dealerPage = PageFactory.initElements(driver1, AdcDealerPage.class);
        //* add a way to store node numbers to names of device
        //Pair 2 door lock locally( name it stock "Front Door" and "Back Door")
        localZwaveAdd();
        logger.info("Paring Two Door locks with stock names, Front Door and Back Door");
        addStockNameFrontDoor();
        addStockNameBackDoor();
    }

    @Test(priority = 4)
    public void remoteAddParingTest() throws Exception{
        //pair 1 door lock from ADC( name custom name "Door Lock with node ID")
        AdcDealerPage dealerPage = PageFactory.initElements(adc.driver1, AdcDealerPage.class);
        adc.remoteAddMode("5390018");
        addRemoteZwaveDoorLock(1,"srg");
        logger.info("waiting for new device name");
        waitForTextInElement(adc.driver1,dealerPage.newlyAddDeviceName,"Door Lock", 130);
        adc.verifyOneDeviceAdded();
        adc.exitRemoteAddMode();
        Thread.sleep(4000);
    }







        //pair 1 door lock locally and expect max number failure

        //change max number setting with setters service call

        //pair other 3 lock with custom name with node id



//
//    @Test
//    public void disArmNameVerificationTest(){
//        //verify all name are correct on the panel
//
//        //verify all names are correct on ADC Dealer
//    }
//
//    @Test
//    public void disArmNameChangeTest(){
//        // Change stock name Front Door to custom name "Door Lock with # and Node ID" locally
//        //change 3rd door lock with custom named to stock name "Side Door" locally
//        // verify change on panel
//        // verify change on ADC Dealer site
//        // verify change on user site
//        // change side door lock name to "srg door" on user site
//        // verify side door lock changed to srg door on the panel
//    }
//
//    @Test
//    public void disArmUserCodeTest(){
//        //add longterm user code
//        //verify user code work on door lock
//        //add a user code with an expiration time
//        //verify user code works when active and doesn't work when deactivated
//    }

//    @Test
//    public void disArmLockActionTest() throws Exception{
//        DoorLockPage lockPage = PageFactory.initElements(driver,DoorLockPage.class);
//        swipeToDoorLockPage(lockPage);
//        System.out.println("At Door Lock Page");
//        //panel test
//        //lock all door locks one at a time and verify each change one at a time
//        //unlock all door locks one at a time and verify each change one at a time
//        //lock all door lock function and verify all
//        //unlock all door locks
//        //*ADC user site test
//        //lock all door locks one at a time
//        //verify each status change one at a time on user site
//        //*(transmitter only)
//        // unlock lock all door lock devices and verify the panel changes status.
//        //verify the user site reflects the new device status
//        //verify history events appear on panel event page
//    }

//    @Test
//    public void disArmRulesTest(){
//        //create rules for Front door lock to
//    }
//    @Test
//    public void armStayLockActionTest() throws Exception{
//        DoorLockPage lockPage = PageFactory.initElements(driver,DoorLockPage.class);
//        swipeToDoorLockPage(lockPage);
//        System.out.println("At Door Lock Page");
//        //panel test in arm stay
//        //lock all door locks one at a time and verify each change one at a time
//        //unlock all door locks one at a time and verify each change one at a time
//        //lock all door lock function and verify all locked
//        //unlock all door locks and verify all unlocked
//        //*ADC user site test
//        //lock all door locks one at a time
//        //verify each status change one at a time on user site
//        //*(transmitter only)
//        // unlock lock all door lock devices and verify the panel changes status.
//        //verify the user site reflects the new device status
//        //verify history events appear on panel event page
//    }

//    @Test
//    public void armStayRulesTest(){
//        //same rules as disArmRuleTest
//        //verify panel is in armStay
//    }

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
//    @AfterMethod
//    public void wedriverTearDown(){
//        adc.driver1.quit();
//    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        adc.driver1.quit();
        driver.quit();
        service.stop();
    }
}

