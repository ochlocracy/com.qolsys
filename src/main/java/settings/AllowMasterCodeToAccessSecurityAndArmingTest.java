package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.InstallationPage;
import panel.SecurityArmingPage;
import panel.SettingsPage;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class AllowMasterCodeToAccessSecurityAndArmingTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Sec_and_Arming");

    public AllowMasterCodeToAccessSecurityAndArmingTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Security_and_Arming_page() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        List<WebElement> checkbox = driver.findElements(By.id("com.qolsys:id/statusButton"));

        rep.create_report("MasterCodeAccessSec_and_Arming_01");
        rep.log.log(LogStatus.INFO, ("*MasterCodeAccessSec_and_Arming_01* Enable access to Security and Arming page using Master Code -> Expected result = Security and Arming icon is present"));
        Thread.sleep(3000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
//        swipeVertical();
//        if (!checkAttribute(checkbox.get(0), "checked", "false")) { //needs more work
//            return;
//
//        }
//        if (arming.Allow_Master_Code_To_Access_Security_and_Arming.()) {
//            arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
//        }
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        if (inst.SECURITY_AND_ARMING.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Security and Arming icon is present"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Security and Arming icon is NOT present"));
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);
//        swipeVertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        rep.create_report("MasterCodeAccessSec_and_Arming_02");
        rep.log.log(LogStatus.INFO, ("*MasterCodeAccessSec_and_Arming_02* Disable access to Security and Arming page using Master Code -> Expected result = Security and Arming icon is gone"));
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SECURITY_AND_ARMING.isDisplayed())
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Security and Arming icon is present"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: Security and Arming icon is NOT present"));
        } finally {
        }
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
