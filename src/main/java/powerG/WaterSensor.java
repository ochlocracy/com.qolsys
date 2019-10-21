package powerG;

import adc.ADC;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WaterSensor extends Setup {

    /* Estimate execution time: 9m 30sec */

    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("PowerG_Water_Sensor");

    public WaterSensor() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void ADC_verification(String string, String string1) throws InterruptedException, IOException {
        adc.newAdcSession(adc.getAccountId());
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
            }
            Thread.sleep(7000);
        }
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setArmStayNoDelay("Enable");
        setAutoStay("Disable");
    }

    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }


    public void default_state() throws IOException, InterruptedException {
        disarmServiceCall();
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPERREST);
    }

    @Test
    public void Water_01() throws Exception {
        rep.create_report("Wat_01");
        rep.log.log(LogStatus.INFO, ("*Wat_01* Disarm mode tripping Water group 38 -> Expected result = Instant Alarm"));
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(241, 1971, PGSensorsActivity.FLOOD);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Water 241-1971')]", "//*[contains(text(), 'sensor 21')]");
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void Water_02() throws Exception {
        rep.add_to_report("Wat_02");
        rep.log.log(LogStatus.INFO, ("*Wat_02* ArmStay mode tripping Water group 38 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(241, 1971, PGSensorsActivity.FLOOD);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Water 241-1971')]", "//*[contains(text(), 'sensor 21')]");
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void Water_03() throws Exception {
        rep.add_to_report("Wat_03");
        rep.log.log(LogStatus.INFO, ("*Wat_03* ArmAway mode tripping Water_flood group 38 -> Expected result = Instant Alarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(241, 1971, PGSensorsActivity.FLOOD);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Water 241-1971')]", "//*[contains(text(), 'sensor 21')]");
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void Water_04() throws Exception {
        rep.add_to_report("Wat_04");
        rep.log.log(LogStatus.INFO, ("*Wat_04* Disarm mode tampering Water group 38 -> Expected result = Disarm"));
        if (RetryAnalizer.retry == true) {
            default_state();
            TimeUnit.SECONDS.sleep(2);
        }
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(5);
        verifySystemState("Disarmed");
        ADC_verification("//*[contains(text(), 'Water 241-1971')]", "//*[contains(text(), 'Disarmed')]");
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void Water_05() throws Exception {
        rep.add_to_report("Wat_05");
        rep.log.log(LogStatus.INFO, ("*Wat_05* ArmStay mode tampering Water group 38 -> Expected result = ArmStay"));
        if (RetryAnalizer.retry == true) {
            default_state();
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_STAY();
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(3);
        verifySystemState("Armed Stay");
        ADC_verification("//*[contains(text(), 'Water 241-1971')]", "//*[contains(text(), 'Armed Stay')]");
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPERREST);
        DISARM();
        rep.log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void Water_06() throws Exception {
        rep.add_to_report("Wat_06");
        rep.log.log(LogStatus.INFO, ("*Wat_06* ArmAway mode tampering Water group 38 -> Expected result = Alarm"));


        if (RetryAnalizer.retry == true) {
            default_state();
            TimeUnit.SECONDS.sleep(2);
        }
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPER);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Water 241-1971')]", "//*[contains(text(), 'sensor 21')]");
        pgprimaryCall(241, 1971, PGSensorsActivity.TAMPERREST);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverQuit(ITestResult result) throws IOException {
        rep.report_tear_down(result);
        adc.driver1.quit();
    }
}
