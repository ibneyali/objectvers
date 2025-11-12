package com.citi.objectvers.Employee;

import com.citi.objectvers.JSONComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeJSONComparatorTest {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String empV0;
    private String empV1;

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException {
        URL oldResource = EmployeeJSONComparatorTest.class.getResource("/employee_v0.json");
        URL newResource = EmployeeJSONComparatorTest.class.getResource("/employee_v1.json");
        byte[] oldBytes = Files.readAllBytes(Path.of(oldResource.toURI()));
        byte[] newBytes = Files.readAllBytes(Path.of(newResource.toURI()));
        empV0 = new String(oldBytes);
        empV1 = new String(newBytes);

    }

    @Test
    @DisplayName("Test 1: Basic key differences")
    public void testBasiKeyDiffereces() {
        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(empV0, empV1);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.has("modified"), "Should have modified fields");
        assertTrue(result.has("added"), "Should have added fields");

        // Check modified fields
        JsonObject modified = result.getAsJsonObject("modified");
        assertTrue(modified.has("email"), "email should be modified");
        assertTrue(modified.has("phone"), "phone should be modified");
        assertTrue(modified.has("department.addressLine1"), "department.addressLine1 should be modified");
        assertTrue(modified.has("address.name"), "address.name should be modified");
        assertTrue(modified.has("address.createdTimestamp"), "address.createdTimestamp should be modified");
        assertTrue(modified.has("address.updatedTimestamp"), "address.updatedTimestamp should be modified");
        assertTrue(modified.has("roles[length]"), "roles array length should be modified");

        // Verify email changed (John@gmail.com to JohnDoe@gmail.com)
        JsonObject emailChange = modified.getAsJsonObject("email");
        assertEquals("John@gmail.com", emailChange.get("old_value").getAsString());
        assertEquals("JohnDoe@gmail.com", emailChange.get("new_value").getAsString());

        // Verify phone changed
        JsonObject phoneChange = modified.getAsJsonObject("phone");
        assertEquals("1234567", phoneChange.get("old_value").getAsString());
        assertEquals("1234567890", phoneChange.get("new_value").getAsString());

        // Verify department.addressLine1 changed
        JsonObject addressLine1Change = modified.getAsJsonObject("department.addressLine1");
        assertEquals("123 Main", addressLine1Change.get("old_value").getAsString());
        assertEquals("123 Main Street", addressLine1Change.get("new_value").getAsString());

        // Verify address.name changed
        JsonObject addressNameChange = modified.getAsJsonObject("address.name");
        assertEquals("Information", addressNameChange.get("old_value").getAsString());
        assertEquals("Information Technology", addressNameChange.get("new_value").getAsString());

        // Verify address timestamps changed
        JsonObject createdTimestampChange = modified.getAsJsonObject("address.createdTimestamp");
        assertEquals("2025-11-03T00:44:18.665827", createdTimestampChange.get("old_value").getAsString());
        assertEquals("2025-11-03T00:45:18.665827", createdTimestampChange.get("new_value").getAsString());

        JsonObject updatedTimestampChange = modified.getAsJsonObject("address.updatedTimestamp");
        assertEquals("2025-11-03T00:44:18.665827", updatedTimestampChange.get("old_value").getAsString());
        assertEquals("2025-11-03T00:45:18.665827", updatedTimestampChange.get("new_value").getAsString());

        // Verify roles array length changed
        JsonObject rolesLengthChange = modified.getAsJsonObject("roles[length]");
        assertEquals(1, rolesLengthChange.get("old_value").getAsInt());
        assertEquals(2, rolesLengthChange.get("new_value").getAsInt());

        // Check added fields
        JsonObject added = result.getAsJsonObject("added");
        assertTrue(added.has("roles[1]"), "roles[1] should be added");
        assertEquals("Team Lead", added.get("roles[1]").getAsString());

        System.out.println("Test 1 - Basic key differences:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 2: Deeply Nested Objects")
    public void testDeeplyNestedObjects() {
        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(empV0, empV1);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.has("modified"), "Should have modified fields");

        JsonObject modified = result.getAsJsonObject("modified");

        // Check nested department.addressLine1 change
        assertTrue(modified.has("department.addressLine1"), "department.addressLine1 should be modified");
        JsonObject addressLine1Change = modified.getAsJsonObject("department.addressLine1");
        assertEquals("123 Main", addressLine1Change.get("old_value").getAsString());
        assertEquals("123 Main Street", addressLine1Change.get("new_value").getAsString());

        // Check nested address.name change
        assertTrue(modified.has("address.name"), "address.name should be modified");
        JsonObject nameChange = modified.getAsJsonObject("address.name");
        assertEquals("Information", nameChange.get("old_value").getAsString());
        assertEquals("Information Technology", nameChange.get("new_value").getAsString());

        // Check nested address.createdTimestamp change
        assertTrue(modified.has("address.createdTimestamp"), "address.createdTimestamp should be modified");

        System.out.println("Test 2 - Deeply Nested Objects:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 3: Arrays with Objects")
    public void testArraysWithObjects() {
        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(empV0, empV1);

        assertNotNull(result, "Result should not be null");

        // Check array length change
        if (result.has("modified")) {
            JsonObject modified = result.getAsJsonObject("modified");
            assertTrue(modified.has("roles[length]"), "roles array length should be modified");
            JsonObject lengthChange = modified.getAsJsonObject("roles[length]");
            assertEquals(1, lengthChange.get("old_value").getAsInt());
            assertEquals(2, lengthChange.get("new_value").getAsInt());
        }

        // Check added array element
        assertTrue(result.has("added"), "Should have added fields");
        JsonObject added = result.getAsJsonObject("added");
        assertTrue(added.has("roles[1]"), "roles[1] should be added");
        assertEquals("Team Lead", added.get("roles[1]").getAsString());

        System.out.println("Test 3 - Arrays with Objects:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 4: Type Changes")
    public void testTypeChanges() {
        // Add type change properties to empV0 and empV1
        JsonObject json1 = gson.fromJson(empV0, JsonObject.class);
        JsonObject json2 = gson.fromJson(empV1, JsonObject.class);

        // Add properties with different types
        json1.addProperty("age", "25");  // String in v0
        json2.addProperty("age", 25);     // Number in v1

        json1.addProperty("active", true);   // Boolean in v0
        json2.addProperty("active", "true"); // String in v1

        json1.addProperty("count", 100);    // Number in v0
        json2.addProperty("count", "100");  // String in v1

        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(json1, json2);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.has("type_changed"), "Should have type_changed fields");

        JsonObject typeChanged = result.getAsJsonObject("type_changed");

        // Check age type change from string to number
        assertTrue(typeChanged.has("age"), "age should have type change");
        JsonObject ageChange = typeChanged.getAsJsonObject("age");
        assertEquals("string", ageChange.get("old_type").getAsString());
        assertEquals("number", ageChange.get("new_type").getAsString());

        // Check active type change from boolean to string
        assertTrue(typeChanged.has("active"), "active should have type change");
        JsonObject activeChange = typeChanged.getAsJsonObject("active");
        assertEquals("boolean", activeChange.get("old_type").getAsString());
        assertEquals("string", activeChange.get("new_type").getAsString());

        // Check count type change from number to string
        assertTrue(typeChanged.has("count"), "count should have type change");
        JsonObject countChange = typeChanged.getAsJsonObject("count");
        assertEquals("number", countChange.get("old_type").getAsString());
        assertEquals("string", countChange.get("new_type").getAsString());

        System.out.println("Test 4 - Type Changes:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 5: Null and Boolean Values")
    public void testNullAndBooleanValues() {
        // Add null and boolean properties to empV0 and empV1
        JsonObject json1 = gson.fromJson(empV0, JsonObject.class);
        JsonObject json2 = gson.fromJson(empV1, JsonObject.class);

        // Add properties for null and boolean testing
        json1.add("middleName", null);  // Null in v0
        json2.addProperty("middleName", "Smith");  // String in v1

        json1.addProperty("isActive", true);   // true in v0
        json2.addProperty("isActive", false);  // false in v1

        json1.addProperty("isVerified", false);  // false in both
        json2.addProperty("isVerified", false);

        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(json1, json2);

        assertNotNull(result, "Result should not be null");

        // Check middleName changed from null to value
        assertTrue(result.has("type_changed"), "Should have type_changed fields");
        JsonObject typeChanged = result.getAsJsonObject("type_changed");
        assertTrue(typeChanged.has("middleName"), "middleName should change from null");
        JsonObject middleNameChange = typeChanged.getAsJsonObject("middleName");
        assertEquals("null", middleNameChange.get("old_type").getAsString());
        assertEquals("string", middleNameChange.get("new_type").getAsString());

        // Check isActive boolean change
        assertTrue(result.has("modified"), "Should have modified fields");
        JsonObject modified = result.getAsJsonObject("modified");
        assertTrue(modified.has("isActive"), "isActive should be modified");
        JsonObject isActiveChange = modified.getAsJsonObject("isActive");
        assertTrue(isActiveChange.get("old_value").getAsBoolean());
        assertFalse(isActiveChange.get("new_value").getAsBoolean());

        // isVerified should not be in modified (both false)
        assertFalse(modified.has("isVerified"), "isVerified should not be modified");

        System.out.println("Test 5 - Null and Boolean Values:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 6: Complex Real-World API Response")
    public void testComplexRealWorldSenario() {
        // Build complex API response using empV0 and empV1 as reference
        JsonObject json1 = new JsonObject();
        JsonObject json2 = new JsonObject();

        // Add status
        json1.addProperty("status", "success");
        json2.addProperty("status", "success");

        // Add data with nested user
        JsonObject data1 = new JsonObject();
        JsonObject user1 = new JsonObject();
        user1.addProperty("id", 123);
        user1.addProperty("name", "Alice");
        user1.addProperty("email", "alice@example.com");
        com.google.gson.JsonArray roles1 = new com.google.gson.JsonArray();
        roles1.add("user");
        roles1.add("admin");
        user1.add("roles", roles1);
        data1.add("user", user1);

        com.google.gson.JsonArray permissions1 = new com.google.gson.JsonArray();
        permissions1.add("read");
        permissions1.add("write");
        data1.add("permissions", permissions1);
        json1.add("data", data1);

        JsonObject data2 = new JsonObject();
        JsonObject user2 = new JsonObject();
        user2.addProperty("id", 123);
        user2.addProperty("name", "Alice Smith");
        user2.addProperty("email", "alice.smith@example.com");
        com.google.gson.JsonArray roles2 = new com.google.gson.JsonArray();
        roles2.add("user");
        roles2.add("admin");
        roles2.add("superadmin");
        user2.add("roles", roles2);
        data2.add("user", user2);

        com.google.gson.JsonArray permissions2 = new com.google.gson.JsonArray();
        permissions2.add("read");
        permissions2.add("write");
        permissions2.add("delete");
        data2.add("permissions", permissions2);
        json2.add("data", data2);

        // Add metadata
        JsonObject metadata1 = new JsonObject();
        metadata1.addProperty("timestamp", "2025-11-12T10:00:00Z");
        metadata1.addProperty("version", "1.0");
        json1.add("metadata", metadata1);

        JsonObject metadata2 = new JsonObject();
        metadata2.addProperty("timestamp", "2025-11-12T11:00:00Z");
        metadata2.addProperty("version", "1.1");
        json2.add("metadata", metadata2);

        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(json1, json2);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.has("modified"), "Should have modified fields");

        JsonObject modified = result.getAsJsonObject("modified");

        // Check nested user.name change
        assertTrue(modified.has("data.user.name"), "data.user.name should be modified");
        JsonObject nameChange = modified.getAsJsonObject("data.user.name");
        assertEquals("Alice", nameChange.get("old_value").getAsString());
        assertEquals("Alice Smith", nameChange.get("new_value").getAsString());

        // Check nested user.email change
        assertTrue(modified.has("data.user.email"), "data.user.email should be modified");

        // Check array length changes
        assertTrue(modified.has("data.user.roles[length]"), "roles array length should be modified");
        assertTrue(modified.has("data.permissions[length]"), "permissions array length should be modified");

        // Check added elements
        assertTrue(result.has("added"), "Should have added fields");
        JsonObject added = result.getAsJsonObject("added");
        assertTrue(added.has("data.user.roles[2]"), "roles[2] should be added");
        assertTrue(added.has("data.permissions[2]"), "permissions[2] should be added");

        System.out.println("Test 6 - Complex Real-World API Response:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 7: Empty Structures" )
    public void testEmptyStructures() {
        // Create test objects with empty structures using empV0/empV1 as base
        JsonObject json1 = new JsonObject();
        JsonObject json2 = new JsonObject();

        // Add empty and populated structures
        json1.add("data", new JsonObject());  // Empty object
        JsonObject data2 = new JsonObject();
        data2.addProperty("name", "John");
        json2.add("data", data2);  // Object with property

        json1.add("items", new com.google.gson.JsonArray());  // Empty array
        com.google.gson.JsonArray items2 = new com.google.gson.JsonArray();
        items2.add("item1");
        json2.add("items", items2);  // Array with element

        json1.addProperty("count", 0);
        json2.addProperty("count", 1);

        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(json1, json2);

        assertNotNull(result, "Result should not be null");

        // Check added fields in previously empty object
        assertTrue(result.has("added"), "Should have added fields");
        JsonObject added = result.getAsJsonObject("added");
        assertTrue(added.has("data.name"), "data.name should be added");
        assertEquals("John", added.get("data.name").getAsString());

        // Check array changes
        assertTrue(result.has("modified"), "Should have modified fields");
        JsonObject modified = result.getAsJsonObject("modified");
        assertTrue(modified.has("items[length]"), "items array length should be modified");
        assertTrue(modified.has("count"), "count should be modified");

        // Check added array element
        assertTrue(added.has("items[0]"), "items[0] should be added");
        assertEquals("item1", added.get("items[0]").getAsString());

        // Test comparison of identical empty structures
        JsonObject json3 = new JsonObject();
        JsonObject json4 = new JsonObject();
        json3.add("data", new JsonObject());
        json3.add("items", new com.google.gson.JsonArray());
        json4.add("data", new JsonObject());
        json4.add("items", new com.google.gson.JsonArray());

        JSONComparator comparator2 = new JSONComparator();
        JsonObject result2 = comparator2.compare(json3, json4);

        // Should have no changes
        assertFalse(result2.has("added"), "Should not have added fields");
        assertFalse(result2.has("removed"), "Should not have removed fields");
        assertFalse(result2.has("modified"), "Should not have modified fields");
        assertFalse(result2.has("type_changed"), "Should not have type_changed fields");

        System.out.println("Test 7 - Empty Structures:");
        System.out.println(gson.toJson(result));
    }

    @Test
    @DisplayName("Test 8: Large Array Comparison" )
    public void testLargeArray() {
        // Build large arrays programmatically using empV0/empV1 as reference
        JsonObject json1 = new JsonObject();
        JsonObject json2 = new JsonObject();

        // Create large numbers array
        com.google.gson.JsonArray numbers1 = new com.google.gson.JsonArray();
        com.google.gson.JsonArray numbers2 = new com.google.gson.JsonArray();
        for (int i = 0; i < 100; i++) {
            numbers1.add(i);
            // Change every 10th element in json2
            if (i % 10 == 0) {
                numbers2.add(i * 2);
            } else {
                numbers2.add(i);
            }
        }
        json1.add("numbers", numbers1);
        json2.add("numbers", numbers2);

        // Create users array with objects
        com.google.gson.JsonArray users1 = new com.google.gson.JsonArray();
        com.google.gson.JsonArray users2 = new com.google.gson.JsonArray();
        for (int i = 0; i < 10; i++) {
            JsonObject user1 = new JsonObject();
            user1.addProperty("id", i);
            user1.addProperty("name", "User" + i);
            users1.add(user1);

            JsonObject user2 = new JsonObject();
            user2.addProperty("id", i);
            // Change name for even indices
            if (i % 2 == 0) {
                user2.addProperty("name", "UpdatedUser" + i);
            } else {
                user2.addProperty("name", "User" + i);
            }
            users2.add(user2);
        }
        json1.add("users", users1);
        json2.add("users", users2);

        JSONComparator comparator = new JSONComparator();
        JsonObject result = comparator.compare(json1, json2);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.has("modified"), "Should have modified fields");

        JsonObject modified = result.getAsJsonObject("modified");

        // Check that changed numbers are detected (every 10th element except 0, since 0*2=0)
        assertTrue(modified.has("numbers[10]"), "numbers[10] should be modified");
        assertTrue(modified.has("numbers[20]"), "numbers[20] should be modified");
        assertTrue(modified.has("numbers[30]"), "numbers[30] should be modified");
        assertTrue(modified.has("numbers[40]"), "numbers[40] should be modified");

        // Check that changed user names are detected (even indices)
        assertTrue(modified.has("users[0].name"), "users[0].name should be modified");
        assertTrue(modified.has("users[2].name"), "users[2].name should be modified");
        assertTrue(modified.has("users[4].name"), "users[4].name should be modified");

        // Verify specific changes
        JsonObject numbers10Change = modified.getAsJsonObject("numbers[10]");
        assertEquals(10, numbers10Change.get("old_value").getAsInt());
        assertEquals(20, numbers10Change.get("new_value").getAsInt());

        JsonObject numbers20Change = modified.getAsJsonObject("numbers[20]");
        assertEquals(20, numbers20Change.get("old_value").getAsInt());
        assertEquals(40, numbers20Change.get("new_value").getAsInt());

        JsonObject user0NameChange = modified.getAsJsonObject("users[0].name");
        assertEquals("User0", user0NameChange.get("old_value").getAsString());
        assertEquals("UpdatedUser0", user0NameChange.get("new_value").getAsString());

        System.out.println("Test 8 - Large Array Comparison:");
        System.out.println("Total modified fields: " + modified.size());
        System.out.println("Sample changes:");
        System.out.println("  numbers[10]: " + gson.toJson(numbers10Change));
        System.out.println("  numbers[20]: " + gson.toJson(numbers20Change));
        System.out.println("  users[0].name: " + gson.toJson(user0NameChange));
    }

}
