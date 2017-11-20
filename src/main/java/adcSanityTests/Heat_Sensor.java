package adcSanityTests;

import adc.ADC;
import panel.*;
import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class Heat_Sensor  extends Setup {
    // PRECONDITIONS: Disable SIA limits, set Entry-Exit Delay time to 30, 31, 32, 33 sec; Disable ArmStay No-Delay setting

    public Heat_Sensor() throws Exception {
        ConfigProps.init();
    }

    String page_name = "adc Smoke Test: Heat Sensor Arm Stay";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    String AccountID = adc.getAccountId();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    private String activate = "02 01";
    private String restore = "00 01";
    private String tamper = "01 01";
    private int Normal_Exit_Delay = 30;
    private int Normal_Entry_Delay = 31;
    private int Long_Exit_Delay = 32;
    private int Long_Entry_Delay = 33;
    private String DL = "75 00 26";
    public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }
    public void delete_from_primary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(ConfigProps.adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);}

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
        setArmStay_NoDelay("Disable");
        setAutoStay("Disable");
    }
    @BeforeMethod
    public  void webDriver(){
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        add_primary_call(27, 26, 7667810, 111);
        Thread.sleep(2000);
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(2000);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(Normal_Entry_Delay);
        Thread.sleep(2000);
        servcall.set_NORMAL_EXIT_DELAY(Normal_Exit_Delay);
        Thread.sleep(2000);
        servcall.set_LONG_ENTRY_DELAY(Long_Entry_Delay);
        Thread.sleep(2000);
        servcall.set_LONG_EXIT_DELAY(Long_Exit_Delay);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();

    }
    public void ArmStay_Activate_Restore_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Activate/Restore Group " + group + " Heat sensor during exit delay");
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        ARM_STAY();
        Thread.sleep(5000);
        logger.info("Activate/Restore a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        sensors.primary_call(DLID, restore);
        Thread.sleep(2000);
        element_verification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
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
    public void ArmStay_Tamper_sensor_during_Arm_Stay(int group, String DLID, String element_to_verify1, String element_to_verify2 ) throws Exception {
        logger.info("ArmStay -Tamper Group " +group + " Heat sensor during Arm Stay");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_STAY();
        Thread.sleep(33000);
        verify_armstay();
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, tamper);
        Thread.sleep(2000);
        verify_armstay();
        element_verification(home.Tamper_Status, "Tampered");
        Thread.sleep(5000);
        sensors.primary_call(DLID, restore);
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(AccountID);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());{
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        }catch (Exception e){
            logger.info("***No such element found!***");
        }
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());{
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        }catch (Exception e){
            logger.info("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    @Test (dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_26() throws Exception {
        ArmStay_Activate_Restore_sensor_during_Exit_Delay(26, DL, "//*[contains(text(), '(Sensor 27) Fire Alarm')]", "//*[contains(text(), '(Sensor 27) Reset')]");
    }
    @Test  (priority = 1, retryAnalyzer = RetryAnalizer.class)
    public  void ArmStayTamper_26() throws Exception {
        ArmStay_Tamper_sensor_during_Arm_Stay(26,DL, "//*[contains(text(), '(Sensor 27) Tamper')]", "//*[contains(text(), '(Sensor 27) End of Tamper')]");
    }
    public void ArmAway_Activate_Restore_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate/Restore Group " + group + " Heat sensor during exit delay");
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        ARM_AWAY(5);
        logger.info("Activate/Restore a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        sensors.primary_call(DLID, restore);
        Thread.sleep(2000);
        element_verification(emg.Fire_icon_Alarmed, "Fire icon Alarmed");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
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
    public void ArmAway_Tamper_sensor_during_Arm_Stay(int group, String DLID, String element_to_verify1, String element_to_verify2, String element_to_verify3, String elemnt_to_verify4 ) throws Exception {
        logger.info("ArmAway -Tamper Group " +group + " Heat sensor during Arm Away");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_AWAY(33);
        verify_armaway();
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, tamper);
        Thread.sleep(2000);
        verify_in_alarm();
        logger.info("Disarm the system");
        enter_default_user_code();
        element_verification(home.Tamper_Status, "Tampered");
        Thread.sleep(2000);
        sensors.primary_call(DLID, restore);
        Thread.sleep(15000);
        // adc website verification
        adc.New_ADC_session(AccountID);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify1));
            Assert.assertTrue(history_message.isDisplayed());{
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        }catch (Exception e){
            logger.info("***No such element found!***");
        }
        Thread.sleep(2000);
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(element_to_verify2));
            Assert.assertTrue(history_message.isDisplayed());{
                logger.info("Pass: message is displayed " + history_message.getText());
            }
        }catch (Exception e){
            logger.info("***No such element found!***");
        }
        Thread.sleep(5000);
    }

    @Test (dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_26() throws Exception {
        ArmAway_Activate_Restore_sensor_during_Exit_Delay(26, DL, "//*[contains(text(), '(Sensor 27) Fire Alarm')]", "//*[contains(text(), '(Sensor 27) Reset')]");
    }
    @Test  (priority = 1, retryAnalyzer = RetryAnalizer.class)
    public  void ArmAwayTamper_26() throws Exception {
        ArmAway_Tamper_sensor_during_Arm_Stay(26,DL, "//*[contains(text(), '(Sensor 27) Tamper')]", "//*[contains(text(), '(Sensor 27) End of Tamper')]", "//*[contains(text(), '(Sensor 27) Fire Alarm')]","//*[contains(text(), '(Sensor 27) Reset')]");
    }
    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
       delete_from_primary(27);
    }
    @AfterMethod
    public void webDriverQuit(){
        adc.driver1.quit();
    }

}

