package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UpcomingPage {

    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(UpcomingPage.class);

    // PageFactory


    @FindBy(xpath = "//*[@id='modelList']/li[100]/div/div/div/div/div/ul/li[5]/a")
    private WebElement filterDropdown;

    @FindBy(xpath = "//label[contains(text(),'4 Lakh')] | //span[contains(text(),'4 Lakh')] | //li[contains(.,'4 Lakh')]")
    private WebElement priceFilterUnder4Lakh;

    @FindBy(xpath = "//li[contains(@class,'modelItem')]")
    private List<WebElement> bikeCards;



    public UpcomingPage(WebDriver driver) {
        this.driver = driver;
        // This is required to initialize the @FindBy elements
        PageFactory.initElements(driver, this);
    }



    public void selectFilter() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            logger.info("Locating and scrolling to the filter...");
            wait.until(ExpectedConditions.visibilityOf(filterDropdown));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", filterDropdown);
            js.executeScript("arguments[0].click();", filterDropdown);

            logger.info("Filter clicked successfully via JavaScript.");
        } catch (Exception e) {
            logger.error("Failed to click filter: " + e.getMessage());
            throw e;
        }
    }

    public void applyPriceFilter() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            logger.info("Applying price filter: Under 4 Lakhs...");
            wait.until(ExpectedConditions.visibilityOf(priceFilterUnder4Lakh));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", priceFilterUnder4Lakh);
            js.executeScript("arguments[0].click();", priceFilterUnder4Lakh);

            logger.info("Price filter applied successfully.");
        } catch (Exception e) {
            logger.error("Step 07 Failed: Price filter not found or clickable.");
            throw e;
        }
    }

    public void printUpcomingBikeDetails() {
        try {
            logger.info("Extracting and printing bike details...");

            System.out.println("\n==================================================");
            System.out.println("          UPCOMING BIKES UNDER 4 LAKHS            ");
            System.out.println("==================================================");

            for (WebElement card : bikeCards) {
                try {
                    // Finding sub-elements using By inside the loop is standard
                    // even within PageFactory for dynamic lists
                    String name = card.findElement(org.openqa.selenium.By.xpath(".//span[contains(@data-track-label,'model-name')] | .//strong")).getText();
                    String price = card.findElement(org.openqa.selenium.By.xpath(".//div[contains(@class,'clr-try')] | .//div[contains(text(),'Rs.')]")).getText();
                    String launchDate = card.findElement(org.openqa.selenium.By.xpath(".//div[contains(@class,'clr-try')]/following-sibling::div | .//div[contains(text(),'Launch Date')]")).getText();

                    System.out.println("Bike Name   : " + name.trim());
                    System.out.println("Price       : " + price.trim());
                    System.out.println("Launch Date : " + launchDate.replace("Launch Date :", "").trim());
                    System.out.println("--------------------------------------------------");

                } catch (Exception e) {
                    // Skip if a specific card has missing data
                    continue;
                }
            }

            System.out.println("Total Bikes Found: " + bikeCards.size());
            System.out.println("==================================================\n");

        } catch (Exception e) {
            logger.error("Error while printing bike details: " + e.getMessage());
        }
    }
}