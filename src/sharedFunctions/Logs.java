/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharedFunctions;

/**
 *
 * @author Tim
 */
public class Logs {
    
    public String username;
    public String time;
    public String log;

    public Logs(){
        this.username = "";
        this.time = "";
        this.log = "";

                
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getTime(){
        return time;
    }
    
    public String getLog(){
        return log;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setTime(String time){
        this.time = time;
    }
    
    public void setLog(String log){
        this.log = log;
    }
    
}
