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

}
