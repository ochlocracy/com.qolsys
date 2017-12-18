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
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
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

        element_verification(adv.INSTALLATION, "Installation");
        element_verification(adv.USER_MANAGEMENT, "User Management");
        element_verification(adv.ABOUT, "About");
        element_verification(adv.SYSTEM_TESTS, "System Tests");
        element_verification(adv.ZWAVE_DEVICE_LIST, "Z-Wave Device List");
        element_verification(adv.DEALER_CONTACT, "Dealer Contact");
        element_verification(adv.PANEL_REBOOT, "Panel Reboot");
        element_verification(adv.POWER_DOWN, "Power Down");
        element_verification(adv.UPGRADE_SOFTWARE, "Upgrade Software");
        element_verification(adv.WI_FI, "Wi-Fi");
        element_verification(adv.SOUND, "Sound");
        element_verification(adv.DATE_TIME, "Date &Time");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
