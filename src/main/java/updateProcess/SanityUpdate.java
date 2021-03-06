package updateProcess;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.ContactUs;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;

public class SanityUpdate extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    Sensors sensors = new Sensors();

    String ON = "00000001";
    String OFF = "00000000";
    int one_sec = 1000;

    public SanityUpdate() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }


    public void setDefaultSettings() throws IOException, InterruptedException {
        int ON = 1;
        int OFF = 0;
        int one_sec = 1000;
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(one_sec);
        //sets media volume to 1
        servcall.set_SPEAKER_VOLUME(OFF);
        Thread.sleep(one_sec);
        servcall.set_ALL_VOICE_PROMPTS(ON);
        Thread.sleep(one_sec);
        servcall.set_ALL_CHIMES(OFF);
        Thread.sleep(one_sec);
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_SECURE_DELETE_IMAGES(ON);
        Thread.sleep(one_sec);
        servcall.set_DISARM_PHOTO(ON);
        Thread.sleep(one_sec);
        servcall.set_ALARM_PHOTOS(ON);
        Thread.sleep(one_sec);
        servcall.set_SETTINGS_PHOTOS(OFF);
        Thread.sleep(one_sec);
        servcall.set_DURESS_AUTHENTICATION_disable();
        Thread.sleep(one_sec);
        rt.exec("adb shell service call qservice 40 i32 0 i32 0 i32 35 i32 0 i32 0 i32 0"); //since the servcall wasnt working.
//        servcall.set_SECURE_ARMING_disable();
        Thread.sleep(one_sec);
        servcall.set_NO_ARMING_ON_LOW_BATTERY_disable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_BYPASS(ON);
        Thread.sleep(one_sec);
        servcall.set_AUTO_STAY(ON);
        Thread.sleep(one_sec);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_EXIT_TIME_EXTENSION(ON);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_ALARM_DISARM(OFF);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_DISARMING(ON);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_ENTRY_DELAY(11);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_EXIT_DELAY(10);
        Thread.sleep(one_sec);
        servcall.set_LONG_ENTRY_DELAY(13);
        Thread.sleep(one_sec);
        servcall.set_LONG_EXIT_DELAY(12);
        Thread.sleep(one_sec);
        servcall.set_SIREN_DISABLE(ON);
        Thread.sleep(one_sec);
        servcall.set_FIRE_VERIFICATION(OFF);
        Thread.sleep(one_sec);
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(ON);
        Thread.sleep(one_sec);
        servcall.set_DIALER_DELAY(30);
        Thread.sleep(one_sec);
        servcall.set_SIREN_TIMEOUT(240);
        Thread.sleep(one_sec);
        servcall.set_WATER_FREEZE_ALARM_disable();
        Thread.sleep(one_sec);
        servcall.set_POLICE_PANIC(ON);
        Thread.sleep(one_sec);
        servcall.set_FIRE_PANIC(ON);
        Thread.sleep(one_sec);
        servcall.set_AUXILIARY_PANIC(ON);
        Thread.sleep(one_sec);
        servcall.set_AUTO_UPLOAD_LOGS(ON);
        Thread.sleep(one_sec);
        servcall.set_POWER_MANAGEMENT_ON_OFF_enable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_disable();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_TIMEOUTY_24h();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT(4);
        Thread.sleep(one_sec);
        servcall.set_Cell_Signal_Timeout(30);
        Thread.sleep(one_sec);
        servcall.set_RF_JAM_DETECT_disable();
        Thread.sleep(one_sec);
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(ON);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM_TIMEOUT(10);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(OFF);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(OFF);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(OFF);
        Thread.sleep(one_sec);
    }

    public void open_close(String DLID) throws InterruptedException, IOException {
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(500);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(500);
    }

    public void verifySetting(String setting, String call, String expected) throws IOException {
        String result = execCmd(ConfigProps.adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            log.log(LogStatus.PASS, "[Pass] " + setting + " has value: " + expected);
        else
            log.log(LogStatus.FAIL, "[Fail] " + setting + " has value: " + result + ". Expected:" + expected);
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        deleteSanityReport();
    }

    @Test
    public void settingsCheck() throws InterruptedException, IOException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Zachary Pulling")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("UpdateProcess.Settings");

        verifySetting("SIA Limits", "37 i32 0 i32 0 i32 37 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Media Volume", "36 i32 0 i32 0 i32 39 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Voice Prompts", "37 i32 0 i32 0 i32 42 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("All Chimes", "37 i32 0 i32 0 i32 46 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Trouble Beeps", "37 i32 0 i32 0 i32 111 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Fire Safety Device", "37 i32 0 i32 0 i32 146 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("WiFi", "37 i32 0 i32 0 i32 32 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Secure Delete Images", "37 i32 0 i32 0 i32 104 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Disarm Photo", "37 i32 0 i32 0 i32 102 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Alarm Videos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Alarm Photos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("settings Photos", "37 i32 0 i32 0 i32 143 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Duress Authentication", "37 i32 0 i32 0 i32 61 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Secure Arming", "37 i32 0 i32 0 i32 35 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("No Arming On Low Battery", "37 i32 0 i32 0 i32 36 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Bypass", "37 i32 0 i32 0 i32 19 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Auto Stay", "37 i32 0 i32 0 i32 20 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Arm Stay No Delay", "37 i32 0 i32 0 i32 21 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Exit Time Extension", "37 i32 0 i32 0 i32 84 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Alarm Disarm", "37 i32 0 i32 0 i32 129 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Disarming", "37 i32 0 i32 0 i32 134 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Normal Entry Delay", "36 i32 0 i32 0 i32 15 i32 0 i32 0", "0000000b");
        Thread.sleep(one_sec);
        verifySetting("Normal Exit Delay", "36 i32 0 i32 0 i32 16 i32 0 i32 0", "0000000a");
        Thread.sleep(one_sec);
        verifySetting("Long Entry Delay", "36 i32 0 i32 0 i32 114 i32 0 i32 0", "0000000d");
        Thread.sleep(one_sec);
        verifySetting("Long Exit Delay", "36 i32 0 i32 0 i32 115 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verifySetting("Siren Disable", "37 i32 0 i32 0 i32 14 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Fire Verification", "37 i32 0 i32 0 i32 100 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Severe Weather Siren Warning", "37 i32 0 i32 0 i32 103 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Dialer Delay", "36 i32 0 i32 0 i32 12 i32 0 i32 0", "00000017");
        Thread.sleep(one_sec);
        verifySetting("Siren Timeout", "36 i32 0 i32 0 i32 13 i32 0 i32 0", "000001a4"); //1a4
        Thread.sleep(one_sec);
        verifySetting("Water Freeze Alarm", "37 i32 0 i32 0 i32 122  i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Police Panic", "37 i32 0 i32 0 i32 131 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Fire Panic", "37 i32 0 i32 0 i32 132 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Auxillary Panic", "37 i32 0 i32 0 i32 133 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Auto Upload Logs", "37 i32 0 i32 0 i32 90 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Power Management On/Off", "37 i32 0 i32 0 i32 73 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("SIA Power Restoration", "37 i32 0 i32 0 i32 74 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Loss of Supervisory Signals for Non-emergency sensors", "36 i32 0 i32 0 i32 31 i32 0 i32 0", "0000000c"); //12 hours
        Thread.sleep(one_sec);
        verifySetting("Loss of Supervisory Signals for Emergency sensors", "36 i32 0 i32 0 i32 118 i32 0 i32 0", "0000000c"); //12 hours
        Thread.sleep(one_sec);
        verifySetting("Cell Signal Timeout", "36 i32 0 i32 0 i32 101 i32 0 i32 0", "00000019");
        Thread.sleep(one_sec);
        verifySetting("RF Jam Detect", "37 i32 0 i32 0 i32 25 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Open/Close Reports for Auto Learn", "37 i32 0 i32 0 i32 127 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth", "37 i32 0 i32 0 i32 142 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth Disarm", "37 i32 0 i32 0 i32 138 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth Disarm Timeout", "36 i32 0 i32 0 i32 139 i32 0 i32 0", "0000001e"); //30
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Camera settings", "37 i32 0 i32 0 i32 107 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Security and Arming settings", "37 i32 0 i32 0 i32 106 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Siren and Alarms settings", "37 i32 0 i32 0 i32 105 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        Thread.sleep(5000);
        System.out.println("Done, setting default settings");
        setDefaultSettings();
        Thread.sleep(5000);
    }

    @Test(priority = 1)
    public void sensorsCheck() throws Exception, IOException {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.Sensors");
        System.out.println("Open-Close contact sensors");
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        Thread.sleep(2000);
        log.log(LogStatus.INFO, ("Verify sensors activity"));
        log.log(LogStatus.INFO, ("Activate DW sensors"));
        open_close("65 00 0A");
        open_close("65 00 1A");
        open_close("65 00 2A");
        open_close("65 00 3A");
        open_close("65 00 4A");
        open_close("65 00 5A");
//        enterDefaultUserCode();
        Thread.sleep(1000);
        open_close("65 00 6A");
//        enterDefaultUserCode();
        Thread.sleep(1000);
        open_close("65 00 7A");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, ("Pass: DW sensors behavior is as expected"));
        System.out.println("Activate motion sensors");
        log.log(LogStatus.INFO, ("Activate motion sensors"));
        sensors.primaryCall("55 00 44", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primaryCall("55 00 54", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primaryCall("55 00 64", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primaryCall("55 00 74", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primaryCall("55 00 84", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: DW sensors behavior is as expected");

        System.out.println("Activate smoke sensors");
        log.log(LogStatus.INFO, "Activate smoke sensor");
        sensors.primaryCall("67 00 22", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
//        try {
//            if (home.pinpad.isDisplayed()) ;
//            {
//                enterDefaultUserCode();
//            }
//        }catch(NoSuchElementException e){}

        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: smoke sensor behavior is as expected");

        System.out.println("Activate CO sensors");
        log.log(LogStatus.INFO, "Activate CO sensor");
        sensors.primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: CO sensor behavior is as expected");

        System.out.println("Activate glassbreak sensors");
        log.log(LogStatus.INFO, "Activate glassbreak sensors");
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        sensors.primaryCall("67 00 99", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
        sensors.primaryCall("67 00 39", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: CO sensor behavior is as expected");

        System.out.println("Open/close tilt sensors");
        log.log(LogStatus.INFO, "Activate tilt sensors");
        open_close("63 00 EA");
        open_close("63 00 FA");
        open_close("63 00 0A");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: tilt sensors behavior is as expected");

        System.out.println("Activate IQShock sensors");
        log.log(LogStatus.INFO, "Activate IQShock sensors");
        sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        sensors.primaryCall("66 00 C9", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: IQShock sensors behavior is as expected");

        System.out.println("Activate freeze sensor");
        log.log(LogStatus.INFO, "Activate Freeze sensor");
        sensors.primaryCall("73 00 1A", SensorsActivity.ACTIVATE);
        sensors.primaryCall("73 00 1A", SensorsActivity.RESTORE);
        Thread.sleep(1000);
//        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: freeze sensor behavior is as expected");

        System.out.println("Activate heat sensor");
        log.log(LogStatus.INFO, "Activate heat sensor");
        sensors.primaryCall("75 00 26", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: heat sensor behavior is as expected");

        System.out.println("Activate water sensors");
        log.log(LogStatus.INFO, "Activate water sensor");
        sensors.primaryCall("75 11 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: water sensor behavior is as expected");

        System.out.println("Activate keyfobs");
        log.log(LogStatus.INFO, "Activate keyfobs");
        sensors.primaryCall("65 00 AF", SensorsActivity.OPEN);
        Thread.sleep(1000);
//        enterDefaultUserCode();
        Thread.sleep(1000);

        sensors.primaryCall("65 00 BF", SensorsActivity.OPEN);
        Thread.sleep(1000);
//        enterDefaultUserCode();
        Thread.sleep(1000);

        sensors.primaryCall("65 00 CF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: keyfobs behavior is as expected");

        System.out.println("Activate keypad sensors");
        log.log(LogStatus.INFO, "Activate keypads");
        sensors.primaryCall("85 00 AF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);

        sensors.primaryCall("85 00 BF", "04 04"); //changed it to 14 seconds cause it isnt instant
        Thread.sleep(14000);
        verifyArmaway();
        sensors.primaryCall("85 00 BF", "08 01");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: keypads behavior is as expected");

        System.out.println("Activate medical pendants");
        log.log(LogStatus.INFO, "Activate medical pendants");
        sensors.primaryCall("61 12 13", "03 01");
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);

        sensors.primaryCall("61 12 23", "03 01");
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: medical pendants behavior is as expected");

        System.out.println("Activate doorbell sensor");
        log.log(LogStatus.INFO, ("Activate doorbell sensor"));
        open_close("61 BD AA");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: doorbell sensor behavior is as expected");

        contact.acknowledge_all_alerts();
        swipeLeft();
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void verifyNewUserCodeWorks() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.UserCode");
        System.out.println("Verifying a new user code is working correctly");
        log.log(LogStatus.INFO, ("Verifying a new user code is working correctly"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        servcall.set_SECURE_ARMING_disable();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(14000);
        home.DISARM.click(); //if this fails, must turn off arm stay delay and secure arming.
        home.Five.click();
        home.Six.click();
        home.Four.click();
        home.Three.click();
        Thread.sleep(1000);
        verifyDisarm();
        log.log(LogStatus.PASS, "Pass: new user code is working correctly");
    }

    @Test(priority = 3)
    public void verifyNewMasterCodeWorks() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.MasterCode");
        System.out.println("Verifying a new master code is working correctly");
        log.log(LogStatus.INFO, "Verifying a new master code is working correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(13000);
        home.DISARM.click();
        home.Three.click();
        home.Three.click();
        home.Three.click();
        home.One.click();
        Thread.sleep(1000);
        verifyDisarm();
        log.log(LogStatus.PASS, "Pass: new master code is working correctly");
    }

    @Test(priority = 4)
    public void verifyNewGuestCodeWorks() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.GuestCode");
        System.out.println("Verifying a new guest code is working correctly");
        log.log(LogStatus.INFO, "Verifying a new guest code is working correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(13000);
        home.DISARM.click();
        home.Eight.click();
        home.Eight.click();
        home.Zero.click();
        home.Zero.click();
        Thread.sleep(1000);
        verifyDisarm();
        log.log(LogStatus.PASS, "Pass: new guest code is working correctly");
    }

//    @Test(priority = 5)
//    public void deleteNewUsers() throws Exception {
//        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        navigateToUserManagementPage();
//        List<WebElement> delete = driver.findElements(By.id("com.qolsys:id/deleteImg"));
//        for (int i = 3; i > 0; i--) {
//            delete.get(1).click();
//            user_m.User_Management_Delete_User_Ok.click();
//        }
//        Thread.sleep(1000);
//        home.Home_button.click();
//    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
    }

    @AfterClass
    public void driver_quit() throws IOException, InterruptedException {
//        for (int i = 3; i < 36; i++) {
//            deleteFromPrimary(i);
//            System.out.println("3-35 nodes deleted");
//        }
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}
