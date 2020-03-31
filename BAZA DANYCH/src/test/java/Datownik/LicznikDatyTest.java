package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import Enums.SLMiesiace;

public class LicznikDatyTest {

	@Test
	public void jedenDzienKalendarzowy() {
		assertEquals(1,
				LicznikDaty.ileDniKalendarzowych(new Interval(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1))));
	}

	@Test
	public void miesiacDzienKalendarzowy() {
		assertEquals(31, LicznikDaty.ileDniKalendarzowych(SLMiesiace.N01_STYCZEN.getOkres(2020)));
	}

	@Test
	public void ileDniRoboczych() {
		assertEquals(21, LicznikDaty.ileDniRoboczych(SLMiesiace.N01_STYCZEN.getOkres(2021)));
	}
}
