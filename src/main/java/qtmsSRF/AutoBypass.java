package qtmsSRF;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AutoBypass extends Setup {
    String page_name = "QTMS: Auto Bypass";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    private String log_path = new String(System.getProperty("user.dir")) + "/log/test.txt";
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;


    public AutoBypass() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    public void swipe_bypass_page() throws InterruptedException {
        int starty = 507;
        int endy = 177;
        int startx = 907;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }
    public void create_report(String test_area_name) throws InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/QTMS_Auto-Bypass.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Olga Kulik")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(projectPath + "/Report/QTMS_Auto-Bypass.html", false);
        log = report.startTest(test_case_name);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        Thread.sleep(1000);
    }

    // @Test  /*** Disarm mode/1)a sensor must be paired 2)Auto Bypass Enabled ***/
    public void addSensors() throws Exception {
        addPrimaryCall(3, 10, 6619296, 1); // 65 00 A0
        Thread.sleep(2000);
        addPrimaryCall(4, 12, 6619297, 1);
        Thread.sleep(2000);
        addPrimaryCall(5, 13, 6619298, 1);
        Thread.sleep(2000);
        addPrimaryCall(6, 14, 6619299, 1);
        Thread.sleep(2000);
        addPrimaryCall(7, 16, 6619300, 1);
        Thread.sleep(2000);
        addPrimaryCall(20, 15, 5570628, 2);
        Thread.sleep(2000);
        addPrimaryCall(22, 17, 5570629, 2);
        Thread.sleep(2000);
        addPrimaryCall(23, 20, 5570630, 2); // 55 00 46
        Thread.sleep(8000);
    }

    @Test(priority = 2)
/*** Disarm mode/1)a sensor must be paired 2)Auto Bypass Enabled ***/
    public void AB319_02_AB319_04() throws Exception {
        add_to_report("AB319_02_AB319_04");
        log.log(LogStatus.INFO, ("*AB319_02* Verify that open sensor will be selected for bypass and at top of sensor list when pushing arm button."));
        Thread.sleep(4000);
        addPrimaryCall(3, 10, 6619296, 1);
        log.log(LogStatus.PASS, "Pass: Sensor is added ***");
        servcall.set_AUTO_BYPASS(01);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Auto Bypass is enabled ***");
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        log.log(LogStatus.PASS, "Pass: Auto Stay setting is disabled ***");
        Thread.sleep(2000);
        home_page.DISARM.click();
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_active")).click();
        if (driver.findElement(By.id("com.qolsys:id/uiTVName")).getText().equals("Door/Window 3"))
            log.log(LogStatus.PASS, "AB319_2 Pass: Sensor is bypassed ***");
        else
            log.log(LogStatus.FAIL, "Fail: Sensor is not selected for bypass ***");
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
        Thread.sleep(2000);
        home_page.ARM_AWAY.click();
        // driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(13000);
        logger.info("*AB319_04*Verify that open sensors are bypassed after arming");
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(8000);
        verifyArmaway();
        log.log(LogStatus.PASS, "AB319_4 Pass: System is in arm away ***");
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "AB319_4 Pass: Sensor is bypassed ***");
        home_page.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 3)
    public void AB319_03_AB319_09() throws Exception {
        Thread.sleep(2000);
        add_to_report("AB319_03_AB319_09");
        log.log(LogStatus.INFO, ("*AB319_03_AB319_09* "));
        logger.info("*AB319_03* Verify that TTS will announce bypassed sensors after arming ");
        servcall.set_AUTO_STAY(0);
        log.log(LogStatus.PASS, "Pass: Auto Stay setting is disabled ***");
        Thread.sleep(2000);
        servcall.set_AUTO_BYPASS(01);
        log.log(LogStatus.PASS, "Pass: Auto Bypass is enabled ***");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is added ***");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("open DW before ARM AWAY");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in Arm Away ***");
        deleteLogFile(log_path);
        Thread.sleep(2000);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS processing:Door/Window 3, ByPassed"}, 1);
        Thread.sleep(4000);
        log.log(LogStatus.PASS, "Pass: Event in logs found 'TtsUtil:: TTS processing:Door/Window 3, ByPassed' ***");
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is closed ***");
       // home.ArwAway_State.click();
      //  enterDefaultUserCode();
        logger.info("*AB319_09* Verify that TTS will not announce opening and closing of bypassed sensor while armed away");
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        log.log(LogStatus.PASS, "Pass: Sensor is closed ***");
        Thread.sleep(2000);
        deleteLogFile(log_path);
        Thread.sleep(2000);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS service received Door/Window 3,  ByPassed"}, 1);
        log.log(LogStatus.PASS, "Pass: No TTS message is into logs ***");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        logger.info("*AB319_09* Verify that TTS will not announce opening and closing of bypassed sensor while armed stay");
        logger.info("open DW before ARM STAY");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        log.log(LogStatus.PASS, "Pass: System is in Arm Stay ***");
        deleteLogFile(log_path);
        Thread.sleep(2000);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS processing:Door/Window 3, ByPassed"}, 1);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is closed ***");
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is closed ***");
        deleteLogFile(log_path);
        Thread.sleep(2000);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS service received Door/Window 3,  ByPassed"}, 1);
        log.log(LogStatus.PASS, "Pass: No TTS message is into logs ***");
        Thread.sleep(2000);
        DISARM();
        Thread.sleep(2000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 8)
    public void AB319_10_AB319_11() throws Exception {
        add_to_report("AB319_05_AB319_10_AB319_11");
        logger.info("*AB319_05* Verify that Open Sensor Protest will appear if bypass is unselected and system is armed");
        logger.info("*AB319_10*Verify that Open Sensor Protest appears when Auto Bypass is dissabled, sensor is opened, and arm is attempted.");
        Thread.sleep(10000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
//        log.log(LogStatus.PASS, "Pass: Sensor is added ***");
      servcall.set_AUTO_BYPASS(00);
//        Thread.sleep(2000);
//        log.log(LogStatus.PASS, "Pass: Auto Bypass setting is enabled ***");
        servcall.set_AUTO_STAY(0);
        log.log(LogStatus.PASS, "Pass: Auto Stay setting is disabled ***");
        Thread.sleep(10000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        home_page.DISARM.click();
        Thread.sleep(2000);
        home_page.ARM_AWAY.click();
        Thread.sleep(2000);
        if (driver.findElements(By.id("com.qolsys:id/message")).size() == 1) {
            log.log(LogStatus.PASS, "Pass: Open Sensor Pop-up Message Received ***");
            logger.info("Pass: Open Sensor Pop-up Message Received");
            driver.findElement(By.id("com.qolsys:id/ok")).click();
        } else {
            logger.info("Fail: Open Sensor Pop-up Message Not Received");
            log.log(LogStatus.FAIL, "Fail: Open Sensor Pop-up Message Not Received***");
            Thread.sleep(2000);
            home_page.ARM_AWAY.click();
        }
        Thread.sleep(15000);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in Arm Away ***");
        Thread.sleep(2000);
        home_page.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 9)
    public void AB319_06() throws Exception {
        add_to_report("AB319_06");
        log.log(LogStatus.INFO, ("*AB319_06* Verify that panel will arm once sensor is closed from step AB319_05 "));
        logger.info("Verify that panel will arm once sensor is closed from step AB319_05");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        log.log(LogStatus.PASS, "Pass: Sensor is added ***");
        Thread.sleep(2000);
//        servcall.set_AUTO_BYPASS(01);
//        Thread.sleep(2000);
//        log.log(LogStatus.PASS, "Pass: Auto Bypass is enabled ***");
//        servcall.set_AUTO_STAY(0);
//        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Auto Stay setting is disabled ***");
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(4000);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        home_page.DISARM.click();
        Thread.sleep(2000);
        home_page.ARM_AWAY.click();
        Thread.sleep(2000);
        if (driver.findElements(By.id("com.qolsys:id/message")).size() == 1) {
            logger.info("Pass: Open Sensor Pop-up Message Received");
            log.log(LogStatus.PASS, "Pass: Open Sensor Pop-up Message Received ***");
            sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
            Thread.sleep(5000);
            log.log(LogStatus.PASS, "Pass: Sensor is closed ***");
            driver.findElement(By.id("com.qolsys:id/cancel")).click();
            Thread.sleep(2000);
//            driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
 //           Thread.sleep(2000);
            home_page.ARM_AWAY.click();
        } else {
            log.log(LogStatus.FAIL, "Fail: Open Sensor Pop-up Message Not Received ***");
            logger.info("Fail: Open Sensor Pop-up Message Not Received");
            Thread.sleep(2000);
            driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
            Thread.sleep(2000);
            home_page.ARM_AWAY.click();
        }
        Thread.sleep(14000);
        verifyArmaway();
        log.log(LogStatus.PASS, "Pass: System is in Arm Away ***");
        Thread.sleep(2000);
        logger.info("AB319_06 Pass: Verified that panel will arm once sensor is closed from step AB319_05");
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 4)
    public void AB319_07() throws Exception {
        add_to_report("AB319_07");
        log.log(LogStatus.INFO, ("*AB319_07* Verify that sensor will not Auto Bypass if sensor is opened after selecting arm button. "));
        logger.info("Verify that sensor will not Auto Bypass if sensor is opened after selecting arm button.");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is added ***");
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Auto Bypass is enabled ***");
        servcall.set_AUTO_STAY(0);
        log.log(LogStatus.PASS, "Pass: Auto Stay setting is disabled ***");
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(6000);
        home_page.DISARM.click();
        Thread.sleep(2000);
        home_page.ARM_AWAY.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        Thread.sleep(13000);
        verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: System in Alarm ***");
        Thread.sleep(500);
        logger.info("AB319_07 Pass: Verified that sensor will not Auto Bypass if sensor is opened after selecting arm button.");
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 5)
    public void AB319_08() throws Exception {
        add_to_report("AB319_08");
        log.log(LogStatus.INFO, ("*AB319_08* Verify that sensor can be unselected from bypass and system can be armed as normal."));
        logger.info("Verify that sensor can be unselected from bypass and system can be armed as normal");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is added ***");
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Auto Bypass is enabled ***");
        servcall.set_AUTO_STAY(0);
        log.log(LogStatus.PASS, "Pass: Auto Stay setting is disabled ***");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(4000);
        log.log(LogStatus.PASS, "Pass: Sensor is opened ***");
        driver.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_active")).click();
        if (driver.findElement(By.id("com.qolsys:id/uiTVName")).getText().equals("Door/Window 1"))
            tap(764, 192);
        else
            //logger.info("Fail: Sensor is not selected for bypass");
            log.log(LogStatus.FAIL, "Fail: Sensor is not selected for bypass ***");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Sensor is closed ***");
        driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: System is in Arm Away ***");
        logger.info("AB319_08 Pass: System armed.");
        home_page.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 11)
    public void AB319_12() throws Exception {
        add_to_report("AB319_12");
        log.log(LogStatus.INFO, ("*AB319_12* Verify that panel user can manually bypass opened sensors and any sensor"));
        addSensors();
        Thread.sleep(10000);
        logger.info("AB319_12: Verify that panel user can manually bypass opened sensors and any sensor.");
        Thread.sleep(6000);
        servcall.set_AUTO_BYPASS(0);
        Thread.sleep(2000);
        servcall.get_AUTO_BYPASS();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(6000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("55 00 44", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall("55 00 54", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall("55 00 64", SensorsActivity.TAMPER);
        Thread.sleep(8000);
        driver.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
//        home_page.DISARM.click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_active")).click();
        Thread.sleep(2000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/uiTVName"));
        li.size();
        Thread.sleep(2000);
        if (li.get(0).getText().equals("Door/Window 3")) {
            tap(764, 192);
            logger.info("Pass: Door/Window 3 is selected for bypass");
        } else
            logger.info("Fail: Door/Window 3 is not selected for bypass");
        if (li.get(1).getText().equals("Door/Window 5")) {
            tap(764, 271);
            logger.info("Pass: Door/Window 5 is selected for bypass");
        } else
            logger.info("Fail: Door/Window 5 is not selected for bypass");
        if (li.get(2).getText().equals("Door/Window 4")) {
            tap(764, 358);
            Thread.sleep(2000);
            logger.info("Pass: Door/Window 4 is selected for bypass");
        } else
            logger.info("Fail: Door/Window 4 is not selected for bypass");
        if (li.get(3).getText().equals("Motion 20")) {
            tap(764, 442);
            logger.info("Pass: Motion 20 is selected for bypass");
        } else
            logger.info("Fail: Motion 20 is not selected for bypass");
        Thread.sleep(2000);
        swipe_bypass_page();
        Thread.sleep(2000);
        List<WebElement> lis = driver.findElements(By.id("com.qolsys:id/uiTVName"));
        lis.size();
        Thread.sleep(2000);
        if (lis.get(3).getText().equals("Motion 23")) {
            tap(764, 407);
            logger.info("Pass: Motion 23 is selected for bypass");
        } else
            logger.info("Fail: Motion 23 is not selected for bypass");
        if (lis.get(2).getText().equals("Motion 22")) {
            tap(764, 317);
            logger.info("Pass: Motion 22 is selected for bypass");
        } else
            logger.info("Fail: Motion 22 is not selected for bypass");
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(2000);
        logger.info("AB319_12 Pass: System armed.");
        home_page.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(2000);
        deleteFromPrimary(4);
        Thread.sleep(2000);
        deleteFromPrimary(5);
        Thread.sleep(2000);
        deleteFromPrimary(6);
        Thread.sleep(2000);
        deleteFromPrimary(7);
        Thread.sleep(2000);
        deleteFromPrimary(20);
        Thread.sleep(2000);
        deleteFromPrimary(22);
        Thread.sleep(2000);
        deleteFromPrimary(23);
        Thread.sleep(8000);
    }

    @Test(priority = 6)
    public void AB319_13() throws Exception {
        add_to_report("AB319_13");
        log.log(LogStatus.INFO, ("*AB319_13* Verify that panel user can manually bypass any sensors"));
        addSensors();
        Thread.sleep(10000);
        logger.info("AB319_13: Verify that panel user can manually bypass any sensors");
        Thread.sleep(6000);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        servcall.get_AUTO_BYPASS();
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(6000);
        driver.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
        // home_page.DISARM.click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_all")).click();
        Thread.sleep(2000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/uiTVName"));
        li.size();
        Thread.sleep(2000);
        if (li.get(0).getText().equals("Door/Window 3")) {
            tap(764, 192);
            logger.info("Pass: Door/Window 3 is selected for bypass");
        } else
            logger.info("Fail: Door/Window 3 is not selected for bypass");
        Thread.sleep(4000);
        swipe_bypass_page();
        List<WebElement> lis = driver.findElements(By.id("com.qolsys:id/uiTVName"));
        lis.size();
        if (lis.get(3).getText().equals("Motion 23")) {
            tap(764, 462);
            logger.info("Pass: Motion 23 is selected for bypass");
        } else
            logger.info("Fail: Motion 23 is not selected for bypass");
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("55 00 64", SensorsActivity.ACTIVATE);

        logger.info("AB319_13 Pass: System armed.");
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        deleteFromPrimary(4);
        Thread.sleep(2000);
        deleteFromPrimary(5);
        Thread.sleep(2000);
        deleteFromPrimary(6);
        Thread.sleep(2000);
        deleteFromPrimary(7);
        Thread.sleep(2000);
        deleteFromPrimary(20);
        Thread.sleep(2000);
        deleteFromPrimary(22);
        Thread.sleep(2000);
        deleteFromPrimary(23);
        Thread.sleep(8000);
    }

    @Test(priority = 7)
    public void AB319_14oldversion() throws Exception {
        add_to_report("AB319_14");
        log.log(LogStatus.INFO, ("*AB319_14* Verify that panel can arm away when a entry delay(group10,12) sensor is unselected from bypassed sensor list"));
        addSensors();
        Thread.sleep(10000);
        logger.info("Verify that panel can arm away when a entry delay(group10,12) sensor is unselected from bypassed sensor list");
        Thread.sleep(8000);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(1);
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(6000);
        driver.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
        // home_page.DISARM.click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_active")).click();
        List<WebElement> lis = driver.findElements(By.id("com.qolsys:id/uiTVName"));
        lis.size();
        if (lis.get(0).getText().equals("Door/Window 4")) {
            tap(764, 182);
            logger.info("Pass: Door/Window 4 is disselected for bypass");
        } else
            logger.info("Fail: Sensor is selected for bypass");
        Thread.sleep(4000);
        home_page.ARM_AWAY.click();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(2000);
        logger.info("AB319_06 Pass: Verified that panel will arm once sensor is closed from step AB319_05");
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(2000);
        deleteFromPrimary(4);
        Thread.sleep(2000);
        deleteFromPrimary(5);
        Thread.sleep(2000);
        deleteFromPrimary(6);
        Thread.sleep(2000);
        deleteFromPrimary(7);
        Thread.sleep(2000);
        deleteFromPrimary(20);
        Thread.sleep(2000);
        deleteFromPrimary(22);
        Thread.sleep(2000);
        deleteFromPrimary(23);
        Thread.sleep(8000);
    }

    @Test /*** Disarm mode/Enabled by Default ***/(priority = 1)
    public void AB319_01() throws Exception {
        create_report("AB319_01*");
        log.log(LogStatus.INFO, ("*AB319_01* Verify that Auto Bypass is enabled"));
        logger.info("AB319_01: Verify that Auto Bypass is enabled");
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        servcall.get_AUTO_BYPASS();
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass: Auto Bypass is enabled ***");
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass:  Normal Entry Delay time is set ***");
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(2000);
        log.log(LogStatus.PASS, "Pass:  Normal Exit Delay time is set ***");
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        log.log(LogStatus.PASS, "Pass:  Long Entry Delay time is set ***");
        Thread.sleep(2000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        log.log(LogStatus.PASS, "Pass:  Long Exit Delay time is set ***");
        Thread.sleep(2000);
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
        report.flush();}

}
