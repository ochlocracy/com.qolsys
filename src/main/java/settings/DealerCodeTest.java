package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class DealerCodeTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Dealer_Code");

    public DealerCodeTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Dealer_Code_Change() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);

        rep.create_report("Dealer_Code_01");
        rep.log.log(LogStatus.INFO, ("*Dealer_Code_01* Change Dealer Name and Dealer Pass -> Expected result = Old Dealer Code will not access the settings page"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("NewDealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("5555");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);
        navigateToPartitionsAdvancedSettingsPage();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        Thread.sleep(1000);
        adv.USER_MANAGEMENT.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='NewDealer']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: old Dealer code does not work"));
        }
        Thread.sleep(2000);
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        rep.log.log(LogStatus.PASS, ("Pass: New Dealer code works"));
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Dealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("2222");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("2222");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);

    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
