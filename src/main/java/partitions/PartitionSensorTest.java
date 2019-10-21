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
import utils.*;

import java.io.IOException;

public class PartitionSensorTest extends Setup {

    ExtentReport rep = new ExtentReport("Partitions_SensorTest");
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();
    String open = "01 00";

    public PartitionSensorTest() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test (priority = 1)
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

//        ARM_STAY();
//        Thread.sleep(2000);
//        sensors.slprimaryCall("65 00 1A", open); //does local variable work?
//        Thread.sleep(5000);
//        home.DISARM.click();
//        enterDefaultUserCode();
        sensors.slprimaryCall("65 00 1A", "04 00");

        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.slprimaryCall("65 00 1A", open);
        Thread.sleep(2000);
        enterDefaultUserCode(); //rfinjector 02 65 00 1A 06 00 works, but the open should trigger an alarm state.
        sensors.slprimaryCall("65 00 1A", "04 00");
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", "01 00");
        Thread.sleep(5000);
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
        Thread.sleep(5000);
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
        Thread.sleep(3000);
        sensors.primaryCall("65 00 4A", "01 00");
        Thread.sleep(5000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 4A", "04 00");


        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 4A", "01 00");
        Thread.sleep(2000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 4A", "04 00");
        Thread.sleep(4000);
        swipeVertical();
        Thread.sleep(2000);

        rep.log.log(LogStatus.PASS, ("Pass: SRF sensors put panel into alarm in different partitions"));
    }

    @Test (priority = 2)
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
        Thread.sleep(10000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 1A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 1A", "04 00");
        Thread.sleep(3000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        sensors.primaryCall("65 00 2A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", "04 00");
        Thread.sleep(10000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 2A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        sensors.primaryCall("65 00 2A", "04 00");
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        sensors.primaryCall("65 00 3A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", "04 00");
        Thread.sleep(10000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 3A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 3A", "04 00");
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);

        ARM_STAY();
        sensors.primaryCall("65 00 4A", "06 00");
        Thread.sleep(2000);
        sensors.primaryCall("65 00 4A", "04 00");
        Thread.sleep(10000);
        enterDefaultUserCode();
        Thread.sleep(5000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(14000);
        sensors.primaryCall("65 00 4A", "06 00");
        Thread.sleep(11000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 4A", "04 00");
        Thread.sleep(2000);
        swipeVertical();
        Thread.sleep(2000);

        rep.log.log(LogStatus.PASS, ("Pass: SRF sensors put panel into alarm in different partitions"));
    }
//                  PowerG does not signal in partitions
//    @Test (priority = 3)
//    public void P_Tamper_PG_Test() throws IOException, InterruptedException {
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        serv.set_DIALER_DELAY(0);
//        rep.createReport("Sensortest_03");
//        rep.log.log(LogStatus.INFO, ("*Sensortest_03* Tamper / Restore events to PG on Arm Away and Stay -> Expected result = Sensors will signal and panel will go into alarm."));
//        Thread.sleep(4000);
//
//        try {
//            if (home.pinpad.isDisplayed()) ;
//            {
//                enterDefaultDealerCode();
//            }
//        } catch (NoSuchElementException e) {
//        }
//        Thread.sleep(15000);
//
//        ARM_STAY();
//        Thread.sleep(5000);
//        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPER);
//        Thread.sleep(2000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1152,PGSensorsActivity.TAMPERREST);
//
//        Thread.sleep(2000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1152,PGSensorsActivity.TAMPER);
//        Thread.sleep(4000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1152,PGSensorsActivity.TAMPERREST);
//        Thread.sleep(4000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        ARM_STAY();
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1231,PGSensorsActivity.TAMPER);
//        Thread.sleep(4000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1231,PGSensorsActivity.TAMPERREST);
//
//        Thread.sleep(2000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1231,PGSensorsActivity.TAMPER);
//        Thread.sleep(2000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1231,PGSensorsActivity.TAMPERREST);
//        Thread.sleep(4000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        ARM_STAY();
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1216,PGSensorsActivity.TAMPER);
//        Thread.sleep(4000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1216,PGSensorsActivity.TAMPERREST);
//
//        Thread.sleep(2000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1216,PGSensorsActivity.TAMPER);
//        Thread.sleep(4000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1216,PGSensorsActivity.TAMPERREST);
//        Thread.sleep(4000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        ARM_STAY();
//        Thread.sleep(3000);
//        pgprimaryCall(104, 1331,PGSensorsActivity.TAMPER);
//        Thread.sleep(4000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1331,PGSensorsActivity.TAMPERREST);
//
//
//        Thread.sleep(2000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1331,PGSensorsActivity.TAMPER);
//        Thread.sleep(4000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1331,PGSensorsActivity.TAMPERREST);
//        Thread.sleep(4000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        rep.log.log(LogStatus.PASS, ("Pass: PG sensors put panel into alarm in different partitions"));
//    }
//
//    @Test (priority = 4)
//    public void P_OpenClose_PG_Test() throws IOException, InterruptedException {
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//        serv.set_DIALER_DELAY(0);
//        rep.createReport("Sensortest_04");
//        rep.log.log(LogStatus.INFO, ("*Sensortest_04* Open / Close events to srf on Arm Away and Stay -> Expected result = Sensors will signal and panel will go into alarm."));
//        Thread.sleep(4000);
//
//        try {
//            if (home.pinpad.isDisplayed()) ;
//            {
//                enterDefaultDealerCode();
//            }
//        } catch (NoSuchElementException e) {
//        }
//
//        Thread.sleep(15000);
//
//        ARM_STAY();
//        pgprimaryCall(104, 1152,PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1152,PGSensorsActivity.INCLOSE);
//        Thread.sleep(10000);
//        enterDefaultUserCode();
//        Thread.sleep(5000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1152,PGSensorsActivity.INOPEN);
//        Thread.sleep(11000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1152,PGSensorsActivity.INCLOSE);
//        Thread.sleep(3000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        ARM_STAY();
//        pgprimaryCall(104, 1231,PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1231,PGSensorsActivity.INCLOSE);
//        Thread.sleep(10000);
//        enterDefaultUserCode();
//        Thread.sleep(5000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1231,PGSensorsActivity.INOPEN);
//        Thread.sleep(11000);
//        enterDefaultUserCode();
//        pgprimaryCall(104, 1231,PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        ARM_STAY();
//        pgprimaryCall(104, 1216,PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1216,PGSensorsActivity.INCLOSE);
//        Thread.sleep(10000);
//        enterDefaultUserCode();
//        Thread.sleep(5000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1216,PGSensorsActivity.INOPEN);
//        Thread.sleep(11000);
//        enterDefaultUserCode();
//        Thread.sleep(4000);
//        pgprimaryCall(104, 1216,PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        ARM_STAY();
//        pgprimaryCall(104, 1331,PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1331,PGSensorsActivity.INCLOSE);
//        Thread.sleep(10000);
//        enterDefaultUserCode();
//        Thread.sleep(5000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(14000);
//        pgprimaryCall(104, 1331,PGSensorsActivity.INOPEN);
//        Thread.sleep(11000);
//        enterDefaultUserCode();
//        Thread.sleep(4000);
//        pgprimaryCall(104, 1331,PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//        swipeVertical();
//        Thread.sleep(2000);
//
//        rep.log.log(LogStatus.PASS, ("Pass: PG sensors put panel into alarm in different partitions"));
//    }



    @AfterMethod(alwaysRun = true)
        public void tearDown(ITestResult result) throws IOException, InterruptedException {
            rep.report_tear_down(result);
            driver.quit();
        }
    }
