package zwave;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DoorLockPage {
    @FindBy(id = "com.qolsys:id/doorStatusbutton")
    public WebElement keyIcon;

    @FindBy(id = "com.qolsys:id/doorLockName")
    public WebElement doorLockName;

    @FindBy(id = "com.qolsys:id/doorLockStatus")
    public WebElement doorLockStatus;

    @FindBy(id = "com.qolsys:id/uiDoorStatus")
    public WebElement refreshStatus;

    @FindBy(id = "com.qolsys:id/uiDoorBattery")
    public WebElement Door_battery;

    @FindBy(id = "com.qolsys:id/allOn")
    public WebElement unlockAll;

    @FindBy(xpath = "//android.widget.TextView[@text='UNLOCK ALL']")
    public WebElement unloackAllTxt;

    @FindBy(id = "com.qolsys:id/allOff")
    public WebElement Lock_ALL;

    @FindBy(id = "com.qolsys:id/doorStatusbutton")
    public WebElement LocknUnlock;

    @FindBy(xpath = "//android.widget.TextView[@text='LOCKED']")
    public WebElement lockedText;

    @FindBy(xpath = "//android.widget.TextView[@text='UNLOCKED']")
    public WebElement unlockedText;

    public final By LockedTxt = By.xpath("//android.widget.TextView[@text='LOCKED']");
    public final By UnlockedTxt = By.xpath("//android.widget.TextView[@text='UNLOCKED']");





    //********************************ADC User site Door Lock Elements**************************************

    @FindBy (xpath = "//div[@id='app-content']/div/div[1]/div[1]//div[@class='icons']/div/div[2]")
    public WebElement UsitelockUnlock;
    @FindBy (xpath = "")
    public WebElement UsiteLocking;
    @FindBy ()
    public WebElement UsiteUnlocking;
    @FindBy ()
    public WebElement DoorLock4;
    @FindBy ()
    public WebElement DoorLock5;
    @FindBy ()
    public WebElement DoorLock6;
    @FindBy ()
    public WebElement DoorLock7;


}
