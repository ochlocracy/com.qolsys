package qtmsSettings;

import panel.AdvancedSettingsPage;
import panel.HomePage;
import utils.Log;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AboutPage extends Setup {
    public AboutPage() throws Exception {}

    private String page_name = "About page testing";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    public void swipe_vertical1() throws InterruptedException {
        int starty = 620;
        int endy = 250;
        int startx = 1000;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }
    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
        setupLogger(page_name);
    }
    @Test
    public void accessAboutPage() throws InterruptedException {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigateToAdvancedSettingsPage();
        adv.ABOUT.click();
        Thread.sleep(2000);
    }
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_001_SASA_002_SASA_003 () throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying that battery info");
        elementVerification(about.Battery, "Battery");
        about.Battery.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Battery_Status, "Battery Status");
        logger.info("Battery Status: " + li.get(0).getText());
        elementVerification(about.Battery_Level, "Battery Level");
        logger.info("Battery Level: " + li.get(1).getText());
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @Parameters ({"Linux Version", "Android OS Version"})
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_004_SASA_005_SASA_006 (String Linux_Version, String Android_OS_Version) throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying software info");
        elementVerification(about.Software, "Software");
        about.Software.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Software_Version, "Software Version");
        logger.info("Software Version: " + li.get(0).getText());
        elementVerification(about.Build_Number, "Build Number");
        logger.info("Build Number: " + li.get(1).getText());
        elementVerification(about.Linux_Version, "Linux Version");
        logger.info("Linux Version: " + li.get(2).getText());
        if ((li.get(2).getText()).equals(Linux_Version)){
            logger.info("Pass: Correct Linux Version");}
        else {
            logger.info("***FAILED*** incorrect Linux Version "+li.get(2).getText());}
        elementVerification(about.Android_OS_Version, "Android OS Version");
        logger.info("Android OS Version: " + li.get(3).getText());
        if ((li.get(3).getText()).equals(Android_OS_Version)){
            logger.info("Pass: Correct Android OS Version");}
        else {
            logger.info("***FAILED*** incorrect Android OS Version "+li.get(3).getText());}
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_007_SASA_008_SASA_009 () throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying hardware info");
        elementVerification(about.Hardware, "Hardware");
        about.Hardware.click();
        swipeVertical();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Hardware, "Hardware");
        logger.info("Hardware Version: " + li.get(0).getText());
        elementVerification(about.Manufacturer, "Manufacturer");
        logger.info("Manufacturer: " + li.get(1).getText());
        elementVerification(about.PCA_Serial_Number, "PCA Serial Number");
        logger.info("PCA Serial Number: " + li.get(2).getText());
        swipeVertical();
        Thread.sleep(1000);
        List<WebElement> li1 = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Part_Number, "Part Number");
        logger.info("Part Number: " + li1.get(2).getText());
        elementVerification(about.System_Configuration, "System Configuration");
        logger.info("System Configuration: " + li1.get(3).getText());
        swipeVertical();
        Thread.sleep(1000);
        List<WebElement> li2 = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.System_Serial_Number, "System Serial Number");
        logger.info("System Serial Number: " + li2.get(0).getText());
        elementVerification(about.RF_PIC_Version, "RF Pic Version");
        logger.info("RF PIC Version: " + li2.get(1).getText());
        elementVerification(about.EEPROM_Format_Version, "EEPROM Format Version");
        logger.info("EEPROM Format Version: " + li2.get(2).getText());
        elementVerification(about.Image_Sensor_Version, "Image Sensor Version");
        logger.info("Image Sensor Version: " + li2.get(3).getText());
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_010_SASA_011_SASA_012_SASA_014 () throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verifying patches info");
        elementVerification(about.Patches, "Patches");
        about.Patches.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        logger.info("Patch Description: " + li.get(0).getText());
        logger.info("Last Upgrade Date: " + li.get(1).getText());
        about.Patch_Description.click();
        elementVerification(about.Patch, "Patch");
        elementVerification(about.Date, "Date");
        elementVerification(about.Status, "Status");
        elementVerification(about.Checksum, "Checksum");
        home.Back_button.click();
        Thread.sleep(1000);
        about.Patches.click();
        Thread.sleep(1000);
    }
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_015_SASA_016_SASA_017_SASA_018 () throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying panel info");
        elementVerification(about.Panel_About, "panel");
        about.Panel_About.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.MAC_Address, "MAC Address");
        logger.info("MAC Address: " + li.get(0).getText());
        elementVerification(about.Panel_Up_Time, "panel Up Time");
        logger.info("panel Up Time: " + li.get(1).getText());
        about.Panel_About.click();
        Thread.sleep(1000);
    }
    @Parameters ({"Baseband_Version", "Verizon_Config_Version", "ATandT_Config_version"})
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_019_SASA_020_SASA_021_SASA_022_SASA_023_SASA_024_SASA_025 (String Baseband_Version, String Verizon_Config_Version, String ATandT_Config_version) throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying cellular info");
        elementVerification(about.Cellular, "cellular");
        about.Cellular.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Carrier, "Carrier");
        logger.info("Carrier: " + li.get(0).getText());
        String carrier = li.get(0).getText();
        elementVerification(about.Cellular_Connection, "cellular Connection");
        logger.info("cellular Connection: " + li.get(1).getText());
        elementVerification(about.Cellular_Signal_Strength, "cellular Signal Strength");
        logger.info("cellular Signal Strength: " + li.get(2).getText());
        elementVerification(about.IMEI, "IMEI");
        logger.info("IMEI: " + li.get(3).getText());
        swipe_vertical1();
        Thread.sleep(1000);
        List<WebElement> li1 = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.IMSI, "IMSI");
        logger.info("IMSI: " + li1.get(2).getText());
        elementVerification(about.ICCID, "ICCID");
        logger.info("ICCID: " + li1.get(3).getText());
        elementVerification(about.Baseband_Version, "Baseband Version");
        if (li1.get(4).getText().equals(Baseband_Version)){
            logger.info("Pass: Correct Baseband Version"); }
            else { logger.info("***FAILED*** incorrect Baseband Version "+li.get(4).getText());
        }
        logger.info("Configuration Version: " + li1.get(5).getText());

        if (carrier.equals("Verizon Wireless")) {
            boolean config_version = li1.get(5).getText().toString().equals(Verizon_Config_Version);
            System.out.println("Carrier is " +carrier+  " configuration version is correct: " +config_version);
        }else  if (carrier.equals("AT&T")){
            boolean config_version = li1.get(5).getText().toString().equals(ATandT_Config_version);
            System.out.println("Carrier is " +carrier+  " configuration version is correct: " +config_version);
        }
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @Parameters ({"Z_Wave_Firmware"})
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_026_SASA_027_SASA_028_SASA_030_SASA_031 (String Z_Wave_Firmware) throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying Z-Wave info");
        elementVerification(about.ZWave_About, "Z-Wave");
        about.ZWave_About.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Home_ID, "Home-ID");
        logger.info("Home ID: " + li.get(0).getText());
        elementVerification(about.ZWave_Firmware_Version, "Z-Wave Firmware Version");
        logger.info("Z-Wave Firmware Version: " + li.get(1).getText());
        if ((li.get(1).getText()).equals(Z_Wave_Firmware)){
            logger.info("Pass: Correct Z-Wave Firmware Version");}
                else {
            logger.info("***FAILED: Incorrect Z-Wave Firmware Version");}
        elementVerification(about.ZWave_Api_Version, "Z-Wave Api Version");
        logger.info("Z-Wave Api Version: " + li.get(2).getText());
        swipe_vertical1();
        Thread.sleep(1000);
        List<WebElement> li1 = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Manufacturing_ID, "Manufacturing ID");
        logger.info("Manufacturing ID: " + li1.get(2).getText());
        elementVerification(about.Product_Type, "Product Type");
        logger.info("Product Type: " + li1.get(3).getText());
        elementVerification(about.Product_ID, "Product ID");
        logger.info("Product ID: " + li1.get(4).getText());
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_037_SASA_038_SASA_039_SASA_040_SASA_041 () throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying Wi-Fi info");
        elementVerification(about.WI_FI_Info, "Wi-Fi Info");
        about.WI_FI_Info.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Connection, "Connection");
        logger.info("Connection: " + li.get(0).getText());
        elementVerification(about.IP_Address, "IP Address");
        logger.info("IP Address: " + li.get(1).getText());
        swipe_vertical1();
        Thread.sleep(1000);
        List<WebElement> li1 = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.SSID, "SSID");
        logger.info("SSID: " + li1.get(2).getText());
        elementVerification(about.Speed, "Speed");
        logger.info("Speed: " + li1.get(3).getText());
        elementVerification(about.Internet, "Internet");
        logger.info("Internet: " + li1.get(4).getText());
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @Test (dependsOnMethods = {"accessAboutPage"})
    public void SASA_049_SASA_050_SASA_051_SASA_052_SASA_053 () throws Exception {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        logger.info("Verifying Internal Storage info");
        elementVerification(about.Internal_storage, "Internal Storage");
        about.Internal_storage.click();
        Thread.sleep(1000);
        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Total_space, "Total space");
        logger.info("Total Space: " + li.get(0).getText());
        swipe_vertical1();
        Thread.sleep(1000);
        List<WebElement> li1 = driver.findElements(By.id("com.qolsys:id/summary"));
        elementVerification(about.Available_space, "Available space");
        logger.info("Available space: " + li.get(1).getText());
        elementVerification(about.Photos_About, "Photos");
        logger.info("Photos: " + li1.get(2).getText());
        elementVerification(about.Videos_About, "Videos");
        logger.info("Videos: " + li1.get(3).getText());
        elementVerification(about.Logs, "Logs");
        logger.info("Logs: " + li1.get(4).getText());
        driver.findElement(By.id("com.qolsys:id/child_view")).click();
        Thread.sleep(1000);
    }
    @AfterTest
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}