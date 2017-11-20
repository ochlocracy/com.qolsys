package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;


public class Slide_Menu_Test extends Setup {

    String page_name = "Slide Menu";
    Logger logger = Logger.getLogger(page_name);

    public Slide_Menu_Test() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }
    @Test
    public void Check_all_elements_on_Slide_Menu() throws Exception {
        Slide_Menu menu = PageFactory.initElements(driver, Slide_Menu.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000); Thread.sleep(1000);

        menu.Slide_menu_open.click();
        element_verification(menu.System_state_icon, "System state");
        element_verification(menu.Battery, "Battery");
        element_verification(menu.WiFi, "Wi-Fi");
        element_verification(menu.Bluetooth, "Bluetooth");
        element_verification(menu.Cell_bar, "cellular");
        menu.Cell_bar.click();
        Thread.sleep(1000);
        element_verification(menu.Cell_info, "cellular info");
        element_verification(menu.Volume_down, "Volume Down");
        element_verification(menu.Volume_up, "Volume UP");
        element_verification(menu.Volume_adjuster, "Volume adjuster");
        element_verification(menu.Brightness_down, "Brightness Down");
        element_verification(menu.Brightness_up, "Brightness UP");
        element_verification(menu.Brightness_adjuster, "Brightness adjuster");
        element_verification(menu.Settings, "settings");
        element_verification(menu.Messages_Alerts, "Messages Alerts");
        element_verification(menu.Photo_Frame, "Photo Frame");
        element_verification(menu.Clean_Screen, "Clean Screen");
        element_verification(menu.Espanol, "Espanol");
        element_verification(menu.Slide_menu_close, "Slide menu close");
        menu.Slide_menu_close.click();
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
