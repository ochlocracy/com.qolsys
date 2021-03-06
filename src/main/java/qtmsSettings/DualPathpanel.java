package qtmsSettings;

import cellular.Dual_path_page_elements;
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

public class DualPathpanel extends Setup {
    String page_name = "QTMS SystemTest_DualPath test cases panel part";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public DualPathpanel() throws Exception {
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);}

    /*** WiFi On and Dual Path ON ***/
    @Test
    public void SASST_027() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Verify Dual Path Wi-Fi test pass if Dual-Path control is enabled and Wi-Fi is connected.");
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        dual.start_button.click();
        Thread.sleep(6000);
        elementVerification(dual.Test_result, "Dual_path_Test_result_text");
        logger.info("SASST_027 Pass:Dual Path Wi-Fi test pass if Dual-Path control is enabled and Wi-Fi is connected.");
    }

    /*** WiFi On and Dual Path Off ***/
    @Test(priority = 2)
    public void SASST_028() throws Exception {
        servcall.get_WiFi_name();
        Thread.sleep(4000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Verify Dual Path Wi-Fi test won't pass if Dual-Path control is disabled and Wi-Fi is connected.");
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        dual.Dual_path_Control_check_box.click();
        Thread.sleep(6000);
        elementVerification(dual.chkbox_result_text, "Dual_path_checkbox_text");
        {
            dual.start_button.click();
        }
        Thread.sleep(6000);
        elementVerification(dual.Test_result, "Dual_path_Test_result_text");
        logger.info("SASST_028 Pass:Dual Path Wi-Fi test won't pass if Dual-Path control is disabled and Wi-Fi is connected.");
    }

    @Test(priority = 1)
    public void SASST_019() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Verify Wi-Fi communication test canceled. The message is shown.");
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        dual.start_button.click();
        Thread.sleep(1000);
        dual.cancel_button.click();
        elementVerification(dual.Test_result, "Dual_path_Test_result_text");
        logger.info("SASST_019 Pass:Wi-Fi communication test canceled. The message is shown.");
        Thread.sleep(2000);
    }

    @Test(priority = 3)
    public void SASST_020() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Verify User can enable/disable dual-path control.\n" +
                "Enable message 'Dual-path is enabled; using cellular and Wi-Fi.'\n" +
                "Disable message 'Dual-path is disabled; using cellular only.'");
        navigateToSettingsPage();
        set.ADVANCED_SETTINGS.click();
        Thread.sleep(1000);
        enterDefaultUserCode();
        Thread.sleep(1000);
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(1000);
        dual.Dual_path_Control_check_box.click();
        elementVerification(dual.chkbox_result_text, "Dual_path_checkbox_text");
        Thread.sleep(1000);
        dual.Dual_path_Control_check_box.click();
        elementVerification(dual.chkbox_result_text, "Dual_path_checkbox_text");
        dual.Dual_path_Control_check_box.click();
        logger.info("SASST_020 Pass: User can enable/disable dual-path control.\n" +
                "Enable message 'Dual-path is enabled; using cellular and Wi-Fi.'\n" +
                "Disable message 'Dual-path is disabled; using cellular only.'");
        Thread.sleep(2000);
    }

    /*** WiFi is connected***/
    @Test(priority = 4)
    public void SASST_021022() throws Exception {
        servcall.get_WiFi();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Verify Wi-Fi status shows 'Connected' when Wi-Fi is connected to a router/hotspot network.");
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(1000);
        elementVerification(dual.WiFi_status, "WiFi status");
        logger.info("SASST_021022 Pass:Wi-Fi status shows 'Connected' when Wi-Fi is connected to a router/hotspot network.");
        Thread.sleep(2000);
    }

    @Test(priority = 5)
    public void SASST_023() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        logger.info("Verify Wi-Fi status shows 'Disabled' when Wi-Fi is disabled.");
        servcall.Wifi_disable();
        Thread.sleep(2000);
        servcall.get_WiFi();
        Thread.sleep(2000);
        navigateToSettingsPage();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(1000);
        elementVerification(dual.WiFi_status, "WiFi status");
        logger.info("SASST_023 Pass: Wi-Fi status shows 'Disabled' when Wi-Fi is disabled.");
        // servcall.Wifi_enable();
        Thread.sleep(6000);
    }

    /**
     * Dual path is enabled
     **/
    @Test(priority = 6)
    public void SASST_026() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        logger.info("Verify There is a warning message \n" +
                " 'Network connection failed.' shows after user try to check Dual-Path control checkbox when Wi-Fi is disabled.\n" +
                "WiFi is disabled by service call");
        Thread.sleep(6000);
        servcall.get_WiFi();
        Thread.sleep(4000);
        navigateToSettingsPage();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        Thread.sleep(2000);
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(3000);
        elementVerification(dual.WiFi_status, "WiFi status");
        dual.Dual_path_Control_check_box.click();
        Thread.sleep(2000);
        dual.Dual_path_Control_check_box.click();
        Thread.sleep(2000);
        elementVerification(dual.warning_message, "Warning message");
        System.out.println("Press 'OK'");
        Thread.sleep(2000);
        dual.warning_message_OK_button.click();
        Thread.sleep(2000);
        logger.info("SASST_026 Pass:There is a warning message \n" +
                " 'Network connection failed.' shows after user try to check Dual-Path control checkbox when Wi-Fi is disabled.\n" +
                "WiFi is disabled by service call");
        servcall.Wifi_enable();
        Thread.sleep(6000);
        dual.Back_button.click();
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(2000);
        dual.Dual_path_Control_check_box.click();
//       dual.warning_message_OK_button.click();
        //   dual.Dual_path_Control_check_box.click();
        elementVerification(dual.WiFi_status, "WiFi status");
        Thread.sleep(6000);
    }

    /*** WiFi Off and Dual Path Off ***/

    @Test(priority = 7)
    public void SASST_029() throws Exception {
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        logger.info("Verify Dual Path Wi-Fi test won't pass if Dual-Path control is disabled and Wi-Fi is disconnected.");
        servcall.Wifi_disable();
        Thread.sleep(8000);
        servcall.get_WiFi();
        Thread.sleep(6000);
        navigateToSettingsPage();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enterDefaultUserCode();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(3000);
        elementVerification(dual.chkbox_result_text, "Dual_path_checkbox_text");
        Thread.sleep(10000);
        dual.start_button.click();
        elementVerification(dual.Test_result, "Dual_path_Test_result_text");
        logger.info("SASST_029 Pass:Dual Path Wi-Fi test won't pass if Dual-Path control is disabled and Wi-Fi is disconnected.");
        servcall.Wifi_enable();
        Thread.sleep(8000);
    }


    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }

}