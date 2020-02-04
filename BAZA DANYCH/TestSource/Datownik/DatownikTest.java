package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

class DatownikTest {
	@Test
	void jedenDzien() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("06/24/2017");

		assertEquals(1, LicznikDaty.ileDniPomiedzy(lvFirstDate, lvFirstDate));
	}

	@Test
	void JedenRok() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("06/24/2016");
		Date lvSecondDate = lvFormat.parse("06/23/2017");
		assertEquals(365, LicznikDaty.ileDniPomiedzy(lvFirstDate, lvSecondDate));
	}

	@Test
	void odwrotnaKolejnosc() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("06/24/2016");
		Date lvSecondDate = lvFormat.parse("06/23/2017");
		assertEquals(365, LicznikDaty.ileDniPomiedzy(lvSecondDate, lvFirstDate));
	}

	@Test
	void przestepnyLuty() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("02/20/2016");
		Date lvSecondDate = lvFormat.parse("03/01/2016");
		assertEquals(11, LicznikDaty.ileDniPomiedzy(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRobocze1() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/01/2019");
		assertEquals(1, LicznikDaty.ileDniRobotnych(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRobocze2() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/02/2019");
		assertEquals(2, LicznikDaty.ileDniRobotnych(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRobocze5() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/07/2019");
		assertEquals(5, LicznikDaty.ileDniRobotnych(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRoboczeKwiecien() throws ParseException {
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/30/2019");
		assertEquals(22, LicznikDaty.ileDniRobotnych(lvFirstDate, lvSecondDate));
	}

	@Test
	void filtrujAbsencjePoOkresie() {
		List<AbsencjaDTO> lvLista = new ArrayList<>();
		lvLista.add(new AbsencjaDTO()
				.setOkres(JodaTime.okresOdDo(Data.utworzDate(2019, 1, 22), Data.utworzDate(2019, 2, 10))));
		LicznikDaty.filtrujAbsencjePoOkresie(lvLista, JodaTime.okresMiesiac(2, 2019));
		assertEquals(new LocalDate(2019, 2, 1), lvLista.get(0).getOkres().getStart().toLocalDate());
	}

}
