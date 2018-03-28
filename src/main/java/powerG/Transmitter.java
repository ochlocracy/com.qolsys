package powerG;

import org.testng.annotations.Test;
import qolsys.powerGRadioController.PowergDeviceHandler;

import java.io.IOException;
import java.net.Socket;


public class Transmitter {

    PowergDeviceHandler powergDevice =PowergDeviceHandler.getPowergDeviceHandler();
    Socket MyClient;


    @Test
    public void test1() throws IOException {
        try {
            MyClient = new Socket("Machine name", 0);
        }
        catch (IOException e) {
            System.out.println(e);
        }

        String Sticker_Id = "1012222";
        powergDevice.send_registration(Sticker_Id);
        powergDevice.stop_read_thread();
    }


}
