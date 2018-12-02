# Ruts

Simple java library to manage [RUT](https://en.wikipedia.org/wiki/National_identification_number#Chile) as value object

## Features

* Immutable value object
* Parse from string value
* Validate if check digit is correct
* Implements serializable interface
* Implements comparable interfaces, list of `rut` is sortable.
* Implements `hashcode` and `equals,` the rut object could be used as a key on a Hashmap or HashSet collections.
* Implements `toString` method, return format "123.123-1"

## How to use it?

This library is only compatible with Java version 8 or newer

Gradle

```
compile "com.saburto:ruts:1.0.0-SNAPSHOT"

```

Maven

```
<dependency>
    <groupId>com.saburto</groupId>
    <artifactId>ruts</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>

```


Code example:

```java
Rut rut = Rut.parse("124.123-4"); // parse from String
new Rut(1234, "k"); // constructor by integer and string

rut.isValid(); // returns `true` if check digit is correct

rut.toString() // returns "124.123-4"

Map<Rut, Foo> map = new HashMap<>() // could be used as key on hashmap/hashset
Set<Rut> set = new HashSet<>(); // rut implements `hashcode` and `equals`
```
