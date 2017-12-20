package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class DisplayPageTest extends Setup {

    String page_name = "Display page testing";
    Logger logger = Logger.getLogger(page_name);

    public DisplayPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Display_page() throws Exception {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        DisplayPage display = PageFactory.initElements(driver, DisplayPage.class);
        logger.info("Verifying elements on the page...");
        navigateToSettingsPage();
        settings.DISPLAY.click();
        Thread.sleep(1000);
        elementVerification(display.Brightness_Level, "Brightness Level");
        elementVerification(display.Brightness_Level_summery_default, "Brightness Level summery");
        display.Brightness_Level.click();
        Thread.sleep(1000);
        elementVerification(display.Brightness_Level_title, "Brightness Level title");
        elementVerification(display.Brightness_lvl, "Brightness Level");
        elementVerification(display.Brightness_Close, "Close button");
        display.Brightness_Close.click();
        Thread.sleep(1000);
        elementVerification(display.Font_Size, "Font Size");
        elementVerification(display.Font_Size_summery_default, "Font Size summery");
        display.Font_Size.click();
        Thread.sleep(1000);
        elementVerification(display.Font_Size_title, "Font Size title");
        elementVerification(display.Font_Size_Small, "Font Size Small");
        elementVerification(display.Font_Size_Normal, "Font Size Normal");
        elementVerification(display.Font_Size_Large, "Font Size Large");
        display.Brightness_Close.click();
        Thread.sleep(1000);
        elementVerification(display.Use_24hour_format, "Use 24-hour format");
        elementVerification(display.Use_24hour_format_summery_default, "Use 24-hour format summery");
        display.Use_24hour_format.click();
        elementVerification(display.Use_24hour_format_summery_enabled, "Use 24-hour format summery when enabled");
        display.Use_24hour_format.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
