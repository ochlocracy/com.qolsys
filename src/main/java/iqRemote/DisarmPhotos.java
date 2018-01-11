package iqRemote;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.PanelCameraPage;
import utils.ConfigProps;
import utils.Setup;

import java.io.IOException;

public class DisarmPhotos extends SetupRemote {

    EventContains EventContaints = new EventContains();
    Setup setup = new Setup();

    public DisarmPhotos() throws Exception {
        ConfigProps.init();
    }

    @BeforeMethod
    public void setupDriver() throws Exception {
        setup_driver("6NJUM6KOWF", "http://127.0.1.1", "4723");
        // deleteLogFile(logcat);
    }

    @Test(priority = 1)
    public void deleteAllPhotos() throws Exception {
        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
        System.out.println("Delete_All_Photos Begin");
        Thread.sleep(15000);

        swipeFromLefttoRight(); //check

        Thread.sleep(3000);
        try {
            while (camera.Photo_lable.isDisplayed()) {
                camera.Camera_delete.click();
                camera.Camera_delete_yes.click();
                enter_default_user_code();
            }
        } catch (Exception e) {
            System.out.println("No photos left to delete...");
        } finally {
        }
        swipeFromRighttoLeft();
        Thread.sleep(3000);
        String process = ConfigProps.adbPath + " shell ls -l /storage/sdcard0/DisarmPhotos | busybox1.11  wc -l";
        setup.execCmd(process);
    }

    @Test(priority = 2)
    public void addDisarmPhotos() throws Exception {
        System.out.println("Add_All_Photos Begin");

        Thread.sleep(15000);
        driver.findElement(By.id("com.qolsys:id/img_arm_stay")).click();
        driver.findElement(By.id("com.qolsys:id/t3_img_disarm")).click();
        Thread.sleep(5000);
        driver.findElement(By.id("com.qolsys:id/tv_keyOne")).click();
        driver.findElement(By.id("com.qolsys:id/tv_keyTwo")).click();
        driver.findElement(By.id("com.qolsys:id/tv_keyThree")).click();
        driver.findElement(By.id("com.qolsys:id/tv_keyFour")).click();
        Thread.sleep(5000);

        //do a stop when it hits 21
        //would i have to check image count, compare variables then do a stop when its 21
        //maybe do a verify that it overwrites the last pic
    }

    @Test(priority = 3)
    public void checkPhotosAdded() throws Exception {
        System.out.println("Verify Added Photos Begin");

        Process exec = rt.exec(ConfigProps.adbPath + " ls -l /storage/sdcard0/DisarmPhotos | busybox1.11  wc -l");
        System.out.println(exec.getOutputStream().toString());
        System.out.println("Verify Added Photos Finished");


        //File dir = new File(Environment.getExternalStorageDirectory()
        //+ "/images");
        //File[] files = dir.listFiles();
        //int numberOfImages=files.length;


        //on main panel
        //on ADC
    }

    @AfterMethod
    public void tearDown() throws IOException, InterruptedException {
        driver.quit();
    }
}

//precondition; delete all photos CHECK
//verify from ui and from internal SD CHECK

//The idea is to be able to send the event from the IQRemote panel
//and do the verification on the Primary panel and on the ADC dealer website.

//shell commands, see how many files their are CHECK
//max of 20 images will be stored.
//creating 21st will overwrite.

//run test. then wait for remote to disconnect and connect again.

//verify on remote


//eventually
//will be on gen 2
//will read logs and compare. log check / library needs to be written
//checking event was sent to adc