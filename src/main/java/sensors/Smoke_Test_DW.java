package sensors;

import panel.Contact_Us;
import panel.Home_Page;
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

    public Smoke_Test_DW() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void a_Disarm_Mode() throws Exception {

        logger.info("Current software version: " + Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode tripping sensors group 10, 12, 13, 14, 16, 25 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door4 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 4']");
        verify_sensor_is_displayed(Door4);
        WebElement Door5 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        verify_sensor_is_displayed(Door5);
        WebElement Door6 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        verify_sensor_is_displayed(Door6);
        WebElement Door7 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        verify_sensor_is_displayed(Door7);
        WebElement Door8 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 8']");
        verify_sensor_is_displayed(Door8);
        WebElement Door9 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        verify_sensor_is_displayed(Door9);
        verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping sensors group 8 -> Expected result= Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door2 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
        verify_sensor_is_displayed(Door2);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping sensors group 9 -> Expected result= 30 sec delay -> Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door3 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 3']");
        verify_sensor_is_displayed(Door3);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("**********************TAMPER**********************");
        logger.info("Disarm mode tamper sensors group 10, 12, 13, 14, 16, 25 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door4);
        verify_sensor_is_tampered(Door5);
        verify_sensor_is_tampered(Door6);
        verify_sensor_is_tampered(Door7);
        verify_sensor_is_tampered(Door8);
        verify_sensor_is_tampered(Door9);
        verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);

        logger.info("********************************************************");
        logger.info("Disarm mode tamper sensors group 8 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door2);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tamper sensors group 9 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door3);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void b_Armed_Stay_Mode () throws Exception {

        MySensors.read_sensors_from_csv();
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 10 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door4 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 4']");
        verify_sensor_is_displayed(Door4);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 12 -> Expected result = 100 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Open);
        TimeUnit.SECONDS.sleep(Long_Exit_Delay);
        WebElement Door5 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        verify_sensor_is_displayed(Door5);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 13 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door6 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        verify_sensor_is_displayed(Door6);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 14 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door9 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        verify_sensor_is_displayed(Door9);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 16, 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door7 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        verify_sensor_is_displayed(Door7);
        TimeUnit.SECONDS.sleep(2);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 8 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door2 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
        verify_sensor_is_displayed(Door2);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 9 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door3 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 3']");
        verify_sensor_is_displayed(Door3);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 10 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door4);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 12 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door5);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 13 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door6);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 14 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        TimeUnit.SECONDS.sleep(5);
        verify_sensor_is_tampered(Door9);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 16 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        TimeUnit.SECONDS.sleep(5);
        verify_sensor_is_tampered(Door7);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door8 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 8']");
        verify_sensor_is_tampered(Door8);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 8 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door2);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 9 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door3);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void c_Armed_Away_Mode() throws Exception {

        MySensors.read_sensors_from_csv();
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        Contact_Us contact_us = PageFactory.initElements(driver, Contact_Us.class);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 10 -> Expected result = 30 sec delay -> Alarm");
        // autostayPref();
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door4 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 4']");
        verify_sensor_is_displayed(Door4);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 12 -> Expected result = 100 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door5 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        verify_sensor_is_displayed(Door5);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 13 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door6 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        verify_sensor_is_displayed(Door6);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 14 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door9 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        verify_sensor_is_displayed(Door9);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 16 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door7 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        verify_sensor_is_displayed(Door7);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 8 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door2 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
        verify_sensor_is_displayed(Door2);
        verify_status_open();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 9 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door3 = driver.findElementByXPath("//android.widget.TextView[@text='Door/Window 3']");
        verify_sensor_is_displayed(Door3);
        verify_status_open();
        verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Open);
        TimeUnit.SECONDS.sleep(3);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
        home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);
        //    autostayPref();

        logger.info("********************TAMPER********************");
        logger.info("ArmAway mode tampering sensors group 10 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verify_sensor_is_tampered(Door4);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 12 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verify_sensor_is_tampered(Door5);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 13 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door6);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 14 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        TimeUnit.SECONDS.sleep(5);
        verify_sensor_is_tampered(Door9);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 16 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door7);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
       home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 8 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Door2);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 9 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verify_sensor_is_tampered(Door3);
        verify_status_tampered();
        verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors...");
        MySensors.deleteAllSensors();
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}