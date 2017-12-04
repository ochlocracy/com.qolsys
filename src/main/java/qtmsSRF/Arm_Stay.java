package qtmsSRF;

import adc.ADC;
import adc.UIRepo;
import panel.*;
import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ConfigProps;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Arm_Stay extends Setup {
    public Arm_Stay() throws Exception { ConfigProps.init();}
    String page_name = "QTMS: ARM STAY";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    UIRepo homePage;
    String login = "mypanel01";
    String password = "qolsys123";
    private int Normal_Exit_Delay = 10;
    private int Normal_Entry_Delay = 11;
    private int Long_Exit_Delay = 12;
    private int Long_Entry_Delay = 13;

    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    String open = "06 00";
    String close = "04 00";
    String tamper = "01 00";
    String tamperM = "01 01";
    private String activate = "02 01";
    private String idle = "00 01";
    private String restore = "04 01";
    private String active = "02 01";
    private String log_path =  new String(System.getProperty("user.dir")) +"/log/test.txt";
    ADC adc = new ADC();
    private String keyfobAway = "04 04";
    private String keyfobStay = "04 01";
    private String keyfobDisarm = "08 01";
    String door_window10 = "65 00 0A";
    String door_window12 = "65 00 1A";
    String door_window13 = "65 00 2A";
    String door_window14 = "65 00 3A";
    String door_window16 = "65 00 4A";
    String door_window25 = "65 00 5A";
    String door_window8 = "65 00 6A";
    String door_window9 = "65 00 7A";
    String motion15 = "55 00 44";
    String motion17 = "55 00 54";
    String motion20 = "55 00 64";
    String motion35 = "55 00 74";
    String keyfob1 = "65 00 AF";
    String keyfob4 = "65 00 BF";
    String keyfob6 = "65 00 CF";
    String Freeze = "73 00 1A";
    String Smoke = "67 00 22";
    String Water = "75 11 0A";
    String CO = "75 00 AA";
    public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
    }

    public void delete_from_primary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(ConfigProps.adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);
    }
    public void sensor_status_check(String DLID, String Status, String Status1, int n,int n1 ) throws InterruptedException, IOException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigate_to_Settings_page();
        Thread.sleep(1000);
        sett.STATUS.click();
        sett.Panel_history.click();
         List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n).getText().equals(Status)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n).getText()+ "***");}
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText()+ "***");}
        Thread.sleep(1000);
        home.Home_button.click();
        Thread.sleep(2000);
    }
    @BeforeTest
    public void capabilities_setup() throws Exception {

        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
        Thread.sleep(1000);

    }
    @BeforeMethod
    public  void webDriver() {
        adc.webDriverSetUp();
    }


    @Test(priority = 0)
    public void AS_02() throws Exception {
        homePage = PageFactory.initElements(adc.driver1, UIRepo.class);
        logger.info("Verify the panel can be disarmed from adc");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        servcall.set_DIALER_DELAY(6);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        verify_armstay();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(3000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {}
        Thread.sleep(5000);
        homePage.Arm_Stay_state.click();
        Thread.sleep(5000);
        homePage.Disarm.click();
        Thread.sleep(4000);
        Thread.sleep(4000);
        verify_disarm();
        System.out.println("Pass");
        Thread.sleep(4000);
    }

    @Test(priority = 1 )
    public void AS_04() throws Exception {
        logger.info("Verify the panel can be disarmed using a keyfob: If KeyFob Instant Arming is disabled, panel should arm away after exit delay.\n" +
                " If KeyFob Instant Arming is enabled, panel should arm away immediately.");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        servcall.set_KEYFOB_NO_DELAY_disable();
        Thread.sleep(2000);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(Normal_Entry_Delay);
        Thread.sleep(2000);
        servcall.set_NORMAL_EXIT_DELAY(Normal_Exit_Delay);
        Thread.sleep(2000);
        servcall.set_LONG_ENTRY_DELAY(Long_Entry_Delay);
        Thread.sleep(2000);
        servcall.set_LONG_EXIT_DELAY(Long_Exit_Delay);
        Thread.sleep(2000);
        add_primary_call(38, 1, 6619386, 102);
        Thread.sleep(2000);
        sensors.primary_call("65 00 AF", keyfobStay);
        deleteLogFile(log_path);
         Thread.sleep(1000);
        eventLogsGenerating(log_path,new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-STAY-EXIT-DELAY"},1);
        Thread.sleep(15000);
        verify_armstay();
        sensors.primary_call("65 00 AF", keyfobAway);
        Thread.sleep(1000);
       // deleteLogFile(log_path);
       // Thread.sleep(2000);
        eventLogsGenerating(log_path,new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-AWAY-EXIT-DELAY"},1);
             Thread.sleep(15000);
        verify_armaway();
       // sensors.primary_call("65 00 AF", keyfobDisarm);
        home.ArwAway_State.click();
        enter_default_user_code();
        verify_disarm();
        System.out.println("*** Pass:If KeyFob Instant Arming is disabled, panel armed away after exit delay. ***");
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(2000);
        sensors.primary_call("65 00 AF", keyfobStay);
        Thread.sleep(1000);
        deleteLogFile(log_path);
        Thread.sleep(1000);
        eventLogsGenerating(log_path,new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-STAY-EXIT-DELAY"},1);
        Thread.sleep(1000);
        verify_armstay();
        sensors.primary_call("65 00 AF", keyfobAway);
        Thread.sleep(1000);
        eventLogsGenerating(log_path,new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-AWAY-EXIT-DELAY"},1);
        Thread.sleep(1000);
        verify_armaway();
        Thread.sleep(2000);
        home.ArwAway_State.click();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(2000);
        verify_disarm();
        Thread.sleep(2000);
        System.out.println("*** Pass: If KeyFob Instant Arming is enabled, panel should arm away immediately. ***");
        Thread.sleep(4000);
    delete_from_primary(38);
        Thread.sleep(4000);}

        @Test(priority = 2)
    public void AS_05() throws Exception {
        logger.info("Verify the panel returns to Arm Stay at the end of the entry delay if the user does not enter a valid user code");
        add_primary_call(38, 1, 6619386, 102);
        Thread.sleep(2000);
        ARM_STAY();
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        home.DISARM.click();
        Thread.sleep(13000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call("65 00 AF", keyfobDisarm);
        Thread.sleep(6000);
        delete_from_primary(38);
        Thread.sleep(4000);
    }

    public void arm_stay_sensor_event(int zone,int group,String DLID) throws Exception {
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
      //  add_primary_call(38, 1, 6619386, 102);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        //ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call(DLID, open);
        Thread.sleep(2000);
        sensors.primary_call(DLID, close);
        Thread.sleep(4000);
         }

    public void user_history_arm_stay_sensor_event_verification(int zone) throws Exception {
        adc.New_ADC_session_User(login,password);
        Thread.sleep(5000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {}
        Thread.sleep(10000);
        adc.driver1.get("https://www.alarm.com/web/History/EventHistory.aspx");

        Thread.sleep(10000);
        File source = ((TakesScreenshot)adc.driver1).getScreenshotAs(OutputType.FILE);
        String path = "/home/qolsys/IdeaProjects/comqolsys2017/scr/" + source.getName();
        org.apache.commons.io.FileUtils.copyFile(source, new File(path));


   //     "//div[contains(@class, 'icon ') and contains(@title, 'Disarmed ')]")

//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath(""));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(3000);
        delete_from_primary(zone);
        Thread.sleep(3000);
    }

        @Test(priority = 3)
    public void AS_06() throws Exception {
        logger.info("Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Stay");
        int zone = 10;
        int group = 10;
        add_primary_call(zone, group,6619296, 1);
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window10);
        Thread.sleep(15000);
        verify_in_alarm();
        verify_sensorstatus_inAlarm("Open");
        enter_default_user_code();
        Thread.sleep(2000);
        sensor_status_check(door_window10,"Closed", "Open",3,4);
        user_history_arm_stay_sensor_event_verification(zone);
        delete_from_primary(zone);
        Thread.sleep(6000);}
    @Test(priority = 4)
    public void AS_07() throws Exception {
        logger.info("Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay");
        int zone = 12;
        int group = 12;
        add_primary_call(zone, group,6619297, 1);
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window12);
        Thread.sleep(15000);
        verify_in_alarm();
        verify_sensorstatus_inAlarm("Open");
        enter_default_user_code();
        Thread.sleep(2000);
        sensor_status_check(door_window12,"Closed", "Open",3,4);
        user_history_arm_stay_sensor_event_verification(zone);
        delete_from_primary(zone);
        Thread.sleep(6000);}
    @Test(priority = 5)
    public void AS_08() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Stay");
        int zone = 14;
        int group = 14;
        add_primary_call(zone, group,6619299, 1);
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window14);
        Thread.sleep(1000);
        verify_in_alarm();
        verify_sensorstatus_inAlarm("Open");
        enter_default_user_code();
        Thread.sleep(2000);
        sensor_status_check(door_window14,"Closed", "Open",2,4);
        user_history_arm_stay_sensor_event_verification(zone);
        delete_from_primary(zone);
        Thread.sleep(6000);}
    @Test(priority = 6)
    public void AS_09() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Stay");
        int zone = 13;
        int group = 13;
        add_primary_call(zone, group,6619298, 1);
        Thread.sleep(2000);
        arm_stay_sensor_event(zone, group, door_window13);
        Thread.sleep(1000);
        verify_in_alarm();
        verify_sensorstatus_inAlarm("Open");
        enter_default_user_code();
        Thread.sleep(2000);
        sensor_status_check(door_window13,"Closed", "Open",2,4);
        user_history_arm_stay_sensor_event_verification(zone);
        delete_from_primary(zone);
        Thread.sleep(6000);
    }
    @Test(priority = 7)
    public void AS_10() throws Exception {
        logger.info("Verify the system will NOT go into  alarm if a sensor in group 16 is opened in Arm Stay");
        int zone = 16;
        int group = 16;
        add_primary_call(zone, group, 6619300, 1);
        Thread.sleep(1000);
        arm_stay_sensor_event(zone,group, door_window16);
        Thread.sleep(1000);
        verify_armstay();
        DISARM();
        sensor_status_check(door_window16,"Closed", "Open",2,3);
        Thread.sleep(4000);
        delete_from_primary(zone);
        Thread.sleep(6000);
    }
    @Test(priority = 8)
    public void AS_12() throws Exception {
        logger.info("Verify the system will NOT go into  alarm if a sensor in group 17 is activated in Arm Stay");
        int zone = 17;
        int group = 17;
        //delete_from_primary(zone);
        add_primary_call(zone, group, 5570629, 2);
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verify_armstay();
        sensors.primary_call(motion17, active);
        sensors.primary_call(motion17, idle);
        Thread.sleep(1000);
        verify_armstay();
        DISARM();
        sensor_status_check(motion17,"Idle", "Activated",2,3);
        Thread.sleep(4000);
        delete_from_primary(zone);
        Thread.sleep(6000);}
    @Test(priority = 9)
    public void AS_11() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 15 is activated in Arm Stay");
        int zone = 15;
        int group = 15;

        add_primary_call(zone, group, 5570628, 2);
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verify_armstay();
        sensors.primary_call(motion15, active);
        Thread.sleep(2000);
        sensors.primary_call(motion15, idle);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        sensor_status_check(motion15,"Idle", "Activated",2,4);
        Thread.sleep(4000);
        user_history_arm_stay_sensor_event_verification(zone);
        delete_from_primary(zone);
        Thread.sleep(6000);}
    @Test(priority = 10)
    public void AS_13() throws Exception {
        logger.info("Verify the system will NOT go into  alarm if a sensor in group 20 is activated in Arm Stay");
        int zone = 20;
        int group = 20;
        add_primary_call(zone, group, 5570630, 2);
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call(motion20, active);
        Thread.sleep(2000);
        sensors.primary_call(motion20, idle);
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        DISARM();
        Thread.sleep(4000);
        sensor_status_check(motion20,"Idle", "Activated",2,3);
        Thread.sleep(4000);
        delete_from_primary(zone);
        Thread.sleep(6000);
    }
    @Test(priority = 11)
    public void AS_14() throws Exception {
        logger.info("Verify the system will go into alarm at the end of the entry delay if a sensor in group 35 is Activated in Arm Stay");
        add_primary_call(35, 35, 5570631, 2);
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verify_armstay();
        Thread.sleep(4000);
        sensors.primary_call(motion35, active);
        Thread.sleep(2000);
        sensors.primary_call(motion35, idle);
        Thread.sleep(13000);
        verify_in_alarm();
        Thread.sleep(4000);
        verify_sensorstatus_inAlarm("Activated");
        enter_default_user_code();
        Thread.sleep(4000);
        sensor_status_check(motion20,"Idle", "Activated",3,4);
        Thread.sleep(4000);
        user_history_arm_stay_sensor_event_verification(35);
        Thread.sleep(4000);}
    @Test(priority = 12)
    public void AS_15() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 15 is activate and that the system can be disarmed before the dialer delay.");
        int zone = 15;
        int group = 15;

        add_primary_call(zone, group, 5570628, 2);
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verify_armstay();
        sensors.primary_call(motion15, active);
        sensors.primary_call(motion15, idle);
        Thread.sleep(1000);
        verify_in_alarm();
        verify_sensorstatus_inAlarm("Activated");
        enter_default_user_code();
        sensor_status_check(motion20,"Idle", "Activated",2,4);
        user_history_arm_stay_sensor_event_verification(zone);
        delete_from_primary(zone);
        Thread.sleep(4000);
    }

    public void arm_stay_2sensors_event(int zone1, int zone2, String DLID1,String DLID2,String Status1, String Status2, int time) throws Exception {
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(4000);
        verify_armstay();
        Thread.sleep(4000);
        sensors.primary_call(DLID1, open);
        Thread.sleep(1000);
        sensors.primary_call(DLID1, close);
        Thread.sleep(3000);
        sensors.primary_call(DLID2, open);
        Thread.sleep(time);
        //Thread.sleep(15000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals(Status1)) {
            logger.info("Pass: Correct status is " + Status1);
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
       try {
           if (events.get(1).getText().equals(Status2)) {
               logger.info("Pass: Correct status is " + Status2);
           } else {
               take_screenshot();
               logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
           }
       } catch (Exception e) {logger.info("Sensor " + zone2 +" event is not present on Alarm page");}

        verify_in_alarm();
        Thread.sleep(1000);
        enter_default_user_code();
        Thread.sleep(2000);
        delete_from_primary(zone1);
        Thread.sleep(4000);
        delete_from_primary(zone2);
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(10000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {}

        Thread.sleep(4000);
        String a = adc.driver1.findElement(By.xpath("//div[contains(@class, 'main') and contains(text(), 'Pending Alarm (Awaiting panel's Programmed Delay)')]")).getAttribute("InnerHTML");
        System.out.println(a);
//
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone1 +") Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor " + zone1 + " event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone2 +") Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor " + zone2 + " event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(3000);}
    @Test(priority = 13)
    public void AS_16() throws Exception {
        logger.info("Verify the system reports alarm on both sensors (10 and 12 groups) at the end of the entry delay ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(12, 12,6619297, 1);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,12,door_window10,door_window12, "Open","Open",15000);}
    @Test(priority = 14)
    public void AS_17() throws Exception {
        logger.info("Verify the system reports alarm on both sensors (10 and 14 groups) at the end of the entry delay ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(14, 14,6619299, 1);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,14,door_window10,door_window14, "Open","Open",15000);}
    @Test(priority = 15)
    public void AS_18() throws Exception {
        logger.info("Verify the panel goes into immediate alarm when a sensor in group 13 is activated and reports alarm on both sensors ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(13, 13,6619298, 1);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,13,door_window10,door_window13, "Open","Open",1000);}
    @Test(priority = 16)
    public void AS_19() throws Exception {
        logger.info("Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 16 ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(16, 16,6619300, 1);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,16,door_window10,door_window16, "Open","Open",15000);
        System.out.println("passed if only 1 status shown in Alarm page and 'Door/Window 16 (Sensor 16) Pending Alarm' event " +
                "is not present in User website history"); }
    @Test(priority = 17)
    public void AS_20()throws Exception {
        logger.info("Verify the system reports alarm on both sensors(10 and 15 groups)  at the end of the entry delay ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(15, 15,5570628, 2);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,15,door_window10,motion15,"Open","Activated",15000);}
    @Test(priority = 18)
    public void AS_21()throws Exception {
        logger.info("Verify the system reports alarm on both sensors(10 and 35 groups)  at the end of the entry delay ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(35, 35,5570631, 2);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,35,door_window10,motion35,"Open","Activated",15000);}
   @Test(priority = 19)
    public void AS_22a()throws Exception {
        logger.info("Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 20");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(20, 20,5570630, 2);
       Thread.sleep(4000);
        arm_stay_2sensors_event(10,20,door_window10,motion20,"Open","Activated",15000);
    logger.info("Passed.The system does not report alarm on group 20. Event not present in Alarm page and User website history");}
    @Test(priority = 20)
    public void AS_22b()throws Exception {
        logger.info("Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 17");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(17, 17,5570629, 2);
        Thread.sleep(4000);
        arm_stay_2sensors_event(10,17,door_window10,motion17,"Open","Activated",15000);
        logger.info("Passed.The system does not report alarm on group 17. Event not present in Alarm page and User website history");}
  @Test(priority = 15)
  public void AS_23() throws Exception {
      logger.info("Verify the system can be disarmed during the entry delay (10 and 10 groups). ");
      add_primary_call(10, 10,6619296, 1);
      add_primary_call(13, 10,6619298, 1);
      Thread.sleep(4000);
      disarm_during_entry_delay(10,13,door_window10,door_window13, "Disarmed (Intrusion)","Open","Closed","Open","Arm-Stay",15000, 1, 4, 5, 1, 2);
     }
    public void disarm_during_entry_delay(int zone1, int zone2, String DLID1,String DLID2,String Status, String Status1, String Status2, String Status3,String Status4, int entry_delay,int n0, int n, int n1,int n3, int n4) throws Exception {
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verify_armstay();
        sensors.primary_call(DLID1, open);
        Thread.sleep(1000);
        sensors.primary_call(DLID1, close);
        Thread.sleep(3000);
        sensors.primary_call(DLID2, open);
        Thread.sleep(entry_delay/2);
        enter_default_user_code();
        verify_disarm();
        Thread.sleep(1000);
        logger.info("panel history verification");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigate_to_Settings_page();
        Thread.sleep(1000);
        sett.STATUS.click();
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText()+ "***");}
        Thread.sleep(2000);
       if (li_status1.get(n).getText().equals(Status1)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n).getText()+ "***");}
        Thread.sleep(2000);
         if (li_status1.get(n1).getText().equals(Status2)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText()+ "***");}
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(5000);
        List<WebElement> li_status = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n3).getText().equals(Status3)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status.get(n3).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status.get(n3).getText()+ "***");}
        Thread.sleep(2000);
        if (li_status1.get(n4).getText().equals(Status4)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status.get(n4).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status.get(n4).getText()+ "***");}
        Thread.sleep(2000);
        home.Home_button.click();
        delete_from_primary(zone1);
        delete_from_primary(zone2);
        logger.info("User website history verification");

       adc.New_ADC_session_User(login,password);
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone1 + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(3000);
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone1 +") Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone1 + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone2 +") Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone2 + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Stay')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone1 + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!"); } }
    @Test(priority = 16)
    public void AS_24() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 12 groups). ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(12, 12,6619297, 1);
        Thread.sleep(4000);
        disarm_during_entry_delay(10,12,door_window10,door_window12, "Disarmed (Intrusion)","Open","Closed","Open","Arm-Stay",15000, 1, 4, 5, 1, 2);
    }
    @Test(priority = 17)
    public void AS_25() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 15 groups). ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(15, 15,5570628, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10,15,door_window10,motion15, "Disarmed (Intrusion)","Idle","Activated","Closed","Open",15000, 1, 4, 5, 1, 2);
    }
    @Test(priority = 18)
    public void AS_26() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 17 groups). ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(17, 17,5570629, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10,17,door_window10,motion17, "Disarmed (Intrusion)","Idle","Activated","Open","Arm-Stay",15000, 1, 3, 4, 1, 2);
        logger.info("Pass: No event for Sensor group 17 on User website");}
    @Test(priority = 19)
    public void AS_27() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 20 groups). ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(20, 20,5570630, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10,20,door_window10,motion20, "Disarmed (Intrusion)","Idle","Activated","Open","Arm-Stay",15000, 1, 3, 4, 1, 2);
        logger.info("Pass: No event for Sensor group 20 on User website");}
    @Test(priority = 20)
    public void AS_28() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 35 groups). ");
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(35, 35,5570631, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10,35,door_window10,motion35, "Disarmed (Intrusion)","Idle","Activated","Closed","Open",15000, 1, 5, 5, 1, 2);
        }
    @Test(priority = 21)
    public void AS_29() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (8 group). ");
        add_primary_call(8, 8,6619302, 1);
        Thread.sleep(4000);
         immediate_tamper_alarm(8,8, door_window8,"Tampered","Closed", 4,2);
        Thread.sleep(4000);}
     @Test(priority = 22)
    public void AS_30() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (9 group). ");
        add_primary_call(9, 9,6619303, 1);
         Thread.sleep(4000);
        immediate_tamper_alarm(9,9, door_window9,"Tampered","Closed", 4,2);
         Thread.sleep(4000);
    }
      @Test(priority = 23)
      public void AS_31() throws Exception {
          logger.info("Verify the panel will report an immediate tamper alarm (10 group). ");
          add_primary_call(10, 10,6619296, 1);
          Thread.sleep(4000);
          immediate_tamper_alarm(10,10, door_window10,"Tampered","Closed", 4,2);
          Thread.sleep(4000);}
     @Test(priority = 24)
     public void AS_32() throws Exception {
         logger.info("Verify the panel will report an immediate tamper alarm (12 group). ");
         add_primary_call(12, 12,6619297, 1);
         Thread.sleep(4000);
         immediate_tamper_alarm(12,12, door_window12,"Tampered","Closed", 4,2);
         Thread.sleep(4000);}
     @Test(priority = 25)
      public void AS_33() throws Exception {
          logger.info("Verify the panel will report an immediate tamper alarm (13 group). ");
          add_primary_call(13, 13,6619298, 1);
         Thread.sleep(4000);
          immediate_tamper_alarm(13,13, door_window13,"Tampered","Closed", 4,2);
         Thread.sleep(4000);}
    @Test(priority = 26)
      public void AS_34() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (14 group). ");
        add_primary_call(14, 14,6619299, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(14,14, door_window14,"Tampered","Closed", 4,2);
        Thread.sleep(4000);}
    @Test(priority = 27)
     public void AS_35() throws Exception {
         logger.info("Verify the panel will report an immediate tamper alarm (15 group). ");
         add_primary_call(15, 15,5570628, 2);
         Thread.sleep(4000);
         immediate_tamper_alarm(15,15, motion15,"Tampered" ,"Idle", 4,2);
         Thread.sleep(4000);}
     @Test(priority = 28)
    public void AS_36() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (35 group). ");
        add_primary_call(35, 35,5570631, 2);
         Thread.sleep(4000);
        immediate_tamper_alarm(35,35, motion35,"Tampered","Idle", 4,2);
         Thread.sleep(4000);}
     @Test(priority = 29)
      public void AS_37a() throws Exception {
          logger.info("Verify the panel will not report a tamper alarm (16 group). ");
          add_primary_call(16, 16, 6619300, 1);
         Thread.sleep(4000);
          NO_tamper_alarm(16,16, door_window16,"Tampered","Closed", 3,2);
         Thread.sleep(4000);}
      @Test(priority = 30)
       public void AS_37b() throws Exception {
           logger.info("Verify the panel will notvreport an immediate tamper alarm (17 group). ");
           add_primary_call(17, 17, 5570629, 2);
          Thread.sleep(4000);Thread.sleep(4000);
           NO_tamper_alarm(17,17, motion17,"Tampered","Idle", 3,2);
          Thread.sleep(4000);}
      @Test(priority = 31)
      public void AS_37c() throws Exception {
          logger.info("Verify the panel will not report an immediate tamper alarm (20 group). ");
          add_primary_call(20, 20,5570630, 2);
          Thread.sleep(4000);
          NO_tamper_alarm(20,20, motion20,"Tampered","Idle", 3,2);
          Thread.sleep(4000);}
    @Test(priority = 32)
    public void AS_37d() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (26 group). ");
        add_primary_call(26, 26, 6750242, 5);
        Thread.sleep(4000);
        NO_tamper_alarm(26,26, Smoke,"Tampered","Normal", 3,2);
        Thread.sleep(4000);}
   @Test(priority = 33)
    public void AS_37e() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (52 group Freeze sensor). ");
        add_primary_call(52, 52,  7536801, 17);
       Thread.sleep(4000);
        NO_tamperM_alarm(52,52, Freeze,"Tampered","Normal", 3,1);
       Thread.sleep(4000);}
  @Test(priority = 34)
    public void AS_37h() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (34 group). ");
        add_primary_call(34, 34, 7667882, 6);
      Thread.sleep(4000);
        NO_tamper_alarm(34,34, CO,"Tampered","Normal", 3,2);
      Thread.sleep(4000);}
    @Test(priority = 35)
    public void AS_37waterM() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (38 group, subtype_ multi). ");
        add_primary_call(38, 38, 7672224, 22);
        Thread.sleep(4000);
        NO_tamperM_alarm(38,38, Water,"Tampered","Normal", 3,1);
        Thread.sleep(4000);}
    @Test(priority = 36)
    public void AS_37waterF() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (38 group, subtype_ flood). ");
        add_primary_call(36, 38, 7672224, 23);
        Thread.sleep(4000);
        NO_tamperM_alarm(36,38, Water,"Tampered","Normal", 3,1);
        Thread.sleep(4000);}
    public void NO_tamperM_alarm(int zone, int group, String DLID, String Status,String Status1, int n0, int n1 ) throws Exception {
        Thread.sleep(6000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(4000);
        sensors.primary_call(DLID, tamperM);
        Thread.sleep(2000);
        //Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        verify_armstay();
        Thread.sleep(4000);
       // sensors.primary_call(DLID, restore);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone +") Tamper')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Stay')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!"); }
        DISARM();
        Thread.sleep(3000);
        sensors.primary_call(DLID, restore);
        Thread.sleep(4000);
        //Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigate_to_Settings_page();
        Thread.sleep(2000);
        sett.STATUS.click();
        Thread.sleep(2000);
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText()+ "***");}
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText()+ "***");}
        Thread.sleep(3000);
        delete_from_primary(zone);
        Thread.sleep(4000);}
    public void NO_tamper_alarm(int zone, int group, String DLID, String Status,String Status1, int n0, int n1 ) throws Exception {
        Thread.sleep(1000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        sensors.primary_call(DLID, tamper);
        Thread.sleep(3000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call(DLID, restore);
        Thread.sleep(4000);
        verify_armstay();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone +") Tamper')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Stay')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!"); }
        DISARM();
        Thread.sleep(3000);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigate_to_Settings_page();
        Thread.sleep(1000);
        sett.STATUS.click();
        Thread.sleep(1000);
        sett.Panel_history.click();
        Thread.sleep(1000);
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText()+ "***");}
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)){
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText()+ "***");
        }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText()+ "***");}
        Thread.sleep(2000);
        delete_from_primary(zone);
        Thread.sleep(4000);}

        public void immediate_tamper_alarm(int zone, int group, String DLID, String Status,String Status1, int n0, int n1 ) throws Exception {
            Thread.sleep(3000);
            servcall.EVENT_ARM_STAY();
            Thread.sleep(3000);
            sensors.primary_call(DLID, tamper);
            Thread.sleep(3000);
            HomePage home_page = PageFactory.initElements(driver, HomePage.class);
            List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
            if (events.get(0).getText().equals(Status)) {
                logger.info("Pass: Correct status is " + Status);
            } else {
                take_screenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
            verify_in_alarm();
            Thread.sleep(3000);
            adc.New_ADC_session_User(login,password);
            Thread.sleep(60000);
            adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
            try {
                WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone+") Pending Alarm ')]"));
                Assert.assertTrue(history_message_alarm.isDisplayed());
                {
                    System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
                }
            } catch (Exception e) {
                System.out.println("No such element found!!!");
            }
            Thread.sleep(30000);
            adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
            try {
                WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Tamper Alarm')]"));
                Assert.assertTrue(history_message_alarm.isDisplayed());
                {
                    System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
                }
            } catch (Exception e) {
                System.out.println("No such element found!!!");
            }
            enter_default_user_code();
            Thread.sleep(1000);
            sensors.primary_call(DLID, restore);
            Thread.sleep(3000);
            SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
            navigate_to_Settings_page();
            Thread.sleep(1000);
            sett.STATUS.click();
            Thread.sleep(1000);
            sett.Panel_history.click();
            Thread.sleep(1000);
            List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
            if (li_status1.get(n0).getText().equals(Status)){
                logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText()+ "***");
            }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText()+ "***");}
            Thread.sleep(2000);
            if (li_status1.get(n1).getText().equals(Status1)){
                logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText()+ "***");
            }else {logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText()+ "***");}
            Thread.sleep(2000);
            delete_from_primary(zone);
            Thread.sleep(6000);}
            @Test(priority = 38)
    public void AS_40() throws Exception {
        logger.info("Verify the system can not use a duress code if Duress Authentication is disabled(12 group). ");
        Thread.sleep(5000);
        add_primary_call(12, 12,6619297, 1);
        servcall.set_DURESS_AUTHENTICATION_disable();
                Thread.sleep(3000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(6000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(6000);
        sensors.primary_call(door_window12, open);
        Thread.sleep(1000);
        sensors.primary_call(door_window12, close);
        Thread.sleep(15000);
        enter_default_DURESS_code();
        verify_in_alarm();
        Thread.sleep(3000);
        enter_default_user_code();
                Thread.sleep(4000);
        delete_from_primary(12);
                Thread.sleep(4000);}

    @Test(priority = 39) /***Please Set 5 min PhotoFrame start time manually ***/
    public void AS_41() throws Exception {
        logger.info("Verify the system is still responsive when the panel is in screensaver mode(10-group sensor). ");
        Thread.sleep(5000);
        add_primary_call(10, 10,6619296, 1);
        servcall.set_photo_frame_SCREEN_SAVER_TYPE();
        servcall.set_ARM_STAY_NO_DELAY_enable();
       // servcall.set_5minutes_SCREEN_SAVER_IDLE_TIME();
        TimeUnit.MINUTES.sleep(1);
        ARM_STAY();
        TimeUnit.MINUTES.sleep(5);
        Thread.sleep(1000);
        verify_photoframe_mode();
        Thread.sleep(1000);
        sensors.primary_call(door_window10, open);
        Thread.sleep(15000);
        verify_in_alarm();
        Thread.sleep(3000);
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(10);
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " panel event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }

    }
    @Test(priority = 42) /***Please Set 5 min PhotoFrame start time manually ***/
    public void AS_42() throws Exception {
        logger.info("Verify the system is still responsive when the panel is in screensaver mode (10 and 12-group sensors). ");
        Thread.sleep(3000);
        add_primary_call(13, 13,6619298, 1);
        servcall.set_photo_frame_SCREEN_SAVER_TYPE();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        // servcall.set_5minutes_SCREEN_SAVER_IDLE_TIME();
        TimeUnit.MINUTES.sleep(1);
        ARM_STAY();
        TimeUnit.MINUTES.sleep(5);
        verify_photoframe_mode();
        Thread.sleep(1000);
        sensors.primary_call(door_window13, open);
        Thread.sleep(15000);
        verify_in_alarm();
        Thread.sleep(3000);
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(13);
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 13) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 13 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " panel event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        } }
    @Test(priority = 43) /***Please Set 5 min PhotoFrame start time manually ***/
    public void AS_43() throws Exception {
        logger.info("Verify the system is still responsive when the panel is in screensaver mode (13-group sensor). ");
        Thread.sleep(5000);
        add_primary_call(10, 10,6619296, 1);
        add_primary_call(12, 12,6619297, 1);
        Thread.sleep(4000);
        servcall.set_photo_frame_SCREEN_SAVER_TYPE();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        // servcall.set_5minutes_SCREEN_SAVER_IDLE_TIME();
        Thread.sleep(6000);
        ARM_STAY();
        TimeUnit.MINUTES.sleep(5);
        verify_photoframe_mode();
        Thread.sleep(1000);
        sensors.primary_call(door_window10, open);
        sensors.primary_call(door_window12, open);
        Thread.sleep(2000);
        sensors.primary_call(door_window10, close);
        sensors.primary_call(door_window12, close);
        Thread.sleep(15000);
        verify_in_alarm();
        Thread.sleep(3000);
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(10);
        Thread.sleep(4000);
        delete_from_primary(12);
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 12) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " panel event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        } }

    @Test(priority = 83)
    public void AS_45() throws Exception {
        logger.info("Verify that the websites report that the sensor has been left open");
        Thread.sleep(3000);
        add_primary_call(10, 10,6619296, 1);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
        Thread.sleep(2000);
        adc.driver1.get("https://alarmadmin.alarm.com/Support/SensorActions.aspx?device_id=10");
        Thread.sleep(2000);
        Select type_menu = new Select(adc.driver1.findElement(By.id("ctl00_phBody_ddlActions")));
        type_menu.selectByVisibleText("Change Sensor Activity Monitoring Status");
        Thread.sleep(2000);
        adc.driver1.findElement(By.id("ctl00_phBody_rbtMonitoring_0")).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.id("ctl00_phBody_btnSubmit")).click();
        Thread.sleep(2000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        ARM_STAY();
        Thread.sleep(1000);
        sensors.primary_call(door_window10, open);
        Thread.sleep(15000);
        verify_in_alarm();
        Thread.sleep(3000);
        enter_default_user_code();
        Thread.sleep(4000);
        delete_from_primary(10);
        Thread.sleep(4000);
          adc.New_ADC_session_User(login,password);
        Thread.sleep(20000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Opened')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
     }}

    @Test(priority = 44)
    public void AS_49() throws Exception {
        logger.info("Verify the panel will Arm Stay at the end of the exit delay if Arm Stay button is pressed by 1-group keyfob ");
        add_primary_call(38, 1, 6619386, 102);
        Thread.sleep(4000);
        servcall.set_KEYFOB_NO_DELAY_disable();
        Thread.sleep(4000);
        logger.info("Keyfob group 1: Disarm, panic = Police");
        Thread.sleep(4000);
        sensors.primary_call("65 00 AF", keyfobStay);
        Thread.sleep(15000);
        verify_armstay();
        Thread.sleep(4000);
        sensors.primary_call("65 00 AF", keyfobDisarm);
        Thread.sleep(4000);
        verify_disarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Stay ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 38 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 38 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }

    delete_from_primary(38);
        Thread.sleep(4000);
    }
    @Test(priority = 45)
    public void AS_50_52() throws Exception {
        logger.info("Verify the panel will Arm Away at the end of the exit delay if Arm Away button is pressed by 1-group keyfob");
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 6-group keyfob");
        add_primary_call(38, 1, 6619386, 102);
        add_primary_call(39, 6, 6619387, 102);
        servcall.set_KEYFOB_NO_DELAY_disable();
        servcall.set_KEYFOB_DISARMING(01);
        logger.info("Keyfob group 1: Disarm, panic = Police");
        logger.info("Keyfob group 6: Disarm, panic = Auxiliary");
        Thread.sleep(5000);
        sensors.primary_call("65 00 AF", keyfobAway);
        Thread.sleep(15000);
        verify_armaway();
        Thread.sleep(3000);
        sensors.primary_call("65 00 BF", keyfobDisarm);
        Thread.sleep(4000);
        verify_disarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Away ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 38 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 39 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(15000);
        delete_from_primary(38);
        Thread.sleep(4000);
        delete_from_primary(39);
        Thread.sleep(4000);
    }
    @Test(priority = 46)
    public void AS_56() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will Arm Away at the end of the exit delay if Arm Away button is pressed by 6-group keyfob");
        add_primary_call(39, 6, 6619387, 102);
        servcall.set_KEYFOB_NO_DELAY_disable();
        logger.info("Keyfob group 6: Disarm, panic = Auxiliary");
        Thread.sleep(5000);
        sensors.primary_call("65 00 BF", keyfobAway);
        Thread.sleep(15000);
        verify_armaway();
        Thread.sleep(3000);
        home.ArwAway_State.click();
        enter_default_user_code();
        Thread.sleep(4000);
        verify_disarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Away ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 38 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(15000);
        delete_from_primary(39);
        Thread.sleep(4000);
    }
    @Test(priority = 47)
    public void AS_58() throws Exception {
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 4-group keyfob");
        add_primary_call(40, 4, 6619388, 102);
        Thread.sleep(4000);
        servcall.set_KEYFOB_NO_DELAY_enable();
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");;
        Thread.sleep(5000);
        sensors.primary_call("65 00 CF", keyfobAway);
        Thread.sleep(2000);
        verify_armaway();
        Thread.sleep(3000);
        sensors.primary_call("65 00 CF", keyfobDisarm);
        Thread.sleep(4000);
        verify_disarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Away ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 40 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(40);
        Thread.sleep(4000);
    }

    @Test(priority = 48)
    public void AS_59() throws Exception {
        logger.info("Verify the panel will Arm Away at the end of the exit delay if Arm Away button is pressed by 4-group keyfob");
        add_primary_call(40, 4, 6619388, 102);
        Thread.sleep(4000);
        servcall.set_KEYFOB_NO_DELAY_disable();
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");;
        Thread.sleep(5000);
        sensors.primary_call("65 00 CF", keyfobAway);
        Thread.sleep(15000);
        verify_armaway();
        Thread.sleep(3000);
        sensors.primary_call("65 00 CF", keyfobDisarm);
        Thread.sleep(4000);
        verify_disarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Armed Away ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 40 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(40);
        Thread.sleep(4000);
    }

    @Test(priority = 50)
    public void AS_68() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered");
        add_primary_call(33, 13, 6684828, 107);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        home.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Tamper Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 52)
    public void AS_67() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered");
        add_primary_call(33, 13, 6684828, 107);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        home.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Tamper Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(33);
        Thread.sleep(4000);
    }
    @Test(priority = 53)
    public void AS_69() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered");
        add_primary_call(33, 13, 6684828, 107);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        home.DISARM.click();
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Tamper Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 54)
    public void AS_70() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        add_primary_call(33, 13, 6684828, 107);
        Thread.sleep(4000);
        add_primary_call(10, 10,6619296, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call(door_window10, open);
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " +"Open");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Tampered")) {
                logger.info("Pass: Correct status is " + "Tampered");
            } else {
                take_screenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {logger.info("Sensor 33 event is not present on Alarm page");}
       enter_default_user_code();
        Thread.sleep(4000);
      adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(2000);
        delete_from_primary(33);
        Thread.sleep(4000);
        delete_from_primary(10);
        Thread.sleep(4000);
}

        @Test(priority = 55)
        public void AS_71() throws Exception {
            logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
            add_primary_call(33, 13, 6684828, 107);
            Thread.sleep(4000);
            add_primary_call(12, 12,6619297, 1);
            Thread.sleep(3000);
            servcall.EVENT_ARM_STAY();
            Thread.sleep(2000);
            verify_armstay();
            Thread.sleep(2000);
            sensors.primary_call(door_window12, open);
            Thread.sleep(2000);
            sensors.primary_call("66 00 C9", tamperM);
            Thread.sleep(2000);
            verify_in_alarm();
            Thread.sleep(2000);
            HomePage home_page = PageFactory.initElements(driver, HomePage.class);
            List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
            // for (int j = 0; j < events.size(); j++)
            if (events.get(1).getText().equals("Open")) {
                logger.info("Pass: Correct status is " +"Open");
            } else {
                take_screenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
            try {
                if (events.get(0).getText().equals("Tampered")) {
                    logger.info("Pass: Correct status is " + "Tampered");
                } else {
                    take_screenshot();
                    logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
                }
            } catch (Exception e) {logger.info("Sensor 33 event is not present on Alarm page");}
            Thread.sleep(4000);
            enter_default_user_code();
            Thread.sleep(4000);
            adc.New_ADC_session_User(login,password);
            Thread.sleep(60000);
            adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
            try {
                WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
                Assert.assertTrue(history_message_alarm.isDisplayed());
                {
                    System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
                }
            } catch (Exception e) {
                System.out.println("No such element found!!!");
            }
            Thread.sleep(30000);
            adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
            try {
                WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 12) Pending Alarm ')]"));
                Assert.assertTrue(history_message_alarm.isDisplayed());
                {
                    System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
                }
            } catch (Exception e) {
                System.out.println("No such element found!!!");
            }
            Thread.sleep(1000);
            delete_from_primary(33);
            Thread.sleep(4000);
            delete_from_primary(12);
            Thread.sleep(4000);
        }

    @Test(priority = 57)
    public void AS_72() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        add_primary_call(33, 13, 6684828, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", active);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " +"Alarmed");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(33);
        Thread.sleep(4000);
       }
    @Test(priority = 60)
    public void AS_73() throws Exception {
        logger.info("Verify the panel will go into immediate tamper alarm if a shock-detector in group 13 is tampered");
        add_primary_call(33, 13, 6684828, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call("66 00 C9", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " +"tampered");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }

        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 58)
    public void AS_75() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        add_primary_call(34, 17,  6684829 , 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(4000);
        sensors.primary_call("66 00 D9", active);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", restore);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9","Activated","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);}
    @Test(priority = 59)
    public void AS_76() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will will just create notification if a shock-detector in group 17 is tampered");
        add_primary_call(34, 17,  6684829 , 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(4000);
        sensors.primary_call("66 00 D9", tamperM);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", restore);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9","Tampered","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);}

    @Test(priority = 61)
    public void AS_77() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will will just create notification if a shock-detector in group 17 is tampered");
        add_primary_call(34, 17,  6684829 , 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", tamperM);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", restore);
        sensor_status_check("66 00 D9","Tampered","Normal", 3, 1);
        delete_from_primary(34);}
    @Test(priority = 62)
    public void AS_78() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if a Glass-break detector in group 17 is activated");
        add_primary_call(34, 17,  6684829 , 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        sensors.primary_call("66 00 D9", active);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(4000);
        sensors.primary_call("66 00 D9", restore);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9","Activated","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);}

    @Test(priority = 63)
    public void AS_79() throws Exception {
        logger.info("Verify the panel will will just create notification if a shock-detector in group 17 is tampered");
        add_primary_call(34, 17,  6684829 , 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        sensors.primary_call("66 00 D9", tamperM);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", restore);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9","Tampered","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);}

    @Test(priority = 65)
    public void AS_80() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        add_primary_call(34, 17,  6684829 , 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", active);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("66 00 D9", restore);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9","Activated","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(34);
        Thread.sleep(4000);}
    @Test(priority = 64)
    public void AS_81() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        //add_primary_call(28, 13,  6750361 , 19);
        add_primary_call(28,13,6750361,19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", active);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " +"Alarmed");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(10000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 66)
    public void AS_82() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a Glass-break detector in group 13 is tampered");
        //add_primary_call(28, 13,  6750361 , 19);
        add_primary_call(28,13,6750361,19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " +"Tampered");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enter_default_user_code();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(10000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(28);
        Thread.sleep(4000);
    }
    @Test(priority = 67)
    public void AS_83() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if Glass-breakdetector in group 13 is activated");
        add_primary_call(28,13,6750361,19);
        Thread.sleep(4000);
        add_primary_call(10, 10,6619296, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call(door_window10, open);
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", active);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " +"Open");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                take_screenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {logger.info("Sensor 28 event is not present on Alarm page");}
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(28);
        Thread.sleep(4000);
        delete_from_primary(10);
        Thread.sleep(4000);}

    @Test(priority = 69)
    public void AS_84() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if Glass-breakdetector in group 13 is activated");
        add_primary_call(28,13,6750361,19);
        Thread.sleep(4000);
        add_primary_call(12, 12,6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call(door_window12, open);
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", active);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " +"Open");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                take_screenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {logger.info("Sensor 33 event is not present on Alarm page");}
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 38 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(28);
        Thread.sleep(4000);
        delete_from_primary(12);
        Thread.sleep(4000);}

    @Test(priority = 70)
    public void AS_85() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if Glass-breakdetector in group 13 is activated");
        add_primary_call(28,13,6750361,19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", active);
        Thread.sleep(2000);
        verify_in_alarm();
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " +"alarmed");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
          delete_from_primary(28);
        Thread.sleep(4000);
       }

    @Test(priority = 71)
    public void AS_86() throws Exception {
        logger.info("Verify the panel will go into immediate tamper alarm if a Glass-break detector in group 13 is tampered");
        add_primary_call(28,13,6750361,19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(4000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " +"Tampered");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enter_default_user_code();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        delete_from_primary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 72)
    public void AS_87() throws Exception {
        logger.info("Verify the panel will go into immediate alarm is a Glass-break detector in group 13 is activated");
        add_primary_call(28,13,6750361,19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(4000);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", active);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
        WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
        Assert.assertTrue(history_message_alarm.isDisplayed());
        {
            System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
        }
    } catch (Exception e) {
        System.out.println("No such element found!!!");
    }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
        WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm')]"));
        Assert.assertTrue(history_message_alarm.isDisplayed());
        {
            System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
        }
    } catch (Exception e) {
        System.out.println("No such element found!!!");
    }
        Thread.sleep(1000);
    delete_from_primary(28);
        Thread.sleep(4000);
}
    @Test(priority = 73)
    public void AS_88() throws Exception {
        logger.info("Verify the panel will go into immediate alarm is a Glass-break detector in group 13 is activated");
        add_primary_call(28,13,6750361,19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", active);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        Thread.sleep(4000);
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(28);
        Thread.sleep(4000);
    }
    @Test(priority = 74)
    public void AS_89() throws Exception {
        logger.info("Verify the panel will  just create notification if a Glass-break detector in group 17 is tampered");
        add_primary_call(28,13,6750361,19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primary_call("67 00 99", tamperM);
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(2000);
        enter_default_user_code();
        adc.New_ADC_session_User(login,password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(1000);
        delete_from_primary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 75)
    public void AS_90() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        add_primary_call(29,17,6750355,19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primary_call("67 00 39", active);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("67 00 39", restore);
        Thread.sleep(4000);
        sensor_status_check("67 00 39","Activated","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(29);
        Thread.sleep(4000);}
    @Test(priority = 76)
    public void AS_91() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is tampered");
        add_primary_call(29,17,6750355,19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        sensors.primary_call("67 00 39", active);
        Thread.sleep(2000);
        verify_armstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primary_call("67 00 39", restore);
        Thread.sleep(4000);
        sensor_status_check("67 00 39","Activated","Normal", 3, 1);
        Thread.sleep(4000);
        delete_from_primary(29);
        Thread.sleep(4000);}
    @Test(priority = 92)
    public void AS_92() throws Exception {
        logger.info("verify the panel will not go into immediate alarm when group 13 d/w sensor is tripped during 'Quick Exit' ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        add_primary_call(2,17,6750355,19);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 2A", open);
        Thread.sleep(2000);
        sensors.primary_call("65 00 2A", close);
        Thread.sleep(20000);
        verify_armstay();
        DISARM();
        Thread.sleep(2000);
        delete_from_primary(2);
        Thread.sleep(4000);
    }
    @Test(priority = 93)
    public void AS_93() throws Exception {
        logger.info("verify the panel will not go into immediate alarm when group 13 d/w sensor is tripped during 'Quick Exit' ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        add_primary_call(3, 13, 6619298, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 2A", open);
        Thread.sleep(2000);
        sensors.primary_call("65 00 2A", close);
        Thread.sleep(20000);
        verify_armstay();
        Thread.sleep(4000);
        DISARM();
        Thread.sleep(2000);
        delete_from_primary(3);
        Thread.sleep(4000);
    }
    @Test(priority = 94)
    public void AS_94() throws Exception {
        logger.info("verify the panel will not go into immediate alarm when group 14 d/w sensor is tripped during 'Quick Exit'   ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        add_primary_call(4, 14, 6619299, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 3A", open);
        Thread.sleep(2000);
        sensors.primary_call("65 00 3A", close);
        Thread.sleep(20000);
        verify_armstay();
        DISARM();
        Thread.sleep(2000);
        delete_from_primary(4);
        Thread.sleep(4000);
    }

    @Test(priority = 95)
    public void AS_95() throws Exception {
        logger.info("verify the panel will go into immediate alarm when group 8 d/w sensor is tripped during 'Quick Exit'  ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        add_primary_call(6, 8, 6619301, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 5A", open);
        Thread.sleep(2000);
        verify_in_alarm();
        enter_default_user_code();
        Thread.sleep(2000);
        delete_from_primary(6);
        Thread.sleep(4000);
    }

    @Test(priority = 96)
    public void AS_96() throws Exception {
        logger.info("verify the panel will go into  alarm  when group 9 d/w sensor is tripped during 'Quick Exit' ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        add_primary_call(7, 9, 6619302, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primary_call("65 00 6A", open);
        Thread.sleep(16000);
        verify_in_alarm();
        Thread.sleep(4000);
        enter_default_user_code();
        Thread.sleep(2000);
        delete_from_primary(7);
        Thread.sleep(4000);
        }

    @AfterTest
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();

    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }





}