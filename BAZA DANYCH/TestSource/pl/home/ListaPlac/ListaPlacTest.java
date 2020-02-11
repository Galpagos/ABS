package pl.home.ListaPlac;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import Datownik.Data;
import Datownik.Interval;
import Enums.SLRodzajeAbsencji;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class ListaPlacTest {
	private ListaPlac mPlace;
	private List<PracownikDTO> mListaPracownikow;
	private List<AbsencjaDTO> mListaAbsencji;
	private List<LocalDate> mListaDniWolnych;

	@Mock
	ListaPlacRepository mRepo;

	@BeforeEach
	public void setUp() {
		mPlace = new ListaPlac(YearMonth.of(2019, 1));
		MockitoAnnotations.initMocks(this);
		mPlace.setListaPlacRepository(mRepo);
		mListaPracownikow = new ArrayList<PracownikDTO>();
		mListaAbsencji = new ArrayList<AbsencjaDTO>();
		mListaDniWolnych = new ArrayList<LocalDate>();
	}

	@Test
	public void pustaLista() {
		assertTrue(mPlace.wyliczWyplate(new ArrayList<PracownikDTO>()).isEmpty());
	}

	@Test
	public void jedenPracownikBezUrlopow() {
		PracownikDTO lvPracownik = new PracownikDTO().setListaAbsencji(new ArrayList<AbsencjaDTO>());
		mListaPracownikow.add(lvPracownik);
		List<MiesiecznaPlacaPracownika> lvWynik = mPlace.wyliczWyplate(mListaPracownikow);

		assertAll(//
				() -> assertEquals(1, lvWynik.size()),
				() -> assertEquals(BigDecimal.valueOf(2600.00).setScale(2), lvWynik.get(0).getKwotaRazem()),
				() -> assertEquals(lvPracownik, lvWynik.get(0).getPracownik()));
	}

	@Test
	public void jedenPracownikUrlopWypoczynkowy() {

		mListaDniWolnych.add(LocalDate.now());
		mListaDniWolnych.add(LocalDate.now());
		Mockito.doReturn(mListaDniWolnych).when(mRepo).getListaDniWolnychWMiesiacu(Mockito.any(YearMonth.class));

		mListaAbsencji.add(//
				new AbsencjaDTO()//
						.setRodzaj(SLRodzajeAbsencji.urlop_wypoczynkowy)//
						.setOkres(new Interval(Data.utworzDate(2018, 12, 21), Data.utworzDate(2019, 1, 1))));

		PracownikDTO lvPracownik = new PracownikDTO()//
				.setId(10)//
				.setListaAbsencji(mListaAbsencji);

		mListaPracownikow.add(lvPracownik);
		List<MiesiecznaPlacaPracownika> lvWynik = mPlace.wyliczWyplate(mListaPracownikow);

		assertAll(//
				() -> assertEquals(1, lvWynik.size()),
				() -> assertEquals(BigDecimal.valueOf(2600.00).setScale(2), lvWynik.get(0).getKwotaRazem()),
				() -> assertEquals(BigDecimal.valueOf(0.00).setScale(2), lvWynik.get(0).getKwotaChorobowa()),
				() -> assertEquals(BigDecimal.valueOf(2476.19).setScale(2), lvWynik.get(0).getKwotaZaPrace()),
				() -> assertEquals(BigDecimal.valueOf(123.81).setScale(2), lvWynik.get(0).getKwotaZaUrlopy()),
				() -> assertEquals(lvPracownik, lvWynik.get(0).getPracownik()));
	}

	@Test
	public void liczbaDniRoboczychWMiesiacuStyczen2020() {
		mPlace.mRokMiesiac = YearMonth.of(2020, 1);
		Mockito.doReturn(mListaDniWolnych).when(mRepo).getListaDniWolnychWMiesiacu(Mockito.any(YearMonth.class));
		assertEquals(23, mPlace.liczbaDniRoboczychWMiesiacu());
	}

	@Test
	public void liczbaDniRoboczychWMiesiacuLuty2020() {
		mPlace.mRokMiesiac = YearMonth.of(2020, 2);
		Mockito.doReturn(mListaDniWolnych).when(mRepo).getListaDniWolnychWMiesiacu(Mockito.any(YearMonth.class));
		assertEquals(20, mPlace.liczbaDniRoboczychWMiesiacu());
	}

	@Test
	public void liczbaDniRoboczychWMiesiacuLuty2020ZDniamiWolnymi() {
		mPlace.mRokMiesiac = YearMonth.of(2020, 2);
		mListaDniWolnych.add(LocalDate.now());
		mListaDniWolnych.add(LocalDate.now());
		Mockito.doReturn(mListaDniWolnych).when(mRepo).getListaDniWolnychWMiesiacu(Mockito.any(YearMonth.class));

		assertEquals(18, mPlace.liczbaDniRoboczychWMiesiacu());
	}

	@Test
	public void jedenPracownikChorobaL4() {

		mListaDniWolnych.add(LocalDate.now());
		mListaDniWolnych.add(LocalDate.now());
		Mockito.doReturn(mListaDniWolnych).when(mRepo).getListaDniWolnychWMiesiacu(Mockito.any(YearMonth.class));

		mListaAbsencji.add(//
				new AbsencjaDTO()//
						.setRodzaj(SLRodzajeAbsencji.L_4)//
						.setOkres(new Interval(Data.utworzDate(2019, 1, 1), Data.utworzDate(2019, 1, 24))));

		PracownikDTO lvPracownik = new PracownikDTO()//
				.setId(10)//
				.setListaAbsencji(mListaAbsencji);

		mListaPracownikow.add(lvPracownik);
		List<MiesiecznaPlacaPracownika> lvWynik = mPlace.wyliczWyplate(mListaPracownikow);

		assertAll(//
				() -> assertEquals(1, lvWynik.size()),
				() -> assertEquals(BigDecimal.valueOf(1807.35).setScale(2), lvWynik.get(0).getKwotaRazem(), "razem"),
				() -> assertEquals(BigDecimal.valueOf(1435.92).setScale(2), lvWynik.get(0).getKwotaChorobowa(),
						"choroba"),
				() -> assertEquals(BigDecimal.valueOf(371.43).setScale(2), lvWynik.get(0).getKwotaZaPrace(),
						"za prace"),
				() -> assertEquals(BigDecimal.valueOf(0.00).setScale(2), lvWynik.get(0).getKwotaZaUrlopy(),
						"Za urlopy"),
				() -> assertEquals(lvPracownik, lvWynik.get(0).getPracownik()));
	}

	@Test
	public void jedenPracownikCiaza() {

		Mockito.doReturn(mListaDniWolnych).when(mRepo).getListaDniWolnychWMiesiacu(Mockito.any(YearMonth.class));

		mListaAbsencji.add(//
				new AbsencjaDTO()//
						.setRodzaj(SLRodzajeAbsencji.ci¹¿a)//
						.setOkres(new Interval(Data.utworzDate(2018, 12, 21), Data.utworzDate(2019, 2, 24))));

		PracownikDTO lvPracownik = new PracownikDTO()//
				.setId(10)//
				.setListaAbsencji(mListaAbsencji);

		mListaPracownikow.add(lvPracownik);
		List<MiesiecznaPlacaPracownika> lvWynik = mPlace.wyliczWyplate(mListaPracownikow);

		assertAll(//
				() -> assertEquals(1, lvWynik.size()),
				() -> assertEquals(BigDecimal.valueOf(2318.18).setScale(2), lvWynik.get(0).getKwotaRazem(), "razem"),
				() -> assertEquals(BigDecimal.valueOf(2318.18).setScale(2), lvWynik.get(0).getKwotaChorobowa(),
						"choroba"),
				() -> assertEquals(BigDecimal.valueOf(0.00).setScale(2), lvWynik.get(0).getKwotaZaPrace(), "za prace"),
				() -> assertEquals(BigDecimal.valueOf(0.00).setScale(2), lvWynik.get(0).getKwotaZaUrlopy(),
						"Za urlopy"),
				() -> assertEquals(lvPracownik, lvWynik.get(0).getPracownik()));
	}
}
