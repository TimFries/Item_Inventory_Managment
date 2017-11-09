/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  WarehouseViewController.java
 * Created on Sep 18, 2017, 12:42:57 PM
 * 
 */
package sharedFunctions;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.apache.commons.lang3.text.WordUtils;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class WarehouseViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox ScrollContent;
    @FXML
    private AnchorPane warehouseViewRoot;
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ComboBox BuildingType_Combo;

    @FXML
    private TextField TableName_Text;
    @FXML
    private Button TableSubmit;

    private ArrayList<String> warehouseList;
    private Label emptyTables;
    
    private WarehouseViewExtend exFun = new WarehouseViewExtend();

    
    /*
        Handles the creation of a new table (warehouse/retail store).  Handles error
        checks.
    */
    @FXML
    private void handleTableCreation_action() {

        if (exFun.formCreationCheck(BuildingType_Combo.getValue(), TableName_Text.getText().trim().isEmpty())) {
            Alert confirm = new Alert(AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Building Name");
            confirm.setHeaderText(null);
            confirm.setContentText(("Are you sure you want to use the building name '" + TableName_Text.getText() + "'?"));
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (exFun.tableNameAvaliable(TableName_Text.getText(), BuildingType_Combo.getValue().toString())) {
                    String name = exFun.createTableName(TableName_Text.getText(), BuildingType_Combo.getValue().toString());
                    exFun.createTableDB(name);
                    FXMLLoader fxmlLoader;
                    try {

                        name = name.replace('_', ' ');
                        name = WordUtils.capitalizeFully(name);
                        WarehouseSelectController controller = new WarehouseSelectController();
                        controller.setWarehouseName(name);
                        fxmlLoader = new FXMLLoader(getClass().getResource("/sharedFunctions/WarehouseSelect.fxml"));
                        fxmlLoader.setController(controller);

                        AnchorPane newPane = fxmlLoader.load();

                        if (ScrollContent.getChildren().contains(emptyTables)) {
                            ScrollContent.getChildren().clear();
                        }
                        ScrollContent.getChildren().add(newPane);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        }

    }

    /*
        During the initialize function the Hbox is populated dynamicaly with WarehouseSelect Ui's
        unquie to each item inventory table avaliable on the database.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader fxmlLoader;

        setPermissionAreas();
        setComboBox();
        String newName = "";
        warehouseList = exFun.getTableCount();
        
        
        if (warehouseList.size() != 0) {
            for (int i = 0; i < warehouseList.size(); i++) {
                newName = warehouseList.get(i);
                newName = newName.replace('_', ' ');
                newName = WordUtils.capitalizeFully(newName);
                try {

                    WarehouseSelectController controller = new WarehouseSelectController();
                    controller.setWarehouseName(newName);
                    fxmlLoader = new FXMLLoader(getClass().getResource("/sharedFunctions/WarehouseSelect.fxml"));
                    fxmlLoader.setController(controller);

                    AnchorPane newPane = fxmlLoader.load();

                    ScrollContent.getChildren().add(newPane);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {

            emptyTables = new Label();
            emptyTables.setText("No Tables Avaliable");
            emptyTables.setTextFill(Color.web("#e10c0c"));
            emptyTables.setFont(Font.font(32));
            ScrollContent.getChildren().add(emptyTables);
        }

    }

    /*
        Sets the ComboBox items for the table creation form.
    */
    private void setComboBox() {

        ObservableList<String> content = FXCollections.observableArrayList("Warehouse", "Retail Store");
        BuildingType_Combo.setItems(content);
    }


    /*
        Set permission area based on the current users permission levels.
    */
    private void setPermissionAreas() {

        if (Context.getInstance().getData().getCurPerm().equals("NNYY") || Context.getInstance().getData().getCurPerm().equals("NNNY")) {
            BuildingType_Combo.setDisable(true);
            TableName_Text.setDisable(true);
            TableSubmit.setDisable(true);
        }

    }
}
