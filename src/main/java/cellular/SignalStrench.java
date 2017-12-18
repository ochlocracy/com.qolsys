package cellular;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class SignalStrench extends Setup {

    public static final int SIGNAL_STRENGTH_NONE_OR_UNKNOWN_ZERO = 0;
    public static final int GSM_SIGNAL_STRENGTH_NONE_OR_UNKNOWN_99 = 99;
    public static final int LTE_SIGNAL_STRENGTH_NONE_OR_UNKNOWN = 255;
    public static final int LTE_RSRP_SIGNAL_STRENGTH_UNKNOWN = 2147483647;
    private static final int SIGNAL_STRENGTH_LEVEL_ONE = 1;
    private static final int SIGNAL_STRENGTH_LEVEL_TWO = 2;
    private static final int SIGNAL_STRENGTH_LEVEL_THREE = 3;
    private static final int SIGNAL_STRENGTH_LEVEL_FOUR = 4;
    private static final int SIGNAL_STRENGTH_LEVEL_FIVE = 5;
    private static final String TAG = "CellSignalStrengthUtil";
    String page_name = "cellular on the About page";

    public SignalStrench() throws Exception {
    }

    /* Steven.N, Added method to convert LTE DBm to a 6 level representation */
    public static int convertDbmToLevel(int dbm) {
        // Qlog.d(TAG, "convertDbmToLevel() called dbm val : " + dbm); take data from logcat

        if (dbm == LTE_RSRP_SIGNAL_STRENGTH_UNKNOWN) {
            return SIGNAL_STRENGTH_NONE_OR_UNKNOWN_ZERO;
        } else if (dbm > -85) {
            System.out.println("This is equal to " + SIGNAL_STRENGTH_LEVEL_FIVE + "/5 Bars. The signal is excellent");
            return SIGNAL_STRENGTH_LEVEL_FIVE;
        } else if (dbm > -95) {
            System.out.println("This is equal to " + SIGNAL_STRENGTH_LEVEL_FOUR + "/5 Bars. The signal is very good");
            return SIGNAL_STRENGTH_LEVEL_FOUR;
        } else if (dbm > -105) {
            System.out.println("This is equal to " + SIGNAL_STRENGTH_LEVEL_THREE + "/5 Bars. The signal is good");
            return SIGNAL_STRENGTH_LEVEL_THREE;
        } else if (dbm > -115) {
            System.out.println("This is equal to " + SIGNAL_STRENGTH_LEVEL_TWO + "/5 Bars. The signal is very fair");
            return SIGNAL_STRENGTH_LEVEL_TWO;
        } else if (dbm <= -115) {
            System.out.println("This is equal to " + SIGNAL_STRENGTH_LEVEL_ONE + "/5 Bars. The signal is very poor");
            return SIGNAL_STRENGTH_LEVEL_ONE;
        } else {
            System.out.println("No signal info");
            return SIGNAL_STRENGTH_NONE_OR_UNKNOWN_ZERO;
        }
    }

    public static int convertGsmSignalStrengthToLevel(int gsmSignalStrength) {
        if (GSM_SIGNAL_STRENGTH_NONE_OR_UNKNOWN_99 == gsmSignalStrength) {
            return 0;
        } else if (gsmSignalStrength >= 16) {
            return 5;
        } else if (gsmSignalStrength >= 12) {
            return 4;
        } else if (gsmSignalStrength >= 8) {
            return 3;
        } else if (gsmSignalStrength >= 5) {
            return 2;
        } else if (gsmSignalStrength >= 2) {
            return 1;
        } else {
            return 0;
        }
    }

    public void swipe_vertical1() throws InterruptedException {
        int starty = 620;
        int endy = 250;
        int startx = 1000;
        driver.swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name); }

    @Test
    public void Check_all_Elements_in_Cellular_tap() throws InterruptedException {
        panel.AboutPage about = PageFactory.initElements(driver, panel.AboutPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        navigate_to_Advanced_Settings_page();
        adv.ABOUT.click();
        Thread.sleep(4000);
        about.Cellular.click();
        Thread.sleep(2000);
        List<WebElement> Cellular_Info = driver.findElements(By.id("com.qolsys:id/summary"));
        System.out.println("Carrier Name: " + Cellular_Info.get(0).getText());
        System.out.println("cellular Connection Status: " + Cellular_Info.get(1).getText());
        System.out.println("cellular Signal Strength: " + Cellular_Info.get(2).getText());
        String convertDbmToLevel = (Cellular_Info.get(2).getText()).split("\\s")[0];
        System.out.println("The value " + convertDbmToLevel + " is used for conversion");
        int dBm = Integer.parseInt(convertDbmToLevel);
//        Cellular_Info.get(dBm);
        convertDbmToLevel(dBm);
        //System.out.println(Carrier_Name.get(3).getText()); /* IMEI */
        swipe_vertical1();
        Thread.sleep(2000);
        List<WebElement> Cellular_Info1 = driver.findElements(By.id("com.qolsys:id/summary"));
        System.out.println("Baseband Version: " + Cellular_Info1.get(4).getText());
        System.out.println("Configuration Version: " + Cellular_Info1.get(5).getText());
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}