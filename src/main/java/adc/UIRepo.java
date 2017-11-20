package adc;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class UIRepo {

    @FindBy(xpath = "//div[contains(@class, 'icon ') and contains(@title, 'Disarmed ')]")
    public WebElement Disarm_state;
    @FindBy(xpath = "//button[contains(@id, 'ember') and contains(@class, 'armed-stay btn ember-view')]")
    public WebElement Arm_Stay;
    @FindBy(xpath = "//div[contains(@class, 'icon ') and contains(@title, 'Armed Stay ')]")
    public WebElement Arm_Stay_state;
    @FindBy(xpath = "//button[contains(@id, 'ember') and contains(@class, 'disarmed btn ember-view')]")
    public WebElement Disarm;

}
