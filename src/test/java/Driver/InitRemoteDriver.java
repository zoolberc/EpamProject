package Driver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;

public class InitRemoteDriver {
   public RemoteWebDriver driverRem;

   public RemoteWebDriver initRemDriver() throws MalformedURLException {
           DesiredCapabilities capabilities = new DesiredCapabilities();
           capabilities.setBrowserName("chrome");
           capabilities.setVersion("88.0");
           capabilities.setCapability("enableVNC", true);
           capabilities.setCapability("enableVideo", false);
           driverRem = new RemoteWebDriver(
                   URI.create("http://localhost:4444/wd/hub").toURL(), capabilities
           );
       return driverRem;
   }

}
