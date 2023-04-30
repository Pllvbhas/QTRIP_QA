package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HomePage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.management.openmbean.OpenType;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_02 {
    static String lastGeneratedUsername_;
    RemoteWebDriver driver;
    ExtentTest test;
    ExtentReports report;

    @BeforeSuite(alwaysRun = true)
    public void driverSetup() throws MalformedURLException{
        driver = DriverSingleton.getInstance();
        driver.manage().window().maximize(); // maximize the window
        report = ReportSingleton.getReport();
        test = report.startTest("Qtrip_Search and Filter flow");
    }
    @Test (description= "Verify that Search and filters work fine", priority= 2,groups= {"Search and Filter flow"}, dataProvider = "data_provider", dataProviderClass = DP.class, enabled= true )
    public void TestCase02(String cityName, String Category_Filter, String DurationFilter,  String ExpectedFilteredResults, String ExpectedUnFilteredResults ) throws InterruptedException, IOException {
        Boolean status;
        // WebDriver driver = DriverSingleton.getInstance();
        // driver.manage().window().maximize(); // maximize the window
        HomePage homepage = new HomePage(driver);
        homepage.navigateToHomePage();
        homepage.searchCity(cityName);
        // try {
        //Assert.assertEquals(driver.findElement(By.xpath("//h5[text()='No City found']")), "No City found");
        // } catch (Exception e) {
        //     //TODO: handle exception
        //     System.out.println("exception found" + e.getMessage());
        // }
      
        homepage.searchCity(cityName);
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(homepage.assertAutocompleteText(cityName));
    
        Thread.sleep(2000);
        homepage.selectCity(cityName);
        AdventurePage adv = new AdventurePage(driver);
        adv.setCategoryValue(Category_Filter);
        Thread.sleep(2000);
        adv.setFilterValue(DurationFilter);
        Thread.sleep(2000);
       // ExpectedFilteredResults = adv.getResultCount();
       status = adv.expectedFilterResult(ExpectedFilteredResults);
       Assert.assertTrue(status, "Expected count didn't match");
        adv.clearFilters();
        Thread.sleep(2000);
        //ExpectedUnFilteredResults = adv.getUnfilteredResultCount();
        status = adv.ExpectedUnFilteredResults(ExpectedUnFilteredResults);
        Assert.assertTrue(status);
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
