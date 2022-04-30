package ProjektGlowny.commons.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ProjektGlowny.commons.enums.SLMiesiace;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

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

	@Test
	public void getLiczbaDniKalendarzowychLuty() {
		Interval lvOkres1 = SLMiesiace.NO2_LUTY.getOkres(2021);
		assertEquals(28, lvOkres1.getLiczbaDniKalendarzowych());
	}

	@Test
	public void getLiczbaDniRoboczych0() {
		Interval lvOkres1 = new Interval(LocalDate.of(2021, 1, 2), LocalDate.of(2021, 1, 2));
		assertEquals(0, lvOkres1.getLiczbaDniRoboczych());
	}

	@Test
	public void getLiczbaDniRoboczych1() {
		Interval lvOkres1 = new Interval(LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 5));
		assertEquals(1, lvOkres1.getLiczbaDniRoboczych());
	}

	@Test
	public void getLiczbaDniRoboczychLuty() {
		Interval lvOkres1 = SLMiesiace.NO2_LUTY.getOkres(2021);
		assertEquals(20, lvOkres1.getLiczbaDniRoboczych());
	}

}
