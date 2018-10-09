package adc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by qolsys on 8/1/18.
 */
public class AdcDealerPage {
    @FindBy(id = "")
    public WebElement deviceNames;

    @FindBy(xpath = "//table[@id='tblAddedDevices']//span[@class='tbDeviceName']")
    public WebElement name;

    //*******************Empower Page Edit name**************************************

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_btnEditDeviceNames")
    public WebElement empowerEditNameBtn;

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_EditDeviceNames_rptAddedDevices_ctl02_txtDeviceName")
    public WebElement empowerEditName;
//    WebElement empowerEditName1 = driver.findElement(By.id("ctl00_phBody_ZWaveDeviceList_EditDeviceNames_rptAddedDevices_ctl0"+number+"_txtDeviceName"));

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_EditDeviceNames_btn_SaveDeviceNames")
    public WebElement saveEditNameBtn;

    //***********Remote add*****************

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_btnRemoteAdd")
    public WebElement dealerAddZwaveDeviceBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_btnCancelAddStep1")
    public WebElement remoteAddCancelBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_btnSaveAndExit")
    public WebElement saveAndExitAddMode;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_rptAddedDevicesStep2_ctl00_txtDeviceName")
    public WebElement newlyAddDeviceName;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_lblAddStatus")
    public WebElement addModeMessage;


//    String addmodeMessage = "ctl00_phBody_ZWaveRemoteAddDevices_lblAddStatus";
    //*********Remote Delete

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_btnRemoteDelete")
    public WebElement remoteDeleteBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteDeleteDevices_btnCancelDeleteStep1")
    public WebElement remoteDeleteCancelBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteDeleteDevices_btnCancelDeleteStep1")
    public WebElement getRemoteAddCancelBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteDeleteDevices_lblDeleteStatus")
    public WebElement deleteModeMessage;



//    @FindBy(id = "")
//    public WebElement name1;

//    @FindBy(id = "")
//    public WebElement name1;

//    @FindBy(id = "")
//    public WebElement name1;

//    @FindBy(id = "")
//    public WebElement name1;

//    @FindBy(id = "")
//    public WebElement name1;

//    @FindBy(id = "")
//    public WebElement name1;


}
