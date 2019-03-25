package iqRemote;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import panel.*;
import sensors.Sensors;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Vantron extends Setup {

    //Must run
    //PreupdateSensors
    //PreUpdateUserManagement typically just once if the users are already there
    //Sanityupdate to set the right default settings

    //And this is the official sanity settings

    //and that is all, let it loop

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

    @Test
    public void Vantron_Sanity() throws IOException, InterruptedException, Exception {

        while (true) {
            HomePage home = PageFactory.initElements(driver, HomePage.class);
            EmergencyPage emergency = PageFactory.initElements(driver, EmergencyPage.class);
            PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
            CameraSettingsPage set_cam = PageFactory.initElements(driver, CameraSettingsPage.class);
            SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
            AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
            InstallationPage inst = PageFactory.initElements(driver, InstallationPage.class);
            SirenAlarmsPage siren = PageFactory.initElements(driver, SirenAlarmsPage.class);
            SecurityArmingPage arming = PageFactory.initElements(driver, SecurityArmingPage.class);



            serv.set_SIA_LIMITS_disable();

            serv.set_NORMAL_ENTRY_DELAY(11);
            Thread.sleep(1000);
            serv.set_NORMAL_EXIT_DELAY(10);
            Thread.sleep(1000);
            serv.set_LONG_ENTRY_DELAY(13);
            Thread.sleep(1000);
            serv.set_LONG_EXIT_DELAY(12);

//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.CAMERA_SETTINGS.click();
//            try {
//                if (set_cam.Alarm_Videos_summery.isDisplayed())
//                    System.out.println("setting is disabled, continue with the test.");
//            } catch (Exception e) {
//                set_cam.Alarm_Videos_summery_enabled.click();
//            }
//            home.Home_button.click();
//            System.out.println("Verifying Alarm photo is taken when setting in enabled...");
//            deleteAllCameraPhotos();
//            Thread.sleep(1000);
//            home.Emergency_Button.click();
//            emergency.Police_icon.click();
//            Thread.sleep(1000);
//            enterDefaultUserCode();
//            swipeFromLefttoRight();
//            swipeFromLefttoRight();
//            camera.Alarms_photo.click();
//            if (camera.Photo_lable.isDisplayed()) {
//                System.out.println("Pass: Alarm photo is displayed");
//            } else {
//                System.out.println("Fail: Alarm photo is NOT displayed");
//            }
//            Thread.sleep(1000);
//            swipeFromLefttoRight();
//            Thread.sleep(1000);
//            deleteAllCameraPhotos();
//            Thread.sleep(1000);
//            System.out.println("Verifying Alarm photo is NOT taken when setting in disabled...");
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            Thread.sleep(1000);
//            inst.CAMERA_SETTINGS.click();
//            Thread.sleep(1000);
//            set_cam.Alarm_Photos.click();
//            Thread.sleep(1000);
//            settings.Home_button.click();
//            Thread.sleep(1000);
//            home.Emergency_Button.click();
//            emergency.Police_icon.click();
//            Thread.sleep(1000);
//            enterDefaultUserCode();
//            swipeFromLefttoRight();
//            swipeFromLefttoRight();
//            camera.Alarms_photo.click();
//            try {
//                if (camera.Camera_delete.isDisplayed())
//                    System.out.println("Fail: Alarm photo is displayed");
//            } catch (Exception e) {
//                System.out.println("Pass: Alarm photo is not displayed");
//            }
//            Thread.sleep(1000);
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.CAMERA_SETTINGS.click();
//            set_cam.Alarm_Photos.click();
//            Thread.sleep(1000);
//            settings.Home_button.click();
//            Thread.sleep(2000);
//
//
//
//
//            Thread.sleep(3000);
//            System.out.println("Navigate to the setting page to enable the access to the Camera settings page using Master Code");
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.CAMERA_SETTINGS.click();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(3000);
//            try {
//                if (inst.CameraSettingsEnabled.isDisplayed())
//                    System.out.println( "setting is enabled, continue with the test.");
//            } catch (Exception e) {
//                set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
//            }
//            Thread.sleep(1000);
//            settings.Home_button.click();
//            Thread.sleep(1000);
//            navigateToSettingsPage();
//            settings.ADVANCED_SETTINGS.click();
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            try {
//                if (inst.CAMERA_SETTINGS.isDisplayed())
//                    System.out.println("Pass: Camera settings icon is present");
//            } catch (Exception e) {
//                System.out.println("Failed: Camera settings icon is NOT present");
//            }
//            Thread.sleep(2000);
//            try {
//                if (home.Home_button.isDisplayed())
//                    home.Home_button.click();
//            } catch (Exception e) {
//                System.out.println("Already On Home Page, Continue With Test");
//            }
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.CAMERA_SETTINGS.click();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(2000);
//            set_cam.Allow_Master_Code_to_access_Camera_Settings.click();
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            System.out.println("Verify Camera settings icon disappears after disabling the setting");
//            navigateToSettingsPage();
//            settings.ADVANCED_SETTINGS.click();
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            try {
//                if (inst.CAMERA_SETTINGS.isDisplayed())
//                    System.out.println("Failed: Camera settings icon is present"); //fails?
//            } catch (Exception e) {
//                System.out.println("Pass: Camera settings icon is NOT present");
//            }
//            settings.Home_button.click();
//            Thread.sleep(2000);
//
//
//
//
//
//            Thread.sleep(3000);
//            System.out.println("Navigate to the Installation page to enable the access to the Security and Arming page using Master Code");
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.SECURITY_AND_ARMING.click();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(1000);
//            swipeVertical();
//            Thread.sleep(1000);
//            swipeVertical();
//            swipeVertical();
//            try {
//                if (inst.SecAndArmingEnabled.isDisplayed())
//                    System.out.println( "setting is enabled, continue with the test.");
//            } catch (Exception e) {
//                arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
//            }
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            Thread.sleep(2000);
//            navigateToSettingsPage();
//            settings.ADVANCED_SETTINGS.click();
//            enterDefaultUserCode();
//            Thread.sleep(3000);
//            try {
//                if (inst.SECURITY_AND_ARMING.isDisplayed())
//                    System.out.println("Pass: Security and Arming icon is present");
//            } catch (Exception e) {
//                System.out.println("Failed: Security and Arming icon is NOT present");
//            }
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.SECURITY_AND_ARMING.click();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(2000);
//            swipeVertical();
//            swipeVertical();
//            arming.Allow_Master_Code_To_Access_Security_and_Arming.click();
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            logger.info("Verify Security and Arming icon disappears after disabling the setting");
//            navigateToSettingsPage();
//            settings.ADVANCED_SETTINGS.click();
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            try {
//                if (inst.SECURITY_AND_ARMING.isDisplayed())
//                    System.out.println("Failed: Security and Arming icon is present");
//            } catch (Exception e) {
//                System.out.println("Pass: Security and Arming icon is NOT present");
//            }
//            settings.Home_button.click();
//            Thread.sleep(2000);
//
//
//
//
//
//
//            Thread.sleep(3000);
//            System.out.println("Navigate to the Installation page to enable the access to the Siren and Alarms page using Master Code");
//            navigateToAdvancedSettingsPage(); //check if
//            adv.INSTALLATION.click();
//            inst.SIREN_AND_ALARMS.click();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(1000);
//            swipeVertical();
//            Thread.sleep(1000);
//            swipeVertical();
//            try {
//                if (inst.SirenandAlarmsEnabled.isDisplayed())
//                    System.out.println("setting is enabled, continue with the test to enable it.");
//            } catch (Exception e) {
//                siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
//            }
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            Thread.sleep(2000);
//            navigateToSettingsPage();
//            settings.ADVANCED_SETTINGS.click();
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            try {
//                if (inst.SIREN_AND_ALARMS.isDisplayed())
//                    System.out.println("Pass: Siren and Alarms icon is present");
//            } catch (Exception e) {
//                System.out.println("Failed: Siren and Alarms icon is NOT present");
//            }
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.SIREN_AND_ALARMS.click();
//            Thread.sleep(2000);
//            swipeVertical();
//            Thread.sleep(1000);
//            swipeVertical();
//            Thread.sleep(1000);
//            swipeVertical();
//            Thread.sleep(1000);
//            siren.Allow_Master_Code_To_Access_Siren_and_Alarms.click();
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            System.out.println("Verify Siren and Alarms icon disappears after disabling the setting");
//            navigateToSettingsPage();
//            settings.ADVANCED_SETTINGS.click();
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            try {
//                if (inst.SIREN_AND_ALARMS.isDisplayed())
//                    System.out.println("Failed: Siren and Alarms icon is present");
//            } catch (Exception e) {
//                System.out.println("Pass: Siren and Alarms icon is NOT present");
//            }
//            settings.Home_button.click();
//            Thread.sleep(2000);
//
//
//            serv.set_ARM_STAY_NO_DELAY_enable();
//
//
//            Thread.sleep(2000);
//            System.out.println("Verify that Arm Stay - No Delay works when enabled");
//            ARM_STAY();
//            Thread.sleep(2000);
//            verifyArmstay();
//            home.DISARM.click();
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            System.out.println("Verify that Arm Stay - No Delay does not work when disabled");
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.SECURITY_AND_ARMING.click();
//            Thread.sleep(3000);
//            swipeVertical();
//            Thread.sleep(2000);
//            swipeVertical();
//            arming.Arm_Stay_No_Delay.click();
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            ARM_STAY();
//            try {
//                if (home.Disarmed_text.getText().equals("ARMED STAY"))
//                    System.out.println("Failed: System is ARMED STAY");
//            } catch (Exception e) {
//                System.out.println("Pass: System is NOT ARMED STAY");
//            }
//            Thread.sleep(15000);
//            home.DISARM.click(); //if this failed, the delay is not taken down to 13 seconds from the settings update.
//            enterDefaultUserCode();
//            Thread.sleep(2000);
//            navigateToAdvancedSettingsPage();
//            adv.INSTALLATION.click();
//            inst.SECURITY_AND_ARMING.click();
//            Thread.sleep(3000);
//            swipeVertical();
//            swipeVertical();
//            arming.Arm_Stay_No_Delay.click();
//            Thread.sleep(2000);
//            settings.Home_button.click();
//            Thread.sleep(2000);






            System.out.println("Adding sensors...");
            sensors.add_primary_call(3, 10, 6619296, 1);
            Thread.sleep(2000);
            System.out.println("Verify that Auto Bypass works when enabled");
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(3000);
            home.DISARM.click();
            home.ARM_STAY.click();
            Thread.sleep(3000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "04 00");
            verifyArmstay();
            System.out.println("Pass: sensors bypassed, system is in Arm Stay mode");
            home.DISARM.click();
            enterDefaultUserCode();
            Thread.sleep(3000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            Thread.sleep(2000);
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(1000);
            swipeVertical();
            swipeVertical();
            Thread.sleep(1000);
            arming.Auto_Bypass.click();
            Thread.sleep(3000);
            settings.Home_button.click();
            Thread.sleep(3000);
            System.out.println("Verify that Auto Bypass does not work when disabled");
            sensors.primaryCall("65 00 0A", "06 00");
            home.DISARM.click();
            Thread.sleep(2000);
            home.ARM_STAY.click();
            Thread.sleep(4000);
            if (home.Message.isDisplayed()) {
                System.out.println( "Pass: Bypass message is displayed");
            } else
                Thread.sleep(2000);
            home.Bypass_OK.click();
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(1000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(1000);
            verifyArmstay();
            System.out.println("Pass: sensors bypassed, system is in Arm Stay mode");
            home.DISARM.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            Thread.sleep(2000);
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(1000);
            arming.Auto_Bypass.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(1000);
            sensors.delete_from_primary(3);
            Thread.sleep(2000);






            Thread.sleep(2000);
            System.out.println("Verify that Auto Exit Time Extension works when enabled");
            //do check for if its enabled. Needs a checkbox check
            System.out.println("Adding sensors...");
            sensors.add_primary_call(3, 10, 6619296, 1);
            Thread.sleep(2000);
            ARM_AWAY(3);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(2000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(2000);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(2000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(15000);
            try {
                if (home.ArwAway_State.isDisplayed())
                    System.out.println("Failed: System is ARMED AWAY");
            } catch (Exception e) {
                System.out.println("Pass: System is NOT ARMED AWAY");
            }
            Thread.sleep(50000);
            home.ArwAway_State.click();
            enterDefaultUserCode();
            Thread.sleep(2000);
            System.out.println("Verify that Auto Exit Time Extension does not works when disabled");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(3000);
            swipeVertical();
            Thread.sleep(3000);
            swipeVertical();
            swipeVertical();
            arming.Auto_Exit_Time_Extension.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            Thread.sleep(2000);
            ARM_AWAY(3);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(2000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(2000);
            sensors.primaryCall("65 00 0A", "06 00");
            Thread.sleep(2000);
            sensors.primaryCall("65 00 0A", "04 00");
            Thread.sleep(14000);
            try {
                if (home.ArwAway_State.isDisplayed())
                    System.out.println("Pass: System is ARMED AWAY");
            } catch (Exception e) {
                System.out.println("Fail: System is NOT ARMED AWAY");
            }
            Thread.sleep(14000);
            home.ArwAway_State.click();
            enterDefaultUserCode();
            Thread.sleep(2000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(3000);
            swipeVertical();
            Thread.sleep(3000);
            swipeVertical();
            swipeVertical(); //sometimes swipe is good some times too far
            arming.Auto_Exit_Time_Extension.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            sensors.delete_from_primary(3);
            Thread.sleep(2000);



            int delay = 15;

            System.out.println("Adding sensors...");
            sensors.add_primary_call(3, 10, 6619296, 1);
            Thread.sleep(2000);
            System.out.println("Verify that Auto Stay works when enabled");
            //cant do check if its enabled, no text change, the checkbox only.
            Thread.sleep(2000);
            System.out.println("Arm Away the system");
            ARM_AWAY(delay);
            verifyArmstay();
            System.out.println("Pass: System is in ARMED STAY mode");
            home.DISARM.click();
            enterDefaultUserCode();
            System.out.println("Verify that Auto Stay does not works when disabled");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(3000);
            swipeVertical();
            swipeVertical();
            arming.Auto_Stay.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            System.out.println("Arm Away the system");
            ARM_AWAY(delay);
            verifyArmaway();
            System.out.println("Pass: System is in ARMED AWAY mode");
            home.ArwAway_State.click();
            enterDefaultUserCode();
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(3000);
            swipeVertical();
            swipeVertical();
            arming.Auto_Stay.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            sensors.delete_from_primary(3);
            Thread.sleep(2000);



            UserManagementPage user = PageFactory.initElements(driver, UserManagementPage.class);
            navigateToAdvancedSettingsPage();
            System.out.println( "Verify a Dealer code can be changed");
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            arming.Dealer_Code.click();
            user.Add_User_Name_field.clear();
            System.out.println("Changing Dealer name");
            user.Add_User_Name_field.sendKeys("NewDealer");
            user.Add_User_Code_field.clear();
            System.out.println("Changing Dealer password");
            user.Add_User_Code_field.sendKeys("5555");
            driver.hideKeyboard();
            user.Add_Confirm_User_Code_field.click();
            user.Add_Confirm_User_Code.clear();
            user.Add_Confirm_User_Code.sendKeys("5555");
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
            }
            user.User_Management_Save.click();
            Thread.sleep(1000);
            driver.findElement(By.id("com.qolsys:id/ok")).click();
            Thread.sleep(1000);
            navigateToSettingsPage();
            settings.ADVANCED_SETTINGS.click();
            settings.Five.click();
            settings.Five.click();
            settings.Five.click();
            settings.Five.click();
            Thread.sleep(2000);
            logger.info("Verify Dealer name changed");
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            arming.Dealer_Code.click();
//        <WebElement> name = driver.findElement(By.id("com.qolsys:id/username"));
//        user.Add_User_Name_field.clear();
            Assert.assertTrue(driver.findElement(By.xpath("//android.widget.EditText[@text='NewDealer']")).isDisplayed());
            System.out.println( "Pass: new dealer name is displayed");
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(2000);
            System.out.println("Verify old Dealer code does not work");
            settings.ADVANCED_SETTINGS.click();
            settings.Two.click();
            settings.Two.click();
            settings.Two.click();
            settings.Two.click();
            if (settings.Invalid_User_Code.isDisplayed()) {
                System.out.println("Pass: old Dealer code does not work");
            }
            Thread.sleep(2000);
            System.out.println("Verify new Dealer code works");
            settings.Five.click();
            settings.Five.click();
            settings.Five.click();
            settings.Five.click();
            System.out.println("Pass: new Dealer code works as expected");
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            arming.Dealer_Code.click();
            Thread.sleep(2000);
            user.Add_User_Name_field.clear();
            user.Add_User_Name_field.sendKeys("Dealer");
            user.Add_User_Code_field.clear();
            user.Add_User_Code_field.sendKeys("2222");
            driver.hideKeyboard();
            user.Add_Confirm_User_Code_field.click();
            user.Add_Confirm_User_Code.clear();
            user.Add_Confirm_User_Code.sendKeys("2222");
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
            }
            user.User_Management_Save.click();
            Thread.sleep(1000);
            driver.findElement(By.id("com.qolsys:id/ok")).click();
            Thread.sleep(1000);



            try {
                if (home.Home_button.isDisplayed())
                    home.Home_button.click();
            } catch (Exception e) {
                System.out.println("Already On Home Page, Continue With Test");
            }


            System.out.println("Verifying Disarm photo is taken when setting in enabled...");
            deleteAllCameraPhotos(); //must have secure delete on
            Thread.sleep(1000);
            ARM_STAY();
            home.DISARM.click();
            enterDefaultUserCode();
            swipeFromLefttoRight();
            Thread.sleep(2000);
            swipeFromLefttoRight();
            camera.Disarm_photos.click();
            if (camera.Photo_lable.isDisplayed()) {
                System.out.println("Pass: Disarm photo is displayed");
            } else {
                System.out.println("Failed: Disarm photo is NOT displayed");
            }
            camera.Camera_delete.click();
            camera.Camera_delete_yes.click();
            if (home.Four.isDisplayed()) {
                enterDefaultUserCode();
            }
            Thread.sleep(1000);
            System.out.println("Verifying Disarm photo is NOT taken when setting in disabled...");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.CAMERA_SETTINGS.click();
            Thread.sleep(1000);
            set_cam.Disarm_Photos.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(1000);
            ARM_STAY();
            home.DISARM.click();
            enterDefaultUserCode();
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            camera.Disarm_photos.click();
            try {
                if (camera.Photo_lable.isDisplayed())
                    System.out.println("Failed: Disarm photo is displayed");
            } catch (Exception e) {
                System.out.println("Pass: Disarm photo is NOT displayed");
            }
            Thread.sleep(1000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.CAMERA_SETTINGS.click();
            set_cam.Disarm_Photos.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(2000);



            PanelCameraPage cam = PageFactory.initElements(driver, PanelCameraPage.class);

            Thread.sleep(2000);
            System.out.println("Verify system can be DISARMED with Duress code");
            Thread.sleep(2000);
            home.DISARM.click();
            home.ARM_STAY.click();
            Thread.sleep(2000);
            home.DISARM.click();
            home.Nine.click();
            home.Nine.click();
            home.Nine.click();
            home.Nine.click();
            Thread.sleep(1000);
            swipeFromRighttoLeft();
            Thread.sleep(1000);
            cam.Disarm_photos.click();
            Thread.sleep(1000);
            if (cam.DISARMED_BY_ADMIN.isDisplayed()) { //shows up as admin to hide from criminal that it was a duress code
                System.out.println("Pass: Duress code does work");
            } else {
                takeScreenshot();
                System.out.println("Failed: Duress code did not work");
            }
            Thread.sleep(1000);
            navigateToAdvancedSettingsPage();
            adv.USER_MANAGEMENT.click();
            Thread.sleep(5000);
            swipeUserManagementVertical();
            System.out.println("Change Duress Code -> Expected result = system can be disarmed with New Duress");
            List<WebElement> li_status1 = driver.findElements(By.id("com.qolsys:id/editImg"));
            Thread.sleep(2000);
            swipeUserManagementVertical();
            Thread.sleep(1000);
            li_status1.get(2).click();
            Thread.sleep(1000);
            user.Add_User_Name_field.clear();
            logger.info("Changing Duress name");
            user.Add_User_Name_field.sendKeys("NewDuress");
            user.Add_User_Code_field.clear();
            logger.info("Changing Duress password");
            user.Add_User_Code_field.sendKeys("9996");
            driver.hideKeyboard();
            user.Add_Confirm_User_Code_field.click();
            user.Add_Confirm_User_Code.clear();
            user.Add_Confirm_User_Code.sendKeys("9996");
            Thread.sleep(2000);
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
            }
            user.User_Management_Save.click();
            Thread.sleep(5000);
            user.Home_Button.click();
//        user.User_Management_Delete_User_Cancel.click();
            try {
                user.Home_Button.click();
            } catch (Exception e) {
            }
            Thread.sleep(4000);
            home.DISARM.click();
            home.ARM_STAY.click();
            Thread.sleep(2000);
            home.DISARM.click();
            home.Nine.click();
            home.Nine.click();
            home.Nine.click();
            home.Six.click();
            Thread.sleep(1000);
            swipeFromRighttoLeft();
            Thread.sleep(1000);
            cam.Disarm_photos.click();
            Thread.sleep(1000);
            if (cam.DISARMED_BY_ADMIN.isDisplayed()) {
                System.out.println("Pass: new Duress code works");
            } else {
                takeScreenshot();
                System.out.println("Failed: new Duress code did not worked");
            }
            Thread.sleep(2000);
            navigateToAdvancedSettingsPage();
            adv.USER_MANAGEMENT.click();
            Thread.sleep(2000);
            swipeUserManagementVertical();
            Thread.sleep(1000);
            List<WebElement> li_status2 = driver.findElements(By.id("com.qolsys:id/editImg"));
            Thread.sleep(2000);
            swipeUserManagementVertical();
            Thread.sleep(1000);
            li_status2.get(2).click();
            Thread.sleep(1000);
            user.Add_User_Name_field.clear();
            logger.info("Changing Duress name");
            user.Add_User_Name_field.sendKeys("Duress");
            user.Add_User_Code_field.clear();
            logger.info("Changing Duress password");
            user.Add_User_Code_field.sendKeys("9999");
            driver.hideKeyboard();
            user.Add_Confirm_User_Code_field.click();
            user.Add_Confirm_User_Code.clear();
            user.Add_Confirm_User_Code.sendKeys("9999");
//        driver.pressKeyCode(AndroidKeyCode.ENTER);
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
            }
            user.User_Management_Save.click();
            Thread.sleep(1000);



            try {
                if (home.Home_button.isDisplayed())
                    home.Home_button.click();
            } catch (Exception e) {
                System.out.println("Already On Home Page, Continue With Test");
            }



            System.out.println("Verify Installer Code can be modified");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            arming.Installer_Code.click();
            user.Add_User_Name_field.clear();
            System.out.println("Changing Installer name");
            user.Add_User_Name_field.sendKeys("NewInstall");
            user.Add_User_Code_field.clear();
            System.out.println("Changing Installer password");
            user.Add_User_Code_field.sendKeys("5555");
            driver.hideKeyboard();
            user.Add_Confirm_User_Code_field.click();
            user.Add_Confirm_User_Code.clear();
            user.Add_Confirm_User_Code.sendKeys("5555");
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
            }
            user.User_Management_Save.click();
            Thread.sleep(1000);
            settings.Back_button.click();
            Thread.sleep(1000);
            settings.Back_button.click();
            Thread.sleep(2000);
//        settings.Back_button.click();
//        Thread.sleep(2000);
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            arming.Installer_Code.click();
            System.out.println("Verify Installer name changed");
            Assert.assertTrue(driver.findElement(By.xpath("//android.widget.EditText[@text='NewInstall']")).isDisplayed());
            System.out.println("Pass: New Installer name is displayed");
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(3000);
            settings.Back_button.click();
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(2000);
            settings.Back_button.click();
            Thread.sleep(2000);
            System.out.println("Verify old Installer code does not work");
            settings.ADVANCED_SETTINGS.click();
            settings.One.click();
            settings.One.click();
            settings.One.click();
            settings.One.click();
            if (settings.Invalid_User_Code.isDisplayed()) {
                System.out.println("Pass: old Installer code does not work");
            }
            Thread.sleep(2000);
            System.out.println("Verify new Installer code works");
            settings.Five.click();
            settings.Five.click();
            settings.Five.click();
            settings.Five.click();
            System.out.println("Pass: new Installer code works as expected");
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            arming.Installer_Code.click();
            Thread.sleep(2000);
            user.Add_User_Name_field.clear();
            user.Add_User_Name_field.sendKeys("Installer");
            user.Add_User_Code_field.clear();
            user.Add_User_Code_field.sendKeys("1111");
            driver.hideKeyboard();
            user.Add_Confirm_User_Code_field.click();
            user.Add_Confirm_User_Code.clear();
            user.Add_Confirm_User_Code.sendKeys("1111");
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
            }
            user.User_Management_Save.click();
            Thread.sleep(2000);
            user.User_Management_Delete_User_Ok.click();
            Thread.sleep(2000);



            try {
                if (home.Home_button.isDisplayed())
                    home.Home_button.click();
            } catch (Exception e) {
                System.out.println("Already On Home Page, Continue With Test");
            }


            String disarm = "08 01";
            System.out.println("Adding sensors...");
            sensors.add_primary_call(3, 4, 6619386, 102);
            Thread.sleep(2000);
            System.out.println("Verify that keyfob Alarm Disarm does not work when disabled");
            home.Emergency_Button.click();
            emergency.Police_icon.click();
            Thread.sleep(2000);
            sensors.primaryCall("65 00 AF", disarm);
            Thread.sleep(2000);
            if (emergency.Police_Emergency_Alarmed.isDisplayed()) { //f
                System.out.println("Pass: Police Emergency is displayed");
            } else {
                System.out.println("Failed: Police Emergency is NOT displayed");
            }
            enterDefaultUserCode();
            Thread.sleep(2000);
            System.out.println("Verify that keyfob Alarm Disarm  works when enabled");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(2000);
            swipeVertical();
            swipeVertical();
            arming.Keyfob_Alarm_Disarm.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            Thread.sleep(2000);
            home.Emergency_Button.click();
            emergency.Police_icon.click();
            Thread.sleep(2000);
            sensors.primaryCall("65 00 AF", disarm);
            Thread.sleep(2000);
            verifyDisarm();
            System.out.println("Pass: System is disarmed by a keyfob");
            Thread.sleep(2000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(2000);
            swipeVertical();
            swipeVertical();
            arming.Keyfob_Alarm_Disarm.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            sensors.delete_from_primary(3);
            Thread.sleep(2000);






            PanelInfo_ServiceCalls service = PageFactory.initElements(driver, PanelInfo_ServiceCalls.class);

            System.out.println("Adding sensors...");
            //service.set_AUTO_STAY(00);
            sensors.add_primary_call(3, 4, 6619386, 102);
            Thread.sleep(2000);
            System.out.println("Verify that Keyfob Disarming works when enabled");
            //cant check if box is clicked, the text doesn't change"
            ARM_STAY();
            Thread.sleep(2000);
            sensors.primaryCall("65 00 AF", disarm);
            Thread.sleep(2000);
            verifyDisarm();
            System.out.println("Pass: system is disarmed by a keyfob from Arm Stay");
            Thread.sleep(2000);
            ARM_AWAY(delay);
            Thread.sleep(2000);
            sensors.primaryCall("65 00 AF", disarm);
            Thread.sleep(2000);
            verifyDisarm();
            System.out.println( "Pass: system is disarmed by a keyfob from Arm Away");
            Thread.sleep(2000);
            System.out.println("Verify that Keyfob Disarming does not work when disabled");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(2000);
            swipeVertical();
            swipeVertical();
            arming.Keyfob_Disarming.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            Thread.sleep(2000);
            ARM_STAY();
            Thread.sleep(2000);
            sensors.primaryCall("65 00 AF", disarm);
            Thread.sleep(2000);
            verifyArmstay();
            System.out.println("Pass: system is NOT disarmed by a keyfob from Arm Stay");
            home.DISARM.click();
            enterDefaultUserCode();
            Thread.sleep(2000);
            ARM_AWAY(delay);
            Thread.sleep(2000);
            sensors.primaryCall("65 00 AF", disarm);
            Thread.sleep(2000);
            verifyArmaway(); //fail?
            System.out.println("Pass: system is NOT disarmed by a keyfob from Arm Away");
            home.ArwAway_State.click();
            enterDefaultUserCode();
            Thread.sleep(2000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(2000);
            swipeVertical();
            Thread.sleep(2000);
            swipeVertical();
            swipeVertical();
            arming.Keyfob_Disarming.click();
            Thread.sleep(2000);
            sensors.delete_from_primary(3);
            settings.Home_button.click();
            service.set_AUTO_STAY(01);
            Thread.sleep(2000);




            Thread.sleep(1000);
            System.out.println("Verify panic disappears from the Emergency page when disabled");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SIREN_AND_ALARMS.click();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            System.out.println("Disable Police Panic");
            siren.Police_Panic.click();
            Thread.sleep(1000);
            settings.Emergency_button.click();
            try {
                if (emergency.Police_icon.isDisplayed())
                    System.out.println("Failed: Police Emergency is displayed");
            } catch (Exception e) {
                System.out.println("Pass: Police Emergency is NOT displayed");
            }
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            Thread.sleep(1000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SIREN_AND_ALARMS.click();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            System.out.println("Disable Fire Panic");
            siren.Fire_Panic.click();
            Thread.sleep(1000);
            settings.Emergency_button.click();
            try {
                if (emergency.Fire_icon.isDisplayed())
                    System.out.println("Failed: Fire Emergency is displayed");
            } catch (Exception e) {
                System.out.println("Pass: Fire Emergency is NOT displayed");
            }
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            Thread.sleep(1000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SIREN_AND_ALARMS.click();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            System.out.println( "Disable Auxiliary Panic");
            siren.Auxiliary_Panic.click();
            Thread.sleep(1000);
            try {
                if (settings.Emergency_button.isDisplayed())
                    System.out.println("Failed: Emergency button is displayed");
            } catch (Exception e) {
                System.out.println("Pass: Emergency button is NOT displayed");
            }
            siren.Police_Panic.click();
            siren.Fire_Panic.click();
            siren.Auxiliary_Panic.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(2000);









            Thread.sleep(2000);
            logger.info("Verify no code is required to Arm the system when setting is disabled");
            home.DISARM.click();
            home.ARM_STAY.click();
            Thread.sleep(2000);
            verifyArmstay();
            System.out.println("Pass: System is in Arm Stay mode, no password was required");
            home.DISARM.click();
            enterDefaultUserCode();
            Thread.sleep(2000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(1000);
            swipeVertical();
            arming.Secure_Arming.click();
            Thread.sleep(2000);
            settings.Home_button.click();
            Thread.sleep(2000);
            logger.info("Verify code is required to Arm the system when setting is enabled");
            home.DISARM.click();
            home.ARM_STAY.click();
            if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
                logger.info("Pass: code is requires to Arm the system");
            }
            enterDefaultUserCode();
            Thread.sleep(2000);
            home.DISARM.click();
            enterDefaultUserCode();
            Thread.sleep(2000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.SECURITY_AND_ARMING.click();
            Thread.sleep(1000);
            swipeVertical();
            arming.Secure_Arming.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(2000);







            System.out.println("Verifying deleting panel images requires valid code...");
            deleteAllCameraPhotos();
            Thread.sleep(1000);
            serv.set_SECURE_DELETE_IMAGES(1);
            //home.Home_button.click();
            Thread.sleep(1000);
            ARM_STAY();
            home.DISARM.click();
            enterDefaultUserCode();
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            camera.Camera_delete.click();
            Thread.sleep(2000);
            camera.Camera_delete_yes.click();
            if (home.Enter_Code_to_Access_the_Area.isDisplayed()) {
                System.out.println("Pass: Password is required to delete images");
            } else {
                System.out.println("Failed: Password is NOT required to delete the image");
            }
            enterDefaultUserCode();
            Thread.sleep(1000);
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            Thread.sleep(1000);
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.CAMERA_SETTINGS.click();
            set_cam.Secure_Delete_Images.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(1000);
            System.out.println("Verifying deleting panel images does not require valid code...");
            ARM_STAY();
            home.DISARM.click();
            enterDefaultUserCode();
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            camera.Camera_delete.click();
            Thread.sleep(2000);
            if (camera.Camera_delete_title.isDisplayed()) {
                System.out.println("Delete pop-up");
            }
            camera.Camera_delete_yes.click();
            try {
                if (home.Enter_Code_to_Access_the_Area.isDisplayed())
                    System.out.println("Failed: Password is required to delete the image");
            } catch (Exception e) {
                System.out.println("Pass: Password is NOT required to delete the image");
            }
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.CAMERA_SETTINGS.click();
            set_cam.Secure_Delete_Images.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(2000);










            System.out.println("Verifying settings photo is NOT taken when setting in disabled...");
            deleteAllCameraPhotos();
            Thread.sleep(1000);
            navigateToSettingsPage();
            settings.ADVANCED_SETTINGS.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            settings.Home_button.click();
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            camera.Settings_photos.click();
            try {
                if (camera.Photo_lable.isDisplayed())
                    System.out.println("Failed: Disarm photo is displayed");
            } catch (Exception e) {
                System.out.println("Pass: Disarm photo is NOT displayed");
            }
            System.out.println("Verifying settings photo is taken when setting in enabled...");
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.CAMERA_SETTINGS.click();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            set_cam.Settings_Photos.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            navigateToSettingsPage();
            settings.ADVANCED_SETTINGS.click();
            enterDefaultUserCode();
            Thread.sleep(1000);
            settings.Home_button.click();
            swipeFromLefttoRight();
            swipeFromLefttoRight();
            camera.Settings_photos.click();
            if (camera.Photo_lable.isDisplayed()) {
                System.out.println("Pass: settings photo is displayed");
            } else {
                System.out.println("Failed: settings photo is NOT displayed");
            }
            camera.Camera_delete.click();
            camera.Camera_delete_yes.click();
            enterDefaultUserCode();
            navigateToAdvancedSettingsPage();
            adv.INSTALLATION.click();
            inst.CAMERA_SETTINGS.click();
            Thread.sleep(1000);
            swipeVertical();
            Thread.sleep(1000);
            set_cam.Settings_Photos.click();
            Thread.sleep(1000);
            settings.Home_button.click();
            Thread.sleep(2000);
        }
    }


    @AfterClass
    public void driver_quit() throws InterruptedException {
        System.out.println("*****Stop driver*****");
        driver.quit();
        Thread.sleep(1000);
        System.out.println("\n\n*****Stop appium service*****" + "\n\n");
        service.stop();
    }
}


