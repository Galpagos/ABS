package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import Enums.SLMiesiace;
import ProjektGlowny.commons.utils.Interval;

public class IntervalTest {

	@Test
	public void okresRok() {

		Interval lvOkres = SLMiesiace.N00_ROK.getOkres(2020);
		assertEquals(LocalDate.of(2020, 1, 1), lvOkres.getStart());
		assertEquals(LocalDate.of(2020, 12, 31), lvOkres.getEnd());
	}

	@Test
	public void okresLuty() {

		Interval lvOkres = SLMiesiace.NO2_LUTY.getOkres(2020);
		assertEquals(LocalDate.of(2020, 2, 1), lvOkres.getStart());
		assertEquals(LocalDate.of(2020, 2, 29), lvOkres.getEnd());
	}

	@Test
	public void okresyNieNachodzaceSie() {
		Interval lvOkres1 = SLMiesiace.N01_STYCZEN.getOkres(2020);
		Interval lvOkres2 = SLMiesiace.NO2_LUTY.getOkres(2020);

		assertFalse(lvOkres1.overlap(lvOkres2).isPresent());
	}

	@Test
	public void okresyTakieSame() {
		Interval lvOkres1 = SLMiesiace.N01_STYCZEN.getOkres(2020);

		assertTrue(lvOkres1.overlap(lvOkres1).isPresent());
		assertEquals(lvOkres1, lvOkres1.overlap(lvOkres1).get());
	}

	@Test
	public void pierwszyMiesiacWRoku() {
		Interval lvOkres1 = SLMiesiace.N01_STYCZEN.getOkres(2020);
		Interval lvOkres2 = SLMiesiace.N00_ROK.getOkres(2020);
		assertTrue(lvOkres1.overlap(lvOkres2).isPresent());
		assertEquals(lvOkres1, lvOkres1.overlap(lvOkres2).get());
		assertEquals(lvOkres1, lvOkres2.overlap(lvOkres1).get());
	}

	@Test
	public void okresWSrodkuRoku() {
		Interval lvOkres1 = SLMiesiace.NO2_LUTY.getOkres(2020);
		Interval lvOkres2 = SLMiesiace.N00_ROK.getOkres(2020);
		assertTrue(lvOkres1.overlap(lvOkres2).isPresent());
		assertEquals(lvOkres1, lvOkres1.overlap(lvOkres2).get());
		assertEquals(lvOkres1, lvOkres2.overlap(lvOkres1).get());
	}

}
