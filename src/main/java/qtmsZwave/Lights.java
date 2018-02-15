package qtmsZwave;

import adc.ADC;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigProps;
import utils.Setup;
import utils.ZTransmitter;

import java.io.File;
import java.io.IOException;

import static utils.ConfigProps.primary;

/**
 * Created by Sergio on 2/14/18.
 */



public class Lights extends Setup {

    String pageName= "QTMS Zwave Light Test";
    Logger logger = Logger.getLogger(pageName);
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    ADC adc = new ADC();



    public Lights() throws Exception {
        ConfigProps.init();
        ZTransmitter.init();

    }
    // Add option for Real device, transmitter, and ADC

    @BeforeClass
    // Have real devices paired before starting test
    public void capabilities_setup() throws Exception{
        setupDriver(primary,"http://127.0.1.1", "4723");
//        setupLogger(pageName);
    }

    @BeforeMethod
    public void setUpWeb() {
        adc.webDriverSetUp();
        //pair light switch and dimmer with Transmitter
    }
   // ADC verification of paired devices
    @Test
    public void adcVerification() throws Exception{
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Sergio Bustos")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("Zwave.Light Test");

    }
    //Pair device
    @Test



    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = captureScreenshot(driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(captureScreenshot(driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
    }
    @AfterClass
    public void driver_quit() throws InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}
