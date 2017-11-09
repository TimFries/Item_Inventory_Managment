/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  LoggerConnector.java
 * Created on Oct 12, 2017, 10:03:38 AM
 * 
 */
package sharedFunctions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Tim
 */
public class LoggerConnector {
    
    
    /*
        Method is used to insert (LOG) user activates to the log table on the database
    */
    public void log(String log, String username){

        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logs", "app_control", "password");
            Statement myStmt = con.createStatement();
            
             myStmt.executeUpdate("INSERT INTO inv_managerLogs (UserName, Log) VALUES ('"
                                                                       +username+"', '"
                                                                       +log+"');");
             con.close();
             myStmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
