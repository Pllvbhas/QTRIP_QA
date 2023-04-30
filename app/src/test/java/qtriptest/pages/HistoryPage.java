
package qtriptest.pages;

import qtriptest.SeleniumWrapper;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {
    WebDriver driver;
    
    public HistoryPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    }
   
    public void getReservations() throws InterruptedException{
        Thread.sleep(4000);
    WebElement reservation_button = driver.findElement(By.linkText("Reservations"));
    // reservation_button.click();
    SeleniumWrapper.click(reservation_button, driver);
    }
    public void  gettransactionid(){
        WebElement transactionID = driver.findElement(By.xpath("//tr//th[@scope='row']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElements(transactionID));
        transactionID.getText();
    }
    public boolean cancelReservation(String advName) throws InterruptedException{
      WebElement adventurename =  driver.findElement(By.xpath("//tr//td[2]"));
      advName = adventurename.getText();
      WebElement cancel = driver.findElement(By.xpath("//button[@class='cancel-button']"));
      SeleniumWrapper.click(cancel, driver);
      //cancel.click();
     // Thread.sleep(2000);
      //WebElement transactionID = driver.findElement(By.xpath("//tr//th[@scope='row']"));
      WebDriverWait wait1 = new WebDriverWait(driver, 10);
      wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("no-reservation-banner")));
      return true;
      
       
        
      }
      
    }
