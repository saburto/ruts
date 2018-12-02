package com.saburto.ruts;

import static com.saburto.ruts.CheckDigitGenerator.fromNumber;
import static com.saburto.ruts.ValueExtractor.extract;
import static java.util.Objects.requireNonNull;

/**
 * TODO Add javadoc
 */
public class Rut {

  private final int number;
  private final String checkDigit;

  public Rut(int number, String checkDigit) {
    this.number = number;
    this.checkDigit = checkDigit;
  }

  public int getNumber() {
    return number;
  }

  public String getCheckDigit() {
    return checkDigit;
  }

  public boolean isValid() {
    return fromNumber(number).equalsIgnoreCase(checkDigit);
  }

  public static Rut parse(String rawRut) {
    requireNonNull(rawRut, "raw rut must not be null");
    return extract(rawRut, Rut::new);
  }
}
