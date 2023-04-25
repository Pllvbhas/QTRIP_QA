
package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class AdventureDetailsPage {
    @FindBy(xpath = "//input[@name='name']")
    WebElement GuestName;
    @FindBy(xpath = "//input[@name='date']")
    WebElement selectDate;
    @FindBy(xpath = "//input[@name='person']")
    WebElement personCount;
    @FindBy(className="reserve-button") WebElement reserve_button;

    WebDriver driver;

    public AdventureDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public void bookAdventure(String Name, String date, String numOfPersons) throws InterruptedException {
        GuestName.sendKeys(Name);
        Thread.sleep(1000);
        // use handling of calenders
       selectDate.sendKeys(date);
       Thread.sleep(1000);
       personCount.clear();
        personCount.sendKeys(numOfPersons);
        Thread.sleep(1000);
        reserve_button.click();

    }

    public boolean isBookingSuccessful() {
        return false;

    }
}
