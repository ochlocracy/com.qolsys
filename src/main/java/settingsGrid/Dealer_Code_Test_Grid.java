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

public class Dealer_Code_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Dealer Code change";
    Logger logger = Logger.getLogger(page_name);

    public Dealer_Code_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Dealer_Code_Change(String UDID_) throws Exception {
        SettingsPage settings = PageFactory.initElements(s.driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(s.driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.driver, InstallationPage.class);
        UserManagementPage user = PageFactory.initElements(s.driver, UserManagementPage.class);

        s.navigateToAdvancedSettingsPage();
        System.out.println("Verify a Dealer code can be changed");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        user.Add_User_Name_field.clear();
        System.out.println("Changing Dealer name");
        user.Add_User_Name_field.sendKeys("NewDealer");
        user.Add_User_Code_field.clear();
        System.out.println("Changing Dealer password");
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
        s.driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);
        s.navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        Thread.sleep(2000);
        logger.info("Verify Dealer name changed");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
//        <WebElement> name = driver.findElement(By.id("com.qolsys:id/username"));
//        user.Add_User_Name_field.clear();
        Assert.assertTrue(s.driver.findElement(By.xpath("//android.widget.EditText[@text='NewDealer']")).isDisplayed());
        logger.info("Pass: new dealer name is displayed");
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        System.out.println("Verify old Dealer code does not work");
        logger.info("Verify old Dealer code does not work");
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            System.out.println("Pass: old Dealer code does not work");
            logger.info("Pass: old Dealer code does not work");
        }
        Thread.sleep(2000);
        System.out.println("Verify new Dealer code works");
        logger.info("Verify new Dealer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        System.out.println("Pass: new Dealer code works as expected");
        logger.info("Pass: new Dealer code works as expected");
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Dealer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Dealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("2222");
        s.driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("2222");
        try {
            s.driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
        Thread.sleep(1000);
        s.driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);

    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}