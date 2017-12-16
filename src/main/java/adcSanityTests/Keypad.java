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

public class Keypad extends Setup {
    public Keypad() throws Exception {
        ConfigProps.init();}

    String page_name = "Arm Stay Arm Away Keypad sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    String AccountID = adc.getAccountId();
    String ADCexecute = "true";
    private String keypadActivated = "03 01";
    String   element_to_verify = "//*[contains(text(), 'panel armed-stay ')]";
    String   element_to_verify1 = "//*[contains(text(), 'panel armed-away ')]";
    String   element_to_verify3 = "//*[contains(text(), 'panel disarmed ')]";
    private int Long_Exit_Delay = 13;

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
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        add_primary_call(41, 0, 8716538, 104);
        Thread.sleep(2000);
        add_primary_call(42, 2, 8716539, 104);
        Thread.sleep(2000);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keypad_group0() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM STAY BY KEYPAD****************************");
        logger.info("Keypad group 0 - Fixed Intrusion");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verify_armstay();
        sensors.primary_call("85 00 AF", keypadActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' KeypadTouchscreen 41 Delayed Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
        Thread.sleep(2000);
    }
    /** please disarm system from User Site, after 2group keypad activation **/
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keypad_group2() throws Exception {
        logger.info("****************************ARM STAY BY KEYPAD****************************");
        logger.info("Keypad group 2 - Fixed Silent");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verify_armstay();
        sensors.primary_call("85 00 BF", keypadActivated);
        Thread.sleep(2000);
        verify_armstay();
        System.out.println("Pass: the system  continues to be in ARM STAY");
        String   element_to_verify2 = "//*[contains(text(), 'Keypad/Touchscreen(42) Silent Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
        DISARM();
        Thread.sleep(2000);
    }

    @Test (dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group1() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 0 - Fixed Intrusion");
        Thread.sleep(4000);
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("85 00 AF", keypadActivated);
        Thread.sleep(2000);
        element_verification(emg.Emergency_sent_text, "Emergency Icon" );
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        String   element_to_verify2 = "//*[contains(text(), ' KeypadTouchscreen 41 Delayed Police Panic')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
        Thread.sleep(2000);
    }
    /** please disarm system from User Site, after 2group keypad activation **/
    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_by_keypad_group2() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("****************************ARM AWAY BY KEYPAD****************************");
        logger.info("Keypad group 2 - Fixed Silent");
        Thread.sleep(4000);
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("85 00 BF", keypadActivated);
        Thread.sleep(2000);
        verify_armaway();
        System.out.println("Pass: the system continues to be in ARM AWAY");
        String   element_to_verify2 = "//*[contains(text(), 'Keypad/Touchscreen(42) Silent Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
        home.DISARM_from_away.click();
        enter_default_user_code();
        Thread.sleep(2000);
           }

    @AfterTest
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
        for (int i= 42; i>40; i--) {
            delete_from_primary(i);
        }
    }


    @AfterMethod
    public void webDriverQuit(){
        adc.driver1.quit();  }
}
