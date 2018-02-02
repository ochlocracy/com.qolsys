package updateProcess;

import adc.ADC;
import adcSanityTests.RetryAnalizer;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.EmergencyPage;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SanityPGSensors extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public SanityPGSensors() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(1000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longEntryDelay);
        setArmStayNoDelay("Disable");
        setAutoStay("Disable");
    }

    @BeforeMethod
    public void setUpWeb() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Olga Kulik")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("PGSensors.SmokeSensor");

        Thread.sleep(2000);
        log.log(LogStatus.INFO, "Adding sensors");
        navigate_to_autolearn_page();
//        addPGSensors(104, 1101, 0);//gr10
//        addPGSensors(104, 1152, 1);//gr12
//        addPGSensors(104, 1311, 5);//gr25
//        addPGSensors(201, 1541, 0);//gr26 smoke-M

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Activate_Restore_sensor_during_Exit_Delay(int type, int id, String element_to_verify1, String element_to_verify2) throws Exception {
        System.out.println("ArmStay -Activate/Restore SmokeM sensor during exit delay");
        log.log(LogStatus.INFO, "ArmStay -Activate/Restore SmokeM sensor during exit delay");
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(5000);
        System.out.println("Activate/Restore a sensor");
        activation_restoration(type, id, PGSensorsActivity.SMOKEM, PGSensorsActivity.SMOKEMREST);
        Thread.sleep(2000);
        elementVerification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        log.log(LogStatus.PASS, "Pass: System is in Alarm");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(15000);
        adc.ADC_verification(element_to_verify1, element_to_verify2);
        Thread.sleep(5000);
    }

    public void ArmStay_Tamper_sensor_during_Arm_Stay(int type, int id, String element_to_verify1, String element_to_verify2) throws Exception {
        System.out.println("ArmStay -Tamper sensor during Arm Stay");
        log.log(LogStatus.INFO, "ArmStay -Tamper SmokeM sensor during Arm Stay");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(33000);
        System.out.println("Tamper a sensor");
        pgprimaryCall(type, id, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyArmstay();
        elementVerification(home.Tamper_Status, "Tampered");
        log.log(LogStatus.PASS, "Pass: Sensor is tampered, system is in Armed Stay mode");
        Thread.sleep(5000);
        pgprimaryCall(type, id, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(15000);
        adc.ADC_verification(element_to_verify1, element_to_verify2);
        Thread.sleep(5000);
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_26() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmStay_SmokeSensor");
        ArmStay_Activate_Restore_sensor_during_Exit_Delay(201, 1541, "//*[contains(text(), '(Sensor 4) Fire Alarm')]", "//*[contains(text(), '(Sensor 4) Closed')]");
        //incorrect status for smoke restore event
        for (int i = 1; i < 6; i++) {
            deleteFromPrimary(i);
        }
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamper_26() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmStay_SmokeSensor_Tamper");
        ArmStay_Tamper_sensor_during_Arm_Stay(201, 1541, "//*[contains(text(), '(Sensor 4) Tamper')]", "//*[contains(text(), '(Sensor 4) End of Tamper')]");
    }

    public void ArmAway_Open_Close_sensor_during_Exit_Delay(int type, int id, String element_to_verify, String element_to_verify2) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("ArmAway -Open/Close DW sensor during exit delay");
        log.log(LogStatus.INFO, "ArmAway -Open/Close Group DW sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        System.out.println("Open/Close a sensor");
        activation_restoration(type, id, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalExitDelay);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in ArmAway mode");
        Thread.sleep(3000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt10_ExitDelay");
        ArmAway_Open_Close_sensor_during_Exit_Delay(104, 1101, "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 3)
    public void ArmAwayExitDelay_12() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt12_ExitDelay");
        ArmAway_Open_Close_sensor_during_Exit_Delay(104, 1152, "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_25() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt25_ExitDelay");
        ArmAway_Open_Close_sensor_during_Exit_Delay(104, 1311, "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Open-Close sensor, disarm during Dialer Delay ***/
    public void ArmAway_Open_Close_sensor_Alarm(int type, int id, String element_to_verify, String element_to_verify2) throws Exception {
        System.out.println("ArmAway -Open/Close Group DW sensor during exit delay");
        log.log(LogStatus.INFO, "ArmAway -Open/Close Group DW sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Open/Close a sensor");
        activation_restoration(type, id, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        Thread.sleep(2000);
        verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: System is in Alarm");
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmAway_Open_Close_sensor(int type, int id, String element_to_verify, String element_to_verify2) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("ArmAway -Open/Close Group DW sensor during exit delay");
        log.log(LogStatus.INFO, "ArmAway -Open/Close Group DW tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Open/Close a sensor");
        activation_restoration(type, id, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalExitDelay);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in Armed Away mode");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 5)
    public void ArmAway_10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt10_DisarmDuringDialerDelay");
        ArmAway_Open_Close_sensor_Alarm(104, 1101, "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 6)
    public void ArmAway_12() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("sensors.ArmAway_Tilt12_DisarmDuringDialerDelay");
        ArmAway_Open_Close_sensor_Alarm(104, 1152, "//*[contains(text(), 'Entry delay')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 7)
    public void ArmAway_25() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt25_DisarmDuringDialerDelay");
        ArmAway_Open_Close_sensor(104, 1311, "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Tamper sensor ***/
    public void ArmAway_Tamper_sensor_Alarm(int type, int id, String element_to_verify, String element_to_verify1) throws Exception {
        System.out.println("ArmAway Tamper Group DW sensor");
        log.log(LogStatus.INFO, "ArmAway Tamper DW sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Tamper a sensor");
        activation_restoration(type, id, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: system is in Alarm");
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmAway_Tamper_sensor(int type, int id, String element_to_verify, String element_to_verify1) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        System.out.println("ArmAway Tamper Group DW sensor");
        log.log(LogStatus.INFO, "ArmAway Tamper Group DW sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        System.out.println("Tamper a sensor");
        activation_restoration(type, id, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: system is in Armed Away mode");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 8)
    public void ArmAway_Tamper_10() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt10_Tamper");
        ArmAway_Tamper_sensor_Alarm(104, 1101, "//*[contains(text(), 'Sensor 1 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 9)
    public void ArmAway_Tamper_12() throws Exception {
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        log = report.startTest("Sensors.ArmAway_Tilt12_Tamper");
        ArmAway_Tamper_sensor_Alarm(104, 1152, "//*[contains(text(), 'Sensor 2 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 10)
    public void ArmAway_Tamper_25() throws Exception {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/PGSanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Olga Kulik")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("Sensors.ArmAway_Tilt25_Tamper");
        ArmAway_Tamper_sensor(104, 1311, "//*[contains(text(), 'Sensor 3 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
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
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}
