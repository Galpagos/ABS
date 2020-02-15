package Absencja;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import Datownik.Data;
import Enums.SLRodzajeAbsencji;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;

class ObslugaAbsencjiTest {
	@Mock
	AbsencjaRepositor mRepo;
	Object[][] mObj;
	Object[][] mObj2;

	@InjectMocks
	ObslugaAbsencji mObsluga = new ObslugaAbsencji();

	@BeforeEach
	void setUp() {
		Timestamp lvdata = new Timestamp(1559347200000l);
		mObj = new Object[1][6];
		mObj[0][0] = 1;
		mObj[0][1] = 3;
		mObj[0][2] = lvdata;
		mObj[0][3] = lvdata;
		mObj[0][4] = SLRodzajeAbsencji.urlop_w_pracy.getKod();
		mObj[0][5] = SLEkwiwalentZaUrlop.PROCENT_100.getKod();

		mObj2 = new Object[2][6];
		mObj2[0][0] = 1;
		mObj2[0][1] = 3;
		mObj2[0][2] = lvdata;
		mObj2[0][3] = lvdata;
		mObj2[0][4] = SLRodzajeAbsencji.urlop_w_pracy.getKod();
		mObj2[0][5] = SLEkwiwalentZaUrlop.PROCENT_100.getKod();
		mObj2[1][0] = 2;
		mObj2[1][1] = 4;
		mObj2[1][2] = lvdata;
		mObj2[1][3] = lvdata;
		mObj2[1][4] = SLRodzajeAbsencji.urlop_ojcowski.getKod();
		mObj2[1][5] = SLEkwiwalentZaUrlop.PROCENT_100.getKod();

		MockitoAnnotations.initMocks(this);
		org.mockito.Mockito.when(mRepo.getAbsencjePoId(3)).thenReturn(mObj);

		org.mockito.Mockito.when(mRepo.getAbsencjePracownika(1)).thenReturn(mObj2);
	}

	@Test
	void pobranieDanychJednejAbsencji() {
		AbsencjaDTO lvWynik = mObsluga.pobierzAbsencjePoId(3);
		assertEquals(3, lvWynik.getIdPracownika());
		assertEquals(1, lvWynik.getId());
		assertEquals(SLRodzajeAbsencji.urlop_w_pracy, lvWynik.getRodzaj());
		assertEquals(Data.LocalDateFromDate(new Date(1559347200000l)), lvWynik.getOkres().getStart());
		assertEquals(Data.LocalDateFromDate(new Date(1559347800000l)), lvWynik.getOkres().getEnd());
	}

	@Test
	void pobranieDanychPracownika() {
		List<AbsencjaDTO> lvWynik = mObsluga.pobierzAbsencjePracownika(1);
		AbsencjaDTO a1 = lvWynik.get(0);
		assertEquals(3, a1.getIdPracownika());
		assertEquals(1, a1.getId());
		assertEquals(SLRodzajeAbsencji.urlop_w_pracy, a1.getRodzaj());
		assertEquals(Data.LocalDateFromDate(new Date(1559347200000l)), a1.getOkres().getStart());
		assertEquals(Data.LocalDateFromDate(new Date(1559347800000l)), a1.getOkres().getEnd());

		AbsencjaDTO a2 = lvWynik.get(1);

		assertAll(() -> assertEquals(4, a2.getIdPracownika()), //
				() -> assertEquals(2, a2.getId()), //
				() -> assertEquals(SLRodzajeAbsencji.urlop_ojcowski, a2.getRodzaj()), //
				() -> assertEquals(Data.LocalDateFromDate(new Date(1559347200000l)), a2.getOkres().getStart()), //
				() -> assertEquals(Data.LocalDateFromDate(new Date(1559347800000l)), a2.getOkres().getEnd()), //
				() -> assertEquals(2, mObsluga.pobierzAbsencjePracownika(1).size()));
	}
}
