package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BlackWhiteActions {
    private WebDriver driver;
    private final By photoCardSelector = By.cssSelector("div.media.result.is-photo");
    private final By bwButtonSelector = By.cssSelector("button[aria-label='Convert to Black & White']");
    private final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);

    public BlackWhiteActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Opens the first available photo in the gallery
     * @return true if photo was successfully opened
     */
    public boolean openPhoto() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);

            // Ensure at least one photo is visible
            List<WebElement> photos = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(photoCardSelector));

            if (photos.isEmpty()) {
                System.out.println("No photos found!");
                return false;
            }

            // Try multiple photos in case one is unclickable
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    WebElement photo = photos.get(attempt % photos.size()); // Rotate through available photos
                    wait.until(ExpectedConditions.elementToBeClickable(photo)).click();
                    System.out.println("Photo clicked successfully.");

                    // Wait for the B&W button to confirm viewer opened
                    wait.until(ExpectedConditions.visibilityOfElementLocated(bwButtonSelector));
                    System.out.println("Photo viewer opened successfully.");
                    return true;
                } catch (StaleElementReferenceException e) {
                    System.out.println("Photo element went stale, retrying...");
                    photos = driver.findElements(photoCardSelector);
                } catch (TimeoutException e) {
                    System.out.println("Photo clicked but viewer didn't open, retrying...");
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Failed to open photo: " + e.getMessage());
            return false;
        }
    }




    /**
     * Clicks the Black & White conversion button
     * @return true if button was clicked successfully
     */
    public boolean clickBlackWhiteButton() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
            WebElement bwButton = wait.until(ExpectedConditions.elementToBeClickable(bwButtonSelector));

            // Retry clicking if the element goes stale
            for (int attempt = 0; attempt < 3; attempt++) {
                try {
                    bwButton.click();
                    System.out.println("B/W button clicked successfully.");
                    return true;
                } catch (StaleElementReferenceException e) {
                    System.out.println("B/W button went stale, retrying...");
                    bwButton = wait.until(ExpectedConditions.elementToBeClickable(bwButtonSelector));
                } catch (ElementClickInterceptedException e) {
                    System.out.println("B/W button was blocked, retrying...");
                    Thread.sleep(500); // Wait and retry
                    bwButton.click();
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Failed to click B/W button: " + e.getMessage());
            return false;
        }
    }



    /**
     * Handles any alert that might appear after clicking the B/W button
     * @return true if no alert appeared or if alert was handled successfully
     */
    public boolean handlePossibleAlert() {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            Alert alert = alertWait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Alert appeared with text: " + alertText);
            alert.accept();

            // Try clicking again if alert appeared
            WebElement bwButton = driver.findElement(bwButtonSelector);
            bwButton.click();
            return true;
        } catch (TimeoutException e) {
            // No alert, which is good for the success path
            System.out.println("No alert detected, conversion may be processing...");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to handle alert: " + e.getMessage());
            return false;
        }
    }

    /**
     * Waits for the conversion to complete and for a new window to open
     * @param waitTimeInSeconds how long to wait for conversion
     * @return the new window handle if one was opened, null otherwise
     */
    public String waitForConversionAndSwitchToNewWindow(int waitTimeInSeconds) {
        String originalWindow = driver.getWindowHandle();
        Set<String> oldWindows = driver.getWindowHandles();

        System.out.println("Waiting for conversion...");
        try {
            Thread.sleep(waitTimeInSeconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Wait for a new window to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(d -> driver.getWindowHandles().size() > oldWindows.size());
        } catch (TimeoutException e) {
            System.out.println("New window did not open in time.");
            return null;
        }

        // Get all windows and find the new one
        Set<String> newWindows = driver.getWindowHandles();
        newWindows.removeAll(oldWindows);

        if (newWindows.isEmpty()) {
            System.out.println("No new window detected.");
            return null;
        }

        // Switch to the new window
        String newWindowHandle = newWindows.iterator().next();
        driver.switchTo().window(newWindowHandle);
        System.out.println("Switched to new window: " + newWindowHandle);
        return newWindowHandle;
    }



    /**
     * Checks if the current window contains a Black & White image heading
     * @return true if heading is found
     */
    public boolean hasBlackWhiteHeading() {
        try {
            WebElement heading = driver.findElement(By.xpath("//h2[contains(text(),'Black & White Image')]"));
            boolean hasHeading = heading.isDisplayed();
            System.out.println("Found 'Black & White Image' heading: " + hasHeading);
            return hasHeading;
        } catch (Exception e) {
            System.out.println("Could not find heading: " + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the current window contains a converted image
     * @return true if a converted image is found
     */
    public boolean hasConvertedImage() {
        try {
            WebElement image = driver.findElement(By.tagName("img"));
            String imageSrc = image.getAttribute("src");
            System.out.println("Found image with src: " + imageSrc);

            // Check if this is a converted B&W image
            boolean hasConvertedImage = imageSrc.contains("converted_images/bw_");
            System.out.println("Image URL contains converted B&W indicators: " + hasConvertedImage);
            return hasConvertedImage;
        } catch (Exception e) {
            System.out.println("Could not find image: " + e.getMessage());
            return false;
        }
    }
}