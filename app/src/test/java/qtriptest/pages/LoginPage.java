package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;
    @FindBy(id="floatingInput") WebElement username_Textbox;
    @FindBy(id="floatingPassword") WebElement password_Textbox;
    @FindBy(xpath ="//*[text()='Login to QTrip']") WebElement logIN_button;
    @FindBy(xpath = "//div[text()='Logout']") WebElement logOut_button;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";
    public LoginPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    }
    public void navigateToLoginPage(){
        // if(!this.driver.getCurrentUrl().equals(this.url)){
        //     this.driver.get(this.url);
        // }
        SeleniumWrapper.NavigatetoURL(driver, url);
    }
    public void performLogIn(String userName, String password) throws InterruptedException{
        // username_Textbox.clear();
        // username_Textbox.sendKeys(userName);
        SeleniumWrapper.sendKeys(username_Textbox, userName);
        // password_Textbox.clear();
        // password_Textbox.sendKeys(password);
        SeleniumWrapper.sendKeys(password_Textbox, password);
        // logIN_button.click();
        SeleniumWrapper.click(logIN_button, driver);
        Thread.sleep(3000);
        // WebDriverWait wait = new WebDriverWait(driver, 10);
        // wait.until(ExpectedConditions.visibilityOf(logOut_button));
//System.out.println("user logged in successfully");
       
    }
    // public void logOutuser(){
    //     logOut_button.click();
    //     }
}
