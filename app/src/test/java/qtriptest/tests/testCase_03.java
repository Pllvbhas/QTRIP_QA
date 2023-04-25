package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.net.MalformedURLException;
import javax.print.DocFlavor.STRING;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {
    static String lastGeneratedUsername_;
    RemoteWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void driverSetup() throws MalformedURLException{
        driver = DriverSingleton.getInstance();
        driver.manage().window().maximize(); // maximize the window
    }
    @Test (description= "Verify that adventure booking and cancellation works fine", priority= 3,groups= {"Booking and Cancellation Flow"},  dataProvider = "data_provider", dataProviderClass = DP.class, enabled = true )
    public void TestCase03(String NewUser, String Password, String SearchCity, String AdventureName, String GuestName, String Date, String count ) throws InterruptedException, MalformedURLException {
        Boolean status;
        HomePage homepage = new HomePage(driver);
        homepage.navigateToHomePage();
       // Thread.sleep(3000);
        homepage.clickRegister();
        RegisterPage registration = new RegisterPage(driver);
        registration.navigateToRegisterPage();
        registration.registerNewuser(NewUser, Password, Password, true);
        Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login"),
                "failed to register new");
        lastGeneratedUsername_ = registration.lastGeneratedUsername;
        System.out.println(lastGeneratedUsername_);
        LoginPage login = new LoginPage(driver);
        login.performLogIn(lastGeneratedUsername_, Password);
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        status = driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/");
        Assert.assertTrue(status, "failed to login user");
       
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOf(HomePage.searct_textbox));
        homepage.searchCity(SearchCity);
        homepage.assertAutocompleteText(SearchCity);
        homepage.selectCity(SearchCity);
        

        AdventurePage adv = new AdventurePage(driver);
        status = adv.selectAdventure(AdventureName);
        WebDriverWait wait1 = new WebDriverWait(driver,30);
        wait1.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("reserve-button"))));
      
        Assert.assertTrue(status, "Adventure is not selected");
        AdventureDetailsPage adp = new AdventureDetailsPage(driver);
        adp.bookAdventure(GuestName, Date, count);
        Thread.sleep(4000);
        HistoryPage history = new HistoryPage(driver);
        history.getReservations();
        Thread.sleep(4000);
        history.gettransactionid();
        Assert.assertTrue(history.cancelReservation(AdventureName), "Failed to cancel reservation");
        homepage.logOutUser();
    }
}
