package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class SecurityArmingPageTest extends Setup {

    String page_name = "Security and Arming page testing";
    Logger logger = Logger.getLogger(page_name);

    public SecurityArmingPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Security_Arming_page() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        elementVerification(arming.Dealer_Code_summery, "Dealer Code summery");
        elementVerification(arming.Installer_Code_summery, "Installer Code summery");
        elementVerification(arming.SwingerShutdown, "Swinger Shutdown");
        elementVerification(arming.SwingerShutdownEnabled, "Swinger shutdown enabled");
        arming.SwingerShutdown.click();
        elementVerification(arming.SwingerShutdownDisabled, "Swinger shutdown disabled");
        arming.SwingerShutdown.click();
        elementVerification(arming.SwingerShutdownCount, "Swinger Shutdown Count");
        swipeVertical();


        elementVerification(arming.Secure_Arming_Summary, "Secure Arming summery");  //DISABLED by default
        arming.Secure_Arming.click();
        elementVerification(arming.Secure_Arming_Summary, " Secure Arming summery when enabled");
        arming.Secure_Arming.click();
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(arming.Refuse_Arming_When_Battery_Low_Summary, "Refuse Arming When Battery Low summery"); //DISABLED by default
        arming.Refuse_Arming_When_Battery_Low.click();
        elementVerification(arming.Refuse_Arming_When_Battery_Low_Summary, "Refuse Arming When Battery Low summery");
        arming.Refuse_Arming_When_Battery_Low.click();
        elementVerification(arming.Auto_Bypass_Summary, "Auto Bypass summery"); //ENABLED by default
        arming.Auto_Bypass.click();
        elementVerification(arming.Auto_Bypass_Summary, "Auto Bypass summery when disabled");
        arming.Auto_Bypass.click();
        elementVerification(arming.Auto_Stay_Summary, " Auto Stay summery"); //ENABLED by default
        arming.Auto_Stay.click();
        elementVerification(arming.Auto_Stay_Summary, "Auto Stay summery when disabled");
        arming.Auto_Stay.click();
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(arming.Arm_Stay_No_Delay_Summary, "Auto Stay No Delay summery when enabled");
        arming.Arm_Stay_No_Delay.click();
        elementVerification(arming.Arm_Stay_No_Delay_Summary, "Auto Stay No Delay summery"); //ENABLED by default
        arming.Arm_Stay_No_Delay.click();
        elementVerification(arming.Auto_Exit_Time_Extension_Summary, "Auto Exit Time Extension summery"); //ENABLED by default
        arming.Auto_Exit_Time_Extension.click();
        elementVerification(arming.Auto_Exit_Time_Extension_Summary, "Auto Exit Time Extension summery when disabled");
        arming.Auto_Exit_Time_Extension.click();
        elementVerification(arming.Keyfob_Instant_Arming_Summary, "Keyfob Instant Arming");  //ENABLED by default
        arming.Keyfob_Instant_Arming.click();
        elementVerification(arming.Keyfob_Instant_Arming_Summary, "Keyfob Instant Arming summery when disabled");
        arming.Keyfob_Instant_Arming.click();
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(arming.Keyfob_Alarm_Disarm_Is_Disabled, "Keyfob Alarm Disarm summery");  //DISABLED by default
        arming.Keyfob_Alarm_Disarm.click();
        elementVerification(arming.Keyfob_Alarm_Disarm_Is_Enabled, "Keyfob Alarm Disarm summery when enabled");
        arming.Keyfob_Alarm_Disarm.click();
        elementVerification(arming.Keyfob_Disarming_Summary, "Keyfob Disarming summery");  //ENABLED by default
        arming.Keyfob_Disarming.click();
        elementVerification(arming.Keyfob_Disarming_Summary, "Keyfob Disarming summery when disabled");
        arming.Keyfob_Disarming.click();
        elementVerification(arming.Allow_Master_Code_To_Access_Security_and_Arming_Is_Disabled, "Allow Master Code To Access Security and Arming summery");  //DISABLED by default
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        elementVerification(arming.Allow_Master_Code_To_Access_Security_and_Arming_Is_Enabled, "Allow Master Code To Access Security and Arming summery when enabled");
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        swipeVertical();
        elementVerification(arming.Normal_Entry_Delay, "Normal Entry Delay");
        elementVerification(arming.Normal_Exit_Delay, "Normal Exit Delay");
        elementVerification(arming.Long_Entry_Delay, "Long Entry Delay");
        elementVerification(arming.Long_Exit_Delay, " Long Exit Delay");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
