package updateProcess;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import panel.HomePage;
import panel.PanelInfo_ServiceCalls;
import panel.UserManagementPage;
import utils.Setup;

import java.io.IOException;
import java.util.List;

public class PreUpdateUserManagement extends Setup {

    String page_name = "PreUpdate User Management set up";
    Logger logger = Logger.getLogger(page_name);
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    public PreUpdateUserManagement() throws Exception {}

    public void addUser(String Name, String UserCode) throws InterruptedException {
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        user_m.Add_User_Name_field.sendKeys(Name);
        user_m.Add_User_Code_field.sendKeys(UserCode);
        user_m.Add_Confirm_User_Code_field.sendKeys(UserCode);
        try {
            driver.hideKeyboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkCodes(List<WebElement> li, List<String> exp, int size) {
        logger.info("Checking user codes on adc");
        for (int i = 2; i < size; i++) {
            String s1 = li.get(i).getText().split(" ")[0];
            String s2 = exp.get(i - 2);
            if (s1.equals(s2))
                logger.info("Pass: User Code " + s2 + " is displayed.");
            else
                logger.info("Fail: " + s1 + " is displayed. Expected " + s2);
        }
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {
        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
        setup_logger(page_name);
        servcall.set_ARM_STAY_NO_DELAY_enable();
    }

    @Test
    public void addUserUnlimited() throws InterruptedException {
        logger.info("Adding a new user NewUser with the user code 5643");
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewUser", "5643");
        user_m.Add_User_add_page.click();
        home.Home_button.click();
        Thread.sleep(4000);
    }

    @Test(priority = 1)
    public void verifyNewUserCodeWorks() throws Exception {
        logger.info("Verifying a new user code is working correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Five.click();
        home.Six.click();
        home.Four.click();
        home.Three.click();
        Thread.sleep(1000);
        verify_disarm();
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void addMasterUnlimited() throws InterruptedException {
        logger.info("Adding a new Master NewMaster with the code 3331");
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewMaster", "3331");
        user_m.Add_User_Type_options.click();
        user_m.User_Type_Master.click();
        user_m.Add_User_add_page.click();
        home.Home_button.click();
        Thread.sleep(1000);
    }

    @Test(priority = 3)
    public void verifyNewMasterCodeWorks() throws Exception {
        logger.info("Verifying a new user code is working correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Three.click();
        home.Three.click();
        home.Three.click();
        home.One.click();
        Thread.sleep(1000);
        verify_disarm();
        Thread.sleep(1000);
    }

    @Test(priority = 4)
    public void addGuestUnlimited() throws InterruptedException {
        logger.info("Adding a new Guest NewGuest with the code 8800");
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewGuest", "8800");
        user_m.Add_User_Type_options.click();
        user_m.User_Type_Guest.click();
        user_m.Add_User_add_page.click();
        home.Home_button.click();
        Thread.sleep(1000);
    }

    @Test(priority = 5)
    public void verifyNewGuestCodeWorks() throws Exception {
        logger.info("Verifying a new user code is working correctly");
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ARM_STAY();
        Thread.sleep(3000);
        home.DISARM.click();
        home.Eight.click();
        home.Eight.click();
        home.Zero.click();
        home.Zero.click();
        Thread.sleep(1000);
        verify_disarm();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }
}
