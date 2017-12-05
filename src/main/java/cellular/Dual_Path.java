package cellular;

import panel.AdvancedSettingsPage;
import panel.PanelInfo_ServiceCalls;
import panel.SettingsPage;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class Dual_Path extends Setup{
    public Dual_Path() throws Exception {}
    String page_name = "Dual Path testing";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);

    }
/*** WiFi On, Cell On, DualPath On ***/
@Test
public void test1() throws Exception {
    logger.info("/*** WiFi On, Cell On, DualPath On ***/");
    servcall.data_verification();
    servcall.get_WiFi_name();
    AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
    System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
    Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
    navigate_to_Advanced_Settings_page();
    adv.SYSTEM_TESTS.click();
    sys.DUAL_PATH_TEST.click();
    if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
    {dual.start_button.click();}

    else {dual.Dual_path_Control_check_box.click();
       if ( dual.warning_message_OK_button.isDisplayed()){
           logger.info("warning message!Please update #AUT-6 ticket status!");
           dual.warning_message_OK_button.click();
           dual.start_button.click();
                    }
           else {
           dual.start_button.click();
           //Thread.sleep(8000);
           //element_verification(dual.Test_result, "Test result");
       }}
    Thread.sleep(8000);
    element_verification(dual.Test_result, "Test result");
    logger.info("Pass: Dual Path test passed if Dual-Path control is enabled, Wi-Fi and Cell are connected.");
}

    /*** WiFi Off, Cell On, DualPath On ***/
    @Test (priority = 1)
    public void test2() throws Exception {
        logger.info("/*** WiFi Off, Cell On, DualPath On ***/");
        servcall.Wifi_disable(); //  Precondition
        Thread.sleep(3000);
        servcall.data_verification();
        servcall.get_WiFi();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        navigate_to_Settings_page();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
        {dual.start_button.click();}

        else {dual.Dual_path_Control_check_box.click();
            if ( dual.warning_message_OK_button.isDisplayed()){
                logger.info("warning message!Please update #AUT-6 ticket status!");
                dual.warning_message_OK_button.click();
                dual.start_button.click();
            }
            else {
                dual.start_button.click();
                //Thread.sleep(8000);
                //element_verification(dual.Test_result, "Test result");
            }}
        Thread.sleep(8000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected error when NO wifi connection");
       // servcall.Wifi_enable();// turning on wiFi */
        }
    /*** WiFi Off, Cell On, DualPath Off ***/
    @Test (priority = 2)
    public void test4() throws Exception {
        logger.info("/*** WiFi Off, Cell On, DualPath Off ***/");
     // servcall.Wifi_disable(); //  Precondition
     //  Thread.sleep(3000);
        servcall.data_verification();
        //servcall.get_WiFi();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        navigate_to_Settings_page();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
        { System.out.println("Ups...turning Off Dual-Path");
            dual.Dual_path_Control_check_box.click();}
        else {
            System.out.println("Dual Path is OFF");
            }
        dual.start_button.click();
        Thread.sleep(6000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message:' Dual-Path is not enabled; Wi-Fi test is not supported'");
       // servcall.Wifi_enable();// turning on wiFi */
    }
    /*** WiFi Off, Cell Off, DualPath On ***/
    @Test(priority = 3)
    public void test3() throws Exception {
        logger.info("/*** WiFi Off, Cell Off, DualPath On ***/");
        servcall.APN_enable();
        //servcall.Wifi_disable(); // Precondition
        Thread.sleep(3000);
        servcall.data_verification();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        navigate_to_Settings_page();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
        {dual.start_button.click();}

        else {dual.Dual_path_Control_check_box.click();
            if ( dual.warning_message_OK_button.isDisplayed()){
                logger.info("warning message!Please update #AUT-6 ticket status!");
                dual.warning_message_OK_button.click();
                dual.start_button.click();
            }
            else {
                dual.start_button.click();
                //Thread.sleep(8000);
                //element_verification(dual.Test_result, "Test result");
            }}
        Thread.sleep(8000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message: 'Dual-Path is not enabled; Wi-Fi test is not supported.'");
       // servcall.APN_disable();// turning on cell
       // servcall.Wifi_enable();
    }
    /*** WiFi Off, Cell Off, DualPath Off ***/
    @Test(priority = 4)
    public void test7() throws Exception {
        logger.info("/*** WiFi Off, Cell Off, DualPath Off ***/");
       // servcall.APN_enable();
      //  servcall.Wifi_disable(); // Precondition
        Thread.sleep(3000);
        servcall.data_verification();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        SettingsPage set = PageFactory.initElements(driver, SettingsPage.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        navigate_to_Settings_page();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enter_default_user_code();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
        { System.out.println("Ups...turning Off Dual-Path");
            dual.Dual_path_Control_check_box.click();}
        else {
            System.out.println("Dual Path is OFF");
        }
        dual.start_button.click();
        Thread.sleep(8000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message: 'Dual-Path is not enabled; Wi-Fi test is not supported.'");
       // servcall.APN_disable();// turning on cell
       // servcall.Wifi_enable();
    }
    /*** WiFi On, Cell Off, DualPath Off ***/
    @Test(priority = 5)
    public void test6() throws Exception {
        logger.info("/*** WiFi On, Cell Off, DualPath Off ***/");
       // servcall.APN_enable();
        servcall.Wifi_enable(); // Precondition
        Thread.sleep(5000);
        servcall.data_verification();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        navigate_to_Advanced_Settings_page();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
        { System.out.println("Ups...turning Off Dual-Path");
            dual.Dual_path_Control_check_box.click();}
        else {
            System.out.println("Dual Path is OFF");
        }
        dual.start_button.click();
        Thread.sleep(8000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message: 'Dual-Path is not enabled; Wi-Fi test is not supported.'");
        // servcall.APN_disable();// turning on cell
        // servcall.Wifi_enable();
    }
    /*** WiFi On, Cell Off, DualPath Off ***/
    @Test(priority = 6)
    public void test5() throws Exception {
        logger.info("/*** WiFi On, Cell Off, DualPath Off ***/");
        // servcall.APN_enable();
       // servcall.Wifi_enable(); // Precondition
        Thread.sleep(5000);
        servcall.data_verification();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        navigate_to_Advanced_Settings_page();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        if(checkAttribute(dual.Dual_path_Control_check_box, "checked", "true"))
        {dual.start_button.click();}

        else {dual.Dual_path_Control_check_box.click();
            if ( dual.warning_message_OK_button.isDisplayed()){
                logger.info("warning message!Please update #AUT-6 ticket status!");
                dual.warning_message_OK_button.click();
                dual.start_button.click();
            }
            else {
                dual.start_button.click();
                //Thread.sleep(8000);
                //element_verification(dual.Test_result, "Test result");
            }}
        dual.start_button.click();
        Thread.sleep(10000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message: 'Dual-Path is not enabled; Wi-Fi test is not supported.'");
         servcall.APN_disable();// turning on cell
         servcall.Wifi_enable();
    }

     @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();

    }

}
