/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot.elements;

import org.openqa.selenium.By;
import plemiona_bot.bases.BaseElement;

/**
 *
 * @author TiMan
 */
public class Button extends BaseElement {

    public Button(By locator) {
        super(locator);
    }

    public void click() {
        waitUntilClickable().click();            
    }
}
