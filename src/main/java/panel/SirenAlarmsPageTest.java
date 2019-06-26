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
        elementVerification(siren.Disable_Siren_Is_Enabled, "Disable Siren Summary");
        siren.Panel_Sirens.click();
        Thread.sleep(1000);
        elementVerification(siren.ALL_SIRENS_OFF, "ALL SIRENS OFF");
        elementVerification(siren.ALL_SIRENS_ON, "ALL SIRENS ON");
        elementVerification(siren.INSTALLER_TEST_MODE, "INSTALLER TEST MODE");
        siren.ALL_SIRENS_OFF.click();
        Thread.sleep(1000);
        elementVerification(siren.Disable_Siren_Summary_disabled, "Disable Siren Summary when Disabled");
        siren.Panel_Sirens.click();
        Thread.sleep(1000);
        siren.INSTALLER_TEST_MODE.click();
        elementVerification(siren.Disable_Siren_Summary_Installer_Mode, "Disable Siren Summary in Installer mode");
        siren.Panel_Sirens.click();
        Thread.sleep(1000);
        siren.ALL_SIRENS_ON.click();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Annunciation_Is_Disabled, "Siren Annunciation Summary");
        siren.Siren_Annunciation.click();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Annunciation_Is_Enabled, "Siren Annunciation Summary when enabled");
        siren.Siren_Annunciation.click();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Verification_is_Disabled, "Fire Verification Summary when Disabled");
        siren.Fire_Verification.click();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Verification_Is_Enabled, "Fire Verification Summary when enabled");
        siren.Fire_Verification.click();
        Thread.sleep(1000);
        elementVerification(siren.Severe_Weather_Siren_Warning_Is_Enabled, "Severe Weather Siren Warning Summary when enabled");
        siren.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        elementVerification(siren.Severe_Weather_Siren_Warning_Is_Disabled, "Severe Weather Siren Warning Summary when disabled");
        siren.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        elementVerification(siren.Dialer_Delay_Summary, "Dialer Delay Summary");
        Thread.sleep(1000);
        siren.Dialer_Delay.click();
        Thread.sleep(1000);
        elementVerification(siren.Set_value_title, "Set Value in sec");
        siren.Cancel.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Timeout_Summary, "Siren Timeout Summary");
        siren.Siren_Timeout.click();
        Thread.sleep(1000);
        elementVerification(siren.Siren_Timeout_title, "Siren Timeout options");
        siren.Siren_Cancel.click();
        Thread.sleep(1000);
        elementVerification(siren.Water_Freeze_Siren_Is_Disabled, "Water/Freeze Siren Summary when disabled");
        siren.Water_Freeze_Siren.click();
        Thread.sleep(1000);
        elementVerification(siren.Water_Freeze_Siren_Is_Enabled, " Water/Freeze Siren Summary when enabled");
        siren.Water_Freeze_Siren.click();
        Thread.sleep(1000);
        elementVerification(siren.Police_Panic_Is_Enabled, "Police Panic Summary when enabled");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Police_Panic_Is_Disabled, "Police Panic Summary when disable");
        siren.Police_Panic.click();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Panic_Is_Enabled, "Fire Panic Summary when enabled");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Fire_Panic_Is_Disabled, "Fire Panic Summary when disabled");
        siren.Fire_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Auxiliary_Panic_Is_Enabled, "Auxiliary Panic Summary when enabled");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Auxiliary_Panic_Is_Disabled, "Auxiliary Panic Summary when disabled");
        siren.Auxiliary_Panic.click();
        Thread.sleep(1000);
        elementVerification(siren.Allow_Master_Code_To_Access_Siren_and_Alarms_Is_Disabled, "Allow Master Code To Access Siren and Alarms Summary");
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(1000);
        elementVerification(siren.Allow_Master_Code_To_Access_Siren_and_Alarms_Is_Enabled, "Allow Master Code To Access Siren and Alarms Summary when enabled");
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}