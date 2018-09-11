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

import java.util.List;

public class CreatePartitions extends Setup{

    ExtentReport rep = new ExtentReport("Partitions_CreatePartitions");

    public CreatePartitions() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Edit_Sensor_To_Create_Partitions() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);

        rep.create_report("Create_Partitions_01");
        rep.log.log(LogStatus.INFO, ("*Create_Partitions_01* Change Sensor Partition Name -> Expected result = New Partitions will be created."));
        Thread.sleep(2000);
        navigateToEditSensorPage();
        List<WebElement> editSensor = driver.findElements(By.id("com.qolsys:id/imageView1"));
        Thread.sleep(1000);
        editSensor.get(0).click();
        Thread.sleep(1000);
        sec.Partition_Name.click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("partition1")).click();
        sec.Save.click();
        Thread.sleep(1000);
        editSensor.get(1).click();
        Thread.sleep(1000);
        sec.Partition_Name.click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("partition2")).click();
        sec.Save.click();
        Thread.sleep(1000);
    }
}
