package com.saburto.ruts;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

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
}
