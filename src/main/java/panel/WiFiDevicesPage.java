package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WiFiDevicesPage {
    @FindBy(xpath = "//android.widget.TextView[@text='Access Point Connected Devices']")
    public WebElement Associated_Wifi_Clients;
    @FindBy(xpath = "//android.widget.TextView[@text='Access Point Settings']")
    public WebElement Qolsys_Access_Point;
    @FindBy(xpath = "//android.widget.TextView[@text='WI-FI Access Point']")
    public WebElement Wifi_Access_Point;
    @FindBy(xpath = "//android.widget.TextView[@text='Wi-Fi Access Point Disabled']")
    public WebElement Wifi_Access_Point_summery;
    @FindBy(xpath = "//*[contains(text(), ' Enabled ')]")
    public WebElement Wifi_Access_Point_summery_Enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='SSID']")
    public WebElement SSID;
    @FindBy(id = "com.qolsys:id/ui_di_body_sc")
    public WebElement SSID_txt_field;
    @FindBy(xpath = "//android.widget.TextView[@text='DHCP IP Range']")
    public WebElement DHCP_Range;
    @FindBy(className = "//android.widget.EditText']")
    public WebElement DHCP_Range_txt_field;
    @FindBy(xpath = "//android.widget.TextView[@text='DHCP IP Range is set to 50']")
    public WebElement DHCP_Range_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Change Password']")
    public WebElement Change_Password;
    @FindBy(xpath = "//android.widget.TextView[@text='Password Changes']")
    public WebElement Change_Password_title;
    @FindBy(id = "com.qolsys:id/uiEdit")
    public WebElement New_Password_field;
    @FindBy(id = "com.qolsys:id/uichngepwd")
    public WebElement Confirm_Password_field;
    @FindBy(id = "com.qolsys:id/positive_button")
    public WebElement Access_Point_OK_Button;
    @FindBy(id = "com.qolsys:id/negative_button")
    public WebElement Access_Point_Cancel_Button;
    @FindBy(xpath = "//android.widget.TextView[@text='AP MODE']")
    public WebElement AP_MODE;
    @FindBy(xpath = "//android.widget.TextView[@text='Ap Mode set to HIDDEN']")
    public WebElement AP_MODE_summery_Hidden;
    @FindBy(xpath = "//android.widget.TextView[@text='Ap Mode set to BROADCAST']")
    public WebElement AP_MODE_summery_Broadcast;
    @FindBy(xpath = "//android.widget.TextView[@text='BROADCAST']")
    public WebElement AP_MODE_BROADCAST;
    @FindBy(xpath = "//android.widget.TextView[@text='HIDDEN']")
    public WebElement AP_MODE_HIDDEN;
    @FindBy(xpath = "//android.widget.TextView[@text='Activate WPS']")
    public WebElement ActiveWPS;
    @FindBy(xpath = "//android.widget.TextView[@text='Access Point Password']")
    public WebElement AccessPointPassword;
    @FindBy(xpath = "//android.widget.TextView[@text='No']")
    public WebElement No;
    @FindBy(xpath = "//android.widget.TextView[@text='MAC Address']")
    public WebElement MAC_Address;
    @FindBy(xpath = "//android.widget.TextView[@text='IP Address']")
    public WebElement IP_Address;
    @FindBy(xpath = "//android.widget.TextView[@text='Connected Time(MM:DD:HH:mm)']")
    public WebElement Connected_Time;
    @FindBy(xpath = "//android.widget.TextView[@text='Actions']")
    public WebElement Actions;

    @FindBy(xpath = "//android.widget.TextView[@text='Access Point Settings']")
    public WebElement Access_Point_Settings;
    @FindBy(xpath = "//android.widget.TextView[@text='Access Point Connected Devices']")
    public WebElement Access_Point_Connected_Devices;
    @FindBy(xpath = "//android.widget.TextView[@text='WI-FI']")
    public WebElement WiFi;
    @FindBy(xpath = "//android.widget.TextView[@text='IQ Remote Devices']")
    public WebElement IQ_Remote_Devices;
}
