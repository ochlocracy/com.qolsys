package powerG;

import adc.ADC;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.HomePage;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShockSensor extends Setup {

    /* Estimate execution time:  */

    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("Shock_Sensor");

    public ShockSensor() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void ADC_verification(String string, String string1) throws InterruptedException, IOException {
        adc.New_ADC_session(adc.getAccountId());
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
            }
            Thread.sleep(7000);
        }
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setArmStayNoDelay("Enable");
        setAutoStay("Disable");
    }

    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }

    @Test
    public void Shock() throws IOException, InterruptedException {
        rep.add_to_report("Shock_0");
        rep.log.log(LogStatus.INFO, ("*Shock_0* Set sensitivity lvl from ADC dealer website"));
        adc.New_ADC_session(adc.getAccountId());
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_navLinks']/ul/li[10]/a/i")).click();
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_UcAirFxMenu1_UcSensorSettings_lnkAdvancedSensorSettings']/li/span")).click();
        TimeUnit.SECONDS.sleep(2);
        Select list = new Select(adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_deviceList_ddlDevicesDdl']")));
        list.selectByVisibleText("17 - Shock 171-1741");
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_udList_gvSettings']/tbody/tr[18]/td[5]/a")).click();
        TimeUnit.SECONDS.sleep(3);

        Select sensitivity_lvl = new Select (adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_udList_ddlNewValue']")));
        sensitivity_lvl.selectByVisibleText("High");
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_udList_butChange']")).click();
        TimeUnit.SECONDS.sleep(3);
        WebElement desired = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_udList_gvSettings']/tbody/tr[18]/td[4]"));
        Assert.assertTrue(desired.getText().equals("High"));
        rep.log.log(LogStatus.PASS, ("Pass: Sensitivity lvl is set to \"High\""));
    }

    @Test(priority = 1)
    public void Shock_01() throws Exception {
        rep.create_report("Shock_01");
        rep.log.log(LogStatus.INFO, ("*Shock_01* Disarm mode tripping Shock group 13 -> Disarm"));
        TimeUnit.SECONDS.sleep(1);
        pgprimaryCall(171, 1741, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        rep.log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 2)
    public void Shock_02() throws Exception {
        rep.add_to_report("Shock_02");
        rep.log.log(LogStatus.INFO, ("*Shock_02* ArmStay mode tripping Shock group 13 -> Instant Alarm"));
        TimeUnit.SECONDS.sleep(1);
        ARM_STAY();
        pgprimaryCall(171, 1741, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 3)
    public void Shock_03() throws Exception {
        rep.add_to_report("Shock_03");
        rep.log.log(LogStatus.INFO, ("*Shock_03* ArmAway mode tripping Shock group 13 -> Instant Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(171, 1741, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 4)
    public void Shock_04() throws Exception {
        rep.add_to_report("Shock_04");
        rep.log.log(LogStatus.INFO, ("*Shock_04* Disarm mode tripping Shock group 17 -> Disarm"));
        pgprimaryCall(171, 1771, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'Disarmed')]");
        rep.log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 5)
    public void Shock_05() throws Exception {
        rep.add_to_report("Shock_05");
        rep.log.log(LogStatus.INFO, ("*Shock_05* ArmStay mode tripping Shock group 17 -> ArmStay"));
        ARM_STAY();
        pgprimaryCall(171, 1771, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'Armed Stay')]");
        DISARM();
        rep.log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 6)
    public void Shock_06() throws Exception {
        rep.add_to_report("Shock_06");
        rep.log.log(LogStatus.INFO, ("*Shock_06* ArmAway mode tripping Shock group 17 -> Instant Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay + 2);
        pgprimaryCall(171, 1771, PGSensorsActivity.SHOCK);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'sensor 18')]");
        enterDefaultUserCode();
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 7)
    public void Shock_07() throws Exception {
        rep.add_to_report("Shock_07");
        rep.log.log(LogStatus.INFO, ("*Shock_07* Disarm mode tamper Shock_other group 13, 17 -> Expected result = system stays in Disarm mode"));
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Disarmed");
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: System is Disarmed"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 8)
    public void Shock_08() throws Exception {
        rep.add_to_report("Shock_08");
        rep.log.log(LogStatus.INFO, ("*Shock_08* ArmStay mode tamper Shock_other group 13 -> Instant Alarm"));
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 9)
    public void Shock_09() throws Exception {
        rep.add_to_report("Shock_09");
        rep.log.log(LogStatus.INFO, ("*Shock_09* ArmStay mode tamper Shock_other group 17 -> ArmStay"));
        ARM_STAY();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifySystemState("Armed Stay");
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'sensor 18')]");
        DISARM();
        rep.log.log(LogStatus.PASS, ("Pass: System is Armed Stay"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 10)
    public void Shock_10() throws Exception {
        rep.add_to_report("Shock_10");
        rep.log.log(LogStatus.INFO, ("*Shock_10* ArmAway mode tamper Shock_other group 13 -> Instant Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1741')]", "//*[contains(text(), 'sensor 17')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1741, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
        TimeUnit.SECONDS.sleep(3);
    }

    @Test(priority = 11)
    public void Shock_11() throws Exception {
        rep.add_to_report("Shock_11");
        rep.log.log(LogStatus.INFO, ("*Shock_11* ArmAway mode tamper Shock_other group 17 -> Instant Alarm"));
        ARM_AWAY(ConfigProps.longExitDelay);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPER);
        TimeUnit.SECONDS.sleep(2);
        verifyInAlarm();
        ADC_verification("//*[contains(text(), 'Shock 171-1771')]", "//*[contains(text(), 'sensor 18')]");
        enterDefaultUserCode();
        TimeUnit.SECONDS.sleep(2);
        pgprimaryCall(171, 1771, PGSensorsActivity.TAMPERREST);
        rep.log.log(LogStatus.PASS, ("Pass: System is in Alarm"));
    }

    @Test
    public void Shock_12() throws IOException, InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        rep.add_to_report("Shock_12");
        rep.log.log(LogStatus.INFO, ("*Shock_12* Verify on a panel sensitivity lvl is set to \"High\""));
        navigateToEditSensorPage();
        driver.scrollTo("171-1741");
        TimeUnit.SECONDS.sleep(2);
        WebElement b = driver.findElement(By.className("android.widget.LinearLayout"));
        List<WebElement> li = b.findElements(By.id("com.qolsys:id/imageView1"));
        li.get(5).click();
        TimeUnit.SECONDS.sleep(1);
        WebElement a = driver.findElement(By.id("com.qolsys:id/pgsensitivitylevel"));
        List<WebElement> li1 = a.findElements(By.id("android:id/text1"));
        Assert.assertTrue(li1.get(0).getText().equals("High"));
        TimeUnit.SECONDS.sleep(1);
        home.Home_button.click();
        rep.log.log(LogStatus.PASS, ("Pass: sensitivity lvl is set to \"High\""));
    }




    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod
    public void webDriverQuit(ITestResult result) throws IOException {
        rep.report_tear_down(result);
        adc.driver1.quit();
    }


}
