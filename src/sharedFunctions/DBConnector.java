/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  DBConnector.java
 * Created on Sep 23, 2017, 11:34:34 AM
 * 
 */
package sharedFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class DBConnector {

    /*
        Function takes two strings (current username, current user's permissions)
        and gathers the other users information from the database and saves it as
        a  ArrayList of Users so it can later be used to display.
        *******NO PASSWORD IS RETRIEVED FROM THE DATABASE*********
    */
    public ArrayList<Users> getUsers(String curUser, String curPerm) {

        ArrayList<Users> templist = new ArrayList<Users>();
        String queRestrictions = getRestrictions(curPerm);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "app_control", "password");

            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("SELECT * FROM app_users WHERE BINARY UserName !='" + curUser + "' " + queRestrictions + ";");

            while (rs.next()) {
                Users tempuser = new Users();
                tempuser.firstname = rs.getString("FirstName");
                tempuser.lastname = rs.getString("LastName");
                tempuser.username = rs.getString("UserName");
                tempuser.permissions = getPermissionString(rs.getString("Permissions"));
                tempuser.created = rs.getString("Created");
                tempuser.lastlog = rs.getString("LastLogin");
                templist.add(tempuser);

            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            exc.printStackTrace();

        }

        return templist;
    }

    /*
        Method that takes a String (the table's name we are grabbing information from)
        and saves the information as an ArrayList of Items so it can be used to display
        information later.
    */
    public ArrayList<Items> getItems(String tableName) {
        ArrayList<Items> templist = new ArrayList<Items>();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_tables", "app_control", "password");

            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("SELECT * FROM " + tableName + ";");

            while (rs.next()) {
                Items tempitem = new Items();
                tempitem.item_id = rs.getString("Item_ID");
                tempitem.name = rs.getString("ItemName");
                tempitem.stock_quantity = rs.getString("Stock_Quantity");
                tempitem.aisle = rs.getString("Aisle");
                tempitem.total_shipped = rs.getString("Total_Shipped");
                tempitem.restock_quantity = rs.getString("Restock_Quantity");
                tempitem.restock_tracking = rs.getString("Restock_Tracking_Number");
                tempitem.restock_date = rs.getString("Restock_Date");
                tempitem.item_description = rs.getString("Item_Description");
                templist.add(tempitem);
            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return templist;
    }

    /*
        Method used to get Logs are user interactions.  The data is saved as an
        ArrayList of Logs so it can be displayed later.
    */
    public ArrayList<Logs> getLogs() {

        ArrayList<Logs> templist = new ArrayList<Logs>();

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logs", "app_control", "password");
            Statement myStmt = con.createStatement();

            ResultSet rs = myStmt.executeQuery("SELECT * FROM inv_managerlogs;");

            while (rs.next()) {
                Logs templog = new Logs();
                templog.username = rs.getString("UserName");
                templog.time = rs.getString("Time");
                templog.log = rs.getString("Log");
                templist.add(templog);

            }

            rs.close();
            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }

        return templist;
    }

    /*
        Generates a String to be used in getUsers.  This string is used to 
        restrict the query so users with lower permissions can't see user information
        of users that have higher permissions levels.
    */
    private String getRestrictions(String curPerm) {
        String queStm = "";

        switch (curPerm) {

            case "NYYY":
                queStm = "AND (Permissions = 'NYYY') OR (Permissions = 'NNYY') OR (Permissions = 'NNNY')";
                break;
            case "NNYY":
                queStm = "AND (Permissions = 'NNYY') OR (Permissions = 'NNNY')";
                break;
            default:
                break;
        }

        return queStm;
    }

    /*
        Method converts the permission code that is used on the database to
        a String that a user will understand.
    */
    private String getPermissionString(String code) {
        String type;
        switch (code) {
            case "YYYY":
                type = "SuperAdmin";
                break;
            case "NYYY":
                type = "Admin";
                break;
            case "NNYY":
                type = "Manager";
                break;
            case "NNNY":
            default:
                type = "NormalUser";
                break;

        }

        return type;
    }

    /*
        Method takes the new information of a username that had its permissions changed
        and updates the database with the changes.
    */
    public void updateUsers(Users newInfo) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "app_control", "password");
            Statement myStmt = con.createStatement();

            String stmtString = ("UPDATE app_users SET Permissions= '"
                    + getPermissionCode(newInfo.permissions)
                    + "' WHERE BINARY UserName= '"
                    + newInfo.username + "';");

            myStmt.executeUpdate(stmtString);

            myStmt.close();
            con.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    /*
        Method converts the permission String to the code that is used on the
        databases.
    */
    private String getPermissionCode(String type) {
        String code = "";

        switch (type) {
            case "SuperAdmin":
                code = "YYYY";
                break;
            case "Admin":
                code = "NYYY";
                break;
            case "Manager":
                code = "NNYY";
                break;
            case "NormalUser":
                code = "NNNY";
                break;

        }

        return code;
    }
}
