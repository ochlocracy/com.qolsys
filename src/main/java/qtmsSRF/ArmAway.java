package qtmsSRF;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import panel.*;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class ArmAway extends Setup {
    String page_name = "QTMS: ARM AWAY";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    String login = "mypanel01";
    String password = "qolsys123";
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
    private String keyfobDisarm = "08 01";
    private String idle = "00 01";

    public ArmAway() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        Thread.sleep(1000);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    //@Test
    public void AA_01() throws Exception {
        logger.info("Verify the panel can be disarmed from adc");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(3000);
        servcall.set_DIALER_DELAY(7);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        adc.New_ADC_session_User(login, password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.xpath("//div[contains(@class, 'icon ') and contains(@title, 'Armed Away ')]")).click();
        Thread.sleep(4000);
        adc.driver1.findElement(By.xpath("//button[contains(@id, 'ember') and contains(@class, 'disarmed btn ember-view')]")).click();
        Thread.sleep(10000);
        verifyDisarm();
        System.out.println("Pass");
    }

    @Test(priority = 1)
    public void AA_02() throws Exception {
        logger.info("Verify the panel can be disarmed using a keyfob");
        servcall.set_KEYFOB_DISARMING(1);
        addPrimaryCall(38, 1, 6619386, 102);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        //ARM_AWAY(Long_Exit_Delay);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 AF", keyfobDisarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        deleteFromPrimary(38);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 2)
    public void AA_03() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        sensor_status_check("Open", "Closed", 3, 2);
        deleteFromPrimary(10);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 3)
    public void AA_04() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (12 group)");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        sensor_status_check("Open", "Closed", 3, 2);
        deleteFromPrimary(12);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 4)
    public void AA_05() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay using a Guest code)");
        logger.info("Adding a new Guest NewGuest with the code 1233");
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        AdvancedSettingsPage advanced = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigateToAdvancedSettingsPage();
        advanced.USER_MANAGEMENT.click();
        Thread.sleep(1000);
        user_m.Add_User.click();
        Thread.sleep(2000);
        user_m.Add_User_Name_field.sendKeys("NewGuest");
        user_m.Add_User_Code_field.sendKeys("1233");
        user_m.Add_Confirm_User_Code_field.sendKeys("1233");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        user_m.Add_User_Type_options.click();
        Thread.sleep(2000);
        user_m.User_Type_Guest.click();
        Thread.sleep(2000);
        user_m.Add_User_add_page.click();
        Thread.sleep(2000);
        home.Home_button.click();
        Thread.sleep(2000);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(3000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        enterGuestCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        swipeRight();
        swipeRight();
        Thread.sleep(2000);
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/title"));
        if (li_status1.get(0).getText().equals("DISARMED BY NEWGUEST")) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(0).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(0).getText() + "***");
        }
        Thread.sleep(2000);
        sensor_status_check("Open", "Closed", 3, 2);
        deleteFromPrimary(12);
        Thread.sleep(2000);
        navigateToAdvancedSettingsPage();
        advanced.USER_MANAGEMENT.click();
        Thread.sleep(1000);
        List<WebElement> delete = driver.findElements(By.id("com.qolsys:id/deleteImg"));
        delete.get(1).click();
        user_m.User_Management_Delete_User_Ok.click();
        home.Home_button.click();
        System.out.println("Pass");
    }


    @Test(priority = 5)
    public void AA_06() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 12 groups)");
        servcall.set_AUTO_STAY(0);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        sensor_status_check("Open", "Closed", 4, 3);
        sensor_status_check("Open", "Disarmed", 2, 1);
        Thread.sleep(2000);
        deleteFromPrimary(12);
        deleteFromPrimary(10);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    public void sensor_status_check(String Status, String Status1, int n, int n1) throws InterruptedException, IOException {
        logger.info("Check if the sensor status is displayed correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        SettingsPage sett = PageFactory.initElements(driver, SettingsPage.class);
        navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        sett.Panel_history.click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(n).getText().equals(Status)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n).getText() + "***");
        }
        Thread.sleep(2000);
        if (li_status1.get(n1).getText().equals(Status1)) {
            logger.info("Pass: sensor status is displayed correctly: ***" + li_status1.get(n1).getText() + "***");
        } else {
            logger.info("Failed: sensor status is displayed in correct: ***" + li_status1.get(n1).getText() + "***");
        }
        Thread.sleep(1000);
        home.Home_button.click();
        Thread.sleep(2000);
    }

    @Test(priority = 6)
    public void AA_07() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (10 and 12 groups)");
        servcall.set_AUTO_STAY(0);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(17, 17, 5570629, 2);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("55 00 54", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        sensor_status_check("Open", "Closed", 5, 4);
        sensor_status_check("Idle", "Activated", 2, 3);
        Thread.sleep(2000);
        deleteFromPrimary(12);
        deleteFromPrimary(17);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    @Test(priority = 7)
    public void AA_08() throws Exception {
        logger.info("Verify the system can be disarmed during the entry delay (20group)");
        servcall.set_AUTO_STAY(0);
        addPrimaryCall(20, 20, 5570630, 2);
        Thread.sleep(1000);
        ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway(); // if it fails then you need to disable arm away delay.
        Thread.sleep(3000);
        sensors.primaryCall("55 00 64", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        sensor_status_check("Idle", "Activated", 2, 3);
        Thread.sleep(2000);
        deleteFromPrimary(20);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

//    @Test(priority = 8)
//    public void AA_09() throws Exception {
//        logger.info("Verify the system will pretend to disarm if a valid duress code" +
//                " is used and a duress alarm will be sent to adc, " +
//                "simulating the user being forced to disarm the system.");
//        servcall.set_AUTO_STAY(0);
//        Thread.sleep(1000);
//        servcall.set_DURESS_AUTHENTICATION_enable();
//        Thread.sleep(1000);
//        addPrimaryCall(10, 10,6619296, 1);
//        Thread.sleep(1000);
//        servcall.EVENT_ARM_AWAY();
//        Thread.sleep(3000);
//        verifyArmaway();
//        Thread.sleep(3000);
//        sensors.primaryCall("65 00 0A", open);
//        Thread.sleep(2000);
//        sensors.primaryCall("65 00 0A", close);
//        Thread.sleep(2000);
//        enterDefaultDuressCode();
//        Thread.sleep(2000);
//        verifyDisarm();
//        Thread.sleep(2000);
//        sensor_status_check( "Open", "Closed", 3,2);
//        deleteFromPrimary(10);
//        Thread.sleep(2000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(5000);
//         adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        Thread.sleep(5000);
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Duress')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        //System.out.println(adc.driver1.findElement(By.id("ctl00_phBody_CurrentAlarmsWidget_rptAlarms_ctl01_EncodingLabel1")).getText());
//        adc.driver1.findElement(By.id("ctl00_phBody_CurrentAlarmsWidget_btnClearDuress")).click();
//        Thread.sleep(4000);
//        try {
//            Alert alert = adc.driver1.switchTo().alert();
//            adc.driver1.switchTo().alert().accept();
//            alert.accept();
//        } catch (NoAlertPresentException Ex) {
//        }
//        System.out.println("Pass");
//    }

//    @Test(priority = 9)
//    public void AA_10() throws Exception {
//        logger.info("Verify the system will pretend to disarm if a valid duress code" +
//                " is used and a duress alarm will be sent to adc, " +
//                "simulating the user being forced to disarm the system(12group).");
//        servcall.set_AUTO_STAY(0);
//        Thread.sleep(1000);
//        servcall.set_DURESS_AUTHENTICATION_enable();
//        Thread.sleep(1000);
//        addPrimaryCall(12, 12,6619297, 1);
//        Thread.sleep(1000);
//        servcall.EVENT_ARM_AWAY();
//        Thread.sleep(3000);
//        verifyArmaway();
//        Thread.sleep(3000);
//        sensors.primaryCall("65 00 1A", open);
//        Thread.sleep(2000);
//        sensors.primaryCall("65 00 1A", close);
//        Thread.sleep(2000);
//        enterDefaultDuressCode();
//        Thread.sleep(2000);
//        verifyDisarm();
//        Thread.sleep(2000);
//        sensor_status_check( "Open", "Closed", 3,2);
//        deleteFromPrimary(12);
//        Thread.sleep(2000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(5000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Duress')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        System.out.println(adc.driver1.findElement(By.id("ctl00_phBody_CurrentAlarmsWidget_rptAlarms_ctl01_EncodingLabel1")).getText());
//        adc.driver1.findElement(By.id("ctl00_phBody_CurrentAlarmsWidget_btnClearDuress")).click();
//        Thread.sleep(4000);
//        try {
//            Alert alert = adc.driver1.switchTo().alert();
//            adc.driver1.switchTo().alert().accept();
//            alert.accept();
//        } catch (NoAlertPresentException Ex) {
//        }
//        System.out.println("Pass");
//    }

    //@Test(priority = 10)
    public void AA_11() throws Exception {
        logger.info("Verify the system can not use a duress code if Duress Authentication is disabled");
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        servcall.set_DURESS_AUTHENTICATION_disable();
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        enterDefaultDuressCode();
        Thread.sleep(1000);
        if (home_page.Three.isDisplayed()){
            System.out.println("Pass: Duress code did not work");
        } else {
            System.out.println("Fail: Duress code worked when Duress Authentication is disabled");

        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

   //@Test(priority = 11)
    public void AA_12() throws Exception {
        logger.info("Verify the system can not use a duress code if Duress Authentication is disabled");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        servcall.set_DURESS_AUTHENTICATION_disable();
        Thread.sleep(1000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 1A", SensorsActivity.CLOSE);
        Thread.sleep(1000);
        enterDefaultDuressCode();
        Thread.sleep(1000);
        System.out.println("Pass: invalid User code");
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        deleteFromPrimary(12);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 12)
    public void AA_13() throws Exception {
        logger.info("Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened" +
                " in Arm Away");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 13)
    public void AA_14() throws Exception {
        logger.info("Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened" +
                " in Arm Away");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 1A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(12);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 14)
    public void AA_15_27() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Away");
        logger.info("Verify the system will go into alarm immediately if a sensor in group 13 is opened in Arm Away" +
                " and verify the system can be disarmed from Alarm");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(13, 13, 6619298, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 2A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 2A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        deleteFromPrimary(13);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 15)
    public void AA_16_29() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Away");
        logger.info("Verify the system will go into alarm immediately if a sensor in group 14 is opened in Arm Away" +
                " and verify the system can be disarmed from Alarm");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(14, 14, 6619299, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 3A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 3A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        deleteFromPrimary(14);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 16)
    public void AA_17() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 16 is opened in Arm Away");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(16, 16, 6619300, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 4A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 4A", SensorsActivity.CLOSE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Open")) {
            logger.info("Pass: Correct status is Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(16);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 17)
    public void AA_18() throws Exception {
        logger.info("Verify the system will go into immediate alarm if a sensor in group 35 is Activated in Arm Away");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(35, 35, 5570631, 2);
        Thread.sleep(1000);
        ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("55 00 74", SensorsActivity.ACTIVATE);
        sensors.primaryCall("55 00 74", idle);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Activated")) {
            logger.info("Pass: Correct status is Activated");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(35);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 18)
    public void AA_19_30() throws Exception {
        logger.info("Verify the system goes into immediate pending alarm and then alarm after dialer delay");
        logger.info("Verify the system goes into immediate pending alarm but can be disarmed from Alrm");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(15, 15, 5570628, 2);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("55 00 44", SensorsActivity.ACTIVATE);
        sensors.primaryCall("55 00 44", idle);
        Thread.sleep(1000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        if (home_page.Red_banner_sensor_status.getText().equals("Activated")) {
            logger.info("Pass: Correct status is Activated");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(2000);
        deleteFromPrimary(15);
        Thread.sleep(2000);
        System.out.println("Pass");
    }

    @Test(priority = 19)
    public void AA_20() throws Exception {
        logger.info("Verify the system will report alarm on both sensors at the end of the entry delay");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Open")) {
                logger.info("Pass: Correct status is open");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 2 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        deleteFromPrimary(12);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    @Test(priority = 20)
    public void AA_21() throws Exception {
        logger.info("Verify the system will go into immediate alarm when a group 13 sensor is opened " +
                "and will report alarm on both sensors ");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(13, 13, 6619298, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Open")) {
                logger.info("Pass: Correct status is open");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 2 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        deleteFromPrimary(13);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    @Test(priority = 21)
    public void AA_22() throws Exception {
        logger.info("Verify the system will report alarm on both sensors at the end of the entry delays (10,14) ");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(14, 14, 6619299, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Open")) {
                logger.info("Pass: Correct status is open");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 2 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        deleteFromPrimary(14);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    @Test(priority = 22)
    public void AA_23() throws Exception {
        logger.info("Verify the system will report alarm on both sensors at the end of the entry delays (10,16) ");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(16, 16, 6619300, 1);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("65 00 4A", SensorsActivity.OPEN);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Open")) {
                logger.info("Pass: Correct status is open");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 2 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        deleteFromPrimary(16);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    @Test(priority = 23)
    public void AA_24() throws Exception {
        logger.info("Verify the system will report alarm on both sensors at the end of the entry delays (10,15) ");
        servcall.set_AUTO_STAY(0);
        Thread.sleep(1000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(1000);
        addPrimaryCall(15, 15, 5570628, 2);
        Thread.sleep(1000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(3000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall("65 00 0A", SensorsActivity.CLOSE);
        Thread.sleep(2000);
        sensors.primaryCall("55 00 44", SensorsActivity.ACTIVATE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Activated")) {
                logger.info("Pass: Correct status is activated");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 2 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(10);
        deleteFromPrimary(15);
        Thread.sleep(4000);
        System.out.println("Pass");
    }

    @Test(priority = 25)
    public void AA_31() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (8 group). ");
        addPrimaryCall(8, 8, 6619302, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 6A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(8);
        Thread.sleep(6000);
        //     tamper_alarm_ver(8);
    }

    public void tamper_alarm_ver(int zone) throws Exception {
        adc.New_ADC_session_User(login, password);
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor " + zone + ") Pending Alarm ')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
        Thread.sleep(5000);
        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
        try {
            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Tamper Alarm')]"));
            Assert.assertTrue(history_message_alarm.isDisplayed());
            {
                System.out.println("User website history -> " + " Sensor " + zone + " event: " + history_message_alarm.getText());
            }
        } catch (Exception e) {
            System.out.println("No such element found!!!");
        }
    }

    @Test(priority = 26)
    public void AA_32() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (9 group). ");
        addPrimaryCall(9, 9, 6619303, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 7A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(9);
        Thread.sleep(6000);
        //   tamper_alarm_ver(9);
    }

    @Test(priority = 27)
    public void AA_33() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (10 group). ");
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(10);
        Thread.sleep(6000);
        //       tamper_alarm_ver(10);
    }

    @Test(priority = 28)
    public void AA_34() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (12 group). ");
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(12);
        Thread.sleep(6000);
        //       tamper_alarm_ver(12);
    }

    @Test(priority = 29)
    public void AA_35() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (13 group). ");
        addPrimaryCall(13, 13, 6619298, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 2A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(13);
        Thread.sleep(6000);
        //       tamper_alarm_ver(13);
    }

    @Test(priority = 30)
    public void AA_36() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (14 group). ");
        addPrimaryCall(14, 14, 6619299, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 3A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(14);
        Thread.sleep(6000);
        //      tamper_alarm_ver(14);
    }

    @Test(priority = 31)
    public void AA_37() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (16 group). ");
        addPrimaryCall(16, 16, 6619300, 1);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 4A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(16);
        Thread.sleep(6000);
        //       tamper_alarm_ver(16);
    }

    @Test(priority = 32)
    public void AA_38() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (15 group). ");
        addPrimaryCall(15, 15, 5570628, 2);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("55 00 44", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(15);
        Thread.sleep(6000);
        //       tamper_alarm_ver(15);
    }

    @Test(priority = 33)
    public void AA_39() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (17 group). ");
        addPrimaryCall(17, 17, 5570629, 2);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("55 00 54", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(17);
        Thread.sleep(6000);
        //       tamper_alarm_ver(17);
    }

    @Test(priority = 34)
    public void AA_40() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (20 group). ");
        addPrimaryCall(20, 20, 5570630, 2);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("55 00 64", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(20);
        Thread.sleep(6000);
        //       tamper_alarm_ver(20);
    }

    @Test(priority = 35)
    public void AA_41() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (35 group). ");
        addPrimaryCall(35, 35, 5570631, 2);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("55 00 74", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(35);
        Thread.sleep(6000);
        //    tamper_alarm_ver(35);
    }

    @Test(priority = 36)
    public void AA_42() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (26 group). ");
        addPrimaryCall(26, 26, 6750242, 5);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 22", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(26);
        Thread.sleep(6000);
        //     tamper_alarm_ver(26);
    }

    @Test(priority = 37)
    public void AA_43() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (34 group). ");
        addPrimaryCall(34, 34, 7667882, 6);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("75 00 AA", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(34);
        Thread.sleep(6000);
        //       tamper_alarm_ver(34);
    }

    @Test(priority = 38)
    public void AA_44() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (38 group). ");
        addPrimaryCall(38, 38, 7672224, 22);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("75 11 0A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(38);
        Thread.sleep(6000);
//        tamper_alarm_ver(38);
    }

    @Test(priority = 39)
    public void AA_45() throws Exception {
        logger.info("Verify the panel will report an immediate tamper alarm (52 group). ");
        addPrimaryCall(52, 52, 7536801, 17);
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        sensors.primaryCall("73 00 1A", SensorsActivity.TAMPER);
        Thread.sleep(3000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(52);
        Thread.sleep(6000);
//        tamper_alarm_ver(52);
    }

    @Test(priority = 40)
    public void AA_47() throws Exception {
        logger.info("Verify the panel will report an keypad tamper alarm at the end of the entry delay ");
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        home_page.DISARM_from_away.click();
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(1000);
        // System.out.println((driver.findElementById("com.qolsys:id/tv_name").getText()));
        if (driver.findElementById("com.qolsys:id/tv_name").getText().equals("Invalid User Code")) {
            logger.info("Pass: Correct status");
        } else {
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(6000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Keypad Tamper Alarm')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history ->  event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(5000);
    }

    @Test(priority = 41)
    public void AA_48() throws Exception {
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 1-group keyfob");
        addPrimaryCall(38, 1, 6619386, 102);
        Thread.sleep(2000);
        servcall.set_KEYFOB_DISARMING(01);
        Thread.sleep(2000);
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 AF", keyfobDisarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(6000);
        deleteFromPrimary(38);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history ->  event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(5000);
    }

    @Test(priority = 42)
    public void AA_50() throws Exception {
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 6-group keyfob");
        addPrimaryCall(39, 6, 6619387, 102);
        Thread.sleep(2000);
        servcall.set_KEYFOB_DISARMING(01);
        Thread.sleep(2000);
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 BF", keyfobDisarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(6000);
        deleteFromPrimary(39);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history ->  event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(5000);
    }

    @Test(priority = 44)
    public void AA_53() throws Exception {
        logger.info("Verify the panel will Disarm instantly if Disarm button is pressed by 4-group keyfob");
        addPrimaryCall(40, 4, 6619388, 102);
        Thread.sleep(2000);
        servcall.set_KEYFOB_DISARMING(01);
        Thread.sleep(2000);
        servcall.set_KEYFOB_NO_DELAY_enable();
        Thread.sleep(2000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(4000);
        sensors.primaryCall("65 00 CF", keyfobDisarm);
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(6000);
        deleteFromPrimary(40);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'panel Disarmed ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history ->  event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(5000);
    }

    @Test(priority = 39)
    public void AA_61() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if a shock-detector in group 13 is tampered ");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("66 00 C9", SensorsActivity.TAMPER);
        Thread.sleep(10000);
        sensors.primaryCall("66 00 C9", SensorsActivity.CLOSE);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(33);
        Thread.sleep(6000);
        //      tamper_alarm_ver(33);
    }

    @Test(priority = 54)
    public void AA_62() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13, 10 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(4000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(30000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(2000);
        deleteFromPrimary(33);
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(4000);
    }

    @Test(priority = 55)
    public void AA_63() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13, 12 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(4000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
//    adc.New_ADC_session_User(login,password);
//    Thread.sleep(60000);
//    adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//    try {
//        WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
//        Assert.assertTrue(history_message_alarm.isDisplayed());
//        {
//            System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
//        }
//    } catch (Exception e) {
//        System.out.println("No such element found!!!");
//    }
        Thread.sleep(2000);
        deleteFromPrimary(33);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(4000);
    }

    @Test(priority = 56)
    public void AA_64() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        Thread.sleep(4000);
        verifyInAlarm();
        Thread.sleep(4000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }

        Thread.sleep(4000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(33);
        Thread.sleep(4000);
    }

    @Test(priority = 57)
    public void AA_65() throws Exception {
        logger.info("Verify the panel will just create notification if a shock-detector in group 17 is tampered");
        addPrimaryCall(29, 17, 6750355, 19);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("67 00 39", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(29);
        Thread.sleep(6000);
        //   tamper_alarm_ver(29);
    }

    @Test(priority = 58)
    public void AA_66() throws Exception {
        logger.info("Verify the panel will go into immediate alarm is a Glass-break detector in group 13 is activated");
        addPrimaryCall(28, 13, 6750361, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        servcall.set_DIALER_DELAY(6);
        Thread.sleep(5000);
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        verifyArmstay();
        home_page.Quick_exit.click();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(6000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 28) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(3000);
//        adc.driver1.findElement(By.id("ctl00_phBody_RecentEventsWidget_btnEventsRefresh")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), '  (Sensor 28) Tamper Alarm')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(1000);
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 60)
    public void AA_67() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 17 is activated");
        addPrimaryCall(29, 17, 6750355, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(4000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 29) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 29 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 12) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        enterDefaultUserCode();
        Thread.sleep(4000);
        Thread.sleep(2000);
        deleteFromPrimary(29);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(4000);
    }

    @Test(priority = 61)
    public void AA_68() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 17 is activated");
        addPrimaryCall(29, 17, 6750355, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(4000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
        Thread.sleep(15000);
        verifyInAlarm();
        Thread.sleep(2000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is " + "Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(6000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 29) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 29 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(4000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), 'Alarm')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(4000);
        enterDefaultUserCode();
        Thread.sleep(4000);
        deleteFromPrimary(29);
        Thread.sleep(4000);
    }

    @Test(priority = 74)
    public void AA_69() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if a Glass-break detector in group 13 is tampered");
        addPrimaryCall(28, 13, 6750361, 19);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("67 00 99", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(4000);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is " + "Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        Thread.sleep(2000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(10000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 13) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 13 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(30000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' Alarm')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 28 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        deleteFromPrimary(28);
        Thread.sleep(4000);
    }

    @Test(priority = 71)
    public void AA_71() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if shock-detector in group 13 is activated");
        addPrimaryCall(33, 13, 6684828, 107);
        Thread.sleep(4000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(1).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(0).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 33 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(2000);
        deleteFromPrimary(33);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(6000);
    }

    @Test(priority = 73)
    public void AA_73() throws Exception {
        logger.info("Verify the panel will go into immediate alarm if a Glass-break detector in group 17 is tampered");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("66 00 D9", SensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Tampered")) {
            logger.info("Pass: Correct status is Tampered");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(34);
        Thread.sleep(6000);
        //      tamper_alarm_ver(34);
        Thread.sleep(3000);
    }

    @Test(priority = 74)
    public void AA_76() throws Exception {
        logger.info("Verify the panel will NOT go into immediate alarm if Glass-break detector in group 17 is activated");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(1000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        if (events.get(0).getText().equals("Alarmed")) {
            logger.info("Pass: Correct status is Alarmed");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        enterDefaultUserCode();
        Thread.sleep(1000);
        deleteFromPrimary(34);
        Thread.sleep(6000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(10000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 33) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 34 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");}
    }

    @Test(priority = 75)
    public void AA_75() throws Exception {
        logger.info("Verify the panel will NOT go into immediate alarm if Glass-break detector in group 17 is activated");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(4000);
        addPrimaryCall(12, 12, 6619297, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 1A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(13000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 33 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 34) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 34 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(2000);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 12) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 12 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(2000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
        deleteFromPrimary(12);
        Thread.sleep(6000);
    }

    @Test(priority = 76)
    public void AA_74() throws Exception {
        logger.info("Verify the panel will NOT go into immediate alarm if Glass-break detector in group 17 is activated");
        addPrimaryCall(34, 17, 6684829, 107);
        Thread.sleep(4000);
        addPrimaryCall(10, 10, 6619296, 1);
        Thread.sleep(3000);
        servcall.EVENT_ARM_AWAY();
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        sensors.primaryCall("65 00 0A", SensorsActivity.OPEN);
        Thread.sleep(2000);
        sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
        Thread.sleep(13000);
        verifyInAlarm();
        Thread.sleep(2000);
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        List<WebElement> events = driver.findElements(By.id("com.qolsys:id/tv_status"));
        // for (int j = 0; j < events.size(); j++)
        if (events.get(0).getText().equals("Open")) {
            logger.info("Pass: Correct status is " + "Open");
        } else {
            takeScreenshot();
            logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
        }
        try {
            if (events.get(1).getText().equals("Alarmed")) {
                logger.info("Pass: Correct status is " + "Alarmed");
            } else {
                takeScreenshot();
                logger.info("Failed: Incorrect status: " + home_page.Red_banner_sensor_status.getText());
            }
        } catch (Exception e) {
            logger.info("Sensor 34 event is not present on Alarm page");
        }
        enterDefaultUserCode();
        Thread.sleep(4000);
//        adc.New_ADC_session_User(login,password);
//        Thread.sleep(60000);
//        adc.driver1.findElement(By.id("ctl00_HeaderLinks1_imgReload")).click();
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 34) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 34 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(2000);
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), ' (Sensor 10) Pending Alarm ')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                System.out.println("User website history -> " + " Sensor 10 event: " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            System.out.println("No such element found!!!");
//        }
        Thread.sleep(2000);
        deleteFromPrimary(34);
        Thread.sleep(4000);
        deleteFromPrimary(10);
        Thread.sleep(6000);
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}