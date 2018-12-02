package com.saburto.ruts;

import static com.saburto.ruts.CheckDigitGenerator.fromNumber;
import static com.saburto.ruts.ValueExtractor.extract;
import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

/**
 * TODO Add javadoc
 */
public class Rut implements Comparable<Rut> {

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

  @Override
  public int hashCode() {
    return hash(number, checkDigit);
  }

  @Override
  public boolean equals(Object other) {
    if(other instanceof Rut) {
      Rut otherRut = (Rut) other;
      return number == otherRut.number &&
        checkDigit.equalsIgnoreCase(otherRut.checkDigit);
    }
    return false;
  }

  @Override
  public int compareTo(Rut o) {
    int numberCompare = Integer.compare(number, o.number);
    if (numberCompare == 0) {
      return checkDigit.compareTo(o.checkDigit);
    }
    return numberCompare;
  }
}
