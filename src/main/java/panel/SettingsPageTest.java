package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class SettingsPageTest extends Setup {

    String page_name = "Settings page";
    Logger logger = Logger.getLogger(page_name);

    public SettingsPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Settings_page() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        element_verification(settings.DISPLAY, "Display");
        element_verification(settings.SD_CARD, "SD Card");
        element_verification(settings.WEATHER_TEMPERATURE, "Weather temperature");
        element_verification(settings.STATUS, "Status");
        element_verification(settings.ZWAVE_STATUS, "ZWave device status");
        element_verification(settings.OTHER_ZWAVE, "Other ZWave devices");
        element_verification(settings.AUTOMATION, "Automation");
        element_verification(settings.ACTIVITY_MONITOR, "Activity Monitor");
        element_verification(settings.ADVANCED_SETTINGS, "Advanced settings");
        element_verification(settings.Back_button, "Back button");
        element_verification(settings.Emergency_button, "Emergency button");
        element_verification(settings.Home_button, "Home button");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
