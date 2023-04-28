# PESEL

## Description

Java library for validating and decoding Polish Personal Identification Number (PESEL).

## Definition

A correct PESEL has 11 digits and contains information about the date of birth, gender, and a serial number.
It has form of `YYMMDDZZZXQ`, where `YYMMDD` digits represent the date of birth, `ZZZ` digits represent the unique serial number,
and `X` digit determines the person's gender: odd are assigned to males, even to females.
The last digit `Q` is a control sum number that verifies whether a given PESEL is correct or not.
You can read more about PESEL [**here**](https://en.wikipedia.org/wiki/PESEL)

## Usage

### Validation of PESEL number

To validate a PESEL, you can use either the `Pesel` or `PeselValidator` class.

```java
Pesel pesel = new Pesel("92082683499"); //may throw InvalidPeselException
//or
PeselValidator.assertIsValid("92082683499"); //may throw InvalidPeselException
//or
boolean isPeselValid = PeselValidator.isValid("92082683499"); //returns true or false
```

### Decoding PESEL date of birth or gender

To decode a PESEL and retrieve information, use the `Pesel` class.

```java
Pesel pesel = new Pesel("92082683499"); //may throw InvalidPeselException
String gender = pesel.getGender(); // returns MALE or FEMALE
LocalDate birthDate = pesel.getBirthDate();
```

### Retrieving PESEL digits

To retrieve specific digits from a PESEL, use the `Pesel` class.

```java
Pesel pesel = new Pesel("92082683499"); //may throw InvalidPeselException
int birthDate = pesel.getBirthDateNumbers();
int serial = pesel.getSerialNumbers();
int gender = pesel.getGenderNumber();
int control = pesel.getControlNumber();
```

## Exceptions

When using `Pesel` or PeselValidator to validate, they may throw an exception if the given parameter is `null` (a `NullPointerException`), or if the given PESEL is invalid (an `InvalidPeselException`).
The `InvalidPeselException` message includes information about the validation of the invalid PESEL, and is thrown when the PESEL:
- has an invalid length (other than 11)
- has invalid characters (characters other than digits)
- has an invalid control number (e.g. is 5 but should be 7)
- has an invalid birth date (e.g. the 32nd of any month)
