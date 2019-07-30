package adcSanityTests;

import adc.ADC;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import panel.PanelInfo_ServiceCalls;
import utils.Setup;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class TestLink extends Setup {

    String page_name = "TestLink Name Change";
    Logger logger = Logger.getLogger(page_name);
    ADC adc = new ADC();
    //String accountID = adc.getAccountId();
    PanelInfo_ServiceCalls servcall = new PanelInfo_ServiceCalls();

    /*** If you want to run tests only on the panel, please set ADCexecute value to false ***/
    //String ADCexecute = "true";

    public TestLink() throws Exception {
    }
    public WebDriver driver1;


    public void New_TL_session() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        adc.driver1.manage().window().maximize();
        String TestLinkURL = "http://testlink.iqolsys.com/login.php";
        adc.driver1.get(TestLinkURL);
        String login = "zachary.pulling";
        String password = "Qowu2879";
        Thread.sleep(2000);
        adc.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tl_login")));
        adc.driver1.findElement(By.id("tl_login")).sendKeys(login);
        adc.driver1.findElement(By.id("tl_password")).sendKeys(password);
        //adc.driver1.findElement(By.id("submit")).click();
        //needs a login button link
        Thread.sleep(15000);
        adc.driver1.get("http://testlink.iqolsys.com/lib/general/frmWorkArea.php?feature=editTc");
    }

    public void manualToAutomatic(String linkText) {
        WebElement toolkit_options = (new WebDriverWait(adc.driver1, 20))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//html/body/div/fieldset/form[3]/select")));
        //adc.driver1.findElement(By.xpath("html/body/div/fieldset/form[3]/select")).click();
        Select Stoolkit_options = new Select(toolkit_options);
        Stoolkit_options.selectByVisibleText(linkText);
        //html/body/div/fieldset/form[3]/select/option[2]
        //html/body/div/fieldset/form[3]/select
    }

    @BeforeClass
    public void capabilities_setup() throws Exception {
        setupLogger(page_name);
        adc.webDriverSetUp();
    }

    @Test
    public void navigateToPage() throws java.lang.Exception {
        New_TL_session();
        Thread.sleep(2000);
    }

//    @Test
//    public void expandAll() throws java.lang.Exception {
//        Thread.sleep(2000);
//        adc.driver1.findElement(By.id("expand_tree")).click();
//    }

    @Test
    public void selectTest() throws java.lang.Exception {
        int id = 320; //beginning test case
        while (id < 431) { //ending (-1) test case                              //change the (GEN) to (iqHub) depending on test area you want to change
            adc.driver1.get("http://testlink.iqolsys.com/linkto.php?tprojectPrefix=GEN&item=testcase&id=GEN-" + id);
            Thread.sleep(10000);

            //The following commented out lines of code could not locate the manual to automatic drop down element.
            //You have 10 seconds to decide whether to change the specific test to automatic or keep it at manual.
            //You can change the range of Gen-# test case it starts and ends at and how much time you want to have to decide to change it.

//            adc.driver1.findElement(By.xpath("//html/body/div/fieldset/form[3]/select")).click();
//            //new Actions(driver).moveByOffset(x coordinate, y coordinate).click().build().perform();
//            manualToAutomatic("Automated");
//            Select drpMtoA = new Select(adc.driver1.findElement(By.name("exec_type")));
////            drpMtoA.selectByVisibleText("Automatic");
//            drpMtoA.selectByValue("2");

            id++;
        }
    }

    @AfterClass
    public void tearDown() throws IOException, InterruptedException {
    }
}






