package qtriptest.tests;

import net.bytebuddy.jar.asm.commons.Remapper;
import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testCase_01 {

    static String lastGeneratedUsername_;
    RemoteWebDriver driver;
    ExtentTest test;
    ExtentReports report;

    @BeforeTest(alwaysRun = true)
    public void driverSetup() throws MalformedURLException{
        driver = DriverSingleton.getInstance();
        driver.manage().window().maximize(); // maximize the 
        report = ReportSingleton.getReport();
        test = report.startTest("Qtrip_Login Flow");
    }
    // @BeforeTest(alwaysRun = true)

    @Test(description = "Verify registration happens correctly", priority = 1,groups= {"Login Flow"},  dataProvider = "data_provider", dataProviderClass = DP.class, enabled = true)
    
    public void TestCase01(String userName, String password) throws InterruptedException, IOException {
       
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

    

