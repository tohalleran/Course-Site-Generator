/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tonyohalleran
 */
public class Team <E extends Comparable<E>> implements Comparable<E>{
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty colorText;
    private final StringProperty link;
    
    public Team(String initName, String initColor, String initColorText, String initLink){
        name = new SimpleStringProperty(initName);
        color = new SimpleStringProperty(initColor);
        colorText = new SimpleStringProperty(initColorText);
        link = new SimpleStringProperty(initLink);
    }
    
    
    @Override
    public int compareTo(E otherTeam) {
        return getName().compareTo(((Team)otherTeam).getName());
    }

    public String getName() {
        return name.get();
    }

    public String getColor() {
        return color.get();
    }
    public String getRed() {
        String redHex = color.get().substring(2,4);
        int redInt = Integer.parseInt(redHex, 16);
        String red = String.valueOf(redInt);
        if(red.substring(0, 1).equals("0"))
            return red.substring(1);
        else
            return red;
    }
    public String getGreen() {
        String greenHex = color.get().substring(4,6);
        int greenInt = Integer.parseInt(greenHex, 16);
        String green = String.valueOf(greenInt);
        if(green.substring(0, 1).equals("0"))
            return green.substring(1);
        else
            return green;
    }
    public String getBlue() {
        String blueHex = color.get().substring(6,8);
        int blueInt = Integer.parseInt(blueHex, 16);
        String blue = String.valueOf(blueInt);
        if(blue.substring(0, 1).equals("0"))
            return blue.substring(1);
        else
            return blue;
    }

    public String getColorText() {
        return colorText.get();
    }
    
    public String getColorTextTrim() {
        String color = colorText.get();
        if(color.equals("0xffffffff")){
            return "white";
        }
        if(color.equals("0x000000ff")){
            return "black";
        }
        else
            return "white";
    }

    public String getLink() {
        return link.get();
    }
    
    public void setName(String initName) {
        name.set(initName);
    }

    public void setColor(String initColor) {
        color.set(initColor);
    }

    public void setColorText(String initColorText) {
        colorText.set(initColorText);
    }

    public void setLink(String initLink) {
        link.set(initLink);
    }
    
    
    
}
