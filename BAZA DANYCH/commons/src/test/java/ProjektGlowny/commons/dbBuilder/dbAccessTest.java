package ProjektGlowny.commons.dbBuilder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import ProjektGlowny.commons.DbBuilder.AccessDB;

public class dbAccessTest {

	@Test
	public void testPolaczenia()
	{
	assertDoesNotThrow(()->AccessDB.executeQuery("SELECT 1 FROM ZESTAWIENIE"));
	}
}
