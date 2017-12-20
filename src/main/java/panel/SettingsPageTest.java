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
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Settings_page() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        elementVerification(settings.DISPLAY, "Display");
        elementVerification(settings.SD_CARD, "SD Card");
        elementVerification(settings.WEATHER_TEMPERATURE, "Weather temperature");
        elementVerification(settings.STATUS, "Status");
        elementVerification(settings.ZWAVE_STATUS, "ZWave device status");
        elementVerification(settings.OTHER_ZWAVE, "Other ZWave devices");
        elementVerification(settings.AUTOMATION, "Automation");
        elementVerification(settings.ACTIVITY_MONITOR, "Activity Monitor");
        elementVerification(settings.ADVANCED_SETTINGS, "Advanced settings");
        elementVerification(settings.Back_button, "Back button");
        elementVerification(settings.Emergency_button, "Emergency button");
        elementVerification(settings.Home_button, "Home button");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
