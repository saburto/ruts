package com.saburto.ruts;

import java.util.Iterator;
import java.util.stream.IntStream;

public class CheckDigitGenerator {

  private CheckDigitGenerator() {
  }

  public static String fromNumber(int number) {

    Iterator<Integer> seq = new SequenceIterator();

    int sum = IntStream.iterate(number, n -> n / 10)
      .limit(String.valueOf(number).length())
      .map(n -> n % 10)
      .map(n -> n * seq.next())
      .sum();

    int mod = 11 - (sum % 11);

    return transform(mod);
  }

  private static class SequenceIterator implements Iterator<Integer> {
    private static final int[] RANGE = IntStream.range(2, 8).toArray();
    private int index = 0;

    @Override
    public boolean hasNext() {
      return true;
    }

    @Override
    public Integer next() {
      if (index == RANGE.length) {
        index = 0;
      }
      return RANGE[index++];
    }
  }

  private static String transform(int mod) {
    switch (mod) {
    case 11:
      return "0";
    case 10:
      return "k";
    default:
      return String.valueOf(mod);
    }
  }
}
