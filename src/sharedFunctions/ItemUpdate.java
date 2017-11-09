/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedFunctions;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;

/**
 *
 * @author Tim
 */
public class ItemUpdate {

    /*
        Method gets the database column name that will be used from the String
        of the TableView column name that had an edit.
    */
    public String getItemColumnName(String name) {
        String colName = "";

        switch (name) {

            case "Stock":
                colName = "Stock_Quantity";
                break;

            case "Aisle":
                colName = name;
                break;

            case "Total Shipped":
                colName = "Total_Shipped";
                break;

            case "Restock Quantity":
                colName = "Restock_Quantity";
                break;

            case "Restock Tracking #":
                colName = "Restock_Tracking_Number";
                break;

            case "Restock ETA":
                colName = "Restock_Date";
                break;

            case "Description":
                colName = "Item_Description";
                break;
        }

        return colName;
    }

    /*
        Method updates the building inventory with new values.
    */
    public boolean itemUpdateDB(String tableName, String itemCol, String id, String newValue) {

        boolean pass = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");

            Statement myStmt = con.createStatement();

           myStmt.execute("UPDATE "
                    + tableName
                    + " SET " + itemCol + " = '" + newValue
                    + "' WHERE Item_ID = " + id + ";");

            pass = true;

            myStmt.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pass;

    }

}
