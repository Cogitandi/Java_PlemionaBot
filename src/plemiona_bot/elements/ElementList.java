/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import plemiona_bot.bases.BaseElement;

/**
 *
 * @author TiMan
 */
public class ElementList extends BaseElement {

    public ElementList(By locator) {
        super(locator);
    }

    public void selectFromList(String requiredValue) {
        for (WebElement element : getElements()) {
            if (element.getText().equals(requiredValue)) {
                element.click();
                break;
            }
        }
    }
    public void selectFromList(int index) {
        getElements().get(index).click();
    }
    public int getListSize() {
        return getElements().size();
    }
    public boolean isValuePresent(String requiredValue) {
        for(WebElement element: getElements() ) {
            if(element.getText().equals(requiredValue)) {
                return true;
            }
        }
        return false;
    }
}
