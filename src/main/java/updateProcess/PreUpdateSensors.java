package updateProcess;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.ContactUs;
import panel.EmergencyPage;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class PreUpdateSensors extends Setup {

    String page_name = "Add sensors + sensors activity";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();

    public PreUpdateSensors() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    @BeforeTest
    public void setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        logger.info("Adding a list of sensors");
        add_primary_call(3, 10, 6619296, 1); // 65 00 A0
        add_primary_call(4, 12, 6619297, 1); // 65 00 A1
        add_primary_call(5, 13, 6619298, 1); // 65 00 A2
        add_primary_call(6, 14, 6619299, 1); // 65 00 A3
        add_primary_call(7, 16, 6619300, 1); // 65 00 A4
        add_primary_call(8, 8, 6619301, 1); // 65 00 A5
        add_primary_call(9, 9, 6619302, 1); // 65 00 A6
        add_primary_call(10, 25, 6619303, 1); // 65 00 A7
        Thread.sleep(1000);
        add_primary_call(11, 15, 5570628, 2); // 55 00 44
        add_primary_call(12, 17, 5570629, 2); // 55 00 45
        add_primary_call(13, 20, 5570630, 2); // 55 00 46
        add_primary_call(14, 25, 5570631, 2); // 55 00 47
        add_primary_call(15, 35, 5570632, 2); // 55 00 48
        Thread.sleep(1000);
        add_primary_call(16, 26, 6750242, 5); // 67 00 22
        add_primary_call(17, 34, 7667882, 6); // 75 00 AA
        add_primary_call(18, 13, 6750361, 19); // 67 00 99
        add_primary_call(19, 17, 6750355, 19); // 67 00 93
        Thread.sleep(1000);
        add_primary_call(20, 10, 6488238, 16); // 63 00 AE
        add_primary_call(21, 12, 6488239, 16); // 63 00 AF
        add_primary_call(22, 25, 6488224, 16); // 63 00 A0
        add_primary_call(23, 13, 6684828, 107); // 66 00 9C
        add_primary_call(24, 17, 6684829, 107); // 66 00 9D
        add_primary_call(25, 52, 7536801, 17); // 73 00 A1
        Thread.sleep(1000);
        add_primary_call(26, 26, 7667810, 111); // 75 00 62
        add_primary_call(27, 38, 7672224, 22); // 75 11 A0
        add_primary_call(28, 1, 6619386, 102); // 65 00 FA
        add_primary_call(29, 6, 6619387, 102); // 65 00 FB
        add_primary_call(30, 4, 6619388, 102); // 65 00 FC
        add_primary_call(31, 0, 8716538, 104); // 85 00 FA
        add_primary_call(32, 2, 8716539, 104); // 85 00 FB
        Thread.sleep(1000);
        add_primary_call(33, 25, 6405546, 109); // 61 BD AA
        add_primary_call(34, 6, 6361649, 21); // 61 12 31
        add_primary_call(35, 0, 6361650, 21); // 61 12 32
    }

    public void open_close(String DLID) throws InterruptedException, IOException {
        sensors.primary_call(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primary_call(DLID, SensorsActivity.CLOSE);
        Thread.sleep(1000);
    }

    @Test(dependsOnMethods = {"addSensors"})
    public void sensorsCheck() throws Exception {
        logger.info("Open-Close contact sensors");
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        for (int i = 1; i > 0; i--) {
            open_close("65 00 0A");
            open_close("65 00 1A");
            open_close("65 00 2A");
            open_close("65 00 3A");
            open_close("65 00 4A");
            open_close("65 00 5A");
            enter_default_user_code();
            Thread.sleep(1000);
            open_close("65 00 6A");
            enter_default_user_code();
            Thread.sleep(1000);
            open_close("65 00 7A");
            Thread.sleep(1000);

            logger.info("Activate motion sensors");
            sensors.primary_call("55 00 44", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primary_call("55 00 54", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primary_call("55 00 64", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primary_call("55 00 74", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primary_call("55 00 84", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);

            logger.info("Activate smoke sensors");
            sensors.primary_call("67 00 22", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate CO sensors");
            sensors.primary_call("75 00 AA", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate glassbreak sensors");
            sensors.primary_call("67 00 99", SensorsActivity.ACTIVATE);
            sensors.primary_call("67 00 99", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            sensors.primary_call("67 00 39", SensorsActivity.ACTIVATE);
            sensors.primary_call("67 00 39", SensorsActivity.RESTORE);
            Thread.sleep(1000);

            logger.info("Open/close tilt sensors");
            open_close("63 00 EA");
            open_close("63 00 FA");
            open_close("63 00 0A");
            Thread.sleep(1000);

            logger.info("Activate IQShock sensors");
            sensors.primary_call("66 00 C9", SensorsActivity.ACTIVATE);
            sensors.primary_call("66 00 C9", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            sensors.primary_call("66 00 D9", SensorsActivity.ACTIVATE);
            sensors.primary_call("66 00 D9", SensorsActivity.RESTORE);
            Thread.sleep(1000);

            logger.info("Activate freeze sensors");
            sensors.primary_call("73 00 1A", SensorsActivity.ACTIVATE);
            sensors.primary_call("73 00 1A", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate heat sensors");
            sensors.primary_call("75 00 26", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate water sensors");
            sensors.primary_call("75 11 0A", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate keyfob sensors");
            sensors.primary_call("65 00 AF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            sensors.primary_call("65 00 BF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            sensors.primary_call("65 00 CF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate keypad sensors");
            sensors.primary_call("85 00 AF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            sensors.primary_call("85 00 BF", "04 04");
            Thread.sleep(2000);
            verify_armaway();
            sensors.primary_call("85 00 BF", "08 01");
            Thread.sleep(1000);

            logger.info("Activate medical pendant sensors");
            sensors.primary_call("61 12 13", "03 01");
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            sensors.primary_call("61 12 23", "03 01");
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enter_default_user_code();
            Thread.sleep(1000);

            logger.info("Activate doorbell sensors");
            open_close("61 BD AA");
            Thread.sleep(1000);

            contact.acknowledge_all_alerts();
            swipe_left();
            Thread.sleep(1000);
        }
    }

    //    @Test
//    public void deleteSensors() throws IOException, InterruptedException {
//        for (int i = 33; i>0; i--) {
//            delete_from_primary(i);
//        }
//    }
    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }
}