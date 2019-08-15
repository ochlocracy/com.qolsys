package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class UserManagementPageTest extends Setup {

    String page_name = "User Management page";
    Logger logger = Logger.getLogger(page_name);

    public UserManagementPageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_User_Management_page() throws Exception {
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        UserManagementPage um = PageFactory.initElements(driver, UserManagementPage.class);
        navigateToAdvancedSettingsPage();
        adv.USER_MANAGEMENT.click();
        elementVerification(um.User_Management_Id, "User Management Id");
        elementVerification(um.User_Management_Name, "User Management Name");
        elementVerification(um.User_Management_Type, "User Management Type");
        elementVerification(um.User_Management_Expiration_Date, "User Management Expiration Date");
        elementVerification(um.User_Management_Edit, "User Management Edit");
        elementVerification(um.User_Management_Delete, "User Management Delete");
        elementVerification(um.User_Management_Admin_Name, "User Management Admin Name");
        elementVerification(um.User_Management_Installer_Name, "User Management Installer Name");
        elementVerification(um.User_Management_Dealer_Name, "User Management Dealer Name");
        elementVerification(um.Add_User, "Add User button");
        um.Add_User.click();
        Thread.sleep(1000);
        elementVerification(um.Add_User_Name, "Add User Name");
        elementVerification(um.Add_User_Name_field, "Add User Name field");
        um.Add_User_Name_field.sendKeys("Tester1");
//       driver.hideKeyboard();
        elementVerification(um.Add_User_Code, "Add User Code");
        elementVerification(um.Add_User_Code_field, "Add User Code field");
        um.Add_User_Code_field.sendKeys("5555");
        //      driver.hideKeyboard();
        elementVerification(um.Add_Confirm_User_Code, "Add User Confirm User Code");
        elementVerification(um.Add_Confirm_User_Code_field, "Add User Confirm User Code field");
        um.Add_Confirm_User_Code_field.sendKeys("5555");
//        try {
//            driver.hideKeyboard();
//        } finally {
            elementVerification(um.Add_User_Type, "Add User Type");
            elementVerification(um.Add_User_Type_options, "Add User Type options");
            um.Add_User_Type_options.click();
            Thread.sleep(1000);
            elementVerification(um.User_Type_User, "User Type User");
            elementVerification(um.User_Type_Master, "User Type Master");
            elementVerification(um.User_Type_Guest, "User Type Guest");
            //um.User_Type_Guest.click();
            elementVerification(um.Add_User_Expiration_Date, "Add User Expiration Date");
            elementVerification(um.Add_User_Expiration_Date_entry, "Add User Expiration Date widget");
            um.Add_User_Expiration_Date_entry.click();
            elementVerification(um.Calendar_Clear, "Calendar Clear button");
            elementVerification(um.Calendar_Cancel, "Calendar Cancel button");
            elementVerification(um.Calendar_Ok, "Calendar Ok button");
            swipeVertical();
            elementVerification(um.Add_User_add_page, "Add User button");
            um.Add_User_add_page.click();
            Thread.sleep(4000);
            WebElement new_user = driver.findElement(By.xpath("//android.widget.TextView[@text='2']"));
            new_user.isDisplayed();
            Thread.sleep(2000);
            tap(1185, 373);
            Thread.sleep(1000);
            elementVerification(um.User_Management_Delete_User_title, "Delete User message title");
            elementVerification(um.User_Management_Delete_User_message, "Delete User message text");
            elementVerification(um.User_Management_Delete_User_Cancel, "Delete User message Cancel button");
            elementVerification(um.User_Management_Delete_User_Ok, "Delete User message Ok button");
            um.User_Management_Delete_User_Ok.click();
        }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}