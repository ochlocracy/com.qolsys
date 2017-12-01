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
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Display_page() throws Exception {
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        DisplayPage display = PageFactory.initElements(driver, DisplayPage.class);
        logger.info("Verifying elements on the page...");
        navigate_to_Settings_page();
        settings.DISPLAY.click();
        Thread.sleep(1000);
        element_verification(display.Brightness_Level, "Brightness Level");
        element_verification(display.Brightness_Level_summery_default, "Brightness Level summery");
        display.Brightness_Level.click();
        Thread.sleep(1000);
        element_verification(display.Brightness_Level_title, "Brightness Level title");
        element_verification(display.Brightness_lvl, "Brightness Level");
        element_verification(display.Brightness_Close, "Close button");
        display.Brightness_Close.click();
        Thread.sleep(1000);
        element_verification(display.Font_Size, "Font Size");
        element_verification(display.Font_Size_summery_default, "Font Size summery");
        display.Font_Size.click();
        Thread.sleep(1000);
        element_verification(display.Font_Size_title, "Font Size title");
        element_verification(display.Font_Size_Small, "Font Size Small");
        element_verification(display.Font_Size_Normal, "Font Size Normal");
        element_verification(display.Font_Size_Large, "Font Size Large");
        display.Brightness_Close.click();
        Thread.sleep(1000);
        element_verification(display.Use_24hour_format, "Use 24-hour format");
        element_verification(display.Use_24hour_format_summery_default, "Use 24-hour format summery");
        display.Use_24hour_format.click();
        element_verification(display.Use_24hour_format_summery_enabled, "Use 24-hour format summery when enabled");
        display.Use_24hour_format.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
