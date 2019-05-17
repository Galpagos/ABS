package dbAccess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Enums.SLRodzajeAbsencji;

class AbsencjaTest
{
	private static Date data1;
	private static Date data2;
	private static Absencja NowaAbs;

	@BeforeAll
	static void setUpBeforeClass() throws Exception
	{
		String string = "01/01/2001";
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		data1 = format.parse(string);
		data2 = format.parse("02/02/2002");
		NowaAbs = new Absencja(10, 10, data1, data2, SLRodzajeAbsencji.L_4);
	}

	@Test
	void testGetDataOd()
	{
		assertEquals(data1, NowaAbs.getDataOd());
	}

	@Test
	void testGetIdPracownika()
	{

		assertEquals(10, NowaAbs.getIdPracownika());
	}

	@Test
	void testGetId()
	{

		assertEquals(10, NowaAbs.getId());
	}

	@Test
	void testGetDataDo()
	{
		assertEquals(data2, NowaAbs.getDataDo());
	}

	@Test
	void testGetRodzajAbsencji_txt()
	{
		assertEquals("L-4", NowaAbs.GetRodzajAbsencji_txt());
	}

}
