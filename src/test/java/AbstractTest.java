import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class AbstractTest {

    final String baseURL = "http://www.e-katalog.ru/";
    final String email = "nadena.xq95@mail.ru";
    final String password = "naduxa3009";

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Step("Вход")
    void login(){
        driver.get(baseURL);
        driver.findElement(By.className("wu_entr")).click();
        driver.findElement(By.name("l_")).sendKeys(email);
        driver.findElement(By.name("pw_")).sendKeys(password);
        driver.findElement(By.className("l-but")).click();
    }


    @Step("Выход")
    void logout(){
        try {
            click(By.id("mui_user_login_row"));
        } catch (TimeoutException e) {
            throw new AssertionError("Logout button has not displayed");
        }
    }
    @BeforeMethod
    public void setup() {
        // System.setProperty("webdriver.firefox.driver","/home/naduxa/geckodriver");

        System.setProperty("webdriver.chrome.driver","/home/naduxa/chromedriver");
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);
        driver.get(baseURL);
    }
    @AfterMethod
    public void  tearDown() {
        getDriver().quit();
    }
    WebDriver getDriver(){
        return driver;
    }

    void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    void click(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));

        WebElement element = driver.findElement(by);
        this.scrollIntoView(element);
        try {
            element.click();
        } catch (StaleElementReferenceException exception) {
            System.err.println("Failed to click element, " + by.toString() + " tries total.");
            exception.printStackTrace();
        }
    }
}
