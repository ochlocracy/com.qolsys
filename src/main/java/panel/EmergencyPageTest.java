package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class EmergencyPageTest extends Setup {

    String page_name = "Emergency page";
    Logger logger = Logger.getLogger(page_name);

    public EmergencyPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Emergency_page() throws Exception {
        EmergencyPage emergency_page = PageFactory.initElements(driver, EmergencyPage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        emergency_page.Emergency_Button.click();
        elementVerification(emergency_page.Police_icon, "Police emergency icon");
        elementVerification(emergency_page.Police_title, "Police title");
        elementVerification(emergency_page.Police_Silent, "Police silent Alarm");
        elementVerification(emergency_page.Fire_icon, "Fire emergency icon");
        elementVerification(emergency_page.Fire_title, "Fire title");
        elementVerification(emergency_page.Auxiliary_icon, "Auxiliary emergency icon");
        elementVerification(emergency_page.Auxiliary_title, "Auxiliary title");
        elementVerification(emergency_page.Auxiliary_Silent, "Auxiliary silent Alarm");
        swipeFromLefttoRight();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}