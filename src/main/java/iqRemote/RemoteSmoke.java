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
import utils.Setup;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class RemoteSmoke extends Setup {

    public Runtime rt = Runtime.getRuntime();
    SimpleDateFormat time_formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentTime = time_formatter.format(System.currentTimeMillis());
    public RemoteSmoke() throws Exception {
        ConfigProps.init();
    }

    public void AppiumStart() throws IOException, InterruptedException {
        rt.exec(ConfigProps.adbPath + " /usr/local/bin/appium");
        System.out.println("Appium session is started successfully");
        Thread.sleep(4000);
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
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
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
        rt.exec(ConfigProps.adbPath + " shell logcat -T" + currentTime + " -v time -s QolsysProvider > /data/log.txt");
        for (int i = 1000; i > 0; i--) {
            ARM_STAY();
            Thread.sleep(5000);
            DISARM();
            Thread.sleep(5000);
            ARM_AWAY(15);
            Thread.sleep(5000);
            home_page.ArwAway_State.click();
            enterDefaultUserCode();
            Thread.sleep(5000);

            /*** EMERGENCY ***/
            EmergencyPage emergency_page = PageFactory.initElements(driver, EmergencyPage.class);
            emergency_page.Emergency_Button.click();
            Thread.sleep(2000);
            emergency_page.Police_icon.click();
            Thread.sleep(5000);
            emergency_page.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(5000);

            emergency_page.Emergency_Button.click();
            Thread.sleep(2000);
            emergency_page.Fire_icon.click();
            Thread.sleep(5000);
            emergency_page.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(5000);

            emergency_page.Emergency_Button.click();
            Thread.sleep(2000);
            emergency_page.Auxiliary_icon.click();
            Thread.sleep(5000);
            emergency_page.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(5000);
        }
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }
}
