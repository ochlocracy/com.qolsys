package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutPage {

    @FindBy(xpath = "//android.widget.TextView[@text='Battery']")
    public WebElement Battery;
    @FindBy(xpath = "//android.widget.TextView[@text='Battery Status']")
    public WebElement Battery_Status;
    @FindBy(xpath = "//android.widget.TextView[@text='Battery Level']")
    public WebElement Battery_Level;
    @FindBy(xpath = "//android.widget.TextView[@text='Software']")
    public WebElement Software;
    @FindBy(xpath = "//android.widget.TextView[@text='Software Version']")
    public WebElement Software_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Build Number']")
    public WebElement Build_Number;
    @FindBy(xpath = "//android.widget.TextView[@text='Linux Version']")
    public WebElement Linux_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Android OS Version']")
    public WebElement Android_OS_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Hardware']")
    public WebElement Hardware;
    @FindBy(xpath = "//android.widget.TextView[@text='Hardware Version']")
    public WebElement Hardware_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Manufacturer']")
    public WebElement Manufacturer;
    @FindBy(xpath = "//android.widget.TextView[@text='PCA Serial Number']")
    public WebElement PCA_Serial_Number;
    @FindBy(xpath = "//android.widget.TextView[@text='Part Number']")
    public WebElement Part_Number;
    @FindBy(xpath = "//android.widget.TextView[@text='System Configuration']")
    public WebElement System_Configuration;
    @FindBy(xpath = "//android.widget.TextView[@text='System Serial Number']")
    public WebElement System_Serial_Number;
    @FindBy(xpath = "//android.widget.TextView[@text='RF PIC Version']")
    public WebElement RF_PIC_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='EEPROM Format Version']")
    public WebElement EEPROM_Format_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Image Sensor Version']")
    public WebElement Image_Sensor_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Patches']")
    public WebElement Patches;
    @FindBy(xpath = "//android.widget.TextView[@text='Patch Description']")
    public WebElement Patch_Description;
    @FindBy(xpath = "//android.widget.TextView[@text='Last Upgrade Date']")
    public WebElement Last_Upgrade_Date;
    @FindBy(xpath = "//android.widget.TextView[@text='Patch']")
    public WebElement Patch;
    @FindBy(xpath = "//android.widget.TextView[@text='Date']")
    public WebElement Date;
    @FindBy(xpath = "//android.widget.TextView[@text='Status']")
    public WebElement Status;
    @FindBy(xpath = "//android.widget.TextView[@text='Checksum']")
    public WebElement Checksum;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel']")
    public WebElement Panel_About;
    @FindBy(xpath = "//android.widget.TextView[@text='MAC Address']")
    public WebElement MAC_Address;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel Up Time(YY:MM:DD:HH:mm)']")
    public WebElement Panel_Up_Time;
    @FindBy(xpath = "//android.widget.TextView[@text='Cellular']")
    public WebElement Cellular;
    @FindBy(xpath = "//android.widget.TextView[@text='Carrier']")
    public WebElement Carrier;
    @FindBy(xpath = "//android.widget.TextView[@text='Cellular Connection']")
    public WebElement Cellular_Connection;
    @FindBy(xpath = "//android.widget.TextView[@text='Cellular Signal Strength']")
    public WebElement Cellular_Signal_Strength;
    @FindBy(xpath = "//android.widget.TextView[@text='IMEI']")
    public WebElement IMEI;
    @FindBy(xpath = "//android.widget.TextView[@text='IMSI']")
    public WebElement IMSI;
    @FindBy(xpath = "//android.widget.TextView[@text='ICCID']")
    public WebElement ICCID;
    @FindBy(xpath = "//android.widget.TextView[@text='Baseband Version']")
    public WebElement Baseband_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-Wave']")
    public WebElement ZWave_About;
    @FindBy(xpath = "//android.widget.TextView[@text='Home ID']")
    public WebElement Home_ID;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-Wave Firmware Version']")
    public WebElement ZWave_Firmware_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-Wave Api Version']")
    public WebElement ZWave_Api_Version;
    @FindBy(xpath = "//android.widget.TextView[@text='Manufacturing ID']")
    public WebElement Manufacturing_ID;
    @FindBy(xpath = "//android.widget.TextView[@text='Product Type']")
    public WebElement Product_Type;
    @FindBy(xpath = "//android.widget.TextView[@text='Product ID']")
    public WebElement Product_ID;
    @FindBy(xpath = "//android.widget.TextView[@text='Ethernet']")
    public WebElement Ethernet;
    @FindBy(xpath = "//android.widget.TextView[@text='WI-FI Information']")
    public WebElement WI_FI_Info;
    @FindBy(xpath = "//android.widget.TextView[@text='Connection']")
    public WebElement Connection;
    @FindBy(xpath = "//android.widget.TextView[@text='IP Address']")
    public WebElement IP_Address;
    @FindBy(xpath = "//android.widget.TextView[@text='SSID']")
    public WebElement SSID;
    @FindBy(xpath = "//android.widget.TextView[@text='Speed']")
    public WebElement Speed;
    @FindBy(xpath = "//android.widget.TextView[@text='Internet']")
    public WebElement Internet;
    @FindBy(xpath = "//android.widget.TextView[@text='Internal storage']")
    public WebElement Internal_storage;
    @FindBy(xpath = "//android.widget.TextView[@text='Total space']")
    public WebElement Total_space;
    @FindBy(xpath = "//android.widget.TextView[@text='Total space']")
    public WebElement Available_space;
    @FindBy(xpath = "//android.widget.TextView[@text='Photos']")
    public WebElement Photos_About;
    @FindBy(xpath = "//android.widget.TextView[@text='Videos']")
    public WebElement Videos_About;
    @FindBy(xpath = "//android.widget.TextView[@text='Logs']")
    public WebElement Logs;
}
