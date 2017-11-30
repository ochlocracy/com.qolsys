package adcSanityTests;

import adc.ADC;
import panel.ImageSensor_ServiceCalls;
import utils.Setup;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by nchortek on 8/17/17.
 */
public class ImageSensorScratch extends Setup {

    String page_name = "Image Sensor Scratch";
    Logger logger = Logger.getLogger(page_name);
    ImageSensor_ServiceCalls servcall = new ImageSensor_ServiceCalls();
    ADC adc = new ADC();

    public ImageSensorScratch() throws Exception {
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
        adc.webDriverSetUp();
    }

    @Test
    public void image_sensors() throws IOException, InterruptedException {
        //int three_sec = 3000;

        /*servcall.EVENT_IMAGE_SENSOR_LEARNED();
        Thread.sleep(three_sec);
        servcall.EVENT_IMAGE_SENSOR_TRIPPED();
        Thread.sleep(three_sec);
        servcall.EVENT_IMAGE_SENSOR_SUPERVISION_PING();
        Thread.sleep(three_sec);
        servcall.EVENT_IMAGE_SENSOR_NEW_IMAGE();
        Thread.sleep(three_sec);
        servcall.EVENT_IMAGE_SENSOR_LOW_BATTERY();
        Thread.sleep(three_sec);
        servcall.EVENT_IMAGE_SENSOR_TAMPERED();
        Thread.sleep(three_sec);*/

        adc.New_ADC_session(adc.getAccountId());
        adc.get_image_sensors();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        adc.driver1.quit();
        driver.quit();
    }

}
