package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.jupiter.api.Test;

class DatownikTest
{
	@Test
	void jedenDzien() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("06/24/2017");

		assertEquals(1, LicznikDaty.ileDniPomiedzy(lvFirstDate, lvFirstDate));
	}

	@Test
	void JedenRok() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("06/24/2016");
		Date lvSecondDate = lvFormat.parse("06/23/2017");
		assertEquals(365, LicznikDaty.ileDniPomiedzy(lvFirstDate, lvSecondDate));
	}

	@Test
	void odwrotnaKolejnosc() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("06/24/2016");
		Date lvSecondDate = lvFormat.parse("06/23/2017");
		assertEquals(365, LicznikDaty.ileDniPomiedzy(lvSecondDate, lvFirstDate));
	}

	@Test
	void przestepnyLuty() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("02/20/2016");
		Date lvSecondDate = lvFormat.parse("03/01/2016");
		assertEquals(11, LicznikDaty.ileDniPomiedzy(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRobocze1() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/01/2019");
		assertEquals(1, LicznikDaty.ileDniRobotnych(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRobocze2() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/02/2019");
		assertEquals(2, LicznikDaty.ileDniRobotnych(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRobocze5() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/07/2019");
		assertEquals(5, LicznikDaty.ileDniRobotnych(lvSecondDate, lvFirstDate));
	}

	@Test
	void dniRoboczeKwiecien() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/30/2019");
		assertEquals(22, LicznikDaty.ileDniRobotnych(lvFirstDate, lvSecondDate));
	}

	@Test
	void dniwolne() throws ParseException
	{
		SimpleDateFormat lvFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date lvFirstDate = lvFormat.parse("04/01/2019");
		Date lvSecondDate = lvFormat.parse("04/30/2019");
		assertEquals(3, LicznikDaty.ileDniWolnych(lvFirstDate, lvSecondDate));
	}

	@Test
	void jedenDzienInterwal() throws ParseException
	{

		DateTime lvData = DateTime.now();
		Interval lvOkres = new Interval(lvData, lvData);
		assertEquals(5, lvOkres.getStart().getDayOfMonth());
	}
}
