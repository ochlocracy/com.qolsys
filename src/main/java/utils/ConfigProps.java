package utils;

public class ConfigProps extends ConfigPropsBase {

    public static String adbPath;
    public static String appiumPath;
    public static String nodePath;
    public static String transmitter;
    public static String primary;
    public static String url;
    public static String login;
    public static String password;
    public static String srgTransmitter;
    public static int normalExitDelay;
    public static int normalEntryDelay;
    public static int longExitDelay;
    public static int longEntryDelay;
    private static ConfigProps instance;

    private ConfigProps() throws Exception {
        super("config.properties");
        srgTransmitter =getString("srgTransmitter");
        adbPath = getString("adb.path");
        appiumPath = getString("appium.path");
        nodePath = getString("node.path");
        transmitter = getString("transmitter");
        primary = getString("primary");
        url = getString("url");
        login = getString("login");
        password = getString("password");
        normalExitDelay = Integer.parseInt(getString("normalExitDelay"));
        normalEntryDelay = Integer.parseInt(getString("normalEntryDelay"));
        longExitDelay = Integer.parseInt(getString("longExitDelay"));
        longEntryDelay = Integer.parseInt(getString("longEntryDelay"));
    }

    public static void init() throws Exception {
        if (instance == null) {
            instance = new ConfigProps();
        }
    }
}
