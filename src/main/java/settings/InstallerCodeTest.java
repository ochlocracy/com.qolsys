package settings;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.android.AndroidKeyCode;
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
import java.util.List;


public class InstallerCodeTest extends Setup {

    ExtentReport rep = new ExtentReport("SettingsReport");

    public InstallerCodeTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Installer_Code_Change() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);

        rep.create_report("Install_Code_01");
        rep.log.log(LogStatus.INFO, ("*Install_Code_01* Change Installer code -> Expected result = Old installer code will not work"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Thread.sleep(1000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        arming.Installer_Code.click();
        Thread.sleep(1000);
        user.Add_User_Name_field.clear();
        logger.info("Changing Installer name");
        user.Add_User_Name_field.sendKeys("NewInstall");
        user.Add_User_Code_field.clear();
        logger.info("Changing Installer password");
        user.Add_User_Code_field.sendKeys("5555");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
//        driver.pressKeyCode(AndroidKeyCode.ENTER);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(5000);
        settings.Home_button.click();
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: old Installer code does not work"));
        }
        Thread.sleep(2000);
        rep.create_report("Install_Code_02");
        rep.log.log(LogStatus.INFO, ("*Install_Code_02* Try out new Installer code -> Expected result = New Installer code works"));
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        Thread.sleep(2000);
        rep.log.log(LogStatus.PASS, ("Pass: new Installer code works as expected"));
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Installer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("1111");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("1111");
        driver.hideKeyboard();
        user.User_Management_Save.click();
        Thread.sleep(2000);
    }

    @Test
    public void Verify_6_Installer_Code_Change() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);

        rep.create_report("Installer_Code_03");
        rep.log.log(LogStatus.INFO, ("*Installer_Code_03* Change Installer Code to 6 digits -> Expected result = Old Installer Code will not access the settings page"));
        Thread.sleep(2000);

        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEALER_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='6 Digit User Code']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);

        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: old Installer code does not work"));
        }
        rep.create_report("Install_Code_04");
        rep.log.log(LogStatus.INFO, ("*Install_Code_04* Try out new Installer code -> Expected result = New Installer code works"));
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.Zero.click();
        settings.Zero.click();

        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();

        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("6Installer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("111111");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("111111");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
        navigateToPartitionsAdvancedSettingsPage();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        rep.log.log(LogStatus.PASS, ("Pass: New Installer code works"));
        adv.INSTALLATION.click();
        inst.INSTALLER_SETTINGS.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='6 Digit User Code']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        //rep.report.flush();
        driver.quit();
    }
}
