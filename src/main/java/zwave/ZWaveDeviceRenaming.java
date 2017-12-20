package zwave;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import utils.Setup;

import java.io.IOException;
import java.util.List;

/**
 * At least one zwave device must be paired, no devices can be named "TEMP" or "adc"
 */
public class ZWaveDeviceRenaming extends Setup {

    String page_name = "Device Renaming";
    Logger logger = Logger.getLogger(page_name);
    ADC adc = new ADC();
    String rename = "TEMP";
    String rename1 = "adc";

    //test here

    //adc Credentials
    String login = "Gen2-8334";
    String password = "qolsys1234";

    public ZWaveDeviceRenaming() throws Exception {
    }

    public void navigate_to_ZWave_Page() throws InterruptedException {
        navigateToAdvancedSettingsPage();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Installation']")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Devices']")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Z-wave Devices']")).click();
    }

    public void renameLightFromUserSite(String name) throws InterruptedException {
        adc.driver1.findElement(By.id("ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices_" +
                "ctl00_lnkDeviceName")).click();
        Thread.sleep(5000);
        WebElement text = adc.driver1.findElement(By.id("ctl00_phBody_txtEditName"));
        text.clear();
        text.sendKeys(name);
        adc.driver1.findElement(By.id("ctl00_phBody_btnSaveEdit")).click();
        Thread.sleep(5000);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void renameFromPanel() throws Exception {
        int i, j, x, y;

        navigateToAdvancedSettingsPage();

        navigate_to_ZWave_Page();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Edit Device']")).click();
        driver.findElement(By.id("com.qolsys:id/edit")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("android:id/text1")).click();

        List<WebElement> li = driver.findElements(By.id("android:id/text1"));
        WebElement tmp = null;
        for (i = 0; i < 2; i++) {
            tmp = li.get(i);

            if (tmp.getText().equals("Custom Name"))
                break;

            if (i == 1) {
                x = tmp.getLocation().getX();
                y = tmp.getLocation().getY();
                touchSwipe(x, y, x, (y + 300));
                li.clear();
                li = driver.findElements(By.id("android:id/text1"));
                tmp = li.get(i);
                break;
            }
        }

        tmp.click();
        tmp = driver.findElement(By.id("com.qolsys:id/customDesc1"));
        tmp.clear();
        tmp.sendKeys(rename);
        try {
            driver.hideKeyboard();
        } catch (WebDriverException e) {
            logger.info("Soft Keyboard not present, safely skipping [driver.hideKeyboard()]");
        }

        driver.findElement(By.id("com.qolsys:id/editButton")).click();
        Thread.sleep(5000);

        if (!driver.findElement(By.id("com.qolsys:id/nodeName")).getAttribute("text").equals(rename))
            logger.info("Fail: Name change not reflected in panel UI (Edit Z-Wave Devices Page)");
        else
            logger.info("Pass: Name change successfully reflected in panel UI (Edit Z-Wave Devices Page)");

        driver.findElement(By.id("com.qolsys:id/ft_home_button")).click();
        swipeLeft();

        Thread.sleep(2000);
        if (!driver.findElement(By.id("com.qolsys:id/uiName")).getAttribute("text").equals(rename))
            logger.info("Fail: Name change not reflected in panel UI (Lights Page)");
        else
            logger.info("Pass: Name change successfully reflected in panel UI (Lights Page)");

        adc.navigate_to_user_site_lights(login, password);

        for (j = 0; j < 3; j++) {
            if (adc.driver1.findElement(By.id("ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers" +
                    "_rptDevices_ctl0" + j + "_lnkDeviceName")).getText().equals(rename)) {
                logger.info("Pass: Name change successfully reflected on user site");
                break;
            }
        }
        if (j == 3)
            logger.info("Fail: Name change not reflected on user site");

        Thread.sleep(2000);
    }

    @Test(priority = 1)
    public void renameFromUserSite() throws Exception {
        int i, j, size;

        adc.navigate_to_user_site_lights(login, password);
        renameLightFromUserSite(rename1);

        if (driver.findElements(By.id("com.qolsys:id/allOn")).size() == 0)
            swipeLeft();

        List<WebElement> li = driver.findElements(By.id("com.qolsys:id/uiName"));

        size = li.size();
        for (i = 0; i < size; i++) {
            if (li.get(i).getText().equals(rename1)) {
                logger.info("Pass: Name change reflected in panel UI (Lights Page)");
                break;
            }
        }

        if (i == size)
            logger.info("Fail: Name change not reflected in panel UI (Lights Page)");

        li.clear();

        navigate_to_ZWave_Page();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Edit Device']")).click();

        li = driver.findElements(By.id("com.qolsys:id/nodeName"));
        size = li.size();

        for (j = 0; j < size; j++) {
            if (li.get(j).getText().equals(rename1)) {
                logger.info("Pass: Name change reflected in panel UI (Edit Z-Wave Devices Page)");
                break;
            }
        }

        if (i == size)
            logger.info("Fail: Name change not reflected in panel UI (Edit Z-Wave Devices Page)");

        renameLightFromUserSite("Rename Test Complete");
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}
