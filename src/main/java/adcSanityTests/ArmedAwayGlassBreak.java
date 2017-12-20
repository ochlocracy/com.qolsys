package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class ArmedAwayGlassBreak extends Setup {

    String page_name = "Arm Away Mode Glass Break";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();

    public ArmedAwayGlassBreak() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    public void ArmsAway_Trigger_Sensor_During_Exit_Delay_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Trip glass break " + group + " sensor during exit delay");
        ARM_AWAY(0);
        Thread.sleep(3000);
        logger.info("Trip glass break sensor " + group);
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        logger.info("Restore Glass Break " + group);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(4000);
        logger.info("Verify Alarm");
        verifyInAlarm();
        Thread.sleep(1000);
        logger.info("Disarm with 1234");
        enterDefaultUserCode();
        /*** ADC website verification ***/
        logger.info("ADC website verification");
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(3000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        Thread.sleep(3000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(10000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed: " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(2000);
    }

    public void ArmAway_Trigger_Sensor_During_Exit_Delay(int group, String DLID, String element_to_verify) throws Exception {
        logger.info("ArmStay -Trip glass break " + group + " sensor during exit delay");
        ARM_AWAY(0);
        Thread.sleep(3000);
        logger.info("Trip glass break sensor " + group);
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        logger.info("Restore Glass Break " + group);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(2000);
        logger.info("Verify Alarm");
        verifyInAlarm();
        Thread.sleep(1000);
        logger.info("Disarm with 1234");
        enterDefaultUserCode();
        /*** ADC website verification ***/
        logger.info("ADC website verification");
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        Thread.sleep(3000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();

        Thread.sleep(10000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed: " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
    }

    public void ArmAway_tamper_Sensor_After_Exit_Delay_Alarm(int group, String DLID, String element_to_verify) throws Exception {
        logger.info("ArmStay -Trip glass break " + group + " sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        logger.info("Tamper glass break sensor " + group);
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        logger.info("Restore Glass Break " + group);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        logger.info("Verify Alarm");
        verifyInAlarm();
        Thread.sleep(1000);
        logger.info("Disarm with 1234");
        enterDefaultUserCode();
        /*** ADC website verification ***/
        logger.info("ADC website verification");
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        Thread.sleep(3000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();

        Thread.sleep(10000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed: " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
    }

    public void ArmAway_tamper_Sensor_After_Exit_Delay(int group, String DLID, String element_to_verify) throws Exception {
        logger.info("ArmStay -Trip glass break " + group + " sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        logger.info("Tamper glass break sensor " + group);
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        logger.info("Restore Glass Break " + group);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        logger.info("Verify Alarm");
        verifyInAlarm();
        Thread.sleep(1000);
        logger.info("Disarm with 1234");
        enterDefaultUserCode();
        /*** ADC website verification ***/
        logger.info("ADC website verification");
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        Thread.sleep(3000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();

        Thread.sleep(10000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed: " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
    }

    public void deleteFromPrimary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(ConfigProps.adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        setArmStayNoDelay("Disable");
        setAutoStay("Disable");
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addGlassBreakSensor() throws IOException, InterruptedException {
        logger.info("Addig sensors");
        Thread.sleep(2000);
        addPrimaryCall(11, 13, 6750361, 19);
        addPrimaryCall(12, 17, 6750355, 19);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    @Test(dependsOnMethods = {"addGlassBreakSensor"})
    public void ArmsAwayExitDelay_13() throws Exception {
        ArmsAway_Trigger_Sensor_During_Exit_Delay_Alarm(13, "67 00 99", "//*[contains(text(), 'Glass Break 11 (Sensor 11) Pending Alarm')]", "//*[contains(text(), 'Glass Break 11 (Sensor 11) Alarm')]");
    }

    @Test(priority = 1, dependsOnMethods = {"addGlassBreakSensor"})
    public void ArmAwayExitDelay_17() throws Exception {
        ArmAway_Trigger_Sensor_During_Exit_Delay(17, "67 00 39", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 2, dependsOnMethods = {"addGlassBreakSensor"})
    public void ArmAway_tamper_13() throws Exception {
        ArmAway_tamper_Sensor_After_Exit_Delay_Alarm(13, "67 00 99", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 3, dependsOnMethods = {"addGlassBreakSensor"})
    public void ArmAway_tamper_17() throws Exception {
        ArmAway_tamper_Sensor_After_Exit_Delay(17, "67 00 39", "//*[contains(text(), 'Pending Alarm')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i = 12; i > 10; i--) {
            deleteFromPrimary(i);
        }
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}