package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Installation_Page {

    @FindBy(xpath = "//android.widget.TextView[@text='Devices']")
    public WebElement DEVICES;
    @FindBy(xpath = "//android.widget.TextView[@text='Dealer Settings']")
    public WebElement DEALER_SETTINGS;
    @FindBy(xpath = "//android.widget.TextView[@text='System Logs']")
    public WebElement SYSTEM_LOGS;
    @FindBy(xpath = "//android.widget.TextView[@text='Siren and Alarms']")
    public WebElement SIREN_AND_ALARMS;
    @FindBy(xpath = "//android.widget.TextView[@text='Security and Arming']")
    public WebElement SECURITY_AND_ARMING;
    @FindBy(xpath = "//android.widget.TextView[@text='Camera Settings']")
    public WebElement CAMERA_SETTINGS;
    @FindBy(xpath = "//android.widget.TextView[@text='Load Help Videos']")
    public WebElement LOAD_HELP_VIDEOS;

}
