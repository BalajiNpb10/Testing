package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GooglePage {

    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(GooglePage.class);

    // PageFactory

    @FindBy(xpath = "//input[@id='identifierId']")
    private WebElement emailInputField;

    @FindBy(xpath = "//*[@id='identifierNext']/div/button/span")
    private WebElement nextButton;

    @FindBy(xpath = "//*[@id='yDmH0d']/c-wiz/main/div[2]/div/div/div/span/section/div/div/div/div[2]")
    private WebElement errorOrNextPageText;


    public GooglePage(WebDriver driver) {
        this.driver = driver;
        // This initializes all the @FindBy elements above
        PageFactory.initElements(driver, this);
    }



    public void GoogleLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // 1. Enter Email
            wait.until(ExpectedConditions.visibilityOf(emailInputField)).sendKeys("balaji10@gmail.com");
            logger.info("Email entered in Google login.");

            // 2. Click Next
            wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
            logger.info("Next button clicked.");

            // 3. Capture result text (e.g., error message or password prompt)
            wait.until(ExpectedConditions.visibilityOf(errorOrNextPageText));
            System.out.println("Page Result Text: " + errorOrNextPageText.getText());

            // 4. Handle Window Closing and Switching
            driver.close(); // Closes the current Google window
            logger.info("Google window closed.");

            Set<String> address = driver.getWindowHandles();
            List<String> windows = new ArrayList<>(address);

            // Switch back to the main application window (index 0)
            if (!windows.isEmpty()) {
                driver.switchTo().window(windows.get(0));
                logger.info("Successfully switched back to the Main Page.");
            }

        } catch (Exception e) {
            logger.error("Error during Google Login handling: " + e.getMessage());
            throw e;
        }
    }
}