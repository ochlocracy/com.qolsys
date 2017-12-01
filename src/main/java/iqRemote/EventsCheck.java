package iqRemote;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.HomePage;
import utils.ConfigProps;

public class EventsCheck extends SetupRemote {

    EventContains EventContaints = new EventContains();
    String logcat = new String(System.getProperty("user.dir")) + "/log/test.txt";

    public EventsCheck() throws Exception {
        ConfigProps.init();
    }

    @BeforeMethod
    public void Setup() throws Exception {
        setup_driver("6NJUMAGLVK", "http://127.0.1.1", "4723");
        deleteLogFile(logcat);
        ConfigProps.init();
    }

    @Test
    public void Test1() throws Exception {
        System.out.println("Press pair button:");
        Thread.sleep(3000);
        rt.exec(ConfigProps.adbPath + " now=$(date +\"%b_%d_%Y_%k_%M\"");
        rt.exec(ConfigProps.adbPath + " shell logcat -c");
        rt.exec(ConfigProps.adbPath + " logcat|tee $now");

        driver.findElement(By.id("com.qolsys:id/pair_to_primary_button")).click();
        Thread.sleep(25000);
        eventLogsGenerating(logcat, new String[]{
                EventContaints.client_crt,
                EventContaints.ca_crt,
                EventContaints.Certificate_exchange_complete,
                EventContaints.Initialization,
                EventContaints.Attempt_to_connect,
                EventContaints.DB_sync}, 6);
        Thread.sleep(2000);
    }

    @Test
    public void Test2() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Stay - Disarm");
        for (int i = 5; i > 0; i--) {
            Thread.sleep(15000);
            home_page.DISARM.click();
            home_page.ARM_STAY.click();
            Thread.sleep(5000);
            verify_armstay();
            eventLogsGenerating(logcat, new String[]{
                    EventContains.ARM_STAY_Ui}, 1);
            Thread.sleep(10000);
            home_page.DISARM.click();
            Thread.sleep(5000);
            enter_default_user_code();
            Thread.sleep(7000);
            verify_disarm();
            eventLogsGenerating(logcat, new String[]{
                    EventContains.DISARM_event,
                    EventContains.DISARM_Ui,
                    EventContains.Exception}, 3);

            Thread.sleep(15000);
            System.out.println("Counter: " + i);
        }
    }

    @Test
    public void Test3() throws Exception {
        HomePage home_page = PageFactory.initElements(driver, HomePage.class);
        System.out.println("Arm Away - Disarm");
        for (int i = 5; i > 0; i--) {
            Thread.sleep(20000);
            home_page.DISARM.click();
            home_page.ARM_AWAY.click();
            Thread.sleep(20000);
            verify_armaway();
            home_page.ArwAway_State.click();
            Thread.sleep(4000);
            enter_default_user_code();
            Thread.sleep(10000);
            verify_disarm();
            Thread.sleep(15000);
            eventLogsGenerating(logcat, new String[]{
                    EventContains.Exception}, 1);
            System.out.println("Counter: " + i);
        }
    }

    @AfterMethod
    public void TearDown() {
        driver.quit();
    }

}
