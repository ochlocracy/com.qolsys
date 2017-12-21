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

public class Smoke_Test_Freeze_Heat_Water extends Setup {

    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Smoke and CO Detectors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int Activate = 1;
    private int Idle = 0;

    private int Long_Exit_Delay =16;

    public Smoke_Test_Freeze_Heat_Water() throws Exception {}

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
        logger.info("Current software version: " + softwareVersion());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode tripping Freeze group 52 -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Activate);
        WebElement freeze27 = driver.findElement(By.xpath("//android.widget.TextView[@text='Freeze 27']"));
        verifySensorIsDisplayed(freeze27);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Freeze group 52 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Activate);
        verifySensorIsDisplayed(freeze27);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Freeze group 52 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Activate);
        verifySensorIsDisplayed(freeze27);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tampering Freeze group 52  -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.freeze_zones, 52);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(freeze27);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tampering Freeze group 52  -> Expected result = ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.freeze_zones, 52);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(freeze27);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering Freeze group 52  -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.freeze_zones, 52);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(freeze27);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.freeze_zones, 52,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************HEAT********************");
        logger.info("Disarm mode tripping Heat group 26 -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Activate);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in Disarm mode");}
        else { takeScreenshot();
            logger.info("Failed: Fire emergency is NOT sent in Disarm mode");}
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Heat group 26 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Activate);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in ArmStay mode");}
        else { takeScreenshot();
            logger.info("Failed: Fire emergency is NOT sent in ArmStay mode");}
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Heat group 26 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Activate);
        if (emergency.Emergency_sent_text.getText().equals("Fire Emergency Sent")){
            logger.info("Pass: Fire emergency is sent in ArmAway mode");}
        else { takeScreenshot();
            logger.info("Failed: Fire emergency is NOT sent in ArmAway mode");}
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tampering Heat group 26  -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.heat_zones, 26);
        TimeUnit.SECONDS.sleep(3);
        WebElement heat26 = driver.findElement(By.xpath("//android.widget.TextView[@text='Smoke-M 26']"));
        verifySensorIsTampered(heat26);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tampering Heat group 26  -> Expected result = ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.heat_zones, 26);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(heat26);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering Heat group 26  -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.heat_zones, 26);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(heat26);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.heat_zones, 26,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************WATER********************");
        logger.info("Disarm mode tripping Water_flood group 38 -> Expected result = Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Activate);
        WebElement water18 = driver.findElement(By.xpath("//android.widget.TextView[@text='Multi-Function-1 18']"));
        verifySensorIsDisplayed(water18);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Water_flood group 38 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Activate);
        verifySensorIsDisplayed(water18);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Water_flood group 38 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Activate);
        verifySensorIsDisplayed(water18);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************TAMPER********************");
        logger.info("Disarm mode tampering Water group 38 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.water_flood_zones, 38);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(water18);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tampering Water group 38 -> Expected result = ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.water_flood_zones, 38);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(water18);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tampering Water group 38 -> Expected result = Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.water_flood_zones, 38);
        verifySensorIsTampered(water18);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.water_flood_zones, 38,Idle);
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
