package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage
{
    private WebDriver driver;
    private By contentPageBy = By.className("v-dialog__content");
    private By dropButtonBy = By.className("p-expand-search");



    public HomePage(WebDriver driver)
    {
        this.driver = driver;


    }

    public boolean isLoggedInSuccess()
    {
        WebElement contentPage = driver.findElement(contentPageBy);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(500));
        wait.until(d -> contentPage.isDisplayed());
        return contentPage.isDisplayed();

    }



}
