package zwave;

import io.appium.java_client.android.AndroidDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import panel.*;
import qtmsZwave.Thermostat;
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
    String lightServiceCall = " service call zwavetransmitservice 3 i32 1";
    String dimmerServiceCall = " service call zwavetransmitservice 3 i32 4";
    String smartSocket = " service call zwavetransmitservice 3 i32 2";
    String doorLockServiceCall = " service call zwavetransmitservice 3 i32 5";
    String thermostatServiceCall = " service call zwavetransmitservice 3 i32 3";
    String gdcServiceCall = "s ervice call zwavetransmitservice 3 i32 6";



    Logger logger = Logger.getLogger(page_name);
    int node;
    ZTransmitter transmit = new ZTransmitter();






    public ZwaveTransmitter() throws Exception {
        ZTransmitter.init();
        ConfigProps.init();
    }


//***********************done************
    // bridge will be included to the Gen2 an nodeID 2
    public void localIncludeBridge() throws Exception {
        InstallationPage Install = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver,HomePage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        Install.DEVICES.click();
        dev.zwaveDevices.click();
        logger.info("Clearing Transmitter");
        zwave.clearDeviceZwavePage.click();
        Thread.sleep(4000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.excludeTransmitter);
        System.out.println(adbPath + " -s " + transmitter + " " + ZTransmitter.excludeTransmitter);
        Thread.sleep(6000);
        zwave.oKBtnZwaveRemoveAllDevicesPage.click();
        logger.info("Removing all devices");
        zwave.removeAllDevicesZwavePage.click();
        waitForElementnClick(driver,zwave.oKBtnZwaveRemoveAllDevicesPage,zwave.oKBtnZwaveRemoveAllDevicesPage,10);

        waitForElementnClick(driver,zwave.oKBtnZwaveRemoveAllDevicesPage,zwave.oKBtnZwaveRemoveAllDevicesPage,10);
        logger.info("all devices are removed");
        Thread.sleep(2000);
        logger.info("Including transmitter");
        zwave.addDeviceZwavePage.click();
        Thread.sleep(2000);
        zwave.includeZwaveDeviceButton.click();
        Thread.sleep(7000);
        rt.exec(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        logger.info(adbPath + " -s " + transmitter + " " + ZTransmitter.includeTransmitter);
        logger.info("waiting for Element Button");
        waitForElement(driver, zwave.newDevicePageAddBtn,90);
        zwave.nameSelectionDropDown.click();
        zwave.customDeviceName.click();
        Thread.sleep(2000);
        zwave.customNameField.sendKeys("Transmitter");
        try {
            driver.hideKeyboard();
            logger.info("Hiding Keyboard");
        }catch (Exception e){
            logger.info("Keyboard is still Present");
        }
        zwave.newDevicePageAddBtn.click();
        Thread.sleep(2000);
        zwave.UnsupportedDeviceAcknowledgement.click();
        Thread.sleep(2000);
        home.Home_button.click();
        Thread.sleep(2000);
    }

    public void remoteAdd() throws IOException, InterruptedException {

    }

//    public void remoteAddAbort() throws IOException, InterruptedException {
//        Thread.sleep(5000);
//        logger.info("Aborting remote add mode");
//        rt.exec(adbPath + " -s " + primary + " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0");
//        Thread.sleep(2000);
//    }

//    public void remoteExcludeBridge() throws IOException, InterruptedException {
//        logger.info("Panel in remote clear mode with service call");
//        Thread.sleep(3000);
//        rt.exec(adbPath + " -s " + primary + remoteClear);
//        System.out.println(adbPath + " -s " + primary + remoteClear);
//        Thread.sleep(3000);
//        logger.info("Exclude/Clear transmitter to panel ");
//        rt.exec(adbPath + " -s " + transmitter + "" + ZTransmitter.excludeTransmitter);
//        Thread.sleep(60000);
//        logger.info("Aborting remote Add");
//        rt.exec(adbPath + " -s " + primary + remoteClearAbort);
//        System.out.println(adbPath + " -s " + primary + remoteClearAbort);
//
//    }

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




//    public void stockNameLights(){
//        ZWavePage zwave = PageFactory.initElements(driver,ZWavePage.class);
//        driver.findElement(By.xpath("stocklightName")).click();
//    }
//
//    public void addStockZwaveLights(WebElement stockNames) throws Exception{
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        zwave.includeZwaveDeviceButton.click();
//        Thread.sleep(2000);
//        for (WebElement stockName : stockNames) {
//            logger.info("Adding "+ stockName);
//            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//            Thread.sleep(50000);
////            waitForElement(driver, zwave.deviceTypeField, 30);
//            zwave.nameSelectionDropDown.click();
//            stockNameLights();
//            driver.findElement((By)stockNames).click();
//            zwave.newDevicePageAddBtn.click();
//        }
//    }





//    public void addStockZwaveLights(WebElement stockNames) throws Exception{
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        zwave.includeZwaveDeviceButton.click();
//        Thread.sleep(2000);
//        for (WebElement stockName : stockNames) {
//            logger.info("Adding "+ stockName);
//            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//            Thread.sleep(50000);
////            waitForElement(driver, zwave.deviceTypeField, 30);
//            zwave.nameSelectionDropDown.click();
//            stockNameLights();
//            driver.findElement((By)stockNames).click();
//            zwave.newDevicePageAddBtn.click();
//        }
//    }


    public void remoteAddThermostat() throws IOException {
        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
    }





    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(primary, "http://127.0.1.1", "4723");
    }

    @Test
    public void trasmitterPairing() throws Exception {
        localIncludeBridge();
    }

    @Test
    public void addCustomDevices () throws Exception{
        localZwaveAdd();
        addCustomZwaveLight("Srg Light","Garage light");
        addCustomZwaveDimmer("dimmer1");
        addCustomZwaveDoorLock("front Door", "gdc Door");
        addCustomZwaveThermostat("Down Stairs Thermostat");
        addCustomZwaveGdc("Garage Door 1");
    }

    @Test
    public void addStockDevices() throws Exception {
        localZwaveAdd();
        addStockNameLight();
        addStockNameBedroomLight();
    }


    @AfterTest
    public void teardown() throws Exception {
        log.endTestCase(page_name);
        driver.quit();
        service.stop();
    }


}



//
//
//    @Test
//    public void test2() throws Exception{
//        localZwaveDeviceAdd();
//
//
//
//    }
//        for (int i = 1; i <= numLocks; i++){
//            logger.info("Adding Door lock " + i);
//            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
//            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
//            Thread.sleep(5000);
//            waitForElement(driver, zwave.deviceTypeField,30);
//            zwave.nameSelectionDropDown.click();
//            zwave.customDeviceName.click();
//            zwave.customNameField.sendKeys(deviceName);
//            zwave.newDevicePageAddBtn.click();
//        }

//        String deviceName;
//        if (numLocks >= 1) {
//            logger.info("Adding Door lock " + deviceName);
//            rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
//            System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 5");
////            Thread.sleep(5000);
////            waitForElement(driver, zwave.deviceTypeField, 30);
//            waitForText(driver,zwave.deviceType,90);
//            zwave.nameSelectionDropDown.click();
//            zwave.customDeviceName.click();
//            zwave.customNameField.sendKeys(deviceName);
//            zwave.newDevicePageAddBtn.click();
//            Thread.sleep(4000);
//
//        } else {
//        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
//        homePage.Back_button.click();
//        Thread.sleep(4000);
//            homePage.Home_button.click();
//            Thread.sleep(3000);
//            swipeFromRighttoLeft();
//            zwave.newDevicePageAddBtn.click();
//            Thread.sleep(3000);
//        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
//        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
//        logger.info("Adding door lock");
//        zwave.includeZwaveDeviceButton.click();
//        Thread.sleep(3000);
////        rt.exec(String.format("%s -s %s %s %d", adbPath, transmitter, ZTransmitter.addLight));
//        rt.exec(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 5");
//        System.out.println(adbPath + " -s " + transmitter + " service call zwavetransmitservice 3 i32 5");
//        Thread.sleep(10000);
//        homePage.Back_button.click();






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