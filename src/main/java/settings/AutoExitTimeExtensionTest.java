package settings;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.ExtentReport;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;

public class AutoExitTimeExtensionTest extends Setup {

    ExtentReports report;
    ExtentTest logs;
    ExtentTest test;
    Sensors sensors = new Sensors();

    public AutoExitTimeExtensionTest() throws Exception {
        SensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Exit_Time_Extension_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SettingsReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Zachary Pulling")
                .addSystemInfo("Software Version", softwareVersion());
        logs = report.startTest("Auto_Exit_Time_Extension_01");

        Thread.sleep(2000);
        logs.log(LogStatus.INFO, ("** Enable Auto Exit Time Extension -> Expected result = System extends timer 'til armed when sensor is opened"));
        Thread.sleep(2000);
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
                takeScreenshot();
            logs.log(LogStatus.FAIL, ("Failed: System is ARMED AWAY"));
        } catch (Exception e) {
            logs.log(LogStatus.PASS, ("Pass: System is NOT ARMED AWAY"));
        } finally {
        }
        Thread.sleep(60000);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        addToReport("Auto_Exit_Time_Extension_02");
        logs.log(LogStatus.INFO, ("** Enable Auto Exit Time Extension -> Expected result = System does not extend timer 'til armed when sensor is opened"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(3000);
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
        try {
            if (home.ArwAway_State.isDisplayed())
                takeScreenshot();
            logs.log(LogStatus.PASS, ("Pass: System is ARMED AWAY"));
        } catch (Exception e) {
            logs.log(LogStatus.FAIL, ("Failed: System is NOT ARMED AWAY"));
        } finally {
        }
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
        arming.Auto_Exit_Time_Extension.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        sensors.delete_from_primary(3);
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            logs.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            logs.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(logs);
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