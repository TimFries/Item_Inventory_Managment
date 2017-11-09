/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Alert;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Tim
 */
public class TableViewExtend {

    /*
        Displays error message for cell edits for certain columns.
    */
    public void displayEditError(String column) {

        Alert errorFound = new Alert(Alert.AlertType.ERROR);
        errorFound.setTitle("Edit Error");
        switch (column) {

            case "Stock":
            case "Total Shipped":
            case "Restock Quantity":
                errorFound.setContentText("This column only uses numbers");

                break;
            case "Aisle":
                errorFound.setContentText("This column uses the pattern\n'Uppercase Letter followed by a maximum of 3 numbers'\nEx. A12");
                break;
            case "Restock Tracking #":
                errorFound.setContentText("This column must be 32 characters long and only uses alphanumeric characters");
                break;
            case "Restock ETA":
                errorFound.setContentText("This column uses the date pattern yyyy-mm-dd");
                break;
            case "Description":
                errorFound.setContentText("This column can be no more than 255 characters long");
                break;

        }

    }

    /*
        Converts the table name that is used on the database to a more readable one
        so it can be used in logging user edits.
    */
    public String convertTableNameDisplay(String name) {

        String temp = name;
        temp = temp.replace('_', ' ');
        temp = WordUtils.capitalizeFully(temp);

        return temp;
    }
    
    /*
        Method is used to get new Item information that the user added to the database table
        so it can be displayed in the Table in the UI.
    
    */
    public Items addToList(String tableName, String itemName){
        
        Items tempitem = new Items();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");

            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("SELECT * FROM " + tableName + " WHERE ItemName= '" + itemName + "';");

            while (rs.next()) {

                tempitem.item_id = rs.getString("Item_ID");
                tempitem.name = rs.getString("ItemName");
                tempitem.stock_quantity = rs.getString("Stock_Quantity");
                tempitem.aisle = rs.getString("Aisle");
                tempitem.total_shipped = rs.getString("Total_Shipped");
                tempitem.restock_quantity = rs.getString("Restock_Quantity");
                tempitem.restock_tracking = rs.getString("Restock_Tracking_Number");
                tempitem.restock_date = rs.getString("Restock_Date");
                tempitem.item_description = rs.getString("Item_Description");

            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return tempitem;
    }
    
    /*
        Checks to see if the Item name is in use on the table.  This is called when
        the user is trying to add a new item to the table.
    */
    public boolean isItemUsed(String tableName, String itemName) {
        boolean errorFound = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");

            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("SELECT * FROM " + tableName + " WHERE BINARY ItemName = '" + itemName + "';");

            while (rs.next()) {
                if (rs.getString("ItemName") != null) {
                    errorFound = true;
                }

            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);

        }

        return errorFound;
    }
    
    /*
        Basic form check, this is called the user is trying to create a new item.
    */
    public boolean formCreationCheck(String name, String aisle, String descrip){
        
        String errormsg = "";
        Pattern editPattern = Pattern.compile("^[A-Z]{1}[0-9]{1,3}$");
        Matcher matcher = editPattern.matcher(aisle);
        boolean noError = true;
        if (name.isEmpty()) {
            errormsg = errormsg.concat("No 'Item Name' was given.\n");
            noError = false;
        }
        if (aisle.isEmpty() || !matcher.matches()) {
            if (aisle.isEmpty()) {
                errormsg = errormsg.concat("No 'Aisle' was given.\n");
            } else {
                errormsg = errormsg.concat("Aisle uses the pattern\n'Uppercase Letter followed by a maximum of 3 numbers'\nEx. A12\n");
            }
            noError = false;
        }
        if (descrip.isEmpty()) {
            errormsg = errormsg.concat("No 'Item Description' was given.\n");
            noError = false;
        }
        if (!noError) {
            Alert errorFound = new Alert(Alert.AlertType.ERROR);
            errorFound.setTitle("Item Creation Error");
            errorFound.setContentText(errormsg);
            errorFound.showAndWait();
        }
        return noError;
    }

}
