package UIPerformanceTesting;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Setup;
import utils.Timer;
import zwave.DoorLockPage;
import zwave.LightsPage;
import zwave.ThermostatPage;

import static utils.ConfigProps.primary;

/**
 * Created by qolsys on 3/5/18.
 */
public class ZwavePerformance extends Setup{

    StopWatch stopWatch = new StopWatch();





    public ZwavePerformance() throws Exception{
        LightsPage lightsPage = PageFactory.initElements(driver, LightsPage.class);



    }


    Timer timer = new Timer();
//    double avarageTime;
//    double[] runtimes = new double[5];





    public void smart_click(WebElement element, WebElement element2, String status, String message) {
        if (element.getText().equals(status)) {
            element2.click();
            System.out.println("Door lock is successfully " + message);
        }else  {
            System.out.println("Status is not as expected");
        }
    }
    public void status_verification(WebElement element, String text) {
        if (element.getText().equals(text)) {
            System.out.println("Pass: status successfully changed to " + text);
        } else {
            System.out.println("Failed: status is not " + text);
        }
    }

    // might need to delete
    public void waitForNextAction(WebDriver driver,By element, int seconds){

        WebDriverWait wait = new WebDriverWait(driver,seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }

//    public void waitForText(WebDriver driver,By element, int seconds){
//
//        WebDriverWait wait = new WebDriverWait(driver,seconds);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
//    }


    //***************************************************************************************************************************
    // add times for each run to input in excel spread sheet with the correct software and date run. Save the sheet for each run.
    //***************************************************************************************************************************
    @BeforeTest
    public void setup() throws Exception {
        setupDriver(primary , "http://127.0.1.1", "4723");
    }


    // testing the light
    @Test
    public void ligthPerformanceTest() throws Exception{
        LightsPage lights = PageFactory.initElements(driver, LightsPage.class);
        swipeLightsPage(lights);
        for (int n=1; n <=4; n++) {
            System.out.println("Currently On Lights Page");
            System.out.println("starting Timer");
            stopWatch.start();
            for (int i = 1; i <= 4; i++) {
                lights.lightSelectionBox.click();
                lights.allOnBtn.click();
                lights.lightSelectionBox.click();
                lights.allOffBtn.click();
            }
            stopWatch.stop();
            System.out.println("Ending Timer");
            System.out.println("number " + n + " Total Time "  + stopWatch.getTime());
            stopWatch.reset();
        }
    }

//    @Test
//    public void DimmerPerformanceTest(){
//        LightsPage lightpage = PageFactory.initElements(driver,LightsPage.class);
//    }

    @Test
    public void DoorLockPerformanceTest() throws Exception {
        DoorLockPage doorlockPage = PageFactory.initElements(driver, DoorLockPage.class);
        swipeToDoorLockPage(doorlockPage);
        for (int n=1; n<=2; n++) {
            System.out.println("Currently On Doorlock Page");
            System.out.println("starting Timer");
            stopWatch.start();
            for (int i = 1; i <= 2; i++) {
                System.out.println("Unlocking");
                System.out.println("Current Lock Status " + doorlockPage.doorLockStatus);
                doorlockPage.LocknUnlock.click();
                System.out.println("wait for Locked text");
//                waitForText(driver, doorlockPage.LockedTxt, 30);
                waitForElementText(driver,doorlockPage.lockedText,30);
                System.out.println("locking");
                doorlockPage.LocknUnlock.click();
//                waitForText(driver, doorlockPage.UnlockedTxt, 30);
                System.out.println("wait for Unlocked");
                waitForElementText(driver, doorlockPage.unlockedText,30);
                System.out.println("done");
            }
            System.out.println("Ending Timer");
            stopWatch.stop();
            System.out.println("number " + n + " Total Time "  + stopWatch.getTime());
            stopWatch.reset();
        }
    }

    @Test
    public void thermostatPerformance() throws Exception {
        ThermostatPage therm = PageFactory.initElements(driver, ThermostatPage.class);
        swipeToThermostatPage(therm);
        for (int i = 1; i <= 4; i++) {
            System.out.println("Currently On Thermostat Page");
            System.out.println("starting Timer");
            stopWatch.start();
            therm.setThermostatMode.click();
            //-Heat mode
            System.out.println("Setting Mode to Heat");
            therm.heatMode.click();
            //change to waitForElementTxt
            waitForText(driver, therm.heatCurrentModeText, 30);
            // Temp Change Down by 5
            System.out.println("Current Target Temp 80");
            elementVerification(therm.targetTemp, "80°");
            thermostatDown5();
            elementVerification(therm.targetTemp, "75°");
            thermostatUp5();
            elementVerification(therm.targetTemp, "80°");
            //-cool mode
            System.out.println("Changing Thermostat Mode to Cool");
            therm.setThermostatMode.click();
            System.out.println("Selecting Cool Mode");
            therm.coolModeTxt.click();
            System.out.println("Waiting for Cool Mode");
            waitForText(driver, therm.coolCurrentModeText, 300);
            elementVerification(therm.targetTemp, "70°");
            thermostatDown5();
            elementVerification(therm.targetTemp, "65°");
            thermostatUp5();
            elementVerification(therm.targetTemp, "70°");
            therm.setThermostatMode.click();
            therm.offModeTxt.click();
            elementVerification(therm.currentMode, "OFF");
            waitForText(driver, therm.offCurrentModeText, 300);
            elementVerification(therm.currentMode, "OFF");
            System.out.println("Ending Timer");
            stopWatch.stop();
            System.out.println("number " + i + " Total Time " + stopWatch.getTime());
            stopWatch.reset();
        }
    }



    @AfterTest
    public void teardown() throws Exception {
        driver.quit();
        service.stop();
    }
   //**************Small Version*******


    /*Light
    -Pair light 10 time
    -Active light on
    -Active light off
     */

    /*Dimmer
    -pair dimmer 10 time
    -on
    -off
    -dimmer level change
        -from 100% --> 50%
        -from 50% --> 100%
     */

    /*Door Lock (need new batteries before test)
    -Pair Door lock 10 time
    -Lock
    -unlock
     */



    /*GDC
    -pair GDC 10 time
    -open
    -Close
     */



    //************Fully loaded***************


    /*Light
    -Pair 12 light 10 time
    -Active all light on
    -Active all light off
     */

    /*Door Lock (need new batteries before test)
    -Pair 6 Door lock
    -Lock all 6 door lock
    -unlock all 6 door locks
     */

    /*Thermostat
    -pair 6 thermostat
    *mode Changes
    -Off mode
    -Heat mode
    -cool mode
    *Mode Operations
    -Heat
        -Change temp by 10 degrease
    -Cool mode
        -Change temp by 10 degrease
    -fan mode
        -on
        -off
     */

    /*GDC
    -pair 3 GDC
    -open all
    -Close all
     */














}
