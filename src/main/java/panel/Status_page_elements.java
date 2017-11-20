package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Status_page_elements {
    @FindBy(id = "com.qolsys:id/uiTabName4")
    public WebElement HISTORY_tab;
    @FindBy(id = "com.qolsys:id/uiTabName3")
    public WebElement ALARMS_tab;
    @FindBy(id = "com.qolsys:id/uiTabName1")
    public WebElement CURRENT_STATUS_tab;

}
