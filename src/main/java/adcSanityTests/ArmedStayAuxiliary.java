package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import panel.EmergencyPage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

/**
 * Created by qolsysauto (Jeff) on 8/7/17.
 */
public class ArmedStayAuxiliary extends Setup {
    // PRECONDITIONS: Disable SIA limits, set Entry-Exit Delay time to 30, 31, 32, 33 sec; Disable ArmStay No-Delay setting
    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";
    String page_name = "adc Smoke Test: Auxiliary Arm Stay";
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();

    public ArmedStayAuxiliary() throws Exception {
        SensorsActivity.init();
        ConfigProps.init();
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
        addPrimaryCall(11, 6, 6361649, 21);
        addPrimaryCall(12, 0, 6361650, 21);
        addPrimaryCall(13, 1, 6361652, 21);
        addPrimaryCall(14, 2, 6361653, 21);
        addPrimaryCall(15, 4, 6361654, 21);

        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Activate_Silent_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Activate Group " + group + " Silent Auxiliary Police Pendant during Arm Stay");
        ARM_STAY();
        Thread.sleep(13000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyArmstay();
        DISARM();
        Thread.sleep(15000);
        // adc website verification
        adc.ADC_verification(element_to_verify1, element_to_verify2);
    }

    public void ArmStay_Activate_Medical_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Activate Group " + group + " Auxiliary Medical Pendant during Arm Stay");
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_STAY();
        Thread.sleep(13000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency Sent");
        Thread.sleep(35000);
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        // adc website verification
        adc.ADC_verification(element_to_verify1, element_to_verify2);
    }

    public void ArmStay_Activate_Police_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmStay -Activate Group " + group + " Auxiliary Police Pendant during Arm Stay");
        EmergencyPage emg = PageFactory.initElements(driver, EmergencyPage.class);
        ARM_STAY();
        Thread.sleep(13000);
        logger.info("Activate a sensor");
        sensors.primaryCall(DLID, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        elementVerification(emg.Police_Emergency_Alarmed, "Police Alarmed");
        Thread.sleep(35000);
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enterDefaultUserCode();
        // adc website verification
        adc.ADC_verification(element_to_verify1, element_to_verify2);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_2() throws Exception {
        ArmStay_Activate_Silent_Sensor(2, "61 12 53", "//*[contains(text(), '(Sensor 14) Police Panic')]", "//*[contains(text(), 'Sensor 14 Alarm')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_4() throws Exception {
        ArmStay_Activate_Medical_Sensor(4, "61 12 63", "//*[contains(text(), '(Sensor 15) Pending Alarm')]", "//*[contains(text(), 'Sensor 15 Alarm')]");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_6() throws Exception {
        ArmStay_Activate_Medical_Sensor(6, "61 12 13", "//*[contains(text(), '(Sensor 11) Pending Alarm')]", "//*[contains(text(), 'Sensor 11 Alarm')]");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_1() throws Exception {
        ArmStay_Activate_Police_Sensor(1, "61 12 43", "//*[contains(text(), '(Sensor 13) Pending Alarm')]", "//*[contains(text(), '(Sensor 13) Police Panic')]");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_0() throws Exception {
        ArmStay_Activate_Police_Sensor(0, "61 12 23", "//*[contains(text(), '(Sensor 12) Pending Alarm')]", "//*[contains(text(), '(Sensor 12) Police Panic')]");
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        for (int i = 15; i > 10; i--) {
            deleteFromPrimary(i);
        }
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}
