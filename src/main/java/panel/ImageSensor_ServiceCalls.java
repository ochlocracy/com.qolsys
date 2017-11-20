package panel;

import org.apache.log4j.Logger;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class ImageSensor_ServiceCalls extends Setup {
    public Logger logger = Logger.getLogger(this.getClass().getName());
    public Runtime rt = Runtime.getRuntime();


    public ImageSensor_ServiceCalls() throws Exception { ConfigProps.init();}

    public void EVENT_IMAGE_SENSOR_LEARNED() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1542 i32 6 i32 0 i32 0 i32 100 i32 0 i32 0 i32 1";
        rt.exec(command);    }
    public void EVENT_IMAGE_SENSOR_TRIPPED() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1543 i32 6 i32 0 i32 0 i32 60 i32 1 i32 0 i32 0";
        rt.exec(command);    }
    public void EVENT_IMAGE_SENSOR_SUPERVISION_PING() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1544 i32 6 i32 0 i32 0 i32 60 i32 0 i32 0 i32 0";
        rt.exec(command);    }
    public void EVENT_IMAGE_SENSOR_TAMPERED() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1545 i32 6 i32 0 i32 0 i32 60 i32 0 i32 0 i32 0";
        rt.exec(command);    }
    public void EVENT_IMAGE_SENSOR_LOW_BATTERY() throws IOException, InterruptedException {
        String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1546 i32 6 i32 0 i32 0 i32 60 i32 0 i32 0 i32 0";
        rt.exec(command);    }

    public void EVENT_IMAGE_SENSOR_NEW_IMAGE() throws IOException, InterruptedException {
         String command = ConfigProps.adbPath + " shell service call qservice 1 i32 0 i32 1547 i32 6 i32 0 i32 0 i32 60 i32 0 i32 0 i32 0";
         rt.exec(command);    }


}
