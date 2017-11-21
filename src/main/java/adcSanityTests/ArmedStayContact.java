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

/*** Estimate execution time: 26 min, just panel:   min ***/

public class ArmedStayContact extends Setup {

    String page_name = "Arm Stay mode contact sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();


    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";

    public ArmedStayContact() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
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
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        logger.info("Adding a list of sensors");
        add_primary_call(1, 10, 6619296, 1);
        add_primary_call(2, 12, 6619297, 1);
        add_primary_call(3, 13, 6619298, 1);
        add_primary_call(4, 14, 6619299, 1);
        add_primary_call(5, 16, 6619300, 1);
        add_primary_call(6, 8, 6619301, 1);
        add_primary_call(7, 9, 6619302, 1);
        add_primary_call(8, 25, 6619303, 1);
        add_primary_call(9, 15, 5570628, 2);
        add_primary_call(10, 35, 5570629, 2);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Open_Close_sensor_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " contact sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.normalEntryDelay);
        verify_armstay();
        Thread.sleep(3000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmStay_Open_Close_sensor_during_Exit_Delay_Alarm(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " contact sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Open_Close_sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " contact sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(2000);
        enter_default_user_code();
        verify_disarm();

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Open_Close_sensor_ArmStay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " contact sensor during exit delay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        verify_armstay();
        DISARM();

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Tamper_sensor(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        servcall.EVENT_ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    public void ArmStay_Tamper_sensor_ArmStay(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmStay Tamper Group " + group + " contact sensor");
        servcall.EVENT_ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Tamper a sensor");
        sensors.primary_call(DLID, SensorsActivity.TAMPER);
        Thread.sleep(4000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(3000);
        verify_armstay();
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);
    }

    /*** Open-Close sensor During Exit Delay ***/

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_10() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(10, "65 00 0A", "//*[contains(text(), 'Door/Window 1  (Sensor 1) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_12() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(12, "65 00 1A", "//*[contains(text(), 'Door/Window 2  (Sensor 2) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_13() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay_Alarm(13, "65 00 2A", "//*[contains(text(), 'Door/Window 3  (Sensor 3) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_14() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(14, "65 00 3A", "//*[contains(text(), 'Door/Window 4  (Sensor 4) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_16() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(16, "65 00 4A", "//*[contains(text(), 'Door/Window 5  (Sensor 5) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_8() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay_Alarm(8, "65 00 5A", "//*[contains(text(), 'Delayed alarm on sensor 6 in partition 1')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_9() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay_Alarm(9, "65 00 6A", "//*[contains(text(), 'Delayed alarm on sensor 7 in partition 1')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayExitDelay_25() throws Exception {
        ArmStay_Open_Close_sensor_during_Exit_Delay(25, "65 00 7A", "//*[contains(text(), 'Door/Window 8  (Sensor 8) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    /*** Open-Close sensor in Arm Stay mode ***/

    @Test(priority = 8, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_10() throws Exception {
        ArmStay_Open_Close_sensor(10, "65 00 0A", "//*[contains(text(), 'Door/Window 1  (Sensor 1) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalizer.class)
    public void ArmStat_Open_Close_sensor_12() throws Exception {
        ArmStay_Open_Close_sensor(12, "65 00 1A", "//*[contains(text(), 'Door/Window 2  (Sensor 2) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_13() throws Exception {
        ArmStay_Open_Close_sensor(13, "65 00 2A", "//*[contains(text(), 'Door/Window 3  (Sensor 3) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_14() throws Exception {
        ArmStay_Open_Close_sensor(14, "65 00 3A", "//*[contains(text(), 'Door/Window 4  (Sensor 4) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_16() throws Exception {
        ArmStay_Open_Close_sensor_ArmStay(16, "65 00 4A", "//*[contains(text(), 'Door/Window 5  (Sensor 5) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_8() throws Exception {
        ArmStay_Open_Close_sensor(8, "65 00 5A", "//*[contains(text(), 'Delayed alarm')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_9() throws Exception {
        ArmStay_Open_Close_sensor(9, "65 00 6A", "//*[contains(text(), 'Entry delay')]", "//*[contains(text(), 'Armed Stay')]");
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Open_Close_sensor_25() throws Exception {
        ArmStay_Open_Close_sensor_ArmStay(25, "65 00 7A", "//*[contains(text(), 'Door/Window 8  (Sensor 8) Opened/Closed')]", "//*[contains(text(), 'Armed Stay')]");
    }

    /*** Contact + follower ***/

    public void ArmStay_Open_Close_Follower_Motion(int group, int group2, String DLID, String DLID2, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " contact sensor followed by motion " + group2);
        ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(4000);
        sensors.primary_call(DLID2, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        sensors.primary_call(DLID2, SensorsActivity.RESTORE);
        Thread.sleep(5000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmStay_Open_Close_Follower_Contact(int group, int group2, String DLID, String DLID2, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Open/Close Group " + group + " contact sensor followed by contact " + group2);
        servcall.EVENT_ARM_STAY();
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        logger.info("Open/Close a sensor");
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primary_call(DLID2, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primary_call(DLID2, SensorsActivity.CLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 16, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_10_follower_motion15() throws Exception {
        ArmStay_Open_Close_Follower_Motion(10, 15, "65 00 0A", "55 00 44", "//*[contains(text(), 'Door/Window 1  (Sensor 1) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 17, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_10_follower_motion35() throws Exception {
        ArmStay_Open_Close_Follower_Motion(10, 35, "65 00 0A", "55 00 54", "//*[contains(text(), 'Door/Window 1  (Sensor 1) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 18, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_10_follower_contact12() throws Exception {
        ArmStay_Open_Close_Follower_Contact(10, 12, "65 00 0A", "65 00 1A", "//*[contains(text(), 'Door/Window 1  (Sensor 1) Opened/Closed')]", "//*[contains(text(), 'Delayed alarm on sensor 2 in partition 1')]");
    }

    @Test(priority = 19, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_10_follower_contact14() throws Exception {
        ArmStay_Open_Close_Follower_Contact(10, 14, "65 00 0A", "65 00 3A", "//*[contains(text(), 'Door/Window 1  (Sensor 1) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm ')]");
    }

    /*** Tamper sensor in Arm Stay mode ***/

    @Test(priority = 20, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_10() throws Exception {
        ArmStay_Tamper_sensor(10, "65 00 0A", "//*[contains(text(), 'Sensor 1 Tamper**')]", "//*[contains(text(), 'Sensor 1 End of tamper')]");
    }

    @Test(priority = 21, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_12() throws Exception {
        ArmStay_Tamper_sensor(12, "65 00 1A", "//*[contains(text(), 'Sensor 2 Tamper**')]", "//*[contains(text(), 'Sensor 2 End of tamper')]");
    }

    @Test(priority = 22, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_13() throws Exception {
        ArmStay_Tamper_sensor(13, "65 00 2A", "//*[contains(text(), 'Sensor 3 Tamper**')]", "//*[contains(text(), 'Sensor 3 End of tamper')]");
    }

    @Test(priority = 23, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_14() throws Exception {
        ArmStay_Tamper_sensor(14, "65 00 3A", "//*[contains(text(), 'Sensor 4 Tamper**')]", "//*[contains(text(), 'Sensor 4 End of tamper')]");
    }

    @Test(priority = 24, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_16() throws Exception {
        ArmStay_Tamper_sensor_ArmStay(16, "65 00 4A", "//*[contains(text(), 'Sensor 5 Tamper**')]", "//*[contains(text(), 'Sensor 5 End of tamper')]");
    }

    @Test(priority = 25, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_8() throws Exception {
        ArmStay_Tamper_sensor(8, "65 00 5A", "//*[contains(text(), 'Sensor 6 Tamper**')]", "//*[contains(text(), 'Sensor 6 End of tamper')]");
    }

    @Test(priority = 26, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_9() throws Exception {
        ArmStay_Tamper_sensor(9, "65 00 6A", "//*[contains(text(), 'Sensor 7 Tamper**')]", "//*[contains(text(), 'Sensor 7 End of tamper')]");
    }

    @Test(priority = 27, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_Tamper_sensor_25() throws Exception {
        ArmStay_Tamper_sensor_ArmStay(25, "65 00 7A", "//*[contains(text(), 'Sensor 8 Tamper**')]", "//*[contains(text(), 'Sensor 8 End of tamper')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i = 10; i > 0; i--) {
            delete_from_primary(i);
        }
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}