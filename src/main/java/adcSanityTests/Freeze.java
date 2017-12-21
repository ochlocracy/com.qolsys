package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Freeze extends Setup {
    String page_name = "Arm Stay/Arm Away mode freeze sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/


    public Freeze() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
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
    public void webDriver() {
        adc.webDriverSetUp();
    }

    /*** Open-Close sensor During Exit Delay ***/
    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding a list of sensors");
        addPrimaryCall(52, 52, 7536801, 17);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Activate_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Activate Group " + group + " freeze sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification( element_to_verify, element_to_verify2);
    }

    @Test(dependsOnMethods = {"addSensors"})
    public void ArmStayExitDelay_52() throws Exception {
        ArmStay_Activate_sensor_during_Exit_Delay(52, "73 00 1A", "//*[contains(text(), 'Freeze 52')]", "//*[contains(text(), 'Delayed alarm on sensor 52 in partition 1')]");
    }

    public void ArmStay_Activate_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " freeze sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification( element_to_verify, element_to_verify2);
    }

    @Test(priority = 2)
    public void ArmStay_52() throws Exception {
        ArmStay_Activate_sensor_Alarm(52, "73 00 1A", "//*[contains(text(), 'Freeze 52')]", "//*[contains(text(), 'Delayed alarm on sensor 52 in partition 1')]");
    }

    /*** Tamper sensor ***/
    public void ArmStay_Tamper_sensor(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " freeze sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        verifyArmstay();
        DISARM();
        Thread.sleep(2000);
        adc.ADC_verification( element_to_verify, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmStayTamper_52() throws Exception {
        ArmStay_Tamper_sensor(52, "73 00 1A", "//*[contains(text(), 'Freeze 52')]", "//*[contains(text(), 'Delayed alarm on sensor 52 in partition 1')]");
    }

    /*** ARM AWAY ***/
    public void ArmAway_Activate_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate Group " + group + " freeze sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_52() throws Exception {
        ArmAway_Activate_sensor_during_Exit_Delay(52, "73 00 1A", "//*[contains(text(), 'Freeze 52')]", "//*[contains(text(), 'Delayed alarm on sensor 52 in partition 1')]");
    }

    public void ArmAway_Activate_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " freeze sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 5)
    public void ArmAway_52() throws Exception {
        ArmAway_Activate_sensor_Alarm(52, "73 00 1A", "//*[contains(text(), 'Freeze 52')]", "//*[contains(text(), 'Delayed alarm on sensor 52 in partition 1')]");
    }

    public void ArmAway_Tamper_sensor(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " freeze sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 6)
    public void ArmAwayTamper_52() throws Exception {
        ArmAway_Tamper_sensor(52, "73 00 1A", "//*[contains(text(), 'Freeze 52')]", "//*[contains(text(), 'Delayed alarm on sensor 52 in partition 1')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        deleteFromPrimary(52);
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}
