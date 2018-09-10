package updateProcess;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SanitySettings extends Setup {

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();
    String OFF = "00000000";

    public SanitySettings() throws Exception {
        SensorsActivity.init();
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Settings_Test1() throws Exception {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("Settings.Alarm_Photos");

        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);

        System.out.println("Verifying Alarm photo is taken when setting in enabled...");
        log.log(LogStatus.INFO, "Verifying Alarm photo is taken when setting in enabled...");
        //serv.set_ALARM_VIDEOS(00); alarm videos are the same service call as alarm photos
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
//        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        if (camera.Photo_lable.isDisplayed()) {
            System.out.println("Pass: Alarm photo is displayed");
            log.log(LogStatus.PASS, "Pass: Alarm photo is displayed");
        } else {
            System.out.println("Failed: Alarm photo is NOT displayed");
            log.log(LogStatus.FAIL, "Failed: Alarm photo is NOT displayed");
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        System.out.println("Verifying Alarm photo is NOT taken when setting in disabled...");
        log.log(LogStatus.INFO, "Verifying Alarm photo is NOT taken when setting in disabled...");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(1000);
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(1000);
//        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Alarms_photo.click();
        try {
            if (camera.Photo_lable.isDisplayed() && camera.Video_icon.isDisplayed())
                System.out.println("Pass: Alarm photo is not displayed");
            log.log(LogStatus.PASS, "Pass: Alarm photo is not displayed");
        } catch (Exception e) {
            log.log(LogStatus.FAIL, "Fail: Alarm photo is displayed");
            System.out.println("Fail: Alarm photo is displayed");
        } finally {
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Alarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void Settings_Test2() throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Allow_Master_Code_Access_Camera_Settings");

        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        System.out.println("Navigate to the setting page to enable the access to the Camera settings page using Master Code");
        log.log(LogStatus.INFO, "Navigate to the setting page to enable the access to the Camera settings page using Master Code");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.CAMERA_SETTINGS.isDisplayed()) {
                System.out.println("Pass: Camera settings icon is present");
                log.log(LogStatus.PASS, "Pass: Camera settings icon is present");
            } else {
                System.out.println("Failed: Camera settings icon is NOT present");
                log.log(LogStatus.FAIL, "Failed: Camera settings icon is NOT present");
            }
        } catch (Exception e) {
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        System.out.println("Verify Camera settings icon disappears after disabling the setting");
        log.log(LogStatus.INFO, "Verify Camera settings icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.CAMERA_SETTINGS.isDisplayed())
                System.out.println("Failed: Camera settings icon is present");
            log.log(LogStatus.FAIL, "Failed: Camera settings icon is present");
        } catch (Exception e) {
            System.out.println("Pass: Camera settings icon is NOT present");
            log.log(LogStatus.PASS, "Pass: Camera settings icon is NOT present");
        } finally {
        }
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 2)
    public void Settings_Test3() throws InterruptedException {

        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Allow_Master_Code_Access_Security_Arming");

        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        System.out.println("Navigate to the setting page to enable the access to the Security and Arming page using Master Code");
        log.log(LogStatus.INFO, "Navigate to the setting page to enable the access to the Security and Arming page using Master Code");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(3000);
        if (inst.SECURITY_AND_ARMING.isDisplayed()) {
            System.out.println("Pass: Security and Arming icon is present");
            log.log(LogStatus.PASS, "Pass: Security and Arming icon is present");
        } else {
            System.out.println("Failed: Security and Arming icon is NOT present");
            log.log(LogStatus.FAIL, "Failed: Security and Arming icon is NOT present");
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Verify Security and Arming icon disappears after disabling the setting");
        log.log(LogStatus.INFO, "Verify Security and Arming icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SECURITY_AND_ARMING.isDisplayed())
                System.out.println("Failed: Security and Arming icon is present");
            log.log(LogStatus.FAIL, "Failed: Security and Arming icon is present");
        } catch (Exception e) {
            System.out.println("Pass: Security and Arming icon is NOT present");
            log.log(LogStatus.PASS, "Pass: Security and Arming icon is NOT present");
        } finally {
        }
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void Settings_Test4() throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Allow_Master_Code_Access_Siren_Alarms");

        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        System.out.println("Navigate to the setting page to enable the access to the Siren and Alarms page using Master Code");
        log.log(LogStatus.INFO, "Navigate to the setting page to enable the access to the Siren and Alarms page using Master Code");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        if (inst.SIREN_AND_ALARMS.isDisplayed()) {
            System.out.println("Pass: Siren and Alarms icon is present");
            log.log(LogStatus.PASS, "Pass: Siren and Alarms icon is present");
        } else {
            System.out.println("Failed: Siren and Alarms icon is NOT present");
            log.log(LogStatus.FAIL, "Failed: Siren and Alarms icon is NOT present");
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        System.out.println("Verify Siren and Alarms icon disappears after disabling the setting");
        log.log(LogStatus.INFO, "Verify Siren and Alarms icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                System.out.println("Failed: Siren and Alarms icon is present");
            log.log(LogStatus.FAIL, "Failed: Siren and Alarms icon is present");
        } catch (Exception e) {
            System.out.println("Pass: Siren and Alarms icon is NOT present");
            log.log(LogStatus.PASS, "Pass: Siren and Alarms icon is NOT present");
        } finally {
        }
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 4)
    public void Settings_Test5() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Arm_Stay_No_Delay");
        serv.set_ARM_STAY_NO_DELAY_enable();

        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        System.out.println("Verify that Arm Stay - No Delay works when enabled");
        log.log(LogStatus.INFO, "Verify that Arm Stay - No Delay works when enabled");
        ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        log.log(LogStatus.PASS, "System is in Arm Stay Mode");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that Arm Stay - No Delay does not work when disabled");
        log.log(LogStatus.INFO, "Verify that Arm Stay - No Delay does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        ARM_STAY();
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                System.out.println("Failed: System is ARMED STAY");
            log.log(LogStatus.FAIL, "Failed: System is ARMED STAY");
        } catch (Exception e) {
            System.out.println("Pass: System is NOT ARMED STAY");
            log.log(LogStatus.PASS, "Pass: System is NOT ARMED STAY");
        } finally {
        }
        Thread.sleep(15000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 5)
    public void Settings_Test6() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Auto_Bypass");

        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Adding sensors...");
        serv.set_ARM_STAY_NO_DELAY_enable();
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        System.out.println("Verify that Auto Bypass works when enabled");
        log.log(LogStatus.INFO, "Verify that Auto Bypass works when enabled");
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        verifyArmstay();
        log.log(LogStatus.PASS, "Pass: sensors bypassed, system is in Arm Stay mode");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(3000);
        settings.Home_button.click();
        Thread.sleep(3000);
        System.out.println("Verify that Auto Bypass does not work when disabled");
        log.log(LogStatus.INFO, "Verify that Auto Bypass does not work when disabled");
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        home.DISARM.click();
        Thread.sleep(2000);
        home.ARM_STAY.click();
        Thread.sleep(2000);
        elementVerification(home.Bypass_message, "Bypass pop-up message");
        log.log(LogStatus.PASS, "Pass: Bypass message is displayed");
        Thread.sleep(2000);
        home.Bypass_OK.click();
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        verifyArmstay();
        log.log(LogStatus.PASS, "Pass: sensors bypassed, system is in Arm Stay mode");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Bypass.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 6)
    public void Settings_Test7() throws Exception {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("Settings.Auto_Exit_Time_Extention");

        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        System.out.println("Verify that Auto Exit Time Extension works when enabled");
        log.log(LogStatus.INFO, "Verify that Auto Exit Time Extension works when enabled");
        System.out.println("Adding sensors...");
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        ARM_AWAY(3);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        try {
            if (home.ArwAway_State.isDisplayed())
                System.out.println("Failed: System is ARMED AWAY");
            log.log(LogStatus.FAIL, "Failed: System is ARMED AWAY");
        } catch (Exception e) {
            System.out.println("Pass: System is NOT ARMED AWAY");
            log.log(LogStatus.PASS, "Pass: System is NOT ARMED AWAY");
        } finally {
        }
        Thread.sleep(60000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that Auto Exit Time Extension does not works when disabled");
        log.log(LogStatus.INFO, "Verify that Auto Exit Time Extension does not works when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_AWAY(3);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(10000);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: system is ARMED AWAY, Auto Exit Time Extension does not work");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 7)
    public void Settings_Test8() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Auto_Stay");
        int delay = 15;
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Adding sensors...");
        sensors.add_primary_call(3, 10, 6619296, 1);
        Thread.sleep(2000);
        System.out.println("Verify that Auto Stay works when enabled");
        log.log(LogStatus.INFO, "Verify that Auto Stay works when enabled");
        Thread.sleep(3000);
        System.out.println("Arm Away the system");
        ARM_AWAY(delay);
        verifyArmstay();
        log.log(LogStatus.PASS, "Pass: System is in ARMED STAY mode");
        home.DISARM.click();
        enterDefaultUserCode();
        System.out.println("Verify that Auto Stay does not works when disabled");
        log.log(LogStatus.INFO, "Verify that Auto Stay does not works when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        System.out.println("Arm Away the system");
        ARM_AWAY(delay);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in ARMED AWAY mode");
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 8)
    public void Settings_Test9() throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Dealer_Code");
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
        navigateToAdvancedSettingsPage();
        log.log(LogStatus.INFO, "Verify a Dealer code can be changed");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        user.Add_User_Name_field.clear();
        System.out.println("Changing Dealer name");
        user.Add_User_Name_field.sendKeys("NewDealer");
        user.Add_User_Code_field.clear();
        System.out.println("Changing Dealer password");
        user.Add_User_Code_field.sendKeys("5555");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
        user.User_Management_Save.click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        adv.USER_MANAGEMENT.click();
        logger.info("Verify Dealer name changed");
        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='NewDealer']")).isDisplayed());
        log.log(LogStatus.PASS, "Pass: new dealer name is displayed");
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        System.out.println("Verify old Dealer code does not work");
        log.log(LogStatus.INFO, "Verify old Dealer code does not work");
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            System.out.println("Pass: old Dealer code does not work");
            log.log(LogStatus.PASS, "Pass: old Dealer code does not work");
        }
        Thread.sleep(2000);
        System.out.println("Verify new Dealer code works");
        log.log(LogStatus.INFO, "Verify new Dealer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        System.out.println("Pass: new Dealer code works as expected");
        log.log(LogStatus.PASS, "Pass: new Dealer code works as expected");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Dealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("2222");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("2222");
        user.User_Management_Save.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 9)
    public void Settings_Test10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Disarm_Photos");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        System.out.println("Verifying Disarm photo is taken when setting in enabled...");
        log.log(LogStatus.INFO, "Verifying Disarm photo is taken when setting in enabled");
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Disarm_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            System.out.println("Pass: Disarm photo is displayed");
            log.log(LogStatus.PASS, "Pass: Disarm photo is displayed");
        } else {
            System.out.println("Failed: Disarm photo is NOT displayed");
            log.log(LogStatus.FAIL, "Failed: Disarm photo is NOT displayed");
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        System.out.println("Verifying Disarm photo is NOT taken when setting in disabled...");
        log.log(LogStatus.INFO, "Verifying Disarm photo is NOT taken when setting in disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Disarm_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                System.out.println("Failed: Disarm photo is displayed");
            log.log(LogStatus.FAIL, "Failed: Disarm photo is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Disarm photo is NOT displayed");
            log.log(LogStatus.PASS, "Pass: Disarm photo is NOT displayed");
        } finally {
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Disarm_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 10)
    public void Settings_Test11 (int type, int id, String Status, String Status2, String State, String State1, int line) throws Exception, InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Duress_Authentication");
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage cam = PageFactory.initElements(driver, PanelCameraPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);

        Thread.sleep(2000);
        System.out.println("Verify system can be DISARMED with Duress code");
        log.log(LogStatus.INFO, "Verify system can be DISARMED with Duress code");
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Duress']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        log.log(LogStatus.INFO, ("Change Duress Code -> Expected result = system can be disarmed with New Duress"));
        Thread.sleep(2000);
        adv.USER_MANAGEMENT.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/editImg"));
        Thread.sleep(2000);
        li_status1.get(1).click();
        Thread.sleep(1000);
        user.Add_User_Name_field.clear();
        logger.info("Changing Duress name");
        user.Add_User_Name_field.sendKeys("NewDuress");
        user.Add_User_Code_field.clear();
        logger.info("Changing Duress password");
        user.Add_User_Code_field.sendKeys("9998");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("9998");
//        driver.pressKeyCode(AndroidKeyCode.ENTER);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Eight.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            log.log(LogStatus.PASS, ("Pass: new Duress code works"));
        } else {
            takeScreenshot();
            log.log(LogStatus.FAIL, ("Failed: new Duress code did not worked"));
        }
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        List<WebElement> li_status2 = driver.findElements(By.id("com.qolsys:id/editImg"));
        Thread.sleep(2000);
        li_status2.get(1).click();
        Thread.sleep(1000);
        user.Add_User_Name_field.clear();
        logger.info("Changing Duress name");
        user.Add_User_Name_field.sendKeys("NewDuress");
        user.Add_User_Code_field.clear();
        logger.info("Changing Duress password");
        user.Add_User_Code_field.sendKeys("9998");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("9998");
//        driver.pressKeyCode(AndroidKeyCode.ENTER);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();

    }

    @Test(priority = 11)
    public void Settings_Test12() throws InterruptedException {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Installer_Code");
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
        System.out.println("Verify Installer Code can be modified");
        log.log(LogStatus.INFO, "Verify Installer Code can be modified");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();
        user.Add_User_Name_field.clear();
        System.out.println("Changing Installer name");
        user.Add_User_Name_field.sendKeys("NewInstall");
        user.Add_User_Code_field.clear();
        System.out.println("Changing Installer password");
        user.Add_User_Code_field.sendKeys("5555");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
        user.User_Management_Save.click();
        Thread.sleep(7000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        adv.USER_MANAGEMENT.click();
        System.out.println("Verify Installer name changed");
        log.log(LogStatus.INFO, "Verify Installer name changed");
        Assert.assertTrue(driver.findElement(By.xpath("//android.widget.TextView[@text='NewInstall']")).isDisplayed());
        log.log(LogStatus.PASS, "Pass: New Installer name is displayed");
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        System.out.println("Verify old Installer code does not work");
        log.log(LogStatus.INFO, "Verify old Installer code does not work");
        settings.ADVANCED_SETTINGS.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            System.out.println("Pass: old Installer code does not work");
            log.log(LogStatus.PASS, "Pass: old Installer code does not work");
        }
        Thread.sleep(2000);
        System.out.println("Verify new Installer code works");
        log.log(LogStatus.INFO, "Verify new Installer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        log.log(LogStatus.PASS, "Pass: new Installer code works as expected");
        System.out.println("Pass: new Installer code works as expected");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Installer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("1111");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("1111");
        user.User_Management_Save.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 12)
    public void Settings_Test13() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Keyfob_Alarm_Disarm");
        String disarm = "08 01";
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        System.out.println("Adding sensors...");
        sensors.add_primary_call(3, 4, 6619386, 102);
        Thread.sleep(2000);
        System.out.println("Verify that Keyfod Alarm Disarm does not work when disabled");
        log.log(LogStatus.INFO, "Verify that Keyfod Alarm Disarm does not work when disabled");
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        if (emergency.Emergency_sent_text.isDisplayed()) {
            System.out.println("Pass: Police Emergency is displayed");
            log.log(LogStatus.PASS, "Pass: Police Emergency is displayed");
        } else {
            System.out.println("Failed: Police Emergency is NOT displayed");
            log.log(LogStatus.FAIL, "Failed: Police Emergency is NOT displayed");
        }
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that Keyfod Alarm Disarm  works when enabled");
        log.log(LogStatus.INFO, "Verify that Keyfod Alarm Disarm  works when enabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        emergency.Police_icon.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        log.log(LogStatus.PASS, "Pass: System is disarmed by a keyfob");
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Alarm_Disarm.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 13)
    public void Settings_Test14() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Keyfob_Disarming");
        int delay = 15;
        String disarm = "08 01";
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelInfo_ServiceCalls service = PageFactory.initElements(driver, PanelInfo_ServiceCalls.class);
        System.out.println("Adding sensors...");
        service.set_AUTO_STAY(00);
        sensors.add_primary_call(3, 4, 6619386, 102);
        Thread.sleep(2000);
        System.out.println("Verify that Keyfob Disarming works when enabled");
        log.log(LogStatus.INFO, "Verify that Keyfob Disarming works when enabled");
        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        log.log(LogStatus.PASS, "Pass: system is disarmed by a keyfob from Arm Stay");
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyDisarm();
        log.log(LogStatus.PASS, "Pass: system is disarmed by a keyfob from Arm Away");
        Thread.sleep(2000);
        System.out.println("Verify that Keyfob Disarming does not work when disabled");
        log.log(LogStatus.INFO, "Verify that Keyfob Disarming does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyArmstay();
        log.log(LogStatus.PASS, "Pass: system is NOT disarmed by a keyfob from Arm Stay");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        ARM_AWAY(delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", disarm);
        Thread.sleep(2000);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: system is NOT disarmed by a keyfob from Arm Away");
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Disarming.click();
        Thread.sleep(2000);
        sensors.delete_from_primary(3);
        settings.Home_button.click();
        service.set_AUTO_STAY(01);
        Thread.sleep(2000);
    }

    @Test(priority = 14)
    public void Settings_Test15() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Instant_Arming");
        String armstay = "04 01";
        String armaway = "04 04";
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        System.out.println("Verify that Keyfob Instant Arming works when enabled");
        log.log(LogStatus.INFO, "Verify that Keyfob Instant Arming works when enabled");
        System.out.println("Adding sensors...");
        sensors.add_primary_call(3, 4, 6619386, 102);
        System.out.println("Arm Stay the system");
        Thread.sleep(3000);
        sensors.primaryCall("65 00 AF", armstay);
        Thread.sleep(5000);
        log.log(LogStatus.PASS, "Pass: System is in Arm Stay mode, no exit delay");
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        sensors.primaryCall("65 00 AF", armaway);
        Thread.sleep(4000);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in Arm Away mode, no exit delay");
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Verify that Keyfob Instant Arming does not work when disabled");
        log.log(LogStatus.INFO, "Verify that Keyfob Instant Arming does not work when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        System.out.println("Arm Stay the system");
        sensors.primaryCall("65 00 AF", armstay);
        Thread.sleep(1000);
        if (home.Countdown.isDisplayed()) {
            System.out.println("Pass: System is not ARMED STAY, countdown is displayed");
            log.log(LogStatus.PASS, "Pass: System is not ARMED STAY, countdown is displayed");
        }else{
            System.out.println("Fail: System is ARMED STAY");
            log.log(LogStatus.FAIL, "Fail: System is ARMED STAY, exit count down is not displayed");
        }
        Thread.sleep(10000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        System.out.println("Arm Away the system");
        sensors.primaryCall("65 00 AF", armaway);
        Thread.sleep(1000);
        if (home.Countdown.isDisplayed()) {
            System.out.println("Pass: System is not ARMED AWAY, countdown is displayed");
            log.log(LogStatus.PASS, "Pass: System is not ARMED AWAY, countdown is displayed");
        }else{
            System.out.println("Fail: System is ARMED AWAY");
            log.log(LogStatus.FAIL, "Fail: System is NOT ARMED AWAY, exit count down is displayed");
        }
        Thread.sleep(10000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        swipeVertical();
        arming.Keyfob_Instant_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @Test(priority = 15)
    public void Settings_Test16() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Panic_Disable");
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        Thread.sleep(1000);
        System.out.println("Verify panic disappears from the Emergency page when disabled");
        log.log(LogStatus.INFO, "Verify panic disappears from the Emergency page when disabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        log.log(LogStatus.INFO, "Disable Police Panic");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Police_icon.isDisplayed())
                System.out.println("Failed: Police Emergency is displayed");
            log.log(LogStatus.FAIL, "Failed: Police Emergency is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Police Emergency is NOT displayed");
            log.log(LogStatus.PASS, "Pass: Police Emergency is NOT displayed");
        } finally {
        }
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        log.log(LogStatus.INFO, "Disable Fire Panic");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Fire_icon.isDisplayed())
                System.out.println("Failed: Fire Emergency is displayed");
            log.log(LogStatus.FAIL, "Failed: Fire Emergency is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Fire Emergency is NOT displayed");
            log.log(LogStatus.PASS, "Pass: Fire Emergency is NOT displayed");
        } finally {
        }
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        log.log(LogStatus.INFO, "Disable Auxiliary Panic");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        settings.Emergency_button.click();
        try {
            if (emergency.Auxiliary_icon.isDisplayed())
                System.out.println("Failed: Auxiliary Emergency is displayed");
            log.log(LogStatus.FAIL, "Failed: Auxiliary Emergency is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Auxiliary Emergency is NOT displayed");
            log.log(LogStatus.PASS, "Auxiliary: Fire Emergency is NOT displayed");
        } finally {
        }
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Police_Panic.click();
        siren.Fire_Panic.click();
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 16)
    public void Settings_Test17() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Secure_Arming");
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        logger.info("Verify no code is required to Arm the system when setting is disabled");
        log.log(LogStatus.INFO, "Verify no code is required to Arm the system when setting is disabled");
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        verifyArmstay();
        log.log(LogStatus.PASS, "Pass: System is in Arm Stay mode, no password was required");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        arming.Secure_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        logger.info("Verify code is required to Arm the system when setting is enabled");
        log.log(LogStatus.INFO, "Verify code is required to Arm the system when setting is enabled");
        home.DISARM.click();
        home.ARM_STAY.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
            logger.info("Pass: code is requires to Arm the system");
            log.log(LogStatus.PASS, "Pass: code is requires to Arm the system");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        arming.Secure_Arming.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 17)
    public void Settings_Test18() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Secure_Delete_Images");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        System.out.println("Verifying deleting panel images requires valid code...");
        log.log(LogStatus.INFO, "Verifying deleting panel images requires valid code");
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            System.out.println("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
            System.out.println("Pass: Password is required to delete images");
            log.log(LogStatus.PASS, "Pass: password is required to delete images");
        } else {
            System.out.println("Failed: Password is NOT required to delete the image");
            log.log(LogStatus.FAIL, "Fail: password is NOT required to delete images");
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        System.out.println("Verifying deleting panel images does not require valid code...");
        log.log(LogStatus.INFO, "Verifying deleting panel images does not require a valid code");
        ARM_STAY();
        home.DISARM.click();
        enterDefaultUserCode();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Camera_delete.click();
        Thread.sleep(2000);
        if (camera.Camera_delete_title.isDisplayed()) {
            System.out.println("Delete pop-up");
        }
        camera.Camera_delete_yes.click();
        try {
            if (home.Enter_Code_to_Access_the_Area.isDisplayed())
                System.out.println("Failed: Password is required to delete the image");
            log.log(LogStatus.FAIL, "Failed: Password is required to delete images");
        } catch (Exception e) {
            System.out.println("Pass: Password is NOT required to delete the image");
            log.log(LogStatus.PASS, "Pass: Password is NOT required to delete images");
        } finally {
        }
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        set_cam.Secure_Delete_Images.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 18)
    public void Settings_Test19() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Settings_Potos");
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        System.out.println("Verifying settings photo is NOT taken when setting in disabled...");
        log.log(LogStatus.INFO, "Verifying settings photo is NOT taken when setting in disabled");
        deleteAllCameraPhotos();
        Thread.sleep(1000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        settings.Home_button.click();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Settings_photos.click();
        try {
            if (camera.Photo_lable.isDisplayed())
                System.out.println("Failed: Disarm photo is displayed");
            log.log(LogStatus.FAIL, "Failed: Disarm photo is displayed");
        } catch (Exception e) {
            System.out.println("Pass: Disarm photo is NOT displayed");
            log.log(LogStatus.PASS, "Pass: Disarm photo is NOT displayed");
        } finally {
        }
        System.out.println("Verifying settings photo is taken when setting in enabled...");
        log.log(LogStatus.INFO, "Verifying settings photo is taken when setting in enabled");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(1000);
        settings.Home_button.click();
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        camera.Settings_photos.click();
        if (camera.Photo_lable.isDisplayed()) {
            System.out.println("Pass: settings photo is displayed");
            log.log(LogStatus.PASS, "Pass: settings photo is displayed");
        } else {
            System.out.println("Failed: settings photo is NOT displayed");
            log.log(LogStatus.FAIL, "Failed: settings photo is NOT displayed");
        }
        camera.Camera_delete.click();
        camera.Camera_delete_yes.click();
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.CAMERA_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        set_cam.Settings_Photos.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(2000);
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
    public void driver_quit() throws InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}
