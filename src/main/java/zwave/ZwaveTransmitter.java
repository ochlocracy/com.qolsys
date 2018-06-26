package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import panel.*;
import utils.ConfigProps;
import utils.Setup;
import utils.ZTransmitter;


import java.io.IOException;

import static utils.ConfigProps.*;

/**
 * Created by qolsys on 11/28/17.
 */
public class ZwaveTransmitter extends Setup {
    String remoteNodeAdd = " shell service call qservice 1 i32 0 i32 1560 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteNodeAddAbort = " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteClear = " shell service call qservice 1 i32 0 i32 1562 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteClearAbort = " shell service call qservice 1 i32 0 i32 1563 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String brigeFirmware = " shell service call zwavetransmitservice 1";
    String page_name = " zwave";
    Logger logger = Logger.getLogger(page_name);
    int node;
    ZTransmitter transmit = new ZTransmitter();






    public ZwaveTransmitter() throws Exception {
        ZTransmitter.init();
        ConfigProps.init();
    }



    // bridge will be included to the Gen2 an nodeID 2
    public void localIncludeBridge() throws Exception {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.Zwave_Devices.click();
        //clear transmitter
        logger.info("clearing Transmitter");
        zwave.Clear_Device_Z_Wave_Page.click();
        Thread.sleep(4000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.excludeTransmitter);
        System.out.println(adbPath + " -s " + transmitter + " " + ZTransmitter.excludeTransmitter);
        Thread.sleep(6000);
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        logger.info("Removing all device");
        zwave.Remove_All_Devices_Z_Wave_Page.click();
        Thread.sleep(4000);
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        Thread.sleep(4000);
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        Thread.sleep(1000);
        logger.info("Including transmitter");
        zwave.Add_Device_Z_Wave_Page.click();
        Thread.sleep(2000);
        zwave.Include_Z_Wave_Device_Button.click();
        Thread.sleep(8000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        System.out.println(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        Thread.sleep(60000);

        // add name to transmitter
        zwave.NewDevicePageAddBtn.click();
        Thread.sleep(5000);
        zwave.UnsupportedDeviceAcknowledgement.click();
    }

    public void remoteIncludeBrige() throws IOException, InterruptedException{
        logger.info("Setting panel in remote add mode for Z-Wave");
        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(5000);
        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(7000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        System.out.println(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        Thread.sleep(55000);
        rt.exec(adbPath + " -s " + primary + remoteNodeAddAbort);
        System.out.println(adbPath + " -s " + primary + remoteNodeAddAbort);
    }

    public void remoteAdd() throws IOException, InterruptedException {
        logger.info("Setting panel in remote add mode for Z-Wave");
        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(5000);
        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
    }

//    public void remoteAddAbort() throws IOException, InterruptedException {
//        Thread.sleep(5000);
//        logger.info("Aborting remote add mode");
//        rt.exec(adbPath + " -s " + primary + " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0");
//        Thread.sleep(2000);
//    }

    public void remoteExcludeBridge() throws IOException, InterruptedException {
        logger.info("Panel in remote clear mode with service call");
        Thread.sleep(3000);
        rt.exec(adbPath + " -s " + primary + remoteClear);
        System.out.println(adbPath + " -s " + primary + remoteClear);
        Thread.sleep(3000);
        logger.info("Exclude/Clear transmitter to panel ");
        rt.exec(adbPath + " -s " + transmitter + "" + ZTransmitter.excludeTransmitter);
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






    public void localAddLights() throws Exception {
        // rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
        // zwave.NewDevicePageAddBtn.click();
        // Thread.sleep(3000);
        //            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();


        //**********
        // Number of Light switch added in method
        //Custome name of light "LSW" with incrementing by one for name "LSW1, LSW2"

        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(1000);
        zwave.Include_Z_Wave_Device_Button.click();
        Thread.sleep(4000);
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            Thread.sleep(10000);
            homePage.Back_button.click();
            Thread.sleep(5000);
    }
    public void localTransmitAddDimmer(int numLights) throws Exception {

        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(1000);
        zwave.Include_Z_Wave_Device_Button.click();
        Thread.sleep(2000);
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
        rt.exec(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 4");
        System.out.println(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 4");
        Thread.sleep(25000);
//            zwave.NewDevicePageAddBtn.click();
//            Thread.sleep(3000);
        homePage.Back_button.click();
        Thread.sleep(5000);
//            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();

    }
    public void localAddDoorLock() throws IOException, InterruptedException {
//        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        logger.info("Adding door lock");
//        zwave.Include_Z_Wave_Device_Button.click();
//        Thread.sleep(3000);
////        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
//        rt.exec(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 5");
//        System.out.println(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 5");
//        Thread.sleep(10000);
//        homePage.Back_button.click();
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
        Thread.sleep(5000);
//            zwave.NewDevicePageAddBtn.click();
//            Thread.sleep(3000);
        homePage.Back_button.click();
        Thread.sleep(4000);
//            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();

    }

        public void localAddThermostat() throws Exception {
            ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
            HomePage homePage = PageFactory.initElements(driver, HomePage.class);
            Thread.sleep(1000);
            zwave.Include_Z_Wave_Device_Button.click();
            Thread.sleep(2000);
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
            Thread.sleep(25000);
//            zwave.NewDevicePageAddBtn.click();
//            Thread.sleep(3000);
            homePage.Back_button.click();
            Thread.sleep(5000);
//            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();

        }

    public void localAddGdc() throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(1000);
        zwave.Include_Z_Wave_Device_Button.click();
        Thread.sleep(2000);
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
        rt.exec(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 6");
        System.out.println(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 6");
        Thread.sleep(25000);
//            zwave.NewDevicePageAddBtn.click();
//            Thread.sleep(3000);
        homePage.Back_button.click();
        Thread.sleep(5000);
//            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();

    }
    public void localDeleteLight () throws Exception{

    }

//    public void localLightON() throws IOException, InterruptedException{
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ));
//        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
//        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
//        Thread.sleep(9000);
//
//        for (int i = Lights_number; i > 0; i--) {
//            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//            Thread.sleep(20000);
//        }
//        rt.exec(adbPath + " -s " + primary + remoteNodeAbort);
//        System.out.println(adbPath + " -s " + primary + remoteNodeAbort);
//
//    }

    public void remoteAddThermostat() throws IOException {
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
    }





    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(primary, "http://127.0.1.1", "4723");
    }

    @Test
    public void trasmitterPair() throws Exception {
//        localIncludeBridge();
        removeAllDevices();
//        remoteExcludeBridge();
//        remoteIncludeBrige();

    }


    @Test
    public void addDevices () throws Exception{
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        localZwaveAdd();
        localAddLights();
//        localAddThermostat();
        homePage.Home_button.click();
    }
    @Test
    public void lightTest() throws Exception{
        swipeFromRighttoLeft();
        driver.findElement(By.id("com.qolsys:id/statusButton")).click();
//        log.log(LogStatus.PASS, "Light is turned On");
        Thread.sleep(2000);
        driver.findElement(By.id("com.qolsys:id/statusButton")).click();
//        log.log(LogStatus.PASS, "Light is turned Off");
        Thread.sleep(2000);
    }

    @AfterTest
    public void teardown() throws Exception {
        log.endTestCase(page_name);
        driver.quit();
        service.stop();
    }


}
