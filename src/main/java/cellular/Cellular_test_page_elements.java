package cellular;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Cellular_test_page_elements {
    @FindBy(id = "com.qolsys:id/carrier_text")
    public WebElement Carrier_name;
    @FindBy(id = "com.qolsys:id/cell_connection_text")
    public WebElement Cellular_status;
    @FindBy(id = "com.qolsys:id/imei_text")
    public WebElement IMEI;
    @FindBy(id = "com.qolsys:id/signal_strength_text")
    public WebElement signal_strength;
    @FindBy(id = "com.qolsys:id/start_button")
    public WebElement start_button;
    @FindBy(id = "com.qolsys:id/cancel_button")
    public WebElement cancel_button;
    @FindBy(id = "com.qolsys:id/result_text")
    public WebElement test_result;
    @FindBy(id = "com.qolsys:id/ft_back")
    public WebElement Back_button;










}
