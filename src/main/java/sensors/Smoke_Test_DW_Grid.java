package sensors;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import utils.Log;
import utils.Setup1;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_DW_Grid  {
    Setup1 s = new Setup1();
    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Door-Window sensors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int Open = 1;
    private int Close = 0;

    private int Normal_Entry_Delay = 15;
    private int Long_Exit_Delay =20;

    public Smoke_Test_DW_Grid() throws Exception {}

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters ({"UDID_"})
    @Test
    public void a_Disarm_Mode(String UDID_) throws Exception {
        SensorsList list = PageFactory.initElements(s.getDriver(), SensorsList.class);

        logger.info("Current software version: " + s.Software_Version());
        MySensors.read_sensors_from_csv();
        TimeUnit.SECONDS.sleep(2);
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode tripping sensors group 10, 12, 13, 14, 16, 25 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Open);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Open);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Open);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Open);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Open);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_displayed1(UDID_, list.Door4);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_displayed1(UDID_, list.Door5);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_displayed1(UDID_, list.Door6);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_displayed1(UDID_, list.Door7);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_displayed1(UDID_, list.Door8);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_displayed1(UDID_, list.Door9);
        TimeUnit.SECONDS.sleep(3);
        s.verify_disarm(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        TimeUnit.SECONDS.sleep(7);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping sensors group 8 -> Expected result= Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Open);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_displayed1(UDID_, list.Door2);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping sensors group 9 -> Expected result= 30 sec delay -> Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed1(UDID_, list.Door3);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("**********************TAMPER**********************");
        logger.info("Disarm mode tamper sensors group 10, 12, 13, 14, 16, 25 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered1(UDID_, list.Door4);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered1(UDID_, list.Door5);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered1(UDID_, list.Door6);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered1(UDID_, list.Door7);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered1(UDID_, list.Door8);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered1(UDID_, list.Door9);
        TimeUnit.SECONDS.sleep(1);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tamper sensors group 8 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_tampered1(UDID_, list.Door2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tamper sensors group 9 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered1(UDID_, list.Door3);
        TimeUnit.SECONDS.sleep(1);
        s.verify_status_tampered();
        TimeUnit.SECONDS.sleep(1);
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        logger.info("Deleting all sensors...");
        MySensors.deleteAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(2);
    }

//    @Parameters ({"UDID_"})
//    @Test
//    public void c_Armed_Away_Mode(String UDID_) throws Exception {
//
//        MySensors.read_sensors_from_csv();
//        Home_Page home_page = PageFactory.initElements(s.getDriver(), Home_Page.class);
//        Contact_Us contact_us = PageFactory.initElements(s.getDriver(), Contact_Us.class);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 10 -> Expected result = 30 sec delay -> Alarm");
//        // autostayPref();
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Open);
//        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
//        WebElement Door4 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 4']");
//        s.verifySensorIsDisplayed(UDID_, Door4);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 12 -> Expected result = 100 sec delay -> Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Open);
//        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
//        WebElement Door5 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
//        s.verifySensorIsDisplayed(UDID_, Door5);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 13 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Open);
//        TimeUnit.SECONDS.sleep(3);
//        WebElement Door6 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
//        s.verifySensorIsDisplayed(UDID_, Door6);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 14 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Open);
//        TimeUnit.SECONDS.sleep(5);
//        WebElement Door9 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
//        s.verifySensorIsDisplayed(UDID_, Door9);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 16 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Open);
//        TimeUnit.SECONDS.sleep(3);
//        WebElement Door7 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
//        s.verifySensorIsDisplayed(UDID_, Door7);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 8 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Open);
//        TimeUnit.SECONDS.sleep(3);
//        WebElement Door2 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
//        s.verifySensorIsDisplayed(UDID_, Door2);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 9 -> Expected result = 30 sec delay -> Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Open);
//        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
//        WebElement Door3 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 3']");
//        s.verifySensorIsDisplayed(UDID_, Door3);
//        s.verifyStatusOpen();
//        s.verifyInAlarm();
//        TimeUnit.SECONDS.sleep(5);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Open);
//        TimeUnit.SECONDS.sleep(3);
//        s.verifyArmaway(UDID_);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
//        home_page.ArwAway_State.click();
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(3);
//        //    autostayPref();
//
//        logger.info("********************TAMPER********************");
//        logger.info("ArmAway mode tampering sensors group 10 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
//        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
//        s.verifySensorIsTampered(Door4);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 12 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
//        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
//        s.verifySensorIsTampered(Door5);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 13 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
//        TimeUnit.SECONDS.sleep(3);
//        s.verifySensorIsTampered(Door6);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 14 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
//        TimeUnit.SECONDS.sleep(5);
//        s.verifySensorIsTampered(Door9);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 16 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
//        TimeUnit.SECONDS.sleep(3);
//        s.verifySensorIsTampered(Door7);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
//        TimeUnit.SECONDS.sleep(3);
//        s.verifyArmaway(UDID_);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
//        home_page.ArwAway_State.click();
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 8 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
//        TimeUnit.SECONDS.sleep(3);
//        s.verifySensorIsTampered(Door2);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        logger.info("********************************************************");
//        logger.info("ArmAway mode tampering sensors group 9 -> Expected result = Instant Alarm");
//        s.ARM_AWAY(Long_Exit_Delay);
//        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
//        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
//        s.verifySensorIsTampered(Door3);
//        s.verifyStatusTampered();
//        s.verifyInAlarm();
//        TimeUnit.SECONDS.sleep(5);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
//        s.enterDefaultUserCode();
//        TimeUnit.SECONDS.sleep(5);
//
//        contact_us.acknowledge_all_alerts();
//        logger.info("Deleting all sensors...");
//        MySensors.deleteAllSensors();
 //   }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        s.getDriver().quit();
    }
}