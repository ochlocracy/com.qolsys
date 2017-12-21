package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import panel.EmergencyPage;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class HeatSensor extends Setup {

    String page_name = "ADC Smoke Test: Heat Sensor Arm Stay";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    private String DL = "75 00 26";

    public HeatSensor() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
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
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        addPrimaryCall(27, 26, 7667810, 111);
        Thread.sleep(2000);
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(2000);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(2000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(2000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(2000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Activate_Restore_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Activate/Restore Group " + group + " Heat sensor during exit delay");
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_STAY();
        Thread.sleep(5000);
        logger.info("Activate/Restore a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
        elementVerification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    public void ArmStay_Tamper_sensor_during_Arm_Stay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Tamper Group " + group + " Heat sensor during Arm Stay");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(33000);
        verifyArmstay();
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyArmstay();
        elementVerification(home.Tamper_Status, "Tampered");
        Thread.sleep(5000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_26() throws Exception {
        ArmStay_Activate_Restore_sensor_during_Exit_Delay(26, DL, "//*[contains(text(), '(Sensor 27) Fire Alarm')]", "//*[contains(text(), '(Sensor 27) Reset')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamper_26() throws Exception {
        ArmStay_Tamper_sensor_during_Arm_Stay(26, DL, "//*[contains(text(), '(Sensor 27) Tamper')]", "//*[contains(text(), '(Sensor 27) End of Tamper')]");
    }

    public void ArmAway_Activate_Restore_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate/Restore Group " + group + " Heat sensor during exit delay");
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_AWAY(5);
        logger.info("Activate/Restore a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
        elementVerification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    public void ArmAway_Tamper_sensor_during_Arm_Stay(int group, String DLID, String element_to_verify1, String element_to_verify2, String element_to_verify3, String elemnt_to_verify4) throws Exception {
        logger.info("ArmAway -Tamper Group " + group + " Heat sensor during Arm Away");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_AWAY(33);
        verifyArmaway();
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        logger.info("Disarm the system");
        enterDefaultUserCode();
        elementVerification(home.Tamper_Status, "Tampered");
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(adc.getAccountId());
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());
            {
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            logger.info("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_26() throws Exception {
        ArmAway_Activate_Restore_sensor_during_Exit_Delay(26, DL, "//*[contains(text(), '(Sensor 27) Fire Alarm')]", "//*[contains(text(), '(Sensor 27) Reset')]");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayTamper_26() throws Exception {
        ArmAway_Tamper_sensor_during_Arm_Stay(26, DL, "//*[contains(text(), '(Sensor 27) Tamper')]", "//*[contains(text(), '(Sensor 27) End of Tamper')]", "//*[contains(text(), '(Sensor 27) Fire Alarm')]", "//*[contains(text(), '(Sensor 27) Reset')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        deleteFromPrimary(27);
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}
