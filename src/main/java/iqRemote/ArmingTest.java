package iqRemote;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.HomePage;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArmingTest  {
    public AndroidDriver<WebElement> driver_remote;
    public AndroidDriver<WebElement> driver_primary;
    public AppiumDriverLocalService service;
    public Runtime rt = Runtime.getRuntime();
    Sensors s = new Sensors();
    String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(Calendar.getInstance().getTime());

    public ArmingTest() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    public void setup_driver(String url_, String port_) throws Exception {
//        DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability("deviceName", "IQRemote");
//        cap.setCapability("BROWSER_NAME", "Android");
//        cap.setCapability("udid", "6C21A2483244");
//        cap.setCapability("appPackage", "com.qolsys");
//        cap.setCapability("appActivity", "com.qolsys.themes.IQRemoteHomeActivity");
//        driver_remote = new AndroidDriver<WebElement>(new URL(url_ + ":" + port_ + "/wd/hub"), cap);


        System.out.println("\n*****killing all nodes*****\n");
        rt.exec("killall node");
        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(ConfigProps.nodePath))
                        .withAppiumJS(new File(ConfigProps.appiumPath))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "warn")
                        .withIPAddress("127.0.1.1").usingPort(4723));
//        DesiredCapabilities cap = new DesiredCapabilities();
//        cap.setCapability("deviceName", "Andorid");
//        cap.setCapability("platformName", "Android");
////        cap.setCapability("automationName", "UiAutomator2");//new
//        cap.setCapability("udid", "6C21A2483244");
//        cap.setCapability("appPackage", "com.qolsys");
//        cap.setCapability("appActivity", "com.qolsys.themes.IQRemoteHomeActivity");
//        cap.setCapability("newCommandTimeout", 10000);
//        //in case previous session was not stopped
//
//
//
//        service.stop();
//        Thread.sleep(2000);
//        service.start();
//        System.out.println("\n*****Start Appium*****\n");
//        Thread.sleep(2000);
//
//
//        driver_remote = new AndroidDriver<>(service.getUrl(), cap);
//        driver_remote.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","Android");
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("deviceName", "IQPanel");
        cap.setCapability("platformVersion", "Android");
        cap.setCapability("udid", "6C21A2483244");
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
        driver_remote = new AndroidDriver<WebElement>(new URL(url_ + ":" + port_ + "/wd/hub"), cap);
    }

    public void setup_driver1(String url_, String port_) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("platformName","Android");
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("deviceName", "IQPanel");
        cap.setCapability("platformVersion", "Android");
        cap.setCapability("udid", "6C21A2483244");
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
        driver_remote = new AndroidDriver<WebElement>(new URL(url_ + ":" + port_ + "/wd/hub"), cap);
    }

    public void swipeFromLefttoRight_primary() throws Exception {
        Thread.sleep(2000);
        int sx = (int) (driver_primary.manage().window().getSize().width * 0.90);
        int ex = (int) (driver_primary.manage().window().getSize().width * 0.10);
        int sy = driver_primary.manage().window().getSize().height / 2;
        driver_primary.swipe(ex, sy, sx, sy, 3000);
        Thread.sleep(2000);
    }

    public void swipeFromLefttoRight_remote() throws Exception {
        Thread.sleep(2000);
        int sx = (int) (driver_remote.manage().window().getSize().width * 0.90);
        int ex = (int) (driver_remote.manage().window().getSize().width * 0.10);
        int sy = driver_remote.manage().window().getSize().height / 2;
        driver_remote.swipe(ex, sy, sx, sy, 3000);
        Thread.sleep(2000);
    }

    public void add_primary_call(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
        String add_primary = " -s 8ebdbc76 shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
        rt.exec(ConfigProps.adbPath + add_primary);
    }

    public void primary_call(String DLID, String State) throws IOException {
        String primary_send = " -s 8ebdbc76 shell rfinjector 02 " + DLID + " " + State;
        rt.exec(ConfigProps.adbPath + primary_send);
        System.out.println(ConfigProps.adbPath + " " + primary_send);
    }

    public void tap(int x, int y) {
        TouchAction touch = new TouchAction(driver_primary);
        touch.tap(x, y).perform();
    }

    public void tap_remote(int x, int y) {
        TouchAction touch = new TouchAction(driver_remote);
        touch.tap(x, y).perform();
    }

    public void ARM_AWAY(int delay) throws Exception {
        HomePage home_page = PageFactory.initElements(driver_remote, HomePage.class);
        System.out.println("Arm Away");
        home_page.DISARM.click();
        home_page.ARM_AWAY.click();
        TimeUnit.SECONDS.sleep(delay);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
//        setup_driver("http://127.0.1.1", "4723");
              setup_driver1("http://127.0.1.1", "4725");
//        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }
    @Test
    public void Testsb() throws Exception{
        Thread.sleep(15000);
        ARM_AWAY(17);
        System.out.println("Swiping from left to right");
        swipeFromLefttoRight_remote();
        System.out.println("Swiping from left to right 2");
        swipeFromLefttoRight_remote();
        System.out.println("Swiping from left to right3");



    }

    @Test
    public void Test1() throws Exception {
        for (int i = 10; i > 0; i--) {
            Thread.sleep(10000);
            swipeFromLefttoRight_primary();
            System.out.println("swipe from left to right");
            Thread.sleep(2000);
            swipeFromLefttoRight_primary();
            Thread.sleep(4000);
            System.out.println("swipe from left to right");
            System.out.println("Click Mode button");
            tap(1055, 275);
            //           driver_primary.findElement(By.id("com.qolsys:id/btThermoMode")).click();
            Thread.sleep(4000);
            System.out.println("Select Heat mode, OFF mode");
//            driver.findElement(By.id("com.qolsys:id/heat")).click();
            Thread.sleep(10000);
            tap(1055, 275);
//            driver_primary.findElement(By.id("com.qolsys:id/btThermoMode")).click();
            Thread.sleep(3000);
//            driver.findElement(By.id("com.qolsys:id/off")).click();
            Thread.sleep(4000);
//        swipeFromLefttoRight_remote();
//        Thread.sleep(3000);
            //       driver_remote.findElement(By.id("com.qolsys:id/btThermoMode")).click();
            //       Thread.sleep(2000);
//        driver_remote.findElement(By.id("com.qolsys:id/off")).click();
//        Thread.sleep(5000);
            System.out.println("Open/close the door");
            swipeFromLefttoRight_primary();
            Thread.sleep(3000);
            driver_primary.findElement(By.id("com.qolsys:id/doorStatusbutton")).click();
            Thread.sleep(10000);
//        swipeFromLefttoRight_remote();
//        Thread.sleep(3000);
//        driver_remote.findElement(By.id("com.qolsys:id/doorStatusbutton")).click();
//        Thread.sleep(10000);
            swipeFromLefttoRight_primary();
            Thread.sleep(2000);
            driver_primary.findElement(By.id("com.qolsys:id/statusButton")).click();
            Thread.sleep(3000);
//        swipeFromLefttoRight_remote();
//        Thread.sleep(3000);
//        driver_remote.findElement(By.id("com.qolsys:id/statusButton")).click();
//        Thread.sleep(3000);
            swipeFromLefttoRight_primary();
            Thread.sleep(2000);
            driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            driver_primary.findElement(By.id("com.qolsys:id/img_arm_stay")).click();
            Thread.sleep(10000);
            driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            Thread.sleep(3000);
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
            Thread.sleep(5000);
            System.out.println(i);
        }
//        driver_remote.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
//        Thread.sleep(3000);
//
//        driver_remote.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
//        driver_remote.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
//        driver_remote.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
//        driver_remote.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
//        Thread.sleep(5000);
//        driver_remote.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
//        driver_remote.findElement(By.id("com.qolsys:id/img_arm_away")).click();
//        Thread.sleep(15000);
//        driver_primary.findElement(By.id("com.qolsys:id/main")).click();
//        driver_primary.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
//        driver_primary.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
//        driver_primary.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
//        driver_primary.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
//        Thread.sleep(5000);}
    }

    @Test
    public void Test1_Remote() throws Exception {
        for (int i = 20; i > 0; i--) {
            Thread.sleep(35000);
            swipeFromLefttoRight_remote();
            Thread.sleep(4000);
            System.out.println("Click Mode button");
            tap_remote(860, 207);
            Thread.sleep(4000);
            System.out.println("Select Heat mode, OFF mode");
            driver_remote.findElement(By.id("com.qolsys:id/heat")).click();
            Thread.sleep(10000);
            System.out.println(driver_remote.findElement(By.id("com.qolsys:id/uiTargTemp")).getText());
            driver_remote.findElement(By.id("com.qolsys:id/btTempUp")).click();
            Thread.sleep(2000);
            driver_remote.findElement(By.id("com.qolsys:id/btTempUp")).click();
            Thread.sleep(10000);
            driver_remote.findElement(By.id("com.qolsys:id/btTempDown")).click();
            Thread.sleep(2000);
            driver_remote.findElement(By.id("com.qolsys:id/btTempDown")).click();
            Thread.sleep(10000);
            System.out.println(driver_remote.findElement(By.id("com.qolsys:id/uiTargTemp")).getText());
            Thread.sleep(4000);
            tap_remote(860, 207);
            Thread.sleep(3000);
            driver_remote.findElement(By.id("com.qolsys:id/off")).click();
            Thread.sleep(4000);
            System.out.println("Open/close the door");
            swipeFromLefttoRight_remote();
            Thread.sleep(3000);
            driver_remote.findElement(By.id("com.qolsys:id/doorStatusbutton")).click();
            Thread.sleep(10000);
            System.out.println("Turn On/Off the light");
            swipeFromLefttoRight_remote();
            Thread.sleep(3000);
            driver_remote.findElement(By.id("com.qolsys:id/statusButton")).click();
            Thread.sleep(3000);
//        swipeFromLefttoRight_remote();
//        Thread.sleep(3000);
//        driver_remote.findElement(By.id("com.qolsys:id/statusButton")).click();
//        Thread.sleep(3000);
            swipeFromLefttoRight_remote();
            Thread.sleep(2000);
            System.out.println("Arm Stay");
            driver_remote.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            driver_remote.findElement(By.id("com.qolsys:id/img_arm_stay")).click();
            Thread.sleep(10000);
            System.out.println("Disarm");
            driver_remote.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            Thread.sleep(3000);
            driver_remote.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            driver_remote.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
            driver_remote.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
            driver_remote.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
            Thread.sleep(5000);
            System.out.println(i);
        }
    }

    @Test
    public void Test2() throws InterruptedException, IOException {
        Thread.sleep(5000);
        System.out.println(timeStamp);
        s.add_primary_call(1, 10, 123411, 1);
        s.add_primary_call(2, 10, 123412, 1);
        s.add_primary_call(3, 10, 123413, 1);
        s.add_primary_call(4, 10, 123414, 1);
        s.add_primary_call(5, 12, 123415, 1);
        s.add_primary_call(6, 12, 123416, 1);
        s.add_primary_call(7, 12, 123417, 1);
        s.add_primary_call(8, 12, 123418, 1);
        s.add_primary_call(9, 25, 123419, 1);
        s.add_primary_call(10, 25, 123420, 1);
        s.add_primary_call(11, 15, 333441, 2);
        s.add_primary_call(12, 15, 333442, 2);
        s.add_primary_call(13, 17, 333443, 2);
        s.add_primary_call(14, 17, 333444, 2);
        s.add_primary_call(15, 26, 787871, 5);
        s.add_primary_call(16, 26, 787872, 5);
        s.add_primary_call(17, 26, 787873, 5);
        s.add_primary_call(18, 26, 787874, 5);
        s.add_primary_call(19, 34, 555544, 6);
        s.add_primary_call(20, 34, 555545, 6);

        for (int i = 500; i > 0; i--) {
            Thread.sleep(5000);
            System.out.println("ArmStay");
            driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            driver_primary.findElement(By.id("com.qolsys:id/img_arm_stay")).click();
            Thread.sleep(5000);
            primary_call("01 E2 31", SensorsActivity.OPEN);
            Thread.sleep(2000);
            //       driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            //       Thread.sleep(3000);
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
            primary_call("01 E2 31", SensorsActivity.CLOSE);
            Thread.sleep(5000);
            System.out.println("ArmAway");
            driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            driver_primary.findElement(By.id("com.qolsys:id/img_arm_away")).click();
            Thread.sleep(20000);
            primary_call("01 E2 71", SensorsActivity.OPEN);
            Thread.sleep(2000);
            ///       driver_primary.findElement(By.id("com.qolsys:id/main")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
            primary_call("01 E2 71", SensorsActivity.CLOSE);
            System.out.println("Fire Emergency");
            driver_primary.findElement(By.id("com.qolsys:id/t3_emergency_icon")).click();
            Thread.sleep(3000);
            driver_primary.findElement(By.id("com.qolsys:id/tv_fire")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_emg_cancel")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
            Thread.sleep(300000);
            System.out.println("Open/close door window sensors");
            primary_call("01 E2 31", SensorsActivity.OPEN);
            primary_call("01 E2 41", SensorsActivity.OPEN);
            primary_call("01 E2 51", SensorsActivity.OPEN);
            primary_call("01 E2 61", SensorsActivity.OPEN);
            primary_call("01 E2 71", SensorsActivity.OPEN);
            primary_call("01 E2 81", SensorsActivity.OPEN);
            primary_call("01 E2 91", SensorsActivity.OPEN);
            primary_call("01 E2 A1", SensorsActivity.OPEN);
            primary_call("01 E2 B1", SensorsActivity.OPEN);
            primary_call("01 E2 C1", SensorsActivity.OPEN);
            Thread.sleep(10000);
            primary_call("01 E2 31", SensorsActivity.CLOSE);
            primary_call("01 E2 41", SensorsActivity.CLOSE);
            primary_call("01 E2 51", SensorsActivity.CLOSE);
            primary_call("01 E2 61", SensorsActivity.CLOSE);
            primary_call("01 E2 71", SensorsActivity.CLOSE);
            primary_call("01 E2 81", SensorsActivity.CLOSE);
            primary_call("01 E2 91", SensorsActivity.CLOSE);
            primary_call("01 E2 A1", SensorsActivity.CLOSE);
            primary_call("01 E2 B1", SensorsActivity.CLOSE);
            primary_call("01 E2 C1", SensorsActivity.CLOSE);
            Thread.sleep(150000);
            System.out.println("Arm Stay");
            driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            driver_primary.findElement(By.id("com.qolsys:id/img_arm_stay")).click();
            Thread.sleep(5000);
            driver_primary.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
            Thread.sleep(3000);
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
            driver_primary.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
            Thread.sleep(10000);
            System.out.println("Activate motion sensors ");
            primary_call("05 16 18", SensorsActivity.ACTIVATE);
            primary_call("05 16 28", SensorsActivity.ACTIVATE);
            primary_call("05 16 38", SensorsActivity.ACTIVATE);
            primary_call("05 16 48", SensorsActivity.ACTIVATE);
            Thread.sleep(5000);
            System.out.println(i);
        }
    }

    @Test
    public void test3() throws Exception {
        swipeFromLefttoRight_primary();
        swipeFromLefttoRight_primary();
        swipeFromLefttoRight_primary();
        swipeFromLefttoRight_primary();
        List<WebElement> li = driver_primary.findElements(By.id("com.qolsys:id/lightSelect"));
        li.get(0).click();
        li.get(1).click();
        //       driver_primary.findElement(By.id("com.qolsys:id/uiTvchkbox")).click();

    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        driver_remote.quit();
        driver_remote.quit();
        //      driver_primary.quit();
    }
}