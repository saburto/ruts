package com.saburto.ruts;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO Add javadoc
 */
public class Rut {

  private static final String PATTERN =
    "^(?<number>[\\d]{1,3}(\\.[\\d]{3})*|\\d+)" + // Number part
    "-" + // Separator
    "(?<check>[\\dkK]{1})$"; // Check digit

  private static Pattern FORMAT_PATTERN = Pattern.compile(PATTERN);

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

    Matcher rutMatcher = FORMAT_PATTERN.matcher(rawRut);
    if (!rutMatcher.matches()) {
      throwInvalidExeption(rawRut);
    }

    String numberString = rutMatcher.group("number");
    String checkDigit = rutMatcher.group("check");

    Number v = null;
    try {
      v = NumberFormat.getNumberInstance(new Locale("es", "CL"))
        .parse(numberString);
    } catch (ParseException e) {
      throwInvalidExeption(rawRut);
    }

    return new Rut(v.intValue(), checkDigit);
  }

  private static void throwInvalidExeption(String rawRut) {
      throw new IllegalArgumentException(String.format("Illegal format of [%s]", rawRut));
  }
}
