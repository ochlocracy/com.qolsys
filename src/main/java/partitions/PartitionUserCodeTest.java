package partitions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import utils.*;

import java.io.IOException;
import java.util.List;

public class PartitionUserCodeTest extends Setup {

    public PartitionUserCodeTest() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    ExtentReport rep = new ExtentReport("Partitions_SensorTest");
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test(priority = 1)
    public void AddUserPartition1() throws IOException, InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        UserManagementPage usr = PageFactory.initElements(driver, UserManagementPage.class);

        List<WebElement> pli = driver.findElements(By.id("com.qolsys:id/partitionCheckBox"));

        String P1usr = "P1usr";
        String P2usr = "P2usr";
        String P3usr = "P3usr";
        String P4usr = "P4usr";

        String P1usrKey = "5551";
        String P2usrKey = "5552";
        String P3usrKey = "5553";
        String P4usrKey = "5554";

        try {
            if (home.pinpad.isDisplayed()) ;
            {
                enterDefaultDealerCode();
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(10000);

        navigateToPartitionsAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        usr.Add_User.click();
        usr.Add_User_Name_field.sendKeys(P1usr);
        usr.Add_User_Code_field.sendKeys(P1usrKey);
        usr.Add_Confirm_User_Code_field.sendKeys(P1usrKey);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
        pli.get(0).click();
        Thread.sleep(1000);
        usr.Add_User_add_page.click();


        usr.Add_User.click();
        usr.Add_User_Name_field.sendKeys(P2usr);
        usr.Add_User_Code_field.sendKeys(P2usrKey);
        usr.Add_Confirm_User_Code_field.sendKeys(P2usrKey);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
        pli.get(1).click();
        Thread.sleep(1000);
        usr.Add_User_add_page.click();

        usr.Add_User.click();
        usr.Add_User_Name_field.sendKeys(P3usr);
        usr.Add_User_Code_field.sendKeys(P3usrKey);
        usr.Add_Confirm_User_Code_field.sendKeys(P3usrKey);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
        pli.get(2).click();
        Thread.sleep(1000);
        usr.Add_User_add_page.click();

        usr.Add_User.click();
        usr.Add_User_Name_field.sendKeys(P4usr);
        usr.Add_User_Code_field.sendKeys(P4usrKey);
        usr.Add_Confirm_User_Code_field.sendKeys(P4usrKey);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
        pli.get(3).click();
        Thread.sleep(1000);
        usr.Add_User_add_page.click();
    }

    @Test(priority = 3)
    public void DuressTestAllP() throws Exception {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        PanelCameraPage cam = PageFactory.initElements(driver, PanelCameraPage.class);

        try {
            if (home.pinpad.isDisplayed()) ;
            {
                enterDefaultDealerCode();
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(15000);

        rep.create_report("Duress_Part_01");
        rep.log.log(LogStatus.INFO, ("*Duress_Part_01* Test Duress Code works -> Expected result = Duress Photo is taken"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Eight.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }

        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(12000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Eight.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
        rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
    } else {
        takeScreenshot();
        rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
    }
        swipeFromLefttoRight();

        swipeVertical();

        rep.create_report("Duress_Part_02");
        rep.log.log(LogStatus.INFO, ("*Duress_Part_02* Test Duress Code works -> Expected result = Duress Photo is taken"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.One.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }

        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(12000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.One.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }

        swipeFromLefttoRight();

        swipeVertical();

        rep.create_report("Duress_Part_03");
        rep.log.log(LogStatus.INFO, ("*Duress_Part_03* Test Duress Code works -> Expected result = Duress Photo is taken"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Two.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }

        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(12000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Two.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }

        swipeFromLefttoRight();

        swipeVertical();

        rep.create_report("Duress_Part_04");
        rep.log.log(LogStatus.INFO, ("*Duress_Part_04* Test Duress Code works -> Expected result = Duress Photo is taken"));
        Thread.sleep(2000);
        home.DISARM.click();
        home.ARM_STAY.click();
        Thread.sleep(1000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Two.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }

        Thread.sleep(3000);
        home.DISARM.click();
        home.ARM_AWAY.click();
        Thread.sleep(12000);
        home.DISARM.click();
        home.Nine.click();
        home.Nine.click();
        home.Nine.click();
        home.Two.click();
        Thread.sleep(1000);
        swipeFromRighttoLeft();
        if (cam.Duress_Disarm_Photo.isDisplayed()) {
            rep.log.log(LogStatus.PASS, ("Pass: Duress code does work"));
        } else {
            takeScreenshot();
            rep.log.log(LogStatus.FAIL, ("Failed: Duress code did not work"));
        }




    }

    //d 9998 p1
    //d1 9991 p2
    //d2 9992 p3
    //d3 9993 p4

}