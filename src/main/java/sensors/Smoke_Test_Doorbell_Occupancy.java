package sensors;

import panel.ContactUs;
import panel.HomePage;
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

public class Smoke_Test_Doorbell_Occupancy extends Setup {

    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Doorbell and Occupancy sensors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int Ring = 1;
    private int Occupied =1;
    private int Idle = 0;
    private int Vacant = 0;

    private int Long_Exit_Delay =16;

 public Smoke_Test_Doorbell_Occupancy() throws Exception {
 }

 @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

   @Test
    public void Test1() throws Exception {

       HomePage home_page = PageFactory.initElements(driver, HomePage.class);
       ContactUs contact_us = PageFactory.initElements(driver, ContactUs.class);

        logger.info("Current software version: " + Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode trigger doorbell group 25 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Ring);
        TimeUnit.SECONDS.sleep(3);
        verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("Disarm mode trigger occupancy sensor group 25 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Vacant);
        TimeUnit.SECONDS.sleep(3);
        verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Occupied);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode trigger doorbell group 25 -> ArmStay");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Ring);
        TimeUnit.SECONDS.sleep(3);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode trigger occupancy sensor group 25 -> ArmStay");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Vacant);
        TimeUnit.SECONDS.sleep(3);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Occupied);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmAway mode trigger doorbell group 25 -> ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Ring);
        TimeUnit.SECONDS.sleep(3);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmAway mode trigger occupancy sensor group 25 -> ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Vacant);
        TimeUnit.SECONDS.sleep(3);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.occupancy_zones, 25,Occupied);
        home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("*********************TAMPER*********************");
        logger.info("Disarm mode tamper doorbell group 25 -> Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.doorbell_zones, 25);
        WebElement doorbell37 = driver.findElementByXPath("//android.widget.TextView[@text='Door Bell 37']");
        verify_sensor_is_tampered(doorbell37);
        verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper doorbell group 25 -> ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.doorbell_zones, 25);
        verify_sensor_is_tampered(doorbell37);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper doorbell group 25 -> ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.doorbell_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(doorbell37);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.doorbell_zones, 25,Idle);
        home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);

        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors");
        MySensors.deleteAllSensors();
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
