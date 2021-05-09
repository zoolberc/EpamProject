package Driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;

public class InitLocalDriver {
    public WebDriver driver;
    private String browser;

    public WebDriver initLocDriver() {
        if (System.getProperty("Browser") == null) {
            browser = "";
        } else {
            browser = System.getProperty("Browser").toLowerCase();
        }
        switch (browser) {
            case ("opera"):
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;

            case ("firefox"):
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        return driver;
    }

}

