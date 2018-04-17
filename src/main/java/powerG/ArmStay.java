package powerG;

import adc.ADC;
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
import panel.AdvancedSettingsPage;
import panel.DevicesPage;
import panel.InstallationPage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ArmStay extends Setup{

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    ADC adc = new ADC();
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public ArmStay() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/QTMS_PowerG_ArmStay.html");
        report.loadConfig(new File(file));
//        report
//                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/QTMS_PowerG_ArmStay.html", false);
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
    public void navigate_to_Security_Sensors_page() throws InterruptedException {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        dev.Security_Sensors.click();
    }
    public void resetAlarm(String alarm) throws InterruptedException {
        adc.New_ADC_session_User("LeBron_James", "qolsys123");
        Thread.sleep(5000);
        adc.driver1.get("https://www.alarm.com/web/system/alerts-issues");
        Thread.sleep(5000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stop " + alarm + "']"))).click();
        Thread.sleep(4000);
        adc.driver1.findElement(By.xpath("(//*[text()='Stop Alarms'])[last()]")).click();
        Thread.sleep(10000);
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
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }

    @Test
    public void AS_01() throws Exception {
        create_report("AS_01");
        log.log(LogStatus.INFO, ("*AS_01* Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_02() throws Exception {
        add_to_report("AS_02");
        log.log(LogStatus.INFO, ("*AS_02* Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_03() throws Exception {
        add_to_report("AS_03");
        log.log(LogStatus.INFO, ("*AS_03* Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_04() throws Exception {
        add_to_report("AS_04");
        log.log(LogStatus.INFO, ("*AS_04* Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_05() throws Exception {
        add_to_report("AS_05");
        log.log(LogStatus.INFO, ("*AS_05* Verify the system will NOT go into alarm if a sensor in group 13 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyArmstay();
        Thread.sleep(1000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
    }

    @Test
    public void AS_06() throws Exception {
        add_to_report("AS_06");
        log.log(LogStatus.INFO, ("*AS_06* Verify the system will go into immediate alarm if a sensor in group 8 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1127, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1127, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_07() throws Exception {
        add_to_report("AS_07");
        log.log(LogStatus.INFO, ("*AS_07* Verify the system will go into alarm at the end of entry delay if a sensor in group 9 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1123, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        pgprimaryCall(104, 1123, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_08() throws Exception {
        add_to_report("AS_08");
        log.log(LogStatus.INFO, ("*AS_08* Verify the system will NOT go into alarm if a sensor in group 25 is opened in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1311, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyArmstay();
        Thread.sleep(1000);
        pgprimaryCall(104, 1311, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
    }

    @Test
    public void AS_09() throws Exception {
        add_to_report("AS_09");
        log.log(LogStatus.INFO, ("*AS_09* Verify the system will go into immediate alarm if a sensor in group 15 is activated in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_10() throws Exception {
        add_to_report("AS_10");
        log.log(LogStatus.INFO, ("*AS_10* Verify the system will NOT go into alarm if a sensor in group 17 is activated in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(123, 1441, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        verifyArmstay();
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
    }

    @Test
    public void AS_11() throws Exception {
        add_to_report("AS_11");
        log.log(LogStatus.INFO, ("*AS_11* Verify the system will NOT go into alarm if a sensor in group 20 is activated in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        verifyArmstay();
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
    }

    @Test
    public void AS_12() throws Exception {
        add_to_report("AS_12");
        log.log(LogStatus.INFO, ("*AS_12* Verify the system will go into alarm at the end of entry delay if a sensor in group 35 is activated in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(1000);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm"));
    }

    @Test
    public void AS_13() throws Exception {
        add_to_report("AS_13");
        log.log(LogStatus.INFO, ("*AS_13* Verify the system will NOT go into alarm if a sensor in group 25 is activated in Arm Stay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(120, 1445, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(1000);
        verifyArmstay();
        Thread.sleep(1000);
        DISARM();
        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
    }

    @Test
    public void AS_14() throws Exception {
        add_to_report("AS_14");
        log.log(LogStatus.INFO, ("*AS_14* Verify the system will go into alarm if a sensor in group 15 is activated and that the system can be disarmed before the dialer delay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(1);
        verifyInAlarm();
        TimeUnit.SECONDS.sleep(1);
        enterDefaultUserCode();
        log.log(LogStatus.PASS, ("Pass: system is in alarm and can de disarmed"));
    }

    @Test
    public void AS_15() throws Exception {
        add_to_report("AS_15");
        log.log(LogStatus.INFO, ("*AS_15* Verify the system reports alarm for both sensors group 10 and 12 at the end of entry delay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement door2 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1152']"));
        elementVerification(door1, "DW 104-1101");
        elementVerification(door2, "DW 104-1152");
        enterDefaultUserCode();
        Thread.sleep(1000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        log.log(LogStatus.PASS, ("Pass: system reports alarm for both sensors group 10 and 12"));
    }

    @Test
    public void AS_16() throws Exception {
        add_to_report("AS_16");
        log.log(LogStatus.INFO, ("*AS_16* Verify the system reports alarm for both sensors group 10 and 14 at the end of entry delay"));
        Thread.sleep(1000);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay +2);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(1000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(1000);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        WebElement door1 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101']"));
        WebElement door2 = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1216']"));
        elementVerification(door1, "DW 104-1101");
        elementVerification(door2, "DW 104-1216");
        enterDefaultUserCode();
        Thread.sleep(1000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        log.log(LogStatus.PASS, ("Pass: system reports alarm for both sensors group 10 and 14"));
    }

    @Test
    public void AS_17(){
        add_to_report("AS_17");
        log.log(LogStatus.INFO, ("*AS_17* Verify the system reports alarm for both sensors group 10 and 13 at the end of entry delay"));

    }






    @AfterTest(alwaysRun = true)
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
