package settings;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import utils.ExtentReport;
import utils.Setup;

import java.io.IOException;

public class SecureArmingTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Secure_Arming");

    public SecureArmingTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Secure_Arming_works() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        rep.create_report("Secure_Arming_01");
        rep.log.log(LogStatus.INFO, ("*Secure_Arming_01* Disable Secure Arming -> Expected result = No code required to Arm the system"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(2000);
        verifyArmstay();
        if (home.ARM_STAY_text.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: System armed without code"));
        }
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        swipeVertical();
        Thread.sleep(2000);
        arming.Secure_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        rep.create_report("Secure_Arming_02");
        rep.log.log(LogStatus.INFO, ("*Secure_Arming_02* Enable Secure Arming -> Expected result = A code is required to Arm the system"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: code is requires to Arm the system"));
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        swipeVertical();
        Thread.sleep(2000);
        arming.Secure_Arming.click();
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}
