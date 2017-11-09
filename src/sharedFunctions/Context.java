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

/*
    Class is used to share current login username, permissions and root BorderPane
    reference so they don't need to be passed from one controller to another.
*/
public class Context {
    
    private final static Context instance = new Context();

    
    private final SharedData data = new SharedData();
    
    public static Context getInstance(){
        return instance;
    }
    
    public SharedData getData(){
        return data;
    }
    
    
}
