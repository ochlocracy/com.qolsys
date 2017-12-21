package qtmsSettings;

import adc.ADC;
import adc.UIRepo;
import cellular.Dual_path_page_elements;
import cellular.System_Tests_page;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import panel.AdvancedSettingsPage;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;

import java.io.IOException;


public class DualPathADC extends Setup {
    public WebDriverWait wait;
    String page_name = "QTMS SystemTest_DualPath test cases";
    Logger logger = Logger.getLogger(page_name);
    ADC adc = new ADC();
    UIRepo repo;
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();
    public DualPathADC() throws Exception {
        /*** If you want to run tests only on the panel, please setADCexecute value to false ***/
        adc.setADCexecute("true");
    }

    public void webDriverSetUp() {
        driver1 = new FirefoxDriver();
        wait = new WebDriverWait(driver1, 300);
    }

    @BeforeTest
    public void capabilities_setup() throws Exception {

        setupDriver(get_UDID(), "http://127.0.1.1", "4723");
        setupLogger(page_name);
    }

    @BeforeMethod
    public void webDriver() {
        adc.webDriverSetUp();
    }

    @Test
    public void SASST_030() throws Exception {
        servcall.get_WiFi();
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        dual.start_button.click();
        Thread.sleep(10000);
        elementVerification(dual.Test_result, "Test result");
        servcall.set_ARM_STAY_NO_DELAY_enable();
        servcall.EVENT_ARM_STAY();
        Thread.sleep(2000);
        logger.info("SASST_030 Pass.");
        servcall.EVENT_DISARM();
        Thread.sleep(2000);
    }

    @Test
    public void SASST_031() throws Exception {
        servcall.set_ALL_CHIMES(1);
        Thread.sleep(5000);
        servcall.get_ALL_CHIMES();
        Thread.sleep(2000);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        dual.start_button.click();
        Thread.sleep(5000);
        elementVerification(dual.Test_result, "Test result");
        logger.info("adc AirFX");
        adc.driver1.manage().window().maximize();
        String ADC_URL = "https://alarmadmin.alarm.com/Support/CustomerInfo.aspx?customer_Id=" + adc.getAccountId();
        adc.driver1.get(ADC_URL);
        String login = "qapple";
        String password = "qolsys123";
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
        adc.driver1.findElement(By.id("txtUsername")).sendKeys(login);
        adc.driver1.findElement(By.id("txtPassword")).sendKeys(password);
        adc.driver1.findElement(By.id("butLogin")).click();
        Thread.sleep(2000);
        adc.driver1.get("https://alarmadmin.alarm.com/Support/AirFx/rt_ChimeSettings.aspx");
        Thread.sleep(1000);
        Select type_menu = new Select(adc.driver1.findElement(By.id("ctl00_phBody_drpdwnStatusAllChime")));
        type_menu.selectByVisibleText("OFF");
        Thread.sleep(1000);
        adc.driver1.findElement(By.id("ctl00_phBody_btnSubmit")).click();
        System.out.println("Please wait 5 minutes to get update of setting value");
        Thread.sleep(300000);
        servcall.get_ALL_CHIMES();
        Thread.sleep(2000);
    }

    @Test
    public void SASST_030_usersitearming() throws Exception {
        repo = PageFactory.initElements(driver, UIRepo.class);
        AdvancedSettingsPage adv = PageFactory.initElements(driver, AdvancedSettingsPage.class);
        System_Tests_page sys = PageFactory.initElements(driver, System_Tests_page.class);
        Dual_path_page_elements dual = PageFactory.initElements(driver, Dual_path_page_elements.class);
        navigateToAdvancedSettingsPage();
        adv.SYSTEM_TESTS.click();
        sys.DUAL_PATH_TEST.click();
        dual.start_button.click();
        Thread.sleep(6000);
        elementVerification(dual.Test_result, "Test result");
        adc.driver1.manage().window().maximize();
        String ADC_URL = "https://www.alarm.com/login.aspx";
        adc.driver1.get(ADC_URL);
        // String login = "LeBron_James";
        String login = "pan7Aut";
        String password = "qolsys123";
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_loginform_txtUserName")));
        adc.driver1.findElement(By.id("ctl00_ContentPlaceHolder1_loginform_txtUserName")).sendKeys(login);
        adc.driver1.findElement(By.xpath(".//*[@id='aspnetForm']/div[5]/div/div[1]/div[1]/div/div[2]/input")).sendKeys(password);
        adc.driver1.findElement(By.id("ctl00_ContentPlaceHolder1_loginform_signInButton")).click();
        Thread.sleep(2000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {
        }
//        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_phBody_ArmingStateWidget_btnArmStay"))).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.xpath("//div[contains(@class, 'icon ') and contains(@title, 'Disarmed ')]")).click();
        Thread.sleep(2000);
        adc.driver1.findElement(By.xpath("//button[contains(@id, 'ember') and contains(@class, 'armed-stay btn ember-view')]")).click();
        // adc.getDriver1().findElement(By.id("ctl00_phBody_ArmingStateWidget_cbArmOptionSilent")).click();
        // adc.getDriver1().findElement(By.id("ctl00_phBody_ArmingStateWidget_cbArmOptionNoEntryDelay")).click();
        // adc.getDriver1().findElement(By.id("ctl00_phBody_ArmingStateWidget_btnArmOptionStay")).click();
        Thread.sleep(5000);
        System.out.println("status verification");

        // if (adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("//div[contains(@class, 'icon ') and contains(@title, 'Armed Stay today ')]"))).isDisplayed()) {
        verifyArmstay();
        Thread.sleep(4000);
        // System.out.println("Please wait 5 minutes to get update of User site");
        logger.info("SASST_030 Pass:Remote arming takes less than 5 minutes after Dual path test passed.");
        servcall.EVENT_DISARM();
    }

    @AfterTest
    public void tearDown() throws IOException, InterruptedException {
        log.endTestCase(page_name);
        driver.quit();
    }

    @AfterMethod
    public void webDriverQuit() {
        adc.driver1.quit();
    }
}
