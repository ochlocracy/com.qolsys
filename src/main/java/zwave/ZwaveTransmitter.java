package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.DevicesPage;
import panel.HomePage;
import panel.InstallationPage;
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
    String brigeFirmware = " sehll service call zwavetransmitservice 1";
    String page_name = "zwave";
    Logger logger = Logger.getLogger(page_name);
    int node;
    ZTransmitter transmit = new ZTransmitter();

    public ZwaveTransmitter() throws Exception {
        ZTransmitter.init();
        ConfigProps.init();

    }

    public void removeAllDevices() throws Exception{
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        navigateToAdvancedSettingsPage();
        logger.info("Testing Transmitter is operational ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.Zwave_Devices.click();
        zwave.Remove_All_Devices_Z_Wave_Page.click();
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        Thread.sleep(1000);
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        Thread.sleep(1000);
        zwave.Add_Device_Z_Wave_Page.click();
        zwave.Include_Z_Wave_Device_Button.click();
        logger.info("Testing Transmitter is operational ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        logger.info("Including transmitter to panel ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        Thread.sleep(20000);
        homePage.Back_button.click();
        Thread.sleep(1000);
    }
    // bridge will be included to the Gen2 an nodeID 2
    public void localIncludeBridge() throws Exception {
//        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
//        logger.info("Testing Transmitter is operational ");
//        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
//        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
////        logger.info("Panel in remote add mode with service call");
//        Thread.sleep(2000);
////        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
////        Thread.sleep(2000);
////        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
////        Thread.sleep(5000);
//        logger.info("Including transmitter to panel ");
//        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
//        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
//        Thread.sleep(20000);
//        homePage.Back_button.click();
//        Thread.sleep(1000);
//
////        logger.info("Aborting remote Add");
////        rt.exec(adbPath + " -s " + primary + remoteNodeAbort);
////        System.out.println(adbPath + " -s " + primary + remoteNodeAbort);

        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        navigateToAdvancedSettingsPage();
        logger.info("Testing Transmitter is operational ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.Zwave_Devices.click();
        zwave.Remove_All_Devices_Z_Wave_Page.click();
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        Thread.sleep(1000);
        zwave.OK_Z_Wave_Remove_All_Devices_Page.click();
        Thread.sleep(1000);
        zwave.Add_Device_Z_Wave_Page.click();
        zwave.Include_Z_Wave_Device_Button.click();;
        logger.info("Including transmitter to panel ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        Thread.sleep(10000);
        homePage.Back_button.click();
        Thread.sleep(1000);

    }
    public void remoteIncludeBrige() throws IOException, InterruptedException{
        logger.info("Setting panel in remote add mode for Z-Wave");
        rt.exec(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(5000);
        System.out.println(adbPath + " -s " + primary + remoteNodeAdd);
        Thread.sleep(2000);
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        Thread.sleep(10000);
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


    public void localZwaveAdd() throws IOException, InterruptedException {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        navigateToAdvancedSettingsPage();
        logger.info("Testing Transmitter is operational ");
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 1");
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.Zwave_Devices.click();
        zwave.Add_Device_Z_Wave_Page.click();
        zwave.Include_Z_Wave_Device_Button.click();
        Thread.sleep(3000);

    }


    public void localAddLights() throws Exception {
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
            Thread.sleep(5000);
//            zwave.NewDevicePageAddBtn.click();
//            Thread.sleep(3000);
            homePage.Back_button.click();
            Thread.sleep(7000);
//            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();

    }

    public void localIncludeDoorLock() throws IOException, InterruptedException {
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
            Thread.sleep(3000);
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
            Thread.sleep(5000);
//            zwave.NewDevicePageAddBtn.click();
//            Thread.sleep(3000);
            homePage.Back_button.click();
            Thread.sleep(4000);
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







    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(primary, "http://127.0.1.1", "4723");

    }
    @BeforeMethod
    public void trasmitterPair() throws Exception {
//        localIncludeBridge();

    }
//    @Test()
//    public void addDevices () throws Exception{
//        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
//        localZwaveAdd();
////        localAddLights();
//        localAddThermostat();
//        homePage.Home_button.click();
//    }

    @Test(priority = 1)
    public void Test1() throws Exception {
        LightsPage lite = PageFactory.initElements(driver , LightsPage.class);
        ThermostatPage thermo = PageFactory.initElements(driver,ThermostatPage.class);
//        localZwaveAdd();
//        localAddLights();
        for(int i=0; i<=500; i++){
            swipeFromRighttoLeft();
            Thread.sleep(1000);
            lite.Select_All.click();
            Thread.sleep(1000);
            lite.On_Button.click();
            Thread.sleep(1000);
            swipeFromRighttoLeft();
            //thermostat panel action change to heat
            thermo.thermostatMode.click();
            Thread.sleep(2000);
            thermo.Heat_Icon.click();
            Thread.sleep(3000);
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
      //    light action from device  light off "service call zwavetransmitservice 7 i32 <node-id> i32 0 i32 1 i32 2 i32 0 "
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 5 i32 3 i32 0 i32 1 i32 2 i32 0");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 5 i32 3 i32 0 i32 1 i32 2 i32 0");
            Thread.sleep(1000);
            swipeFromRighttoLeft();
            Thread.sleep(3000);
            //"devices The thermostat cool "service call zwavetransmitservice 7 i32 4 i32 0 i32 1 i32 2 i32 0 "
            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 6 i32 4 i32 0 i32 2 i32 2 i32 2");
            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 6 i32 4 i32 0 i32 2 i32 2 i32 2");
            Thread.sleep(2000);
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();
            swipeFromRighttoLeft();

        }

//        Thread.sleep(1000);
//        //"devices The thermostat  heat "service call zwavetransmitservice 7 i32 4 i32 0 i32 1 i32 2 i32 0 "
//        rt.exec(adbPath + " -s " + transmitter + " service call zwavetransmitservice 6 i32 4 i32 0 i32 2 i32 2 i32 1");
////          Thermostat o
//        //"devices The thermostat coole "service call zwavetransmitservice 7 i32 4 i32 0 i32 1 i32 2 i32 0 "
//        Thread.sleep(1000);
//        rt.exec(adbPath + " -s " + transmitter + " service call zwavetransmitservice 6 i32 4 i32 0 i32 2 i32 2 i32 2");
    }


    @AfterMethod
    public void teardown() throws Exception {
        log.endTestCase(page_name);
        driver.quit();
    }


}
