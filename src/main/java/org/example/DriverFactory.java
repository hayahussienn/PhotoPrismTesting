package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebDriverBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.logging.Logger;

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class.getName());
    private static final String grid_url = System.getenv("GRID_URL");
    private static final String browser = Optional
            .ofNullable(System.getenv("BROWSER"))
            .orElse("chrome");
    private static final int MAX_RETRIES = 3;
    private static final Duration RETRY_DELAY = Duration.ofSeconds(5);

    public static WebDriver getDriver() {
        LOGGER.info("Initializing WebDriver with grid URL: " + grid_url + " and browser: " + browser);

        WebDriver driver = null;
        Exception lastException = null;

        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                driver = (grid_url != null && !grid_url.isEmpty())
                        ? getRemoteDriver(browser)
                        : getLocalDriver(browser);

                if (driver != null) {
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    driver.manage().window().maximize();
                    LOGGER.info("WebDriver successfully initialized on attempt " + attempt);
                    return driver;
                }
            } catch (Exception e) {
                lastException = e;
                LOGGER.warning("Attempt " + attempt + " failed: " + e.getMessage());
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY.toMillis());
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        throw new RuntimeException("Failed to initialize WebDriver after " + MAX_RETRIES + " attempts", lastException);
    }

    private static WebDriver getRemoteDriver(String browser) {
        URL hubUrl;
        try {
            LOGGER.info("Connecting to grid at: " + grid_url);
            hubUrl = new URI(grid_url).toURL();
        } catch (URISyntaxException | MalformedURLException err) {
            throw new IllegalArgumentException("Invalid grid URL: " + grid_url, err);
        }

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            LOGGER.info("Creating Remote Chrome Driver with options: " + options);
            return new RemoteWebDriver(hubUrl, options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            LOGGER.info("Creating Remote Firefox Driver with options: " + options);
            return new RemoteWebDriver(hubUrl, options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver getLocalDriver(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            LOGGER.info("Creating Local Chrome Driver");
            return new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            LOGGER.info("Creating Local Firefox Driver");
            return new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}