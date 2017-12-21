package sensors;

import panel.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Log;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Smoke_CO extends Setup {

    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Smoke and CO Detectors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    int Idle = 0;
    int Alarm = 1;

    private int Long_Exit_Delay =16;

    public Smoke_Test_Smoke_CO() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Test1() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact_us = PageFactory.initElements(driver, ContactUs.class);
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        logger.info("Current software version: "+ softwareVersion());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm activate Smoke Sensor -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Alarm);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Idle);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in Disarm mode");
        }
        else { takeScreenshot();
            logger.info("Failed: Fire emergency is NOT sent in Disarm mode");
        }
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay activate Smoke Sensor -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Alarm);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Idle);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in ArmStay mode");
        }
        else { takeScreenshot();
            logger.info("Failed: Fire emergency is NOT sent in ArmStay mode");
        }
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway activate Smoke Sensor -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Alarm);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Idle);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in ArmAway mode");
        }
        else { takeScreenshot();
            logger.info("Failed: Fire emergency is NOT sent in ArmAway mode");
        }
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("**********************TAMPER**********************");
        logger.info("Disarm state tamper Smoke Sensor -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26);
        TimeUnit.SECONDS.sleep(3);
        WebElement smoke_detector15 = driver.findElement(By.xpath("//android.widget.TextView[@text='Smoke Detector 15']"));
        verifySensorIsTampered(smoke_detector15);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay state tamper Smoke Sensor -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(smoke_detector15);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway state tamper Smoke Sensor -> Expected result = Instant Alarm ");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(smoke_detector15);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.smoke_detector_zones, 26,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);


        logger.info("********************CO SENSOR********************");
        logger.info("Disarm activate CO Sensor -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Alarm);
        TimeUnit.SECONDS.sleep(2);
        WebElement CO_Detector16 = driver.findElementByXPath("//android.widget.TextView[@text='CO Detector 16']");
        verifySensorIsDisplayed(CO_Detector16);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay activate CO Sensor -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Alarm);
        TimeUnit.SECONDS.sleep(2);
        verifySensorIsDisplayed(CO_Detector16);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway activate CO Sensor -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Alarm);
        TimeUnit.SECONDS.sleep(2);
        verifySensorIsDisplayed(CO_Detector16);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("**********************TAMPER**********************");
        logger.info("Disarm state tamper CO Sensor -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.co_detector_zones, 34);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay state tamper CO Sensor -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.co_detector_zones, 34);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(CO_Detector16);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway state tamper CO Sensor -> Expected result = Instant Alarm ");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.co_detector_zones, 34);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(CO_Detector16);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.co_detector_zones, 34,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors");
        MySensors.deleteAllSensors();
        TimeUnit.SECONDS.sleep(5);
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}