package gridTesting;

import panel.*;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import utils.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GridSetup {

    public String new_dw_name = "NewDoor1";
    public String new_motion_name = "NewMotion1";
    public String new_smoke_name = "NewSmoke1";
    public String new_co_name = "NewCO1";
    public String new_glassbreak_name = "NewGlassBreak1";
    public String new_tilt_name = "NewTilt1";
    public String new_shock_other_name = "NewShockOther1";
    public String new_freeze_name = "NewFreeze1";
    public String new_heat_name = "NewHeat1";
    public String new_water_flood_name = "NewWaterFlood1";
    public String new_keyfob_name = "NewKeyFob1";
    public String new_keypad_name = "NewKeyPad1";
    public String new_med_pendant_name = "NewAuxiliaryPendant1";
    public String new_doorbell_name = "NewDoorBell1";
    public String new_occupancy_name = "NewOccupancy1";
    public String new_iq_shock_name = "NewIQShock1";

    final int door_window = 1;
    final int motion = 2;
    final int smoke_detector = 5;
    final int co_detector = 6;
    final int glassbreak = 19;
    final int tilt = 16;
    final int shock_IQ = 107;
    final int shock_other = 113;
    final int freeze = 17;
    final int heat = 111;
    final int water_flood = 22;
    final int keyfob = 102;
    final int keypad = 104;
    final int med_pendant = 21;
    final int doorbell = 109;
    final int occupancy = 114;


    public String adbPath = "/home/qolsys/android-sdk-linux/platform-tools/adb";
    public Runtime rt = Runtime.getRuntime();
    public Log log = new Log();
    public Logger logger = Logger.getLogger("String");

    private AndroidDriver driver;
    DesiredCapabilities capabilities = new DesiredCapabilities();


    public AndroidDriver getDriver() {
        return driver;
    }

    public GridSetup() {
        this.driver = driver;

    }

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass(alwaysRun=true)
    public void setCapabilities(String URL_) throws MalformedURLException {
        capabilities.setCapability("BROWSER_NAME", "Android");
        capabilities.setCapability("deviceName", "deviceName_");
        capabilities.setCapability("UDID", "UDID_");
        capabilities.setVersion("platformVersion_");
        capabilities.setCapability("applicationName", "applicationName_");
        capabilities.setCapability("appPackage", "com.qolsys");
        capabilities.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
        capabilities.setCapability("PORT", "PORT_");
        this.driver = new AndroidDriver(new URL(URL_), getCapabilities());
    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public void add_primary_call(int zone, int group, int sencor_dec, int sensor_type, String UDID_) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sencor_dec + " i32 " + sensor_type;
        rt.exec(adbPath + " -s " + UDID_ + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }
    public void delete_from_primary(String panel, int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(adbPath + " -s " + panel + deleteFromPrimary);
    }
    public String Software_Version () throws InterruptedException {

        TimeUnit.SECONDS.sleep(3);
        Slide_Menu menu = PageFactory.initElements(driver, Slide_Menu.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        About_page about = PageFactory.initElements(driver, About_page.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        adv.ABOUT.click();
        about.Software.click();
        WebElement soft_version = driver.findElement(By.id("com.qolsys:id/summary"));
        String current_version = soft_version.getText();
        driver.findElementById("com.qolsys:id/ft_home_button").click();
        return current_version;
    }

    public void Rename_Sensor(int number, String new_name) {
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li1 = b.findElements(By.id("com.qolsys:id/imageView1"));
        li1.get(number).click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).clear();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys(new_name);
        driver.hideKeyboard();
        driver.findElement(By.id("com.qolsys:id/addsensor")).click();
    }
    public void verify_disarm() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.Disarmed_text.getText().equals("DISARMED")) {
            logger.info("Pass: System is DISARMED");
        } else {
            //         take_screenshot();
            logger.info("Failed: System is not DISARMED " + home_page.Disarmed_text.getText());
        }
    }
    public void verify_sensor_is_displayed(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            logger.info(sensor_name.getText() +" is successfully opened/activated");
        } else {
            //         take_screenshot();
            logger.info(sensor_name +" is NOT opened/activated");}
    }
    public void verify_status_open() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        }else {
            //       take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());}
    }
    public void verify_in_alarm() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.ALARM.isDisplayed()) {
            logger.info("Pass: System is in ALARM");
        } else {
            //       take_screenshot();
            logger.info("Failed: System is NOT in ALARM");}
    }
    public void enter_default_user_code (){
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Four.click();
    }
    public void verify_sensor_is_tampered(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            logger.info(sensor_name.getText() + " is successfully tampered");
        } else {
            //        take_screenshot();
            logger.info(sensor_name +" is NOT tampered");}
    }
    public void verify_status_tampered() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        }else {
            //      take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());}
    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        driver.quit();
    }


}