package Pages;

import Base.Base;
import io.cucumber.java.bs.A;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;


public class EvPage {

    WebDriver driver;
    private static final Logger logger = LogManager.getLogger(EvPage.class);

    public EvPage(WebDriver driver) {
        this.driver = (driver != null) ? driver : Base.driver;
        PageFactory.initElements(this.driver, this);
    }

    //  PageFactory
    @FindBy(xpath = "//*[@id='headerNewVNavWrap']/nav/ul/li[5]/span")
    WebElement moreBtn;

    @FindBy(xpath = "//*[@id='headerNewVNavWrap']/nav/ul/li[5]/ul/li[1]/a")
    WebElement electricVehicleBtn;

    @FindBy(id = "headerSearch")
    WebElement searchBtn;

    @FindBy(xpath = "//h1")
    WebElement vehicleName;


    public void EvOperations() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        String expUrl = "https://www.zigwheels.com/";
        Assert.assertEquals(driver.getCurrentUrl(), expUrl, "Not in correct URL");

        String title = driver.getTitle();
        System.out.println("CurrentPage Title " + title);

        moreBtn.click();
        logger.info("More button clicked");

        electricVehicleBtn.click();
        logger.info("Electric Vehicle button clicked");

        String EvPageUrl = "https://www.zigwheels.com/electric-vehicles";
        Assert.assertEquals(driver.getCurrentUrl(), EvPageUrl, "Not in correct URL");

        String EvPageTitle = driver.getTitle();
        System.out.println("CurrentPage Title " + EvPageTitle);
    }

    public void VerifyTopNavigationMenu() {

        List<WebElement> navBtns = driver.findElements(By.xpath("//*[@id='headerNewVNavWrap']/nav/ul"));

        for (WebElement btns : navBtns) {

            System.out.println(btns.getText());
            logger.info("Nav buttons listing");

            Assert.assertTrue(btns.isEnabled(), "Not Enabled Button");
            logger.info("Enabled: " + btns.isEnabled());

            Assert.assertTrue(btns.isDisplayed(), "Not Displayed Button");
            logger.info("Displayed: " + btns.isDisplayed());

            btns.click();
            logger.info("Button is clickable");
        }
    }

    public void searchValidEVName() {

        moreBtn.click();
        logger.info("More button clicked");

        electricVehicleBtn.click();
        logger.info("Electric Vehicle button clicked");

        searchBtn.sendKeys("Ather 450X", Keys.ENTER);
        logger.info("Search button clicked & gave value as Ather 450X");

        Assert.assertTrue(vehicleName.getText().contains("Ather 450X"), "Not in valid name");

        System.out.println(vehicleName.getText());

        // Screenshot
        File folder = new File("screenshots/EvPageOutputs/ValidSS/");
        File[] files = folder.listFiles();
        int count = (files != null) ? files.length + 1 : 1;

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/EvPageOutputs/ValidSS/Output" + count + ".png");

        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchInValidEVName() {

        moreBtn.click();
        logger.info("More button clicked");

        electricVehicleBtn.click();
        logger.info("Electric Vehicle button clicked");

        searchBtn.sendKeys("ScootyPep", Keys.ENTER);
        logger.info("Search button clicked & gave value as ScootyPep");

        System.out.println(vehicleName.getText());

        Assert.assertFalse(vehicleName.getText().contains("Ather 450X"), "No results found message is displayed.");

        // Screenshot
        File folder = new File("screenshots/EvPageOutputs/InValidsSS/");
        File[] files = folder.listFiles();
        int count = (files != null) ? files.length + 1 : 1;

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("screenshots/EvPageOutputs/InValidsSS/Output" + count + ".png");

        try {
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
