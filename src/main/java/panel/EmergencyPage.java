package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmergencyPage {

    @FindBy(id = "com.qolsys:id/t3_emergency_icon")
    public WebElement Emergency_Button;
    @FindBy(id = "com.qolsys:id/tv_police")
    public WebElement Police_icon;
    @FindBy(id = "com.qolsys:id/tv_fire")
    public WebElement Fire_icon;
    @FindBy(id = "com.qolsys:id/tv_action_img")
    public WebElement Fire_icon_Alarmed;
    @FindBy(id = "com.qolsys:id/tv_medical")
    public WebElement Auxiliary_icon;
    @FindBy(id = "com.qolsys:id/tv_police_title")
    public WebElement Police_title;
    @FindBy(id = "com.qolsys:id/tv_fire_title")
    public WebElement Fire_title;
    @FindBy(id = "com.qolsys:id/tv_medical_title")
    public WebElement Auxiliary_title;
    @FindBy(id = "com.qolsys:id/tv_police_silent")
    public WebElement Police_Silent;
    @FindBy(id = "com.qolsys:id/tv_medical_silent")
    public WebElement Auxiliary_Silent;

    @FindBy(id = "com.qolsys:id/tv_img_action_text")
    public WebElement Emergency_sent_text;
    @FindBy(id = "com.qolsys:id/tv_emg_cancel")
    public WebElement Cancel_Emergency;
    @FindBy(xpath = "//android.widget.TextView[@text='Police Emergency']")
    public WebElement Police_Emergency_Alarmed;
    @FindBy(xpath = "//android.widget.TextView[@text='Auxiliary Emergency']")
    public WebElement Auxiliary_Emergency_Alarmed;
    @FindBy(xpath = "//android.widget.TextView[@text='Fire Emergency']")
    public WebElement Fire_Emergency_Alarmed;
}
