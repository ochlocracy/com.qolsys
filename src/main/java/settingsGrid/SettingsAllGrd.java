package settingsGrid;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Setup1;

import java.io.IOException;

public class SettingsAllGrd {
    Setup1 s = new Setup1();
    String page_name = "settings All";
    Logger logger = Logger.getLogger(page_name);

    public SettingsAllGrd() throws Exception {}

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test(priority = 1)
    public void Test_1(String UDID_) throws Exception {
        Alarm_Photos_Test_Grid alarm_photos = new Alarm_Photos_Test_Grid();
//        Allow_Master_Code_to_access_Security_and_Arming_Test_Grid security_arming = new Allow_Master_Code_to_access_Security_and_Arming_Test_Grid();

        alarm_photos.Verify_Alarm_Photos_works(UDID_);
        Thread.sleep(2000);
    }
    @Parameters({"UDID_"})
    @Test(priority = 2)
    public void Test_2(String UDID_) throws Exception {
        Allow_Master_Code_to_access_Siren_and_Alarms_Test_Grid siren_alarm = new Allow_Master_Code_to_access_Siren_and_Alarms_Test_Grid();
        siren_alarm.Verify_Master_Code_gets_access_to_Siren_and_Alarms_page(UDID_);
        Thread.sleep(2000);
//        security_arming.Verify_Master_Code_gets_access_to_Security_and_Arming_page(UDID_);

    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}
