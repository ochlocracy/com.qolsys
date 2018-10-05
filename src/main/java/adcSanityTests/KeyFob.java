package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import panel.EmergencyPage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.RetryAnalizer;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class KeyFob extends Setup {
    String page_name = "Arm Stay Arm Away KeyFob sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";
    String element_to_verify = "//*[contains(text(), 'panel armed-stay ')]";
    String element_to_verify1 = "//*[contains(text(), 'panel armed-away ')]";
    String element_to_verify3 = "//*[contains(text(), 'panel disarmed ')]";


    public KeyFob() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
        adc.setADCexecute("true");
    }

    public void ADC_verification(String string, String string1, String string3) throws IOException, InterruptedException {
        String[] message = {string, string1, string3};

        if (ADCexecute.equals("true")) {
            adc.New_ADC_session(adc.getAccountId());
            adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
            Thread.sleep(10000);
            for (int i = 0; i < message.length; i++) {
                try {
                    WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                    Assert.assertTrue(history_message.isDisplayed());
                    {
                        System.out.println("Dealer Site History: " + history_message.getText());
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
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        addPrimaryCall(38, 1, 6619386, 102);
        addPrimaryCall(39, 6, 6619387, 102);
        addPrimaryCall(40, 4, 6619388, 102);
        Thread.sleep(2000);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
        Thread.sleep(2000);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Disarm_keyfob_group1() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************DISARM KEY FOB****************************");
        logger.info("Keyfob group 1: Disarm, panic = Police");
        Thread.sleep(4000);
        sensors.primaryCall("65 00 AF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 38 Delayed Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Disarm_keyfob_group4() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************DISARM KEY FOB****************************");
        logger.info("Keyfob group 4: Disarm, panic = Auxiliary");
        Thread.sleep(4000);
        sensors.primaryCall("65 00 CF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 40 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Disarm_keyfob_group6() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************DISARM KEY FOB****************************");
        logger.info("Keyfob group 6: Disarm, panic = Auxiliary");
        Thread.sleep(4000);
        sensors.primaryCall("65 00 BF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), 'Keyfob 39 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keyfob_group1() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM STAY BY KEY FOB****************************");
        logger.info("Keyfob group 1: ArmStay-ArmAway-Disarm, panic = Police");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verifyArmstay();
        sensors.primaryCall("65 00 AF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 38 Delayed Police Panic')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keyfob_group6() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM STAY BY KEY FOB****************************");
        logger.info("Keyfob group 6: ArmStay-ArmAway-Disarm, panic = Mobile Auxiliary");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verifyArmstay();
        sensors.primaryCall("65 00 BF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 39 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStay_by_keyfob_group4() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM STAY BY KEY FOB****************************");
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");
        Thread.sleep(4000);
        ARM_STAY();
        Thread.sleep(1000);
        verifyArmstay();
        sensors.primaryCall("65 00 CF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 40 Delayed Aux')]";
        ADC_verification(element_to_verify, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group1() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 1: ArmStay-ArmAway-Disarm, panic = Police");
        Thread.sleep(4000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        sensors.primaryCall("65 00 AF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 38 Delayed Police Panic')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group6() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 6: ArmStay-ArmAway-Disarm, panic = Mobile Auxiliary");
        Thread.sleep(4000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        sensors.primaryCall("65 00 BF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        System.out.println("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 39 Delayed Aux')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void Armaway_by_keyfob_group4() throws Exception {
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("****************************ARM AWAY BY KEY FOB****************************");
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");
        Thread.sleep(4000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        sensors.primaryCall("65 00 CF", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Emergency_sent_text, "Emergency Icon");
        System.out.println("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        String element_to_verify2 = "//*[contains(text(), ' Keyfob 40 Delayed Aux')]";
        ADC_verification(element_to_verify1, element_to_verify2, element_to_verify3);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
        for (int i = 38; i < 41; i++) {
            deleteFromPrimary(i);
        }
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}


