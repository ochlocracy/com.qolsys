package cellular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Dual_path_page_elements {
    @FindBy(id = "com.qolsys:id/wifi_status_resut")
    public WebElement WiFi_status;
    @FindBy(id = "com.qolsys:id/chkbox_title")
    public WebElement Dual_path_Control_check_box;
    @FindBy(id = "com.qolsys:id/chkbox_result_text")
    public WebElement chkbox_result_text;
    @FindBy(id = "com.qolsys:id/result_text")
    public WebElement Test_result;
    @FindBy(id = "com.qolsys:id/start_button")
    public WebElement start_button;
    @FindBy(id = "com.qolsys:id/cancel_button")
    public WebElement cancel_button;
    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement warning_message_cancell_button;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement warning_message_OK_button;
    @FindBy(id = "com.qolsys:id/message")
    public WebElement warning_message;
    @FindBy(id = "com.qolsys:id/ft_home_button")
    public WebElement Home_button;
    @FindBy(id = "com.qolsys:id/ft_back")
    public WebElement Back_button;
}
