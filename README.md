# PESEL

## Description

Java library for validating and decoding Polish Personal Identification Number (PESEL).

## Definition

A correct PESEL has 11 digits and contains information about the date of birth, gender, and a serial number.
It has form of `YYMMDDZZZXQ`, where `YYMMDD` digits represent the date of birth, `ZZZ` digits represent the unique serial number,
and `X` digit determines the person's gender: odd are assigned to males, even to females.
The last digit `Q` is a control sum number that verifies whether a given PESEL is correct or not.
You can read more about PESEL [**here**](https://en.wikipedia.org/wiki/PESEL)


## Installation

To use this PESEL validation library in your Java project, you can add the dependency to your build tool.

For Gradle, add the following line to your build.gradle file:

```java
implementation 'io.github.viepovsky:pesel:1.0'
```

For Maven, add the following code to your pom.xml file:

```java
<dependency>
    <groupId>io.github.viepovsky</groupId>
    <artifactId>pesel</artifactId>
    <version>1.0</version>
</dependency>
```

After adding the dependency, you can start using the PESEL validation library in your project.

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

### Generating random PESEL

To generate a random PESEL, you can use the `PeselGenerator` class, which provides methods to generate PESELs with either default parameters or specified ones.

To generate a PESEL with default parameters, you can use the static method `generatePeselStatic()`, which generates a PESEL with the following default parameters:

- Random gender
- Date of birth between today's date and 100 years before today's date.

Here is an example code snippet to generate a PESEL with default parameters:

```java
String generatedPesel = PeselGenerator.generatePeselStatic();
```
The `generatePeselStatic()` method returns a `String` with a random PESEL.

If you want to generate a PESEL with specific parameters, you can use the `PeselGeneratorParams` class to specify the desired gender and date range. For example:

```java
LocalDate minDate = LocalDate.of(1990, 1, 1); 
LocalDate maxDate = LocalDate.of(2010, 1, 1);
PeselGeneratorParams.Gender gender = PeselGeneratorParams.Gender.FEMALE;

PeselGeneratorParams params = PeselGeneratorParams.builder()
        .gender(gender)
        .minDate(minDate)
        .maxDate(maxDate)
        .build();
        
PeselGenerator peselGenerator = new PeselGenerator(params);
String generatedPesel = peselGenerator.generatePesel();
```

This will generate a random PESEL for a female person born between January 1, 1990 and January 1, 2010. You can customize the parameters to generate the desired PESEL.

## Exceptions

When using `Pesel` or PeselValidator to validate, they may throw an exception if the given parameter is `null` (a `NullPointerException`), or if the given PESEL is invalid (an `InvalidPeselException`).
The `InvalidPeselException` message includes information about the validation of the invalid PESEL, and is thrown when the PESEL:
- has an invalid length (other than 11)
- has invalid characters (characters other than digits)
- has an invalid control number (e.g. is 5 but should be 7)
- has an invalid birth date (e.g. the 32nd of any month)

When using `PeselGeneratorParams`, if you pass an out-of-range date parameter, it will throw an `IllegalArgumentException`.
This means that the minimum and maximum date should be within the valid range of PESEL, which is between January 1, 1800 and December 31, 2299. 
If you attempt to use dates outside of this range, an exception will be thrown. 
It is recommended to handle this exception in your code to ensure that your program runs smoothly.
