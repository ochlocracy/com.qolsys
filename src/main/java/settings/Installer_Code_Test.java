package settings;

import panel.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Installer_Code_Test extends Setup {

    String page_name = "Installer Code change";
    Logger logger = Logger.getLogger(page_name);

    public Installer_Code_Test() throws Exception {}

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }
    @Test
    public void Verify_Installer_Code_Change() throws Exception {
        SettingsPage settings =  PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Installer_Code.click();
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
        user.User_Management_Save.click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        adv.USER_MANAGEMENT.click();
        logger.info("Verify Installer name changed");
        driver.findElement(By.xpath("//android.widget.TextView[@text='NewInstall']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        logger.info("Verify old Installer code does not work");
        settings.ADVANCED_SETTINGS.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        settings.One.click();
        if(settings.Invalid_User_Code.isDisplayed()){
            logger.info("Pass: old Installer code does not work");}
        Thread.sleep(2000);
        logger.info("Verify new Installer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        logger.info("Pass: new Installer code works as expected");
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
        user.User_Management_Save.click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
