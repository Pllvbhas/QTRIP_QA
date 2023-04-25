package qtriptest.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class AdventurePage {
    WebDriver driver;
    @FindBy(id="duration-select") WebElement duration_filter;
    @FindBy(id="category-select") WebElement category;
    @FindBy(xpath = "//*[@id='search-adventures']") WebElement searchAdventures_filter;
    @FindBy(xpath = "(//*[contains(text(),'Clear')])[1]") WebElement clearDuratioElement;
    @FindBy(xpath = "(//*[contains(text(),'Clear')])[2]") WebElement clearCategoryElement;
    @FindBy(xpath = "(//h5[@class='text-left'])[1]") WebElement adventureToBeSelected;
    public AdventurePage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20), this);
    }
  
    public void setFilterValue(String duration) throws InterruptedException{
       // WebDriverWait wait = new WebDriverWait(driver,30);
        //    wait.until(ExpectedConditions.elementToBeClickable(duration_filter));
           Thread.sleep(2000);
            Select dropdown = new Select(duration_filter);
            duration_filter.click();
            
        dropdown.selectByVisibleText(duration);
       

    }
    public void setCategoryValue( String addCategory) throws InterruptedException{
       
            Thread.sleep(2000);
            Select category_dropdown = new Select(category);
            category.click();
            category_dropdown.selectByVisibleText(addCategory);
            

    }
   
    public boolean expectedFilterResult(String result) throws InterruptedException{

            Thread.sleep(3000);
      
        
        List<WebElement> filterResults = driver.findElements(By.className("category-banner"));
        if(filterResults.size()==Integer.parseInt(result)){
            return true;
        }
        else{
            return false;
        }

    }
    public void clearFilters(){
        clearDuratioElement.click();
        try {
            clearDuratioElement.click();
            Thread.sleep(2000);
            clearCategoryElement.click();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            System.out.println("exception found" + e.getMessage());
        }
        
    }
    public boolean ExpectedUnFilteredResults(String result) throws InterruptedException{
        // Thread.sleep(2000);
        List<WebElement> filterResults = driver.findElements(By.className("category-banner"));
        if(filterResults.size()==Integer.parseInt(result)){
            return true;
        }
            return false;

    }
    public boolean selectAdventure(String nameOfAdventure) throws InterruptedException{
        // boolean selected = true;
       searchAdventures_filter.sendKeys(nameOfAdventure);
       Thread.sleep(2000);
       adventureToBeSelected.click();
       //Create object of the Actions class
    //    Actions actions = new Actions(driver);
    //    actions.keyDown(Keys.ENTER);
    //    actions.keyUp(Keys.ENTER);
       return true;
       
    //    WebDriverWait wait = new WebDriverWait(driver,30);
    //    wait.until(ExpectedConditions.visibilityOfElementLocated(driver.findElement(By.xpath("//h5[@class='text-left']")), selected));
    }
    //    actions.build().perform();

}