package iqRemote;

import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.EmergencyPage;
import panel.HomePage;
import utils.ConfigProps;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class RemoteSmoke extends SetupRemote {

    public Runtime rt = Runtime.getRuntime();
    SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

    public RemoteSmoke() throws Exception {
        ConfigProps.init();
    }


    public void setSliderValue(WebElement elem, int intToSlideValue) {
        WebElement slider = elem;
        int startX = slider.getLocation().getX();
        int yAxis = slider.getLocation().getY();
        int moveToDirectionAt = intToSlideValue + startX;
        TouchAction act = new TouchAction(driver);
        act.longPress(startX, yAxis).moveTo(moveToDirectionAt, yAxis).release().perform();
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver("6NJUM1H24Z", "http://127.0.1.1", "4723");
    }

    @Test
    public void Test1() throws Exception {
        swipeFromRighttoLeft();
        Thread.sleep(3000);
        WebElement slider = driver.findElement(By.id("com.qolsys:id/dimmer_seek_bar"));
        int sliderSize = slider.getSize().getHeight();
        setSliderValue(slider, (int) (sliderSize * 0.5));
        Thread.sleep(3000);
    }

    @Test
    public void TestMain() throws Exception {
        /*** PANEL ARMING ***/
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        //    rt.exec(ConfigProps.adbPath + " shell logcat -T" + currentTime + " -v time -s QolsysProvider > /data/log.txt");
        Thread.sleep(20000);

        for (int i = 100; i > 0; i--) {
            rt.exec(ConfigProps.adbPath + " shell date >> /sdcard/meminfo.txt");
            rt.exec(ConfigProps.adbPath + " shell dumpsys meminfo >> /sdcard/meminfo.txt");
            ARM_STAY();
            Thread.sleep(10000);
            DISARM();
            Thread.sleep(10000);
            ARM_AWAY(25);
            home_page.ArwAway_State.click();
            enterDefaultUserCode();
            Thread.sleep(10000);

            /*** EMERGENCY ***/
            EmergencyPage emergency_page = PageFactory.initElements(driver, EmergencyPage.class);
            emergency_page.Emergency_Button.click();
            Thread.sleep(10000);
            System.out.println("Police Emergency");
            emergency_page.Police_icon.click();
            Thread.sleep(10000);
            emergency_page.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(10000);

            emergency_page.Emergency_Button.click();
            Thread.sleep(10000);
            System.out.println("Fire Emergency");
            emergency_page.Fire_icon.click();
            Thread.sleep(10000);
            emergency_page.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(10000);

            emergency_page.Emergency_Button.click();
            Thread.sleep(10000);
            System.out.println("Auxiliary Emergency");
            emergency_page.Auxiliary_icon.click();
            Thread.sleep(10000);
            emergency_page.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(10000);
            rt.exec(ConfigProps.adbPath + " shell echo ***END*** >> /sdcard/meminfo.txt");
            rt.exec(ConfigProps.adbPath + " shell dumpsys meminfo >> /sdcard/meminfo.txt");
            System.out.println(String.format("***Loop #%d", i));
            Thread.sleep(10000);
        }
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }
}
