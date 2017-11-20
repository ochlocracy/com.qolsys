package updateProcess;

import panel.PanelInfo_ServiceCalls;
import utils.Setup;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class PostUpdate_WiFi extends Setup{
    String page_name = "Verify Wi-Fi connection after upgrade";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public PostUpdate_WiFi() throws Exception {}

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver( get_UDID(),"http://127.0.1.1", "4723");
        setup_logger(page_name);
    }

    @Test
    public void SASW_008() throws Exception {
        servcall.get_WiFi();
        servcall.get_WiFi_name();
        logger.info(" SASW 008 Pass: Wi-Fi won't be enabled and connected to the network after upgrade.");
    }
    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }

}
