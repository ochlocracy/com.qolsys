package zwave;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Setup;

public class ZWavePage extends Setup{
    public ZWavePage() throws Exception {
    }

    //Main Z-Wave Page
    @FindBy(xpath = "//android.widget.TextView[@text='Add Device']")
    public WebElement addDeviceZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Edit Device']")
    public WebElement editDeviceZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Delete Failed Device']")
    public WebElement deleteFailedDeviceZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Clear Device']")
    public WebElement clearDeviceZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Remove All Devices']")
    public WebElement removeAllDevicesZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Z-wave settings']")
    public WebElement zwaveSettingsZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='NWI']")
    public WebElement nwiZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Replace Failed Node']")
    public WebElement replaceFailedNodeZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='Association']")
    public WebElement associationZwavePage;

    @FindBy(xpath = "//android.widget.TextView[@text='set as sis']")
    public WebElement setAsSISZwavePage;


    //Add Device Page
    @FindBy(id = "com.qolsys:id/button")
    public WebElement includeZwaveDeviceButton; // start the paring process

    @FindBy(id = "com.qolsys:id/negative_button")
    public WebElement addDeviceCancelButtonZwaveAddDevicePage; //Cancel pairing process button

    @FindBy(id = "com.qolsys:id/ok")
    public WebElement addDeviceOKButtonZwaveAddDevicePage;

    @FindBy(id = "com.qolsys:id/button")
    public WebElement newDevicePageAddBtn;// Add button in the new device page


    @FindBy(xpath = "//android.widget.Button[@text='Automation']")
    public WebElement panelLightAutomation; //Automation button for panel selection

    @FindBy(id = "com.qolsys:id/deviceName1")
    public WebElement deviceNameListMenu;//Device name Selection
    //Name Options
    //Name Selection Dropdown
    @FindBy(id = "com.qolsys:id/deviceName1")
    public WebElement nameSelectionDropDown;

    //Custom Name
    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Custom Name']")
    public WebElement customDeviceName;

    @FindBy(id = "com.qolsys:id/ok")
    public WebElement UnsupportedDeviceAcknowledgement;

    @FindBy(id= "com.qolsys:id/customDesc1")
    public WebElement customNameField;

    @FindBy(id = "com.qolsys:id/deviceType")
    public By deviceTypeField;

    @FindBy(xpath = "//android.widget.TextView[@text='Searching for Device']")
    public WebElement searchingForDevicePopup;
    //Zwave setting Page


    //*****************************************Stock Names**********************************************************

    //Lights Stock Names
    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Light']")
    public WebElement lightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Bedroom Light']")
    public WebElement bedroomLightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Downstairs Light']")
    public WebElement downstairsLightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Entertainment Center Light']")
    public WebElement entertainmentCenterLightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Family Room Light']")
    public WebElement familyRoomLightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Hallway Light']")
    public WebElement hallwayLightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Kitchen Light']")
    public WebElement kitchenLightStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Upstairs Light']")
    public WebElement upstairsLightStockName;




    // DoorLock Stock Names
    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Front Door']")
    public WebElement frontDoorStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Back Door']")
    public WebElement backDoorStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Garage Door']")
    public WebElement garageDoorStockName;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Side Door']")
    public WebElement sideDoorStockName;

    // Thermostat Stock Names

    //GDC Stock Names

    //Edit Device Page
    @FindBy(id = "com.qolsys:id/edit")
    public WebElement Edit_Device_Icon_Z_Wave_Edit_Device_Page; //Update button
    @FindBy(id = "com.qolsys:id/editButton")
    public WebElement Update_Device_Button_Z_Wave_Add_Device_Page;

    //Delete Failed Node Page
    @FindBy(id = "com.qolsys:id/btnDelete")
    public WebElement Delete_Button_Z_Wave_Delete_Failed_Node_Page;
    @FindBy(id = "com.qolsys:id/checkBox1")
    public WebElement Checkbox_One_Z_Wave_Delete_Failed_Device_Page;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement OK_Z_Wave_Delete_Failed_Device_Page;
    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement Cancel_Z_Wave_Delete_Failed_Device_Page;

    //Remove all Devices
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement oKBtnZwaveRemoveAllDevicesPage;

    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement Cancel_Z_Wave_Remove_All_Devices_Page;

    //NWI
    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement Quit_NWI_Z_Wave_Page;

    //Replace Failed Node Page
    @FindBy(id = "com.qolsys:id/checkBox1")
    public WebElement Checkbox_One_Z_Wave_Replace_Failed_Node_Page;
    @FindBy(id = "com.qolsys:id/btnDelete")
    public WebElement Replace_Button_Z_Wave_Replace_Failed_Node_Page;
    @FindBy(id = "com.qolsys:id/ok")
    public WebElement OK_Z_Wave_Replace_Failed_Node_Page;
    @FindBy(id = "com.qolsys:id/cancel")
    public WebElement Cancel_Z_Wave_Replace_Failed_Node_Page;

    //Association Page
    @FindBy(id = "com.qolsys:id/uiBTNInfo")
    public WebElement View_Button_Z_Wave_Association_Page;




}
