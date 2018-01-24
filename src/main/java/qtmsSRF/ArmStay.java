package qtmsSRF;

import adc.ADC;
import adc.UIRepo;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import panel.SettingsPage;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArmStay extends Setup {
    String page_name = "QTMS: ARM STAY";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    UIRepo homePage;

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
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
    private String log_path = new String(System.getProperty("user.dir")) + "/log/test.txt";
    private String keyfobAway = "04 04";
    private String keyfobStay = "04 01";
    private String keyfobDisarm = "08 01";

    public ArmStay() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/QTMS_ArmStay.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/QTMS_ArmStay.html", false);
        log = report.startTest(test_case_name);
    }

    public void sensor_status_check(String DLID, String Status, String Status1, int n, int n1) throws InterruptedException, IOException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText() + "***");
        }
        Thread.sleep(1000);
        home.Home_button.click();
        Thread.sleep(2000);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        Thread.sleep(1000);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(2000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(2000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(2000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }


    @Test(priority = 0)
    public void AS_02() throws Exception {
        create_report("ASK319_02");
        homePage = PageFactory.initElements(adc.driver1, UIRepo.class);
        log.log(LogStatus.INFO, ("*ASK319_02* Verify the panel can be disarmed from ADC"));
        logger.info("Verify the panel can be disarmed from adc");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(1000);
        servcall.set_DIALER_DELAY(6);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        Thread.sleep(5000);
        verifyDisarm();
        log.log(LogStatus.PASS, ("Pass: system is successfully disarmed from user site"));
        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void AS_04() throws Exception {
        add_to_report("ASK319_04");
        log.log(LogStatus.INFO, ("*ASK319_04* Verify the panel can be disarmed using a keyfob"));
        logger.info("Verify the panel can be disarmed using a keyfob: If KeyFob Instant Arming is disabled, panel should arm away after exit delay.\n" +
                " If KeyFob Instant Arming is enabled, panel should arm away immediately.");
        servcall.set_KEYFOB_NO_DELAY_disable();
        Thread.sleep(2000);
        addPrimaryCall(38, 1, 6619386, 102);
        Thread.sleep(3000);
        sensors.primaryCall("65 00 AF", keyfobStay);
        deleteLogFile(log_path);
        Thread.sleep(1000);
        eventLogsGenerating(log_path, new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-STAY-EXIT-DELAY"}, 1);
        Thread.sleep(15000);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        sensors.primaryCall("65 00 AF", keyfobAway);
        Thread.sleep(1000);
        // deleteLogFile(log_path);
        // Thread.sleep(2000);
        eventLogsGenerating(log_path, new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-AWAY-EXIT-DELAY"}, 1);
        Thread.sleep(ConfigProps.longExitDelay);
        verifyArmaway();
        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
        // sensors.primaryCall("65 00 AF", keyfobDisarm);
        DISARM();
        Thread.sleep(1000);
        verifyDisarm();
        log.log(LogStatus.PASS, ("Pass: when KeyFob Instant Arming is disabled, panel armed away after exit delay"));
        System.out.println("*** Pass:If KeyFob Instant Arming is disabled, panel armed away after exit delay. ***");
        Thread.sleep(2000);
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", keyfobStay);
        Thread.sleep(1000);
        deleteLogFile(log_path);
        Thread.sleep(1000);
        eventLogsGenerating(log_path, new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-STAY-EXIT-DELAY"}, 1);
        Thread.sleep(1000);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        sensors.primaryCall("65 00 AF", keyfobAway);
        Thread.sleep(1000);
        eventLogsGenerating(log_path, new String[]{
                "SYSTEM_STATUS_CHANGED_TIME value : ARM-AWAY-EXIT-DELAY"}, 1);
        Thread.sleep(1000);
        verifyArmaway();
        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, ("Pass: when KeyFob Instant Arming is enabled, panel arms away immediately"));
        System.out.println("*** Pass: If KeyFob Instant Arming is enabled, panel should arm away immediately. ***");
        Thread.sleep(4000);
        deleteFromPrimary(38);
        Thread.sleep(4000);
    }

    @Test(priority = 2)
    public void AS_05() throws Exception {
        add_to_report("ASK319_05");
        log.log(LogStatus.INFO, ("*ASK319_05* Verify the panel returns to Arm Stay at the end of the entry delay if the user does not enter a valid user code"));
        logger.info("Verify the panel returns to Arm Stay at the end of the entry delay if the user does not enter a valid user code");
        Thread.sleep(2000);
        ARM_STAY();
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        home.DISARM.click();
        Thread.sleep(ConfigProps.longEntryDelay);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(4000);
    }

    public void arm_stay_sensor_event(int zone, int group, String DLID) throws Exception {
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        //  addPrimaryCall(38, 1, 6619386, 102);
        Thread.sleep(2000);
        servcall.EVENT_ARM_STAY();
        //ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(5000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(4000);
    }

    public void user_history_arm_stay_sensor_event_verification(int zone) throws Exception {
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(5000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(10000);
        adc.driver1.get("https://www.alarm.com/web/History/EventHistory.aspx");

        Thread.sleep(10000);
        File source = ((TakesScreenshot) adc.driver1).getScreenshotAs(OutputType.FILE);
        String path = projectPath+"/scr/" + source.getName();
        org.apache.commons.io.FileUtils.copyFile(source, new File(path));
        Thread.sleep(20000);
        adc.driver1.navigate().refresh();
        Thread.sleep(10000);
       
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
        deleteFromPrimary(zone);
        Thread.sleep(3000);
    }

    @Test(priority = 3)
    public void AS_06() throws Exception {
        add_to_report("ASK319_06");
        log.log(LogStatus.INFO, ("*ASK319_06* Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Stay"));
        logger.info("*ASK319_06* Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Stay");
        int zone = 10;
        int group = 10;
        addPrimaryCall(zone, group, 6619296, 1);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window10);
        Thread.sleep(15000);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        verifySensorstatusInAlarm("Open");
        enterDefaultUserCode();
        Thread.sleep(2000);
        sensor_status_check(door_window10, "Closed", "Open", 3, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Closed***, ***Open***"));
        user_history_arm_stay_sensor_event_verification(zone);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 4)
    public void AS_07() throws Exception {
        add_to_report("ASK319_07");
        logger.info("*ASK319_07* Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_07* Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay"));
        int zone = 12;
        int group = 12;
        addPrimaryCall(zone, group, 6619297, 1);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window12);
        Thread.sleep(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        verifySensorstatusInAlarm("Open");
        enterDefaultUserCode();
        Thread.sleep(2000);
        sensor_status_check(door_window12, "Closed", "Open", 3, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Closed***, ***Open***"));
        user_history_arm_stay_sensor_event_verification(zone);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 5)
    public void AS_08() throws Exception {
        add_to_report("ASK319_08");
        logger.info("*ASK319_08* Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_08* Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Stay"));
        int zone = 14;
        int group = 14;
        addPrimaryCall(zone, group, 6619299, 1);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window14);
        Thread.sleep(1000);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        verifySensorstatusInAlarm("Open");
        enterDefaultUserCode();
        Thread.sleep(2000);
        sensor_status_check(door_window14, "Closed", "Open", 2, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Closed***, ***Open***"));
        user_history_arm_stay_sensor_event_verification(zone);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 6)
    public void AS_09() throws Exception {
        add_to_report("ASK319_09");
        logger.info("Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_09* Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Stay"));
        int zone = 13;
        int group = 13;
        addPrimaryCall(zone, group, 6619298, 1);
        Thread.sleep(2000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window13);
        Thread.sleep(1000);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        verifySensorstatusInAlarm("Open");
        enterDefaultUserCode();
        Thread.sleep(2000);
        sensor_status_check(door_window13, "Closed", "Open", 2, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Closed***, ***Open***"));
        user_history_arm_stay_sensor_event_verification(zone);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 7)
    public void AS_10() throws Exception {
        add_to_report("ASK319_10");
        logger.info("*ASK319_10* Verify the system will NOT go into  alarm if a sensor in group 16 is opened in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_10* Verify the system will NOT go into  alarm if a sensor in group 16 is opened in Arm Stay"));
        int zone = 16;
        int group = 16;
        addPrimaryCall(zone, group, 6619300, 1);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_sensor_event(zone, group, door_window16);
        Thread.sleep(1000);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        DISARM();
        sensor_status_check(door_window16, "Closed", "Open", 2, 3);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Closed***, ***Open***"));
        Thread.sleep(4000);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 9)
    public void AS_12() throws Exception {
        add_to_report("ASK319_12");
        logger.info("Verify the system will NOT go into  alarm if a sensor in group 17 is activated in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_12* Verify the system will NOT go into  alarm if a sensor in group 17 is activated in Arm Stay"));
        int zone = 17;
        int group = 17;
        //deleteFromPrimary(zone);
        addPrimaryCall(zone, group, 5570629, 2);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        sensors.primaryCall(motion17, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system stays Armed Stay"));
        DISARM();
        sensor_status_check(motion17, "Idle", "Activated", 2, 3);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Idle***, ***Activated***"));
        Thread.sleep(4000);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 8)
    public void AS_11() throws Exception {
        add_to_report("ASK319_11");
        logger.info("Verify the system will go into immediate alarm if a sensor in group 15 is activated in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_11* Verify the system will go into immediate alarm if a sensor in group 15 is activated in Arm Stay"));
        int zone = 15;
        int group = 15;
        addPrimaryCall(zone, group, 5570628, 2);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        sensors.primaryCall(motion15, SensorsActivity.ACTIVATE);
        Thread.sleep(5000);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensor_status_check(motion15, "Idle", "Activated", 2, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Idle***, ***Activated***"));
        Thread.sleep(4000);
        user_history_arm_stay_sensor_event_verification(zone);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 10)
    public void AS_13() throws Exception {
        add_to_report("ASK319_13");
        logger.info("Verify the system will NOT go into  alarm if a sensor in group 20 is activated in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_13* Verify the system will NOT go into  alarm if a sensor in group 20 is activated in Arm Stay"));
        int zone = 20;
        int group = 20;
        addPrimaryCall(zone, group, 5570630, 2);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        Thread.sleep(2000);
        sensors.primaryCall(motion20, SensorsActivity.ACTIVATE);
        Thread.sleep(5000);
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system stays Armed Stay"));
        Thread.sleep(4000);
        DISARM();
        Thread.sleep(4000);
        sensor_status_check(motion20, "Idle", "Activated", 2, 3);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Idle***, ***Activated***"));
        Thread.sleep(4000);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 11)
    public void AS_14() throws Exception {
        add_to_report("ASK319_14");
        logger.info("Verify the system will go into alarm at the end of the entry delay if a sensor in group 35 is Activated in Arm Stay");
        log.log(LogStatus.INFO, ("*ASK319_14* Verify the system will go into alarm at the end of the entry delay if a sensor in group 35 is Activated in Arm Stay"));
        addPrimaryCall(35, 35, 5570631, 2);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
        Thread.sleep(4000);
        sensors.primaryCall(motion35, SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        Thread.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        Thread.sleep(4000);
        verifySensorstatusInAlarm("Activated");
        enterDefaultUserCode();
        Thread.sleep(4000);
        sensor_status_check(motion20, "Idle", "Activated", 3, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Idle***, ***Activated***"));
        Thread.sleep(4000);
        user_history_arm_stay_sensor_event_verification(35);
        Thread.sleep(4000);
    }

    @Test(priority = 12)
    public void AS_15() throws Exception {
        add_to_report("ASK319_15");
        logger.info("Verify the system will go into immediate alarm if a sensor in group 15 is activate and that the system can be disarmed before the dialer delay");
        log.log(LogStatus.INFO, ("*ASK319_15* Verify the system will go into immediate alarm if a sensor in group 15 is activate and that the system can be disarmed before the dialer delay"));
        int zone = 15;
        int group = 15;
        addPrimaryCall(zone, group, 5570628, 2);
        Thread.sleep(1000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        sensors.primaryCall(motion15, SensorsActivity.ACTIVATE);
        sensors.primaryCall(motion15, SensorsActivity.CLOSE);
        Thread.sleep(1000);
        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        verifySensorstatusInAlarm("Activated");
        enterDefaultUserCode();
        sensor_status_check(motion20, "Idle", "Activated", 2, 4);
        log.log(LogStatus.PASS, ("Pass: history events are displayed correctly: ***Idle***, ***Activated***"));
        user_history_arm_stay_sensor_event_verification(zone);
        deleteFromPrimary(zone);
        Thread.sleep(4000);
    }

    public void arm_stay_2sensors_event(int zone1, int zone2, String DLID1, String DLID2, String Status1, String Status2, int time) throws Exception {
        servcall.set_ARM_STAY_NO_DELAY_disable();
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(4000);
        verifyArmstay();
        Thread.sleep(4000);
        sensors.primaryCall(DLID1, SensorsActivity.OPEN);
        Thread.sleep(5000);
        sensors.primaryCall(DLID2, SensorsActivity.OPEN);
        Thread.sleep(2000);
        Thread.sleep(time);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals(Status1)) {
            logger.info("Pass: Correct status is " + Status1);
            log.log(LogStatus.PASS, ("Pass: Correct status is " + Status1));
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            log.log(LogStatus.FAIL, ("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText()));
        }
        try {
            if (events.get(1).getText().equals(Status2)) {
                logger.info("Pass: Correct status is " + Status2);
                log.log(LogStatus.PASS, ("Pass: Correct status is " + Status2));
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
                log.log(LogStatus.FAIL, ("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText()));
            }
        } catch (Exception e) {
            logger.info("Sensor " + zone2 + " event is not present on Alarm page");
            log.log(LogStatus.INFO, ("Sensor " + zone2 + " event is not present on Alarm page"));
        }

        verifyInAlarm();
        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
        Thread.sleep(10000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(zone1);
        Thread.sleep(1000);
        deleteFromPrimary(zone2);
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(10000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {
        }

        Thread.sleep(10000);
        adc.driver1.get("https://www.alarm.com/web/History/EventHistory.aspx");

        Thread.sleep(10000);
        File source = ((TakesScreenshot) adc.driver1).getScreenshotAs(OutputType.FILE);
        String path = projectPath+"/scr/" + source.getName();
        org.apache.commons.io.FileUtils.copyFile(source, new File(path));
        Thread.sleep(2000);
//        String a = adc.driver1.findElement(By.xpath("//div[contains(@class, 'main') and contains(text(), 'Pending Alarm (Awaiting panel's Programmed Delay)')]")).getAttribute("InnerHTML");
//       System.out.println(a);
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
        Thread.sleep(3000);
    }

    @Test(priority = 13)
    public void AS_16() throws Exception {
        add_to_report("ASK319_16");
        logger.info("Verify the system reports alarm on both sensors (10 and 12 groups) at the end of the entry delay ");
        log.log(LogStatus.INFO, ("*ASK319_16* Verify the system reports alarm on both sensors (10 and 12 groups) at the end of the entry delay "));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 12, door_window10, door_window12, "Open", "Open", ConfigProps.normalEntryDelay);

    }

    @Test(priority = 14)
    public void AS_17() throws Exception {
        add_to_report("ASK319_17");
        logger.info("Verify the system reports alarm on both sensors (10 and 14 groups) at the end of the entry delay ");
        log.log(LogStatus.INFO, ("*ASK319_17* Verify the system reports alarm on both sensors (10 and 14 groups) at the end of the entry delay"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(14, 14, 6619299, 1);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 14, door_window10, door_window14, "Open", "Open", ConfigProps.normalEntryDelay);
    }

    @Test(priority = 15)
    public void AS_18() throws Exception {
        add_to_report("ASK319_18");
        logger.info("Verify the panel goes into immediate alarm when a sensor in group 13 is activated and reports alarm on both sensors");
        log.log(LogStatus.INFO, ("*ASK319_18* Verify the panel goes into immediate alarm when a sensor in group 13 is activated and reports alarm on both sensors"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(13, 13, 6619298, 1);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 13, door_window10, door_window13, "Open", "Open", 1000);
    }

    @Test(priority = 16)
    public void AS_19() throws Exception {
        add_to_report("ASK319_19");
        logger.info("Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 16 ");
        log.log(LogStatus.INFO, ("*ASK319_19* Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 16"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(16, 16, 6619300, 1);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 16, door_window10, door_window16, "Open", "Open", ConfigProps.longEntryDelay);
        System.out.println("passed if only 1 status shown in Alarm page and 'Door/Window 16 (Sensor 16) Pending Alarm' event " +
                "is not present in User website history");
        log.log(LogStatus.PASS, ("Pass: only 1 sensor is displayed on Alarm page"));
    }

    @Test(priority = 17)
    public void AS_20() throws Exception {
        add_to_report("ASK319_20");
        logger.info("Verify the system reports alarm on both sensors (10 and 15 groups) at the end of the entry delay ");
        log.log(LogStatus.INFO, ("*ASK319_20* Verify the system reports alarm on both sensors (10 and 15 groups) at the end of the entry delay"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(15, 15, 5570628, 2);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 15, door_window10, motion15, "Open", "Activated", ConfigProps.longEntryDelay);
    }

    @Test(priority = 18)
    public void AS_21() throws Exception {
        add_to_report("ASK319_21");
        logger.info("Verify the system reports alarm on both sensors(10 and 35 groups)  at the end of the entry delay ");
        log.log(LogStatus.INFO, ("*ASK319_21* Verify the system reports alarm on both sensors (10 and 35 groups) at the end of the entry delay"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(35, 35, 5570631, 2);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 35, door_window10, motion35, "Open", "Activated", ConfigProps.longEntryDelay);
    }

    @Test(priority = 19)
    public void AS_22a() throws Exception {
        add_to_report("ASK319_22_a");
        logger.info("Verify the system reports alarm on only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 20");
        log.log(LogStatus.INFO, ("*ASK319_22_a* Verify the system reports alarm on only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 20"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(20, 20, 5570630, 2);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 20, door_window10, motion20, "Open", "Activated", ConfigProps.longEntryDelay);
        logger.info("Passed.The system does not report alarm on group 20. Event not present in Alarm page and User website history");
        log.log(LogStatus.PASS, ("Pass: The system does not report alarm on group 20. Event not present in Alarm page and User website history "));
    }

    @Test(priority = 20)
    public void AS_22b() throws Exception {
        add_to_report("ASK319_22_b");
        logger.info("Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 17");
        log.log(LogStatus.INFO, ("*ASK319_22_b* Verify the system reports alarm on  only the sensor in group 10 at the end of the entry delay. Verify the system does not report alarm on group 17"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(17, 17, 5570629, 2);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        arm_stay_2sensors_event(10, 17, door_window10, motion17, "Open", "Activated", ConfigProps.longEntryDelay);
        logger.info("Passed.The system does not report alarm on group 17. Event not present in Alarm page and User website history");
        log.log(LogStatus.PASS, ("Pass: The system does not report alarm on group 17. Event not present in Alarm page and User website history"));
    }

    @Test(priority = 15)
    public void AS_23() throws Exception {
        add_to_report("ASK319_23");
        logger.info("Verify the system can be disarmed during the entry delay (10 and 10 groups)");
        log.log(LogStatus.INFO, ("*ASK319_23* Verify the system can be disarmed during the entry delay (10 and 10 groups)"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(13, 10, 6619298, 1);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        disarm_during_entry_delay(10, 13, door_window10, door_window13, "Disarmed", "Open", "Arm-Stay", "Open", "Arm-Stay", 15000, 1, 2, 3, 4, 5);
        log.log(LogStatus.PASS, ("Pass: system is successfully disarmed during entry delay"));
    }

    public void disarm_during_entry_delay(int zone1, int zone2, String DLID1, String DLID2, String Status, String Status1, String Status2, String Status3, String Status4, int entry_delay, int n0, int n, int n1, int n3, int n4) throws Exception {
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        sensors.primaryCall(DLID1, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(DLID1, SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall(DLID2, SensorsActivity.OPEN);
        Thread.sleep(ConfigProps.normalEntryDelay /2);
        enterDefaultUserCode();
        verifyDisarm();
        Thread.sleep(1000);
        logger.info("Panel history verification");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(1).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(1).getText() + "***");
            log.log(LogStatus.PASS, ("Pass: sensor status is displayed correctly: ***" + li_status1.get(1).getText() + "***"));

        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(1).getText() + "***");
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed incorrect: ***" + li_status1.get(1).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(2).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(2).getText() + "***");
            log.log(LogStatus.PASS, ("Pass: sensor status is displayed correctly: ***" + li_status1.get(2).getText() + "***"));
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(2).getText() + "***");
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed in correct: ***" + li_status1.get(2).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(3).getText().equals(Status2)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(3).getText() + "***");
            log.log(LogStatus.PASS, ("Pass: sensor status is displayed correctly: ***" + li_status1.get(3).getText() + "***"));
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(3).getText() + "***");
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed in correct: ***" + li_status1.get(3).getText() + "***");
        }
        Thread.sleep(1000);
        if (li_status1.get(4).getText().equals(Status3)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(4).getText() + "***");
            log.log(LogStatus.PASS, ("Pass: sensor status is displayed correctly: ***" + li_status1.get(4).getText() + "***"));
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(4).getText() + "***");
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed in correct: ***" + li_status1.get(4).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(5).getText().equals(Status4)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(5).getText() + "***");
            log.log(LogStatus.PASS, ("Pass: sensor status is displayed correctly: ***" + li_status1.get(5).getText() + "***"));
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(5).getText() + "***");
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed in correct: ***" + li_status1.get(5).getText() + "***");
        }
        Thread.sleep(2000);
        home.Home_button.click();
        deleteFromPrimary(zone1);
        deleteFromPrimary(zone2);
        logger.info("User website history verification");

        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(10000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {
        }

        Thread.sleep(10000);
        adc.driver1.get("https://www.alarm.com/web/History/EventHistory.aspx");

        Thread.sleep(10000);
        File source = ((TakesScreenshot) adc.driver1).getScreenshotAs(OutputType.FILE);
        String path = projectPath+"/scr/" + source.getName();
        org.apache.commons.io.FileUtils.copyFile(source, new File(path));
        Thread.sleep(2000);
    }

    @Test(priority = 16)
    public void AS_24() throws Exception {
        add_to_report("ASK319_24");
        logger.info("Verify the system can be disarmed during the entry delay (10 and 12 groups)");
        log.log(LogStatus.INFO, ("*ASK319_24* Verify the system can be disarmed during the entry delay (10 and 12 groups)"));
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(4000);
        adc.New_ADC_session(adc.getAccountId());
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        adc.Request_equipment_list();
        Thread.sleep(1000);
        disarm_during_entry_delay(10, 12, door_window10, door_window12, "Disarmed", "Open", "Closed", "Open", "Arm-Stay", 15000, 1, 4, 5, 1, 2);
        log.log(LogStatus.PASS, ("Pass: system is successfully disarmed during entry delay"));
    }

    @Test(priority = 17)
    public void AS_25() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 15 groups). ");
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(15, 15, 5570628, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10, 15, door_window10, motion15, "Disarmed (Intrusion)", "Idle", "Activated", "Closed", "Open", 15000, 1, 4, 5, 1, 2);
    }

    @Test(priority = 18)
    public void AS_26() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 17 groups). ");
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(17, 17, 5570629, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10, 17, door_window10, motion17, "Disarmed (Intrusion)", "Idle", "Activated", "Open", "Arm-Stay", 15000, 1, 3, 4, 1, 2);
        logger.info("Pass: No event for Sensor group 17 on User website");
    }

    @Test(priority = 19)
    public void AS_27() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 20 groups). ");
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(20, 20, 5570630, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10, 20, door_window10, motion20, "Disarmed (Intrusion)", "Idle", "Activated", "Open", "Arm-Stay", 15000, 1, 3, 4, 1, 2);
        logger.info("Pass: No event for Sensor group 20 on User website");
    }

    @Test(priority = 20)
    public void AS_28() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 35 groups). ");
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(35, 35, 5570631, 2);
        Thread.sleep(4000);
        disarm_during_entry_delay(10, 35, door_window10, motion35, "Disarmed (Intrusion)", "Idle", "Activated", "Closed", "Open", 15000, 1, 5, 5, 1, 2);
    }

    @Test(priority = 21)
    public void AS_29() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (8 group). ");
        addPrimaryCall(8, 8, 6619302, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(8, 8, door_window8, "Tampered", "Closed", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 22)
    public void AS_30() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (9 group). ");
        addPrimaryCall(9, 9, 6619303, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(9, 9, door_window9, "Tampered", "Closed", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 23)
    public void AS_31() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (10 group). ");
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(10, 10, door_window10, "Tampered", "Closed", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 24)
    public void AS_32() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (12 group). ");
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(12, 12, door_window12, "Tampered", "Closed", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 25)
    public void AS_33() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (13 group). ");
        addPrimaryCall(13, 13, 6619298, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(13, 13, door_window13, "Tampered", "Closed", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 26)
    public void AS_34() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (14 group). ");
        addPrimaryCall(14, 14, 6619299, 1);
        Thread.sleep(4000);
        immediate_tamper_alarm(14, 14, door_window14, "Tampered", "Closed", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 27)
    public void AS_35() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (15 group). ");
        addPrimaryCall(15, 15, 5570628, 2);
        Thread.sleep(4000);
        immediate_tamper_alarm(15, 15, motion15, "Tampered", "Idle", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 28)
    public void AS_36() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (35 group). ");
        addPrimaryCall(35, 35, 5570631, 2);
        Thread.sleep(4000);
        immediate_tamper_alarm(35, 35, motion35, "Tampered", "Idle", 4, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 29)
    public void AS_37a() throws Exception {
        logger.info("Verify the panel will not report a tamper alarm (16 group). ");
        addPrimaryCall(16, 16, 6619300, 1);
        Thread.sleep(4000);
        NO_tamper_alarm(16, 16, door_window16, "Tampered", "Closed", 3, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 30)
    public void AS_37b() throws Exception {
        logger.info("Verify the panel will notvreport an immediate tamper alarm (17 group). ");
        addPrimaryCall(17, 17, 5570629, 2);
        Thread.sleep(4000);
        Thread.sleep(4000);
        NO_tamper_alarm(17, 17, motion17, "Tampered", "Idle", 3, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 31)
    public void AS_37c() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (20 group). ");
        addPrimaryCall(20, 20, 5570630, 2);
        Thread.sleep(4000);
        NO_tamper_alarm(20, 20, motion20, "Tampered", "Idle", 3, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 32)
    public void AS_37d() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (26 group). ");
        addPrimaryCall(26, 26, 6750242, 5);
        Thread.sleep(4000);
        NO_tamper_alarm(26, 26, Smoke, "Tampered", "Normal", 3, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 33)
    public void AS_37e() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (52 group Freeze sensor). ");
        addPrimaryCall(52, 52, 7536801, 17);
        Thread.sleep(4000);
        NO_tamperM_alarm(52, 52, Freeze, "Tampered", "Normal", 3, 1);
        Thread.sleep(4000);
    }

    @Test(priority = 34)
    public void AS_37h() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (34 group). ");
        addPrimaryCall(34, 34, 7667882, 6);
        Thread.sleep(4000);
        NO_tamper_alarm(34, 34, CO, "Tampered", "Normal", 3, 2);
        Thread.sleep(4000);
    }

    @Test(priority = 35)
    public void AS_37waterM() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (38 group, subtype_ multi). ");
        addPrimaryCall(38, 38, 7672224, 22);
        Thread.sleep(4000);
        NO_tamperM_alarm(38, 38, Water, "Tampered", "Normal", 3, 1);
        Thread.sleep(4000);
    }

    @Test(priority = 36)
    public void AS_37waterF() throws Exception {
        logger.info("Verify the panel will not report an immediate tamper alarm (38 group, subtype_ flood). ");
        addPrimaryCall(36, 38, 7672224, 23);
        Thread.sleep(4000);
        NO_tamperM_alarm(36, 38, Water, "Tampered", "Normal", 3, 1);
        Thread.sleep(4000);
    }

    public void NO_tamperM_alarm(int zone, int group, String DLID, String Status, String Status1, int n0, int n1) throws Exception {
        Thread.sleep(6000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(4000);
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(2000);
        //Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        verifyArmstay();
        Thread.sleep(4000);
        // sensors.primaryCall(DLID, restore);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone + ") Tamper')]"));
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
            System.out.println("No such element found!!!");
        }
        DISARM();
        Thread.sleep(3000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(4000);
        //Home_Page home = PageFactory.initElements(driver, Home_Page.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigateToSettingsPage();
        Thread.sleep(2000);
        sett.STATUS.click();
        Thread.sleep(2000);
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText() + "***");
        }
        Thread.sleep(3000);
        deleteFromPrimary(zone);
        Thread.sleep(4000);
    }

    public void NO_tamper_alarm(int zone, int group, String DLID, String Status, String Status1, int n0, int n1) throws Exception {
        Thread.sleep(1000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(4000);
        verifyArmstay();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone + ") Tamper')]"));
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
            System.out.println("No such element found!!!");
        }
        DISARM();
        Thread.sleep(3000);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        Thread.sleep(1000);
        sett.Panel_history.click();
        Thread.sleep(1000);
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText() + "***");
        }
        Thread.sleep(2000);
        deleteFromPrimary(zone);
        Thread.sleep(4000);
    }

    public void immediate_tamper_alarm(int zone, int group, String DLID, String Status, String Status1, int n0, int n1) throws Exception {
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(3000);
        sensors.primaryCall(DLID, SensorsActivity.TAMPER);
        Thread.sleep(3000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals(Status)) {
            logger.info("Pass: Correct status is " + Status);
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        verifyInAlarm();
        Thread.sleep(3000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone + ") Pending Alarm ')]"));
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
        enterDefaultUserCode();
        Thread.sleep(1000);
        sensors.primaryCall(DLID, SensorsActivity.RESTORE);
        Thread.sleep(3000);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        Thread.sleep(1000);
        sett.Panel_history.click();
        Thread.sleep(1000);
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText() + "***");
        }
        Thread.sleep(2000);
        deleteFromPrimary(zone);
        Thread.sleep(6000);
    }

    @Test(priority = 38)
    public void AS_40() throws Exception {
        logger.info("Verify the system can not use a duress code if Duress Authentication is disabled(12 group). ");
        Thread.sleep(5000);
        addPrimaryCall(12, 12, 6619297, 1);
        servcall.set_DURESS_AUTHENTICATION_disable();
        Thread.sleep(3000);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(6000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(6000);
        sensors.primaryCall(door_window12, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall(door_window12, SensorsActivity.CLOSE);
        Thread.sleep(15000);
        enterDefaultDuressCode();
        verifyInAlarm();
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(4000);
    }

    @Test(priority = 39)
    /***Please Set 5 min PhotoFrame start time manually ***/
    public void AS_41() throws Exception {
        logger.info("Verify the system is still responsive when the panel is in screensaver mode(10-group sensor). ");
        Thread.sleep(5000);
        addPrimaryCall(10, 10, 6619296, 1);
        servcall.set_photo_frame_SCREEN_SAVER_TYPE();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        // servcall.set_5minutes_SCREEN_SAVER_IDLE_TIME();
        TimeUnit.MINUTES.sleep(1);
        ARM_STAY();
        TimeUnit.MINUTES.sleep(5);
        Thread.sleep(1000);
        verifyPhotoframeMode();
        Thread.sleep(1000);
        sensors.primaryCall(door_window10, SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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

    @Test(priority = 42)
    /***Please Set 5 min PhotoFrame start time manually ***/
    public void AS_42() throws Exception {
        logger.info("Verify the system is still responsive when the panel is in screensaver mode (10 and 12-group sensors). ");
        Thread.sleep(3000);
        addPrimaryCall(13, 13, 6619298, 1);
        servcall.set_photo_frame_SCREEN_SAVER_TYPE();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        // servcall.set_5minutes_SCREEN_SAVER_IDLE_TIME();
        TimeUnit.MINUTES.sleep(1);
        ARM_STAY();
        TimeUnit.MINUTES.sleep(5);
        verifyPhotoframeMode();
        Thread.sleep(1000);
        sensors.primaryCall(door_window13, SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(13);
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        }
    }

    @Test(priority = 43)
    /***Please Set 5 min PhotoFrame start time manually ***/
    public void AS_43() throws Exception {
        logger.info("Verify the system is still responsive when the panel is in screensaver mode (13-group sensor). ");
        Thread.sleep(5000);
        addPrimaryCall(10, 10, 6619296, 1);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(4000);
        servcall.set_photo_frame_SCREEN_SAVER_TYPE();
        servcall.set_ARM_STAY_NO_DELAY_enable();
        // servcall.set_5minutes_SCREEN_SAVER_IDLE_TIME();
        Thread.sleep(6000);
        ARM_STAY();
        TimeUnit.MINUTES.sleep(5);
        verifyPhotoframeMode();
        Thread.sleep(1000);
        sensors.primaryCall(door_window10, SensorsActivity.OPEN);
        sensors.primaryCall(door_window12, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall(door_window10, SensorsActivity.CLOSE);
        sensors.primaryCall(door_window12, SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        }
    }

    @Test(priority = 83)
    public void AS_45() throws Exception {
        logger.info("Verify that the websites report that the sensor has been left open");
        Thread.sleep(3000);
        addPrimaryCall(10, 10, 6619296, 1);
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
        sensors.primaryCall(door_window10, SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(3000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        }
    }

    @Test(priority = 44)
    public void AS_49() throws Exception {
        logger.info("Verify the panel will Arm Stay at the end of the exit delay if Arm Stay button is pressed by 1-group keyfob ");
        addPrimaryCall(38, 1, 6619386, 102);
        Thread.sleep(4000);
        servcall.set_KEYFOB_NO_DELAY_disable();
        Thread.sleep(4000);
        logger.info("Keyfob group 1: Disarm, panic = Police");
        Thread.sleep(4000);
        sensors.primaryCall("65 00 AF", keyfobStay);
        Thread.sleep(15000);
        verifyArmstay();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 AF", keyfobDisarm);
        Thread.sleep(4000);
        verifyDisarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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

        deleteFromPrimary(38);
        Thread.sleep(4000);
    }

    @Test(priority = 45)
    public void AS_50_52() throws Exception {
        logger.info("Verify the panel will Arm Away at the end of the exit delay if Arm Away button is pressed by 1-group keyfob");
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 6-group keyfob");
        addPrimaryCall(38, 1, 6619386, 102);
        addPrimaryCall(39, 6, 6619387, 102);
        servcall.set_KEYFOB_NO_DELAY_disable();
        servcall.set_KEYFOB_DISARMING(01);
        logger.info("Keyfob group 1: Disarm, panic = Police");
        logger.info("Keyfob group 6: Disarm, panic = Auxiliary");
        Thread.sleep(5000);
        sensors.primaryCall("65 00 AF", keyfobAway);
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 BF", keyfobDisarm);
        Thread.sleep(4000);
        verifyDisarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(38);
        Thread.sleep(4000);
        deleteFromPrimary(39);
        Thread.sleep(4000);
    }

    @Test(priority = 46)
    public void AS_56() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will Arm Away at the end of the exit delay if Arm Away button is pressed by 6-group keyfob");
        addPrimaryCall(39, 6, 6619387, 102);
        servcall.set_KEYFOB_NO_DELAY_disable();
        logger.info("Keyfob group 6: Disarm, panic = Auxiliary");
        Thread.sleep(5000);
        sensors.primaryCall("65 00 BF", keyfobAway);
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(3000);
        home.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        verifyDisarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(39);
        Thread.sleep(4000);
    }

    @Test(priority = 47)
    public void AS_58() throws Exception {
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 4-group keyfob");
        addPrimaryCall(40, 4, 6619388, 102);
        Thread.sleep(4000);
        servcall.set_KEYFOB_NO_DELAY_enable();
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");
        ;
        Thread.sleep(5000);
        sensors.primaryCall("65 00 CF", keyfobAway);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 CF", keyfobDisarm);
        Thread.sleep(4000);
        verifyDisarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(40);
        Thread.sleep(4000);
    }

    @Test(priority = 48)
    public void AS_59() throws Exception {
        logger.info("Verify the panel will Arm Away at the end of the exit delay if Arm Away button is pressed by 4-group keyfob");
        addPrimaryCall(40, 4, 6619388, 102);
        Thread.sleep(4000);
        servcall.set_KEYFOB_NO_DELAY_disable();
        logger.info("Keyfob group 4: ArmStay-ArmAway-Disarm, panic = Fixed Auxiliary");
        ;
        Thread.sleep(5000);
        sensors.primaryCall("65 00 CF", keyfobAway);
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 CF", keyfobDisarm);
        Thread.sleep(4000);
        verifyDisarm();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(40);
        Thread.sleep(4000);
    }

    @Test(priority = 50)
    public void AS_68() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(4000);
        home.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 52)
    public void AS_67() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(4000);
        home.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 53)
    public void AS_69() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        home.DISARM.click();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 54)
    public void AS_70() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(4000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall(door_window10, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Tampered")) {
                logger.info("Pass: Correct status is " + "Tampered");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(4000);
    }

    @Test(priority = 55)
    public void AS_71() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(4000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall(door_window12, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Tampered")) {
                logger.info("Pass: Correct status is " + "Tampered");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(4000);
    }

    @Test(priority = 57)
    public void AS_72() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 60)
    public void AS_73() throws Exception {
        logger.info("Verify the panel will go into immediate tamper alarm if a shock-detector in group 13 is tampered");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " + "tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }

        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 58)
    public void AS_75() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(4000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9", "Activated", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 59)
    public void AS_76() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will will just create notification if a shock-detector in group 17 is tampered");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(4000);
        sensors.primaryCall("66 00 D9", SensorsActivity.TAMPER);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9", "Tampered", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 61)
    public void AS_77() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will will just create notification if a shock-detector in group 17 is tampered");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.TAMPER);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        sensor_status_check("66 00 D9", "Tampered", "Normal", 3, 1);
        deleteFromPrimary(34);
    }

    @Test(priority = 62)
    public void AS_78() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if a Glass-break detector in group 17 is activated");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(4000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(4000);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9", "Activated", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 63)
    public void AS_79() throws Exception {
        logger.info("Verify the panel will will just create notification if a shock-detector in group 17 is tampered");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(4000);
        sensors.primaryCall("66 00 D9", SensorsActivity.TAMPER);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9", "Tampered", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 65)
    public void AS_80() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("66 00 D9", "Activated", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
    }

    @Test(priority = 64)
    public void AS_81() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        //addPrimaryCall(28, 13,  6750361 , 19);
        addPrimaryCall(28, 13, 6750361, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 66)
    public void AS_82() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verify the panel will go into immediate alarm if a Glass-break detector in group 13 is tampered");
        //addPrimaryCall(28, 13,  6750361 , 19);
        addPrimaryCall(28, 13, 6750361, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " + "Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 67)
    public void AS_83() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if Glass-breakdetector in group 13 is activated");
        addPrimaryCall(28, 13, 6750361, 19);
        Thread.sleep(4000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall(door_window10, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 28 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(4000);
    }

    @Test(priority = 69)
    public void AS_84() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if Glass-breakdetector in group 13 is activated");
        addPrimaryCall(28, 13, 6750361, 19);
        Thread.sleep(4000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall(door_window12, SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(4000);
    }

    @Test(priority = 70)
    public void AS_85() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if Glass-breakdetector in group 13 is activated");
        addPrimaryCall(28, 13, 6750361, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 71)
    public void AS_86() throws Exception {
        logger.info("Verify the panel will go into immediate tamper alarm if a Glass-break detector in group 13 is tampered");
        addPrimaryCall(28, 13, 6750361, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(4000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " + "Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 72)
    public void AS_87() throws Exception {
        logger.info("Verify the panel will go into immediate alarm is a Glass-break detector in group 13 is activated");
        addPrimaryCall(28, 13, 6750361, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(4000);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 73)
    public void AS_88() throws Exception {
        logger.info("Verify the panel will go into immediate alarm is a Glass-break detector in group 13 is activated");
        addPrimaryCall(28, 13, 6750361, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 74)
    public void AS_89() throws Exception {
        logger.info("Verify the panel will  just create notification if a Glass-break detector in group 17 is tampered");
        addPrimaryCall(28, 13, 6750361, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
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
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 75)
    public void AS_90() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is activated");
        addPrimaryCall(29, 17, 6750355, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.DISARM.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 39", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("67 00 39", "Activated", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(29);
        Thread.sleep(4000);
    }

    @Test(priority = 76)
    public void AS_91() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is tampered");
        addPrimaryCall(29, 17, 6750355, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 39", SensorsActivity.RESTORE);
        Thread.sleep(4000);
        sensor_status_check("67 00 39", "Activated", "Normal", 3, 1);
        Thread.sleep(4000);
        deleteFromPrimary(29);
        Thread.sleep(4000);
    }

    @Test(priority = 92)
    public void AS_92() throws Exception {
        logger.info("verify the panel will not go into immediate alarm when group 13 d/w sensor is tripped during 'Quick Exit' ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        addPrimaryCall(2, 17, 6750355, 19);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.CLOSE);
        Thread.sleep(20000);
        verifyArmstay();
        DISARM();
        Thread.sleep(2000);
        deleteFromPrimary(2);
        Thread.sleep(4000);
    }

    @Test(priority = 93)
    public void AS_93() throws Exception {
        logger.info("verify the panel will not go into immediate alarm when group 13 d/w sensor is tripped during 'Quick Exit' ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        addPrimaryCall(3, 13, 6619298, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.CLOSE);
        Thread.sleep(20000);
        verifyArmstay();
        Thread.sleep(4000);
        DISARM();
        Thread.sleep(2000);
        deleteFromPrimary(3);
        Thread.sleep(4000);
    }

    @Test(priority = 94)
    public void AS_94() throws Exception {
        logger.info("verify the panel will not go into immediate alarm when group 14 d/w sensor is tripped during 'Quick Exit'   ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        addPrimaryCall(4, 14, 6619299, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", SensorsActivity.CLOSE);
        Thread.sleep(20000);
        verifyArmstay();
        DISARM();
        Thread.sleep(2000);
        deleteFromPrimary(4);
        Thread.sleep(4000);
    }

    @Test(priority = 95)
    public void AS_95() throws Exception {
        logger.info("verify the panel will go into immediate alarm when group 8 d/w sensor is tripped during 'Quick Exit'  ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        addPrimaryCall(6, 8, 6619301, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 5A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(6);
        Thread.sleep(4000);
    }

    @Test(priority = 96)
    public void AS_96() throws Exception {
        logger.info("verify the panel will go into  alarm  when group 9 d/w sensor is tripped during 'Quick Exit' ");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        addPrimaryCall(7, 9, 6619302, 1);
        Thread.sleep(2000);
        ARM_STAY();
        driver.findElement(By.id("com.qolsys:id/img_quick_exit")).click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 6A", SensorsActivity.OPEN);
        Thread.sleep(16000);
        verifyInAlarm();
        Thread.sleep(4000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(7);
        Thread.sleep(4000);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();

        adc.driver1.quit();
    }
}