package utils;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xpath.SourceTree;
import org.openqa.selenium.*;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.PageFactory;
import panel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Setup extends Driver {

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
        menu.Settings.click();
        Thread.sleep(1000);
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
        rt.exec(ConfigProps.adbPath + " shell busybox pkill logcat");
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

    public void verifySetting(String setting, String call, String expected) throws IOException {
        String result = execCmd(ConfigProps.adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            logger.info("[Pass] " + setting + " has value: " + expected);
        else
            logger.info("[Fail] " + setting + " has value: " + result + ". Expected:" + expected);

    }

    public void addPrimaryCall(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 2 i32 10 i32 6619296 i32 1
    }

    public void addPrimaryCallPG(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type + " i32 8";
        rt.exec(ConfigProps.adbPath + add_primary);
        // shell service call qservice 50 i32 100 i32 10 i32 3201105 i32 21
    }

    public void deleteFromPrimary(int zone) throws IOException, InterruptedException {
        String deleteFromPrimary = " shell service call qservice 51 i32 " + zone;
        rt.exec(ConfigProps.adbPath + deleteFromPrimary);
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
        rt.exec(ConfigProps.adbPath + add_pg);
        //shell powerg_simulator_registrator 101-0001
    }

    public void powerGactivator(int type, int id) throws IOException, InterruptedException {
        Thread.sleep(2000);
        String activate_pg = " shell powerg_simulator_activator " + type + "-" + id;
        rt.exec(ConfigProps.adbPath + activate_pg);
        Thread.sleep(2000);
        //shell powerg_simulator_activatortor 101-0001
    }

    public void pgprimaryCall(int type, int id, String status) throws IOException {
        String status_send = " shell powerg_simulator_status " + type + "-" + id + " " + status;
        rt.exec(ConfigProps.adbPath + status_send);
        System.out.println(status_send);
    }

    public void pgarmer(int type, int id, String status) throws IOException {
        String status_send = " shell powerg_simulator_armer " + type + "-" + id + " " + status;
        rt.exec(ConfigProps.adbPath + status_send);
        System.out.println(status_send);
    }

    public void powerGsupervisory(int type, int id) throws IOException {
        String status_send = " shell powerg_simulator_supervisory " + type + "-" + id + " 1 0 0 0";
        rt.exec(ConfigProps.adbPath + status_send);
        System.out.println(status_send);
    }

    public void powerGjamer(int state) throws IOException {
        String status_send = " shell powerg_simulator_jamer " + state;
        rt.exec(ConfigProps.adbPath + status_send);
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
}
