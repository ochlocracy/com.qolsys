package gridTestingSetup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test3 {

    GridSetup setup = new GridSetup();

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws MalformedURLException {
        setup.setCapabilities(URL_);
    }

    @Parameters ({"UDID_"})
    @Test(threadPoolSize = 3)
    public void Test1 (String UDID_) throws InterruptedException, MalformedURLException {

        for (int i = 30; i > 0; i--) {
            setup.Software_Version();
            TimeUnit.SECONDS.sleep(3);
            setup.getDriver().findElement(By.id("com.qolsys:id/btn_drop")).click();
            setup.getDriver().findElement(By.id("com.qolsys:id/tv_tray_settings")).click();
            WebElement a = setup.getDriver().findElement(By.id("com.qolsys:id/gridview"));
            List<WebElement> li = a.findElements(By.className("android.widget.TextView"));
            li.get(8).click();
            setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
            li.get(2).click();
            setup.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Software']")).click();
            WebElement soft_version = setup.getDriver().findElement(By.id("com.qolsys:id/summary"));
            String current_version = soft_version.getText();
            System.out.println(current_version);
            setup.getDriver().findElement(By.id("com.qolsys:id/ft_home_button")).click();
            TimeUnit.SECONDS.sleep(3);
            setup.getDriver().findElement(By.id("com.qolsys:id/t3_home_tab2")).click();
            TimeUnit.SECONDS.sleep(1);
        }
    }
    @AfterClass(alwaysRun = true)
    public void TearDown(){
        setup.getDriver().quit();
    }

}
