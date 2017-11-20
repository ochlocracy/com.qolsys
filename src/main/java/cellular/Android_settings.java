package cellular;

import utils.Log;
import utils.Setup;
import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Android_settings {

    public AndroidDriver<WebElement> driver;
    String page_name = "Native Android setting";
    Logger logger = Logger.getLogger(page_name);

    Setup a;

    public WebElement element_verification(WebElement element, String element_name) throws Exception {
        try {
            if (element.isDisplayed()) {
                logger.info("Pass: " + element_name + " is present, value = " + element.getText());
                //System.out.println(("Pass: " + element_name + " is present, value = " + element.getText()));
            }
        } catch (Exception e) {
            //take_screenshot();
            Log.error("*" + element_name + "* - Element is not found!");
            throw (e);
        } finally {
            return element;
        }
    }

    public Android_settings() throws Exception {
        a = new Setup();
    }

    public void setup_driver_and(String getUdid, String url_, String port_) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
        cap.setCapability("BROWSER_NAME", "Android");
        cap.setCapability("udid", getUdid);
        cap.setCapability("appPackage", "com.android.phone");
        cap.setCapability("appActivity", ".MobileNetworkSettings");
        driver = new AndroidDriver<WebElement>(new URL(url_ + ":" + port_ + "/wd/hub"), cap);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        setup_driver_and(a.get_UDID(), "http://127.0.1.1", "4723");
        a.setup_logger(page_name);

        // setup_driver_and(s.get_UDID(),"http://127.0.1.1", "4723");
    }

    @Test
//        adb shell am start -a android.settings.WIRELESS_SETTINGS
//        adb shell am start com.qolsys

    public void Android_settings_elements() throws Exception {

        cellular.Android_settings_elements an_ver = PageFactory.initElements(driver, Android_settings_elements.class);
        logger.info("Verifying cellular data ...");
        Thread.sleep(2000);
        List<WebElement> li = driver.findElements(By.id("android:id/summary"));
        String value = li.get(1).getText();
        logger.info(value);
        List<WebElement> li1 = driver.findElements(By.id("android:id/title"));
        String value1 = li1.get(5).getText();
        // System.out.println(value1);
        li1.get(5).click();
        Thread.sleep(2000);
        cellular.Android_settings_elements an_var1 = PageFactory.initElements(driver, Android_settings_elements.class);
        List<WebElement> new_li1 = driver.findElements(By.id("android:id/title"));
        System.out.println(new_li1.size());
        for (int i = 0; i < new_li1.size(); i++) {
            logger.info("APN name: " + new_li1.get(i).getText());
        }
        Thread.sleep(3000);

        List<WebElement> new_li = driver.findElements(By.id("android:id/summary"));
        // String apn = new_li.get(0).getText();
        System.out.println(new_li.size());
        for (int j = 0; j < new_li.size(); j++) {
            logger.info("APN summary: " + new_li.get(j).getText());
        }
        Thread.sleep(3000);

        List<WebElement> b_li = driver.findElements(By.id("com.android.settings:id/apn_radiobutton"));
        System.out.println("Number of present radio buttons: " + b_li.size());
              try {
                if (b_li.get(0).getAttribute("checked").equals("true")) ;
                { System.out.println("APN has selected");
                  return ;
                }
            } catch (Exception e) {
            System.out.println("NO ONE SELECTED");
            }
        Thread.sleep(2000);
           // a.tap(40,30);

              Thread.sleep(2000);
        System.out.println("End of testing");
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        a.log.endTestCase(page_name);
        driver.quit();
    }
}

