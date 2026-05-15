package Pages;

import Base.Base;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {

    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    // --- PageFactory Locators ---

    @FindBy(xpath = "//*[@id='headerNewVNavWrap']/nav/ul/li[3]/span")
    private WebElement newBikeMenu;

    @FindBy(xpath = "//*[@id='headerNewVNavWrap']/nav/ul/li[3]/ul/li[4]/a")
    private WebElement upcomingBikesOption;

    @FindBy(xpath = "//span[text()='Chennai']")
    private WebElement usedCarCityChennai;

    // It's better to target the list items (li) rather than the whole ul for better data extraction
    @FindBy(xpath = "/html/body/div[7]/div/div[1]/div[1]/div[1]/div[2]/ul/li[2]/div[2]/div[5]/ul/li")
    private List<WebElement> popularModelList;

    // --- Constructor ---

    public HomePage(WebDriver driver) {
        this.driver = (driver != null) ? driver : Base.driver;
        // This line is crucial for PageFactory to work
        PageFactory.initElements(this.driver, this);
    }

    // --- Methods ---

    public void UpcomingBikes() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            logger.info("Clicking the New Bike dropdown...");
            wait.until(ExpectedConditions.elementToBeClickable(newBikeMenu)).click();

            logger.info("Clicking Upcoming Bikes...");
            wait.until(ExpectedConditions.elementToBeClickable(upcomingBikesOption)).click();

            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("upcoming-bikes"), "Navigation Failed!");
            logger.info("Successfully reached Upcoming Bikes page.");

        } catch (Exception e) {
            logger.error("Navigation to Upcoming Bikes failed: " + e.getMessage());
            throw e;
        }
    }

    public void UsedCars() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Click Chennai
        wait.until(ExpectedConditions.elementToBeClickable(usedCarCityChennai));
        js.executeScript("arguments[0].scrollIntoView(true);", usedCarCityChennai);
        js.executeScript("arguments[0].click();", usedCarCityChennai);
        logger.info("Chennai city selected.");

        // Scroll to see popular models
        js.executeScript("window.scrollBy(0,700)");
        logger.info("Scrolling down to extract popular models...");

        List<String> modelNames = new ArrayList<>();

        System.out.println("--- Popular Used Car Models ---");
        for (WebElement model : popularModelList) {
            String name = model.getText().trim();
            if (!name.isEmpty()) {
                modelNames.add(name);
                System.out.println(name);
            }
        }
    }
}