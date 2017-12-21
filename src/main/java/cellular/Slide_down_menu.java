package cellular;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.SlideMenu;
import utils.Setup;

import java.io.IOException;

public class Slide_down_menu extends Setup {
    String page_name = "Slide down Menu";
    Logger logger = Logger.getLogger(page_name);
    public Slide_down_menu() throws Exception {
    }

    public void swipe_vertical1() throws InterruptedException {
        int starty = 620;
        int endy = 250;
        int startx = 1000;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
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
        elementVerification(menu.Cell_bar, "cellular");
        menu.Cell_bar.click();
        Thread.sleep(1000);
        elementVerification(menu.Cell_info, "cellular info");

        String Carrier_name = (menu.Cell_info.getText()).split(":")[0];
        System.out.println("Carrier name: " + Carrier_name);
        String Cellular_Signal_Strength = (menu.Cell_info.getText()).substring(7).split("/")[0];
        // System.out.println("The value " + Cellular_Signal_Strength + " is used for comparison");
        // int Cellular_Signal_Strength_in_bars = Integer.parseInt(Cellular_Signal_Strength);


        elementVerification(menu.Slide_menu_close, "Slide menu close");
        menu.Slide_menu_close.click();

    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}

