package zwave;

import adc.ADC;
import adc.UIRepo;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.Setup;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LightsADC extends Setup {
    String page_name = "Lights_ADC";
    Logger logger = Logger.getLogger(page_name);
    ADC adc = new ADC();

    //adc Credentials
    String login = "Gen2-8334";
    String password = "qolsys1234";

    //light states
    String light_on = "light_on";
    String light_off = "light_off";

    //triggers
    String arm_away = "Armed Away";
    String arm_stay = "Armed Stay";
    String disarm = "Disarmed";

    //responses
    String turn_on = "Turn ON";
    //String turn_off = "Turn OFF";

    public LightsADC() throws Exception {
        ConfigProps.init();
    }

    //compares each light icon on the page to a given state
    public void checkPanelUI(String state) throws Exception {
        File light_state = new File(projectPath + "/scr/" + state);
        Thread.sleep(10000);
        List<WebElement> status = driver.findElements(By.id("com.qolsys:id/statusButton"));

        checkAllStatus(light_state, status);
    }

    //given a trigger, response, and light number (0, 1, or 2), creates a corresponding rule
    public void add_light_rule(String trigger, String response, int light_number) {
        driver1.findElement(By.partialLinkText("Add a Rule")).click();
        driver1.findElement(By.id("ctl00_phBody_txtRuleName")).sendKeys(response + " upon " + trigger);
        driver1.findElement(By.id("ctl00_phBody_rbArmingTrigger")).click();
        Select dropdown1 = new Select(driver1.findElement(By.id("ctl00_phBody_ddlArmingLevel")));
        Select dropdown2 = new Select(driver1.findElement(By.id("ctl00_phBody_ddlAction")));
        dropdown1.selectByVisibleText(trigger);
        dropdown2.selectByVisibleText(response);
        driver1.findElement(By.id("ctl00_phBody_ucLightGroupsWithFewLights_rptDevices_ctl0" + light_number +
                "_lnkDeviceName")).click();

        driver1.findElement(By.id("ctl00_phBody_pageActionButtons_buttonSave")).click();
    }


    @BeforeTest
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        adc.webDriverSetUp();
        setupLogger(page_name);
    }

    @Test
    public void turnOnLights() throws Exception {
        logger.info("individually turning lights on from adc");

        //navigate to user site
        adc.navigate_to_user_site(login, password);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("emPower")));
        adc.driver1.findElement(By.partialLinkText("emPower")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Lights")));
        adc.driver1.findElement(By.id("Lights")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("id('ctl00_phBody_ucLightDeviceRepeater" +
                "Control_SwitchesAndDimmers_rptDevices_ctl00_btnDevicesViewDeviceOn')")));

        //individually turn on all 3 lights
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices" +
                "_ctl00_btnDevicesViewDeviceOn')")).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices" +
                "_ctl01_btnDevicesViewDeviceOn')")).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices" +
                "_ctl02_btnDevicesViewDeviceOn')")).click();
        Thread.sleep(2000);

        swipeLeft();
        checkPanelUI(light_on);
    }

    @Test(priority = 1)
    public void turnOffLights() throws Exception {
        logger.info("individually turning lights off from adc");

        //individually turn all 3 lights off
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices_" +
                "ctl00_btnDevicesViewDeviceOff')")).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices" +
                "_ctl01_btnDevicesViewDeviceOff')")).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightDeviceRepeaterControl_SwitchesAndDimmers_rptDevices" +
                "_ctl02_btnDevicesViewDeviceOff')")).click();
        Thread.sleep(2000);

        checkPanelUI(light_off);
    }

    @Test(priority = 2)
    public void groupOn() throws Exception {
        logger.info("turning lights on as a group in adc");

        //begin creation of new group
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_lnkNewGroup')")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("id('ctl00_lblPageTitle')")));
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_txtName')")).sendKeys("test");
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_LinkButtonWithPermissionsChecker1')")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("id('ctl00_phBody_rptEditDevices_ctl00_" +
                "lblDevice')")));

        //add all 3 lights & save
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_rptEditDevices_ctl00_lblDevice')")).click();
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_rptEditDevices_ctl01_lblDevice')")).click();
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_rptEditDevices_ctl02_lblDevice')")).click();
        adc.driver1.findElement(By.xpath("//button[contains(.,'Done')]")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_btnSave")));
        Thread.sleep(2000);
        adc.driver1.findElement(By.id("ctl00_phBody_btnSave")).click();
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("id('ctl00_phBody_ucLightGroupRepeater" +
                "Control_SwitchesAndDimmers_rptGroups_ctl00_lblGroupName')")));

        //turn on group
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightGroupRepeaterControl_SwitchesAndDimmers_" +
                "rptGroups_ctl00_btnGroupOn')")).click();
        Thread.sleep(3000);

        checkPanelUI(light_on);
    }

    @Test(priority = 3)
    public void groupOff() throws Exception {
        logger.info("turning lights off as a group in adc");

        //turn off group
        adc.driver1.findElement(By.xpath("id('ctl00_phBody_ucLightGroupRepeaterControl_SwitchesAndDimmers_" +
                "rptGroups_ctl00_btnGroupOff')")).click();
        Thread.sleep(10000);

        //delete group
        adc.driver1.findElement(By.id("ctl00_phBody_ucLightGroupRepeaterControl_SwitchesAndDimmers_rptGroups_ctl00" +
                "_lnkGroupEdit")).click();
        adc.driver1.findElement(By.id("ctl00_phBody_btnDelete")).click();

        checkPanelUI(light_off);
    }

    @Test(priority = 4)
    public void rules() throws Exception {
        logger.info("testing adc rule creation");
        /*adc.navigate_to_user_site(login, password);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("emPower")));
        getDriver1().findElement(By.partialLinkText("emPower")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Rules")));

        //create rules
        getDriver1().findElement(By.id("Rules")).click();

        add_light_rule(disarm, turn_on, 0);
        add_light_rule(arm_stay, turn_on, 1);
        add_light_rule(arm_away, turn_on, 2);*/

        //trigger rules
        swipeFromLefttoRight();
        ARM_STAY();
        Thread.sleep(3000);
        DISARM();
        Thread.sleep(3000);
        ARM_AWAY(15);
        driver.findElement(By.id("com.qolsys:id/main")).click();
        enterDefaultUserCode();
        swipeLeft();
        Thread.sleep(3000);

        checkPanelUI(light_on);

        List<WebElement> status = driver.findElements(By.id("com.qolsys:id/statusButton"));
        clickAll(status);
        Thread.sleep(6000);
    }

    @Test
    public void Tets1() throws InterruptedException {
        //Light Off before testing
        UIRepo web = PageFactory.initElements(adc.driver1, UIRepo.class);
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        adc.driver1.get("https://www.alarm.com/web/system/home/lights");
        Thread.sleep(5000);
        web.Light_Off.click();
        Thread.sleep(2000);
        web.Light_On.click();
        Thread.sleep(5000);
        Assert.assertTrue(web.Light_Status.getText().equals("ON"));
        Thread.sleep(5000);
        System.out.println(web.Light_Name.getText());
        System.out.println(web.Light_Time_Stamp.getText());
        web.Light_On.click();
        Thread.sleep(3000);
        web.Light_Off.click();
        Thread.sleep(5000);
        Assert.assertTrue(web.Light_Status.getText().equals("OFF"));
        System.out.println(web.Light_Time_Stamp.getText());
    }
    @Test
    public void test2() throws InterruptedException {
        adc.New_ADC_session_User(ConfigProps.login, ConfigProps.password);
        adc.driver1.get("https://www.alarm.com/web/History/EventHistory.aspx");
        Thread.sleep(10000);

        WebElement mytable = adc.driver1.findElement(By.xpath("//*[@id='ctl00_phBody_acdgEventHistory']/tbody"));
        List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));

        int rows_count = rows_table.size();

        System.out.println(rows_count);


    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
        adc.driver1.quit();
        service.stop();
    }
}
