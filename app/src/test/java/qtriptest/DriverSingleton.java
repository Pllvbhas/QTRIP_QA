package qtriptest;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverSingleton {
//Initialise the remote webdriver to null
private static RemoteWebDriver driverInstance = null;

// private conructor as it will stop from creating any new objects outside this class
private DriverSingleton(){
}

    public static RemoteWebDriver getInstance() throws MalformedURLException{
        // Launch Browser using Zalenium
        if(driverInstance == null) {
            final DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(BrowserType.CHROME);
            driverInstance = new RemoteWebDriver(new URL("http://localhost:8082/wd/hub"), capabilities);
            System.out.println("getInstance()");

            // WebDriverManager.chromedriver().setup();
            // driver = new ChromeDriver();
        }
        return driverInstance;
    }

    public static void quitDriver() {
        // System.out.println("quit()");
        driverInstance.quit();
        driverInstance = null;
    }
}