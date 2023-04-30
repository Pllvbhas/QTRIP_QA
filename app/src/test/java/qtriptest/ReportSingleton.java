package qtriptest;
import java.io.File;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeTest;



public class ReportSingleton {

 static String lastGeneratedUsername_;
 RemoteWebDriver driver; 
 private static ExtentReports report = null;
 private ReportSingleton(){}

     public static ExtentReports  getReport(){
     //driver = DriverSingleton.getInstance();
     //driver.manage().window().maximize(); // maximize the window
     if(report == null){
     String path = System.getProperty("user.dir")
                + "/src/test/java/qtriptest/ExtentReportResults.html";
     report = new ExtentReports(path, true);

     report.loadConfig(new File(System.getProperty("user.dir") + "/app/extent_customization_configs.xml"));
     }
     return report;
 }
    
}
