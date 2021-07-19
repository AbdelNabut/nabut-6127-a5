package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTrackerControllerTest {

    @Test
    void addItem() {
        // instantiate new InventoryTrackerController
        // call the addItem function
        // assert that addItem returns a size of 1.
    }

    @Test
    void loadList() {
        // instantiate new ObservableArrayList
        // add a few items to the list
        // instantiate new InventoryTrackerController
        // call the loadList function on an externally saved list with the same items as the previously added.
        // assert that loadList equals the recently created ObservableArrayList
    }

    @Test
    void removeItem() {
        // instantiate new InventoryTrackerController
        // add a few items to the items ObservableArrayList
        // copy the item to be removed into a new Item
        // call the remoteItem function on the item to be removed
        // assert that the item is no longer in the ObservableArrayList
    }

    @Test
    void saveList() {
        // instantiate new InventoryTrackerController
        // add the following items to the items ObservableArrayList
        // name: cereal, value: $3.00, serialNumber: AAAAAAAAAA
        // name: chipwiches, value: $4.00, serialNumber: BBBBBBBBBB
        // name: bread, value: $2.00, serialNumber: CCCCCCCCCC
        // call the saveList function
        // check if asserting saveList equals the test file holding the aforementioned items.

    }

    @Test
    void searchByName() {
        // instantiate new InventoryTrackerController
        // add the following items to the items ObservableArrayList
        // name: cereal, value: $3.00, serialNumber: AAAAAAAAAA
        // name: chipwiches, value: $4.00, serialNumber: BBBBBBBBBB
        // name: bread, value: $2.00, serialNumber: CCCCCCCCCC
        // call searchByName on "cereal".
        // assert that calling the function returns the Item with the name "cereal".
    }

    @Test
    void searchBySerialNumber() {
        // instantiate new InventoryTrackerController
        // add the following items to the items ObservableArrayList
        // name: cereal, value: $3.00, serialNumber: AAAAAAAAAA
        // name: chipwiches, value: $4.00, serialNumber: BBBBBBBBBB
        // name: bread, value: $2.00, serialNumber: CCCCCCCCCC
        // call searchBySerialNumber on "AAAAAAAAAA".
        // assert that calling the function returns the Item with the serial number "AAAAAAAAAA".
    }

    @Test
    void searchByValue() {
        // instantiate new InventoryTrackerController
        // add the following items to the items ObservableArrayList
        // name: cereal, value: $3.00, serialNumber: AAAAAAAAAA
        // name: chipwiches, value: $4.00, serialNumber: BBBBBBBBBB
        // name: bread, value: $2.00, serialNumber: CCCCCCCCCC
        // call searchByValue on "3.00".
        // assert that calling the function returns the Item with the value "3.00".
    }
}