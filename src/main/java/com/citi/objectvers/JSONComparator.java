package com.citi.objectvers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import java.util.HashSet;
import java.util.Set;

public class JSONComparator {

    private final Gson gson;
    private final JsonObject result;
    private final JsonObject added;
    private final JsonObject removed;
    private final JsonObject modified;
    private final JsonObject typeChanged;

    public JSONComparator() {
        this.gson = new Gson();
        this.result = new JsonObject();
        this.added = new JsonObject();
        this.removed = new JsonObject();
        this.modified = new JsonObject();
        this.typeChanged = new JsonObject();
    }

    public JsonObject compare(String json1Str, String json2Str) {
        JsonElement json1 = JsonParser.parseString(json1Str);
        JsonElement json2 = JsonParser.parseString(json2Str);
        return compare(json1, json2);
    }

    public JsonObject compare(JsonElement json1, JsonElement json2) {
        compareElements(json1, json2, "");
        if (added.size() > 0) result.add("added", added);
        if (removed.size() > 0) result.add("removed", removed);
        if (modified.size() > 0) result.add("modified", modified);
        if (typeChanged.size() > 0) result.add("type_changed", typeChanged);
        return result;
    }

    private void compareElements(JsonElement elem1, JsonElement elem2, String path) {
        if (elem1 == null && elem2 == null) {
            return;
        }
        if (elem1 == null) {
            added.add(path, elem2);
            return;
        }
        if (elem2 == null) {
            removed.add(path, elem1);
            return;
        }
        String type1 = getElementType(elem1);
        String type2 = getElementType(elem2);
        if (!type1.equals(type2)) {
            JsonObject typeChange = new JsonObject();
            typeChange.addProperty("old_type", type1);
            typeChange.addProperty("new_type", type2);
            typeChange.add("old_value", elem1);
            typeChange.add("new_value", elem2);
            typeChanged.add(path, typeChange);
            return;
        }
        if (elem1.isJsonObject() && elem2.isJsonObject()) {
            compareObjects(elem1.getAsJsonObject(), elem2.getAsJsonObject(), path);
        } else if (elem1.isJsonArray() && elem2.isJsonArray()) {
            compareArrays(elem1.getAsJsonArray(), elem2.getAsJsonArray(), path);
        } else if (elem1.isJsonPrimitive() && elem2.isJsonPrimitive()) {
            comparePrimitives(elem1.getAsJsonPrimitive(), elem2.getAsJsonPrimitive(), path);
        } else if (elem1.isJsonNull() && elem2.isJsonNull()) {
            return;
        } else if (!elem1.equals(elem2)) {
            JsonObject change = new JsonObject();
            change.add("old_value", elem1);
            change.add("new_value", elem2);
            modified.add(path, change);
        }
    }

    private void compareObjects(JsonObject obj1, JsonObject obj2, String path) {
        Set<String> allKeys = new HashSet<>();
        allKeys.addAll(obj1.keySet());
        allKeys.addAll(obj2.keySet());

        for (String key : allKeys) {
            String currentPath = path.isEmpty() ? key : path + "." + key;

            if (obj1.has(key) && obj2.has(key)) {
                compareElements(obj1.get(key), obj2.get(key), currentPath);
            } else if (obj1.has(key)) {
                removed.add(currentPath, obj1.get(key));
            } else {
                added.add(currentPath, obj2.get(key));
            }
        }
    }

    private void compareArrays(JsonArray arr1, JsonArray arr2, String path) {
        int size1 = arr1.size();
        int size2 = arr2.size();

        if (size1 != size2) {
            JsonObject lengthChange = new JsonObject();
            lengthChange.addProperty("old_value", size1);
            lengthChange.addProperty("new_value", size2);
            modified.add(path + "[length]", lengthChange);
        }

        int minSize = Math.min(size1, size2);

        for (int i = 0; i < minSize; i++) {
            String currentPath = path + "[" + i + "]";
            compareElements(arr1.get(i), arr2.get(i), currentPath);
        }

        for (int i = minSize; i < size1; i++) {
            removed.add(path + "[" + i + "]", arr1.get(i));
        }

        for (int i = minSize; i < size2; i++) {
            added.add(path + "[" + i + "]", arr2.get(i));
        }
    }

    private void comparePrimitives(JsonPrimitive prim1, JsonPrimitive prim2, String path) {
        if (!prim1.equals(prim2)) {
            JsonObject change = new JsonObject();
            change.add("old_value", prim1);
            change.add("new_value", prim2);
            modified.add(path, change);
        }
    }

    private String getElementType(JsonElement element) {
        if (element.isJsonObject()) return "object";
        if (element.isJsonArray()) return "array";
        if (element.isJsonNull()) return "null";
        if (element.isJsonPrimitive()) {
            JsonPrimitive prim = element.getAsJsonPrimitive();
            if (prim.isBoolean()) return "boolean";
            if (prim.isNumber()) return "number";
            if (prim.isString()) return "string";
        }
        return "unknown";
    }

    public String getResultAsString() {
        return gson.toJson(result);
    }

}
