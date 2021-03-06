package cellular;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup;

import java.io.IOException;

public class Dual_path_page extends Setup {
    String page_name = "Dual-Path Wi-Fi Test page";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public Dual_path_page() throws Exception {
    }


    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    public void accessDual_path_page() throws InterruptedException {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        menu.Slide_menu_open.click();
        menu.Settings.click();
        Thread.sleep(2000);
        try {
        if (driver.findElementById("com.qolsys:id/ok").isDisplayed()) ;
        {
            driver.findElementById("com.qolsys:id/ok").click();
        }}
        catch (Exception e) {
            logger.info("Failed: No WiFi network connected");
        }

        settings.ADVANCED_SETTINGS.click();
        Thread.sleep(2000);
        enterDefaultDealerCode();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(2000);
    }

    @Test
    public void Dual_path_page() throws Exception {
        accessDual_path_page();
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Elements verification, Happy path");
        elementVerification(d_test.WiFi_status, "WiFI_status");
        elementVerification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        elementVerification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        elementVerification(d_test.start_button, "start_button");
        elementVerification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(6000);
        elementVerification(d_test.Test_result, "Dual_path_Test_result_text");
    }

    // Test2 The default setting values. In this case, dual-path and wifi are enabled
    @Test(priority = 1)
    public void Dual_path_test_when_wifi_settings_unchecked() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        WiFi_setting_page_elements w = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        HomePage h = PageFactory.initElements(driver, HomePage.class);
        navigateToAdvancedSettingsPage();
        adv.WI_FI.click();
        Thread.sleep(2000);
        try {
            if (w.Network_connection_status.isDisplayed()) {
                logger.info("Pass: I see the message 'connected'. Turning off the setting");
                w.Checkbox.click();
            }
        } catch (Exception e) {
            logger.info("Failed: No WiFi network connected");
        } finally {
        }

        h.Back_button.click();
        Thread.sleep(2000);
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(2000);
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("WiFi settings is disabled");
        elementVerification(d_test.WiFi_status, "WiFI_status");
        elementVerification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        elementVerification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        elementVerification(d_test.start_button, "start_button");
        elementVerification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(30000);
        elementVerification(d_test.Test_result, "Dual_path_Test_result_text");
        Assert.assertTrue(d_test.Test_result.isDisplayed());
        try {
            Assert.assertTrue(d_test.Test_result.isDisplayed());
            System.out.println("The message is : ***" + d_test.Test_result.getText() + "***");
        } finally {
        }
        h.Back_button.click();
        Thread.sleep(2000);
        h.Back_button.click();
        Thread.sleep(2000);
        adv.WI_FI.click();
        Thread.sleep(6000);
        w.Checkbox.click();
        logger.info("The WiFi setting is enabled back");
    }

    @Test
    public void Dual_path_test_when_mobile_data_() throws Exception {
        servcall.APN_disable();
        Thread.sleep(3000);
    }

    // Test3 The default setting values. In this case, dual-path and wifi are enabled
    @Test(priority = 2)
    public void Dual_path_test_when_mobile_data_disabled() throws Exception {
        servcall.APN_enable();
        Thread.sleep(10000);
        servcall.Wifi_enable();
        Thread.sleep(10000);
        accessDual_path_page();
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Dual path control check-box is Enabled");
        elementVerification(d_test.WiFi_status, "WiFI_status");
        elementVerification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        d_test.Dual_path_Control_check_box.click();
        Thread.sleep(2000);
        elementVerification(d_test.start_button, "start_button");
        elementVerification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(6000);
        elementVerification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        elementVerification(d_test.Test_result, "Dual_path_Test_result_text");
        servcall.APN_disable();// turning on cell
        Thread.sleep(10000);
    }

    @Test(priority = 3)
    public void Dual_path_disabled() throws Exception {
        accessDual_path_page();
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Dual path control check-box is Disabled");
        elementVerification(d_test.WiFi_status, "WiFI_status");
        elementVerification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        d_test.Dual_path_Control_check_box.click();
        Thread.sleep(2000);
        elementVerification(d_test.start_button, "start_button");
        elementVerification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(6000);
        elementVerification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        elementVerification(d_test.Test_result, "Dual_path_Test_result_text");
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}



