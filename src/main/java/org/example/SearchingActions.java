package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchingActions
{
    private WebDriver driver;
    private By searchBoxBy = By.cssSelector("input.v-field__input[placeholder='Search']");
    private By photoListBy = By.cssSelector("div.media.result.is-photo");

    private By messageBy = By.xpath("//div[contains(@class, 'v-alert__content')]/div[contains(text(), 'No pictures found')]");
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);





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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        searchBox.sendKeys(Keys.ENTER);

    }


    public List<WebElement> getListofPhotosByKeyWord(String keyWord)
    {
        searchByKeyword(keyWord);
        // Wait until photo results are visible
        new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(photoListBy));

        return driver.findElements(photoListBy);
    }


    public List<WebElement> getListofPhotosBySearch2KeyWord(String keyWord)
    {
        searchByKeyword(keyWord);
        // Wait until photo results are visible
        new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(photoListBy));

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