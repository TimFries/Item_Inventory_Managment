/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedFunctions;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;


/**
 * FXML Controller class
 *
 * @author Tim
 */
public class TableViewController implements Initializable {

    private ArrayList<Items> itemlist = new ArrayList<>();

    private ObservableList<Items> observable;

    @FXML
    private Label characterCount;
    @FXML
    private TextArea itemDescription_text;
    @FXML
    private TextField itemName_text, itemAisle_text;
    @FXML
    private Button ItemSubmit;
    @FXML
    private TableView<Items> item_table;

    @FXML
    private TableColumn<Items, String> itemID_col, itemName_col, stockQuantity_col, aisle_col, totalShipped_col, restockQuantity_col, restockTracking_col, restockDate_col, itemDescription_col;

    private String tableName;

    private Pattern editPattern;
    
    private TableViewExtend exFun = new TableViewExtend();

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /*
        Action Handler sets the pattern the user needs to follow when editing table
        information.
    */
    @FXML
    private void handleEditPattern_action(TableColumn.CellEditEvent<Items, String> t) {

        switch (t.getTableColumn().getText()) {

            case "Stock":

                editPattern = Pattern.compile("^[0-9]{1,11}$");
                break;
            case "Aisle":

                editPattern = Pattern.compile("^[A-Z]{1}[0-9]{1,3}$");
                break;
            case "Total Shipped":

                editPattern = Pattern.compile("^[0-9]{1,11}$");
                break;
            case "Restock Quantity":

                editPattern = Pattern.compile("^[0-9]{1,11}$");
                break;
            case "Restock Tracking #":

                editPattern = Pattern.compile("^[a-zA-Z0-9]{32}$");
                break;
            case "Restock ETA":

                editPattern = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
                break;
            default:
                break;
        }

    }

    /*
        Handles the remaining character count the user has when making an item description for a 
        new item.
    */
    @FXML
    private void handleLength_action() {
        characterCount.setText("Character Count: " + (255 - itemDescription_text.getLength()));
    }

    /*
        Handles the action for editing an item on the table.  Does error checking and
        saves the edit to the item table in the database and logs the users edit to the
        log table.
    */
    @FXML
    private void handleItemEdit_action(TableColumn.CellEditEvent<Items, String> t) {

        String old = t.getOldValue();
        if (t.getTableColumn().getText().equals("Decription")) {
            if (t.getNewValue().length() > 255) {
                exFun.displayEditError(t.getTableColumn().getText());
                item_table.refresh();

            } else {
                ItemUpdate update = new ItemUpdate();
                String tempname = update.getItemColumnName(t.getTableColumn().getText().trim());
                
                if(update.itemUpdateDB(tableName, tempname, t.getRowValue().item_id, t.getNewValue())){
                    LoggerConnector logcon = new LoggerConnector();
                    
                    String temp = exFun.convertTableNameDisplay(tableName);
                    
                    logcon.log(Context.getInstance().getData().getCurUser()
                               +" changed the value of "
                               +t.getTableColumn().getText()
                               +" from " +old+ " to " +t.getNewValue()
                               +" for Item ID " +t.getRowValue().item_id
                               +" at building "+temp, Context.getInstance().getData().getCurUser());
                    saveObservable(t.getTableColumn().getText(),t.getNewValue(), t.getTablePosition().getRow());
                }
            }

        } else {
            Matcher matcher = editPattern.matcher(t.getNewValue());
            if (matcher.matches()) {
                
                ItemUpdate update = new ItemUpdate();
                String tempname = update.getItemColumnName(t.getTableColumn().getText().trim());
                
                if(update.itemUpdateDB(tableName, tempname, t.getRowValue().item_id, t.getNewValue())){
                    LoggerConnector logcon = new LoggerConnector();
                    
                    String temp = exFun.convertTableNameDisplay(tableName);
                    
                    
                    logcon.log(Context.getInstance().getData().getCurUser()
                               +" changed the value of "
                               +t.getTableColumn().getText()
                               +" from " +old+ " to " +t.getNewValue()
                               +" for Item ID " +t.getRowValue().item_id
                               +" at building "+temp, Context.getInstance().getData().getCurUser());
                    saveObservable(t.getTableColumn().getText(),t.getNewValue(), t.getTablePosition().getRow());
                }
                
                
            } else {
                exFun.displayEditError(t.getTableColumn().getText());
                item_table.refresh();

            }
        }

    }

    /*
        Handles the new item creation for the table.  Does error checks and finalizes the item addition 
        to the item table in the database.
    */
    @FXML
    private void handleItemAdd_action() {

        if (exFun.formCreationCheck(itemName_text.getText().trim(),itemAisle_text.getText().trim(),itemDescription_text.getText().trim())) {
            if (!exFun.isItemUsed(tableName, itemName_text.getText())) {
                createItemDB();
                itemlist.add(exFun.addToList(tableName, itemName_text.getText()));
                observable.add(exFun.addToList(tableName, itemName_text.getText()));
                itemName_text.clear();
                itemAisle_text.clear();
                itemDescription_text.clear();
                characterCount.setText("Character Count: " + (255 - itemDescription_text.getLength()));

            }
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        characterCount.setText("Character Count: 255");
        itemDescription_text.setTextFormatter(new TextFormatter<>(change
                -> change.getControlNewText().length() <= 255 ? change : null));

        DBConnector conDB = new DBConnector();
        itemlist = conDB.getItems(tableName);
        observable = FXCollections.observableArrayList(itemlist);
        setcolProperties();
        item_table.setItems(observable);
    }


    /*
        Sets the TableView column properties so the UI table can be populated with
        data.
    */
    private void setcolProperties() {

        itemID_col.setCellValueFactory(new PropertyValueFactory<>("item_id"));
        itemName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockQuantity_col.setCellValueFactory(new PropertyValueFactory<>("stock_quantity"));
        aisle_col.setCellValueFactory(new PropertyValueFactory<>("aisle"));
        totalShipped_col.setCellValueFactory(new PropertyValueFactory<>("total_shipped"));
        restockQuantity_col.setCellValueFactory(new PropertyValueFactory<>("restock_quantity"));
        restockTracking_col.setCellValueFactory(new PropertyValueFactory<>("restock_tracking"));
        restockDate_col.setCellValueFactory(new PropertyValueFactory<>("restock_date"));
        itemDescription_col.setCellValueFactory(new PropertyValueFactory<>("item_description"));

        setPermissionAreas();

    }


    /*
        MySQL insert of an new item to the item inventory.
    */
    private void createItemDB() {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");
            Statement myStmt = con.createStatement();

            String stmtString = ("INSERT INTO "
                    + tableName
                    + "(ItemName, Aisle, Item_Description) VALUES ('"
                    + itemName_text.getText().trim() + "', '"
                    + itemAisle_text.getText().trim() + "', '"
                    + itemDescription_text.getText().trim() + "');");

            myStmt.executeUpdate(stmtString);

            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }

    

    /*
        Set areas in the UI that users with higher permissions can use.
    */
    private void setPermissionAreas() {

        if (Context.getInstance().getData().getCurPerm().equals("NNNY")) {

            itemName_text.setDisable(true);
            itemAisle_text.setDisable(true);
            itemDescription_text.setDisable(true);
            ItemSubmit.setDisable(true);

        } else {

            stockQuantity_col.setCellFactory(TextFieldTableCell.forTableColumn());
            aisle_col.setCellFactory(TextFieldTableCell.forTableColumn());
            totalShipped_col.setCellFactory(TextFieldTableCell.forTableColumn());
            restockQuantity_col.setCellFactory(TextFieldTableCell.forTableColumn());
            restockTracking_col.setCellFactory(TextFieldTableCell.forTableColumn());
            restockDate_col.setCellFactory(TextFieldTableCell.forTableColumn());
            itemDescription_col.setCellFactory(TextFieldTableCell.forTableColumn());

            item_table.setRowFactory(new Callback<TableView<Items>, TableRow<Items>>() {
                @Override
                public TableRow<Items> call(TableView<Items> tableView) {
                    final TableRow<Items> row = new TableRow<>();
                    final ContextMenu contextMenu = new ContextMenu();
                    final MenuItem deleteRow = new MenuItem("Delete");
                    deleteRow.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            item_table.getItems().remove(row.getItem());
                        }
                    });
                    contextMenu.getItems().add(deleteRow);
                    row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                    return row;
                }
            });
        }
    }
    
    /*
        Method is used to save any edits to the observable list.
    */
    private void saveObservable(String colName, String newValue, int index){
        
        
        switch (colName) {

            case "Stock":
                observable.get(index).stock_quantity = newValue;
                break;

            case "Aisle":
                observable.get(index).aisle = newValue;
                break;

            case "Total Shipped":
                observable.get(index).total_shipped = newValue;
                break;

            case "Restock Quantity":
                observable.get(index).restock_quantity = newValue;
                break;

            case "Restock Tracking #":
                observable.get(index).restock_tracking = newValue;
                break;

            case "Restock ETA":
                observable.get(index).restock_date = newValue;
                break;

            case "Description":
                observable.get(index).item_description = newValue;
                break;
        }
    }

}
