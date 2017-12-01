package practice;

import panel.AboutPage;
import panel.AdvancedSettingsPage;
import utils.Setup;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by qolsys on 7/26/17.
 */
public class Sergio_test extends Setup {
    String page_name = "About_Page_beta";

    public Sergio_test() throws Exception {}

    @BeforeClass
    public void capabilities_setup() throws Exception{
    setup_driver(get_UDID(), "http://127.0.1.1", "4723");
    setup_logger(page_name);
    }

    @Test
    public  void Check_Zwave_Version() throws Exception{
        AboutPage about = PageFactory.initElements(driver, AboutPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        logger.info("Verifying elements on the page...");
        navigate_to_Advanced_Settings_page();
        adv.ABOUT.click();
        Thread.sleep(1000);
        element_verification(about.ZWave_About, "Z-Wave");
        about.ZWave_About.click();
        Thread.sleep(5000);
    }







    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }







}
