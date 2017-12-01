package settings;

import panel.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Allow_Master_Code_to_access_Siren_and_Alarms_Test extends Setup {

    String page_name = "Allow Master Code to access Siren and Alarms testing";
    Logger logger = Logger.getLogger(page_name);

    public Allow_Master_Code_to_access_Siren_and_Alarms_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

    @Test
    public void Verify_Master_Code_gets_access_to_Siren_and_Alarms_page() throws Exception {
        Siren_Alarms_Page siren = PageFactory.initElements(driver, Siren_Alarms_Page.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        logger.info("Navigate to the setting page to enable the access to the Siren and Alarms page using Master Code");
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigate_to_Settings_page();
        settings.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        Thread.sleep(2000);
        if (inst.SIREN_AND_ALARMS.isDisplayed()){
            logger.info("Pass: Siren and Alarms icon is present");
        }else {  take_screenshot();
            logger.info("Failed: Siren and Alarms icon is NOT present");}
        Thread.sleep(2000);
        settings.Home_button.click();
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipe_vertical();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Verify Siren and Alarms icon disappears after disabling the setting");
        navigate_to_Settings_page();
        settings.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                take_screenshot();
                logger.info("Failed: Siren and Alarms icon is present");
        } catch(Exception e){
            logger.info("Pass: Siren and Alarms icon is NOT present");
        } finally{
        }
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
