package powerG;

import adc.ADC;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

public class Keypad extends Setup {

    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("Shock_Sensor");

    public Keypad() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setArmStayNoDelay("Enable");
        setAutoStay("Disable");
    }

    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }







    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverQuit(ITestResult result) throws IOException {
        rep.report_tear_down(result);
        adc.driver1.quit();
    }
}
