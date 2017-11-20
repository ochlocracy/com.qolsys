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

public class Smoke_Test_Shock_Grid {
    Setup1 s = new Setup1();
    String page_name = "Smoke Test Glassbreak";
    Logger logger = Logger.getLogger(page_name);
    Sensors MySensors = new Sensors();
    private int Activate = 1;
    private int Idle = 0;

    private int Long_Exit_Delay =17;

    public Smoke_Test_Shock_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_"})
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);}
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
        TimeUnit.SECONDS.sleep(3);

        logger.info("***********************SHOCK_OTHER***********************");
        logger.info("Disarm mode tripping Shock group 13 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(4);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 13 -> Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Shock35);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 13 -> Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Shock35);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping Shock group 17 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 17 -> ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 17 -> Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Shock36);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper Shock_other group 13, 17 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 13);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Shock35);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_tampered(list.Shock36);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_other group 13 -> Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 13);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Shock35);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_other group 17 -> ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Shock36);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_other group 13 -> Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 13);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Shock35);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_other group 17 -> Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Shock36);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);


        logger.info("***********************SHOCK_IQ***********************");
        logger.info("Disarm mode tripping Shock group 13 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 13 -> Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.IQShock24);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 13 -> Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.IQShock24);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping Shock group 17 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 17 -> ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 17 -> Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.IQShock25);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper Shock_IQ group 13, 17 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.IQShock24);
        TimeUnit.SECONDS.sleep(2);
        s.verify_sensor_is_tampered(list.IQShock25);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_IQ group 13 -> Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.IQShock24);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_IQ group 17 -> ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.IQShock25);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(4);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_IQ group 13 -> Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.IQShock24);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_IQ group 17 -> Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.IQShock25);
        s.verify_status_tampered();
        s.verify_in_alarm();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

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