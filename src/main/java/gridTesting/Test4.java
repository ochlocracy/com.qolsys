package gridTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test4 {

    GridSetup setup = new GridSetup();

    @Parameters({"deviceName_", "applicationName_", "UDID_", "platformVersion_", "URL_", "PORT_" })
    @BeforeClass
    public void setUp(String deviceName_, String applicationName_, String UDID_, String platformVersion_, String URL_, String PORT_) throws MalformedURLException {
        setup.setCapabilities(URL_);
    }


    @Parameters({"UDID_"})
    @Test(threadPoolSize = 3)
    public void  Test1(String UDID_) throws Exception {
        TimeUnit.SECONDS.sleep(3);
        setup.getDriver().findElement(By.id("com.qolsys:id/btn_drop")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_tray_settings")).click();
        WebElement a =  setup.getDriver().findElement(By.id("com.qolsys:id/gridview"));
        List<WebElement> li = a.findElements(By.className("android.widget.TextView"));
        li.get(8).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyOne")).click();
        li.get(2).click();
        setup.getDriver().findElement(By.xpath("//android.widget.TextView[@text='Software']")).click();
        WebElement soft_version =  setup.getDriver().findElement(By.id("com.qolsys:id/summary"));
        String current_version = soft_version.getText();
        System.out.println(current_version);
        setup.getDriver().findElement(By.id("com.qolsys:id/ft_home_button")).click();
        TimeUnit.SECONDS.sleep(3);
        setup.getDriver().findElement(By.id("com.qolsys:id/t3_home_tab2")).click();
        TimeUnit.SECONDS.sleep(1);
        setup.add_primary_call(1, 10, 6619305, setup.door_window, UDID_);//default name Door/Window 1
        setup.add_primary_call(2, 17, 5570628, setup.motion, UDID_); //default name Motion 2
        setup.add_primary_call(3, 26, 6750242, setup.smoke_detector, UDID_);
        setup.add_primary_call(4, 34, 7667882, setup.co_detector, UDID_); //default name CO Detector 4
        System.out.println("Go to the settings");
        setup.getDriver().findElement(By.id("com.qolsys:id/btn_drop")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_tray_settings")).click();
        WebElement element =  setup.getDriver().findElement(By.xpath("//android.widget.TextView[@text='ADVANCED SETTINGS']"));
        element.click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
        setup.getDriver().findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
        li.get(0).click();
        li.get(0).click();
        li.get(0).click();
        li.get(2).click();
        TimeUnit.SECONDS.sleep(5);
        setup.Rename_Sensor(0, setup.new_dw_name);
        setup.logger.info("Renaming motion sensor, new name: " + setup.new_motion_name);
        setup.Rename_Sensor(1, setup.new_motion_name);
        setup.logger.info("Renaming smoke detector, new name: " + setup.new_smoke_name);
        setup.Rename_Sensor(2, setup.new_smoke_name);
        setup.logger.info("Renaming co_detector, new name: " + setup.new_co_name);
        setup.Rename_Sensor(3, setup.new_co_name);
        TimeUnit.SECONDS.sleep(3);
        setup.getDriver().findElement(By.id("com.qolsys:id/ft_home_button")).click();
        TimeUnit.SECONDS.sleep(3);
        setup.getDriver().findElement(By.id("com.qolsys:id/t3_home_tab2")).click();
        setup.delete_from_primary(UDID_, 1);
        setup.delete_from_primary(UDID_, 2);
        setup.delete_from_primary(UDID_, 3);
        setup.delete_from_primary(UDID_, 4);
    }

    @AfterClass(alwaysRun = true)
    public void TearDown(){
        setup.getDriver().quit();
    }
}
