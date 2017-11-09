/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  WarehouseHomeController.java
 * Created on Sep 14, 2017, 12:22:07 PM
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
public class WarehouseHomeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane warehouseHome;

    @FXML
    private void handleMouseEnter_action() {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        warehouseHome.setEffect(colorAdjust);

    }

    @FXML
    private void handleMouseExit_action() {

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        warehouseHome.setEffect(colorAdjust);

    }

    /*
        Action handler sets the root BorderPane to the WarehouseView UI
        that will display all the warehouses/retail stores to choose from.
    */
    @FXML
    private void handleMouseClick_action() throws IOException {

        BorderPane root = Context.getInstance().getData().getContentPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sharedFunctions/WarehouseView.fxml"));
        AnchorPane newPane = loader.load();
        root.setCenter(newPane);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
