package Absencja;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import enums.SLRodzajeAbsencji;

public class WalidatorOpiekiNaKogos extends WalidatorRodzajuAbsencji {

	private static final Integer LIMIT = 14;
	@Override
	List<SLRodzajeAbsencji> ograniczRodzaje() {
		return Arrays.asList(SLRodzajeAbsencji.opieka_na_kogos);
	}

	@Override
	Function<Integer, Integer> getLimitPracownika() {
		return lvInt -> LIMIT;
	}
}
