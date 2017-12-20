package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class AdvancedSettingsPageTest extends Setup {

    String page_name = "Advanced settings page";
    Logger logger = Logger.getLogger(page_name);

    public AdvancedSettingsPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Advanced_Settings_page() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        menu.Slide_menu_open.click();
        menu.Settings.click();
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();

        elementVerification(adv.INSTALLATION, "Installation");
        elementVerification(adv.USER_MANAGEMENT, "User Management");
        elementVerification(adv.ABOUT, "About");
        elementVerification(adv.SYSTEM_TESTS, "System Tests");
        elementVerification(adv.ZWAVE_DEVICE_LIST, "Z-Wave Device List");
        elementVerification(adv.DEALER_CONTACT, "Dealer Contact");
        elementVerification(adv.PANEL_REBOOT, "Panel Reboot");
        elementVerification(adv.POWER_DOWN, "Power Down");
        elementVerification(adv.UPGRADE_SOFTWARE, "Upgrade Software");
        elementVerification(adv.WI_FI, "Wi-Fi");
        elementVerification(adv.SOUND, "Sound");
        elementVerification(adv.DATE_TIME, "Date &Time");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
