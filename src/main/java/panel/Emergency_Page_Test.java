package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class Emergency_Page_Test extends Setup {

    String page_name = "Emergency page";
    Logger logger = Logger.getLogger(page_name);

    public Emergency_Page_Test() throws Exception {}

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_all_elements_on_Emergency_page() throws Exception {
        Emergency_Page emergency_page = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        emergency_page.Emergency_Button.click();
        element_verification(emergency_page.Police_icon, "Police emergency icon");
        element_verification(emergency_page.Police_title, "Police title");
        element_verification(emergency_page.Police_Silent, "Police silent Alarm");
        element_verification(emergency_page.Fire_icon, "Fire emergency icon");
        element_verification(emergency_page.Fire_title, "Fire title");
        element_verification(emergency_page.Auxiliary_icon, "Auxiliary emergency icon");
        element_verification(emergency_page.Auxiliary_title, "Auxiliary title");
        element_verification(emergency_page.Auxiliary_Silent, "Auxiliary silent Alarm");
        swipeFromLefttoRight();
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}