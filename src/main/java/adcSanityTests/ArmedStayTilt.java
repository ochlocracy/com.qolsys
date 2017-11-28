package adcSanityTests;

import adc.ADC;
import utils.ConfigProps;
import panel.PanelInfo_ServiceCalls;
import utils.SensorsActivity;
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

/*** Estimate execution time: 10 min, just panel:   min ***/

public class ArmedStayTilt extends Setup{

    String page_name = "Arm Stay mode tilt sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public ArmedStayTilt() throws Exception { SensorsActivity.init();
        ConfigProps.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");}

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        servcall.set_AUTO_STAY(0);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }
    @BeforeMethod
    public  void webDriver(){
        adc.webDriverSetUp();
    }

    /*** Open-Close sensor During Exit Delay ***/
    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding a list of sensors");
        add_primary_call(11, 10, 6488238, 16);
        add_primary_call(12, 12, 6488239, 16);
        add_primary_call(13, 25, 6488224, 16);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }
    public void ArmStay_Open_Close_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2 ) throws Exception {
        logger.info("ArmStay -Open/Close Group " +group + " tilt sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep( ConfigProps.longExitDelay/2);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalExitDelay);
        verify_armstay();
        Thread.sleep(3000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test (dependsOnMethods = {"addSensors"})
    public void ArmStayExitDelay_10 () throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(10, "63 00 EA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }
    @Test(priority = 1)
    public void ArmStayExitDelay_12 () throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(12, "63 00 FA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }
    @Test(priority = 2)
    public void ArmStayExitDelay_25 () throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(25, "63 00 0A", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }
    /*** Open-Close sensor, disarm during Dialer Delay ***/
    public void ArmStay_Open_Close_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2 ) throws Exception {
        logger.info("ArmStay -Open/Close Group " +group + " tilt sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }
    public void ArmStay_Open_Close_sensor(int group, String DLID, String element_to_verify, String element_to_verify2 ) throws Exception {
        logger.info("ArmStay -Open/Close Group " +group + " tilt sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(2000);
        verify_armstay();
        DISARM();
        Thread.sleep(2000);

       adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmStay_10 () throws Exception {
        ArmStay_Open_Close_sensor_Alarm(10, "63 00 EA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }
    @Test(priority = 4)
    public void ArmStay_12() throws Exception {
        ArmStay_Open_Close_sensor_Alarm(12, "63 00 FA", "//*[contains(text(), 'Entry delay')]", "//*[contains(text(), 'Armed Stay')]");
    }
    @Test(priority = 5)
    public void ArmStay_25() throws Exception {
        ArmStay_Open_Close_sensor(25, "63 00 0A", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    /*** Tamper sensor ***/

    public void ArmStay_Tamper_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " +group + " contact sensor");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }
    public void ArmStay_Tamper_sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " +group + " contact sensor");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 6)
    public void ArmStay_Tamper_10() throws Exception {
        ArmStay_Tamper_sensor_Alarm(10, "63 00 EA", "//*[contains(text(), 'Sensor 1 Tamper**')]","//*[contains(text(), 'End of Tamper')]");
    }
    @Test(priority = 7)
    public void ArmStay_Tamper_12() throws Exception {
        ArmStay_Tamper_sensor_Alarm(12, "63 00 FA", "//*[contains(text(), 'Sensor 2 Tamper**')]","//*[contains(text(), 'End of Tamper')]");
    }
    @Test(priority = 8)
    public void ArmStay_Tamper_25() throws Exception {
        ArmStay_Tamper_sensor(25, "63 00 0A", "//*[contains(text(), 'Sensor 3 Tamper**')]","//*[contains(text(), 'End of Tamper')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i= 13; i>10; i--) {
            delete_from_primary(i);
        }
    }
    @AfterMethod
    public void webDriverQuit(){
        adc.driver1.quit();
    }
}
