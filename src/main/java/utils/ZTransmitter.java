package utils;

/**
 * Created by qolsys on 12/5/17.
 */
public class ZTransmitter extends ConfigPropsBase {

    public static String addDoorlock;
    // clear door lock plus nodeID
    // add light
    // clear light plus nodeID
    //add thermostat
    // clear thermostat plus nodeID
    // add GDC
    // clear gdc plus nodeID

    private static ZTransmitter instance;


    public ZTransmitter() throws Exception {
        super("transmitter.properties");
        addDoorlock = getString("doorlock.inclusion");
    }

    public static void init() throws Exception {
        if (instance == null) {
            instance = new ZTransmitter();
        }
    }
}


