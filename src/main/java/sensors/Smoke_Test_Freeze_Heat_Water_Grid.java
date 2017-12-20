package sensors;

import panel.ContactUs;
import panel.EmergencyPage;
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

public class Smoke_Test_Freeze_Heat_Water_Grid {
    Setup1 s = new Setup1();
    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Freeze, Heat, Water sensors";
    Logger logger = Logger.getLogger(page_name);
    private int Activate = 1;
    private int Idle = 0;

    private int Long_Exit_Delay = 20;


    public Smoke_Test_Freeze_Heat_Water_Grid() throws Exception {}

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
        EmergencyPage emergency = PageFactory.initElements(s.getDriver(), EmergencyPage.class);
        SensorsList list = PageFactory.initElements(s.getDriver(), SensorsList.class);

        logger.info("Current software version: " + s.Software_Version());
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(3);
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode tripping Freeze group 52 -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Freeze27);
        Thread.sleep(4000);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        Thread.sleep(2000);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Freeze group 52 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Freeze27);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Freeze group 52 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Activate);
        TimeUnit.SECONDS.sleep(6);
        s.verify_sensor_is_displayed(UDID_, list.Freeze27);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tampering Freeze group 52  -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.freeze_zones, 52);
        TimeUnit.SECONDS.sleep(7);
        s.verify_sensor_is_tampered(list.Freeze27);
        TimeUnit.SECONDS.sleep(4);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tampering Freeze group 52  -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.freeze_zones, 52);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Freeze27);
        TimeUnit.SECONDS.sleep(2);
        s.verify_armstay(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(2);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering Freeze group 52  -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.freeze_zones, 52);
        TimeUnit.SECONDS.sleep(6);
//        s.verifySensorIsTampered(list.Freeze27);
 //       s.verifyStatusTampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************HEAT********************");
        logger.info("Disarm mode tripping Heat group 26 -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Activate);
        TimeUnit.SECONDS.sleep(5);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in Disarm mode");}
        else { s.take_screenshot();
            logger.info("Failed: Fire emergency is NOT sent in Disarm mode");}
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        TimeUnit.SECONDS.sleep(5);
        emergency.Cancel_Emergency.click();
        TimeUnit.SECONDS.sleep(2);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Heat group 26 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Activate);
        TimeUnit.SECONDS.sleep(5);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in ArmStay mode");}
        else { s.take_screenshot();
            logger.info("Failed: Fire emergency is NOT sent in ArmStay mode");}
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        TimeUnit.SECONDS.sleep(2);
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Heat group 26 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Activate);
        TimeUnit.SECONDS.sleep(5);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in ArmAway mode");}
        else { s.take_screenshot();
            logger.info("Failed: Fire emergency is NOT sent in ArmAway mode");}
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        TimeUnit.SECONDS.sleep(2);
        emergency.Cancel_Emergency.click();
        TimeUnit.SECONDS.sleep(2);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tampering Heat group 26  -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.heat_zones, 26);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.SmokeM26);
        TimeUnit.SECONDS.sleep(2);
        s.verify_disarm(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tampering Heat group 26  -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.heat_zones, 26);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.SmokeM26);
        TimeUnit.SECONDS.sleep(2);
        s.verify_armstay(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(2);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering Heat group 26  -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.heat_zones, 26);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.SmokeM26);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************WATER********************");
        logger.info("Disarm mode tripping Water_flood group 38 -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Water18);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Water_flood group 38 -> Expected result = Instant Alarm");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Water18);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Water_flood group 38 -> Expected result = Instant Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Activate);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_displayed(UDID_, list.Water18);
        TimeUnit.SECONDS.sleep(3);
        s.verify_status_alarmed();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tampering Water group 38 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.water_flood_zones, 38);
        TimeUnit.SECONDS.sleep(8);
        s.verify_sensor_is_tampered(list.Water18);
        TimeUnit.SECONDS.sleep(4);
        s.verify_disarm(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tampering Water group 38 -> Expected result = ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.water_flood_zones, 38);
        TimeUnit.SECONDS.sleep(6);
        s.verify_sensor_is_tampered(list.Water18);
        TimeUnit.SECONDS.sleep(3);
        s.verify_armstay(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(2);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering Water group 38 -> Expected result = Alarm");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.water_flood_zones, 38);
        TimeUnit.SECONDS.sleep(5);
        s.verify_sensor_is_tampered(list.Water18);
        TimeUnit.SECONDS.sleep(2);
        s.verify_status_tampered();
        s.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
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