/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  NewUserUIController.java
 * Created on Sep 2, 2017, 2:46:36 PM
 * 
 */
package userCreation;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.*;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class NewUserUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PasswordField Password_text; 
    @FXML
    private PasswordField Confirm_text;
    @FXML
    private TextField FirstName_text;
    @FXML
    private TextField LastName_text;
    @FXML
    private TextField Username_text;
    public Text UsedUsername_text;
    public Text ServerError_text;
    public Text NoMatchPass_text;
    public Text PatternError_text;
    public Text SubmitError_text;
    
    UserConnector userConnector = new UserConnector();
    
    @FXML
    private void handleBacktoLog_ButtonAction(ActionEvent event) throws IOException{
        System.out.println("Switch to login UI");
        
        Parent newuser_ui = FXMLLoader.load(getClass().getResource("/inv_manager/LoginUIDocument.fxml"));
        Scene newuser_scene = new Scene(newuser_ui);
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(newuser_scene);
        app_stage.show();
    }
    
    @FXML
    private void handleSubmit_ButtonAction(ActionEvent event) throws IOException{
        
        resetErrorMessages();
        
        
        
        if(accountFormCheck()) {
            if (Password_text.getText().equals(Confirm_text.getText())) {
                System.out.println("The passwords match");
                userConnector.setUserCreationData((FirstName_text.getText().substring(0,1).toUpperCase() + FirstName_text.getText().substring(1)),
                                                  (LastName_text.getText().substring(0,1).toUpperCase() + LastName_text.getText().substring(1)), 
                                                  Username_text.getText(), Password_text.getText());
                
                
                if (!userConnector.UserCheckConnector() || !userConnector.Server_Error) {
                    System.out.println("No matching username was found");
                    System.out.println("Creating new user now");
                    userConnector.UserCreationConnector();
                } else {
                    if (userConnector.Server_Error) {
                        ServerError_text.setVisible(true);
                    } else {
                        System.out.println("A matching username was found");
                        UsedUsername_text.setVisible(true);
                    }
                }
            } else {
                System.out.println("Passwords didn't match");
                NoMatchPass_text.setVisible(true);
            }
        }
        else{
            System.out.println("Pattern Error");
            
        }
    }
    
    private boolean accountFormCheck(){
        boolean pass = true;
        Pattern userpattern = Pattern.compile("^[a-zA-Z]{1}([a-zA-Z0-9.]){4,11}$");
        Pattern namepattern = Pattern.compile("^[a-zA-Z]{2,16}$");
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,16}$");
        Matcher matcher = namepattern.matcher(FirstName_text.getText());
        
        if(!matcher.matches()){
            pass = false;
            System.out.println("Firstname field was blank or didn't match a name pattern");
            FirstName_text.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            //set visible error marker for Firstname
            
        }
        else{
            FirstName_text.setStyle(null);
        }
        matcher = namepattern.matcher(LastName_text.getText());
        if(!matcher.matches()){
            pass = false;
            System.out.println("Lastname field was blank or didn't match a name pattern");
            LastName_text.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            //set visible error marker for Lastname
        }
        else{
            LastName_text.setStyle(null);
        }
        matcher = userpattern.matcher(Username_text.getText());
        if(!matcher.matches()){
            pass = false;
            Username_text.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            System.out.println("Username field was blank or didn't match a username pattern");
        }
        else{
            Username_text.setStyle(null);
        }
        matcher = passwordPattern.matcher(Password_text.getText());
        if(!matcher.matches()){
            pass = false;
            Password_text.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            Confirm_text.setStyle("-fx-text-box-border: red; -fx-focus-color: red;");
            System.out.println("Password field was blank or didn't match a password pattern");
            PatternError_text.setVisible(true);
            
            //set visible error marker for Password
        }
        else{
            Password_text.setStyle(null);
            Confirm_text.setStyle(null);
        }
        
        if(!pass)
            SubmitError_text.setVisible(true);
        else
            SubmitError_text.setVisible(false);
        return pass;
    }
    
    private void resetErrorMessages(){
        ServerError_text.setVisible(false);
        NoMatchPass_text.setVisible(false);
        UsedUsername_text.setVisible(false);
        PatternError_text.setVisible(false);
        SubmitError_text.setVisible(false);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
