//package zwave;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.Point;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import panel.AdvancedSettingsPage;
//import panel.DevicesPage;
//import panel.HomePage;
//import panel.InstallationPage;
//import utils.Setup;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Created by nchortek on 6/22/17.
// * Edited by JMAUS 11/3/17
// */
//public class LightsTest extends Setup {
//    String page_name = "Z-Wave Lights and Switches Test";
//    Logger logger = Logger.getLogger(page_name);
//
//
//
//    public LightsTest() throws Exception {
//        LightsPage lights = PageFactory.initElements(driver, LightsPage.class);
//
//
//    }
//
//    @BeforeMethod
//    public void capabilities_setup() throws Exception {
//        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
//        setupLogger(page_name);
//    }
////    @Test
////    public void LgihtTest () throws Exception {
////        // navigate to lights page and initialize variables
////        LightsPage lights = PageFactory.initElements(driver, LightsPage.class);
////        File light_on = new File(projectPath + "/scr/light_on");
////        File light_off = new File(projectPath + "/scr/light_off");
////        swipeLeft();
////        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/lightSelect"));
////        List<WebElement> status = driver.findElements(By.id("com.qolsys:id/statusButton"));
////        for (int i = 0; i <= 4; i++) {
////            if (checkStatus(light_off, status.get(0)))
////                lights.Light_Icon.click();
////            if (checkStatus(light_on, status.get(0)))
////                lights.Light_Icon.click();
////        }
////    }
//
//    @Test(priority = 1)
//    public void Test_Lights_Page() throws Exception {
//        // navigate to lights page and initialize variables
//        LightsPage lights = PageFactory.initElements(driver, LightsPage.class);
//        File light_on = new File(projectPath + "/scr/light_on");
//        File light_off = new File(projectPath + "/scr/light_off");
//        swipeLeft();
//        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/lightSelect"));
//        List<WebElement> status = driver.findElements(By.id("com.qolsys:id/statusButton"));
//
//        File screenshot = driver.getScreenshotAs(OutputType.FILE);
//        File screenshotLocation = new File(projectPath+"/scr/test");
//        FileUtils.copyFile(screenshot, screenshotLocation);
//
//
//        // check if light can be selected
//        li.get(0).click();
//        if (!checkAttribute(li.get(0), "checked", "true"))
//            return;
//
//        // check if light can be turned on
//        lights.On_Button.click();
//        Thread.sleep(6000);
//        if (!checkAttribute(li.get(0), "checked", "false"))
//            return;
//
//        // check if light icon turns yellow
//        if (!checkStatus(light_on, status.get(0)))
//            return;
//
//        //test dimmer functionality
//        Point DimLocation = lights.Dimmer.getLocation();
//        int DimWidth = lights.Dimmer.getSize().getWidth();
//        int startx = DimLocation.getX();
//        int starty = DimLocation.getY();
//        int endx = startx + DimWidth - 10;
//
//        touchSwipe(endx, starty, startx, starty);
//        if (!checkStatus(light_off, status.get(0)))
//            return;
//
//        li.get(0).click();
//        lights.On_Button.click();
//
//        // ensure light can be selected
//        li.get(0).click();
//        if (!checkAttribute(li.get(0), "checked", "true"))
//            return;
//
//        // check if light is deselected upon turn-off
//        lights.Off_Button.click();
//        Thread.sleep(10000);
//        if (!checkAttribute(li.get(0), "checked", "false")) {
//            return;
//        }
//
//        // check if light icon turns grey
//        if (!checkStatus(light_off, status.get(0)))
//            lights.Light_Icon.click();
//            return;
//
//        // repeat above process but for multiple lights
//        clickAll(li);
//
//        lights.On_Button.click();
//        Thread.sleep(10000);
//
//        //check that they're deselected
//        if (!checkAttribute(li.get(0), "checked", "false"))
//            return;
//
//        if (!checkAttribute(li.get(1), "checked", "false"))
//            return;
//
//        if (!checkAttribute(li.get(2), "checked", "false"))
//            return;
//
//        // check that lights turn yellow
//        checkAllStatus(light_on, status);
//
//        clickAll(li);
//        lights.Off_Button.click();
//        Thread.sleep(10000);
//
//        //check that they're deselected
//        if (!checkAttribute(li.get(0), "checked", "false"))
//            return;
//
//        if (!checkAttribute(li.get(1), "checked", "false"))
//            return;
//
//        if (!checkAttribute(li.get(2), "checked", "false"))
//            return;
//
//        // check that lights turn grey
//        checkAllStatus(light_off, status);
//
//        //check that the lights can be turned on/off by clicking on their status buttons
//        clickAll(status);
//        Thread.sleep(10000);
//        checkAllStatus(light_on, status);
//        clickAll(status);
//        Thread.sleep(10000);
//        checkAllStatus(light_off, status);
//
//        Thread.sleep(6000);
//    }
//
//    public void Z_Wave_Lights_Disarm_Mode(String UDID_) throws Exception {
//        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
//        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
//        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        logger.info("Test software version: " + softwareVersion());
//
//        logger.info("*************************ZLS_DDD_001*******************************");
//        logger.info("Disarm mode: Verify that a Light can be paired with a Panel");
//        navigateToAdvancedSettingsPage();
//        logger.info("Adding lights...");
//
//
//        logger.info("****************************ZLS_DDD_003****************************");
//        logger.info("Disarm mode: Verify that multiple Lights can be paired with a Panel");
//
//        logger.info("************************ZLS_DDD_004********************************");
//        logger.info("Disarm mode: Verify that you can pair max of Lights according to the set current number");
//
//        logger.info("************************ZLS_DDD_002********************************");
//        logger.info("Disarm mode: Verify that a new Light appears on the ADC websites");
//
//        logger.info("*****************************ZLS_DDD_005***************************");
//        logger.info("Disarm mode: Verify that you can edit a device name (panel)");
//
//        logger.info("**************************ZLS_DDD_006******************************");
//        logger.info("Disarm mode: Verify that you can edit a device name (ADC");
//
//        logger.info("**********************************ZLS_DDD_011**********************");
//        logger.info("Disarm mode: Verify that a light can be turned ON from a panel");
//
//        logger.info("********************************ZLS_DDD_012************************");
//        logger.info("Disarm mode: Verify that a light can be turned OFF from a panel");
//
//        logger.info("***************************ZLS_DDD_013*****************************");
//        logger.info("Disarm mode: Verify that multiple lights can be turned ON from a panel");
//
//        logger.info("***************************ZLS_DDD_014*****************************");
//        logger.info("Disarm mode: Verify that multiple lights can be turned OFF from a panel");
//
//        logger.info("****************************ZLS_DDD_015****************************");
//        logger.info("Disarm mode: Verify that dimming level can be changed from a panel");
//
//        logger.info("*****************************ZLS_DDD_0016***************************");
//        logger.info("Disarm mode: Verify that the set dimming level remains after the light been turned  ON/OFF");
//
//        logger.info("******************************ZLS_DDD_017**************************");
//        logger.info("Disarm mode: Verify that panel recognizes status change (ON)");
//
//        logger.info("*******************************ZLS_DDD_018*************************");
//        logger.info("Disarm mode: Verify that panel recognizes status change (OFF)");
//
//        logger.info("**********************ZLS_DDD_038**********************************");
//        logger.info("Disarm mode: Power Metering on Panel");
//
//        logger.info("******************************ZLS_DDD_021**************************");
//        logger.info("Disarm mode: Verify that the Light can be turned On from the User website");
//
//        logger.info("*******************************ZLS_DDD_022*************************");
//        logger.info("Disarm mode: Verify that the Light can be turned Off from the User website");
//
//        logger.info("*****************************ZLS_DDD_023***************************");
//        logger.info("Disarm mode: Verify that the dimming level can be changed from the User website");
//
//        logger.info("*****************************ZLS_DDD_039***************************");
//        logger.info("Disarm mode: Power Metering on ADC");
//
//        logger.info("*****************************ZLS_DDD_007***************************");
//        logger.info("Disarm mode: Verify that when the device is unreachable a Panel recognizes the status correctly");
//
//        logger.info("******************************ZLS_DDD_019**************************");
//        logger.info("Disarm mode: Verify that only 10 lights are being monitored on ADC");
//
//        logger.info("********************************ZLS_DDD_020************************");
//        logger.info("Disarm mode: Verify that you can change the 10 lights being monitored on ADC");
//
//        logger.info("********************************ZLS_DDD_024************************");
//        logger.info("Disarm mode: Create rule engine for lights with alarm condition: When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("**********************************ZLS_DDD_025**********************");
//        logger.info("Disarm mode: Create rule engine for lights with alarm condition:  When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("*************************ZLS_DDD_026*******************************");
//        logger.info("Disarm mode: Create rule engine for lights with arm/disarm conditions: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn On for 5 mins, During these time frames: At all times, Select devices");
//
//        logger.info("********************************ZLS_DDD_027************************");
//        logger.info("Disarm mode: Create rule engine for lights with arm/disarm conditions: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn Off, During these time frames: At all times, Select devices");
//
//        logger.info("***************************ZLS_DDD_028*****************************");
//        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Alarm (Panel Alarm), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("*****************************ZLS_DDD_029***************************");
//        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Alarm (Panel Alarm), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("*******************************ZLS_DDD_030*************************");
//        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Arm (Panel Arm A way), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("**************************ZLS_DDD_031******************************");
//        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Arm (Panel Arm S tay), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("*****************************ZLS_DDD_032***************************");
//        logger.info("Disarm mode: Create rule engine for lights with  Geo-fencing condition: When this event occurs: People cross a Geo-Fence (Exits Home), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("*****************************ZLS_DDD_033***************************");
//        logger.info("Disarm mode: Create rule engine for lights with  Geo-fencing condition: When this event occurs: People cross a Geo-Fence (Enters Home), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("***********************ZLS_DDD_037*********************************");
//        logger.info("Disarm mode: Create a group of lights");
//
//        logger.info("*********************************ZLS_DDD_008***********************");
//        logger.info("Disarm mode: Verify that the history events are displayed on the Home Control Status page");
//
//        logger.info("***************************ZLS_DDD_009*****************************");
//        logger.info("Disarm mode: Verify that  a Light can be deleted from a Panel");
//
//        logger.info("*********************************ZLS_DDD_010***********************");
//        logger.info("Disarm mode: Verify that  a Light can be deleted from the ADC website");
//    }
//
//    public void Z_Wave_Lights_Arm_Stay_Mode(String UDID_) throws Exception {
//        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
//        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
//        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        logger.info("Test software version: " + softwareVersion());
//
//        logger.info("*****************************ZLSD_AS_005***************************");
//        logger.info("Arm Stay: Verify that a light can be turned ON from a panel");
//
//        logger.info("*******************************ZLSD_AS_006*************************");
//        logger.info("Arm Stay: Verify that a light can be turned OFF from a panel");
//
//        logger.info("**********************************ZLSD_AS_007**********************");
//        logger.info("Arm Stay: Verify that multiple lights can be turned ON from a panel");
//
//        logger.info("******************************ZLSD_AS_008**************************");
//        logger.info("Arm Stay: Verify that multiple lights can be turned OFF from a panel");
//
//        logger.info("******************************ZLSD_AS_009**************************");
//        logger.info("Arm Stay: Verify that dimming level can be changed from a panel");
//
//        logger.info("********************************ZLSD_AS_010************************");
//        logger.info("Arm Stay: Verify that the set dimming level remains after the light been turned  ON/OFF");
//
//        logger.info("*******************************ZLSD_AS_011*************************");
//        logger.info("Arm Stay: Verify that panel recognizes status change");
//
//        logger.info("******************************ZLSD_AS_012**************************");
//        logger.info("Arm Stay: Verify that panel recognizes status change");
//
//        logger.info("********************************ZLSD_AS_015************************");
//        logger.info("Arm Stay: Verify that the Light can be turned On from the User website");
//
//        logger.info("******************************ZLSD_AS_016**************************");
//        logger.info("Arm Stay: Verify that the Light can be turned Off from the User website");
//
//        logger.info("*******************************ZLSD_AS_017*************************");
//        logger.info("Arm Stay: Verify that the dimming level can be changed from the User website");
//
//        logger.info("**********************************ZLSD_AS_032**********************");
//        logger.info("Arm Stay: Power Metering on Panel");
//
//        logger.info("*************************ZLSD_AS_033*******************************");
//        logger.info("Arm Stay: Power Metering on ADC");
//
//        logger.info("****************************ZLSD_AS_002****************************");
//        logger.info("Arm Stay: Verify that when the device is unreachable a Panel recognizes the status correctly");
//
//        logger.info("************************ZLSD_AS_001********************************");
//        logger.info("Arm Stay: Verify that you can edit a device name (ADC)");
//
//        logger.info("*********************************ZLSD_AS_013***********************");
//        logger.info("Arm Stay: Verify that only 10 lights are being monitored on ADC");
//
//        logger.info("*********************************ZLSD_AS_014***********************");
//        logger.info("Arm Stay: Verify that you can change the 10 lights being monitored on ADC");
//
//        logger.info("*****************************ZLSD_AS_018***************************");
//        logger.info("Arm Stay: Create rule engine for lights with alarm condition: Lights are Off: When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("**************************ZLSD_AS_019******************************");
//        logger.info("Arm Stay: Create rule engine for lights with alarm condition: Lights are On: When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("*********************************ZLSD_AS_020***********************");
//        logger.info("Arm Stay: Create rule engine for lights with arm/disarm conditions: Lights are OFF: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn On for 5 mins, During these time frames: At all times, Select devices");
//
//        logger.info("*********************************ZLSD_AS_021***********************");
//        logger.info("Arm Stay: Create rule engine for lights with arm/disarm conditions: Lights are ON: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn Off, During these time frames: At all times, Select devices");
//
//        logger.info("*******************************ZLSD_AS_022*************************");
//        logger.info("Arm Stay: Create rule engine for lights with  Alarm condition: Lights are Off:  When this event occurs: Alarm (Panel Alarm), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("*****************************ZLSD_AS_023***************************");
//        logger.info("Arm Stay: Create rule engine for lights with  Alarm condition: Lights are On: When this event occurs: Alarm (Panel Alarm), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("********************************ZLSD_AS_024************************");
//        logger.info("Arm Stay: Create rule engine for lights with  Alarm condition: Lights are Off: When this event occurs: Arm (Panel Arm A way), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("******************************ZLSD_AS_025**************************");
//        logger.info("Arm Stay: Create rule engine for lights with  Alarm condition: Lights are Off: When this event occurs: Arm (Panel Arm S tay), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("**********************************ZLSD_AS_026**********************");
//        logger.info("Arm Stay: Create rule engine for lights with  Geo-fencing condition: Lights are ON: Automate My: Light, When this event occurs: People cross a Geo-Fence (Exits Home), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("*********************************ZLSD_AS_027***********************");
//        logger.info("Arm Stay: Create rule engine for lights with  Geo-fencing condition: Lights are OFF:  When this event occurs: People cross a Geo-Fence (Enters Home), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("*****************************ZLSD_AS_031***************************");
//        logger.info("Arm Stay: Create a group of lights: Turn the grouped lights On/Off");
//
//        logger.info("******************************ZLSD_AS_003**************************");
//        logger.info("Arm Stay: Verify that the history events are displayed on the Home Control Status page");
//
//        logger.info("*******************************ZLSD_AS_004*************************");
//        logger.info("Arm Stay: Verify that  a Light can be deleted from the ADC website");
//
//    }
//
//    public void Z_Wave_Lights_Arm_Away_Mode(String UDID_) throws Exception {
//        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
//        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
//        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        logger.info("Test software version: " + softwareVersion());
//
//        logger.info("**********************************ZLSDD_AW_005**********************");
//        logger.info("Arm Away: Verify that the Light can be turned On from the User website");
//
//        logger.info("****************************ZLSDD_AW_006****************************");
//        logger.info("Arm Away: Verify that the Light can be turned Off from the User website");
//
//        logger.info("*****************************ZLSDD_AW_007***************************");
//        logger.info("Arm Away: Verify that the dimming level can be changed from the User website");
//
//        logger.info("**************************ZLSDD_AW_022******************************");
//        logger.info("Arm Away: Power Metering on ADC");
//
//        logger.info("**********************ZLSDD_AW_001**********************************");
//        logger.info("Arm Away: Verify that you can edit a device name (ADC)");
//
//        logger.info("*****************************ZLSDD_AW_003***************************");
//        logger.info("Arm Away: Verify that only 10 lights are being monitored on ADC");
//
//        logger.info("****************************ZLSDD_AW_004****************************");
//        logger.info("Arm Away: Verify that you can change the 10 lights being monitored on ADC");
//
//        logger.info("*****************************ZLSDD_AW_008***************************");
//        logger.info("Arm Away: Create rule engine for lights with alarm condition: Lights are Off: When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("******************************ZLSDD_AW_009**************************");
//        logger.info("Arm Away: Create rule engine for lights with alarm condition: Lights are On: When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("**********************************ZLSDD_AW_010**********************");
//        logger.info("Arm Away: Create rule engine for lights with arm/disarm conditions: Lights are Off: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn On for 5 mins, During these time frames: At all times, Select devices");
//
//        logger.info("******************************ZLSDD_AW_011**************************");
//        logger.info("Arm Away: Create rule engine for lights with arm/disarm conditions: Lights are On: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn Off, During these time frames: At all times, Select devices");
//
//        logger.info("************************************ZLSDD_AW_012********************");
//        logger.info("Arm Away: Create rule engine for lights with  Alarm condition: Lights are Off:  When this event occurs: Alarm (Panel Alarm), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("********************************ZLSDD_AW_0013************************");
//        logger.info("Arm Away: Create rule engine for lights with  Alarm condition: Lights are On: When this event occurs: Alarm (Panel Alarm), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("*****************************ZLSDD_AW_014***************************");
//        logger.info("Arm Away: Create rule engine for lights with  Alarm condition: Lights are Off: When this event occurs: Arm (Panel Arm A way), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("********************************ZLSDD_AW_015************************");
//        logger.info("Arm Away: Create rule engine for lights with  Alarm condition: Lights are Off: When this event occurs: Arm (Panel Arm S tay), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("********************************ZLSDD_AW_016************************");
//        logger.info("Arm Away: Create rule engine for lights with  Geo-fencing condition: Lights are ON: When this event occurs: People cross a Geo-Fence (Exits Home), During these time frames: At all times, Select devices, Turn OFF");
//
//        logger.info("******************************ZLSDD_AW_017**************************");
//        logger.info("Arm Away: Create rule engine for lights with  Geo-fencing condition: Lights are ON:  When this event occurs: People cross a Geo-Fence (Enters Home), During these time frames: At all times, Select devices, Turn ON");
//
//        logger.info("******************************ZLSDD_AW_021**************************");
//        logger.info("Arm Away: Create a group of lights: Turn lights ON/OFF");
//
//        logger.info("***************************ZLSDD_AW_002*****************************");
//        logger.info("Arm Away: Verify that  a Light can be deleted from the ADC website");
//
//    }
//
//
//    public void Z_Wave_Lights_Scedules(String UDID_) throws Exception {
//
//        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
//        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
//        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        logger.info("Test software version: " + softwareVersion());
//
//        logger.info("********************************************************");
//        logger.info("Schedules: Add a schedule: On these days: select; Perform these scheduled actions: Turn On based on: Time of day at -select");
//
//        logger.info("********************************************************");
//        logger.info("Schedules: Add a schedule: On these days: select; Perform these scheduled actions: Dim based on: Time of day at -select");
//
//        logger.info("********************************************************");
//        logger.info("Schedules: Add a schedule:  On these days: select; Perform these scheduled actions: Turn Off based on: Time of day at -select");
//    }
//
//    @AfterMethod
//    public void tearDown() throws IOException, InterruptedException {
//        log.endTestCase(page_name);
//        driver.quit();
//    }
//}
