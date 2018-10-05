package settingsGrid;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
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
    SettingsPage settings =  PageFactory.initElements(s.getDriver(), SettingsPage.class);
    ContactUs contact =  PageFactory.initElements(s.getDriver(), ContactUs.class);
    SecurityArmingPage arming = PageFactory.initElements(s.getDriver(), SecurityArmingPage.class);
    AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
    InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
    UserManagementPage user = PageFactory.initElements(s.getDriver(), UserManagementPage.class);
        Thread.sleep(2000);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(1000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        arming.Installer_Code.click();
        user.Add_User_Name_field.clear();
        logger.info("Changing Installer name");
        user.Add_User_Name_field.sendKeys("NewInstall");
        user.Add_User_Code_field.clear();
        logger.info("Changing Installer password");
        user.Add_User_Code_field.sendKeys("5555");
        s.getDriver().hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
        user.User_Management_Save.click();
        Thread.sleep(1000);
        s.getDriver().findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);
        s.navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        adv.USER_MANAGEMENT.click();
        logger.info("Verify Installer name changed");
        s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='NewInstall']")).isDisplayed();
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
        logger.info(UDID_ +" Pass: old Installer code does not work");}
        Thread.sleep(2000);
        logger.info("Verify new Installer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        if(adv.INSTALLATION.isDisplayed()){
        logger.info(UDID_ + " Pass: new Installer code works as expected");}
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(1000);
        arming.Installer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Installer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("1111");
        s.getDriver().hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("1111");
        user.User_Management_Save.click();
        Thread.sleep(1000);
        s.getDriver().findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);
}
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}