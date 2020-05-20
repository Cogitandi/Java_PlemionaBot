/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import plemiona_bot.bases.BaseDriver;
import plemiona_bot.bases.BaseTest;
import plemiona_bot.elements.Button;
import plemiona_bot.elements.ElementList;
import plemiona_bot.elements.Label;
import plemiona_bot.elements.Table;
import plemiona_bot.elements.TextBox;

/**
 *
 * @author TiMan
 */
public class FirstTest extends BaseTest {

    WebDriver driver;
    static final int KIND_SS = 1;
    static final int KIND_LK = 5;
    // Account
    String login = "Krasy Kropek";
    String password = "ziomek12";

    // Village
    final String villageId = "7179";
    String cords = "511|579";
    String worldNumber = "154";

    // Settings
    final String MAP_SIZE = "20"; // area to search villages under attack
    public static int timeBetweenAttacks = 2500; // in seconds

    // Attacks setting
    public static int MAX_POINTS = 55;
    Integer spearsToAttack = 2;
    Integer swordsToAttack = 2;
    double maxDistanceSS = 0; // max distance to send attack
    static int secondsPerFieldSS = 1380;

    String lightTropperToAttack = "1";
    double minDistanceLT = 0; // max distance to send attack
    double maxDistanceLT = 20; // max distance to send attack
    static int secondsPerFieldLT = 600;

    // Temp
    List<Village> villages;
    long commandsTime;
    List<Command> commands = new ArrayList<>();
    long secondsToNextAction;
    int attackAmountSS;
    int attackAmountLightTropper;

    // Buildings
    private final String main = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=main";
    private final String barracks = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=barracks";
    private final String place = "https://pl" + worldNumber + ".plemiona.pl/game.php?village=" + villageId + "&screen=place";

    public void makeTest() {
        try {

            beforeTest();
            driver = BaseDriver.getDriver();
            //driver.manage().window().setPosition(new Point(0,-1000));
            if (villages == null || villages.isEmpty()) {
                villages = getList();
            }

            login();
            countArmy();
            getCommmandList();
            System.out.println("2");
            sendAttackToVillagesFromList();
            System.out.println("3");
            afterTest();
            System.out.println("4");
            setSecondsToNextAction();
            // Wait and re run script 
            System.out.println("Wylaczylem przegladarke, widzimy sie za: " + secondsToNextAction + " sekund");
            TimeUnit.SECONDS.sleep(secondsToNextAction);
            makeTest();
        } catch (Exception e) {
            System.out.println("erro zlapalem");
            villages = null;
            afterTest();
            makeTest();
        }

    }

    // Fill form looking for villages to attack
    private void villageForm() {

        driver.get("https://plemiona.vopo.pl/village_list/");

        // Initialize elements
        Select worldSelect = new Select(driver.findElement(By.xpath("//*[@id=\"id_world\"]")));
        TextBox villageInput = new TextBox(By.xpath("//*[@id=\"id_coords\"]"));
        TextBox mapSizeInput = new TextBox(By.xpath("//*[@id=\"id_size\"]"));
        Button submitBtn = new Button(By.xpath("//*[@id=\"page-wrapper\"]/div/form/button"));

        worldSelect.selectByValue("pl" + worldNumber);
        villageInput.setText(cords);
        mapSizeInput.setText(MAP_SIZE);
        submitBtn.click();
    }

    // get list contained villages to attack
    private List<Village> getList() {
        villageForm();
        List<Village> villages = new ArrayList<>();
        Button onlyAbandonen = new Button(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div[7]/div/label[3]"));
        Button hideAbandoned = new Button(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/div[7]/div/label[2]"));
        //onlyAbandonen.click();
        //hideAbandoned.click();
        List<WebElement> rows = new ElementList(By.id("villages")).getTableRows();
        rows.forEach((WebElement row) -> {
            String name = row.findElements(By.tagName("td")).get(1).getText();
            String cord = name.substring(name.length() - 12, name.length() - 5);
            int points = Integer.parseInt(row.findElements(By.tagName("td")).get(2).getText());
            float distance = Float.parseFloat(row.findElements(By.tagName("td")).get(5).getText());
            System.out.println(name+", "+cord+", "+points);
            villages.add(new Village(cord, points, distance));
            
        });
        Collections.sort(villages);
        System.out.println("Liczba znalezionych wiosek: " + villages.size());
        return villages;
    }

    // login to account
    private void login() {
        driver.get("https://www.plemiona.pl");

        TextBox nameInput = new TextBox(By.xpath("//*[@id=\"user\"]"));
        TextBox passwordInput = new TextBox(By.xpath("//*[@id=\"login_form\"]/label[2]"));
        Button submit = new Button(By.xpath("//*[@id=\"login_form\"]/div/div/a"));

        nameInput.setText(login);
        passwordInput.setText(password);
        submit.click();

        WebElement worldButton = new Button(By.xpath("//*[@id=\"home\"]/div[3]/div[4]/div[10]/div[3]/div[2]/div[1]/h4")).waitUntilVisible();
        driver.get("https://www.plemiona.pl/page/play/pl" + worldNumber);
    }

    // close award window
    private void closeAward() {
        Button close = new Button(By.xpath("//*[@id=\"popup_box_daily_bonus\"]/div/a"));
        close.click();
    }

    // send attack to village
    private void sendAttack(String cords, int kind) {
        driver.get(place);

        // send attack
        TextBox spearsInput = new TextBox(By.xpath("//*[@id=\"unit_input_spear\"]"));
        TextBox swordsInput = new TextBox(By.xpath("//*[@id=\"unit_input_sword\"]"));
        TextBox lightTropperInput = new TextBox(By.xpath("//*[@id=\"unit_input_light\"]"));
        TextBox cordsInput = new TextBox(By.xpath("//*[@id=\"place_target\"]/input"));
        Button submit = new Button(By.xpath("//*[@id=\"target_attack\"]"));
        System.out.println("KIND" + kind);
        if (kind == KIND_SS) {
            spearsInput.setText(spearsToAttack.toString());
            swordsInput.setText(swordsToAttack.toString());
        }
        if (kind == KIND_LK) {
            lightTropperInput.setText(lightTropperToAttack);
        }

        cordsInput.setText(cords);
        submit.click();

        // new content
        Button sendAttackBtn = new Button(By.xpath("//*[@id=\"troop_confirm_go\"]"));
        sendAttackBtn.click();
    }

    private void countArmy() {
        driver.get(place);

        String spearsLabel;
        String swordsLabel;
        String lightTropperLabel;

        spearsLabel = new Label(By.xpath("//*[@id=\"units_entry_all_spear\"]")).getText();
        swordsLabel = new Label(By.xpath("//*[@id=\"units_entry_all_sword\"]")).getText();
        lightTropperLabel = new Label(By.xpath("//*[@id=\"units_entry_all_light\"]")).getText();

        // cut brackets
        spearsLabel = spearsLabel.substring(1, spearsLabel.length() - 1);
        swordsLabel = swordsLabel.substring(1, swordsLabel.length() - 1);
        lightTropperLabel = lightTropperLabel.substring(1, lightTropperLabel.length() - 1);

        int spearsAmount = Integer.parseInt(spearsLabel);
        int swordsAmount = Integer.parseInt(swordsLabel);
        int lightTropperAmount = Integer.parseInt(lightTropperLabel);

        attackAmountSS = spearsAmount > swordsAmount ? spearsAmount : swordsAmount;
        attackAmountSS /= spearsToAttack;
        attackAmountLightTropper = lightTropperAmount / Integer.parseInt(lightTropperToAttack);
        System.out.println("Ilosc atakow do wyslania ss: " + attackAmountSS + ", lk" + attackAmountLightTropper);
    }
//        private void countArmy() {
//        String spearsLabel;
//        String swordsLabel;
//        int slashPosition;
//        driver.get(barracks);
//
//        List<WebElement> rows = new ElementList(By.xpath("//*[@id=\"train_form\"]/table/tbody")).getTableRows();
//
//        if (rows.size() >= 3) {
//            spearsLabel = new Label(By.xpath("//*[@id=\"train_form\"]/table/tbody/tr[2]/td[3]")).getText();
//            slashPosition = spearsLabel.indexOf('/');
//            spearsInVillage = Integer.parseInt(spearsLabel.substring(0, slashPosition));
//            spearsInArmy = Integer.parseInt(spearsLabel.substring(slashPosition + 1));
//        }
//        if (rows.size() >= 4) {
//            swordsLabel = new Label(By.xpath("//*[@id=\"train_form\"]/table/tbody/tr[3]/td[3]")).getText();
//            slashPosition = swordsLabel.indexOf('/');
//            swordsInVillage = Integer.parseInt(swordsLabel.substring(0, slashPosition));
//            swordsInArmy = Integer.parseInt(swordsLabel.substring(slashPosition + 1));
//        }
//        int lowerUnit = spearsInVillage > swordsInVillage ? swordsInVillage : spearsInVillage;
//        attackAmount = lowerUnit / Integer.parseInt(spearsToAttack);
//        System.out.println("Ilosc atakow do wyslania: " + attackAmount);
//    }

    private void sendLightTropperAttack() {

    }

    private void sendAttackToVillagesFromList() {
        for (Iterator<Village> it = villages.iterator(); it.hasNext();) {
            try {
                Village village = it.next();
                if (attackAmountSS == 0 && attackAmountLightTropper == 0) {
                    break;
                }
                System.out.println(village.getCords() + "odleglosc: " + village.getDistance());
                //SS Attack
                if (attackAmountSS > 0 && village.getDistance() <= maxDistanceSS && village.canBeAttack(commands, commandsTime, KIND_SS)) {
                    System.out.println("SS moge atakowac: " + village.getCords() + " pozostalo" + attackAmountSS);
                    sendAttack(village.getCords(), KIND_SS);
                    attackAmountSS--;
                    driver.manage().timeouts().implicitlyWait(150, TimeUnit.MILLISECONDS);
                    it.remove();
                    continue;
                }
                if (attackAmountLightTropper > 0 && village.getDistance() >= minDistanceLT && village.getDistance() <= maxDistanceLT && village.canBeAttack(commands, commandsTime, KIND_LK)) {
                    System.out.println("LT moge atakowac: " + village.getCords() + " pozostalo" + attackAmountLightTropper);
                    sendAttack(village.getCords(), KIND_LK);
                    attackAmountLightTropper--;
                    driver.manage().timeouts().implicitlyWait(150, TimeUnit.MILLISECONDS);
                    it.remove();
                    continue;
                }
                it.remove();
            } catch (Exception e) {
            }

        }

    }

    private void getCommmandList() {
        driver.get(place);
        try {
            Table table = new Table(By.xpath("//*[@id=\"commands_outgoings\"]/table/tbody"));
            commands = table.getCommandList();
            commandsTime = Calendar.getInstance().getTimeInMillis() / 1000;
            if (commands.size() > 0) {
                secondsToNextAction = commands.get(0).getSecondsToAction();
            }
        } catch (Exception e) {

        }

    }

    private void setSecondsToNextAction() {
        long currentTime = Calendar.getInstance().getTimeInMillis() / 1000;
        if (commands.size() > 0) {
            secondsToNextAction = (commandsTime + commands.get(0).getSecondsToAction()) - currentTime;
        } else {
            secondsToNextAction = 10;
        }
    }

}
