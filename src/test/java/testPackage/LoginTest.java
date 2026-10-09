package testPackage;

import com.shaft.driver.SHAFT;
import com.shaft.driver.DriverFactory.DriverType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testPackage.data.LoginTestData;
import pages.LoginPage;

import java.nio.file.Files;
import java.nio.file.Path;

public class LoginTest {
    private SHAFT.GUI.WebDriver driver;
    private LoginTestData testData;

    @Test(description = "Sprint 1: log in with valid credentials")
    public void validCredentialsOpenDashboard() {
        new LoginPage(driver)
                .openDashboard()
                .login(testData.username(), testData.password())
                .assertDashboardLoaded();
    }

    @BeforeMethod
    public void beforeMethod() {
        boolean hasLocalCredentials = Files.exists(
                Path.of("src/test/resources/testDataFiles/login.local.json"));
        SHAFT.TestData.JSON data = new SHAFT.TestData.JSON(
                hasLocalCredentials ? "login.local.json" : "login.json");
        String username = data.get("username");
        String password = data.get("password");
        testData = hasLocalCredentials
                ? new LoginTestData(username, password)
                : new LoginTestData(requiredEnvironmentVariable(username),
                        requiredEnvironmentVariable(password));
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        driver = new SHAFT.GUI.WebDriver(DriverType.CHROME, options);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        if (driver != null) {
            driver.quit();
        }
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Set the " + name + " environment variable before running this test.");
        }
        return value;
    }
}
