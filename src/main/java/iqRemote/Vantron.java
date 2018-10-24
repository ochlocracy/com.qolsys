package iqRemote;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import sensors.Sensors;
import utils.ConfigProps;
import utils.ExtentReport;
import utils.PGSensorsActivity;
import utils.Setup;

import java.io.IOException;

public class Vantron extends Setup {

    ExtentReport rep = new ExtentReport("Vantron_RemoteTest");
    Sensors sensors = new Sensors();
    PanelInfo_ServiceCalls serv = new PanelInfo_ServiceCalls();

    public Vantron() throws Exception {
        ConfigProps.init();
        PGSensorsActivity.init();
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
    }

    @Test(priority = 1)
    public void P_Tamper_SRF_Test() throws IOException, InterruptedException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);



    }
}
