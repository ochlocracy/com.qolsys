package zwave;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.AdvancedSettingsPage;
import panel.DevicesPage;
import panel.HomePage;
import panel.InstallationPage;
import utils.Setup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/* Pair 1 thermostat prior to testing, set the Mode to OFF */

public class ThermostatTest extends Setup {
    ThermostatPage thermPage = new ThermostatPage();
    ArrayList<WebElement> elem = new ArrayList<>();
    HashMap<String, String> thermostatDiv;
    String page_name = "Thermostat_Testing";
    Logger logger = Logger.getLogger(page_name);
    public String thermostat1 = "Thermostat 1";
    public String thermostat2 = "Thermostat 2";
    public String thermostat3 = "Thermostat 3";
    public String thermostat4 = "Thermostat 4";
    public String thermostat5 = "Thermostat 5";
    public String thermostat6 = "Thermostat 6";

    public ThermostatTest() throws Exception {
        thermostatDiv = new HashMap<>();
        thermostatDiv.put(thermostat1, "1");
        thermostatDiv.put(thermostat2, "2");
        thermostatDiv.put(thermostat3, "3");
        thermostatDiv.put(thermostat4, "4");
        thermostatDiv.put(thermostat5, "5");
        thermostatDiv.put(thermostat6, "6");
    }
// add a way to select panel only test, panel and user site test, and transmitter or real devices


//************************************User Site Methods************************************************************************************

    public String getThermDiv(String thermName){
        return thermostatDiv.get(thermName);
    }
    public void setThermModeUserSite(String thermName, String thermMode) {
        String thermDiv = getThermDiv(thermName);
        System.out.println("Changing Thermostat Mode");
        driver.findElement(By.xpath("//div[@id='app-content']/div/div["+ thermDiv + "]/div/div//div[@class='btn-group']/button[2]")).click();
        System.out.println("Selecting " + thermMode + " Mode");
        driver.findElement(By.xpath(thermMode)).click();
        System.out.println(thermMode + " Has Been Selected");
        System.out.println(thermMode + " Mode Verified");
    }

    public void getThermSetTempUserSiteText(String thermName){
        String thermDiv = getThermDiv(thermName);
        System.out.println(thermName + "'s Div is "+ thermDiv);
        System.out.println("Getting current set Temp for " + thermName);
        String currentTemp = driver.findElement(By.xpath("//div[@id='app-content']/div["+ thermDiv+ "]/div[1]/div/div/div[@class='row temps']/div[1]")).getText();
        System.out.println(thermName +"'s new current thermostat temp is " + currentTemp);
        System.out.println("");
    }
    public void userThermTempUp(String thermName, int x) throws InterruptedException{
        String thermDiv = getThermDiv(thermName);
        System.out.println("Going to temp selection");
        getThermSetTempUserSiteText(thermName);
        System.out.println("Selecting Temp");
        driver.findElement(By.xpath("//div[@id='app-content']/div/div["+ thermDiv + "]/div/div//div[@class='btn-group']/button[1]")).click();
        System.out.println("changing temp down " + x + " degrees");
        for (int i=0; i<x; i++){
            driver.findElement(By.xpath("//div[@id='app-content']/div["+ thermDiv +"]/div[1]/div/div/div[@class='row temps']/div[1]/div[2]/button[2]/div")).click();
        }
        Thread.sleep(10000);
        getThermSetTempUserSiteText(thermName);
    }

    public void userThermTempDown(String thermName, int x) throws InterruptedException{
        String thermDiv = getThermDiv(thermName);
        System.out.println("Going to temp selection");
        getThermSetTempUserSiteText(thermName);
        System.out.println("Selecting Temp");
        driver.findElement(By.xpath("//div[@id='app-content']/div/div["+ thermDiv + "]/div/div//div[@class='btn-group']/button[1]")).click();
        System.out.println("changing temp down " + x + " degrees");
        for (int i=0; i<x; i++){
            driver.findElement(By.xpath("//div[@id='app-content']/div["+ thermDiv +"]/div[1]/div/div/div[@class='row temps']/div[1]/div[2]/button[1]/div")).click();
        }
        Thread.sleep(10000);
        getThermSetTempUserSiteText(thermName);
    }

    public void getCurrentRoomTempUserSite(String thermName){
        String thermDiv = getThermDiv(thermName);
        System.out.println("Getting current room temp for " + thermName);
        String currentTemp = driver.findElement(By.xpath("//div[@id='app-content']/div["+ thermDiv +"]/div[1]/div/div//p[@class='temp']")).getText();
        System.out.println(thermName +"'s current room thermostat temp is " + currentTemp);
        System.out.println("");
    }

//*************************************************************************************************************************************************************
    public String method(String str) {
        return str.split("°")[0];
    } /**/

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    public void swipeToThermostatPage(ThermostatPage therm) throws Exception {
        while (!isOnThermostatPage(therm)){
            swipeFromRighttoLeft();
        }
    }
    private boolean isOnThermostatPage (ThermostatPage therm) {
        try {
            System.out.println("Checking For Thermostat Page ");
            return therm.Current_Temp_Txt.isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Handling NoSuchElementException ");
            return false;
        }
    }

    @Test
    public void PracticeTest(){

    }
    @Test
    public void Check_elements_on_page() throws Exception {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        swipeToThermostatPage(therm);
        elementVerification(therm.targetTemp, "Target temperature");
        elementVerification(therm.tempUp, "Set target temperature Up");
        elementVerification(therm.tempDown, "Set target temperature Down");
        elementVerification(therm.Thermostat_Name, "Thermostat name");
        elementVerification(therm.currentMode, "Current Mode");
        elementVerification(therm.Fan_Mode, "Fan Mode");
        elementVerification(therm.setFanMode, "Set thermostat mode");
        elementVerification(therm.Therm_Battery, "Thermostat battery");
        elementVerification(therm.Current_Temp, "Current temperature");
        elementVerification(therm.Current_Temp_Txt, "Current temperature text");
//
//        therm.Fan_Mode.click();
//        Thread.sleep(2000);
//        elementVerification(therm.Fan_On_Icon, "Fan On icon");
//        elementVerification(therm.Fan_On_Txt, "Fan On text");
//        elementVerification(therm.Fan_On_Message, "Fan On Message");
//        elementVerification(therm.Fan_Auto_Icon, "Fan Auto icon");
//        elementVerification(therm.Fan_Auto_Txt, "Fan Auto text");
//        elementVerification(therm.Fan_Auto_Message, "Fan Auto message");
//        tap(1111, 405);

        therm.setFanMode.click();
        Thread.sleep(2000);
        elementVerification(therm.offModeIcon, "Off Mode icon");
        elementVerification(therm.offModeTxt, "Off Mode text");
        elementVerification(therm.offModeMessage, "Off Mode message");
        elementVerification(therm.heatMode, "Heat Mode icon");
        elementVerification(therm.Heat_Mode_Txt, "Heat Mode text");
        elementVerification(therm.Heat_Mode_Message, "Heat Mode message");
        elementVerification(therm.coolModeSelection, "Cool Mode icon");
        elementVerification(therm.coolModeTxt, "Cool Mode text");
        elementVerification(therm.Cool_Mode_Message, "Cool Mode message");
        elementVerification(therm.Auto_Icon, "Auto Mode icon");
        elementVerification(therm.Auto_Mode_Txt, "Auto Mode text");
        elementVerification(therm.Auto_Mode_Message, "Auto Mode message");
        swipeFromLefttoRight();
        Thread.sleep(2000);
    }
    @BeforeMethod
    public void onThermostatPage() throws Exception{
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        swipeToThermostatPage(therm);

    }
    @Test
    public void Thermostat_test() throws Exception {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        swipeToThermostatPage(therm);
        Thread.sleep(2000);
        therm.setFanMode.click();
        Thread.sleep(4000);
        therm.heatMode.click();
        Thread.sleep(7000);
        if (therm.targetTemp.getText().equals("OFF")) {
            System.out.println("Failed: Thermostat mode is not HEAT");
        }

        if (therm.currentMode.getText().equals("HEAT")) {
            System.out.println("Pass: Mode successfully changed to HEAT");
        } else {
            System.out.println("Failed: Mode is not set to HEAT");
        }

        String target_temp_up = therm.targetTemp.getText();
        Integer target_temp_up_int = Integer.valueOf(method(target_temp_up));
        System.out.println("Target temperature is " + target_temp_up_int);

        therm.tempUp.click();
        therm.tempUp.click();
        Thread.sleep(4000);

        String new_target_temp_up = therm.targetTemp.getText();
        Integer new_target_temp_up_int = Integer.valueOf(method(new_target_temp_up));
        System.out.println("New target temperature is " + new_target_temp_up_int);

        if (new_target_temp_up_int == (target_temp_up_int + 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);

        String target_temp_down = therm.targetTemp.getText();
        Integer target_temp_down_int = Integer.valueOf(method(target_temp_down));
        System.out.println("Target temperature is " + target_temp_down_int);

        therm.tempDown.click();
        therm.tempDown.click();
        Thread.sleep(4000);

        String new_target_temp_down = therm.targetTemp.getText();
        Integer new_target_temp_down_int = Integer.valueOf(method(new_target_temp_down));
        System.out.println("New target temperature is " + new_target_temp_down_int);

        if (new_target_temp_down_int == (target_temp_down_int - 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);
        therm.setFanMode.click();
        Thread.sleep(3000);
        therm.offModeIcon.click();
        Thread.sleep(7000);

        /** COOL MODE **/

        therm.setFanMode.click();
        Thread.sleep(4000);
        therm.coolModeSelection.click();
        Thread.sleep(5000);
        if (therm.targetTemp.getText().equals("OFF")) {
            System.out.println("Failed: Thermostat mode is not COOL");
        }

        if (therm.currentMode.getText().equals("COOL")) {
            System.out.println("Pass: Mode successfully changed to COOL");
        } else {
            System.out.println("Failed: Mode is not set to COOL");
        }

        String cool_target_temp_up = therm.targetTemp.getText();
        Integer cool_target_temp_up_int = Integer.valueOf(method(cool_target_temp_up));
        System.out.println("Target temperature is " + cool_target_temp_up_int);

        therm.tempUp.click();
        therm.tempUp.click();
        Thread.sleep(4000);

        String new_cool_target_temp_up = therm.targetTemp.getText();
        Integer new_cool_target_temp_up_int = Integer.valueOf(method(new_cool_target_temp_up));
        System.out.println("New target temperature is " + new_cool_target_temp_up_int);

        if (new_target_temp_up_int == (target_temp_up_int + 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);

        String cool_target_temp_down = therm.targetTemp.getText();
        Integer cool_target_temp_down_int = Integer.valueOf(method(cool_target_temp_down));
        System.out.println("Target temperature is " + cool_target_temp_down_int);

        therm.tempDown.click();
        therm.tempDown.click();
        Thread.sleep(4000);

        String new_cool_target_temp_down = therm.targetTemp.getText();
        Integer new_cool_target_temp_down_int = Integer.valueOf(method(new_cool_target_temp_down));
        System.out.println("New target temperature is " + new_cool_target_temp_down_int);

        if (new_target_temp_down_int == (target_temp_down_int - 2)) {
            System.out.println("Pass: the temperature was successfully changed");
        } else {
            System.out.println("Failed: the temperature was not successfully changed");
        }
        Thread.sleep(5000);
        therm.setFanMode.click();
        Thread.sleep(3000);
        therm.offModeIcon.click();
        Thread.sleep(7000);

        /** AUTO MODE **/

        therm.setFanMode.click();
        Thread.sleep(4000);
        therm.Auto_Icon.click();
        Thread.sleep(5000);
        if (therm.targetTemp.getText().equals("OFF")) {
            System.out.println("Failed: Thermostat mode is not AUTO");
        }

        if (therm.currentMode.getText().equals("AUTO")) {
            System.out.println("Pass: Mode successfully changed to AUTO");
        } else {
            System.out.println("Failed: Mode is not set to AUTO");
        }

        if (therm.targetTemp.getText().equals("N/A")) {
            System.out.println("Pass: Current temp is displayed as N/A");
        }

        Thread.sleep(5000);
        therm.setFanMode.click();
        Thread.sleep(3000);
        therm.offModeIcon.click();
        Thread.sleep(7000);
    }


    public void preTestSetup(){
        //Add 3 door window sensor and call it Front Door, back door, bathroom window
        //remove all devices
        //change all zwave settings to default
    }
    public void disArmParingDeviceTest(){
        //Pair 2 light switches locally( name it stock "Front Door" and "Back Door")
        //pair 1 door lock from ADC( name custom name "Door Lock with node ID")
        //pair 1 door lock locally and expect max number failure
        //change max number setting with setters service call
        //pair other 3 lock with custom name with node id
        //verify all name are correct on the panel
        //verify all names are correct on ADC Dealer
    }
    public void disArmNameChangeTest(){}
    public void disArmActionTest(){}
    public void disArmRulesTest(){}
    public void armStayActionTest(){}
    public void armStayRulesTest(){}
    public void armAwayRulesTest(){}
    public void armAwayActionTest(){}



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

    @AfterClass (alwaysRun = true)
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}