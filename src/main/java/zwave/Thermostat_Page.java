package zwave;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Thermostat_Page {

    @FindBy(id = "com.qolsys:id/uiTargTemp")
    public WebElement Target_Temp;
    @FindBy(id = "com.qolsys:id/btTempUp")
    public WebElement Temp_Up;
    @FindBy(id = "com.qolsys:id/btTempDown")
    public WebElement Temp_Down;
    @FindBy(id = "com.qolsys:id/uiThermoName")
    public WebElement Thermostat_Name;
    @FindBy(id = "com.qolsys:id/uiThermoMode")
    public WebElement Current_Mode;
    @FindBy(id = "com.qolsys:id/btFanMode")
    public WebElement Fan_Mode;
    @FindBy(id = "com.qolsys:id/btThermoMode")
    public WebElement Set_Mode;
    @FindBy(id = "com.qolsys:id/uiThermoBattery")
    public WebElement Therm_Battery;
    @FindBy(id = "com.qolsys:id/uiThermoCurrTemp")
    public WebElement Current_Temp;
    @FindBy(id = "com.qolsys:id/uiCurrTempText")
    public WebElement Current_Temp_Txt;
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
    @FindBy(id = "com.qolsys:id/off")
    public WebElement Off_Mode_Icon;
    @FindBy(id = "com.qolsys:id/offmode")
    public WebElement Off_Mode_Txt;
    @FindBy(id = "com.qolsys:id/offmodetxt")
    public WebElement Off_Mode_Message;
    @FindBy(id = "com.qolsys:id/heat")
    public WebElement Heat_Icon;
    @FindBy(id = "com.qolsys:id/heatmode")
    public WebElement Heat_Mode_Txt;
    @FindBy(id = "com.qolsys:id/heatmodetxt")
    public WebElement Heat_Mode_Message;
    @FindBy(id = "com.qolsys:id/cool")
    public WebElement Cool_Icon;
    @FindBy(id = "com.qolsys:id/coolmode")
    public WebElement Cool_Mode_Txt;
    @FindBy(id = "com.qolsys:id/coolmodetxt")
    public WebElement Cool_Mode_Message;
    @FindBy(id = "com.qolsys:id/auto")
    public WebElement Auto_Icon;
    @FindBy(id = "com.qolsys:id/automode")
    public WebElement Auto_Mode_Txt;
    @FindBy(id = "com.qolsys:id/automodetxt")
    public WebElement Auto_Mode_Message;

}
