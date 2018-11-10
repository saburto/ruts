package com.saburto.ruts;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class TestRut {

  @Test
  void parseSimpleRut() {
    Rut rut = Rut.parse("1-9");
    assertThat(rut.getNumber()).isEqualTo(1);
    assertThat(rut.getCheckDigit()).isEqualTo("9");
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
  void parseInvalidCheckDigit() {
    assertThatExceptionOfType(IllegalArgumentException.class)
      .isThrownBy( () -> Rut.parse("1-q"))
      .withMessageContaining("1-q")
      .withNoCause();
  }
}