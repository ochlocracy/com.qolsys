package qtmsSRF;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sensors.Sensors;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;
import org.testng.annotations.AfterTest;

public class AddSRF extends Setup {

    String page_name = "Add SRF sensors";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    ExtentReport rep = new ExtentReport("SRF_Sensors");

    public AddSRF() throws Exception {
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
        //srf all groups
        addPrimaryCall(3, 10, 6619296, 1); // 65 00 A0 //dw's
        addPrimaryCall(4, 12, 6619297, 1); // 65 00 A1
        addPrimaryCall(5, 13, 6619298, 1); // 65 00 A2
        addPrimaryCall(6, 14, 6619299, 1); // 65 00 A3
        addPrimaryCall(7, 16, 6619300, 1); // 65 00 A4
        addPrimaryCall(8, 8, 6619301, 1); // 65 00 A5
        addPrimaryCall(9, 9, 6619302, 1); // 65 00 A6
        addPrimaryCall(10, 25, 6619303, 1); // 65 00 A7
        Thread.sleep(1000);
        addPrimaryCall(11, 15, 5570628, 2); // 55 00 44 //motion's
        addPrimaryCall(12, 17, 5570629, 2); // 55 00 45
        addPrimaryCall(13, 20, 5570630, 2); // 55 00 46
        addPrimaryCall(14, 25, 5570631, 2); // 55 00 47
        addPrimaryCall(15, 35, 5570632, 2); // 55 00 48
        Thread.sleep(1000);
        addPrimaryCall(16, 26, 6750242, 5); // 67 00 22 //smoke
        addPrimaryCall(17, 34, 7667882, 6); // 75 00 AA //co
        addPrimaryCall(18, 13, 6750361, 19); // 67 00 99 //glass
        addPrimaryCall(19, 17, 6750355, 19); // 67 00 93 //glass
        Thread.sleep(1000);
        addPrimaryCall(20, 10, 6488238, 16); // 63 00 AE //tilt
        addPrimaryCall(21, 12, 6488239, 16); // 63 00 AF //tilt
        addPrimaryCall(22, 25, 6488224, 16); // 63 00 A0 //tilt
        addPrimaryCall(23, 13, 6684828, 107); // 66 00 9C //shock
        addPrimaryCall(24, 17, 6684829, 107); // 66 00 9D //shock
        addPrimaryCall(25, 52, 7536801, 17); // 73 00 A1 //freeze
        Thread.sleep(1000);
        addPrimaryCall(26, 26, 7667810, 111); // 75 00 62 //heat
        addPrimaryCall(27, 38, 7672224, 22); // 75 11 A0 //water
        addPrimaryCall(28, 1, 6619386, 102); // 65 00 FA //keyfob
        addPrimaryCall(29, 6, 6619387, 102); // 65 00 FB //keyfob
        addPrimaryCall(30, 4, 6619388, 102); // 65 00 FC //keyfob
        addPrimaryCall(31, 0, 8716538, 104); // 85 00 FA //keypad
        addPrimaryCall(32, 2, 8716539, 104); // 85 00 FB //keypad
        Thread.sleep(1000);
        addPrimaryCall(33, 25, 6405546, 109); // 61 BD AA //doorbell
        addPrimaryCall(34, 6, 6361649, 21); // 61 12 31 // medical
        addPrimaryCall(35, 0, 6361650, 21); // 61 12 32 //medical

    }
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