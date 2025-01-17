package org.example;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;



public class LoginPage
{
    private  WebDriver driver;

    private By userNameFieldBy = By.id("auth-username");
    private By passwordFieldBy = By.id("auth-password");
    private By signInButtonBy = By.cssSelector(".action-confirm ");
    private  By failedMessageBy = By.cssSelector(".error span");




    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));



    }

    public HomePage signInAsValidUser(String userName, String password)
    {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Add implicit wait


        driver.findElement(userNameFieldBy).sendKeys(userName);
        driver.findElement(passwordFieldBy).sendKeys(password);
        driver.findElement(signInButtonBy).click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Add implicit wait

        return new HomePage(driver);


    }

    public LoginPage signInAsInValidUser(String userName, String password)
    {
        driver.findElement(userNameFieldBy).sendKeys(userName);
        driver.findElement(passwordFieldBy).sendKeys(password);
        driver.findElement(signInButtonBy).click();
        return new LoginPage(driver);


    }


    public  boolean isLoggedInFailed()
    {

        WebElement failedMessage = driver.findElement(failedMessageBy);
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(500));
        wait.until(d -> failedMessage.isDisplayed());
        return failedMessage.isDisplayed();
    }

}
