package com.saburto.ruts;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * TODO Add javadoc
 */
public class Rut {

  private static final String PATTERN = "^(?<number>[\\d]{1,3}(\\.[\\d]{3})*|\\d+)" + // Number part
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
      v = NumberFormat.getNumberInstance(new Locale("es", "CL")).parse(numberString);
    } catch (ParseException e) {
      throwInvalidExeption(rawRut);
    }

    int number = v.intValue();

    Iterator<Integer> seq = cycleIterator(IntStream.range(2, 8).toArray());

    int sum = IntStream.iterate(number, n -> n / 10)
      .limit(numberString.length())
      .map(n -> n % 10)
      .map(n -> n* seq.next())
      .sum();

    int mod = 11 - (sum % 11);

    if(!checkDigitFrom(mod).equalsIgnoreCase(checkDigit)) {
      throwInvalidExeption(rawRut);
    }

    return new Rut(number, checkDigit);
  }

  private static void throwInvalidExeption(String rawRut) {
    throw new IllegalArgumentException(String.format("Invalid RUT [%s]", rawRut));
  }

  private static Iterator<Integer> cycleIterator(int[] range) {
    return new Iterator<Integer>() {

      int index = 0;

      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public Integer next() {
        if(index == range.length) {
          index = 0;
        }

        return range[index++];
      }

    };
  }

  private static String checkDigitFrom(int mod) {
    switch (mod) {
      case 11: return "0";
      case 10: return "k";
      default: return String.valueOf(mod);
    }
  }
}
