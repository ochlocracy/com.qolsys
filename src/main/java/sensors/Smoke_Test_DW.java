package sensors;

import panel.ContactUs;
import panel.HomePage;
import utils.ConfigProps;
import utils.Log;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_DW extends Setup {
    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Door-Window sensors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int Open = 1;
    private int Close = 0;

    private int Normal_Entry_Delay = 13;
    private int Long_Exit_Delay =16;

    public Smoke_Test_DW() throws Exception {
        ConfigProps.init();
    }

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setupDriver(ConfigProps.primary,"http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void a_Disarm_Mode() throws Exception {

        logger.info("Current software version: " + softwareVersion());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell logcat -v time -s valgrind >> /sdcard/Valgrindlog.txt");
//        TimeUnit.SECONDS.sleep(5);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/meminfo.txt");
//        Thread.sleep(1000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys meminfo >> /sdcard/meminfo.txt");
//        Thread.sleep(2000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys batterystats >> /sdcard/batteryinfo.txt");
//        Thread.sleep(2000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >>  /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys cpuinfo >> /sdcard/cpuinfo.txt");
//
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 1 i32 0 ");

        logger.info("Disarm mode tripping sensors group 10, 12, 13, 14, 16, 25 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door4 = driver.findElementByXPath("//android.widget.TextView[@text='DoorWindow 4']");
        verifySensorIsDisplayed(Door4);
        WebElement Door5 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        verifySensorIsDisplayed(Door5);
        WebElement Door6 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        verifySensorIsDisplayed(Door6);
        WebElement Door7 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        verifySensorIsDisplayed(Door7);
        WebElement Door8 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 8']");
        verifySensorIsDisplayed(Door8);
        WebElement Door9 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        verifySensorIsDisplayed(Door9);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        TimeUnit.SECONDS.sleep(5);

//        logger.info("********************************************************");
//        logger.info("Disarm mode tripping sensors group 8 -> Expected result= Instant Alarm");
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Open);
//        TimeUnit.SECONDS.sleep(5);
//        WebElement Door2 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
//        verifySensorIsDisplayed(Door2);
//        verifyStatusOpen();
//        verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
//        enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping sensors group 9 -> Expected result= 30 sec delay -> Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door3 = driver.findElementByXPath("//android.widget.TextView[@text='DoorWindow 3']");
        verifySensorIsDisplayed(Door3);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("**********************TAMPER**********************");
        logger.info("Disarm mode tamper sensors group 10, 12, 13, 14, 16, 25 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(Door4);
        verifySensorIsTampered(Door5);
        verifySensorIsTampered(Door6);
        verifySensorIsTampered(Door7);
        verifySensorIsTampered(Door8);
        verifySensorIsTampered(Door9);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
//
//        logger.info("********************************************************");
//        logger.info("Disarm mode tamper sensors group 8 -> Expected result -> Instant Alarm");
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
//        TimeUnit.SECONDS.sleep(5);
//        verifySensorIsTampered(Door2);
//        verifyStatusTampered();
//        verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
//        enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tamper sensors group 9 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door3);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 0 i32 0");
//
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys meminfo >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys batterystats >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys cpuinfo >> /sdcard/cpuinfo.txt");

    }

    @Test
    public void b_Armed_Stay_Mode () throws Exception {

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/meminfo.txt");
//        Thread.sleep(1000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys meminfo >> /sdcard/meminfo.txt");
//        Thread.sleep(2000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys batterystats >> /sdcard/batteryinfo.txt");
//        Thread.sleep(2000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >>  /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys cpuinfo >> /sdcard/cpuinfo.txt");


        MySensors.read_sensors_from_csv();
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 10 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door4 = driver.findElementByXPath("//android.widget.TextView[@text='DoorWindow 4']");
        verifySensorIsDisplayed(Door4);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 1 i32 0 ");

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 12 -> Expected result = 100 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Open);
        TimeUnit.SECONDS.sleep(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door5 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        verifySensorIsDisplayed(Door5);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 13 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door6 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        verifySensorIsDisplayed(Door6);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 14 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door9 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        verifySensorIsDisplayed(Door9);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

 //       rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 0 i32 0");

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 16, 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door7 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        verifySensorIsDisplayed(Door7);
        TimeUnit.SECONDS.sleep(2);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        logger.info("********************************************************");
//        logger.info("ArmStay mode tripping sensors group 8 -> Expected result = Instant Alarm");
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(3);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Open);
//        TimeUnit.SECONDS.sleep(5);
//        WebElement Door2 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
//        verifySensorIsDisplayed(Door2);
//        verifyStatusOpen();
//        verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
//        enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 9 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door3 = driver.findElementByXPath("//android.widget.TextView[@text='DoorWindow 3']");
        verifySensorIsDisplayed(Door3);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 10 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door4);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 12 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door5);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 13 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door6);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 14 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(Door9);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 16 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(Door7);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door8 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 8']");
        verifySensorIsTampered(Door8);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        logger.info("********************************************************");
//        logger.info("ArmStay mode tamper sensors group 8 -> Expected result = Instant Alarm");
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(3);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
//        TimeUnit.SECONDS.sleep(3);
//        verifySensorIsTampered(Door2);
//        verifyStatusTampered();
//        verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
//        enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 9 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door3);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys meminfo >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys batterystats >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys cpuinfo >> /sdcard/cpuinfo.txt");
    }

    @Test
    public void c_Armed_Away_Mode() throws Exception {

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/meminfo.txt");
//        Thread.sleep(1000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys meminfo >> /sdcard/meminfo.txt");
//        Thread.sleep(2000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys batterystats >> /sdcard/batteryinfo.txt");
//        Thread.sleep(2000);
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******Sensors*BEGINNING***** >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >>  /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys cpuinfo >> /sdcard/cpuinfo.txt");


        MySensors.read_sensors_from_csv();
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact_us = PageFactory.initElements(driver, ContactUs.class);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 1 i32 0 ");

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 10 -> Expected result = 30 sec delay -> Alarm");
        // autostayPref();
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door4 = driver.findElementByXPath("//android.widget.TextView[@text='DoorWindow 4']");
        verifySensorIsDisplayed(Door4);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 12 -> Expected result = 100 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door5 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        verifySensorIsDisplayed(Door5);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 13 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door6 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        verifySensorIsDisplayed(Door6);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 14 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door9 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        verifySensorIsDisplayed(Door9);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 16 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door7 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        verifySensorIsDisplayed(Door7);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 0 i32 0 ");

//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 8 -> Expected result = Instant Alarm");
//        ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Open);
//        TimeUnit.SECONDS.sleep(5);
//        WebElement Door2 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
//        verifySensorIsDisplayed(Door2);
//        verifyStatusOpen();
//        verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
//        enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 9 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door3 = driver.findElementByXPath("//android.widget.TextView[@text='DoorWindow 3']");
        verifySensorIsDisplayed(Door3);
        verifyStatusOpen();
        verifyInAlarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Open);
        TimeUnit.SECONDS.sleep(5);
        verifyArmaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
        DISARM();
        TimeUnit.SECONDS.sleep(3);
        //    autostayPref();

        logger.info("********************TAMPER********************");
        logger.info("ArmAway mode tampering sensors group 10 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verifySensorIsTampered(Door4);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

 //       rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 1 i32 0");

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 12 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verifySensorIsTampered(Door5);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 13 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door6);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 14 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(Door9);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 16 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Door7);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verifyArmaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
        DISARM();
        TimeUnit.SECONDS.sleep(5);

//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 8 -> Expected result = Instant Alarm");
//        ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
//        TimeUnit.SECONDS.sleep(3);
//        verifySensorIsTampered(Door2);
//        verifyStatusTampered();
//        verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
//        enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 9 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verifySensorIsTampered(Door3);
        verifyStatusTampered();
        verifyInAlarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys meminfo >> /sdcard/meminfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys batterystats >> /sdcard/batteryinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell echo ******SENSORS*END***** >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell date >> /sdcard/cpuinfo.txt");
//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell dumpsys cpuinfo >> /sdcard/cpuinfo.txt");

        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors...");
 //       MySensors.deleteAllSensors();

//        rt.exec(ConfigProps.adbPath +" -s 8ebdbc39 shell service call qzwaveservice 19 i32 30000 i32 1 i32 2 i32 2 i32 0 i32 0 i32 0");

    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}