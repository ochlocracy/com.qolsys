package sensors;

import panel.Contact_Us;
import panel.Home_Page;
import utils.Setup1;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Tilt_Grid {
    Setup1 s = new Setup1();
    String page_name = "Smoke Test Tilt";
    Logger logger = Logger.getLogger(page_name);
    Sensors MySensors = new Sensors();
    private int Open = 1;
    private int Close = 0;

    private int Normal_Entry_Delay = 15;
    private int Long_Exit_Delay = 17;

    public Smoke_Test_Tilt_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_"})
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Test1(String UDID_) throws Exception {
        Home_Page home_page = PageFactory.initElements(s.getDriver(), Home_Page.class);
        Contact_Us contact_us = PageFactory.initElements(s.getDriver(), Contact_Us.class);
        SensorsList list = PageFactory.initElements(s.getDriver(), SensorsList.class);

        logger.info("Current software version: " + s.Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TILT***********************");
        logger.info("Disarm mode tripping Tilt group 10, 12, 25 -> Expected result = system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Open);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Open);
        TimeUnit.SECONDS.sleep(4);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Tilt group 10 -> Expected result = 30 sec delay -> Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Tilt21);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Tilt group 12 -> Expected result = 100 sec delay -> Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Tilt22);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Tilt group 25 -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Tilt23);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Tilt group 10 -> Expected result = 30 sec delay -> Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Tilt21);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Tilt group 12 -> Expected result = 100 sec delay -> Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Tilt22);
        s.verify_status_open();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Tilt group 25 -> Arm Away");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.ArwAway_State.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper tilt group 10, 12, 25 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 10);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 12);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Tilt21);
        s.verify_sensor_is_tampered(list.Tilt22);
        s.verify_sensor_is_tampered(list.Tilt23);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper tilt group 10 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 10);
        TimeUnit.SECONDS.sleep(3);
        s.verify_sensor_is_tampered(list.Tilt21);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper tilt group 12 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 12);
        TimeUnit.SECONDS.sleep(6);
        s.verify_sensor_is_tampered(list.Tilt22);
        TimeUnit.SECONDS.sleep(5);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper tilt group 25 -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Tilt23);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper tilt group 10 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 10);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Tilt21);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper tilt group 12 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 12);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Tilt22);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper tilt group 25 -> Expected result = ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.ArwAway_State.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);
        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors");
        MySensors.deleteAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(3);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}