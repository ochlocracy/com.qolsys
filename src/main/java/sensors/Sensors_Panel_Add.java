package sensors;

import utils.Log;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Sensors_Panel_Add extends Setup {

    Log log = new Log();
    private String door_window_dlid = "6500A0";
    String page_name = "Adding sensors from panel UIr";
    Logger logger = Logger.getLogger(page_name);

    public Sensors_Panel_Add() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Test1 () throws InterruptedException {

        driver.findElementById("com.qolsys:id/btn_drop").click();
        driver.findElementById("com.qolsys:id/tv_tray_settings").click();
        driver.findElement(By.xpath("//android.widget.TextView[@index='8']")).click();
        driver.findElementById("com.qolsys:id/tv_keyTwo").click();
        driver.findElementById("com.qolsys:id/tv_keyTwo").click();
        driver.findElementById("com.qolsys:id/tv_keyTwo").click();
        driver.findElementById("com.qolsys:id/tv_keyTwo").click();
        WebElement a = driver.findElement(By.id("com.qolsys:id/gridview"));
        List<WebElement> li = a.findElements(By.className("android.widget.TextView"));
        li.get(0).click();
        li.get(0).click();
        li.get(0).click();
        li.get(1).click();
        WebElement sensor_DLID = driver.findElementById("com.qolsys:id/sensor_id");
        sensor_DLID.sendKeys(door_window_dlid);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        TimeUnit.SECONDS.sleep(2);
        WebElement element0 = driver.findElement(By.xpath("//android.widget.CheckedTextView[@index='0']"));
        System.out.println(element0.getText());
   //     driver.scrollTo("Garage Door").click();
        TimeUnit.SECONDS.sleep(10);
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}

