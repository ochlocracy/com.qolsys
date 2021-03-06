package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PanelCameraPage {
    @FindBy(id = "com.qolsys:id/tv_name")
    public WebElement Panel_camera_page_title;
    @FindBy(id = "//android.widget.TextView[@text='Anyone can delete panel camera images")
    public WebElement Summary_No_Code_Delete;
    @FindBy(id = "//android.widget.TextView[@text='Deleting panel camera images requires valid Master code")
    public WebElement Summary_Code_Required;
    @FindBy(id = "com.qolsys:id/camera_arm_disarm_filter")
    public WebElement Disarm_photos;
    @FindBy(id = "com.qolsys:id/camera_settings_filter")
    public WebElement Settings_photos;
    @FindBy(id = "com.qolsys:id/camera_alarms_filter")
    public WebElement Alarms_photo;
    @FindBy(id = "com.qolsys:id/camera_all_filter")
    public WebElement All_photos;
    @FindBy(id ="com.qolsys:id/playIcon")
    public WebElement Video_icon;
    @FindBy(id = "com.qolsys:id/rl")
    public WebElement Photo_lable;
    @FindBy(id = "com.qolsys:id/delete_img")
    public WebElement Camera_delete;
    @FindBy(xpath = "//android.widget.TextView[@text='ARE YOU SURE YOU \n" + "WANT TO DELETE THIS?']")
    public WebElement Camera_delete_title;
    @FindBy(id = "com.qolsys:id/yesBt")
    public WebElement Camera_delete_yes;
    @FindBy(id = "com.qolsys:id/noBt")
    public WebElement Camera_delete_no;
    @FindBy(xpath = "//android.widget.TextView[@text='DISARMED BY ADMIN']")
    public WebElement DISARMED_BY_ADMIN;
    @FindBy(xpath = "//android.widget.TextView[@text='POLICE EMERGENCY - PANEL']")
    public WebElement POLICE_EMERGENCY_PANEL;
    @FindBy(id = "com.qolsys:id/alarm_videos_title")
    public WebElement Alarm_Videos_title;
    @FindBy(id = "com.qolsys:id/imageView")
    public WebElement Alarm_Video_img;
    @FindBy(id = "com.qolsys:id/tv_no_photos")
    public WebElement No_Alarm_Photos_to_display;
    @FindBy(xpath = "//android.widget.TextView[@text='DISARMED BY NEWDURESS']") //they changed the name for duress disarm to ADMIN. if you change the title to anything else,
    public WebElement Duress_Disarm_Photo; //then that name is posted. Since the test changes it to NewDuress. That will be the text
}
