/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  LoginUIDocumentController.java
 * Created on Sep 2, 2017, 2:16:52 PM
 * 
 */
package inv_manager;


import adminUser.AdminUIController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.*;
import sharedFunctions.LoggerConnector;
import userCreation.UserConnector;

/**
 *
 * @author Tim
 */
public class LoginUIDocumentController implements Initializable {
    
    @FXML
    private TextField Username_text;
    @FXML
    private PasswordField Password_text;
    
    
    public Text ServerError_text;       //Error message for server connection issues. Is currently invisible.
    public Text LoginError_text;        //Error message for username/password error.  Is currently invisible.
    
    
    
    /*
        Action handler for when the user presses the login button.
        Checks if the username and password are correct to one on the database.
        If there is no server error the user permissions associated with the login user
        are used to determine the home login screen.
    */
    
    @FXML
    private void handleLogin_ButtonAction(ActionEvent event) throws IOException{
       
        
        UserConnector userConnector = new UserConnector();
        resetSubmit();
        userConnector.setUserLoginData(Username_text.getText(), Password_text.getText());
        
        if(userConnector.LoginConnector()){
            Parent newuser_ui;
            LoggerConnector logCon = new LoggerConnector();
            logCon.log((Username_text.getText()+" logged in to the application"), Username_text.getText());
            
            
                    
                    FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/adminUser/AdminUI.fxml"));
                    
                    
                    AdminUIController controller = new AdminUIController();
                    controller.setUsername(Username_text.getText());
                    controller.setPermissions(userConnector.getPermissions());
                    fmxlLoader.setController(controller);
                    newuser_ui = fmxlLoader.load();
       
                   
                    
                    
                
            
        Scene newuser_scene = new Scene(newuser_ui);
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(newuser_scene);
        app_stage.show();
        }
        else{
            if(userConnector.Server_Error){
                ServerError_text.setVisible(true);
            }
            else{
                LoginError_text.setVisible(true);
            }
            
            
        }
    }
    
    /*
        Action handler for when the user presses the new user button.
        Changes to the new user UI for user creation
    */
    @FXML
    private void handleNewuser_ButtonAction(ActionEvent event) throws IOException {
        
        
        Parent newuser_ui = FXMLLoader.load(getClass().getResource("/userCreation/NewUserUI.fxml"));
        Scene newuser_scene = new Scene(newuser_ui);
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(newuser_scene);
        app_stage.show();
    }
    
    private void resetSubmit(){
        ServerError_text.setVisible(false);
        LoginError_text.setVisible(false);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
       
        
    }    
    
}
