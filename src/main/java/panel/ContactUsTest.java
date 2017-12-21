package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class ContactUsTest extends Setup {

    String page_name = "Contact Us page";
    Logger logger = Logger.getLogger(page_name);

    public ContactUsTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Contact_Us_page() throws Exception {
        ContactUs contact_us = PageFactory.initElements(driver, ContactUs.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);

        contact_us.Contact_Us.click();
        elementVerification(contact_us.Contact_us_tab, "Contact Us tab");
        elementVerification(contact_us.Dealer_website, "Dealer website");
        elementVerification(contact_us.Video_Tutorials_tab, "Video Tutorials tab");
        elementVerification(contact_us.Messages_Alerts_Alarms_tab, "Messages/Alerts/Alarms tab");
        swipeFromLefttoRight();
        Thread.sleep(1000);
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
