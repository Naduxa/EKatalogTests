import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.testng.annotations.*;

@Listeners(TestListener.class)
public class ActionCameraTest extends AbstractTest {

    final static String actionCameraName = "Sony HDR-AZ1VW";

    @Step("Переход в гаджеты и применение фильтров")
    private void goToCategoryAndApplyFilters() {
        final By gadgets = By.linkText("Гаджеты");
        driver.findElement(gadgets).click();
        final By actionCameras = By.linkText("Action камеры");
        driver.findElement(actionCameras).click();

        By sonyFilterEl = By.xpath(".//*[@id='li_br156']");
       // By sonyFilterEl = By.linkText("Sony");
        click(sonyFilterEl);
        final By nfcFilterEl = By.cssSelector("label[for=\"c17983\"]");
        //wait.until(ExpectedConditions.elementToBeClickable(nfcFilterEl));
        //driver.findElement(nfcFilterEl).click();
        click(nfcFilterEl);
        final By showModels = By.cssSelector("a.show-models");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(showModels));
        } catch (TimeoutException e) {
            throw new AssertionError("Show results popup has not displayed");
        }
        click(showModels);
    }

    @Step(" Открытие подробностей этой камеры и добавление ее в закладки")
    private void checkItemAndAddBookmark() {
        By itemLink = By.linkText(actionCameraName);

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(itemLink));
        } catch (TimeoutException e) {
            throw new AssertionError( actionCameraName + " not found in search results");
        }

        WebElement item = driver.findElement(itemLink);
        new Actions(driver)
                .moveToElement(item)
                .click()
                .perform();

        By addBookmark = By.cssSelector(".big-star-off.off");
        click(addBookmark);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".big-star-on.on")));
        } catch (TimeoutException e) {
            throw new AssertionError("Failed to add the test item to bookmarks");
        }
    }


    @Step("Проверка истории")
    private void checkHistory() {
        By historyBtn = By.id("bar_bm_visited");
        click(historyBtn);

        By historyItem = By.partialLinkText(actionCameraName);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(historyItem));
        } catch (TimeoutException|ElementNotFoundException e) {
            throw new AssertionError("Item is not found in view history");
        }

        click(historyBtn);
    }


    @Step("Удаление")
    private void checkAndRemoveBookmark() {
        By bookmarksBtn = By.id("bar_bm_marked");
        click(bookmarksBtn);
        WebElement item = driver.findElement(By.partialLinkText(actionCameraName));
        try {
            wait.until(ExpectedConditions.visibilityOf(item));
        } catch (TimeoutException e) {
            throw new AssertionError("Item is not found in bookmarks");
        }

        By removeBookmark = By.cssSelector(".big-star-on.on");
        click(removeBookmark);
        click(bookmarksBtn);
    }

    @Test
    public void actionCameraTest() {
        login();
        goToCategoryAndApplyFilters();
        checkItemAndAddBookmark();
        checkHistory();
        checkAndRemoveBookmark();
        logout();
    }
}
