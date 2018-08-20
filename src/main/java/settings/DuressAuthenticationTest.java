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

public class DuressAuthenticationTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Duress_Authentication");

    public DuressAuthenticationTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Duress_Authentication_works() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        rep.create_report("Duress_Auth_01");
        rep.log.log(LogStatus.INFO, ("*Duress_Auth_01* Duress code disabled / not set up -> Expected result = system cannot be disarmed with Duress"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Eight.click();
        if (settings.Invalid_User_Code.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does not work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code worked"));
        }
        Thread.sleep(1000);
        enterDefaultUserCode();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Duress_Authentication.click();
        Thread.sleep(3000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/ft_back")).click();
        Thread.sleep(5000);
        adv.USER_MANAGEMENT.click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Duress']")).isDisplayed();
        Thread.sleep(2000);
        settings.Home_button.click();
        rep.create_report("Duress_Auth_02");
        rep.log.log(LogStatus.INFO, ("*Duress_Auth_02* Duress code enabled -> Expected result = system can be disarmed with Duress"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Eight.click();
        verifyDisarm();
        if (home.Disarmed_text.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code works"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not worked"));
        }
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        arming.Duress_Authentication.click();
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
