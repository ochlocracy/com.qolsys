package zwave;

import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

/* Pair 1 thermostat prior to testing, set the Mode to OFF */

public class Thermostat_Test extends Setup {

    ArrayList <WebElement> elem = new ArrayList<>();

    String page_name = "Thermostat_Testing";
    Logger logger = Logger.getLogger(page_name);

    public Thermostat_Test() throws Exception {}

    public String method (String str) {
        return str.split("°")[0];
    } /**/

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void Check_elements_on_page() throws Exception {
        Thermostat_Page therm = PageFactory.initElements(driver, Thermostat_Page.class);

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
        Thermostat_Page therm = PageFactory.initElements(driver, Thermostat_Page.class);

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
    



    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}