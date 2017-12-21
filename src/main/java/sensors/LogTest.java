package sensors;

import utils.Setup;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class LogTest extends Setup {

    public LogTest() throws Exception {
    }

    @BeforeMethod
    public void capabilities_setup() throws Exception {
        setupDriver(get_UDID(),"http://127.0.1.1", "4723");
    }

    @Test
    public void Test1 () throws Exception {
        deleteLogFile("/home/qolsys/Desktop/comqolsysPOM/log/test.txt");
        ARM_STAY();
        eventLogsGenerating("/home/qolsys/Desktop/comqolsysPOM/log/test.txt",new String[]{
                EventConstants.EVENT_TO_ADC_STAY,
                "*******  ArmingLevelChange: Level: 2, adc_device_class: 14, User: 0, normal_closing_ack: 1"},2); // arm stay
        //ArmingLevelChange: Level: 1, adc_device_class: 14, User: 1, normal_closing_ack: 0  // disarm
        Thread.sleep(2000);
        DISARM();
    }

    @Test
    public void  Test2 () throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(new File("/home/qolsys/Desktop/config.xls"));
        Sheet sh = wb.getSheet(0);
        String CellGetContent = sh.getCell(1,0).getContents();
        System.out.println(CellGetContent);
    }


    @AfterMethod
    public void tearDown () throws IOException, InterruptedException {
        driver.quit();
    }
}
