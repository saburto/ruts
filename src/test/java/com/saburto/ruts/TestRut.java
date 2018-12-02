package com.saburto.ruts;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TestRut {

  @Nested
  class RutParse {
    @Test
    void parseSimpleRut() {
      Rut rut = Rut.parse("1-9");
      assertThat(rut.getNumber()).isEqualTo(1);
      assertThat(rut.getCheckDigit()).isEqualTo("9");
      assertThat(rut.isValid()).isTrue();
    }

    @Test
    void parseNull() {
      assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy( () -> Rut.parse(null))
        .withMessageContaining("rut")
        .withNoCause();
    }
    @Test
    void parseInvalidString() {
      assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy( () -> Rut.parse("helloWorld"))
        .withMessageContaining("helloWorld")
        .withNoCause();
    }
    @Test
    void parseInvalidRut() {
      Rut rut = Rut.parse("30.686.957-5");
      assertThat(rut.getNumber()).isEqualTo(30_686_957);
      assertThat(rut.getCheckDigit()).isEqualTo("5");
      assertThat(rut.isValid()).isFalse();
    }
    @Test
    void parseInvalidCheckDigit() {
      assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy( () -> Rut.parse("1-q"))
        .withMessageContaining("1-q")
        .withNoCause();
    }
  }

  @Test
  void rutInHashSetNoDuplicate() {
    Set<Rut> rutSet = new HashSet<>();
    Rut rut1 = new Rut(1, "1");
    Rut rut2 = new Rut(1, "1");
    Rut rut3 = new Rut(1, "2");
    rutSet.add(rut1);
    rutSet.add(rut2);
    rutSet.add(rut3);

    assertThat(rutSet).hasSize(2);
  }

  @Test
  void rutWithSameNumberAndCheckdigitAreEquals() {
    Rut rut1 = new Rut(1, "1");
    Rut rut2 = new Rut(1, "1");

    assertThat(rut1.equals(rut2)).isTrue();
  }

  @Test
  void rutWithSameNumberAndCheckdigitDifferentCaseAreEquals() {
    Rut rut1 = new Rut(1, "k");
    Rut rut2 = new Rut(1, "K");

    assertThat(rut1.equals(rut2)).isTrue();
  }

  @Test
  void rutWithSameNumberAndCheckdigitDifferentAreNotEquals() {
    Rut rut1 = new Rut(1, "k");
    Rut rut2 = new Rut(1, "5");

    assertThat(rut1.equals(rut2)).isFalse();
  }

  @Test
  void listOfRutSorted() {
    Rut rut1 = new Rut(1, "k");
    Rut rut2 = new Rut(2, "5");
    Rut rut3 = new Rut(3, "6");
    Rut rut4 = new Rut(5, "6");
    Rut rut5 = new Rut(5, "7");

    List<Rut> ruts = Arrays.asList(rut1, rut2, rut3, rut4, rut5)
      .stream()
      .sorted()
      .collect(Collectors.toList());

    assertThat(ruts).containsExactly(rut1, rut2, rut3, rut4, rut5);
  }

  @Test
  void serializeRut() throws IOException, ClassNotFoundException {
    Rut rut = new Rut(1, "k");
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(output);
    out.writeObject(rut);
    output.close();

    byte[] bytes = output.toByteArray();

    ByteArrayInputStream input = new ByteArrayInputStream(bytes);
    ObjectInputStream in = new ObjectInputStream(input);
    Rut serialized = (Rut) in.readObject();
    in.close();
    input.close();

    assertThat(serialized).isEqualTo(rut);
  }

  @Test
  void rutToString() {
    assertThat(new Rut(123, "1").toString()).isEqualTo("123-1");
    assertThat(new Rut(123123, "1").toString()).isEqualTo("123.123-1");
    assertThat(new Rut(10123123, "1").toString()).isEqualTo("10.123.123-1");
    assertThat(new Rut(10123123, "k").toString()).isEqualTo("10.123.123-k");
    assertThat(new Rut(10123123, "K").toString()).isEqualTo("10.123.123-K");
  }
}
