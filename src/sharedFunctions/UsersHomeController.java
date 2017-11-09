/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  UsersHomeController.java
 * Created on Sep 14, 2017, 12:22:29 PM
 * 
 */
package sharedFunctions;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class UsersHomeController implements Initializable {

    @FXML
    private AnchorPane usersHome;

    @FXML
    private void handleMouseEnter_action() {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        usersHome.setEffect(colorAdjust);

    }

    @FXML
    private void handleMouseExit_action() {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        usersHome.setEffect(colorAdjust);

    }

    /*
        Action handler sets the root BorderPane to the UsersView UI which displays
        user data.
    */
    @FXML
    private void handleMouseClick_action() throws IOException {

        BorderPane root = Context.getInstance().getData().getContentPane();
        UsersViewController controller = new UsersViewController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sharedFunctions/UsersView.fxml"));
        loader.setController(controller);
        AnchorPane newPane = loader.load();
        root.setCenter(newPane);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
