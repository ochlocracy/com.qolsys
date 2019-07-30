package updateProcess;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import panel.*;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.File;
import java.io.IOException;

public class PreUpdatePGSensors extends Setup {

    String page_name = "Add PG sensors + sensors activity";
    Logger logger = Logger.getLogger(page_name);

    public PreUpdatePGSensors() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    @BeforeTest
    public void setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @BeforeMethod
    public void webdriver(){
    }

    @Test
    public void addSensors() throws IOException, InterruptedException {
        logger.info("Will delete all security sensors to begin, not z wave, must have no partitions enabled.");
        for (int i = 1; i < 99; i++) {
            deleteFromPrimary(i);
        }
        Thread.sleep(1000);
        logger.info("Adding a list of sensors");
        navigate_to_autolearn_page();

        /*** DW (104, 100, 107, 108, 109, 101, 110)***/
        addPGSensors("DW", 104, 1101, 0);//gr10
//        addPGSensors("DW", 104, 1152, 1);//gr12
//        addPGSensors("DW", 104, 1231, 2);//gr13
//        addPGSensors("DW", 104, 1216, 3);//gr14
//        addPGSensors("DW", 104, 1331, 4);//gr16
//        addPGSensors("DW", 104, 1127, "8", "8-Reporting Safety Sensor");//gr8 //scrolls to is broke, doing the swipe vertical works
//        addPGSensors("DW", 104, 1123, "9", "9-Delayed Reporting Safety Sensor");//gr9 //scrolls to
//        addPGSensors("DW", 104, 1311, "25", "25-Local Safety Sensor");//gr25 //scrolls to
        //adding more device types below from the Excel sheet. Not sure which group comes with these types.


        /*** Motion (120, 122, 123, 126, 127, 128, 129, 130, 150, 140 (Maxes out at 2 devices), 142***/
//        addPGSensors("Motion", 120, 1411, 1);//gr15   //(
//        addPGSensors("Motion", 123, 1441, 0);//gr17
//        addPGSensors("Motion", 122, 1423, 3);//gr20   //original
//        addPGSensors("Motion", 120, 1445, 4);//gr25
//        addPGSensors("Motion", 123, 1446, 2);//gr35   //)
        //adding more device types below from the Excel sheet. Not sure which group comes with these types.
//        addPGSensors("Motion", 126, 1412, 1);//gr15
//        addPGSensors("Motion", 127, 1447, 2);//35
//        addPGSensors("Motion", 128, 1448, 1);//gr15
//        addPGSensors("Motion", 129, 1449, 1);//gr15
//        addPGSensors("Motion", 130, 1450, 2);//35
//        addPGSensors("Motion", 150, 1412, 1);//gr15
//        addPGSensors("Motion", 140, 1447, 2);//35
//        addPGSensors("Motion", 142, 1448, 1);//gr15

        /* powerG camera (motion) limit test
        addPGSensors("Motion", 140, 1412, 1);//gr15
        addPGSensors("Motion", 140, 1447, 2);//gr15
        addPGSensors("Motion", 140, 1448, 1);//gr15
        */
        /*** Smoke (200, 201, 202) ***/
//        addPGSensors("Smoke", 201, 1541, 0);//gr26 smoke-M
//        addPGSensors("Smoke", 200, 1531, 0);//gr26 smoke - not works for real sensor at 01/17/1
        /*** CO (220, 221) ***/
//        addPGSensors("CO", 220, 1661, 0);//gr34

        /*** Shock (170, 171, 172, 173) - doesn't work for real sensor at 01/17/18***/
//        addPGSensors("Shock", 171, 1741, 0);//gr13
//        addPGSensors("Shock", 171, 1771, 1);//gr17

        /*** Glass-break (160, 161) ***/
//        addPGSensors("GBreak", 160, 1874, 0);//gr13
//        add.PGSensors("GBreak", 160, 1871, 1);//gr17
//
        /*** Water (240, 241) - doesn't for real sensor at 01/17/18***/
//        addPGSensors("Water", 241, 1971, 0);//gr38

        /***Key-fob (300, 301, 305, 306, 322) ***/
//        addPGSensors("Keyfob", 300, 1004, 0);//gr1
//        addPGSensors("Keyfob", 305, 1009, 1);//gr6
//        addPGSensors("Keyfob", 306, 1003, 2);//gr4

        /***Keypad (370, 371, 374, 379) ***/
//        addPGSensors("Keypad", 371, 1005, 0);//gr0
//        addPGSensors("Keypad", 371, 1006, 1);//gr1
//        addPGSensors("Keypad", 371, 1008, 2);//gr2
        //Added in the other group Types below from the excel spreadsheet.
//        addPGSensors("Keypad", 370, 1007, 0);//gr0
//        addPGSensors("Keypad", 370, 1009, 1);//gr1
//        addPGSensors("Keypad", 370, 1003, 2);//gr2
//        addPGSensors("Keypad", 374, 1010, 0);//gr0
//        addPGSensors("Keypad", 374, 1011, 1);//gr1
//        addPGSensors("Keypad", 374, 1012, 2);//gr2
//        addPGSensors("Keypad", 379, 1014, 0);//gr0
//        addPGSensors("Keypad", 379, 1015, 1);//gr1
//        addPGSensors("Keypad", 379, 1016, 2);//gr2
//        addPGSensors("Keypad", 379, 1017, 0);//gr0

        /***Aux pendant (320) ***/
//        addPGSensors("AuxPendant", 320, 1015, 6);
//        addPGSensors("AuxPendant", 320, 1016, 4);
//        addPGSensors("AuxPendant", 320, 1018, 25);
//        addPGSensors("AuxPendant", 320, 1014, 25);
        /***Siren (400, 410) ***/
//        addPGSensors("Siren", 400, 1995, 0);//gr33
//        addPGSensors("Siren", 410, 1998, 1);//gr25
        /*** Temperature (250) ***/
//        addPGSensors("Freeze", 250, 3030, 0);//gr
//        addPGSensors("Heat", 250, 3131, 0);//gr
        /***Translator (430)***/
        //addPGSensors("Translator", 430, 3231, 0);//gr

    }


    public void activation_restoration(int type, int id, String status1, String status2) throws InterruptedException, IOException {
        pgprimaryCall(type, id, status1);
        Thread.sleep(9000);
        pgprimaryCall(type, id, status2);
        Thread.sleep(2000);
    }

    //@Test//(dependsOnMethods = {"addSensors"})
    public void sensorsCheck() throws Exception {
        logger.info("Open-Close contact sensors");
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);

            logger.info("Activate Contact sensors");

            activation_restoration(104, 1101, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr10
            activation_restoration(104, 1152, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr12
            activation_restoration(104, 1231, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr13
            activation_restoration(104, 1216, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr14
            activation_restoration(104, 1331, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr16
            activation_restoration(104, 1311, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr25
            activation_restoration(104, 1127, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr8
            Thread.sleep(4000);
//            enterDefaultUserCode(); why isn't going into alarm sounding
            Thread.sleep(2000);
            activation_restoration(104, 1123, PGSensorsActivity.INOPEN, PGSensorsActivity.INCLOSE);//gr9
            Thread.sleep(2000);
            Thread.sleep(ConfigProps.longExitDelay);
            enterDefaultUserCode();
            Thread.sleep(2000);

            logger.info("Activate motion sensors");
            activation_restoration(120, 1411, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr15
            activation_restoration(123, 1441, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr17
            activation_restoration(120, 1445, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr25
            activation_restoration(122, 1423, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr20
            activation_restoration(123, 1446, PGSensorsActivity.MOTIONACTIVE, PGSensorsActivity.MOTIONIDLE);//gr35

            logger.info("Activate smoke sensors");
            activation_restoration(201, 1541, PGSensorsActivity.SMOKEM, PGSensorsActivity.SMOKEMREST);
            Thread.sleep(3000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(200, 1531, PGSensorsActivity.SMOKE, PGSensorsActivity.SMOKEREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(200, 1531, PGSensorsActivity.GAS, PGSensorsActivity.GASREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(201, 1541, PGSensorsActivity.GAS, PGSensorsActivity.GASREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate CO sensors");
            activation_restoration(220, 1661, PGSensorsActivity.CO, PGSensorsActivity.COREST);
            Thread.sleep(1000);

            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(220, 1661, PGSensorsActivity.GAS, PGSensorsActivity.GASREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);

            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate Shock sensors");
            ARM_STAY();
            activation_restoration(171, 1741, PGSensorsActivity.SHOCK, PGSensorsActivity.SHOCKREST);//gr13
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            ARM_AWAY(ConfigProps.longExitDelay);
            Thread.sleep(2000);
            activation_restoration(171, 1771, PGSensorsActivity.SHOCK, PGSensorsActivity.SHOCKREST);//gr17
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate Glass-break sensors");
            ARM_STAY();
            Thread.sleep(1000);
            activation_restoration(160, 1874, PGSensorsActivity.GB, PGSensorsActivity.GBREST);//gr13
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            ARM_AWAY(ConfigProps.longExitDelay);
            activation_restoration(160, 1871, PGSensorsActivity.GB, PGSensorsActivity.GBREST);//gr17
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            /*** Water - not works for real sensor at 01/17/18***/
            //  addPGSensors(241,1971,0);//gr38

            logger.info("Activate Key-fob sensors");
            activation_restoration(300, 1004, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr1
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(305, 1009, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr6
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(306, 1003, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr4
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate Keypad sensors");
            activation_restoration(371, 1005, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr0
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(371, 1006, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr1
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(371, 1008, PGSensorsActivity.POLICEPANIC, PGSensorsActivity.POLICEPANICREST);//gr2
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate Aux pendant");
            activation_restoration(320, 1015, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr6
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(320, 1016, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr4
            Thread.sleep(2000);
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(320, 1018, PGSensorsActivity.AUXPANIC, PGSensorsActivity.AUXPANICREST);//gr25
            Thread.sleep(2000);
            verifyDisarm();
            Thread.sleep(1000);

            contact.acknowledge_all_alerts();
            swipeLeft();
            Thread.sleep(1000);
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
