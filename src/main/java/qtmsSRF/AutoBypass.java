package qtmsSRF;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class AutoBypass extends Setup {
    String page_name = "QTMS: Auto Bypass";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    int two_sec = 2000;
    private String log_path = new String(System.getProperty("user.dir")) + "/log/test.txt";

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

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        Thread.sleep(1000);
    }

    // @Test  /*** Disarm mode/1)a sensor must be paired 2)Auto Bypass Enabled ***/
    public void addSensors() throws Exception {
        addPrimaryCall(3, 10, 6619296, 1);
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
        addPrimaryCall(23, 20, 5570630, 2);
        Thread.sleep(8000);
    }

    @Test(priority = 2)
/*** Disarm mode/1)a sensor must be paired 2)Auto Bypass Enabled ***/
    public void AB319_02_AB319_04() throws Exception {
        Thread.sleep(4000);
        addPrimaryCall(3, 10, 6619296, 1);
        servcall.set_AUTO_BYPASS(01);
        Thread.sleep(2000);
        logger.info("Verify that open sensor will be selected for bypass and at top of sensor list when pushing arm button.");
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        servcall.get_AUTO_BYPASS();
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(8000);
        home_page.DISARM.click();
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_active")).click();
        if (driver.findElement(By.id("com.qolsys:id/uiTVName")).getText().equals("Door/Window 3"))
            logger.info("AB319_2 Pass: Sensor is bypassed");
        else
            logger.info("Fail: Sensor is not selected for bypass");
        Thread.sleep(two_sec);
        driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
        Thread.sleep(two_sec);
        home_page.ARM_AWAY.click();
        // driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(13000);
        logger.info("Verify that bypassed sensors are really bypassed after panel is armed");
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(8000);
        verifyArmaway();
        Thread.sleep(two_sec);
        logger.info("AB319_4 Pass: Sensor is bypassed");
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
        logger.info("Verify that TTS will announce bypassed sensors during arming & ");
        logger.info("Verify that TTS will not announce opening and closing of bypassed sensor while armed");
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("open before ARM AWAY");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(2000);
        servcall.set_AUTO_BYPASS(01);
        Thread.sleep(2000);
        servcall.get_AUTO_BYPASS();
        Thread.sleep(6000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(two_sec);
        deleteLogFile(log_path);
        Thread.sleep(two_sec);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS processing:Door/Window 3, ByPassed"}, 1);
        Thread.sleep(4000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(two_sec);
        //home.ArwAway_State.click();
        //  enterDefaultUserCode();
        logger.info("Verify that TTS will not announce opening and closing of bypassed sensor while armed away");
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(two_sec);
        deleteLogFile(log_path);
        Thread.sleep(two_sec);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS service received Door/Window 3,  ByPassed"}, 1);
        logger.info("No TTS message is into logs");
        Thread.sleep(2000);
        home.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(two_sec);
        logger.info("open before ARM STAY");
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(two_sec);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(two_sec);
        deleteLogFile(log_path);
        Thread.sleep(two_sec);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS processing:Door/Window 3, ByPassed"}, 1);
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(two_sec);
        logger.info("Verify that TTS will not announce opening and closing of bypassed sensor while armed stay");
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(two_sec);
        deleteLogFile(log_path);
        Thread.sleep(two_sec);
        eventLogsGenerating(log_path, new String[]{
                "TtsUtil:: TTS service received Door/Window 3,  ByPassed"}, 1);
        logger.info("No TTS message is into logs");
        Thread.sleep(two_sec);
        DISARM();
        Thread.sleep(2000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 8)
    public void AB319_05_AB319_10_AB319_11() throws Exception {
        logger.info("Verify that Open Sensor Protest will appear if bypass is unselected and system is armed");
        logger.info("Verify that Open Sensor Protest appears when Auto Bypass is dissabled, sensor is opened, and arm is attempted.");
        logger.info("Verify that panel will arm once 'Arming protest' message appears and selected 'ok' ");
        Thread.sleep(10000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        servcall.set_AUTO_BYPASS(0);
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(10000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(two_sec);
        home_page.DISARM.click();
        Thread.sleep(two_sec);
        home_page.ARM_AWAY.click();
        Thread.sleep(two_sec);
        if (driver.findElements(By.id("com.qolsys:id/message")).size() == 1) {
            logger.info("Pass: Open Sensor Pop-up Message Received");
            driver.findElement(By.id("com.qolsys:id/ok")).click();
        } else {
            logger.info("Fail: Open Sensor Pop-up Message Not Received");
            Thread.sleep(two_sec);
            home_page.ARM_AWAY.click();
        }
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(two_sec);
        home_page.ArwAway_State.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 9)
    public void AB319_06() throws Exception {
        logger.info("Verify that panel will arm once sensor is closed from step AB319_05");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        //servcall.set_AUTO_BYPASS(0);
        //  servcall.set_AUTO_STAY(0);
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(4000);
        home_page.DISARM.click();
        Thread.sleep(two_sec);
        home_page.ARM_AWAY.click();
        Thread.sleep(two_sec);
        if (driver.findElements(By.id("com.qolsys:id/message")).size() == 1) {
            logger.info("Pass: Open Sensor Pop-up Message Received");
            sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
            Thread.sleep(5000);
            driver.findElement(By.id("com.qolsys:id/cancel")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
            Thread.sleep(2000);
            home_page.ARM_AWAY.click();
        } else {
            logger.info("Fail: Open Sensor Pop-up Message Not Received");
            Thread.sleep(two_sec);
            driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
            Thread.sleep(2000);
            home_page.ARM_AWAY.click();
        }
        Thread.sleep(14000);
        verifyArmaway();
        Thread.sleep(two_sec);
        logger.info("AB319_06 Pass: Verified that panel will arm once sensor is closed from step AB319_05");
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 4)
    public void AB319_07() throws Exception {
        logger.info("Verify that sensor will not Auto Bypass if sensor is opened after selecting arm button.");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(6000);
        home_page.DISARM.click();
        Thread.sleep(two_sec);
        home_page.ARM_AWAY.click();
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(13000);
        verifyInAlarm();
        Thread.sleep(500);
        logger.info("AB319_07 Pass: Verified that sensor will not Auto Bypass if sensor is opened after selecting arm button.");
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(3);
        Thread.sleep(8000);
    }

    @Test(priority = 5)
    public void AB319_08() throws Exception {
        logger.info("Verify that sensor can be unselected from bypass and system can be armed as normal");
        Thread.sleep(2000);
        addPrimaryCall(3, 10, 6619296, 1);
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        servcall.set_AUTO_BYPASS(1);
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(4000);
        driver.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/img_expand")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/t3_open_tv_active")).click();
        if (driver.findElement(By.id("com.qolsys:id/uiTVName")).getText().equals("Door/Window 1"))
            tap(764, 192);
        else
            logger.info("Fail: Sensor is not selected for bypass");
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(two_sec);
        driver.findElement(By.id("com.qolsys:id/img_collapse")).click();
        Thread.sleep(two_sec);
        driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(two_sec);
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
        Thread.sleep(two_sec);
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
        Thread.sleep(two_sec);
        driver.findElement(By.id("com.qolsys:id/img_arm_away")).click();
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(two_sec);
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
        Thread.sleep(two_sec);
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
    public void AB319_14() throws Exception {
        addSensors();
        Thread.sleep(10000);
        logger.info("Verify that panel can arm away when a entry delay(group10,12) sensor is unselected from bypassed sensor list");
        Thread.sleep(8000);
        //servcall.set_AUTO_BYPASS(1);
        //Thread.sleep(2000);
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
        Thread.sleep(two_sec);
        sensors.primaryCall("65 00 1A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyArmaway();
        Thread.sleep(two_sec);
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
        logger.info("AB319_01: Verify that Auto Bypass is enabled");
        servcall.get_AUTO_BYPASS();
        Thread.sleep(2000);
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(2000);
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(2000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(2000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(2000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
