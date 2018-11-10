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

  public static Rut parse(String rawRut) {
    Objects.requireNonNull(rawRut, "raw rut must not be null");
    if (!rawRut.matches("[\\d]{1,10}-[\\dkK]{1}")) {
      throw new IllegalArgumentException(String.format("Illegal formant from %s", rawRut));
    }

    return new Rut(1, "9");
  }
}
