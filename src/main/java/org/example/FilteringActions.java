package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilteringActions {

    private WebDriver driver;
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    // Locators
    private By expandSearchBy = By.xpath("//button[@title='Expand Search']");

    private By filterCountryBy = By.xpath("//div[contains(@class, 'v-select') and contains(@class, 'input-countries')]");
    private By franceOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='France']");


    private By photoListBy = By.cssSelector("div.media.result.is-photo");

    By filterYearBy = By.xpath("//div[contains(@class, 'p-year-select')]");
    By option2013By = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='2013']");
    By option2015By = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='2015']");


    private By filterCategoryBy = By.className("p-category-select");
    private By animalOptionBy =By.xpath("//div[contains(@class, 'v-list-item-title') and text()='Animal']");
    private By natureOptionBy = By.xpath("//div[contains(@class, 'v-list__tile__title') and text()='Nature']");

    private By filterColorBy = By.className("p-color-select");
    private By redOptionBy =By.xpath("//div[contains(@class, 'v-list-item-title') and text()='Red']");
    private By greenOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='Green']");

    private By filterMonthBy=By.className("p-month-select");
    private By januaryOptionBy=By.xpath("//div[contains(@class, 'v-list-item-title') and text()='January']");
    private By februaryOptionBy=By.xpath("//div[contains(@class, 'v-list-item-title') and text()='February']");
    private By marchOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='March']");
    private By aprilOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title) and text()='April']");
    private By mayOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='May']");
    private By juneOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='June']");
    private By julyOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='July']");
    private By augustOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='August']");
    private By septemberOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='September']");
    private By octoberOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='October']");
    private By novemberOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='November']");
    private By decemberOptionBy = By.xpath("//div[contains(@class, 'v-list-item-title') and text()='December']");



    public FilteringActions(WebDriver driver)
    {
        this.driver = driver;
    }

    private void waitForElementToBeClickable(WebElement element)
    {
        Wait<WebDriver> wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(d -> element.isDisplayed() && element.isEnabled());
    }

    private List<WebElement> waitForPhotoResults()
    {
        // Wait until photo results are visible
        new WebDriverWait(driver, DEFAULT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(photoListBy));

        return driver.findElements(photoListBy);
    }

    public void clickOnExpandButton()
    {
        WebElement expandSearchButton = driver.findElement(expandSearchBy);
        waitForElementToBeClickable(expandSearchButton);
        expandSearchButton.click();
    }

    public List<WebElement> filterByCountry(String country)
    {
        clickOnExpandButton();

        WebElement filterByCountry = driver.findElement(filterCountryBy);
        waitForElementToBeClickable(filterByCountry);
        filterByCountry.click();

        // Initialize the country options map
        Map<String, By> countryOptions = new HashMap<>();
        countryOptions.put("France", franceOptionBy);

        // Get the locator for the selected country
        By countryLocator = countryOptions.get(country);

        // Click the corresponding country option
        WebElement option = driver.findElement(countryLocator);
        waitForElementToBeClickable(option);
        option.click();

        return waitForPhotoResults();
    }

    public List<WebElement> filterByYear(int year)
    {
        clickOnExpandButton();
        WebElement filterByYear = driver.findElement(filterYearBy);
        waitForElementToBeClickable(filterByYear);
        filterByYear.click();

        // Initialize the year options map
        Map<Integer, By> yearOptions = new HashMap<>();
        yearOptions.put(2013, option2013By);
        yearOptions.put(2015, option2015By);



        // Get the locator for the selected year
        By yearLocator = yearOptions.get(year);

        // Click the corresponding year option
        WebElement option = driver.findElement(yearLocator);
        waitForElementToBeClickable(option);
        option.click();

        return waitForPhotoResults();
    }

    public List<WebElement> filterByCategory(String category)
    {
        clickOnExpandButton();
        WebElement filterByCategory = driver.findElement(filterCategoryBy);
        waitForElementToBeClickable(filterByCategory);
        filterByCategory.click();

        // Initialize the category options map
        Map<String, By> categoryOptions = new HashMap<>();
        categoryOptions.put("animal", animalOptionBy);
        categoryOptions.put("nature", natureOptionBy);

        // Get the locator for the selected category
        By categoryLocator = categoryOptions.get(category);

        WebElement option = driver.findElement(categoryLocator);
        waitForElementToBeClickable(option);
        option.click();

        return waitForPhotoResults();
    }

    public List<WebElement> filterByColor(String color)
    {
        clickOnExpandButton();
        WebElement filterByColor = driver.findElement(filterColorBy);
        waitForElementToBeClickable(filterByColor);
        filterByColor.click();

        // Initialize the color options map
        Map<String, By> colorOptions = new HashMap<>();
        colorOptions.put("red", redOptionBy);
        colorOptions.put("green", greenOptionBy);

        // Get the locator for the selected color
        By colorLocator = colorOptions.get(color);

        WebElement option = driver.findElement(colorLocator);
        waitForElementToBeClickable(option);
        option.click();

        return waitForPhotoResults();
    }


    public List<WebElement> filterByMonth(int month) {
        clickOnExpandButton();

        // Locate the filter by month dropdown
        WebElement filterByMonth = driver.findElement(filterMonthBy);
        waitForElementToBeClickable(filterByMonth);
        filterByMonth.click();

        // Initialize the month options map
        Map<Integer, By> monthOptions = new HashMap<>();
        monthOptions.put(1, januaryOptionBy);
        monthOptions.put(2, februaryOptionBy);
        monthOptions.put(3, marchOptionBy);
        monthOptions.put(4, aprilOptionBy);
        monthOptions.put(5, mayOptionBy);
        monthOptions.put(6, juneOptionBy);
        monthOptions.put(7, julyOptionBy);
        monthOptions.put(8, augustOptionBy);
        monthOptions.put(9, septemberOptionBy);
        monthOptions.put(10, octoberOptionBy);
        monthOptions.put(11, novemberOptionBy);
        monthOptions.put(12, decemberOptionBy);

        // Get the locator for the selected month
        By monthLocator = monthOptions.get(month);

        if (monthLocator == null) {
            throw new IllegalArgumentException("Invalid month: " + month + ". Please provide a value between 1 and 12.");
        }

        // Click the corresponding month option
        WebElement option = driver.findElement(monthLocator);
        waitForElementToBeClickable(option);
        option.click();

        // Wait for and return photo results
        return waitForPhotoResults();
    }



}