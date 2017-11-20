package utils;

public class SensorsActivity extends ConfigPropsBase {

    public static String OPEN;
    public static String CLOSE;
    public static String ACTIVATE;
    public static String RESTORE;
    public static String TAMPER;
    private static SensorsActivity instance;

    private SensorsActivity() throws Exception {
        super("sensors.properties");

        OPEN = getString("openSensor");
        CLOSE = getString("closeSensor");
        ACTIVATE = getString("activateSensor");
        RESTORE = getString("restoreSensor");
        TAMPER = getString("tamperSensor");
    }

    public static void init() throws Exception {
        if (instance == null) {
            instance = new SensorsActivity();
        }
    }
}
