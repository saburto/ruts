package com.saburto.ruts;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ValueExtractor {

  private static final String PATTERN = "^(?<number>[\\d]{1,3}(\\.[\\d]{3})*|\\d+)-(?<check>[\\dkK]{1})$";
  private static final Pattern FORMAT_PATTERN = Pattern.compile(PATTERN);
  private static final Locale LOCALE_ESCL = new Locale("es", "CL");

  private ValueExtractor() {
  }

  static <T> T extract(String rawRut, BiFunction<Integer, String, T> consumeValues) {
    Matcher rutMatcher = FORMAT_PATTERN.matcher(rawRut);
    if (!rutMatcher.matches()) {
      throw new IllegalArgumentException(String.format("Bad format of RUT [%s]", rawRut));
    }

    return consumeValues.apply(toInt(rutMatcher.group("number")), rutMatcher.group("check"));
  }

  private static int toInt(String number) {
    try {
      return NumberFormat.getNumberInstance(LOCALE_ESCL)
        .parse(number)
        .intValue();
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
