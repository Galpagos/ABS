package Absencja;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import enums.SLRodzajeAbsencji;

public class WalidatorNaZadanie extends WalidatorRodzajuAbsencji {

	private static Integer LIMIT = 4;
	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return Arrays.asList(SLRodzajeAbsencji.NZ);
	}

	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return lvInt -> LIMIT;
	}

}
