package qtmsSRF;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.ContactUs;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.SensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;

public class ActivateSRF extends Setup {

    ExtentReports report;
    ExtentTest log;
    ExtentTest test;
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();
    String OFF = "00000000";

    ExtentReport rep = new ExtentReport("SRF_Sensors");

    public ActivateSRF() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    @BeforeTest
    public void setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    public void open_close(String DLID) throws InterruptedException, IOException {
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(1000);
    }

    @Test
    public void sensorsCheck() throws Exception {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/SRFreport.html", false);
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Becky Jameson")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest("QTMS.ActivateAllSRF");

        logger.info("Open-Close contact sensors");
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        for (int i = 1; i > 0; i--) {

            open_close("65 00 0A");
            open_close("65 00 1A");
            open_close("65 00 2A");
            open_close("65 00 3A");
            open_close("65 00 4A");
            open_close("65 00 5A"); //Reporting Safety Sensor
            enterDefaultUserCode();
            Thread.sleep(4000);
            open_close("65 00 6A"); //Delayed Reporting Safety Sensor
            enterDefaultUserCode();
            Thread.sleep(4000);
            open_close("65 00 7A"); //Local Safety Sensor. (Doesn't Alarm)
            Thread.sleep(1000);

            logger.info("Activate motion sensors");
            sensors.primaryCall("55 00 44", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 54", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 64", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 74", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 84", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);

            logger.info("Activate smoke sensors");
            sensors.primaryCall("67 00 22", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("67 00 22", SensorsActivity.RESTORE);


            logger.info("Activate CO sensors");
            sensors.primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate glassbreak sensors");
            sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
            sensors.primaryCall("67 00 99", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
            sensors.primaryCall("67 00 39", SensorsActivity.RESTORE);  //NOT
            Thread.sleep(1000);

            logger.info("Open/close tilt sensors");
            open_close("63 00 EA");
            open_close("63 00 FA");
            open_close("63 00 0A");
            Thread.sleep(1000);

            logger.info("Activate IQShock sensors");
            sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
            sensors.primaryCall("66 00 C9", SensorsActivity.RESTORE); //NOT working
            Thread.sleep(1000);
            sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
            sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
            Thread.sleep(1000);

            logger.info("Activate freeze sensors");
            sensors.primaryCall("73 00 1A", SensorsActivity.ACTIVATE);
            sensors.primaryCall("73 00 1A", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            //enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate heat sensors");
            sensors.primaryCall("75 00 26", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("75 00 26", SensorsActivity.RESTORE);


            logger.info("Activate water sensors");
            sensors.primaryCall("75 11 0A", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("75 11 0A", SensorsActivity.RESTORE);


            logger.info("Activate keyfob sensors");
            sensors.primaryCall("65 00 AF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("65 00 AF", SensorsActivity.RESTORE);


            sensors.primaryCall("65 00 BF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            //UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
            //user_m.User_Management_Delete_User_Ok.click(); when wellness is enabled
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("65 00 BF", SensorsActivity.RESTORE);


            sensors.primaryCall("65 00 CF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("65 00 CF", SensorsActivity.RESTORE);

            logger.info("Activate keypad sensors");
            sensors.primaryCall("85 00 AF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("85 00 AF", SensorsActivity.RESTORE);


            sensors.primaryCall("85 00 BF", "04 04");
            Thread.sleep(13000);
            verifyArmaway();
            sensors.primaryCall("85 00 BF", "08 01");
            Thread.sleep(1000);

            logger.info("Activate medical pendant sensors");
            sensors.primaryCall("61 12 13", "03 01");
            Thread.sleep(2000);
//            user_m.User_Management_Delete_User_Ok.click(); when wellness is enabled
            enterDefaultUserCode();
            Thread.sleep(1000);

            sensors.primaryCall("61 12 23", "03 01");
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            sensors.primaryCall("61 12 23", "04 01");


            logger.info("Activate doorbell sensors");
            open_close("61 BD AA");
            Thread.sleep(1000);

            contact.acknowledge_all_alerts();
            swipeLeft();
            Thread.sleep(1000);

            System.out.println(i);

        }
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
    public void driver_quit() throws InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}