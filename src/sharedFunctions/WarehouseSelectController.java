/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedFunctions;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author Tim
 */
public class WarehouseSelectController implements Initializable {

    private String warehouseName;

    @FXML
    private Label name;

    @FXML
    private AnchorPane warehouseChoice;
    
    @FXML
    private ContextMenu DeletionMenu;


    /*
        Action handles the deletion of a warehouse/retail store and all the 
        item inventory information that goes with it.  An alert box is called 
        asking if the user is sure they want to delete it.
    */
    @FXML
    private void handleWarehouseDeletion_action(){
        
        
        VBox tables = (VBox)warehouseChoice.getParent();
        Alert confirmDel = new Alert(AlertType.CONFIRMATION);
        confirmDel.setTitle("Confirm Deletion");
        confirmDel.setContentText("Are you sure you want to delete '"+warehouseName+"'?");
        Optional<ButtonType> result = confirmDel.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            tables.getChildren().remove(this.warehouseChoice);
            removeTableDB();
        }
        
        
    }
    
    /*
        Action is called if the user clicks the primary mouse button.  The root BorderPane's
        center node is changed to the TableView UI.
    */
    @FXML
    private void handleTableChoice_action(MouseEvent e) throws IOException{
        MouseButton button = e.getButton();
        if(button == MouseButton.PRIMARY){
            String tempname;
            
            BorderPane root = (BorderPane) Context.getInstance().getData().getContentPane();
            TableViewController controller = new TableViewController();
            tempname = name.getText();
            tempname = tempname.replace(' ', '_');
            tempname = tempname.toLowerCase();
            controller.setTableName(tempname);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sharedFunctions/TableView.fxml"));
            loader.setController(controller);
            AnchorPane newPane = loader.load();
            root.setCenter(newPane);
        
        }
        
    }

   

    @FXML
    private void handleMouseEnter_action() {

        InnerShadow inner = new InnerShadow();
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.3);
        inner.setInput(colorAdjust);
        warehouseChoice.setEffect(inner);

    }

    @FXML
    private void handleMouseExit_action() {

        InnerShadow inner = new InnerShadow();
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.0);
        inner.setInput(colorAdjust);
        warehouseChoice.setEffect(inner);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setName();
        setPermissionAreas();

        // TODO
    }

    public void setWarehouseName(String value) {

        warehouseName = value;
    }

    public void setName() {
        name.setText(warehouseName);
    }
    
    /*
        Set the permission areas that can accessed by the user.
    */
    private void setPermissionAreas(){
        
        if(Context.getInstance().getData().getCurPerm().equals("NNNY") || Context.getInstance().getData().getCurPerm().equals("NNYY")){
            
            DeletionMenu.getItems().clear();
            
        }
        
    }
    
    /*
        Function handles the mySQL query to drop the table when the user wants it
        deleted.
    */
    private void removeTableDB(){
        LoggerConnector logcon = new LoggerConnector();
            
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");
            Statement myStmt = con.createStatement();
            myStmt.execute("DROP TABLE "+convertName());
            logcon.log(Context.getInstance().getData().getCurUser()
                       +" deleted the inventory table for "
                       +warehouseName, Context.getInstance().getData().getCurUser());
            
            myStmt.close();
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private String convertName(){
        String temp = warehouseName;
        
        temp = temp.replace(' ', '_');
        temp = temp.toLowerCase();
        return temp;
    }
    
    

}
