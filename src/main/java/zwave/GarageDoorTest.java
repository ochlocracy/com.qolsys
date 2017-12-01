package zwave;

import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import panel.Advanced_Settings_Page;
import panel.Devices_Page;
import panel.Home_Page;
import panel.Installation_Page;
import utils.Setup;

import java.io.IOException;

/**
 * Created by qolsys on 5/10/17.
 */
public class GarageDoorTest extends Setup{
    String page_name = "Door Lock Testing";
    Logger logger = Logger.getLogger(page_name);

    public GarageDoorTest() throws Exception {}


    public void Z_Wave_Garage_Door_Disarm_Mode(String UDID_) throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page instal = PageFactory.initElements(driver, Installation_Page.class);
        Devices_Page dev = PageFactory.initElements(driver, Devices_Page.class);
        Z_Wave_Page zwave = PageFactory.initElements(driver, Z_Wave_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);

        logger.info("*************************ZGD_D_001*******************************");
        logger.info("Disarm mode: Verify that a Garage Door Controller can be paired with a panel");

        logger.info("*************************ZGD_D_006*******************************");
        logger.info("Disarm mode: Verify that a Garage Door can be opened from a panel");

        logger.info("*************************ZGD_D_008*******************************");
        logger.info("Disarm mode: Verify that a Garage Door can be Closed from a panel");

        logger.info("*************************ZGD_D_007*******************************");
        logger.info("Disarm mode: Verify that Linear Garage Door can be Opened from a ADC User Site");

        logger.info("*************************ZGD_D_009*******************************");
        logger.info("Disarm mode: Verify that Linear Garage Door can be Closed from a ADC User Site");

        logger.info("*************************ZGD_D_004*******************************");
        logger.info("Disarm mode: Verify that when the device is unreachable a panel recognizes the status correctly");

        logger.info("*************************ZGD_D_003*******************************");
        logger.info("Disarm mode: Verify that Name, Type, Status, Battery info is correctly displayed on Home Control Status page");

        logger.info("*************************ZGD_D_005*******************************");
        logger.info("Disarm mode: Verify that Unreachable status will disappear from the Alerts page");

        logger.info("*************************ZGD_D_002*******************************");
        logger.info("Disarm mode: Verify that you can edit a device name (panel)");

        logger.info("*************************ZGD_D_011*******************************");
        logger.info("Disarm mode: Verify that the history events are displayed on the Home Control Status page");

        logger.info("*************************ZGD_D_012*******************************");
        logger.info("Disarm mode: Verify that a garage door will function when a panel is in AC failure");

        logger.info("*************************ZGD_D_010*******************************");
        logger.info("Disarm mode: Verify that a garage door controller can be deleted from a panel");


    }
    public void Z_Wave_Garage_Door_Arm_Stay_Mode(String UDID_) throws Exception {
        Advanced_Settings_Page adv = PageFactory.initElements(driver, Advanced_Settings_Page.class);
        Installation_Page instal = PageFactory.initElements(driver, Installation_Page.class);
        Devices_Page dev = PageFactory.initElements(driver, Devices_Page.class);
        Z_Wave_Page zwave = PageFactory.initElements(driver, Z_Wave_Page.class);
        Home_Page home = PageFactory.initElements(driver, Home_Page.class);

        logger.info("*************************ZGD_AS_004*******************************");
        logger.info("Arm Stay mode: Verify that a Garage Door can be opened from a panel");

        logger.info("*************************ZGD_AS_006*******************************");
        logger.info("Arm Stay mode: Verify that a Garage Door can be Closed from a panel");

        logger.info("*************************ZGD_AS_005*******************************");
        logger.info("Arm Stay mode: Verify that Linear Garage Door can be Opened from a ADC User Site");

        logger.info("*************************ZGD_AS_007*******************************");
        logger.info("Arm Stay mode: Verify that Linear Garage Door can be Closed from a ADC User Site");

        logger.info("*************************ZGD_AS_002*******************************");
        logger.info("Arm Stay mode: Verify that when the device is unreachable a panel recognizes the status correctly");

        logger.info("*************************ZGD_AS_003*******************************");
        logger.info("Arm Stay mode: Verify that Unreachable status will disappear from the Alerts page");

        logger.info("*************************ZGD_AS_001*******************************");
        logger.info("Arm Stay mode: Verify that the history events are displayed on the Home Control Status page");

        logger.info("*************************ZGD_AS_009*******************************");
        logger.info("Arm Stay mode: Verify that a garage door will function when a panel is in AC failure");

        logger.info("*************************ZGD_AS_008*******************************");
        logger.info("Arm Stay mode: Verify that the history events are displayed on the Home Control Status page");


    }


}
