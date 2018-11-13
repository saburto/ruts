package com.saburto.ruts;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

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

  @Nested
  class RutParseValid {
    @Test
    void parseValidSimpleRut() {
      Rut rut = Rut.parseValid("1-9");
      assertThat(rut.getNumber()).isEqualTo(1);
      assertThat(rut.getCheckDigit()).isEqualTo("9");
    }

    @Test
    void parseValidSimpleRutWithK() {
      Rut rut = Rut.parseValid("23-k");
      assertThat(rut.getNumber()).isEqualTo(23);
      assertThat(rut.getCheckDigit()).isEqualTo("k");
    }

    @Test
    void parseValidLargeRut() {
      Rut rut = Rut.parseValid("1100001000-8");
      assertThat(rut.getNumber()).isEqualTo(1_100_001_000);
      assertThat(rut.getCheckDigit()).isEqualTo("8");
    }

    @Test
    void parseValidLargeRutWhitThousandSeparators() {
      Rut rut = Rut.parseValid("1.100.001.000-8");
      assertThat(rut.getNumber()).isEqualTo(1_100_001_000);
      assertThat(rut.getCheckDigit()).isEqualTo("8");
    }

    @Test
    void parseValidNull() {
      assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy( () -> Rut.parseValid(null))
        .withMessageContaining("rut")
        .withNoCause();
    }

    @Test
    void parseValidInvalidString() {
      assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy( () -> Rut.parseValid("helloWorld"))
        .withMessageContaining("helloWorld")
        .withNoCause();
    }

    @Test
    void parseValidInvalidCheckDigit() {
      assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy( () -> Rut.parseValid("1-q"))
        .withMessageContaining("1-q")
        .withNoCause();
    }

    @Test
    void parseValidInvalidRut() {
      assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy( () -> Rut.parseValid("30.686.957-5"))
        .withMessageContaining("30.686.957-5")
        .withNoCause();
    }

    @Test
    void parseValidValidRut() {
      Rut rut = Rut.parseValid("30.686.957-4");
      assertThat(rut.getNumber()).isEqualTo(30_686_957);
      assertThat(rut.getCheckDigit()).isEqualTo("4");
    }
  }
}
