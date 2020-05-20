/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plemiona_bot;

import java.util.Calendar;
import java.util.List;

/**
 *
 * @author TiMan
 */
public class Village implements Comparable<Village> {

    private String cords;
    private int points;
    private float distance;

    public Village(String cords, int points, float distance) {
        this.cords = cords;
        this.points = points;
        this.distance = distance;
    }

    public String getCords() {
        return cords;
    }

    public int getPoints() {
        return points;
    }

    public float getDistance() {
        return distance;
    }

    public boolean canBeAttack(List<Command> commands, long commandTime,int kind) {
        System.out.println("Sprawdzam" + this.getCords());
        if (commands.size() == 0) {
            return true;
        }
        if( this.getPoints()>FirstTest.MAX_POINTS) {
            return false;
        }

       for(int i=commands.size()-1;i>=0;i--) {
           
           if (!commands.get(i).isAttack() || !commands.get(i).getCords().equals(this.getCords())) {
                    continue;
                }
            
           long timeToAttack = commands.get(i).getSecondsToAction(); // in seconds
           long fullAttackDurationTime=0;
           if(kind==FirstTest.KIND_SS) {
               fullAttackDurationTime = (int)(getDistance()*FirstTest.secondsPerFieldSS); //in seconds
           }
           if(kind==FirstTest.KIND_LK) {
               fullAttackDurationTime = (int)getDistance()*FirstTest.secondsPerFieldLT; //in seconds
           }
           //long attackTime = commandTime+timeToAttack; //in seconds
           System.out.println(" pelny czas ataku: "+fullAttackDurationTime+" pozostalo :"+timeToAttack+" roznica:"+(fullAttackDurationTime-timeToAttack));
           
           
           if((fullAttackDurationTime-timeToAttack)>=FirstTest.timeBetweenAttacks) {
                //System.out.println("wyslalem "+commands.get(i).getCords());
                return true;
           } else {
                //System.out.println("nie wyslalem");
                return false;
           }
           
       }
       return true;
    }
    

    @Override
    public int compareTo(Village arg0) {
        return (int) (this.getDistance() * 10 - arg0.getDistance() * 10);
    }

}
