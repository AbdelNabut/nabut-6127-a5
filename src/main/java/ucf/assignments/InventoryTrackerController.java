package ucf.assignments;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.*;


public class InventoryTrackerController {

    @FXML
    private TextField valueTextField;

    @FXML
    private TextField serialNumberTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    void AddButtonClicked(ActionEvent event) {
        addItem();

    }

    @FXML
    void loadButtonClicked(ActionEvent event) {
        loadList();

    }

    @FXML
    void removeButtonClicked(ActionEvent event) {
        removeItem();

    }

    @FXML
    void saveButtonClicked(ActionEvent event) {
        saveList();
    }

    @FXML
    void searchByNameButtonClicked(ActionEvent event) {
        searchByName();
    }

    @FXML
    void searchBySerialNumberButtonClicked(ActionEvent event) {
        searchBySerialNumber();
    }

    @FXML
    void searchByValueButtonClicked(ActionEvent event) {
        searchByValue();

    }

    private Item addItem() {
        // instantiates new Item
        // adds item to items ObservableArray list.
        return new Item();
    }

    private void loadList() {
        // reads items in file
        // saves items to an ObservableArray list.
    }

    private ArrayList<Item> removeItem() {
        // deletes item from the list
        // returns the modified list.
        return new ArrayList<>();
    }

    private File saveList() {
        // ask user which file format they would like to save the list to.
        // serialize File into said format
        // output serialized data into user-specified directory with user-specified name.
        return new File("test");
    }

    private Item searchByName() {
        // filter items in list by name
        // return item(s)
        return new Item();
    }

    private Item searchBySerialNumber() {
        // filter items in list by serial number
        // return item(s)
        return new Item();
    }

    private Item searchByValue() {
        // filter items in list by value
        // return item(s)
        return new Item();
    }

}
