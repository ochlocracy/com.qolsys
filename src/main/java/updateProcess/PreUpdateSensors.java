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
import utils.ExtentReport;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class PreUpdateSensors extends Setup {

    String page_name = "Add sensors + sensors activity";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ExtentReport rep = new ExtentReport("SRF_Sensors_Sanity");

    public PreUpdateSensors() throws Exception {
        ConfigProps.init();
        SensorsActivity.init();
    }

    @BeforeTest
    public void setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        logger.info("Adding a list of sensors");
        addPrimaryCall(3, 10, 6619296, 1); // 65 00 A0
        addPrimaryCall(4, 12, 6619297, 1); // 65 00 A1
        addPrimaryCall(5, 13, 6619298, 1); // 65 00 A2
        addPrimaryCall(6, 14, 6619299, 1); // 65 00 A3
        addPrimaryCall(7, 16, 6619300, 1); // 65 00 A4
        addPrimaryCall(8, 8, 6619301, 1); // 65 00 A5
        addPrimaryCall(9, 9, 6619302, 1); // 65 00 A6
        addPrimaryCall(10, 25, 6619303, 1); // 65 00 A7
        Thread.sleep(1000);
        addPrimaryCall(11, 15, 5570628, 2); // 55 00 44
        addPrimaryCall(12, 17, 5570629, 2); // 55 00 45
        addPrimaryCall(13, 20, 5570630, 2); // 55 00 46
        addPrimaryCall(14, 25, 5570631, 2); // 55 00 47
        addPrimaryCall(15, 35, 5570632, 2); // 55 00 48
        Thread.sleep(1000);
        addPrimaryCall(16, 26, 6750242, 5); // 67 00 22
        addPrimaryCall(17, 34, 7667882, 6); // 75 00 AA
        addPrimaryCall(18, 13, 6750361, 19); // 67 00 99
        addPrimaryCall(19, 17, 6750355, 19); // 67 00 93
        Thread.sleep(1000);
        addPrimaryCall(20, 10, 6488238, 16); // 63 00 AE
        addPrimaryCall(21, 12, 6488239, 16); // 63 00 AF
        addPrimaryCall(22, 25, 6488224, 16); // 63 00 A0
        addPrimaryCall(23, 13, 6684828, 107); // 66 00 9C
        addPrimaryCall(24, 17, 6684829, 107); // 66 00 9D
        addPrimaryCall(25, 52, 7536801, 17); // 73 00 A1
        Thread.sleep(1000);
        addPrimaryCall(26, 26, 7667810, 111); // 75 00 62
        addPrimaryCall(27, 38, 7672224, 22); // 75 11 A0
        addPrimaryCall(28, 1, 6619386, 102); // 65 00 FA
        addPrimaryCall(29, 6, 6619387, 102); // 65 00 FB
        addPrimaryCall(30, 4, 6619388, 102); // 65 00 FC
        addPrimaryCall(31, 0, 8716538, 104); // 85 00 FA
        addPrimaryCall(32, 2, 8716539, 104); // 85 00 FB
        Thread.sleep(1000);
        addPrimaryCall(33, 25, 6405546, 109); // 61 BD AA
        addPrimaryCall(34, 6, 6361649, 21); // 61 12 31
        addPrimaryCall(35, 0, 6361650, 21); // 61 12 32
    }

    public void open_close(String DLID) throws InterruptedException, IOException {
        sensors.primaryCall(DLID, SensorsActivity.OPEN);
        Thread.sleep(1000);
        sensors.primaryCall(DLID, SensorsActivity.CLOSE);
        Thread.sleep(1000);
    }

    @Test//(dependsOnMethods = {"addSensors"})
    public void sensorsCheck() throws Exception {
        logger.info("Open-Close contact sensors");
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        for (int i = 1; i > 0; i--) {

            open_close("65 00 0A");
            open_close("65 00 1A");
            open_close("65 00 2A");
            open_close("65 00 3A");
            open_close("65 00 4A");
            open_close("65 00 5A");
            enterDefaultUserCode();
            Thread.sleep(1000);
            open_close("65 00 6A");
            enterDefaultUserCode();
            Thread.sleep(1000);
            open_close("65 00 7A");
            Thread.sleep(1000);

            logger.info("Activate motion sensors");
            sensors.primaryCall("55 00 44", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 54", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 64", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 74", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            sensors.primaryCall("55 00 84", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);

            logger.info("Activate smoke sensors");
            sensors.primaryCall("67 00 22", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate CO sensors");
            sensors.primaryCall("75 00 AA", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate glassbreak sensors");
            sensors.primaryCall("67 00 99", SensorsActivity.ACTIVATE);
            sensors.primaryCall("67 00 99", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            sensors.primaryCall("67 00 39", SensorsActivity.ACTIVATE);
            sensors.primaryCall("67 00 39", SensorsActivity.RESTORE);
            Thread.sleep(1000);

            logger.info("Open/close tilt sensors");
            open_close("63 00 EA");
            open_close("63 00 FA");
            open_close("63 00 0A");
            Thread.sleep(1000);

            logger.info("Activate IQShock sensors");
            sensors.primaryCall("66 00 C9", SensorsActivity.ACTIVATE);
            sensors.primaryCall("66 00 C9", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            sensors.primaryCall("66 00 D9", SensorsActivity.ACTIVATE);
            sensors.primaryCall("66 00 D9", SensorsActivity.RESTORE);
            Thread.sleep(1000);

            logger.info("Activate freeze sensors");
            sensors.primaryCall("73 00 1A", SensorsActivity.ACTIVATE);
            sensors.primaryCall("73 00 1A", SensorsActivity.RESTORE);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate heat sensors");
            sensors.primaryCall("75 00 26", SensorsActivity.ACTIVATE);
            Thread.sleep(1000);

            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate water sensors");
            sensors.primaryCall("75 11 0A", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate keyfob sensors");
            sensors.primaryCall("65 00 AF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            sensors.primaryCall("65 00 BF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            sensors.primaryCall("65 00 CF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate keypad sensors");
            sensors.primaryCall("85 00 AF", SensorsActivity.OPEN);
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            sensors.primaryCall("85 00 BF", "04 04");
            Thread.sleep(2000);
            verifyArmaway();
            sensors.primaryCall("85 00 BF", "08 01");
            Thread.sleep(1000);

            logger.info("Activate medical pendant sensors");
            sensors.primaryCall("61 12 13", "03 01");
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            sensors.primaryCall("61 12 23", "03 01");
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate doorbell sensors");
            open_close("61 BD AA");
            Thread.sleep(1000);

            contact.acknowledge_all_alerts();
            swipeLeft();
            Thread.sleep(1000);

            System.out.println(i);
        }
    }

    //    @Test
//    public void deleteSensors() throws IOException, InterruptedException {
//        for (int i = 33; i>0; i--) {
//            deleteFromPrimary(i);
//        }
//    }
    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}