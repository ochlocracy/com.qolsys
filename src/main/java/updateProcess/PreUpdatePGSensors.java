package updateProcess;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import sensors.Sensors;
import utils.ConfigProps;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class PreUpdatePGSensors extends Setup{

    String page_name = "Add PG sensors + sensors activity";
    Logger logger = Logger.getLogger(page_name);


    public PreUpdatePGSensors() throws Exception {
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

        addPGSensors(101,1001,0);//gr10
        addPGSensors(104,1011,1);//gr12
        addPGSensors(104,1231,2);//gr13
        addPGSensors(104,1211,3);//gr13
        Thread.sleep(2000);
        addPGSensors(104,1231,4);//gr16
        addPGSensors(101,1022,6);//gr8
        addPGSensors(101,1023,7);//gr9
        addPGSensors(104,1211,5);//gr25
        Thread.sleep(2000);

        ///*
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
        addPrimaryCall(35, 0, 6361650, 21); // 61 12 32*/
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
