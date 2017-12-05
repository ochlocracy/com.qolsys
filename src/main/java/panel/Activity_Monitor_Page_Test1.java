package panel;

import sensors.Sensors;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.Setup1;

import java.io.IOException;

public class Activity_Monitor_Page_Test1 {

    Setup1 s =new Setup1();
    String page_name = "Activity Monitor page testing ";
    Logger logger = Logger.getLogger(page_name);
    Sensors sensors = new Sensors();
    private String open = "06 00";
    private String close = "04 00";

    public Activity_Monitor_Page_Test1() throws Exception {
        ConfigProps.init();
    }

    public void swipe_vertical1() throws InterruptedException, IOException {
        int starty = 620;
        int endy = 250;
        int startx = 1000;
        s.getDriver().swipe(startx, starty, startx, endy, 3000);
        Thread.sleep(2000);}

    public void delete_sensor(String UDID_, int zone) throws IOException {
        String delete = "shell service call qservice 51 i32 " + zone;
        sensors.rt.exec( ConfigProps.adbPath + " -s "+ UDID_+ " " + delete);
    }
//    public void delete_all() throws IOException {
//        for (int i = 1; i < 8; i++) {
//            delete_sensor(i);
//        }
//    }

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
       s.setCapabilities(URL_);
       s.setup_logger(page_name, UDID_);
    }

    @Parameters ({"UDID_"})
    @org.testng.annotations.Test
    public void Test1_Check_all_elements_on_Activity_Monitor_page(String UDID_) throws Exception {
        ActivityMonitorPage activity = PageFactory.initElements(s.getDriver(), ActivityMonitorPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        logger.info("Adding sensors...");
        s.add_primary_call(1, 25, 6619814, 1, UDID_);
        Thread.sleep(2000);
        s.navigate_to_Settings_page();
        Thread.sleep(2000);
        settings.ACTIVITY_MONITOR.click();
        Thread.sleep(2000);
        s.element_verification(UDID_, activity.Quick_Access, "Quick Access");
        s.element_verification(UDID_, activity.Quick_Access_img, "Quick Access image");
        s.element_verification(UDID_, activity.Safty_State, "Safety State icon");
        s.element_verification(UDID_, activity.Safety_State_txt, "Safety State text");
        Thread.sleep(2000);
        if(activity.Safety_State_txt.getText().equals("Press to Deactivate")){
            logger.info(UDID_ + " Pass: Correct Safety state text: "+activity.Safety_State_txt.getText());
        } else { s.take_screenshot();
            logger.info(UDID_ + "Failed: Incorrect Safety state text: "+activity.Safety_State_txt.getText());}
        s.element_verification(UDID_, activity.Safety_Active, "Safety Active tab");
        s.element_verification(UDID_, activity.Safety_All, "Safety All tab");
        activity.Quick_Access_img.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        s.element_verification(UDID_, activity.Quick_Access_CountDown, "Quick Access countdown window");
        s.tap(110,620);
        Thread.sleep(1000);
        activity.Safty_State.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.element_verification(UDID_, activity.Safty_State, "Safety State icon");
        s.element_verification(UDID_, activity.Safety_State_txt, "Safety State text");
        if(activity.Safety_State_txt.getText().equals("Press to Activate")){
            logger.info(UDID_ + "Pass: Correct Safety state text: "+activity.Safety_State_txt.getText());
        } else {  s.take_screenshot();
            logger.info(UDID_ + "Failed: Incorrect Safety state text: "+activity.Safety_State_txt.getText());}
        s.element_verification(UDID_, activity.Safety_Active, "Safety Active tab");
        s.element_verification(UDID_, activity.Safety_All, "Safety All tab");
        s.element_verification(UDID_, activity.Safety_Bypass, "Safety Bypass tab");
        Thread.sleep(1000);
        activity.Safty_State.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        delete_sensor(UDID_,1);
        Thread.sleep(1000);
    }

    @Parameters ({"UDID_"})
   @Test
    public void Test2_Check_Activity_Monitor_behavior(String UDID_) throws Exception {
        ActivityMonitorPage activity = PageFactory.initElements( s.getDriver(), ActivityMonitorPage.class);
        SettingsPage settings = PageFactory.initElements(s.getDriver(), SettingsPage.class);
        HomePage home = PageFactory.initElements(s.getDriver(), HomePage.class);
        ContactUs contact = PageFactory.initElements(s.getDriver(), ContactUs.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(3000);
        logger.info("Adding sensors...");
        s.add_primary_call(1, 25, 6619814, 1, UDID_);
        s.add_primary_call(2, 8, 6619815, 1, UDID_);
        s.add_primary_call(3, 9, 6619816, 1, UDID_);
        s.add_primary_call(4, 25, 5570631, 2, UDID_);
        s.add_primary_call(5, 25, 6361651, 21, UDID_);
        s.add_primary_call(6, 25, 6405546, 109, UDID_);
        s.add_primary_call(7, 25, 8716449, 114, UDID_);
        Thread.sleep(4000);
        s.navigate_to_Settings_page();
        Thread.sleep(2000);
        settings.ACTIVITY_MONITOR.click();
        Thread.sleep(3000);
        activity.Safety_All.click();
        Thread.sleep(3000);
        WebElement dw1 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Door/Window 1']"));
        s.verify_sensor_is_displayed(UDID_, dw1);
        WebElement dw2 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Door/Window 2']"));
        s.verify_sensor_is_displayed(UDID_, dw2);
        WebElement dw3 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Door/Window 3']"));
        s.verify_sensor_is_displayed(UDID_, dw3);
        WebElement motion4 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Motion 4']"));
        s.verify_sensor_is_displayed(UDID_, motion4);
        WebElement auxil5 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Auxiliary Pendant 5']"));
        s.verify_sensor_is_displayed(UDID_, auxil5);
        WebElement doorbell6 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Door Bell 6']"));
        s.verify_sensor_is_displayed(UDID_, doorbell6);
        swipe_vertical1();
        Thread.sleep(1000);
        WebElement occupancy7 =  s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Occupancy Sensor 7']"));
        occupancy7.getText();
        s.verify_sensor_is_displayed(UDID_, occupancy7);
        Thread.sleep(2000);
        System.out.println(ConfigProps.adbPath + " -s " +UDID_);
        s.primary_call(UDID_,"65 02 7A",open); //open  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",open); //open  DW3
        Thread.sleep(2000);
        s.verify_in_alarm();
        Thread.sleep(1000);
        s.verify_sensor_is_displayed(UDID_, dw2);
        s.verify_sensor_is_displayed(UDID_, dw3);
        Thread.sleep(1000);
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 7A",close); //close  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",close); //close  DW3
        Thread.sleep(2000);
        s.navigate_to_Settings_page();
        settings.ACTIVITY_MONITOR.click();
        Thread.sleep(3000);
        activity.Safety_All.click();
        Thread.sleep(2000);
        activity.Safty_State.click();
        s.enter_default_user_code();
        Thread.sleep(2000);
        activity.Safety_All.click();
        Thread.sleep(2000);
        s.tap(815,335);
        Thread.sleep(1000);
        s.tap(815,420);
        activity.Safty_State.click();
        Thread.sleep(2000);
        s.enter_default_user_code();
        Thread.sleep(1000);
        settings.Home_button.click();
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 7A",open); //open  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",open); //open  DW3
        Thread.sleep(1000);
        s.verify_disarm(UDID_);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 7A",close); //close  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",close); //close  DW3
        Thread.sleep(2000);
        s.ARM_STAY();
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 7A",open); //open  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",open); //open  DW3
        Thread.sleep(1000);
        s.verify_armstay(UDID_);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 7A",close); //close  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",close); //close  DW3
        home.DISARM.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        s.ARM_AWAY(15);
        s.primary_call(UDID_,"65 02 7A",open); //open  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",open); //open  DW3
        Thread.sleep(1000);
        s.verify_armaway(UDID_);
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 7A",close); //close  DW2
        Thread.sleep(1000);
        s.primary_call(UDID_,"65 02 8A",close); //close  DW3
        home.ArwAway_State.click();
        s.enter_default_user_code();
        Thread.sleep(1000);
        contact.acknowledge_all_alerts();
        for (int i = 1; i < 8; i++)
        delete_sensor(UDID_, i);
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}
