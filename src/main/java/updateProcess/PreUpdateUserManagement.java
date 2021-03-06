package updateProcess;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
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

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
        servcall.set_ARM_STAY_NO_DELAY_enable();
        rt.exec("adb shell service call qservice 40 i32 0 i32 0 i32 35 i32 0 i32 0 i32 0"); //secure arming off
    }

//    @Test (priority = 1)
//    public void deleteUsers() throws InterruptedException {
//        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
//        logger.info("Delete Users If They Are Added.");
//        navigateToUserManagementPage();
//        while (user_m.Delete.isDisplayed()) {
//            user_m.Delete.click();
//            Thread.sleep(1000);
//            user_m.User_Management_Delete_User_Ok.click();
//        }
//    } //I added in a check  for if the user name already exists then it just presses okay and continues


    @Test (priority = 2)
    public void addUserUnlimited() throws InterruptedException {
        logger.info("Adding a new user NewUser with the user code 5643");
        UserManagementPage user_m = PageFactory.initElements(driver, UserManagementPage.class);
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        navigateToUserManagementPage();
        user_m.Add_User.click();
        Thread.sleep(1000);
        addUser("NewUser", "5643");
        user_m.Add_User_add_page.click();
        Thread.sleep(1000);
        try {
                user_m.User_Management_Delete_User_Ok.click();
                logger.info("User Code was already created");
        } catch (Exception e) {
        }
        try {
            home.Home_button.click();
        } catch (Exception e) {
        }
        Thread.sleep(2000);
    }

    @Test(priority = 3)
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
        verifyDisarm();
        Thread.sleep(1000);
    }

    @Test(priority = 4)
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
        Thread.sleep(1000);
        try {
                user_m.User_Management_Delete_User_Ok.click();
                logger.info("User Code was already created");
        } catch (Exception e) {
        }
        try {
            home.Home_button.click();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
    }

    @Test(priority = 5)
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
        verifyDisarm();
        Thread.sleep(1000);
    }

    @Test(priority = 6)
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
        Thread.sleep(1000);
        try {
                user_m.User_Management_Delete_User_Ok.click();
                logger.info("User Code was already created");
        } catch (Exception e) {
        }
        try {
            home.Home_button.click();
        } catch (Exception e) {
        }
        Thread.sleep(1000);
    }

    @Test(priority = 7)
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
        verifyDisarm();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}
