package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class AboutPageTest extends Setup {

    String page_name = "About page testing";
    Logger logger = Logger.getLogger(page_name);
    AboutPage about;
    AdvancedSettingsPage adv;

    public AboutPageTest() throws Exception {
    }

    public void swipe_vertical1() throws InterruptedException {
        int starty = 620;
        int endy = 250;
        int startx = 1000;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_About_page() throws Exception {
        about = PageFactory.initElements(driver, AboutPage.class);
        adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        logger.info("Verifying elements on the page...");
        navigateToAdvancedSettingsPage();
        adv.ABOUT.click();
        Thread.sleep(1000);
        elementVerification(about.Battery, "Battery");
        about.Battery.click();
        Thread.sleep(1000);
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li = b.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Battery_Status, "Battery Status");
        //      logger.info(li.get(0).getText());
        elementVerification(about.Battery_Level, "Battery Level");
        //      logger.info(li.get(1).getText());
        about.Battery.click();
        Thread.sleep(1000);
        elementVerification(about.Software, "Software");
        about.Software.click();
        elementVerification(about.Software_Version, "Software Version");
        elementVerification(about.Build_Number, "Build Number");
        elementVerification(about.Linux_Version, "Linux Version");
        elementVerification(about.Android_OS_Version, "Android OS Version");
        about.Software.click();
        Thread.sleep(1000);
        elementVerification(about.Hardware, "Hardware");
        about.Hardware.click();
        Thread.sleep(1000);
        elementVerification(about.Manufacturer, "Manufacturer");
        swipeVertical();
        Thread.sleep(2000);
        elementVerification(about.PCA_Serial_Number, "PCA Serial Number");
        elementVerification(about.Part_Number, "Part Number");
        elementVerification(about.System_Configuration, "System Configuration");
        elementVerification(about.System_Serial_Number, "System Serial Number");
        swipeVertical();
        Thread.sleep(2000);
        elementVerification(about.RF_PIC_Version, "RF Pic Version");
        elementVerification(about.EEPROM_Format_Version, "EEPROM Format Version");
        elementVerification(about.Image_Sensor_Version, "Image Sensor Version");
        about.Image_Sensor_Version.click();
        elementVerification(about.Patches, "Patches");
        about.Patches.click();
        Thread.sleep(1000);
        elementVerification(about.Patch_Description, "Patch Description");
        elementVerification(about.Last_Upgrade_Date, "Last Upgrade Date");
        about.Patches.click();
        Thread.sleep(1000);
        elementVerification(about.Panel_About, "Panel");
        about.Panel_About.click();
        elementVerification(about.MAC_Address, "MAC Address");
        elementVerification(about.Panel_Up_Time, "Panel Up Time");
        about.Panel_About.click();
        Thread.sleep(1000);
        elementVerification(about.Cellular, "Cellular");
        about.Cellular.click();
        Thread.sleep(1000);
        elementVerification(about.Carrier, "Carrier");
        elementVerification(about.Cellular_Connection, "Cellular Connection");
        elementVerification(about.Cellular_Signal_Strength, "Cellular Signal Strength");
        elementVerification(about.IMEI, "IMEI");
        swipe_vertical1();
        Thread.sleep(1000);
        elementVerification(about.IMSI, "IMSI");
        elementVerification(about.ICCID, "ICCID");
        elementVerification(about.Baseband_Version, "Baseband Version");
        about.Baseband_Version.click();
        Thread.sleep(1000);
        elementVerification(about.ZWave_About, "Z-Wave");
        about.ZWave_About.click();
        Thread.sleep(1000);
        elementVerification(about.Home_ID, "Home-ID");
        elementVerification(about.ZWave_Firmware_Version, "Z-Wave Firmware Version");
        elementVerification(about.ZWave_Api_Version, "Z-Wave Api Version");
        swipe_vertical1();
        Thread.sleep(1000);
        elementVerification(about.Manufacturing_ID, "Manufacturing ID");
        elementVerification(about.Product_Type, "Product Type");
        elementVerification(about.Product_ID, "Product ID");
        about.Product_ID.click();
        elementVerification(about.Ethernet, "Ethernet");
        elementVerification(about.WI_FI_Info, "Wi-Fi Info");
        about.WI_FI_Info.click();
        Thread.sleep(1000);
        elementVerification(about.Connection, "Connection");
        elementVerification(about.IP_Address, "IP Address");
        swipe_vertical1();
        Thread.sleep(1000);
        elementVerification(about.SSID, "SSID");
        elementVerification(about.Speed, "Speed");
        elementVerification(about.Internet, "Internet");
        about.Internet.click();
        elementVerification(about.Internal_storage, "Internal storage");
        about.Internal_storage.click();
        Thread.sleep(1000);
        elementVerification(about.Total_space, "Total space");
        swipe_vertical1();
        Thread.sleep(1000);
        elementVerification(about.Available_space, "Total space");
        elementVerification(about.Photos_About, "Photos");
        elementVerification(about.Videos_About, "Videos");
        elementVerification(about.Logs, "Logs");
        about.Logs.click();
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
