package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;
import java.util.List;

public class HomePage
{
    private WebDriver driver;
    private By contentPageBy = By.className("theme-default");
    private SearchingActions searchActions;
    private FilteringActions filteringActions;






    public HomePage(WebDriver driver)
    {
        this.driver = driver;
        this.searchActions = new SearchingActions(driver); // Composition: HomePage "has a" SearchingActions
        this.filteringActions = new FilteringActions(driver); // Composition: HomePage "has a" FilteringActions

    }
    public SearchingActions getSearchActions()
    {
        return searchActions;
    }

    public FilteringActions getFilteringActions() {
        return filteringActions;
    }

    public boolean isLoggedInSuccess()
    {
        WebElement contentPage = driver.findElement(contentPageBy);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        wait.until(d -> contentPage.isDisplayed());
        return contentPage.isDisplayed();

    }


}
