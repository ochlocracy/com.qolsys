package cellular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Android_settings_elements {
    @FindBy(id = "android:id/summary")
    public WebElement Preferred_network_type;
    @FindBy(id = "android:id/title")
    public WebElement Access_Point_Names;
    @FindBy(id = "android:id/title")
    public WebElement APN_name;
    @FindBy(id = "android:id/summary")
    public WebElement APN_summary;
    @FindBy(id = "com.android.settings:id/apn_radiobutton")
    public WebElement APN_selection_button;
    @FindBy(xpath = "//*[@class='android.widget.ImageButton' && @index='0']")
    public WebElement APN_page_back_button;
}

   // findElementsByXPath("//*[@class='android.widget.ImageButton' and @index='0']")