package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ProjektGlowny.commons.config.Config;
import ProjektGlowny.commons.enums.SLMiesiace;
import ProjektGlowny.commons.utils.Interval;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;

public class LicznikDatyTest {

	@BeforeEach
	public void setUp() {
		Config.setSystemTableNames(SystemTablesNames.ABSENCJE);
	}

	@Test
	public void jedenDzienKalendarzowy() {
		assertEquals(1, LicznikDaty.ileDniKalendarzowych(new Interval(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1))));
	}

	@Test
	public void miesiacDzienKalendarzowy() {
		assertEquals(31, LicznikDaty.ileDniKalendarzowych(SLMiesiace.N01_STYCZEN.getOkres(2020)));
	}

	@Test
	@Disabled
	public void ileDniRoboczych() {
		assertEquals(21, LicznikDaty.ileDniRoboczych(SLMiesiace.N01_STYCZEN.getOkres(2021)));
	}
}
