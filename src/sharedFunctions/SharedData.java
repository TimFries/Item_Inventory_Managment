/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedFunctions;

import javafx.scene.layout.BorderPane;

/**
 *
 * @author Tim
 */
public class SharedData {

    private String curUser;
    private String curPerm;
    private BorderPane contentPane;
    
    public String getCurUser() {
        return curUser;
    }

    public void setCurUser(String curUser) {
        this.curUser = curUser;
    }

    public String getCurPerm() {
        return curPerm;
    }

    public void setCurPerm(String curPerm) {
        this.curPerm = curPerm;
    }

    public BorderPane getContentPane() {
        return contentPane;
    }

    public void setContentPane(BorderPane contentPane) {
        this.contentPane = contentPane;
    }
    
    
}
