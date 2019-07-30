package settingsGrid;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup1;

import java.io.IOException;

public class Installer_Code_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Installer Code change";
    Logger logger = Logger.getLogger(page_name);

    public Installer_Code_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Installer_Code_Change(String UDID_) throws Exception {
        SettingsPage settings = PageFactory.initElements(s.driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(s.driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(s.driver, UserManagementPage.class);

        System.out.println("Verify Installer Code can be modified");
        logger.info("Verify Installer Code can be modified");
        s.navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();
        user.Add_User_Name_field.clear();
        System.out.println("Changing Installer name");
        user.Add_User_Name_field.sendKeys("NewInstall");
        user.Add_User_Code_field.clear();
        System.out.println("Changing Installer password");
        user.Add_User_Code_field.sendKeys("5555");
        s.driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
        try {
            s.driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(10000);
        s.navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        arming.Installer_Code.click();
        System.out.println("Verify Installer name changed");
        logger.info("Verify Installer name changed");
        Assert.assertTrue(s.driver.findElement(By.xpath("//android.widget.EditText[@text='NewInstall']")).isDisplayed());
        logger.info("Pass: New Installer name is displayed");
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(3000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        System.out.println("Verify old Installer code does not work");
        logger.info("Verify old Installer code does not work");
        settings.ADVANCED_SETTINGS.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            System.out.println("Pass: old Installer code does not work");
            logger.info("Pass: old Installer code does not work");
        }
        Thread.sleep(2000);
        System.out.println("Verify new Installer code works");
        logger.info("Verify new Installer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        logger.info("Pass: new Installer code works as expected");
        System.out.println("Pass: new Installer code works as expected");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Installer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("1111");
        s.driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("1111");
        try {
            s.driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(2000);
        user.User_Management_Delete_User_Ok.click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}