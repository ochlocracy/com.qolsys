package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*** Estimate execution time: 10 min, just panel:   min ***/

public class ArmedAwayTilt extends Setup {

    String page_name = "Arm Away mode tilt sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public ArmedAwayTilt() throws Exception {
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
        addPrimaryCall(11, 10, 6488238, 16);
        addPrimaryCall(12, 12, 6488239, 16);
        addPrimaryCall(13, 25, 6488224, 16);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    public void ArmAway_Open_Close_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalExitDelay);
        verifyArmaway();
        Thread.sleep(3000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(dependsOnMethods = {"addSensors"})
    public void ArmAwayExitDelay_10() throws Exception {
        ArmAway_Open_Close_sensor_during_Exit_Delay(10, "63 00 EA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 1)
    public void ArmAwayExitDelay_12() throws Exception {
        ArmAway_Open_Close_sensor_during_Exit_Delay(12, "63 00 FA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 2)
    public void ArmAwayExitDelay_25() throws Exception {
        ArmAway_Open_Close_sensor_during_Exit_Delay(25, "63 00 0A", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Open-Close sensor, disarm during Dialer Delay ***/
    public void ArmAway_Open_Close_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmAway_Open_Close_sensor(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("ArmAway -Open/Close Group " + group + " tilt sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 3)
    public void ArmAway_10() throws Exception {
        ArmAway_Open_Close_sensor_Alarm(10, "63 00 EA", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 4)
    public void ArmAway_12() throws Exception {
        ArmAway_Open_Close_sensor_Alarm(12, "63 00 FA", "//*[contains(text(), 'Entry delay')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 5)
    public void ArmAway_25() throws Exception {
        ArmAway_Open_Close_sensor(25, "63 00 0A", "//*[contains(text(), 'Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Tamper sensor ***/
    public void ArmAway_Tamper_sensor_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmAway Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmAway_Tamper_sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("ArmAway Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyArmaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    @Test(priority = 6)
    public void ArmAway_Tamper_10() throws Exception {
        ArmAway_Tamper_sensor_Alarm(10, "63 00 EA", "//*[contains(text(), 'Sensor 11 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 7)
    public void ArmAway_Tamper_12() throws Exception {
        ArmAway_Tamper_sensor_Alarm(12, "63 00 FA", "//*[contains(text(), 'Sensor 12 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @Test(priority = 8)
    public void ArmAway_Tamper_25() throws Exception {
        ArmAway_Tamper_sensor(25, "63 00 0A", "//*[contains(text(), 'Sensor 13 Tamper**')]", "//*[contains(text(), 'End of Tamper')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i = 13; i > 10; i--) {
            deleteFromPrimary(i);
        }
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}
