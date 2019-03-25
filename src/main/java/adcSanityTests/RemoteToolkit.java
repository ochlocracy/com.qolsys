package adcSanityTests;

import adc.ADC;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RemoteToolkit extends Setup {

    //full regression testing every setting option on ALARMADMIN Remote toolkit

    String page_name = "Remote Toolkit";
    Logger logger = Logger.getLogger(page_name);
    ADC adc = new ADC();
    String accountID = adc.getAccountId();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";

    public RemoteToolkit() throws Exception {
    }

    public void New_ADC_session(String accountID) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.manage().window().maximize();
        String ADC_URL = "https://alarmadmin.alarm.com/Support/CustomerInfo.aspx?customer_Id=" + accountID;
        adc.driver1.get(ADC_URL);
        String login = "qautomation";
        String password = "Qolsys123";
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
        adc.driver1.findElement(By.id("txtUsername")).sendKeys(login);
        adc.driver1.findElement(By.id("txtPassword")).sendKeys(password);
        adc.driver1.findElement(By.id("butLogin")).click();
        Thread.sleep(1000);
        adc.driver1.get("https://alarmadmin.alarm.com/Support/RemoteToolkit.aspx");
    }

    public void clickAnElementByLinkText(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ddlNewValue")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void DualPathSelectTextDropdown(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucDualPath_DropDownMode")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void ImageSensorRangeSelectTextDropdown(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucRange_ddlStatus")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void TurnOnTroubleBeepsDropdown(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucTurnOnOffTroubleBeeps_ddlTroubleBeeps")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        adc.webDriverSetUp();
    }

//    @BeforeMethod
//    public void webDriver() {
//        adc.webDriverSetUp();
//    }

    @Test
    public void GetToRemoteKitPage() throws java.lang.Exception {
        New_ADC_session(accountID);
        adc.driver1.manage().window().maximize();
        Thread.sleep(3000);
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 1)
    public void Remote_Advanced_Panel_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Log Level Test Begin");
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("No Log Output");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(2000);
        clickAnElementByLinkText("Error");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Fatal");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Warn");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Info");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Debug");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Advanced_Panel_Settings_Dropdown.click();
        remote.Log_Level.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Verbose");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Log Level Test finished");

        logger.info("*Remote_Advanced_Panel_Settings Test Suite finished*");

    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 2)
    public void Remote_Alarm_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Alarm settings Test on/off begin");
        remote.Alarm_Settings_Dropdown.click();
        remote.Alarm_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Alarm_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Alarm settings Test on/off finished");

        logger.info("Disarm Photos Test on/off begin");
        remote.Alarm_Settings_Dropdown.click();
        Thread.sleep(1000);
        remote.Disarm_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Disarm_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Disarm Photos Test on/off finished");

        logger.info("Fire Panic Test on/off begin");
        remote.Alarm_Settings_Dropdown.click();
        remote.Fire_Panic.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Fire_Panic.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Fire Panic Test on/off finished");

        logger.info("Fire Verification Test on/off begin");
        remote.Alarm_Settings_Dropdown.click();
        remote.Fire_Verification.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Fire_Verification.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Fire Verification Test on/off finished");

        logger.info("Police Panic Test on/off begin");
        remote.Alarm_Settings_Dropdown.click();
        remote.Police_Panic.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Police_Panic.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Police Panic Test on/off finished");

        logger.info("RF Jam Detection Alarm Test on/off begin");
        remote.Alarm_Settings_Dropdown.click();
        remote.RF_Jam_Detection_Alarm.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.RF_Jam_Detection_Alarm.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("RF Jam Detection Alarm Test on/off finished");

        logger.info("Siren Timeout Test 4-8 min begin");
        remote.Alarm_Settings_Dropdown.click();
        remote.Siren_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4 min");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Siren_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5 min");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Siren_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("6 min");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Siren_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("7 min");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Alarm_Settings_Dropdown.click();
        remote.Siren_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("8 min");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Siren Timeout Test 4-8 min finished");

        logger.info("*Remote_Alarm_Settings Test Suite finished*");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 3)
    public void Remote_Arming_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Dialer_Delay = "9";
        String Entry_Delay = "1";
        String Exit_Delay = "9";

        logger.info("Auto Stay Test on/off begin");
        remote.Arming_Setting_Dropdown.click();
        remote.Auto_Stay.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Arming_Setting_Dropdown.click();
        remote.Auto_Stay.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Auto Stay Test on/off finished");

        logger.info("Dialer_Delay Test begin");
        remote.Arming_Setting_Dropdown.click();
        remote.Dialer_Delay.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Dialer_Delay);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Dialer_Delay Test finished");

        logger.info("Entry_Delay Test begin");
        remote.Arming_Setting_Dropdown.click();
        remote.Entry_Delay.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Entry_Delay);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Entry_Delay Test finished");

        logger.info("Exit_Delay Test begin");
        remote.Arming_Setting_Dropdown.click();
        remote.Exit_Delay.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Exit_Delay);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Exit_Delay Test finished");

        logger.info("Refuse_Arming_When_Battery_Low Test on/off begin");
        remote.Arming_Setting_Dropdown.click();
        Thread.sleep(5000);
        remote.Refuse_Arming_When_Battery_Low.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Arming_Setting_Dropdown.click();
        Thread.sleep(5000);
        remote.Refuse_Arming_When_Battery_Low.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Refuse_Arming_When_Battery_Low Test on/off finished");

        logger.info("Secure_Arming Test on/off begin");
        Thread.sleep(1000);
        remote.Arming_Setting_Dropdown.click();
        Thread.sleep(6000);
        remote.Secure_Arming.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Arming_Setting_Dropdown.click();
        Thread.sleep(1000);
        remote.Secure_Arming.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Secure_Arming Test on/off finished");

        logger.info("Secure_Arming_Photos Test on/off begin");
        remote.Arming_Setting_Dropdown.click();
        Thread.sleep(1000);
        remote.Secure_Arming_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Arming_Setting_Dropdown.click();
        Thread.sleep(2000);
        remote.Secure_Arming_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Secure_Arming_Photos Test on/off finished");

        logger.info("Secure_Delete_Images Test on/off begin");
        remote.Arming_Setting_Dropdown.click();
        remote.Secure_Delete_Images.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Arming_Setting_Dropdown.click();
        remote.Secure_Delete_Images.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Secure_Delete_Images Test on/off finished");

        logger.info("*Remote_Arming_Settings Test Suite finished*");

    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 4)
    public void Remote_Beeps_and_Speaker_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Trouble_Beeps = "5";

        logger.info("All_Chimes Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.All_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.All_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("All_Chimes Test on/off finished");

        logger.info("All_Trouble_Beeps Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.All_Trouble_Beeps.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.All_Trouble_Beeps.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("All_Trouble_Beeps Test on/off finished");

        logger.info("All_Voice_Prompts Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.All_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.All_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("All_Voice_Prompts Test on/off finished");

        logger.info("Beeps_And_Chimes_Volume Test 0-7 lvl begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("0");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("1");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("2");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("3");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("6");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Beeps_And_Chimes_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("7");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Beeps_And_Chimes_Volume Test 0-7 lvl finished");

        logger.info("Media_Volume Test 0-7 lvl begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("0");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("1");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("2");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("3");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("6");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Media_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("7");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Media_Volume Test 0-7 lvl finished");

        logger.info("Panel_Chimes Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Panel_Chimes Test on/off finished");

        logger.info("Panel_Siren Test Enable/Disable begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Siren.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Siren.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Panel_Siren Test Enable/Disable finished");

        logger.info("Panel_Tamper_Trouble_Beep Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Tamper_Trouble_Beep.click();
        Thread.sleep(2000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Tamper_Trouble_Beep.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Panel_Tamper_Trouble_Beep Test on/off finished");

        logger.info("Panel_Voice_Prompts Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Panel_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Panel_Voice_Prompts Test on/off finished");

        logger.info("Safety_Sensor_Chimes Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Safety_Sensor_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Safety_Sensor_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Safety_Sensor_Chimes Test on/off finished");

        logger.info("Safety_Sensor_Voice_Prompts Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Safety_Sensor_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Safety_Sensor_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Safety_Sensor_Voice_Prompts Test on/off finished");

        logger.info("Sensor_Chimes Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Chimes.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Sensor_Chimes Test on/off finished");

        logger.info("Sensor_Low_Battery_Trouble_Beep Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Low_Battery_Trouble_Beep.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Low_Battery_Trouble_Beep.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Sensor_Low_Battery_Trouble_Beep Test on/off finished");

        logger.info("Sensor_Tamper_Trouble_Beep Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Tamper_Trouble_Beep.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Tamper_Trouble_Beep.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Sensor_Tamper_Trouble_Beep Test on/off finished");

        logger.info("Sensor_Voice_Prompts Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Sensor_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Sensor_Voice_Prompts Test on/off finished");

        logger.info("Severe_Weather_Siren_Warning Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Severe_Weather_Siren_Warning.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Severe_Weather_Siren_Warning Test on/off finished");

        logger.info("Siren_Annunciation Test Enable/Disable begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Siren_Annunciation.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Siren_Annunciation.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Siren_Annunciation Test Enable/Disable finished");

        logger.info("Touch_Sounds Test Enable/Disable begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Touch_Sounds.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Touch_Sounds.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Touch_Sounds Test Enable/Disable finished");

        logger.info("Trouble_Beeps_Timeout Test interval begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Trouble_Beeps_Timeout.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Trouble_Beeps);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Trouble_Beeps_Timeout Test interval finished");

        logger.info("Turn_On_Off_Trouble_Beeps Test on/off begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Turn_On_Off_Trouble_Beeps.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucTurnOnOffTroubleBeeps_ddlTroubleBeeps"))).click();
        Thread.sleep(1000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ctl00_responsiveBody_ucCommands_ucTurnOnOffTroubleBeeps_ddlTroubleBeeps']/option[1]"))).click();
        TurnOnTroubleBeepsDropdown("On");
        Thread.sleep(1000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucTurnOnOffTroubleBeeps_btnSendCommand"))).click();
        remote.Trouble_Beeps_Send_Command.click();
        Thread.sleep(6000);
        logger.info("Turn_On_Off_Trouble_Beeps Test on/off finished");

        logger.info("Voices_Volume Test 0-15 lvl begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("0");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("1");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("2");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("3");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("6");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("7");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("8");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("9");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("10");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("11");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("12");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("13");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("14");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Voices_Volume.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("15");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Voices_Volume Test 0-15 lvl finished");

        logger.info("Water_And_Freeze_Siren Test Enable/Disable begin");
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Water_And_Freeze_Siren.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Beeps_And_Speakers_Dropdown.click();
        remote.Water_And_Freeze_Siren.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Water_And_Freeze_Siren Test Enable/Disable finished");

        logger.info("*Remote_Beeps_and_Speaker_Settings Test Suite finished*");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 5)
    public void Remote_Broadband_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Set_Wifi_Network_Name = "The_Sandbox";

        logger.info("Bluetooth_Disarming_Feature Test Enable/Disable begin");
        remote.Broadband_Settings_Dropdown.click();
        remote.Bluetooth_Disarming_Feature.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Broadband_Settings_Dropdown.click();
        remote.Bluetooth_Disarming_Feature.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Bluetooth_Disarming_Feature Test Enable/Disable finish");

        logger.info("Wi-Fi Test on/off begin");
        remote.Broadband_Settings_Dropdown.click();
        remote.Wi_Fi.click();
        Thread.sleep(2000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Broadband_Settings_Dropdown.click();
        remote.Wi_Fi.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Wi-Fi Test on/off finish");

        logger.info("Wi-Fi Test name change begin");
        remote.Broadband_Settings_Dropdown.click();
        remote.Wi_Fi_Network_Name.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Set_Wifi_Network_Name);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Wi-Fi Test name change finish");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 6)
    public void Remote_Communication_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Dual_Path_Communication_settings Test DualPath/Cell begin");
        remote.Communication_Dropdown.click();
        remote.Dual_Path_Communication_settings.click();
        Thread.sleep(1000);
        DualPathSelectTextDropdown("Cell");
        remote.Dual_Path_Send_Command.click();
        Thread.sleep(2000);
        remote.Communication_Dropdown.click();
        remote.Dual_Path_Communication_settings.click();
        Thread.sleep(1000);
        DualPathSelectTextDropdown("Dual-Path");
        remote.Dual_Path_Send_Command.click();
        Thread.sleep(2000);
        logger.info("Dual_Path_Communication_settings Test DualPath/Cell finish");

        remote.Communication_Dropdown.click();
        remote.Get_IP_Address.click();
        Thread.sleep(1000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucGetIPAddress_btnGetLocalIP"))).click();
        Thread.sleep(3000);

        remote.Communication_Dropdown.click();
        remote.Ping_Dual_Path_System.click();
        Thread.sleep(1000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucDualPing_btnSendCommand"))).click();
        Thread.sleep(2000);

        remote.Communication_Dropdown.click();
        remote.Ping_Module.click();
        Thread.sleep(1000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucPingModule_btnSendCommand"))).click();
        Thread.sleep(2000);

        remote.Communication_Dropdown.click();
        remote.Request_Firmware_Version.click();
        Thread.sleep(1000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucRequestFirmware_btnSendCommand"))).click();
        Thread.sleep(2000);

        logger.info("*Communication_Settings Test Suite finish*");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 7)
    public void Remote_Date_And_Time_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Request_Panel_Time Test begin");
        remote.Date_and_Time_Dropdown.click();
        remote.Request_Panel_Time.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucRequestPanelTime_btnSendCommand"))).click();
        logger.info("Request_Panel_Time Test finished");

        logger.info("Set_Panel_Time Test begin");
        remote.Date_and_Time_Dropdown.click();
        remote.Set_Panel_Time.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucSetPanelTime_btnSendCommand"))).click();
        logger.info("Set_Panel_Time Test finish");

        logger.info("*Date_And_Time_Settings Test Suite finished*");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 8)
    public void Remote_Debug_And_Testing() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Allow software updates from Manage My Home begin");
        remote.Debug_And_Testing_Dropdown.click();
        remote.Allow_Software_Updates_From_Manage_My_Home.click();
        Thread.sleep(1000);
        remote.Change.click();
        logger.info("Allow software updates from Manage My Home finished");

        logger.info("Auto Upgrade settings begin");
        remote.Debug_And_Testing_Dropdown.click();
        remote.Auto_Upgrade_Settings.click();
        Thread.sleep(1000);
        remote.Change.click();
        logger.info("Auto Upgrade Settings finished");

        logger.info("Master User Add Sensor begin");
        remote.Debug_And_Testing_Dropdown.click();
        Thread.sleep(1000);
        remote.Master_User_Add_Sensors.click();
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ctl00_responsiveBody_ucCommands_ddlNewValue']/option[2]"))).click();
        remote.Change.click();
        logger.info("Master User Add Sensor finished");

        logger.info("Panel Tamper Enable/Disable begin");
        remote.Debug_And_Testing_Dropdown.click();
        remote.Panel_Tamper.click();
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='ctl00_responsiveBody_ucCommands_ddlNewValue']/option[2]"))).click();
        remote.Change.click();
        logger.info("Panel Tamper Enable/Disable finished");

        logger.info("Panel wifi Connectivity begin");
        remote.Debug_And_Testing_Dropdown.click();
        remote.Panel_WiFi_Connectivity_Warning_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        logger.info("Panel wifi Connectivity finished");

    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 9)
    public void Remote_General_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Automatic_Upgrade Test on/off begin");
        remote.General_Dropdown.click();
        remote.Automatic_Upgrade.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.General_Dropdown.click();
        remote.Automatic_Upgrade.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Automatic_Upgrade Test on/off finished");

        logger.info("Auxiliary_Panic Test on/off begin");
        remote.General_Dropdown.click();
        remote.Auxiliary_Panic.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.General_Dropdown.click();
        remote.Auxiliary_Panic.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Auxiliary_Panic Test on/off finished");

        logger.info("Resend_Panel_Location  Test begin");
        remote.General_Dropdown.click();
        remote.Backup_Panel_Now.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucBackupPanelNow_btnSendCommand"))).click();
        logger.info("Resend_Panel_Location Test finished");

        logger.info("Bluetooth Test Enable/Disable begin");
        remote.General_Dropdown.click();
        remote.Bluetooth.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.General_Dropdown.click();
        remote.Bluetooth.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Bluetooth Test Enable/Disable finished");

        logger.info("Bluetooth_Disarm_Timeout Test 1,5,10,20 lvl begin");
        remote.General_Dropdown.click();
        remote.Bluetooth_Disarm_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("1 minute");
        remote.Change.click();
        Thread.sleep(2000);
        remote.General_Dropdown.click();
        remote.Bluetooth_Disarm_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.General_Dropdown.click();
        remote.Bluetooth_Disarm_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("10 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.General_Dropdown.click();
        remote.Bluetooth_Disarm_Timeout.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("20 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Bluetooth_Disarm_Timeout Test 1,5,10,20 lvl finished");

        logger.info("Request_Updated_Equipment_List  Test begin");
        remote.General_Dropdown.click();
        remote.Request_Updated_Equipment_List.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucReqEqList_btnSendCommand"))).click();
        logger.info("Request_Updated_Equipment_List Test finish");

        logger.info("Resend_Panel_Location  Test begin");
        remote.General_Dropdown.click();
        remote.Resend_Panel_Location.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucPanelLocation_btnSendCommand"))).click();
        logger.info("Resend_Panel_Location Test finished");

        logger.info("*General_Settings Test Suite finished*");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 10)
    public void Remote_Image_Sensor_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Request_Latest_Image_Sensor_Info begin");
        remote.Image_Sensor_Dropdown.click();
        remote.Request_Latest_Image_Sensor_Info.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucRequestIsInfo_btnSendCommand"))).click();
        logger.info("Request_Latest_Image_Sensor_Info finished");

        logger.info("Verify_Daughterboard_Attachment  Test begin");
        remote.Image_Sensor_Dropdown.click();
        remote.Verify_Daughterboard_Attachment.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucVerifyDb_btnSendCommand"))).click();
        logger.info("Verify_Daughterboard_Attachment Test finish");

        logger.info("*Image Sensor settings Test Suite finish*");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 11)
    public void Remote_Keypad_And_Screen_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Automatically_Turn_Off_display = "22:00"; //(PM Military)
        String Automatically_Turn_On_display = "5:00"; //(AM Military)
        String Screen_Brightness = "254"; //(0-255)

        logger.info("Brightness Test time change begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Brightness.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Screen_Brightness);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Brightness Test time change finish");

        logger.info("Display_Type Test Weather Clock / Photo Frame begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Display_Type.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Photo Frame");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Display_Type.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Weather Clock");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Display_Type Test Weather Clock / Photo Frame finish");

        logger.info("Font_Size Test Small, Normal, Large begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Font_Size.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Small");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Font_Size.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Normal");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Font_Size.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Large");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Font_Size Test Small, Normal, Large finish");

        logger.info("Language Test begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Language.click();
        Thread.sleep(1000);
        remote.Change.click();
        logger.info("Language Test finished");

        logger.info("Photo Frame Duration Test 1,2,5 begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Duration.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("1 minute");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Duration.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("2 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Duration.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Photo_Frame_Duration Test 1,2,5 finish");

        logger.info("Photo_Frame_Shuffle Test on/off begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Shuffle.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Shuffle.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Photo_Frame_Shuffle Test on/off finish");

        logger.info("Photo_Frame_Start_Time Test 5/10/15/20/25/30 begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Start_Time.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Start_Time.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("10 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Start_Time.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("15 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Start_Time.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("20 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Start_Time.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("25 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Photo_Frame_Start_Time.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("30 minutes");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Photo_Frame_Start_Time Test 5/10/15/20/25/30 finish");

        logger.info("Setting_Photos Test Enable/Disable begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Setting_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Setting_Photos.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Setting_Photos Test Enable/Disable finish");

        logger.info("Setting_Photos Test Dissolve/Fade To Black begin");
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Transition_Effect.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Fade to Black");
        remote.Change.click();
        Thread.sleep(4000);
        remote.Keypad_And_Screen_Settings_Dropdown.click();
        remote.Transition_Effect.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Dissolve");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Transition_Effect Test Dissolve/Fade To Black finish");

        logger.info("Keypad_And_Screen_Settings Test Suite Finished");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 12)
    public void Remote_Panel_Information() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Power_Management Test on/off begin");
        remote.Panel_Information_Dropdown.click();
        remote.Power_Management.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Panel_Information_Dropdown.click();
        remote.Power_Management.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Power_Management Test on/off finish");

        logger.info("RF_Jam_Detection Test on/off begin");
        remote.Panel_Information_Dropdown.click();
        remote.RF_Jam_Detection.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Panel_Information_Dropdown.click();
        remote.RF_Jam_Detection.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("RF_Jam_Detection Test on/off finish");

        logger.info("Secondary_Panels Test Enable/Disable begin");
        remote.Panel_Information_Dropdown.click();
        remote.Secondary_Panels.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Panel_Information_Dropdown.click();
        remote.Secondary_Panels.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Secondary_Panels Test Enable/Disable finish");

        logger.info("Remote_Panel_Information Test suite finished");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 13)
    public void Remote_Sensors() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Request_Sensor_List Test begin");
        remote.Sensors_Dropdown.click();
        remote.Request_Sensor_Names.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucRequestSensorNames_btnSendCommand"))).click();

        Thread.sleep(2000);
        logger.info("Request_Sensor_List Test finish");

        logger.info("Update_System_And_Sensor_Status Test begin");
        remote.Sensors_Dropdown.click();
        remote.Update_System_And_Sensor_Status.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucSysSensorStatus_btnSendCommand"))).click();
        Thread.sleep(2000);
        logger.info("Update_System_And_Sensor_Status Test finish");

        logger.info("Remote_Sensors Test suite finished");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 14)
    public void Remote_Timers() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Long_Entry_Delay = "11";
        String Long_Exit_Delay = "10";

        logger.info("Arm_Stay_No_Delay Test on/off begin");
        remote.Timers_Dropdown.click();
        remote.Arm_Stay_No_Delay.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Timers_Dropdown.click();
        remote.Arm_Stay_No_Delay.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Arm_Stay_No_Delay Test on/off finish");

        logger.info("Auto_Bypass Test on/off begin");
        remote.Timers_Dropdown.click();
        remote.Auto_Bypass.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Timers_Dropdown.click();
        remote.Auto_Bypass.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Auto_Bypass Test on/off finish");

        logger.info("Auto_Exit_Time_Extension Test on/off begin");
        remote.Timers_Dropdown.click();
        remote.Auto_Exit_Time_Extension.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Timers_Dropdown.click();
        remote.Auto_Exit_Time_Extension.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Auto_Exit_Time_Extension Test on/off finish");

        logger.info("Keyfob_No_Delay Test on/off begin");
        remote.Timers_Dropdown.click();
        remote.Keyfob_No_Delay.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Timers_Dropdown.click();
        remote.Keyfob_No_Delay.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Keyfob_No_Delay Test on/off finish");

        logger.info("Long_Entry_Delay_Toolkit Test time change begin");
        remote.Timers_Dropdown.click();
        remote.Long_Entry_Delay_Toolkit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Long_Entry_Delay);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Long_Entry_Delay_Toolkit Test time change finish");

        logger.info("Long_Exit_Delay_Toolkit Test time change begin");
        remote.Timers_Dropdown.click();
        remote.Long_Exit_Delay_Toolkit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Long_Exit_Delay);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Long_Exit_Delay_Toolkit Test time change finish");

        logger.info("SIA_Limits Test on/off begin");
        remote.Timers_Dropdown.click();
        remote.SIA_Limits.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Timers_Dropdown.click();
        remote.SIA_Limits.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("SIA_Limits Test on/off finish");

        logger.info("SIA_Power_Restoration Test on/off begin");
        remote.Timers_Dropdown.click();
        remote.SIA_Power_Restoration.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Timers_Dropdown.click();
        remote.SIA_Power_Restoration.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("SIA_Power_Restoration Test on/off finish");

        logger.info("Remote_Timers Test suite finished");

    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 15)
    public void Remote_Trouble_Condition_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        logger.info("Loss_Of_Supervisory_Signals_Emergency_only Test 4/12/24 begin");
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Loss_Of_Supervisory_Signals_Emergency_only.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4 hours");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Loss_Of_Supervisory_Signals_Emergency_only.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("12 hours");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Loss_Of_Supervisory_Signals_Emergency_only.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("24 hours");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Loss_Of_Supervisory_Signals_Emergency_only Test 4/12/24 finish");


        logger.info("Loss_Of_Supervisory_Signals_Non_Emergency_Sensors Test 4/12/24 begin");
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Loss_Of_Supervisory_Signals_Non_Emergency_Sensors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4 hours");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Loss_Of_Supervisory_Signals_Non_Emergency_Sensors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("12 hours");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Loss_Of_Supervisory_Signals_Non_Emergency_Sensors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("24 hours");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Loss_Of_Supervisory_Signals_Non_Emergency_Sensors Test 4/12/24 finish");


        logger.info("Panel_Communication_Test_Frequency Test Weekly/Monthly/Never begin");
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Panel_Communication_Test_Frequency.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Weekly");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Panel_Communication_Test_Frequency.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Monthly");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Trouble_Condition_Settings_Dropdown.click();
        remote.Panel_Communication_Test_Frequency.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Never");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Panel_Communication_Test_Frequency Test Weekly/Monthly/Never finish");

        logger.info("Remote_Trouble_Condition_Settings Test suite finished");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 16)
    public void Remote_User_Codes_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Dealer_Code = "2222";
        String Installer_Code = "1234";

        logger.info("Allow_Master_Code_to_Access_Image_Settings Test on/off begin");
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_to_Access_Image_Settings.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_to_Access_Image_Settings.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Allow_Master_Code_to_Access_Image_Settings Test on/off finish");

        logger.info("Allow_Master_Code_to_Access_Security_and_Arming Test on/off begin");
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_to_Access_Security_and_Arming.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_to_Access_Security_and_Arming.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Allow_Master_Code_to_Access_Security_and_Arming Test on/off finish");

        logger.info("Allow_Master_Code_to_Access_Siren_and_Alarms Test on/off begin");
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_to_Access_Siren_and_Alarms.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_to_Access_Siren_and_Alarms.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Allow_Master_Code_to_Access_Siren_and_Alarms Test on/off finish");

        logger.info("Allow_Master_Code_ZWave_Management Test on/off begin");
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_ZWave_Management.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_ZWave_Management.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Allow_Master_Code_ZWave_Management Test on/off finish");

        logger.info("Allow_Master_Code_ZWave_Settings Test on/off begin");
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_ZWave_Settings.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.User_Codes_Dropdown.click();
        remote.Allow_Master_Code_ZWave_Settings.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Allow_Master_Code_ZWave_Settings Test on/off finish");

        logger.info("Dealer_Code Change Test numeral change begin");
        remote.User_Codes_Dropdown.click();
        remote.Dealer_Code.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Dealer_Code);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Dealer_Code Change Test numeral change finish");

        logger.info("Duress_Authentication Test on/off begin");
        remote.User_Codes_Dropdown.click();
        remote.Duress_Authentication.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.User_Codes_Dropdown.click();
        remote.Duress_Authentication.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Duress_Authentication Test on/off finish");

        logger.info("Installer_Code Change Test numeral change begin");
        remote.User_Codes_Dropdown.click();
        remote.Installer_Code.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Installer_Code);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Installer_Code Change Test numeral change finish");

        logger.info("Remote_User_Codes_Settings Test suite finished");
    }

    @Test(dependsOnMethods = {"GetToRemoteKitPage"}, priority = 17)
    public void Remote_Z_Wave_Settings() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String Door_Lock_Limit = "5";
        String Light_Limit = "5";
        String Other_Z_Wave_Device_Limit = "21";
        String Smart_Socket_Limit = "15";
        String Thermostat_Limit = "6";

        logger.info("Door_Lock_Limit Change Test numeral change begin");
        remote.Z_Wave_Dropdown.click();
        remote.Door_Lock_Limit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Door_Lock_Limit);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Door_Lock_Limit Change Test numeral change finish");

        logger.info("Garage_Doors Test 0-6 begin");
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("0");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("1");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("2");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("3");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("4");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("5");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Garage_Doors.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("6");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Garage_Doors Test 0-6 finish");

//        logger.info("Get_Equipment_List Test begin");
//        remote.Z_Wave_Dropdown.click();
//        Thread.sleep(1000);
//        remote.Get_Equipment_List.click();
//        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ucUpdateZWaveList_btnSendCommand"))).click();
//
//        Thread.sleep(2000);
//        logger.info("Get_Equipment_List Test finish");

        logger.info("Light_Limit Change Test numeral change begin");
        remote.Z_Wave_Dropdown.click();
        remote.Light_Limit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Light_Limit);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Light_Limit Change Test numeral change finish");

        logger.info("Local_Z_Wave_Voice_Prompts Test Enable/Disable begin");
        remote.Z_Wave_Dropdown.click();
        remote.Local_Z_Wave_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Local_Z_Wave_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Local_Z_Wave_Voice_Prompts Test Enable/Disable finish");

        logger.info("Other_Z_Wave_Device_Limit Change Test numeral change begin");
        remote.Z_Wave_Dropdown.click();
        remote.Other_Z_Wave_Device_Limit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Other_Z_Wave_Device_Limit);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Other_Z_Wave_Device_Limit Change Test numeral change finish");

        logger.info("Remote_Z_Wave_Voice_Prompts Test Enable/Disable begin");
        remote.Z_Wave_Dropdown.click();
        remote.Remote_Z_Wave_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Disable");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Remote_Z_Wave_Voice_Prompts.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Enable");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Remote_Z_Wave_Voice_Prompts Test Enable/Disable finish");

        logger.info("Smart_Socket_Limit Change Test numeral change begin");
        remote.Z_Wave_Dropdown.click();
        remote.Smart_Socket_Limit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Smart_Socket_Limit);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Smart_Socket_Limit Change Test numeral change finish");

        logger.info("Temperature Display Test Enable/Disable begin");
        remote.Z_Wave_Dropdown.click();
        remote.Temperature.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Celsius");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Temperature.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Fahrenheit");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Temperature Display Test Enable/Disable finish");

        logger.info("Smart_Socket_Limit Change Test numeral change begin");
        remote.Z_Wave_Dropdown.click();
        remote.Thermostat_Limit.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_txtNewValue"))).clear();
        remote.Txt_New_Value.sendKeys(Thermostat_Limit);
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Thermostat_Limit Change Test numeral change finish");

        logger.info("Z_Wave signal Test on/off begin");
        remote.Z_Wave_Dropdown.click();
        remote.Z_Wave.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("Off");
        remote.Change.click();
        Thread.sleep(2000);
        remote.Z_Wave_Dropdown.click();
        remote.Z_Wave.click();
        Thread.sleep(1000);
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);
        logger.info("Z_Wave signal Test on/off finish");

        logger.info("*Remote_Z_Wave_Settings signal Test suite finished*");
    }


    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        adc.driver1.quit();
    }
}
