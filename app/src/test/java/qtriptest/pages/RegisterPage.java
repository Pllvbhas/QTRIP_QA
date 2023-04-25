package qtriptest.pages;

import java.util.UUID;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {
    WebDriver driver;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
    public String lastGeneratedUsername = "";
    // @CacheLookUp
    @FindBy(id = "floatingInput")
    WebElement username_Textbox;
    @FindBy(id = "floatingPassword")
    WebElement password_Textbox;
    @FindBy(xpath = "//input[@name='confirmpassword']")
    WebElement confirm_password_Textbox;
    @FindBy(xpath ="//button[text()='Register Now']" )
    WebElement registerUser_button;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }
    public void navigateToRegisterPage() {
        if (!driver.getCurrentUrl().equals(this.url)) {
            driver.get(this.url);
        }
    }
    public boolean registerNewuser(String userName, String password, String confirmPassword,
            boolean generateRandomUsername) throws InterruptedException {
        // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String testdata_username = userName;

        if (generateRandomUsername = true) {
            testdata_username = userName + UUID.randomUUID().toString();// if(makeUsernameDynamic)
        } else {
            testdata_username = userName;
        }
        // {
        // testdata_username = userName + "-" + timestamp.getTime();
        // }
        // else
        // {
        // testdata_username = userName;
        // }
        username_Textbox.clear();
        username_Textbox.sendKeys(testdata_username);
        Thread.sleep(2000);
        password_Textbox.clear();
        password_Textbox.sendKeys(password);
        Thread.sleep(2000);
        confirm_password_Textbox.clear();
        confirm_password_Textbox.sendKeys(confirmPassword);
        Thread.sleep(2000);
        registerUser_button.click();

        Thread.sleep(2000);
        this.lastGeneratedUsername = testdata_username;
       // WebDriverWait wait = new WebDriverWait(driver, 10);
        Thread.sleep(3000);
        // wait.until(ExpectedConditions
        //         .urlToBe("https://qtripdynamic-qa-frontend.vercel.app/pages/login/"));
        return this.driver.getCurrentUrl().endsWith("/login");
    }

}

