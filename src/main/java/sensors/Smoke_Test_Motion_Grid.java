package sensors;

import panel.ContactUs;
import panel.HomePage;
import utils.Setup1;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Motion_Grid {
    Setup1 s = new Setup1();
    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Motion sensor";
    Logger logger = Logger.getLogger(page_name);

    private int Activate = 1;
    private int Idle =0;

    private int Normal_Entry_Delay = 15;
    private int Long_Exit_Delay =20;

    public Smoke_Test_Motion_Grid() throws Exception {}

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters ({"UDID_"})
    @Test
    public void Test1(String UDID_) throws Exception {
        HomePage home_page = PageFactory.initElements(s.getDriver(), HomePage.class);
        ContactUs contact_us = PageFactory.initElements(s.getDriver(), ContactUs.class);
        SensorsList list = PageFactory.initElements(s.getDriver(), SensorsList.class);

        logger.info("Current software version: " + s.Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode tripping sensors group 15, 17, 20, 25, 35 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(4);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 15 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        TimeUnit.SECONDS.sleep(4);
        s.verify_sensor_is_displayed(UDID_, list.Motion10);
        s.verify_status_activated();
        s.verify_in_alarm();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 17, 20, 25 -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        TimeUnit.SECONDS.sleep(4);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(5);
        home_page.DISARM.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 35 -> Expected result = 30 sec delay -> Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Motion14);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_activated();
        s.verify_in_alarm();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 15 -> Expected result = Instant Alarm");
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        TimeUnit.SECONDS.sleep(4);
        s.verify_sensor_is_displayed(UDID_, list.Motion10);
        s.verify_status_activated();
        s.verify_in_alarm();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 17 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Motion11);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_activated();
        s.verify_in_alarm();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 20 -> Expected result = 30 sec delay -> Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Motion12);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_activated();
        s.verify_in_alarm();
        s.enter_default_user_code();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        TimeUnit.SECONDS.sleep(4);
        s.verify_armaway(UDID_);
        home_page.ArwAway_State.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 35 -> Expected result = 30 sec delay -> Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        s.verify_sensor_is_displayed(UDID_, list.Motion14);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_activated();
        s.verify_in_alarm();
        s.enter_default_user_code();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tamper sensors group 15, 17, 20, 25, 35 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 20);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        s.verify_sensor_is_tampered(list.Motion10);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered(list.Motion11);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered(list.Motion12);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered(list.Motion13);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered(list.Motion14);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Idle);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 15 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Motion10);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 17, 20, 25 -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 20);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Motion11);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered(list.Motion12);
        TimeUnit.SECONDS.sleep(1);
        s.verify_sensor_is_tampered(list.Motion13);
        TimeUnit.SECONDS.sleep(2);
        s.verify_armstay(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Idle);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        TimeUnit.SECONDS.sleep(1);
        home_page.DISARM.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 35 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(4);
        s.verify_sensor_is_tampered(list.Motion14);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 15 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Motion10);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 17 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Motion11);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 25 -> Expected result = ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        home_page.ArwAway_State.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 35 -> Expected result =Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Motion14);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);
        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors");
        MySensors.deleteAllSensors1(UDID_);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}