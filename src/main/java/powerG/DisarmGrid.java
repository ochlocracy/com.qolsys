package powerG;

import adc.ADC;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.*;
import sensors.Sensors;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;
import utils.Setup1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DisarmGrid {
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    ADC adc = new ADC();
    Setup1 s = new Setup1();
    Setup setup = new Setup();
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public DisarmGrid() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void create_report(String test_area_name) throws InterruptedException {
        String file = setup.projectPath + "/extent-config.xml";
        report = new ExtentReports(setup.projectPath + "/Report/QTMS_PowerG_Disarm.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Anya Dyshleva");
        //       .addSystemInfo("Software Version", s.softwareVersion());
        log = report.startTest(test_area_name);
    }

    public void add_to_report(String test_case_name) {
        report = new ExtentReports(setup.projectPath + "/Report/QTMS_PowerG_Disarm.html", false);
        log = report.startTest(test_case_name);
    }

    public void ADC_verification(String account, String string, String string1) throws InterruptedException, IOException {
        adc.New_ADC_session(account);
        Thread.sleep(2000);
        adc.driver1.findElement(By.partialLinkText("History")).click();
        Thread.sleep(7000);
        String[] message = {string, string1};
        adc.driver1.navigate().refresh();
        Thread.sleep(7000);
        for (int i = 0; i < message.length; i++) {
            adc.driver1.navigate().refresh();
            try {
                WebElement history_message = adc.driver1.findElement(By.xpath(message[i]));
                Assert.assertTrue(history_message.isDisplayed());
                {
                    System.out.println("Pass: message is displayed " + history_message.getText());
                }
            } catch (Exception e) {
                System.out.println("***No such element found!***");
            } finally {
            }
            Thread.sleep(7000);
        }
    }
    public void navigate_to_Security_Sensors_page() throws InterruptedException {
        AdvancedSettingsPage adv = PageFactory.initElements(s.driver, AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(s.driver, DevicesPage.class);
        s.navigateToAdvancedSettingsPage();
        adv.INSTALLATION.click();
        inst.DEVICES.click();
        dev.Security_Sensors.click();
    }

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    public void sensor_status_check(String UDID_, int type, int id, String Status, String Status2, String State, String State1, int line) throws InterruptedException, IOException {
        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.pgprimaryCall(UDID_, type, id, Status);
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(line).getText().equals(State)) {
            log.log(LogStatus.PASS, "Pass: sensor status is displayed correctly: ***" + li_status1.get(line).getText() + "***");
        } else {
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed incorrect: ***" + li_status1.get(line).getText() + "***");
        }
        Thread.sleep(2000);
        li_status1.clear();
        s.pgprimaryCall(UDID_, type, id, Status2);
        Thread.sleep(1000);
        List<WebElement> li_status2 = s.driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status2.get(line).getText().equals(State1)) {
            log.log(LogStatus.PASS, ("Pass: sensor status is displayed correctly: ***" + li_status2.get(line).getText() + "***"));
        } else {
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed in correct: ***" + li_status2.get(line).getText() + "***");
        }
        Thread.sleep(1000);
        home.Home_button.click();
    }

    @Parameters({"UDID_"})
    @Test
    public void Dis_01_DW10(String UDID_) throws IOException, InterruptedException {
        create_report("Dis_01");
        log.log(LogStatus.INFO, ("*Dis_01* Open/Close event is displayed in panel history for sensor group 10"));
        Thread.sleep(1000);
        sensor_status_check(UDID_, 104, 1101, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE, "Open", "Closed", 1);
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 1)
    public void Dis_02_DW12(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_02");
        log.log(LogStatus.INFO, ("*Dis_02* Open/Close event is displayed in panel history for sensor group 12"));
        Thread.sleep(1000);
        sensor_status_check(UDID_, 104, 1152, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE, "Open", "Closed", 2);
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 2)
    public void Dis_03_DW13(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_03");
        log.log(LogStatus.INFO, ("*Dis_03* Open/Close event is displayed in panel history for sensor group 13"));
        Thread.sleep(1000);
        sensor_status_check( UDID_, 104, 1231, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE, "Open", "Closed", 3);
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 3)
    public void Dis_04_DW14(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_04");
        log.log(LogStatus.INFO, ("*Dis_04* Open/Close event is displayed in panel history for sensor group 14"));
        Thread.sleep(1000);
        sensor_status_check(UDID_,104, 1216, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE, "Open", "Closed", 4);
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 4)
    public void Dis_05_DW16(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_05");
        log.log(LogStatus.INFO, ("*Dis_05* Open/Close event is displayed in panel history for sensor group 16"));
        Thread.sleep(1000);
        sensor_status_check(UDID_, 104, 1331, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE, "Open", "Closed", 5);
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 5)
    public void Dis_06_M15(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_06");
        log.log(LogStatus.INFO, ("*Dis_06* Activate event is displayed in panel history for motion sensor group 15"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_,120, 1411, PGSensorsActivity.MOTIONACTIVE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        s.driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Activated"));
        log.log(LogStatus.PASS, ("Pass: Activated event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 6)
    public void Dis_07_M17(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_07");
        log.log(LogStatus.INFO, ("*Dis_07* Activate event is displayed in panel history for motion sensor group 17"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 123, 1441, PGSensorsActivity.MOTIONACTIVE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        s.driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Activated"));
        log.log(LogStatus.PASS, ("Pass: Activated event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 7)
    public void Dis_08_M20(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_08");
        log.log(LogStatus.INFO, ("*Dis_08* Activate event is displayed in panel history for motion sensor group 20"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 122, 1423, PGSensorsActivity.MOTIONACTIVE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        s.driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Activated"));
        log.log(LogStatus.PASS, ("Pass: Activated event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 8)
    public void Dis_09_M35(String UDID_) throws IOException, InterruptedException {
        add_to_report("Dis_09");
        log.log(LogStatus.INFO, ("*Dis_09* Activate event is displayed in panel history for motion sensor group 35"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 123, 1446, PGSensorsActivity.MOTIONACTIVE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        s.driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
        Thread.sleep(1000);
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Idle"));
        log.log(LogStatus.PASS, ("Pass: Idle event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Activated"));
        log.log(LogStatus.PASS, ("Pass: Activated event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "account"})
    @Test(priority = 9)
    public void Dis_14_DW10(String UDID_, String account) throws IOException, InterruptedException {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_14");
        log.log(LogStatus.INFO, ("*Dis_14* Verify the sensor is being monitored, dw sensor group 10"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_,104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(21000);
        s.pgprimaryCall(UDID_, 104, 1101, PGSensorsActivity.INCLOSE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Open"));
        log.log(LogStatus.PASS, ("Pass: Open event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();

        ADC_verification(account,"//*[contains(text(), 'DW 104-1101')]", "//*[contains(text(), 'Sensor 1 Open/Close')]");
        log.log(LogStatus.PASS, ("Pass: (Sensor 1) Opened/Closed and Sensor 1 Open/Close messages are displayed"));
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "account"})
    @Test(priority = 10)
    public void Dis_15_DW12(String UDID_, String account) throws IOException, InterruptedException {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_15");
        log.log(LogStatus.INFO, ("*Dis_15* Verify the sensor is being monitored, dw sensor group 12"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(21000);
        s.pgprimaryCall(UDID_, 104, 1152, PGSensorsActivity.INCLOSE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Open"));
        log.log(LogStatus.PASS, ("Pass: Open event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();
        Thread.sleep(2000);
        ADC_verification(account, "//*[contains(text(), 'DW 104-1152')]", "//*[contains(text(), 'Sensor 2 Open/Close')]");
        log.log(LogStatus.PASS, ("Pass: (Sensor 2) Opened/Closed and Sensor 2 Open/Close messages are displayed"));
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "account"})
    @Test(priority = 11)
    public void Dis_16_DW13(String UDID_, String account) throws IOException, InterruptedException {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_16");
        log.log(LogStatus.INFO, ("*Dis_16* Verify the sensor is being monitored, dw sensor group 13"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(21000);
        s.pgprimaryCall(UDID_, 104, 1231, PGSensorsActivity.INCLOSE);
        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Open"));
        log.log(LogStatus.PASS, ("Pass: Open event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();

        ADC_verification(account, "//*[contains(text(), 'DW 104-1231')]", "//*[contains(text(), 'Sensor 3 Open/Close')]");
        log.log(LogStatus.PASS, ("Pass: (Sensor 3) Opened/Closed and Sensor 3 Open/Close messages are displayed"));
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "account"})
    @Test(priority = 12)
    public void Dis_17_DW14(String UDID_, String account) throws IOException, InterruptedException {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_17");
        log.log(LogStatus.INFO, ("*Dis_17* Verify the sensor is being monitored, dw sensor group 14"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_,104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(21000);
        s.pgprimaryCall(UDID_, 104, 1216, PGSensorsActivity.INCLOSE);

        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Open"));
        log.log(LogStatus.PASS, ("Pass: Open event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();

        ADC_verification(account, "//*[contains(text(), 'DW 104-1216')]", "//*[contains(text(), 'Sensor 4 Open/Close')]");
        log.log(LogStatus.PASS, ("Pass: (Sensor 4) Opened/Closed and Sensor 4 Open/Close messages are displayed"));
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "account"})
    @Test(priority = 13)
    public void Dis_18_DW16(String UDID_, String account) throws IOException, InterruptedException {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_18");
        log.log(LogStatus.INFO, ("*Dis_18* Verify the sensor is being monitored, dw sensor group 16"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1331, PGSensorsActivity.INOPEN);
        Thread.sleep(21000);
        s.pgprimaryCall(UDID_, 104, 1331, PGSensorsActivity.INCLOSE);

        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Open"));
        log.log(LogStatus.PASS, ("Pass: Open event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();

        ADC_verification(account, "//*[contains(text(), 'DW 104-1331')]", "//*[contains(text(), 'Sensor 5 Open/Close')]");
        log.log(LogStatus.PASS, ("Pass: (Sensor 5) Opened/Closed and Sensor 5 Open/Close messages are displayed"));
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "account"})
    @Test(priority = 14)
    public void Dis_19_DW25(String UDID_, String account) throws IOException, InterruptedException {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_19");
        log.log(LogStatus.INFO, ("*Dis_19* Open/Close event is displayed in panel history for sensor group 25"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1311, PGSensorsActivity.INOPEN);
        Thread.sleep(21000);
        s.pgprimaryCall(UDID_, 104, 1311, PGSensorsActivity.INCLOSE);

        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        s.driver.findElement(By.id("com.qolsys:id/tab4")).click();
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));

        Assert.assertTrue(li_status1.get(1).getText().equals("Closed"));
        log.log(LogStatus.PASS, ("Pass: Closed event is displayed"));
        Assert.assertTrue(li_status1.get(2).getText().equals("Open"));
        log.log(LogStatus.PASS, ("Pass: Open event is displayed"));
        Thread.sleep(2000);
        li_status1.clear();

        ADC_verification(account, "//*[contains(text(), 'DW 104-1311')]", "//*[contains(text(), 'Sensor 8 Open/Close')]");
        log.log(LogStatus.PASS, ("Pass: (Sensor 8) Opened/Closed and Sensor 8 Open/Close messages are displayed"));
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 15)
    public void Dis_20_DW8(String UDID_) throws Exception {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
        add_to_report("Dis_20");
        log.log(LogStatus.INFO, ("*Dis_20* Open/Close event is displayed in panel history for sensor group 8"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1127, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        s.verifyInAlarm();
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1127, PGSensorsActivity.INCLOSE);
        log.log(LogStatus.PASS, "Pass: system is in ALARM");
        s.enterDefaultUserCode();

        s.navigateToSettingsPage();
        Thread.sleep(1000);
        sett.STATUS.click();
        Thread.sleep(2000);
        List<WebElement> li_status1 = s.driver.findElements(By.id("com.qolsys:id/textView3"));
        if (li_status1.get(1).getText().equals("Closed")) {
            log.log(LogStatus.PASS, "Pass: sensor status is displayed correctly: ***" + li_status1.get(1).getText() + "***");
        } else {
            log.log(LogStatus.FAIL, "Failed: sensor status is displayed in correct: ***" + li_status1.get(1).getText() + "***");
        }
        Thread.sleep(1000);
        home.Home_button.click();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", "User", "Password"})
    @Test(priority = 16)
    public void Dis_21_DW8(String UDID_, String User, String Password) throws Exception {
        add_to_report("Dis_21");
        log.log(LogStatus.INFO, ("*Dis_21* system will disarm from Police Alarm from the User Site, dw 8"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1127, PGSensorsActivity.INOPEN);
        Thread.sleep(20000);
        s.verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: system is in ALARM");
        adc.New_ADC_session_User(User, Password);
        Thread.sleep(5000);
        adc.driver1.get("https://www.alarm.com/web/system/alerts-issues");
        Thread.sleep(5000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stop Alarm']"))).click();
        Thread.sleep(4000);
        adc.driver1.findElement(By.xpath("(//*[text()='Stop Alarms'])[last()]")).click();
        Thread.sleep(10000);
        s.verifyDisarm();
        log.log(LogStatus.PASS, ("Pass: system is successfully Disarmed from user site"));
        Thread.sleep(1000);
        s.pgprimaryCall(UDID_, 104, 1127, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 17)
    public void Dis_22_DW9(String UDID_) throws Exception {
        SettingsPage sett = PageFactory.initElements(s.driver, SettingsPage.class);
        add_to_report("Dis_22");
        log.log(LogStatus.INFO, ("*Dis_22* Open/Close event is displayed in panel history for sensor group 9, system goes into alarm at the end of entry delay"));
        s.navigateToSettingsPage();
        sett.STATUS.click();
        s.pgprimaryCall(UDID_, 104, 1123, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
        Thread.sleep(2);
        s.pgprimaryCall(UDID_, 104, 1123, PGSensorsActivity.INCLOSE);
        s.verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: system is in ALARM");
        s.enterDefaultUserCode();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_"})
    @Test(priority = 18)
    public void Dis_23_DW8(String UDID_) throws Exception {
        add_to_report("Dis_23");
        log.log(LogStatus.INFO, ("*Dis_23* system goes into alarm after sensor tamper, dw8"));
        Thread.sleep(2000);
        s.pgprimaryCall(UDID_, 104, 1127, PGSensorsActivity.TAMPER);
        Thread.sleep(5000);
        s.pgprimaryCall(UDID_, 104, 1127, PGSensorsActivity.TAMPERREST);
        s.verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: system is in ALARM");
        s.enterDefaultUserCode();
        Thread.sleep(2000);
    }

    @Parameters({"UDID_", ""})
    @Test(priority = 19)
    public void Dis_24_DW9(String UDID_) throws Exception {
        add_to_report("Dis_24");
        log.log(LogStatus.INFO, ("*Disb_24* system goes into alarm after sensor tamper, dw9"));
        Thread.sleep(2000);
        s.pgprimaryCall(UDID_, 104, 1123, PGSensorsActivity.TAMPER);
        Thread.sleep(4000);
        s.pgprimaryCall(UDID_, 104, 1123, PGSensorsActivity.TAMPERREST);
        s.verifyInAlarm();
        log.log(LogStatus.PASS, "Pass: system is in ALARM");
        s.enterDefaultUserCode();
        Thread.sleep(2000);
    }

//    @Parameters({"UDID_"})
//    @Test(priority = 20)
//    public void Dis_25_DW10(String UDID_) throws Exception {
//        add_to_report("Dis_25");
//        SecuritySensorsPage sen = PageFactory.initElements(s.driver, SecuritySensorsPage.class);
//        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
//        log.log(LogStatus.INFO, ("*Dis_25* sensor name can be edited and changes will be reflected on the panel and website"));
//        navigate_to_Security_Sensors_page();
//        sen.Edit_Sensor.click();
//        sen.Edit_Img.click();
//        s.driver.findElement(By.id("com.qolsys:id/powergsensorDescText")).clear();
//        s.driver.findElement(By.id("com.qolsys:id/powergsensorDescText")).sendKeys("DW 104-1101NEW");
//        try {
//            s.driver.hideKeyboard();
//        } catch (Exception e) {
//        }
//        sen.Save.click();
//        home.Home_button.click();
//        Thread.sleep(2000);
//        home.All_Tab.click();
//        Thread.sleep(2000);
//        log.log(LogStatus.INFO, ("Verify new name is displayed"));
//        WebElement newSensorName = s.driver.findElement(By.xpath("//android.widget.TextView[@text='DW 104-1101NEW']"));
//        Assert.assertTrue(newSensorName.isDisplayed());
//        log.log(LogStatus.PASS, ("Pass: new name is displayed on panel"));
//        Thread.sleep(10000);
//        adc.update_sensors_list();
//        Thread.sleep(4000);
//
//        WebElement webname = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[2]/td[2]"));
//        Thread.sleep(5000);
//        Assert.assertTrue(webname.getText().equals("DW 104-1101NEW"));
//        log.log(LogStatus.PASS, ("Pass: The name is displayed correctly " + webname.getText()) + " on ADC web page");
//        Thread.sleep(2000);
//    }
//
//    @Parameters({"UDID_"})
//    @Test(priority = 21)
//    public void Dis_26_All(String UDID_) throws InterruptedException, IOException {
//        add_to_report("Dis_26");
//        log.log(LogStatus.INFO, ("*Dis_26* Verify a sensor can be deleted from the panel and will reflect on the websites"));
//        Thread.sleep(1000);
//        s.delete_from_primary(UDID_, 1);
//        Thread.sleep(5000);
//        adc.New_ADC_session(adc.getAccountId());
//        adc.driver1.findElement(By.partialLinkText("Sensors")).click();
//        Thread.sleep(2000);
//        adc.Request_equipment_list();
//        Thread.sleep(1000);
//        WebElement ispresent = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_sensorList_AlarmDataGridSensor']/tbody/tr[2]/td[2]"));
//        if (ispresent.getText().contains("DW 104-1101")) {
//            log.log(LogStatus.FAIL, ("Fail: Sensor is displayed on the ADC dealer website"));
//            System.out.println(ispresent.getText() + " fail");
//        } else {
//            log.log(LogStatus.PASS, ("Pass: Sensor is deleted successfully"));
//            System.out.println(ispresent.getText() + " pass");
//        }
//        s.addPrimaryCallPG(UDID_, 1, 10, 1041101, 1);
//        Thread.sleep(2000);
//    }
//
//    @Parameters({"UDID_"})
//    @Test(priority = 22)
//    public void Dis_27_DW10(String UDID_) throws Exception {
//        add_to_report("Dis_27");
//        SecuritySensorsPage sen = PageFactory.initElements(s.driver, SecuritySensorsPage.class);
//        HomePage home = PageFactory.initElements(s.driver, HomePage.class);
//        log.log(LogStatus.INFO, ("*Dis_27* readd same sensor from panel"));
//        s.delete_from_primary(UDID_, 1);
//        navigate_to_Security_Sensors_page();
//        sen.Add_Sensor.click();
//        Thread.sleep(1000);
//        s.driver.findElement(By.id("com.qolsys:id/sensorprotocaltype")).click();
//        s.driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='PowerG']")).click();
//
//        s.driver.findElement(By.id("com.qolsys:id/sensor_id")).sendKeys("104");
//        s.driver.findElement(By.id("com.qolsys:id/sensor_powerg_id")).sendKeys("1101");
//        try {
//            s.driver.hideKeyboard();
//        } catch (WebDriverException e) {
//        }
//        s.driver.findElement(By.id("com.qolsys:id/powerg_sensor_desc")).click();
//        s.driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Custom Description']")).click();
//        s.driver.findElement(By.id("com.qolsys:id/powergsensorDescText")).sendKeys("DW 104-1101");
//        try {
//            s.driver.hideKeyboard();
//        } catch (WebDriverException e) {
//        }
//        sen.Save.click();
//        Thread.sleep(2000);
//        home.Home_button.click();
//        Thread.sleep(3000);
//        s.pgprimaryCall(UDID_,104, 1101, PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//    }

//    @Test(priority = 23)
//    public void Dis_31_DW10() throws Exception {
//        add_to_report("Dis_31");
//        log.log(LogStatus.INFO, ("*Dis_31* Verify that the system does not allow an entry delay. The panel should go into immediate alarm if a sensor is triggered."));
//        UIRepo adcUI = PageFactory.initElements(adc.driver1, UIRepo.class);
//        adc.New_ADC_session_User("LeBron_James", "qolsys123");
//        Thread.sleep(5000);
//        adcUI.Disarm_state.click();
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.xpath("//*[contains(@id,'ember')]/div[1]/div/label")).click();
//        adcUI.Arm_Stay.click();
//        Thread.sleep(5000);
//        verifyArmstay();
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in immediate Alarm"));
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 24)
//    public void Dis_32_DM10() throws Exception {
//        add_to_report("Dis_32");
//        log.log(LogStatus.INFO, ("*Dis_32* Verify that the Entry Delay Off option turns off the entry delay and the panel goes into immediate alarm if a sensor is activated."));
//        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
//        servcall.set_ARM_STAY_NO_DELAY_enable();
//        home_page.DISARM.click();
//        home_page.System_state_expand.click();
//        Thread.sleep(2000);
//        driver.findElement(By.id("com.qolsys:id/img_entry_delay")).click();
//        Thread.sleep(1000);
//        home_page.ARM_STAY.click();
//        Thread.sleep(2000);
//        verifyArmstay();
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in immediate Alarm"));
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 25)
//    public void Dis_33_DW10() throws Exception {
//        add_to_report("Dis_33");
//        log.log(LogStatus.INFO, ("*Dis-33* Verify the panel will Arm Stay at the end of the exit delay, open/close sensor gr10"));
//        servcall.set_ARM_STAY_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        DISARM();
//        ADC_verification("//*[contains(text(), 'Armed Stay')]", "//*[contains(text(), 'DW 104-1101')]");
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//    }
//
//    @Test(priority = 26)
//    public void Dis_34_DW12() throws Exception {
//        add_to_report("Dis_34");
//        log.log(LogStatus.INFO, ("*Dis-34* Verify the panel will Arm Stay at the end of the exit delay, open/close sensor gr12"));
//        servcall.set_ARM_STAY_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        DISARM();
//        ADC_verification("//*[contains(text(), 'Armed Stay')]", "//*[contains(text(), 'DW 104-1152')]");
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//    }
//
//    @Test(priority = 27)
//    public void Dis_35_DW13() throws Exception {
//        add_to_report("Dis_35");
//        log.log(LogStatus.INFO, ("*Dis-35* Verify the panel will go into immediate Alarm, open/close sensor gr13"));
//        servcall.set_ARM_STAY_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyInAlarm();
//        enterDefaultUserCode();
//        ADC_verification("//*[contains(text(), 'Pending Alarm')]", "//*[contains(text(), 'DW 104-1231')]");
//        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
//    }
//
//    @Test(priority = 28)
//    public void Dis_36_DW14() throws Exception {
//        add_to_report("Dis_36");
//        log.log(LogStatus.INFO, ("*Dis-36* Verify the panel will Arm Stay at the end of the exit delay, open/close sensor gr14"));
//        servcall.set_ARM_STAY_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        DISARM();
//        ADC_verification("//*[contains(text(), 'Armed Stay')]", "//*[contains(text(), 'DW 104-1216')]");
//        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
//    }
//
//    @Test(priority = 29)
//    public void Dis_37_DW14_DW16() throws Exception {
//        add_to_report("Dis_37");
//        log.log(LogStatus.INFO, ("*Dis-37* System will ArmStay at the end of delay if DW sensors in 16 and 14 group are tampered"));
//        Thread.sleep(1000);
//        servcall.set_AUTO_BYPASS(01);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPER);
//        Thread.sleep(2000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        DISARM();
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPERREST);
//        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 30)
//    public void Dis_38_KF1() throws Exception {
//        add_to_report("Dis_38");
//        log.log(LogStatus.INFO, ("*Dis-38* Verify the panel will Arm Stay instantly if Arm Stay button is pressed by 1-group keyfob"));
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(1000);
//        pgarmer(300, 1004, "02");
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        Thread.sleep(1000);
//        DISARM();
//        Thread.sleep(2000);
//        ADC_verification("//*[contains(text(), 'Arm Stay')]", "//*[contains(text(), 'Keyfob 300-1004')]");
//        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
//    }
//
//    @Test(priority = 31)
//    public void Dis_39_KF1() throws Exception {
//        add_to_report("Dis_39");
//        log.log(LogStatus.INFO, ("*Dis-39* System will ArmStay at the end of exit delay from keyfob group 1"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(300, 1004, "02");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        Thread.sleep(1000);
//        DISARM();
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 32)
//    public void Dis_40_KF1() throws Exception {
//        add_to_report("Dis_40");
//        log.log(LogStatus.INFO, ("*Dis-40* System will Disarm while count down by keyfob group 1, while armed from keyfob"));
//        Thread.sleep(1000);
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(300, 1004, "02");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(300, 1004, "01");
//        Thread.sleep(2000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 33)
//    public void Dis_41_KF1() throws Exception {
//        add_to_report("Dis_41");
//        log.log(LogStatus.INFO, ("*Dis-41* System will ArmStay after count down disarmed by keyfob group 1 while armed from panel"));
//        Thread.sleep(5000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(300, 1004, "01");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 34)
//    public void Dis_42_KF1() throws Exception {
//        add_to_report("Dis_42");
//        log.log(LogStatus.INFO, ("*Dis-42* Verify the panel will Arm Stay instantly if Arm Stay button is pressed by 4-group keyfob"));
//        Thread.sleep(2000);
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//        pgarmer(306, 1003, "02");
//        Thread.sleep(2000);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        DISARM();
//        ADC_verification("//*[contains(text(), 'Arm Stay')]", "//*[contains(text(), 'Keyfob 306-1003')]");
//        log.log(LogStatus.PASS, ("Pass: system is in Arm Stay mode"));
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 35)
//    public void Dis_43_KF4() throws Exception {
//        add_to_report("Dis_43");
//        log.log(LogStatus.INFO, ("*Dis-43* System will Disarm while count down by keyfob group 4, while armed from keyfob"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(306, 1003, "02");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(306, 1003, "01");
//        Thread.sleep(1000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 36)
//    public void Dis_44_KF4() throws Exception {
//        add_to_report("Dis_44");
//        log.log(LogStatus.INFO, ("*Dis-44* System will ArmStay after count down disarmed by keyfob group 4 while armed from panel"));
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(306, 1003, "01");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 37)
//    public void Dis_45_KF6() throws Exception {
//        add_to_report("Dis_45");
//        log.log(LogStatus.INFO, ("*Dis-45* System will ArmStay instantly armed by keyfob 6"));
//        Thread.sleep(2000);
//        pgarmer(305, 1009, "02");
//        Thread.sleep(1000);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        Thread.sleep(1000);
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 38)
//    public void Dis_46_KF6() throws Exception {
//        add_to_report("Dis_46");
//        log.log(LogStatus.INFO, ("*Dis-46* System will ArmStay after exit delay armed by keyfob 6"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(305, 1009, "02");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        Thread.sleep(1000);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        Thread.sleep(1000);
//        DISARM();
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 39)
//    public void Dis_47_KF6() throws Exception {
//        add_to_report("Dis_47");
//        log.log(LogStatus.INFO, ("*Dis-47* System will Disarm while count down by keyfob group 6, while armed from keyfob"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(305, 1009, "02");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(305, 1009, "01");
//        Thread.sleep(1000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 40)
//    public void Dis_48_KF6() throws Exception {
//        add_to_report("Dis_48");
//        log.log(LogStatus.INFO, ("*Dis-48* System will ArmStay after count down disarmed by keyfob group 6 while armed from panel"));
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(305, 1009, "01");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        Thread.sleep(1000);
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 41)
//    public void Dis_49_DW10() throws Exception {
//        add_to_report("Dis_49");
//        log.log(LogStatus.INFO, ("*Dis-49* System will go into immediate alarm at the end of exit delay after tampering contact sensor group 10"));
//        addPrimaryCall(3, 10, 6619296, 1);
//        Thread.sleep(1000);
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        enterDefaultUserCode();
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 42)
//    public void Dis_50_DW12() throws Exception {
//        add_to_report("Dis_50");
//        log.log(LogStatus.INFO, ("*Dis-50* System will go into immediate alarm at the end of exit delay after tampering contact sensor group 12"));
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        enterDefaultUserCode();
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//    }
//
//    @Test(priority = 43)
//    public void Dis_51_DW13() throws Exception {
//        add_to_report("Dis_51");
//        log.log(LogStatus.INFO, ("*Dis-51* System will go into immediate alarm after tampering contact sensor group 13"));
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPER);
//        Thread.sleep(1000);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        enterDefaultUserCode();
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//    }
//
//    @Test(priority = 44)
//    public void Dis_52_DW14() throws Exception {
//        add_to_report("Dis_52");
//        log.log(LogStatus.INFO, ("*Dis-52* System will go into alarm at the end of exit delay after tampering contact sensor group 14"));
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//    }
//
//    @Test(priority = 45)
//    public void Dis_53_DW16() throws Exception {
//        add_to_report("Dis_53");
//        log.log(LogStatus.INFO, ("*Dis-53* System will ArmStay after contact sensor group 16 tamper while exit delay"));
//        ARM_STAY();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        DISARM();
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//    }
//
//    @Test (priority = 46)
//    public void Dis_54_DW10() throws Exception {
//        add_to_report("Dis_54");
//        log.log(LogStatus.INFO, ("*Dis-54* Verify the panel will Arm Stay at the end of the exit delay"));
//        servcall.set_ARM_STAY_NO_DELAY_disable();
//        Thread.sleep(2000);
//        ARM_STAY();
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(10000);
//        verifyArmstay();
//        Thread.sleep(2000);
//        DISARM();
//        servcall.set_ARM_STAY_NO_DELAY_enable();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//    }
//
//    @Test (priority = 47)
//    public void Dis_55_DW10( ) throws Exception {
//        add_to_report("Dis_55");
//        UIRepo adcUI = PageFactory.initElements(adc.driver1, UIRepo.class);
//        log.log(LogStatus.INFO, ("*Dis-55* Verify  if the ADC No Entry Delay option is selected the panel does not have an entry delay and will go into immediate alarm is a sensor is activated. "));
//        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
//        adcUI.Disarm_state.click();
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.xpath("//*[contains(@id,'ember')]/div[1]/div/label")).click();
//        adcUI.Arm_Away.click();
//        Thread.sleep(3000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(3000);
//        verifyInAlarm();
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
//
//    }
//
//    @Test(priority = 48)
//    public void Dis_56_DW10() throws Exception {
//        add_to_report("Dis_56");
//        log.log(LogStatus.INFO, ("*Dis-56* System will go into alarm when Arm Away from panel, select Entry Delay Off, activate a sensor group 10"));
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        home.DISARM.click();
//        try {
//            home.System_state_expand.click();
//        } catch (NoSuchElementException e) {
//        }
//
//        Thread.sleep(1000);
//        home.Entry_Delay.click();
//        Thread.sleep(1000);
//        home.ARM_AWAY.click();
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(1000);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        Thread.sleep(1000);
//    }
//
//    @Test(priority = 49)
//    public void Dis_57_DW10() throws Exception {
//        add_to_report("Dis_57");
//        log.log(LogStatus.INFO, ("*Dis-57* Verify a sensor or group of sensors can be automatically bypassed. Bypassed status should be reflected on websites"));
//        adc.update_sensors_list();
//        Thread.sleep(1000);
//        servcall.set_AUTO_BYPASS(01);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(4000);
//        ARM_AWAY(ConfigProps.longExitDelay);
//        Thread.sleep(1000);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        Thread.sleep(3000);
//        adc.driver1.findElement(By.partialLinkText("History")).click();
//        Thread.sleep(10000);
//        try {
//            WebElement history_message_alarm = adc.driver1.findElement(By.xpath("//*[contains(text(), '(Sensor 1) Bypassed')]"));
//            Assert.assertTrue(history_message_alarm.isDisplayed());
//            {
//                log.log(LogStatus.PASS, ("Dealer website history: " + " " + history_message_alarm.getText()));
//                System.out.println("Dealer website history: " + " " + history_message_alarm.getText());
//            }
//        } catch (Exception e) {
//            log.log(LogStatus.FAIL, ("No such element found!!!"));
//            System.out.println("No such element found!!!");
//        }
//        Thread.sleep(3000);
//        DISARM();
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 50)
//    public void Dis_58_DW10() throws Exception {
//        add_to_report("Dis_58");
//        log.log(LogStatus.INFO, ("*Dis-58* Verify the system will going to alarm at the end of the exit delay when a sensor is left open"));
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        Thread.sleep(1000);
//        servcall.set_AUTO_BYPASS(0);
//        Thread.sleep(4000);
//        home.DISARM.click();
//        Thread.sleep(1000);
//        home.ARM_AWAY.click();
//        Thread.sleep(3000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        Thread.sleep(1000);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        Thread.sleep(2000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//        servcall.set_AUTO_BYPASS(01);
//        Thread.sleep(1000);
//    }
//
//    @Test(priority = 51)
//    public void Dis_59_DW10() throws Exception {
//        add_to_report("Dis_59");
//        log.log(LogStatus.INFO, ("*Dis-59* Verify the panel will arm away at the end of the exit delay, open/close sensor gr10"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        verifyArmaway();
//        DISARM();
//        log.log(LogStatus.PASS, ("Pass: system is in ARM AWAY mode"));
//    }
//
//    @Test(priority = 52)
//    public void Dis_60_DW12() throws Exception {
//        add_to_report("Dis_60");
//        log.log(LogStatus.INFO, ("*Dis-60* Verify the panel will arm away at the end of the exit delay, open/close sensor gr12"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        verifyArmaway();
//        DISARM();
//        log.log(LogStatus.PASS, ("Pass: system is in ARM AWAY mode"));
//    }
//
//    @Test(priority = 53)
//    public void Dis_61_DW13() throws Exception {
//        add_to_report("Dis_61");
//        log.log(LogStatus.INFO, ("*Dis-61* Verify the panel will go into immediate alarm is a sensor in group 13 is opened"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
//        verifyInAlarm();
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
//    }
//
//    @Test(priority = 54)
//    public void Dis_62_DW16() throws Exception {
//        add_to_report("Dis_62");
//        log.log(LogStatus.INFO, ("*Dis-62* Verify the panel will arm away at the end of the exit delay, open/close sensor gr16"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        verifyArmaway();
//        DISARM();
//        log.log(LogStatus.PASS, ("Pass: system is in ARM AWAY mode"));
//    }
//
//    @Test(priority = 55)
//    public void Dis_63_DW14() throws Exception {
//        add_to_report("Dis_63");
//        log.log(LogStatus.INFO, ("*Dis-63* Verify the panel will arm away at the end of the exit delay, open/close sensor gr14"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        verifyArmaway();
//        DISARM();
//        log.log(LogStatus.PASS, ("Pass: system is in ARM AWAY mode"));
//    }
//
//    @Test(priority = 56)
//    public void Dis_64_DW9() throws Exception {
//        add_to_report("Dis_64");
//        log.log(LogStatus.INFO, ("*Dis-64* Verify the panel will go into alarm after delay if sensor in gr9 is opened"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1123, PGSensorsActivity.INOPEN);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1123, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyInAlarm();
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in Alarm"));
//    }
//
//    @Test(priority = 57)
//    public void Dis_65_DW10_DW12() throws Exception {
//        add_to_report("Dis_65");
//        log.log(LogStatus.INFO, ("*Dis-65* Verify the panel will automatically extend the exit delay by 60 seconds if a second entry/exit delay sensor if tripped during exit delay"));
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
//        Thread.sleep(3000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
//        Thread.sleep(3000);
//        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
//        TimeUnit.SECONDS.sleep(65);
//        verifyArmaway();
//        DISARM();
//        log.log(LogStatus.PASS, ("Pass: system is in ARM Away mode, exit delay extended by 60 seconds"));
//    }
//
//    @Test(priority = 58)
//    public void Dis_66_KF1() throws Exception {
//        add_to_report("Dis_66");
//        log.log(LogStatus.INFO, ("*Dis-66* System ArmAway instantly from keyfob group 1"));
//        pgarmer(300, 1004, "06");
//        Thread.sleep(1000);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 59)
//    public void Dis_67_KF1() throws Exception {
//        add_to_report("Dis_67");
//        log.log(LogStatus.INFO, ("*Dis-67* System ArmAway at the end of exit delay from keyfob group 1"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(2000);
//        pgarmer(300, 1004, "03");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        Thread.sleep(1000);
//        DISARM();
//        Thread.sleep(1000);
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 60)
//    public void Dis_68_KF1() throws Exception {
//        add_to_report("Dis_68");
//        log.log(LogStatus.INFO, ("*Dis-68* System will Disarm while count down by keyfob group 1, while armed from keyfob"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(300, 1004, "03");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(300, 1004, "01");
//        Thread.sleep(1000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 61)
//    public void Dis_69_KF1() throws Exception {
//        add_to_report("Dis_69");
//        log.log(LogStatus.INFO, ("*Dis-69* System will ArmAway after count down armed from panel, press disarm on keyfob 1"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgarmer(300, 1004, "01");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        DISARM();
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 62)
//    public void Dis_70_KF4() throws Exception {
//        add_to_report("Dis_70");
//        log.log(LogStatus.INFO, ("*Dis-70* System ArmAway instantly from keyfob group 4"));
//        Thread.sleep(2000);
//        pgarmer(306, 1003, "03");
//        Thread.sleep(3000);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        Thread.sleep(2000);
//        DISARM();
//        Thread.sleep(1000);
//    }
//
//    @Test(priority = 63)
//    public void Dis_71_KF4() throws Exception {
//        add_to_report("Dis_71");
//        log.log(LogStatus.INFO, ("*Dis-71* System ArmStay at the end of exit delay from keyfob group 4"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(306, 1003, "02");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
//        verifyArmstay();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Stay"));
//        Thread.sleep(1000);
//        DISARM();
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 64)
//    public void Dis_72_KF4() throws Exception {
//        add_to_report("Dis_72");
//        log.log(LogStatus.INFO, ("*Dis-72* System ArmAway at the end of exit delay from keyfob group 4"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(306, 1003, "03");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        Thread.sleep(1000);
//        DISARM();
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 65)
//    public void Dis_73_KF4() throws Exception {
//        add_to_report("Dis_73");
//        log.log(LogStatus.INFO, ("*Dis-73* System will Disarm while countdown by keyfob group 4"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(306, 1003, "03");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(306, 1003, "01");
//        Thread.sleep(2000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//        Thread.sleep(1000);
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 66)
//    public void Dis_74_KF4() throws Exception {
//        add_to_report("Dis_74");
//        log.log(LogStatus.INFO, ("*Dis-74* System will ArmAway after count down armed from panel, press disarm on keyfob 4"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgarmer(306, 1003, "01");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        DISARM();
//        Thread.sleep(1000);
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 67)
//    public void Dis_75_KF6() throws Exception {
//        add_to_report("Dis_75");
//        log.log(LogStatus.INFO, ("*Dis-75* System will ArmAway instantly armed from keyfob 6"));
//        Thread.sleep(2000);
//        pgarmer(305, 1009, "03");
//        Thread.sleep(1000);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 68)
//    public void Dis_76_KF6() throws Exception {
//        add_to_report("Dis_76");
//        log.log(LogStatus.INFO, ("*Dis-76* System will ArmAway at the end of count down armed from keyfob 6"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(2000);
//        pgarmer(305, 1009, "03");
//        Thread.sleep(1000);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        DISARM();
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 69)
//    public void Dis_77_KF6() throws Exception {
//        add_to_report("Dis_77");
//        log.log(LogStatus.INFO, ("*Dis-77* System will Disarm while count down by keyfob group 6, while armed from keyfob"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        pgarmer(305, 1009, "03");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2);
//        pgarmer(305, 1009, "01");
//        Thread.sleep(1000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 70)
//    public void Dis_78_KF6() throws Exception {
//        add_to_report("Dis_78");
//        log.log(LogStatus.INFO, ("*Dis-78* System will ArmAway after count down armed from panel, press disarm on keyfob 6"));
//        servcall.set_KEYFOB_NO_DELAY_disable();
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgarmer(305, 1009, "03");
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is Armed Away"));
//        DISARM();
//        Thread.sleep(1000);
//        servcall.set_KEYFOB_NO_DELAY_enable();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 71)
//    public void Dis_79_DW10() throws Exception {
//        add_to_report("Dis_79");
//        log.log(LogStatus.INFO, ("*Dis_79* Verify the system will alert (message) the user that some sensors are in a tampered or failure state"));
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        servcall.set_AUTO_BYPASS(0);
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
//        Thread.sleep(2000);
//        home.DISARM.click();
//        home.ARM_AWAY.click();
//        Thread.sleep(2000);
//        if (driver.findElements(By.id("com.qolsys:id/message")).size() == 1) {
//            log.log(LogStatus.PASS, "Pass: Tamper Sensor Pop-up Message Received ***");
//            driver.findElement(By.id("com.qolsys:id/ok")).click();
//        } else {
//            log.log(LogStatus.FAIL, "Fail: Tamper Sensor Pop-up Message Not Received***");
//            Thread.sleep(2000);
//            home.ARM_AWAY.click();
//        }
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay);
//        verifyArmaway();
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
//        DISARM();
//    }
//
//    @Test(priority = 72)
//    public void Dis_80_DW10() throws Exception {
//        add_to_report("Dis_80");
//        log.log(LogStatus.INFO, ("*Dis_80* System will go into pending tamper alarm at the end of exit delay, tamper sensor gr10"));
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        Thread.sleep(5000);
//        ADC_verification("//*[contains(text(), 'Sensor 1 Tamper**')]", "//*[contains(text(), 'Delayed alarm on sensor 1 in partition 1')]");
//        log.log(LogStatus.PASS, ("Pass: Tamper message is displayed, Delayed Alarm is displayed"));
//        Thread.sleep(1000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 73)
//    public void Dis_81_DW12() throws Exception {
//        add_to_report("Dis_81");
//        log.log(LogStatus.INFO, ("*Dis_81* System will go into pending tamper alarm at the end of exit delay, tamper sensor gr12"));
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        Thread.sleep(5000);
//        ADC_verification("//*[contains(text(), 'Sensor 2 Tamper**')]", "//*[contains(text(), 'Delayed alarm on sensor 2 in partition 1')]");
//        log.log(LogStatus.PASS, ("Pass: Tamper message is displayed, Delayed Alarm is displayed"));
//        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 74)
//    public void Dis_82_DW13() throws Exception {
//        add_to_report("Dis_82");
//        log.log(LogStatus.INFO, ("*Dis_82* System will go into pending tamper alarm at the end of exit delay, tamper sensor gr13"));
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        Thread.sleep(5000);
//        ADC_verification("//*[contains(text(), 'Sensor 3 Tamper**')]", "//*[contains(text(), 'Delayed alarm on sensor 3 in partition 1')]");
//        log.log(LogStatus.PASS, ("Pass: Tamper message is displayed, Delayed Alarm is displayed"));
//        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 75)
//    public void Dis_83_DW14() throws Exception {
//        add_to_report("Dis_83");
//        log.log(LogStatus.INFO, ("*Dis_83* System will go into pending tamper alarm at the end of exit delay, tamper sensor gr14"));
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPER);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        Thread.sleep(5000);
//        ADC_verification("//*[contains(text(), 'Sensor 4 Tamper**')]", "//*[contains(text(), 'Delayed alarm on sensor 4 in partition 1')]");
//        log.log(LogStatus.PASS, ("Pass: Tamper message is displayed, Delayed Alarm is displayed"));
//        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 76)
//    public void Dis_84_DW16() throws Exception {
//        add_to_report("Dis_84");
//        log.log(LogStatus.INFO, ("*Dis_84* System will go into immediate tamper alarm, tamper sensor gr16"));
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPER);
//        Thread.sleep(1000);
//        verifyInAlarm();
//        log.log(LogStatus.PASS, ("Pass: system is in ALARM"));
//        Thread.sleep(5000);
//        ADC_verification("//*[contains(text(), 'Sensor 5 Tamper**')]", "//*[contains(text(), 'Delayed alarm on sensor 5 in partition 1')]");
//        log.log(LogStatus.PASS, ("Pass: Tamper message is displayed, Delayed Alarm is displayed"));
//        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPERREST);
//        Thread.sleep(1000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 77)
//    public void Dis_85_DW10() throws Exception {
//        add_to_report("Dis_85");
//        log.log(LogStatus.INFO, ("*Dis_85* Verify the panel will Arm Away if a group 10 sensor is tampered and untampered before the end of  the exit delay"));
//        Thread.sleep(1000);
//        ARM_AWAY(ConfigProps.longExitDelay / 2);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
//        Thread.sleep(2000);
//        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
//        TimeUnit.SECONDS.sleep(ConfigProps.longExitDelay / 2 + 2);
//        verifyArmaway();
//        log.log(LogStatus.PASS, ("Pass: system is in ARM AWAY mode"));
//        Thread.sleep(1000);
//        DISARM();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 78)
//    public void Dis_86_KF1() throws Exception {
//        add_to_report("Dis_86");
//        log.log(LogStatus.INFO, ("*Dis-86* Verify the panel will go into an immediate Police Emergency Panic if Alarm is sent by 1-group keyfob"));
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(300, 1004, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        ADC_verification("//*[contains(text(), 'Keyfob 300-1004')]", "//*[contains(text(), 'Delayed Police Panic')]");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 79)
//    public void Dis_87_KF4() throws Exception {
//        add_to_report("Dis_87");
//        log.log(LogStatus.INFO, ("*Dis-87* Verify the panel will go into an immediate Auxiliary Emergency Alarm if Alarm is sent by 4-group keyfob"));
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(306, 1003, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        ADC_verification("//*[contains(text(), 'Keyfob 306-1003')]", "//*[contains(text(), 'Keyfob 24 Delayed Aux')]");
//        log.log(LogStatus.PASS, ("Pass: Auxiliary Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 80)
//    public void Dis_88_KF6() throws Exception {
//        add_to_report("Dis_88");
//        log.log(LogStatus.INFO, ("*Dis-88* Verify the panel will go into an immediate Auxiliary Emergency Alarm if Alarm is sent by 6-group keyfob"));
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(305, 1009, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        ADC_verification("//*[contains(text(), 'Keyfob 305-1009')]", "//*[contains(text(), 'Keyfob 23 Delayed Aux')]");
//        log.log(LogStatus.PASS, ("Pass: Auxiliary Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 81)
//    public void Dis_89_AUX6() throws Exception {
//        add_to_report("Dis_89");
//        log.log(LogStatus.INFO, ("*Dis-89* Verify the panel will go into an immediate Auxiliary Emergency Alarm if Alarm is sent by 6-group aux. pendant"));
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(320, 1015, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        ADC_verification("//*[contains(text(), 'AuxPendant 320-1015')]", "//*[contains(text(), 'Delayed alarm')]");
//        log.log(LogStatus.PASS, ("Pass: Auxiliary Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 82)
//    public void Dis_90_AUX4() throws Exception {
//        add_to_report("Dis_90");
//        log.log(LogStatus.INFO, ("*Dis-90* Verify the panel will go into an immediate Auxiliary Emergency Alarm if Alarm is sent by 4-group aux. pendant"));
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(320, 1016, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        ADC_verification("//*[contains(text(), 'AuxPendant 320-1016')]", "//*[contains(text(), 'Delayed alarm')]");
//        log.log(LogStatus.PASS, ("Pass: Auxiliary Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    public void addAux() throws IOException, InterruptedException {
//        report = new ExtentReports(projectPath + "/Report/QTMS_PowerG_Disarm.html", false);
//        Thread.sleep(2000);
//        addPrimaryCallPG(33, 1, 3201105, 21);
//        addPrimaryCallPG(34, 0, 3201116, 21);
//        addPrimaryCallPG(35, 2, 3201207, 21);
//        adc.New_ADC_session(adc.getAccountId());
//        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Sensors"))).click();
//        adc.Request_equipment_list();
//    }
//
//    @Test(/*dependsOnMethods = {"addAux"},*/ priority = 81)
//    public void Dis_91_AUX1() throws Exception {
//        add_to_report("Dis_91");
//        log.log(LogStatus.INFO, ("*Dis-91* Verify the panel will go into an immediate Auxiliary Emergency Alarm if Alarm is sent by 4-group aux. pendant"));
//        Thread.sleep(2000);
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(320, 1105, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        ADC_verification("//*[contains(text(), 'Auxiliary Pendant 33')]", "//*[contains(text(), 'Sensor 33 Alarm**')]");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 83)
//    public void Dis_92_AUX0() throws Exception {
//        add_to_report("Dis_92");
//        log.log(LogStatus.INFO, ("*Dis-92* Verify the panel will go into an immediate Police Emergency Panic if Alarm is sent by 0-group aux. pendant"));
//        Thread.sleep(2000);
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        pgprimaryCall(320, 1116, "11 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        ADC_verification("//*[contains(text(), 'Auxiliary Pendant 34')]", "//*[contains(text(), 'Sensor 34 Alarm**')]");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//    }
//
//    public void resetAlarm(String alarm) throws InterruptedException {
//        adc.New_ADC_session_User("LeBron_James", "qolsys123");
//        Thread.sleep(5000);
//        adc.driver1.get("https://www.alarm.com/web/system/alerts-issues");
//        Thread.sleep(5000);
//        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stop " + alarm + "']"))).click();
//        Thread.sleep(4000);
//        adc.driver1.findElement(By.xpath("(//*[text()='Stop Alarms'])[last()]")).click();
//        Thread.sleep(10000);
//    }
//
//    @Test(priority = 84)
//    public void Dis_93_AUX2() throws Exception {
//        add_to_report("Dis_93");
//        log.log(LogStatus.INFO, ("*Dis-93* Verify the panel will go into an silent Police Emergency Panic if Alarm is sent by 2-group aux. pendant"));
//        Thread.sleep(3000);
//        pgprimaryCall(320, 1207, "11 1");
//        Thread.sleep(500);
//        //reset to idle
//        pgprimaryCall(320, 1207, "80 0");
//        Thread.sleep(3000);
//        verifyDisarm();
//        ADC_verification("//*[contains(text(), 'Auxiliary Pendant 35')]", "//*[contains(text(), 'Sensor 35 Alarm**')]");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        Thread.sleep(5000);
//    }
//
//    @Test(priority = 85)
//    public void Dis_94() throws InterruptedException {
//        add_to_report("Dis_94");
//        log.log(LogStatus.INFO, ("*Dis-94* Verify the panel will disarm silently from User site if Alarm is sent by 2-group aux. pendant"));
//        resetAlarm("Alarm");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is successfully disarmed from user website"));
//    }
//
//    @Test(priority = 86)
//    public void Dis_96_KP2() throws Exception {
//        add_to_report("Dis_96");
//        log.log(LogStatus.INFO, ("*Dis-96* Verify the panel will go into an silent Police Emergency Panic if Alarm is sent by 2-group keypad"));
//        Thread.sleep(3000);
//        pgprimaryCall(371, 1008, "10 1");
//        Thread.sleep(500);
//        //reset to idle
//        pgprimaryCall(371, 1008, "80 0");
//        Thread.sleep(3000);
//        try {
//            verifyDisarm();
//        } catch (NoSuchElementException e) {
//            log.log(LogStatus.FAIL, ("Failed: Panel is not Disarmed"));
//        }
//        ADC_verification("//*[contains(text(), 'Keypad 27')]", "//*[contains(text(), 'Sensor 27 Alarm**')]");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        Thread.sleep(5000);
//    }
//
//    @Test(priority = 87)
//    public void Dis_97_KP0() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_97");
//        log.log(LogStatus.INFO, ("*Dis-97* Verify the panel will go into an immediate Police Emergency Panic if Alarm is sent by 0-group keypad"));
//        Thread.sleep(3000);
//        pgprimaryCall(371, 1005, "10 1");
//        Thread.sleep(500);
//        //reset to idle
//        pgprimaryCall(371, 1005, "80 0");
//        Thread.sleep(3000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        Thread.sleep(3000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        ADC_verification("//*[contains(text(), 'Keypad/Touchscreen(25)')]", "//*[contains(text(), 'Police Panic')]");
//        log.log(LogStatus.PASS, ("Pass: Police Emergency is displayed, events are correctly displayed at the ADC dealer website"));
//        Thread.sleep(5000);
//    }
//
//    @Test(priority = 88)
//    public void Dis_101_SM() throws InterruptedException, IOException {
//        add_to_report("Dis_101");
//        log.log(LogStatus.INFO, ("*Dis-101* Verify the system will disarm from Smoke detector Fire Alarm at the User Site"));
//        Thread.sleep(3000);
//        pgprimaryCall(201, 1541, "06 1");
//        Thread.sleep(5000);
//        resetAlarm("Alarm");
//        log.log(LogStatus.PASS, ("Pass: Fire Alarm is successfully disarmed from user website"));
//    }
//
//    @Test(priority = 89)
//    public void Dis_102_SM() throws Exception {
//        add_to_report("Dis_102");
//        log.log(LogStatus.INFO, ("*Dis-102*Verify that the panel sees when the Smoke sensor is tampered"));
//        Thread.sleep(3000);
//        pgprimaryCall(201, 1541, "82 1");
//        Thread.sleep(2000);
//        WebElement sensor = driver.findElement(By.xpath("//android.widget.TextView[@text='Smoke 201-1541']"));
//        elementVerification(sensor, "Smoke sensor");
//        Thread.sleep(2000);
//        ADC_verification("//*[contains(text(), 'Smoke 201-1541')]", "//*[contains(text(), 'Sensor 14 Tamper**')]");
//        Thread.sleep(2000);
//        pgprimaryCall(201, 1541, "82 0");
//        log.log(LogStatus.PASS, ("Pass: panel and dealer website are correctly displaying Tamper event for smoke sensor"));
//    }
//
//    @Test(priority = 90)
//    public void Dis_103_SM() throws InterruptedException, IOException {
//        add_to_report("Dis_103");
//        log.log(LogStatus.INFO, ("*Dis-103* Verify that the system restores the Smoke detector status  from 'Tampered' to 'Normal'"));
//        Thread.sleep(3000);
//        pgprimaryCall(201, 1541, "82 1");
//        Thread.sleep(4000);
//        pgprimaryCall(201, 1541, "82 0");
//        navigateToSettingsPage();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("com.qolsys:id/tab4")).click();
//        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
//
//        Assert.assertTrue(li_status1.get(1).getText().equals("Normal"));
//        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
//        Assert.assertTrue(li_status1.get(2).getText().equals("Tampered"));
//        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
//        Thread.sleep(2000);
//        li_status1.clear();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 91)
//    public void Dis_104_SM() throws InterruptedException, IOException {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_104");
//        log.log(LogStatus.INFO, ("*Dis-104* Verify that the system restorers as the Smoke Detector status  from 'Alarmed' to 'Normal'"));
//        Thread.sleep(3000);
//        pgprimaryCall(201, 1541, "06 1");
//        Thread.sleep(3000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        Thread.sleep(3000);
//        navigateToSettingsPage();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("com.qolsys:id/tab4")).click();
//        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
//
//        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
//        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
//        Assert.assertTrue(li_status1.get(3).getText().equals("Alarmed"));
//        log.log(LogStatus.PASS, ("Pass: Alarm event is displayed"));
//        Thread.sleep(2000);
//        li_status1.clear();
//        Thread.sleep(7000);
//
//        ADC_verification("//*[contains(text(), 'Fire Alarm')]", "//*[contains(text(), 'Panel Disarmed')]");
//        log.log(LogStatus.PASS, ("Pass: The system changes mode from Alarm to Disarm"));
//    }
//
//    @Test(priority = 92)
//    public void Dis_105_Wat() throws Exception {
//        add_to_report("Dis_105");
//        log.log(LogStatus.INFO, ("*Dis-105* Verify the system is getting Water alarm from Water detector"));
//        Thread.sleep(3000);
//        pgprimaryCall(241, 1971, "0B 1");
//        Thread.sleep(3000);
//        verifyInAlarm();
//        Thread.sleep(7000);
//        enterDefaultUserCode();
//
//        ADC_verification("//*[contains(text(), 'Pending Alarm')]", "//*[contains(text(), 'Water Alarm')]");
//        log.log(LogStatus.PASS, ("Pass: Alarm event is displayed"));
//    }
//
//    @Test(priority = 93)
//    public void Dis_106_Wat() throws Exception {
//        add_to_report("Dis_106");
//        log.log(LogStatus.INFO, ("*Dis-106* Verify the system will disarm from Water detector Water Alarm at the User Site"));
//        Thread.sleep(3000);
//        pgprimaryCall(241, 1971, "0B 1");
//        Thread.sleep(3000);
//        verifyInAlarm();
//
//        resetAlarm("Alarms");
//        Thread.sleep(3000);
//        verifyDisarm();
//        log.log(LogStatus.PASS, ("Pass: System is Disarmed from the User site"));
//    }
//
//    @Test (priority = 94)
//    public void Dis_107_Wat() throws Exception {
//        add_to_report("Dis_107");
//        log.log(LogStatus.INFO, ("*Dis-107* Verify the system will disarm from Water detector Water Alarm at the User Site"));
//        Thread.sleep(3000);
//        pgprimaryCall(241, 1971, "82 1");
//        Thread.sleep(3000);
//        verifyDisarm();
//        pgprimaryCall(241, 1971, "82 0");
//        navigateToSettingsPage();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("com.qolsys:id/tab4")).click();
//        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
//
//        Assert.assertTrue(li_status1.get(1).getText().equals("Normal"));
//        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
//        Assert.assertTrue(li_status1.get(2).getText().equals("Tampered"));
//        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
//
//    }
//
//    @Test (priority = 95)
//    public void Dis_108_Wat() throws Exception {
//        add_to_report("Dis_108");
//        log.log(LogStatus.INFO, ("*Dis-108* Verify that the system restores the Water Detector status  from 'Tampered' to 'Normal'"));
//        Thread.sleep(3000);
//        pgprimaryCall(241, 1971, "82 1");
//        Thread.sleep(3000);
//        verifyDisarm();
//        pgprimaryCall(241, 1971, "82 0");
//
//        ADC_verification("//*[contains(text(), ' Sensor 21 Tamper**')]", "//*[contains(text(), ' End of Tamper')]");
//        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
//    }
//
//    @Test (priority = 96)
//    public void Dis_109_Wat() throws Exception {
//        add_to_report("Dis_109");
//        log.log(LogStatus.INFO, ("*Dis-109* Verify that the system restores the Water Detector status  from 'Alarmed' to 'Normal'"));
//        Thread.sleep(3000);
//        pgprimaryCall(241, 1971, "0B 1");
//        Thread.sleep(3000);
//        verifyInAlarm();
//        Thread.sleep(3000);
//        enterDefaultUserCode();
//        navigateToSettingsPage();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("com.qolsys:id/tab4")).click();
//        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
//
//        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
//        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
//        Assert.assertTrue(li_status1.get(4).getText().equals("Alarmed"));
//        log.log(LogStatus.PASS, ("Pass: Alarmed event is displayed"));
//        log.log(LogStatus.PASS, ("Pass: system restores the Water Detector status"));
//
//    }
//
//    @Test(priority = 97)
//    public void Dis_110_CO() throws Exception {
//        add_to_report("Dis_110");
//        log.log(LogStatus.INFO, ("*Dis-110* Verify that the system restores the CO Detector status  from 'Alarmed' to 'Normal'"));
//        Thread.sleep(3000);
//        pgprimaryCall(220, 1661, "08 1");
//        Thread.sleep(3000);
//        verifyInAlarm();
//        Thread.sleep(2000);
//        enterDefaultUserCode();
//        Thread.sleep(2000);
//        navigateToSettingsPage();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("com.qolsys:id/tab4")).click();
//        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
//
//        Assert.assertTrue(li_status1.get(2).getText().equals("Normal"));
//        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
//        Assert.assertTrue(li_status1.get(4).getText().equals("Alarmed"));
//        log.log(LogStatus.PASS, ("Pass: Alarmed event is displayed"));
//        Thread.sleep(2000);
//        li_status1.clear();
//        Thread.sleep(7000);
//
//        ADC_verification("//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'Disarmed')]");
//        log.log(LogStatus.PASS, ("Pass: The system changes mode from Alarm to Disarm"));
//    }
//
//    @Test(priority = 98)
//    public void Dis_112_CO() throws InterruptedException, IOException {
//        add_to_report("Dis_112");
//        log.log(LogStatus.INFO, ("*Dis-112* Verify that the system restores the CO Detector status  from 'Tampered' to 'Normal'"));
//        Thread.sleep(3000);
//        pgprimaryCall(220, 1661, "82 1");
//        Thread.sleep(4000);
//        pgprimaryCall(220, 1661, "82 0");
//        navigateToSettingsPage();
//        Thread.sleep(1000);
//        driver.findElement(By.xpath("//android.widget.TextView[@text='STATUS']")).click();
//        Thread.sleep(1000);
//        driver.findElement(By.id("com.qolsys:id/tab4")).click();
//        List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/textView3"));
//
//        Assert.assertTrue(li_status1.get(1).getText().equals("Normal"));
//        log.log(LogStatus.PASS, ("Pass: Normal event is displayed"));
//        Assert.assertTrue(li_status1.get(2).getText().equals("Tampered"));
//        log.log(LogStatus.PASS, ("Pass: Tampered event is displayed"));
//        Thread.sleep(2000);
//        li_status1.clear();
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 99)
//    public void Dis_113_CO() throws Exception {
//        add_to_report("Dis_113");
//        log.log(LogStatus.INFO, ("*Dis-113* Verify that the panel sees when the CO detector is tampered"));
//        Thread.sleep(3000);
//        pgprimaryCall(220, 1661, "82 1");
//        Thread.sleep(4000);
//        WebElement sensor = driver.findElement(By.xpath("//android.widget.TextView[@text='CO 220-1661']"));
//        elementVerification(sensor, "Smoke sensor");
//        Thread.sleep(2000);
//        log.log(LogStatus.PASS, ("Pass: Panel is successfully displayed that the sensor is tampered"));
//        pgprimaryCall(220, 1661, "82 0");
//        Thread.sleep(2000);
//    }
//
//    @Test(priority = 100)
//    public void Dis_114_CO() throws InterruptedException, IOException {
//        add_to_report("Dis_114");
//        log.log(LogStatus.INFO, ("*Dis-114* Verify the system will disarm from CO detector CO Alarm at the User Site"));
//        Thread.sleep(3000);
//        pgprimaryCall(220, 1661, "08 1");
//        Thread.sleep(4000);
//        resetAlarm("Alarm");
//        log.log(LogStatus.PASS, ("Pass: Fire Alarm is successfully disarmed from user website"));
//    }
//
//    @Test(priority = 101)
//    public void Dis_115_KF1() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        add_to_report("Dis_115");
//        log.log(LogStatus.INFO, ("*Dis-115* Verify the system will still into alarm mode if to press Disarm button by keyfob in 1-group"));
//        servcall.set_KEYFOB_ALARM_DISARM(0);
//        Thread.sleep(2000);
//        home.Emergency_Button.click();
//        emergency.Police_icon.click();
//        Thread.sleep(3000);
//        pgarmer(300, 1004, "01");
//        Thread.sleep(3000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        Thread.sleep(1000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in alarm mode if to press Disarm button by keyfob in 1-group"));
//    }
//
//    @Test(priority = 102)
//    public void Dis_116_KF1() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_116");
//        log.log(LogStatus.INFO, ("*Dis-116* Verify the system will still into alarm mode from The keyfob itself if to press Disarm button by keyfob in 1-group"));
//        servcall.set_KEYFOB_ALARM_DISARM(0);
//        Thread.sleep(2000);
//        pgprimaryCall(300, 1004, "10 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        Thread.sleep(2000);
//        pgarmer(300, 1004, "01");
//        Thread.sleep(3000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        Thread.sleep(1000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in alarm mode if to press Disarm button by keyfob in 1-group"));
//    }
//
//    @Test(priority = 103)
//    public void Dis_117_KF1() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_117");
//        log.log(LogStatus.INFO, ("*Dis-117* Verify the system will disarmed from alarm mode if to press Disarm button by keyfob in 1-group"));
//        servcall.set_KEYFOB_ALARM_DISARM(1);
//        Thread.sleep(2000);
//        pgprimaryCall(300, 1004, "10 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Police_Emergency_Alarmed, "Police Emergency");
//        Thread.sleep(2000);
//        pgarmer(300, 1004, "01");
//        Thread.sleep(3000);
//        verifyDisarm();
//        Thread.sleep(1000);
//        servcall.set_KEYFOB_ALARM_DISARM(0);
//        log.log(LogStatus.PASS, ("Pass: system is Disarmed"));
//    }
//
//    @Test(priority = 104)
//    public void Dis_118() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_118");
//        log.log(LogStatus.INFO, ("*Dis-118* Verify the system will generate different alarms if activate different sensors at the same time"));
//        pgprimaryCall(320, 1015, "10 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        Thread.sleep(1000);
//        pgprimaryCall(200, 1531, "06 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Fire_Emergency_Alarmed, "Fire Emergency");
//        Thread.sleep(1000);
//        pgprimaryCall(220, 1661, "08 1");
//        Thread.sleep(2000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//
//        ADC_verification("//*[contains(text(), 'Carbon Monoxide')]", "//*[contains(text(), 'AuxPendant 320-1015')]");
//    }
//
//    @Test(priority = 105)
//    public void Dis_119_KF6() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//        add_to_report("Dis_119");
//        log.log(LogStatus.INFO, ("*Dis-119* Verify the system will still into alarm mode if to press Disarm button by keyfob in 6-group"));
//        servcall.set_KEYFOB_ALARM_DISARM(0);
//        Thread.sleep(2000);
//        home.Emergency_Button.click();
//        emergency.Fire_icon.click();
//        Thread.sleep(3000);
//        pgarmer(305, 1009, "01");
//        Thread.sleep(3000);
//        elementVerification(emergency.Fire_Emergency_Alarmed, "Fire Emergency");
//        Thread.sleep(1000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in alarm mode if to press Disarm button by keyfob in 6-group"));
//    }
//
//    @Test(priority = 106)
//    public void Dis_120_KF6() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_120");
//        log.log(LogStatus.INFO, ("*Dis-120* Verify the system will still into alarm mode from The keyfob itself if to press Disarm button by keyfob in 6-group"));
//        servcall.set_KEYFOB_ALARM_DISARM(0);
//        Thread.sleep(2000);
//        pgprimaryCall(305, 1009, "10 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        Thread.sleep(2000);
//        pgarmer(305, 1009, "01");
//        Thread.sleep(3000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        Thread.sleep(1000);
//        emergency.Cancel_Emergency.click();
//        enterDefaultUserCode();
//        log.log(LogStatus.PASS, ("Pass: system is in alarm mode if to press Disarm button by keyfob in 6-group"));
//    }
//
//    @Test(priority = 107)
//    public void Dis_121_KF6() throws Exception {
//        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
//        add_to_report("Dis_121");
//        log.log(LogStatus.INFO, ("*Dis-121* Verify the system will still into alarm mode from The keyfob itself if to press Disarm button by same keyfob in 6-group"));
//        servcall.set_KEYFOB_ALARM_DISARM(1);
//        Thread.sleep(2000);
//        pgprimaryCall(305, 1009, "10 1");
//        Thread.sleep(2000);
//        elementVerification(emergency.Auxiliary_Emergency_Alarmed, "Auxiliary Emergency");
//        Thread.sleep(2000);
//        pgarmer(305, 1009, "01");
//        Thread.sleep(3000);
//        verifyDisarm();
//        servcall.set_KEYFOB_ALARM_DISARM(0);
//        log.log(LogStatus.PASS, ("Pass: system is in alarm mode if to press Disarm button by keyfob in 6-group"));
//    }


    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        s.driver.quit();
        adc.driver1.quit();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshot_path = setup.captureScreenshot(s.driver, result.getName());
            log.log(LogStatus.FAIL, "Test Case failed is " + result.getName());
            log.log(LogStatus.FAIL, "Snapshot below:  " + test.addScreenCapture(screenshot_path));
            //      log.log(LogStatus.FAIL,"Test Case failed", screenshot_path);
            test.addScreenCapture(setup.captureScreenshot(s.driver, result.getName()));
        }
        report.endTest(log);
        report.flush();
        adc.driver1.quit();
    }
}
