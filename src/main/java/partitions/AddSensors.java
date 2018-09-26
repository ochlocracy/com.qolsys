package partitions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class AddSensors extends Setup {

    ExtentReport rep = new ExtentReport("Partitions_CreatePartitions");

    public AddSensors() throws Exception {
    }

    public void addPartitionPGSensors(String sensor, int Type, int Id, int gn, int pn) throws IOException, InterruptedException {
        Thread.sleep(1000);
        powerGregistrator(Type, Id);
        Thread.sleep(3000);
        driver.findElementById("com.qolsys:id/ok").click();

        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/sensor_desc")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys(sensor + " " + Type + "-" + Id);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            //       e.printStackTrace();
        }
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/grouptype")).click();
        List<WebElement> gli = driver.findElements(By.id("android:id/text1"));
        gli.get(gn).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/partition_name")).click();
        List<WebElement> pli = driver.findElements(By.id("android:id/text1"));
        pli.get(pn).click();
        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(1000);
        powerGactivator(Type, Id);
        Thread.sleep(2000);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test (priority = 1)
    public void Add_SRF_Sensors() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        String Partition1SRF = "6500A1";

        rep.create_report("Add_Sensors_01");
        rep.log.log(LogStatus.INFO, ("*Add_Sensors_01* Start Fresh: Add List of SRF sensors -> Expected result = New Sensors will be added."));
        Thread.sleep(2000);

        try {
            if (home.pinpad.isDisplayed()) ;
            {
                enterDefaultDealerCode();
            }
        }catch(NoSuchElementException e)
        {}

        try {
            if (home.Partition_Name.isDisplayed());
            {
                menu.Slide_menu_open.click();
                logger.info("Removing panel partition setting to add sensors.");
                menu.Lock_Screen.click();
                Thread.sleep(1000);
                enterDefaultDealerCode();
                Thread.sleep(2500);
                menu.Slide_menu_open.click();
                menu.Settings.click();
                settings.ADVANCED_SETTINGS.click();
                adv.INSTALLATION.click();
                inst.DEALER_SETTINGS.click();
                swipeVertical();
                Thread.sleep(1500);
                swipeVertical();
                Thread.sleep(1500);
                swipeVertical();
                Thread.sleep(1500);
                swipeVertical();
                Thread.sleep(1500);
                swipeVertical();
                Thread.sleep(1500);
                swipeVertical();
                Thread.sleep(1500);
                driver.findElementByXPath("//android.widget.TextView[@text='Partitions']").click();
                driver.findElement(By.id("com.qolsys:id/ok")).click();
                Thread.sleep(2000);
                settings.Home_button.click();
            }
        }catch(NoSuchElementException e)
        {}

        Thread.sleep(3000);
        home.All_Tab.click();

        try {
        if (home.Tamper_Status.isDisplayed()); {
            deleteAllSensorsOnPanel();
        }
        }catch(NoSuchElementException e)
        {}
        Thread.sleep(4000);

        navigateToAddSensorsPage();
        WebElement sensor_DLID = driver.findElementById("com.qolsys:id/sensor_id");
        sensor_DLID.sendKeys(Partition1SRF);
        driver.hideKeyboard();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/sensor_desc")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        WebElement sensorName = driver.findElementById("com.qolsys:id/sensorDescText");
        sensorName.sendKeys(Partition1SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/addsensor").click();

        String Partition2SRF = "6500A2";

        Thread.sleep(2000);
        navigateToAddSensorsPage();
        sensor_DLID.sendKeys(Partition2SRF);
        driver.hideKeyboard();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/sensor_desc")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        sensorName.sendKeys(Partition2SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/addsensor").click();

        String Partition3SRF = "6500A3";

        Thread.sleep(2000);
        navigateToAddSensorsPage();
        sensor_DLID.sendKeys(Partition3SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        sensorName.sendKeys(Partition3SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/addsensor").click();

        String Partition4SRF = "6500A4";

        Thread.sleep(2000);
        navigateToAddSensorsPage();
        sensor_DLID.sendKeys(Partition4SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
        sensorName.sendKeys(Partition4SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/addsensor").click();

        settings.Home_button.click();

        rep.log.log(LogStatus.PASS, ("Pass: SRF sensors added"));


    }

    @Test(priority = 2)
    public void enable_partitions() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);

        menu.Slide_menu_open.click();
        menu.Settings.click();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultDealerCode();
        adv.INSTALLATION.click();
        inst.DEALER_SETTINGS.click();
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        swipeVertical();
        Thread.sleep(1500);
        driver.findElementByXPath("//android.widget.TextView[@text='Partitions']").click();
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
        enterDefaultDealerCode();

    }

//    @Test (priority = 3)
//    public void Add_PG_Sensors() throws Exception {
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        rep.create_report("Add_Sensors_02");
//        rep.log.log(LogStatus.INFO, ("*Add_Sensors_02* Add List of PowerG sensors -> Expected result = New Sensors will be added."));
//
//        try {
//            if (home.pinpad.isDisplayed()) ;
//            {
//                enterDefaultDealerCode();
//            }
//        }catch(NoSuchElementException e)
//        {}
//        navigate_to_partitions_autolearn_page();
//        addPartitionPGSensors("DW", 104, 1152, 1, 0);//gr12
//        addPartitionPGSensors("DW", 104, 1231, 2, 1);//gr13
//        addPartitionPGSensors("DW", 104, 1216, 3, 2);//gr14
//        addPartitionPGSensors("DW", 104, 1331, 4, 3);//gr16
//
//        rep.log.log(LogStatus.PASS, ("Pass: PowerG sensors added"));
//
//    }

    @Test (priority = 3)
    public void Assign_Partitions_to_SRF() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        SecuritySensorsPage ss = PageFactory.initElements(driver, SecuritySensorsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        rep.create_report("Add_Sensors_03");
        rep.log.log(LogStatus.INFO, ("*Add_Sensors_03* Separate SRF into different Partitions -> Expected result = New Sensors wil be on different Partitions."));


        try {
            if (home.pinpad.isDisplayed()) ;
            {
                enterDefaultDealerCode();
            }
        }catch(NoSuchElementException e)
        {}
        navigateToPartitionsAdvancedSettingsPage();
        adv.INSTALLATION.click();
        instal.DEVICES.click();
        dev.Security_Sensors.click();
        ss.Edit_Sensor.click();
        Thread.sleep(1000);
        List<WebElement> eli = driver.findElements(By.id("com.qolsys:id/imageView1"));
        Thread.sleep(2000);
        eli.get(1).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/partition_name")).click();
        List<WebElement> pnn = driver.findElements(By.id("android:id/text1"));
        pnn.get(1).click();
        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(2000);
        eli.get(2).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/partition_name")).click();
        pnn.get(2).click();
        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(2000);
        eli.get(3).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/partition_name")).click();
        pnn.get(3).click();
        Thread.sleep(1000);
        driver.findElementById("com.qolsys:id/addsensor").click();
        Thread.sleep(2000);
        settings.Home_button.click();
        rep.log.log(LogStatus.PASS, ("Pass: sensors are partitioned"));

            }

        @AfterMethod(alwaysRun = true)
        public void tearDown(ITestResult result) throws IOException, InterruptedException {
            rep.report_tear_down(result);
            driver.quit();
        }
    }
