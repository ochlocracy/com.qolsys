package sensors;

import panel.Contact_Us;
import panel.Emergency_Page;
import utils.Log;
import utils.Setup;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Smoke_Test_Keyfob_Keypad_Pendant extends Setup {

    Sensors MySensors = new Sensors();
    String page_name = "Smoke Test Smoke and CO Detectors";
    Logger logger = Logger.getLogger(page_name);
    Log log = new Log();

    private int ArmAway_state = 1;
    private int Disarm_state = 0;
    private int ArmStay_state = 2;
    private int Keyfob_Panic = 4;
    private int Keypad_Panic = 3;
    private int Activate = 1;

    private int Long_Exit_Delay =16;

    public Smoke_Test_Keyfob_Keypad_Pendant() throws Exception {}

    @BeforeMethod
    public void capabilitiesSetup() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    public void verify_police_emergency() throws Exception {
        Emergency_Page emergency = PageFactory.initElements(driver, Emergency_Page.class);
        if (emergency.Emergency_sent_text.getText().equals("Police Emergency Sent")){
            logger.info("Pass: Police Emergency is Sent");
        } else { take_screenshot();
            logger.info("Failed: Police Emergency is NOT Sent");}
    }

    public void verify_auxiliary_emergency() throws Exception {
        Emergency_Page emergency = PageFactory.initElements(driver, Emergency_Page.class);
        if (emergency.Emergency_sent_text.getText().equals("Auxiliary Emergency Sent")) {
            logger.info("Pass: Auxiliary Emergency is Sent");
        } else { take_screenshot();
            logger.info("Failed: Auxiliary Emergency is NOT Sent");}
    }

    @Test
    public void Test1() throws Exception {
        Contact_Us contact_us = PageFactory.initElements(driver, Contact_Us.class);
        Emergency_Page emergency = PageFactory.initElements(driver, Emergency_Page.class);
        logger.info("Current software version: " + Software_Version());
        MySensors.read_sensors_from_csv();
        logger.info("Adding sensors...");
        MySensors.addAllSensors();
        TimeUnit.SECONDS.sleep(5);

        logger.info("****************************KEYFOB****************************");
        logger.info("Keyfob group 1: can ArmStay-ArmAway-Disarm, panic = Police");

        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 1,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Keyfob group 4: can ArmStay-ArmAway-Disarm, panic = Auxiliary");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 4,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Keyfob group 6: can ArmStay-ArmAway-Disarm, panic = Auxiliary");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keyfob_zones, 6,Keyfob_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
       enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("****************************KEYPAD****************************");
        logger.info("Keypad group 0: can ArmStay-ArmAway-Disarm, panic = Police");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 0,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Keypad group 2: can ArmStay-ArmAway-Disarm, panic = Police");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 2,Disarm_state);

        logger.info("********************************************************");
        logger.info("Keypad group 1: can ArmStay-ArmAway-Disarm, panic = Police");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,Disarm_state);
        TimeUnit.SECONDS.sleep(5);
        verify_disarm();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        TimeUnit.SECONDS.sleep(5);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,ArmStay_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armstay();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        TimeUnit.SECONDS.sleep(5);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,ArmAway_state);
        TimeUnit.SECONDS.sleep(5);
        verify_armaway();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.keypad_zones, 1,Keypad_Panic);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        TimeUnit.SECONDS.sleep(5);
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("****************************AUXILIARY PENDANT****************************");
        logger.info("Auxiliary pendant group 0: Disarm mode activated -> Police emergency ");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.pendant_zones, 0,Activate);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Auxiliary pendant group 6: Disarm mode activated -> Auxiliary emergency ");
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.pendant_zones, 6,Activate);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Auxiliary pendant group 0: ArmStay mode activated -> Police emergency ");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.pendant_zones, 0,Activate);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Auxiliary pendant group 6: ArmStay mode activated -> Auxiliary emergency ");
        ARM_STAY();
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.pendant_zones, 6,Activate);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Auxiliary pendant group 0: ArmAway mode activated -> Police emergency ");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.pendant_zones, 0,Activate);
        TimeUnit.SECONDS.sleep(5);
        verify_police_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

        logger.info("********************************************************");
        logger.info("Auxiliary pendant group 6: ArmAway mode activated -> Auxiliary emergency ");
        ARM_AWAY(Long_Exit_Delay);
        MySensors.sendPacket_allSensors_selectedGroup(MySensors.pendant_zones, 6,Activate);
        TimeUnit.SECONDS.sleep(5);
        verify_auxiliary_emergency();
        emergency.Cancel_Emergency.click();
        enter_default_user_code();
        TimeUnit.SECONDS.sleep(5);

       contact_us.acknowledge_all_alerts();
       logger.info("Deleting all sensors");
       MySensors.deleteAllSensors();
    }

    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}