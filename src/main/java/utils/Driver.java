package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Driver {

    public AppiumDriverLocalService service;
    public Runtime rt = Runtime.getRuntime();
    public AndroidDriver<WebElement> driver;
    public WebDriver driver1;

    public WebDriverWait wait;

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

    public String splitMethod(String str) {
        // import splitter, pass the string, convert into a list of words, add to getUDID
        String a = null;
        try{
            a = str.split("\\n")[1];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Please check panel USB connection");
        }
        return a.split("\\s")[0];
    }

    public String get_UDID() throws IOException {
        String command = ConfigProps.adbPath + " devices";
        rt.exec(command);
        String panel_UDID = splitMethod(execCmd(command));
        return panel_UDID;
    }

    public void killnode() throws IOException {
        String command = " killall node";
        for (int i = 3; i > 0; i--) {
            rt.exec(ConfigProps.adbPath + command);
        }
    }

    public void setupDriver(String getUdid, String url_, String port_) throws Exception {

        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(ConfigProps.nodePath))
                        .withAppiumJS(new File(ConfigProps.appiumPath))
                        .withArgument(GeneralServerFlag.LOG_LEVEL, "warn")
                        .withIPAddress("127.0.0.1").usingPort(4723));
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("deviceName", "IQPanel2");
//        cap.setCapability("automationName", "UiAutomator2");//new
        cap.setCapability("platformName", "Android");
        cap.setCapability("udid", get_UDID());
        cap.setCapability("appPackage", "com.qolsys");
        cap.setCapability("appActivity", "com.qolsys.activites.Theme3HomeActivity");
        cap.setCapability("noReset", "true");
        cap.setCapability("newCommandTimeout", 1000);
        //in case previous session was not stopped
        killnode();
        service.stop();
        Thread.sleep(2000);
        service.start();
        System.out.println("\n*****Start Appium*****\n");
        Thread.sleep(2000);


        driver = new AndroidDriver<>(service.getUrl(), cap);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void webDriverSetUp() {

//        driver1 = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", "/home/sergio/Downloads/chromedriver");
        driver1 = new ChromeDriver();
        wait = new WebDriverWait(driver1, 60);
    }


}
