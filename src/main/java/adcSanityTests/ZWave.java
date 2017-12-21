package adcSanityTests;

import utils.ConfigProps;
import utils.Setup;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static utils.ConfigProps.transmitter;

public class ZWave extends Setup {

    String remoteNodeAdd = " shell service call qservice 1 i32 0 i32 1560 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";
    String remoteNodeAbort = " shell service call qservice 1 i32 0 i32 1561 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0 i32 0";

    public ZWave() throws Exception {
        ConfigProps.init();

    }

    // bridge will be included to the Gen2 an nodeID 2
    public void includeBridge() throws IOException, InterruptedException {
        Thread.sleep(3000);
        rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAdd);
        System.out.println(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAdd);
        Thread.sleep(3000);

        rt.exec(ConfigProps.adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
        Thread.sleep(50000);
        rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAbort);
        System.out.println(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAbort);
    }
    public void addLights(int Lights_number) throws IOException, InterruptedException {
        Thread.sleep(3000);
        rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAdd);
        System.out.println(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAdd);
        Thread.sleep(3000);

        for (int i = Lights_number; i>0; i--){
        rt.exec(ConfigProps.adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
        System.out.println(ConfigProps.adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
        Thread.sleep(20000);
        }
        rt.exec(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAbort);
        System.out.println(ConfigProps.adbPath + " -s " + ConfigProps.primary + remoteNodeAbort);

    }
    public void addThermostat() throws IOException {
        rt.exec(ConfigProps.adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(ConfigProps.primary, "http://127.0.1.1", "4723");
////        setupLogger(page_name);
    }


    @Test
    public void addTranmitter () throws IOException, InterruptedException {
       includeBridge();

    }
    @Test
    public void addLigth() throws IOException, InternalError{

    }

}
