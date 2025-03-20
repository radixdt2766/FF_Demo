package stepdefinitions;

import java.util.concurrent.atomic.AtomicInteger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import globalSetup.DriverUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApplicationHooks {

    private static WebDriver driver;
    private static final AtomicInteger runningScenarios = new AtomicInteger(0);
    private DriverUtil driverutil;

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        if (driver == null) {
            driverutil = new DriverUtil();
            String browserName = "chrome";
            driver = driverutil.init_driver(browserName);
        }
        runningScenarios.incrementAndGet();
        
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String screenshotName = scenario.getName().replaceAll(" ", "_");
                byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(sourcePath, "image/png", screenshotName);
            }
        } catch (Exception e) {
            System.out.println("Error while taking screenshot: " + e.getMessage());
        }

        runningScenarios.decrementAndGet();
    }

    public static void closeBrowser() {
        if (driver != null) {
            System.out.println("Closing browser after feature execution.");
            driver.quit();
            driver = null;
        }
    }
}

