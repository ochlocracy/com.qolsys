package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Access_Point_Test extends Setup {


    String page_name = "Qolsys Access Point testing";
    Logger logger = Logger.getLogger(page_name);

    public Access_Point_Test() throws Exception {
    }
//////2.1 version AccessPoint is Enabled by default////////////////////
    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);}

    @Test(priority = 1)
    public void Verify_elements_on_Access_Point_page() throws Exception {
        WiFi_Devices_Page access = PageFactory.initElements(driver, WiFi_Devices_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Devices_Page devices = PageFactory.initElements(driver, Devices_Page.class);
        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        devices.WiFi_Devices.click();
        access.Access_Point_Settings.click();
        //element_verification(access.Wifi_Access_Point_summery,"Access Point default summery"); //????
        //access.Wifi_Access_Point.click();
        TimeUnit.SECONDS.sleep(4);
        logger.info("Verifying elements on the page...");
        element_verification(access.Wifi_Access_Point_summery_Enabled,"Access Point summery when Enabled");
        element_verification(access.SSID,"SSID");
        element_verification(access.DHCP_Range_summery,"DHCP Range default summery");
        element_verification(access.Change_Password,"Change Password");
  //      element_verification(access.AP_MODE_summery_Hidden,"AP Mode default summery");
  //      access.AP_MODE.click();
        TimeUnit.SECONDS.sleep(1);
 //       element_verification(access.AP_MODE_BROADCAST,"AP Mode Broadcast");
  //      element_verification(access.AP_MODE_HIDDEN,"AP Mode Hidden");
 //       access.Access_Point_Cancel_Button.click();
        swipe_vertical();
        element_verification(access.WPS_PUSH_BUTTON,"WPS Push Button");
//        boolean WPS_status = access.WPS_PUSH_BUTTON.isEnabled();
//        if (WPS_status == false) {
//            logger.info("WPS PUSH BUTTON is Disabled");
//        }else { take_screenshot();
//            logger.info("WPS PUSH BUTTON is Enabled");}
        element_verification(access.AP_PASSWORD,"AP Password");
        swipe_vertical_up();
 //       access.Wifi_Access_Point.click();

    }
    @Test (priority = 2)
    public void Verify_Access_Point() throws Exception {
        WiFi_Devices_Page access = PageFactory.initElements(driver, WiFi_Devices_Page.class);
        Settings_Page settings = PageFactory.initElements(driver, Settings_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Devices_Page devices = PageFactory.initElements(driver, Devices_Page.class);
        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        devices.WiFi_Devices.click();
        access.Qolsys_Access_Point.click();
        //access.Wifi_Access_Point.click();
        TimeUnit.SECONDS.sleep(2);
        element_verification(access.Wifi_Access_Point_summery_Enabled, "Access point summery");
        TimeUnit.SECONDS.sleep(2);
        settings.Back_button.click();
        TimeUnit.SECONDS.sleep(1);
        access.Associated_Wifi_Clients.click();
        TimeUnit.SECONDS.sleep(1);
        element_verification(access.No, "NO");
        element_verification(access.MAC_Address, "MAC Address");
        element_verification(access.IP_Address, "IP Address");
        element_verification(access.Connected_Time, "Connected Time");
        element_verification(access.Actions, "Actions");
        settings.Back_button.click();
        TimeUnit.SECONDS.sleep(1);
        access.Qolsys_Access_Point.click();
        logger.info("Changing SSID...");
        access.SSID.click();
        TimeUnit.SECONDS.sleep(2);
        access.SSID_txt_field.click();
        access.SSID_txt_field.clear();
        access.SSID_txt_field.sendKeys("test-panel001");
        TimeUnit.SECONDS.sleep(1);
        access.Access_Point_OK_Button.click();
        TimeUnit.SECONDS.sleep(7);
        access.Change_Password.click();
        TimeUnit.SECONDS.sleep(2);
        logger.info("Changing password...");
        access.New_Password_field.sendKeys("newpassword123");
        access.Confirm_Password_field.sendKeys("newpassword123");
        TimeUnit.SECONDS.sleep(1);
        access.Access_Point_OK_Button.click();
        TimeUnit.SECONDS.sleep(7);
//        logger.info("Changing AP mode to broadcast...");
//        access.AP_MODE.click();
//        TimeUnit.SECONDS.sleep(1);
//        access.AP_MODE_BROADCAST.click();
//        TimeUnit.SECONDS.sleep(7);
//        swipe_vertical();
//        TimeUnit.SECONDS.sleep(2);
//        element_verification(access.AP_MODE_summery_Broadcast, "AP mode Broadcast");
//        TimeUnit.SECONDS.sleep(1);
//        boolean WPS_status = access.WPS_PUSH_BUTTON.isEnabled();
//        if (WPS_status == true) {
//            logger.info("WPS PUSH BUTTON is Enabled ");
//        }else { take_screenshot();
//            logger.info("WPS PUSH BUTTON is Disabled ");}
//        logger.info("Changing AP mode to hidden...");
//        access.AP_MODE.click();
//        TimeUnit.SECONDS.sleep(1);
//        access.AP_MODE_HIDDEN.click();
//        TimeUnit.SECONDS.sleep(8);
//        element_verification(access.AP_MODE_summery_Hidden, "AP mode Hidden");
//        TimeUnit.SECONDS.sleep(2);
//        boolean WPS_status_new = access.WPS_PUSH_BUTTON.isEnabled();
//        if (WPS_status_new == false) {
//            logger.info("WPS PUSH BUTTON is Disabled ");
//        }else { take_screenshot();
//            logger.info("WPS PUSH BUTTON is Enabled ");}
//        TimeUnit.SECONDS.sleep(1);
        logger.info("Changing SSID...");
        access.SSID.click();
        TimeUnit.SECONDS.sleep(2);
        access.SSID_txt_field.clear();
        access.SSID_txt_field.sendKeys("default_name");
        TimeUnit.SECONDS.sleep(1);
        access.Access_Point_OK_Button.click();
        TimeUnit.SECONDS.sleep(7);
        swipe_vertical_up();
        access.Wifi_Access_Point.click();
        TimeUnit.SECONDS.sleep(2);
        element_verification(access.Wifi_Access_Point_summery, "Access point summery");
        TimeUnit.SECONDS.sleep(1);
        settings.Back_button.click();
        TimeUnit.SECONDS.sleep(1);
        //access.Associated_Wifi_Clients.click();
        TimeUnit.SECONDS.sleep(1);
        try {
            if (access.No.isDisplayed())
                take_screenshot();
            logger.info("Failed: Able to access Associated WiFi Clients page");
        } catch (Exception e) {
            logger.info("Pass: Not able to access Associated WiFi Clients page");
        } finally {
        }
        TimeUnit.SECONDS.sleep(2);
    }
    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}