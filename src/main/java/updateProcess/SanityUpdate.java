package updateProcess;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
        servcall.set_SECURE_ARMING_disable();
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
        servcall.set_NORMAL_ENTRY_DELAY(10);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_EXIT_DELAY(11);
        Thread.sleep(one_sec);
        servcall.set_LONG_ENTRY_DELAY(12);
        Thread.sleep(one_sec);
        servcall.set_LONG_EXIT_DELAY(13);
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
        servcall.set_AUTO_UPLOAD_LOGS(OFF);
        Thread.sleep(one_sec);
        servcall.set_POWER_MANAGEMENT_ON_OFF_enable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_disable();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_TIMEOUTY_24h();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT();
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
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(500);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(500);
    }

    public void verify_setting(String setting, String call, String expected) throws IOException {
        String result = execCmd(ConfigProps.adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            log.log(LogStatus.PASS, "[Pass] " + setting + " has value: " + expected);
        else
            log.log(LogStatus.FAIL, "[Fail] " + setting + " has value: " + result + ". Expected:" + expected);
    }

    @BeforeClass
    public void setUp() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void settingsCheck() throws InterruptedException, IOException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", Software_Version());
        log = report.startTest("UpdateProcess.Settings");

        verify_setting("Media Volume", "36 i32 0 i32 0 i32 39 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("All Voice Prompts", "37 i32 0 i32 0 i32 42 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("All Chimes", "37 i32 0 i32 0 i32 46 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("All Trouble Beeps", "37 i32 0 i32 0 i32 111 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Fire Safety Device", "37 i32 0 i32 0 i32 146 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("WiFi", "37 i32 0 i32 0 i32 32 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Secure Delete Images", "37 i32 0 i32 0 i32 104 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Disarm Photo", "37 i32 0 i32 0 i32 102 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Alarm Videos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Alarm Photos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("settings Photos", "37 i32 0 i32 0 i32 143 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Duress Authentication", "37 i32 0 i32 0 i32 61 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Secure Arming", "37 i32 0 i32 0 i32 35 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("No Arming On Low Battery", "37 i32 0 i32 0 i32 36 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Auto Bypass", "37 i32 0 i32 0 i32 19 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Auto Stay", "37 i32 0 i32 0 i32 20 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Arm Stay No Delay", "37 i32 0 i32 0 i32 21 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Auto Exit Time Extension", "37 i32 0 i32 0 i32 84 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Keyfob Alarm Disarm", "37 i32 0 i32 0 i32 129 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Keyfob Disarming", "37 i32 0 i32 0 i32 134 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Normal Entry Delay", "36 i32 0 i32 0 i32 15 i32 0 i32 0", "00000021");
        Thread.sleep(one_sec);
        verify_setting("Normal Exit Delay", "36 i32 0 i32 0 i32 16 i32 0 i32 0", "00000039");
        Thread.sleep(one_sec);
        verify_setting("Long Entry Delay", "36 i32 0 i32 0 i32 114 i32 0 i32 0", "00000071");
        Thread.sleep(one_sec);
        verify_setting("Long Exit Delay", "36 i32 0 i32 0 i32 115 i32 0 i32 0", "00000075");
        Thread.sleep(one_sec);
        verify_setting("Siren Disable", "37 i32 0 i32 0 i32 14 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Fire Verification", "37 i32 0 i32 0 i32 100 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Severe Weather Siren Warning", "37 i32 0 i32 0 i32 103 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Dialer Delay", "36 i32 0 i32 0 i32 12 i32 0 i32 0", "00000017");
        Thread.sleep(one_sec);
        verify_setting("Siren Timeout", "36 i32 0 i32 0 i32 13 i32 0 i32 0", "000001a4");
        Thread.sleep(one_sec);
        verify_setting("Water Freeze Alarm", "37 i32 0 i32 0 i32 122  i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Police Panic", "37 i32 0 i32 0 i32 131 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Fire Panic", "37 i32 0 i32 0 i32 132 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Auxillary Panic", "37 i32 0 i32 0 i32 133 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Auto Upload Logs", "37 i32 0 i32 0 i32 90 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Power Management On/Off", "37 i32 0 i32 0 i32 73 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("SIA Power Restoration", "37 i32 0 i32 0 i32 74 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Loss of Supervisory Signals for Non-emergency sensors", "36 i32 0 i32 0 i32 31 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verify_setting("Loss of Supervisory Signals for Emergency sensors", "36 i32 0 i32 0 i32 118 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verify_setting("Cell Signal Timeout", "36 i32 0 i32 0 i32 101 i32 0 i32 0", "00000019");
        Thread.sleep(one_sec);
        verify_setting("SIA Limits", "37 i32 0 i32 0 i32 37 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("RF Jam Detect", "37 i32 0 i32 0 i32 25 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Open/Close Reports for Auto Learn", "37 i32 0 i32 0 i32 127 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verify_setting("Bluetooth", "37 i32 0 i32 0 i32 142 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Bluetooth Disarm", "37 i32 0 i32 0 i32 138 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Bluetooth Disarm Timeout", "36 i32 0 i32 0 i32 139 i32 0 i32 0", "0000001e");
        Thread.sleep(one_sec);
        verify_setting("Allow Master Code to Access Camera settings", "37 i32 0 i32 0 i32 107 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Allow Master Code to Access Security and Arming settings", "37 i32 0 i32 0 i32 106 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verify_setting("Allow Master Code to Access Siren and Alarms settings", "37 i32 0 i32 0 i32 105 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        Thread.sleep(5000);
        System.out.println("Done, setting default settings");
        setDefaultSettings();
        Thread.sleep(5000);
    }

    @Test(priority = 1)
    public void sensorsCheck() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.Sensors");

        System.out.println("Open-Close contact sensors");
        Emergency_Page emergency = PageFactory.initElements(driver, Emergency_Page.class);
        Contact_Us contact = PageFactory.initElements(driver, Contact_Us.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Thread.sleep(2000);
        log.log(LogStatus.INFO, "Verify sensors activity");
        log.log(LogStatus.INFO, "Activate DW sensors");
        open_close("65 00 0A");
        open_close("65 00 1A");
        open_close("65 00 2A");
        open_close("65 00 3A");
        open_close("65 00 4A");
        open_close("65 00 5A");
        enter_default_user_code();
        Thread.sleep(1000);
        open_close("65 00 6A");
        enter_default_user_code();
        Thread.sleep(1000);
        open_close("65 00 7A");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: DW sensors behavior is as expected");
        System.out.println("Activate motion sensors");
        log.log(LogStatus.INFO, "Activate motion sensors");
        sensors.primary_call("55 00 44", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primary_call("55 00 54", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primary_call("55 00 64", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primary_call("55 00 74", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        sensors.primary_call("55 00 84", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: DW sensors behavior is as expected");

        System.out.println("Activate smoke sensors");
        log.log(LogStatus.INFO, "Activate smoke sensor");
        sensors.primary_call("67 00 22", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: smoke sensor behavior is as expected");

        System.out.println("Activate CO sensors");
        log.log(LogStatus.INFO, "Activate CO sensor");
        sensors.primary_call("75 00 AA", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: CO sensor behavior is as expected");

        System.out.println("Activate glassbreak sensors");
        log.log(LogStatus.INFO, "Activate glassbreak sensors");
        sensors.primary_call("67 00 99", SensorsActivity.ACTIVATE);
        sensors.primary_call("67 00 99", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        sensors.primary_call("67 00 39", SensorsActivity.ACTIVATE);
        sensors.primary_call("67 00 39", SensorsActivity.RESTORE);
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
        sensors.primary_call("66 00 C9", SensorsActivity.ACTIVATE);
        sensors.primary_call("66 00 C9", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        sensors.primary_call("66 00 D9", SensorsActivity.ACTIVATE);
        sensors.primary_call("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: IQShock sensors behavior is as expected");

        System.out.println("Activate freeze sensor");
        log.log(LogStatus.INFO, "Activate Freeze sensor");
        sensors.primary_call("73 00 1A", SensorsActivity.ACTIVATE);
        sensors.primary_call("73 00 1A", SensorsActivity.RESTORE);
        Thread.sleep(1000);
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: freeze sensor behavior is as expected");

        System.out.println("Activate heat sensor");
        log.log(LogStatus.INFO, "Activate heat sensor");
        sensors.primary_call("75 00 26", SensorsActivity.ACTIVATE);
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: heat sensor behavior is as expected");

        System.out.println("Activate water sensors");
        log.log(LogStatus.INFO, "Activate water sensor");
        sensors.primary_call("75 11 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: water sensor behavior is as expected");

        System.out.println("Activate keyfobs");
        log.log(LogStatus.INFO, "Activate keyfobs");
        sensors.primary_call("65 00 AF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);

        sensors.primary_call("65 00 BF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);

        sensors.primary_call("65 00 CF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: keyfobs behavior is as expected");

        System.out.println("Activate keypad sensors");
        log.log(LogStatus.INFO, "Activate keypads");
        sensors.primary_call("85 00 AF", SensorsActivity.OPEN);
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);

        sensors.primary_call("85 00 BF", "04 04");
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("85 00 BF", "08 01");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: keypads behavior is as expected");

        System.out.println("Activate medical pendants");
        log.log(LogStatus.INFO, "Activate medical pendants");
        sensors.primary_call("61 12 13", "03 01");
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);

        sensors.primary_call("61 12 23", "03 01");
        Thread.sleep(1000);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: medical pendants behavior is as expected");

        System.out.println("Activate doorbell sensor");
        log.log(LogStatus.INFO, "Activate doorbell sensor");
        open_close("61 BD AA");
        Thread.sleep(1000);
        log.log(LogStatus.PASS, "Pass: doorbell sensor behavior is as expected");

        contact.acknowledge_all_alerts();
        swipe_left();
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void verifyNewUserCodeWorks() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.UserCode");
        System.out.println("Verifying a new user code is working correctly");
        log.log(LogStatus.INFO, "Verifying a new user code is working correctly");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Five.click();
        home.Six.click();
        home.Four.click();
        home.Three.click();
        Thread.sleep(1000);
        verify_disarm();
        log.log(LogStatus.PASS, "Pass: new user code is working correctly");
    }

    @Test(priority = 3)
    public void verifyNewMasterCodeWorks() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.MasterCode");
        System.out.println("Verifying a new master code is working correctly");
        log.log(LogStatus.INFO, "Verifying a new master code is working correctly");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Three.click();
        home.Three.click();
        home.Three.click();
        home.One.click();
        Thread.sleep(1000);
        verify_disarm();
        log.log(LogStatus.PASS, "Pass: new master code is working correctly");
    }

    @Test(priority = 4)
    public void verifyNewGuestCodeWorks() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("UpdateProcess.GuestCode");
        System.out.println("Verifying a new guest code is working correctly");
        log.log(LogStatus.INFO, "Verifying a new guest code is working correctly");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Eight.click();
        home.Eight.click();
        home.Zero.click();
        home.Zero.click();
        Thread.sleep(1000);
        verify_disarm();
        log.log(LogStatus.PASS, "Pass: new guest code is working correctly");
    }

    @Test(priority = 5)
    public void deleteNewUsers() throws Exception {
        User_Management_Page user_m = PageFactory.initElements(driver, User_Management_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        navigateToUserManagementPage();
        List<WebElement> delete = driver.findElements(By.id("com.qolsys:id/deleteImg"));
        for (int i = 3; i > 0; i--) {
            delete.get(1).click();
            user_m.User_Management_Delete_User_Ok.click();
        }
        Thread.sleep(1000);
        home.Home_button.click();
    }

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
        driver.quit();
        for (int i = 3; i < 36; i++) {
            delete_from_primary(i);
        }
    }
}
