package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class DuressAuthenticationTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Duress_Authentication");

    public DuressAuthenticationTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Duress_Authentication_works() throws Exception  {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage cam = PageFactory.initElements(driver, PanelCameraPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);

        rep.create_report("Duress_Auth_01");
        rep.log.log(LogStatus.INFO, ("*Duress_Auth_01* Test Duress Code works -> Expected result = Duress Photo is taken"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Duress']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        rep.create_report("Duress_Auth_02");
        rep.log.log(LogStatus.INFO, ("*Duress_Auth_02* Change Duress Code -> Expected result = system can be disarmed with Duress"));
        Thread.sleep(2000);
        adv.USER_MANAGEMENT.click();
        Thread.sleep(1000);
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/editImg"));
        Thread.sleep(2000);
        li_status1.get(1).click();
        Thread.sleep(1000);
        user.Add_User_Name_field.clear();
        logger.info("Changing Duress name");
        user.Add_User_Name_field.sendKeys("NewDuress");
        user.Add_User_Code_field.clear();
        logger.info("Changing Duress password");
        user.Add_User_Code_field.sendKeys("9998");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("9998");
//        driver.pressKeyCode(AndroidKeyCode.ENTER);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
        user.User_Management_Save.click();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Eight.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: new Duress code works"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: new Duress code did not worked"));
        }
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        List<WebElement> li_status2 = driver.findElements(By.id("com.qolsys:id/editImg"));
        Thread.sleep(2000);
        li_status2.get(1).click();
        Thread.sleep(1000);
        user.Add_User_Name_field.clear();
        logger.info("Changing Duress name");
        user.Add_User_Name_field.sendKeys("NewDuress");
        user.Add_User_Code_field.clear();
        logger.info("Changing Duress password");
        user.Add_User_Code_field.sendKeys("9998");
        driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("9998");
//        driver.pressKeyCode(AndroidKeyCode.ENTER);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user.User_Management_Save.click();
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
