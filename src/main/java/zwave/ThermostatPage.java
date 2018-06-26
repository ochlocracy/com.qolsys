package zwave;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ThermostatPage {

    @FindBy(id = "com.qolsys:id/uiTargTemp")
    public WebElement targetTemp;
    //change temp button up
    @FindBy(id = "com.qolsys:id/btTempUp")
    public WebElement tempUp;
    //change temp button down
    @FindBy(id = "com.qolsys:id/btTempDown")
    public WebElement tempDown;
    //
    @FindBy(id = "com.qolsys:id/uiThermoName")
    public WebElement Thermostat_Name;
    @FindBy(id = "com.qolsys:id/uiThermoMode")
    public WebElement currentMode;
    @FindBy(xpath = "//android.widget.TextView[@text='COOL']")
    public WebElement coolCurrentMode;
//    @FindBy(xpath = "//android.widget.TextView[@text='HEAT']")
//    public WebElement heatCurrentModeText;
    public final By heatCurrentModeText = By.xpath("//android.widget.TextView[@text='HEAT']");    //Change thermostat mode
    public final By coolCurrentModeText = By.xpath("//android.widget.TextView[@text='COOL']");    //Change thermostat mode
    public final By offCurrentModeText = By.xpath("//android.widget.TextView[@text='OFF']");
    @FindBy(id = "com.qolsys:id/btThermoMode")
    public WebElement setThermostatMode;
    @FindBy(id = "com.qolsys:id/btThermoMode")
    public WebElement Fan_Mode;
    @FindBy(id = "com.qolsys:id/btFanMode")
    public WebElement setFanMode;
    @FindBy(id = "com.qolsys:id/uiThermoBattery")
    public WebElement Therm_Battery;
    @FindBy(id = "com.qolsys:id/uiThermoCurrTemp")
    public WebElement Current_Temp;
    @FindBy(id = "com.qolsys:id/uiCurrTempText")
    public WebElement Current_Temp_Txt;
    //***********************************************
    //Fan mode popup window
    @FindBy(id = "com.qolsys:id/fanon")
    public WebElement Fan_On_Icon;
    @FindBy(id = "com.qolsys:id/fanonmode")
    public WebElement Fan_On_Txt;
    @FindBy(id = "com.qolsys:id/fanonmodetxt")
    public WebElement Fan_On_Message;
    @FindBy(id = "com.qolsys:id/fanoff")
    public WebElement Fan_Auto_Icon;
    @FindBy(id = "com.qolsys:id/fanoffmode")
    public WebElement Fan_Auto_Txt;
    @FindBy(id = "com.qolsys:id/fanoffmodetxt")
    public WebElement Fan_Auto_Message;
    //*******************************************************
    //mode selection popup window
    @FindBy(id = "com.qolsys:id/off")
    public WebElement offModeIcon;
    @FindBy(id = "com.qolsys:id/offmode")
    public WebElement offModeTxt;
    @FindBy(id = "com.qolsys:id/offmodetxt")
    public WebElement offModeMessage;
    @FindBy(id = "com.qolsys:id/heat")
    public WebElement heatMode;
    @FindBy(id = "com.qolsys:id/heatmode")
    public WebElement Heat_Mode_Txt;
    @FindBy(id = "com.qolsys:id/heatmodetxt")
    public WebElement Heat_Mode_Message;
    @FindBy(id = "com.qolsys:id/cool")
    public WebElement coolModeSelection;
    @FindBy(id = "com.qolsys:id/coolmode")
    public WebElement coolModeTxt;
    @FindBy(id = "com.qolsys:id/coolmodetxt")
    public WebElement Cool_Mode_Message;
    @FindBy(id = "com.qolsys:id/auto")
    public WebElement Auto_Icon;
    @FindBy(id = "com.qolsys:id/automode")
    public WebElement Auto_Mode_Txt;
    @FindBy(id = "com.qolsys:id/automodetxt")
    public WebElement Auto_Mode_Message;

//    public final By uHeatMode = By.xpath("//div[@id='app-content']/div/div["+ thermDiv + "]/div/div//div[@class='btn-group']/button[2]");
//    @FindBy(xpath = "//div[@id='app-content']/div/div["+ thermDiv + "]/div/div//div[@class='btn-group']/button[2]")
//    public WebElement heatModeUserSite;




}
