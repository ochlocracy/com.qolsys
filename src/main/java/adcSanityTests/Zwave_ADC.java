package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import utils.Setup;

import java.io.IOException;

/**
 * Created by nchortek on 7/25/17.
 */
public class Zwave_ADC extends Setup {

    ADC adc = new ADC();
    String page_name = "Zwave_ADC ExtentManager";
    Logger logger = Logger.getLogger(page_name);
    //adc Credentials
    String user_login = "Gen2-8334";
    String user_password = "qolsys1234";
    String accountID = adc.getAccountId();

    public Zwave_ADC() throws Exception {
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver("ac8312fd", "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    //******************************************
    //************* SMOKE TEST *****************
    //******************************************

    @Test
    public void remote_add() throws Exception {
        logger.info("navigating to dealer site...");
        adc.New_ADC_session(accountID);
        adc.driver1.findElement(By.xpath("/html/body/form/table/tbody/tr/td[2]/div/div[2]/div[3]/div/div/ul/" +
                "li[5]/a")).click();
        adc.driver1.findElement(By.id("ctl00_phBody_ZWaveDeviceList_btnAdvancedZWaveCommands")).click();
        adc.driver1.findElement(By.id("ctl00_phBody_ZWaveDeviceList_btn_RemoteAdd")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_ZWaveRemoteAddDevices_btnSave" +
                "AndExit")));

        //check panel UI
        elementVerification(driver.findElement(By.id("com.qolsys:id/title")), "Remote Add Status");
        adc.driver1.findElement(By.id("ctl00_phBody_ZWaveRemoteAddDevices_btnSaveAndExit")).click();
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
