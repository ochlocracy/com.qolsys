package adcSanityTests;

import adc.ADC;
import utils.ConfigProps;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;
import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CarbonMonoxide extends Setup {

    String page_name = "CarbonMonoxide";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    String AccountID = adc.getAccountId();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";

    private int Normal_Exit_Delay = 10;
    private int Normal_Entry_Delay = 11;
    private int Long_Exit_Delay = 12;
    private int Long_Entry_Delay = 13;
    private String open = "06 00";
    private String close = "00 01";
    private String activate = "02 01";
    private String tamper = "01 01";

    public CarbonMonoxide() throws Exception {
        ConfigProps.init();
    }

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

    public void ADC_verification(String string, String string1) throws IOException, InterruptedException {
        String[] message = {string, string1};

        if (ADCexecute.equals("true")) {
            adc.New_ADC_session(AccountID);
            adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
            Thread.sleep(10000);
            for (int i = 0; i < message.length; i++) {
                try {
                    WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                    Assert.assertTrue(history_message.isDisplayed());
                    {
                        System.out.println(" Pass: message is displayed " + history_message.getText());
                    }
                } catch (Exception e) {
                    System.out.println("***No such element found!***");
                }
            }
        } else {
            System.out.println("Set execute to TRUE to run adc verification part");
        }
        Thread.sleep(2000);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
        servcall.set_NORMAL_ENTRY_DELAY(Normal_Exit_Delay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(Normal_Entry_Delay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(Long_Exit_Delay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(Long_Entry_Delay);

        servcall.set_AUTO_STAY(0);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensor() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding Sensor");
        add_primary_call(27, 34, 7667882, 6);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
        Thread.sleep(2000);
    }

    /*** ArmStay Activate***/

    public void ArmStay_Activate_Sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " Carbon Monoxide during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay / 2);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        verify_in_alarm();
        sensors.primary_call(DLID, close);
        Thread.sleep(5000);
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Activate_Sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " Carbon Monoxide during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay / 2);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(dependsOnMethods = {"addSensor"})
    public void ArmStayExitDelay_34() throws Exception {
        ArmStay_Activate_Sensor_during_Exit_Delay(34, "75 00 AA", "//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Sensor 27 Alarm')]");
    }

    @Test(priority = 2)
    public void ArmStay_34() throws Exception {
        ArmStay_Activate_Sensor_Alarm(34, "75 00 AA", "//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Sensor 27 Alarm')]");
    }

    /*** Arm Stay Tamper ***/

    public void ArmStay_Tamper_Sensor(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " Carbon Monoxide during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, tamper);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        verify_armstay();
        DISARM();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmStayTamper_34() throws Exception {
        ArmStay_Tamper_Sensor(34, "75 00 AA", "//*[contains(text(), 'Sensor 27 Tamper')]", "//*[contains(text(), 'Sensor 27 Tamper alarm')]");
    }

    /*** ArmAway Activate***/

    public void ArmAway_Activate_Sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " Carbon Monoxide during exit delay");
        ARM_AWAY(Long_Exit_Delay / 2);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_34() throws Exception {
        ArmAway_Activate_Sensor_during_Exit_Delay(34, "75 00 AA", "//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Sensor 27 Alarm')]");
    }

    public void ArmAway_Activate_Sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " Carbon Monoxide during exit delay");
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 5)
    public void ArmAway_34() throws Exception {
        ArmAway_Activate_Sensor_Alarm(34, "75 00 AA", "//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Sensor 27 Alarm')]");
    }

    /*** ArmAway Tamper ***/

    public void ArmAway_Tamper_Sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, tamper);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        Thread.sleep(3000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 6)
    public void ArmAwayTamper_34() throws Exception {
        ArmAway_Tamper_Sensor(34, "75 00 AA", "//*[contains(text(), 'Sensor 27 Tamper')]", "//*[contains(text(), 'Sensor 27 Tamper alarm')]");
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        delete_from_primary(27);
    }
}



