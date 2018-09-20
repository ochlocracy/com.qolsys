package partitions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import sun.security.krb5.Config;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class PartitionSensorTest extends Setup {

    ExtentReport rep = new ExtentReport("Partitions_SensorTest");
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();


    public PartitionSensorTest() throws Exception {
    }

    public void Open(String DLID) throws InterruptedException, IOException {
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(500);
    }
    public void Close(String DLID) throws InterruptedException, IOException {
        sensors.primaryCall(DLID,SensorsActivity.DWRestore);
        Thread.sleep(500);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void P_Tamper_SRF_Test() throws IOException, InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        serv.set_DIALER_DELAY(0);
        rep.create_report("Sensortest_01");
        rep.log.log(LogStatus.INFO, ("*Sensortest_01* Tamper / Restore events to srf on Arm Away and Stay -> Expected result = Sensors will signal and panel will go into alarm."));
        Thread.sleep(4000);

        try {
            if (home.pinpad.isDisplayed()) ;
            {
                enterDefaultDealerCode();
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(15000);

        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", "01 00");
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 1A", "04 00");

        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 1A", "01 00");
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 1A", "04 00");
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", "01 00");
        Thread.sleep(4000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 2A", "04 00");

        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 2A", "01 00");
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 2A", "04 00");
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", "01 00");
        Thread.sleep(4000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 3A", "04 00");

        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 3A", "01 00");
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 3A", "04 00");
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 4A", "01 00");
        Thread.sleep(4000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 4A", "04 00");


        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 4A", "01 00");
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 2A", "04 00");
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        rep.log.log(LogStatus.PASS, ("Pass: SRF sensors put panel into alarm in different partitions"));
    }

    @Test
    public void P_OpenClose_SRF_Test() throws IOException, InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        serv.set_DIALER_DELAY(0);
        rep.create_report("Sensortest_02");
        rep.log.log(LogStatus.INFO, ("*Sensortest_02* Open / Close events to srf on Arm Away and Stay -> Expected result = Sensors will signal and panel will go into alarm."));
        Thread.sleep(4000);

        try {
            if (home.pinpad.isDisplayed()) ;
            {
                enterDefaultDealerCode();
            }
        } catch (NoSuchElementException e) {
        }

        Thread.sleep(15000);

        ARM_STAY();
        sensors.primaryCall("65 00 1A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", "04 00");
        Thread.sleep(4000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 1A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 1A", "04 00");
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        sensors.primaryCall("65 00 2A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", "04 00");
        Thread.sleep(4000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 2A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 2A", "04 00");
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        sensors.primaryCall("65 00 3A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", "04 00");
        Thread.sleep(4000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 3A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 3A", "04 00");
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        sensors.primaryCall("65 00 4A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 4A", "04 00");
        Thread.sleep(4000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 4A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 4A", "01 00");
        swipeVertical();
        Thread.sleep(2000);

        rep.log.log(LogStatus.PASS, ("Pass: SRF sensors put panel into alarm in different partitions"));
    }

        @AfterMethod(alwaysRun = true)
        public void tearDown(ITestResult result) throws IOException, InterruptedException {
            rep.report_tear_down(result);
            driver.quit();
        }
    }
