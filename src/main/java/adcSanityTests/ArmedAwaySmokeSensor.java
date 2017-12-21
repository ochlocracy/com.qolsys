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
import sensors.Sensors;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class ArmedAwaySmokeSensor extends Setup {

    String page_name = "ADC Smoke Test: Smoke Sensor Arm Away";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    String AccountID = adc.getAccountId();
    private String activate = "02 01";
    private String restore = "00 01";
    private String tamper = "01 01";

    public ArmedAwaySmokeSensor() throws Exception {
        ConfigProps.init();
    }

    public void addPrimaryCall(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
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
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        addPrimaryCall(26, 26, 6750242, 5);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();
    }

    public void ArmAway_Activate_Restore_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate/Restore Group " + group + " smoke sensor during exit delay");
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_AWAY(5);
        logger.info("Activate/Restore a sensor");
        sensors.primaryCall(DLID, activate);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, restore);
        Thread.sleep(2000);
        elementVerification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(AccountID);
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
        logger.info("ArmAway -Tamper Group " + group + " smoke sensor during Arm Away");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_AWAY(33);
        verifyArmaway();
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, tamper);
        Thread.sleep(2000);
        verifyInAlarm();
        logger.info("Disarm the system");
        enterDefaultUserCode();
        elementVerification(home.Tamper_Status, "Tampered");
        Thread.sleep(2000);
        sensors.primaryCall(DLID, restore);
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(AccountID);
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
    public void ArmAwayExitDelay_26() throws Exception {
        ArmAway_Activate_Restore_sensor_during_Exit_Delay(26, "67 00 22", "//*[contains(text(), '(Sensor 26) Fire Alarm')]", "//*[contains(text(), '(Sensor 26) Reset')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayTamper_26() throws Exception {
        ArmAway_Tamper_sensor_during_Arm_Stay(26, "67 00 22", "//*[contains(text(), '(Sensor 26) Tamper')]", "//*[contains(text(), '(Sensor 26) End of Tamper')]", "//*[contains(text(), '(Sensor 26) Fire Alarm')]", "//*[contains(text(), '(Sensor 26) Reset')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        deleteFromPrimary(26);
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}