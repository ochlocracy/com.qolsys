package utils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import panel.*;

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

public class Setup {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM.dd_HH.mm.ss");
    public String projectPath = new String(System.getProperty("user.dir"));
    public File appDir = new File("src");
    public AndroidDriver<WebElement> driver;
    public WebDriver driver1;
    public WebDriverWait wait;
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

    public String split_method(String str) {
        // import splitter, pass the string, convert into a list of words, add to getUDID
        String a = str.split("\\n")[1];
        return a.split("\\s")[0];
    }

    public String get_UDID() throws IOException {
        String command = ConfigProps.adbPath + " devices";
        rt.exec(command);
        String panel_UDID = split_method(execCmd(command));
        return panel_UDID;
    }

    public void setup_driver(String getUdid, String url_, String port_) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
        cap.setCapability("BROWSER_NAME", "Android");
        cap.setCapability("udid", getUdid);
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
        cap.setCapability("newCommandTimeout", "1000");
        cap.setCapability("clearSystemFiles", true);
        driver = new AndroidDriver(new URL(String.format("%s:%s/wd/hub", url_, port_)), cap);
    }

    public void setup_logger(String test_case_name) throws Exception {
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

    public WebElement element_verification(WebElement element, String element_name) throws Exception {
        try {
            if (element.isDisplayed()) {
                logger.info("Pass: " + element_name + " is present, value = " + element.getText());
            }
        } catch (Exception e) {
            take_screenshot();
            Log.error("*" + element_name + "* - Element is not found!");
            e.printStackTrace();
        } finally {
            return element;
        }
    }

    public void swipe_vertical() throws InterruptedException {
        int starty = 660;
        int endy = 260;
        int startx = 502;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    public void swipe_vertical_up() throws InterruptedException {
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

    public void navigate_to_Settings_page() {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
    }

    public void navigate_to_Advanced_Settings_page() throws InterruptedException {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        Thread.sleep(1000);
        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
        enter_default_dealer_code();
    }

    public void Navigate_To_Edit_Sensor_Page() throws IOException, InterruptedException {
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        sec.Edit_Sensor.click();
    }

    public void navigateToUserManagementPage() throws InterruptedException {
        AdvancedSettingsPage advanced = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigate_to_Advanced_Settings_page();
        advanced.USER_MANAGEMENT.click();
        Thread.sleep(1000);
    }

    public void DISARM() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Disarm");
        home_page.DISARM.click();
        enter_default_user_code();
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

    public void enter_default_DURESS_code() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.Nine.click();
        home_page.Nine.click();
        home_page.Nine.click();
        home_page.Eight.click();
    }

    public void enter_default_user_code() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Four.click();
    }

    public void enter_guest_code() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Three.click();
    }

    public void enter_default_dealer_code() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.Two.click();
        home_page.Two.click();
        home_page.Two.click();
        home_page.Two.click();
    }

    public void verify_disarm() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Disarmed_text.getText().equals("DISARMED")) {
            logger.info("Pass: System is DISARMED");
        } else {
            take_screenshot();
            logger.info("Failed: System is not DISARMED " + home_page.Disarmed_text.getText());
        }
    }

    public void verify_armstay() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Disarmed_text.getText().equals("ARMED STAY")) {
            logger.info("Pass: System is ARMED STAY");
        } else {
            take_screenshot();
            logger.info("Failed: System is NOT ARMED STAY");
        }
    }

    public void verify_armaway() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.ArwAway_State.isDisplayed()) {
            logger.info("Pass: panel is in Arm Away mode");
        } else {
            take_screenshot();
            logger.info("Failed: panel is not in Arm Away mode");
        }
    }

    public void verify_photoframe_mode() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.PhotoFrame_Mode.isDisplayed()) {
            logger.info("Pass: panel is in Photo Frame mode");
        } else {
            take_screenshot();
            logger.info("Failed: panel is not in Photo Frame mode");
        }
    }

    public void verify_in_alarm() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.ALARM.isDisplayed()) {
            logger.info("Pass: System is in ALARM");
        } else {
            take_screenshot();
            logger.info("Failed: System is NOT in ALARM");
        }
    }

    public void verify_panel_alarm() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.panel_Alarm.isDisplayed()) {
            logger.info("Pass: System is in ALARM");
        } else {
            take_screenshot();
            logger.info("Failed: System is NOT in ALARM");
        }
    }

    public void verify_sensor_is_displayed(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            logger.info(sensor_name.getText() + " is successfully opened/activated");
        } else {
            take_screenshot();
            logger.info(sensor_name + " is NOT opened/activated");
        }
    }

    public void verify_sensor_is_tampered(WebElement sensor_name) throws Exception {
        if (sensor_name.isDisplayed()) {
            logger.info(sensor_name.getText() + " is successfully tampered");
        } else {
            take_screenshot();
            logger.info(sensor_name + " is NOT tampered");
        }
    }

    public void verify_status_open() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verify_status_activated() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Activated")) {
            logger.info("Pass: Correct status is Activated");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verify_status_tampered() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verify_status_alarmed() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is Alarmed");
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public void verify_sensorstatus_inAlarm(String Al_status) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals(Al_status)) {
            logger.info("Pass: Correct status is " + Al_status);
        } else {
            take_screenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
    }

    public String Software_Version() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        AboutPage about = PageFactory.initElements(driver, AboutPage.class);
        navigate_to_Advanced_Settings_page();
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

    public void delete_all_camera_photos() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        swipeFromLefttoRight();
        swipeFromLefttoRight();
        Thread.sleep(3000);
        try {
            while (camera.Photo_lable.isDisplayed()) {
                camera.Camera_delete.click();
                camera.Camera_delete_yes.click();
                enter_default_user_code();
            }
        } catch (Exception e) {
            System.out.println("No photos to delete...");
        } finally {
        }
        swipeFromRighttoLeft();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
    }

    public void take_screenshot() throws Exception {
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
        rt.exec(ConfigProps.adbPath + " shell busybox pkill logcat");
    }

    public void autoStaySetting() throws InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        arming.Auto_Stay.click();
        Thread.sleep(1000);
        home.Home_button.click();
    }

    public void swipe_right() throws InterruptedException {
        int startY = 400;
        int endX = 660;
        int startX = 360;
        driver.swipe(startX, startY, endX, startY, 500);
        Thread.sleep(2000);
    }

    public void swipe_left() throws InterruptedException {
        int startY = 400;
        int endX = 360;
        int startX = 660;
        driver.swipe(startX, startY, endX, startY, 500);
        Thread.sleep(2000);
    }

    public void swipe_up() throws InterruptedException {
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
    public void setArmStay_NoDelay(String state) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 0 i32 21 i32 0 i32 0";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value);

        if (state.equals("Enable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is already Enabled");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is Disabled, Enabling the setting");
                rt.exec(ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 21 i32 1 i32 0 i32 0");
            }
        } else if (state.equals("Disable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is Enabled, Disabling the setting");
                rt.exec(ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 21 i32 0 i32 0 i32 0");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is already Disabled");
            }
        }
        Thread.sleep(1000);
    }

    //State is "Enable" or "Disable"
    public void setAutoStay(String state) throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 37 i32 0 i32 0 i32 20 i32 0 i32 0";
        rt.exec(command);
        String value = (execCmd(command)).toString();
        System.out.println(value);

        if (state.equals("Enable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is already Enabled");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is Disabled, Enabling the setting");
                rt.exec(ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 20 i32 1 i32 0 i32 0");
            }
        } else if (state.equals("Disable")) {
            if (value.contains("00000001")) {
                System.out.println("Setting is Enabled, Disabling the setting");
                rt.exec(ConfigProps.adbPath + " shell service call qservice 40 i32 0 i32 0 i32 20 i32 0 i32 0 i32 0");
            } else if (value.contains("00000000")) {
                System.out.println("Setting is already Disabled");
            }
        }
        Thread.sleep(1000);
    }

    public void verify_setting(String setting, String call, String expected) throws IOException {
        String result = execCmd(ConfigProps.adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            logger.info("[Pass] " + setting + " has value: " + expected);
        else
            logger.info("[Fail] " + setting + " has value: " + result + ". Expected:" + expected);

    }

    public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }


    public void delete_from_primary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(ConfigProps.adbPath + deleteFromPrimary);
        System.out.println(deleteFromPrimary);
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
}
