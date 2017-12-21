package sensors;

import panel.ContactUs;
import panel.HomePage;
import utils.Log;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Glassbreak_Shock_Tilt extends Setup {

    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Glassbreak, Shock and Tilt sensors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int Activate = 1;
    private int Idle = 0;
    private int Open =1;
    private int Close =0;

    private int Normal_Entry_Delay = 13;
    private int Long_Exit_Delay =16;

    public Smoke_Test_Glassbreak_Shock_Tilt() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Test1() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact_us = PageFactory.initElements(driver, ContactUs.class);
        logger.info("Current software version: " + softwareVersion());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

        logger.info("Disarm mode tripping Glass_break group 13, 17 -> Expected result = system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Idle);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Glass_break group 13 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        WebElement glass19 = driver.findElement(By.xpath("//android.widget.TextView[@text='Glass Break 19']"));
        verifySensorIsDisplayed(glass19);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Glass_break group 17 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Glass_Break group 13 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsDisplayed(glass19);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Glass_Break group 17 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        WebElement glass20 = driver.findElement(By.xpath("//android.widget.TextView[@text='Glass Break 20']"));
        verifySensorIsDisplayed(glass20);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper glassbreak group 13, 17 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.glassbreak_zones, 13);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.glassbreak_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(glass19);
        verifySensorIsTampered(glass20);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper glassbreak group 13 -> Expected result = Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.glassbreak_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(glass19);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper glassbreak group 17 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.glassbreak_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(glass20);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper glassbreak group 13 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.glassbreak_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(glass19);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper glassbreak group 17 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.glassbreak_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(glass20);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.glassbreak_zones, 17, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);


        logger.info("***********************TILT***********************");
        logger.info("Disarm mode tripping Tilt group 10, 12, 25 -> Expected result = system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Open);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Open);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Open);
        TimeUnit.SECONDS.sleep(2);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        verifyDisarm();

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Tilt group 10 -> Expected result = 30 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement tilt21 = driver.findElement(By.xpath("//android.widget.TextView[@text='Tilt 21']"));
        verifySensorIsDisplayed(tilt21);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Tilt group 12 -> Expected result = 100 sec delay -> Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement tilt22 = driver.findElement(By.xpath("//android.widget.TextView[@text='Tilt 22']"));
        verifySensorIsDisplayed(tilt22);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Tilt group 25 -> Expected result = ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement tilt23 = driver.findElement(By.xpath("//android.widget.TextView[@text='Tilt 23']"));
        verifySensorIsDisplayed(tilt23);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Tilt group 10 -> Expected result = 30 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verifySensorIsDisplayed(tilt21);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Tilt group 12 -> Expected result = 100 sec delay -> Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        verifySensorIsDisplayed(tilt22);
        verifyStatusOpen();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Tilt group 25 -> Arm Away");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Open);
        TimeUnit.SECONDS.sleep(3);
        verifyArmaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(3);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper tilt group 10, 12, 25 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 10);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 12);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 25);
        verifySensorIsTampered(tilt21);
        verifySensorIsTampered(tilt22);
        verifySensorIsTampered(tilt23);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper tilt group 10 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 10);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(tilt21);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper tilt group 12 -> Expected result = Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 12);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(tilt22);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper tilt group 25 -> Expected result = ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(tilt23);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper tilt group 10 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 10);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(tilt21);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 10, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper tilt group 12 -> Expected result = Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 12);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(tilt22);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 12, Close);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper tilt group 25 -> Expected result = ArmAway");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.tilt_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        verifyArmaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.tilt_zones, 25, Close);
        home_page.ArwAway_State.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(3);

        logger.info("***********************SHOCK_OTHER***********************");
        logger.info("Disarm mode tripping Shock group 13 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 13 -> Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        WebElement other_shock35 = driver.findElement(By.xpath("//android.widget.TextView[@text='Other Shock 35']"));
        verifySensorIsDisplayed(other_shock35);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 13 -> Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsDisplayed(other_shock35);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping Shock group 17 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 17 -> ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 17 -> Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        WebElement other_shock36 = driver.findElement(By.xpath("//android.widget.TextView[@text='Other Shock 36']"));
        verifySensorIsDisplayed(other_shock36);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper Shock_other group 13, 17 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 13);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(other_shock35);
        TimeUnit.SECONDS.sleep(2);
        verifySensorIsTampered(other_shock36);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_other group 13 -> Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(other_shock35);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_other group 17 -> ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(other_shock36);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_other group 13 -> Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(other_shock35);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_other group 17 -> Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_other_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(other_shock36);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_other_zones, 17, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(3);


        logger.info("***********************SHOCK_IQ***********************");
        logger.info("Disarm mode tripping Shock group 13 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 13 -> Instant Alarm");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        WebElement iq_shock24 = driver.findElement(By.xpath("//android.widget.TextView[@text='IQ Shock 24']"));
        verifySensorIsDisplayed(iq_shock24);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 13 -> Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsDisplayed(iq_shock24);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Disarm mode tripping Shock group 17 -> Disarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        TimeUnit.SECONDS.sleep(3);

        logger.info("********************************************************");
        logger.info("ArmStay mode tripping Shock group 17 -> ArmStay");
        ARM_STAY();
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tripping Shock group 17 -> Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Activate);
        TimeUnit.SECONDS.sleep(3);
        WebElement iq_shock25 = driver.findElement(By.xpath("//android.widget.TextView[@text='IQ Shock 25']"));
        verifySensorIsDisplayed(iq_shock25);
        verifyStatusAlarmed();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("***********************TAMPER***********************");
        logger.info("Disarm mode tamper Shock_IQ group 13, 17 -> Expected result = system stays in Disarm mode");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17);
        TimeUnit.SECONDS.sleep(5);
        verifySensorIsTampered(iq_shock24);
        TimeUnit.SECONDS.sleep(2);
        verifySensorIsTampered(iq_shock25);
        verifyDisarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_IQ group 13 -> Instant Alarm");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(iq_shock24);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmStay mode tamper Shock_IQ group 17 -> ArmStay");
        ARM_STAY();
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(iq_shock25);
        verifyArmstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        home_page.DISARM.click();
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_IQ group 13 -> Instant Alarm");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(iq_shock24);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 13, Idle);
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("ArmAway mode tamper Shock_IQ group 17 -> Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17);
        TimeUnit.SECONDS.sleep(3);
        verifySensorIsTampered(iq_shock25);
        verifyStatusTampered();
        verifyInAlarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.shock_IQ_zones, 17, Idle);
        enterDefaultUserCode();
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
