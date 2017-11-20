package iqRemote;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ElementsList {

    @FindBy(id = "com.qolsys:id/wire_less_toggle")
    public WebElement WiFi;
    @FindBy(id = "android:id/summary")
    public WebElement Connected;
    @FindBy(id = "com.qolsys:id/t3_img_disarm")
    public WebElement Disarm;
    @FindBy(id = "com.qolsys:id/img_arm_stay")
    public WebElement ArmStay;
    @FindBy(id = "com.qolsys:id/img_arm_away")
    public WebElement ArmAway;
    @FindBy(id="com.qolsys:id/tv_keyOne")
    public WebElement One;
    @FindBy(id="com.qolsys:id/tv_keyTwo")
    public WebElement Two;
    @FindBy(id="com.qolsys:id/tv_keyThree")
    public WebElement Three;
    @FindBy(id="com.qolsys:id/tv_keyFour")
    public WebElement Four;





}
