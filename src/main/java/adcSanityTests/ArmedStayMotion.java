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

public class ArmedStayMotion extends Setup{

    String page_name = "Arm Stay mode motion sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();



    public ArmedStayMotion() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");
       }

    private String DLID_15 = "55 00 44";
    private String DLID_17 = "55 00 54";
    private String DLID_20 = "55 00 64";
    private String DLID_25 = "55 00 74";
    private String DLID_35 = "55 00 84";
    private String Armed_Stay = "//*[contains(text(), 'Armed Stay')]";


    public void history_verification (String message) {
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(message));
            Assert.assertTrue(history_message.isDisplayed());
            {
                System.out.println("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            System.out.println("***No such element found!***");
        }
    }

    public void ArmStay_Activate_During_Delay(int group, String DLID, String element_to_verify, String element_to_verify2 ) throws Exception {
        logger.info("ArmStay During Delay Activate Group " + group + " motion sensor");
        ARM_STAY();
        logger.info("Activate a sensor");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay/3);
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyArmstay();
        DISARM();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
       }
    public void ArmStay_Activate_After_Delay_Disarm_During_Entry(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay After Delay Activate Group " + group + " motion sensor");
        ARM_STAY();
        logger.info("Activate a sensor");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyArmstay();
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay/3);
        enterDefaultUserCode();
        Thread.sleep(2000);

        adc.ADC_verification(element_to_verify, element_to_verify2);
            }
    public void ArmStay_Activate_After_Delay_Disarm_During_Dialer(int group, int sensor, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay After Delay Activate Group " + group + " motion sensor");
        ARM_STAY();
        logger.info("Activate a sensor");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyArmstay();
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        adc.ADC_verification(element_to_verify, element_to_verify2);
        Thread.sleep(2000);
        adc.driver1.findElement(By.id("ctl00_phBody_butSearch")).click();
        TimeUnit.SECONDS.sleep(3);
        history_verification("//*[contains(text(), '(Sensor " + sensor + ") Alarm')]");
        enterDefaultUserCode();
    }
    public void ArmStay_Tamper_Alarm(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay After Delay Tamper Group " + group + " motion sensor");
        ARM_STAY();
        logger.info("Tamper a sensor");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyArmstay();
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        adc.ADC_verification(element_to_verify, element_to_verify2);
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(2000);
        adc.driver1.findElement(By.id("ctl00_phBody_butSearch")).click();
        history_verification("//*[contains(text(), 'End of Tamper')]");
        enterDefaultUserCode();
    }
    public void ArmStay_Tamper(int group, String DLID, String element_to_verify, String element_to_verify2) throws Exception {
        logger.info("ArmStay After Delay Tamper Group " + group + " motion sensor");
        ARM_STAY();
        logger.info("Tamper a sensor");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        verifyArmstay();
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyArmstay();
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        adc.ADC_verification(element_to_verify, element_to_verify2);
        adc.driver1.findElement(By.id("ctl00_phBody_butSearch")).click();
        Thread.sleep(5000);
        history_verification("//*[contains(text(),  'End of Tamper')]");
        DISARM();
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
        setupLogger(page_name);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }
    @BeforeMethod
    public  void webDriver(){
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        addPrimaryCall(11, 15, 5570628, 2);
        addPrimaryCall(12, 17, 5570629, 2);
        addPrimaryCall(13, 20, 5570630, 2);
        addPrimaryCall(14, 25, 5570631, 2);
        addPrimaryCall(15, 35, 5570632, 2);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
    }

    @Test (dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayDelay_15() throws Exception {
        ArmStay_Activate_During_Delay(15, DLID_15, "//*[contains(text(), '(Sensor 1) Activated')]", Armed_Stay);
    }
    @Test (priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayDelay_17() throws Exception {
        ArmStay_Activate_During_Delay(17, DLID_17, "//*[contains(text(), '(Sensor 2) Activated')]", Armed_Stay);
    }
    @Test (priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayDelay_20() throws Exception {
        ArmStay_Activate_During_Delay(20, DLID_20, "//*[contains(text(), '(Sensor 3) Activated')]", Armed_Stay);
    }
    @Test (priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayDelay_25() throws Exception {
        ArmStay_Activate_During_Delay(25, DLID_25, "//*[contains(text(), '(Sensor 4) Activated')]", Armed_Stay);
    }
    @Test (priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayDelay_35() throws Exception {
        ArmStay_Activate_During_Delay(35, DLID_35, "//*[contains(text(), '(Sensor 5) Activated')]", Armed_Stay);
    }
    @Test (priority = 5, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayAfterDelayDisarmDuringEntry_35() throws Exception {
        ArmStay_Activate_After_Delay_Disarm_During_Entry(35, DLID_35, "//*[contains(text(), 'Entry delay on sensor 5')]", Armed_Stay);
    }
    @Test (priority = 6, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayAfterDelayDisarmDuringDialer_15() throws Exception {
        ArmStay_Activate_After_Delay_Disarm_During_Dialer(15, 1, DLID_15, "//*[contains(text(), '(Sensor 1) Pending Alarm')]", Armed_Stay);
         }
    @Test (priority = 7, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayAfterDelayDisarmDuringDialer_35() throws Exception {
        ArmStay_Activate_After_Delay_Disarm_During_Dialer(35, 5, DLID_35, "//*[contains(text(), '(Sensor 5) Pending Alarm')]", Armed_Stay);
       }
    @Test (priority = 8, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamperAfterDelay_15() throws Exception {
        ArmStay_Tamper_Alarm(15, DLID_15, "//*[contains(text(), '(Sensor 1) Tamper')]", Armed_Stay);
    }
    @Test (priority = 9, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamperAfterDelay_17() throws Exception {
        ArmStay_Tamper(17, DLID_17, "//*[contains(text(), '(Sensor 2) Tamper')]", Armed_Stay);
    }
    @Test (priority = 10, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamperAfterDelay_20() throws Exception {
        ArmStay_Tamper(20, DLID_20, "//*[contains(text(), '(Sensor 3) Tamper')]", Armed_Stay);
    }
    @Test (priority = 11, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamperAfterDelay_25() throws Exception {
        ArmStay_Tamper(25, DLID_25, "//*[contains(text(), '(Sensor 4) Tamper')]", Armed_Stay);
    }
    @Test (priority = 12, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayTamperAfterDelay_35() throws Exception {
        ArmStay_Tamper_Alarm(35, DLID_35, "//*[contains(text(), '(Sensor 5) Tamper')]", Armed_Stay);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i= 15; i>10; i--) {
            deleteFromPrimary(i);
        }
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit(){
        adc.driver1.quit();
    }
}