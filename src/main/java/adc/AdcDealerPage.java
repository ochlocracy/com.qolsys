package adc;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by qolsys on 8/1/18.
 */
public class AdcDealerPage {
    @FindBy(id = "")
    public WebElement name1;

    @FindBy(xpath = "")
    public WebElement name;

    @FindBy(id = "ctl00_phBody_ZWaveDeviceList_btnRemoteAdd")
    public WebElement dealerAddZwaveDevice;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_btnCancelAddStep1")
    public WebElement remoteAddCancelBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteDeleteDevices_btnCancelDeleteStep1")
    public WebElement remoteDeleteCancelBtn;

    @FindBy(id = "ctl00_phBody_ZWaveRemoteAddDevices_btnSaveAndExit")
    public WebElement remoteAddExitBtn;

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
