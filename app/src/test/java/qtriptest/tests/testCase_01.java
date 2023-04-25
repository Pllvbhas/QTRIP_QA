package qtriptest.tests;

import net.bytebuddy.jar.asm.commons.Remapper;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;

import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_01 {

    static String lastGeneratedUsername_;
    RemoteWebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void driverSetup() throws MalformedURLException{
        driver = DriverSingleton.getInstance();
        driver.manage().window().maximize(); // maximize the window
    }
    // @BeforeTest(alwaysRun = true)

    @Test(description = "Verify registration happens correctly", priority = 1,groups= {"Login Flow"},  dataProvider = "data_provider", dataProviderClass = DP.class, enabled = true)
    public void TestCase01(String userName, String password) throws InterruptedException, MalformedURLException {
        Boolean status;
        HomePage homepage = new HomePage(driver);
        homepage.navigateToHomePage();
        Thread.sleep(3000);
        homepage.clickRegister();
        RegisterPage registration = new RegisterPage(driver);
        registration.navigateToRegisterPage();
        registration.registerNewuser(userName, password, password, true);
        Assert.assertTrue(driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login"),
                "failed to register new");
        lastGeneratedUsername_ = registration.lastGeneratedUsername;
        System.out.println(lastGeneratedUsername_);
        LoginPage login = new LoginPage(driver);
        login.performLogIn(lastGeneratedUsername_, password);
        driver.switchTo().alert().accept();
        status = driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/");
        Assert.assertTrue(status, "failed to login user");
       
        Thread.sleep(2000);
    
        homepage.logOutUser();
    }
}
