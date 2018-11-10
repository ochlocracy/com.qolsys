package utils;

import adc.ADC;
import adc.AdcDealerPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xpath.SourceTree;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import panel.*;
import zwave.DoorLockPage;
import zwave.LightsPage;
import zwave.ThermostatPage;
import zwave.ZWavePage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


import static utils.ConfigProps.adbPath;
import static utils.ConfigProps.transmitter;

public class Setup extends Driver{


    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM.dd_HH.mm.ss");
    public String projectPath = new String(System.getProperty("user.dir"));
    public File appDir = new File("src");
    public Log log = new Log();
    public Logger logger = Logger.getLogger(this.getClass().getName());
    public Runtime rt = Runtime.getRuntime();

    public Setup() throws Exception {
        ConfigProps.init();
    }

    public static String execCmd(String cmd) throws java.io.IOException {
        Process proc = Runtime.getRuntime().exec(cmd);
        InputStream is = proc.getInputStream();
        Scanner s = new Scanner(is).useDelimiter("\\A");
        String val = "";
        while (s.hasNext()) {
            val = s.next();
        }
        return val;
    }

    public static String captureScreenshot(AndroidDriver driver, String screenshotName) throws IOException {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs((OutputType.FILE));
            String dest = new String(System.getProperty("user.dir")) + "/Report/" + screenshotName + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot taken");
            return dest;
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
            return e.getMessage();
        }
    }


    public void webDriverSetUp() {
        driver1 = new FirefoxDriver();
        wait = new WebDriverWait(driver1, 40);
    }

    public String splitMethod(String str) {
        // import splitter, pass the string, convert into a list of words, add to getUDID
        String a = str.split("\\n")[1];
        return a.split("\\s")[0];
    }

    public String get_UDID() throws IOException {
        String command = adbPath + " devices";
        rt.exec(command);
        String panel_UDID = splitMethod(execCmd(command));
        return panel_UDID;
    }

    public void setupDriver(String getUdid, String url_, String port_) throws Exception {
//        DesiredCapabilities cap = new DesiredCapabilities();

//        System.out.println("\n*****killing all nodes*****\n");
//        rt.exec("killall node");
//        cap.setCapability("deviceName", "IQPanel2");
//        cap.setCapability("BROWSER_NAME", "Android");
//        cap.setCapability("udid", getUdid);
//        cap.setCapability("appPackage", "com.qolsys");
//        cap.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
//        cap.setCapability("newCommandTimeout", "1000");
//        cap.setCapability("clearSystemFiles", true);
//        driver = new AndroidDriver(new URL(String.format("%s:%s/wd/hub", url_, port_)), cap);

        System.out.println("\n*****killing all nodes*****\n");
        rt.exec("killall node");
        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(ConfigProps.nodePath))
                        .withAppiumJS(new File(ConfigProps.appiumPath))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "warn")
                        .withIPAddress("127.0.1.1").usingPort(4723));
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
        cap.setCapability("platformName", "Android");
//        cap.setCapability("automationName", "UiAutomator2");//new
        cap.setCapability("udid", get_UDID());
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
        cap.setCapability("newCommandTimeout", 1000);
        //in case previous session was not stopped



        service.stop();
        Thread.sleep(2000);
        service.start();
        System.out.println("\n*****Start Appium*****\n");
        Thread.sleep(2000);


        driver = new AndroidDriver<>(service.getUrl(), cap);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }



    public void setupLogger(String test_case_name) throws Exception {
        PropertyConfigurator.configure(new File(appDir, "/main/resources/log4j.properties").getAbsolutePath());
        log.clearLog();
        log.startTestCase(" " + test_case_name + " ");
    }

    public void swipeFromLefttoRight() throws Exception {
        Thread.sleep(2000);
        int sx = (int) (driver.manage().window().getSize().width * 0.90);
        int ex = (int) (driver.manage().window().getSize().width * 0.10);
        int sy = driver.manage().window().getSize().height / 2;
        driver.swipe(ex, sy, sx, sy, 3000);
        Thread.sleep(2000);
    }

    public void swipeFromRighttoLeft() throws Exception {
        Thread.sleep(2000);
        int sx = (int) (driver.manage().window().getSize().width * 0.90);
        int ex = (int) (driver.manage().window().getSize().width * 0.10);
        int sy = driver.manage().window().getSize().height / 2;
        driver.swipe(sx, sy, ex, sy, 3000);
        Thread.sleep(2000);
    }

    public WebElement elementVerification(WebElement element, String element_name) throws Exception {
        try {
            if (element.isDisplayed()) {
                logger.info("Pass: " + element_name + " is present, value = " + element.getText());
            }
        } catch (Exception e) {
            takeScreenshot();
            Log.error("*" + element_name + "* - Element is not found!");
            e.printStackTrace();
        } finally {
            return element;
        }
    }

    public void swipeVertical() throws InterruptedException {
        int starty = 660;
        int endy = 260;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    public void swipeVerticalUp() throws InterruptedException {
        int starty = 260;
        int endy = 660;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    public void tap(int x, int y) {
        TouchAction touch = new TouchAction(driver);
        touch.tap(x, y).perform();
    }

    public void navigateToSettingsPage() {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
    }

    public void navigateToAdvancedSettingsPage() throws InterruptedException {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        menu.Slide_menu_open.click();
        logger.info("Settings Menu");
        menu.Settings.click();
        Thread.sleep(1000);
        logger.info("Advanced Settings");
        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
        enterDefaultDealerCode();
    }

    public void navigateToPartitionsAdvancedSettingsPage() throws InterruptedException {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        Thread.sleep(1000);
        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
    }

    public void navigateToAddSensorsPage() throws InterruptedException {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        Thread.sleep(1000);
        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
        enterDefaultDealerCode();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        sec.Add_Sensor.click();
    }
    public void navigate_to_autolearn_page() throws InterruptedException {
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        SecuritySensorsPage ss = PageFactory.initElements(driver, SecuritySensorsPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        ss.Auto_Learn_Sensor.click();
        Thread.sleep(1000);
    }

    public void navigate_to_partitions_autolearn_page() throws InterruptedException {
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        SecuritySensorsPage ss = PageFactory.initElements(driver, SecuritySensorsPage.class);
        navigateToPartitionsAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        ss.Auto_Learn_Sensor.click();
        Thread.sleep(1000);
    }

    public void navigateToEditSensorPage() throws IOException, InterruptedException {
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        sec.Edit_Sensor.click();
    }

    public void navigateToUserManagementPage() throws InterruptedException {
        AdvancedSettingsPage advanced = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigateToAdvancedSettingsPage();
        advanced.USER_MANAGEMENT.click();
        Thread.sleep(1000);
    }

    public void navigateToHomePage() throws InterruptedException {
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        try {
            if (set.Home_button.isDisplayed())
                set.Home_button.click();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
    }


    public void disarmServiceCall() throws IOException {
        String servicecall = " shell service call qservice 1 i32 0 i32 0 i32 0 i32 0 i32 0 i32 1 i32 0 i32 0 i32 1";
        rt.exec(ConfigProps.adbPath + " " + servicecall);
    }

    public void DISARM() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Disarm");
        home_page.DISARM.click();
        enterDefaultUserCode();
    }

    public void ARM_STAY() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Arm Stay");
        home_page.DISARM.click();
        home_page.ARM_STAY.click();
    }

    public void ARM_AWAY(int delay) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Arm Away");
        home_page.DISARM.click();
        home_page.ARM_AWAY.click();
        TimeUnit.SECONDS.sleep(delay);
    }

    public void ARM_AWAY() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Arm Away");
        home_page.DISARM.click();
        home_page.ARM_AWAY.click();
    }

    public void enterDefaultDuressCode() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.Nine.click();
        home_page.Nine.click();
        home_page.Nine.click();
        home_page.Eight.click();
    }

    public void enterDefaultUserCode() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Four.click();
    }

    public void enterGuestCode() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Three.click();
    }

    public void enterDefaultDealerCode() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.Two.click();
        home_page.Two.click();
        home_page.Two.click();
        home_page.Two.click();
    }

    public String verifySystemState(String state) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        String panel_state = home_page.Disarmed_text.getText();
        if (home_page.Disarmed_text.getText().equals(state.toUpperCase())) {
            switch (state) {
                case "DISARMED":
                    break;
                case "ARMED STAY":
                    break;
                case "ARMED AWAY":
                    break;
            }
            return "System is: " + panel_state;

        } else {
            takeScreenshot();
            System.out.println("Failed: System is not in the expected state! Current state: " + home_page.Disarmed_text.getText());
            System.exit(0);
        }
        return "System is: " + panel_state;
    }

    public void verifyDisarm() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        try {
            if (home_page.Disarmed_text.getText().equals("DISARMED")) {
                System.out.println("Pass: System is DISARMED");
            } else {
                takeScreenshot();
                System.out.println("Failed: System is not DISARMED " + home_page.Disarmed_text.getText());
            }
        } catch (NoSuchElementException e) {
        }
    }

    public void verifyArmstay() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Disarmed_text.getText().equals("ARMED STAY")) {
            logger.info("Pass: System is ARMED STAY");
        } else {
            takeScreenshot();
            logger.info("Failed: System is NOT ARMED STAY");
        }
    }

    public void verifyArmaway() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (//home_page.ArwAway_State.isDisplayed()) {
                home_page.Disarmed_text.getText().equals("ARMED AWAY")) {    //a change appeared in rc18.1 12/19
            logger.info("Pass: panel is in Arm Away mode");
        } else {
            takeScreenshot();
            logger.info("Failed: panel is not in Arm Away mode");
        }
    }

    public void verifyPhotoframeMode() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.PhotoFrame_Mode.isDisplayed()) {
            logger.info("Pass: panel is in Photo Frame mode");
        } else {
            takeScreenshot();
            logger.info("Failed: panel is not in Photo Frame mode");
        }
    }

    public void verifyInAlarm() throws Exception {
        HomePage home_page = PageFactory.initElements(this.driver, HomePage.class);
        if (home_page.ALARM.isDisplayed()) {
            logger.info("Pass: System is in ALARM");
        } else {
            System.out.println("FAIL: System is not in ALARM");
        }
    }

    public void verifyPanelAlarm() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.panel_Alarm.isDisplayed()) {
            logger.info("Pass: System is in ALARM");
        } else {
            takeScreenshot();
            logger.info("Failed: System is NOT in ALARM");
        }
    }

    public void verifySensorIsDisplayed(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            logger.info(sensor_name.getText() + " is successfully opened/activated");
        } else {
            takeScreenshot();
            logger.info(sensor_name + " is NOT opened/activated");
        }
    }

    public void verifySensorIsTampered(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            logger.info(sensor_name.getText() + " is successfully tampered");
        } else {
            takeScreenshot();
            logger.info(sensor_name + " is NOT tampered");
        }
    }

    public void verifyStatusOpen() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verifyStatusActivated() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Activated")) {
            logger.info("Pass: Correct status is Activated");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verifyStatusTampered() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verifyStatusAlarmed() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verifySensorstatusInAlarm(String Al_status) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals(Al_status)) {
            logger.info("Pass: Correct status is " + Al_status);
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verifyOnHomePage(WebElement sensor_name) throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        if (home.System_Disarmed_State.isDisplayed()) {
            logger.info("Panel is successfully on home page");
        } else {
            swipeFromLefttoRight();
        }
        if (home.System_Disarmed_State.isDisplayed()) {
            logger.info("Panel is successfully on home page");
        } else {
            swipeFromLefttoRight();
        }
    }


    public void verifyOnCameraHomepage() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        if (!camera.All_photos.isDisplayed()) {
            logger.info("Panel is successfully on camera page");
        } else {
            swipeFromLefttoRight();
        }
        if (camera.All_photos.isDisplayed()) {
            logger.info("Panel is successfully on camera page");
        } else {
            swipeFromLefttoRight();
        }
    }

    public void verifyOnQuickSettingsHomepage() throws Exception {
        QuickSettingsPage QS = PageFactory.initElements(driver, QuickSettingsPage.class);
        if (QS.SoftwareUpdateDetailsParameter.isDisplayed()) {
            logger.info("Panel is successfully on Quick Settings");
        } else {
            swipeFromLefttoRight();
        }
        if (QS.SoftwareUpdateDetailsParameter.isDisplayed()) {
            logger.info("Panel is successfully on Quick Settings");
        } else {
            swipeFromLefttoRight();
        }
    }

    public String softwareVersion() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        AboutPage about = PageFactory.initElements(driver, AboutPage.class);
        navigateToAdvancedSettingsPage();
        adv.ABOUT.click();
        about.Software.click();
        WebElement soft_version = driver.findElement(By.id("com.qolsys:id/summary"));
        String current_version = soft_version.getText();
        driver.findElementById("com.qolsys:id/ft_home_button").click();
        return current_version;
    }

    public void renameSensor(int number, String new_name) {
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li1 = b.findElements(By.id("com.qolsys:id/imageView1"));
        li1.get(number).click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).clear();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys(new_name);
        driver.hideKeyboard();
        driver.findElement(By.id("com.qolsys:id/addsensor")).click();
    }

    public void deleteAllCameraPhotos() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        swipeFromRighttoLeft();
        Thread.sleep(2000);
        try {
            while (camera.Camera_delete.isDisplayed()) {
                camera.Camera_delete.click();
                Thread.sleep(2000);
                camera.Camera_delete_yes.click();
                enterDefaultUserCode();
            }
        } catch (Exception e) {
            System.out.println("No photos to delete...");
        }
        swipeFromLefttoRight();
        Thread.sleep(1000);
    }

    public void deleteAllSensorsOnPanel() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
        DealerSettingsPage deal = PageFactory.initElements(driver, DealerSettingsPage.class);

        System.out.println("Will delete all security sensors, not z wave, must have no partitions enabled.");

        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEALER_SETTINGS.click();
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        deal.Delete_All_Sensors.click();
        Thread.sleep(1000);

        driver.findElement(By.id("com.qolsys:id/ok")).click();

        deal.Home_Button.click();

        Thread.sleep(1000);
    }


    public void takeScreenshot() throws Exception {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            // now copy the  screenshot to desired location using copyFile //method
            FileUtils.copyFile(src, new File("src/screenshots/" + sdf.format(timestamp) + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eventLogsGenerating(String fileName, String[] findEvent, int length) throws Exception {
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < logEntries.size(); i++) {
            String log = logEntries.get(i).getMessage();
            bw.write(log);
            bw.newLine();
            displayingEvent(log, findEvent, length);
        }
        bw.close();
    }

    private void displayingEvent(String log, String[] findEvent, int length) {
        for (int i = 0; i < length; i++) {
            if (log.contains(findEvent[i])) {
                System.out.println(findEvent[i] + " RECEIVED");
            }
        }
    }

    protected void deleteLogFile(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    public void killLogcat() throws Exception {
        rt.exec(adbPath + " shell busybox pkill logcat");
    }

    public void autoStaySetting() throws InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        arming.Auto_Stay.click();
        Thread.sleep(1000);
        home.Home_button.click();
    }

    public void swipeRight() throws InterruptedException {
        int startY = 400;
        int endX = 660;
        int startX = 360;
        driver.swipe(startX, startY, endX, startY, 500);
        Thread.sleep(2000);
    }

    public void swipeLeft() throws InterruptedException {
        int startY = 400;
        int endX = 360;
        int startX = 660;
        driver.swipe(startX, startY, endX, startY, 500);
        Thread.sleep(2000);
    }

    public void swipeUp() throws InterruptedException {
        int startY = 616;
        int endY = 227;
        int startX = 314;
        driver.swipe(startX, startY, startX, endY, 3000);
        Thread.sleep(2000);
    }

    /*public void imageCaptureRotateCropById(String img, String element) throws Exception{
        logger.info("Image Capturing is in progress .......");
        WebElement ele = driver.findElement(By.id(element));
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File screenshotLocation = new File(projectPath+img);
        FileUtils.copyFile(screenshot, screenshotLocation);
        //rotateImage(screenshotLocation);
        BufferedImage  fullImg = ImageIO.read(new File(projectPath+img));
        Point point = ele.getLocation();
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        File screenshotLocation1 = new File(projectPath+img);
        FileUtils.copyFile(screenshot, screenshotLocation1);
    }*/

    public void swipe_down() throws InterruptedException {
        int startY = 227;
        int endY = 616;
        int startX = 314;
        driver.swipe(startX, startY, startX, endY, 3000);
        Thread.sleep(2000);
    }

    public boolean checkAttribute(WebElement ele, String attribute, String state) {
        if (ele.getAttribute(attribute).equals(state)) {
            logger.info("Pass: the element's attribute [" + attribute + "] is " + state);
            return true;
        } else {
            logger.info("Fail: the element's attribute [" + attribute + "] is " + ele.getAttribute(attribute));
            return false;
        }
    }

    //isnt checked, false base state
    public boolean isntChecked(WebElement ele, String attribute, String state) {
        if (ele.getAttribute(attribute).equals(state)) {
            logger.info("Pass: the element's attribute [" + attribute + "] is " + state);
            return true;
        } else {
            logger.info("Fail: the element's attribute [" + attribute + "] is " + ele.getAttribute(attribute));
            return false;
        }
    }

    //compares two images and returns whether or not they're identical
    public boolean compareImage(File fileA, File fileB) {
        try {
            // take buffer data from both image files //
            BufferedImage biA = ImageIO.read(fileA);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(fileB);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            // compare data-buffer objects //
            if (sizeA == sizeB) {
                for (int i = 0; i < sizeA; i++) {
                    if (dbA.getElem(i) != dbB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.info("Failed to compare image files ..." + e);
            return false;
        }
    }

    //takes a screenshot of the given element and saves it to the given destination
    public File takeScreenshot(WebElement ele, String dst) throws Exception {
        // Get entire page screenshot
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Get the location of element on the page
        org.openqa.selenium.Point point = ele.getLocation();

        // Get width and height of the element
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);

        // Copy the element screenshot to disk
        File screenshotLocation = new File(dst);
        FileUtils.copyFile(screenshot, screenshotLocation);

        return screenshotLocation;
    }

    //compares a given element against a provided screenshot
    public boolean checkStatus(File cmp, WebElement ele) throws Exception {
        File tmp = takeScreenshot(ele, projectPath + "/scr/tmp");
        Thread.sleep(2000);
        if (compareImage(tmp, cmp)) {
            logger.info("Pass: light icon is the expected color");
            java.lang.Runtime.getRuntime().exec("rm -f " + tmp.getAbsolutePath());
            return true;
        } else {
            logger.info("Fail: light icon is not the expected color");
            java.lang.Runtime.getRuntime().exec("rm -f " + tmp.getAbsolutePath());
            return false;
        }
    }

    //calls checkStatus on every element in a given list
    public void checkAllStatus(File status, List<WebElement> ele) throws Exception {
        int i;
        int size = ele.size();
        for (i = 0; i < size; i++)
            checkStatus(status, ele.get(i));
    }

    //clicks all of the elements contained in a given list
    public void clickAll(List<WebElement> ele) {
        int i;
        int size = ele.size();
        for (i = 0; i < size; i++)
            ele.get(i).click();
    }

    //performs a swipe using a touch action
    public void touchSwipe(int startx, int starty, int endx, int endy) {
        TouchAction a = new TouchAction(driver);
        a.longPress(startx, starty).moveTo(endx, endy).release().perform();
    }

    //State is "Enable" or "Disable"
    public void setArmStayNoDelay(String state) throws IOException, InterruptedException {
        String command = adbPath + " shell service call qservice 37 i32 0 i32 0 i32 21 i32 0 i32 0";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value);

        if (state.equals("Enable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is already Enabled");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is Disabled, Enabling the setting");
                rt.exec(adbPath + " shell service call qservice 40 i32 0 i32 0 i32 21 i32 1 i32 0 i32 0");
            }
        } else if (state.equals("Disable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is Enabled, Disabling the setting");
                rt.exec(adbPath + " shell service call qservice 40 i32 0 i32 0 i32 21 i32 0 i32 0 i32 0");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is already Disabled");
            }
        }
        Thread.sleep(1000);
    }

    //State is "Enable" or "Disable"
    public void setAutoStay(String state) throws IOException, InterruptedException {
        String command = adbPath + " shell service call qservice 37 i32 0 i32 0 i32 20 i32 0 i32 0";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value);

        if (state.equals("Enable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is already Enabled");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is Disabled, Enabling the setting");
                rt.exec(adbPath + " shell service call qservice 40 i32 0 i32 0 i32 20 i32 1 i32 0 i32 0");
            }
        } else if (state.equals("Disable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is Enabled, Disabling the setting");
                rt.exec(adbPath + " shell service call qservice 40 i32 0 i32 0 i32 20 i32 0 i32 0 i32 0");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is already Disabled");
            }
        }
        Thread.sleep(1000);
    }

    public void verifySetting(String setting, String call, String expected) throws IOException {
        String result = execCmd(adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            logger.info("[Pass] " + setting + " has value: " + expected);
        else
            logger.info("[Fail] " + setting + " has value: " + result + ". Expected:" + expected);

    }

    public void addPrimaryCall(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }
    public void addPrimaryCall(String transmitterID,int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " -s " + transmitterID + " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(adbPath + add_primary);
        System.out.println(add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }

    public void addPrimaryCallPG(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type + " i32 8";
        rt.exec(adbPath + add_primary);
        // shell service call qservice 50 i32 100 i32 10 i32 3201105 i32 21
    }

    public void deleteFromPrimary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);
    }

    public void DeleteAllPGsesors() throws Exception {
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        SecuritySensorsPage ss = PageFactory.initElements(driver, SecuritySensorsPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        //      ss.Remove_All_Powerg_Sensors.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@index='2']")).click();
        Thread.sleep(5000);
        // ss.OK.click();
        // driver.findElement(By.xpath("//android.widget.TextView[@text='OK']")).click();
        swipeFromLefttoRight();
        //driver.findElementById("com.qolsys:id/ok").click();
    }

    public void addPGSensors(String sensor, int Type, int Id, int gn) throws IOException, InterruptedException {
        Thread.sleep(1000);
        powerGregistrator(Type, Id);
        Thread.sleep(3000);
        driver.findElementById("com.qolsys:id/ok").click();

        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/sensor_desc")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys(sensor + " " + Type + "-" + Id);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            //       e.printStackTrace();
        }
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/grouptype")).click();
        List<WebElement> gli = driver.findElements(By.id("android:id/text1"));
        gli.get(gn).click();

        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(1000);
        powerGactivator(Type, Id);
        Thread.sleep(2000);
    }

    public void addPGSensors(String sensor, int Type, int Id, String gn, String text) throws IOException, InterruptedException {
        Thread.sleep(1000);
        powerGregistrator(Type, Id);
        Thread.sleep(3000);
        driver.findElementById("com.qolsys:id/ok").click();

        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/sensor_desc")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys(sensor + " " + Type + "-" + Id);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            //       e.printStackTrace();
        }
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/grouptype")).click();
        driver.scrollTo(gn);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='"+ text+"']")).click();
        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(1000);
        Thread.sleep(1000);
        powerGactivator(Type, Id);
        Thread.sleep(2000);
    }


    public void powerGregistrator(int type, int id) throws IOException {
        String add_pg = " shell powerg_simulator_registrator " + type + "-" + id;
        rt.exec(adbPath + add_pg);
        //shell powerg_simulator_registrator 101-0001
    }

    public void powerGactivator(int type, int id) throws IOException, InterruptedException {
        Thread.sleep(2000);
        String activate_pg = " shell powerg_simulator_activator " + type + "-" + id;
        rt.exec(adbPath + activate_pg);
        Thread.sleep(2000);
        //shell powerg_simulator_activatortor 101-0001
    }

    public void pgprimaryCall(int type, int id, String status) throws IOException {
        String status_send = " shell powerg_simulator_status " + type + "-" + id + " " + status;
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void pgarmer(int type, int id, String status) throws IOException {
        String status_send = " shell powerg_simulator_armer " + type + "-" + id + " " + status;
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void powerGsupervisory(int type, int id) throws IOException {
        String status_send = " shell powerg_simulator_supervisory " + type + "-" + id + " 1 0 0 0";
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void powerGjamer(int state) throws IOException {
        String status_send = " shell powerg_simulator_jamer " + state;
        rt.exec(adbPath + status_send);
        System.out.println(status_send);
    }

    public void activation_restoration(int type, int id, String status1, String status2) throws InterruptedException, IOException {
        pgprimaryCall(type, id, status1);
        Thread.sleep(5000);
        pgprimaryCall(type, id, status2);
        Thread.sleep(1000);
    }

    public void deleteReport() {
        try {
            File file = new File(projectPath + "Report/SanityReport.html");
            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Failed to delete!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean alarmVerification(String sensor_name) throws Exception {
        swipeFromRighttoLeft();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='ALARMS']")).click();
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.xpath("//android.widget.TextView[@text='" + sensor_name + "']"));
        List<WebElement> date_time = driver.findElements(By.id("com.qolsys:id/type"));
        try {
            if (element.isDisplayed()) {
                System.out.println("Pass: sensor alarm is displayed " + sensor_name);
                System.out.println(date_time.get(1).getText());
                swipeFromLefttoRight();
            }
            return true;
        } catch (NoSuchElementException e) {
        }
        return false;

    }

    public void addDwSensor(int numOfDw) throws IOException, InterruptedException {
        int dlid = 12345;
        int new_dlid = ++dlid;
        logger.info("adding "+ numOfDw +" Door Windows sensors");
        for(int i = 1; i <= numOfDw; i++){
            addPrimaryCall(i, 10, ++new_dlid, 1);
            System.out.println(i + " " + new_dlid);
            Thread.sleep(1000);
        }
    }
    public void addDwSensor(String transmitterID, int numOfDw) throws IOException, InterruptedException {
        int dlid = 12345;
        int new_dlid = ++dlid;
        logger.info("adding "+ numOfDw +" Door Windows sensors");
        for(int i = 1; i <= numOfDw; i++){
            addPrimaryCall(transmitterID, i, 10, ++new_dlid, 1);
            System.out.println(i + " " + new_dlid);
            Thread.sleep(1000);
        }
    }

//*********************************************Report Methods***************************************************



    //************Sanity reporting


    //Creating report

//    public void sanityReportSetup(String nameOfReporter)throws Exception{
//        String file = projectPath + "/extent-config-Sanity.xml";
//        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
//        report.loadConfig(new File(file));
//        report
//                .addSystemInfo("User Name", nameOfReporter)
//                .addSystemInfo("Software Version", softwareVersion());
//
//        log = report.startTest("Zwave.Light");
//    }

    //Adding to Report

    //    public void addToSanityReport(String testName){
//        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
//        log = report.startTest(testName);
//    }

    //Deleting report


//    public void deleteSanityReport() {
//        try {
//            File file = new File(projectPath + "Report/SanityReport.html");
//            if (file.delete()) {
//                System.out.println(file.getName() + " is deleted!");
//            } else {
//                System.out.println("Failed to delete!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //*************Smoke Reporting

    //Creating report

    //    public void smokeReportSetup(String nameOfReporter)throws Exception{
//        String file = projectPath + "/extent-config-Smoke.xml";
//        report = new ExtentReports(projectPath + "/Report/SmokeReport.html", false);
//        report.loadConfig(new File(file));
//        report
//                .addSystemInfo("User Name", nameOfReporter)
//                .addSystemInfo("Software Version", softwareVersion());
//
//        log = report.startTest("Zwave.Light");
//    }


    //Adding to Report

    //    public void addToSmokeReport(String testName){
//        report = new ExtentReports(projectPath + "/Report/SmokeReport.html", false);
//        log = report.startTest(testName);
//    }


//    public void deleteSmokeReport() {
//        try {
//            File file = new File(projectPath + "Report/SmokeReport.html");
//            if (file.delete()) {
//                System.out.println(file.getName() + " is deleted!");
//            } else {
//                System.out.println("Failed to delete!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    // ********* Regression Reporting

    //Creating Report

//    public void regressionReportSetup(String nameOfReporter)throws Exception{
//        String file = projectPath + "/extent-config-Regression.xml";
//        report = new ExtentReports(projectPath + "/Report/RegressionReport.html", false);
//        report.loadConfig(new File(file));
//        report
//                .addSystemInfo("User Name", nameOfReporter)
//                .addSystemInfo("Software Version", softwareVersion());
//
//        log = report.startTest("Zwave.Light");
//    }


    //Adding to Report

    //    public void addToRegressionReport(String testName){
//        report = new ExtentReports(projectPath + "/Report/RegressionReport.html", false);
//        log = report.startTest(testName);
//    }


    // Deleting Report


//    public void deleteRegressionReport() {
//        try {
//            File file = new File(projectPath + "Report/RegressionReport.html");
//            if (file.delete()) {
//                System.out.println(file.getName() + " is deleted!");
//            } else {
//                System.out.println("Failed to delete!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }








    //**************************************Z-Wave Methods*******************************************************
    //Transmitter methods

    public void editRemoteName()throws Exception{
        String editBtn = "ctl00_phBody_ZWaveDeviceList_btnEditDeviceNames";
        String editTxtBox = "ctl00_phBody_ZWaveDeviceList_EditDeviceNames_rptAddedDevices_ctl00_txtDeviceName";
    }

    public void editRemoteDeviceName(String deviceName) throws Exception{
        AdcDealerPage dealerPage = PageFactory.initElements(driver1, AdcDealerPage.class);
        TimeUnit.SECONDS.sleep(3);
        logger.info("editing name in empower page");
        dealerPage.empowerEditNameBtn.click();
//        driver.findElement(By.id("ctl00_phBody_ZWaveDeviceList_btnEditDeviceNames")).click();
        logger.info("clicking 3rd device name");
        driver.findElement(By.id("ctl00_phBody_ZWaveDeviceList_EditDeviceNames_rptAddedDevices_ctl02_txtDeviceName")).clear();
        logger.info("editing device name to 3rd device");
        driver.findElement(By.id("ctl00_phBody_ZWaveDeviceList_EditDeviceNames_rptAddedDevices_ctl02_txtDeviceName")).sendKeys(deviceName);
        logger.info("saving new name ");
        dealerPage.saveEditNameBtn.click();
//        driver.findElement(By.id("ctl00_phBody_ZWaveDeviceList_EditDeviceNames_btn_SaveDeviceNames")).click();
        TimeUnit.SECONDS.sleep(5);
    }


    // i might not need this method with the use of the waitForTextInElement method
    public String verifyOneDeviceAdded() throws Exception{
        AdcDealerPage dealerPage = PageFactory.initElements(driver1, AdcDealerPage.class);
        TimeUnit.SECONDS.sleep(5);
        logger.info("Verifying new device is added");
        logger.info("Waiting for new device name to show");
        while (!dealerPage.newlyAddDeviceName.isDisplayed()){
            logger.info("device name not available. waiting 3 second");
            TimeUnit.SECONDS.sleep(3);
        }

        String remoteDeviceName = dealerPage.newlyAddDeviceName.getText();
        logger.info("device name is " + remoteDeviceName);
        TimeUnit.SECONDS.sleep(2);
        return remoteDeviceName;
    }



    public void addRemoteZwaveDoorLock(int numOfDoorLocks) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        AdcDealerPage adcPage = new AdcDealerPage();
        for (int i=1; i<= numOfDoorLocks; i++) {
            String deviceNames = "ctl00_phBody_ZWaveDeviceList_EditDeviceNames_rptAddedDevices_ctl0"+i+"_txtDeviceName";
            Thread.sleep(2000);
            logger.info("Adding door lock number " + i);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        }
    }
    public void getAddModeMessage() throws Exception{
        String addModeMessage = "ctl00_phBody_ZWaveRemoteAddDevices_lblAddStatus";
        logger.info("Getting mode message");
        String modeMessage = driver1.findElement(By.id(addModeMessage)).getText();
        while(!modeMessage.contains("The panel is in Add Mode")) {
            logger.info("Panel is not in add mode. Wating 5 seconds");
            Thread.sleep(5000);
            modeMessage = driver1.findElement(By.id(addModeMessage)).getText();
        }
        logger.info("Panel is in add mode. Ready to add a new device");
        TimeUnit.SECONDS.sleep(3);
    }


    // bridge will be included to the Gen2 an nodeID 2
    public void localIncludeBridge() throws Exception {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Navigating to setting");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.zwaveDevices.click();
        logger.info("Clearing Transmitter");
        System.out.println("Clearing Transmitter");
        zwave.clearDeviceZwavePage.click();
        Thread.sleep(4000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.excludeTransmitter);
        System.out.println(adbPath + " -s " + transmitter + " " + ZTransmitter.excludeTransmitter);
        Thread.sleep(6000);
        zwave.oKBtnZwaveRemoveAllDevicesPage.click();
        logger.info("Removing all devices");
        zwave.removeAllDevicesZwavePage.click();
//        Thread.sleep(4000);
        waitForElement(driver, zwave.oKBtnZwaveRemoveAllDevicesPage,30);
        zwave.oKBtnZwaveRemoveAllDevicesPage.click();
        waitForElement(driver, zwave.oKBtnZwaveRemoveAllDevicesPage,30);
//        Thread.sleep(4000);
        zwave.oKBtnZwaveRemoveAllDevicesPage.click();
        logger.info("All Devices have been removed");
        Thread.sleep(1000);
        logger.info("Including transmitter");
        zwave.addDeviceZwavePage.click();
        Thread.sleep(2000);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(7000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        System.out.println(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        logger.info("Waiting for Element");
        waitForElement(driver, zwave.newDevicePageAddBtn, 90); //test of wait
        zwave.nameSelectionDropDown.click();
        zwave.customDeviceName.click();
        logger.info("Naming transmitter");
        Thread.sleep(2000);
        zwave.customNameField.sendKeys("Transmitter");
        try {
            driver.hideKeyboard();
            logger.info("Hidding Keyboard");
        } catch (Exception e) {
            logger.info("Keyboard was Present");
        }
        zwave.newDevicePageAddBtn.click();
        Thread.sleep(2000);
        zwave.UnsupportedDeviceAcknowledgement.click();
        logger.info("Transmitter has been added successfully");
        Thread.sleep(2000);
        home.Home_button.click();
        Thread.sleep(2000);
    }

    public void thermostatDown5() {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        System.out.println("Changing Thermostat Temp Down By 5 ");
        for (int i = 0; i <= 4; i++) {
            therm.tempDown.click();
        }
    }

    public void thermostatUp5() {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        System.out.println("Changing Thermostat Temp Down By 5 ");
        for (int i = 0; i <= 4; i++) {
            therm.tempUp.click();
        }
    }

    //****************************swiping to devices page********************
    //have to crate an instances of the DoorLockPage with the name locksPage to use this method
    public void swipeLightsPage(LightsPage lights) throws Exception {
        while (!isOnLightsPage(lights)) {
            swipeFromRighttoLeft();
        }
    }

    private boolean isOnLightsPage(LightsPage lights) {
        try {
            System.out.println("Checking For Lights Page ");
            return lights.allOnBtn.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Handling NoSuchElementException");
            return false;
        }
    }

    //have to crate an instances of the DoorLockPage with the name locksPage to use this method
    public void swipeToDoorLockPage(DoorLockPage locksPage) throws Exception {
        while (!isOnDoorLockPage(locksPage))
            logger.info("Swipping left");
            swipeFromRighttoLeft();
    }

    private boolean isOnDoorLockPage(DoorLockPage locksPage) {
        try {
            System.out.println("Checking For Door Lock Page ");
            return locksPage.unloackAllTxt.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("This is not the Door Lock  Page");
            System.out.println("Handling NoSuchElementException");
            return false;
        }
    }

    //have to crate an instances of the ThermostatPage with the name locksPage to use this method
    public void swipeToThermostatPage(ThermostatPage therm) throws Exception {
        while (!isOnThermostatPage(therm)) {
            swipeFromRighttoLeft();
        }
    }

    private boolean isOnThermostatPage(ThermostatPage therm) {
        try {
            System.out.println("Checking For Thermostat Page ");
            return therm.Current_Temp_Txt.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("This is not the Thermostat Page");
            System.out.println("Handling the exception");
            return false;
        }
    }

    // *******need to finish swipe to GDC******
//
//    public void swipeToGdcPage(GaragePage GdcPage) throws Exception {
//        while (!isOnGdcPage(GdcPage)) {
//            swipeFromRighttoLeft();
//        }
//    }
//
//    private boolean isOnGdcPage(GaragePage GdcPage) {
//        try {
//            System.out.println("Checking For GDC Page ");
//            return GdcPage.Current_Temp_Txt.isDisplayed();
//        } catch (NoSuchElementException e) {
//            System.out.println("This is not the GDC Page");
//            System.out.println("Handling the exception");
//            return false;
//        }
//    }


    public void zwaveSettingReset(String transmitterId) throws Exception {
        PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
        logger.info("resetting all device limits to factory litmits");
        logger.info("Setting thermostat limit to 3");
        servcall.setDeviceLimitThermostatWithTrans(3,transmitterId);
        logger.info("Setting smartsocket limit to 0");
        servcall.setDeviceLimitSmartSocketWithTrans(0,transmitterId);
        logger.info("Setting Lihgts limit to 5");
        servcall.setDeviceLimitLightsWithTrans(5,transmitterId);
        logger.info("Setting Door Lock Limit to 3");
        servcall.setDeviceLimitDoorLockWithTrans(3,transmitterId);
        logger.info("Setting Other Device Limit to 3");
        servcall.setDeviceLimitOtherDevicesWithTrans(3,transmitterId);
        logger.info("Setting Garage Door Limit to 3");
        servcall.setDeviceLimitGarageDoorWithTrans(3,transmitterId);
    }

    public void zwaveChipReset(String transmitterId) throws Exception {
        PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
//
        servcall.setZwaveOnOff(1);
        servcall.getZwaveOnOff();
    }



        public void localZwaveAdd() throws IOException, InterruptedException {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        logger.info("adding new device locally ");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.zwaveDevices.click();
        zwave.addDeviceZwavePage.click();
        Thread.sleep(4000);
        logger.info("ready to add new device");
    }

    public void removeAllDevices() throws Exception {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        logger.info("Removing All Devices");
        navigateToAdvancedSettingsPage();
        logger.info("entering Advance Installation");
        adv.INSTALLATION.click();
        logger.info("entering devices");
        Install.DEVICES.click();
        logger.info("selecting zwave devices");
        dev.zwaveDevices.click();
        logger.info("removing all devices");
        zwave.removeAllDevicesZwavePage.click();
        logger.info("selecting ok button");
        zwave.oKBtnZwaveRemoveAllDevicesPage.click();
        Thread.sleep(1000);
        zwave.oKBtnZwaveRemoveAllDevicesPage.click();
        Thread.sleep(1000);
        logger.info("All devices have been removed");
    }


//***************"Wait for" methods***************

    public void waitForText(WebDriver driver, By element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        logger.info("wating for text");
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));

    }

    public void waitForTextInElement(WebDriver driver, WebElement element, String deviceType,int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        logger.info("wating for text in the element");
        wait.until(ExpectedConditions.textToBePresentInElement(element,deviceType));
    }

    public void waitForElementText(WebDriver driver, WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        logger.info("waiting for element text");
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    public void waitForElement(WebDriver driver, WebElement element, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        logger.info("waiting for element to perform next action");
        wait.until(ExpectedConditions.visibilityOf(element));
    }



    public void waitForElementnClick(WebDriver driver, WebElement element, WebElement clickElement, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        logger.info("waiting for " + element + " for next action");
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.info(element + " is present. Clicking on " + element);
        clickElement.click();
    }

    //*************Adding Custom Devices *************
    public void addCustomZwaveLight(String... deviceNames) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceNames) {
            logger.info("Adding " + deviceName);
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            logger.info("wating for add button to be displayed ");
            waitForElement(driver, zwave.newDevicePageAddBtn, 60);
            zwave.nameSelectionDropDown.click();
            logger.info("Selecting custom name");
            zwave.customDeviceName.click();
            zwave.customNameField.sendKeys(deviceName);
            try {
                driver.hideKeyboard();
                logger.info("Hidding Keyboard");
            } catch (Exception e) {
                logger.info("Keyboard was Present");
            }
            zwave.newDevicePageAddBtn.click();
            logger.info("add complete for " + deviceName);
            System.out.println("add complete for " + deviceName);
        }
        //Add assertion and logger message
        //add a retry if add process fails
    }

    public void addCustomZwaveDimmer(String... deviceNames) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceNames) {
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            logger.info("Adding " + deviceName);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 4");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 4");
            logger.info("wating for add button to be displayed ");
            waitForElement(driver, zwave.newDevicePageAddBtn, 60);
            zwave.nameSelectionDropDown.click();
            logger.info("Selecting custom name");
            zwave.customDeviceName.click();
            zwave.customNameField.sendKeys(deviceName);
            try {
                driver.hideKeyboard();
                logger.info("Hidding Keyboard");
            } catch (Exception e) {
                logger.info("Keyboard was Present");
            }
            zwave.newDevicePageAddBtn.click();
            logger.info("add complete for " + deviceName);
            System.out.println("add complete for " + deviceName);
        }
        //Add assertion and logger message
        //add a retry if add process fails
    }

    public void addCustomZwaveDoorLock(String... deviceNames) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceNames) {
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            logger.info("Adding " + deviceName);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            logger.info("wating for add button to be displayed ");
            waitForElement(driver, zwave.newDevicePageAddBtn, 60);
            zwave.nameSelectionDropDown.click();
            zwave.customDeviceName.click();
            zwave.customNameField.sendKeys(deviceName);
            try {
                driver.hideKeyboard();
                logger.info("Hidding Keyboard");
            } catch (Exception e) {
                logger.info("Keyboard was Present");
            }
            zwave.newDevicePageAddBtn.click();
            logger.info("add complete for " + deviceNames);
            System.out.println("add complete for " + deviceNames);
        }
        //Add assertion and logger message
        //add a retry if add process fails
    }
    public void addCustomMaxZwaveDoorLockFailure(String... deviceNames) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceNames) {
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            logger.info("Adding " + deviceName);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            logger.info("wating for max device button to be displayed ");
            waitForElement(driver, zwave.maxDeviceAcknowledgement, 60);
            zwave.maxDeviceAcknowledgement.click();
            logger.info("max number door locks have been reached");
        }
    }
    public void addCustomZwaveDoorLockNode(String... deviceTypeName) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceTypeName) {
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            logger.info("Adding " + deviceName);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
            logger.info("wating for add button to be displayed ");
            waitForElement(driver, zwave.newDevicePageAddBtn, 60);
            String deviceId = zwave.nodeId.getText();
            zwave.nameSelectionDropDown.click();
            zwave.customDeviceName.click();
            zwave.customNameField.sendKeys(deviceName+deviceId);
            try {
                driver.hideKeyboard();
                logger.info("Hidding Keyboard");
            } catch (Exception e) {
                logger.info("Keyboard was Present");
            }
            zwave.newDevicePageAddBtn.click();
            logger.info("add complete for " + deviceTypeName);

        }
    }

    public void addCustomZwaveThermostat(String... deviceNames) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceNames) {
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            logger.info("Adding " + deviceName);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
            logger.info("wating for add button to be displayed ");
            waitForElement(driver, zwave.newDevicePageAddBtn, 60);
            zwave.nameSelectionDropDown.click();
            logger.info("Selecting custom name");
            zwave.customDeviceName.click();
            zwave.customNameField.sendKeys(deviceName);
            try {
                driver.hideKeyboard();
                logger.info("Hidding Keyboard");
            } catch (Exception e) {
                logger.info("Keyboard was Present");
            }
            zwave.newDevicePageAddBtn.click();
            logger.info("add complete for " + deviceName);
            System.out.println("add complete for " + deviceName);
        }
        //Add assertion and logger message
        //add a retry if add process fails
    }


    public void addCustomZwaveGdc(String... deviceNames) throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        for (String deviceName : deviceNames) {
            zwave.includeZwaveDeviceButton.click();
            Thread.sleep(2000);
            logger.info("Adding " + deviceName);
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 6");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 6");
            logger.info("waiting for add button to be displayed ");
            waitForElement(driver, zwave.newDevicePageAddBtn, 60);
            zwave.nameSelectionDropDown.click();
            logger.info("Selecting custom name");
            zwave.customDeviceName.click();
            zwave.customNameField.sendKeys(deviceName);
            try {
                driver.hideKeyboard();
                logger.info("Hiding Keyboard");
            } catch (Exception e) {
                logger.info("Keyboard was Present");
            }
            zwave.newDevicePageAddBtn.click();
            logger.info("add complete for " + deviceName);
            System.out.println("add complete for " + deviceName);
        }
        //Add assertion and logger message
        //add a retry if add process fails
    }

    //***************Add Stock Devices***********************

    public void addStockNameLight()throws Exception{
        ZWavePage zwave = PageFactory.initElements(driver,ZWavePage.class);
        zwave.includeZwaveDeviceButton.click();
        TimeUnit.SECONDS.sleep(5);
        logger.info("Adding Stock Light");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
        logger.info("waiting for ADD button to appear" );
        waitForElement(driver,zwave.newDevicePageAddBtn, 60);
        zwave.nameSelectionDropDown.click();
        logger.info("Selecting Light as a stock name");
        zwave.lightStockName.click();
        Thread.sleep(2000);
        zwave.newDevicePageAddBtn.click();
        logger.info("Light has been added");
        Thread.sleep(2000);
        //Add assertion and logger message
        //add a retry if add process fails
    }

    public void addStockNameBedroomLight()throws Exception{
        ZWavePage zwave = PageFactory.initElements(driver,ZWavePage.class);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(2000);
        logger.info("Adding Stock Bedroom Light");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
        logger.info("waiting for ADD button to appear" );
        waitForElement(driver,zwave.newDevicePageAddBtn, 60);
        zwave.nameSelectionDropDown.click();
        logger.info("Selecting Bedroom Light as a name");
        zwave.bedroomLightStockName.click();
        Thread.sleep(2000);
        zwave.newDevicePageAddBtn.click();
        logger.info("Bedroom Light has been added");
        Thread.sleep(2000);
        //Add assertion and logger message
        //add a retry if add process fails
    }



    public void addStockNameFrontDoor()throws Exception{
        ZWavePage zwave = PageFactory.initElements(driver,ZWavePage.class);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(2000);
        logger.info("Adding Stock Front Door");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        logger.info("waiting for ADD button to appear" );
        waitForElement(driver,zwave.newDevicePageAddBtn, 60);
        zwave.nameSelectionDropDown.click();
        logger.info("Selecting Front Door as a name");
        zwave.frontDoorStockName.click();
        Thread.sleep(2000);
        zwave.newDevicePageAddBtn.click();
        logger.info("Front Door has been added successfully");
        Thread.sleep(2000);
        //Add assertion and logger message
        //add a retry if add process fails
    }

    public void addStockNameBackDoor()throws Exception{
        ZWavePage zwave = PageFactory.initElements(driver,ZWavePage.class);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(2000);
        logger.info("Adding Stock Back Door");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        logger.info("waiting for ADD button to appear" );
        waitForElement(driver,zwave.newDevicePageAddBtn, 60);
        zwave.nameSelectionDropDown.click();
        logger.info("Selecting Back Door as a name");
        zwave.backDoorStockName.click();
        Thread.sleep(2000);
        zwave.newDevicePageAddBtn.click();
        logger.info("Back Door has been added successfully");
        Thread.sleep(2000);
        //Add assertion and logger message
        //add a retry if add process fails
    }

    public void addStockNameGarageDoor()throws Exception{
        ZWavePage zwave = PageFactory.initElements(driver,ZWavePage.class);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(2000);
        logger.info("Adding Stock Garage Door");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        logger.info("waiting for ADD button to appear" );
        waitForElement(driver,zwave.newDevicePageAddBtn, 60);
        zwave.nameSelectionDropDown.click();
        logger.info("Selecting Garage Door as a name");
        zwave.garageDoorStockName.click();
        Thread.sleep(2000);
        zwave.newDevicePageAddBtn.click();
        logger.info("Garage Door has been added");
        Thread.sleep(2000);
        //Add assertion and logger message
        //add a retry if add process fails
    }

    public void addStockNameSideDoor()throws Exception{
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(2000);
        logger.info("Adding Stock Side Door");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        logger.info("waiting for ADD button to appear" );
        waitForElement(driver,zwave.newDevicePageAddBtn, 60);
        zwave.nameSelectionDropDown.click();
        logger.info("Selecting Side Door as a name");
        zwave.sideDoorStockName.click();
        Thread.sleep(2000);
        zwave.newDevicePageAddBtn.click();
        logger.info("Side Door has been added");
        Thread.sleep(2000);
        //Add assertion and logger message
        //add a retry if add process fails
    }

//    public void preTestSetup(String testName) throws Exception {
//        addToSanityReport(testName);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        //remove all zwave devices
//        log.log(LogStatus.INFO, "Removing all device");
//        removeAllDevices();
//        log.log(LogStatus.PASS, "All devices have been removed");
//        //change all zwave settings to default
//        log.log(LogStatus.INFO,"Changing all Zwave setting to default");
//        zwaveSettingReset("ac8312fb");
//        log.log(LogStatus.PASS, "Zwave settings set to default");
//        //Add 3 door window sensor and call it Front Door, back door, bathroom window
//        log.log(LogStatus.INFO, "Adding 3 door window sensors");
//        addDwSensor("ac8312fb", 3);
//        log.log(LogStatus.PASS, "3 DW sensors added successfully");
//        home.Home_button.click();
//    }
}

