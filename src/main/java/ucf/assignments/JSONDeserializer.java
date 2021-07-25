/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Abdel Nabut
 */

package ucf.assignments;

import com.google.gson.*;
import java.lang.reflect.Type;

public class JSONDeserializer implements JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        // use FileReader to read the items in the .json file
        // parse each line of data into a new Item object
        // add the new Item object to the items ObservableArrayList
        // return the ObservableArrayList
        JsonObject jsonObject = json.getAsJsonObject();
        String name = "", value = "", serialNumber = "";
        if (jsonObject.get("name") != null) {
            name = jsonObject.get("name").getAsString();
        }
        if (jsonObject.get("value") != null) {
            value = jsonObject.get("value").getAsString();
        }
        if (jsonObject.get("serialNumber") != null) {
            serialNumber = jsonObject.get("serialNumber").getAsString();
        }
        return new Item(name,
                value,
                serialNumber);
    }
}