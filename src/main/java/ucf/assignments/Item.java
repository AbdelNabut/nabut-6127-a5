/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Abdel Nabut
 */

package ucf.assignments;

public class Item {
    private String value;
    private String serialNumber;
    private String name;

    public Item() {

    }


    public Item(String name, String value, String serialNumber) {
        this.name = name;
        this.value = value;
        this.serialNumber = serialNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
