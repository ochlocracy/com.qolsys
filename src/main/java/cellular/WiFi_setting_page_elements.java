package cellular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WiFi_setting_page_elements {
    @FindBy(id = "com.qolsys:id/wire_less_toggle")
    public WebElement Checkbox;
    @FindBy(xpath = "//android.widget.TextView[@text='Connected']")
    public WebElement Network_connection_status;
    @FindBy(id = "com.qolsys:id/message")
    public WebElement Warning_message;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement OK;
    @FindBy(xpath = "//android.widget.TextView[@text='NETGEAR56']")
    public WebElement Network_name;
    @FindBy(id = "com.qolsys:id/negative_button")
    public WebElement FORGET;
    @FindBy(id = "com.qolsys:id/value")
    public WebElement value;
    @FindBy(id = "com.qolsys:id/positive_button")
    public WebElement DONE;
    @FindBy(id="com.qolsys:id/ft_emergency")
    public WebElement Emergency_Button;
    @FindBy(id = "com.qolsys:id/ft_back")
    public WebElement Back_button;
    @FindBy(id = "com.qolsys:id/ft_home_button")
    public WebElement Home_button;
    @FindBy(id = "com.qolsys:id/t3_dialog_emergency_ll")
    public WebElement Emergency_page;
    @FindBy(id = "com.qolsys:id/t3_cdialog_ll")
    public WebElement out_of_Emergency_screen;
}
