package settingsGrid;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import panel.*;
import utils.Setup1;

import java.io.IOException;

public class Dealer_Code_Test_Grid {
    Setup1 s = new Setup1();
    String page_name = "Dealer Code change";
    Logger logger = Logger.getLogger(page_name);

    public Dealer_Code_Test_Grid() throws Exception {}
    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws Exception {
        s.setCapabilities(URL_);
        s.setup_logger(page_name, UDID_);
    }
    @Parameters({"UDID_"})
    @Test
    public void Verify_Dealer_Code_Change(String UDID_) throws Exception {
        Settings_Page settings =  PageFactory.initElements(s.getDriver(), Settings_Page.class);
        Security_Arming_Page arming = PageFactory.initElements(s.getDriver(), Security_Arming_Page.class);
        Advanced_Settings_Page adv = PageFactory.initElements(s.getDriver(), Advanced_Settings_Page.class);
        Installation_Page inst = PageFactory.initElements(s.getDriver(), Installation_Page.class);
        User_Management_Page user = PageFactory.initElements(s.getDriver(), User_Management_Page.class);
        s.navigate_to_Advanced_Settings_page();
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        arming.Dealer_Code.click();
        user.Add_User_Name_field.clear();
        logger.info("Changing Dealer name");
        user.Add_User_Name_field.sendKeys("NewDealer");
        user.Add_User_Code_field.clear();
        logger.info("Changing Dealer password");
        user.Add_User_Code_field.sendKeys("5555");
        s.driver.hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("5555");
        Thread.sleep(2000);
        user.User_Management_Save.click();
        Thread.sleep(3000);
        settings.Back_button.click();
        Thread.sleep(3000);
        settings.Back_button.click();
        Thread.sleep(3000);
        adv.USER_MANAGEMENT.click();
        logger.info("Verify Dealer name changed");
        s.getDriver().findElement(By.xpath("//android.widget.TextView[@text='NewDealer']")).isDisplayed();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        settings.Back_button.click();
        Thread.sleep(2000);
        logger.info("Verify old Dealer code does not work");
        settings.ADVANCED_SETTINGS.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        settings.Two.click();
        if(settings.Invalid_User_Code.isDisplayed()){
            logger.info(UDID_ + " Pass: old Dealer code does not work");
        }else { logger.info(UDID_ + "Failed: old Dealer code works");}
        Thread.sleep(2000);
        logger.info("Verify new Dealer code works");
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        settings.Five.click();
        if(adv.INSTALLATION.isDisplayed()){
        logger.info(UDID_ + "Pass: new Dealer code works as expected");}
        adv.INSTALLATION.click();
        Thread.sleep(2000);
        inst.SECURITY_AND_ARMING.click();
        Thread.sleep(2000);
        arming.Dealer_Code.click();
        Thread.sleep(2000);
        user.Add_User_Name_field.clear();
        user.Add_User_Name_field.sendKeys("Dealer");
        user.Add_User_Code_field.clear();
        user.Add_User_Code_field.sendKeys("2222");
        s.getDriver().hideKeyboard();
        user.Add_Confirm_User_Code_field.click();
        user.Add_Confirm_User_Code.clear();
        user.Add_Confirm_User_Code.sendKeys("2222");
        user.User_Management_Save.click();
        Thread.sleep(2000);
    }
    @AfterClass
    public void tearDown () throws IOException, InterruptedException {
        s.log.endTestCase(page_name);
        s.getDriver().quit();
    }
}