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
}
