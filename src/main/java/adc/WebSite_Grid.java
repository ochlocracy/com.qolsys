package adc;

import utils.Setup1;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WebSite_Grid {
    Setup1 s = new Setup1();
    ADC myADC = new ADC();
    String page_name = "adc sensors name change";
    Logger logger = Logger.getLogger(page_name);

    public WebSite_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
        myADC.webDriverSetUp();
    }
    @Parameters ({"UDID_"})
   @Test
    public void Test1 (String UDID_) throws IOException, InterruptedException {
        logger.info("********************************************************");
        logger.info("Add sensors");
        myADC.add_all_sensor_types_grid(UDID_);
        logger.info("sensors added successfully");
        TimeUnit.SECONDS.sleep(10);
    }
   // @Parameters ({"Account_"})
    @Test
    public void Test2() throws IOException, InterruptedException {
        logger.info("********************************************************");
        logger.info("Verify sensors appear on the dealer website");
        myADC.New_ADC_session("4679473");
        TimeUnit.SECONDS.sleep(5);
        myADC.driver1.findElement(By.partialLinkText("sensors")).click();
        TimeUnit.SECONDS.sleep(5);
        myADC.Request_equipment_list();
        myADC.Sensor_verification("Door/Window 1", "10","Door/Window", 2);
        myADC.Sensor_verification("Motion 2","17", "Motion", 3);
        myADC.Sensor_verification("Smoke Detector 3", "26", "Smoke Detector", 4);
        myADC.Sensor_verification("CO Detector 4","34", "CO Detector", 5);
        myADC.Sensor_verification("Glass Break 5", "13", "Glass Break", 6);
        myADC.Sensor_verification("Tilt 6", "12", "Tilt", 7);
        myADC.Sensor_verification("Other Shock 7", "13", "Shock: Others", 8);
        myADC.Sensor_verification("Freeze 8", "52", "Freeze", 9);
        myADC.Sensor_verification("Smoke-M 9", "26", "IQ Smoke: Multi-function", 10);
        myADC.Sensor_verification("Multi-Function-1 10", "38", "Water: Multi-Function", 11);
        myADC.Sensor_verification("Auxiliary Pendant 13", "6", "Medical Pendant", 12);
        myADC.Sensor_verification("Door Bell 14", "25", "Door Bell", 13);
        myADC.Sensor_verification("Occupancy Sensor 15", "25", "Occupancy", 14);
        myADC.Sensor_verification("IQ Shock", "13", "IQ Shock", 15);
        TimeUnit.SECONDS.sleep(10);
    }
    @AfterClass
    public void tearDown() {
       myADC.driver1.quit();
    }
}