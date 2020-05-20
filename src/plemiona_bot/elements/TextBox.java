/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot.elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import plemiona_bot.bases.BaseElement;

/**
 *
 * @author TiMan
 */
public class TextBox extends BaseElement {

    public TextBox(By locator) {
        super(locator);
    }
    
    public void setText(String text) {
        waitUntilClickable().sendKeys(text);
        clickESC();
    }
}
