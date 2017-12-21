package cellular;

import panel.AdvancedSettingsPage;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Cellular_Tests_page extends Setup {

    String page_name = "Test cellular Path to Alarm.com page";
    Logger logger = Logger.getLogger(page_name);

    public Cellular_Tests_page() throws Exception {
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
        setupLogger(page_name);}

    @Test(priority = 1)
    public void Check_all_elements_on_Cellular_test_page() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Cellular_test_page_elements c_test = PageFactory.initElements(driver, Cellular_test_page_elements.class);
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.CELLULAR_TEST.click();
        Thread.sleep(2000);
        elementVerification(c_test.Carrier_name, "Carrier name");
        elementVerification(c_test.Cellular_status, "cellular Status");
        elementVerification(c_test.IMEI, "IMEI");
        elementVerification(c_test.signal_strength, "signal strength");
        elementVerification(c_test.start_button, "start_button");
        elementVerification(c_test.cancel_button, "cancel_button");
        Thread.sleep(2000);
        c_test.start_button.click();
        Thread.sleep(10000);
        elementVerification(c_test.test_result, "test result");
    }
    @Test (priority = 2)
    public void Cancel_Cellular_test_after_running() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Cellular_test_page_elements c_test = PageFactory.initElements(driver, Cellular_test_page_elements.class);
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.CELLULAR_TEST.click();
        Thread.sleep(2000);
        elementVerification(c_test.Carrier_name, "Carrier name");
        elementVerification(c_test.Cellular_status, "cellular Status");
        elementVerification(c_test.IMEI, "IMEI");
        elementVerification(c_test.signal_strength, "signal strength");
        elementVerification(c_test.start_button, "start_button");
        elementVerification(c_test.cancel_button, "cancel_button");
        Thread.sleep(2000);
        c_test.start_button.click();
        Thread.sleep(1000);
        c_test.cancel_button.click();
        Thread.sleep(1000);
        elementVerification(c_test.test_result, "test result");}

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
