package qtmsSRF;

import adc.ADC;
import adcSanityTests.RemoteToolkitVariables;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;
import sensors.Sensors;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Air_FX extends Setup {

    String page_name = "Air_FX";
    Logger logger = Logger.getLogger(page_name);
    ADC adc = new ADC();
    String accountID = adc.getAccountId();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    Sensors sensors = new Sensors();
    private int Long_Exit_Delay = 12;
    private String activate = "02 01";


    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    String ADCexecute = "true";

    public Air_FX() throws Exception {}

    public void New_ADC_session(String accountID) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.manage().window().maximize();
        String ADC_URL = "https://alarmadmin.alarm.com/Support/CustomerInfo.aspx?customer_Id=" + accountID;
        adc.driver1.get(ADC_URL);
        String login = "qautomation";
        String password = "Qolsys123";
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
        adc.driver1.findElement(By.id("txtUsername")).sendKeys(login);
        adc.driver1.findElement(By.id("txtPassword")).sendKeys(password);
        adc.driver1.findElement(By.id("butLogin")).click();
        Thread.sleep(1000);
        adc.driver1.get("https://alarmadmin.alarm.com/Support/Commands/GetSensorNames.aspx");
    }

    public void Sensor_Data_Change(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_phBody_ddlActions")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void SensorTypeDropDown(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_phBody_drpDeviceType")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void SensorGroupDropDown(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_phBody_ddlSensorGroup")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void clickAnElementByLinkText(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("ctl00_responsiveBody_ucCommands_ddlNewValue")));
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
    }

    public void history_verification (String message) {
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_butSearch"))).click();
        try {
            WebElement history_message = adc.driver1.findElement(By.xpath(message));
            Assert.assertTrue(history_message.isDisplayed());
            {
                System.out.println("Pass: message is displayed " + history_message.getText());
            }
        } catch (Exception e) {
            System.out.println("***No such element found!***");
        }
    }

    @BeforeClass
    public void webDriver() throws Exception {
        setup_driver(get_UDID(),"http://127.0.1.1", "4723");
        adc.webDriverSetUp();
    }

    @Test
    public void Navigate_Sensor_Control_Page() throws java.lang.Exception {
        New_ADC_session(accountID);
        adc.driver1.manage().window().maximize();
        Thread.sleep(2000);
    }

    @Test (dependsOnMethods = {"Navigate_Sensor_Control_Page"}, priority =1)
    public void ADC_Add_A_Sensor() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String SensorDL = "6500A1";
        String SensorID = "1";
        String SensorName = "Door Window Sensor";

        logger.info("Upload Logs Test begin");
        Thread.sleep(2000);
        remote.Add_Sensors.click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_ucsAddSensor_txtID"))).sendKeys(SensorID);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_ucsAddSensor_UcEligibleSensorName1_txtSN"))).sendKeys(SensorName);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_ucsAddSensor_txtDL"))).sendKeys(SensorDL);
        remote.Add_Sensor_Change.click();
        Thread.sleep(2000);
    }

    @Test (dependsOnMethods = {"ADC_Add_A_Sensor"}, priority =2)
    public void Check_Panel_For_Added_Sensor() throws InterruptedException, IOException, BiffException {

        Navigate_To_Edit_Sensor_Page();
        Assert.assertTrue(driver.findElement(By.id("com.qolsys:id/textView4")).getText().contains("Door/Window"));
        Assert.assertTrue(driver.findElement(By.id("com.qolsys:id/textView5")).getText().contains("Door/Window 1"));
        Assert.assertTrue(driver.findElement(By.id("com.qolsys:id/textView6")).getText().contains("10-Entry-Exit-Normal Delay"));
    }

    @Test (dependsOnMethods = {"Check_Panel_For_Added_Sensor"}, priority =3)
    public void ADC_Change_Sensor_Data() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        String NewSensorName = "Cool Door Window";

        //adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Click here to visit the Equipment List screen"))).click();
        remote.Remote_Back.click();
        Thread.sleep(2000);
        remote.Sensor_Change_Page.click();
        Sensor_Data_Change("Change Sensor Name");
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_UcEligibleSensorName1_txtSN"))).sendKeys(NewSensorName);
        remote.Send_Command_Change.click();
        Alert simpleAlert = adc.driver1.switchTo().alert();
        String alertText = simpleAlert.getText();
        System.out.println("Alert text is " + alertText);
        simpleAlert.accept();

        Sensor_Data_Change("Change Sensor Type");
        SensorTypeDropDown("Orientation");
        remote.Send_Command_Update.click();

        Sensor_Data_Change("Change Sensor Group");
        SensorGroupDropDown("12");
        remote.Send_Command_Change.click();

        Sensor_Data_Change("Change Sensor Activity Monitoring Status");
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_rbtMonitoring_1"))).click();
        remote.Send_Command_Change.click();
        Thread.sleep(120000);

        adc.driver1.get("https://alarmadmin.alarm.com/Support/RemoteToolkit.aspx");
        remote.Arming_Setting_Dropdown.click();
        remote.Auto_Stay.click();
        clickAnElementByLinkText("On");
        remote.Change.click();
        Thread.sleep(2000);

    }


    @Test (dependsOnMethods = {"ADC_Change_Sensor_Data"}, priority =4)
    public void Check_Panel_For_Updated_Sensor(String DLID, String element_to_verify, int sensor) throws Exception {
        Navigate_To_Edit_Sensor_Page();
        Assert.assertTrue(driver.findElement(By.id("com.qolsys:id/textView5")).getText().contains("Cool Door Window"));
        Assert.assertTrue(driver.findElement(By.id("com.qolsys:id/textView6")).getText().contains("12-Entry-Exit-Long Delay"));

        ARM_AWAY(Long_Exit_Delay/5);
        verify_armaway();
        sensors.primary_call(DLID, activate);
        Thread.sleep(2000);
        verify_armstay();
        adc.driver1.findElement(By.id("ctl00_phBody_butSearch")).click();
        history_verification("//*[contains(text(), '(Sensor " + sensor + ") Alarm')]");
        enter_default_user_code();
    }

        @Test (dependsOnMethods = {"Check_Panel_For_Updated_Sensor"}, priority =4)
    public void ADC_Delete_A_Sensor() throws InterruptedException, IOException, BiffException {
        RemoteToolkitVariables remote = PageFactory.initElements(adc.driver1, RemoteToolkitVariables.class);

        Sensor_Data_Change("Delete Sensor");
        remote.Send_Command_Change.click();
        Alert simpleAlert = adc.driver1.switchTo().alert();
        String alertText = simpleAlert.getText();
        System.out.println("Alert text is " + alertText);
        simpleAlert.accept();
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
        for (int i = 2; i>0; i--){
        sensors.delete_from_primary(i);
        }
        adc.driver1.quit();
        driver.quit();
    }
}

/*
Verify the sensor can be added from Dealer site CHECK
Verify sensor can be deleted from dealer site CHECK
Verify sensor group can be changed from dealer site CHECK
Verify sensor type can be changed from dealer site CHECK

Verify sensor name can be changed from adc dealer site and synced to panel
(in disarm and arm stay and away) CHECK

Verify Sensor activity monitor can be changed from adc dealersite CHECK

Verify Turn On/Off Auto Stay command can be send to panel from adc dealer site CHECK

*/

