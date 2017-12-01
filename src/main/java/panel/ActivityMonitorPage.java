package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ActivityMonitorPage {
    @FindBy(id = "com.qolsys:id/t3_tv_quick_access")
    public WebElement Quick_Access;
    @FindBy(id = "com.qolsys:id/t3_img_quick_access")
    public WebElement Quick_Access_img; //hand
    @FindBy(id = "com.qolsys:id/t3_iv_safety_state")
    public WebElement Safty_State;
    @FindBy(id = "com.qolsys:id/t3_tv_state")
    public WebElement Safety_State_txt;
    @FindBy(id = "com.qolsys:id/t3_tv_safety_active")
    public WebElement Safety_Active;
    @FindBy(id = "com.qolsys:id/t3_tv_safety_all")
    public WebElement Safety_All;
    @FindBy(id = "com.qolsys:id/t3_tv_safety_bypass")
    public WebElement Safety_Bypass;
    @FindBy(id = "com.qolsys:id/ui_relative_progress")
    public WebElement Quick_Access_CountDown; //300 sec

}
