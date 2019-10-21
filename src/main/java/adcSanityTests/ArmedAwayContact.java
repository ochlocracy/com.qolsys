package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import utils.RetryAnalizer;
import utils.Setup;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;

import java.io.IOException;

/**
 * Estimate execution time: 31 min, just panel: 22 min
 ***/

public class ArmedAwayContact extends Setup {

    String page_name = "Arm Away mode contact sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public ArmedAwayContact() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4724");
        setupLogger(page_name);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.longExitDelay);
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
        addPrimaryCall(11, 10, 6619296, 1);
        addPrimaryCall(12, 12, 6619297, 1);
        addPrimaryCall(3, 13, 6619298, 1);
        addPrimaryCall(4, 14, 6619299, 1);
        addPrimaryCall(5, 16, 6619300, 1);
        addPrimaryCall(6, 8, 6619301, 1);
        addPrimaryCall(7, 9, 6619302, 1);
        addPrimaryCall(8, 25, 6619303, 1);
        addPrimaryCall(9, 17, 5570628, 2);
        addPrimaryCall(10, 20, 5570629, 2);
        adc.newAdcSession(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.requestEquipmentList();
    }

    public void ArmAway_Open_Close_during_Exit_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " contact sensor during exit delay");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(17000);
        verifyArmaway();
        Thread.sleep(5000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmAway_Open_Close_during_Exit_Delay_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway - Open/Close Group " + group + " contact sensor during exit delay");
        ARM_AWAY(ConfigProps.longExitDelay / 2);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(17000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_10() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay(10, "65 00 0A", "//*[contains(text(), '(Sensor 11) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_12() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay(12, "65 00 1A", "//*[contains(text(), '(Sensor 12) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_13() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay_Alarm(13, "65 00 2A", "//*[contains(text(), '(Sensor 3) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_14() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay(14, "65 00 3A", "//*[contains(text(), '(Sensor 4) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_16() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay(16, "65 00 4A", "//*[contains(text(), '(Sensor 5) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_8() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay_Alarm(8, "65 00 5A", "//*[contains(text(), 'Door/Window 6')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 6, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_9() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay_Alarm(9, "65 00 6A", "//*[contains(text(), 'Delayed alarm on sensor 7 in partition 1')]", "//*[contains(text(), 'Armed Away')]");
    }

    @Test(priority = 7, retryAnalyzer = RetryAnalizer.class)
    public void ArmAwayExitDelay_25() throws Exception {
        ArmAway_Open_Close_during_Exit_Delay(25, "65 00 7A", "//*[contains(text(), '(Sensor 8) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /* ARM AWAY Open-Close sensors*/

    public void ArmAway_Open_Close(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " contact sensor during exit delay");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(17000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmAway_Open_Close_ArmAway(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " contact sensor during exit delay");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_AWAY(ConfigProps.longExitDelay);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(17000);
        verifyArmaway();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 8, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_10() throws Exception {
        ArmAway_Open_Close(10, "65 00 0A", "//*[contains(text(), '(Sensor 11) Opened/Closed')]", "//*[contains(text(), 'Delayed alarm on sensor 11 in partition 1')]");
    }

    @Test(priority = 9, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_12() throws Exception {
        ArmAway_Open_Close(12, "65 00 1A", "//*[contains(text(), '(Sensor 12) Opened/Closed')]", "//*[contains(text(), 'Delayed alarm on sensor 12 in partition 1')]");
    }

    @Test(priority = 10, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_13() throws Exception {
        ArmAway_Open_Close(13, "65 00 2A", "//*[contains(text(), '(Sensor 3) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 11, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_14() throws Exception {
        ArmAway_Open_Close(14, "65 00 3A", "//*[contains(text(), '(Sensor 4) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 12, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_16() throws Exception {
        ArmAway_Open_Close(16, "65 00 4A", "//*[contains(text(), '(Sensor 5) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 13, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_8() throws Exception {
        ArmAway_Open_Close(8, "65 00 5A", "//*[contains(text(), 'Delayed alarm')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 14, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_9() throws Exception {
        ArmAway_Open_Close(9, "65 00 6A", "//*[contains(text(), 'Entry delay on sensor 7')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 15, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_25() throws Exception {
        ArmAway_Open_Close_ArmAway(25, "65 00 7A", "//*[contains(text(), '(Sensor 8) Opened/Closed')]", "//*[contains(text(), 'Armed Away')]");
    }

    /*** Contact + follower ***/

    public void ArmAway_Open_Close_Follower_Motion(int group, int group2, String DLID, String DLID2, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " contact sensor followed by motion " + group2);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(4000);
        sensors.primaryCall(DLID2, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        sensors.primaryCall(DLID2, SensorsActivity.RESTORE);
        Thread.sleep(5000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    public void ArmAway_Open_Close_Follower_Contact(int group, int group2, String DLID, String DLID2, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Open/Close Group " + group + " contact sensor followed by contact " + group2);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(4000);
        sensors.primaryCall(DLID2, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID2, SensorsActivity.CLOSE);
        Thread.sleep(5000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
    }

    @Test(priority = 16, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_10_follower_motion17() throws Exception {
        ArmAway_Open_Close_Follower_Motion(10, 17, "65 00 0A", "55 00 44", "//*[contains(text(), '(Sensor 11) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 17, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_10_follower_motion20() throws Exception {
        ArmAway_Open_Close_Follower_Motion(10, 20, "65 00 0A", "55 00 54", "//*[contains(text(), '(Sensor 11) Opened/Closed')]", "//*[contains(text(), 'Pending Alarm')]");
    }

    @Test(priority = 18, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_10_follower_contact12() throws Exception {
        ArmAway_Open_Close_Follower_Contact(10, 12, "65 00 0A", "65 00 1A", "//*[contains(text(), '(Sensor 11) Opened/Closed')]", "//*[contains(text(), 'Delayed alarm on sensor 12 in partition 1')]");
    }

    @Test(priority = 19, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_10_follower_contact14() throws Exception {
        ArmAway_Open_Close_Follower_Contact(10, 14, "65 00 0A", "65 00 3A", "//*[contains(text(), '(Sensor 11 Opened/Closed')]", "//*[contains(text(), 'Delayed alarm on sensor 4 in partition 1')]");
    }

    /*** ARM AWAY Tamper sensors ***/

    public void ArmAway_Tamper(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        logger.info("ArmAway Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Open/Close a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(17000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);

    }

    public void ArmAway_Tamper_ArmAway(int group, String DLID, String element_to_verify, String element_to_verify1) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("ArmAway Tamper Group " + group + " contact sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        logger.info("Trip a sensor");
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(17000);
        verifyArmaway();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify1);

    }

    @Test(priority = 20, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_10() throws Exception {
        ArmAway_Tamper(10, "65 00 0A", "//*[contains(text(), 'Sensor 11 Tamper**')]", "//*[contains(text(), 'Sensor 11 End of tamper')]");
    }

    @Test(priority = 21, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_12() throws Exception {
        ArmAway_Tamper(12, "65 00 1A", "//*[contains(text(), 'Sensor 12 Tamper**')]", "//*[contains(text(), 'Sensor 12 End of tamper')]");
    }

    @Test(priority = 22, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_13() throws Exception {
        ArmAway_Tamper(13, "65 00 2A", "//*[contains(text(), 'Sensor 3 Tamper**')]", "//*[contains(text(), 'Sensor 3 End of tamper')]");
    }

    @Test(priority = 23, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_14() throws Exception {
        ArmAway_Tamper(14, "65 00 3A", "//*[contains(text(), 'Sensor 4 Tamper**')]", "//*[contains(text(), 'Sensor 4 End of tamper')]");
    }

    @Test(priority = 24, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_16() throws Exception {
        ArmAway_Tamper(16, "65 00 4A", "//*[contains(text(), 'Sensor 5 Tamper**')]", "//*[contains(text(), 'Sensor 5 End of tamper')]");
    }

    @Test(priority = 25, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_8() throws Exception {
        ArmAway_Tamper(8, "65 00 5A", "//*[contains(text(), 'Sensor 6 Tamper**')]", "//*[contains(text(), 'Sensor 6 End of tamper')]");
    }

    @Test(priority = 26, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_9() throws Exception {
        ArmAway_Tamper(9, "65 00 6A", "//*[contains(text(), 'Sensor 7 Tamper**')]", "//*[contains(text(), 'Sensor 7 End of tamper')]");
    }

    @Test(priority = 27, retryAnalyzer = RetryAnalizer.class)
    public void ArmAway_Tamper_25() throws Exception {
        ArmAway_Tamper_ArmAway(25, "65 00 7A", "//*[contains(text(), 'Sensor 8 Tamper**')]", "//*[contains(text(), 'Sensor 8 End of tamper')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i = 13; i > 2; i--) {
            deleteFromPrimary(i);
        }
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}