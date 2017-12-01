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
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Security_Arming_page() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        element_verification(arming.Dealer_Code_summery, "Dealer Code summery");
        element_verification(arming.Installer_Code_summery, "Installer Code summery");
        element_verification(arming.Duress_Authentication_summery, "Duress Authentication summery"); //DISABLED by default
        arming.Duress_Authentication.click();
        element_verification(arming.Duress_Authentication_summery_enabled, "Duress Authentication summery when enabled");
        arming.Duress_Authentication.click();
        element_verification(arming.SwingerShutdown, "Swinger Shutdown");
        element_verification(arming.SwingerShutdownEnabled, "Swinger shutdown enabled");
        arming.SwingerShutdown.click();
        element_verification(arming.SwingerShutdownDisabled, "Swinger shutdown disabled");
        arming.SwingerShutdown.click();
        element_verification(arming.SwingerShutdownCount, "Swinger Shutdown Count");
        swipe_vertical();


        element_verification(arming.Secure_Arming_summery, "Secure Arming summery");  //DISABLED by default
        arming.Secure_Arming.click();
        element_verification(arming.Secure_Arming_summery, " Secure Arming summery when enabled");
        arming.Secure_Arming.click();
        swipe_vertical();
        Thread.sleep(1000);
        element_verification(arming.Refuse_Arming_When_Battery_Low_summery, "Refuse Arming When Battery Low summery"); //DISABLED by default
        arming.Refuse_Arming_When_Battery_Low.click();
        element_verification(arming.Refuse_Arming_When_Battery_Low_summery, "Refuse Arming When Battery Low summery");
        arming.Refuse_Arming_When_Battery_Low.click();
        element_verification(arming.Auto_Bypass_summery, "Auto Bypass summery"); //ENABLED by default
        arming.Auto_Bypass.click();
        element_verification(arming.Auto_Bypass_summery, "Auto Bypass summery when disabled");
        arming.Auto_Bypass.click();
        element_verification(arming.Auto_Stay_summery, " Auto Stay summery"); //ENABLED by default
        arming.Auto_Stay.click();
        element_verification(arming.Auto_Stay_summery, "Auto Stay summery when disabled");
        arming.Auto_Stay.click();
        swipe_vertical();
        Thread.sleep(1000);
        element_verification(arming.Arm_Stay_No_Delay_summery, "Auto Stay No Delay summery when enabled");
        arming.Arm_Stay_No_Delay.click();
        element_verification(arming.Arm_Stay_No_Delay_summery, "Auto Stay No Delay summery"); //ENABLED by default
        arming.Arm_Stay_No_Delay.click();
        element_verification(arming.Auto_Exit_Time_Extension_summery, "Auto Exit Time Extension summery"); //ENABLED by default
        arming.Auto_Exit_Time_Extension.click();
        element_verification(arming.Auto_Exit_Time_Extension_summery, "Auto Exit Time Extension summery when disabled");
        arming.Auto_Exit_Time_Extension.click();
        element_verification(arming.Keyfob_Instant_Arming_summery, "Keyfob Instant Arming");  //ENABLED by default
        arming.Keyfob_Instant_Arming.click();
        element_verification(arming.Keyfob_Instant_Arming_summery, "Keyfob Instant Arming summery when disabled");
        arming.Keyfob_Instant_Arming.click();
        swipe_vertical();
        Thread.sleep(1000);
        element_verification(arming.Keyfob_Alarm_Disarm_summery, "Keyfob Alarm Disarm summery");  //DISABLED by default
        arming.Keyfob_Alarm_Disarm.click();
        element_verification(arming.Keyfob_Alarm_Disarm_summery_enabled, "Keyfob Alarm Disarm summery when enabled");
        arming.Keyfob_Alarm_Disarm.click();
        element_verification(arming.Keyfob_Disarming_summery, "Keyfob Disarming summery");  //ENABLED by default
        arming.Keyfob_Disarming.click();
        element_verification(arming.Keyfob_Disarming_summery, "Keyfob Disarming summery when disabled");
        arming.Keyfob_Disarming.click();
        element_verification(arming.Allow_Master_Code_To_Access_Security_and_Arming_summery, "Allow Master Code To Access Security and Arming summery");  //DISABLED by default
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        element_verification(arming.Allow_Master_Code_To_Access_Security_and_Arming_summery_enabled, "Allow Master Code To Access Security and Arming summery when enabled");
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        swipe_vertical();
        element_verification(arming.Normal_Entry_Delay, "Normal Entry Delay");
        element_verification(arming.Normal_Exit_Delay, "Normal Exit Delay");
        element_verification(arming.Long_Entry_Delay, "Long Entry Delay");
        element_verification(arming.Long_Exit_Delay, " Long Exit Delay");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
