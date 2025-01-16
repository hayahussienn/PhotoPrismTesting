package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class PhotoCollectionUtils
{


    public PhotoCollectionUtils(WebDriver driver)
    {

    }

    public static String getID(WebElement photo)
    {

        return photo.getAttribute("data-uid");



    }

    public static List<String> sortIDs(List<WebElement> photoElements)
    {

        return photoElements.stream()
                .map(PhotoCollectionUtils::getID)
                .sorted() // Sort IDs to ensure consistent order
                .collect(Collectors.toList());
    }
}
