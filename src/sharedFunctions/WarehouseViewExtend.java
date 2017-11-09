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
import java.util.ArrayList;
import javafx.scene.control.Alert;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Tim
 */
public class WarehouseViewExtend {
    
    /*
        Function gets the number of item inventory tables and their names
        and returns a ArrayList of Strings.
    */
    public ArrayList<String> getTableCount() {
        ArrayList<String> templist = new ArrayList<String>();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");
            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("Show tables;");

            while (rs.next()) {
                String tempString = new String();
                
                tempString = rs.getString("Tables_in_app_tables");
                templist.add(tempString);

            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }

        return templist;
    }
    
    /*
        Boolean function to check if the name of a table is currently in use.
        This function is called during the table creation error checks.
    */
    public boolean nameFound(String namePattern) {

        boolean found = false;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");
            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("Show tables;");

            while (rs.next()) {
                if (rs.getString("Tables_in_app_tables").equals(namePattern)) {
                    found = true;
                    break;
                }

            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
        return found;
    }
    
    /*
        Function creates the table name that will be used on the database
        for the table.  This function is called during the table creation.
    */
    public String createTableName(String name, String type) {
        String tempname = "";

        tempname = (type + " ");
        tempname = tempname.concat(name);
        tempname = tempname.toLowerCase();
        tempname = tempname.replace(' ', '_');

        return tempname;
    }
    
    /*
        MySQL query used to create a new item inventory table on the database.
    */
    public void createTableDB(String name) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");
            Statement myStmt = con.createStatement();
            LoggerConnector logcon = new LoggerConnector();
            String stmtString = ("CREATE TABLE " + name
                    + "(Item_ID int NOT NULL AUTO_INCREMENT, "
                    + "ItemName varchar(32) DEFAULT NULL, "
                    + "Stock_Quantity int DEFAULT 0, "
                    + "Aisle varchar(4) DEFAULT 'A0', "
                    + "Total_Shipped int DEFAULT 0, "
                    + "Restock_Quantity int DEFAULT 0, "
                    + "Restock_Tracking_Number varchar(32) DEFAULT null,"
                    + "Restock_Date DATE, "
                    + "Item_Description TINYTEXT, "
                    + "PRIMARY KEY(Item_ID));");

            myStmt.executeUpdate(stmtString);
            myStmt.executeUpdate(("ALTER TABLE " + name + " AUTO_INCREMENT=100"));
            name = name.replace('_', ' ');
            name = WordUtils.capitalizeFully(name);
            
            logcon.log(Context.getInstance().getData().getCurUser()
                    + " created the inventory table "
                    +name, Context.getInstance().getData().getCurUser());
            
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(exc);
        }
    }
    
    /*
        Boolean function that will return false if an error is found
        in the table creation form.  If an error is found an alert box will be
        displayed telling the user what the error/errors are.
    */
    public boolean formCreationCheck(Object type, Boolean nameEmpty) {
        String errormsg = "";
        boolean noError = true;
        
        if (type == null) {
            errormsg = errormsg.concat("No 'Building Type' was selected.\n");
            noError = false;
        }
        if (nameEmpty) {
            errormsg = errormsg.concat("No 'Building Name' was given.\n");
            noError = false;
        }
        if (!noError) {
            Alert errorFound = new Alert(Alert.AlertType.ERROR);
            errorFound.setTitle("Table Creation Error");
            errorFound.setContentText(errormsg);
            errorFound.showAndWait();
        }
        return noError;
    }
    
    /*
        Function that handles the checking and creation of the table name.
        1. Creates the table name that will be used to check if the name is
        in use on the database.
    */
    public boolean tableNameAvaliable(String name, String type) {
        boolean noError = true;
        String temp = createTableName(name, type);
        if (nameFound(temp)) {
            Alert errorFound = new Alert(Alert.AlertType.ERROR);
            errorFound.setTitle("Table Name Error");
            errorFound.setContentText("Table name '" + name + "' is being used.");
            errorFound.showAndWait();
            noError = false;
        }

        return noError;
    }
    
}
