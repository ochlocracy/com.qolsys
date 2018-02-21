package adcSanityTests;

import adc.ADC;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class IQShock extends Setup {
    String page_name = "ArmedStay_IQShock";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    private String open = "06 00";
    private String close = "04 01";
    private String activate = "02 01";
    private String tamper = "01 01";

    public IQShock() throws Exception {
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

    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding a list of sensors");
        addPrimaryCall(3, 13, 6684828, 107);
        addPrimaryCall(4, 17, 6684829, 107);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    /*** Disarm Activate***/
    @Test(dependsOnMethods = {"addSensors"})
    public void Disarm_Activate_13() throws IOException, InterruptedException {
        logger.info("Activate a sensor");
        sensors.primaryCall("66 00 C9", activate);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", close);
        adc.ADC_verification("//*[contains(text(), ' Activated')]", "//*[contains(text(), ' Normal')]");
    }

    /*** ArmStay Activate***/
    public void ArmStay_Activate_Sensor_during_Exit_Delay_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " IQ Shock during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, activate);
        Thread.sleep(10000);
        sensors.primaryCall(DLID, close);
        Thread.sleep(10000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Activate_Sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Activate " + group + " IQ Shock during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, activate);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, close);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
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
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, tamper);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, close);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Tamper(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, tamper);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, close);
        Thread.sleep(3000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
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
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, activate);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, close);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
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
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, tamper);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, close);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 6)
    public void ArmAway_Tamper_13() throws Exception {
        ArmAway_Tamper_Alarm(13, "66 00 C9", "//*[contains(text(), 'Sensor 3 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 7)
    public void ArmAway_Tamper_17() throws Exception {
        ArmAway_Tamper_Alarm(17, "66 00 D9", "//*[contains(text(), 'Sensor 4 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        deleteFromPrimary(3);
        deleteFromPrimary(4);
        service.stop();
    }
}

