package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class HomePageTest extends Setup {

    String page_name = "Home page";
    Logger logger = Logger.getLogger(page_name);

    public HomePageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Home_page() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        /*Checking elements on the Header of the page*/

        element_verification(home_page.Weather_img, "Weather");
        element_verification(home_page.High_temp, "High Temp");
        element_verification(home_page.Low_temp, "Low Temp");
        element_verification(home_page.Time, "Time");
        element_verification(home_page.Date, "Date");

        /*Checking Contact Us*/

        element_verification(home_page.Contact_Us, "Contact us");

        /*Checking System State page*/

        element_verification(home_page.DISARM, "Disarm");
        element_verification(home_page.Disarmed_text, "Disarmed text");
        element_verification(home_page.Active_Tab, "Active Tab");
        element_verification(home_page.All_Tab, "All Tab");
        home_page.DISARM.click();

        element_verification(home_page.System_status, "System Status");
        element_verification(home_page.ARM_STAY_text, "ArmStay text");
        element_verification(home_page.ARM_AWAY_text, "ArmAway text");
        element_verification(home_page.System_state_expand, "Window expand");
        home_page.System_state_expand.click();
        element_verification(home_page.System_status, "System Status");
        element_verification(home_page.ARM_STAY_text, "ArmStay text");
        element_verification(home_page.ARM_AWAY_text, "ArmAway text");
        element_verification(home_page.Bypass_Tab, "Bypass Tab");
        element_verification(home_page.Active_Tab_expand, "Active Tab");
        element_verification(home_page.All_Tab_expand, "All Tab");
        element_verification(home_page.Exit_Sounds_title, "Exit Sounds Title");
        element_verification(home_page.Exit_Sounds, "Exit Sounds");
        element_verification(home_page.Exit_Sounds_value, "Exit Sounds value");
        element_verification(home_page.Entry_Delay_title, "Entry Delay Title");
        element_verification(home_page.Entry_Delay, "Entry Delay");
        element_verification(home_page.Entry_Delay_value, "Entry Delay value");
        element_verification(home_page.System_state_collapse, "Window collapse");
        home_page.System_state_collapse.click();
        swipeFromLefttoRight();
        Thread.sleep(1000);

        /*Checking Emergency page*/
//
        element_verification(home_page.Emergency_Button, "Emergency button");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
