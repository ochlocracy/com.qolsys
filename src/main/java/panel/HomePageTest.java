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
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Home_page() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        /*Checking elements on the Header of the page*/

        elementVerification(home_page.Weather_img, "Weather");
        elementVerification(home_page.High_temp, "High Temp");
        elementVerification(home_page.Low_temp, "Low Temp");
        elementVerification(home_page.Time, "Time");
        elementVerification(home_page.Date, "Date");

        /*Checking Contact Us*/

        elementVerification(home_page.Contact_Us, "Contact us");

        /*Checking System State page*/

        elementVerification(home_page.DISARM, "Disarm");
        elementVerification(home_page.Disarmed_text, "Disarmed text");
        elementVerification(home_page.Active_Tab, "Active Tab");
        elementVerification(home_page.All_Tab, "All Tab");
        home_page.DISARM.click();

        elementVerification(home_page.System_status, "DISARMED");
        elementVerification(home_page.ARM_STAY_text, "ArmStay text");
        elementVerification(home_page.ARM_AWAY_text, "ArmAway text");
        elementVerification(home_page.System_state_expand, "Window expand");
        home_page.System_state_expand.click();
        elementVerification(home_page.ARM_STAY_text, "ArmStay text");
        elementVerification(home_page.ARM_AWAY_text, "ArmAway text");
        elementVerification(home_page.Bypass_Tab, "Bypass Tab");
        elementVerification(home_page.Active_Tab_expand, "Active Tab");
        elementVerification(home_page.All_Tab_expand, "All Tab");
        elementVerification(home_page.Exit_Sounds_title, "Exit Sounds Title");
        elementVerification(home_page.Exit_Sounds, "Exit Sounds");
        elementVerification(home_page.Exit_Sounds_value, "Exit Sounds value");
        elementVerification(home_page.Entry_Delay_title, "Entry Delay Title");
        elementVerification(home_page.Entry_Delay, "Entry Delay");
        elementVerification(home_page.Entry_Delay_value, "Entry Delay value");
        elementVerification(home_page.System_state_collapse, "Window collapse");
        home_page.System_state_collapse.click();
        swipeFromLefttoRight();
        Thread.sleep(1000);

        /*Checking Emergency page*/
//
        elementVerification(home_page.Emergency_Button, "Emergency button");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
