package cellular;

import panel.Advanced_Settings_Page;
import panel.Home_Page;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Dual_path_page extends Setup{
    String page_name = "Dual-Path Wi-Fi Test page";
    Logger logger = Logger.getLogger(page_name);

    public Dual_path_page() throws Exception {}



    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

@Test
        public void accessDual_path_page() throws InterruptedException {
            Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
            System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
            navigate_to_Advanced_Settings_page();
            adv.SYSTEM_TESTS.click();
            Thread.sleep(2000);
            sys.DUAL_PATH_TEST.click();
            Thread.sleep(2000);

        }
/*@Test
    public void wifi_data_off() throws IOException {
        String wifi_off = "shell svc wifi disable";
        rt.exec(adbPath + " -s ");
    }*/
// Test1 The default setting values. In this case, dual-path is enabled
   @Test(priority = 1,dependsOnMethods = {"accessDual_path_page"})
    public void Dual_path_page() throws Exception {
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Elements verification, Happy path");
        element_verification(d_test.WiFi_status, "WiFI_status");
        element_verification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        element_verification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        element_verification(d_test.start_button, "start_button");
        element_verification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(6000);
        element_verification(d_test.Test_result, "Dual_path_Test_result_text");
           }
    // Test2 The default setting values. In this case, dual-path and wifi are enabled
    @Test(priority = 2)
    public void Dual_path_test_when_wifi_settings_unchecked() throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        WiFi_setting_page_elements w = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        Home_Page h = PageFactory.initElements(driver, Home_Page.class);
        navigate_to_Advanced_Settings_page();
        adv.WI_FI.click();
        Thread.sleep(2000);
        try {
            if (w.Network_connection_status.isDisplayed()) {
                logger.info("Pass: I see the message 'connected'. Turning off the setting");
                w.Checkbox.click();
            }
        }catch (Exception e){
            logger.info("Failed: No WiFi network connected");
        }finally {}

        h.Back_button.click();
        Thread.sleep(2000);
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(2000);
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("WiFi settings is disabled");
        element_verification(d_test.WiFi_status, "WiFI_status");
        element_verification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        element_verification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        element_verification(d_test.start_button, "start_button");
        element_verification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(30000);
 //       element_verification(d_test.Test_result, "Dual_path_Test_result_text");
        Assert.assertTrue(d_test.Test_result.isDisplayed());
        try{
            Assert.assertTrue(d_test.Test_result.isDisplayed());
            System.out.println("The message is : ***" +d_test.Test_result.getText() +"***");
        }finally {
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





    // Test3 The default setting values. In this case, dual-path and wifi are enabled
   /* @Test(priority = 3)
    public void Dual_path_test_when_mobile_data_disabled() throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        WiFi_setting_page_elements w = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        Home_Page h = PageFactory.initElements(driver, Home_Page.class);
        navigate_to_Advanced_Settings_page();
        adv.WI_FI.click();
        Thread.sleep(2000);


    }*/



    @Test(priority = 3, dependsOnMethods = {"accessDual_path_page"})
    public void Dual_path_disabled() throws Exception {
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Dual path control check-box is Disabled");
        element_verification(d_test.WiFi_status, "WiFI_status");
        element_verification(d_test.Dual_path_Control_check_box, "Dual_path_Control_check_box");
        d_test.Dual_path_Control_check_box.click();
        Thread.sleep(2000);
        element_verification(d_test.start_button, "start_button");
        element_verification(d_test.cancel_button, "cancel_button");
        d_test.start_button.click();
        Thread.sleep(6000);
        element_verification(d_test.chkbox_result_text, "Dual_path_Control_chkbox_result_text");
        element_verification(d_test.Test_result, "Dual_path_Test_result_text");
    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}



