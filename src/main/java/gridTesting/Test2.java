package gridTesting;

import sensors.Sensors;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class Test2 {

    GridSetup setup = new GridSetup();
    Sensors MySensors = new Sensors();

    public Test2() throws Exception {
    }

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws MalformedURLException {
        setup.setCapabilities(URL_);
    }

    private int Open = 1;
    private int Close = 0;

    private int Normal_Entry_Delay = 13;

    @Parameters({"UDID_"})
    @Test(threadPoolSize = 3)
    public void Test2(String UDID_) throws Exception {

        setup.logger.info("Current software version: " + setup.Software_Version());
        TimeUnit.SECONDS.sleep(1);
        setup.getDriver().findElement(By.id("com.qolsys:id/t3_home_tab2")).click();
        TimeUnit.SECONDS.sleep(1);
        MySensors.read_sensors_from_csv();
        setup.logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(1);
        setup.getDriver().findElement(By.id("com.qolsys:id/t3_home_tab1")).click();
        TimeUnit.SECONDS.sleep(1);

        setup.logger.info("Disarm mode tripping sensors group 10, 12, 13, 14, 16, 25 -> Expected result= system stays in Disarm mode");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Open);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Open);
        TimeUnit.SECONDS.sleep(5);
        WebElement Door4 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 4']");
        setup.verify_sensor_is_displayed(Door4);
        WebElement Door5 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 5']");
        setup.verify_sensor_is_displayed(Door5);
        WebElement Door6 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 6']");
        setup.verify_sensor_is_displayed(Door6);
        WebElement Door7 = setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 7']");
        setup.verify_sensor_is_displayed(Door7);
        WebElement Door8 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 8']");
        setup.verify_sensor_is_displayed(Door8);
        WebElement Door9 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 9']");
        setup.verify_sensor_is_displayed(Door9);
        setup.verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);
        TimeUnit.SECONDS.sleep(5);

        setup.logger.info("********************************************************");
        setup.logger.info("Disarm mode tripping sensors group 8 -> Expected result= Instant Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Open);
        TimeUnit.SECONDS.sleep(3);
        WebElement Door2 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 2']");
        setup.verify_sensor_is_displayed(Door2);
        setup.verify_status_open();
        setup.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        setup.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        setup.logger.info("********************************************************");
        setup.logger.info("Disarm mode tripping sensors group 9 -> Expected result= 30 sec delay -> Alarm");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Open);
        TimeUnit.SECONDS.sleep(Normal_Entry_Delay);
        WebElement Door3 =  setup.getDriver().findElementByXPath("//android.widget.TextView[@text='Door/Window 3']");
        setup.verify_sensor_is_displayed(Door3);
        setup.verify_status_open();
        setup.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        setup.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        setup.logger.info("**********************TAMPER**********************");
        setup.logger.info("Disarm mode tamper sensors group 10, 12, 13, 14, 16, 25 -> Expected result = Disarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 10);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 12);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 13);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 14);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 16);
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 25);
        TimeUnit.SECONDS.sleep(3);
        setup. verify_sensor_is_tampered(Door4);
        setup.verify_sensor_is_tampered(Door5);
        setup.verify_sensor_is_tampered(Door6);
        setup.verify_sensor_is_tampered(Door7);
        setup.verify_sensor_is_tampered(Door8);
        setup.verify_sensor_is_tampered(Door9);
        setup.verify_disarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 12, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 13, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 14, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 16, Close);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 25, Close);

        setup.logger.info("********************************************************");
        setup.logger.info("Disarm mode tamper sensors group 8 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 8);
        TimeUnit.SECONDS.sleep(3);
        setup.verify_sensor_is_tampered(Door2);
        setup.verify_status_tampered();
        setup.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        setup.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        setup.logger.info("********************************************************");
        setup.logger.info("Disarm mode tamper sensors group 9 -> Expected result -> Instant Alarm");
        MySensors.sendTamper_allSensors_selectedGroup(MySensors.door_window_zones, 9);
        TimeUnit.SECONDS.sleep(3);
        setup.verify_sensor_is_tampered(Door3);
        setup.verify_status_tampered();
        setup.verify_in_alarm();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 9, Close);
        setup.enter_default_user_code();
        TimeUnit.SECONDS.sleep(3);
        setup.getDriver().findElement(By.id("com.qolsys:id/t3_home_tab2")).click();
        MySensors.deleteAllSensors1(UDID_);

    }
    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        setup.getDriver().quit();
    }
}
