package cellular;

import org.openqa.selenium.By;
import org.testng.Assert;
import panel.*;
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
    servcall.APN_disable();
    Thread.sleep(3000);
    servcall.data_verification();
    Thread.sleep(3000);
    servcall.get_WiFi_name();
    Thread.sleep(3000);
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
           Thread.sleep(8000);
           element_verification(dual.Test_result, "Test result");
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
        Settings_Page set = PageFactory.initElements(driver, Settings_Page.class);
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
                Thread.sleep(8000);
                element_verification(dual.Test_result, "Test result");
            }}
        Thread.sleep(8000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected error when NO wifi connection");
       // servcall.Wifi_enable();// turning on wiFi */
        }
    /*** WiFi Off, Cell On, DualPath Off ***/
    @Test (priority = 3)
    public void test4() throws Exception {
        logger.info("/*** WiFi Off, Cell On, DualPath Off ***/");
        servcall.Wifi_disable(); //  Precondition
        Thread.sleep(3000);
        servcall.data_verification();
        Thread.sleep(3000);
        //servcall.get_WiFi();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        Settings_Page set = PageFactory.initElements(driver, Settings_Page.class);
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
    @Test(priority = 2)
    public void test3() throws Exception {
        logger.info("/*** WiFi Off, Cell Off, DualPath On ***/");
        servcall.APN_enable();
        Thread.sleep(3000);
        servcall.Wifi_disable(); // Precondition
        Thread.sleep(3000);
        servcall.data_verification();
        Thread.sleep(3000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        Settings_Page set = PageFactory.initElements(driver, Settings_Page.class);
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
                Thread.sleep(8000);
                element_verification(dual.Test_result, "Test result");
            }}
        Thread.sleep(8000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message: 'Dual-Path is not enabled; Wi-Fi test is not supported.'");
        servcall.APN_disable();// turning on cell
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
        Thread.sleep(3000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        Settings_Page set = PageFactory.initElements(driver, Settings_Page.class);
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
        servcall.APN_enable();
        Thread.sleep(5000);
        servcall.Wifi_enable(); // Precondition
        Thread.sleep(5000);
        servcall.data_verification();
        Thread.sleep(3000);
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
                Thread.sleep(8000);
                element_verification(dual.Test_result, "Test result");
            }}
        dual.start_button.click();
        Thread.sleep(10000);
        element_verification(dual.Test_result, "Test result");
        logger.info("Pass: expected message: 'Dual-Path is not enabled; Wi-Fi test is not supported.'");
         servcall.APN_disable();// turning on cell
        Thread.sleep(5000);
         servcall.Wifi_enable();
        Thread.sleep(53000);
    }
    public void accessDual_path_page() throws InterruptedException {
        navigate_to_Advanced_Settings_page();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        adv.SYSTEM_TESTS.click();
        Thread.sleep(2000);
        sys.DUAL_PATH_TEST.click();
        Thread.sleep(2000);

    }
    @Test(priority = 7)
    public void Dual_path_page() throws Exception {
        accessDual_path_page();
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
    @Test(priority = 8)
    public void Dual_path_test_when_wifi_settings_unchecked() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        WiFi_setting_page_elements w = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        HomePage h = PageFactory.initElements(driver, HomePage.class);
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
        element_verification(d_test.Test_result, "Dual_path_Test_result_text");
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
    @Test(priority = 9)
    public void Dual_path_test_when_mobile_data_disabled() throws Exception {
        servcall.APN_enable();
        Thread.sleep(5000);
        servcall.Wifi_enable();
        Thread.sleep(5000);
        accessDual_path_page();
        Dual_path_page_elements d_test = PageFactory.initElements(driver, Dual_path_page_elements.class);
        logger.info("Dual path control check-box is Enabled");
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
        servcall.APN_disable();// turning on cell
        Thread.sleep(5000); }

    @Test(priority = 10)
    public void Dual_path_disabled() throws Exception {
        accessDual_path_page();
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

    public void UploadLogs() throws Exception {
        driver.findElement(By.xpath("//android.widget.TextView[@text='Installation']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='System Logs']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='Upload Logs to the server']")).click();
        if (driver.findElement(By.xpath("//android.widget.TextView[@text='System Logs']")).isDisplayed())
        {driver.findElement(By.xpath("//android.widget.TextView[@text='System Logs']")).click();}
        else {
            System.out.println("***** Warning!!! No Message 'Do you want to upload system logs to the server ?' is present*****");
        }}


       @Test(priority = 11)//Cell&WiFi On
       public void UploadLogsPositive() throws Exception {
           navigate_to_Advanced_Settings_page();
        UploadLogs();
        driver.findElement(By.id("com.qolsys:id/ok")).click();
           if (driver.findElement(By.xpath("//android.widget.TextView[@text='Uploading logs started']")).isDisplayed())
           {driver.findElement(By.xpath("//android.widget.TextView[@text='Uploading logs started']")).click();
               System.out.println("Pass: Message 'Uploading logs started' is present");}
           else {
               System.out.println("Fail: ***** Warning!!! No Message 'Uploading logs started' is present*****");}
       }
    @Test(priority = 12)//Cell&WiFi On
    public void UploadLogsCancell() throws Exception {
        navigate_to_Advanced_Settings_page();
        UploadLogs();
        driver.findElement(By.id("com.qolsys:id/cancel")).click();
        if (driver.findElement(By.xpath("//android.widget.TextView[@text='Upload Logs to the server']")).isDisplayed())
        {System.out.println("pass 'Uploading logs' has cancelled");}
        else {
        System.out.println("fail: cancellation is broken");}}
    @Test(priority = 13)//CellOn & WiFi Off
    public void UploadLogsPositiveCellON() throws Exception {
        servcall.Wifi_disable();
        Thread.sleep(5000);
        Settings_Page set = PageFactory.initElements(driver, Settings_Page.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        navigate_to_Settings_page();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enter_default_dealer_code();
        UploadLogs();
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        if (driver.findElement(By.xpath("//android.widget.TextView[@text='Uploading logs started']")).isDisplayed())
        {driver.findElement(By.xpath("//android.widget.TextView[@text='OK']")).click();
            System.out.println("Message 'Uploading logs started' is present");}
        else {
            System.out.println("Fail: ***** Warning!!! No Message 'Uploading logs started' is present*****");}
            Thread.sleep(4000);
       // if (driver.findElement(By.id("com.qolsys:id/title")).isDisplayed())
       // {driver.findElement(By.id("com.qolsys:id/ok")).click();
       //     System.out.println("Message 'Could not connect with server, Wi-Fi is not enabled. Please check Wi-Fi network connection' is present");}
    //    else {
        //    System.out.println("Fail: ***** Warning!!! No Message 'Could not connect with server, Wi-Fi is not enabled. Please check Wi-Fi network connection' is present*****");}
    }
    @Test(priority = 14)//CellOn & WiFi Off
    public void UploadLogsCancell_CellON() throws Exception {
        //servcall.Wifi_disable();
        Thread.sleep(5000);
        Settings_Page set = PageFactory.initElements(driver, Settings_Page.class);
        WiFi_setting_page_elements wifi = PageFactory.initElements(driver, WiFi_setting_page_elements.class);
        navigate_to_Settings_page();
        wifi.OK.click();
        set.ADVANCED_SETTINGS.click();
        enter_default_dealer_code();
        Thread.sleep(2000);
        UploadLogs();
        driver.findElement(By.id("com.qolsys:id/cancel")).click();
        if (driver.findElement(By.xpath("//android.widget.TextView[@text='Upload Logs to the server']")).isDisplayed())
        {System.out.println("pass 'Uploading logs' has cancelled");}
        else {
            System.out.println("fail: cancellation is broken");}
    servcall.Wifi_enable();
    Thread.sleep(5000);}

    @Test(priority = 15)//CellOff & WiFi On
    public void UploadLogsCancell_CellOff() throws Exception {
        servcall.APN_enable();
        Thread.sleep(5000);
        servcall.Wifi_enable();
        Thread.sleep(5000);
        navigate_to_Advanced_Settings_page();
        Thread.sleep(2000);
        UploadLogs();
        driver.findElement(By.id("com.qolsys:id/cancel")).click();
        if (driver.findElement(By.xpath("//android.widget.TextView[@text='Upload Logs to the server']")).isDisplayed())
        {System.out.println("pass 'Uploading logs' has cancelled");}
        else {
            System.out.println("fail: cancellation is broken");}
        }
    @Test(priority = 16)//Cell&WiFi On
    public void UploadLogsPositiveCellOff() throws Exception {
        navigate_to_Advanced_Settings_page();
        UploadLogs();
        driver.findElement(By.id("com.qolsys:id/ok")).click();
        if (driver.findElement(By.xpath("//android.widget.TextView[@text='Uploading logs started']")).isDisplayed())
        {driver.findElement(By.xpath("//android.widget.TextView[@text='Uploading logs started']")).click();
            System.out.println("Pass: Message 'Uploading logs started' is present");}
        else {
            System.out.println("Fail: ***** Warning!!! No Message 'Uploading logs started' is present*****");}
    }
     @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();

    }

}
