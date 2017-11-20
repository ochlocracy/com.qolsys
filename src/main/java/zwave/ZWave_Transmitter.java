package zwave;

/**
 * Created by qolsys on 10/24/17.
 *
 *
 * Methods to add
 * -Add transmitter
 * -delete transmitter
 * -pair lights
 * -pair thermostat
 * -pair door lock
 * -pair Garage Door
 * -pair Other devices
 * -pair unsupported devices
 * -turn on light
 * -turn off light
 * -turn on all lights
 * -turn off all lights
// *
// */

//public class ZWave_Transmitter extends Setup {
//// bridge will be included to the Gen2 an nodeID 2
//
//public void includeBridge() throws IOException, InterruptedException {
//        Thread.sleep(3000);
//        rt.exec(adbPath + " -s " + Gen2 + remoteNodeAdd);
//        System.out.println(adbPath + " -s " + Gen2 + remoteNodeAdd);
//        Thread.sleep(3000);
//
//        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 2");
//        Thread.sleep(50000);
//        rt.exec(adbPath + " -s " + Gen2 + remoteNodeAbort);
//        System.out.println(adbPath + " -s " + Gen2 + remoteNodeAbort);
//        }
//public void addLights(int Lights_number) throws IOException, InterruptedException {
//        Thread.sleep(3000);
//        rt.exec(adbPath + " -s " + Gen2 + remoteNodeAdd);
//        System.out.println(adbPath + " -s " + Gen2 + remoteNodeAdd);
//        Thread.sleep(3000);
//
//        for (int i = Lights_number; i>0; i--){
//        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//        System.out.println(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 1");
//        Thread.sleep(20000);
//        }
//        rt.exec(adbPath + " -s " + Gen2 + remoteNodeAbort);
//        System.out.println(adbPath + " -s " + Gen2 + remoteNodeAbort);
//
//        }
//public void addThermostat() throws IOException {
//        rt.exec(adbPath + " -s " + transmitter + " shell service call zwavetransmitservice 3 i32 3");
//        }
//
////    @BeforeMethod
////    public void capabilities_setup() throws Exception {
//////        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
////////        setup_logger(page_name);
////    }
//
//@Test
//public void addTranmitter () throws IOException, InterruptedException {
//        includeBridge();
//
//        }
//
//
//}
