package Driver;

import com.epam.healenium.SelfHealingDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.EventsPage;
import pages.HomePage;
import pages.VideoPage;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class DriverConfiguration {
    public WebDriver driver;
    public RemoteWebDriver remDriver;
    SelfHealingDriver selfDriver;
    public HomePage homePage;
    public EventsPage eventsPage;
    public VideoPage videoPage;
    public static WebDriverWait waitEl;
    public Logger logger = LogManager.getLogger(DriverConfiguration.class);


    @BeforeEach
    public void SetUp() throws MalformedURLException {
        if (System.getProperty("RemBrowser").equals("true")) {
            remDriver = new InitRemoteDriver().initRemDriver();
            selfDriver = SelfHealingDriver.create(remDriver);
            logger.info("Remote browser driver open");
            waitEl = new WebDriverWait(selfDriver, 20);
        } else {
            driver = new InitLocalDriver().initLocDriver();
            selfDriver = SelfHealingDriver.create(driver);
            logger.info("Local browser driver open");
            waitEl = new WebDriverWait(driver, 20);
        }
        selfDriver.manage().window().maximize();
        selfDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        homePage = PageFactory.initElements(selfDriver, HomePage.class);
        eventsPage = PageFactory.initElements(selfDriver, EventsPage.class);
        videoPage = PageFactory.initElements(selfDriver, VideoPage.class);
    }

    @AfterEach
    public void setDown() {
        if (selfDriver != null) {
            selfDriver.manage().deleteAllCookies();
            selfDriver.quit();
            logger.info("Browser driver closed");
        }
    }
}
