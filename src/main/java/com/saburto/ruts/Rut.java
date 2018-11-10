package com.saburto.ruts;

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

  public static Rut parse(String rawRut) {

    return new Rut(1, "9");
  }
}
