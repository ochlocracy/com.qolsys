package sensors;

import panel.ContactUs;
import panel.HomePage;
import utils.Log;
import utils.Setup1;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_DW_Grid_ArmAway {
    Setup1 s = new Setup1();
    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Door-Window sensors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int Open = 1;
    private int Close = 0;

    private int Normal_Entry_Delay = 15;
    private int Long_Exit_Delay =20;

    public Smoke_Test_DW_Grid_ArmAway() throws Exception {}

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }

    @Parameters({"UDID_"})
    @Test
    public void Test1 (String UDID_) throws Exception {
        MySensors.read_sensors_from_csv();
        HomePage home_page = PageFactory.initElements(s.getDriver(), HomePage.class);
        ContactUs contact_us = PageFactory.initElements(s.getDriver(), ContactUs.class);
        SensorsList list = PageFactory.initElements(s.getDriver(), SensorsList.class);

        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 10 -> Expected result = 30 sec delay -> Alarm");
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Door4);
        s.verify_status_open();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 12 -> Expected result = 100 sec delay -> Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Door5);
        s.verify_status_open();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 13 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Open);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_displayed(UDID_, list.Door6);
        s.verify_status_open();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 14 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Open);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Door9);
        s.verify_status_open();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 16 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Open);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_displayed(UDID_, list.Door7);
        s.verify_status_open();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 8 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Open);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_displayed(UDID_, list.Door2);
        s.verify_status_open();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 9 -> Expected result = 30 sec delay -> Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Door3);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Open);
        TimeUnit.SECONDS.sleep(3);
        s.verify_armaway(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
        home_page.ArwAway_State.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************TAMPER********************");
        logger.info("ArmAway mode tampering sensors group 10 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_tampered(list.Door4);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 12 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_tampered(list.Door5);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 13 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay) ;
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_tampered(list.Door6);
        s.verify_status_tampered();
        s.verify_in_alarm() ;
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 14 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Door9);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 16 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_tampered(list.Door7);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> Expected result = ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        s.verify_armaway(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25,Close);
       home_page.ArwAway_State.click();
       s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 8 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_tampered(list.Door2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering sensors group 9 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_tampered(list.Door3);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9,Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);
        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors...");
        MySensors.deleteAllSensors1(UDID_);
}
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        s.getDriver().quit();
    }
}