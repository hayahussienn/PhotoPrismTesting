package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchingActions
{
    private WebDriver driver;
    private By searchBoxBy = By.xpath("//input[@aria-label='Search']");
    private By photoListBy = By.cssSelector("div.result.card.is-photo");
    private By messageBy = By.xpath("//h3[@class='body-2 ma-0 pa-0' and span[text()='No pictures found']]");





    public SearchingActions(WebDriver driver)
    {
        this.driver = driver;

    }

    public void searchByKeyword(String keyWord)
    {
        // Set initial implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Locate the search box
        WebElement searchBox = driver.findElement(searchBoxBy);

        // Create a WebDriverWait instance
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));

        // Wait until the search box is displayed
        wait.until(d -> searchBox.isDisplayed());

        // Enter the keyword and press ENTER
        searchBox.sendKeys(keyWord);
        searchBox.sendKeys(Keys.ENTER);


    }

    public List<WebElement> getListofPhotosByKeyWord(String keyWord)
    {
        searchByKeyword(keyWord);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        // Wait until the photos are displayed
        wait.until(d -> !driver.findElements(photoListBy).isEmpty());

        return driver.findElements(photoListBy);
    }

    public  String searchWithInvalidKeyWord(String invalidKeyWord)
    {
        searchByKeyword(invalidKeyWord);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        WebElement noPictureMessage = driver.findElement(messageBy);
        wait.until(d -> noPictureMessage.isDisplayed());
        return noPictureMessage.getText();
    }





}
