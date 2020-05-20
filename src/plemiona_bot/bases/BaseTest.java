/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot.bases;

/**
 *
 * @author TiMan
 */
public class BaseTest {

    public BaseTest() {
    }

    //@BeforeTest(alwaysRun = true)
    public void beforeTest() {
        BaseDriver.instantiateWebDriver();
    }

    //@AfterTest(alwaysRun = true)
    public void afterTest() {
        BaseDriver.quitDriver();
    }
}
