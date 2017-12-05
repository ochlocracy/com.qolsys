package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.DevicesPage;
import panel.HomePage;
import panel.InstallationPage;
import utils.Setup;

import java.io.IOException;
import java.util.ArrayList;

/* Pair 1 thermostat prior to testing, set the Mode to OFF */

public class ThermostatTest extends Setup {

    ArrayList<WebElement> elem = new ArrayList<>();

    String page_name = "Thermostat_Testing";
    Logger logger = Logger.getLogger(page_name);

    public ThermostatTest() throws Exception {
    }

    public String method(String str) {
        return str.split("°")[0];
    } /**/

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_elements_on_page() throws Exception {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);

        swipeFromRighttoLeft();
        element_verification(therm.Target_Temp, "Target temperature");
        element_verification(therm.Temp_Up, "Set target temperature Up");
        element_verification(therm.Temp_Down, "Set target temperature Down");
        element_verification(therm.Thermostat_Name, "Thermostat name");
        element_verification(therm.Current_Mode, "Current Mode");
        element_verification(therm.Fan_Mode, "Fan Mode");
        element_verification(therm.Set_Mode, "Set thermostat mode");
        element_verification(therm.Therm_Battery, "Thermostat battery");
        element_verification(therm.Current_Temp, "Current temperature");
        element_verification(therm.Current_Temp_Txt, "Current temperature text");

        therm.Fan_Mode.click();
        Thread.sleep(2000);
        element_verification(therm.Fan_On_Icon, "Fan On icon");
        element_verification(therm.Fan_On_Txt, "Fan On text");
        element_verification(therm.Fan_On_Message, "Fan On Message");
        element_verification(therm.Fan_Auto_Icon, "Fan Auto icon");
        element_verification(therm.Fan_Auto_Txt, "Fan Auto text");
        element_verification(therm.Fan_Auto_Message, "Fan Auto message");
        tap(1111, 405);

        therm.Set_Mode.click();
        Thread.sleep(2000);
        element_verification(therm.Off_Mode_Icon, "Off Mode icon");
        element_verification(therm.Off_Mode_Txt, "Off Mode text");
        element_verification(therm.Off_Mode_Message, "Off Mode message");
        element_verification(therm.Heat_Icon, "Heat Mode icon");
        element_verification(therm.Heat_Mode_Txt, "Heat Mode text");
        element_verification(therm.Heat_Mode_Message, "Heat Mode message");
        element_verification(therm.Cool_Icon, "Cool Mode icon");
        element_verification(therm.Cool_Mode_Txt, "Cool Mode text");
        element_verification(therm.Cool_Mode_Message, "Cool Mode message");
        element_verification(therm.Auto_Icon, "Auto Mode icon");
        element_verification(therm.Auto_Mode_Txt, "Auto Mode text");
        element_verification(therm.Auto_Mode_Message, "Auto Mode message");
        swipeFromLefttoRight();
        Thread.sleep(2000);
    }

    @Test
    public void Thermostat_test() throws Exception {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        swipeFromRighttoLeft();
        swipeFromRighttoLeft();
        Thread.sleep(2000);
        therm.Set_Mode.click();
        Thread.sleep(4000);
        therm.Heat_Icon.click();
        Thread.sleep(7000);
        if (therm.Target_Temp.getText().equals("OFF")) {
            System.out.println("Failed: Thermostat mode is not HEAT");
        }

        if (therm.Current_Mode.getText().equals("HEAT")) {
            System.out.println("Pass: Mode successfully changed to HEAT");
        } else {
            System.out.println("Failed: Mode is not set to HEAT");
        }

        String target_temp_up = therm.Target_Temp.getText();
        Integer target_temp_up_int = Integer.valueOf(method(target_temp_up));
        System.out.println("Target temperature is " + target_temp_up_int);

        therm.Temp_Up.click();
        therm.Temp_Up.click();
        Thread.sleep(4000);

        String new_target_temp_up = therm.Target_Temp.getText();
        Integer new_target_temp_up_int = Integer.valueOf(method(new_target_temp_up));
        System.out.println("New target temperature is " + new_target_temp_up_int);

        if (new_target_temp_up_int == (target_temp_up_int + 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);

        String target_temp_down = therm.Target_Temp.getText();
        Integer target_temp_down_int = Integer.valueOf(method(target_temp_down));
        System.out.println("Target temperature is " + target_temp_down_int);

        therm.Temp_Down.click();
        therm.Temp_Down.click();
        Thread.sleep(4000);

        String new_target_temp_down = therm.Target_Temp.getText();
        Integer new_target_temp_down_int = Integer.valueOf(method(new_target_temp_down));
        System.out.println("New target temperature is " + new_target_temp_down_int);

        if (new_target_temp_down_int == (target_temp_down_int - 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);
        therm.Set_Mode.click();
        Thread.sleep(3000);
        therm.Off_Mode_Icon.click();
        Thread.sleep(7000);

        /** COOL MODE **/

        therm.Set_Mode.click();
        Thread.sleep(4000);
        therm.Cool_Icon.click();
        Thread.sleep(5000);
        if (therm.Target_Temp.getText().equals("OFF")) {
            System.out.println("Failed: Thermostat mode is not COOL");
        }

        if (therm.Current_Mode.getText().equals("COOL")) {
            System.out.println("Pass: Mode successfully changed to COOL");
        } else {
            System.out.println("Failed: Mode is not set to COOL");
        }

        String cool_target_temp_up = therm.Target_Temp.getText();
        Integer cool_target_temp_up_int = Integer.valueOf(method(cool_target_temp_up));
        System.out.println("Target temperature is " + cool_target_temp_up_int);

        therm.Temp_Up.click();
        therm.Temp_Up.click();
        Thread.sleep(4000);

        String new_cool_target_temp_up = therm.Target_Temp.getText();
        Integer new_cool_target_temp_up_int = Integer.valueOf(method(new_cool_target_temp_up));
        System.out.println("New target temperature is " + new_cool_target_temp_up_int);

        if (new_target_temp_up_int == (target_temp_up_int + 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);

        String cool_target_temp_down = therm.Target_Temp.getText();
        Integer cool_target_temp_down_int = Integer.valueOf(method(cool_target_temp_down));
        System.out.println("Target temperature is " + cool_target_temp_down_int);

        therm.Temp_Down.click();
        therm.Temp_Down.click();
        Thread.sleep(4000);

        String new_cool_target_temp_down = therm.Target_Temp.getText();
        Integer new_cool_target_temp_down_int = Integer.valueOf(method(new_cool_target_temp_down));
        System.out.println("New target temperature is " + new_cool_target_temp_down_int);

        if (new_target_temp_down_int == (target_temp_down_int - 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);
        therm.Set_Mode.click();
        Thread.sleep(3000);
        therm.Off_Mode_Icon.click();
        Thread.sleep(7000);

        /** AUTO MODE **/

        therm.Set_Mode.click();
        Thread.sleep(4000);
        therm.Auto_Icon.click();
        Thread.sleep(5000);
        if (therm.Target_Temp.getText().equals("OFF")) {
            System.out.println("Failed: Thermostat mode is not AUTO");
        }

        if (therm.Current_Mode.getText().equals("AUTO")) {
            System.out.println("Pass: Mode successfully changed to AUTO");
        } else {
            System.out.println("Failed: Mode is not set to AUTO");
        }

        if (therm.Target_Temp.getText().equals("N/A")) {
            System.out.println("Pass: Current temp is displayed as N/A");
        }

        Thread.sleep(5000);
        therm.Set_Mode.click();
        Thread.sleep(3000);
        therm.Off_Mode_Icon.click();
        Thread.sleep(7000);
    }

    public void Z_Wave_Thermostat_Disarm_Mode(String UDID_) throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        logger.info("*************************ZT_D_001*******************************");
        logger.info("Disarm mode: Verify that a Thermostat can be paired with a Panel");

        logger.info("*************************ZT_D_003*******************************");
        logger.info("Disarm mode: Verify that multiple Thermostats can be paired with a Panel");

        logger.info("*************************ZT_D_004*******************************");
        logger.info("Disarm mode: Verify that you can pair max of Thermostats according to the set current number");

        logger.info("*************************ZT_D_002*******************************");
        logger.info("Disarm mode: Verify that a new Thermostat will appear on the ADC websites");

        logger.info("*************************ZT_D_012*******************************");
        logger.info("Disarm mode: Verify that changing the Mode on a Panel the Mode on the Thermostat will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_D_013*******************************");
        logger.info("Disarm mode: Verify that changing the Mode on a Thermostat the Mode on the Panel will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_D_017*******************************");
        logger.info("Disarm mode: Verify that changing the target temperature on a Panel the target temperature on the Thermostat will match");

        logger.info("*************************ZT_D_016*******************************");
        logger.info("Disarm mode: Verify that changing the target temperature on a Thermostat the target temperature on the Panel will match. (If applicable)");

        logger.info("*************************ZT_D_019*******************************");
        logger.info("Disarm mode: Verify that changing the Fan Mode on a Thermostat, a panel will reflect the changes");

        logger.info("*************************ZT_D_011*******************************");
        logger.info("Disarm mode: Verify that a Panel correctly reflects the temperature from the Thermostat");

        logger.info("*************************ZT_D_021*******************************");
        logger.info("Disarm mode: Verify that the battery status is displayed on the Thermostats page");

        logger.info("*************************ZT_D_022*******************************");
        logger.info("Disarm mode: Verify that when the battery is low alert message will appear");

        logger.info("*************************ZT_D_015*******************************");
        logger.info("Disarm mode: Verify that changing the Mode on the ADC user website, the Mode on the Panel/Thermostat will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_D_018*******************************");
        logger.info("Disarm mode: Verify that the ADC user website reflects the Target Temperature changes correctly");

        logger.info("*************************ZT_D_014*******************************");
        logger.info("Disarm mode: Verify that the ADC user website reflects the Mode change correctly");

        logger.info("*************************ZT_D_027*******************************");
        logger.info("Disarm mode: Verify that Stelpro STZW402+ has only Heat Mode on ADC");

        logger.info("*************************ZT_D_010*******************************");
        logger.info("Disarm mode: Verify that a thermostat can be deleted from the ADC website");

        logger.info("*************************ZT_D_007*******************************");
        logger.info("Disarm mode: Verify that when the device is unreachable a Panel recognizes the status correctly");

        logger.info("*************************ZT_D_028*******************************");
        logger.info("Disarm mode: Verify that panel does not query Thermostat Mode every 15 seconds after thermostat is unreachable");

        logger.info("*************************ZT_D_026*******************************");
        logger.info("Disarm mode: Verify that IQ Remote does not lose sync when changing the Temperature Units on primary panel");

        logger.info("*************************ZT_D_005*******************************");
        logger.info("Disarm mode: Verify that you can edit a device name (panel)");

        logger.info("*************************ZT_D_006*******************************");
        logger.info("Disarm mode: Verify that you can edit a device name (ADC)");

        logger.info("*************************ZT_D_020*******************************");
        logger.info("Disarm mode: Verify that changing the Weather Temperature, panel will display the temperature according to the changes");

        logger.info("*************************ZT_D_025*******************************");
        logger.info("Disarm mode: Verify that lights work after including a thermostat");

        logger.info("*************************ZT_D_008*******************************");
        logger.info("Disarm mode: Verify that the history events are displayed on the Z-Wave device Status page");

        logger.info("*************************ZT_D_009*******************************");
        logger.info("Disarm mode: Verify that a Thermostat can be deleted from a Panel");
    }

    public void Z_Wave_Thermostat_Arm_Stay_Mode(String UDID_) throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        logger.info("*************************ZT_AS_006*******************************");
        logger.info("Arm Stay mode: Verify that changing the Mode on a Thermostat the Mode on the Panel will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_AS_010*******************************");
        logger.info("Arm Stay mode: Verify that changing the target temperature on a Panel the target temperature on the Thermostat will match");

        logger.info("*************************ZT_AS_005*******************************");
        logger.info("Arm Stay mode: Verify that changing the Mode on a Panel the Mode on the Thermostat will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_AS_009*******************************");
        logger.info("Arm Stay mode: Verify that changing the target temperature on a Thermostat the target temperature on the Panel will match. (If applicable)");

        logger.info("*************************ZT_AS_012*******************************");
        logger.info("Arm Stay mode: Verify that changing the Fan Mode on a Thermostat, a panel will reflect the changes");

        logger.info("*************************ZT_AS_011*******************************");
        logger.info("Arm Stay mode: Verify that the ADC user website reflects the Target Temperature changes correctly");

        logger.info("*************************ZT_AS_007*******************************");
        logger.info("Arm Stay mode: Verify that the ADC user website reflects the Mode change correctly");

        logger.info("*************************ZT_AS_008*******************************");
        logger.info("Arm Stay mode: Verify that changing the Mode on the ADC user website, the Mode on the Panel/Thermostat will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_AS_013*******************************");
        logger.info("Arm Stay mode: Verify that changing the Weather Temperature, panel will display the temperature according to the changes");

        logger.info("*************************ZT_AS_014*******************************");
        logger.info("Arm Stay mode: Verify that the battery status is displayed on the Thermostats page");

        logger.info("*************************ZT_AS_015*******************************");
        logger.info("Arm Stay mode: Verify that when the battery is low alert message will appear");

        logger.info("*************************ZT_AS_002*******************************");
        logger.info("Arm Stay mode: Verify that when the device is unreachable a Panel recognizes the status correctly");

        logger.info("*************************ZT_AS_001*******************************");
        logger.info("Arm Stay mode: Verify that you can edit a device name (ADC)");

        logger.info("*************************ZT_AS_003*******************************");
        logger.info("Arm Stay mode: Verify that the history events are displayed on the Z-Wave device Status page");

        logger.info("*************************ZT_AS_004*******************************");
        logger.info("Arm Stay mode: Verify that a thermostat can be deleted from the ADC website");

    }

    public void Z_Wave_Thermostat_Arm_Away_Mode(String UDID_) throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        logger.info("*************************ZT_AW_003*******************************");
        logger.info("Arm Away mode: Verify that changing the Mode on a Thermostat the Mode on the Panel will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_AW_004*******************************");
        logger.info("Arm Away mode: Verify that the ADC user website reflects the Mode change correctly");

        logger.info("*************************ZT_AW_005*******************************");
        logger.info("Arm Away mode: Verify that changing the Mode on the ADC user website, the Mode on the Panel/Thermostat will match (OFF/Heat/Cool/Auto)");

        logger.info("*************************ZT_AW_007*******************************");
        logger.info("Arm Away mode: Verify that changing the target temperature on a Panel the target temperature on the Thermostat will match");

        logger.info("*************************ZT_AW_006*******************************");
        logger.info("Arm Away mode: Verify that changing the target temperature on a Thermostat the target temperature on the Panel will match. (If applicable)");

        logger.info("*************************ZT_AW_008*******************************");
        logger.info("Arm Away mode: Verify that the ADC user website reflects the Target Temperature changes correctly");

        logger.info("*************************ZT_AW_009*******************************");
        logger.info("Arm Away mode: Verify that changing the Fan Mode on a Thermostat, a panel will reflect the changes");

        logger.info("*************************ZT_AW_010*******************************");
        logger.info("Arm Away mode: Verify that changing the Weather Temperature, panel will display the temperature according to the changes");

        logger.info("*************************ZT_AW_001*******************************");
        logger.info("Arm Away mode: Verify that you can edit a device name (ADC)");

        logger.info("*************************ZT_AW_002*******************************");
        logger.info("Arm Away mode: Verify that a thermostat can be deleted from the ADC website");

    }

    public void Z_Wave_Thermostat_Schedules(String UDID_) throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        InstallationPage instal = PageFactory.initElements(driver, InstallationPage.class);
        DevicesPage dev = PageFactory.initElements(driver, DevicesPage.class);
        ZWavePage zwave = PageFactory.initElements(driver, ZWavePage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);

        logger.info("*************************ZT_D_023*******************************");
        logger.info("Disarm mode: Verify that temperature schedule can be created and panel/thermostat are following this schedule");

        logger.info("*************************ZT_D_024*******************************");
        logger.info("Disarm mode: Verify that away from home thermostat override mode works as expected");

        logger.info("*************************ZT_AS_016*******************************");
        logger.info("Arm Stay mode: Verify that temperature schedule can be created and panel/thermostat are following this schedule");

        logger.info("*************************ZT_AS_017*******************************");
        logger.info("Arm Stay mode: Verify that away from home thermostat override mode works as expected");

        logger.info("*************************ZT_AW_011*******************************");
        logger.info("Arm Away mode: Verify that temperature schedule can be created and panel/thermostat are following this schedule");

        logger.info("*************************ZT_AW_012*******************************");
        logger.info("Arm Away mode: Verify that away from home thermostat override mode works as expected");
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}