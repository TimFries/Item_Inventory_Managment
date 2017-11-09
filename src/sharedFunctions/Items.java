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
public class Items {

    public String item_id;
    public String name;
    public String stock_quantity;
    public String aisle;
    public String total_shipped;
    public String restock_quantity;
    public String restock_tracking;
    public String restock_date;
    public String item_description;

    public Items() {
        this.item_id = "";
        this.name = "";
        this.stock_quantity = "";
        this.aisle = "";
        this.total_shipped = "";
        this.restock_quantity = "";
        this.restock_tracking = "";
        this.restock_date = "";
        this.item_description = "";

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String getTotal_shipped() {
        return total_shipped;
    }

    public void setTotal_shipped(String total_shipped) {
        this.total_shipped = total_shipped;
    }

    public String getRestock_quantity() {
        return restock_quantity;
    }

    public void setRestock_quantity(String restock_quantity) {
        this.restock_quantity = restock_quantity;
    }

    public String getRestock_tracking() {
        return restock_tracking;
    }

    public void setRestock_tracking(String restock_tracking) {
        this.restock_tracking = restock_tracking;
    }

    public String getRestock_date() {
        return restock_date;
    }

    public void setRestock_date(String restock_date) {
        this.restock_date = restock_date;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    
    
    
}
