package practice;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import panel.ContactUs;
import panel.HomePage;
import utils.RetryAnalizer;
import utils.Setup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MyTest extends Setup {

    WebDriver driver = new FirefoxDriver();
    Setup  setup = new Setup();

    public MyTest() throws Exception {}

    public void getListOfLinks() {
        List<WebElement> list = driver.findElements(By.className(""));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getText());
        }
    }

  //  @Test
    public void dynamicXPATH() throws InterruptedException {
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        //start_with
//        driver.findElement(By.xpath("//*[starts-with(@id,'oooooo')]/a"));
//        //contains
//        driver.findElement(By.xpath("//*[contains(@id,'oooooo')]/a"));
        driver.get("https://alarmadmin.alarm.com/Support/CustomerInfo.aspx?action=select&customer_Id=4679473");
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@id='txtUsername']")).sendKeys("qautomation");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id='txtPassword']")).sendKeys("Qolsys123");
        driver.findElement(By.xpath("//*[@id='butLogin']")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//        List <WebElement> list = driver.findElements(By.xpath("//*[@id='ctl00_navLinks']"));
//        for (int i=0; i<list.size(); i++){
//            System.out.println(list.get(i).getText());
//        }
        Thread.sleep(3000);
        driver.findElement(By.xpath("//a[@title='See equipment list for a customer']")).click();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.quit();
    }


    @Test(priority = 1, retryAnalyzer = RetryAnalizer.class)
    public void Low_Battery() throws InterruptedException, IOException {
        HomePage home = PageFactory.initElements(driver, HomePage.class);
        ContactUs contact = PageFactory.initElements(driver, ContactUs.class);
//      String file = projectPath + "/extent-config.xml";
//      report.loadConfig(new File(file));
//      rep.log.log(LogStatus.INFO,"Software Version", softwareVersion()); it actually failed here. will re run this as a solo test tomorrow.

        Thread.sleep(4000);
//        try {
//            home.Home_button.click();
//        } catch (NoSuchElementException e) {
//        }
        pgprimaryCall(104, 1101, "80 1");
        Thread.sleep(15000);
        home.Contact_Us.click();
        contact.Messages_Alerts_Alarms_tab.click();
        Thread.sleep(1000);
        WebElement string = driver.findElement(By.id("com.qolsys:id/ui_msg_text"));
        Assert.assertTrue(string.getText().contains("DW 104-1101(1) - Low Battery"));

    } //40sec
}
