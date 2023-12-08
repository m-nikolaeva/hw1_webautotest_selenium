import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class TestStandGeekBrains {
    static WebDriver chromeDriver;
    private static final String USERNAME = "Student-9";
    private static final String PASSWORD = "425c57255c";
    private static ChromeOptions chromeOptions;
    private static WebDriverWait wait;

    @BeforeAll
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
    }

    @BeforeEach
    void setupTest() {
        chromeDriver = new ChromeDriver(chromeOptions);
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(20));
        chromeDriver.manage().window().maximize();
    }

    void login() {
        String urlBase = "https://test-stand.gb.ru/login";
        chromeDriver.get(urlBase);
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("form#login input[type='text']")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("form#login input[type='password']")));
        usernameField.sendKeys(USERNAME);
        passwordField.sendKeys(PASSWORD);

        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("form#login button")));
        loginButton.click();
        wait.until(ExpectedConditions.invisibilityOf(loginButton));

        WebElement usernameLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.partialLinkText(USERNAME)));
        String actualUsername = usernameLink.getText().replace("\n", "").trim();
        Assertions.assertEquals(String.format("Hello, %s", USERNAME), actualUsername);
    }

    @Test
    void addingGroup() {
        login();
        WebElement createBtn = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("button#create-btn")));
        createBtn.click();

        WebElement groupNameField = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("div.mdc-dialog__surface input")));
        String groupName = "NEWGROUP" + System.currentTimeMillis();
        groupNameField.sendKeys(groupName);

        WebElement saveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector("div.submit button")));
        saveBtn.click();

        WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.cssSelector(".form-modal-header button")));
        closeBtn.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated
                (By.cssSelector("div.mdc-data-table"), groupName));

        File screenshot = ((TakesScreenshot)chromeDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("src/test/resources/screen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void closeTest() {
        chromeDriver.quit();
    }
}
