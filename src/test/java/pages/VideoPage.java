package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VideoPage {
    public WebDriver driver;
    public Logger logger = LogManager.getLogger(VideoPage.class);

    public VideoPage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@class='evnt-toogle-filters-text show-more']")
    public WebElement moreFilter;

    @FindBy(xpath = "//*[@id='filter_category']")
    public WebElement filterCategory;

    //@FindBy(xpath = "//*[@class='form-check-label group-items' and @data-value='Testing']")
    //@FindBy(xpath = "//*[@id='filter_category_15']")
    @FindBy(xpath = "//*[@data-value='Testing']")
//    @FindBy(className = "[data-value|='Testing']:before")
    public WebElement filterCategoryTesting;

    @FindBy(xpath = "//*[@id='filter_location']")
    public WebElement filterLocation;

    @FindBy(xpath = "//*[@data-value='Belarus']")
    public WebElement filterLocationBelarus;

    @FindBy(xpath = "//*[@id='filter_language']")
    public WebElement filterLanguage;

    @FindBy(xpath = "//*[@class='form-check-label' and @data-value='ENGLISH']")
    public WebElement filterLanguageEnglish;

    @FindBy(xpath = "//*[@class='evnt-card-wrapper']")
    public WebElement cardVideo;

    @FindBy(xpath = "//*[@class='evnt-topics-wrapper']//child::div/label")
    public List<WebElement> listCategory;

    @FindBy(xpath = "//*[@class='evnt-talk-details language evnt-now-past-talk']//child::span")
    public WebElement cardLanguage;

    @FindBy(xpath = "//*[@class='evnt-talk-details location evnt-now-past-talk']//child::span")
    public WebElement cardLocation;

    @FindBy(xpath = "//*[@placeholder='Search by Talk Name']")
    public WebElement inputSearch;

    @FindBy(xpath = "//*[@class='evnt-talk-name']//child::h1")
    public List<WebElement> eventName;

    public VideoPage clickMoreFilter() {
        logger.info("Opening an advanced filter");
        moreFilter.isDisplayed();
        moreFilter.click();
        return this;
    }

    public VideoPage setFilter() {
        logger.info("Setting filters");
        filterCategory.isDisplayed();
        filterCategory.click();
        Actions actions = new Actions(driver);
        actions.moveToElement(filterCategoryTesting).build().perform();
        filterCategoryTesting.isDisplayed();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", filterCategoryTesting);
        filterLocation.isDisplayed();
        filterLocation.click();
        filterLocationBelarus.isDisplayed();
        JavascriptExecutor executor1 = (JavascriptExecutor) driver;
        executor1.executeScript("arguments[0].click();", filterLocationBelarus);
        filterLanguage.isDisplayed();
        filterLanguage.click();
        filterLanguageEnglish.isDisplayed();
        filterLanguageEnglish.click();
        filterLanguage.click();
        return this;
    }

    public void checkFilter() throws Exception {
        logger.info("Checking filtered Video Cards");
        cardVideo.isDisplayed();
        cardVideo.click();
        boolean isContainTesting = false;
        for (WebElement webElement : listCategory) {
            if (webElement.getText().equals("Testing")) {
                isContainTesting = true;
            }
        }
        assertTrue(isContainTesting);
        assertEquals("ENGLISH", cardLanguage.getText());
        if (!cardLocation.getText().contains("Belarus")) {
            throw new ContainedTextException("The event location doesn't contain Belarus ");
        }
    }


    public VideoPage searchReports() {
        logger.info("Search for a report by keyword QA");
        inputSearch.isDisplayed();
        inputSearch.sendKeys("QA", Keys.ENTER);
        return this;
    }

    public void checkReports() throws ContainedTextException {
        logger.info("Checking found reports");
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (WebElement webElement : eventName) {
            if (!webElement.getText().contains("QA")) {
                throw new ContainedTextException("The event name doesn't contain QA ");
            }
        }
    }

    static class ContainedTextException extends Exception {
        public ContainedTextException(String message) {
            super(message);
        }
    }
}
