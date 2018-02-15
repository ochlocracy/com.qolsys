package updateProcess;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

/**
 * Created by qolsys on 2/2/18.
 */
public class TestSanityTranszwave extends Setup{
    ExtentReports report;
    ExtentTest log;
    ExtentTest test;

    public TestSanityTranszwave() throws Exception {}

    @BeforeClass
    public void setup() throws Exception{
        setupDriver(get_UDID(),"http://127.0.1.1","4723");
    }
    @BeforeMethod
    public void addDevices() throws Exception{

        /*
        add light
        add/ dimmer
        add thermostat
        add doorlock
        add GDC
        */
    }
    @Test
    public void lightTest() throws Exception {
        /*
        swipe to light page
        p)turn light on
        d) turn light off
        p)change dimming level to halv

        */
    }











}
