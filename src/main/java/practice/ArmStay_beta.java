//package ADC_Sanity_Test;
//
//import adc.adc;
//import panel.Advanced_Settings_Page;
//import panel.Installation_Page;
//import panel.Security_Arming_Page;
//import utils.Setup;
//import sensors.sensors;
//import jxl.read.biff.BiffException;
//import org.apache.log4j.Logger;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;
//import org.testng.annotations.*;
//
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
///**
// * Created by qolsys on 7/25/17.
// */
//
///*** PRECONDITIONS: Disable SIA limits, set Entry-Exit Delay time to 30, 31, 32, 33 sec; Disable ArmStay No-Delay setting ***/
//public class ArmStay_beta extends Setup {
//
//    String page_name = "Arm Stay mode";
//    Logger logger = Logger.getLogger(page_name);
//    sensors sensors = new sensors();
//    adc adc = new adc();
//
//    private int Normal_Exit_Delay = 30000;
//    private String open = "06 00";
//    private String close = "04 00";
//    public ArmStay_beta() throws IOException, BiffException {
//
//    }
//
//    }
//    public void addPrimaryCall(int zone, int group, int sensor_dec, int sensor_type) throws IOException {
//        String add_primary = " shell service call qservice 50 i32 " + zone + " i32 " + group + " i32 " + sensor_dec + " i32 " + sensor_type;
//        rt.exec(adbPath + add_primary);
//    }
//
//    @BeforeTest
//    public void capabilities_setup() throws Exception {
//        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
//        setupLogger(page_name);
//    }
//
//    @BeforeMethod
//    public void webDriver() {
//            adc.webDriverSetUp();
//    }
//    public void ArmStay_NoDelay_Setting () throws InterruptedException {
//        Security_Arming_Page arming = PageFactory.initElements(driver, Security_Arming_Page.class);
//        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
//        Installation_Page inst = PageFactory.initElements(driver, Installation_Page.class);
//        adv.INSTALLATION.click();
//        inst.SECURITY_AND_ARMING.click();
//        Thread.sleep(3000);
//        swipeVertical();
//        arming.Arm_Stay_No_Delay.click();
//        Thread.sleep(2000);
//        }
//
//    @Test
//    public void addContactSensors() throws IOException, InterruptedException {
//        Thread.sleep(2000);
//        addPrimaryCall(1, 10, 6619296, 1);
//        addPrimaryCall(2, 12, 6619297, 1);
//        addPrimaryCall(3, 13, 6619298, 1);
//        addPrimaryCall(4, 14, 6619299, 1);
//        addPrimaryCall(5, 16, 6619300, 1);
//        addPrimaryCall(6, 8, 6619301, 1);
//        addPrimaryCall(7, 9, 6619302, 1);
//        addPrimaryCall(8, 25, 6619303, 1);
//
//        adc.newAdcSession(adc.getAccountId());
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.partialLinkText("sensors")).click();
//        Thread.sleep(2000);
//        adc.requestEquipmentList();
//
//    }
//    @Test (dependsOnMethods = {"addContactSensors"}, retryAnalyzer = RetryAnalizer.class)
//    public void During_Exit_Delay() throws IOException, InterruptedException {
//        logger.info("**************ArmStay -Open/Close Group 10 contact sensor during exit delay**********************");
//        ARM_STAY();
//        Thread.sleep(10000);
//        logger.info("Open/Close a sensor");
//        sensors.primary_call("65 00 0A", open);
//        Thread.sleep(2000);
//        sensors.primary_call("65 00 0A", close);
//        Thread.sleep(Normal_Exit_Delay);
//        //verify_armstay();
//        Thread.sleep(10000);
//        DISARM();
//
//        /*** adc website verification ***/
//
//        adc.newAdcSession(adc.getAccountId());
//        adc.driver1.findElement(By.partialLinkText("History")).click();
//        Thread.sleep(10000);
//        WebElement history_message = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Door/Window 1 (Sensor 1) Opened/Closed')]"));
//        try {
//            Assert.assertTrue(history_message.isDisplayed());{
//                System.out.println("Pass: message is displayed " + history_message.getText());
//            }
//        }finally {
//        }
//        Thread.sleep(5000);
//
//        /**test case 2***/
//
//
//        logger.info("*************ArmStay -Open/Close Group 12 contact sensor during exit delay***********");
//        ARM_STAY();
//        Thread.sleep(10000);
//        logger.info("Open/Close a sensor");
//        sensors.primary_call("65 00 1A", open);
//        Thread.sleep(2000);
//        sensors.primary_call("65 00 1A", close);
//        Thread.sleep(Normal_Exit_Delay);
//        //verify_armstay();
//        Thread.sleep(3000);
//        DISARM();
//
//        adc.newAdcSession(adc.getAccountId());
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.partialLinkText("History")).click();
//        Thread.sleep(10000);
//        WebElement history_message = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Door/Window 2  (Sensor 2) Opened/Closed')]"));
//        try{
//            Assert.assertTrue(history_message.isDisplayed());
//            System.out.println("Pass: message is displayed "+history_message.getText());
//        } finally {}
//        Thread.sleep(2000);
//
//        logger.info("***********ArmStay -Open/Close Group 13 contact sensor during exit delay*************");
//        ARM_STAY();
//        Thread.sleep(10000);
//        logger.info("Open/Close a sensor");
//        sensors.primary_call("65 00 2A", open);
//        Thread.sleep(2000);
//        sensors.primary_call("65 00 2A", close);
//        Thread.sleep(Normal_Exit_Delay);
//        verify_in_alarm();
//        Thread.sleep(2000);
//        enter_default_user_code();
//
//        /*** adc website verification ***/
//
//        adc.newAdcSession(adc.getAccountId());
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.partialLinkText("History")).click();
//        adc.driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        WebElement history_message = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Door/Window 3  (Sensor 3) Opened/Closed')]"));
//        WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Door/Window 3 (Sensor 3) Alarm')]"));
//        try {
//            Assert.assertTrue(history_message.isDisplayed() && history_message_alarm.isDisplayed()); {
//                System.out.println("Pass: message is displayed " + history_message.getText()+" " + history_message_alarm.getText());
//            }
//        }finally {}
//        Thread.sleep(2000);
//
//
//
//
//
//
//
//
//
//
//    }
//        @AfterTest
//        public void tearDown() throws IOException, InterruptedException {
//            driver.quit();
//        }
//        @AfterMethod
//        public void webDriverQuit(){
//            adc.driver1.quit();
//        }
//
//}
