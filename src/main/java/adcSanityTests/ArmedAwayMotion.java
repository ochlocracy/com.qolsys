package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.RetryAnalizer;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Approximate test run duration:
 **/
public class ArmedAwayMotion extends Setup {

    String page_name = "Arm Away mode motion sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    private String DLID_15 = "55 00 44";
    private String DLID_17 = "55 00 54";
    private String DLID_20 = "55 00 64";
    private String DLID_25 = "55 00 74";
    private String DLID_35 = "55 00 84";
    private String Armed_Away = "//*[contains(text(), 'Armed Away')]";

    public ArmedAwayMotion() throws Exception {
        SensorsActivity.init();
        ConfigProps.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");
    }

    public void ArmAway_Activate_During_Delay(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway During Delay Activate Group " + group + " motion sensor");
        ARM_AWAY(ConfigProps.longExitDelay / 3);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyArmaway();
        DISARM();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
    }

    public void ArmAway_Activate_After_Delay_Disarm_During_Entry(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway After Delay Activate Group " + group + " motion sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        logger.info("Activate a sensor");
        verifyArmaway();
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 3);
        enterDefaultUserCode();
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
    }

    public void ArmAway_Activate_After_Delay_Disarm_During_Dialer(int group, int sensor, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway After Delay Activate Group " + group + " motion sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        logger.info("Activate a sensor");
        Thread.sleep(2000);
        verifyArmaway();
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(15000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(2000);
    }

    public void ArmAway_Tamper(int group, int sensor, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmAway After Delay Tamper Group " + group + " motion sensor");
        ARM_AWAY(ConfigProps.longExitDelay);
        logger.info("Tamper a sensor");
        verifyArmaway();
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(4000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        if (group == 25) {
            verifyArmaway();
        } else {
            verifyInAlarm();
        }
        adc.ADC_verification(element_to_verify, element_to_verify2);
        if (group == 25) {
            DISARM();
        } else {
            enterDefaultUserCode();
        }
        }

        @BeforeTest
        public void capabilities_setup () throws Exception {
            setupDriver(get_UDID(), "http://127.0.1.1", "4723");
            setupLogger(page_name);
            servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalExitDelay);
            Thread.sleep(1000);
            servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalEntryDelay);
            Thread.sleep(1000);
            servcall.set_LONG_ENTRY_DELAY(ConfigProps.longExitDelay);
            Thread.sleep(1000);
            servcall.set_LONG_EXIT_DELAY(ConfigProps.longEntryDelay);
            servcall.set_AUTO_STAY(0);
            servcall.set_ARM_STAY_NO_DELAY_disable();
        }

        @BeforeMethod
        public void webDriver () {
            adc.webDriverSetUp();
        }

        @Test
        public void addSensors () throws IOException, InterruptedException {
            Thread.sleep(2000);
            addPrimaryCall(11, 15, 5570628, 2);
            addPrimaryCall(12, 17, 5570629, 2);
            addPrimaryCall(3, 20, 5570630, 2);
            addPrimaryCall(4, 25, 5570631, 2);
            addPrimaryCall(5, 35, 5570632, 2);

            adc.newAdcSession(adc.getAccountId());
            Thread.sleep(2000);
            adc.driver1.findElement(By.partialLinkText("Sensors")).click();
            Thread.sleep(2000);
            adc.requestEquipmentList();
        }

        @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayDelay_15 () throws Exception {
            ArmAway_Activate_During_Delay(15, DLID_15, "//*[contains(text(), '(Sensor 11) Activated')]", Armed_Away);
        }

        @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayDelay_17 () throws Exception {
            ArmAway_Activate_During_Delay(17, DLID_17, "//*[contains(text(), '(Sensor 12) Activated')]", Armed_Away);
        }

        @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayDelay_20 () throws Exception {
            ArmAway_Activate_During_Delay(20, DLID_20, "//*[contains(text(), '(Sensor 3) Activated')]", Armed_Away);
        }

        @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayDelay_25 () throws Exception {
            ArmAway_Activate_During_Delay(25, DLID_25, "//*[contains(text(), '(Sensor 4) Activated')]", Armed_Away);
        }

        @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayDelay_35 () throws Exception {
            ArmAway_Activate_During_Delay(35, DLID_35, "//*[contains(text(), '(Sensor 5) Activated')]", Armed_Away);
        }

        @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayAfterDelayDisarmDuringEntry_35 () throws Exception {
            ArmAway_Activate_After_Delay_Disarm_During_Entry(35, DLID_35, "//*[contains(text(), 'Entry delay on sensor 5')]", Armed_Away);
        }

        @Test(priority = 6, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayAfterDelayDisarmDuringDialer_15 () throws Exception {
            ArmAway_Activate_After_Delay_Disarm_During_Dialer(15, 1, DLID_15, "//*[contains(text(), '(Sensor 11) Pending Alarm')]", Armed_Away);
        }

        @Test(priority = 7, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayAfterDelayDisarmDuringDialer_35 () throws Exception {
            ArmAway_Activate_After_Delay_Disarm_During_Dialer(35, 5, DLID_35, "//*[contains(text(), '(Sensor 5) Pending Alarm')]", Armed_Away);
        }

        @Test(priority = 8, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayTamperAfterDelay_15 () throws Exception {
            ArmAway_Tamper(15, 1, DLID_15, "//*[contains(text(), ' (Sensor 11) Tamper')]", Armed_Away);
        }

        @Test(priority = 9, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayTamperAfterDelay_17 () throws Exception {
            ArmAway_Tamper(17, 2, DLID_17, "//*[contains(text(), '(Sensor 12) Tamper')]", Armed_Away);
        }

        @Test(priority = 10, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayTamperAfterDelay_20 () throws Exception {
            ArmAway_Tamper(20, 3, DLID_20, "//*[contains(text(), '(Sensor 3) Tamper')]", Armed_Away);
        }

        @Test(priority = 11, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayTamperAfterDelay_25 () throws Exception {
            ArmAway_Tamper(25, 4, DLID_25, "//*[contains(text(), '(Sensor 4) Tamper')]", Armed_Away);
        }

        @Test(priority = 12, retryAnalyzer = RetryAnalizer.class)
        public void ArmAwayTamperAfterDelay_35 () throws Exception {
            ArmAway_Tamper(35, 5, DLID_35, "//*[contains(text(), '(Sensor 5) Tamper')]", Armed_Away);
        }

        @AfterTest
        public void tearDown () throws IOException, InterruptedException {
            driver.quit();
            for (int i = 12; i > 2; i--) {
                deleteFromPrimary(i);
            }
        }

        @AfterMethod
        public void webDriverQuit () {
            adc.driver1.quit();
        }
    }
