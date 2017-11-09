/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  Users.java
 * Created on Sep 23, 2017, 2:58:36 PM
 * 
 */
package sharedFunctions;

/**
 *
 * @author Tim
 */
public class Users {
    
    public String firstname;
    public String lastname;
    public String username;
    public String permissions;
    public String created;
    public String lastlog;
    public String curUser;
    
    public Users(){
        this.firstname = "";
        this.lastname = "";
        this.username = "";
        this.permissions = "";
        this.created = "";
        this.lastlog = "";
                
    }
    
    public String getFirstname(){
        return firstname;
    }
    
    public String getLastname(){
        return lastname;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPermissions(){
        return permissions;
    }
    
    public String getCreated(){
        return created;
    }
    
    public String getLastlog(){
        return lastlog;
    }
    
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    
    public void setLastname(String lastname){
        this.firstname = lastname;
    }
    
    public void setUsername(String username){
        this.firstname = username;
    }
    
    public void setPermissions(String permissions){
        this.firstname = permissions;
    }
    
    public void setCreated(String created){
        this.firstname = created;
    }
    
    public void setLastlog(String lastlog){
        this.firstname = lastlog;
    }
    
}
