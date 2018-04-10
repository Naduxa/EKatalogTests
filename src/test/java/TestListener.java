import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        final Object testClass = tr.getInstance();
        final WebDriver driver = ((AbstractTest) testClass).getDriver();

        if (driver != null) {
            saveScreenshotPNG(driver);
        }
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        final Object testClass = tr.getInstance();
        final WebDriver driver = ((AbstractTest) testClass).getDriver();

        if (driver != null) {
            saveScreenshotPNG(driver);
        }
    }
    @Attachment(value = "Page screenshot", type="image/png")
    public byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public String saveTextLog(String message) {
        return message;
    }
}
