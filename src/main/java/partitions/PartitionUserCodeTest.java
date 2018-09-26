package partitions;

import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.HomePage;
import panel.PanelCameraPage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.*;

import java.io.IOException;

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
//
//    @Test(priority = 1)
//    public void AddUserPartition1() throws IOException, InterruptedException {
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//
//    }
//
//    @Test(priority = 2)
//    public void AddUserPartition2() throws IOException, InterruptedException {
//        HomePage home = PageFactory.initElements(driver, HomePage.class);
//
//
//    }



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