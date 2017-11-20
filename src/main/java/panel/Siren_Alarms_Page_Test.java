package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Siren_Alarms_Page_Test extends Setup {

    String page_name = "Siren and Alarms page testing";
    Logger logger = Logger.getLogger(page_name);

    public Siren_Alarms_Page_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

    @Test
    public void Check_all_elements_on_Siren_Alarms_page() throws Exception {
        Siren_Alarms_Page siren = PageFactory.initElements(driver, Siren_Alarms_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        element_verification(siren.Disable_Siren_summery, "Disable Siren summery");
        siren.Disable_Siren.click();
        Thread.sleep(1000);
        element_verification(siren.ALL_SIRENS_OFF, "ALL SIRENS OFF");
        element_verification(siren.ALL_SIRENS_ON, "ALL SIRENS ON");
        element_verification(siren.INSTALLER_TEST_MODE, "INSTALLER TEST MODE");
        siren.ALL_SIRENS_OFF.click();
        Thread.sleep(1000);
        element_verification(siren.Disable_Siren_summery_disabled, "Disable Siren summery when Disabled");
        siren.Disable_Siren.click();
        Thread.sleep(1000);
        siren.INSTALLER_TEST_MODE.click();
        element_verification(siren.Disable_Siren_summery_Installer_Mode, "Disable Siren summery in Installer mode");
        siren.Disable_Siren.click();
        Thread.sleep(1000);
        siren.ALL_SIRENS_ON.click();
        Thread.sleep(1000);
        element_verification(siren.Siren_Annunciation_summery, "Siren Annunciation summery");
        siren.Siren_Annunciation.click();
        Thread.sleep(1000);
        element_verification(siren.Siren_Annunciation_summery_enabled, "Siren Annunciation summery when enabled");
        siren.Siren_Annunciation.click();
        Thread.sleep(1000);
        element_verification(siren.Fire_Verification_summery, "Fire Verification summery");
        siren.Fire_Verification.click();
        Thread.sleep(1000);
        element_verification(siren.Fire_Verification_summery_enabled, "Fire Verification summery when enabled");
        siren.Fire_Verification.click();
        Thread.sleep(1000);
        element_verification(siren.Severe_Weather_Siren_Warning_summery, "Severe Weather Siren Warning summery");
        siren.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        element_verification(siren.Severe_Weather_Siren_Warning_summery_disabled, "Severe Weather Siren Warning summery when disabled");
        siren.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        element_verification(siren.Dialer_Delay_summery, "Dialer Delay summery");
        Thread.sleep(1000);
        siren.Dialer_Delay.click();
        Thread.sleep(1000);
        element_verification(siren.Set_value_title, "Set Value in sec");
        siren.Cancel.click();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        element_verification(siren.Siren_Timeout_summery, "Siren Timeout summery");
        siren.Siren_Timeout.click();
        Thread.sleep(1000);
        element_verification(siren.Siren_Timeout_title, "Siren Timeout options");
        siren.Siren_Cancel.click();
        Thread.sleep(1000);
        element_verification(siren.Water_Freeze_Siren_summery, "Water/Freeze Siren summery");
        siren.Water_Freeze_Siren.click();
        Thread.sleep(1000);
        element_verification(siren.Water_Freeze_Siren_summery_enabled, " Water/Freeze Siren summery when enabled");
        siren.Water_Freeze_Siren.click();
        Thread.sleep(1000);
        element_verification(siren.Police_Panic_summery, "Police Panic summery");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        element_verification(siren.Police_Panic_summery_disabled, "Police Panic summery when disable");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        swipe_vertical();
        Thread.sleep(1000);
        element_verification(siren.Fire_Panic_summery, "Fire Panic summery");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        element_verification(siren.Fire_Panic_summery_disabled, "Fire Panic summery when disabled");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        element_verification(siren.Auxiliary_Panic_summery, "Auxiliary Panic summery");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        element_verification(siren.Auxiliary_Panic_summery_disabled, "Auxiliary Panic summery when disabled");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        element_verification(siren.Allow_Master_Code_To_Access_Siren_and_Alarms_summery, "Allow Master Code To Access Siren and Alarms summery");
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(1000);
        element_verification(siren.Allow_Master_Code_To_Access_Siren_and_Alarms_summery_enabled, "Allow Master Code To Access Siren and Alarms summery when enabled");
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}