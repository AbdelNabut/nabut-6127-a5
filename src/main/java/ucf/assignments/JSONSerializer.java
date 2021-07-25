/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Abdel Nabut
 */

package ucf.assignments;

import com.google.gson.*;
import java.lang.reflect.Type;

public class JSONSerializer implements JsonSerializer<Item> {
    public JsonElement serialize(final Item item, final Type type, final JsonSerializationContext context) {
        // instantiate a new JsonObject
        // add all the values to the GSON object
        // return the JSON object.
        JsonObject result = new JsonObject();
        result.add("name", new JsonPrimitive(item.getName()));
        result.add("value", new JsonPrimitive(item.getValue()));
        result.add("serialNumber", new JsonPrimitive(item.getSerialNumber()));
        return result;
    }
}
