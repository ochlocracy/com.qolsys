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
    Advanced_Settings_Page adv;

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
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_About_page() throws Exception {
        about = PageFactory.initElements(driver, AboutPage.class);
        adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        logger.info("Verifying elements on the page...");
        navigate_to_Advanced_Settings_page();
        adv.ABOUT.click();
        Thread.sleep(1000);
        element_verification(about.Battery, "Battery");
        about.Battery.click();
        Thread.sleep(1000);
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li = b.findElements(By.id("com.qolsys:id/summary"));
        element_verification(about.Battery_Status, "Battery Status");
        //      logger.info(li.get(0).getText());
        element_verification(about.Battery_Level, "Battery Level");
        //      logger.info(li.get(1).getText());
        about.Battery.click();
        Thread.sleep(1000);
        element_verification(about.Software, "Software");
        about.Software.click();
        element_verification(about.Software_Version, "Software Version");
        element_verification(about.Build_Number, "Build Number");
        element_verification(about.Linux_Version, "Linux Version");
        element_verification(about.Android_OS_Version, "Android OS Version");
        about.Software.click();
        Thread.sleep(1000);
        element_verification(about.Hardware, "Hardware");
        about.Hardware.click();
        Thread.sleep(1000);
        element_verification(about.Manufacturer, "Manufacturer");
        swipe_vertical();
        Thread.sleep(2000);
        element_verification(about.PCA_Serial_Number, "PCA Serial Number");
        element_verification(about.Part_Number, "Part Number");
        element_verification(about.System_Configuration, "System Configuration");
        element_verification(about.System_Serial_Number, "System Serial Number");
        swipe_vertical();
        Thread.sleep(2000);
        element_verification(about.RF_PIC_Version, "RF Pic Version");
        element_verification(about.EEPROM_Format_Version, "EEPROM Format Version");
        element_verification(about.Image_Sensor_Version, "Image Sensor Version");
        about.Image_Sensor_Version.click();
        element_verification(about.Patches, "Patches");
        about.Patches.click();
        Thread.sleep(1000);
        element_verification(about.Patch_Description, "Patch Description");
        element_verification(about.Last_Upgrade_Date, "Last Upgrade Date");
        about.Patches.click();
        Thread.sleep(1000);
        element_verification(about.Panel_About, "Panel");
        about.Panel_About.click();
        element_verification(about.MAC_Address, "MAC Address");
        element_verification(about.Panel_Up_Time, "Panel Up Time");
        about.Panel_About.click();
        Thread.sleep(1000);
        element_verification(about.Cellular, "Cellular");
        about.Cellular.click();
        Thread.sleep(1000);
        element_verification(about.Carrier, "Carrier");
        element_verification(about.Cellular_Connection, "Cellular Connection");
        element_verification(about.Cellular_Signal_Strength, "Cellular Signal Strength");
        element_verification(about.IMEI, "IMEI");
        swipe_vertical1();
        Thread.sleep(1000);
        element_verification(about.IMSI, "IMSI");
        element_verification(about.ICCID, "ICCID");
        element_verification(about.Baseband_Version, "Baseband Version");
        about.Baseband_Version.click();
        Thread.sleep(1000);
        element_verification(about.ZWave_About, "Z-Wave");
        about.ZWave_About.click();
        Thread.sleep(1000);
        element_verification(about.Home_ID, "Home-ID");
        element_verification(about.ZWave_Firmware_Version, "Z-Wave Firmware Version");
        element_verification(about.ZWave_Api_Version, "Z-Wave Api Version");
        swipe_vertical1();
        Thread.sleep(1000);
        element_verification(about.Manufacturing_ID, "Manufacturing ID");
        element_verification(about.Product_Type, "Product Type");
        element_verification(about.Product_ID, "Product ID");
        about.Product_ID.click();
        element_verification(about.Ethernet, "Ethernet");
        element_verification(about.WI_FI_Info, "Wi-Fi Info");
        about.WI_FI_Info.click();
        Thread.sleep(1000);
        element_verification(about.Connection, "Connection");
        element_verification(about.IP_Address, "IP Address");
        swipe_vertical1();
        Thread.sleep(1000);
        element_verification(about.SSID, "SSID");
        element_verification(about.Speed, "Speed");
        element_verification(about.Internet, "Internet");
        about.Internet.click();
        element_verification(about.Internal_storage, "Internal storage");
        about.Internal_storage.click();
        Thread.sleep(1000);
        element_verification(about.Total_space, "Total space");
        swipe_vertical1();
        Thread.sleep(1000);
        element_verification(about.Available_space, "Total space");
        element_verification(about.Photos_About, "Photos");
        element_verification(about.Videos_About, "Videos");
        element_verification(about.Logs, "Logs");
        about.Logs.click();
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
