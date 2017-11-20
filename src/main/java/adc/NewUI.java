package adc;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.ConfigProps;
import utils.Setup;

public class NewUI extends Setup {

    ADC adc = new ADC();
    UIRepo homePage;
    public NewUI() throws Exception {
        ConfigProps.init();
    }

    public void webDriverSetUp() {
        driver1 = new FirefoxDriver();
        wait = new WebDriverWait(driver1, 300);
    }

    @BeforeMethod
    public void setUp() {
        adc.webDriverSetUp();
    }

    @Test
    public void Test() throws InterruptedException {
        homePage = PageFactory.initElements(adc.driver1, UIRepo.class);
        adc.driver1.manage().window().maximize();
        adc.driver1.get(ConfigProps.url);
        Thread.sleep(2000);
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_loginform_txtUserName")));
        adc.driver1.findElement(By.id("ctl00_ContentPlaceHolder1_loginform_txtUserName")).sendKeys(ConfigProps.login);
        adc.driver1.findElement(By.xpath(".//*[@id='aspnetForm']/div[5]/div/div[1]/div[1]/div/div[2]/input")).sendKeys(ConfigProps.password);
        adc.driver1.findElement(By.id("ctl00_ContentPlaceHolder1_loginform_signInButton")).click();
        Thread.sleep(2000);
        try {
            if (adc.driver1.findElement(By.xpath("//*[@id='ember735']")).isDisplayed()) {
                adc.driver1.findElement(By.xpath("//*[@id='ember735']")).click();
            }
        } catch (NoSuchElementException e) {
        }
        Thread.sleep(10000);
        homePage.Disarm_state.click();
        Thread.sleep(2000);
        homePage.Arm_Stay.click();
        Thread.sleep(4000);
        homePage.Arm_Stay_state.click();
        Thread.sleep(5000);
        homePage.Disarm.click();
        Thread.sleep(4000);
    }

    @AfterMethod
    public void tearDown() {
        adc.driver1.quit();
    }
}
