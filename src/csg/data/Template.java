/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author tonyohalleran
 */
public class Template <E extends Comparable<E>> implements Comparable<E>{
    private final BooleanProperty use;
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    
    public Template(boolean initUse, String initNavbarTitle, String initFileName, String initScript){
        use = new SimpleBooleanProperty(initUse);
        navbarTitle = new SimpleStringProperty(initNavbarTitle);
        fileName = new SimpleStringProperty(initFileName);
        script = new SimpleStringProperty(initScript);
    }

    public boolean getUse() {
        return use.get();
    }

    public String getNavbarTitle() {
        return navbarTitle.get();
    }

    public String getFileName() {
        return fileName.get();
    }

    public String getScript() {
        return script.get();
    }
    
    
    
    
    @Override
    public int compareTo(E o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
