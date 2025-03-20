package globalSetup;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverUtil {

	public WebDriver driver;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
	
	String projectPath = System.getProperty("user.dir");
	public String downloadPath = projectPath + File.separator + "Downloads";

	public WebDriver init_driver(String browser) {
            switch (browser) {
                case "chrome" -> {
                    ChromeOptions options = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("download.default_directory",downloadPath);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("download.directory_upgrade", true);
                    prefs.put("safebrowsing.enabled", true);
                    options.setExperimentalOption("prefs", prefs);
                    //options.addArguments("--headless=new");
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(options);
                    tlDriver.set(driver);
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    tlDriver.set(new FirefoxDriver());
                }
                case "safari" -> tlDriver.set(new SafariDriver());
                default -> System.out.println("Please pass the correct browser value: " + browser);
            }
		getDriver().manage().window().maximize();
		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
}