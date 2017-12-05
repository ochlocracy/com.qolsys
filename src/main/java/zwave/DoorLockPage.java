package zwave;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DoorLockPage {
    @FindBy(id = "com.qolsys:id/doorStatusbutton")
    public WebElement Key_icon;
    @FindBy(id = "com.qolsys:id/doorLockName")
    public WebElement DoorLock_Name;
    @FindBy(id = "com.qolsys:id/doorLockStatus")
    public WebElement DoorLock_Status;
    @FindBy(id = "com.qolsys:id/uiDoorStatus")
    public WebElement Refresh_Status;
    @FindBy(id = "com.qolsys:id/uiDoorBattery")
    public WebElement Door_battery;
    @FindBy(id = "com.qolsys:id/allOn")
    public WebElement Unlock_ALL;
    @FindBy(id = "com.qolsys:id/allOff")
    public WebElement Lock_ALL;
}
