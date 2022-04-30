package Absencja;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import enums.SLRodzajeAbsencji;

public class WalidatorUrlopuMacierzynskiego extends WalidatorRodzajuAbsencji {
	private static int LIMIT = 140;
	WalidatorUrlopuMacierzynskiego() {
	}

	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return lvInt -> LIMIT;
	}

	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return Arrays.asList(SLRodzajeAbsencji.urlop_macierzynski);
	}
}
