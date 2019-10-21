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

public class CarbonMonoxide extends Setup {

    String page_name = "CarbonMonoxide";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public CarbonMonoxide() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");}

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

    @Test
    public void addSensor() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding Sensor");
        addPrimaryCall(27, 34, 7667882, 6);

        adc.newAdcSession(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.requestEquipmentList();
        Thread.sleep(2000);
    }

    /*** ArmStay Activate***/

    public void ArmStay_Activate_Sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " Carbon Monoxide during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(5000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Activate_Sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " Carbon Monoxide during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify1);
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
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        verifyArmstay();
        DISARM();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmStayTamper_34() throws Exception {
        ArmStay_Tamper_Sensor(34, "75 00 AA", "//*[contains(text(), 'Sensor 27 Tamper')]", "//*[contains(text(), 'Sensor 27 Tamper alarm')]");
    }

    /*** ArmAway Activate***/

    public void ArmAway_Activate_Sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " Carbon Monoxide during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 4)
    public void ArmAwayExitDelay_34() throws Exception {
        ArmAway_Activate_Sensor_during_Exit_Delay(34, "75 00 AA", "//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Sensor 27 Alarm')]");
    }

    public void ArmAway_Activate_Sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " Carbon Monoxide during exit delay");
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
    public void ArmAway_34() throws Exception {
        ArmAway_Activate_Sensor_Alarm(34, "75 00 AA", "//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Sensor 27 Alarm')]");
    }

    /*** ArmAway Tamper ***/

    public void ArmAway_Tamper_Sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify1);
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
        deleteFromPrimary(27);
    }
}