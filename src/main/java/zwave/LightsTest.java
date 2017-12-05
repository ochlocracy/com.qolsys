package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by nchortek on 6/22/17.
 * Edited by JMAUS 11/3/17
 */
public class LightsTest extends Setup {
    String page_name = "Z-Wave Lights and Switches Test";
    Logger logger = Logger.getLogger(page_name);

    public LightsTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

//    @Test
//    public void Check_all_elements_on_Lights_Page() throws Exception {
//        Lights_Page lights = PageFactory.initElements(driver, Lights_Page.class);
//        swipe_left();
//
//        element_verification(lights.On_Button, "On Button");
//        element_verification(lights.Off_Button, "Off Button");
//        element_verification(lights.Get_Status_Button, "Get Status Button");
//
//        List<WebElement> li1 = driver.findElements(By.id("com.qolsys:id/lightSelect"));
//        List<WebElement> li2 = driver.findElements(By.id("com.qolsys:id/uiName"));
//        List<WebElement> li3 = driver.findElements(By.id("com.qolsys:id/statusButton"));
//
//        element_verification(li1.get(0), "Light1 Select Button");
//        element_verification(li1.get(1), "Light2 Select Button");
//        element_verification(li1.get(2), "Light3 Select Button");
//        element_verification(li2.get(0), "Light1 Name");
//        element_verification(li2.get(1), "Light2 Name");
//        element_verification(li2.get(2), "Light3 Name");
//        element_verification(li3.get(0), "Light1 Status");
//        element_verification(li3.get(1), "Light2 Status");
//        element_verification(li3.get(2), "Light3 Status");
//
//        li1.clear();
//        li2.clear();
//        li3.clear();
//    }

    @Test(priority = 1)
    public void Test_Lights_Page() throws Exception {
        // navigate to lights page and initialize variables
        LightsPage lights = PageFactory.initElements(driver, LightsPage.class);
        File light_on = new File(projectPath + "/scr/light_on");
        File light_off = new File(projectPath + "/scr/light_off");
        swipe_left();
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/lightSelect"));
        List<WebElement> status = driver.findElements(By.id("com.qolsys:id/statusButton"));

        //File screenshot = driver.getScreenshotAs(OutputType.FILE);
        //File screenshotLocation = new File(projectPath+"/scr/test");
        //FileUtils.copyFile(screenshot, screenshotLocation);
        //return;

        // check if light can be selected
        li.get(0).click();
        if (!checkAttribute(li.get(0), "checked", "true"))
            return;

        // check if light can be turned on
        lights.On_Button.click();
        Thread.sleep(6000);
        if (!checkAttribute(li.get(0), "checked", "false"))
            return;

        // check if light icon turns yellow
        if (!checkStatus(light_on, status.get(0)))
            return;

        //test dimmer functionality
        Point DimLocation = lights.Dimmer.getLocation();
        int DimWidth = lights.Dimmer.getSize().getWidth();
        int startx = DimLocation.getX();
        int starty = DimLocation.getY();
        int endx = startx + DimWidth - 10;

        touchSwipe(endx, starty, startx, starty);
        if (!checkStatus(light_off, status.get(0)))
            return;

        li.get(0).click();
        lights.On_Button.click();

        // ensure light can be selected
        li.get(0).click();
        if (!checkAttribute(li.get(0), "checked", "true"))
            return;

        // check if light is deselected upon turn-off
        lights.Off_Button.click();
        Thread.sleep(10000);
        if (!checkAttribute(li.get(0), "checked", "false")) {
            return;
        }

        // check if light icon turns grey
        if (!checkStatus(light_off, status.get(0)))
            return;

        // repeat above process but for multiple lights
        clickAll(li);

        lights.On_Button.click();
        Thread.sleep(10000);

        //check that they're deselected
        if (!checkAttribute(li.get(0), "checked", "false"))
            return;

        if (!checkAttribute(li.get(1), "checked", "false"))
            return;

        if (!checkAttribute(li.get(2), "checked", "false"))
            return;

        // check that lights turn yellow
        checkAllStatus(light_on, status);

        clickAll(li);
        lights.Off_Button.click();
        Thread.sleep(10000);

        //check that they're deselected
        if (!checkAttribute(li.get(0), "checked", "false"))
            return;

        if (!checkAttribute(li.get(1), "checked", "false"))
            return;

        if (!checkAttribute(li.get(2), "checked", "false"))
            return;

        // check that lights turn grey
        checkAllStatus(light_off, status);

        //check that the lights can be turned on/off by clicking on their status buttons
        clickAll(status);
        Thread.sleep(10000);
        checkAllStatus(light_on, status);
        clickAll(status);
        Thread.sleep(10000);
        checkAllStatus(light_off, status);

        Thread.sleep(6000);
    }

    public void Z_Wave_Lights_Disarm_Mode(String UDID_) throws Exception {
        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that a Light can be paired with a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that a new Light appears on the adc websites");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that multiple Lights can be paired with a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that you can pair max of Lights according to the set current number");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that you can edit a device name (panel)");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that you can edit a device name (adc");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that when the device is unreachable a panel recognizes the status correctly");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that the history events are displayed on the Home Control Status page");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that  a Light can be deleted from a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that  a Light can be deleted from the adc website");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that a light can be turned ON from a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that a light can be turned OFF from a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that multiple lights can be turned ON from a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that multiple lights can be turned OFF from a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that dimming level can be changed from a panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that a new Light appears on the adc websites");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that the set dimming level remains after the light been turned  ON/OFF");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that panel recognizes status change (ON)");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that panel recognizes status change (OFF)");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that only 10 lights are being monitored on adc");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that you can change the 10 lights being monitored on adc");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that the Light can be turned On from the User website");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that the Light can be turned Off from the User website");

        logger.info("********************************************************");
        logger.info("Disarm mode: Verify that the dimming level can be changed from the User website");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with alarm condition: When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn ON");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with alarm condition:  When this event occurs: Alarm (Intrusion Sensor Alarm), During these time frames: At all times, Select devices, Turn OFF");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with arm/disarm conditions: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn On for 5 mins, During these time frames: At all times, Select devices");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with arm/disarm conditions: When this event occurs: Arm/Dis arm (Dis armed), Perform this action: Turn Off, During these time frames: At all times, Select devices");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Alarm (panel Alarm), During these time frames: At all times, Select devices, Turn ON");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Alarm (panel Alarm), During these time frames: At all times, Select devices, Turn OFF");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Arm (panel Arm A way), During these time frames: At all times, Select devices, Turn ON");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with  Alarm condition: When this event occurs: Arm (panel Arm S tay), During these time frames: At all times, Select devices, Turn ON");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with  Geo-fencing condition: When this event occurs: People cross a Geo-Fence (Exits Home), During these time frames: At all times, Select devices, Turn OFF");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create rule engine for lights with  Geo-fencing condition: When this event occurs: People cross a Geo-Fence (Enters Home), During these time frames: At all times, Select devices, Turn ON");

        logger.info("********************************************************");
        logger.info("Disarm mode: Create a group of lights");

        logger.info("********************************************************");
        logger.info("Disarm mode: Power Metering on panel");

        logger.info("********************************************************");
        logger.info("Disarm mode: Power Metering on adc");
    }

    public void Z_Wave_Lights_Arm_Stay_Mode(String UDID_) throws Exception {
    }

    public void Z_Wave_Lights_Arm_Away_Mode(String UDID_) throws Exception {
    }

    public void Z_Wave_Lights_Scedules(String UDID_) throws Exception {

        logger.info("********************************************************");
        logger.info("Schedules: Add a schedule: On these days: select; Perform these scheduled actions: Turn On based on: Time of day at -select");

        logger.info("********************************************************");
        logger.info("Schedules: Add a schedule: On these days: select; Perform these scheduled actions: Dim based on: Time of day at -select");

        logger.info("********************************************************");
        logger.info("Schedules: Add a schedule:  On these days: select; Perform these scheduled actions: Turn Off based on: Time of day at -select");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
