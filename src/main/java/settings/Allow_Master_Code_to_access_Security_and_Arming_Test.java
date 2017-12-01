package settings;

import panel.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Allow_Master_Code_to_access_Security_and_Arming_Test extends Setup {

    String page_name = "Allow Master Code to access Security and Arming testing";
    Logger logger = Logger.getLogger(page_name);

    public Allow_Master_Code_to_access_Security_and_Arming_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

    @Test
    public void Verify_Master_Code_gets_access_to_Security_and_Arming_page() throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        logger.info("Navigate to the setting page to enable the access to the Security and Arming page using Master Code");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        swipe_vertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigate_to_Settings_page();
        settings.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        Thread.sleep(2000);
        if (inst.SECURITY_AND_ARMING.isDisplayed()){
            logger.info("Pass: Security and Arming icon is present");
        }else { take_screenshot();
            logger.info("Failed: Security and Arming icon is NOT present");}
        Thread.sleep(2000);
        settings.Home_button.click();
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(2000);
        swipe_vertical();
        arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Verify Security and Arming icon disappears after disabling the setting");
        navigate_to_Settings_page();
        settings.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        Thread.sleep(2000);
        try {
            if (inst.SECURITY_AND_ARMING.isDisplayed())
                take_screenshot();
                logger.info("Failed: Security and Arming icon is present");
            } catch(Exception e){
                logger.info("Pass: Security and Arming icon is NOT present");
            } finally{
            }
        }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
