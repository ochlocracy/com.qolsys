package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AccessPointTest extends Setup {

    String page_name = "Qolsys Access Point page testing";
    Logger logger = Logger.getLogger(page_name);

    public AccessPointTest() throws Exception {
    }
    //////2.1.0rc15.1 version AccessPoint is Disabled by default////////////////////
    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test(priority = 1)
    public void Verify_elements_on_Access_Point_page() throws Exception {
        WiFi_Devices_Page access = PageFactory.initElements(driver, WiFi_Devices_Page.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        DevicesPage devices = PageFactory.initElements(driver, DevicesPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        devices.WiFi_Devices.click();
        Thread.sleep(1000);
        access.Access_Point_Settings.click();
        element_verification(access.Wifi_Access_Point_summery,"Access Point default summery");
        access.Wifi_Access_Point.click();
        TimeUnit.SECONDS.sleep(10);
        logger.info("Verifying elements on the page...");
        System.out.println(driver.findElement(By.id("android:id/summary")).getText());
        element_verification(access.SSID, "SSID");
        element_verification(access.DHCP_Range_summery, "DHCP Range default summery");
        element_verification(access.Change_Password, "Change Password");
        TimeUnit.SECONDS.sleep(1);
        swipe_vertical();
        element_verification(access.ActiveWPS, "Active WPS");
        element_verification(access.AccessPointPassword, "Access Point Password");
        swipe_vertical_up();
        access.Wifi_Access_Point.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}