package qtmsSRF;

import adc.ADC;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import panel.Settings_Page;
import utils.Setup;
import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Arm_stay_transmitter extends Setup {
    public Arm_stay_transmitter() throws Exception {
    }

    String page_name = "QTMS: ARM STAY";
    Logger logger = Logger.getLogger(page_name);
    String login = "pan7Aut";
    String password = "qolsys123";
    private int Normal_Exit_Delay = 10;
    private int Normal_Entry_Delay = 11;
    private int Long_Exit_Delay = 12;
    private int Long_Entry_Delay = 13;
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    Sensors MySensors = new Sensors();
    ADC adc = new ADC();
  //  sensors.SensorObject sensOb = new SensorObject();

    int Activate = 1;
    int Idle = 0;
    private int Open = 1;
    private int Close = 0;

    public void immediate_tamper_alarm(int zone, int group, String DLID, String Status, String Status1, int n0, int n1) throws Exception {
        Thread.sleep(1000);
        ARM_STAY();
        // sensors.primary_call(DLID, tamper);
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals(Status)) {
            logger.info("Pass: Correct status is " + Status);
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        verify_in_alarm();
        //sensors.primary_call(DLID, restore);
        adc.New_ADC_session_User(login, password);
        Thread.sleep(60000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone + ") Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(30000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Tamper Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        enter_default_user_code();
        Thread.sleep(1000);
        // sensors.primary_call(DLID, restore);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Settings_Page sett = PageFactory.initElements(driver, Settings_Page.class);
        navigate_to_Settings_page();
        Thread.sleep(1000);
        sett.STATUS.click();
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n0).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n0).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n0).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText() + "***");
        }
        Thread.sleep(2000);


        // delete_from_primary(zone);
        Thread.sleep(1000);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver("62964b68", "http://127.0.0.1", "4723");
        setup_logger(page_name);
        Thread.sleep(1000);
    }

    @Test
    public void Test1() throws Exception {
        MySensors.read_sensors_from_csv();
        //MySensors.addAllSensors();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        servcall.set_ARM_STAY_NO_DELAY_enable_Transmitter();
        TimeUnit.SECONDS.sleep(5);
        servcall.EVENT_ARM_STAY_Transmitter();
        //ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        verify_armstay();
        TimeUnit.SECONDS.sleep(5);
      //  MySensors.sendTamper_allSensors_selectedGroup(2, 8);
        enter_default_user_code();
        Thread.sleep(3000);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 8, Close);
        //  MySensors.deleteAllSensorsTransmitter();

    }
    public void sendPacket_selectedZone(int Zone, int Group, String Status) throws IOException, InterruptedException {

    }

    public void sendPacket_allSensors_selectedGroup(List<Integer> input_sens_zones, int input_group_number, int z) throws IOException, InterruptedException {





 /*
        List<Integer> foundSensorsInGroup;
        foundSensorsInGroup = new ArrayList();
        for (int i = 0; i < input_sens_zones.size(); i++) {
           SensorObject sensor = null;
            sensor = SensorObject_ArrayList.get(input_sens_zones.get(i));
            int found_group = sensor.getSensorGroup();
            if (found_group == input_group_number) {
                foundSensorsInGroup.add(input_sens_zones.get(i));
                //       System.out.println(foundSensorsInGroup);
            }
        }
        for (int l = 0; l < foundSensorsInGroup.size(); l++) {
            sensors.SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(foundSensorsInGroup.get(l));
            String newDLID = temp_sensor.getDLID();
            //       System.out.println(newDLID);
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = "shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + MySensors.transmitter_sensor_int_map.get(sensor_type) + " i32 0 i32 0 i32 " + z;
            rt.exec(adbPath + " -s " + MySensors.transmitter + send_packet);
            //     System.out.println(send_packet);
            TimeUnit.SECONDS.sleep(2);
        }
    }
   public void sendTamper_allSensors_selectedGroup(List<Integer> input_sens_zones, int input_group_number) throws IOException, InterruptedException {
        List<Integer> foundSensorsInGroup;
        foundSensorsInGroup = new ArrayList();
        for (int i = 0; i < input_sens_zones.size(); i++) {
            sensors.SensorObject sensor = null;
            sensor = SensorObject_ArrayList.get(input_sens_zones.get(i));
            int found_group = sensor.getSensorGroup();
            if (found_group == input_group_number) {
                foundSensorsInGroup.add(input_sens_zones.get(i));
                //       System.out.println(foundSensorsInGroup);
            }
        }
        for (int l = 0; l < foundSensorsInGroup.size(); l++) {
            sensors.SensorObject temp_sensor = null;
            temp_sensor = SensorObject_ArrayList.get(foundSensorsInGroup.get(l));
            String newDLID = temp_sensor.getDLID();
            //       System.out.println(newDLID);
            String sensor_type = temp_sensor.getSensorType();
//            initialize_transmitter_sensor_int_map();
            String send_packet = "shell service call srftransmitservice 1 s16 " + newDLID + " i32 0 i32 " + transmitter_sensor_int_map.get(sensor_type) + " i32 1 i32 0";
            rt.exec(adbPath + " -s " + transmitter + send_packet);
            //      System.out.println(send_packet);
            TimeUnit.SECONDS.sleep(2);
        }*/
    }




















    @AfterTest
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit(); }

}
