package updateProcess;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.File;
import java.io.IOException;

public class SanityZWave extends Setup {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    public SanityZWave() throws Exception {}

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void verifyLight() throws Exception {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SanityReport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva")
                .addSystemInfo("Software Version", softwareVersion());

        log = report.startTest("Zwave.Light");

        swipeFromRighttoLeft();
        swipeFromRighttoLeft();
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

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
