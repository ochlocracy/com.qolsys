package powerG;

import adc.ADC;

import adc.UIRepo;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import panel.PanelInfo_ServiceCalls;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.PGSensorsActivity;
import utils.Setup;
import com.relevantcodes.extentreports.LogStatus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ArmAway extends Setup{

    ADC adc = new ADC();
    ExtentReport rep = new ExtentReport("ArmAway");
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public ArmAway() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    public void usersite_alarm_disarm(String login, String password) throws InterruptedException {
        adc.New_ADC_session_User(login, password);
        Thread.sleep(5000);
        adc.driver1.get("https://www.alarm.com/web/system/alerts-issues");
        Thread.sleep(7000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text()='Stop Alarm']"))).click();
        Thread.sleep(4000);
        adc.driver1.findElement(By.xpath("(//*[text()='Stop Alarms'])[last()]")).click();
        Thread.sleep(10000);
    }
    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        servcall.set_NORMAL_ENTRY_DELAY(ConfigProps.normalEntryDelay);
        Thread.sleep(1000);
        servcall.set_NORMAL_EXIT_DELAY(ConfigProps.normalExitDelay);
        Thread.sleep(1000);
        servcall.set_LONG_ENTRY_DELAY(ConfigProps.longEntryDelay);
        Thread.sleep(1000);
        servcall.set_LONG_EXIT_DELAY(ConfigProps.longExitDelay);
        servcall.set_AUTO_STAY(0);
        servcall.set_ARM_STAY_NO_DELAY_disable();
    }
    @BeforeMethod
    public void webDriver() throws Exception {
        adc.webDriverSetUp();
    }
    @Test
    public void AA_01() throws Exception {
        UIRepo ui = PageFactory.initElements(adc.driver1, UIRepo.class);
        rep.create_report( "AA_01");
        rep.log.log(LogStatus.INFO, ("*Away_01* Verify the system can be disarmed from the ADC"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        Thread.sleep(10000);
        ui.Arm_Away_state.click();
        ui.Disarm.click();
        Thread.sleep(10000);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed from ADC user website"));
    }
    @Test
    public void AA_03() throws Exception {
        rep.add_to_report("AA_03");
        rep.log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW10"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_04() throws Exception {
        rep.add_to_report("AA_04");
        rep.log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW12"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_05() throws Exception {
        rep.add_to_report("AA_05");
        rep.log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay using a Guest code DW12"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterGuestCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_06() throws Exception {
        rep.add_to_report("AA_06");
        rep.log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW10, DW12"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_07() throws Exception {
        rep.add_to_report("AA_07");
        rep.log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay DW10, Motion15"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(120, 1411, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_08() throws Exception {
        rep.add_to_report("AA_08");
        rep.log.log(LogStatus.INFO, ("*Verify the system can be disarmed during the entry delay Motion20"));
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(122, 1423, PGSensorsActivity.MOTIONACTIVE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_09() throws Exception {
        rep.add_to_report("AA_09");
        rep.log.log(LogStatus.INFO, ("*Verify the system will pretend to disarm if a valid duress code is used and a duress alarm will be sent to ADC, simulating the user being forced to disarm the system."));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        enterDefaultDuressCode();
        verifyDisarm();
        usersite_alarm_disarm(ConfigProps.login, ConfigProps.password);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_10() throws Exception {
        rep.add_to_report("AA_10");
        rep.log.log(LogStatus.INFO, ("*Verify the system will pretend to disarm if a valid duress code is used and a duress alarm will be sent to ADC, simulating the user being forced to disarm the system."));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        enterDefaultDuressCode();
        verifyDisarm();
        usersite_alarm_disarm(ConfigProps.login, ConfigProps.password);
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_13() throws Exception {
        rep.add_to_report("AA_13");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 10 is opened in Arm Away*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_14() throws Exception {
        rep.add_to_report("AA_14");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 12 is opened in Arm Away*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_15() throws Exception {
        rep.add_to_report("AA_15");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm if a sensor in group 13 is opened in Arm Away*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_16() throws Exception {
        rep.add_to_report("AA_16");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm if a sensor in group 14 is opened in Arm Away*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_17() throws Exception {
        rep.add_to_report("AA_17");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm if a sensor in group 16 is opened in Arm Away*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_18() throws Exception {
        rep.add_to_report("AA_18");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into alarm at the end of the entry delay if a sensor in group 35 is Activated in Arm Away*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay +2);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_19() throws Exception {
        rep.add_to_report("AA_19");
        rep.log.log(LogStatus.INFO, ("*Verify the system goes into immediate pending alarm and then alarm after dialer delay*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1441, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(32);
        verifyInAlarm();
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_20() throws Exception {
        rep.add_to_report("AA_20");
        rep.log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_21() throws Exception {
        rep.add_to_report("AA_21");
        rep.log.log(LogStatus.INFO, ("*Verify the system will go into immediate alarm when a group 13 sensor is opened and will report alarm on both sensors *"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_22() throws Exception {
        rep.add_to_report("AA_22");
        rep.log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_23() throws Exception {
        rep.add_to_report("AA_23");
        rep.log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INOPEN);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.INCLOSE);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_24() throws Exception {
        rep.add_to_report("AA_24");
        rep.log.log(LogStatus.INFO, ("*Verify the system will report alarm on both sensors at the end of the entry delay*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.INOPEN);
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.MOTIONACTIVE);
        TimeUnit.SECONDS.sleep(ConfigProps.longEntryDelay);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.INCLOSE);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_31() throws Exception {
        rep.add_to_report("AA_31");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group8*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1127, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1127, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_32() throws Exception {
        rep.add_to_report("AA_32");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group9*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1123, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1123, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_33() throws Exception {
        rep.add_to_report("AA_33");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group10*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1101, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_34() throws Exception {
        rep.add_to_report("AA_34");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group12*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1152, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_35() throws Exception {
        rep.add_to_report("AA_35");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group13*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1231, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_36() throws Exception {
        rep.add_to_report("AA_36");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group14*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1216, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_37() throws Exception {
        rep.add_to_report("AA_37");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group16*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        pgprimaryCall(104, 1331, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_38() throws Exception {
        rep.add_to_report("AA_38");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group15*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(120, 1411, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_39() throws Exception {
        rep.add_to_report("AA_39");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group17*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(123, 1441, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_40() throws Exception {
        rep.add_to_report("AA_40");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group20*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(122, 1423, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_41() throws Exception {
        rep.add_to_report("AA_41");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group35*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(123, 1446, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_42() throws Exception {
        rep.add_to_report("AA_42");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group26*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(201, 1541, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(201, 1541, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
    }
    @Test
    public void AA_43() throws Exception {
        rep.add_to_report("AA_43");
        rep.log.log(LogStatus.INFO, ("*Verify the panel will report an immediate tamper alarm group34*"));
        Thread.sleep(2000);
        ARM_AWAY(ConfigProps.longExitDelay);
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPER);
        Thread.sleep(2000);
        verifyInAlarm();
        Thread.sleep(2000);
        pgprimaryCall(220, 1661, PGSensorsActivity.TAMPERREST);
        Thread.sleep(2000);
        enterDefaultUserCode();
        verifyDisarm();
        rep.log.log(LogStatus.PASS, ("System is Disarmed"));
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
