# JSON Comparator Test Suite - Quick Reference

## Quick Test Overview

### 🧪 Test 1: Basic Key Differences
**Purpose**: Tests basic field modifications and additions  
**Key Validations**: 
- ✓ Email change: `John@gmail.com` → `JohnDoe@gmail.com`
- ✓ Phone change: `1234567` → `1234567890`
- ✓ Nested changes: `department.addressLine1`, `address.name`
- ✓ Array expansion: roles array grows from 1 to 2 elements

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testBasiKeyDiffereces`

---

### 🏗️ Test 2: Deeply Nested Objects
**Purpose**: Tests nested object comparison at multiple levels  
**Key Validations**:
- ✓ 2-level nesting: `department.addressLine1`
- ✓ Path construction with dot notation
- ✓ Timestamp changes in nested objects

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testDeeplyNestedObjects`

---

### 📋 Test 3: Arrays with Objects
**Purpose**: Tests array modifications  
**Key Validations**:
- ✓ Array length tracking: `roles[length]` 1 → 2
- ✓ New element detection: `roles[1]` = "Team Lead"
- ✓ Array index notation

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testArraysWithObjects`

---

### 🔄 Test 4: Type Changes
**Purpose**: Tests data type change detection  
**Key Validations**:
- ✓ String → Number: `age` "25" → 25
- ✓ Boolean → String: `active` true → "true"
- ✓ Number → String: `count` 100 → "100"

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testTypeChanges`

---

### ⚡ Test 5: Null and Boolean Values
**Purpose**: Tests null handling and boolean comparison  
**Key Validations**:
- ✓ Null → String: `middleName` null → "Smith"
- ✓ Boolean change: `isActive` true → false
- ✓ Unchanged values not reported

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testNullAndBooleanValues`

---

### 🌐 Test 6: Complex Real-World API Response
**Purpose**: Tests complex nested structures  
**Key Validations**:
- ✓ 3-level nesting: `data.user.name`
- ✓ Multiple array changes
- ✓ Coordinated updates across structure
- ✓ Version tracking

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testComplexRealWorldSenario`

---

### 📦 Test 7: Empty Structures
**Purpose**: Tests empty objects and arrays  
**Key Validations**:
- ✓ Empty → Populated: `data: {}` → `{name: "John"}`
- ✓ Empty array → Populated array
- ✓ Identical empty structures = no changes

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testEmptyStructures`

---

### 📊 Test 8: Large Array Comparison
**Purpose**: Tests performance with large datasets  
**Key Validations**:
- ✓ 100-element number array
- ✓ 10-element object array
- ✓ Selective change detection (every 10th number)
- ✓ Performance < 1 second

**Run**: `mvn test -Dtest=EmployeeJSONComparatorTest#testLargeArray`

---

## Command Cheat Sheet

```bash
# Run all tests
mvn test -Dtest=EmployeeJSONComparatorTest

# Run single test (replace method name)
mvn test -Dtest=EmployeeJSONComparatorTest#<methodName>

# Run with verbose output
mvn test -Dtest=EmployeeJSONComparatorTest -X

# Clean and test
mvn clean test -Dtest=EmployeeJSONComparatorTest
```

---

## Output Format Reference

```json
{
  "added": {
    "path": "value"           // New fields
  },
  "removed": {
    "path": "value"           // Deleted fields
  },
  "modified": {
    "path": {                 // Changed fields
      "old_value": "...",
      "new_value": "..."
    }
  },
  "type_changed": {
    "path": {                 // Type changes
      "old_type": "...",
      "new_type": "...",
      "old_value": "...",
      "new_value": "..."
    }
  }
}
```

---

## Path Notation Examples

| Notation | Description | Example |
|----------|-------------|---------|
| `field` | Root field | `email` |
| `parent.child` | Nested field | `address.name` |
| `array[index]` | Array element | `roles[1]` |
| `array[length]` | Array length | `roles[length]` |
| `obj.array[index].field` | Complex path | `users[0].name` |

---

## Common Assertions

```java
// Check section exists
assertTrue(result.has("modified"));

// Check specific field changed
assertTrue(modified.has("fieldName"));

// Verify old/new values
assertEquals("old", change.get("old_value").getAsString());
assertEquals("new", change.get("new_value").getAsString());

// Check type change
assertEquals("string", typeChange.get("old_type").getAsString());
```

---

## Test Results Matrix

| Test | Modifications | Additions | Type Changes | Complexity |
|------|--------------|-----------|--------------|------------|
| Test 1 | 7 | 1 | 0 | ⭐⭐⭐ |
| Test 2 | 7 | 1 | 0 | ⭐⭐⭐ |
| Test 3 | 7 | 1 | 0 | ⭐⭐ |
| Test 4 | 0 | 0 | 3 | ⭐⭐⭐ |
| Test 5 | 1 | 0 | 1 | ⭐⭐ |
| Test 6 | 6 | 2 | 0 | ⭐⭐⭐⭐ |
| Test 7 | 2 | 2 | 0 | ⭐⭐ |
| Test 8 | 14 | 0 | 0 | ⭐⭐⭐⭐ |

---

## Success Criteria

✅ All 8 tests pass  
✅ No compilation errors  
✅ Execution time < 5 seconds  
✅ No false positives  
✅ No false negatives  

---

## Quick Troubleshooting

| Issue | Solution |
|-------|----------|
| Resources not found | Check `employee_v0.json` and `employee_v1.json` in `src/main/resources` |
| Test fails on firstName | JSON files updated, test now expects "John" in both versions |
| Array index error | Verify array length tracking logic |
| Type detection wrong | Check `getElementType()` method |

---

**Last Updated**: November 12, 2025  
**Project**: Objectvers JSON Comparator  
**Version**: 1.0

