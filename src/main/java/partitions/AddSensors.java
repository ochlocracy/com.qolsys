package partitions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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
        List<WebElement> pli = driver.findElements(By.id("com.qolsys:id/partition_name"));
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

    @Test
    public void Add_SRF_Sensors() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);

        String Partition1SRF = "6500A1";

        rep.create_report("Add_Sensors_01");
        rep.log.log(LogStatus.INFO, ("*Add_Sensors_01* Start Fresh: Add List of SRF sensors -> Expected result = New Sensors will be added."));
        Thread.sleep(2000);
        try {
            enterDefaultDealerCode();
        } finally {

        }
        Thread.sleep(3000);

        home.All_Tab.click();
        if (home.Tamper_Status.isDisplayed()); {
            deleteAllSensorsOnPanel();
        }
        Thread.sleep(2000);

        if (home.Partition_Name.isDisplayed()); {
            menu.Slide_menu_open.click();
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
            swipeVertical();
            driver.findElementByXPath("//android.widget.TextView[@text='Delete All Sensors']").click();
            driver.findElement(By.id("com.qolsys:id/ok")).click();
            Thread.sleep(2000);
        }


        navigateToAddSensorsPage();
        WebElement sensor_DLID = driver.findElementById("com.qolsys:id/sensor_id");
        sensor_DLID.sendKeys(Partition1SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Custom Description")).click();
        Thread.sleep(1000);
        WebElement sensorName = driver.findElementById("com.qolsys:id/sensorDescText");
        sensorName.sendKeys(Partition1SRF);
        driver.findElementById("com.qolsys:id/addsensor").click();

        String Partition2SRF = "6500A2";

        Thread.sleep(2000);
        navigateToAddSensorsPage();
        sensor_DLID.sendKeys(Partition2SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Custom Description")).click();
        Thread.sleep(1000);
        sensorName.sendKeys(Partition2SRF);
        driver.findElementById("com.qolsys:id/addsensor").click();

        String Partition3SRF = "6500A3";

        Thread.sleep(2000);
        navigateToAddSensorsPage();
        sensor_DLID.sendKeys(Partition3SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Custom Description")).click();
        Thread.sleep(1000);
        sensorName.sendKeys(Partition3SRF);
        driver.findElementById("com.qolsys:id/addsensor").click();

        String Partition4SRF = "6500A4";

        Thread.sleep(2000);
        navigateToAddSensorsPage();
        sensor_DLID.sendKeys(Partition4SRF);
        driver.hideKeyboard();
        driver.findElementById("com.qolsys:id/sensor_desc").click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Custom Description")).click();
        Thread.sleep(1000);
        sensorName.sendKeys(Partition4SRF);
        driver.findElementById("com.qolsys:id/addsensor").click();
    }

//    @Test
//    public void Add_PG_Sensors() throws Exception {
//        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
//        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
//        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
//        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
//        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
//        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
//
//        navigate_to_autolearn_page();
//        addPartitionPGSensors("DW", 104, 1152, 1, 0);//gr12
//        addPartitionPGSensors("DW", 104, 1231, 2, 1);//gr13
//        addPartitionPGSensors("DW", 104, 1216, 3, 2);//gr14
//        addPartitionPGSensors("DW", 104, 1331, 4, 3);//gr16
//
//    }
}
