package qtmsSRF;

import adc.ADC;
import utils.ConfigProps;
import panel.Home_Page;
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


public class Dialer_Delay extends Setup {

    public Dialer_Delay() throws Exception { ConfigProps.init();}

    String page_name = "Arm Stay Arm Away KeyFob sensor testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ADC adc = new ADC();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    String AccountID = adc.getAccountId();
    String login = "pan7Aut";
    String password = "qolsys123";
    private String keyfobAway = "04 04";
    private String keyfobStay = "04 01";
    private String keyfobDisarm = "08 01";
    private String keyfobActivated = "02 01";
    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";
    String   element_to_verify = "//*[contains(text(), 'panel armed-stay ')]";
    String   element_to_verify1 = "//*[contains(text(), 'panel armed-away ')]";
    String   element_to_verify3 = "//*[contains(text(), 'panel disarmed ')]";
    private int Normal_Exit_Delay = 10;
    private int Normal_Entry_Delay = 11;
    private int Long_Exit_Delay = 13;
    private int Long_Entry_Delay = 12;
    String open = "06 00";
    String close = "04 00";
    String tamper = "01 01";
    private String restore = "04 01";
    private String active = "02 01";
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

    public void ADC_verification (String string, String string1) throws IOException, InterruptedException {
        String[] message = {string, string1};

        if (ADCexecute.equals("true")) {
            adc.New_ADC_session(AccountID);
            adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("History"))).click();
            Thread.sleep(10000);
            for (int i =0; i< message.length; i++) {
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
        }else{
            System.out.println("Set execute to TRUE to run adc verification part");
        }
        Thread.sleep(2000);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
        //servcall.set_ARM_STAY_NO_DELAY_enable();
        //servcall.set_AUTO_STAY(0);
    }

    @BeforeMethod
  public void webDriver() {adc.webDriverSetUp();}


    @Test
    public void alarm_08as() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(0);
        Thread.sleep(3000);
       // add_primary_call(10, 10,6619296, 1);
        add_primary_call(9, 9,6619303, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        sensors.primary_call("65 00 7A", tamper);
       // sensors.primary_call("65 00 0A", open);
        Thread.sleep(12000);
       // verify_in_alarm();
        Thread.sleep(2000);
       // Thread.sleep(40000);
        adc.New_ADC_session_User(login,password);
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
        enter_default_user_code();
        delete_from_primary(9);
        Thread.sleep(4000);
    }
    @Test (priority = 4)
    public void alarm_08aa() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(0);
        add_primary_call(10, 10,6619296, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verify_armaway();
        Thread.sleep(4000);
         sensors.primary_call("65 00 0A", open);
        Thread.sleep(12000);
        verify_in_alarm();
        Thread.sleep(2000);
        adc.New_ADC_session_User(login,password);
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
        enter_default_user_code();
        delete_from_primary(10);
        Thread.sleep(4000);
    }
    @Test(priority = 5)
    public void alarm_09() throws Exception {
        Thread.sleep(3000);
        Home_Page home =  PageFactory.initElements(driver, Home_Page.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.police_Alarm.click();
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Police Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Police Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
        }
    @Test(priority = 6)
    public void alarm_09d() throws Exception {
        Thread.sleep(3000);
        Home_Page home =  PageFactory.initElements(driver, Home_Page.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.police_Alarm.click();
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Police Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Police Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
    }
    @Test(priority = 7)
    public void alarm_10() throws Exception {
        Thread.sleep(3000);
        Home_Page home =  PageFactory.initElements(driver, Home_Page.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Aux_Alarm.click();
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Aux Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Aux/Medical Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
    }
    @Test(priority = 8)
    public void alarm_10d() throws Exception {
        Thread.sleep(3000);
        Home_Page home =  PageFactory.initElements(driver, Home_Page.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Aux_Alarm.click();
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Aux Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Aux/Medical Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
    }
    @Test(priority = 9)
    public void alarm_11() throws Exception {
        Thread.sleep(3000);
        Home_Page home =  PageFactory.initElements(driver, Home_Page.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Fire_Alarm.click();
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
    }
    @Test(priority = 10)
    public void alarm_11d() throws Exception {
        Thread.sleep(3000);
        Home_Page home =  PageFactory.initElements(driver, Home_Page.class);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        home.Emergency_Button.click();
        Thread.sleep(2000);
        home.Fire_Alarm.click();
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
    }
    @Test(priority = 11)
    public void alarm_04_05() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        add_primary_call(26, 26, 6750242, 5);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        sensors.primary_call("67 00 22", active);
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(26);
        Thread.sleep(4000);
    }
    @Test(priority = 12)
    public void alarm_04_05aa() throws Exception {
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(10);
        Thread.sleep(2000);
        add_primary_call(26, 26, 6750242, 5);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verify_armaway();
        sensors.primary_call("67 00 22", active);
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(26);
        Thread.sleep(4000);
    }
    @Test(priority = 13)
    public void alarm_04_05d() throws Exception {
        Thread.sleep(3000);
        add_primary_call(26, 26, 6750242, 5);
        Thread.sleep(3000);
        sensors.primary_call("67 00 22", active);
        Thread.sleep(2000);
        verify_panel_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic Pending Alarm ')]"));
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
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Fire Panic')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(4000);
        driver.findElementById("com.qolsys:id/tv_emg_cancel").click();
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(26);
        Thread.sleep(4000);
    }
    @Test (priority = 1)
    public void alarm_06d() throws Exception {
        Thread.sleep(3000);
        add_primary_call(34, 34, 7667882, 6);
        Thread.sleep(3000);
        sensors.primary_call("75 00 AA", active);
        Thread.sleep(2000);
        verify_in_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Pending Alarm ')]"));
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
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);
    }
    @Test(priority = 2)
    public void alarm_06as() throws Exception {
        Thread.sleep(3000);
        add_primary_call(34, 34, 7667882, 6);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        verify_armstay();
        Thread.sleep(3000);
        sensors.primary_call("75 00 AA", active);
        Thread.sleep(2000);
        verify_in_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Pending Alarm ')]"));
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
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);
    }
    @Test(priority = 3)
    public void alarm_06aa() throws Exception {
        Thread.sleep(3000);
        add_primary_call(34, 34, 7667882, 6);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verify_armaway();
        Thread.sleep(3000);
        sensors.primary_call("75 00 AA", active);
        Thread.sleep(2000);
        verify_in_alarm();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(6000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Pending Alarm ')]"));
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
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);
    }
    @AfterTest
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }

 @AfterMethod
public void webDriverQuit(){adc.driver1.quit();}
}
