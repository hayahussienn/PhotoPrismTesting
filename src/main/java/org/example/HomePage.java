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

public class HomePage {
    private WebDriver driver;
    private By contentPageBy = By.className("theme-default");
    private SearchingActions searchActions;
    private FilteringActions filteringActions;
    private BlackWhiteActions blackWhiteActions;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.searchActions = new SearchingActions(driver); // Composition: HomePage "has a" SearchingActions
        this.filteringActions = new FilteringActions(driver); // Composition: HomePage "has a" FilteringActions
        this.blackWhiteActions = new BlackWhiteActions(driver); // Composition: HomePage "has a" BlackWhiteActions
    }

    public SearchingActions getSearchActions() {
        return searchActions;
    }

    public FilteringActions getFilteringActions() {
        return filteringActions;
    }

    public BlackWhiteActions getBlackWhiteActions() {
        return blackWhiteActions;
    }

    public boolean isLoggedInSuccess() {
        WebElement contentPage = driver.findElement(contentPageBy);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        wait.until(d -> contentPage.isDisplayed());
        return contentPage.isDisplayed();
    }

    /**
     * Performs a complete Black & White conversion workflow
     * @param waitTimeInSeconds how long to wait for conversion to complete
     * @return the new window handle if conversion was successful, null otherwise
     */
    public String convertImageToBlackAndWhite(int waitTimeInSeconds) {
        if (!blackWhiteActions.openPhoto()) {
            return null;
        }

        if (!blackWhiteActions.clickBlackWhiteButton()) {
            return null;
        }

        if (!blackWhiteActions.handlePossibleAlert()) {
            return null;
        }

        return blackWhiteActions.waitForConversionAndSwitchToNewWindow(waitTimeInSeconds);
    }
}