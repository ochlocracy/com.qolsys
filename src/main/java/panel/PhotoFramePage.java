package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PhotoFramePage {

    @FindBy(id = "com.qolsys:id/uiTV_title")
    public WebElement Photo_Frame_page_title;
    @FindBy(id = "com.qolsys:id/uiBTN_Play")
    public WebElement Photo_Frame_Play;
    @FindBy(id = "com.qolsys:id/uiBTN_Rmv")
    public WebElement Photo_Frame_Delete;
    @FindBy(id = "com.qolsys:id/uiBTN_delete_image")
    public WebElement Photo_Frame_Delete_delete_page;
    @FindBy(id = "com.qolsys:id/uiBTN_Add")
    public WebElement Photo_Frame_Add;
    @FindBy(id = "com.qolsys:id/uiBTN_Settings")
    public WebElement Photo_Frame_Settings;
    @FindBy(id = "com.qolsys:id/uiTV_title")
    public WebElement Photo_Frame_delete_page_tille;
    @FindBy(id = "com.qolsys:id/uiBTN_select_all")
    public WebElement Photo_Frame_Select_All;
    @FindBy(id = "com.qolsys:id/uiBTN_cancel")
    public WebElement Photo_Frame_Cancel;
    @FindBy(id = "com.qolsys:id/ui_tv_space_remaining")
    public WebElement Photo_Frame_Space_Remaining;
    @FindBy(id = "com.qolsys:id/uiBTN_add_images")
    public WebElement Photo_Frame_Add_New_Photos;
    @FindBy(id = "com.qolsys:id/uiBTN_replace_images")
    public WebElement Photo_Frame_Replace_All_Photos;
    @FindBy(id = "com.qolsys:id/sourceoption")
    public WebElement Photo_Frame_Sourse_Option;
    @FindBy(xpath = "//android.widget.TextView[@text='SD Card']")
    public WebElement Photo_Frame_SD_Card;
    @FindBy(xpath = "//android.widget.TextView[@text='Default']")
    public WebElement Photo_Frame_Default;
    @FindBy(xpath = "//android.widget.TextView[@text='Buildings']")
    public WebElement Photo_Frame_Buildings;
    @FindBy(xpath = "//android.widget.TextView[@text='Flowers']")
    public WebElement Photo_Frame_Flowers;
    @FindBy(xpath = "//android.widget.TextView[@text='Mountains']")
    public WebElement Photo_Frame_Mountains;
    @FindBy(xpath = "//android.widget.TextView[@text='Water']")
    public WebElement Photo_Frame_Water;
    @FindBy(id = "com.qolsys:id/ui_tv_warning_sdcard")
    public WebElement Photo_Frame_Add_Warning;
    @FindBy(id = "com.qolsys:id/uiTVText")
    public WebElement Photo_Frame_Help;
    @FindBy(id = "com.qolsys:id/title")
    public WebElement Photo_Frame_Help_title;
    @FindBy(id = "com.qolsys:id/positive_button")
    public WebElement Photo_Frame_Help_OK_button;
    @FindBy(xpath = "//android.widget.TextView[@text='Duration']")
    public WebElement Duration;
    @FindBy(xpath = "//android.widget.TextView[@text='Each photo frame image will display for 1 minute']")
    public WebElement Duration_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='1 minute']")
    public WebElement Duration_1_minute;
    @FindBy(xpath = "//android.widget.TextView[@text='2 minutes']")
    public WebElement Duration_2_minutes;
    @FindBy(xpath = "//android.widget.TextView[@text='5 minutes']")
    public WebElement Duration_5_minutes;
    @FindBy(xpath = "//android.widget.TextView[@text='Effect']")
    public WebElement Effect;
    @FindBy(xpath = "//android.widget.TextView[@text='Dissolve between photo frame images']")
    public WebElement Effect_summery_dissolve;
    @FindBy(xpath = "//android.widget.TextView[@text='Fade to Black between photo frame images']")
    public WebElement Effect_summery_fade_to_black;
    @FindBy(xpath = "//android.widget.TextView[@text='Dissolve']")
    public WebElement Effect_Dissolve;
    @FindBy(xpath = "//android.widget.TextView[@text='Fade to Black']")
    public WebElement Effect_Fade_to_Black;
    @FindBy(xpath = "//android.widget.TextView[@text='Shuffle']")
    public WebElement Shuffle;
    @FindBy(xpath = "//android.widget.TextView[@text='Randomly sort photo frame images']")
    public WebElement Shuffle_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Display Type']")
    public WebElement Display_Type;
    @FindBy(xpath = "//android.widget.TextView[@text='When touchscreen is inactive the display is Photo Frame']")
    public WebElement Display_Type_Photo_Frame_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='When touchscreen is inactive the display is Weather Clock']")
    public WebElement Display_Type_Weather_Clock_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='When touchscreen is inactive the display is Off']")
    public WebElement Display_Type_Off_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Photo Frame']")
    public WebElement Display_Type_Photo_Frame;
    @FindBy(xpath = "//android.widget.TextView[@text='Off']")
    public WebElement Display_Type_Off;
    @FindBy(xpath = "//android.widget.TextView[@text='Weather Clock']")
    public WebElement Display_Type_Weather_Clock;
    @FindBy(xpath = "//android.widget.TextView[@text='Photo Frame Start Time']")
    public WebElement Photo_Frame_Start_Time;
    @FindBy(xpath = "//android.widget.TextView[@text='Photo Frame will start 10 minutes after last touch']") //default
    public WebElement Photo_Frame_Start_Time_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Automatically turn off display']") //default
    public WebElement Automatically_turn_off_display;
    @FindBy(xpath = "//android.widget.TextView[@text='Display turns off each day at 11:00 PM']") //default
    public WebElement Automatically_turn_off_display_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Automatically turn on display']") //default
    public WebElement Automatically_turn_on_display;
    @FindBy(xpath = "//android.widget.TextView[@text='Display turns on each day at 06:00 AM']") //default
    public WebElement Automatically_turn_on_display_summery;
}
