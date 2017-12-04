package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class ActivityMonitorPageTest extends Setup {

    String page_name = "Activity Monitor page testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();

    public ActivityMonitorPageTest() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    public void swipe_vertical1() throws InterruptedException, IOException {
        int starty = 620;
        int endy = 250;
        int startx = 1000;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    public void delete_sensor(int zone) throws IOException {
        String delete = "shell service call qservice 51 i32 " + zone;
        sensors.rt.exec(ConfigProps.adbPath + " " + delete);
    }

    public void delete_all() throws IOException {
        for (int i = 3; i < 10; i++) {
            delete_sensor(i);
        }
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test(priority = 1)
    public void Check_all_elements_on_Activity_Monitor_page() throws Exception {
        ActivityMonitorPage activity = PageFactory.initElements(driver, ActivityMonitorPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        logger.info("Adding sensors...");
        sensors.add_primary_call(3, 25, 6619814, 1);
        Thread.sleep(2000);
        navigate_to_Settings_page();
        Thread.sleep(2000);
        settings.ACTIVITY_MONITOR.click();
        element_verification(activity.Quick_Access, "Quick Access");
        element_verification(activity.Quick_Access_img, "Quick Access image");
        element_verification(activity.Safty_State, "Safety State icon");
        element_verification(activity.Safety_State_txt, "Safety State text");
        if (activity.Safety_State_txt.getText().equals("Press to Deactivate")) {
            logger.info("Pass: Correct Safety state text: " + activity.Safety_State_txt.getText());
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect Safety state text: " + activity.Safety_State_txt.getText());
        }
        element_verification(activity.Safety_Active, "Safety Active tab");
        element_verification(activity.Safety_All, "Safety All tab");
        activity.Quick_Access_img.click();
        enter_default_user_code();
        Thread.sleep(2000);
        element_verification(activity.Quick_Access_CountDown, "Quick Access countdown window");
        tap(110, 620);
        Thread.sleep(1000);
        activity.Safty_State.click();
        enter_default_user_code();
        Thread.sleep(1000);
        element_verification(activity.Safty_State, "Safety State icon");
        element_verification(activity.Safety_State_txt, "Safety State text");
        if (activity.Safety_State_txt.getText().equals("Press to Activate")) {
            logger.info("Pass: Correct Safety state text: " + activity.Safety_State_txt.getText());
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect Safety state text: " + activity.Safety_State_txt.getText());
        }
        element_verification(activity.Safety_Active, "Safety Active tab");
        element_verification(activity.Safety_All, "Safety All tab");
        element_verification(activity.Safety_Bypass, "Safety Bypass tab");
        Thread.sleep(1000);
        activity.Safty_State.click();
        enter_default_user_code();
        Thread.sleep(1000);
        sensors.delete_from_primary(3);
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void Check_Activity_Monitor_behavior() throws Exception {
        ActivityMonitorPage activity = PageFactory.initElements(driver, ActivityMonitorPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        logger.info("Adding sensors...");
        sensors.add_primary_call(3, 25, 6619814, 1);
        sensors.add_primary_call(4, 8, 6619815, 1);
        sensors.add_primary_call(5, 9, 6619816, 1);
        sensors.add_primary_call(6, 25, 5570631, 2);
        sensors.add_primary_call(7, 25, 6361651, 21);
        sensors.add_primary_call(8, 25, 6405546, 109);
        sensors.add_primary_call(9, 25, 8716449, 114);
        Thread.sleep(4000);
        navigate_to_Settings_page();
        settings.ACTIVITY_MONITOR.click();
        activity.Safety_All.click();
        Thread.sleep(1000);
        WebElement dw1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Door/Window 3']"));
        verify_sensor_is_displayed(dw1);
        WebElement dw2 = driver.findElement(By.xpath("//android.widget.TextView[@text='Door/Window 4']"));
        verify_sensor_is_displayed(dw2);
        WebElement dw3 = driver.findElement(By.xpath("//android.widget.TextView[@text='Door/Window 5']"));
        verify_sensor_is_displayed(dw3);
        WebElement motion4 = driver.findElement(By.xpath("//android.widget.TextView[@text='Motion 6']"));
        verify_sensor_is_displayed(motion4);
        WebElement auxil5 = driver.findElement(By.xpath("//android.widget.TextView[@text='Auxiliary Pendant 7']"));
        verify_sensor_is_displayed(auxil5);
        WebElement doorbell6 = driver.findElement(By.xpath("//android.widget.TextView[@text='Door Bell 8']"));
        verify_sensor_is_displayed(doorbell6);
        swipe_vertical1();
        Thread.sleep(1000);
        WebElement occupancy7 = driver.findElement(By.xpath("//android.widget.TextView[@text='Occupancy Sensor 9']"));
        verify_sensor_is_displayed(occupancy7);
        Thread.sleep(2000);
        sensors.primary_call("65 02 7A", SensorsActivity.OPEN); //open  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.OPEN); //open  DW3
        Thread.sleep(2000);
        verify_in_alarm();
        Thread.sleep(1000);
        verify_sensor_is_displayed(dw2);
        verify_sensor_is_displayed(dw3);
        Thread.sleep(1000);
        enter_default_user_code();
        Thread.sleep(1000);
        sensors.primary_call("65 02 7A", SensorsActivity.CLOSE); //close  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.CLOSE); //close  DW3
        Thread.sleep(2000);
        navigate_to_Settings_page();
        settings.ACTIVITY_MONITOR.click();
        activity.Safety_All.click();
        activity.Safty_State.click();
        enter_default_user_code();
        Thread.sleep(2000);
        activity.Safety_All.click();
        tap(815, 335);
        Thread.sleep(1000);
        tap(815, 420);
        activity.Safty_State.click();
        enter_default_user_code();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        sensors.primary_call("65 02 7A", SensorsActivity.OPEN); //open  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.OPEN); //open  DW3
        Thread.sleep(1000);
        verify_disarm();
        Thread.sleep(1000);
        sensors.primary_call("65 02 7A", SensorsActivity.CLOSE); //close  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.CLOSE); //close  DW3
        Thread.sleep(2000);
        ARM_STAY();
        Thread.sleep(1000);
        sensors.primary_call("65 02 7A", SensorsActivity.OPEN); //open  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.OPEN); //open  DW3
        Thread.sleep(1000);
        verify_armstay();
        Thread.sleep(1000);
        sensors.primary_call("65 02 7A", SensorsActivity.CLOSE); //close  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.CLOSE); //close  DW3
        home.DISARM.click();
        enter_default_user_code();
        Thread.sleep(1000);
        ARM_AWAY(15);
        sensors.primary_call("65 02 7A", SensorsActivity.OPEN); //open  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.OPEN); //open  DW3
        Thread.sleep(1000);
        verify_armaway();
        Thread.sleep(1000);
        sensors.primary_call("65 02 7A", SensorsActivity.CLOSE); //close  DW2
        Thread.sleep(1000);
        sensors.primary_call("65 02 8A", SensorsActivity.CLOSE); //close  DW3
        home.ArwAway_State.click();
        enter_default_user_code();
        Thread.sleep(1000);
        delete_all();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
