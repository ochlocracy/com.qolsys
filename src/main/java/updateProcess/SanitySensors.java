package updateProcess;

import adc.ADC;
import adcSanityTests.RetryAnalizer;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.Emergency_Page;
import panel.Home_Page;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SanitySensors extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public SanitySensors() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    public void ADC_verification(String string, String string1) throws IOException, InterruptedException {
        String[] message = {string, string1};

        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        Thread.sleep(10000);
        for (int i = 0; i < message.length; i++) {
            try {
                WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                Assert.assertTrue(history_message.isDisplayed());
                {
                    System.out.println("Pass: message is displayed " + history_message.getText());
                    log.log(LogStatus.PASS, "Pass: message is displayed " + history_message.getText());
                }
            } catch (Exception e) {
                System.out.println("***No such element found!***");
                log.log(LogStatus.FAIL, "***No such element found!***");
            }
        }
    }

    @BeforeClass
    public void setUp() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longEntryDelay);
        setArmStay_NoDelay("Disable");
        setAutoStay("Disable");
    }

    @BeforeMethod
    public void setUpWeb() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", Software_Version());
        log = report.startTest("Sensors.SmokeSensorArmStay");

        Thread.sleep(2000);
        log.log(LogStatus.INFO, "Adding sensors");
        add_primary_call(26, 26, 6750242, 5);
        add_primary_call(3, 10, 6488238, 16);
        add_primary_call(4, 12, 6488239, 16);
        add_primary_call(5, 25, 6488224, 16);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Activate_Restore_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        System.out.println("ArmStay -Activate/Restore Group " + group + " smoke sensor during exit delay");
        log.log(LogStatus.INFO, "ArmStay -Activate/Restore Group " + group + " smoke sensor during exit delay");
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        ARM_STAY();
        Thread.sleep(5000);
        System.out.println("Activate/Restore a sensor");
        sensors.primary_call(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
        element_verification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        log.log(LogStatus.PASS, "Pass: System is in Alarm");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(adc.getAccountId());
        log.log(LogStatus.INFO, "Verification on the ADC dealer web site");
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());
            {
                log.log(LogStatus.PASS, "Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            System.out.println("***No such element found!***");
        }
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());
            {
                log.log(LogStatus.PASS, "Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            System.out.println("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    public void ArmStay_Tamper_sensor_during_Arm_Stay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        System.out.println("ArmStay -Tamper Group " + group + " smoke sensor during Arm Stay");
        log.log(LogStatus.INFO, "ArmStay -Tamper Group " + group + " smoke sensor during Arm Stay");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_STAY();
        Thread.sleep(33000);
        System.out.println("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verify_armstay();
        element_verification(home.Tamper_Status, "Tampered");
        log.log(LogStatus.PASS, "Pass: Sensor is tampered, system is in Armed Stay mode");
        Thread.sleep(5000);
        sensors.primary_call(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(adc.getAccountId());
        log.log(LogStatus.INFO, "Verification on the ADC dealer web site");
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());
            {
                log.log(LogStatus.PASS, "Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            System.out.println("***No such element found!***");
        }
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());
            {
                log.log(LogStatus.PASS, "Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            System.out.println("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_26() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmStay_SmokeSensor");
        ArmStay_Activate_Restore_sensor_during_Exit_Delay(26, "67 00 22", "//*[contains(text(), '(Sensor 26) Fire Alarm')]", "//*[contains(text(), '(Sensor 26) Reset')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamper_26() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmStay_SmokeSensor_Tamper");
        ArmStay_Tamper_sensor_during_Arm_Stay(26, "67 00 22", "//*[contains(text(), '(Sensor 26) Tamper')]", "//*[contains(text(), '(Sensor 26) End of Tamper')]");
    }

    public void ArmAway_Open_Close_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        System.out.println("ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        log.log(LogStatus.INFO, "ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        System.out.println("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalExitDelay);
        verify_armaway();
        log.log(LogStatus.PASS, "Pass: System is in ArmAway mode");
        Thread.sleep(3000);
        home.ArwAway_State.click();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt10_ExitDelay");
        ArmAway_Open_Close_sensor_during_Exit_Delay(10, "63 00 EA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 3)
    public void ArmAwayExitDelay_12() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt12_ExitDelay");
        ArmAway_Open_Close_sensor_during_Exit_Delay(12, "63 00 FA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_25() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt25_ExitDelay");
        ArmAway_Open_Close_sensor_during_Exit_Delay(25, "63 00 0A", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Open-Close sensor, disarm during Dialer Delay ***/
    public void ArmAway_Open_Close_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        System.out.println("ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        log.log(LogStatus.INFO, "ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        Thread.sleep(2000);
        verify_in_alarm();
        log.log(LogStatus.PASS, "Pass: System is in Alarm");
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmAway_Open_Close_sensor(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        System.out.println("ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        log.log(LogStatus.INFO, "ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalExitDelay);
        verify_armaway();
        log.log(LogStatus.PASS, "Pass: System is in Armed Away mode");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 5)
    public void ArmAway_10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt10_DisarmDuringDialerDelay");
        ArmAway_Open_Close_sensor_Alarm(10, "63 00 EA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 6)
    public void ArmAway_12() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("sensors.ArmAway_Tilt12_DisarmDuringDialerDelay");
        ArmAway_Open_Close_sensor_Alarm(12, "63 00 FA", "//*[contains(text(), 'Entry delay')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 7)
    public void ArmAway_25() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt25_DisarmDuringDialerDelay");
        ArmAway_Open_Close_sensor(25, "63 00 0A", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Tamper sensor ***/
    public void ArmAway_Tamper_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        System.out.println("ArmAway Tamper Group " + group + " contact sensor");
        log.log(LogStatus.INFO, "ArmAway Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verify_in_alarm();
        log.log(LogStatus.PASS, "Pass: system is in Alarm");
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmAway_Tamper_sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        System.out.println("ArmAway Tamper Group " + group + " contact sensor");
        log.log(LogStatus.INFO, "ArmAway Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verify_armaway();
        log.log(LogStatus.PASS, "Pass: system is in Armed Away mode");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 8)
    public void ArmAway_Tamper_10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt10_Tamper");
        ArmAway_Tamper_sensor_Alarm(10, "63 00 EA", "//*[contains(text(), 'Sensor 3 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 9)
    public void ArmAway_Tamper_12() throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt12_Tamper");
        ArmAway_Tamper_sensor_Alarm(12, "63 00 FA", "//*[contains(text(), 'Sensor 4 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 10)
    public void ArmAway_Tamper_25() throws Exception {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", Software_Version());
        log = report.startTest("Sensors.ArmAway_Tilt25_Tamper");
        ArmAway_Tamper_sensor(25, "63 00 0A", "//*[contains(text(), 'Sensor 5 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
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
        adc.driver1.quit();
    }

    @AfterClass
    public void driver_quit() throws IOException, InterruptedException {
        for (int i = 3; i < 6; i++) {
            delete_from_primary(i);
        }
        delete_from_primary(26);
        driver.quit();
    }
}
