package qtriptest.tests;

import qtriptest.DP;
import org.testng.annotations.Test;
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
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

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

public class testCase_04 {
    static String lastGeneratedUsername_;
    RemoteWebDriver driver;
    ExtentReports report;
    ExtentTest test;

    

    @BeforeTest(alwaysRun = true)
    public void driverSetup() throws MalformedURLException {
        driver = DriverSingleton.getInstance();
        driver.manage().window().maximize(); // maximize the 
        report = ReportSingleton.getReport();
        test = report.startTest("Qtrip_Reliability Flow");
    }

    @Test(description = "Verify that adventure booking and cancellation works fine", priority = 4,groups= {"Reliability Flow"},
            dataProvider = "data_provider", dataProviderClass = DP.class, enabled = true)
    public void TestCase04(String NewUser, String Password, String dataset1, String dataset2,
            String dataset3) throws InterruptedException, IOException {
       
        Boolean status;
        HomePage homepage = new HomePage(driver);
        homepage.navigateToHomePage();
        // Thread.sleep(3000);
        homepage.clickRegister();
        RegisterPage registration = new RegisterPage(driver);
        registration.navigateToRegisterPage();
        registration.registerNewuser(NewUser, Password, Password, true);
        Assert.assertTrue(
                driver.getCurrentUrl()
                        .equals("https://qtripdynamic-qa-frontend.vercel.app/pages/login"),
                "failed to register new");
        lastGeneratedUsername_ = registration.lastGeneratedUsername;
        System.out.println(lastGeneratedUsername_);
        LoginPage login = new LoginPage(driver);
        login.performLogIn(lastGeneratedUsername_, Password);
        driver.switchTo().alert().accept();
        Thread.sleep(2000);
        status = driver.getCurrentUrl().equals("https://qtripdynamic-qa-frontend.vercel.app/");
        Assert.assertTrue(status, "failed to login user");

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(HomePage.searct_textbox));

        String[] DS1 = dataset1.split(";");
        // for (String a : DS1)
        //     System.out.println(a);
        homepage.searchCity(DS1[0]);
        homepage.assertAutocompleteText(DS1[0]);
        homepage.selectCity(DS1[0]);


        AdventurePage adv = new AdventurePage(driver);
        status = adv.selectAdventure(DS1[1]);
        WebDriverWait wait1 = new WebDriverWait(driver, 30);
        wait1.until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.className("reserve-button"))));

        Assert.assertTrue(status, "Adventure is not selected");
        AdventureDetailsPage adp = new AdventureDetailsPage(driver);
        adp.bookAdventure(DS1[2], DS1[3], DS1[4]);
        Thread.sleep(2000);

        homepage.navigateToHomePage();
        // WebElement home_button = driver.findElement(By.linkText("Home"));
        // home_button.click();
        WebDriverWait wait2 = new WebDriverWait(driver, 30);
        wait2.until(ExpectedConditions.visibilityOf(HomePage.searct_textbox));
        String[] DS2 = dataset2.split(";");
        // for (String b : DS2)
        //     System.out.println(b);

        homepage.searchCity(DS2[0]);
        homepage.assertAutocompleteText(DS2[0]);
        homepage.selectCity(DS2[0]);

        status = adv.selectAdventure(DS2[1]);
        WebDriverWait wait3 = new WebDriverWait(driver, 30);
        wait3.until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.className("reserve-button"))));

        Assert.assertTrue(status, "Adventure is not selected");
        
        adp.bookAdventure(DS2[2], DS2[3], DS2[4]);

        Thread.sleep(2000);
        homepage.navigateToHomePage();
        // WebElement home_button1 = driver.findElement(By.linkText("Home"));
        // home_button1.click();

        WebDriverWait wait4 = new WebDriverWait(driver, 30);
        wait4.until(ExpectedConditions.visibilityOf(HomePage.searct_textbox));
        
        String[] DS3 = dataset3.split(";");
        // for (String c : DS3)
        //     System.out.println(c);
        homepage.searchCity(DS3[0]);
        homepage.assertAutocompleteText(DS3[0]);
        homepage.selectCity(DS3[0]);

        status = adv.selectAdventure(DS3[1]);
        WebDriverWait wait5 = new WebDriverWait(driver, 30);
        wait5.until(ExpectedConditions
                .elementToBeClickable(driver.findElement(By.className("reserve-button"))));

        Assert.assertTrue(status, "Adventure is not selected");
        
        adp.bookAdventure(DS3[2], DS3[3], DS3[4]);
        Thread.sleep(2000);
        HistoryPage history = new HistoryPage(driver);
        history.getReservations();
        Thread.sleep(4000);
        history.gettransactionid();
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
