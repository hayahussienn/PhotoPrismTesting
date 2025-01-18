package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class FilteringActions {

    private WebDriver driver;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    // Locators
    private By expandSearchBy = By.cssSelector("button.p-expand-search.v-btn[title='Expand Search']");
    private By filterCountryBy = By.className("p-countries-select");
    private By israelOptionBy = By.xpath("//div[contains(@class, 'v-list__tile__title') and text()='Israel']");
    private By photoListBy = By.cssSelector("div.result.card.is-photo");
    private By filterYearBy = By.className("p-year-select");
    private By option2018By = By.xpath("//div[contains(@class, 'v-list__tile__title') and text()='2018']");
    private By filterCategoryBy = By.className("p-category-select");
    private By animalOptionBy = By.xpath("//div[contains(@class, 'v-list__tile__title') and text()='Animal']");
    private By filterColorBy = By.className("p-color-select");
    private By redOptionBy = By.xpath("//div[contains(@class, 'v-list__tile__title') and text()='Red']");
    private By filterMonthBy = By.className("p-month-select");
    private By octoberOptionBy = By.xpath("//div[contains(@class, 'v-list__tile__title') and text()='October']");



    public FilteringActions(WebDriver driver)
    {
        this.driver = driver;
    }

    private void waitForElementToBeClickable(WebElement element)
    {
        Wait<WebDriver> wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(d -> element.isDisplayed() && element.isEnabled());
    }

    private List<WebElement> waitForPhotoResults(int resultSize)
    {
        Wait<WebDriver> wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(d -> driver.findElements(photoListBy).size() == resultSize);
        return driver.findElements(photoListBy);
    }

    public void clickOnExpandButton()
    {
        WebElement expandSearchButton = driver.findElement(expandSearchBy);
        waitForElementToBeClickable(expandSearchButton);
        expandSearchButton.click();
    }

    public List<WebElement> filterByCountryIsrael()
    {
        clickOnExpandButton();
        WebElement filterByCountry = driver.findElement(filterCountryBy);
        waitForElementToBeClickable(filterByCountry);
        filterByCountry.click();

        WebElement israelOption = driver.findElement(israelOptionBy);
        waitForElementToBeClickable(israelOption);
        israelOption.click();

        return waitForPhotoResults(3);
    }

    public List<WebElement> filterByYear2018()
    {
        clickOnExpandButton();
        WebElement filterByYear = driver.findElement(filterYearBy);
        waitForElementToBeClickable(filterByYear);
        filterByYear.click();

        WebElement year2018Option = driver.findElement(option2018By);
        waitForElementToBeClickable(year2018Option);
        year2018Option.click();

        return waitForPhotoResults(2);
    }

    public List<WebElement> filterByCategoryAnimal()
    {
        clickOnExpandButton();
        WebElement filterByCategory = driver.findElement(filterCategoryBy);
        waitForElementToBeClickable(filterByCategory);
        filterByCategory.click();

        WebElement animalOption = driver.findElement(animalOptionBy);
        waitForElementToBeClickable(animalOption);
        animalOption.click();

        return waitForPhotoResults(3);
    }

    public List<WebElement> filterByColorRed()
    {
        clickOnExpandButton();
        WebElement filterByColor = driver.findElement(filterColorBy);
        waitForElementToBeClickable(filterByColor);
        filterByColor.click();

        WebElement redOption = driver.findElement(redOptionBy);
        waitForElementToBeClickable(redOption);
        redOption.click();

        return waitForPhotoResults(1);
    }

    public List<WebElement> filterByMonthOctober()
    {
        clickOnExpandButton();
        WebElement filterByMonth = driver.findElement(filterMonthBy);
        waitForElementToBeClickable(filterByMonth);
        filterByMonth.click();

        WebElement octoberOption = driver.findElement(octoberOptionBy);
        waitForElementToBeClickable(octoberOption);
        octoberOption.click();

        return waitForPhotoResults(2);
    }


}
