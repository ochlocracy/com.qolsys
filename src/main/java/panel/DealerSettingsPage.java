package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DealerSettingsPage {
    @FindBy(xpath = "//android.widget.TextView[@text='Account Number']")
    public WebElement Account_Number;
    @FindBy(xpath = "//android.widget.TextView[@text='Enter 10-character account number']")
    public WebElement Account_Number_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Power Management']")
    public WebElement Power_Management;
    @FindBy(xpath = "//android.widget.TextView[@text='When running on battery power only, panel will limit services to those required or requested manually']")
    public WebElement Power_Management_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to limit panel services to those required or requested manually when running on battery power']")
    public WebElement Power_Management_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='SIA Power Restoration']")
    public WebElement SIA_Power_Restoration;
    @FindBy(xpath = "//android.widget.TextView[@text='panel ignoring all sensor activity for 60 seconds during power restoration is Disabled']")
    public WebElement SIA_Power_Restoration_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='panel ignoring all sensor activity for 60 seconds during power restoration is Enabled']")
    public WebElement SIA_Power_Restoration_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Loss of supervisory signals for emergency sensors']")
    public WebElement Loss_of_supervisory_signals_for_emergency_sensors;
    @FindBy(xpath = "//android.widget.TextView[@text='panel will wait 4 hours before reporting a non-responsive sensor of type Smoke/CO/Fire']")
    public WebElement Loss_of_supervisory_signals_for_emergency_sensors_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='24 hours']")
    public WebElement _24_hours;
    @FindBy(xpath = "//android.widget.TextView[@text='12 hours']")
    public WebElement _12_hours;
    @FindBy(xpath = "//android.widget.TextView[@text='4 hours']") //default
    public WebElement _4_hours;
    @FindBy(id = "com.qolsys:id/negative_button")
    public WebElement DSettings_Cancel;
    @FindBy(xpath = "//android.widget.TextView[@text='Loss of supervisory signals for non-emergency sensors']")
    public WebElement Loss_of_supervisory_signals_for_non_emergency_sensors;

    @FindBy(xpath = "//android.widget.TextView[@text='SIA Limits']")
    public WebElement SIA_Limits;
    @FindBy(id = "com.qolsys:id/ft_home_button")
    public WebElement Home_Button;
    @FindBy(xpath = "//android.widget.TextView[@text='Delete All Sensors']")
    public WebElement Delete_All_Sensors;

}
