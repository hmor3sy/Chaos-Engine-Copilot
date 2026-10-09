package pages;

import com.shaft.driver.SHAFT;
import org.openqa.selenium.By;

public class LoginPage {
    private static final String DASHBOARD_URL = "https://rasel-revamp-sit.stc.com.sa/#/dashboard";
    private static final By USERNAME =
            By.cssSelector("input[type='email'], input[autocomplete='username'], input[name='username']");
    private static final By PASSWORD = By.cssSelector("input[type='password']");
    private static final By SUBMIT = By.cssSelector("button[type='submit'], input[type='submit']");

    private final SHAFT.GUI.WebDriver driver;

    public LoginPage(SHAFT.GUI.WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage openDashboard() {
        driver.browser().navigateToURL(DASHBOARD_URL);
        return this;
    }

    public LoginPage login(String username, String password) {
        driver.element().type(USERNAME, username);
        driver.element().type(PASSWORD, password);
        driver.element().click(SUBMIT);
        return this;
    }

    public void assertDashboardLoaded() {
        driver.assertThat().element(By.tagName("body")).text().contains("Hassan Ali Morsy");
    }
}
