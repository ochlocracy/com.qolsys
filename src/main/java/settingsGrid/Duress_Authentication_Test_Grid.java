package settingsGrid;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup;
import utils.Setup1;

import java.io.IOException;
import java.util.List;

public class Duress_Authentication_Test_Grid extends Setup {

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    Setup1 s = new Setup1();
    String page_name = "Duress Authentication testing";
    Logger logger = Logger.getLogger(page_name);
    private int delay = 15;

    public Duress_Authentication_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({ "UDID_" })
    @Test
    public void Verify_Duress_Authentication_works(String UDID_) throws Exception {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest("Settings.Duress_Authentication");

        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage cam = PageFactory.initElements(driver, PanelCameraPage.class);
        UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);

        Thread.sleep(2000);
        System.out.println("Verify system can be DISARMED with Duress code");
        log.log(LogStatus.INFO, "Verify system can be DISARMED with Duress code");
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
            log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }
        Thread.sleep(1000);
        navigateToAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Duress']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        log.log(LogStatus.INFO, ("Change Duress Code -> Expected result = system can be disarmed with New Duress"));
        Thread.sleep(2000);
        adv.USER_MANAGEMENT.click();
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
        user.User_Management_Save.click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
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
            log.log(LogStatus.PASS, ("Pass: new Duress code works"));
        } else {
            takeScreenshot();
            log.log(LogStatus.FAIL, ("Failed: new Duress code did not worked"));
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
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        Thread.sleep(1000);

    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}