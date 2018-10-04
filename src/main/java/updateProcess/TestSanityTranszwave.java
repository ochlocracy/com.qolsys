package updateProcess;

import adc.ADC;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.xpath.SourceTree;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.HomePage;
import utils.ConfigProps;
import utils.Setup;
import utils.ZTransmitter;

import java.io.File;
import java.io.IOException;

import static utils.ConfigProps.primary;

/**
 * Created by qolsys on 2/2/18.
 */
public class TestSanityTranszwave extends Setup{
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    ADC adc = new ADC();

    public TestSanityTranszwave() throws Exception {
        ZTransmitter.init();
        ConfigProps.init();
    }

    public void sanityReportSetup(String nameOfReporter) throws Exception {
        String file = projectPath + "/extent-config-Sanity.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", nameOfReporter)
                .addSystemInfo("Software Version", softwareVersion());

//        log = report.startTest("Zwave.Light");
    }
    public void addToSanityReport(String testName) {
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        log = report.startTest(testName);
    }

    public void preTestSetupNoDW(String testName) throws Exception {
        addToSanityReport(testName);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        //remove all zwave devices
        log.log(LogStatus.INFO, "Removing all device");
        removeAllDevices();
        log.log(LogStatus.PASS, "All devices have been removed");
        //change all zwave settings to default
        log.log(LogStatus.INFO,"Changing Zwave settings to default");
        zwaveSettingReset("ac8312fb");
        log.log(LogStatus.PASS, "Zwave settings set to default");
        //Add 3 door window sensor and call it Front Door, back door, bathroom window
//        log.log(LogStatus.INFO, "Adding 3 door window sensors");
//        addDwSensor("ac8312fb", 3);
//        log.log(LogStatus.PASS, "3 DW sensors added successfully");
        home.Home_button.click();
        logger.info("In Home Page");
    }

    @BeforeClass
    public void setup() throws Exception{
        setupDriver(primary, "http://127.0.1.1", "4723");

    }
    @BeforeMethod
    public void addDevices() throws Exception{
        sanityReportSetup("Sergio Bustos");
        System.out.println("Before Method");
    }

    @Test(priority = 1)
    public void preDoorLockTestSetup() throws Exception {
        preTestSetupNoDW("Test Setup");
    }

    @Test(priority = 2)
    public void pairingTransmitter() throws Exception {
        addToSanityReport("Paring Transmitter");
        log.log(LogStatus.INFO, "Including transmitter");
        localIncludeBridge();
        log.log(LogStatus.PASS, "Transmitter has been included successfully ");
    }

    @Test(priority = 3)
    public void lightTest() throws Exception {
        /*
        swipe to light page
        p)turn light on
        d) turn light off
        p)change dimming level to half
        */
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        addToSanityReport("Light \"ON/OFF\"Test");
        log.log(LogStatus.INFO,"Adding Light locally");
        localZwaveAdd();
        addStockNameLight();
        log.log(LogStatus.PASS,"Stock Name \"LIHGT\" has been added");
        home.Home_button.click();
        log.log(LogStatus.INFO,"Swiping to Lights Page");
        swipeFromRighttoLeft();
        log.log(LogStatus.PASS,"In lights page");
        driver.findElement(By.id("com.qolsys:id/statusButton")).click();
        log.log(LogStatus.PASS, "Light is turned On");
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/statusButton")).click();
        log.log(LogStatus.PASS, "Light is turned Off");
        Thread.sleep(2000);
    }





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





}
