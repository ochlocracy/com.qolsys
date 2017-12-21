package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class SirenAlarmsPageTest extends Setup {

    String page_name = "Siren and Alarms page testing";
    Logger logger = Logger.getLogger(page_name);

    public SirenAlarmsPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Siren_Alarms_page() throws Exception {
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        elementVerification(siren.Disable_Siren_summery, "Disable Siren summery");
        siren.Panel_Sirens.click();
        Thread.sleep(1000);
        elementVerification(siren.ALL_SIRENS_OFF, "ALL SIRENS OFF");
        elementVerification(siren.ALL_SIRENS_ON, "ALL SIRENS ON");
        elementVerification(siren.INSTALLER_TEST_MODE, "INSTALLER TEST MODE");
        siren.ALL_SIRENS_OFF.click();
        Thread.sleep(1000);
        elementVerification(siren.Disable_Siren_summery_disabled, "Disable Siren summery when Disabled");
        siren.Panel_Sirens.click();
        Thread.sleep(1000);
        siren.INSTALLER_TEST_MODE.click();
        elementVerification(siren.Disable_Siren_summery_Installer_Mode, "Disable Siren summery in Installer mode");
        siren.Panel_Sirens.click();
        Thread.sleep(1000);
        siren.ALL_SIRENS_ON.click();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Annunciation_summery, "Siren Annunciation summery");
        siren.Siren_Annunciation.click();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Annunciation_summery_enabled, "Siren Annunciation summery when enabled");
        siren.Siren_Annunciation.click();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Verification_summery, "Fire Verification summery");
        siren.Fire_Verification.click();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Verification_summery_enabled, "Fire Verification summery when enabled");
        siren.Fire_Verification.click();
        Thread.sleep(1000);
        elementVerification(siren.Severe_Weather_Siren_Warning_summery, "Severe Weather Siren Warning summery");
        siren.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        elementVerification(siren.Severe_Weather_Siren_Warning_summery_disabled, "Severe Weather Siren Warning summery when disabled");
        siren.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        elementVerification(siren.Dialer_Delay_summery, "Dialer Delay summery");
        Thread.sleep(1000);
        siren.Dialer_Delay.click();
        Thread.sleep(1000);
        elementVerification(siren.Set_value_title, "Set Value in sec");
        siren.Cancel.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Timeout_summery, "Siren Timeout summery");
        siren.Siren_Timeout.click();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Timeout_title, "Siren Timeout options");
        siren.Siren_Cancel.click();
        Thread.sleep(1000);
        elementVerification(siren.Water_Freeze_Siren_summery, "Water/Freeze Siren summery");
        siren.Water_Freeze_Siren.click();
        Thread.sleep(1000);
        elementVerification(siren.Water_Freeze_Siren_summery_enabled, " Water/Freeze Siren summery when enabled");
        siren.Water_Freeze_Siren.click();
        Thread.sleep(1000);
        elementVerification(siren.Police_Panic_summery, "Police Panic summery");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Police_Panic_summery_disabled, "Police Panic summery when disable");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Panic_summery, "Fire Panic summery");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Panic_summery_disabled, "Fire Panic summery when disabled");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Auxiliary_Panic_summery, "Auxiliary Panic summery");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Auxiliary_Panic_summery_disabled, "Auxiliary Panic summery when disabled");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Allow_Master_Code_To_Access_Siren_and_Alarms_summery, "Allow Master Code To Access Siren and Alarms summery");
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(1000);
        elementVerification(siren.Allow_Master_Code_To_Access_Siren_and_Alarms_summery_enabled, "Allow Master Code To Access Siren and Alarms summery when enabled");
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}