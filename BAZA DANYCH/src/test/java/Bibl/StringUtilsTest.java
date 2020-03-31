package Bibl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import Utils.StringUtils;

class StringUtilsTest {

	@Test
	public void sortownie() {
		List<String> lvLista = Arrays.asList("Adam", "Bartek", "adam", "bartek", "¥dam", "¹dam");

		List<String> lvExpected = Arrays.asList("adam", "Adam", "¹dam", "¥dam", "bartek", "Bartek");
		assertEquals(lvExpected, StringUtils.sortujPL(lvLista));
	}
}
