package powerG;

import adc.ADC;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* Estimate run time: 10min 12 sec */

public class CarbonMonoxide extends Setup {

    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public CarbonMonoxide() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/CarbonMonoxide.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/CarbonMonoxide.html", false);
        log = report.startTest(test_case_name);
    }

    public void ADC_verification(String string, String string1) throws InterruptedException, IOException {
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("History")).click();
        Thread.sleep(7000);
        String[] message = {string, string1};
        adc.driver1.navigate().refresh();
        Thread.sleep(7000);
        for (int i = 0; i < message.length; i++) {
            adc.driver1.navigate().refresh();
            try {
                WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                Assert.assertTrue(history_message.isDisplayed());
                {
                    System.out.println("Pass: message is displayed " + history_message.getText());
                }
            } catch (Exception e) {
                System.out.println("***No such element found!***");
            } finally {
            }
            Thread.sleep(7000);
        }
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        servcall.set_AUTO_STAY(0);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    /*** Arm Stay Activate ***/

    public void ArmStay_Activate_Sensor_during_Exit_Delay(String element_to_verify, String element_to_verify1) throws Exception {
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        pgprimaryCall(220, 1661, PGSensorsActivity.CO);
        Thread.sleep(5000);
        verifyInAlarm();
        pgprimaryCall(220, 1661, PGSensorsActivity.COREST);
        Thread.sleep(5000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Activate_Sensor_Alarm(String element_to_verify, String element_to_verify1) throws Exception {
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        pgprimaryCall(220, 1661, PGSensorsActivity.CO);
        Thread.sleep(5000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.COREST);
        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test
    public void ArmStayExitDelay_34() throws Exception {
        create_report("ArmStay CO sensor");
        log.log(LogStatus.INFO, ("ArmStay -Activate Carbon Monoxide during exit delay"));
        ArmStay_Activate_Sensor_during_Exit_Delay("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
    }

    @Test(priority = 2)
    public void ArmStay_34() throws Exception {
        add_to_report("Arm Stay CO sensor");
        log.log(LogStatus.INFO, ("ArmStay -Activate Carbon Monoxide"));
        ArmStay_Activate_Sensor_Alarm("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
    }

    /*** Arm Stay Tamper ***/

    public void ArmStay_Tamper_Sensor(String element_to_verify, String element_to_verify2) throws Exception {
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(5000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmStayTamper_34() throws Exception {
        add_to_report("Arm Stay CO sensor");
        log.log(LogStatus.INFO, ("ArmStay - Tamper Carbon Monoxide"));
        ArmStay_Tamper_Sensor("//*[contains(text(), 'Sensor 16 Tamper')]", "//*[contains(text(), 'Sensor 16 End of tamper')]");
        log.log(LogStatus.PASS, ("Pass: System is in Arm Stay mode, events are displayed correctly on the ADC dealer website"));
    }

    /*** ArmAway Activate***/

    public void ArmAway_Activate_Sensor_during_Exit_Delay(String element_to_verify, String element_to_verify1) throws Exception {
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        pgprimaryCall(220, 1661, PGSensorsActivity.CO);
        Thread.sleep(5000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.COREST);
        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_34() throws Exception {
        add_to_report("Arm Away CO sensor");
        log.log(LogStatus.INFO, ("ArmAway - Activate Carbon Monoxide during exit delay"));
        ArmAway_Activate_Sensor_during_Exit_Delay("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
    }

    public void ArmAway_Activate_Sensor_Alarm(String element_to_verify, String element_to_verify2) throws Exception {
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.CO);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.COREST);
        ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 5)
    public void ArmAway_34() throws Exception {
        add_to_report("Arm Away CO sensor");
        log.log(LogStatus.INFO, ("ArmAway - Activate Carbon Monoxide"));
        ArmAway_Activate_Sensor_Alarm("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
    }

    /*** ArmAway Tamper ***/

    public void ArmAway_Tamper_Sensor(String element_to_verify, String element_to_verify1) throws Exception {
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(5000);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        enterDefaultUserCode();
        Thread.sleep(2000);
        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 6)
    public void ArmAwayTamper_34() throws Exception {
        add_to_report("Arm Away CO sensor");
        log.log(LogStatus.INFO, ("ArmAway - Tamper Carbon Monoxide"));
        ArmAway_Tamper_Sensor("//*[contains(text(), 'Sensor 16 Tamper')]", "//*[contains(text(), '(Sensor 16) Tamper Alarm')]");
        log.log(LogStatus.PASS, ("Pass: System is in Alarm, events are displayed correctly on the ADC dealer website"));
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
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
}
