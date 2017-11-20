package iqRemote;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URL;

public class WifiTesting {

    public AndroidDriver<WebElement> driver;

    public void setup_driver(String url_, String port_) throws Exception {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
        cap.setCapability("BROWSER_NAME", "Android");
        cap.setCapability("udid", "6NJUMAGLVK" );
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.MainActivity");
        driver = new AndroidDriver<WebElement>(new URL(url_+":"+port_+"/wd/hub"), cap);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver("http://127.0.1.1", "4723");
    }

    @Test
    public void Test1 () throws InterruptedException {
        ElementsList el = PageFactory.initElements( driver, ElementsList.class);
    for (int i = 50; i>0; i--) {
        el.WiFi.click();
        Thread.sleep(20000);
        el.WiFi.click();
         Thread.sleep(20000);

         if (el.Connected.getText().equals("Connected")) {
        System.out.println("WiFi is connected");
        } else {
        System.out.println("WiFi is not connected");
    }
        System.out.println(i);}
        Thread.sleep(20000);
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        driver.quit();
    }
}
