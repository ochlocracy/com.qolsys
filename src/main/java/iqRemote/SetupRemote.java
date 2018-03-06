package iqRemote;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import panel.HomePage;
import panel.PanelCameraPage;
import utils.ConfigProps;
import utils.Setup;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SetupRemote {

    public AndroidDriver driver;
    public Runtime rt = Runtime.getRuntime();
    Setup setup = new Setup();
    String logcat = new String(System.getProperty("user.dir")) + "/log/test.txt";

    public SetupRemote() throws Exception {
        ConfigProps.init();
    }

    public void setup_driver(String udid_, String url_, String port_) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
        cap.setCapability("BROWSER_NAME", "Android");
        cap.setCapability("udid", udid_);
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.MainActivity");
        cap.setCapability("newCommandTimeout", "1000");
        driver = new AndroidDriver(new URL(String.format("%s:%s/wd/hub", url_, port_)), cap);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void DISARM() throws InterruptedException {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Disarm");
        home_page.DISARM.click();
        Thread.sleep(2000);
        enterDefaultUserCode();
    }

    public void ARM_STAY() throws InterruptedException {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Stay");
        home_page.DISARM.click();
        Thread.sleep(2000);
        home_page.ARM_STAY.click();
    }

    public void ARM_AWAY(int delay) throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.DISARM.click();
        System.out.println("Arm Away");
        Thread.sleep(2000);
        home_page.ARM_AWAY.click();
        TimeUnit.SECONDS.sleep(delay);
    }

    public void enterDefaultUserCode() throws InterruptedException {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Four.click();
        Thread.sleep(2000);
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
        for (int j = 0; j < length; j++) {
            if (log.contains(findEvent[j])) {
                System.out.println(findEvent[j] + " RECEIVED");
            }
        }
    }

    protected void deleteLogFile(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    public void verify_disarm() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Disarmed_text.getText().equals("DISARMED")) {
            System.out.println("Pass: System is DISARMED");
        } else {
            System.out.println("Failed: System is not DISARMED " + home_page.Disarmed_text.getText());
        }
    }

    public void verify_armstay() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Disarmed_text.getText().equals("ARMED STAY")) {
            System.out.println("Pass: System is ARM STAY");
        } else {
            System.out.println("Failed: System is NOT ARMED STAY");
        }
    }

    public void verify_armaway() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.ArwAway_State.isDisplayed()) {
            System.out.println("Pass: panel is in Arm Away mode");
        } else {
            System.out.println("Failed: panel is not in Arm Away mode");
        }
    }

    public void enter_default_user_code() {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.One.click();
        home_page.Two.click();
        home_page.Three.click();
        home_page.Four.click();
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

    public void delete_all_camera_photos() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
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
    }

    public String number_of_photos() throws IOException {
        String command = ConfigProps.adbPath + " ls -l /storage/sdcard0/DisarmPhotos | busybox1.11  wc -l";
        rt.exec(command);
        String number = setup.execCmd(command);
        return number;
    }
}
