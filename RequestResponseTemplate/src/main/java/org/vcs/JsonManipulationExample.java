package org.vcs;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JsonManipulationExample {
  public static void main(String[] args) {
    //{
    //  "id": 100,
    //  "name": "David",
    //  "permanent": false,
    //  "role": "Manager",
    //  "phoneNumbers": [
    //    123456,
    //    987654
    //  ],
    //  "address": {
    //    "street": "BTM 1st Stage",
    //    "city": "Bangalore",
    //    "zipcode": 560100,
    //    "keyObject": {
    //      "key2": "val",
    //      "key3": "val3",
    //      "k4y": {
    //        "a": 32,
    //        "b": "c"
    //      }
    //    }
    //  }
    //}

//  DOCS: https://javadoc.io/static/com.google.code.gson/gson/2.10.1/com.google.gson/com/google/gson/package-summary.html
    String jsonExample = "{\"id\":100,\"name\":\"David\",\"permanent\":false,\"role\":\"Manager\",\"phoneNumbers\":[123456,987654],\"address\":{\"street\":\"BTM 1st Stage\",\"city\":\"Bangalore\",\"zipcode\":560100,\"keyObject\":{\"key2\":\"val\",\"key3\":\"val3\",\"k4y\":{\"a\":32,\"b\":\"c\"}}}}";
    JsonElement jsonRoot = JsonParser.parseString(jsonExample);

    // JSON OBJECT
    JsonElement keyObject = Helper.getJsonElementByKey(jsonRoot, "key2");
    // Add "k5y": "hihi" into parent "keyObject" object
    System.out.println("Json object before adding: " + keyObject);
    keyObject.getAsJsonObject().addProperty("k5y", "hihi");
    System.out.println("Json object after adding: " + keyObject);
    System.out.println("-------------------------------------------------------------------------");

    // Update "key3": "val3" to "key3": "new-value"
    System.out.println("Json before update: " + keyObject);
    keyObject.getAsJsonObject().addProperty("key3", "new-value");
    System.out.println("Json after update: " + keyObject);
    System.out.println("-------------------------------------------------------------------------");

    // Remove "k4y"
    System.out.println("Json before update: " + keyObject);
    keyObject.getAsJsonObject().remove("k4y");
    System.out.println("Json after update: " + keyObject);
    System.out.println("-------------------------------------------------------------------------");

    // JSON ARRAY
    JsonElement phoneArray = Helper.getJsonElementByValue(jsonRoot, new JsonPrimitive(987654));
    // Add new element into array
    System.out.println("Array before adding: " + phoneArray);
    phoneArray.getAsJsonArray().add(555555555);
    System.out.println("Array after adding: " + phoneArray);
    System.out.println("-------------------------------------------------------------------------");

    // Update array
    System.out.println("Array before adding: " + phoneArray);
    phoneArray.getAsJsonArray().set(1, new JsonPrimitive(11111111));
    System.out.println("Array after adding: " + phoneArray);
    System.out.println("-------------------------------------------------------------------------");

    // Remove element in array
    System.out.println("Array before adding: " + phoneArray);
//    phoneArray.getAsJsonArray().remove(0);
    phoneArray.getAsJsonArray().remove(new JsonPrimitive(123456));
    System.out.println("Array after adding: " + phoneArray);
    System.out.println("-------------------------------------------------------------------------");

    System.out.println("Final object: " + jsonRoot);
  }
}
