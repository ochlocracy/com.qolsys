package partitions;

import android.graphics.Region;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import qtmsSRF.ArmAway;
import sensors.Sensors;
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

    public void Open_Close(String DLID) throws InterruptedException, IOException {
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(500);
        sensors.primaryCall(DLID,SensorsActivity.CLOSE);

    }

    public void Tamper(String DLID) throws InterruptedException, IOException {
        Thread.sleep(500);
        sensors.primaryCall(DLID,SensorsActivity.TAMPER);
        Thread.sleep(2000);
        sensors.primaryCall(DLID,SensorsActivity.RESTORE);
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test
    public void P1_Test() throws IOException, InterruptedException {
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SecuritySensorsPage sec = PageFactory.initElements(driver, SecuritySensorsPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);

        serv.set_DIALER_DELAY(0);
        rep.create_report("Sensortest_01");
        rep.log.log(LogStatus.INFO, ("*Sensortest_01* Open / Close / Tamper events to both srf and powerg -> Expected result = Sensors will signal."));
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
        sensors.primaryCall("65 00 1A", SensorsActivity.TAMPER); //sensors wont go into tamper
        Thread.sleep(4000);
        home.DISARM.click();
        enterDefaultUserCode();

        Thread.sleep(2000);
        serv.EVENT_ARM_AWAY();
        sensors.primaryCall("65 00 1A", SensorsActivity.TAMPER);
        home.DISARM.click(); //tamper will go into the keypad, will have to remove wehn the tamper works eventually
        enterDefaultUserCode();
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Tamper("65 00 2A");
        Thread.sleep(4000);
        home.DISARM.click();
        enterDefaultUserCode();

        Thread.sleep(2000);
        serv.EVENT_ARM_AWAY();
        Tamper("65 00 2A");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Tamper("65 00 3A");
        Thread.sleep(4000);
        home.DISARM.click();
        enterDefaultUserCode();

        Thread.sleep(2000);
        serv.EVENT_ARM_AWAY();
        Tamper("65 00 3A");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Tamper("65 00 4A");
        Thread.sleep(4000);
        home.DISARM.click();
        enterDefaultUserCode();

        Thread.sleep(2000);
        serv.EVENT_ARM_AWAY();
        Tamper("65 00 4A");
        home.DISARM.click();
        enterDefaultUserCode();
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        Open_Close("65 00 1A");
        Thread.sleep(2000);
        Open_Close("65 00 2A");
        Thread.sleep(2000);
        Open_Close("65 00 3A");
        Thread.sleep(2000);
        Open_Close("65 00 4A");
        Thread.sleep(2000);

    }


        @AfterMethod(alwaysRun = true)
        public void tearDown(ITestResult result) throws IOException, InterruptedException {
            rep.report_tear_down(result);
            driver.quit();
        }
    }


//open close and tamper events
//    starting at 1 then 2 then three then four
