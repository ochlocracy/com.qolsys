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

public class Smoke_Test_Motion extends Setup {

    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Motion sensors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    int Activate = 1;
    int Idle =0;

    private int Normal_Entry_Delay = 13;
    private int Long_Exit_Delay =16;

    public Smoke_Test_Motion() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setup_driver("62964b68","http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Test1() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        Contact_Us contact_us = PageFactory.initElements(driver, Contact_Us.class);
        logger.info("Current software version: "+ Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();

        logger.info("Disarm mode tripping sensors group 15, 17, 20, 25, 35 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 15 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        WebElement Motion10 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 10']");
        verify_sensor_is_displayed(Motion10);
        verify_status_activated();
        verify_in_alarm();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 17, 20, 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        verify_armstay();
        TimeUnit.SECONDS.sleep(5);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 35 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Motion14 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 14']");
        verify_sensor_is_displayed(Motion14);
        verify_status_activated();
        verify_in_alarm();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 15 -> Expected result = Instant Alarm");
        // autostayPref();
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        verify_sensor_is_displayed(Motion10);
        verify_status_activated();
        verify_in_alarm();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 17 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        WebElement Motion11 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 11']");
        verify_sensor_is_displayed(Motion11);
        verify_status_activated();
        verify_in_alarm();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 20 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Motion12 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 12']");
        verify_sensor_is_displayed(Motion12);
        verify_status_activated();
        verify_in_alarm();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);

        verify_armaway();
        home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 35 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verify_sensor_is_displayed(Motion14);
        verify_status_activated();
        verify_in_alarm();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tamper sensors group 15, 17, 20, 25, 35 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 20);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(3);
        verify_disarm();
        WebElement Motion13 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 13']");
        verify_sensor_is_tampered(Motion10);
        verify_sensor_is_tampered(Motion11);
        verify_sensor_is_tampered(Motion12);
        verify_sensor_is_tampered(Motion13);
        verify_sensor_is_tampered(Motion14);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 15 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        verify_sensor_is_tampered(Motion10);
        verify_status_tampered();
        verify_in_alarm();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 17, 20, 25 -> Expected result = ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 20);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        verify_sensor_is_tampered(Motion11);
        verify_sensor_is_tampered(Motion12);
        verify_sensor_is_tampered(Motion13);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        home_page.DISARM.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 35 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Motion14);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 15 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Motion10);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 17 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Motion11);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        home_page.ArwAway_State.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 35 -> Expected result =Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(3);
        verify_sensor_is_tampered(Motion14);
        verify_status_tampered();
        verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

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
