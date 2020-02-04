package Datownik;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.joda.time.Interval;
import org.junit.jupiter.api.Test;

public class JodaTimeTest {
	@Test

	public void test() {
		Date lvstart = new Date();
		Interval lvResult = JodaTime.okresOdDo(lvstart, lvstart);
		assertEquals(2l, lvResult.toDuration().getStandardHours());
	}

}
