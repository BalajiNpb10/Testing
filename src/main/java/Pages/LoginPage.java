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

public class LoginPage {

    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    //  PageFactory

    @FindBy(xpath = "//*[@id='des_lIcon']")
    private WebElement profileBtn;

    @FindBy(xpath = "//*[@id='myModal3-modal-content']/div[1]/div/div[3]/div[6]/div")
    private WebElement googleBtn;



    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Initializes the elements annotated with @FindBy
        PageFactory.initElements(driver, this);
    }



    public void clickGoogleBtn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Click Profile Icon
            wait.until(ExpectedConditions.elementToBeClickable(profileBtn)).click();
            logger.info("Profile button clicked.");

            // Click Google Login Button
            wait.until(ExpectedConditions.elementToBeClickable(googleBtn)).click();
            logger.info("Google button clicked.");

            // Handle Window Switching
            Set<String> windowHandles = driver.getWindowHandles();
            List<String> windows = new ArrayList<>(windowHandles);

            if (windows.size() > 1) {
                driver.switchTo().window(windows.get(1));
                logger.info("Switched to Google Login window: " + driver.getTitle());
            } else {
                logger.warn("New window not found!");
            }

        } catch (Exception e) {
            logger.error("Failed to click Google button or switch window: " + e.getMessage());
            throw e;
        }
    }
}