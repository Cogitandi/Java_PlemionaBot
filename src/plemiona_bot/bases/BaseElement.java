/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot.bases;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author TiMan
 */
public class BaseElement {

    WebDriver driver = BaseDriver.getDriver();
    private WebDriverWait wait = new WebDriverWait(driver, 30);
    private By locator;

    public BaseElement(By locator) {
        this.locator = locator;
    }

    public WebElement waitUntilVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitUntilClickable() {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean isDisplayed() {
        return waitUntilVisible().isDisplayed();
    }

    public boolean isEnabled() {
        return waitUntilVisible().isEnabled();
    }

    public String getText() {
        return waitUntilVisible().getText();
    }

    public String getAttribute(String attribute) {
        return waitUntilVisible().getAttribute(attribute);
    }

    protected List<WebElement> getElements() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    public List<WebElement> getTableRows() {
        return wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(driver.findElement(locator), By.tagName("tr")));
    }

    public void clickESC() {
        driver.findElement(locator).sendKeys(Keys.ESCAPE);
    }

}
