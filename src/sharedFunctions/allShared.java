/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  allShared.java
 * Created on Sep 12, 2017, 4:20:00 PM
 * 
 */
package sharedFunctions;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Tim
 */
public class allShared {

    /*
        Function is called by the logout button and changes the window size and UI to
        the login screen (LoginUIDocument).
    */
    public void logoutApp(ActionEvent event, AnchorPane root) throws IOException {

        Context.getInstance().getData().setCurPerm(null);
        Context.getInstance().getData().setCurUser(null);

        Parent newUI = FXMLLoader.load(getClass().getResource("/inv_manager/LoginUIDocument.fxml"));

        Scene newuser_scene = new Scene(newUI);

        Stage app_stage = (Stage) root.getScene().getWindow();

        app_stage.setScene(newuser_scene);

        app_stage.show();
    }

    /*
        Function is called when the return button is pressed by the user.
        It changes the root BorderPane center node to the correct home screen 
        appointed to the users permission level.
    */
    public void returnHome(BorderPane root, String permissions) throws IOException {

        FXMLLoader loader;
        switch (permissions) {
            case "YYYY":
            case "NYYY":
            case "NNYY":

                loader = new FXMLLoader(getClass().getResource("/sharedFunctions/adminHomeCenter.fxml"));
                HBox highPerm = loader.load();
                root.setCenter(highPerm);
                break;
            case "NNNY":
            default:

                loader = new FXMLLoader(getClass().getResource("/sharedFunctions/WarehouseView.fxml"));
                AnchorPane normalPerm = loader.load();
                root.setCenter(normalPerm);
                
                break;

        }
        
        

    }

    /*
        Return the obersvable list of strings used by the combobox in the UserView
        UI.
    */
    public ObservableList<String> getEditPermList(String curUserPerm) {
        ObservableList<String> permList = FXCollections.observableArrayList();
        if (curUserPerm.charAt(0) == 'Y') {
            permList.add("SuperAdmin");
        }
        if (curUserPerm.charAt(1) == 'Y') {
            permList.add("Admin");
        }
        if (curUserPerm.charAt(2) == 'Y') {
            permList.add("Manager");
        }

        permList.add("NormalUser");
        return permList;
    }

}
