package ort.arpitjava;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
        list.add("name");
        list.add("email");
        list.add("department");
        list.add("street");
        list.add("city");

        // Your JSON string
        String jsonString = "{\n" +
                "  \"name\": \"John\",\n" +
                "  \"age\": 30,\n" +
                "  \"address\": {\n" +
                "    \"street\": \"123 Main St\",\n" +
                "    \"city\": \"Anytown\",\n" +
                "    \"country\": \"USA\"\n" +
                "  },\n" +
                "  \"contacts\": [\n" +
                "    {\n" +
                "      \"type\": \"email\",\n" +
                "      \"value\": \"john@example.com\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"phone\",\n" +
                "      \"value\": \"+1234567890\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"orders\": [\n" +
                "    {\n" +
                "      \"id\": \"order001\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"name\": \"Product A\",\n" +
                "          \"quantity\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"Product B\",\n" +
                "          \"quantity\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"total\": 150\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"order002\",\n" +
                "      \"items\": [\n" +
                "        {\n" +
                "          \"name\": \"Product C\",\n" +
                "          \"quantity\": 3\n" +
                "        }\n" +
                "      ],\n" +
                "      \"total\": 200\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";

        try {
            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);

            // Update attribute values recursively
            updateAttributeValues(jsonObject);

            // Convert the JSONObject back to a JSON string
            String updatedJsonString = jsonObject.toString();

            // Print the updated JSON string
            System.out.println(updatedJsonString);
        } catch (Exception e) {
            System.err.println("Failed to parse JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Recursive function to update attribute values
    private static void updateAttributeValues(Object object) {
        if (object instanceof JSONObject jsonObject) {
            jsonObject.keySet().forEach(key -> {
                Object value = jsonObject.get(key);
                if (value instanceof String strValue && list.contains(key)) {
                    if (strValue.length() >= 3) {
                        String newValue = "x".repeat(strValue.length() - 3) + strValue.substring(strValue.length() - 3);
                        jsonObject.put(key, newValue);
                    } else if (strValue.length() == 2) {
                        String newValue = "xx" + strValue.substring(strValue.length() - 1);
                        jsonObject.put(key, newValue);
                    } else if (strValue.length() == 1) {
                        jsonObject.put(key, "x");
                    }
                } else {
                    updateAttributeValues(value); // Recursive call for nested objects or arrays
                }
            });
        } else if (object instanceof JSONArray jsonArray) {
            for (Object element : jsonArray) {
                updateAttributeValues(element); // Recursive call for array elements
            }
        }
    }
}
