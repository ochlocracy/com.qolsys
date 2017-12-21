package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class InstallationPageTest extends Setup {

    String page_name = "Installation page";
    Logger logger = Logger.getLogger(page_name);

    public InstallationPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Installation_page() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        elementVerification(instal.DEVICES, "Devices");
        elementVerification(instal.DEALER_SETTINGS, "Dealer settings");
        elementVerification(instal.SYSTEM_LOGS, "System Logs");
        elementVerification(instal.SIREN_AND_ALARMS, "Siren and Alarms");
        elementVerification(instal.SECURITY_AND_ARMING, "Security and Arming");
        elementVerification(instal.CAMERA_SETTINGS, "Camera settings");
        elementVerification(instal.LOAD_HELP_VIDEOS, "Load Help Videos");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
