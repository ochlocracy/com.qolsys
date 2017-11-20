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

public class IQShock extends Setup {

    String page_name = "ArmedStay_IQShock";
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
    private String close = "04 01";
    private String activate = "02 01";
    private String tamper = "01 01";

    public IQShock() throws Exception {
        ConfigProps.init();}

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
                        System.out.println("Pass: message is displayed " + history_message.getText());
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
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding a list of sensors");
        add_primary_call(1, 13, 6684828, 107);
        add_primary_call(2, 17, 6684829, 107);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    /*** Disarm Activate***/
    @Test(dependsOnMethods = {"addSensors"})
    public void Disarm_Activate_13() throws IOException, InterruptedException {
        logger.info("Activate a sensor");
        sensors.primary_call("66 00 C9", activate);
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", close);
        ADC_verification("//*[contains(text(), ' Activated')]", "//*[contains(text(), ' Normal')]");
    }


    /*** ArmStay Activate***/

    public void ArmStay_Activate_Sensor_during_Exit_Delay_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " IQ Shock during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay / 2);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Activate_Sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " IQ Shock during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay / 2);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        TimeUnit.SECONDS.sleep(Long_Exit_Delay / 2);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(dependsOnMethods = {"addSensors"})
    public void ArmStayExitDelay_13() throws Exception {
        ArmStay_Activate_Sensor_during_Exit_Delay_Alarm(13, "66 00 C9", "//*[contains(text(), 'Activated/Normal')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 1)
    public void ArmStayExitDelay_17() throws Exception {
        ArmStay_Activate_Sensor_during_Exit_Delay(17, "66 00 D9", "//*[contains(text(), 'Armed Stay')]", "//*[contains(text(), 'Activated')]");
    }

    /*** Arm Stay Tamper ***/

    public void ArmStay_Tamper_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay);
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

    public void ArmStay_Tamper(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(Long_Exit_Delay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, tamper);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        Thread.sleep(3000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 2)
    public void ArmStay_Tamper_13() throws Exception {
        ArmStay_Tamper_Alarm(13, "66 00 C9", "//*[contains(text(), 'Tamper')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 3)
    public void ArmStay_Tamper_17() throws Exception {
        ArmStay_Tamper(17, "66 00 D9", "//*[contains(text(), 'Tamper')]", "//*[contains(text(), 'End of Tamper')]");
    }

    /*** ArmAway Activate***/

    public void ArmAway_Activate_Sensor_during_Exit_Delay_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " IQ Shock during exit delay");
        ARM_AWAY(Long_Exit_Delay / 2);
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_13() throws Exception {
        ArmAway_Activate_Sensor_during_Exit_Delay_Alarm(13, "66 00 C9", "//*[contains(text(), 'Activated/Normal')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 5)
    public void ArmAwayExitDelay_17() throws Exception {
        ArmAway_Activate_Sensor_during_Exit_Delay_Alarm(17, "66 00 D9", "//*[contains(text(), 'Pending Alarm')]", "//*[contains(text(), 'Delayed alarm')]");
    }

    /*** ArmAway Tamper ***/

    public void ArmAway_Tamper_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
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
    public void ArmAway_Tamper_13() throws Exception {
        ArmAway_Tamper_Alarm(13, "66 00 C9", "//*[contains(text(), 'Sensor 1 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }
    @Test(priority = 7)
    public void ArmAway_Tamper_17() throws Exception {
        ArmAway_Tamper_Alarm(17, "66 00 D9", "//*[contains(text(), 'Sensor 2 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i = 2; i > 0; i--) {
            delete_from_primary(i);
        }
    }
}

