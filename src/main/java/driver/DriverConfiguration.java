package driver;

import com.epam.healenium.SelfHealingDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.EventsPage;
import pages.HomePage;
import pages.VideoPage;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class DriverConfiguration {
    public WebDriver driver;
    private String browser;
    RemoteWebDriver driverRem;
    SelfHealingDriver selfDriver;
    public HomePage homePage;
    public EventsPage eventsPage;
    public VideoPage videoPage;
    public static WebDriverWait waitEl;
    public Logger logger = LogManager.getLogger(DriverConfiguration.class);

    @Before
    public void SetUp() throws MalformedURLException {
        if (System.getProperty("RemBrowser") == null) {

            if (System.getProperty("Browser") == null) {
                browser = "";
            } else {
                browser = System.getProperty("Browser").toLowerCase();
            }
            switch (browser) {
                case ("\'opera\'"):
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;

                case ("\'firefox\'"):
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;

                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
            }
        } else if (System.getProperty("RemBrowser") == "true") {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            capabilities.setVersion("88.0");
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            driverRem = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(), capabilities
            );
        }

        if (driverRem == null) {
            selfDriver = SelfHealingDriver.create(driver);
        } else selfDriver = SelfHealingDriver.create(driverRem);

        logger.info("Browser driver open");
        waitEl = new WebDriverWait(driver, 20);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        homePage = PageFactory.initElements(driver, HomePage.class);
        eventsPage = PageFactory.initElements(driver, EventsPage.class);
        videoPage = PageFactory.initElements(driver, VideoPage.class);
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
