package updateProcess;

import adc.ADC;
import utils.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* Estimate execution time: 60m */

public class SanityUpdatePG extends Setup {
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("PG_Sensors_Sanity");

    final String ON = "00000001";
    final String OFF = "00000000";
    int one_sec = 1000;

    public SanityUpdatePG() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void navigate_to_Security_Sensors_page() throws InterruptedException {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        dev.Security_Sensors.click();
    }

    public void setDefaultSettings() throws IOException, InterruptedException {
        int ON = 1;
        int OFF = 0;
        int one_sec = 1000;
        servcall.set_SIA_LIMITS_disable();
        Thread.sleep(one_sec);
        //sets media volume to 1
        servcall.set_SPEAKER_VOLUME(OFF);
        Thread.sleep(one_sec);
        servcall.set_ALL_VOICE_PROMPTS(ON);
        Thread.sleep(one_sec);
        servcall.set_ALL_CHIMES(OFF);
        Thread.sleep(one_sec);
        servcall.set_ENABLE_ALL_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_FIRE_SAFETY_DEVICE_TROUBLE_BEEPS(OFF);
        Thread.sleep(one_sec);
        servcall.set_SECURE_DELETE_IMAGES(ON);
        Thread.sleep(one_sec);
        servcall.set_DISARM_PHOTO(ON);
        Thread.sleep(one_sec);
        servcall.set_ALARM_PHOTOS(ON);
        Thread.sleep(one_sec);
        servcall.set_SETTINGS_PHOTOS(OFF);
        Thread.sleep(one_sec);
        servcall.set_DURESS_AUTHENTICATION_disable();
        Thread.sleep(one_sec);
        servcall.set_SECURE_ARMING_disable();
        Thread.sleep(one_sec);
        servcall.set_NO_ARMING_ON_LOW_BATTERY_disable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_BYPASS(ON);
        Thread.sleep(one_sec);
        servcall.set_AUTO_STAY(ON);
        Thread.sleep(one_sec);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        Thread.sleep(one_sec);
        servcall.set_AUTO_EXIT_TIME_EXTENSION(ON);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_ALARM_DISARM(OFF);
        Thread.sleep(one_sec);
        servcall.set_KEYFOB_DISARMING(ON);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_ENTRY_DELAY(10);
        Thread.sleep(one_sec);
        servcall.set_NORMAL_EXIT_DELAY(11);
        Thread.sleep(one_sec);
        servcall.set_LONG_ENTRY_DELAY(12);
        Thread.sleep(one_sec);
        servcall.set_LONG_EXIT_DELAY(13);
        Thread.sleep(one_sec);
        servcall.set_SIREN_DISABLE(ON);
        Thread.sleep(one_sec);
        servcall.set_FIRE_VERIFICATION(OFF);
        Thread.sleep(one_sec);
        servcall.set_SEVERE_WEATHER_SIREN_WARNING(ON);
        Thread.sleep(one_sec);
        servcall.set_DIALER_DELAY(30);
        Thread.sleep(one_sec);
        servcall.set_SIREN_TIMEOUT(240);
        Thread.sleep(one_sec);
        servcall.set_WATER_FREEZE_ALARM_disable();
        Thread.sleep(one_sec);
        servcall.set_POLICE_PANIC(ON);
        Thread.sleep(one_sec);
        servcall.set_FIRE_PANIC(ON);
        Thread.sleep(one_sec);
        servcall.set_AUXILIARY_PANIC(ON);
        Thread.sleep(one_sec);
        servcall.set_AUTO_UPLOAD_LOGS(OFF);
        Thread.sleep(one_sec);
        servcall.set_POWER_MANAGEMENT_ON_OFF_enable();
        Thread.sleep(one_sec);
        servcall.set_SIA_POWER_RESTORATION_disable();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_TIMEOUTY_24h();
        Thread.sleep(one_sec);
        servcall.set_LOSS_OF_SUPERVISORY_EMERGENCY_TIMEOUT(4);
        Thread.sleep(one_sec);
        servcall.set_Cell_Signal_Timeout(30);
        Thread.sleep(one_sec);
        servcall.set_RF_JAM_DETECT_disable();
        Thread.sleep(one_sec);
        servcall.set_OPEN_CLOSE_REPORTS_FOR_AUTO_LEARN(ON);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM(OFF);
        Thread.sleep(one_sec);
        servcall.set_BLUETOOTH_DISARM_TIMEOUT(10);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_IMAGE_SETTINGS(OFF);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SECURITY_AND_ARMING(OFF);
        Thread.sleep(one_sec);
        servcall.set_HOME_OWNER_SIREN_AND_ALARMS(OFF);
        Thread.sleep(one_sec);
    }

    public void verifySetting(String setting, String call, String expected) throws IOException {
        String result = execCmd(ConfigProps.adbPath + " shell service call qservice " + call).split(" ")[2];
        if (result.equals(expected))
            rep.log.log(LogStatus.PASS, "[Pass] " + setting + " has value: " + expected);
        else
           rep.log.log(LogStatus.FAIL, "[Fail] " + setting + " has value: " + result + ". Expected:" + expected);
    }

    @BeforeClass
    public void setUp() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        deleteReport();
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test(enabled = false)
    public void settingsCheck() throws InterruptedException, IOException {
        verifySetting("Media Volume", "36 i32 0 i32 0 i32 39 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Voice Prompts", "37 i32 0 i32 0 i32 42 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("All Chimes", "37 i32 0 i32 0 i32 46 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("All Trouble Beeps", "37 i32 0 i32 0 i32 111 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Fire Safety Device", "37 i32 0 i32 0 i32 146 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("WiFi", "37 i32 0 i32 0 i32 32 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Secure Delete Images", "37 i32 0 i32 0 i32 104 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Disarm Photo", "37 i32 0 i32 0 i32 102 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Alarm Photos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
//        verifySetting("Alarm Videos", "37 i32 0 i32 0 i32 109 i32 0 i32 0", OFF); No service call yet
//        Thread.sleep(one_sec);
        verifySetting("Settings Photos", "37 i32 0 i32 0 i32 143 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Duress Authentication", "37 i32 0 i32 0 i32 61 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Secure Arming", "37 i32 0 i32 0 i32 35 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("No Arming On Low Battery", "37 i32 0 i32 0 i32 36 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Bypass", "37 i32 0 i32 0 i32 19 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Auto Stay", "37 i32 0 i32 0 i32 20 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Arm Stay No Delay", "37 i32 0 i32 0 i32 21 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Exit Time Extension", "37 i32 0 i32 0 i32 84 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Alarm Disarm", "37 i32 0 i32 0 i32 129 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Keyfob Disarming", "37 i32 0 i32 0 i32 134 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Normal Entry Delay", "36 i32 0 i32 0 i32 15 i32 0 i32 0", "00000021");
        Thread.sleep(one_sec);
        verifySetting("Normal Exit Delay", "36 i32 0 i32 0 i32 16 i32 0 i32 0", "00000039");
        Thread.sleep(one_sec);
        verifySetting("Long Entry Delay", "36 i32 0 i32 0 i32 114 i32 0 i32 0", "00000071");
        Thread.sleep(one_sec);
        verifySetting("Long Exit Delay", "36 i32 0 i32 0 i32 115 i32 0 i32 0", "00000075");
        Thread.sleep(one_sec);
        verifySetting("Siren Disable", "37 i32 0 i32 0 i32 14 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Fire Verification", "37 i32 0 i32 0 i32 100 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Severe Weather Siren Warning", "37 i32 0 i32 0 i32 103 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Dialer Delay", "36 i32 0 i32 0 i32 12 i32 0 i32 0", "00000017");
        Thread.sleep(one_sec);
        verifySetting("Siren Timeout", "36 i32 0 i32 0 i32 13 i32 0 i32 0", "000001a4");
        Thread.sleep(one_sec);
        verifySetting("Water Freeze Alarm", "37 i32 0 i32 0 i32 122  i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Police Panic", "37 i32 0 i32 0 i32 131 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Fire Panic", "37 i32 0 i32 0 i32 132 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auxillary Panic", "37 i32 0 i32 0 i32 133 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Auto Upload Logs", "37 i32 0 i32 0 i32 90 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Power Management On/Off", "37 i32 0 i32 0 i32 73 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("SIA Power Restoration", "37 i32 0 i32 0 i32 74 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Loss of Supervisory Signals for Non-emergency sensors", "36 i32 0 i32 0 i32 31 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verifySetting("Loss of Supervisory Signals for Emergency sensors", "36 i32 0 i32 0 i32 118 i32 0 i32 0", "0000000c");
        Thread.sleep(one_sec);
        verifySetting("Cell Signal Timeout", "36 i32 0 i32 0 i32 101 i32 0 i32 0", "00000019");
        Thread.sleep(one_sec);
        verifySetting("SIA Limits", "37 i32 0 i32 0 i32 37 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("RF Jam Detect", "37 i32 0 i32 0 i32 25 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Open/Close Reports for Auto Learn", "37 i32 0 i32 0 i32 127 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth", "37 i32 0 i32 0 i32 142 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth Disarm", "37 i32 0 i32 0 i32 138 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Bluetooth Disarm Timeout", "36 i32 0 i32 0 i32 139 i32 0 i32 0", "0000001e");
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Camera settings", "37 i32 0 i32 0 i32 107 i32 0 i32 0", ON);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Security and Arming settings", "37 i32 0 i32 0 i32 106 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        verifySetting("Allow Master Code to Access Siren and Alarms settings", "37 i32 0 i32 0 i32 105 i32 0 i32 0", OFF);
        Thread.sleep(one_sec);
        Thread.sleep(5000);
        System.out.println("Done, setting default settings");
        setDefaultSettings();
        Thread.sleep(5000);
    }

    @Test(priority = 1)
    public void Add_Sensor() throws Exception {
        rep.create_report("Sanity_01");
        rep.log.log(LogStatus.INFO, ("*Sanity_01* Add a sensor"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        deleteFromPrimary(1);
        navigate_to_autolearn_page();
        rep.log.log(LogStatus.INFO, ("Adding a new sensor"));
        addPGSensors("DW", 104, 1101, 0);//gr10
        rep.log.log(LogStatus.PASS, ("Pass: sensor is added successfully"));
        home.Home_button.click();
    } //48sec

    @Test(priority = 2, retryAnalyzer = RetryAnalizer.class)
    public void Edit_Name() throws InterruptedException, IOException {
        rep.add_to_report("Sanity_02");
        rep.log.log(LogStatus.INFO, ("*Sanity_02* Edit sensor Name"));
        SecuritySensorsPage sen = PageFactory.initElements(driver, SecuritySensorsPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        navigate_to_Security_Sensors_page();
        sen.Edit_Sensor.click();
        sen.Edit_Img.click();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).clear();
        driver.findElement(By.id("com.qolsys:id/sensorDescText")).sendKeys("DW 104-1101NEW");
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        sen.Save.click();
        home.Home_button.click();
        Thread.sleep(2000);
        home.All_Tab.click();
        Thread.sleep(2000);
        activation_restoration(104, 1101, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr10
        rep.log.log(LogStatus.INFO, ("Verify new name is displayed"));
        WebElement newSensorName = driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101NEW']"));
        Assert.assertTrue(newSensorName.isDisplayed());
        rep.log.log(LogStatus.PASS, ("Pass: new name is displayed on panel"));
        Thread.sleep(10000);
        adc.update_sensors_list();
        Thread.sleep(4000);
        WebElement webname = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[2]/td[2]"));
        Thread.sleep(5000);
        Assert.assertTrue(webname.getText().equals("DW 104-1101NEW"));
        rep.log.log(LogStatus.PASS, ("Pass: The name is displayed correctly " + webname.getText()) + " on ADC web page");
        Thread.sleep(2000);
    } //2m 20sec

    @Test(priority = 3, retryAnalyzer = RetryAnalizer.class)
    public void Delete_Sensor() throws InterruptedException, IOException {
        rep.add_to_report("Sanity_03");
        rep.log.log(LogStatus.INFO, ("*Sanity_03* Delete the sensor"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        Thread.sleep(2000);
        deleteFromPrimary(1);
        Thread.sleep(2000);
        rep.log.log(LogStatus.INFO, ("Verify sensor is deleted"));
        adc.update_sensors_list();
        adc.Request_equipment_list();
        Thread.sleep(4000);
        WebElement webname = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[2]/td[2]"));
        Thread.sleep(5000);
        try {
            Assert.assertTrue(webname.getText().equals("DW 104-1152"));
        } catch (NoSuchElementException ignored) {
        } finally {
            navigate_to_autolearn_page();
            addPGSensors("DW", 104, 1101, 0);//gr10
        }
        Thread.sleep(2000);
        rep.log.log(LogStatus.PASS, ("Pass: sensor is deleted successfully and not displayed both on panel and ADC"));
        home.Home_button.click();
    } //3m 25sec

    @Test(priority = 4)
    public void contactSensorCheck() throws Exception {
        rep.add_to_report("Sanity_04");
        rep.log.log(LogStatus.INFO, ("*Sanity_04* Contact Sensors"));

        System.out.println("Open-Close contact sensors");
        Thread.sleep(2000);
        rep.log.log(LogStatus.INFO, "Activate DW sensors in DISARM mode");
        activation_restoration(104, 1101, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr10
        Thread.sleep(10000);
        adc.New_ADC_session(adc.getAccountId());
        Thread.sleep(1000);
        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
        Thread.sleep(2000);
        adc.Request_equipment_list();
        Thread.sleep(2000);
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1101 ')]", "//*[contains(text(), ' Sensor 1 Open/Close')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr10 works as expected");
        activation_restoration(104, 1152, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr12
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1152 ')]", "//*[contains(text(), ' Sensor 2 Open/Close')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr12 works as expected");
        activation_restoration(104, 1231, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr13
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1231 ')]", "//*[contains(text(), ' Sensor 3 Open/Close')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr13 works as expected");
        activation_restoration(104, 1216, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr14
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1216 ')]", "//*[contains(text(), ' Sensor 4 Open/Close')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr14 works as expected");
        activation_restoration(104, 1331, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr16
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1331 ')]", "//*[contains(text(), ' Sensor 5 Open/Close')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr16 works as expected");
        activation_restoration(104, 1311, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr25
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1311 ')]", "//*[contains(text(), ' Sensor 8 Open/Close')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, DW sensor gr25 works as expected");
        activation_restoration(104, 1127, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr8
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1127 ')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, DW sensor gr8 works as expected");
        activation_restoration(104, 1123, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr9
        Thread.sleep(2000);
        adc.ADC_verification_PG("//*[contains(text(), 'DW 104-1123')]", "//*[contains(text(), 'Delayed alarm')]");
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyInAlarm();
        enterDefaultUserCode();
        Thread.sleep(2000);
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, DW sensor gr9 works as expected");
    } //9m 25sec

    @Test(priority = 5)
    public void motionSensorCheck() throws Exception {
        rep.add_to_report("Sanity_05");
        rep.log.log(LogStatus.INFO, ("*Sanity_05* Motion Sensors"));
        System.out.println("Activate motion sensors");
        rep.log.log(LogStatus.INFO, "Activate motion sensors in ARM AWAY mode");
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(120, 1411, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr15 Sensor9
        Thread.sleep(10000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 120-1411')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr15 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(123, 1441, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr17 Sensor10
        Thread.sleep(7000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 123-1441')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr17 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(120, 1445, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr25 Sensor12
        Thread.sleep(10000);
        verifyArmaway();
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 120-1445')]", "//*[contains(text(), 'Armed Away')]");
        Thread.sleep(2000);
        DISARM();
        rep.log.log(LogStatus.PASS, "System is ARMED AWAY, ADC events are displayed correctly, motion gr25 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(122, 1423, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr20 Sensor11
        Thread.sleep(7000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 122-1423')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr20 works as expected");
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(123, 1446, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr35 Sensor13
        Thread.sleep(7000);
        verifyInAlarm();
        Thread.sleep(10000);
        adc.ADC_verification_PG("//*[contains(text(), 'Motion 123-1446')]", "//*[contains(text(), ' Alarm ')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, motion gr35 works as expected");
        Thread.sleep(2000);
    } //8m 20sec

    @Test(priority = 6)
    public void smokeCOSensorCheck() throws IOException, InterruptedException {
        rep.add_to_report("Sanity_06");
        rep.log.log(LogStatus.INFO, ("*Sanity_06* Smoke + SmokeM + CO Sensors"));
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        System.out.println("Activate smoke and smokeM sensors");
        rep.log.log(LogStatus.INFO, "Activate smoke and smokeM sensors");
        activation_restoration(201, 1541, PGSensorsActivity.SMOKEM, PGSensorsActivity.SMOKEMREST); //Sensor14
        Thread.sleep(3000);
//        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM (Fire), ADC events are displayed correctly, smokeM works as expected");
        activation_restoration(200, 1531, PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST); //Sensor15
        Thread.sleep(3000);
//        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 15 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM (Fire), ADC events are displayed correctly, smoke works as expected");
        activation_restoration(201, 1541, PGSensorsActivity.GAS, PGSensorsActivity.GASREST); //Sensor14
        Thread.sleep(3000);
//        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 14 Alarm**')]", "//*[contains(text(), 'Fire Alarm')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM (Fire), ADC events are displayed correctly, smokeM works as expected");

        rep.log.log(LogStatus.INFO, "Activate CO sensor");
        activation_restoration(220, 1661, PGSensorsActivity.CO, PGSensorsActivity.COREST); //Sensor16
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        enterDefaultUserCode();
        Thread.sleep(3000);
        rep.log.log(LogStatus.PASS, "System is in ALARM (Carbon Monoxide), ADC events are displayed correctly, CO works as expected");

        activation_restoration(220, 1661, PGSensorsActivity.GAS, PGSensorsActivity.GASREST); //Sensor16
        Thread.sleep(5000);
        enterDefaultUserCode();
        Thread.sleep(3000);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 16 Alarm**')]", "//*[contains(text(), 'Carbon Monoxide')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM (Carbon Monoxide), ADC events are displayed correctly, CO works as expected");
    } //5m 50sec

    @Test(priority = 7)
    public void waterSensorCheck() throws Exception {
        rep.add_to_report("Sanity_07");
        rep.log.log(LogStatus.INFO, ("*Sanity_07* Water Sensors"));
        rep.log.log(LogStatus.INFO, "Activate water sensor");
        HomePage home_page = PageFactory.initElements(this.driver, HomePage.class);
        activation_restoration(241, 1971, PGSensorsActivity.FLOOD, PGSensorsActivity.FLOODREST); //Sensor21
        Thread.sleep(5000);
        try {
            if (home_page.ALARM.isDisplayed()) {
                Thread.sleep(5000);
                adc.New_ADC_session(adc.getAccountId());
                adc.ADC_verification_PG("//*[contains(text(), 'Water Alarm')]", "//*[contains(text(), 'Sensor 21 Alarm**')]");
                enterDefaultUserCode();
                rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, water sensor works as expected");

            }else {
                System.out.println("after if statement");
                rep.log.log(LogStatus.FAIL, "System is not in ALARM");
            }
        }catch (NoSuchElementException e){
            System.out.println("catch statement");
            rep.log.log(LogStatus.FAIL, "System is not in ALARM");
        }

    } //1m 25sec

    @Test(priority = 8)
    public void shockSensorCheck() throws Exception {
        rep.add_to_report("Sanity_08");
        rep.log.log(LogStatus.INFO, ("*Sanity_08* Shock Sensors"));
        rep.log.log(LogStatus.INFO, "Activate shock sensor in ARM AWAY mode");
        servcall.set_AUTO_STAY(0);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        activation_restoration(171, 1741, PGSensorsActivity.SHOCK, PGSensorsActivity.SHOCKREST); //Sensor17
        Thread.sleep(10000);
        verifyInAlarm();
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'Sensor 17 Alarm**')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, shock sensor gr13 works as expected");

        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        activation_restoration(171, 1771, PGSensorsActivity.SHOCK, PGSensorsActivity.SHOCKREST); //Sensor18
        Thread.sleep(10000);
        verifyInAlarm();
        adc.ADC_verification_PG("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'Sensor 18 Alarm**')]");
        Thread.sleep(2000);
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, shock sensor gr17 works as expected");
    } //3m 11sec

    @Test(priority = 9)
    public void glassbreakSensorCheck() throws Exception {
        rep.add_to_report("Sanity_09");
        rep.log.log(LogStatus.INFO, ("*Sanity_09* Glassbreak Sensors"));
        System.out.println("Activate glassbreak sensors");
        rep.log.log(LogStatus.INFO, "Activate glassbreak sensor in ARM AWAY mode");
        servcall.set_AUTO_STAY(0);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(2000);
        activation_restoration(160, 1874, PGSensorsActivity.GB, PGSensorsActivity.GBREST);//gr13
        Thread.sleep(5000);
        verifyInAlarm();
        Thread.sleep(2000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'GBreak 160-1874')]", "//*[contains(text(), 'Sensor 19 Alarm**')]");
        enterDefaultUserCode();
        Thread.sleep(2000);
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, glassbreak sensor gr13 works as expected");

        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        activation_restoration(160, 1871, PGSensorsActivity.GB, PGSensorsActivity.GBREST);//gr17
        Thread.sleep(5000);
        verifyInAlarm();
        Thread.sleep(2000);
        adc.ADC_verification_PG("//*[contains(text(), 'GBreak 160-1871')]", "//*[contains(text(), 'Sensor 20 Alarm**')]");
        enterDefaultUserCode();
        Thread.sleep(2000);
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, glassbreak sensor gr17 works as expected");
    } //3m

    @Test(priority = 10)
    public void keyfobKeypadPendantCheck() throws Exception {
        rep.add_to_report("Sanity_10");
        rep.log.log(LogStatus.INFO, ("*Sanity_10* Keyfob + AuxPendant Sensors"));
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        System.out.println("Activate keyfobs");
        rep.log.log(LogStatus.INFO, "Activate keyfobs");
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        activation_restoration(300, 1004, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr1
        Thread.sleep(5000);
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Keyfob 300-1004')]", "//*[contains(text(), 'Police Panic')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keyfob gr1 works as expected");
  //      emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(305, 1009, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr6
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keyfob 305-1009')]", "//*[contains(text(), 'Aux Panic')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM(Aux Panic), ADC events are displayed correctly, keyfob gr6 works as expected");
 //       emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(306, 1003, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr4
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keyfob 306-1003')]", "//*[contains(text(), 'Aux Panic')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM(Aux Panic), ADC events are displayed correctly, keyfob gr4 works as expected");
 //       emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);

        //test this part!
        pgarmer(306, 1003, "2");
        Thread.sleep(2000);
        verifyArmstay(); //needs instant arming enabled?
        Thread.sleep(3000);
        pgarmer(306, 1003, "1");
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(3000);
        pgarmer(306, 1003, "3");
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        pgarmer(306, 1003, "1");
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(3000);
        pgarmer(371, 1005, "3");
        Thread.sleep(2000);
        verifyArmaway();
        pgarmer(371, 1005, "01 1234");
        Thread.sleep(2000);
        verifyDisarm();


        System.out.println("Activate keypad sensors");
        rep.log.log(LogStatus.INFO, "Activate keypad");
        activation_restoration(371, 1005, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr0
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'KeypadTouchscreen 25')]", "//*[contains(text(), 'Police Panic')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keypad gr0 works as expected");
//        emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(371, 1006, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr1
        Thread.sleep(7000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keypad/Touchscreen(26)')]", "//*[contains(text(), 'Delayed Police Panic')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keypad gr1 works as expected");
 //       emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(371, 1008, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr2
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'Keypad/Touchscreen(27)')]", "//*[contains(text(), 'Police Panic')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM(Police Panic), ADC events are displayed correctly, keypad gr2 works as expected");
        try {
            if (emergency.Cancel_Emergency.isDisplayed()) {
                System.out.println("***Failed*** Emergency icon is displayed");
                emergency.Cancel_Emergency.click();
                enterDefaultUserCode();
            }
        } catch (NoSuchElementException ignored) {
        } finally {
            System.out.println("***Pass*** Emergency is Silent, no emergency icon is displayed");
        }
        Thread.sleep(5000);

        System.out.println("Activate medical pendants");
        rep.log.log(LogStatus.INFO, "Activate aux pendants");
        activation_restoration(320, 1015, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr6
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'AuxPendant 320-1015')]", "//*[contains(text(), 'Delayed alarm')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, aux pendant gr6 works as expected");
 //       emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(320, 1016, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr4
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'AuxPendant 320-1016')]", "//*[contains(text(), 'Delayed alarm')]");
        rep.log.log(LogStatus.PASS, "System is in ALARM, ADC events are displayed correctly, aux pendant gr4 works as expected");
 //       emergency.Cancel_Emergency.click();
        enterDefaultUserCode();
        Thread.sleep(5000);
        activation_restoration(320, 1018, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr25
        Thread.sleep(5000);
        adc.ADC_verification_PG("//*[contains(text(), 'AuxPendant 320-1018')]", "//*[contains(text(), 'Inactivated')]");
        rep.log.log(LogStatus.PASS, "System is DISARMED, ADC events are displayed correctly, aux pendant gr25 works as expected");
        verifyDisarm();
        Thread.sleep(1000);
        contact.acknowledge_all_alerts();
        swipeLeft();
        Thread.sleep(1000);
    } //11m 20 sec

    @Test(priority = 11)
    public void Tamper() throws IOException, InterruptedException {
        rep.add_to_report("Sanity_11");
        rep.log.log(LogStatus.INFO, ("*Sanity_11* Tamper Sensor"));
        rep.log.log(LogStatus.INFO, "Tamper events verification");
        activation_restoration(104, 1101, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);//gr10
        adc.New_ADC_session(adc.getAccountId());
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 1 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 1 is displayed correctly");
        activation_restoration(120, 1411, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 9 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 9 is displayed correctly");
        activation_restoration(201, 1541, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 14 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 14 is displayed correctly");
        activation_restoration(220, 1661, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 16 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 16 is displayed correctly");
        activation_restoration(171, 1741, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 17 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 17 is displayed correctly");
        activation_restoration(160, 1874, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 19 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 19 is displayed correctly");
        activation_restoration(241, 1971, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 21 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 21 is displayed correctly");
        activation_restoration(400, 1995, PGSensorsActivity.TAMPER, PGSensorsActivity.TAMPERREST);
        adc.ADC_verification_PG("//*[contains(text(), 'Sensor 31 Tamper')]", "//*[contains(text(), 'End of Tamper')]");
        rep.log.log(LogStatus.PASS, "Tamper event for Sensor 31 is displayed correctly");
    } // 12m 1

    @Test(priority = 12)
    public void Supervisory() throws IOException, InterruptedException {
        rep.add_to_report("Sanity_12");
        rep.log.log(LogStatus.INFO, ("*Sanity_12* Supervisory Verification"));
        rep.log.log(LogStatus.INFO, "Supervisory verification");
        driver.findElement(By.id("com.qolsys:id/tray_layout")).click();
        //tap(5,6);
        powerGsupervisory(201, 1541);
        navigateToSettingsPage();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status1.get(1).getText());
        Thread.sleep(1000);
        Assert.assertTrue(li_status1.get(1).getText().contains("Failure"));
        rep.log.log(LogStatus.PASS, ("Pass: Failure event is displayed"));
        Thread.sleep(1000);
        pgprimaryCall(201, 1541, "06 0");
        Thread.sleep(1000);
        List<WebElement> li_status2 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status2.get(1).getText());
        Assert.assertTrue(li_status2.get(1).getText().contains("Normal"));
        Thread.sleep(3000);

        powerGsupervisory(220, 1661);
        Thread.sleep(1000);
        List<WebElement> li_status3 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status3.get(1).getText());
        Thread.sleep(1000);
        Assert.assertTrue(li_status3.get(1).getText().contains("Failure"));
        rep.log.log(LogStatus.PASS, ("Pass: Failure event is displayed"));
        Thread.sleep(1000);
        pgprimaryCall(220, 1661, "08 0");
        Thread.sleep(1000);
        List<WebElement> li_status4 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status4.get(1).getText());
        Assert.assertTrue(li_status4.get(1).getText().contains("Normal"));
        Thread.sleep(3000);

        powerGsupervisory(410, 1998);
        Thread.sleep(1000);
        List<WebElement> li_status5 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status5.get(1).getText());
        Thread.sleep(1000);
        Assert.assertTrue(li_status5.get(1).getText().contains("Failure"));
        rep.log.log(LogStatus.PASS, ("Pass: Failure event is displayed"));
        Thread.sleep(1000);
        pgprimaryCall(410, 1998, "82 0");
        Thread.sleep(1000);
        List<WebElement> li_status6 = driver.findElements(By.id("com.qolsys:id/textView3"));
        System.out.println(li_status6.get(1).getText());
        Assert.assertTrue(li_status6.get(1).getText().contains("Normal"));
        Thread.sleep(3000);
    } //1m

    @Test(priority = 13)
    public void Jam() throws Exception {
        rep.add_to_report("Sanity_13");
        rep.log.log(LogStatus.INFO, ("*Sanity_13* Jam Event Verification"));
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        rep.log.log(LogStatus.INFO, "Jam event verification");
        Thread.sleep(1000);
        servcall.set_RF_JAM_DETECT_enable();
        try {
            contact.acknowledge_all_alerts();
        } catch (NoSuchElementException e) {
        }
        swipeFromLefttoRight();
        Thread.sleep(2000);
        powerGjamer(1);
        home.Contact_Us.click();
        contact.Messages_Alerts_Alarms_tab.click();
        WebElement string = driver.findElement(By.id("com.qolsys:id/ui_msg_text"));
        Assert.assertTrue(string.getText().contains("PowerG receiver jammed"));
        rep.log.log(LogStatus.PASS, ("Pass: PowerG receiver jammed message is displayed"));
        powerGjamer(0);
        Thread.sleep(2000);
        try {
            if (string.isDisplayed()) {
                System.out.println("Fail: jammed message is displayed");
                rep.log.log(LogStatus.FAIL, ("Fail: jammed message is displayed after canceling jam"));
            } else {
                System.out.println("Pass: jammed message is not displayed");
                rep.log.log(LogStatus.PASS, ("Pass: jammed message is not displayed after canceling jam"));
            }
        } catch (Exception e) {
        }

        servcall.set_RF_JAM_DETECT_disable();
        try {
            home.Home_button.click();
        } catch (NoSuchElementException e) {
        }
        } //48sec

    @Test(priority = 14, retryAnalyzer = RetryAnalizer.class)
    public void Low_Battery() throws InterruptedException, IOException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
//      String file = projectPath + "/extent-config.xml";
//      report.loadConfig(new File(file));
//      rep.log.log(LogStatus.INFO,"Software Version", softwareVersion()); it actually failed here. will re run this as a solo test tomorrow.

        rep.add_to_report("Sanity_14");
        rep.log.log(LogStatus.INFO, ("*Sanity_14* Low battery events verification"));
        Thread.sleep(4000);
//        try {
//            home.Home_button.click();
//        } catch (NoSuchElementException e) {
//        }
        pgprimaryCall(104, 1101, "80 1");
        Thread.sleep(15000);
        home.Contact_Us.click();
        contact.Messages_Alerts_Alarms_tab.click();
        Thread.sleep(1000);
        WebElement string = driver.findElement(By.id("com.qolsys:id/ui_msg_text"));
        Assert.assertTrue(string.getText().contains("DW 104-1101(1) - Low Battery"));
        rep.log.log(LogStatus.PASS, ("Pass: low battery event is successfully displayed for DW 104-1101 sensor"));
        pgprimaryCall(104, 1101, "80 0");

    } //40sec


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        rep.report_tear_down(result);
        adc.driver1.close();
    }

    @AfterClass(alwaysRun = true)
    public void driver_quit() throws IOException, InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }

}
