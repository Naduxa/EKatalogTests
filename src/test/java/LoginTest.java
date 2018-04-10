import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class LoginTest extends AbstractTest {


    @Test
    void loginTest(){
        login();
        assertEquals(driver.findElements(By.className("l-err")).size(), 0);
        logout();
    }

}
