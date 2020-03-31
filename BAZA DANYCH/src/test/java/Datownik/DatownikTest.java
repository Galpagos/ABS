package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

class DatownikTest {

	@Test
	void filtrujAbsencjePoOkresie() {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		lvLista.add(
				new AbsencjaDTO().setOkres(new Interval(Data.utworzDate(2019, 1, 22), Data.utworzDate(2019, 2, 10))));
		LicznikDaty.filtrujAbsencjePoOkresie(lvLista, new Interval(2, 2019));
		assertEquals(LocalDate.of(2019, 2, 1), lvLista.get(0).getOkres().getStart());
	}

}
