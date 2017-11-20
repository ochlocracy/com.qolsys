package sensors;

import panel.Contact_Us;
import panel.Emergency_Page;
import utils.Setup1;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Keyfob_Grid {
    Setup1 s = new Setup1();
    String page_name = "Smoke Test Smoke Sensor";
    Logger logger = Logger.getLogger(page_name);
    Sensors MySensors = new Sensors();
    private int ArmAway_state = 1;
    private int Disarm_state = 0;
    private int ArmStay_state = 2;
    private int Keyfob_Panic = 4;

    public Smoke_Test_Keyfob_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    public void verify_police_emergency() throws Exception {
        Emergency_Page emergency = PageFactory.initElements(s.getDriver(), Emergency_Page.class);
        if (emergency.Emergency_sent_text.getText().equals("Police Emergency Sent")){
            logger.info("Pass: Police Emergency is Sent");
        } else { s.take_screenshot();
            logger.info("Failed: Police Emergency is NOT Sent");}
    }

    public void verify_auxiliary_emergency() throws Exception {
        Emergency_Page emergency = PageFactory.initElements(s.getDriver(), Emergency_Page.class);
        if (emergency.Emergency_sent_text.getText().equals("Auxiliary Emergency Sent")) {
            logger.info("Pass: Auxiliary Emergency is Sent");
        } else { s.take_screenshot();
            logger.info("Failed: Auxiliary Emergency is NOT Sent");}
    }
    @Parameters ({"UDID_"})
    @Test
    public void Test1(String UDID_) throws Exception {
        Contact_Us contact_us = PageFactory.initElements(s.getDriver(), Contact_Us.class);
        Emergency_Page emergency = PageFactory.initElements(s.getDriver(), Emergency_Page.class);

        logger.info("Current software version: " + s.Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(5);
        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);

        logger.info("****************************KEYFOB****************************");
        logger.info("Keyfob group 1: can ArmStay-ArmAway-Disarm, panic = Police");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(3);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        TimeUnit.SECONDS.sleep(1);
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Keyfob group 4: can ArmStay-ArmAway-Disarm, panic = Auxiliary");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Keyfob group 6: can ArmStay-ArmAway-Disarm, panic = Auxiliary");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_disarm(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armstay(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        s.verify_armaway(UDID_);
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        s.enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        s.autoStaySetting();
        TimeUnit.SECONDS.sleep(2);
        contact_us.acknowledge_all_alerts();
        logger.info("Deleting all sensors");
        MySensors.deleteAllSensors1(UDID_);
        TimeUnit.SECONDS.sleep(3);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}
