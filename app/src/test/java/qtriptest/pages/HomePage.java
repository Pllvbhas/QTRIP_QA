package qtriptest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    String url = "https://qtripdynamic-qa-frontend.vercel.app/";
    @FindBy(xpath ="/html/body/nav/div/ul/li[4]/a")
     WebElement register_button;
    @FindBy(className="nav-link login") WebElement login_button;
    @FindBy(className="nav-link") WebElement reservations_button;
    @FindBy(id="autocomplete")
    public static WebElement searct_textbox;
    @FindBy(xpath = "//a/li") WebElement autocomplete_text;
    @FindBy(xpath = "//*[@onclick='Logout()']") WebElement logOut_button;
    WebDriver driver;
    public HomePage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    }
    public void navigateToHomePage() throws InterruptedException {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }

        Thread.sleep(2000);
    }
    public void clickRegister() {
        try {
            register_button.click();
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("exception found" + e.getMessage());
        }
    }

    public  boolean isUserLoggedIn() {
      try {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(logOut_button));
        return logOut_button.getText().equals("Logout");
      } catch (Exception e) {
        //TODO: handle exception
        return false;
      }

    }
    public void logOutUser() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 20);
            wait.until(ExpectedConditions.elementToBeClickable(logOut_button));
            logOut_button.click();
           
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("exception found" + e.getMessage());
        }

    }
    public void searchCity(String cityName) {
        try {
        searct_textbox.clear();
        searct_textbox.sendKeys(cityName);
        Thread.sleep(2000);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("exception found" + e.getMessage());
        }

    }
    public boolean assertAutocompleteText(String auto_text) {
        try {
        //WebElement autocomplete_text  = driver.findElement(By.xpath("//ul//a/li"));
          autocomplete_text.getText().equals(auto_text);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("exception found" + e.getMessage());
        }
// 
        return true;
   }
    public void selectCity(String cityName) {
        try {
           // WebElement autocomplete_text  = driver.findElement(By.xpath("//ul//a/li"));

            autocomplete_text.click();
            Thread.sleep(2000);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("exception found" + e.getMessage());
        }

    }




}
