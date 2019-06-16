package Absencja;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import Enums.SLRodzajeAbsencji;
import PrzygotowanieDanych.AbsencjaDTO;

class ObslugaAbsencjiTest
{
	@Mock
	AbsencjaRepository mRepo;
	Object[][] mObj;
	Object[][] mObj2;

	@InjectMocks
	ObslugaAbsencji mObsluga = new ObslugaAbsencji();

	@BeforeEach
	void setUp()
	{
		Timestamp lvdata = new Timestamp(1559347200000l);
		mObj = new Object[1][5];
		mObj[0][0] = 1;
		mObj[0][1] = 3;
		mObj[0][2] = lvdata;
		mObj[0][3] = lvdata;
		mObj[0][4] = SLRodzajeAbsencji.urlop_w_pracy.toString();

		mObj2 = new Object[2][5];
		mObj2[0][0] = 1;
		mObj2[0][1] = 3;
		mObj2[0][2] = lvdata;
		mObj2[0][3] = lvdata;
		mObj2[0][4] = SLRodzajeAbsencji.urlop_w_pracy.toString();
		mObj2[1][0] = 2;
		mObj2[1][1] = 4;
		mObj2[1][2] = lvdata;
		mObj2[1][3] = lvdata;
		mObj2[1][4] = SLRodzajeAbsencji.urlop_ojcowski.toString();

		MockitoAnnotations.initMocks(this);
		org.mockito.Mockito.when(mRepo.getAbsencjePoId(3)).thenReturn(mObj);

		org.mockito.Mockito.when(mRepo.getAbsencjePracownika(1)).thenReturn(mObj2);
	}

	@Test
	void pobranieDanychJednejAbsencji()
	{
		AbsencjaDTO lvWynik = mObsluga.pobierzAbsencjePoId(3);
		assertEquals(3, lvWynik.getIdPracownika());
		assertEquals(1, lvWynik.getId());
		assertEquals(SLRodzajeAbsencji.urlop_w_pracy, lvWynik.getRodzaj());
		assertEquals(new Date(1559347200000l), lvWynik.getOkres().getStart().toDate());
		assertEquals(new Date(1559347800000l), lvWynik.getOkres().getEnd().toDate());
	}

	@Test
	void pobranieDanychPracownika()
	{
		List<AbsencjaDTO> lvWynik = mObsluga.pobierzAbsencjePracownika(1);
		AbsencjaDTO a1 = lvWynik.get(0);
		assertEquals(3, a1.getIdPracownika());
		assertEquals(1, a1.getId());
		assertEquals(SLRodzajeAbsencji.urlop_w_pracy, a1.getRodzaj());
		assertEquals(new Date(1559347200000l), a1.getOkres().getStart().toDate());
		assertEquals(new Date(1559347800000l), a1.getOkres().getEnd().toDate());

		AbsencjaDTO a2 = lvWynik.get(1);

		assertAll(() -> assertEquals(4, a2.getIdPracownika()), //
				() -> assertEquals(2, a2.getId()), //
				() -> assertEquals(SLRodzajeAbsencji.urlop_ojcowski, a2.getRodzaj()), //
				() -> assertEquals(new Date(1559347200000l), a2.getOkres().getStart().toDate()), //
				() -> assertEquals(new Date(1559347800000l), a2.getOkres().getEnd().toDate()), //
				() -> assertEquals(2, mObsluga.pobierzAbsencjePracownika(1).size()));
	}

	@Test
	void jedenDzienWolny()
	{
		int lvWynik = mObsluga.ileDniRoboczych(new Interval(1559347200000l, 1559347800000l));
		assertEquals(0, lvWynik);
	}

	@Test
	void jedenDzienPracowity()
	{
		int lvWynik = mObsluga.ileDniRoboczych(new Interval(1559520000000l, 1559520600000l));
		assertEquals(1, lvWynik);
	}

	@Test
	void szescDniPiecRoboczych()
	{
		int lvWynik = mObsluga.ileDniRoboczych(new Interval(1559520000000l, 1559952600000l));
		assertEquals(5, lvWynik);
	}

	@Test
	void roboczeWCzerwcu()
	{
		int lvWynik = mObsluga.ileDniRoboczych(new Interval(1559347200000l, 1561853400000l));
		assertEquals(20, lvWynik);
	}

	@Test
	void roboczeWMaju()
	{
		int lvWynik = mObsluga.ileDniRoboczych(new Interval(1556668800000l, 1559261400000l));
		assertEquals(23, lvWynik);
	}
}