package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import panel.Emergency_Page;
import panel.Home_Page;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class ArmedAwayAuxiliary extends Setup {
    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";
    String page_name = "ADC Smoke Test: Auxiliary Arm Away";
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    private String activate = "03 01";

    public ArmedAwayAuxiliary() throws Exception {
        ConfigProps.init();
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
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
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        Thread.sleep(2000);
        add_primary_call(43, 6, 6361649, 21);
        add_primary_call(44, 0, 6361650, 21);
        add_primary_call(48, 1, 6361652, 21);
        add_primary_call(49, 2, 6361653, 21);
        add_primary_call(50, 4, 6361654, 21);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(10000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(10000);
        adc.Request_equipment_list();
    }

    public void ArmStay_Activate_Silent_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate Group " + group + " Silent Auxiliary Police Pendant during Arm Away");
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        verify_armaway();
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        verify_armaway();
        home.DISARM_from_away.click();
        enter_default_user_code();
        Thread.sleep(15000);
        // adc website verification
        adc.ADC_verification(element_to_verify1, element_to_verify2);
    }

    public void ArmStay_Activate_Medical_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate Group " + group + " Auxiliary Medical Pendant during Arm Away");
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        verify_armaway();
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        element_verification(emg.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency Sent");
        Thread.sleep(35000);
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        // adc website verification
        adc.ADC_verification(element_to_verify1, element_to_verify2);

    }

    public void ArmStay_Activate_Police_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
        logger.info("ArmAway -Activate Group " + group + " Auxiliary Police Pendant during Arm Away");
        Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(1000);
        verify_armaway();
        logger.info("Activate a sensor");
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        element_verification(emg.Police_Emergency_Alarmed, "Police Alarmed");
        Thread.sleep(35000);
        logger.info("Cancel Emergency Alarm");
        emg.Cancel_Emergency.click();
        enter_default_user_code();
        // adc website verification
        adc.ADC_verification(element_to_verify1, element_to_verify2);
    }

    @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_2() throws Exception {
        ArmStay_Activate_Silent_Sensor(2, "61 12 53", "//*[contains(text(), '(Sensor 49) Police Panic')]", "//*[contains(text(), 'Sensor 49 Alarm')]");
    }

    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_4() throws Exception {
        ArmStay_Activate_Medical_Sensor(4, "61 12 63", "//*[contains(text(), '(Sensor 50) Pending Alarm')]", "//*[contains(text(), 'Sensor 50 Alarm')]");
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_6() throws Exception {
        ArmStay_Activate_Medical_Sensor(6, "61 12 13", "//*[contains(text(), '(Sensor 43) Pending Alarm')]", "//*[contains(text(), 'Sensor 43 Alarm')]");
    }

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_1() throws Exception {
        ArmStay_Activate_Police_Sensor(1, "61 12 43", "//*[contains(text(), '(Sensor 48) Pending Alarm')]", "//*[contains(text(), '(Sensor 48) Police Panic')]");
    }

    @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
    public void ArmStayActivateSensor_0() throws Exception {
        ArmStay_Activate_Police_Sensor(0, "61 12 23", "//*[contains(text(), '(Sensor 44) Pending Alarm')]", "//*[contains(text(), '(Sensor 44) Police Panic')]");
        for (int i = 42; i < 51; i++) {
            System.out.println(i);
            delete_from_primary(i);
        }}

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}