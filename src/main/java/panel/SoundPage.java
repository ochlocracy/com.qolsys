package panel;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SoundPage {

    @FindBy(xpath = "//android.widget.TextView[@text='Volume']")
    public WebElement Volume;
    @FindBy(xpath = "//android.widget.TextView[@text='Edit Chimes']")
    public WebElement Edit_Chimes;
    @FindBy(xpath = "//android.widget.TextView[@text='Voices']")
    public WebElement Voices;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensors']")
    public WebElement Sensors_Voice;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel']")
    public WebElement Panel_Voice;
    @FindBy(xpath = "//android.widget.TextView[@text='Activity Monitoring']")
    public WebElement Activity_Monitoring;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-Wave Device Voice Prompts']")
    public WebElement ZWave_Device_Voice_Prompts;
    @FindBy(xpath = "//android.widget.TextView[@text='Z-Wave Remote Voice Prompts']")
    public WebElement ZWave_Remote_Voice_Prompts;
    @FindBy(xpath = "//android.widget.TextView[@text='All Chimes']")
    public WebElement All_Chimes;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor Chimes']")
    public WebElement Sensor_Chimes;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel']")
    public WebElement Panel_Chimes;
    @FindBy(xpath = "//android.widget.TextView[@text='Activity Sensor']")
    public WebElement Activity_Sensor;
    @FindBy(xpath = "//android.widget.TextView[@text='Trouble Beeps']")
    public WebElement Trouble_Beeps;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor Low Battery']")
    public WebElement Sensor_Low_Battery;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor Tamper Beeps']")
    public WebElement Sensor_Tamper_Beeps;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel Tamper Beeps']")
    public WebElement Panel_Tamper_Beeps;
    @FindBy(xpath = "//android.widget.TextView[@text='Edit Trouble Beep Chimes']")
    public WebElement Edit_Trouble_Beep_Chimes;
    @FindBy(xpath = "//android.widget.TextView[@text='Trouble Beeps Timeout']")
    public WebElement Trouble_Beeps_Timeout;
    @FindBy(xpath = "//android.widget.TextView[@text='Fire Safety Device Trouble Beeps']")
    public WebElement Fire_Safety_Device_Trouble_Beeps;
    @FindBy(xpath = "//android.widget.TextView[@text='Touch Sounds']")
    public WebElement Touch_Sounds;


//Sound settings summary

    @FindBy(xpath = "//android.widget.TextView[@text='Touch to adjust volume of text to speech, beeps and chimes and video volume']")
    public WebElement Volume_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Customize sounds for individual sensors']")
    public WebElement Edit_Chimes_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voices are enabled']")
    public WebElement Voices_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voices are disabled']")
    public WebElement Voices_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voice will vocalize sensor actions']")
    public WebElement Sensors_voice_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to vocalize sensor actions using panel voice']")
    public WebElement Sensors_voice_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voice will vocalize system notifications']")
    public WebElement Panel_voice_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to vocalize system notifications using panel voice']")
    public WebElement Panel_voice_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voice will vocalize activity sensor actions']")
    public WebElement Activity_Monitoring_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to vocalize activity sensor actions using panel voice']")
    public WebElement Activity_Monitoring_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voice will vocalize Z-Wave commands']")
    public WebElement ZWave_devices_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to vocalize Z-Wave commands using panel voice']")
    public WebElement ZWave_devices_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel voice will vocalize Z-Wave commands sent remotely']")
    public WebElement ZWave_remote_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to vocalize Z-Wave commands sent remotely using panel voice']")
    public WebElement ZWave_remote_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel chimes are enabled']")
    public WebElement All_chimes_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel chimes are disabled']")
    public WebElement All_chimes_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor chimes are enabled']")
    public WebElement Sensor_chime_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Sensor chimes are disabled']")
    public WebElement Sensor_chime_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Use panel chimes to report system notifications']")
    public WebElement Panel_chime_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Use panel chimes to report system notifications']")
    public WebElement Panel_chime_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Activity sensors are enabled']")
    public WebElement Activity_Sensor_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable chimes for activity sensors']")
    public WebElement Activity_Sensor_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel will use chimes to indicate problems or issues']")
    public WebElement Trouble_beeps_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to allow Panel to use chimes to indicate problems or issues']")
    public WebElement Trouble_beeps_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel will emit chimes periodically to indicate low batteries']")
    public WebElement Sensor_Low_Battery_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to allow Panel to emit chimes periodically to indicate low batteries']")
    public WebElement Sensor_Low_Battery_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel will emit a chime periodically if a sensor has been tampered']")
    public WebElement Sensor_Tamper_Beeps_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to allow Panel to emit a chime periodically if a sensor has been tampered']")
    public WebElement Sensor_Tamper_Beeps_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel will chime periodically if the panel tamper switch has been triggered']")
    public WebElement Panel_Tamper_Beeps_summery_enabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to allow Panel to chime periodically if the panel tamper switch has been triggered']")
    public WebElement Panel_Tamper_Beeps_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Customize trouble beep chimes']")
    public WebElement Edit_Trouble_Beep_Chimes_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Emit trouble beeps every 30 minutes']")
    public WebElement Trouble_Beeps_Timeout_summery;
    @FindBy(xpath = "//android.widget.TextView[@text='Enable to allow the Panel to use chimes to indicate Fire Safety device trouble conditions']")
    public WebElement Fire_Safety_Device_Trouble_Beeps_summery_disabled;
    @FindBy(xpath = "//android.widget.TextView[@text='Panel will use chimes to indicate Fire Safety device trouble conditions']")
    public WebElement Fire_Safety_Device_Trouble_Beeps_summery_enabled;
}
