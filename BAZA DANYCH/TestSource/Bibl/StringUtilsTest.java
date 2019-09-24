package Bibl;

import static Utils.StringUtils.getKwotaDoDwochMiejscZKropka;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import Utils.StringUtils;

class StringUtilsTest
{

	@Test
	public void mniejNizDwaMiejscaWlLiczbie()
	{
		assertEquals("12.10", getKwotaDoDwochMiejscZKropka(12.1d));
	}

	@Test
	public void zaokraglanieWDol()
	{
		assertEquals("12.01", getKwotaDoDwochMiejscZKropka(12.01324234d));
	}

	@Test
	public void zaokraglanieDoGory()
	{
		assertEquals("12.02", getKwotaDoDwochMiejscZKropka(12.01624234d));
	}

	@Test
	public void sortownie()
	{
		List<String> lvLista = Arrays.asList("Adam", "Bartek", "adam", "bartek", "¥dam", "¹dam");

		List<String> lvExpected = Arrays.asList("adam", "Adam", "¹dam", "¥dam", "bartek", "Bartek");
		assertEquals(lvExpected, StringUtils.sortujPL(lvLista));
	}
}
