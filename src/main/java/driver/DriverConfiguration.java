package driver;

import com.epam.healenium.SelfHealingDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.EventsPage;
import pages.HomePage;
import pages.VideoPage;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class DriverConfiguration {
    public WebDriver driver;
    private String browser;
    SelfHealingDriver selfDriver;
    public HomePage homePage;
    public EventsPage eventsPage;
    public VideoPage videoPage;
    public Logger logger = LogManager.getLogger(DriverConfiguration.class);

    @Before
    public void SetUp() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setVersion("79.0");
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", false);
        RemoteWebDriver driver = new RemoteWebDriver(
                URI.create("http://selenoid:4444/wd/hub").toURL(), capabilities
        );
        selfDriver = SelfHealingDriver.create(driver);
        logger.info("Browser driver open");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        homePage = PageFactory.initElements(selfDriver, HomePage.class);
        eventsPage = PageFactory.initElements(selfDriver, EventsPage.class);
        videoPage = PageFactory.initElements(selfDriver, VideoPage.class);
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.quit();
            logger.info("Browser driver closed");
        }
    }
}
