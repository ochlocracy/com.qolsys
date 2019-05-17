package qtmsSettings;

import cellular.Cellular_test_page_elements;
import cellular.System_Tests_page;
import cellular.WiFi_setting_page_elements;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.PanelInfo_ServiceCalls;
import panel.SettingsPage;
import utils.Setup;

import java.io.IOException;

public class SystemTestWiFiCell extends Setup {
    String page_name = "QTMS SystemTest_WiFI_Cell test cases";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public SystemTestWiFiCell() throws Exception {
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    /*** WiFi is connected ***/
    @Test
    public void SASSY_001_SASST_002() throws Exception {
        servcall.get_WiFi_name();
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        logger.info("Verify Wi-Fi test can be passed when connecting to a router/hotspot network");
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.WIFI_TEST.click();
        sys.WiFiTest_Run_button.click();
        Thread.sleep(3000);
        elementVerification(sys.WiFiTest_result, "Test result");
        elementVerification(sys.WiFiTest_time, "Test time");
        elementVerification(sys.WiFiTest_status, "Test status");
        logger.info(" SASST_001, SASST_002 Pass: Wi-Fi test can be passed when connecting to a router/hotspot network");
        Thread.sleep(5000);
    }

    @Test(priority = 1)
    public void SASSY_004WifiEnabled() throws Exception {
        servcall.get_WiFi();
        Thread.sleep(5000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Cellular_test_page_elements cell = PageFactory.initElements(driver, Cellular_test_page_elements.class);
        logger.info("Verify cellular test passed successfully when wifi is enabled");
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.CELLULAR_TEST.click();
        cell.start_button.click();
        Thread.sleep(3000);
        elementVerification(cell.Carrier_name, "Carrier name");
        elementVerification(cell.Cellular_status, "cellular Status");
        elementVerification(cell.IMEI, "IMEI");
        elementVerification(cell.signal_strength, "signal strength");
        elementVerification(cell.start_button, "start_button");
        elementVerification(cell.cancel_button, "cancel_button");
        logger.info(" SASST_4 Pass: cellular test passed successfully when wifi is enabled");
        Thread.sleep(5000);
    }

    @Test(priority = 2)
    public void SASSY_004WifiDisabled() throws Exception {
        servcall.Wifi_disable();
        Thread.sleep(5000);
        servcall.get_WiFi();
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Cellular_test_page_elements cell = PageFactory.initElements(driver, Cellular_test_page_elements.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        logger.info("Verify cellular test passed successfully when wifi is disabled");
        navigateToSettingsPage();
        Thread.sleep(1000);
        wifi.OK.click();
        settings.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        adv.SYSTEM_TESTS.click();
        sys.CELLULAR_TEST.click();
        cell.start_button.click();
        Thread.sleep(3000);
        elementVerification(cell.Carrier_name, "Carrier name");
        elementVerification(cell.Cellular_status, "cellular Status");
        elementVerification(cell.signal_strength, "signal strength");
        elementVerification(cell.test_result, "Test Result");
        System.out.println("updateProcess of the page");
        cell.Back_button.click();
        sys.CELLULAR_TEST.click();
        cell.start_button.click();
        Thread.sleep(2000);
        elementVerification(cell.Carrier_name, "Carrier name");
        elementVerification(cell.Cellular_status, "cellular Status");
        elementVerification(cell.signal_strength, "signal strength");
        elementVerification(cell.test_result, "Test Result");
        logger.info(" SASST_4 Pass: cellular test passed successfully when wifi is disabled");
        servcall.data_verification();
        Thread.sleep(2000);
        servcall.get_Cell_data();
        Thread.sleep(5000);
        System.out.println("Turning back WiFi");
        servcall.Wifi_enable();
        Thread.sleep(5000);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
