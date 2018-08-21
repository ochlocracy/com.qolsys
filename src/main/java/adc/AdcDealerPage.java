package adc;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by qolsys on 8/1/18.
 */
public class AdcDealerPage {
    @FindBy(id = "")
    public WebElement deviceNames;

    @FindBy(xpath = "")
    public WebElement name;

    //***********Remote add*****************

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_btnRemoteAdd")
    public WebElement dealerAddZwaveDeviceBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_btnCancelAddStep1")
    public WebElement remoteAddCancelBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_btnSaveAndExit")
    public WebElement saveAndExitAddMode;

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
