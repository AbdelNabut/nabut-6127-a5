/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Abdel Nabut
 */

package ucf.assignments;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTrackerControllerTest {

    @Test
    void addItem() {
        // instantiate new InventoryTrackerController
        // call the addItem function
        // assert that addItem returns a size of 1.
        InventoryTrackerController controller = new InventoryTrackerController();
        assertEquals(1, controller.addItem("milk","ASDF4FD3FF", "3.99").size());
    }

    @Test
    void loadList() {
        // instantiate new ObservableArrayList
        // add a few items to the list
        // instantiate new InventoryTrackerController
        // call the loadList function on an externally saved list with the same items as the previously added.
        // assert that loadList equals the recently created ObservableArrayList
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        InventoryTrackerController controller = new InventoryTrackerController();
        File jsonTest = new File("resources/test.json");
        File htmlTest = new File("resources/test.json");
        File tsvTest = new File("resources/test.json");
        Object[] testItems = testController.items.toArray();
        Object[] jsonItems = controller.loadList(jsonTest, "json").toArray();
        Object[] htmlItems = controller.loadList(htmlTest, "html").toArray();
        Object[] tsvItems = controller.loadList(tsvTest, "txt").toArray();
        assertArrayEquals(testItems, jsonItems);
        assertArrayEquals(testItems, htmlItems);
        assertArrayEquals(testItems, tsvItems);

    }

    @Test
    void removeItem() {
        // instantiate new InventoryTrackerController
        // add a few items to the items ObservableArrayList
        // copy the item to be removed into a new Item
        // call the remoteItem function on the item to be removed
        // assert that the item is no longer in the ObservableArrayList
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        int initialItemsSize = testController.items.size();
        testController.items.remove(itemFive);
        int newItemsSize = testController.items.size();
        assertNotEquals(newItemsSize, initialItemsSize);
    }

    @Test
    void saveList() throws IOException {
        // instantiate new InventoryTrackerController
        // add the following items to the items ObservableArrayList
        // name: cereal, value: $3.00, serialNumber: AAAAAAAAAA
        // name: chipwiches, value: $4.00, serialNumber: BBBBBBBBBB
        // name: bread, value: $2.00, serialNumber: CCCCCCCCCC
        // call the saveList function
        // check if asserting saveList equals the test file holding the aforementioned items.
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        File actualJSON = testController.saveList(new File("test.json"), "json");
        File expected = new File("src/test/resources/test.json");
        assertEquals(FileUtils.readFileToString(expected, "utf-8"),
                FileUtils.readFileToString(actualJSON, "utf-8"));

    }

    @Test
    void searchByName() {
        // instantiate new InventoryTrackerController
        // add the misc. items to the items ObservableArrayList
        // call searchByName on "eggs".
        // assert that calling the function returns the Item with the name "eggs".
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        assertEquals(itemOne.getName(), testController.searchByName("eggs").get(0).getName());
    }

    @Test
    void searchBySerialNumber() {
        // instantiate new InventoryTrackerController
        // add the misc. items to the items ObservableArrayList
        // call searchBySerialNumber on "099ABC0991".
        // assert that calling the function returns the Item with the serial number "099ABC0991".
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        assertEquals(itemTwo.getSerialNumber(), testController.searchBySerialNumber("099ABC0991").get(0).getSerialNumber());
    }

    @Test
    void searchByValue() {
        // instantiate new InventoryTrackerController
        // add the misc. items to the items ObservableArrayList
        // call searchByValue on "$4.99".
        // assert that calling the function returns the Item with the value "$4.99".
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        assertEquals(itemThree.getValue(), testController.searchByValue("$4.99").get(0).getValue());
    }

    @Test
    void itemExists() {
        // instantiate new InventoryTrackerController
        // add the misc. items to the items ObservableArrayList
        // call the itemExists function on one of the items and see if it returns true
        InventoryTrackerController testController = new InventoryTrackerController();
        Item itemOne = new Item("eggs", "$3.59", "ABSD45FR45");
        Item itemTwo = new Item("bread", "$2.15", "099ABC0991");
        Item itemThree = new Item("apples", "$4.99", "54AB32QW09");
        Item itemFour = new Item("pears", "$5.41", "MN56UH72OW");
        Item itemFive = new Item("strawberries", "$2.99", "MQ45PS55PV");
        testController.items.addAll(itemOne, itemTwo, itemThree, itemFour, itemFive);
        assertTrue(testController.itemExists("099ABC0991"));
    }

    @Test
    void parseCurrency() {
        // instantiate new InventoryTrackerController
        // assert that the currency equals "$2.99" in a variety of cases.
        InventoryTrackerController testController = new InventoryTrackerController();
        assertEquals("$2.99", testController.parseCurrency("2.99"));
        assertEquals("$2.99", testController.parseCurrency("2.989999"));
        assertEquals("$2.99", testController.parseCurrency("$2.98999"));

    }
}