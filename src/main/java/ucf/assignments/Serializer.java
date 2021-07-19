package ucf.assignments;

import com.google.gson.Gson;

import java.io.File;

public class Serializer {
    File serializeTSV() {
        // loop through items in list
        // write attributes of item to line separated by tab
        // return File
        return new File("testTSV.txt");

    }
    File serializeHTML() {
        // loop through items in list
        // write attributes of item to line separated by the proper html tags
        // return File
        return new File("test.html");
    }
    Gson serializeJSON() {
        // use GSON to convert ArrayList into GSON json format
        // output GSON to new File
        // return File
        return new Gson();
    }
}
