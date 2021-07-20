package ucf.assignments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;


public class InventoryTrackerController implements Initializable {

    static ObservableList<Item> items = FXCollections.observableArrayList();
    FilteredList<Item> filteredList = new FilteredList<>(items, I -> true);

    @FXML public TableView<Item> table;
    @FXML private TableColumn<Item, String> valueColumn;
    @FXML private TableColumn<Item, String> serialNumberColumn;
    @FXML private TableColumn<Item, String> nameColumn;

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
        Item item = table.getSelectionModel().getSelectedItem();
        //table.getItems().removeAll(item);
        removeItem(item);
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

    private Boolean itemExists(String name) {
        if(items.size() == 0)
            return false;
        for(Item item: items) {
            if(item.getName().equals(name))
                return true;
            if(item.getSerialNumber().equals(name))
                return true;
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    private Item addItem() {
        // instantiates new Item
        // adds item to items ObservableArray list.
        String name = nameTextField.getText();
        String serialNumber = serialNumberTextField.getText();
        String value = valueTextField.getText();
        if(itemExists(name)) {
            showAlert("An item already exists with this name.");
            return null;
        }
        if(name.length() < 2 || name.length() > 256) {
            showAlert("Your item must be between 2 and 256 characters.");
            return null;
        }
        if(itemExists(serialNumber)) {
            showAlert("An item already exists with this serial number.");
            return null;
        }
        if(name.length() <= 0 || serialNumber.length() <= 0 || value.length() <=0)
        {
            showAlert("Please fill out all fields to add item to the inventory.");
            return null;
        }
        if(value.indexOf("$") == 0) {
            value = value.replace("$", "");
        }
        try {
            BigDecimal bigDecimalValue = BigDecimal.valueOf(Double.parseDouble(value)).setScale(2, RoundingMode.HALF_UP);
            String currencyValue = bigDecimalValue.toString().substring(0, 0) + "$" + bigDecimalValue;
            Item item = new Item(name, currencyValue, serialNumber);
            items.add(item);
            return item;
        } catch(NumberFormatException e) {
            showAlert("You must enter a valid number into the value field.");
            return null;
        }

    }

    private void loadList() {
        // reads items in file
        // saves items to an ObservableArray list.
    }

    private ObservableList<Item> removeItem(Item item) {
        // deletes item from the list
        // returns the modified list.
        items.remove(item);
        if(items.size()>0)
            return items;
        return null;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setName(event.getNewValue());
        });
        nameColumn.setEditable(true);

        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        valueColumn.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            BigDecimal newValue = BigDecimal.valueOf(Double.parseDouble(event.getNewValue()))
                    .setScale(3, RoundingMode.HALF_UP);
            item.setValue(newValue.toString());
        });
        valueColumn.setEditable(true);

        serialNumberColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        serialNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        serialNumberColumn.setOnEditCommit(event -> {
            Item item = event.getRowValue();
            item.setSerialNumber(event.getNewValue());
        });
        serialNumberColumn.setEditable(true);

        //sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(filteredList);

    }
}
