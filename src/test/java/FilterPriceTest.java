import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertTrue;

@Listeners(TestListener.class)
public class FilterPriceTest extends AbstractTest{

    @Step("Переход в категорию и поиск с ограничением цены до {0} рублей")
    private void locateCategoryAndApplyFilters(int maxPrice) {
        final By computers = By.linkText("Компьютеры");
        click(computers);
        final By tablets = By.linkText("Планшеты");
        click(tablets);

        final WebElement priceUpperLimitField = driver.findElement(By.id("maxPrice_"));
        wait.until(ExpectedConditions.visibilityOf(priceUpperLimitField));

        priceUpperLimitField.sendKeys(String.valueOf(maxPrice));

        final By showModels = By.linkText("Показать");
        click(showModels);
    }

    @Step("Проверка цен товаров в выдаче (до {0} рублей)")
    private void checkPrices(int maxPrice) {
        By lowPrice = By.cssSelector(".model-price-range > a > span");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(lowPrice));
        } catch (TimeoutException |ElementNotFoundException e) {
            throw new AssertionError("Result list is empty");
        }

        List<WebElement> prices = driver.findElements(By.cssSelector(".model-price-range > a > span:first-child"));

        for (WebElement e : prices) {
            int price = Integer.valueOf(e.getText().replaceAll("\\D+", ""));
            assertTrue(price <= maxPrice, "One of the results has min price greater than search price: "
                    + price);
        }
    }

    @Test
    @Parameters("maxPrice")
    public void priceFilterTest(int maxPrice) {
        this.locateCategoryAndApplyFilters(maxPrice);
        this.checkPrices(maxPrice);
    }

}
