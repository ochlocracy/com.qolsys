package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;


public class SlideMenuTest extends Setup {

    String page_name = "Slide Menu";
    Logger logger = Logger.getLogger(page_name);

    public SlideMenuTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Slide_Menu() throws Exception {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        Thread.sleep(1000);

        menu.Slide_menu_open.click();
        elementVerification(menu.System_state_icon, "System state");
        elementVerification(menu.Battery, "Battery");
        elementVerification(menu.WiFi, "Wi-Fi");
        elementVerification(menu.Bluetooth, "Bluetooth");
        elementVerification(menu.Cell_bar, "cellular");
        menu.Cell_bar.click();
        Thread.sleep(1000);
        elementVerification(menu.Cell_info, "cellular info");
        elementVerification(menu.Volume_down, "Volume Down");
        elementVerification(menu.Volume_up, "Volume UP");
        elementVerification(menu.Volume_adjuster, "Volume adjuster");
        elementVerification(menu.Brightness_down, "Brightness Down");
        elementVerification(menu.Brightness_up, "Brightness UP");
        elementVerification(menu.Brightness_adjuster, "Brightness adjuster");
        elementVerification(menu.Settings, "settings");
        elementVerification(menu.Messages_Alerts, "Messages Alerts");
        elementVerification(menu.Photo_Frame, "Photo Frame");
        elementVerification(menu.Clean_Screen, "Clean Screen");
        elementVerification(menu.Espanol, "Espanol");
        elementVerification(menu.Slide_menu_close, "Slide menu close");
        menu.Slide_menu_close.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
