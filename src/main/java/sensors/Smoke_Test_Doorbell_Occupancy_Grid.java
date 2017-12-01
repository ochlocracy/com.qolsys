package sensors;

import panel.ContactUs;
import panel.HomePage;
import utils.Setup1;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Doorbell_Occupancy_Grid {
    Setup1 s = new Setup1();
    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Doorbell and Occupancy sensors";
    Logger logger = Logger.getLogger(page_name);

    private int Ring = 1;
    private int Occupied =1;
    private int Idle = 0;
    private int Vacant = 0;

    private int Long_Exit_Delay =16;

    public Smoke_Test_Doorbell_Occupancy_Grid() throws Exception {}
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

        logger.info("Current software version: " + s.Software_Version());
        s.autoStaySetting();
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode trigger doorbell group 25 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Ring);
        TimeUnit.SECONDS.sleep(4);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        TimeUnit.SECONDS.sleep(4);

        logger.info("********************************************************");
        logger.info("Disarm mode trigger occupancy sensor group 25 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Vacant);
        TimeUnit.SECONDS.sleep(3);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Occupied);
        TimeUnit.SECONDS.sleep(4);

        logger.info("********************************************************");
        logger.info("ArmStay mode trigger doorbell group 25 -> ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(4);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Ring);
        TimeUnit.SECONDS.sleep(4);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode trigger occupancy sensor group 25 -> ArmStay");
        s.ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Vacant);
        TimeUnit.SECONDS.sleep(4);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Occupied);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmAway mode trigger doorbell group 25 -> ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Ring);
        TimeUnit.SECONDS.sleep(4);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.ArwAway_State.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(4);

        logger.info("********************************************************");
        logger.info("ArmAway mode trigger occupancy sensor group 25 -> ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Vacant);
        TimeUnit.SECONDS.sleep(4);
        s.verify_armaway(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Occupied);
        home_page.ArwAway_State.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(4);

        logger.info("*********************TAMPER*********************");
        logger.info("Disarm mode tamper doorbell group 25 -> Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.doorbell_zones, 25);
        TimeUnit.SECONDS.sleep(7);
        WebElement doorbell37 = s.getDriver().findElementByXPath("//android.widget.TextView[@text='Door Bell 37']");
        s.verify_sensor_is_tampered(doorbell37);
        TimeUnit.SECONDS.sleep(2);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(1);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper doorbell group 25 -> ArmStay");
        s.ARM_STAY();
        TimeUnit.SECONDS.sleep(4);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.doorbell_zones, 25);
        TimeUnit.SECONDS.sleep(4);
        s.verify_sensor_is_tampered(doorbell37);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.DISARM.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper doorbell group 25 -> ArmAway");
        s.ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.doorbell_zones, 25);
        TimeUnit.SECONDS.sleep(4);
        s.verify_sensor_is_tampered(doorbell37);
        TimeUnit.SECONDS.sleep(2);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.ArwAway_State.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(2);
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