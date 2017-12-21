package panel;

import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Setup;

import java.io.IOException;

public class PhotoFramePageTest extends Setup {

    String page_name = "Photo Frame page";
    Logger logger = Logger.getLogger(page_name);

    public PhotoFramePageTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @Test
    public void Check_all_elements_on_Photo_Frame_page() throws Exception {
        SlideMenu menu = PageFactory.initElements(driver, SlideMenu.class);
        SettingsPage settings = PageFactory.initElements(driver, SettingsPage.class);
        PhotoFramePage frame = PageFactory.initElements(driver, PhotoFramePage.class);
        logger.info("Verifying elements on the page...");
        Thread.sleep(1000);
        menu.Slide_menu_open.click();
        menu.Photo_Frame.click();
        elementVerification(frame.Photo_Frame_page_title, "Photo Frame page title");
        elementVerification(frame.Photo_Frame_Delete, "Photo Frame Delete");
        elementVerification(frame.Photo_Frame_Add, "Photo Frame Add");
        elementVerification(frame.Photo_Frame_Settings, "Photo Frame settings");
        frame.Photo_Frame_Delete.click();
        elementVerification(frame.Photo_Frame_delete_page_tille, "Photo Frame Delete page title");
        elementVerification(frame.Photo_Frame_Delete_delete_page, "Photo Frame Delete");
        elementVerification(frame.Photo_Frame_Select_All, "Photo Frame Select All");
        elementVerification(frame.Photo_Frame_Cancel, "Photo Frame Cancel");
        frame.Photo_Frame_Cancel.click();
        frame.Photo_Frame_Add.click();
        elementVerification(frame.Photo_Frame_Space_Remaining, "Photo Frame space remaining");
        elementVerification(frame.Photo_Frame_Select_All, "Photo Frame Select All");
        elementVerification(frame.Photo_Frame_Add_New_Photos, "Photo Frame Add New Photos");
        elementVerification(frame.Photo_Frame_Replace_All_Photos, "Photo Frame Replace All Photos");
        elementVerification(frame.Photo_Frame_Sourse_Option, "Photo Frame Source options");
        frame.Photo_Frame_Sourse_Option.click();
        elementVerification(frame.Photo_Frame_SD_Card, "Photo Frame SD Card");
        elementVerification(frame.Photo_Frame_Default, "Photo Frame Default");
        elementVerification(frame.Photo_Frame_Buildings, "Photo Frame Buildings");
        elementVerification(frame.Photo_Frame_Flowers, "Photo Frame Flowers");
        elementVerification(frame.Photo_Frame_Mountains, "Photo Frame Mountains");
        elementVerification(frame.Photo_Frame_Water, "Photo Frame Water");
        frame.Photo_Frame_Sourse_Option.click();
        elementVerification(frame.Photo_Frame_Add_Warning, "Photo Frame Add Warning");
        elementVerification(frame.Photo_Frame_Help, "Photo Frame Add Help");
        frame.Photo_Frame_Help.click();
        frame.Photo_Frame_Help_OK_button.click();
        settings.Back_button.click();

        frame.Photo_Frame_Settings.click();
        elementVerification(frame.Duration, "Duration");
        elementVerification(frame.Duration_summery, "Duration_summery");
        frame.Duration.click();
        Thread.sleep(1000);
        elementVerification(frame.Duration_1_minute, "Duration 1 minute"); //default
        elementVerification(frame.Duration_2_minutes, "Duration 2 minutes");
        elementVerification(frame.Duration_5_minutes, "Duration 5 minutes");
        frame.Duration_1_minute.click();
        elementVerification(frame.Effect, "Effect");
        elementVerification(frame.Effect_summery_dissolve, "Effect dissolve summery"); //default
        frame.Effect.click();
        Thread.sleep(1000);
        elementVerification(frame.Effect_Dissolve, "Effect Dissolve option");
        elementVerification(frame.Effect_Fade_to_Black, "Effect Fade to Black option");
        frame.Effect_Fade_to_Black.click();
        elementVerification(frame.Effect_summery_fade_to_black, "Effect Fade to Black summery");
        frame.Effect.click();
        Thread.sleep(1000);
        frame.Effect_Dissolve.click();
        elementVerification(frame.Shuffle, "Shuffle"); //enabled by default
        elementVerification(frame.Shuffle_summery, "Shuffle summery");
        elementVerification(frame.Display_Type, "Display Type");
        elementVerification(frame.Display_Type_Photo_Frame_summery, "Display Type Photo Frame summery"); //default
        frame.Display_Type.click();
        Thread.sleep(1000);
        elementVerification(frame.Display_Type_Photo_Frame, "Display Type Photo Frame"); //default
        elementVerification(frame.Display_Type_Off, "Display Type Off");
        frame.Display_Type_Off.click();
        elementVerification(frame.Display_Type_Off_summery, "Display Type Off summery");
        frame.Display_Type.click();
        Thread.sleep(1000);
        elementVerification(frame.Display_Type_Weather_Clock, "Display Type Weather Clock");
        frame.Display_Type_Weather_Clock.click();
        elementVerification(frame.Display_Type_Weather_Clock_summery, "Display Type Weather Clock summery");
        frame.Display_Type.click();
        Thread.sleep(1000);
        frame.Display_Type_Photo_Frame.click();
        swipeVertical();
        Thread.sleep(1000);
        elementVerification(frame.Photo_Frame_Start_Time, "Photo Frame Start Time"); //default
        elementVerification(frame.Photo_Frame_Start_Time_summery, "Photo Frame Start Time summery"); //default 10 min
        elementVerification(frame.Automatically_turn_off_display, "Automatically Turn Off display");
        elementVerification(frame.Automatically_turn_off_display_summery, "Automatically Turn Off display summery");
        elementVerification(frame.Automatically_turn_on_display, "Automatically Turn On display");
        elementVerification(frame.Automatically_turn_on_display_summery, "Automatically Turn On display summery");
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }
}