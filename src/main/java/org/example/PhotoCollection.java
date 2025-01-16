package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PhotoCollection
{
    private WebDriver driver;


    public PhotoCollection(WebDriver driver)
    {
        this.driver = driver;

    }

    public String getID(WebElement photo)
    {

        return photo.getAttribute("data-uid");



    }

    public List<String> sortIDs(List<WebElement> photoElements)
    {
        return photoElements.stream()
                .map(photo -> new PhotoCollection(driver).getID(photo))
                .sorted() // Sort IDs to ensure consistent order
                .toList();

    }
}
