package Base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class Base {
    private static final Logger logger = LogManager.getLogger(Base.class);

    // This is the "Master" driver
    public static WebDriver driver;

    public void browserLaunch() {
        // Initialize the Master driver
        driver = new ChromeDriver();
        driver.get("https://www.zigwheels.com");
        driver.manage().window().maximize();
        logger.info("Browser Launched Successfully");
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser Closed Successfully");
        }
    }
}