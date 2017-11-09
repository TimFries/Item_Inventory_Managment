/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  Inv_Manager.java
 * Created on Sep 2, 2017, 2:16:52 PM
 * 
 */
package inv_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tim
 */

/*
    Main startup code for the application
    Will set the application to the login ui
 */
public class Inv_Manager extends Application {

    public static Stage parentWindow;

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("LoginUIDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
