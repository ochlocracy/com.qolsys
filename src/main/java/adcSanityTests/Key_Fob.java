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

public class Key_Fob extends Setup {
    public Key_Fob() throws Exception {
        ConfigProps.init();}
    String page_name = "Arm Stay Arm Away KeyFob sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    String AccountID = adc.getAccountId();

    private String keyfobActivated = "02 01";
    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";
    String   element_to_verify = "//*[contains(text(), 'panel armed-stay ')]";
    String   element_to_verify1 = "//*[contains(text(), 'panel armed-away ')]";
    String   element_to_verify3 = "//*[contains(text(), 'panel disarmed ')]";
    private int Long_Exit_Delay = 13;


    public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }

    public void delete_from_primary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(ConfigProps.adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);
    }

    public void ADC_verification (String string, String string1, String string3) throws IOException, InterruptedException {
        String[] message = {string, string1, string3};

        if (ADCexecute.equals("true")) {
            adc.New_ADC_session(AccountID);
            adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
            Thread.sleep(10000);
            for (int i =0; i< message.length; i++) {
                try {
                    WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                    Assert.assertTrue(history_message.isDisplayed());
                    {
                        System.out.println("Dealer Site History: " + history_message.getText());
                    }
                } catch (Exception e) {
                    System.out.println("***No such element found!***");
                }
            }
        }else{
            System.out.println("Set execute to TRUE to run adc verification part");
        }
        Thread.sleep(2000);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        servcall.set_AUTO_STAY(0);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        add_primary_call(38, 1, 6619386, 102);
        add_primary_call(39, 6, 6619387, 102);
        add_primary_call(40, 4, 6619388, 102);
        Thread.sleep(2000);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Disarm_keyfob_group1() throws Exception {
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************DISARM KEY FOB****************************");
        logger.info("Keyfob group 1: Disarm, panic = Police");
        Thread.sleep(4000);
        sensors.primary_call("65 00 AF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 38 Delayed Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Disarm_keyfob_group4() throws Exception {
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************DISARM KEY FOB****************************");
        logger.info("Keyfob group 4: Disarm, panic = Auxiliary");
        Thread.sleep(4000);
        sensors.primary_call("65 00 CF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 40 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Disarm_keyfob_group6() throws Exception {
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************DISARM KEY FOB****************************");
        logger.info("Keyfob group 6: Disarm, panic = Auxiliary");
        Thread.sleep(4000);
        sensors.primary_call("65 00 BF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), 'Keyfob 39 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keyfob_group1() throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************ARM STAY BY KEY FOB****************************");
        logger.info("Keyfob group 1: ArmStay-ArmAway-Disarm, panic = Police");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verify_armstay();
        sensors.primary_call("65 00 AF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 38 Delayed Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keyfob_group6() throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************ARM STAY BY KEY FOB****************************");
        logger.info("Keyfob group 6: ArmStay-ArmAway-Disarm, panic = Mobile Auxiliary");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verify_armstay();
        sensors.primary_call("65 00 BF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 39 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keyfob_group4() throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************ARM STAY BY KEY FOB****************************");
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verify_armstay();
        sensors.primary_call("65 00 CF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 40 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);}

    @Test (dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group1() throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 1: ArmStay-ArmAway-Disarm, panic = Police");
        Thread.sleep(4000);
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("65 00 AF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 38 Delayed Police Panic')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
    }
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group6() throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 6: ArmStay-ArmAway-Disarm, panic = Mobile Auxiliary");
        Thread.sleep(4000);
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("65 00 BF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        System.out.println("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 39 Delayed Aux')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
    }
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group4() throws Exception {
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");
        Thread.sleep(4000);
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("65 00 CF", keyfobActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        System.out.println("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' Keyfob 40 Delayed Aux')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
    }
        @AfterTest
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
        for (int i= 40; i>37; i--) {
            delete_from_primary(i);
       }
    }

    @AfterMethod
    public void webDriverQuit(){
        adc.driver1.quit();
    }
}


