package powerG;

import adc.ADC;
import adcSanityTests.Freeze;
import cellular.WiFi_setting_page_elements;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.SecuritySensorsPage;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TemperatureSensor extends Setup {

    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("PowerG_Temperature_Sensor");

    public TemperatureSensor() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
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
            }
            Thread.sleep(7000);
        }
    }

    public void default_state() throws IOException, InterruptedException {
        disarmServiceCall();
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPERREST);

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

//   @Test (priority = 1)
    public void Add_Temperature_Sensors() throws Exception {
       SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);

       rep.create_report("Add_Temperature_Sensors");
       rep.log.log(LogStatus.INFO, ("*Run only if the panel hs no Temperature Sensor, comment out @Test after finished"));

       System.out.println("Be sure to comment out this @Test once the sensors are added, then continue w full run.");
       navigateToAddSensorsPage();

//       System.out.println("select PG instead of SRF(if srf card is in the panel)");
//       driver.findElement(By.id("com.qolsys:id/sensorprotocaltype")).click();
//       Thread.sleep(1000);
//       List<WebElement> Source_list = driver.findElements(By.id("com.qolsys:id/sensorprotocaltype"));
//       Source_list.get(2).click();
//       driver.findElement(By.linkText("PowerG")).click();

       System.out.println("Adding a Heat Sensor");
       driver.findElement(By.id("com.qolsys:id/sensor_id")).sendKeys("250");
       driver.findElement(By.id("com.qolsys:id/sensor_powerg_id")).sendKeys("3131");
       driver.hideKeyboard();
       driver.findElement(By.id("com.qolsys:id/addsensor")) .click();
       Thread.sleep(5000);

       driver.findElementById("com.qolsys:id/btnAdd").click();

       System.out.println("Adding a Freeze Sensor");
       driver.findElement(By.id("com.qolsys:id/sensor_id")).sendKeys("250");
       driver.findElement(By.id("com.qolsys:id/sensor_powerg_id")).sendKeys("3030");
       driver.hideKeyboard();

       driver.findElement(By.id("com.qolsys:id/sensortype")).click();
       TimeUnit.SECONDS.sleep(1);
       driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Freeze']")).click();
       TimeUnit.SECONDS.sleep(1);

       driver.findElement(By.id("com.qolsys:id/addsensor")) .click();
       Thread.sleep(6000);
       rep.log.log(LogStatus.PASS, ("Pass: Sensors were added."));
    }

    @Test //(dependsOnMethods = {"Add_Temperature"})
    public void Temperature_01() throws Exception {
        rep.create_report("Temperature_01");
        rep.log.log(LogStatus.INFO, ("*Temperature_01* In Disarm mode send low_temperature event 5' Celsius --> Panel stays in Disarm mode"));
        pgprimaryCall(250,3030, "05 5");
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        rep.log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
    }

    @Test
    public void Temperature_02() throws Exception {
        rep.create_report("Temperature_02");
        rep.log.log(LogStatus.INFO, ("*Temperature_02* In Disarm mode send low_temperature event 4' Celsius --> Panel goes into Alarm"));
        pgprimaryCall(250,3030, "05 4");
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Freeze Sensor')]", "//*[contains(text(), 'Delayed alarm')]");
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_03() throws Exception {
        rep.create_report("Temperature_03");
        rep.log.log(LogStatus.INFO, ("*Temperature_03* In Disarm mode tamper freeze sensor --> Panel stays in Disarm mode"));
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
    }

    @Test
    public void Temperature_04() throws Exception {
        rep.create_report("Temperature_04");
        rep.log.log(LogStatus.INFO, ("*Temperature_04* In Disarm mode send high_temperature event 37' Celsius --> Panel stays in Disarm mode"));
        pgprimaryCall(250,3131, "05 37");
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        rep.log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
    }

    @Test
    public void Temperature_05() throws Exception {
        rep.create_report("Temperature_05");
        rep.log.log(LogStatus.INFO, ("*Temperature_05* In Disarm mode send high_temperature event 38' Celsius --> Panel goes into Alarm"));
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3131, "05 38");
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Heat Detector')]", "//*[contains(text(), 'Fire alarm')]");
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_06() throws Exception {
        rep.create_report("Temperature_06");
        rep.log.log(LogStatus.INFO, ("*Temperature_06* In Disarm mode tamper heat sensor --> Panel stays in Disarm mode"));
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
    }

    @Test
    public void Temperature_07() throws Exception {
        rep.create_report("Temperature_07");
        rep.log.log(LogStatus.INFO, ("*Temperature_07* In Armed Stay mode send low_temperature event 4' Celsius --> Panel goes into Alarm"));
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3030, "05 4");
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Freeze Sensor')]", "//*[contains(text(), 'Delayed alarm')]");
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_08() throws Exception {
        rep.create_report("Temperature_08");
        rep.log.log(LogStatus.INFO, ("*Temperature_08* In Armed Stay mode send high_temperature event 38' Celsius --> Panel goes into Alarm"));
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3131, "05 38");
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Heat Detector')]", "//*[contains(text(), 'Fire alarm')]");
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_09() throws Exception {
        rep.create_report("Temperature_09");
        rep.log.log(LogStatus.INFO, ("*Temperature_09* In Armed Stay mode tamper freeze sensor --> Panel stays in Armed Stay mode"));
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPERREST);
        DISARM();
        rep.log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
    }

    @Test
    public void Temperature_10() throws Exception {
        rep.create_report("Temperature_10");
        rep.log.log(LogStatus.INFO, ("*Temperature_10* In Armed Stay mode tamper heat sensor --> Panel stays in Armed Stay mode"));
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPERREST);
        DISARM();
        rep.log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
    }

    @Test
    public void Temperature_11() throws Exception {
        rep.create_report("Temperature_11");
        rep.log.log(LogStatus.INFO, ("*Temperature_11* In Armed Away mode send low_temperature event 4' Celsius --> Panel goes into Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay );
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3030, "05 4");
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_12() throws Exception {
        rep.create_report("Temperature_12");
        rep.log.log(LogStatus.INFO, ("*Temperature_12* In Armed Away mode send high_temperature event 38' Celsius --> Panel goes into Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay );
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3131, "05 38");
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_13() throws Exception {
        rep.create_report("Temperature_13");
        rep.log.log(LogStatus.INFO, ("*Temperature_13* In Armed Away mode tamper freeze sensor --> Panel goes into Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay );
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        pgprimaryCall(250,3030, PGSensorsActivity.TAMPERREST);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Freeze Sensor')]", "//*[contains(text(), 'Tamper')]");
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
    }

    @Test
    public void Temperature_14() throws Exception {
        rep.create_report("Temperature_14");
        rep.log.log(LogStatus.INFO, ("*Temperature_14* In Armed Away mode tamper heat sensor --> Panel goes into Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay );
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        pgprimaryCall(250,3131, PGSensorsActivity.TAMPERREST);
        enterDefaultUserCode();
        ADC_verification("//*[contains(text(), 'Heat Detector')]", "//*[contains(text(), 'Tamper')]");
        rep.log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
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
