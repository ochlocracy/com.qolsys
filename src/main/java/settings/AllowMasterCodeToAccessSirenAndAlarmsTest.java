package settings;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.InstallationPage;
import panel.SettingsPage;
import panel.SirenAlarmsPage;
import utils.Setup;

import java.io.IOException;

public class AllowMasterCodeToAccessSirenAndAlarmsTest extends Setup {

    String page_name = "Allow Master Code to access Siren and Alarms testing";
    Logger logger = Logger.getLogger(page_name);

    public AllowMasterCodeToAccessSirenAndAlarmsTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Verify_Master_Code_gets_access_to_Siren_and_Alarms_page() throws Exception {
        SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        Thread.sleep(3000);
        logger.info("Navigate to the setting page to enable the access to the Siren and Alarms page using Master Code");
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        Thread.sleep(2000);
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        if (inst.SIREN_AND_ALARMS.isDisplayed()) {
            logger.info("Pass: Siren and Alarms icon is present");
        } else {
            takeScreenshot();
            logger.info("Failed: Siren and Alarms icon is NOT present");
        }
        Thread.sleep(2000);
        settings.Home_button.click();
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.SIREN_AND_ALARMS.click();
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(1000);
        swipeVertical();
        Thread.sleep(1000);
        siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Verify Siren and Alarms icon disappears after disabling the setting");
        navigateToSettingsPage();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        try {
            if (inst.SIREN_AND_ALARMS.isDisplayed())
                takeScreenshot();
            logger.info("Failed: Siren and Alarms icon is present");
        } catch (Exception e) {
            logger.info("Pass: Siren and Alarms icon is NOT present");
        } finally {
        }
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
