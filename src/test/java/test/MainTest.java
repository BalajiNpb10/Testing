package test;

import Base.Base;
import Pages.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utils.ExtentManager;

public class MainTest extends Base {

    // Page objects
    HomePage Hp;
    LoginPage Lp;
    GooglePage gp;
    UpcomingPage up;
    EvPage Ev;

    // Extent Report objects
    ExtentReports extent;
    ExtentTest test;

    // ✅ Create report only ONCE
    @BeforeClass
    public void setupReport() {
        extent = ExtentManager.getReport();
    }

    @BeforeMethod
    public void Launch() {

        browserLaunch();

        Hp = new HomePage(driver);
        Lp = new LoginPage(driver);
        Ev = new EvPage(driver);
        gp = new GooglePage(driver);
        up = new UpcomingPage(driver);
    }

    @Test(testName = "TC01", groups = {"Sanity"},
            description = "Identify Upcoming Honda Bikes (< 4 Lakhs)")
    public void T01() {

        test = extent.createTest("TC01 - Upcoming Bikes");

        Hp.UpcomingBikes();
        up.selectFilter();
        up.applyPriceFilter();
        up.printUpcomingBikeDetails();

        test.pass("Upcoming Bikes Test Executed Successfully ✅");
    }

    @Test(testName = "TC02", groups = {"Sanity"},
            description = "Used Cars in Chennai – Popular Models")
    public void UsedCars() {

        test = extent.createTest("TC02 - Used Cars");

        Hp.UsedCars();

        test.pass("Used Cars Test Executed Successfully ✅");
    }

    @Test(testName = "TC03", groups = {"Sanity"},
            description = "Google Login – Invalid Credentials & Error Message")
    public void GooglePageTesting() {

        test = extent.createTest("TC03 - Google Login");

        Lp.clickGoogleBtn();
        gp.GoogleLogin();

        test.pass("Google Login Test Executed Successfully ✅");
    }

    @Test(testName = "TC04", groups = {"Sanity"},
            description = "Verify EV page loads successfully")
    public void EvCheck() {

        test = extent.createTest("TC04 - EV Page Load");

        Ev.EvOperations();

        test.pass("T04 is passed");
    }

    @Test(testName = "TC05", groups = {"Sanity"},
            description = "Verify top navigation menu")
    public void TC05() {

        test = extent.createTest("TC05 - Navigation Menu");

        Ev.VerifyTopNavigationMenu();

        test.pass("T05 is passed");
    }

    @Test(testName = "TC06", groups = {"Sanity"},
            description = "Verify search bar with valid EV name")
    public void TC06() {

        test = extent.createTest("TC06 - Valid Search");

        Ev.searchValidEVName();

        test.pass("T06 is passed");
    }

    @Test(testName = "TC07", groups = {"Sanity"},
            description = "Verify search bar with invalid input")
    public void TC07() {

        test = extent.createTest("TC07 - Invalid Search");

        Ev.searchInValidEVName();

        test.pass("T07 is passed");
    }

    @Test(testName = "TC08", groups = {"Sanity"},
            description = "Verify additional scenario")
    public void TC08() {

        test = extent.createTest("TC08 - Extra Test");

        Ev.searchInValidEVName();
    }

    @AfterMethod
    public void close() {
        closeBrowser();
    }

    // ✅ Flush ONLY ONCE at end
    @AfterClass
    public void flushReport() {
        extent.flush();
    }
}
