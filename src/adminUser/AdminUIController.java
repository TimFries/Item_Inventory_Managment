/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  AdminUIController.java
 * Created on Sep 6, 2017, 9:54:52 AM
 * 
 */
package adminUser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import sharedFunctions.LoggerConnector;
import sharedFunctions.Context;
import sharedFunctions.allShared;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class AdminUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private String username;
    private String permissions;

    @FXML
    private AnchorPane root;
    @FXML
    public BorderPane Content;


    public void setUsername(String value) {
        username = value;
    }

    public void setPermissions(String value) {
        permissions = value;

    }

    

    @FXML
    private void handleReturnhome_action() throws IOException {

        allShared action = new allShared();
        action.returnHome(Context.getInstance().getData().getContentPane(), permissions);

    }

    /*  Handles the action for the logout menuitem when it is used by the user
    
    */
    @FXML
    private void handleLogout_action(ActionEvent event) throws IOException {

        LoggerConnector logCon = new LoggerConnector();
        logCon.log(username+" logged out of the application", username);
        allShared action = new allShared();
        action.logoutApp(event, root);

    }

    
    /*  During the initialize process the permissions of the user are used to determine the 
        'Home Page' module to use when first logging into the application  */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Context.getInstance().getData().setCurUser(username);        //sets the username of the user that is currently logged in
        Context.getInstance().getData().setCurPerm(permissions);     //sets the current permission level of the user that is logged in
        Context.getInstance().getData().setContentPane(Content);     //sets the BorderPane that is used to organize the modules that are called
        FXMLLoader loader;
        
        if(permissions.equals("NNNY")){
            
            loader = new FXMLLoader(getClass().getResource("/sharedFunctions/WarehouseView.fxml"));
            AnchorPane newAnchor = new AnchorPane();
            
            try {
                
                newAnchor = loader.load();
                
            } catch (IOException ex) {
                
                ex.printStackTrace();
            }
            
            Content.setCenter(newAnchor);
        }
        else{
            
            loader = new FXMLLoader(getClass().getResource("/sharedFunctions/adminHomeCenter.fxml"));
            HBox newBox = new HBox();
            
            try{
                
                newBox = loader.load();
                
            }
            catch(IOException ex){
                
                ex.printStackTrace();
            }
            
            Content.setCenter(newBox);
        }
    }

}
