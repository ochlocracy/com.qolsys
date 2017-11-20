package iqRemote;

import panel.*;
import sensors.EventConstants;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.Setup;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

public class Setup_Remote {

    Setup setup = new Setup();
    public AndroidDriver<WebElement> driver;
    public Runtime rt = Runtime.getRuntime();
   String logcat = "/home/qolsys/IdeaProjects/comqolsys2017/log/test.txt";

    public Setup_Remote() throws Exception { ConfigProps.init();}

    public void setup_driver(String udid_, String url_, String port_) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
        cap.setCapability("BROWSER_NAME", "Android");
        cap.setCapability("udid", udid_ );
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.MainActivity");
        driver = new AndroidDriver<WebElement>(new URL(url_+":"+port_+"/wd/hub"), cap);
    }
    public void ARM_STAY() {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        System.out.println("Arm Stay");
        home_page.DISARM.click();
        home_page.ARM_STAY.click();
    }


    public void eventLogsGenerating(String fileName,String[] findEvent, int length) throws Exception{
        List<LogEntry> logEntries = driver.manage().logs().get("logcat").getAll();
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        for(int i=0; i<logEntries.size(); i++) {
            String log = logEntries.get(i).getMessage();
            bw.write(log);
            bw.newLine();
            displayingEvent(log,findEvent,length);
        }
        bw.close();
    }
    private void displayingEvent(String log, String[] findEvent, int length){
        for(int j=0;j<length;j++){
            if (log.contains(findEvent[j])) {
                System.out.println(findEvent[j]+" RECEIVED");
            }
        }
    }
    protected void deleteLogFile(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }
    public void LogcatClear() throws Exception{
        rt.exec(ConfigProps.adbPath +" logcat -c &");
    }

    public void killLogcat() throws Exception{
        rt.exec(ConfigProps.adbPath+" shell busybox pkill logcat");
    }
    public void verify_disarm() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.Disarmed_text.getText().equals("DISARMED")) {
            System.out.println("Pass: System is DISARMED");
        } else {
            System.out.println("Failed: System is not DISARMED " + home_page.Disarmed_text.getText());
        }
    }
    public void verify_armstay() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.Disarmed_text.getText().equals("ARMED STAY")) {
            System.out.println("Pass: System is ARM STAY");
        } else {
            System.out.println("Failed: System is NOT ARMED STAY");}
    }
    public void verify_armaway() throws Exception {
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
        if (home_page.ArwAway_State.isDisplayed()) {
            System.out.println("Pass: panel is in Arm Away mode");
        } else {
            System.out.println("Failed: panel is not in Arm Away mode");}
    }
    public void enter_default_user_code (){
        Home_Page home_page = PageFactory.initElements(driver, Home_Page.class);
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
        Panel_Camera_Page camera = PageFactory.initElements(driver, Panel_Camera_Page.class);
        swipeFromLefttoRight();
        Thread.sleep(3000);
        try {
            while (camera.Photo_lable.isDisplayed()){
                camera.Camera_delete.click();
                camera.Camera_delete_yes.click();
                enter_default_user_code();}
        }catch (Exception e) {
            System.out.println("No photos to delete...");
        } finally {
        }
        swipeFromRighttoLeft();
    }

    @Test
    public void Test1 () throws Exception {
        deleteLogFile(logcat);
        setup.ARM_STAY();
        eventLogsGenerating(logcat, new String[]{
                EventConstants.EVENT_TO_ADC_STAY,
                "*******  ArmingLevelChange: Level: 2, adc_device_class: 14, User: 0, normal_closing_ack: 1"},2); // arm stay
        //ArmingLevelChange: Level: 1, adc_device_class: 14, User: 1, normal_closing_ack: 0  // disarm
        Thread.sleep(2000);
        setup.DISARM();
    }


}
