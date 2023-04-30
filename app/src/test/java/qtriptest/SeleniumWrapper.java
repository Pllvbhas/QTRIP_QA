package qtriptest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumWrapper {

public static boolean click(WebElement elementToclick, WebDriver driver){
    elementToclick.isDisplayed();
    ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", elementToclick);
    elementToclick.click();
    return true;
}
public static boolean sendKeys(WebElement input_box, String keysTosend){
    input_box.clear();
    input_box.sendKeys(keysTosend);
    return true;

}
public static boolean NavigatetoURL(WebDriver driver, String url){
    if (!driver.getCurrentUrl().equals(url)) {
        driver.get(url);
        return true;
    }
    return false;
}
public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount){
   int  intialCount = 0;
    WebElement element = driver.findElement(by);
   if(!element.isDisplayed()){
    while(intialCount<retryCount){
        element = driver.findElement(by);
        intialCount++;
    }
    
   }
   return element;

}
}
