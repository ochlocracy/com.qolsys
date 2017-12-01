package adc;

import panel.*;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Log;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebSite extends Setup {

    public File appDir = new File("src");

    Log log = new Log();
    ADC myADC = new ADC();
    String open = "06";
    String activate = "02";
    String close = "04";
    String restore = "00";
    String tamper = "01";

    public WebSite() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        PropertyConfigurator.configure(new File(appDir, "log4j.properties").getAbsolutePath());
        log.clearLog();
        log.startTestCase("adc sensors name change");
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        myADC.webDriverSetUp();
    }

    public void swipingVertical() throws InterruptedException {
        int starty = 660;
        int endy = 260;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @Test
    public void Test1 () throws IOException, InterruptedException {
        logger.info("********************************************************");
    //    logger.info("Current software version: " + Software_Version());
        logger.info("Add sensors");
        myADC.add_all_sensor_types();
        logger.info("sensors added successfully");
    }

    @Test
    public void Test2() throws IOException, InterruptedException {
        logger.info("********************************************************");
        logger.info("Verify sensors appear on the dealer website");
        myADC.New_ADC_session("4679473");
        TimeUnit.SECONDS.sleep(5);
        myADC.driver1.findElement(By.partialLinkText("sensors")).click();
        TimeUnit.SECONDS.sleep(3);
        myADC.Request_equipment_list();
        myADC.Sensor_verification("Door/Window 1", "10","Door/Window", 2);
        myADC.Sensor_verification("Motion 2","17", "Motion", 3);
        myADC.Sensor_verification("Smoke Detector 3", "26", "Smoke Detector", 4);
        myADC.Sensor_verification("CO Detector 4","34", "CO Detector", 5);
        myADC.Sensor_verification("Glass Break 5", "13", "Glass Break", 6);
        myADC.Sensor_verification("Tilt 6", "12", "Tilt", 7);
        myADC.Sensor_verification("Other Shock 7", "13", "Shock: Others", 8);
        myADC.Sensor_verification("Freeze 8", "52", "Freeze", 9);
        myADC.Sensor_verification("Smoke-M 9", "26", "IQ Smoke: Multi-function", 10);
        myADC.Sensor_verification("Multi-Function-1 10", "38", "Water: Multi-Function", 11);
        myADC.Sensor_verification("Auxiliary Pendant 13", "6", "Medical Pendant", 12);
        myADC.Sensor_verification("Door Bell 14", "25", "Door Bell", 13);
        myADC.Sensor_verification("Occupancy Sensor 15", "25", "Occupancy", 14);
        myADC.Sensor_verification("IQ Shock", "13", "IQ Shock", 15);
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void Test3() throws Exception {
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        DevicesPage devices = PageFactory.initElements(driver, DevicesPage.class);
        Security_Sensors_Page sec_sencsors = PageFactory.initElements(driver, Security_Sensors_Page.class);
        logger.info("********************************************************");
        logger.info("Rename sensors from panel");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        devices.Security_Sensors.click();
        sec_sencsors.Edit_Sensor.click();
        TimeUnit.SECONDS.sleep(3);
        logger.info("Renaming door_window sensor, new name: "+myADC.new_dw_name);
        Rename_Sensor(0, myADC.new_dw_name);
        logger.info("Renaming motion sensor, new name: "+ myADC.new_motion_name);
        Rename_Sensor(1, myADC.new_motion_name);
        logger.info("Renaming smoke detector, new name: "+ myADC.new_smoke_name);
        Rename_Sensor(2, myADC.new_smoke_name);
        logger.info("Renaming co_detector, new name: "+ myADC.new_co_name);
        Rename_Sensor(3, myADC.new_co_name);
        swipingVertical();
        TimeUnit.SECONDS.sleep(10);
        logger.info("Renaming glassbreak sensor, new name: "+ myADC.new_glassbreak_name);
        Rename_Sensor(0, myADC.new_glassbreak_name);
        logger.info("Renaming tilt sensor, new name: "+ myADC.new_tilt_name);
        Rename_Sensor(1, myADC.new_tilt_name);
        logger.info("Renaming shock_other sensor, new name: "+ myADC.new_shock_other_name);
        Rename_Sensor(2, myADC.new_shock_other_name);
        logger.info("Renaming freeze sensor, new name: "+ myADC.new_freeze_name);
        Rename_Sensor(3, myADC.new_freeze_name);
        swipingVertical();
        TimeUnit.SECONDS.sleep(10);
        logger.info("Renaming heat sensor, new name: "+ myADC.new_heat_name);
        Rename_Sensor(0, myADC.new_heat_name);
        logger.info("Renaming water_flood sensor, new name: "+ myADC.new_water_flood_name);
        Rename_Sensor(1, myADC.new_water_flood_name);
        logger.info("Renaming keyfob, new name: "+ myADC.new_keyfob_name);
        Rename_Sensor(2, myADC.new_keyfob_name);
        logger.info("Renaming keypad, new name: "+ myADC.new_keypad_name);
        Rename_Sensor(3, myADC.new_keypad_name);
        swipingVertical();
        TimeUnit.SECONDS.sleep(10);
        logger.info("Renaming auxiliary pendant, new name: "+ myADC.new_med_pendant_name);
        Rename_Sensor(1, myADC.new_med_pendant_name);
        logger.info("Renaming doorbell sensor, new name: "+ myADC.new_doorbell_name);
        Rename_Sensor(2, myADC.new_doorbell_name);
        logger.info("Renaming occupancy sensor, new name: "+ myADC.new_occupancy_name);
        Rename_Sensor(3, myADC.new_occupancy_name);
        logger.info("Renaming iq shock sensor, new name: "+ myADC.new_iq_shock_name);
        Rename_Sensor(4, myADC.new_iq_shock_name);
        logger.info("sensors renamed successfully");
    }

    @Test
    public void Test4() throws InterruptedException {
        logger.info("********************************************************");
        logger.info("Verify new sensor name, group, type are displayed correctly on the dealer website");
        myADC.New_ADC_session("4679473");
        TimeUnit.SECONDS.sleep(3);
        myADC.driver1.findElement(By.partialLinkText("sensors")).click();
        myADC.driver1.findElement(By.xpath("//input[@value='Request Sensor Names']")).click();
        Alert alert = myADC.driver1.switchTo().alert();
        myADC.driver1.switchTo().alert();
        alert.accept();
        TimeUnit.SECONDS.sleep(5);
        myADC.driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        TimeUnit.SECONDS.sleep(10);
        myADC.Request_equipment_list();
        myADC.Sensor_verification(myADC.new_dw_name, "10", "Door/Window", 2);
        myADC.Sensor_verification(myADC.new_motion_name, "17", "Motion", 3);
        myADC.Sensor_verification(myADC.new_smoke_name, "26", "Smoke Detector", 4);
        myADC.Sensor_verification(myADC.new_co_name, "34", "CO Detector", 5);
        myADC.Sensor_verification(myADC.new_glassbreak_name, "13", "Glass Break", 6);
        myADC.Sensor_verification(myADC.new_tilt_name, "12", "Tilt", 7);
        myADC.Sensor_verification(myADC.new_shock_other_name, "13", "Shock: Others", 8);
        myADC.Sensor_verification(myADC.new_freeze_name, "52", "Freeze", 9);
        myADC.Sensor_verification(myADC.new_heat_name, "26", "IQ Smoke: Multi-function", 10);
        myADC.Sensor_verification(myADC.new_water_flood_name, "38", "Water: Multi-Function", 11);
        myADC.Sensor_verification(myADC.new_med_pendant_name, "6", "Medical Pendant", 12);
        myADC.Sensor_verification(myADC.new_doorbell_name, "25", "Door Bell", 13);
        myADC.Sensor_verification(myADC.new_occupancy_name, "25", "Occupancy", 14);
        myADC.Sensor_verification(myADC.new_iq_shock_name, "13", "IQ Shock", 15);
    }

    @Test
    public void Test5() throws InterruptedException {
        logger.info("********************************************************");
        logger.info("Verify new sensor name, group, type, reporting type, status are displayed correctly on the dealer website");
        myADC.New_ADC_session("4679473");
        TimeUnit.SECONDS.sleep(3);
        myADC.driver1.findElement(By.partialLinkText("sensors")).click();
        myADC.driver1.findElement(By.xpath("//input[@value='Request Sensor Names']")).click();
        Alert alert = myADC.driver1.switchTo().alert();
        myADC.driver1.switchTo().alert();
        alert.accept();
        TimeUnit.SECONDS.sleep(5);
        myADC.driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        TimeUnit.SECONDS.sleep(10);
        myADC.Request_equipment_list();
        myADC.Sensor_verification_full(myADC.new_dw_name,"10", "Door/Window", "Entry/Exit", "Closed",2);
        myADC.Sensor_verification_full(myADC.new_motion_name, "17", "Motion", "Interior", "Idle", 3);
        myADC.Sensor_verification_full(myADC.new_smoke_name, "26", "Smoke Detector", "Fire", "OK", 4);
        myADC.Sensor_verification_full(myADC.new_co_name, "34", "CO Detector", "Carbon Monoxide", "OK", 5);
        myADC.Sensor_verification_full(myADC.new_glassbreak_name, "13", "Glass Break", "Perimeter", "OK", 6);
        myADC.Sensor_verification_full(myADC.new_tilt_name, "12", "Tilt", "Entry/Exit", "Closed", 7);
        myADC.Sensor_verification_full(myADC.new_shock_other_name, "13", "Shock: Others", "Perimeter", "Closed", 8);
        myADC.Sensor_verification_full(myADC.new_freeze_name, "52", "Freeze", "Low temperature", "OK", 9);
        myADC.Sensor_verification_full(myADC.new_heat_name, "26", "IQ Smoke: Multi-function", "Fire", "OK", 10);
        myADC.Sensor_verification_full(myADC.new_water_flood_name, "38", "Water: Multi-Function", "Water", "OK",11);
        myADC.Sensor_verification_full(myADC.new_med_pendant_name, "6", "Medical Pendant", "Emergency/Medical", "OK",12);
        myADC.Sensor_verification_full(myADC.new_doorbell_name, "25", "Door Bell", "Non-Reporting", "Closed", 13);
        myADC.Sensor_verification_full(myADC.new_occupancy_name, "25", "Occupancy", "Non-Reporting", "Occupied",14);
        myADC.Sensor_verification_full(myADC.new_iq_shock_name, "13", "IQ Shock","Perimeter", "Closed", 15);
    }

    @Test
    public void Test6() throws IOException, InterruptedException {
        logger.info("********************************************************");
        logger.info("In Disarm state open/activate sensors");
        myADC.send_event_to_sensor(myADC.door_window_DLID, open);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.motion_DLID, activate);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.glassbreak_DLID, activate);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.shock_other_DLID, activate);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.tilt_DLID, open);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.doorbell_DLID, open);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.occupancy_DLID, open);
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void Test7() throws InterruptedException {
        logger.info("********************************************************");
        logger.info("Verify new sensor name, group, type, reporting type, status are displayed correctly on the dealer website");
        myADC.New_ADC_session("4679473");
        TimeUnit.SECONDS.sleep(3);
        myADC.driver1.findElement(By.partialLinkText("sensors")).click();
        myADC.driver1.findElement(By.xpath("//input[@value='Request Sensor Names']")).click();
        Alert alert = myADC.driver1.switchTo().alert();
        myADC.driver1.switchTo().alert();
        alert.accept();
        TimeUnit.SECONDS.sleep(5);
        myADC.driver1.findElement(By.id("ctl00_refresh_sensors_button_btnRefreshPage")).click();
        TimeUnit.SECONDS.sleep(10);
        myADC.Request_equipment_list();
        myADC.Sensor_verification_full(myADC.new_dw_name, "10", "Door/Window", "Entry/Exit", "Open",2);
        myADC.Sensor_verification_full(myADC.new_motion_name, "17", "Motion", "Interior", "Activated", 3);
        myADC.Sensor_verification_full(myADC.new_smoke_name, "26", "Smoke Detector", "Fire", "OK", 4);
        myADC.Sensor_verification_full(myADC.new_co_name, "34", "CO Detector", "Carbon Monoxide", "OK", 5);
        myADC.Sensor_verification_full(myADC.new_glassbreak_name, "13", "Glass Break", "Perimeter", "OK", 6);
        myADC.Sensor_verification_full(myADC.new_tilt_name, "12", "Tilt", "Entry/Exit", "Open", 7);
        myADC.Sensor_verification_full(myADC.new_shock_other_name, "13", "Shock: Others", "Perimeter", "Closed", 8);
        myADC.Sensor_verification_full(myADC.new_freeze_name, "52", "Freeze", "Low temperature", "OK", 9);
        myADC.Sensor_verification_full(myADC.new_heat_name, "26", "IQ Smoke: Multi-function", "Fire", "OK", 10);
        myADC.Sensor_verification_full(myADC.new_water_flood_name, "38", "Water: Multi-Function", "Water", "OK",11);
        myADC.Sensor_verification_full(myADC.new_med_pendant_name, "6", "Medical Pendant", "Emergency/Medical", "OK",12);
        myADC.Sensor_verification_full(myADC.new_doorbell_name, "25", "Door Bell", "Non-Reporting", "Open", 13);
        myADC.Sensor_verification_full(myADC.new_occupancy_name, "25", "Occupancy", "Non-Reporting", "Vacant",14);
    }

    @Test
    public void Test8() throws InterruptedException, IOException {
        logger.info("********************************************************");
        logger.info("In Disarm state close/idle sensors");
        myADC.send_event_to_sensor(myADC.door_window_DLID, close);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.motion_DLID, restore);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.glassbreak_DLID, restore);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.shock_other_DLID, restore);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.tilt_DLID, close);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.doorbell_DLID, close);
        TimeUnit.SECONDS.sleep(3);
        myADC.send_event_to_sensor(myADC.occupancy_DLID, close);
        TimeUnit.SECONDS.sleep(3);
    }

    @Test
    public void Test9() throws InterruptedException {
        logger.info("********************************************************");
        logger.info("Renaming sensors from the adc dealer website");
        myADC.New_ADC_session("4679473");
        TimeUnit.SECONDS.sleep(3);
        myADC.driver1.findElement(By.partialLinkText("sensors")).click();
        myADC.driver1.findElement(By.partialLinkText("Change Sensor Names")).click();
        TimeUnit.SECONDS.sleep(3);
        WebElement name_field1 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl02$UcEligibleSensorName1$txtSN"));
        name_field1.sendKeys("WebName_DoorWindow1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field2 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl03$UcEligibleSensorName1$txtSN"));
        name_field2.sendKeys("WebName_Motion1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field3 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl04$UcEligibleSensorName1$txtSN"));
        name_field3.sendKeys("WebName_Smoke1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field4 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl05$UcEligibleSensorName1$txtSN"));
        name_field4.sendKeys("WebName_CODetector1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field5 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl06$UcEligibleSensorName1$txtSN"));
        name_field5.sendKeys("WebName_Glassbreak1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field6 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl07$UcEligibleSensorName1$txtSN"));
        name_field6.sendKeys("WebName_Tilt1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field7 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl08$UcEligibleSensorName1$txtSN"));
        name_field7.sendKeys("WebName_OtherShock1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field8 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl09$UcEligibleSensorName1$txtSN"));
        name_field8.sendKeys("WebName_Freeze1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field9 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl10$UcEligibleSensorName1$txtSN"));
        name_field9.sendKeys("WebName_Heat1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field10 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl11$UcEligibleSensorName1$txtSN"));
        name_field10.sendKeys("WebName_Water1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field11 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl12$UcEligibleSensorName1$txtSN"));
        name_field11.sendKeys("WebName_Pendant1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field12 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl13$UcEligibleSensorName1$txtSN"));
        name_field12.sendKeys("WebName_DoorBell1");
        TimeUnit.SECONDS.sleep(2);
        WebElement name_field13 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl14$UcEligibleSensorName1$txtSN"));
        name_field13.sendKeys("WebName_Occupancy1");
        WebElement name_field14 = myADC.driver1.findElement(By.name("ctl00$phBody$dgDevices$ctl15$UcEligibleSensorName1$txtSN"));
        name_field14.sendKeys("WebName_IQShock1");
        TimeUnit.SECONDS.sleep(3);
        myADC.driver1.findElement(By.id("ctl00_phBody_btnSubmit")).click();
        Alert alert = myADC.driver1.switchTo().alert();
        myADC.driver1.switchTo().alert();
        alert.accept();
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void Test10() throws IOException {
        logger.info("********************************************************");
        logger.info("Deleting all sensors");
        myADC.delete_all();
        System.out.println("sensors deleted successfully");
    }

    @AfterMethod
    public void tearDown() {
        log.endTestCase("adc sensors name change");
        myADC.driver1.quit();
        if (driver != null)
        driver.quit();
    }
}