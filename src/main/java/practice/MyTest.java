//package practice;
//import com.relevantcodes.extentreports.LogStatus;
//import org.openqa.selenium.support.PageFactory;
//import org.testng.Assert;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
//import panel.ContactUs;
//import panel.HomePage;
//import utils.RetryAnalizer;
//import utils.Setup;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;
//
//import static java.lang.Integer.reverse;
//
//public class MyTest extends Setup {
//
//    public static void first(String[] args) {
//
//        @BeforeTest
//        public void setup () throws Exception {
//            setupDriver(get_UDID(), "http://127.0.1.1", "4723");
//            setupLogger(page_name);
//        }
//
//        @BeforeMethod
//        public void webdriver () {
//        }
//
//        @Test
//        public void addSensors () throws IOException, InterruptedException {
//            addPGSensors("AuxPendant", 320, 1015, 0);//gr6
//            addPGSensors("AuxPendant", 320, 1016, 4);//gr4
//            addPGSensors("AuxPendant", 320, 1018, 5);//gr25
//
//        }
//    }
//}
