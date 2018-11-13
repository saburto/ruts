package com.saburto.ruts;

import java.util.Objects;

/**
 * TODO Add javadoc
 */
public class Rut {

  private final int number;
  private final String checkDigit;

  private Rut(int number, String checkDigit) {
    this.number = number;
    this.checkDigit = checkDigit;
  }

  public int getNumber() {
    return number;
  }

  public String getCheckDigit() {
    return checkDigit;
  }

  public static Rut parseValid(String rawRut) {
    Rut rut = parse(rawRut);
    if (!rut.isValid()) {
      throw new IllegalArgumentException(String.format("Invalid RUT [%s]", rawRut));
    }
    return rut;
  }

  public static Rut parse(String rawRut) {
    Objects.requireNonNull(rawRut, "raw rut must not be null");

    ValueExtractor valueExtractor = new ValueExtractor(rawRut);
    int number = valueExtractor.getNumber();
    String checkDigit = valueExtractor.getCheckDigit();

    return new Rut(number, checkDigit);
  }

  public boolean isValid() {

    CheckDigitGenerator checkDigitGenerator = new CheckDigitGenerator();
    String rightCheckDigit = checkDigitGenerator.fromNumber(number);

    return rightCheckDigit.equalsIgnoreCase(checkDigit);
  }

}
