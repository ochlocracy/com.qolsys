package panel;

import org.testng.annotations.Test;
import utils.Setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by nchortek on 6/22/17.
 * PRECONDITION: 3 lights must be paired and turned off before
 * executing this test.
 */
public class adbTest extends Setup {

    public adbTest() throws Exception {}



    public static String execCmd(String cmd) throws java.io.IOException {
       // Process proc = Runtime.getRuntime().exec(new String[] { "bash", "-c", cmd });
        Process proc = Runtime.getRuntime().exec(cmd);
        java.io.InputStream is = proc.getInputStream();
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        String val = "";
        if (s.hasNext()) {
            val = s.next();
        }
        else {
            val = "";
        }
        System.out.println(val);
        return val;
    }

    public String split_method (  String str) {
        String a = str.split("\\n")[1];
        return a.split("\\s")[0];
    }

   // public String adbPath1 = System.getenv("ANDROID_HOME") + "platform-tools/adb";

    public void adbPath1 () throws IOException {
        String[] cmd = {"/bin/sh", "-c", "$ANDROID_HOME"};
        rt.exec(cmd );

    }

    public String get_UDID() throws IOException {
        String command =  " devices";
        rt.exec(command);
        String panel_UDID = split_method(execCmd(command));
        return panel_UDID;
    }

//    @BeforeMethod
//    public void capabilities_setup() throws Exception {
//        setup_driver(get_UDID(), "http://127.0.1.1", "4723");
//    }

    @Test
    public void Test() throws Exception {
        String cmd = ("adb reboot");
        Process p = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        System.out.println(in.readLine());

    }
//
//    @AfterMethod
//    public void tearDown () throws IOException, InterruptedException {
//        driver.quit();
    }

