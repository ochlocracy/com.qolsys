package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by qolsys on 3/22/17.
 */
public class Display_Page {
    @FindBy(xpath = "//android.widget.TextView[@text='Brightness Level']")
    public WebElement Brightness_Level;
    @FindBy(xpath = "//android.widget.TextView[@text='100%']")
    public WebElement Brightness_Level_summery_default;
    @FindBy(id ="com.qolsys:id/title")
    public WebElement Brightness_Level_title;
    @FindBy(id = "com.qolsys:id/bright_ness_level_tv")
    public WebElement Brightness_lvl;
    @FindBy (id ="com.qolsys:id/negative_button")
    public WebElement Brightness_Close;
    @FindBy(xpath = "//android.widget.TextView[@text='Font Size']")
    public WebElement Font_Size;
    @FindBy(xpath = "//android.widget.TextView[@text='Large']")
    public WebElement Font_Size_summery_default;
    @FindBy(id = "com.qolsys:id/title")
    public WebElement Font_Size_title;
    @FindBy(xpath = "//android.widget.TextView[@text='Small']")
    public WebElement Font_Size_Small;
    @FindBy(xpath = "//android.widget.TextView[@text='Normal']")
    public WebElement Font_Size_Normal;
    @FindBy(xpath = "//android.widget.TextView[@text='Large']")
    public WebElement Font_Size_Large;
    @FindBy(xpath = "//android.widget.TextView[@text='Use 24-hour format']")
    public WebElement Use_24hour_format;
    @FindBy(xpath = "//android.widget.TextView[@text='1:00 PM']")
    public WebElement Use_24hour_format_summery_default;
    @FindBy(xpath = "//android.widget.TextView[@text='13:00']")
    public WebElement Use_24hour_format_summery_enabled;
}
