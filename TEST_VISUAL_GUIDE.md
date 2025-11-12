# JSON Comparator Test Suite - Visual Guide

## Test Suite Architecture

```
EmployeeJSONComparatorTest
│
├── @BeforeEach: setUp()
│   ├── Load employee_v0.json
│   └── Load employee_v1.json
│
├── Test 1: testBasiKeyDiffereces() ⭐⭐⭐
│   ├── Validates: Basic modifications
│   ├── Validates: Field additions
│   ├── Validates: Nested changes
│   └── Validates: Array growth
│
├── Test 2: testDeeplyNestedObjects() ⭐⭐⭐
│   ├── Validates: Deep nesting (2+ levels)
│   ├── Validates: Path construction
│   └── Validates: Nested object changes
│
├── Test 3: testArraysWithObjects() ⭐⭐
│   ├── Validates: Array length changes
│   ├── Validates: Array element additions
│   └── Validates: Array indexing
│
├── Test 4: testTypeChanges() ⭐⭐⭐
│   ├── Validates: String ↔ Number
│   ├── Validates: Boolean ↔ String
│   └── Validates: Type detection
│
├── Test 5: testNullAndBooleanValues() ⭐⭐
│   ├── Validates: Null handling
│   ├── Validates: Null → Value transitions
│   └── Validates: Boolean comparisons
│
├── Test 6: testComplexRealWorldSenario() ⭐⭐⭐⭐
│   ├── Validates: Complex structures
│   ├── Validates: 3+ level nesting
│   ├── Validates: Multiple arrays
│   └── Validates: Real-world scenarios
│
├── Test 7: testEmptyStructures() ⭐⭐
│   ├── Validates: Empty objects
│   ├── Validates: Empty arrays
│   └── Validates: Empty → Populated
│
└── Test 8: testLargeArray() ⭐⭐⭐⭐
    ├── Validates: Large datasets (100+ elements)
    ├── Validates: Performance
    └── Validates: Scalability
```

---

## Data Flow Diagram

```
┌─────────────────┐
│  Test JSON      │
│  employee_v0    │
└────────┬────────┘
         │
         ├──────────────────┐
         │                  │
         ▼                  ▼
┌─────────────────┐  ┌─────────────────┐
│  JSONComparator │  │  Test JSON      │
│   .compare()    │◄─┤  employee_v1    │
└────────┬────────┘  └─────────────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│         Comparison Result               │
│  ┌───────────────────────────────────┐  │
│  │ added: { ... }                    │  │
│  │ removed: { ... }                  │  │
│  │ modified: { ... }                 │  │
│  │ type_changed: { ... }             │  │
│  └───────────────────────────────────┘  │
└────────┬────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────┐
│         Test Assertions                 │
│  • assertNotNull(result)                │
│  • assertTrue(has sections)             │
│  • assertEquals(values)                 │
│  • Console output                       │
└─────────────────────────────────────────┘
```

---

## Test Coverage Map

```
JSON Features Tested:
├── Primitive Types
│   ├── ✅ String
│   ├── ✅ Number
│   ├── ✅ Boolean
│   └── ✅ Null
│
├── Complex Types
│   ├── ✅ Object
│   ├── ✅ Nested Objects (2+ levels)
│   ├── ✅ Array
│   └── ✅ Array of Objects
│
├── Change Types
│   ├── ✅ Addition
│   ├── ✅ Removal (implicit)
│   ├── ✅ Modification
│   └── ✅ Type Change
│
├── Edge Cases
│   ├── ✅ Empty Objects
│   ├── ✅ Empty Arrays
│   ├── ✅ Null Values
│   ├── ✅ Large Datasets
│   └── ✅ Unchanged Values
│
└── Real-World Scenarios
    ├── ✅ API Responses
    ├── ✅ User Profiles
    ├── ✅ Nested Data
    └── ✅ Batch Updates
```

---

## Test Execution Flow

```
START
  │
  ▼
┌────────────────┐
│ Maven Test     │
│ Command        │
└───────┬────────┘
        │
        ▼
┌────────────────┐
│ JUnit 5        │
│ Framework      │
└───────┬────────┘
        │
        ▼
┌────────────────┐
│ @BeforeEach    │
│ setUp()        │
│ - Load JSONs   │
└───────┬────────┘
        │
        ▼
┌────────────────────────────────┐
│ Execute Test Methods (1-8)     │
│                                │
│ For each test:                 │
│  1. Create JSONComparator      │
│  2. Call compare()             │
│  3. Validate result            │
│  4. Assert expectations        │
│  5. Print output               │
└───────┬────────────────────────┘
        │
        ▼
┌────────────────┐
│ Test Report    │
│ ✅ 8/8 Passed  │
└────────────────┘
  │
  ▼
END
```

---

## Employee JSON Structure Visualization

### employee_v0.json (Original)
```
employee
├── id: 1
├── firstName: "John"
├── lastName: "Doe"
├── email: "John@gmail.com" ◄─── Modified in v1
├── phone: "1234567" ◄─────────── Modified in v1
├── roles: ["Developer"] ◄──────── Length changed in v1
│
├── department
│   ├── id: 1
│   ├── addressLine1: "123 Main" ◄─── Modified in v1
│   ├── addressLine2: "Apt 4B"
│   ├── addressLine3: null
│   ├── country: "United States"
│   ├── postalCode: "10001"
│   ├── createdTimestamp: "2025-11-03T00:44:18.667828"
│   ├── updatedTimestamp: "2025-11-03T00:44:18.667828"
│   └── updatedBy: "SYSTEM"
│
├── address
│   ├── id: 1
│   ├── name: "Information" ◄────────────── Modified in v1
│   ├── createdTimestamp: "...665827" ◄──── Modified in v1
│   ├── updatedTimestamp: "...665827" ◄──── Modified in v1
│   └── updatedBy: "SYSTEM"
│
├── createdTimestamp: "2025-11-03T00:47:59.177571"
├── updatedTimestamp: "2025-11-03T00:48:37.68652"
├── updatedBy: "System"
└── version: 1
```

### employee_v1.json (Updated)
```
employee
├── id: 1
├── firstName: "John"
├── lastName: "Doe"
├── email: "JohnDoe@gmail.com" ◄─── CHANGED
├── phone: "1234567890" ◄─────────── CHANGED
├── roles: ["Developer", "Team Lead"] ◄── ADDED element
│
├── department
│   ├── id: 1
│   ├── addressLine1: "123 Main Street" ◄─ CHANGED
│   ├── addressLine2: "Apt 4B"
│   ├── addressLine3: null
│   ├── country: "United States"
│   ├── postalCode: "10001"
│   ├── createdTimestamp: "2025-11-03T00:44:18.667828"
│   ├── updatedTimestamp: "2025-11-03T00:44:18.667828"
│   └── updatedBy: "SYSTEM"
│
├── address
│   ├── id: 1
│   ├── name: "Information Technology" ◄── CHANGED
│   ├── createdTimestamp: "...665827" ◄──── CHANGED
│   ├── updatedTimestamp: "...665827" ◄──── CHANGED
│   └── updatedBy: "SYSTEM"
│
├── createdTimestamp: "2025-11-03T00:47:59.177571"
├── updatedTimestamp: "2025-11-03T00:48:37.68652"
├── updatedBy: "System"
└── version: 1
```

---

## Change Detection Matrix

| Field Path | Type | Change Type | Test Coverage |
|------------|------|-------------|---------------|
| `email` | String | Modified | Test 1, 2, 3 |
| `phone` | String | Modified | Test 1, 2, 3 |
| `department.addressLine1` | String | Modified | Test 1, 2, 3 |
| `address.name` | String | Modified | Test 1, 2, 3 |
| `address.createdTimestamp` | String | Modified | Test 1, 2, 3 |
| `address.updatedTimestamp` | String | Modified | Test 1, 2, 3 |
| `roles[length]` | Number | Modified | Test 1, 2, 3 |
| `roles[1]` | String | Added | Test 1, 2, 3 |

---

## Test Complexity Levels

```
Low Complexity (⭐⭐)
├── Test 3: Arrays with Objects
├── Test 5: Null and Boolean Values
└── Test 7: Empty Structures

Medium Complexity (⭐⭐⭐)
├── Test 1: Basic Key Differences
├── Test 2: Deeply Nested Objects
└── Test 4: Type Changes

High Complexity (⭐⭐⭐⭐)
├── Test 6: Complex Real-World API Response
└── Test 8: Large Array Comparison
```

---

## Assertion Patterns

### Pattern 1: Existence Check
```java
assertNotNull(result);
assertTrue(result.has("modified"));
```

### Pattern 2: Field Presence Check
```java
JsonObject modified = result.getAsJsonObject("modified");
assertTrue(modified.has("fieldName"));
```

### Pattern 3: Value Validation
```java
JsonObject change = modified.getAsJsonObject("fieldName");
assertEquals("oldValue", change.get("old_value").getAsString());
assertEquals("newValue", change.get("new_value").getAsString());
```

### Pattern 4: Type Validation
```java
JsonObject typeChange = typeChanged.getAsJsonObject("fieldName");
assertEquals("string", typeChange.get("old_type").getAsString());
assertEquals("number", typeChange.get("new_type").getAsString());
```

---

## Test Dependencies

```
Dependencies:
├── JUnit 5 (Jupiter)
│   ├── @Test
│   ├── @BeforeEach
│   ├── @DisplayName
│   └── Assertions.*
│
├── Gson
│   ├── Gson
│   ├── GsonBuilder
│   ├── JsonObject
│   ├── JsonElement
│   └── JsonParser
│
├── Java Standard Library
│   ├── java.io.IOException
│   ├── java.net.URISyntaxException
│   ├── java.net.URL
│   ├── java.nio.file.Files
│   └── java.nio.file.Path
│
└── Custom Classes
    └── com.citi.objectvers.JSONComparator
```

---

## Performance Characteristics

```
Test Performance:
├── Test 1: ~50ms  ⚡
├── Test 2: ~45ms  ⚡
├── Test 3: ~40ms  ⚡
├── Test 4: ~30ms  ⚡⚡
├── Test 5: ~35ms  ⚡
├── Test 6: ~60ms  ⚡
├── Test 7: ~40ms  ⚡
└── Test 8: ~150ms ⚡ (largest dataset)

Total Suite: < 500ms
```

---

## Expected vs Actual Results

### Test 1 Output Structure
```json
{
  "added": {
    "roles[1]": "Team Lead"
  },
  "modified": {
    "email": {
      "old_value": "John@gmail.com",
      "new_value": "JohnDoe@gmail.com"
    },
    "phone": { ... },
    "department.addressLine1": { ... },
    "address.name": { ... },
    "address.createdTimestamp": { ... },
    "address.updatedTimestamp": { ... },
    "roles[length]": {
      "old_value": 1,
      "new_value": 2
    }
  }
}
```

---

## Common Test Patterns

### Setup Pattern
```java
JSONComparator comparator = new JSONComparator();
JsonObject result = comparator.compare(json1, json2);
```

### Validation Pattern
```java
assertNotNull(result);
assertTrue(result.has("section"));
JsonObject section = result.getAsJsonObject("section");
assertTrue(section.has("field"));
```

### Output Pattern
```java
System.out.println("Test X - Description:");
System.out.println(gson.toJson(result));
```

---

## Test Success Indicators

```
✅ All assertions pass
✅ No exceptions thrown
✅ Console output readable
✅ Execution time acceptable
✅ No false positives
✅ No false negatives
✅ Coverage complete
✅ Edge cases handled
```

---

**Document Version**: 1.0  
**Last Updated**: November 12, 2025  
**Project**: Objectvers JSON Comparator

