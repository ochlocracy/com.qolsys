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
        setupDriver("62964b68","http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Test1() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact_us = PageFactory.initElements(driver, ContactUs.class);
        logger.info("Current software version: "+ softwareVersion());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();

        logger.info("Disarm mode tripping sensors group 15, 17, 20, 25, 35 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        verifyDisarm();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 15 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        WebElement Motion10 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 10']");
        verifySensorIsDisplayed(Motion10);
        verifyStatusActivated();
        verifyInAlarm();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 17, 20, 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);
        verifyArmstay();
        TimeUnit.SECONDS.sleep(5);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping sensors group 35 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Motion14 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 14']");
        verifySensorIsDisplayed(Motion14);
        verifyStatusActivated();
        verifyInAlarm();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 15 -> Expected result = Instant Alarm");
        // autostayPref();
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Activate);
        verifySensorIsDisplayed(Motion10);
        verifyStatusActivated();
        verifyInAlarm();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 17 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Activate);
        WebElement Motion11 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 11']");
        verifySensorIsDisplayed(Motion11);
        verifyStatusActivated();
        verifyInAlarm();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 20 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Motion12 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 12']");
        verifySensorIsDisplayed(Motion12);
        verifyStatusActivated();
        verifyInAlarm();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 25 -> ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Activate);

        verifyArmaway();
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping sensors group 35 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Activate);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verifySensorIsDisplayed(Motion14);
        verifyStatusActivated();
        verifyInAlarm();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tamper sensors group 15, 17, 20, 25, 35 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 20);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        WebElement Motion13 = driver.findElementByXPath("//android.widget.TextView[@text='Motion 13']");
        verifySensorIsTampered(Motion10);
        verifySensorIsTampered(Motion11);
        verifySensorIsTampered(Motion12);
        verifySensorIsTampered(Motion13);
        verifySensorIsTampered(Motion14);
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
        verifySensorIsTampered(Motion10);
        verifyStatusTampered();
        verifyInAlarm();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 17, 20, 25 -> Expected result = ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 20);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(Motion11);
        verifySensorIsTampered(Motion12);
        verifySensorIsTampered(Motion13);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 20,Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper sensors group 35 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Motion14);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 15 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 15);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Motion10);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 15,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 17 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Motion11);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 17,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verifyArmaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 25,Idle);
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper sensors group 35 -> Expected result =Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.motion_zones, 35);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(Motion14);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.motion_zones, 35,Idle);
        enterDefaultUserCode();
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
