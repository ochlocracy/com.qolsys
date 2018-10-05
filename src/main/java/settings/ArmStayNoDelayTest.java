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

public class ArmStayNoDelayTest extends Setup {

    ExtentReport rep = new ExtentReport("Settings_Arm_Stay_No_Delay");

    public ArmStayNoDelayTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void Verify_Arm_Stay_No_Delay_works() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        rep.create_report("Arm_Stay_No_Delay_01");
        rep.log.log(LogStatus.INFO, ("*Arm_Stay_No_Delay_01* Enable Arm Stay - No delay -> Expected result = Panel goes into Arm Stay immediately"));
        Thread.sleep(2000);
        ARM_STAY();
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: System is ARMED STAY immediately"));
        Thread.sleep(2000);
        rep.create_report("Arm_Stay_No_Delay_02");
        rep.log.log(LogStatus.INFO, ("*Arm_Stay_No_Delay_02* Disable Arm Stay - No delay -> Expected result = Panel counts down into Arm Stay"));
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        ARM_STAY();
        try {
            if (home.Disarmed_text.getText().equals("ARMED STAY"))
                takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: System is ARMED STAY without delay"));
        } catch (Exception e) {
            rep.log.log(LogStatus.PASS, ("Pass: System is NOT ARMED STAY"));
        } finally {
        }
        Thread.sleep(15000);
        verifyArmstay();
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        swipeVertical();
        swipeVertical();
        arming.Arm_Stay_No_Delay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException, InterruptedException {
        rep.report_tear_down(result);
        driver.quit();
    }
}