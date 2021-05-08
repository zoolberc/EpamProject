package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.junit.Assert.assertEquals;

public class HomePage {
    public WebDriver driver;
    public Logger logger = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"app\"]/header/div/div/ul[1]/li[2]/a")
    private WebElement eventsTab;

    @FindBy(xpath = "//*[@class='evnt-cards-container']//child::h3")
    private WebElement checkPage;

    @FindBy(xpath = "//*[@class='evnt-text']//child::h1")
    private WebElement checkPageVideo;

    @FindBy(xpath = "//*[@class='nav-item talks-library-icon']//child::a")
    private WebElement videoTab;

    @FindBy(xpath = "//*[@class='evnt-description']")
    private WebElement evntDescription;

    @FindBy(xpath = "//*[@id='onetrust-reject-all-handler']")
    private WebElement buttonDisableCookies;


    public HomePage openHomePage() {
        driver.get("https://events.epam.com/");
        buttonDisableCookies.click();
        evntDescription.isDisplayed();
        logger.debug("Checking the opening of the events.epam.com page");
        assertEquals(evntDescription.getText(), "ALL EVENTS LIVE HERE");
        logger.debug("events.epam.com page opened successfully");
        return this;
    }

    public HomePage goToEventsTab() {
        logger.info("Go to the event page");
        eventsTab.click();
        checkPage.isDisplayed();
        logger.debug("Checking the opening of the event page");
        assertEquals(checkPage.getText(), "ALL EVENTS");
        logger.info("Event page opened successfully");
        return this;
    }

    public HomePage goToVideoTab() {
        logger.info("Go to the video page");
        videoTab.click();
        checkPageVideo.isDisplayed();
        logger.debug("Checking the opening of the video page");
        assertEquals(checkPageVideo.getText(), "TALKS LIBRARY");
        logger.debug("Video page opened successfully");
        return this;
    }
}
