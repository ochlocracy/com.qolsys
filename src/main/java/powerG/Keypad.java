package powerG;

import adc.ADC;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Keypad extends Setup {

    private String ArmStay = "2";
    private String ArmAway = "3";

    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("Keypad_Sensor");

    public Keypad() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void resetAlarm(String alarm) throws InterruptedException, IOException {
        adc.New_ADC_session_User( "qolsys123");
        Thread.sleep(5000);
        adc.driver1.get("https://www.alarm.com/web/system/alerts-issues");
        Thread.sleep(5000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stop " + alarm + "']"))).click();
        Thread.sleep(4000);
        adc.driver1.findElement(By.xpath("(//*[text()='Stop Alarms'])[last()]")).click();
        Thread.sleep(10000);
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
    public void Keypad_01() throws Exception {
        rep.create_report("Keypad_01");
        rep.log.log(LogStatus.INFO, ("*Keypad_01* Keypad group 0: can ArmStay-ArmAway-Disarm, panic = Police"));
        pgarmer(371, 1005, ArmStay);
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(3000);
        pgarmer(371, 1005, "01 1234");
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(3000);
        pgarmer(371, 1005, ArmAway);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        pgarmer(371, 1005, "01 1234");
        Thread.sleep(2000);
        activation_restoration(371, 1005, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr0
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Police Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Police alarm is displayed"));
            System.out.println("Pass: Police alarm is displayed");
        }catch (AssertionError e){
            System.out.println("Current alarm: " +  driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        activation_restoration(371, 1005, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr0
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Auxiliary Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Auxiliary alarm is displayed"));
            System.out.println("Pass: Auxiliary alarm is displayed");
        }catch (AssertionError e){
            System.out.println( "Current alarm: " + driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        activation_restoration(371, 1005, PGSensorsActivity.FIREPANIC, PGSensorsActivity.FIREPANICREST);//gr0
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Fire Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Fire alarm is displayed"));
            System.out.println("Pass: Fire alarm is displayed");
        }catch (AssertionError e){
            System.out.println( "Current alarm: " + driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
    }

    @Test
    public void Keypad_02() throws Exception {
        rep.add_to_report("Keypad_02");
        rep.log.log(LogStatus.INFO, ("*Keypad_02* Keypad group 1: can ArmStay-ArmAway-Disarm, panic = Police"));
        pgarmer(371, 1006, ArmStay);
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(3000);
        pgarmer(371, 1006, "01 1234");
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(3000);
        pgarmer(371, 1006, ArmAway);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        pgarmer(371, 1006, "01 1234");
        Thread.sleep(2000);
        activation_restoration(371, 1006, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Police Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Police alarm is displayed"));
            System.out.println("Pass: Police alarm is displayed");
        }catch (AssertionError e){
            System.out.println("Current alarm: " +  driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        activation_restoration(371, 1006, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr0
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Auxiliary Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Auxiliary alarm is displayed"));
            System.out.println("Pass: Auxiliary alarm is displayed");
        }catch (AssertionError e){
            System.out.println( "Current alarm: " + driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
        activation_restoration(371, 1006, PGSensorsActivity.FIREPANIC, PGSensorsActivity.FIREPANICREST);//gr0
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Fire Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Fire alarm is displayed"));
            System.out.println("Pass: Fire alarm is displayed");
        }catch (AssertionError e){
            System.out.println( "Current alarm: " + driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
    }

    @Test
    public void Keypad_03() throws Exception {
        rep.add_to_report("Keypad_03");
        rep.log.log(LogStatus.INFO, ("*Keypad_03* Keypad group 2: can ArmStay-ArmAway-Disarm, panic = Police Silent"));
        pgarmer(371, 1008, ArmStay);
        Thread.sleep(2000);
        verifyArmstay();
        Thread.sleep(3000);
        pgarmer(371, 1008, "01 1234");
        Thread.sleep(2000);
        verifyDisarm();
        Thread.sleep(3000);
        pgarmer(371, 1008, ArmAway);
        Thread.sleep(2000);
        verifyArmaway();
        Thread.sleep(3000);
        pgarmer(371, 1008, "01 1234");
        Thread.sleep(2000);
        activation_restoration(371, 1008, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);
        Thread.sleep(5000);
        try {
            verifyDisarm();
            rep.log.log(LogStatus.PASS, ("Pass: Silent Police alarm is send, system is displayed as Disarmed"));
            System.out.println("Pass: Silent Police alarm is sent");
        }catch (AssertionError e){
            verifyInAlarm();
            rep.log.log(LogStatus.FAIL, ("Fail: alarm is displayed"));
            System.out.println("Fail: alarm is displayed");
        }
        Thread.sleep(5000);
        resetAlarm("Alarm");
        Thread.sleep(3000);

        activation_restoration(371, 1008, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Auxiliary Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Auxiliary alarm is displayed"));
            System.out.println("Pass: Auxiliary alarm is displayed");
        }catch (AssertionError e){
            System.out.println( "Current alarm: " + driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();

        Thread.sleep(2000);
        activation_restoration(371, 1008, PGSensorsActivity.FIREPANIC, PGSensorsActivity.FIREPANICREST);//gr0
        verifyInAlarm();
        try {
            Assert.assertEquals(driver.findElement(By.id("com.qolsys:id/tv_status")).getText(), "Fire Emergency");
            rep.log.log(LogStatus.PASS, ("Pass: Fire alarm is displayed"));
            System.out.println("Pass: Fire alarm is displayed");
        }catch (AssertionError e){
            System.out.println( "Current alarm: " + driver.findElement(By.id("com.qolsys:id/tv_status")).getText());
            driver.findElement(By.id("com.qolsys:id/tv_status")).getText();
            rep.log.log(LogStatus.FAIL, ("Fail: wrong alarm is displayed"));
            System.out.println("Fail: wrong alarm is displayed");
        }
        enterDefaultUserCode();
        Thread.sleep(2000);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
        service.stop();
    }

    @AfterMethod(alwaysRun = true)
    public void webDriverQuit(ITestResult result) throws IOException {
        rep.report_tear_down(result);
        adc.driver1.quit();
    }
}
