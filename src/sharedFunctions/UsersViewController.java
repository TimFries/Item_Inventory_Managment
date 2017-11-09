/*
 * Name:  Tim Fries
 * Assignment:  Inv_Manager
 * 
 * File:  UsersViewController.java
 * Created on Sep 23, 2017, 11:19:28 AM
 * 
 */
package sharedFunctions;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class UsersViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private ArrayList<Users> userlist = new ArrayList<>();
    private ObservableList<Users> observable;

    @FXML
    private TableView<Users> userList;

    @FXML
    private TableColumn<Users, String> first_col, last_col, user_col, perm_col, created_col, lastlog_col;

    

    /*
        Action handles the edit of user permissions.
    */
    @FXML
    private void handlePermEdit_action(CellEditEvent<Users, String> t) {

        
        String oldtemp = t.getOldValue();
        observable.get(t.getTablePosition().getRow()).permissions = t.getNewValue();
        
        DBConnector conDB =  new DBConnector();
        conDB.updateUsers(observable.get(t.getTablePosition().getRow()));
        LoggerConnector logCon = new LoggerConnector();
        logCon.log((Context.getInstance().getData().getCurUser()+ " changed the permissions of "+observable.get(t.getTablePosition().getRow()).username
                    +" from "+oldtemp
                    +" to "+t.getNewValue()+ ""), Context.getInstance().getData().getCurUser());
        
    }

    /*
        Sets the TableView column properties so the UI table can be populated with
        data.
    */
    private void setcolProperties() {

        allShared sharedFunctions = new allShared();
        first_col.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        last_col.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        user_col.setCellValueFactory(new PropertyValueFactory<>("username"));
        perm_col.setCellValueFactory(new PropertyValueFactory<>("permissions"));

        
        perm_col.setCellFactory(ComboBoxTableCell.forTableColumn(sharedFunctions.getEditPermList(Context.getInstance().getData().getCurPerm())));
        created_col.setCellValueFactory(new PropertyValueFactory<>("created"));
        lastlog_col.setCellValueFactory(new PropertyValueFactory<>("lastlog"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        DBConnector conDB = new DBConnector();
        userlist = conDB.getUsers(Context.getInstance().getData().getCurUser(), Context.getInstance().getData().getCurPerm());
        observable = FXCollections.observableArrayList(userlist);
        setcolProperties();
        userList.setItems(observable);

    }
    
    

}
