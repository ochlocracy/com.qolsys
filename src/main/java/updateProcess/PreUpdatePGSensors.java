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
import utils.PGSensorsActivity;
import utils.SensorsActivity;
import utils.Setup;

import java.io.IOException;

public class PreUpdatePGSensors extends Setup{

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

    @Test
    public void addSensors() throws IOException, InterruptedException {
        logger.info("Adding a list of sensors");
        navigate_to_autolearn_page();
        /*** DW ***/
        addPGSensors(104,1101,0);//gr10
        addPGSensors(104,1152,1);//gr12
        addPGSensors(104,1231,2);//gr13
        addPGSensors(104,1216,3);//gr14
        addPGSensors(104,1331,4);//gr16
        addPGSensors(104,1127,6);//gr8
        addPGSensors(104,1123,7);//gr9
        addPGSensors(104,1311,5);//gr25
        /*** Motion ***/
        addPGSensors(120,1411,1);//gr15
        addPGSensors(123,1441,0);//gr17
        addPGSensors(122,1423,3);//gr20
        addPGSensors(120,1445,4);//gr25
        addPGSensors(123,1446,2);//gr35
        /*** Smoke ***/
        addPGSensors(201,1541,0);//gr26 smoke-M
        addPGSensors(200,1531,0);//gr26 smoke - not works for real sensor at 01/17/1
        /*** CO ***/
        addPGSensors(220,1661,0);//gr34
        /*** Shock - not works for real sensor at 01/17/18***/
        //  addPGSensors(171,1741,0);//gr13
        // addPGSensors(171,1771,1);//gr17
       // addPGSensors(171,1771,1);//gr17
        /*** Glass-break ***/
          addPGSensors(160,1874,0);//gr13
          addPGSensors(160,1871,1);//gr17
        /*** Temperature Detector - no plans to support that at 01/17/18***/
        //  addPGSensors(250,1941,0);//gr52
        /*** Water - not works for real sensor at 01/17/18***/
        //  addPGSensors(241,1971,0);//gr38
        /***Key-fob ***/
        addPGSensors(300,1004,0);//gr1
        addPGSensors(305,1009,1);//gr6
        addPGSensors(306,1003,2);//gr4
        /***Keypad ***/
        addPGSensors(371,1005,0);//gr0
        addPGSensors(371,1006,1);//gr1
        addPGSensors(371,1008,2);//gr2
        /***Aux pendant ***/
        addPGSensors(320,1015,0);//gr6
        addPGSensors(320,1016,4);//gr4
        addPGSensors(320,1018,5);//gr25
        /***Siren ***/
        addPGSensors(400,1995,0);//gr33
        addPGSensors(410,1998,1);//gr25
    }
    public void activation_restoration(int type, int id, String status1, String status2) throws InterruptedException, IOException {
        pgprimaryCall(type, id, status1);
        Thread.sleep(1000);
        pgprimaryCall(type, id, status2);
        Thread.sleep(1000);
    }
    @Test//(dependsOnMethods = {"addSensors"})
    public void sensorsCheck() throws Exception {
        logger.info("Open-Close contact sensors");
        EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
        for (int i = 1; i > 0; i--) {
            logger.info("Activate Contact sensors");

            activation_restoration(104,1101, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr10
            activation_restoration(104,1152, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr12
            activation_restoration(104,1231, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr13
            activation_restoration(104,1216, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr14
            activation_restoration(104,1331, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr16
            activation_restoration(104,1311, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr25
            activation_restoration(104,1127, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr8
            Thread.sleep(1000);
            enterDefaultUserCode();
            Thread.sleep(2000);
            activation_restoration(104,1123, PGSensorsActivity.INOPEN,PGSensorsActivity.INCLOSE);//gr9
            Thread.sleep(ConfigProps.longExitDelay);
            enterDefaultUserCode();
            Thread.sleep(2000);

            logger.info("Activate motion sensors");
            activation_restoration(120,1411, PGSensorsActivity.MOTIONACTIVE,PGSensorsActivity.MOTIONIDLE);//gr15
            activation_restoration(123,1441, PGSensorsActivity.MOTIONACTIVE,PGSensorsActivity.MOTIONIDLE);//gr17
            activation_restoration(120,1445, PGSensorsActivity.MOTIONACTIVE,PGSensorsActivity.MOTIONIDLE);//gr25
            activation_restoration(122,1423, PGSensorsActivity.MOTIONACTIVE,PGSensorsActivity.MOTIONIDLE);//gr20
            activation_restoration(123,1446, PGSensorsActivity.MOTIONACTIVE,PGSensorsActivity.MOTIONIDLE);//gr35

            logger.info("Activate smoke sensors");
            activation_restoration(201,1541, PGSensorsActivity.SMOKEM,PGSensorsActivity.SMOKEMREST);
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(200,1531, PGSensorsActivity.SMOKE,PGSensorsActivity.SMOKEREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(200,1531, PGSensorsActivity.GAS,PGSensorsActivity.GASREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(201,1541, PGSensorsActivity.GAS,PGSensorsActivity.GASREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate CO sensors");
            activation_restoration(220,1661, PGSensorsActivity.CO,PGSensorsActivity.COREST);
            Thread.sleep(1000);
            //emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(220,1661, PGSensorsActivity.GAS,PGSensorsActivity.GASREST);//smoke - not works for real sensor at 01/17/1
            Thread.sleep(1000);
            //emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);

            /*** Shock - not works for real sensor at 01/17/18***/


            logger.info("Activate Glass-break sensors");
            activation_restoration(160,1874, PGSensorsActivity.GB,PGSensorsActivity.GBREST);//gr13
            Thread.sleep(1000);
            activation_restoration(160,1871, PGSensorsActivity.GB,PGSensorsActivity.GBREST);//gr17

            /*** Water - not works for real sensor at 01/17/18***/
            //  addPGSensors(241,1971,0);//gr38

            logger.info("Activate Key-fob sensors");
            activation_restoration(300,1004, PGSensorsActivity.POLICEPANIC,PGSensorsActivity.POLICEPANICREST);//gr1
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(305,1009, PGSensorsActivity.AUXPANIC,PGSensorsActivity.AUXPANICREST);//gr6
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(306,1003, PGSensorsActivity.AUXPANIC,PGSensorsActivity.AUXPANICREST);//gr4
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate Keypad sensors");
            activation_restoration(371,1005, PGSensorsActivity.POLICEPANIC,PGSensorsActivity.POLICEPANICREST);//gr0
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(371,1006, PGSensorsActivity.POLICEPANIC,PGSensorsActivity.POLICEPANICREST);//gr1
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(371,1008, PGSensorsActivity.POLICEPANIC,PGSensorsActivity.POLICEPANICREST);//gr2
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);

            logger.info("Activate Aux pendant");
            activation_restoration(320,1015,  PGSensorsActivity.AUXPANIC,PGSensorsActivity.AUXPANICREST);//gr6
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(320,1016, PGSensorsActivity.AUXPANIC,PGSensorsActivity.AUXPANICREST);//gr4
            Thread.sleep(2000);
            emergency.Cancel_Emergency.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            activation_restoration(320,1018,  PGSensorsActivity.AUXPANIC,PGSensorsActivity.AUXPANICREST);//gr25
            Thread.sleep(2000);
            verifyDisarm();
            Thread.sleep(1000);

            contact.acknowledge_all_alerts();
            swipeLeft();
            Thread.sleep(1000);

            System.out.println(i);
        }
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
