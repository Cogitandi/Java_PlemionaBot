/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author TiMan
 */
public class Command {

    private String name;
    private String delivered;
    private String timeToAction;

    public Command(String name, String delivered, String timeToAction) {
        this.name = name;
        this.delivered = delivered;
        this.timeToAction = timeToAction;
    }

    public String getName() {
        return name;
    }

    public String getDelivered() {
        return delivered;
    }

    public String getTimeToAction() {
        return timeToAction;
    }

    public boolean isAttack() {
        return name.contains("Atak");
    }

    public long getSecondsToAction() {
        int firstColon = timeToAction.indexOf(":");
        int lastColon = timeToAction.lastIndexOf(":");
        int hour = Integer.parseInt(timeToAction.substring(0, firstColon));
        int minutes = Integer.parseInt(timeToAction.substring(firstColon + 1, lastColon));
        int seconds = Integer.parseInt(timeToAction.substring(lastColon + 1, timeToAction.length()));
        return hour * 3600 + minutes * 60 + seconds;
    }

    public String getCords() {
        return name.substring(name.length() - 12, name.length() - 5);
    }

//    public int getSecondsToAction() {
//        String deliveredTimeString = delivered.substring(10, delivered.length() - 4);
//        LocalTime deliveredTime = LocalTime.parse(deliveredTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
//        int deliveredTimeInSeconds = (deliveredTime.getHour() * 3600) + (deliveredTime.getMinute() * 60) + deliveredTime.getSecond();
//
//        String currentTimeString = java.util.Calendar.getInstance().getTime().toString();
//        LocalTime currentTime = LocalTime.parse(currentTimeString, DateTimeFormatter.ofPattern("HH:mm:ss"));
//        int currentTimeInSeconds = (currentTime.getHour() * 3600) + (currentTime.getMinute() * 60) + currentTime.getSecond();
//
//        return deliveredTimeInSeconds - currentTimeInSeconds;
//    }
}
