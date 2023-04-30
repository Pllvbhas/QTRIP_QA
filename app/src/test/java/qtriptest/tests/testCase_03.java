package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.print.DocFlavor.STRING;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_03 {
    static String lastGeneratedUsername_;
    RemoteWebDriver driver;
    ExtentTest test;
    ExtentReports report;

    @BeforeTest(alwaysRun = true)
    public void driverSetup() throws MalformedURLException{
        driver = DriverSingleton.getInstance();
        driver.manage().window().maximize(); // maximize the window
        report = ReportSingleton.getReport();
        test = report.startTest("Qtrip_Booking and Cancellation Flow");
    }
    @Test (description= "Verify that adventure booking and cancellation works fine", priority= 3,groups= {"Booking and Cancellation Flow"},  dataProvider = "data_provider", dataProviderClass = DP.class, enabled = true )
    public void TestCase03(String NewUser, String Password, String SearchCity, String AdventureName, String GuestName, String Date, String count ) throws InterruptedException, IOException {
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
        test.log(LogStatus.FAIL,test.addScreenCapture(capture_screenshot(driver))+ "Test Failed");
        
    }
    
  String capture_screenshot(RemoteWebDriver driver) throws IOException {
    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    File Dest = new File(System.getProperty("user.dir") + "/src/test/java/qtriptest/screenshots/" + System.currentTimeMillis()
    + ".png");
    String errflpath = Dest.getAbsolutePath();
    FileUtils.copyFile(scrFile, Dest);
    return errflpath;              
        } 
@AfterSuite
public void quitDriver() {
    driver.quit();

    //  - End the test
    report.endTest(test);
    //  - Write the test to filesystem
    report.flush();
} 
}
