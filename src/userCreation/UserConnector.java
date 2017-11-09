/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  UserCreationConnector.java
 * Created on Sep 3, 2017, 9:28:59 AM
 * 
 */
package userCreation;

import java.sql.*;

/**
 *
 * @author Tim
 */
public class UserConnector {
    
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String permissions;
    public boolean Server_Error = false;
    
    public void setUserCreationData(String first, String last, String user, String pass){
        firstname = first;
        lastname = last;
        username = user;
        password = pass;
       
    }
    
    public void setUserLoginData(String user, String pass){
        username = user;
        password = pass;
    }
    
    
    
    private void setPermissions(String userPerm){
        permissions = userPerm;
    }
    public String getPermissions(){
        return permissions;
    }
    public String getUsername(){
        return username;
    }
    
    
    
    
    public boolean UserCheckConnector(){
        
        boolean pass = false;
        Server_Error = false;
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "UserCreator", "password");
            
            Statement myStmt = con.createStatement();
            
            ResultSet rs = myStmt.executeQuery("SELECT * FROM app_users WHERE BINARY UserName= '" + username + "';");
            
            while(rs.next()){
                if(rs.getString("UserName") !=null){
                    pass = true;
                    
                }
                
            }
            
            rs.close();
            myStmt.close();
            con.close();
        }
        catch (SQLException exc){
            System.out.println(exc);
            Server_Error = true;
            pass = true;
            
        }
        return pass;
    }
    
    public void UserCreationConnector(){
        
        Server_Error = false;
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "UserCreator", "password");
            
            Statement myStmt = con.createStatement();
            
            myStmt.executeUpdate("INSERT INTO app_users (UserName, FirstName, LastName, Pass) VALUES ('"
                                                                       +username+"', '"
                                                                       +firstname+"', '"
                                                                       +lastname+"', MD5('"
                                                                       +password+"'));");
                                                                       
            
            
            myStmt.close();
            con.close();
        }
        catch (SQLException exc){
            System.out.println(exc);
        }
        
    }
    
    public boolean LoginConnector(){
        
        boolean pass = false;
        Server_Error = false;
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "app_control", "password");
            
            Statement myStmt = con.createStatement();
            
            ResultSet rs = myStmt.executeQuery("SELECT * FROM app_users WHERE BINARY UserName= '" + username + "' AND Pass=MD5('"+password+"');");
            
            while(rs.next()){
                if(rs.getString("Permissions") !=null){
                    pass = true;
                    setPermissions(rs.getString("Permissions"));
                }
                
            }
            if(pass){
            myStmt.executeUpdate("UPDATE app_users SET LastLogin=NOW() WHERE BINARY UserName= '" + username + "' AND Pass=MD5('"+password+"');");
            }
            con.close();
            myStmt.close();
            rs.close();
            
        }
        catch (SQLException exc){
            System.out.println(exc);
            Server_Error = true;
            
        }
        
        
       
        return pass;
    }
}
