/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Abdel Nabut
 */

package ucf.assignments;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jsoup.Jsoup;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;


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
        String name = nameTextField.getText();
        String serialNumber = serialNumberTextField.getText().toUpperCase(Locale.ROOT);
        String value = valueTextField.getText();
        addItem(name, serialNumber, value);
    }

    @FXML
    void loadButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select output destination");
        File file = fileChooser.showOpenDialog(new Stage());
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
        if (file != null) {
            loadList(file, fileExtension);
            if(items != null)
                this.table.setItems(items);
        }

    }

    @FXML
    void removeButtonClicked(ActionEvent event) {
        Item item = table.getSelectionModel().getSelectedItem();
        //table.getItems().removeAll(item);
        removeItem(item);
    }

    @FXML
    void saveButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select output destination");
        FileChooser.ExtensionFilter txtExtension =
                new FileChooser.ExtensionFilter("TSV files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter htmlExtension =
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
        FileChooser.ExtensionFilter jsonExtension =
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().addAll(txtExtension, htmlExtension, jsonExtension);
        File file = fileChooser.showSaveDialog(new Stage());
        String fileName = file.getName();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
        saveList(file, fileExtension);
    }

    @FXML
    void searchByNameButtonClicked(ActionEvent event) {
        TextInputDialog td = new TextInputDialog("Product name");
        // setHeaderText
        td.setHeaderText("Product Name");
        td.showAndWait();
        searchByName(td.getEditor().getText());
    }

    @FXML
    void searchBySerialNumberButtonClicked(ActionEvent event) {
        TextInputDialog td = new TextInputDialog("Product serial number");
        // setHeaderText
        td.setHeaderText("Product Serial Number");
        td.showAndWait();
        searchBySerialNumber(td.getEditor().getText());
    }

    @FXML
    void searchByValueButtonClicked(ActionEvent event) {
        TextInputDialog td = new TextInputDialog("Product value");
        // setHeaderText
        td.setHeaderText("Product Value");
        td.showAndWait();
        searchByValue(td.getEditor().getText());
    }

    public Boolean itemExists(String serialNumber) {
        // loop through the observables array
        // check if the array has an item with a serial number matching the one given.
        // return the boolean value.
        if(items.size() == 0)
            return false;
        for(Item item: items) {
            if(item.getSerialNumber().equals(serialNumber))
                return true;
        }
        return false;
    }

    private void showAlert(String message) {
        // instantiate a new alert with the given message
        // show the alert and wait until alert has been closed before resuming program
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    public ObservableList<Item> addItem(String name, String serialNumber, String value) {
        // instantiates new Item
        // check if item matches all requirements.
        // if so: adds item to items ObservableArray list and return the item.
        // otherwise: open an error alert and return null
        if(name.length() < 2 || name.length() > 256) {
            showAlert("Your item must be between 2 and 256 characters.");
            return null;
        }
        if(itemExists(serialNumber)) {
            showAlert("An item already exists with this serial number.");
            return null;
        }
        if(!serialNumber.matches("[a-zA-Z0-9]*")) {
            showAlert("The serial number may only be composed of digits and letters.");
            return null;
        }
        if(serialNumber.length() != 10) {
            showAlert("The serial number must contain a total of 10 characters.");
            return null;
        }
        if(name.length() <= 0 || serialNumber.length() <= 0 || value.length() <=0)
        {
            showAlert("Please fill out all fields to add item to the inventory.");
            return null;
        }
        String parsedCurrency = parseCurrency(value);
        if(parsedCurrency != null) {
            Item item = new Item(name, parsedCurrency, serialNumber);
            items.add(item);
            return items;
        }
        else
            showAlert("You must enter a valid number into the value field.");
    return null;
    }

    public String parseCurrency(String value) {
        try {
            if(value.indexOf("$") == 0) {
                value = value.replace("$", "");
            }
            BigDecimal bigDecimalValue = BigDecimal.valueOf(Double.parseDouble(value)).setScale(2, RoundingMode.HALF_UP);
            String currencyValue = bigDecimalValue.toString().substring(0, 0) + "$" + bigDecimalValue;
            return currencyValue;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public ObservableList<Item> loadList(File file, String fileExtension) {
        // reads items in file
        // saves items to an ObservableArray list.
        if(fileExtension.equals("json"))
            items = loadJSON(file);
        if(fileExtension.equals("html"))
            items = loadHTML(file);
        if(fileExtension.equals("txt"))
            items = loadTSV(file);
        return items;
    }

    private ObservableList<Item> loadJSON(File file) {
        // instantiate a buffered reader
        // instantiate a gson builder using the deserializer class
        // convert the file text from json into an array.
        // store the items in the observable list
        // return the file.
        try {
            Reader reader = Files.newBufferedReader(file.toPath());
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Item.class, new JSONDeserializer())
                    .create();
            List<Item> list = gson.fromJson(reader, new TypeToken<ArrayList<Item>>() {}.getType());
            items = FXCollections.observableArrayList(list);
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return items;
    }

    private ObservableList<Item> loadHTML(File file) {
        // instantiate a new ArrayList to hold the items in the HTML file
        // use Jsoup to parse the HTML file
        // loop through all the rows and instantiate new Item with values from row columns.
        // copy the new array list into the items observable arraylist
        // return the file
        ArrayList<Item> loadedItems = new ArrayList<Item>();
        try {
            org.jsoup.nodes.Document doc = Jsoup.parse(file, "UTF-8");
            org.jsoup.select.Elements rows = doc.select("tr");
            for(org.jsoup.nodes.Element row :rows)
            {
                org.jsoup.select.Elements columns = row.select("td");
                if(columns.size() == 3) {
                    Item item = new Item(columns.get(2).text(), columns.get(0).text(), columns.get(1).text());
                    loadedItems.add(item);
                }
            }
            items = FXCollections.observableArrayList(loadedItems);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return items;
    }

    private ObservableList<Item> loadTSV(File file) {
        // instantiate a new ArrayList
        // instantiate a buffered reader
        // loop through each line in the file
        // delimit the lines by tabs
        // instantiate a new item with values from the delimited line
        // return the file
        ArrayList<Item> loadedItems = new ArrayList<Item>();
        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(String.valueOf(file.toPath())));
            String line = reader.readLine();
            while(line != null) {
                String[] delimitedLine = line.split("\t");
                Item item = new Item(delimitedLine[2], delimitedLine[0], delimitedLine[1]);
                loadedItems.add(item);
                line = reader.readLine();
            }
            items = FXCollections.observableArrayList(loadedItems);
            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return items;
    }


    public ObservableList<Item> removeItem(Item item) {
        // deletes item from the list
        // returns the modified list.
        items.remove(item);
        if(items.size()>0)
            return items;
        return null;
    }

    public File saveList(File file, String fileExtension) {
        // ask user which file format they would like to save the list to.
        // serialize File into said format
        // output serialized data into user-specified directory with user-specified name.
        if(fileExtension.equals("json"))
            file = saveJSON(file);
        else if(fileExtension.equals("txt"))
            file = saveTSV(file);
        else if(fileExtension.equals("html"))
            file = saveHTML(file);
        return file;
    }

    public File saveJSON(File file) {
        // create a printing class
        // initialize GSON
        // use gson to convert array to json
        // print the json to the specified file.
        try {
            PrintWriter printWriter = new PrintWriter(file);
            com.google.gson.Gson gson = new GsonBuilder().registerTypeAdapter(Item.class, new JSONSerializer())
                    .setPrettyPrinting().create();
            String taskJson = gson.toJson(items.toArray());
            printWriter.write(taskJson);
            printWriter.close();
            return file;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File saveTSV(File file) {
        // instantiate a writing class object
        // loop through the all the items in the items observable array
        // write one item per line, with each value separated by a tab character.
        // return the file.
        try {
            PrintWriter printWriter = new PrintWriter(file);
            for(Item item: items) {
                printWriter.write(item.getValue() + "\t");
                printWriter.write(item.getSerialNumber() + "\t");
                printWriter.write(item.getName() + "\n");

            }
            printWriter.close();
            return file;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File saveHTML(File file) {
        // initialize a printWriter
        // loop through every item in items list
        // wrap item properties with table tags
        // print item to the table
        try {
            PrintWriter printWriter = new PrintWriter(file);
            String tableStyle = "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "th, td {\n" +
                    "  padding: 15px;\n" +
                    "  text-align: left;\n" +
                    "}\n" +
                    "table.center {\n" +
                    "  margin-left: auto; \n" +
                    "  margin-right: auto;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>";
            String tableBeginHTML = "<table class=\"center\">\n" +
                    "  <tr>\n" +
                    "    <th>Value</th>\n" +
                    "    <th>Serial #</th>\n" +
                    "    <th>Name</th>\n" +
                    "  </tr>\n";
            String tableEndHTML = "</table></body></html>";
            printWriter.write(tableStyle);
            printWriter.write(tableBeginHTML);
            for(Item item: items) {
                printWriter.write("<tr>");
                printWriter.write("<td>" + item.getValue() + "</td>");
                printWriter.write("<td>" + item.getSerialNumber() + "</td>");
                printWriter.write("<td>" + item.getName() + "</td>");
                printWriter.write("</tr>");

            }
            printWriter.write(tableEndHTML);
            printWriter.close();
            return file;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Item> searchByName(String productName) {
        // filter items in list by name
        // return item(s)
        ObservableList<Item> products = items.filtered(task -> task.getName().equals(productName));
        if (table != null) table.setItems(products);
        return products;
    }

    public ObservableList<Item> searchBySerialNumber(String serialNumber) {
        // filter items in list by serial number
        // return item(s)
        ObservableList<Item> products = items.filtered(task -> task.getSerialNumber().equals(serialNumber.toUpperCase(Locale.ROOT)));
        if (table != null) table.setItems(products);
        return products;
    }

    public ObservableList<Item> searchByValue(String value) {
        // filter items in list by value
        // return item(s)
        String parsedValue = parseCurrency(value);
        if(parsedValue == null) {
            showAlert("You must enter a valid number into the value field.");
            return null;
        }
        ObservableList<Item> products = items.filtered(task -> task.getValue().equals(parsedValue));
        if (table != null) table.setItems(products);
        return products;
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
            String newSerialNumber = event.getNewValue();
            if(itemExists(newSerialNumber)) {
                showAlert("This serial number already exists, please enter another.");
                table.refresh();
                return;
            }
            else
                item.setSerialNumber(event.getNewValue());
        });
        serialNumberColumn.setEditable(true);
        table.setItems(filteredList);

        UnaryOperator<TextFormatter.Change> modifyChange = change -> {
            if (!change.getText().matches("[a-zA-Z0-9]*") || change.getControlNewText().length() > 10) {
                change.setText("");
                change.setRange(
                        change.getRangeStart(),
                        change.getRangeStart()
                );
            }
            change.setText(change.getText().toUpperCase(Locale.ROOT));
            return change;
        };

        serialNumberTextField.setTextFormatter(new TextFormatter<Object>(modifyChange));

    }
}
