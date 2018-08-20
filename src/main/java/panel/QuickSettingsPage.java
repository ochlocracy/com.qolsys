package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class QuickSettingsPage {

    //(Wifi connected details, Software update details, Bluetooth OFF) Page

    @FindBy(id = "com.qolsys:id/update_details_enable_rl")
    public WebElement SoftwareUpdateDetailsParameter;
    @FindBy(id = "com.qolsys:id/upgrade_settings")
    public WebElement SoftwareUpdateDetails;
    @FindBy(id = "com.qolsys:id/wifi_action")
    public WebElement WifiDetails;
    @FindBy(id = "com.qolsys:id/touchless_disarm")
    public WebElement BluetoothSetting;

}
