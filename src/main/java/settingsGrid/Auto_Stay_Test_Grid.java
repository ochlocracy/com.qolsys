package settingsGrid;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import sensors.Sensors;
import sensors.SensorsList;
import utils.ConfigProps;
import utils.Setup1;

import java.io.IOException;

public class Auto_Stay_Test_Grid {

    Setup1 s = new Setup1();
    String page_name = "Auto Stay testing";
    Logger logger = Logger.getLogger(page_name);
    Sensors MySensors = new Sensors();

    private int delay = 15;
    private int Open = 1;
    private int Close = 0;

    public Runtime rt = Runtime.getRuntime();

    public Auto_Stay_Test_Grid() throws Exception {}

    public void SCGrid_Set_Auto_Stay(String UDID_, int state) throws IOException, InterruptedException {
        rt.exec(ConfigProps.adbPath + " -s " + UDID_ +  " shell service call qservice 40 i32 0 i32 0 i32 20 i32 "+state+" i32 0 i32 0");
        System.out.println(ConfigProps.adbPath + " -s " + UDID_ +  " shell service call qservice 40 i32 0 i32 0 i32 19 i32 " +state+ " i32 0 i32 0" + " Auto Stay Enabled");
    }


    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }

    @Parameters ({"UDID_"})
    @Test //EXPECTED:If arming AWAY and no delay door is opened, system assumes you are still inside and changes arming to Stay Mode.
    public void Verify_Auto_Stay_works(String UDID_) throws Exception {
        SecurityArmingPage arming = PageFactory.initElements(s.getDriver(), SecurityArmingPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        AdvancedSettingsPage adv = PageFactory.initElements(s.getDriver(), AdvancedSettingsPage.class);
        InstallationPage inst = PageFactory.initElements(s.getDriver(), InstallationPage.class);
        HomePage home = PageFactory.initElements(s.getDriver(), HomePage.class);

        logger.info("Adding sensors..."); //add test where the dw sensor is opened and closed so it actually does arm away when enabled.
        //no physical summary text change, need to do a servicecall to change value.
        SCGrid_Set_Auto_Stay(UDID_, 1 ); //needs work, service call not changing state on the panel.
        s.delete_from_primary(UDID_, 1);
        s.add_primary_call(1, 10, 6619296, 1, UDID_);
        Thread.sleep(2000);
        logger.info("Verify that Auto Stay works when enabled, automatically switch to arm stay");
        Thread.sleep(3000);
        logger.info("Arm Away the system so it switches to arm stay since no door is opened.");
        s.ARM_AWAY(delay);
        s.verify_armstay(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();

//        logger.info("Verify that Auto Stay works when enabled, open a door, system stays on arm away.");
//        s.ARM_AWAY(5);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Open);
//        Thread.sleep(3000);
//        s.verify_armaway(UDID_);
//        MySensors.sendPacket_allSensors_selectedGroup(MySensors.door_window_zones, 10, Close);
//        home.DISARM.click();
//        s.enter_default_user_code();

        logger.info("Verify that Auto Stay does not work when disabled");
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        s.swipe_vertical();
        Thread.sleep(3000);
        s.swipe_vertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        logger.info("Arm Away the system");
        s.ARM_AWAY(delay);
        s.verify_armaway(UDID_);
        home.DISARM.click();
        s.enter_default_user_code();
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(3000);
        s.swipe_vertical();
        Thread.sleep(3000);
        s.swipe_vertical();
        arming.Auto_Stay.click();
        Thread.sleep(2000);
        settings.Home_button.click();
        s.delete_from_primary(UDID_,1);
        Thread.sleep(3000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}