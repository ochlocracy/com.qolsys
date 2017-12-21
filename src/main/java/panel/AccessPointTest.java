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
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test(priority = 1)
    public void Verify_elements_on_Access_Point_page() throws Exception {
        WiFiDevicesPage access = PageFactory.initElements(driver, WiFiDevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        DevicesPage devices = PageFactory.initElements(driver, DevicesPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        devices.WiFi_Devices.click();
        Thread.sleep(1000);
        access.Access_Point_Settings.click();
        elementVerification(access.Wifi_Access_Point_summery,"Access Point default summery");
        access.Wifi_Access_Point.click();
        TimeUnit.SECONDS.sleep(10);
        logger.info("Verifying elements on the page...");
        System.out.println(driver.findElement(By.id("android:id/summary")).getText());
        elementVerification(access.SSID, "SSID");
        elementVerification(access.DHCP_Range_summery, "DHCP Range default summery");
        elementVerification(access.Change_Password, "Change Password");
        TimeUnit.SECONDS.sleep(1);
        swipeVertical();
        elementVerification(access.ActiveWPS, "Active WPS");
        elementVerification(access.AccessPointPassword, "Access Point Password");
        swipeVerticalUp();
        access.Wifi_Access_Point.click();
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}