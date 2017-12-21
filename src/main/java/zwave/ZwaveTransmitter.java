package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.DevicesPage;
import panel.InstallationPage;
import utils.Setup;
import utils.ZTransmitter;

import java.io.IOException;

import static utils.ConfigProps.*;

/**
 * Created by qolsys on 11/28/17.
 */
public class ZwaveTransmitter extends Setup {
    String remoteNodeAdd = " shell service call qservice 1 i32 0 i32 1560 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteNodeAbort = " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteClear = " shell service call qservice 1 i32 0 i32 1562 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteClearAbort = " shell service call qservice 1 i32 0 i32 1563 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String page_name = "zwave";
    Logger logger = Logger.getLogger(page_name);
    int node;

    public ZwaveTransmitter() throws Exception {
        ZTransmitter.init();
    }

    // bridge will be included to the Gen2 an nodeID 2
    public void includeBridge() throws Exception {
        logger.info("Panel in remote add mode with service call");
        Thread.sleep(2000);
        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(2000);
        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(5000);
        logger.info("Including transmitter to panel ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        Thread.sleep(60000);
//        logger.info("Aborting remote Add");
//        rt.exec(adbPath + " -s " + gen2UDID + remoteNodeAbort);
//        System.out.println(adbPath + " -s " + gen2UDID + remoteNodeAbort);
    }

    public void remoteAdd() throws IOException, InterruptedException {
        logger.info("Setting panel in remote add mode for Z-Wave");
        rt.exec(adbPath + " -s " + primary + " shell service call qservice 1 i32 0 i32 1560 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0");
        Thread.sleep(3000);
    }

    public void remoteAddAbort() throws IOException, InterruptedException {
        Thread.sleep(5000);
        logger.info("Aborting remote add mode");
        rt.exec(adbPath + " -s " + primary + " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0");
        Thread.sleep(2000);
    }

    public void excludeBridge() throws IOException, InterruptedException {
        logger.info("Panel in remote clear mode with service call");
        Thread.sleep(3000);
        rt.exec(adbPath + " -s " + primary + remoteClear);
        System.out.println(adbPath + " -s " + primary + remoteClear);
        Thread.sleep(3000);
        logger.info("Exclude/Clear transmitter to panel ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        Thread.sleep(60000);
        logger.info("Aborting remote Add");
        rt.exec(adbPath + " -s " + primary + remoteClearAbort);
        System.out.println(adbPath + " -s " + primary + remoteClearAbort);
    }

    public void remoteClearStart() throws IOException, InterruptedException {
        logger.info("Panel in remote clear mode with service call");
        Thread.sleep(3000);
        rt.exec(adbPath + " -s " + primary + remoteClear);
        System.out.println(adbPath + " -s " + primary + remoteClear);
        Thread.sleep(3000);
    }

    public void remoteClearAbort() throws IOException, InterruptedException {
        Thread.sleep(60000);
        logger.info("Aborting remote Add");
        rt.exec(adbPath + " -s " + primary + remoteClearAbort);
        System.out.println(adbPath + " -s " + primary + remoteClearAbort);
    }

    public void includeDoorLock() throws IOException, InterruptedException {
        logger.info("Adding door lock");
        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addDoorlock, node));
    }

    public void localZwaveAdd() throws IOException, InterruptedException {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.Zwave_Devices.click();
        zwave.Add_Device_Z_Wave_Page.click();


    }

    public void remoteAddLights(int Lights_number) throws IOException, InterruptedException {
        Thread.sleep(3000);

        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(9000);

        for (int i = Lights_number; i > 0; i--) {
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            Thread.sleep(20000);
        }
//        rt.exec(adbPath + " -s " + primary + remoteNodeAbort);
//        System.out.println(adbPath + " -s " + primary + remoteNodeAbort);

    }

    public void remoteAddThermostat() throws IOException {
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(primary, "http://127.0.1.1", "4723");

    }

    @Test
    public void trasmitterPair() throws Exception {
        includeBridge();

    }

    @Test
    public void Test1() throws IOException, InterruptedException {
       node = 4;
       includeDoorLock();
    }


    @Test
    public void addLights() throws Exception {
        remoteAddLights(3);

    }

    @AfterMethod
    public void teardown() throws Exception {
        log.endTestCase(page_name);
        driver.quit();
    }


}
