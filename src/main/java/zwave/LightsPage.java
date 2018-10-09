package zwave;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LightsPage {

    //******* Panel Elements********
    @FindBy(id = "com.qolsys:id/allOn")
    public WebElement allOnBtn;
    @FindBy(id = "com.qolsys:id/allOff")
    public WebElement allOffBtn;
    @FindBy(id = "com.qolsys:id/lightSelect")
    public WebElement lightSelectionBox;
    @FindBy(id = "com.qolsys:id/statusButton")
    public WebElement lightIcon;
    @FindBy(id = "com.qolsys:id/getStatusButton")
    public WebElement getStatusButton;
    @FindBy(id = "com.qolsys:id/dimmer_seek_bar")
    public WebElement dimmerBar;
    @FindBy(id = "com.qolsys:id/selectallbtn")
    public WebElement selectAllLigtsBtn;


    //******ADC User Site Elements*********




}
