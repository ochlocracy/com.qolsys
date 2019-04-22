package qtmsSRF;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.*;
import sensors.Sensors;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;

    public class SettingsSRF extends Setup {

        ExtentReports report;
        ExtentTest log;
        ExtentTest test;
        Sensors sensors = new Sensors();
        PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();
        String OFF = "00000000";

        public SettingsSRF() throws Exception {
            ConfigProps.init();
            SensorsActivity.init();
        }

        @BeforeClass
        public void setUp() throws Exception {
            setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        }

        @Test(priority = 1)
        public void Settings_Test1() throws Exception {
            String file = projectPath + "/extent-config.xml";
            report = new ExtentReports(projectPath + "/Report/SRFreport.html", false);
            report.loadConfig(new File(file));
            report
                    .addSystemInfo("User Name", "Becky Jameson")
                    .addSystemInfo("Software Version", softwareVersion());
            log = report.startTest("QTMS.Auto_Exit_Time_Extension");

            SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
            SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
            AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
            InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
            HomePage home = PageFactory.initElements(driver, HomePage.class);
            Thread.sleep(2000);
            System.out.println("Verify that Auto Exit Time Extension works when enabled");
            log.log(LogStatus.INFO, "Verify that Auto Exit Time Extension works when enabled");
            //do check for if its enabled. Needs a checkbox check
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
            }
            Thread.sleep(50000);
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
            Thread.sleep(14000);
            try {
                if (home.ArwAway_State.isDisplayed())
                    System.out.println("Pass: System is ARMED AWAY");
                log.log(LogStatus.PASS, "Pass: system is ARMED AWAY, Auto Exit Time Extension does not work");
            } catch (Exception e) {
                System.out.println("Fail: System is NOT ARMED AWAY");
                log.log(LogStatus.FAIL, "Fail: System is NOT ARMED AWAY");
            }
            Thread.sleep(14000);
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
            swipeVertical(); //sometimes swipe is good some times too far
            arming.Auto_Exit_Time_Extension.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            sensors.delete_from_primary(3);
            Thread.sleep(2000);
        }

        @Test(priority = 2)
        public void Settings_Test2() throws Exception {
            report = new ExtentReports(projectPath + "/Report/SRFreport.html", false);
            log = report.startTest("QTMS.Auto_Bypass");

            SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
            SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
            AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
            InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
            HomePage home = PageFactory.initElements(driver, HomePage.class);
            System.out.println("Adding sensors...");
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
            Thread.sleep(4000);
            if (home.Message.isDisplayed()) {
                log.log(LogStatus.PASS, "Pass: Bypass message is displayed");
            } else
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
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(1000);
            arming.Auto_Bypass.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(1000);
            sensors.delete_from_primary(3);
            Thread.sleep(2000);
        }

        @Test(priority = 3)
        public void Settings_Test3() throws Exception {
            report = new ExtentReports(projectPath + "/Report/SRFreport.html", false);
            log = report.startTest("QTMS.Keyfob_Disarming");
            int delay = 15;
            String disarm = "08 01";
            SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
            SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
            AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
            InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
            HomePage home = PageFactory.initElements(driver, HomePage.class);
            PanelInfo_ServiceCalls service = PageFactory.initElements(driver, PanelInfo_ServiceCalls.class);
            System.out.println("Adding sensors...");
            //service.set_AUTO_STAY(00);
            sensors.add_primary_call(3, 4, 6619386, 102);
            Thread.sleep(2000);
            System.out.println("Verify that Keyfob Disarming works when enabled");
            log.log(LogStatus.INFO, "Verify that Keyfob Disarming works when enabled");
            //cant check if box is clicked, the text doesn't change"
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