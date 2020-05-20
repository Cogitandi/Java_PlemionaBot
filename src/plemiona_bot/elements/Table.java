/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot.elements;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import plemiona_bot.Command;
import plemiona_bot.bases.BaseElement;

/**
 *
 * @author TiMan
 */
public class Table extends BaseElement {

    public Table(By locator) {
        super(locator);
    }

    public List<WebElement> getTableRowss() {
        return getElements().get(0).findElements(By.tagName("tr"));
    }

    public List<Command> getCommandList() {
        List<Command> commands = new ArrayList<>();
        getTableRowss().forEach((WebElement element) -> {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            if (cells.size() == 3) {
                String name = cells.get(0).getText();
                String delivered = cells.get(1).getText();
                String timeToAction = cells.get(2).getText();
                commands.add(new Command(name, delivered, timeToAction));
            }

        });
        return commands;
    }
}
