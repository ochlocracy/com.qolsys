package adcSanityTests;

import adc.ADC;
import utils.ConfigProps;
import panel.Emergency_Page;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;
import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * Created by qolsysauto on 9/7/17.
 */
public class Disarm_Auxiliary extends Setup{
        // PRECONDITIONS: Disable SIA limits, set Entry-Exit Delay time to 30, 31, 32, 33 sec; Disable ArmStay No-Delay setting
        /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
        String ADCexecute = "true";
        public Disarm_Auxiliary() throws Exception {
            ConfigProps.init();}
        String page_name = "adc Smoke Test: Auxiliary in Disarm";
        PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
        Logger logger = Logger.getLogger(page_name);
        Sensors sensors = new Sensors();
        ADC adc = new ADC();
        String AccountID = adc.getAccountId();

        private int Normal_Exit_Delay = 10;
        private int Normal_Entry_Delay = 11;
        private int Long_Exit_Delay = 13;
        private int Long_Entry_Delay = 12;
        private String activate = "03 01";

        public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
            String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
            rt.exec(ConfigProps.adbPath + add_primary);
            // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
        }

        public void delete_from_primary(int zone) throws IOException, InterruptedException {
            String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
            rt.exec(ConfigProps.adbPath + deleteFromPrimary);
            System.out.println(deleteFromPrimary);
        }

        public void ADC_verification(String string, String string1) throws IOException, InterruptedException {
            String[] message = {string, string1};

            if (ADCexecute.equals("true")) {
                adc.New_ADC_session(AccountID);
                adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
                Thread.sleep(10000);
                for (int i = 0; i < message.length; i++) {
                    try {
                        WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                        Assert.assertTrue(history_message.isDisplayed());
                        {
                            System.out.println("Pass: message is displayed " + history_message.getText());
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
            setup_driver(get_UDID(), "http://127.0.1.1", "4723");
            setup_logger(page_name);
            servcall.set_NORMAL_ENTRY_DELAY(Normal_Exit_Delay);
            Thread.sleep(1000);
            servcall.set_NORMAL_EXIT_DELAY(Normal_Entry_Delay);
            Thread.sleep(1000);
            servcall.set_LONG_ENTRY_DELAY(Long_Exit_Delay);
            Thread.sleep(1000);
            servcall.set_LONG_EXIT_DELAY(Long_Entry_Delay);

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
            add_primary_call(47, 25, 6361665, 21);

            adc.New_ADC_session(adc.getAccountId());
            Thread.sleep(10000);
            adc.driver1.findElement(By.partialLinkText("sensors")).click();
            Thread.sleep(10000);
            adc.Request_equipment_list();

        }

        public void Activate_Silent_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
            logger.info("ArmStay -Activate Group " + group + " Silent Auxiliary Police Pendant during Disarm");
            verify_disarm();
            Thread.sleep(2000);
            logger.info("Activate a sensor");
            sensors.primary_call(DLID, activate);
            Thread.sleep(2000);
            verify_disarm();
            Thread.sleep(20000);
            // adc website verification
            ADC_verification(element_to_verify1, element_to_verify2);
        }

        public void Activate_Medical_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
            logger.info("ArmStay -Activate Group " + group + " Auxiliary Medical Pendant during Disarm");
            Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
            verify_disarm();
            Thread.sleep(2000);
            logger.info("Activate a sensor");
            sensors.primary_call(DLID, activate);
            Thread.sleep(2000);
            element_verification(emg.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency Sent");
            Thread.sleep(35000);
            logger.info("Cancel Emergency Alarm");
            emg.Cancel_Emergency.click();
            enter_default_user_code();
            // adc website verification
            ADC_verification(element_to_verify1, element_to_verify2);

        }

        public void Activate_Police_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
            logger.info("ArmStay -Activate Group " + group + " Auxiliary Police Pendant during Disarm");
            Emergency_Page emg = PageFactory.initElements(driver, Emergency_Page.class);
            verify_disarm();
            Thread.sleep(2000);
            logger.info("Activate a sensor");
            sensors.primary_call(DLID, activate);
            Thread.sleep(2000);
            element_verification(emg.Police_Emergency_Alarmed, "Police Alarmed");
            Thread.sleep(35000);
            logger.info("Cancel Emergency Alarm");
            emg.Cancel_Emergency.click();
            enter_default_user_code();
            // adc website verification
            ADC_verification(element_to_verify1, element_to_verify2);
        }

        public void Activate_Safety_Sensor(int group, String DLID, String element_to_verify1, String element_to_verify2) throws Exception {
            logger.info("ArmStay -Activate Group " + group + " Safety Sensor during Disarm");
            verify_disarm();
            Thread.sleep(2000);
            logger.info("Activate a sensor");
            sensors.primary_call(DLID, activate);
            Thread.sleep(2000);
            verify_disarm();
            Thread.sleep(20000);
            // adc website verification
            ADC_verification(element_to_verify1, element_to_verify2);
    }


        @Test(dependsOnMethods = {"addSensors"}, retryAnalyzer = RetryAnalizer.class)
        public void ArmStayActivateSensor_2() throws Exception {
            Activate_Silent_Sensor(2, "61 12 53", "//*[contains(text(), '(Sensor 49) Silent Police Panic')]", "//*[contains(text(), 'Sensor 49 Alarm')]");
        }

        @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
        public void ArmStayActivateSensor_4() throws Exception {
            Activate_Medical_Sensor(4, "61 12 63", "//*[contains(text(), '(Sensor 50) Pending Alarm')]", "//*[contains(text(), 'Sensor 50 Alarm')]");
        }

        @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
        public void ArmStayActivateSensor_6() throws Exception {
            Activate_Medical_Sensor(6, "61 12 13", "//*[contains(text(), '(Sensor 43) Pending Alarm')]", "//*[contains(text(), 'Sensor 43 Alarm')]");
        }

        @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
        public void ArmStayActivateSensor_1() throws Exception {
            Activate_Police_Sensor(1, "61 12 43", "//*[contains(text(), '(Sensor 48) Pending Alarm')]", "//*[contains(text(), '(Sensor 48) Police Panic')]");
        }

        @Test(priority = 4, retryAnalyzer = RetryAnalizer.class)
        public void ArmStayActivateSensor_0() throws Exception {
            Activate_Police_Sensor(0, "61 12 23", "//*[contains(text(), '(Sensor 44) Pending Alarm')]", "//*[contains(text(), '(Sensor 44) Police Panic')]");
        }

         @Test(priority = 5, retryAnalyzer = RetryAnalizer.class)
         public void ArmStayActivateSensor_25() throws Exception {
            Activate_Safety_Sensor(25, "61 12 65", "//*[contains(text(), '(Sensor 47) Activated')]", "//*[contains(text(), '(Sensor 47) Idle')]");
    }


        @AfterTest
        public void tearDown() throws IOException, InterruptedException {
            driver.quit();
            for (int i = 50; i > 0; i--) {
                delete_from_primary(i);
            }
        }

        @AfterMethod
        public void webDriverQuit() {
            adc.driver1.quit();
        }

    }