package iqRemote;


import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import panel.PanelCameraPage;
import utils.ConfigProps;
import utils.Setup;

public class DisarmPhotos extends SetupRemote {

    EventContains EventContaints = new EventContains();
    Setup setup = new Setup();
    String logcat = "/home/qolsys/IdeaProjects/comqolsys2017/log/test.txt";
    public DisarmPhotos() throws Exception {
        ConfigProps.init();
    }

    @BeforeMethod
    public void Setup() throws Exception {
        setup_driver("6NJUMEQPGZ", "http://127.0.1.1", "4723");
        // deleteLogFile(logcat);
    }

    @Test
    public void Delete_All_Photos() throws Exception {
        System.out.println("Delete_All_Photos Begin");
        Thread.sleep(3000);

        PanelCameraPage camera = PageFactory.initElements(driver, PanelCameraPage.class);
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
        rt.exec(ConfigProps.adbPath + " ls -l /storage/sdcard0/DisarmPhotos | busybox1.11  wc -l");
    }

    @Test
    public void Add_Disarm_Photos() throws Exception {
        System.out.println("Add_All_Photos Begin");

    }
}
//check qtms for testing rules examples

//precondition; delete all photos
//verify from ui and from internal SD

//The idea is to be able to send the event from the iqRemote panel
//and do the verification on the Primary panel and on the adc dealer website.

//shell commands, see how many files their are
//max of 20 images will be stored.
//creating 21st will overwrite.

//run test. then wait for remote to disconnect and connect again.

//verify on remote


//eventually
//will be on gen 2
//will read logs and compare. log check / library needs to be written
//checking event was sent to adc