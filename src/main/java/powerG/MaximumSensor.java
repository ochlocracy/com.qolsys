package powerG;

import org.apache.log4j.Logger;
import org.testng.annotations.*;
import utils.ConfigProps;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

public class MaximumSensor extends Setup {

    String page_name = "Add PG sensors + sensors activity";
    Logger logger = Logger.getLogger(page_name);

    public MaximumSensor() throws Exception {
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
        for (int i = 1; i < 122; i++) {
            deleteFromPrimary(i);
        }
        Thread.sleep(1000);
        logger.info("Adding a list of sensors");
        navigate_to_autolearn_page();

        //YOU MUST RUN 1 AT A TIME.

        /***Max powerG camera (motion) limit test */
//        addMaxPGSensors("Motion", 140, 1412, 1);//gr15
//        addMaxPGSensors("Motion", 140, 1447, 2);//gr15
//        addMaxPGSensors("Motion", 140, 1413, 1);//gr15
//        addMaxPGSensors("Motion", 140, 1446, 2);//gr15
//        addMaxPGSensors("Motion", 140, 1414, 1);//gr15
//        //should fail at the next sensor
//        addMaxPGSensors("Motion", 140, 1448, 1);//gr15

        /***Max Keypad test(370, 371, 374, 379) ***/
//        addMaxPGSensors("Keypad", 379, 1013, 0);//gr0
//        addMaxPGSensors("Keypad", 379, 1015, 1);//gr1
//        //should fail at the next sensor
//        addMaxPGSensors("Keypad", 379, 1016, 2);//gr2

        /***Max aux pendant test***/  //(32 sensors for 1.52 PG card, 120 for 2.11)
//        addMaxPGSensors("AuxPendant", 320, 1015, 6);
//        addMaxPGSensors("AuxPendant", 320, 1016, 4);
//        addMaxPGSensors("AuxPendant", 320, 1018, 25);
//        addMaxPGSensors("AuxPendant", 320, 1014, 25);
//        addMaxPGSensors("AuxPendant", 320, 1017, 6);
//        addMaxPGSensors("AuxPendant", 320, 1020, 4);
//        addMaxPGSensors("AuxPendant", 320, 1021, 25);
//        addMaxPGSensors("AuxPendant", 320, 1022, 25);
//        addMaxPGSensors("AuxPendant", 320, 1013, 6);
//        addMaxPGSensors("AuxPendant", 320, 1023, 4);
//        addMaxPGSensors("AuxPendant", 320, 1024, 25);
//        addMaxPGSensors("AuxPendant", 320, 1025, 25);
//        addMaxPGSensors("AuxPendant", 320, 1026, 6);
//        addMaxPGSensors("AuxPendant", 320, 1027, 4);
//        addMaxPGSensors("AuxPendant", 320, 1028, 25);
//        addMaxPGSensors("AuxPendant", 320, 1029, 25);
//        addMaxPGSensors("AuxPendant", 320, 1030, 6);
//        addMaxPGSensors("AuxPendant", 320, 1031, 4);
//        addMaxPGSensors("AuxPendant", 320, 1032, 25);
//        addMaxPGSensors("AuxPendant", 320, 1033, 25);
//        addMaxPGSensors("AuxPendant", 320, 1034, 6);
//        addMaxPGSensors("AuxPendant", 320, 1035, 4);
//        addMaxPGSensors("AuxPendant", 320, 1036, 25);
//        addMaxPGSensors("AuxPendant", 320, 1037, 25);
//        addMaxPGSensors("AuxPendant", 320, 1038, 6);
//        addMaxPGSensors("AuxPendant", 320, 1039, 4);
//        addMaxPGSensors("AuxPendant", 320, 1040, 25);
//        addMaxPGSensors("AuxPendant", 320, 1041, 25);
//        addMaxPGSensors("AuxPendant", 320, 1042, 6);
//        addMaxPGSensors("AuxPendant", 320, 1043, 4);
//        addMaxPGSensors("AuxPendant", 320, 1044, 25);
//        addMaxPGSensors("AuxPendant", 320, 1045, 25);
//        addMaxPGSensors("AuxPendant", 320, 1046, 6);
//        addMaxPGSensors("AuxPendant", 320, 1047, 4);
//        addMaxPGSensors("AuxPendant", 320, 1048, 25);
//        addMaxPGSensors("AuxPendant", 320, 1049, 25);
//        addMaxPGSensors("AuxPendant", 320, 1050, 6);
//        addMaxPGSensors("AuxPendant", 320, 1051, 4);
//        addMaxPGSensors("AuxPendant", 320, 1052, 25);
//        addMaxPGSensors("AuxPendant", 320, 1053, 25);
//        addMaxPGSensors("AuxPendant", 320, 1054, 6);
//        addMaxPGSensors("AuxPendant", 320, 1055, 4);
//        addMaxPGSensors("AuxPendant", 320, 1056, 25);
//        addMaxPGSensors("AuxPendant", 320, 1057, 25);
//        addMaxPGSensors("AuxPendant", 320, 1058, 6);
//        addMaxPGSensors("AuxPendant", 320, 1059, 4);
//        addMaxPGSensors("AuxPendant", 320, 1060, 25);
//        addMaxPGSensors("AuxPendant", 320, 1061, 25);
//        addMaxPGSensors("AuxPendant", 320, 1062, 6);
//        addMaxPGSensors("AuxPendant", 320, 1063, 4);
//        addMaxPGSensors("AuxPendant", 320, 1064, 25);
//        addMaxPGSensors("AuxPendant", 320, 1065, 25);
//        addMaxPGSensors("AuxPendant", 320, 1066, 6);
//        addMaxPGSensors("AuxPendant", 320, 1067, 4);
//        addMaxPGSensors("AuxPendant", 320, 1068, 25);
//        addMaxPGSensors("AuxPendant", 320, 1069, 25);
//        addMaxPGSensors("AuxPendant", 320, 1070, 6);
//        addMaxPGSensors("AuxPendant", 320, 1071, 4);
//        addMaxPGSensors("AuxPendant", 320, 1072, 25);
//        addMaxPGSensors("AuxPendant", 320, 1073, 25);
//        addMaxPGSensors("AuxPendant", 320, 1074, 6);
//        addMaxPGSensors("AuxPendant", 320, 1075, 4);
//        addMaxPGSensors("AuxPendant", 320, 1076, 25);
//        addMaxPGSensors("AuxPendant", 320, 1077, 25);
//        addMaxPGSensors("AuxPendant", 320, 1078, 6);
//        addMaxPGSensors("AuxPendant", 320, 1079, 4);
//        addMaxPGSensors("AuxPendant", 320, 1080, 25);
//        addMaxPGSensors("AuxPendant", 320, 1081, 25);
//        addMaxPGSensors("AuxPendant", 320, 1082, 6);
//        addMaxPGSensors("AuxPendant", 320, 1083, 4);
//        addMaxPGSensors("AuxPendant", 320, 1084, 25);
//        addMaxPGSensors("AuxPendant", 320, 1085, 25);
//        addMaxPGSensors("AuxPendant", 320, 1086, 6);
//        addMaxPGSensors("AuxPendant", 320, 1087, 4);
//        addMaxPGSensors("AuxPendant", 320, 1088, 25);
//        addMaxPGSensors("AuxPendant", 320, 1089, 25);
//        addMaxPGSensors("AuxPendant", 320, 1090, 6);
//        addMaxPGSensors("AuxPendant", 320, 1091, 4);
//        addMaxPGSensors("AuxPendant", 320, 1092, 25);
//        addMaxPGSensors("AuxPendant", 320, 1093, 25);
//        addMaxPGSensors("AuxPendant", 320, 1094, 6);
//        addMaxPGSensors("AuxPendant", 320, 1095, 4);
//        addMaxPGSensors("AuxPendant", 320, 1096, 25);
//        addMaxPGSensors("AuxPendant", 320, 1097, 25);
//        addMaxPGSensors("AuxPendant", 320, 1098, 6);
//        addMaxPGSensors("AuxPendant", 320, 1099, 4);
//        addMaxPGSensors("AuxPendant", 320, 1100, 25);
//        addMaxPGSensors("AuxPendant", 320, 1101, 25);
//        addMaxPGSensors("AuxPendant", 320, 1102, 6);
//        addMaxPGSensors("AuxPendant", 320, 1103, 4);
//        addMaxPGSensors("AuxPendant", 320, 1104, 25);
//        addMaxPGSensors("AuxPendant", 320, 1105, 25);
//        addMaxPGSensors("AuxPendant", 320, 1106, 6);
//        addMaxPGSensors("AuxPendant", 320, 1107, 4);
//        addMaxPGSensors("AuxPendant", 320, 1108, 25);
//        addMaxPGSensors("AuxPendant", 320, 1109, 25);
//        addMaxPGSensors("AuxPendant", 320, 1110, 6);
//        addMaxPGSensors("AuxPendant", 320, 1111, 4);
//        addMaxPGSensors("AuxPendant", 320, 1112, 25);
//        addMaxPGSensors("AuxPendant", 320, 1113, 25);
//        addMaxPGSensors("AuxPendant", 320, 1114, 6);
//        addMaxPGSensors("AuxPendant", 320, 1115, 4);
//        addMaxPGSensors("AuxPendant", 320, 1116, 25);
//        addMaxPGSensors("AuxPendant", 320, 1117, 25);
//        addMaxPGSensors("AuxPendant", 320, 1118, 6);
//        addMaxPGSensors("AuxPendant", 320, 1119, 4);
//        addMaxPGSensors("AuxPendant", 320, 1120, 25);
//        addMaxPGSensors("AuxPendant", 320, 1121, 25);
//        addMaxPGSensors("AuxPendant", 320, 1122, 6);
//        addMaxPGSensors("AuxPendant", 320, 1123, 4);
//        addMaxPGSensors("AuxPendant", 320, 1124, 25);
//        addMaxPGSensors("AuxPendant", 320, 1125, 25);
//        addMaxPGSensors("AuxPendant", 320, 1126, 6);
//        addMaxPGSensors("AuxPendant", 320, 1127, 4);
//        addMaxPGSensors("AuxPendant", 320, 1128, 25);
//        addMaxPGSensors("AuxPendant", 320, 1129, 25);
//        addMaxPGSensors("AuxPendant", 320, 1130, 6);
//        addMaxPGSensors("AuxPendant", 320, 1131, 4);
//        addMaxPGSensors("AuxPendant", 320, 1132, 25);
//        addMaxPGSensors("AuxPendant", 320, 1133, 25);
        /***Max Siren Test (400, 410) ***/ //16 is the Maximum amount able to add.
//        addMaxPGSensors("Siren", 400, 1995, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1998, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1996, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1997, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1999, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1990, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1991, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1992, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1993, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1994, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1980, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1982, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1984, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1986, 1);//gr25
//        addMaxPGSensors("Siren", 400, 1988, 0);//gr33
//        addMaxPGSensors("Siren", 410, 1989, 1);//gr25
////        17th below should fail
//        addMaxPGSensors("Siren", 410, 1985, 1);//gr25

        /***Max Repeater (Wireless Translator) test ***/ //16 is the Maximum amount able to add.
//        addMaxPGSensors("Repeater", 430, 1997, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1998, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1999, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1920, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1921, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1922, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1923, 1);//gr25
//        addMaxPGSensors("Repeater", 430, 1924, 1);//gr25
////        9th below should fail
//        addMaxPGSensors("Repeater", 430, 1997, 1);//gr25


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
