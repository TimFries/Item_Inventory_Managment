/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class LogsViewController implements Initializable {

    
    private ArrayList<Logs> loglist = new ArrayList<>();
    
    @FXML
    private TableColumn<Logs, String> Username_col, Time_col, Log_col;
    @FXML
    private TableView<Logs> Log_Table;
    
    
    /*
        Set TableView column properties so the table can be populated with data.
    */
    private void setcolProperties(){
        
       Username_col.setCellValueFactory(new PropertyValueFactory<>("username"));
       Time_col.setCellValueFactory(new PropertyValueFactory<>("time"));
       Log_col.setCellValueFactory(new PropertyValueFactory<>("log"));

    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        DBConnector conDB = new DBConnector();
        loglist = conDB.getLogs();
        ObservableList<Logs> observable = FXCollections.observableArrayList(loglist);
        setcolProperties();
        Log_Table.setItems(observable);
    }    
    
}
