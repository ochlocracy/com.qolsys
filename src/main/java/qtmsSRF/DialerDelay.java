package qtmsSRF;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class DialerDelay extends Setup {
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
    public DialerDelay() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        //servcall.set_ARM_STAY_NO_DELAY_enable();
        //servcall.set_AUTO_STAY(0);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void alarm_08as() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(0);
        Thread.sleep(3000);
        // addPrimaryCall(10, 10,6619296, 1);
        addPrimaryCall(9, 9, 6619303, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 7A", SensorsActivity.TAMPER);
        // sensors.primaryCall("65 00 0A", open);
        Thread.sleep(12000);
        // verifyInAlarm();
        Thread.sleep(2000);
        // Thread.sleep(40000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        deleteFromPrimary(9);
        Thread.sleep(4000);
    }

    @Test(priority = 4)
    public void alarm_08aa() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(0);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(12000);
        verifyInAlarm();
        Thread.sleep(2000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        deleteFromPrimary(10);
        Thread.sleep(4000);
    }

    @Test(priority = 5)
    public void alarm_09() throws Exception {
        Thread.sleep(3000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.police_Alarm.click();
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Police Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Police Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
    }

    @Test(priority = 6)
    public void alarm_09d() throws Exception {
        Thread.sleep(3000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.police_Alarm.click();
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Police Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Police Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
    }

    @Test(priority = 7)
    public void alarm_10() throws Exception {
        Thread.sleep(3000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Aux_Alarm.click();
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Aux Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Aux/Medical Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
    }

    @Test(priority = 8)
    public void alarm_10d() throws Exception {
        Thread.sleep(3000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Aux_Alarm.click();
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Aux Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Aux/Medical Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
    }

    @Test(priority = 9)
    public void alarm_11() throws Exception {
        Thread.sleep(3000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Fire_Alarm.click();
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
    }

    @Test(priority = 10)
    public void alarm_11d() throws Exception {
        Thread.sleep(3000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Fire_Alarm.click();
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
    }

    @Test(priority = 11)
    public void alarm_04_05() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        addPrimaryCall(26, 26, 6750242, 5);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        sensors.primaryCall("67 00 22", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(26);
        Thread.sleep(4000);
    }

    @Test(priority = 12)
    public void alarm_04_05aa() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        addPrimaryCall(26, 26, 6750242, 5);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        sensors.primaryCall("67 00 22", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(26);
        Thread.sleep(4000);
    }

    @Test(priority = 13)
    public void alarm_04_05d() throws Exception {
        Thread.sleep(3000);
        addPrimaryCall(26, 26, 6750242, 5);
        Thread.sleep(3000);
        sensors.primaryCall("67 00 22", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyPanelAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(26);
        Thread.sleep(4000);
    }

    @Test(priority = 1)
    public void alarm_06d() throws Exception {
        Thread.sleep(3000);
        addPrimaryCall(34, 34, 7667882, 6);
        Thread.sleep(3000);
        sensors.primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Sensor 34 (Sensor 34) Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 2)
    public void alarm_06as() throws Exception {
        Thread.sleep(3000);
        addPrimaryCall(34, 34, 7667882, 6);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        verifyArmstay();
        Thread.sleep(3000);
        sensors.primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Sensor 34 (Sensor 34) Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 3)
    public void alarm_06aa() throws Exception {
        Thread.sleep(3000);
        addPrimaryCall(34, 34, 7667882, 6);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Panel Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("Pass: No Pending Alarm Message");
        }
        Thread.sleep(4000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Sensor 34 (Sensor 34) Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}
