package zwave;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by nchortek on 6/22/17.
 */
public class Lights_Page {

    @FindBy(id = "com.qolsys:id/allOn")
    public WebElement On_Button;
    @FindBy(id = "com.qolsys:id/allOff")
    public WebElement Off_Button;
    @FindBy(id = "com.qolsys:id/lightSelect")
    public WebElement Light_Select;
    @FindBy(id = "com.qolsys:id/statusButton")
    public WebElement Light_Icon;
    @FindBy(id = "com.qolsys:id/getStatusButton")
    public WebElement Get_Status_Button;
    @FindBy(id = "com.qolsys:id/uiName")
    public WebElement UI_Name;
    @FindBy(id = "com.qolsys:id/dimmer_seek_bar")
    public WebElement Dimmer;
}
